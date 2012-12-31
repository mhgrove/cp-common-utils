/*
 * Copyright (c) 2005-2011 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.clarkparsia.common.iterations;

import java.util.NoSuchElementException;

import com.google.common.base.Function;
import com.google.common.base.Throwables;

/**
 * Implementation of an Iteration which will apply a function to transform the exceptions of an inner iteration to a 
 * different exception type. If the given transformation fails to wrap the original exception, a {@link RuntimeException}
 * will be thrown instead.
 * 
 * @param <T> the iteration element type
 * @param <IE> the input exception type
 * @param <OE> the output exception thrown
 * @author Evren Sirin
 * @since 2.2
 * @version 2.2
 */
public class TransformException<T,IE extends Throwable,OE extends Throwable> implements Iteration<T, OE> {
	/**
	 * The function that transforms the results
	 */
	private Function<IE,OE> mFunc;

	/**
	 * The iteration whose results should be transformed
	 */
	private Iteration<T, IE> mIter;

	/**
	 * Create a new TransformIteration
	 * @param theFunc the functino to use to transform
	 * @param theIter the iter whose results should be transformed
	 */
	public TransformException(final Function<IE, OE> theFunc, final Iteration<T, IE> theIter) {
		mFunc = theFunc;
		mIter = theIter;
	}
	
	@SuppressWarnings("unchecked")
    private RuntimeException wrapException(Throwable e) throws OE {
        Throwables.propagateIfPossible(e);
        try {
        	throw mFunc.apply((IE) e);
        }
        catch (Throwable ignore) {
        	// if we can't apply the function to the original exception we'll just throw it as a RuntimeException
            throw new RuntimeException(e);
        }		
	}

	/**
	 * @inheritDoc
	 */
	public T next() throws OE, NoSuchElementException {
		try {
            return mIter.next();
        }
        catch (Throwable e) {
            throw wrapException(e);
        }
	}

	/**
	 * @inheritDoc
	 */
	public boolean hasNext() throws OE {
		try {
            return mIter.hasNext();
        }
        catch (Throwable e) {
            throw wrapException(e);
        }
	}

	/**
	 * @inheritDoc
	 */
	public void close() throws OE {
		try {
            mIter.close();
        }
        catch (Throwable e) {
            throw wrapException(e);
        }
	}
}