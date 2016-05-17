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
	 * {@inheritDoc}
	 */
	@Override
	public void write(final MessageLite theMessage) throws IOException {
		byte[] aMsg = theMessage.toByteArray();

		mStream.write(Ints.toByteArray(aMsg.length));
		mStream.write(aMsg);
	}
}
