// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

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
	 * @inheritDoc
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