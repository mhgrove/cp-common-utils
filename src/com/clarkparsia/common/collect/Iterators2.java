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

package com.clarkparsia.common.collect;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.UnmodifiableIterator;

/**
 * <p>Utility methods for using Iterators which are not present in the Guava Iterators class.  Also includes re-implementations of some methods, filter and transform, found
 * in Iterators but which are slightly faster than their Guava counterparts because they avoid all safety checks.  These can be used when you know there are no nulls in your collections
 * and the Predicate/Function itself will not be null or ever return a null value.</p>
 *
 * @author Michael Grove
 * @author Pedro Oliveira
 * @since 2.0
 * @version 2.0
 */
public final class Iterators2 {

	/**
	 * Private constructor
	 */
	private Iterators2() {
	}

	/**
	 * Creates an {@link Iterator} that returns the same element a predetermined number of times
	 *
	 * @param <T> the type of the element
	 * @param theElement the element to return
	 * @param theNumberOfTimes the number of times to repeat the element
	 * @return an Iterator that will repeat the given element the specified number of times
	 */
	public static <T> Iterator<T> repeat(final T theElement, final long theNumberOfTimes) {

		return new UnmodifiableIterator<T>() {
			long count = 0;

			public boolean hasNext() {
	            return count < theNumberOfTimes;
            }

			public T next() {
	            count++;
	            return theElement;
            }
		};
	}


	/**
	 * Returns the elements of {@code unfiltered} that satisfy a predicate.
	 * @param unfiltered the unfiltered iterator
	 * @param predicate the predicate to use to filter
	 * @return the filtered iterator
	 */
	public static <T> UnmodifiableIterator<T> filter(final Iterator<T> unfiltered, final Predicate<? super T> predicate) {
		return new AbstractIterator<T>() {
			/**
			 * @inheritDoc
			 */
			@Override
			protected T computeNext() {
				while (unfiltered.hasNext()) {
					T element = unfiltered.next();
					if (predicate.apply(element)) {
						return element;
					}
				}
				return endOfData();
			}
		};
	}

	/**
	 * Transform the elements of the given Iterator using the function.
	 * @param theIterator the iterator to transform
	 * @param theFunction the function to transform with
	 * @return the transformed iterator
	 */
	public static <T, S> Iterator<T> transform(final Iterator<S> theIterator, final Function<? super S, ? extends T> theFunction) {
		return new UnmodifiableIterator<T>() {

			/**
			 * @inheritDoc
			 */
			public boolean hasNext() {
				return theIterator.hasNext();
			}

			/**
			 * @inheritDoc
			 */
			public T next() {
				return theFunction.apply(theIterator.next());
			}
		};
	}

	/**
	 * Create an Iterator for the given sub-array
	 * @param theArray the array
	 * @param <T> the type of elements in the array
	 * @return an Iterator over the array
	 */
	public static <T> PeekingIterator<T> forArray(final T... theArray) {
		return new ArrayIterator<T>(theArray);
	}

	/**
	 * Create an Iterator for the given sub-array
	 * @param theArray the array
	 * @param theStart the starting index for iteration
	 * @param theEnd the ending index for iteration
	 * @param <T> the type of elements in the array
	 * @return an Iterator over the array
	 */
	public static <T> PeekingIterator<T> forArray(final T[] theArray, final int theStart, final int theEnd) {
		return new ArrayIterator<T>(theArray, theStart, theEnd);
	}
	
	private static final PeekingIterator<Object> EMPTY_PEEKING_ITERATOR = new PeekingIterator<Object>() {
		@Override
        public Object next() {
	        throw new NoSuchElementException();
        }

		@Override
        public Object peek() {
			throw new NoSuchElementException();
        }

		@Override
        public void remove() {
	        throw new IllegalStateException();
        }

		@Override
        public boolean hasNext() {
	        return false;
        }
	};
	
	@SuppressWarnings("unchecked")
    public static <T> PeekingIterator<T> emptyPeekingIterator() {	
		return (PeekingIterator<T>) EMPTY_PEEKING_ITERATOR;
	}
}
