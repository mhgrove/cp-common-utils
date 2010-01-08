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

package com.clarkparsia.utils.yahoo.boss;

import java.net.URL;

/**
 * <p>Interface for an instance which takes a URL to a search and returns the search results listed
 * there.</p>
 *
 * @author Michael Grove
 * @since 1.0
 */
public interface ResultsBuilder<T extends SearchResult> {

	/**
	 * Returns the search results contained at the given URL
	 * @param theURL the URL to get the search results from
	 * @return the search results
	 * @throws BOSSException thrown if there is an error while getting or parsing the search results
	 */
	public SearchResults<T> result(URL theURL) throws BOSSException;
}
