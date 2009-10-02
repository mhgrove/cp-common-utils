// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils;

import java.util.Random;

import java.util.Date;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

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
	 * Returns true if both objects are .equals or both are null.
	 * @param theObj
	 * @param theOtherObj
	 * @param <T>
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
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(theString.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}