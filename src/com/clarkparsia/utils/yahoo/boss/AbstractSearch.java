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

import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>Abstract implementation of the Search interface.</p>
 *
 * @author Michael Grove
 * @since 1.0
 */

public abstract class AbstractSearch<T extends SearchResult> implements Search<T> {

	/**
	 * AppId for the search
	 */
	private String mKey;

	/**
	 * Base URL for the search
	 */
	private String mURL;

	protected AbstractSearch(String theURL, String theKey) {
		mKey = theKey;
		mURL = theURL;

		if (!mURL.endsWith("/")) {
			mURL += "/";
		}

		// TODO: don't hardcode to get XML results
	}

	/**
	 * Constructs a search URl for the given search term
	 * @param theSearchTerm the search term
	 * @return the url for the search
	 * @throws MalformedURLException thrown if a URL cannot be constructed
	 */
	protected URL getSearchURL(String theSearchTerm) throws MalformedURLException {
		try {
			return new URL(mURL + URLEncoder.encode(theSearchTerm, "UTF-8") + "?appid=" + mKey+"&format=xml");
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Constructs a searl URL for the given search term
	 * @param theSearchTerm the search term
	 * @param theParams extra parameters for the search.  these depend on the type of search be conducted
	 * @return the URL for the search
	 * @throws MalformedURLException thrown if a URL cannot be constructed
	 */
	protected URL getSearchURL(String theSearchTerm, Map<String, String> theParams) throws MalformedURLException {		
		try {
			StringBuffer aParamString = new StringBuffer();
			for (String aKey : theParams.keySet()) {
				aParamString.append("&").append(aKey).append("=").append(URLEncoder.encode(theParams.get(aKey), "UTF-8"));
			}

			return new URL(mURL + URLEncoder.encode(theSearchTerm, "UTF-8") + "?appid=" + mKey + "&format=xml" + aParamString.toString());
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
