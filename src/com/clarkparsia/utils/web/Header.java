package com.clarkparsia.utils.web;

import com.clarkparsia.utils.Tuple;
import com.clarkparsia.utils.BasicUtils;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

/**
 * Title: Header<br/>
 * Description: Represents an HTTP header, either from a request or a response.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Dec 1, 2009 7:55:14 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 * @see Request
 * @see Response
 */
public class Header {

	/**
	 * The name of the header. {@link HttpHeaders} is an enumeration of common header names.
	 */
	private String mName;

	/**
	 * The list of values for the header
	 */
	private Map<String, String> mValues = new HashMap<String, String>();

	/**
	 * Create a new HTTP header
	 * @param theName the name of the header attribute
	 * @param theValue the singleton value of the header
	 */
	public Header(final String theName, String theValue) {
		mName = theName;
		addValue(theValue);
	}

	/**
	 * Create a new HTTP header
	 * @param theName the name of the header attribute
	 * @param theValues the values of the HTTP header
	 */
	public Header(final String theName, final List<String> theValues) {
		mName = theName;

		for (String aValue : theValues) {
			addValue(aValue);
		}
	}

	/**
	 * Create a new HTTP header
	 * @param theName the name of the header attribute
	 * @param theValues the values of the HTTP header
	 */
	public Header(final String theName, final Map<String, String> theValues) {
		mName = theName;
		mValues = theValues;
	}

	/**
	 * Add a key-value pair to this header
	 * @param theName the name of the header element
	 * @param theValue the value of the element
	 * @return this Header object
	 */
	public Header addValue(String theName, String theValue) {
		addValues(Collections.singletonMap(theName, theValue));

		return this;
	}

	/**
	 * Add a value to the header
	 * @param theValue the value to add
	 */
	void addValue(String theValue) {
		if (theValue == null) {
			return;
		}

		if (theValue.indexOf(";") != -1) {
			for (String aKeyValuePair : BasicUtils.split(theValue, ";")) {
				Tuple aTuple = split(aKeyValuePair);
				mValues.put(aTuple.<String>get(0), (aTuple.length() < 2 ? null : aTuple.<String>get(1)));
			}
		}
		else if (theValue.indexOf("=") != -1) {
			Tuple aTuple = split(theValue);
			mValues.put(aTuple.<String>get(0), (aTuple.length() < 2 ? null : aTuple.<String>get(1)));
		}
		else {
			mValues.put(null, theValue);
		}
	}

	/**
	 * Add all the values to the header
	 * @param theValues the values to add
	 */
	void addValues(Map<String, String> theValues) {
		mValues.putAll(theValues);
	}

	/**
	 * The name of the HTTP header.  Common HTTP header names can be found in {@link HttpHeaders}
	 * @return the header name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Returns the values of the HTTP header
	 * @return the header values
	 */
	public Map<String, String> getValues() {
		return mValues;
	}

	/**
	 * Return the value of the header element
	 * @param theKey the name of the header element, or null to get the value of the header
	 * @return the value, or null if one is not specified
	 */
	public String getValue(String theKey) {
		return getValues().get(theKey);
	}

	/**
	 * Splits the key-value string on the = sign.
	 * @param theKeyValue the key/value string 
	 * @return a 2-tuple of strings with the key and value.
	 */
	private Tuple split(String theKeyValue) {
		return new Tuple(BasicUtils.split(theKeyValue, "="));
	}

	/**
	 * Return the value(s) of the header as a semi-colon separated string.  For example, if your values are "foo", "bar"
	 * and "baz" this will return the string "foo; bar; baz"
	 * @return the string encoded reprsentation of the header values suitable for insertion into a HTTP request
	 */
	public String getHeaderValue() {
		StringBuffer aBuffer = new StringBuffer();

		boolean aFirst = true;
		for (String aKey : getValues().keySet()) {

			if (!aFirst) {
				aBuffer.append("; ");
			}

			aFirst = false;

			if (aKey != null) {
				aBuffer.append(aKey).append("=");
			}
			
			aBuffer.append(getValues().get(aKey));
		}

		return aBuffer.toString();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		return getName() + " [" + getHeaderValue()  + "]";
	}
}
