package com.clarkparsia.utils.io;

import java.nio.charset.Charset;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Title: <br/>
 * Description: <br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Nov 4, 2009 1:54:56 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class Encoder {
	public static final Charset UTF8 = Charset.availableCharsets().get("UTF-8");
	public static final Charset UTF16 = Charset.availableCharsets().get("UTF-16");
	public static final Charset US_ASCII = Charset.availableCharsets().get("US-ASCII");
	
	public static String urlEncode(String theString, Charset theCharset) {
		try {
			return URLEncoder.encode(theString, theCharset.displayName());
		}
		catch (UnsupportedEncodingException e) {
			// this can be safely ignored, you would not have a charset object for a support charset
			return null;
		}
	}
}
