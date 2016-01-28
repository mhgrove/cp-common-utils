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

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.MessageLite;

/**
 * <p>Implementation of a {@link ProtobufReader} which will read Protobuf messages from an {@link java.io.InputStream}.  Assumes
 * that the messages are the standard length delimited protofbuf messages which can be read via <code>MessageLite.Builder.mergeDelimitedFrom(InputStream)</code></p>
 *
 * @author  Michael Grove
 * @version 1.0
 * @since   1.0
 */
public final class InputStreamRawProtobufReader<T extends MessageLite> implements ProtobufReader<T> {
	private final InputStream mStream;

	private final ExtensionRegistry mExtensionRegistry;

	private final T mPrototype;

	public InputStreamRawProtobufReader(final InputStream theStream, final T thePrototype) {
		this(theStream, thePrototype, null);
	}

	public InputStreamRawProtobufReader(final InputStream theStream, final T thePrototype, final ExtensionRegistry theExtensionRegistry) {
		mStream = theStream;
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
			final MessageLite.Builder aBuilder = mPrototype.newBuilderForType();

			if (mExtensionRegistry == null) {
				return aBuilder.mergeDelimitedFrom(mStream)
					   ? (T) aBuilder.build()
					   : null;

			}
			else {
				return aBuilder.mergeDelimitedFrom(mStream, mExtensionRegistry)
					   ? (T) aBuilder.build()
					   : null;
			}
		}
		catch (EOFException e) {
			return null;
		}
	}
}
