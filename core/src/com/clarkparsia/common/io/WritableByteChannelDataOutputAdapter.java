// Copyright (c) 2010 - 2013 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.clarkparsia.common.io;

import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/**
 * <p>An adapter for a {@link java.io.DataOutput} to use it as a {@link java.nio.channels.WritableByteChannel}.</p>
 *
 * <p>This assumes it's always open, and is generally uncloseable.  It's the responsibility of the user to
 * manage the open/close status of whatever the underlying output of the {@code DataOutput} is.</p>
 *
 * @author  Michael Grove
 * @since   2.4.1
 * @version 2.4.1
 */
public final class WritableByteChannelDataOutputAdapter extends ForwardingDataOutput implements WritableByteChannel, DataOutput {

    public WritableByteChannelDataOutputAdapter(final DataOutput theOutput) {
        super(theOutput);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isOpen() {
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void close() throws IOException {
        // no-op
    }

    /**
     * @inheritDoc
     */
    @Override
    public int write(final ByteBuffer theByteBuffer) throws IOException {
        byte[] aBytes = new byte[theByteBuffer.remaining()];
        theByteBuffer.get(aBytes);
        delegate().write(aBytes);
        return aBytes.length;
    }
}
