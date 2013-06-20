// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.io;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>A {@link DataInput} as an InputStream, akin to {@link java.io.DataInputStream} in that both classes are a {@link DataInput}
 * and an {@link InputStream}, so rather than declaring a parameter is type {@code DataInputStream} you can revert
 * to using {@code <T extends InputStream & DataInput>} and be able to use either class.  Unlike {@link java.io.DataInputStream},
 * this class assumes you have a {@code DataInput} to start with rather than an {@link InputStream}.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public class InputStreamDataInput extends InputStream implements DataInput {
    private final DataInput mInput;

    public InputStreamDataInput(final DataInput theInput) {
        mInput = theInput;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int read() throws IOException {
        try {
            return mInput.readByte();
        }
        catch (EOFException e) {
            return -1;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean readBoolean() throws IOException {
        return mInput.readBoolean();
    }

    /**
     * @inheritDoc
     */
    @Override
    public byte readByte() throws IOException {
        return mInput.readByte();
    }

    /**
     * @inheritDoc
     */
    @Override
    public char readChar() throws IOException {
        return mInput.readChar();
    }

    /**
     * @inheritDoc
     */
    @Override
    public double readDouble() throws IOException {
        return mInput.readDouble();
    }

    /**
     * @inheritDoc
     */
    @Override
    public float readFloat() throws IOException {
        return mInput.readFloat();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void readFully(final byte[] theBytes) throws IOException {
        mInput.readFully(theBytes);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void readFully(final byte[] theBytes, final int i, final int i2) throws IOException {
        mInput.readFully(theBytes, i, i2);
    }

    /**
     * @inheritDoc
     */
    @Override
    public int readInt() throws IOException {
        return mInput.readInt();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String readLine() throws IOException {
        return mInput.readLine();
    }

    /**
     * @inheritDoc
     */
    @Override
    public long readLong() throws IOException {
        return mInput.readLong();
    }

    /**
     * @inheritDoc
     */
    @Override
    public short readShort() throws IOException {
        return mInput.readShort();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int readUnsignedByte() throws IOException {
        return mInput.readUnsignedByte();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int readUnsignedShort() throws IOException {
        return mInput.readUnsignedShort();
    }

    /**
     * @inheritDoc
     */
    @Override
    public String readUTF() throws IOException {
        return mInput.readUTF();
    }

    /**
     * @inheritDoc
     */
    @Override
    public int skipBytes(final int i) throws IOException {
        return mInput.skipBytes(i);
    }
}
