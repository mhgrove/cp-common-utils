package com.clarkparsia.utils.yahoo.boss;

import com.clarkparsia.utils.yahoo.boss.web.WebSearch;
import com.clarkparsia.utils.yahoo.boss.web.WebSearchResult;
import com.clarkparsia.utils.yahoo.boss.image.ImageSearch;
import com.clarkparsia.utils.yahoo.boss.image.ImageSearchResult;

import java.util.Map;

/**
 * Title: BOSS<br/>
 * Description: Factory for creating and running searches via the BOSS API<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:17:08 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class BOSS {

	/**
	 * The AppId to use for searches
	 */
	private String mKey;

	/**
	 * Searcher for image searches
	 */
	private ImageSearch mImgSearch;

	/**
	 * Searcher for web searches
	 */
	private WebSearch mWebSearch;

	/**
	 * Create a new BOSS with the given AppId
	 * @param theKey the AppId
	 */
	private BOSS(String theKey) {
		mKey = theKey;
	}

	/**
	 * Return an instance of a BOSS with the given AppId
	 * @param theKey the AppId to use for searches
	 * @return a BOSS instances
	 */
	public static BOSS instance(String theKey) {
		return new BOSS(theKey);
	}

	/**
	 * Return the searcher for conducting web searches
	 * @return the web searcher
	 */
	private WebSearch getWebSearch() {
		if (mWebSearch == null) {
			mWebSearch = WebSearch.instance(mKey);
		}

		return mWebSearch;
	}

	/**
	 * Return the searcher for conducting image searches
	 * @return the image searcher
	 */
	private ImageSearch getImageSearch() {
		if (mImgSearch == null) {
			mImgSearch = ImageSearch.instance(mKey);
		}

		return mImgSearch;
	}

	/**
	 * Perform an image search
	 * @param theTerm the search term
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<ImageSearchResult> imageSearch(String theTerm) throws BOSSException {
		return getImageSearch().search(theTerm);
	}

	/**
	 * Perform an image search
	 * @param theTerm the search term
	 * @param theParams parameters for the search.
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<ImageSearchResult> imageSearch(String theTerm, Map<String, String> theParams) throws BOSSException {
		return getImageSearch().search(theTerm, theParams);
	}

	/**
	 * Perform a web search
	 * @param theTerm the search term
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<WebSearchResult> webSearch(String theTerm) throws BOSSException {
		return getWebSearch().search(theTerm);
	}

	/**
	 * Perform a web search
	 * @param theTerm the search term
	 * @param theParams parameters for the search.
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<WebSearchResult> webSearch(String theTerm, Map<String, String> theParams) throws BOSSException {
		return getWebSearch().search(theTerm, theParams);
	}

	public static void main(String[] args) throws BOSSException {
		BOSS aYahoo = BOSS.instance("YoXKPEHV34ErgUqn31aqFYEPi2.Vk_xSYGn207ezkXg9HF3a7rtrSXw8XQmNDx7MOA--");

		int aPageCount = 1;
		SearchResults<WebSearchResult> aResults = aYahoo.webSearch("baltimore orioles");

		while (aPageCount < 6 && aResults.hasNextPage()) {
			System.err.println("Displaying Results Page: " + aPageCount);

			for (WebSearchResult aResult : aResults) {
				System.err.println("Title: " + aResult.getTitle());
				System.err.println("URL: " + aResult.getUrl());
				System.err.println("Abstract: " + aResult.getAbstract());
				System.err.println();
			}

			aResults.nextPage();
			aPageCount++;
		}
	}
}
