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

import com.clarkparsia.utils.io.Encoder;
import com.clarkparsia.utils.io.IOUtil;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @since 1.0
 */
public class Request {
	private URL mURL;
	private Method mMethod;
	private ParameterList mParameters = new ParameterList();
	private InputStream mBody;
	private Map<String, Header> mHeaders = new HashMap<String, Header>();

	private int mTimeout = -1;
	private boolean mFollowRedirects;

	public Request(String theURL) throws MalformedURLException {
		this(Method.GET, new URL(theURL));
	}

	public Request(URL theURL) {
		this(Method.GET, theURL);
	}

	public Request(Method theMethod, URL theURL) {
		mMethod = theMethod;
		mURL = theURL;
	}

	public static Request formPost(URL theURL, ParameterList theParams) {
		Request aRequest = new Request(Method.POST, theURL);
		aRequest.addHeader(new Header(HttpHeaders.ContentType.getName(), MimeTypes.FormUrlEncoded.getMimeType()));
		aRequest.setBody(theParams.toString());

		return aRequest;
	}

	public int getTimeout() {
		return mTimeout;
	}

	public Request setTimeout(final int theTimeout) {
		mTimeout = theTimeout;

		return this;
	}

	public boolean isFollowRedirects() {
		return mFollowRedirects;
	}

	public Request setFollowRedirects(final boolean theFollowRedirects) {
		mFollowRedirects = theFollowRedirects;

		return this;
	}

	public Request addParameter(Parameter theParameter) {
		mParameters.add(theParameter);

		return this;
	}

	public Request setParameters(final ParameterList theParameters) {
		mParameters = theParameters;

		return this;
	}

	public Request addHeader(Header theHeader) {
		if (mHeaders.containsKey(theHeader.getName())) {
			theHeader.addValues(mHeaders.get(theHeader.getName()).getValues());
		}

		mHeaders.put(theHeader.getName(), theHeader);

		return this;
	}

	public Request addHeader(String theName, String... theValue) {
		addHeader(new Header(theName, Arrays.asList(theValue)));

		return this;
	}

	public Request setBody(String theString) {
		try {
			mBody = new ByteArrayInputStream(theString.getBytes(Encoder.UTF8.name()));
		}
		catch (UnsupportedEncodingException e) {
			// can safely be ignored, we know java supports UTF8
		}

		return this;
	}

	public Request setBody(final InputStream theBody) {
		mBody = theBody;

		return this;
	}

	public URL getURL() {
		return mURL;
	}

	public Method getMethod() {
		return mMethod;
	}

	public ParameterList getParameters() {
		return mParameters;
	}

	public InputStream getBody() {
		return mBody;
	}

	public Collection<Header> getHeaders() {
		return Collections.unmodifiableCollection(mHeaders.values());
	}

	private URL getURLWithParams() throws IOException {
		if (getMethod().equals(Method.GET) && !getParameters().isEmpty()) {
			try {
				return new URL(getURL().toString() + "?" + getParameters().getURLEncoded());
			}
			catch (MalformedURLException e) {
				throw new IOException(e.getMessage());
			}
		}
		else {
			return getURL();
		}
	}

	public Header getHeader(String theName) {
		return mHeaders.get(theName);
	}

	public Response execute() throws IOException {
		// TODO: use-caches?, if-modified-since, HTTPS security twiddling, HTTP Authentication, chunking, user interactions?

		URLConnection aTempConn = getURLWithParams().openConnection();

		if (!(aTempConn instanceof HttpURLConnection)) {
			throw new IllegalArgumentException("Only HTTP or HTTPS are supported");
		}

		HttpURLConnection aConn = (HttpURLConnection) aTempConn;

		aConn.setDoInput(true);

		if (getTimeout() != -1) {
			aConn.setConnectTimeout(getTimeout());
		}
		
		aConn.setInstanceFollowRedirects(isFollowRedirects());
		aConn.setRequestMethod(getMethod().name());

		for (Header aHeader : getHeaders()) {
			aConn.setRequestProperty(aHeader.getName(), aHeader.getHeaderValue());
		}

		InputStream aInput = getBody();
		if (aInput != null) {
			aConn.setDoOutput(true);
			OutputStream aOut = aConn.getOutputStream();

			IOUtil.transfer(aInput, aOut);

			if (aOut != null) {
				aOut.flush();
				aOut.close();
			}

			aInput.close();
		}
		
		aConn.connect();

		Response aResponse = new Response();

		aResponse.setResponseCode(aConn.getResponseCode());

		Collection<Header> aResponseHeaders = new HashSet<Header>();

		Map<String, List<String>> aHeaderMap = aConn.getHeaderFields();

		for (String aName : aHeaderMap.keySet()) {
			aResponseHeaders.add(new Header(aName, aHeaderMap.get(aName)));
		}

		aResponse.setHeaders(aResponseHeaders);
		
		aResponse.setMessage(aConn.getResponseMessage());

		InputStream aResponseStream = null;

		try {
			aResponseStream = aConn.getInputStream();

			// if this is GZIP encoded, then wrap the input stream
			String contentEncoding = aConn.getContentEncoding();
			if ("gzip".equals(contentEncoding)) {
				aResponseStream = new GZIPInputStream(aResponseStream);
			}

			// ideally we'd like to return the response body as an inputstream and let the caller read from it at demand
			// rather than pulling the entire thing into memory, but doing that (i think) keeps open the connection
			// which is undesirable
			aResponse.setContent(IOUtil.readStringFromStream(aResponseStream));
		}
		catch (IOException e) {
			aResponseStream = aConn.getErrorStream();
			try {
				aResponse.setContent(IOUtil.readStringFromStream(aResponseStream));
			}
			catch (IOException e1) {
				throw e1;
			}
		}
		finally {
			if (aResponseStream != null) {
				aResponseStream.close();
			}
		}

		aConn.disconnect();

		return aResponse;
	}
}
