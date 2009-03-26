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
     * The system dependent end-of-line defined here for convenience 
     */
    public static String ENDL = System.getProperty( "line.separator" );

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
     public static String replace(String theHost, String theOldString, String theNewString)
     {
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
     * Given a path to a file on the local disk, return the contents of that file as a String.<br><br>
     *
     * @param fn     Fully qualified file name to a file on the local disk
     * @return Contents of the file as a String
     * @throws java.io.IOException if there are problems opening or reading from the file
     * @throws java.io.FileNotFoundException if the file cannot be found
     */
    public static String getFileAsString(String fn) throws java.io.IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fn)));
        return getStringFromBufferedReader(reader);
    }

    /**
     * Read the contents of the specified reader return them as a String
     * @param aReader BufferedReader the reader to read from
     * @return String the string contained in the reader
     * @throws IOException if there is an error reading
     */
    public static String getStringFromBufferedReader(BufferedReader aReader) throws IOException
    {
        StringBuffer theFile = new StringBuffer();
        String line = aReader.readLine();

        while (line != null) {
            theFile.append(line).append(ENDL);
            line = aReader.readLine();
        }

        aReader.close();

        return theFile.toString();
    }

    /**
     * Returns the specified URL as a string.  This function will read the contents at the specified URl and return the results.
     * @param theURL URL the URL to read from
     * @return String the String found at the URL
     * @throws IOException if there is an error reading
     */
    public static String getURLAsString(URL theURL) throws IOException
    {
        if (theURL == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(theURL.openStream()));
        return getStringFromBufferedReader(reader);
    }

    public static String getLocalName(String theString) {
        return theString.substring(theString.lastIndexOf("#")+1);
    }

    /**
     * Write the specifed string to the given file name
     * @param theSave String the string to save
     * @param theFileName String the file to save the string to
     * @throws IOException if there is an error while writing
     */
    public static void writeStringToFile(String theSave, String theFileName) throws IOException {
        writeStringToStream(theSave, new FileOutputStream(theFileName));
    }

    public static void writeStringToStream(String theString, OutputStream theOutput) throws IOException {
        writeStringToWriter(theString, new OutputStreamWriter(theOutput));
    }

    public static void writeStringToWriter(String theString, Writer theWriter) throws IOException {
        BufferedWriter aWriter = new BufferedWriter(theWriter);

        StringTokenizer st = new StringTokenizer(theString,"\n",true);
        String s;
        while (st.hasMoreTokens()) {
            s = st.nextToken();
            if (s.equals("\n"))
                aWriter.newLine();
            else aWriter.write(s);
        }
        
        aWriter.flush();
        aWriter.close();
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