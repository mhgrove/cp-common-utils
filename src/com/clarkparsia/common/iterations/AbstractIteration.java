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

/**
 * <p>Abstract implementation of the {@link Iteration} interface providing an implementation for all the basic methods of the interface.  Implementors need only implement the
 * {@link #computeNext} method.  Also includes support for the {@link PeekingIteration} interface build in.</p>
 *
 * @author Based on the AbstractIterator implementation from Google Collections/Guava. (by Kevin Bourrillion)
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public abstract class AbstractIteration<T, E extends Throwable> implements Iteration<T,E> {

	/**
	 * Enumeration of the internal states the iterator can be in
	 */
	private enum State {
		DONE,
		READY,
		NOT_READY,
		CLOSED,
		FAILED
	}

	/**
	 * The state of the iterator
	 */
	private State state = State.NOT_READY;

	/**
	 * The next element to be returned, or null if it has not yet been calculated
	 */
	private T next;

	/**
	 * Return the next element to be returned by the iterator
	 * @return the next element
	 * @throws E if there is an error while computing the element
	 */
	protected abstract T computeNext() throws E;

	/**
	 * @inheritDoc
	 */
	public final T next() throws E {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		state = State.NOT_READY;
		return next;
	}

	/**
	 * Signal that you're done iterating.
	 * @return nothing, the result is ignored
	 */
	protected final T endOfData() {
		state = State.DONE;
		return null;
	}

	/**
	 * @inheritDoc
	 */
	public final boolean hasNext() throws E {
		switch (state) {
			case DONE:
				return false;
			case READY:
				return true;
			case CLOSED:
				return false;
			case NOT_READY:
				return tryToComputeNext();
			default:
				throw new IllegalStateException();
		}
	}

	/**
	 * Attempt to get the next element in the iteration, or mark it done.
	 * @return the next element
	 * @throws E if there is an error while trying to compute the next element
	 */
	private boolean tryToComputeNext() throws E {
		state = State.FAILED;
		next = computeNext();
		if (state != State.DONE) {
			state = State.READY;
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * @inheritDoc
	 */
	public void close() throws E {
		state = State.CLOSED;
	}

	/**
	 * Implementation of peeking to the next item returned by the iterator.  Anyone wishing to expose this method simply need to also implement the {@link PeekingIteration} interface.
	 * @return the next element to be returned
	 * @throws E if there is an error
	 */
	public final T peek() throws E {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		return next;
	}
}
