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

import com.clarkparsia.utils.yahoo.boss.AbstractSearchResult;

import java.util.Map;

/**
 * <p>Implementation of the SearchResult interface for image search specific results</p>
 *
 * @author Michael Grove
 * @since 1.0
 */

public class ImageSearchResult extends AbstractSearchResult {

	/**
	 * Create a new ImageSeachResult
	 * @param theMap the search result data
	 */
	public ImageSearchResult(Map<String, String> theMap) {
		super(theMap);
	}
}
