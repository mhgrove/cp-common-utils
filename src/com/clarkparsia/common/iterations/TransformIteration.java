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

import com.clarkparsia.common.iterations.Iteration;
import com.google.common.base.Function;

import java.util.NoSuchElementException;

/**
 * Implementation of an Iteration which will apply a function to transform the results of an inner iterator to a different type.
 * @param <I> the input type
 * @param <O> the iteration type
 * @param <E> the exception thrown
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public final class TransformIteration<I,O,E extends Throwable> implements Iteration<O, E> {

	/**
	 * The function that transforms the resuts
	 */
	private Function<I,O> mFunc;

	/**
	 * The iteration whose results should be transformed
	 */
	private Iteration<I, E> mIter;

	/**
	 * Create a new TransformIteration
	 * @param theFunc the functino to use to transform
	 * @param theIter the iter whose results should be transformed
	 */
	public TransformIteration(final Function<I, O> theFunc, final Iteration<I, E> theIter) {
		mFunc = theFunc;
		mIter = theIter;
	}

	/**
	 * @inheritDoc
	 */
	public O next() throws E, NoSuchElementException {
		return mFunc.apply(mIter.next());
	}

	/**
	 * @inheritDoc
	 */
	public boolean hasNext() throws E {
		return mIter.hasNext();
	}

	/**
	 * @inheritDoc
	 */
	public void close() throws E {
		mIter.close();
	}

	/**
	 * Simple factory method for creating TransformIteration's which hides some of the nastiness of the generices
	 * @param theFunc the function to use to transform
	 * @param theIter the iter whose results should be transformed
	 * @param <I> the input type
	 * @param <O> the iteration type
	 * @param <E> the exception thrown
	 * @return a new TransformIteration
	 */
	public static <I,O, E extends Throwable> TransformIteration<I, O, E> create(final Function<I,O> theFunc, final Iteration<I,E> theIter) {
		return new TransformIteration<I,O,E>(theFunc, theIter);
	}
}
