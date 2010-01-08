/*
 * Copyright (c) 2005-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.utils.yahoo.boss.web;

import com.clarkparsia.utils.yahoo.boss.XMLResultsBuilder;
import com.clarkparsia.utils.yahoo.boss.SearchResults;
import com.clarkparsia.utils.yahoo.boss.BOSSException;
import com.clarkparsia.utils.yahoo.boss.AbstractSearch;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * <p>Conducts a web search via Yahoo's BOSS API</p>
 *
 * @author Michael Grove
 * @since 1.0
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

}
