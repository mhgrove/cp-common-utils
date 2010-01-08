/*
 * Copyright (c) 2005-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.utils.yahoo.boss;

import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Attr;

import javax.xml.parsers.DocumentBuilderFactory;

import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.net.URL;
import java.net.HttpURLConnection;

/**
 * <p>Implementation of the ResultBuilder interface for parsing XML formatted result sets from the BOSS API.</p>
 *
 * @author Michael Grove
 * @since 1.0
 */
public class XMLResultsBuilder<T extends SearchResult> implements ResultsBuilder<T> {

	/**
	 * Document builder factory used for parsing the XML
	 */
	private DocumentBuilderFactory mDocumentBuilderFactory = DocumentBuilderFactory.newInstance();

	/**
	 * The node name of the result set
	 */
	private String mResultsNodeName;

	/**
	 * The result factory to use to create resutls
	 */
	private ResultFactory<T> mResultFactory;

	/**
	 * Create a new XMLResultsBuilder
	 * @param theName the name of the result set node
	 * @param theFactory the ResultFactory to use to create results
	 */
	public XMLResultsBuilder(String theName, ResultFactory<T> theFactory) {
		mResultsNodeName = theName;
		mResultFactory = theFactory;
	}

	/**
	 * @inheritDoc
	 */
	public SearchResults<T> result(URL theURL) throws BOSSException {
		try {
			return result(getResultStream(theURL));
		}
		catch (IOException e) {
			throw new BOSSException(e);
		}
	}

	/**
	 * Returns the search results contained in the given stream
	 * @param theStream the input stream
	 * @return the results contained on the stream
	 * @throws BOSSException thrown if there is an error while reading from the stream, or parsing its contents.
	 */
	private SearchResults<T> result(InputStream theStream) throws BOSSException {
        InputSource is = new InputSource(theStream);

        Document doc;

		try {
            doc = mDocumentBuilderFactory.newDocumentBuilder().parse(is);
        }
		catch (Exception e) {
            throw new BOSSException("Search Result Parse Error", e);
		}

		Element aResponseElem = doc.getDocumentElement();

		String aCode = aResponseElem.getAttribute("responsecode");
		if (!aCode.equals("200")) {
			throw new BOSSException("Search failed with response code: " + aCode);
		}

		String aNext = null;
		Node aNextPage = child(aResponseElem, "nextpage");
		if (aNextPage != null) {
			// there should only be one
			aNext = aNextPage.getChildNodes().item(0).getTextContent();

			if (aNext != null && !aNext.startsWith("http://boss.yahooapis.com")) {
				aNext = "http://boss.yahooapis.com" + aNext;
			}
		}


		Node aResultsNode = child(aResponseElem, mResultsNodeName);
		Map<String, String> aAttrs = new HashMap<String, String>();

		List<T> aList = new ArrayList<T>();

		if (aResultsNode != null) {
			// there should only be one results set in the returned results
			aAttrs = attributes((Element) aResultsNode);
			NodeList aResults = aResultsNode.getChildNodes();
			for (int i = 0; i < aResults.getLength(); i++) {
				if (!aResults.item(i).getNodeName().equals("result")) {
					continue;
				}

				aList.add(mResultFactory.newResult(map(aResults.item(i))));
			}
		}

        return new DefaultResults<T>(aAttrs, aList, aNext);
	}

	/**
	 * Returns the first child with the given name
	 * @param theNode the node whose children are to be searched
	 * @param theName the node name of the child to find
	 * @return the first child with the given name, or null if one is not found.
	 */
	private Node child(Node theNode, String theName) {
		NodeList aChildren = theNode.getChildNodes();
		for (int aIndex = 0; aIndex < aChildren.getLength(); aIndex++) {
			if (aChildren.item(aIndex).getNodeName().equals(theName)) {
				return aChildren.item(aIndex);
			}
		}

		return null;
	}

	/**
	 * Return the child elements as a map.  The key's are the node names of the children, and the values are the text
	 * content of the nodes.  This assumes that the given node is flat.
	 * @param theElem the node
	 * @return the node's children as a map
	 */
	private Map<String, String> map(Node theElem) {
		Map<String, String> aMap = new HashMap<String, String>();

		NodeList aNodeList = theElem.getChildNodes();
		for (int i = 0; i < aNodeList.getLength(); i++) {
			Node aNode = aNodeList.item(i);
			aMap.put(aNode.getNodeName(), aNode.getTextContent());
		}

		return aMap;
	}

	/**
	 * Return the attributes on the element as a Map
	 * @param theElem the element
	 * @return the element's attributes as a map
	 */
	private Map<String, String> attributes(Element theElem) {
		Map<String, String> aAttributeMap = new HashMap<String, String>();
		NamedNodeMap aAttrMap = theElem.getAttributes();
		for (int aIndex = 0; aIndex < aAttrMap.getLength(); aIndex++) {
			Attr aAttr = (Attr) aAttrMap.item(aIndex);
			aAttributeMap.put(aAttr.getName(), aAttr.getValue());
		}

		return aAttributeMap;
	}

	/**
	 * Open a connection to the URL.
	 * @param theURL the URL
	 * @return the inputstream from the URL
	 * @throws IOException thrown if there is an error while opening the connection
	 */
	private InputStream getResultStream(URL theURL) throws IOException {
		HttpURLConnection aConn = (HttpURLConnection) theURL.openConnection();

		// TODO: should we check the response code or error stream?  probably only if we want to give a good error message

		return aConn.getInputStream();
	}

	/**
	 * Implementation of SearchResults which handles the paging of the result set
	 * @param <T> The type of search results returned by this instance
	 */
	private class DefaultResults<T extends SearchResult> implements SearchResults<T> {

		/**
		 * The URL to the next page of results
		 */
		private String mNextPageURL;

		/**
		 * The current set of results
		 */
		private List<T> mResults;

		/**
		 * The attributes of this result set
		 */
		private Map<String, String> mAttributes;

		/**
		 * Create a new DefaultResults
		 * @param theAttrs the results attributes
		 * @param theResults the current page of results
		 * @param theNextURL the url to the next page of results
		 */
		public DefaultResults(Map<String, String> theAttrs, List<T> theResults, String theNextURL) {
			mAttributes = theAttrs;
			mNextPageURL = theNextURL;
			mResults = theResults;
		}

		/**
		 * @inheritDoc
		 */
		public Map<String, String> getResultAttributes() {
			return mAttributes;
		}

		/**
		 * @inheritDoc
		 */
		public boolean hasNextPage() {
			return mNextPageURL != null;
		}

		/**
		 * @inheritDoc
		 */
		public SearchResults<T> nextPage() throws BOSSException {
			if (!hasNextPage()) {
				throw new NoSuchElementException();
			}

			try {
				// TODO: fuck this generics shit
				return (SearchResults<T>) result(getResultStream(new URL(mNextPageURL)));
			}
			catch (IOException e) {
				throw new BOSSException(e);
			}
		}

		/**
		 * @inheritDoc
		 */
		public Iterator<T> iterator() {
			return mResults.iterator();
		}
	}
}
