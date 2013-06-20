// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.protobuf.io;

import java.io.IOException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;

/**
 * <p>Interface for reading protobuf messages from some source.</p>
 *
 * @author Michael Grove
 * @since   1.0
 * @version 1.0
 */
public interface ProtobufReader<T extends MessageLite> {

    /**
     * Read a Protobuf message from the underlying source
     *
     * @return the message that was read or null if there are no more messages.
     *
     * @throws InvalidProtocolBufferException	if there was an error decoding the message
     * @throws IOException						if there was an error reading the contents of the message
     */
    public T read() throws InvalidProtocolBufferException, IOException;
}
