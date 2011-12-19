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

import java.lang.reflect.InvocationTargetException;

import com.google.common.base.Predicate;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;

/**
 * <p>Additional precondition checks to extend Guava Preconditions</p>
 *
 * @author Michael Grove
 * @version 2.0
 * @since 2.0
 */
public final class Preconditions2 {

	/**
	 * Private constructor
	 */
	private Preconditions2() {
	}

	public static <S, T extends Exception> void check(final Iterable<S> theObject, final Predicate<S> thePredicate, Class<T> theExceptionType) throws T {
		check(Iterables.any(theObject, thePredicate), theExceptionType, null);
	}

	public static <S, T extends Exception> void check(final Iterable<S> theObject, final Predicate<S> thePredicate, final Class<T> theExceptionType,
													  final String theMessage, final Object... theArgs) throws T {
		check(Iterables.any(theObject, thePredicate), theExceptionType, theMessage, theArgs);
	}

	public static <S, T extends Exception> void check(final S theObject, final Predicate<S> thePredicate, Class<T> theExceptionType) throws T {
		check(thePredicate.apply(theObject), theExceptionType, null);
	}

	public static <S, T extends Exception> void check(final S theObject, final Predicate<S> thePredicate, final Class<T> theExceptionType,
													  final String theMessage, final Object... theArgs) throws T {
		check(thePredicate.apply(theObject), theExceptionType, theMessage, theArgs);
	}

	public static <T extends Exception> void check(final boolean theBoolean, Class<T> theExceptionType) throws T {
		check(theBoolean, theExceptionType, null);
	}

	public static <T extends Exception> void check(final boolean theBoolean, Class<T> theExceptionType, final String theMessage, final Object... theArgs) throws T {
		if (!theBoolean) {
			createAndThrowException(theExceptionType, theMessage, theArgs);
		}
	}

	private static <T extends Exception> void createAndThrowException(final Class<T> theExceptionType, final String theMessage, final Object[] theArgs) throws T {
		if (theMessage != null) {
			String aMsg = String.format(theMessage, theArgs);

			try {
				throw theExceptionType.getConstructor(String.class).newInstance(aMsg);
			}
			catch (InstantiationException e) {
				throw new IllegalArgumentException(e);
			}
			catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
			catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
			catch (NoSuchMethodException e) {
				try {
					throw theExceptionType.newInstance();
				}
				catch (InstantiationException e1) {
					throw new IllegalArgumentException(e);
				}
				catch (IllegalAccessException e1) {
					throw new IllegalArgumentException(e);
				}
			}
		}
		else {
			try {
				throw theExceptionType.newInstance();
			}
			catch (InstantiationException e) {
				throw new IllegalArgumentException(e);
			}
			catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	public static <T> T[] checkNotEmpty(final T[] theArray) {
		if (theArray == null || theArray.length == 0) {
			throw new IllegalArgumentException();
		}

		return theArray;
	}
}
