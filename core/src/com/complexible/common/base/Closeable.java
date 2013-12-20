// Copyright (c) 2010 - 2013, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.base;

import com.google.common.annotations.Beta;

/**
 * <p>Interface for anything that can be closed.  Basically, AutoCloseable; which will replace this
 * when the library is moved to JDK7.</p>
 *
 * @author  Michael Grove
 * @since   3.1.2
 * @version 3.1.2
 */
@Beta
public interface Closeable {
	public void close() throws Throwable;
}
