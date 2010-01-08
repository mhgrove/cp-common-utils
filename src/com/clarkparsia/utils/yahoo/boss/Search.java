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
 * <p>Interface for accessing and performing a search on the web.</p>
 *
 * @author Michael Grove
 * @since 1.0
 */
public interface Search<T extends SearchResult> {

	/**
	 * Perform a search
	 * @param theSearchTerm the term(s) to search for
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<T> search(String theSearchTerm) throws BOSSException;

	/**
	 * Perform a search
	 * @param theSearchTerm the term to search for
	 * @param theParameters addtional parameters for the search.  This vary based on the type of search.
	 * @return the results of the search
	 * @throws BOSSException thrown if there is an error while searching
	 */
	public SearchResults<T> search(String theSearchTerm, Map<String, String> theParameters) throws BOSSException;
}
