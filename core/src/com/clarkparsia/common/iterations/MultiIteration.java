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

import java.util.Iterator;

/**
 * <p>An adapter to provide a single iteration over a list of iterations.  This will iterate over each child iteration in the order they are provided in the constructor.</p>
 *
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public class MultiIteration<T, E extends Throwable> extends AbstractIteration<T, E> {

	/**
	 * The list of iterations to iterate over
	 */
	private Iterator<? extends Iteration<T, E>> mIter;

	/**
	 * The current iteration
	 */
	private Iteration<T,E> mCurr;

	/**
	 * Create a new MultiIteration
	 * @param theIter the list of iterations to iterate against
	 */
	public MultiIteration(final Iterable<? extends Iteration<T, E>> theIter) {
		mIter = theIter.iterator();
	}

	/**
	 * @inheritDoc
	 */
	protected T computeNext() throws E {
		if (mCurr == null) {
			if (!mIter.hasNext()) {
				return endOfData();
			}

			mCurr = mIter.next();
		}

		while (!mCurr.hasNext() && mIter.hasNext()) {
			mCurr.close();

			mCurr = mIter.next();
		}

		if (mCurr.hasNext()) {
			return mCurr.next();
		}
		else {
			return endOfData();
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void close() throws E {
		super.close();
		mCurr.close();

		while (mIter.hasNext()) {
			mIter.next().close();
		}
	}
}
