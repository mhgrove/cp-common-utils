// Copyright (c) 2010 - 2013 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.io;

import java.io.DataOutput;
import java.io.IOException;

/**
 * <p>Similar to the Guava {@link com.google.common.hash.Funnel}, implementations of this class are expected to take the input provided to the funnel
 * method and write it to the DataOutput object.  To be used as an alternative to {@link java.io.Serializable} a way to generically encapsulate
 * and algorithm for serializing an object in some form.</p>
 *
 * <p>There is no requirement, generally, that the serialization must be complete such that the object can be reconstructed later; however,
 * concrete implementations can impose this as part of their design.</p>
 *
 * @author  Michael Grove
 * @since   1.0
 * @version 1.0
 */
public interface DataOutputFunnel<T, O extends DataOutput> {

    /**
     * Send a stream of data from the source object into the DataOutput sink.
     *
     * @param theObj        the object to read from
     * @param theTo         the output to write to
     *
     * @throws IOException  if there is an error writing
     */
    public void funnel(final T theObj, final O theTo) throws IOException;
}
