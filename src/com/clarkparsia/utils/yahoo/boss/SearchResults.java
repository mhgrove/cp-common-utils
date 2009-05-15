package com.clarkparsia.utils.yahoo.boss;

import java.util.Map;

/**
 * Title: SearchResults<br/>
 * Description: The entire results of a search.  It represents an iterable set of page search results.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 4:04:42 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public interface SearchResults<T extends SearchResult> extends Iterable<T> {

	/**
	 * Return the attributes of the search result.  This contains things such as the total number of results of the search,
	 * and which page you're on.
	 * @return the result attributes.
	 */
	public Map<String, String> getResultAttributes();

	/**
	 * Return whether or not there is another page of search results
	 * @return true if there is another page, or false if not
	 */
	public boolean hasNextPage();

	/**
	 * Move the cursor to the next page of results
	 * @return return a cursor to the next page of results
	 * @throws BOSSException thrown if there is an error while getting the next page of results.
	 */
	public SearchResults<T> nextPage() throws BOSSException;
}
