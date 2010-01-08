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

package com.clarkparsia.utils.web;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @since 1.0
 */
public class Response {
	private int mResponseCode;
	private String mContent;
	private Map<String, Header> mHeaders = new HashMap<String, Header>();
	private String mMessage;

	void setResponseCode(final int theResponseCode) {
		mResponseCode = theResponseCode;
	}

	void setContent(final String theContent) {
		mContent = theContent;
	}

	public void setHeaders(final Collection<Header> theHeaders) {
		mHeaders = new HashMap<String, Header>();

		for (Header aHeader : theHeaders) {
			mHeaders.put(aHeader.getName(), aHeader);
		}
	}

	void setMessage(final String theMessage) {
		mMessage = theMessage;
	}

	public String getMessage() {
		return mMessage;
	}

	public Collection<Header> getHeaders() {
		return mHeaders.values();
	}

	public Header getHeader(String theName) {
		return mHeaders.get(theName);
	}

	public int getResponseCode() {
		return mResponseCode;
	}

	public String getContent() {
		return mContent;
	}

	public boolean hasErrorCode() {
		// TODO: right?
		return getResponseCode() >= 400;
	}
}
