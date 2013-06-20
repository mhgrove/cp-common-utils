// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.protobuf.io;

import java.io.IOException;

import com.google.protobuf.MessageLite;

/**
 * <p>Interface for writing Protobuf {@link MessageLite messages} to some output.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public interface ProtobufWriter {

    /**
     * Write the Protobuf message.
     *
     * @param theMessage	the message to write
     * @throws IOException	if there was an error while writing
     */
    public void write(final MessageLite theMessage) throws IOException;
}
