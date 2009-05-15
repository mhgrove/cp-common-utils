package com.clarkparsia.utils.yahoo.boss.image;

import com.clarkparsia.utils.yahoo.boss.AbstractSearch;
import com.clarkparsia.utils.yahoo.boss.XMLResultsBuilder;
import com.clarkparsia.utils.yahoo.boss.SearchResults;
import com.clarkparsia.utils.yahoo.boss.BOSSException;

import java.util.Map;
import java.net.MalformedURLException;

/**
 * Title: ImageSearch<br/>
 * Description: Implementation of the Search interface which conducts image searches using Yahoo's BOSS API.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:12:03 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class ImageSearch extends AbstractSearch<ImageSearchResult> {

	/**
	 * The results builder for taking search results and parsing them into the results structure
	 */
	private static final XMLResultsBuilder<ImageSearchResult> RESULTS_BUILDER = new XMLResultsBuilder<ImageSearchResult>("resultset_images", new ImageSearchResultFactory());

	/**
	 * Create a new ImageSearch with the given AppId
	 * @param theKey a valid AppId for using the BOSS API
	 */
	ImageSearch(String theKey) {
		super("http://boss.yahooapis.com/ysearch/images/v1/", theKey);
	}

	/**
	 * Return an instance of an ImageSearch using the given AppId
	 * @param theKey the AppId to use for searches
	 * @return a new ImageSearch
	 */
	public static ImageSearch instance(String theKey) {
		return new ImageSearch(theKey);
	}

	/**
	 * @inheritDoc
	 */
	public SearchResults<ImageSearchResult> search(final String theSearchTerm) throws BOSSException {
		try {
			return RESULTS_BUILDER.result(getSearchURL(theSearchTerm));
		}
		catch (MalformedURLException e) {
			throw new BOSSException(e);
		}
	}

	/**
	 * @inheritDoc
	 */
	public SearchResults<ImageSearchResult> search(final String theSearchTerm, final Map<String, String> theParameters) throws BOSSException {
		// TODO: does the image search take parameters
		return search(theSearchTerm);
	}

	public static void main(String[] args) throws Exception {
		ImageSearch aSearch = new ImageSearch("YoXKPEHV34ErgUqn31aqFYEPi2.Vk_xSYGn207ezkXg9HF3a7rtrSXw8XQmNDx7MOA--");

		SearchResults<ImageSearchResult> aResults = aSearch.search("roger clemens");
		
		System.err.println(aResults.hasNextPage());
		for (ImageSearchResult aRes : aResults) {
			System.err.println(aRes.getUrl());
		}
	}
}
