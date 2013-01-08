/*
 * Copyright (c) 2005-2013 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.clarkparsia.common.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;

/**
 * <p>Utility class to hold functionality not already contained in {@link com.google.common.io.ByteStreams}</p>
 *
 * @author  Michael Grove
 * @since   2.3.1
 * @version 2.4
 */
public final class ByteStreams2 {

    /**
     * No instances
     */
    private ByteStreams2() {
        throw new AssertionError();
    }

    /**
     * Read an int from the {@link InputStream}
     * @param theStream     the stream to read from
     * @return              the int
     * @throws IOException  if there is an error reading the int
     */
    public static int readInt(final InputStream theStream) throws IOException {
        byte[] array = new byte[Ints.BYTES];
        ByteStreams.readFully(theStream, array);
        return Ints.fromByteArray(array);
    }

    /**
     * Write an int to the {@link OutputStream}
     * @param theStream     the stream to write to
     * @param theInt        the int to write
     * @throws IOException  if there was an error writing the int
     */
    public static void writeInt(final OutputStream theStream, final int theInt) throws IOException {
        theStream.write(Ints.toByteArray(theInt));
    }

    /**
     * GZIP the array of bytes
     *
     * @param theBytes      the bytes to gzip
     * @return              the bytes, gzipped
     * @throws IOException  if there was an error during gzipping
     */
    public static byte[] gzip(final byte[] theBytes) throws IOException {
        ByteArrayOutputStream aOut = new ByteArrayOutputStream(theBytes.length);

        GZIPOutputStream aZipped = new GZIPOutputStream(aOut);
        ByteStreams.copy(new ByteArrayInputStream(theBytes), aZipped);

        aZipped.flush();
        aZipped.close();

        return aOut.toByteArray();
    }

    /**
     * Convert the object into an array of bytes.  The object <b>must</b> be {@link java.io.Serializable}
     * as this will use {@link ObjectOutputStream} to convert the object into a byte array.
     * The results of this method can later be used in conjunction with {@link java.io.ObjectInputStream}
     * to re-create the object.
     *
     * @param theObj    the object to conver into a byte array
     * @return          the object as an array of bytes in its serialized form
     *
     * @throws IOException  if there was an error while serializing the object
     */
    public static byte[] toByteArray(final Object theObj) throws IOException {

        ByteArrayOutputStream aByteOutput = new ByteArrayOutputStream();
        ObjectOutputStream aObjOut = new ObjectOutputStream(aByteOutput);
        aObjOut.writeObject(theObj);
        aObjOut.flush();

        return aByteOutput.toByteArray();
    }
}
