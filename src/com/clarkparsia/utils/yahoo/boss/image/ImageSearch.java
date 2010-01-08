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

package com.clarkparsia.utils.yahoo.boss.image;

import com.clarkparsia.utils.yahoo.boss.AbstractSearch;
import com.clarkparsia.utils.yahoo.boss.XMLResultsBuilder;
import com.clarkparsia.utils.yahoo.boss.SearchResults;
import com.clarkparsia.utils.yahoo.boss.BOSSException;

import java.util.Map;
import java.net.MalformedURLException;

/**
 * <p>Implementation of the Search interface which conducts image searches using Yahoo's BOSS API.</p>
 *
 * @author Michael Grove
 * @since 1.0
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
}
