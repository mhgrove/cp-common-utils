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
