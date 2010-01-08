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
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Map;

/**
 * <p>Abstract implementation of a search result which provides access to some universal properties of search
 * results like URL and title.</p>
 *
 * @author Michael Grove
 */
public abstract class AbstractSearchResult implements SearchResult, ResultConsts {

	/**
	 * The result data
	 */
	private Map<String, String> mResult;

	/**
	 * Create a new AbstractSearchResult
	 * @param theResult the result data
	 */
	public AbstractSearchResult(Map<String, String> theResult) {
		mResult = theResult;
	}

	/**
	 * Returns the result data
	 * @return the result data
	 */
	protected Map<String, String> getResult() {
		return Collections.unmodifiableMap(mResult);
	}

	/**
	 * @inheritDoc
	 */
	public URL getUrl() {
		try {
			return new URL(getResult().get(KEY_URL));
		}
		catch (MalformedURLException e) {
			throw new RuntimeException("Yahoo returned an invalid URL?!?! " + getResult().get(KEY_URL));
		}
	}

	/**
	 * @inheritDoc
	 */
	public String getTitle() {
		return getResult().get(KEY_TITLE);
	}

	/**
	 * @inheritDoc
	 */
	public String getAbstract() {
		return getResult().get(KEY_ABSTRACT);
	}
}
