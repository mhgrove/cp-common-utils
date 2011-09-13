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

/**
 * <p>Implementation of an {@link Iteration} which delegates its methods to a child Iteration.</p>
 *
 * @author Michael Grove
 * @version 2.0
 * @since 2.0
 */
public abstract class DelegatingIteration<T, E extends Exception> implements Iteration<T, E> {

	/**
	 * The child iteration
	 */
	private Iteration<T, E> mIter;

	/**
	 * Create a new DelegatingIteration
	 * @param theIter the actual iteration
	 */
	protected DelegatingIteration(final Iteration<T, E> theIter) {
		mIter = theIter;
	}

	/**
	 * Return the child iteration
	 * @return the iteration
	 */
	protected Iteration<T, E> getIteration() {
		return mIter;
	}

	/**
	 * @inheritDoc
	 */
	public T next() throws E {
		return mIter.next();
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
}
