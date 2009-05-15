package com.clarkparsia.utils.yahoo.boss;

import java.net.URL;

/**
 * Title: ResultsBuilder<br/>
 * Description: Interface for an instance which takes a URL to a search and returns the search results listed
 * there.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 15, 2009 8:46:29 AM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public interface ResultsBuilder<T extends SearchResult> {

	/**
	 * Returns the search results contained at the given URL
	 * @param theURL the URL to get the search results from
	 * @return the search results
	 * @throws BOSSException thrown if there is an error while getting or parsing the search results
	 */
	public SearchResults<T> result(URL theURL) throws BOSSException;
}
