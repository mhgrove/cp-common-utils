package com.clarkparsia.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.File;
import java.io.Writer;

import java.net.URL;

import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;

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
        ArrayList<File> aList = new ArrayList<File>();

        File[] aFileList = theDirectory.listFiles();
        for (File aFile : aFileList) {
            if (aFile.isDirectory()) {
                aList.addAll(listFiles(aFile));
            }
            else {
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

    public static <T> Set<T> collectElements(Iterator<T> theIter) {
        Set<T> aSet = new LinkedHashSet<T>();

        while (theIter.hasNext())
            aSet.add(theIter.next());

        return aSet;
    }

    /**
     * Checks to see if any elements of one set are present in another.
     * First parameter is the list of elements to search for, the second
     * parameter is the list to search in.  Basically tests to see if the two sets intersect
     * @param theList Set the elements to look for
     * @param toSearch Set the search set
     * @return boolean true if any element in the first set is present in the second
     */
    public static <T> boolean containsAny(Set<T> theList, Set<T> toSearch) {

        if (toSearch.isEmpty()) {
            return false;
        }
        else
        {
            for (T aObj : theList) {
                if (toSearch.contains(aObj)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static <T> boolean containsAny(List<T> theList, List<T> theSearch) {
        return containsAny(new HashSet<T>(theList), new HashSet<T>(theSearch));
    }
}