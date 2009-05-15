package com.clarkparsia.utils.yahoo.boss.image;

import com.clarkparsia.utils.yahoo.boss.ResultFactory;

import java.util.Map;

/**
 * Title: ImageSearchResultFactory<br/>
 * Description: Implementation of the ResultFactory interface which creates instances of ImageSearchResults.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:54:45 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
class ImageSearchResultFactory implements ResultFactory<ImageSearchResult> {

	/**
	 * @inheritDoc
	 */
	public ImageSearchResult newResult(final Map<String, String> theResultData) {
		return new ImageSearchResult(theResultData);
	}
}
