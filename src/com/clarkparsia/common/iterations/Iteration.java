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
 * <p>New iterator interface.  Mimics {@link java.util.Iterator} in signature, but throws exceptions from its methods and its closeable.  Intended for use in cases where
 * the implementation is iterating over something such that calls to {@link #next} or {@link #hasNext} could result in an exception -- perhaps they are reading over streams or
 * lazily performing a calculation.  Similarly, the {@link #close} method is provide to signal that the iterator is no longer being used and that it is safe to free up any
 * resources that it was using.</p>
 *
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public interface Iteration<T, E extends Throwable> {

	/**
	 * Return the next element
	 * @return the next element
	 * @throws E if there is an error retrieving the next element
	 * @throws java.util.NoSuchElementException if there are no more elements ({@link #hasNext} would return false in this case)
	 */
	public T next() throws E;

	/**
	 * Return whether or not there is another element in the iterator
	 * @return true if there is another element, false otherwise
	 * @throws E if there is an error while trying to see if there is another element
	 */
	public boolean hasNext() throws E;

	/**
	 * Close the iterator.  This signals that it is ok to free up any resources used by the implementation.  It is expected, but not required, that subsequent calls to
	 * {@link #hasNext} or {@link #next} would fail and throw an exception, such as an {@link IllegalStateException}.
	 *
	 * @throws E if there is an exception while closing
	 */
	public void close() throws E;
}
