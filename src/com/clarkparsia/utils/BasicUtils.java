package com.clarkparsia.utils;

import java.io.File;
import java.io.FileFilter;

import java.util.Random;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 * @author Michael Grove
 * @version 1.0
 */
public class BasicUtils
{
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
     * Recursively traverse the directory and all its sub directories and return a list of all the files contained within.
     * @param theDirectory the start directory
     * @return all files in the start directory and all its sub directories.
     */
    public static List<File> listFiles(File theDirectory) {
		return listFiles(theDirectory, new FileFilter() {
			public boolean accept(File theFile) {
				// an accept all file filter
				return true;
			}
		});
    }

	/**
	 * Recursively traverse the directory and all its sub directories and return a list of all the files contained within.
	 * Only files which match the file filter will be returned.
	 * @param theDirectory the start directory
	 * @param theFilter the file filter to use.
	 * @return the list of files in the directory (and its sub directories) which match the file filter.
	 */
	public static List<File> listFiles(File theDirectory, FileFilter theFilter) {
        ArrayList<File> aList = new ArrayList<File>();

        File[] aFileList = theDirectory.listFiles();
        for (File aFile : aFileList) {
            if (aFile.isDirectory()) {
                aList.addAll(listFiles(aFile));
            }
            else if (theFilter.accept(aFile)) {
                aList.add(aFile);
            }
        }

        return aList;
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
            return new SimpleDateFormat("MM-dd-yyyy").parse(theDate);
        }
        catch (ParseException pe) {
        }

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(theDate);
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
}