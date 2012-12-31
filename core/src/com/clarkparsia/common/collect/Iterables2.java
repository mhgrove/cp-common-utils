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

import com.google.common.base.Predicate;
import com.google.common.base.Function;
import static com.google.common.collect.Iterables.find;
import com.google.common.collect.Iterables;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Utility methods for using Iterables which are not present in the Guava Iterables class.  Also includes re-implementations of some methods, filter and transform, found
 * in Iterables but which are slightly faster than their Guava counterparts because they avoid all safety checks.  These can be used when you know there are no nulls in your collections
 * and the Predicate/Function itself will not be null or ever return a null value.</p>
 *
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public final class Iterables2 {

	private Iterables2() {
	}

	/**
	 * Same as {@link Iterables#find} except it does not throw {@link NoSuchElementException} if the result is not found.
	 *
	 * @param theIterable	the iterable to search
	 * @param thePredicate	the predicate to use
	 *
	 * @return true if an element in the Iterable satisfies the predicate, false otherwise
	 */
	public static <T> boolean find(final Iterable<T> theIterable, final Predicate<? super T> thePredicate) {
        try {
            return Iterables.find(theIterable, thePredicate) != null;
        }
        catch (NoSuchElementException e) {
            // find throws this exception when it can't find the element, which is not really helpful
            // we just want the boolean of whether or not it was found.
            return false;
        }
    }

	/**
	 * Apply the predicate to every element in the Iterable.  The result of the predicate is ignored.
	 *
	 * @param theIterable	the iterable whose elements should use the predicate
	 * @param thePredicate	the predicate to apply
	 */
	public static <T> void each(final Iterable<T> theIterable, final Predicate<? super T> thePredicate) {
		for (T aObj : theIterable) {
			thePredicate.apply(aObj);
		}
	}

	/**
	 * Returns the elements of {@code unfiltered} that satisfy a predicate. The resulting iterable's iterator does not support {@code remove()}.
	 * @param unfiltered the unfiltered iterator
	 * @param predicate the filter predicate
	 * @return a filtered iterator
	 */
	public static <T> Iterable<T> filter(final Iterable<T> unfiltered, final Predicate<? super T> predicate) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators2.filter(unfiltered.iterator(), predicate);
			}
		};
	}

	/**
	 * Returns an iterable that applies {@code function} to each element of {@code fromIterable}.
	 *
	 * <p>The returned iterable's iterator supports {@code remove()} if the provided iterator does. After a successful {@code remove()} call,
	 * {@code fromIterable} no longer contains the corresponding element.
	 * @param fromIterable the iterable to transform
	 * @param function the function to tranforms the iterable
	 * @return the transformed iterable
	 */
	public static <F, T> Iterable<T> transform(final Iterable<F> fromIterable, final Function<? super F, ? extends T> function) {
		return new Iterable<T>() {
			public Iterator<T> iterator() {
				return Iterators2.transform(fromIterable.iterator(), function);
			}
		};
	}

	/**
	 * Create an Iterable for the given sub-array
	 * @param theArray the array
	 * @param <T> the type of elements in the array
	 * @return an Iterable over the array
	 */
	public static <T> Iterable<T> forArray(final T... theArray) {
		return new ArrayIterable<T>(theArray);
	}

	/**
	 * Create an Iterable for the given sub-array
	 * @param theArray the array
	 * @param theStart the starting index for iteration
	 * @param theEnd the ending index for iteration
	 * @param <T> the type of elements in the array
	 * @return an Iterable over the array
	 */
	public static <T> Iterable<T> forArray(final T[] theArray, final int theStart, final int theEnd) {
		return new ArrayIterable<T>(theArray, theStart, theEnd);
	}
}
