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
 * <p>An iteration which exposes the next element it will return.  The {@link #peek} function will return the same value a subsequent call to {@link #next} would return,
 * but without moving the internal cursor of the iterator.</p>
 *
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public interface PeekingIteration<T, E extends Exception> extends Iteration<T,E> {

	/**
	 * Return the next element without moving the internal cursor of the iterator to the next element.
	 * @return the element that will be returned by the subsequent call to {@link #next}
	 * @throws E if there is an error retrieving the element
	 * @throws java.util.NoSuchElementException if there is not a next element
	 */
	public T peek() throws E, NoSuchElementException;
}
