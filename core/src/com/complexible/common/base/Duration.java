// Copyright (c) 2006 - 2010, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// This source code is available under the terms of the Affero General Public License v3.
//
// Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
// Questions, comments, or requests for clarification: licensing@clarkparsia.com

package com.complexible.common.base;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Immutable representation of a duration.
 * 
 * @author Evren Sirin
 */
public final class Duration implements Serializable {
    private static final long serialVersionUID = 1L;
    
	private final long durationMS;

	public Duration(long time) {
		this.durationMS = time;
	}

	public Duration(long time, TimeUnit theUnit) {
		this(theUnit.toMillis(time));
	}
    
	public long getMillis() {
	    return durationMS;
    }
    
	public long toUnit(TimeUnit theUnit) {
		return theUnit.convert(durationMS, TimeUnit.MILLISECONDS);
	}

    /**
     * @inheritDoc
     */
	@Override
    public int hashCode() {
	    return (int) (durationMS ^ (durationMS >>> 32));
    }

    /**
     * @inheritDoc
     */
	@Override
    public boolean equals(Object obj) {
	    return (obj instanceof Duration) && this.durationMS == ((Duration) obj).durationMS;
    }

	/**
	 * String representation of duration
	 */
    @Override
	public String toString() {
		return Durations.readable(durationMS);
	}
	
	/**
	 * Creates a duration instance from the string.
	 */
	public static Duration valueOf(String str) {
		return new Duration(Durations.parse(str));
	}
}
