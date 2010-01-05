// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils;

import com.clarkparsia.utils.collections.CollectionUtil;

import com.clarkparsia.utils.io.Encoder;

import java.util.Random;

import java.util.Date;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Collection of simple utility methods
 */
public class BasicUtils {
    public static final int ONE_SECOND = 1000;
    public static final int ONE_MINUTE = 60 * ONE_SECOND;
    public static final int ONE_HOUR = 60 * ONE_MINUTE;
    public static final int ONE_DAY = 24 * ONE_HOUR;
    public static final int ONE_WEEK = 7 * ONE_DAY;

    private static final Random RANDOM = new Random();

	/**
	 * Return whether or not the string is a valid URL
	 * @param theURL the string to test
	 * @return true if the string is a valid URL, false otherwise
	 */
	public static boolean isURL(String theURL) {
		try {
			new URL(theURL);
			return true;
		}
		catch (MalformedURLException e) {
			return false;
		}
	}

	/**
	 * Return whether or not the string is a valid URI
	 * @param theURI the string to test
	 * @return true if it is a valid URI, false otherwise
	 */
	public static boolean isURI(String theURI) {
		try {
			new URI(theURI);
			return true;
		}
		catch (URISyntaxException e) {
			return false;
		}
	}

	/**
	 * Returns a string of the character repeated the specified amount of times.  For example, repeat("*", 5) returns
	 * the string "*****"
	 * @param c the character to repoeat
	 * @param theMult the number of times to repeat it
	 * @return the string with the repeated character
	 */
	public static String repeat(char c, int theMult) {
		StringBuffer aBuffer = new StringBuffer();

		for (int i = 0; i < theMult; i++) {
			aBuffer.append(c);
		}

		return aBuffer.toString();
	}

