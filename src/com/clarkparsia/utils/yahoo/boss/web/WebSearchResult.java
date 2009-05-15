package com.clarkparsia.utils.yahoo.boss.web;

import com.clarkparsia.utils.yahoo.boss.AbstractSearchResult;

import java.util.Map;

/**
 * Title: WebSearchResult <br/>
 * Description: Result from a web search<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 8:34:48 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class WebSearchResult extends AbstractSearchResult {

	/**
	 * Create a new WebSearchResult
	 * @param theResult the result data
	 */
	public WebSearchResult(Map<String, String> theResult) {
		super(theResult);
	}
}
