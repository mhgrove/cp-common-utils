package com.clarkparsia.utils.yahoo.boss;

import java.util.Map;

/**
 * Title: Search<br/>
 * Description: Interface for accessing and performing a search on the web.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:13:05 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public interface Search<T extends SearchResult> {

	/**
	 * Perform a search
	 * @param theSearchTerm the term(s) to search for
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<T> search(String theSearchTerm) throws BOSSException;

	/**
	 * Perform a search
	 * @param theSearchTerm the term to search for
	 * @param theParameters addtional parameters for the search.  This vary based on the type of search.
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<T> search(String theSearchTerm, Map<String, String> theParameters) throws BOSSException;
}
