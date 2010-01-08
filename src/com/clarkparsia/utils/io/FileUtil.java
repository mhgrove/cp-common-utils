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

package com.clarkparsia.utils.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileFilter;
import java.io.FileWriter;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Collection of utility functions for file-centric operations.</p>
 *
 * @author Michael Grove
 * @since 1.0
 */
public class FileUtil {

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
	 * Appends the string to the file
	 * @param theFile the file to append to
	 * @param theString the string to append
	 * @throws IOException thrown if there is an error while appending the data
	 */
	public static void append(File theFile, String theString) throws IOException {
		FileWriter aWriter = new FileWriter(theFile, true);

		aWriter.append(theString);

		aWriter.flush();

		aWriter.close();
	}

	/**
	 * Zip the given directory.
	 * @param theDir the directory to zip
	 * @param theOutputFile the zip file to write to
	 * @throws IOException thrown if there is an error while zipping the directory or while saving the results.
	 */
    public static void zipDirectory(File theDir, File theOutputFile) throws IOException {
        ZipOutputStream aZipOut = new ZipOutputStream(new FileOutputStream(theOutputFile));

        Collection<File> aFileList = listFiles(theDir);

        String aPathToRemove = theDir.getAbsolutePath().substring(0, theDir.getAbsolutePath().lastIndexOf(File.separator));

        for (File aFile : aFileList) {
            FileInputStream aFileIn = new FileInputStream(aFile);

            ZipEntry aZipEntry = new ZipEntry(aFile.getAbsolutePath().substring(aFile.getAbsolutePath().indexOf(aPathToRemove) + aPathToRemove.length() + 1));

            aZipOut.putNextEntry(aZipEntry);

            IOUtil.transfer(aFileIn, aZipOut);

            aFileIn.close();

            aZipOut.closeEntry();
        }

        aZipOut.close();
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
                aList.addAll(listFiles(aFile, theFilter));
            }
            else if (theFilter.accept(aFile)) {
                aList.add(aFile);
            }
        }

        return aList;
	}

	/**
	 * Copy a file
	 * @param theSource the file to copy
	 * @param theDest the location to copy the file to
	 * @throws IOException thrown if there is an error while performing the copy
	 */
	public static void copy(File theSource, File theDest) throws IOException {
		InputStream aIn = null;
		OutputStream aOut = null;

		try {
			aIn = new FileInputStream(theSource);
			aOut = new FileOutputStream(theDest);

			IOUtil.transfer(aIn, aOut);
		}
		finally {
			if (aIn != null) aIn.close();
			if (aOut != null) aOut.close();
		}
	}

	/**
	 * Move a file
	 * @param theSource the file to move
	 * @param theDest the location to move the file to
	 * @return returns true of the move was successful, i.e. the original file was deleted after the copy
	 * @throws IOException thrown if there is an error while copying or removing the file.
	 */
	public static boolean move(File theSource, File theDest) throws IOException {
		copy(theSource, theDest);

		return theSource.delete();
	}
}
