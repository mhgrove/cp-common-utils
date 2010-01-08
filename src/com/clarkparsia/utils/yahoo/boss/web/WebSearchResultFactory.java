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

import com.clarkparsia.utils.yahoo.boss.ResultFactory;
import com.clarkparsia.utils.yahoo.boss.SearchResult;

import java.util.Map;

/**
 * <p>Implementation of the ResultSetFactory interface which creates WebSearchResults.</p>
 *
 * @author Michael Grove
 * @since 1.0
 */

class WebSearchResultFactory implements ResultFactory<WebSearchResult> {

	/**
	 * @inheritDoc
	 */
	public WebSearchResult newResult(final Map<String, String> theResultData) {
		return new WebSearchResult(theResultData);
	}
}
