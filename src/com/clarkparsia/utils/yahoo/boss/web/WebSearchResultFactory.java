package com.clarkparsia.utils.yahoo.boss.web;

import com.clarkparsia.utils.yahoo.boss.ResultFactory;
import com.clarkparsia.utils.yahoo.boss.SearchResult;

import java.util.Map;

/**
 * Title: WebSearchResultFactory<br/>
 * Description: Implementation of the ResultFactory interface which creates WebSearchResults.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 8:35:35 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
class WebSearchResultFactory implements ResultFactory<WebSearchResult> {

	/**
	 * @inheritDoc
	 */
	public WebSearchResult newResult(final Map<String, String> theResultData) {
		return new WebSearchResult(theResultData);
	}
}
