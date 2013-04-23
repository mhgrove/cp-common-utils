// Copyright (c) 2010 - 2011 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.clarkparsia.common.base;

import com.google.common.base.Function;

/**
 * A function interface similar to {@link Function} but accepts two arguments.
 * 
 * @author  Evren Sirin
 * @since   0.7
 * @version 0.7
 */
public interface BinaryFunction<Arg1,Arg2,Output> {
	public Output apply(Arg1 theArg1, Arg2 theArg2);
}