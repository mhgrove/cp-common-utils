package com.clarkparsia.utils.yahoo.boss.web;

import com.clarkparsia.utils.yahoo.boss.XMLResultsBuilder;
import com.clarkparsia.utils.yahoo.boss.SearchResults;
import com.clarkparsia.utils.yahoo.boss.BOSSException;
import com.clarkparsia.utils.yahoo.boss.AbstractSearch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Title: WebSearch <br/>
 * Description: Conducts a web search via Yahoo's BOSS API<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 8:34:40 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class WebSearch extends AbstractSearch<WebSearchResult> {

	/**
	 * Results builder
	 */
	private static final XMLResultsBuilder<WebSearchResult> RESULTS_BUILDER = new XMLResultsBuilder<WebSearchResult>("resultset_web", new WebSearchResultFactory());

	/**
	 * Create a new WebSearch
	 * @param theKey the AppId
	 */
	WebSearch(String theKey) {
		super("http://boss.yahooapis.com/ysearch/web/v1/", theKey);
	}

	/**
	 * Return an instance of WebSearch with the given AppId
	 * @param theKey the AppId
	 * @return a web search
	 */
	public static WebSearch instance(String theKey) {
		return new WebSearch(theKey);
	}

	/**
	 * @inheritDoc
	 */
	public SearchResults<WebSearchResult> search(final String theSearchTerm) throws BOSSException {
		try {
			return doSearch(getSearchURL(theSearchTerm));
		}
		catch (MalformedURLException e) {
			throw new BOSSException(e);
		}
	}

	/**
	 * @inheritDoc
	 */
	public SearchResults<WebSearchResult> search(final String theSearchTerm, final Map<String, String> theParameters) throws BOSSException {
		try {
			return doSearch(getSearchURL(theSearchTerm, theParameters));
		}
		catch (MalformedURLException e) {
			throw new BOSSException(e);
		}
	}

	/**
	 * Runs the search at the given URL
	 * @param theURL the search URL
	 * @return the results of the search at the URL
	 * @throws BOSSException thrown if there is an error while searching
	 */
	private SearchResults<WebSearchResult> doSearch(URL theURL) throws BOSSException {
		return RESULTS_BUILDER.result(theURL);
	}

	public static void main(String[] args) throws Exception {
		WebSearch aSearch = new WebSearch("YoXKPEHV34ErgUqn31aqFYEPi2.Vk_xSYGn207ezkXg9HF3a7rtrSXw8XQmNDx7MOA--");

		SearchResults<WebSearchResult> aResults = aSearch.search("roger clemens");

		System.err.println(aResults.hasNextPage());
		for (WebSearchResult aRes : aResults) {
			System.err.println(aRes.getUrl());
		}
	}
}
