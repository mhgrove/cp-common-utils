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

package com.complexible.common.protobuf.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.util.List;

import com.complexible.common.io.ForwardingDataInput;
import com.complexible.common.io.ForwardingDataOutput;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

/**
 * <p>Utility class for protobuf IO</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public final class Protobuf {

    /**
     * No instances
     */
    private Protobuf() {
        throw new AssertionError();
    }

    @SuppressWarnings("unchecked")
    public static <T extends MessageLite> List<T> decodeList(final CodedInputStream theCodeStream, T thePrototype) throws IOException {
        int aCount = readBigEndianInt32(theCodeStream);

        MessageLite.Builder aBuilder = thePrototype.newBuilderForType();
        List<T> aList = Lists.newArrayList();
        for (int i = 0; i < aCount; i++) {
            aBuilder.clear();

            int aSize = theCodeStream.readRawVarint32();
            aBuilder.mergeFrom(theCodeStream.readRawBytes(aSize));

            aList.add((T) aBuilder.build());
        }

        return aList;
    }

    private static int readBigEndianInt32(final CodedInputStream theCodeStream) throws IOException {
        byte one = theCodeStream.readRawByte();
        byte two = theCodeStream.readRawByte();
        byte three = theCodeStream.readRawByte();
        byte four = theCodeStream.readRawByte();

        return Ints.fromBytes(one, two, three, four);
    }

    @SuppressWarnings("unchecked")
    public static <T extends MessageLite> T parseDelimitedFrom(final ByteString theBytes, final T thePrototype) throws IOException {
        MessageLite.Builder aBuilder = thePrototype.getDefaultInstanceForType().newBuilderForType();

        if (aBuilder.mergeDelimitedFrom(theBytes.newInput())) {
            return (T) aBuilder.build();
        }
        else {
            return null;
        }
    }

    /**
     * Reads a protobuf varint 32, the code is copied from {@link com.google.protobuf.CodedInputStream#readRawVarint32()} to read
     * the same encoding, but to work stand alone against a DataInput rather than a CodedInputStream.
     *
     * @param theInput  the input to read from
     * @return          the var int
     *
     * @throws IOException  if there was an error while reading, or if the DataInput does not contain a valid varint32
     */
    public static int readRawVarint32(final DataInput theInput) throws IOException {
        byte tmp = theInput.readByte();
        if (tmp >= 0) {
            return tmp;
        }
        int result = tmp & 0x7f;
        if ((tmp = theInput.readByte()) >= 0) {
            result |= tmp << 7;
        }
        else {
            result |= (tmp & 0x7f) << 7;
            if ((tmp = theInput.readByte()) >= 0) {
                result |= tmp << 14;
            }
            else {
                result |= (tmp & 0x7f) << 14;
                if ((tmp = theInput.readByte()) >= 0) {
                    result |= tmp << 21;
                }
                else {
                    result |= (tmp & 0x7f) << 21;
                    result |= (tmp = theInput.readByte()) << 28;
                    if (tmp < 0) {
                        // Discard upper 32 bits.
                        for (int i = 0; i < 5; i++) {
                            if (theInput.readByte() >= 0) {
                                return result;
                            }
                        }
                        throw new IOException("malformedVarint32");
                    }
                }
            }
        }
        return result;
    }

    /**
     * Reads a protobuf varint64, the code is copied from {@link com.google.protobuf.CodedInputStream#readRawVarint64()} to read
     * the same encoding, but to work stand alone against a DataInput rather than a CodedInputStream.
     *
     * @param theInput  the input to read from
     * @return          the var int
     *
     * @throws IOException  if there was an error while reading, or if the DataInput does not contain a valid varint64
     */
    public static long readRawVarint64(final DataInput theInput) throws IOException {
        int shift = 0;
        long result = 0;
        while (shift < 64) {
            final byte b = theInput.readByte();
            result |= (long)(b & 0x7F) << shift;
            if ((b & 0x80) == 0) {
                return result;
            }
            shift += 7;
        }

        throw new IOException("malformedVarint64");
    }

    /**
     * Copied from {@link com.google.protobuf.CodedOutputStream#writeRawVarint32(int)}; same encoding just assumes
     * that you're writing to {@link DataOutput} rather than a CodedOutputStream.
     *
     * @param theOutput     the output to write to
     * @param theValue         the value to write
     * @throws IOException  if there was an error while writing
     */
    public static void writeRawVarint32(final DataOutput theOutput, int theValue) throws IOException {
        while (true) {
            if ((theValue & ~0x7F) == 0) {
                theOutput.writeByte(theValue) ;
                return;
            } else {
                theOutput.writeByte((theValue & 0x7F) | 0x80);
                theValue >>>= 7;
            }
        }
    }

    /**
     * Copied from {@link com.google.protobuf.CodedOutputStream#writeRawVarint64(int)}; same encoding just assumes
     * that you're writing to {@link DataOutput} rather than a CodedOutputStream.
     *
     * @param theOutput     the output to write to
     * @param theValue         the value to write
     * @throws IOException  if there was an error while writing
     */
    public static void writeRawVarint64(final DataOutput theOutput, long value) throws IOException {
        while (true) {
            if ((value & ~0x7FL) == 0) {
                theOutput.writeByte((int) value);
                return;
            } else {
                theOutput.writeByte(((int)value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }

    public final static ProtobufDataOutput outputFor(final DataOutput theOutput) {
        return new ProtobufDataOutputImpl(theOutput);
    }

    public static <T extends MessageLite> ProtobufDataInput<T> inputFor(final DataInput theInput, final T thePrototype, final ExtensionRegistry theExtensionRegistry) {
        return new ProtobufDataInputImpl<T>(theInput, thePrototype, theExtensionRegistry);
    }

    private static final class ProtobufDataOutputImpl extends ForwardingDataOutput implements ProtobufDataOutput {
        public ProtobufDataOutputImpl(final DataOutput theOutput) {
            super(theOutput);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final MessageLite theMessage) throws IOException {
            byte[] aBytes = theMessage.toByteArray();

            writeInt(aBytes.length);
            write(aBytes);
        }
    }

    private static final class ProtobufDataInputImpl<T extends MessageLite> extends ForwardingDataInput implements ProtobufDataInput<T> {
        private final ExtensionRegistry mExtensionRegistry;

        private final T mPrototype;

        private ProtobufDataInputImpl(final DataInput theInput, final T thePrototype, final ExtensionRegistry theExtensionRegistry) {
            super(theInput);

            mPrototype = thePrototype;
            mExtensionRegistry = theExtensionRegistry;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public T read() throws InvalidProtocolBufferException, IOException {
            try {
                int aSize = readInt();

                byte[] aData = new byte[aSize];

                readFully(aData);

                if (mExtensionRegistry == null) {
                    return (T) mPrototype.newBuilderForType().mergeFrom(aData).build();
                }
                else {
                    return (T) mPrototype.newBuilderForType().mergeFrom(aData, mExtensionRegistry).build();
                }
            }
            catch (EOFException e) {
                return null;
            }
        }
    }
}
