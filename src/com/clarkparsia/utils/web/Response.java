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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import java.util.Map;
import java.util.Collection;
import java.util.HashMap;

/**
 * <p>A response to an HTTP invocation.  Responses must be closed when they are no longer used to close the
 * connection to the server and release the content stream.</p>
 *
 * @author Michael Grove
 * @since 1.0
 * @version 1.1
 */
public class Response implements Closeable {

	private final InputStream mContent;
	private final Map<String, Header> mHeaders;
	private final String mMessage;
    private final HttpURLConnection mConnection;
    private final int mResponseCode;

    public Response(final HttpURLConnection theConn, final Collection<Header> theHeaders) throws IOException {

        mHeaders = new HashMap<String, Header>();

        for (Header aHeader : theHeaders) {
            mHeaders.put(aHeader.getName(), aHeader);
        }

        mConnection = theConn;
        mContent = theConn.getInputStream();
        mMessage = theConn.getResponseMessage();
        mResponseCode = theConn.getResponseCode();
    }

    /**
     * Return the response message from the server
     * @return the message
     */
	public String getMessage() {
		return mMessage;
	}

    /**
     * Return all headers returned by the server
     * @return the headers
     */
	public Collection<Header> getHeaders() {
		return mHeaders.values();
	}

    /**
     * Get the header with the specified name
     * @param theName the header name
     * @return the value of the header, or null if the header is not present
     */
	public Header getHeader(String theName) {
		return mHeaders.get(theName);
	}

    /**
     * Return the response code
     * @return the response code
     */
	public int getResponseCode() {
		return mResponseCode;
	}

    /**
     * Return the response content from the server
     * @return the response content
     */
	public InputStream getContent() {
		return mContent;
	}

    /**
     * Return whether or not this has an error result
     * @return true if there is an error result, false otherwise
     */
	public boolean hasErrorCode() {
		// TODO: right?
		return getResponseCode() >= 400;
	}

    /**
     * Close this response
     * @throws IOException if there is an error while closing
     */
    public void close() throws IOException {
        mContent.close();
        mConnection.disconnect();
    }
}
