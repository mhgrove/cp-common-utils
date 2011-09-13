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

package com.clarkparsia.common.base;

import java.util.Iterator;

/**
 * <p>Utility methods for working with <code>int</code>s that are not already found in Ints, Integer, or Arrays.</p>
 *
 * @author Michael Grove
 * @version 2.0
 * @since 2.0
 */
public final class Ints2 {

	private Ints2() {
	}

	/**
	 * <p>Provides a List of integers starting at the given index and going to the given end index (inclusive).  Useful
	 * if you want to do something X amount of times:</p>
	 * <code>
	 *   for (int aNum : range(1, 1000)) {
	 *       // do work
	 *   }
	 * </code>
	 * @param theStart the start index
	 * @param theEnd the end index
	 * @return the range of numbers from start to end (inclusive)
	 */
	public static Iterable<Integer> range(final int theStart, final int theEnd) {
		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>(){
					int index = theStart;
					public boolean hasNext() {
						return index <= theEnd;
					}

					public Integer next() {
						return index++;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}
