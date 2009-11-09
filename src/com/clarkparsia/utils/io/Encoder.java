package com.clarkparsia.utils.io;

import com.sun.corba.se.impl.interceptors.CDREncapsCodec;

import java.nio.charset.Charset;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

/**
 * Title: Encoder<br/>
 * Description: Collection of utility methods for encoding/decoding strings.<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Nov 4, 2009 1:54:56 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class Encoder {
	private static BASE64Encoder mBase64Encoder;

	// these are the charsets that should be available in all jvms.  There might be more than just these...
	public static final Charset UTF8 = Charset.availableCharsets().get("UTF-8");
	public static final Charset UTF16 = Charset.availableCharsets().get("UTF-16");
	public static final Charset US_ASCII = Charset.availableCharsets().get("US-ASCII");

	/**
	 * URL encode the string using the UTF8 charset
	 * @param theString the string to encode
	 * @return the url encoded string
	 */
	public static String urlEncode(String theString) {
		return urlEncode(theString, UTF8);
	}

	/**
	 * URL encode the given string using the given Charset
	 * @param theString the string to encode
	 * @param theCharset the charset to encode the string using
	 * @return the string encoded with the given charset
	 */
	public static String urlEncode(String theString, Charset theCharset) {
		try {
			return URLEncoder.encode(theString, theCharset.displayName());
		}
		catch (UnsupportedEncodingException e) {
			// this can be safely ignored, you would not have a charset object for a support charset
			return null;
		}
	}

	/**
	 * Base64 encodes the given byte array.  This utility is provided to abstract over the Sun implementation which
	 * is deprecated and marked for deletion.  The implementation of this method will always work; so if the existing
	 * Sun implementation goes away, this will be switched to an appropriate implementation without requiring any
	 * changes to dependant code.
	 * @param theArrayToEncode the bytes to encode
	 * @return the bytes base64 encoded
	 */
	public static String base64Encode(byte[] theArrayToEncode) {
		return getBase64Encoder().encode(theArrayToEncode);
	}

	/**
	 * Return a Sun base64 encoder
	 * @return an encoder
	 */
	private static BASE64Encoder getBase64Encoder() {
		if (mBase64Encoder == null) {
			mBase64Encoder = new BASE64Encoder();
		}

		return mBase64Encoder;
	}
}
