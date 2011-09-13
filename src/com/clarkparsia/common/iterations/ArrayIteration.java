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
 * <p>Implementation of an {@link Iteration} backed by an array.</p>
 *
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public final class ArrayIteration<T, E extends Exception> extends AbstractIteration<T, E> {

	/**
	 * The current iteration index into the array
	 */
	private int mIndex = 0;

	/**
	 * The array to iterate over
	 */
	private final T[] mArray;

	/**
	 * Create a new Iteration over the provided array
	 * @param theArray the array to iterate over
	 */
	public ArrayIteration(final T[] theArray) {
		mArray = theArray;
	}

	/**
	 * @inheritDoc
	 */
	protected T computeNext() throws E {
		if (mIndex == mArray.length) {
			return endOfData();
		}

		return mArray[mIndex++];
	}
}
