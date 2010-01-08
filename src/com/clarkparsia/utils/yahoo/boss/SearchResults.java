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

import java.util.Map;

/**
 * <p>The entire results of a search.  It represents an iterable set of page search results.</p>
 *
 * @author Michael Grove
 */
public interface SearchResults<T extends SearchResult> extends Iterable<T> {

	/**
	 * Return the attributes of the search result.  This contains things such as the total number of results of the search,
	 * and which page you're on.
	 * @return the result attributes.
	 */
	public Map<String, String> getResultAttributes();

	/**
	 * Return whether or not there is another page of search results
	 * @return true if there is another page, or false if not
	 */
	public boolean hasNextPage();

	/**
	 * Move the cursor to the next page of results
	 * @return return a cursor to the next page of results
	 * @throws BOSSException thrown if there is an error while getting the next page of results.
	 */
	public SearchResults<T> nextPage() throws BOSSException;
}
