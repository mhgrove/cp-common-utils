package com.clarkparsia.utils.yahoo.boss;

import java.net.URL;

/**
 * Title: SearchResult<br/>
 * Description: A single result of a search<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:14:12 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public interface SearchResult {

	/**
	 * Return the URL to the result
	 * @return the url
	 */
	public URL getUrl();

	/**
	 * Return the title of the result
	 * @return the title
	 */
	public String getTitle();

	/**
	 * Return an abstract of the search result
	 * @return the abstract
	 */
	public String getAbstract();
}
