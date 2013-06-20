// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.io;


import java.io.File;

/**
 * <p>Interface for implementing file rotation policies.</p>
 *
 * @author  Michael Grove
 * @since   1.0.1
 * @version 1.0.1
 *
 * @see SizeRotationStrategy
 * @see TimeRotationStrategy
 */
public interface FileRotationStrategy {
    /**
     * Return whether or not the file needs to be rotated based on the policy this interface implements
     * @param theFile   the file
     * @return          true if the file needs to be rotated, false otherwise
     */
    public boolean needsRotation(final File theFile);
}
