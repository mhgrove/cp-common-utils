// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.protobuf.io;

import java.io.OutputStream;
import java.io.IOException;

import com.google.protobuf.MessageLite;
import com.google.common.primitives.Ints;

/**
 * <p>Implementation of {@link ProtobufWriter} which will write protobuf message to an output stream.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public final class OutputStreamProtobufWriter implements ProtobufWriter {
	private final OutputStream mStream;

	public OutputStreamProtobufWriter(final OutputStream theStream) {
		mStream = theStream;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void write(final MessageLite theMessage) throws IOException {
		byte[] aMsg = theMessage.toByteArray();

		mStream.write(Ints.toByteArray(aMsg.length));
		mStream.write(aMsg);
	}
}
