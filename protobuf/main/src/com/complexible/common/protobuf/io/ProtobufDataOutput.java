package com.complexible.common.protobuf.io;


import java.io.DataOutput;

/**
 * <p>A {@link java.io.DataOutput} which knows how to write protobuf messages.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public interface ProtobufDataOutput extends DataOutput, ProtobufWriter {
}
