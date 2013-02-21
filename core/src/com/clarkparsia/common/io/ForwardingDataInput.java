// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.clarkparsia.common.io;

import java.io.DataInput;
import java.io.IOException;

import com.google.common.collect.ForwardingObject;

/**
 * <p>Base class for creating a {@link DataInput} decorator</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public class ForwardingDataInput extends ForwardingObject implements DataInput {
    private final DataInput mInput;

    public ForwardingDataInput(final DataInput theInput) {
        mInput = theInput;
    }

    /**
     * @inheritDoc
     */
    @Override
    protected DataInput delegate() {
        return mInput;
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
