package com.clarkparsia.utils.yahoo.boss;

import java.net.URL;
import java.net.URLEncoder;
import java.net.MalformedURLException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Title: AbstractSearch<br/>
 * Description: Abstract implememtation of the Search interface.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:15:50 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
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
