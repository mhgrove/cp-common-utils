// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.protobuf.io;

import java.io.DataInput;

import com.google.protobuf.MessageLite;

/**
 * <p>A {@link DataInput} which can also {@link ProtobufReader read} protobuf messages.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public interface ProtobufDataInput<T extends MessageLite> extends DataInput, ProtobufReader<T>{

}
