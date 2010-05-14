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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.Reader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.StringTokenizer;
import java.nio.charset.Charset;

/**
 * <p>Collection of utility methods for basic IO tasks.</p>
 *
 * @author Michael Grove
 */
public class IOUtil {

	/**
	 * The default charset to use for cases when we're doing IO with a stream or a string and no charset is specified
	 * explicitly.  Often times the system default is US-ASCII, which ends up leading to encoding problems.  So we'll
	 * stick with this by default.
	 */
	private static final Charset DEFAULT_CHARSET = Encoder.UTF8;

    /**
     * The system dependent end-of-line defined here for convenience
     */
    public final static String ENDL = System.getProperty( "line.separator" );

    /**
     * Given a path to a file on the local disk, return the contents of that file as a String.
     *
     * @param theFileName     the File on the local disk to read
     * @return Contents of the file as a String
     * @throws java.io.IOException if there are problems opening or reading from the file
     * @throws java.io.FileNotFoundException if the file cannot be found
     */
    public static String getFileAsString(File theFileName) throws java.io.IOException {
        return readStringFromReader(new BufferedReader(new InputStreamReader(new FileInputStream(theFileName), DEFAULT_CHARSET.name())));
    }

    /**
     * Given a path to a file on the local disk, return the contents of that file as a String.
     *
     * @param theFileName     Fully qualified file name to a file on the local disk
     * @return Contents of the file as a String
     * @throws java.io.IOException if there are problems opening or reading from the file
     * @throws java.io.FileNotFoundException if the file cannot be found
     */
    public static String getFileAsString(String theFileName) throws java.io.IOException {
        return readStringFromReader(new BufferedReader(new InputStreamReader(new FileInputStream(theFileName), DEFAULT_CHARSET.name())));
    }

    /**
     * Read the contents of the stream into a string
     * @param theStream the stream to read from
     * @return the contents of the input stream
     * @throws IOException thrown if there is an error while reading from the stream
     */
    public static String readStringFromStream(InputStream theStream) throws IOException {
        return readStringFromReader(new BufferedReader(new InputStreamReader(theStream, DEFAULT_CHARSET.name())));
    }

    /**
     * Read the contents of the specified reader return them as a String
     * @param theReader the reader to read from
     * @return String the string contained in the reader
     * @throws java.io.IOException if there is an error reading
     */
    public static String readStringFromReader(Reader theReader) throws IOException {
        BufferedReader aReader = new BufferedReader(theReader);

        StringBuffer theFile = new StringBuffer();
        String line = aReader.readLine();

        while (line != null) {
            theFile.append(line);
            line = aReader.readLine();
            
            if (line != null) {
                theFile.append(ENDL);
            }
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
    public static String getURLAsString(URL theURL) throws IOException {
        if (theURL == null) {
            return null;
        }

        Reader reader = new BufferedReader(new InputStreamReader(theURL.openStream(), DEFAULT_CHARSET.name()));
        return readStringFromReader(reader);
    }

    /**
     * Write the specifed string to the given file name
     * @param theSave String the string to save
     * @param theFile the file to save the string to
     * @throws IOException if there is an error while writing
     */
    public static void writeStringToFile(String theSave, File theFile) throws IOException {
        writeStringToStream(theSave, new FileOutputStream(theFile));
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

    /**
     * Write the specified string to the output stream.  The output stream will be closed when writing is complete.
     * @param theString the string to write to the stream
     * @param theOutput the stream to write to
     * @throws IOException thrown if there is an error while writing
     */
    public static void writeStringToStream(String theString, OutputStream theOutput) throws IOException {
        writeStringToWriter(theString, new OutputStreamWriter(theOutput, DEFAULT_CHARSET.name()));
    }

    /**
     * Write the specified string out to the writer.  The writer is closed when finished.
     * @param theString the string to write
     * @param theWriter the writer to write to
     * @throws IOException thrown if there is an error while writing
     */
    public static void writeStringToWriter(String theString, Writer theWriter) throws IOException {
        BufferedWriter aWriter = new BufferedWriter(theWriter);

        StringTokenizer aTokenizer = new StringTokenizer(theString,"\n",true);
        String aLine;

        while (aTokenizer.hasMoreTokens()) {
            aLine = aTokenizer.nextToken();
            if (aLine.equals("\n")) {
                aWriter.newLine();
			}
            else {
				aWriter.write(aLine);
			}
        }

        aWriter.flush();
        aWriter.close();
    }

	/**
	 * Transfer the contents of the input stream to the output
	 * @param theInputStream the stream to read from
	 * @param theOutputStream the stream to write to
	 * @return the total number of bytes transferred between the streams
	 * @throws IOException thrown if there is an error while either reading or writing, or if you cross the streams
	 */
    public static long transfer(InputStream theInputStream, OutputStream theOutputStream) throws IOException {
		if (theOutputStream == null) {
			return 0;
		}

		long aTotalBytes = 0;
		int aBytesRead = 0;

        byte[] aBuffer = new byte[8192];

		while ((aBytesRead = theInputStream.read(aBuffer)) != -1) {
			theOutputStream.write(aBuffer, 0, aBytesRead);
			aTotalBytes += aBytesRead;
		}

		theOutputStream.flush();

		return aTotalBytes;
    }

	/**
	 * Transfer the contents of the Reader to the Writer
	 * @param theReader the reader to read from
	 * @param theWriter the writer to send the reader's data to
	 * @return the number of total characters transferred between the two
	 * @throws IOException thrown if there is an error while either reading or writing
	 */
    public static long transfer(Reader theReader, Writer theWriter) throws IOException {
		char[] aCharBuffer = new char[2048];
        int aBytesRead = 0;
		long aTotalBytes = 0;

		while ((aBytesRead = theReader.read(aCharBuffer)) != -1) {
			theWriter.write(aCharBuffer, 0, aBytesRead);
			aTotalBytes += aBytesRead;
		}

        theWriter.flush();

		return aTotalBytes;
    }

	/**
	 * Reads all the data from the input stream into a byte array
	 * @param theIn the stream to read from
	 * @return the data from the stream as an array of bytes
	 * @throws IOException thrown if there is an error while reading from the stream
	 */
	public static byte[] readBytesFromStream(InputStream theIn) throws IOException {
        if (theIn == null) {
			return new byte[0];
		}

        ByteArrayOutputStream aOutputStream = new ByteArrayOutputStream(4096);
        byte[] aBuffer = new byte[4096];
        int aBytesRead = -1;

        while ((aBytesRead = theIn.read(aBuffer)) != -1) {
            aOutputStream.write(aBuffer, 0, aBytesRead);
        }

        theIn.close();

        return aOutputStream.toByteArray();
    }
}