	/**
	 * <p>Provides a List of integers starting at the given index and going to the given end index (inclusive).  Useful
	 * if you want to do something X amount of times:</p>
	 * <code>
	 *   for (int aNum : range(1, 1000)) {
	 *       // do work
	 *   }
	 * </code>
	 * @param theStart the start index
	 * @param theEnd the end index
	 * @return the range of numbers from start to end (inclusive)
	 */
	public static Iterable<Integer> range(final int theStart, final int theEnd) {
		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>(){
					int index = theStart;
					public boolean hasNext() {
						return index <= theEnd;
					}

					public Integer next() {
						return index++;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	/**
	 * Merge an array of byte arrays into a single byte array
	 * @param aArrays the arrays to merge
	 * @return the merged array
	 */
	public static byte[] concat(byte[]... aArrays) {
		byte[] aResult = new byte[length(aArrays)];

		int aCurrentResultIndex = 0;
		for (byte[] aArray : aArrays) {
			System.arraycopy(aArray, 0, aResult, aCurrentResultIndex, aArray.length);
			aCurrentResultIndex += aArray.length;
		}

		return aResult;
	}

	/**
	 * Compute combined size of all the given byte arrays
	 * @param aArrays the arrays
	 * @return the size of the arrays
	 */
	private static int length(byte[]...aArrays) {
		int aResult = 0;

		for (byte[] aArray : aArrays) {
			aResult += aArray.length;
		}

		return aResult;
	}

	public static <T> String join(String theJoinWith, Collection<T> theCollection) {
		return join(theJoinWith, theCollection, new DefaultStringRenderer<T>());
	}

	public static <T> String join(final String theJoinWith, final Collection<T> theCollection, final StringRenderer<T> theRenderer) {
		final StringBuffer aBuffer = new StringBuffer();

		CollectionUtil.each(theCollection, new AbstractDataCommand<T>() {
			public void execute() {
				if (aBuffer.length() > 0) {
					aBuffer.append(theJoinWith).append(" ");
				}

				aBuffer.append(theRenderer.render(getData()));
			}
		});

		return aBuffer.toString();
	}

	/**
	 * Wrapper function around String.split() which performs trim() on every token returned and does not return any
	 * empty tokens.
	 * @param theStringToSplit the string to split
	 * @param theSplitToken the regex for the split
	 * @return the list of whitespace trimmed, non-empty tokens resulting from the split operation
	 */
	public static List<String> split(String theStringToSplit, String theSplitToken) {
		List<String> aList = new ArrayList<String>();

		for (String aToken : theStringToSplit.split(theSplitToken)) {
			if (aToken.trim().length() > 0) {
				aList.add(aToken.trim());
			}
		}

		return aList;
	}

	/**
	 * Returns true if both objects are .equals or both are null.
	 * @param theObj the first object to compare
	 * @param theOtherObj the second object to compare
	 * @param <T> the type of the objects to compare
	 * @return Returns true if both objects are .equals or both are null.
	 */
	public static <T> boolean equalsOrNull(T theObj, T theOtherObj) {
		return (theObj == null && theOtherObj == null) ||
			   (theObj != null && theOtherObj != null && theObj.equals(theOtherObj));
	}

    /**
     * Return a string of random characters of the specified length.
     * @param theLength the size of the random string to create
     * @return a string of random letters
     */
    public static String getRandomString(int theLength) {
        StringBuffer aBuffer = new StringBuffer();

        for (int i = 0; i < theLength; i++) {
            int index = 97 + RANDOM.nextInt(26);
            char c = (char)index;
            aBuffer.append(c);
        }
        return aBuffer.toString();
    }

    /**
     * Given a string, replace all occurances of one string with another.<br><br>
     *
     * @param theHost     parent string
     * @param theOldString  String to remove
     * @param theNewString  String to insert in the place of the string to remove
     * @return copy of the initial string with all occurances of the one string replaced by the other
     */
     public static String replace(String theHost, String theOldString, String theNewString) {
         int aIndex = 0;

         // while the string to replace is present, remove it and replace it with the new string
         while (theHost.indexOf(theOldString, aIndex) != -1) {
             // find out where the string to remove is
             aIndex = theHost.indexOf(theOldString, aIndex);

             // replace the current instance of the string to remove
             theHost = theHost.substring(0, aIndex) + theNewString + theHost.substring(aIndex + theOldString.length(), theHost.length());

             // move our search index past the current position so we dont recheck the same spot
             aIndex += theNewString.length();
         }
         return theHost;
     }

	/**
	 * Convert a string to title case.  So if you have "this is a sentence" this function will return "This Is A Sentence"
	 * @param theStr the string to convert
	 * @return the converted string
	 */
	public static String toTitleCase(String theStr) {
		char[] charArray = theStr.toLowerCase().toCharArray();

		Pattern pattern = Pattern.compile("\\b([A-Za-z])");
		Matcher matcher = pattern.matcher(theStr);

		while(matcher.find()){
			int index = matcher.end(1) - 1;
			charArray[index] = Character.toTitleCase(charArray[index]);
		}

		return String.valueOf(charArray);
	}

   /**
     * Formats the given string as a java.util.Date object.
     * @param theDate the date string
     * @return the string as a java.util.Date object
     */
    public static Date asDate(String theDate) {
	   try {
		   return new SimpleDateFormat("MM/dd/yy hh:mm").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("MM/dd/yyyy hh:mm").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("MM/dd/yyyy").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("MM/dd/yy").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("yyyy-MM-dd").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("MM-dd-yyyy").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("yyyy/MM/dd").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("dd-MMM-yy").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new SimpleDateFormat("MM dd yyyy HH:mm:ss").parse(theDate);
	   }
	   catch (ParseException pe) {
	   }

	   try {
		   return new Date(Long.parseLong(theDate));
	   }
	   catch (Exception pe) {
	   }

	   throw new IllegalArgumentException("Invalid date format supplied: " + theDate);
   }

	public static String date(Date theDate) {
		return new SimpleDateFormat("yyyy-MM-dd").format(theDate);
	}

    public static String datetime(Date theDate) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(theDate);
    }

	/**
     * Return the hex string value of the byte array
	 * @param theArray the input array
	 * @return the hex value of the bytes
	 */
	public static String hex(byte[] theArray) {
		StringBuffer sb = new StringBuffer();
		for (byte aByte : theArray) {
			sb.append(Integer.toHexString((aByte & 0xFF) | 0x100).toUpperCase().substring(1, 3));
		}
		return sb.toString();
	}

	/**
     * Returns the md5 representation of a string
     * @param theString the string to md5
     * @return the byte representation of the md5 sum of the string
     */
    public static byte[] md5(String theString) {
		try {
			return md5(theString.getBytes(Encoder.UTF8.name()));
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
     * Returns the md5 representation of a string
     * @param theBytes the bytes to md5
     * @return the byte representation of the md5 sum of the string
     */
    public static byte[] md5(byte[] theBytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(theBytes);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

	public static void main(String[] args) {
		System.err.println(asDate("2007-02-16"));
	}
}