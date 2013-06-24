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

import java.io.InputStream;
import java.io.IOException;
import java.io.EOFException;

import com.google.common.primitives.Ints;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.MessageLite;
import com.google.common.io.ByteStreams;

/**
 * <p>Implementation of a {@link ProtobufReader} which will read Protobuf messages from an {@link InputStream}.  Assumes the messages
 * are length delimited with a fixed-width big endian coded integer which is the size of the subsequent message.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public final class InputStreamSizeDelimitedProtobufReader<T extends MessageLite> implements ProtobufReader<T> {
	private final InputStream mStream;

	private final ExtensionRegistry mExtensionRegistry;

	private final T mPrototype;

    private final byte[] array = new byte[Ints.BYTES];

	public InputStreamSizeDelimitedProtobufReader(final InputStream theStream, final T thePrototype) {
		this(theStream, thePrototype, null);
	}

	public InputStreamSizeDelimitedProtobufReader(final InputStream theStream, final T thePrototype, final ExtensionRegistry theExtensionRegistry) {
		mStream = theStream;
		mPrototype = thePrototype;
		mExtensionRegistry = theExtensionRegistry;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T read() throws InvalidProtocolBufferException, IOException {
		try {
            ByteStreams.readFully(mStream, array);

            int aSize = Ints.fromByteArray(array);

            byte[] aData = new byte[aSize];

			ByteStreams.readFully(mStream, aData);

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
