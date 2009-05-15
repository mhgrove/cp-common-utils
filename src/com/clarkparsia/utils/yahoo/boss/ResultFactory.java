package com.clarkparsia.utils.yahoo.boss;

import java.util.Map;

/**
 * Title: ResultFactory<br/>
* Description: Interface for creating SearchResult instances.<br/>
* Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
* Created: May 14, 2009 3:54:36 PM <br/>
*
* @author Michael Grove <mike@clarkparsia.com>
*/
public interface ResultFactory<T extends SearchResult> {

	/**
	 * Creates a new SearchResult
	 * @param theResultData the data for the result
	 * @return a new SearchResult
	 */
	public T newResult(Map<String, String> theResultData);
}
