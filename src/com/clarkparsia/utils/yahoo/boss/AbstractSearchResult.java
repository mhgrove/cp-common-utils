package com.clarkparsia.utils.yahoo.boss;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

/**
 * Title: AbstractSearchResult<br/>
 * Description: Abstract implementation of a search result which provides access to some universal properties of search
 * results like URL and title.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:14:40 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public abstract class AbstractSearchResult implements SearchResult, ResultConsts {

	/**
	 * The result data
	 */
	private Map<String, String> mResult;

	/**
	 * Create a new AbstractSearchResult
	 * @param theResult the result data
	 */
	public AbstractSearchResult(Map<String, String> theResult) {
		mResult = theResult;
	}

	/**
	 * Returns the result data
	 * @return the result data
	 */
	protected Map<String, String> getResult() {
		return Collections.unmodifiableMap(mResult);
	}

	/**
	 * @inheritDoc
	 */
	public URL getUrl() {
		try {
			return new URL(getResult().get(KEY_URL));
		}
		catch (MalformedURLException e) {
			throw new RuntimeException("Yahoo returned an invalid URL?!?! " + getResult().get(KEY_URL));
		}
	}

	/**
	 * @inheritDoc
	 */
	public String getTitle() {
		return getResult().get(KEY_TITLE);
	}

	/**
	 * @inheritDoc
	 */
	public String getAbstract() {
		return getResult().get(KEY_ABSTRACT);
	}
}
