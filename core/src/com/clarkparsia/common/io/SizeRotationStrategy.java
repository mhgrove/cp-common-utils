// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.clarkparsia.common.io;

import java.io.File;

import com.google.common.base.Preconditions;

/**
 * <p>A {@link FileRotationStrategy rotation strategy} which will rotate a file once it grows
 * past a certain size threshold (in bytes).</p>
 *
 * @author  Michael Grove
 * @since   1.0.1
 * @version 1.0.1
 */
public final class SizeRotationStrategy implements FileRotationStrategy {
    private final long mMaxSize;

    /**
     * Create a new SizeRotationStrategy
     * @param theMaxSize    the max log file size in bytes.
     */
    public SizeRotationStrategy(final long theMaxSize) {
        Preconditions.checkArgument(theMaxSize > 0);
        mMaxSize = theMaxSize;
    }

    public long getMaxSize() {
        return mMaxSize;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean needsRotation(final File theFile) {
        return theFile.length() > mMaxSize;
    }
}
