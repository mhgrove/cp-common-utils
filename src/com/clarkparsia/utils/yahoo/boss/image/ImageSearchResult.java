package com.clarkparsia.utils.yahoo.boss.image;

import com.clarkparsia.utils.yahoo.boss.AbstractSearchResult;

import java.util.Map;

/**
 * Title: ImageSearchResult<br/>
 * Description: Implementation of the SearchResult interface for image search specific results.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:14:18 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class ImageSearchResult extends AbstractSearchResult {

	/**
	 * Create a new ImageSeachResult
	 * @param theMap the search result data
	 */
	public ImageSearchResult(Map<String, String> theMap) {
		super(theMap);
	}
}
