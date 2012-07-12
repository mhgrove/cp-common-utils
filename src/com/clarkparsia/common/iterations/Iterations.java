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

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Function;
import com.google.common.collect.Sets;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * <p>Utility class for working with Iterations</p>
 *
 * @author Michael Grove
 * @since 2.0
 * @version 2.0
 */
public final class Iterations {
	/**
	 * the logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(Iterations.class);

	/**
	 * Create an {@link Iteration} which iterates over the given array
	 * @param theArray the array to iterate over
	 * @return an Iteration over the array
	 */
	public static <T, E extends Exception> Iteration<T,E> forArray(final T[] theArray) {
		return new ArrayIteration<T, E>(theArray);
	}

	/**
	 * Return an empty {@link Iteration} which does not iterate.
	 * @return an empty Iteration
	 */
	public static <T, E extends Exception> Iteration<T,E> emptyIteration() {
		return new EmptyIteration<T,E>();
	}

	/**
	 * <p>An {@link Iteration} implementation which is empty and does not iterate.
	 *
	 * @author Michael Grove
	 * @since 2.0
	 * @version 2.0
	 */
	private static class EmptyIteration<T, E extends Exception> implements Iteration<T,E> {

		/**
		 * @inheritDoc
		 */
		@Override
		public T next() throws E {
			throw new NoSuchElementException();
		}

		/**
		 * @inheritDoc
		 */
		@Override
		public boolean hasNext() throws E {
			return false;
		}

		/**
		 * @inheritDoc
		 */
		@Override
		public void close() throws E {
			// no-op
		}
	}

	/**
	 * Filters any duplicated values from the Iteration.  This keeps a copy of the values it has already seen, so there is additional memory overhead
	 * for computing which values are unique.  Values in the iteration should correctly implement hashCode & equals otherwise the results of invoking
	 * this method are not guaranteed.
	 *
	 * @param theIteration the iteration to filter duplicated values from
	 * @param <T> the type of the value
	 * @param <E> the type of exception that can be thrown
	 * @return a unique iteration
	 * @throws E if there was an error while computing the result.
	 */
	public static <T, E extends Exception> Iteration<T,E> unique(final Iteration<T,E> theIteration) throws E {
		return filter(theIteration, new UniqueFilter<T>());
	}

	/**
	 * Predicate implementation which will return false ( iff it has not seen the value before
	 * @param <T> the type of values that can be filtered
	 */
	private static class UniqueFilter<T> implements Predicate<T> {
		private final Set<T> mResults = Sets.newHashSet();

		/**
		 * @inheritDoc
		 */
		@Override
		public boolean apply(final T theResource) {
			boolean aResult = !mResults.contains(theResource);

			if (aResult) {
				mResults.add(theResource);
			}

			return aResult;
		}
	}

	/**
	 * Combines multiple Iterations into a single Iteration.
	 * @param theIterations the iterations to concat
	 * @param <T> the result type
	 * @param <E> the exception that can be thrown during use
	 * @return a concatenated iteration which will iterate over all the provided Iterations
	 */
	public static <T, E extends Exception> Iteration<T, E> concat(Iterable<Iteration<T, E>> theIterations) {
		return new MultiIteration<T,E>(theIterations);
	}

	/**
	 * Combines multiple Iterations into a single Iteration
	 * @param theIterations the iterations to concat
	 * @param <T> the result type
	 * @param <E> the exception that can be thrown during use
	 * @return a concatenated iteration which will iterate over all the provided Iterations
	 */
	public static <T, E extends Exception> Iteration<T, E> concat(Iteration<T, E>... theIterations) {
		return new MultiIteration<T,E>(Arrays.asList(theIterations));
	}

    /**
     * Quietly close the iteration and swallow any errors that result from closing
     * @param theIteration the iteration to close
     */
    public static void closeQuietly(Iteration<?, ?> theIteration) {
        try {
            if (theIteration != null) {
                theIteration.close();
            }
        }
        catch (Throwable theE) {
            LOGGER.warn("Could not close an iteration due to: " + theE.getMessage());
        }
    }

	/**
	 * Wrap an adapater around the Iteration so it can be used as a standard java Iterator.  Exceptions are thrown unchecked as RuntimeExceptions.  If you use this class, it
	 * is recommended that you catch RuntimeExceptions and rethrow it's cause as the type exposed by the Iteration.  The adapter does not close the Iteration when it is
	 * done with it, so the caller is responsible for closing the original iteration to free up its resources.
	 * @param theIteration the iteration
	 * @param <T> the return type
	 * @param <E> the error type thrown by the iteration
	 * @return an Iterator wrapping the iteration.
	 */
	public static <T, E extends Throwable> Iterator<T> toIterator(final Iteration<T, E> theIteration) {
		return new Iterator<T>() {
			public boolean hasNext() {
				try {
					return theIteration.hasNext();
				}
				catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}

			public T next() {
				try {
					return theIteration.next();
				}
				catch (Throwable e) {
					throw new RuntimeException(e);
				}
				finally {
					try {
						if (!theIteration.hasNext()) {
							theIteration.close();
						}
					}
					catch (Throwable theE) {
						LOGGER.warn("Ignored error while closing iteration", theE);
					}
				}
			}

			public void remove() {
				// no-op
			}
		};
	}

	/**
	 * Create a Iteration backed by a standard Java Iterator
	 * @param theIterator the base iterator
	 * @param <T> the return type
	 * @return an Iteration backed by the Iterator
	 */
	public static <T, E extends Exception> Iteration<T, E> toIteration(final Iterator<T> theIterator) {
		return new IteratorIteration<T, E>(theIterator);
	}

	/**
	 * Create an Iteration that is the union of all the provided iterations
	 * @param theIterations the child iteratinos
	 * @param <T> the return type
	 * @param <E> the error type
	 * @return an Iteration that will iterate over the contents of <b>all</b> the provided iterations
	 */
	public static <T, E extends Throwable> Iteration<T,E> all(final Iterable<Iteration<T,E>> theIterations) {
		return new MultiIteration<T,E>(theIterations);
	}

	/**
	 * Create an Iteration that is the union of all the provided iterations
	 * @param theIterations the child iteratinos
	 * @param <T> the return type
	 * @param <E> the error type
	 * @return an Iteration that will iterate over the contents of <b>all</b> the provided iterations
	 */
	public static <T, E extends Throwable> Iteration<T,E> all(final Iteration<T,E>... theIterations) {
		return new MultiIteration<T,E>(Arrays.asList(theIterations));
	}

	/**
	 * Return the contents of the Iteration as a Set.  The Iteration is closed when the method returns.
	 * @param theIteration the iteration
	 * @param <T> the type returned by the iteration
	 * @param <E> the exception thrown by the iteration
	 * @return the contents of the iteration as a set
	 * @throws E if there is an error getting the elements from the iteration
	 */
	public static <T, E extends Throwable> Set<T> set(final Iteration<T,E> theIteration) throws E {
		try {
			Set<T> aSet = Sets.newHashSet();
			while (theIteration.hasNext()) {
				aSet.add(theIteration.next());
			}

			return aSet;
		}
		finally {
			theIteration.close();
		}
	}

    /**
     * Return whether or not the Iterations have the same contents.  Does not compare implementation.
     * null's are considered equals, otherwise Object.equals is used.  Iterations are not closed.
     *
     * @param theLeftIter   an iteration
     * @param theRightIter  the iteration to compare to
     * @return              true if the iterations have the same exact contents, false otherwise.
     * @throws E            if one of the iterations threw something while comparing
     */
    public static <T, E extends Throwable> boolean equals(final Iteration<T,E> theLeftIter,
                                                          final Iteration<T,E> theRightIter) throws E {
        while (theLeftIter.hasNext()) {
            if (!theRightIter.hasNext()) {
                return false;
            }

            final T aLeft = theLeftIter.next();
            final T aRight = theRightIter.next();

            if (!Objects.equal(aLeft, aRight)) {
                return false;
            }
        }

        return true;
    }

	/**
	 * Return the contents of the Iteration as a List.  The Iteration is closed when the method returns.
	 * @param theIteration the iteration
	 * @param <T> the type of the iteration
	 * @param <E> the error type of the iteration
	 * @return the contents of the iteration as a list
	 * @throws E if there is an error getting the elements from the Iteration
	 */
	public static <T, E extends Throwable> List<T> list(final Iteration<T,E> theIteration) throws E {
		try {
			List<T> aList = Lists.newArrayList();
			while (theIteration.hasNext()) {
				aList.add(theIteration.next());
			}

			return aList;
		}
		finally {
			theIteration.close();
		}
	}

	/**
	 * Apply the provided predicate to each element in the Iteration.  The Iteration is closed when the method returns.
	 * @param theIteration the iteration
	 * @param thePred the predicate to apply
	 * @param <T> the type of the iteration
	 * @param <E> the error type of the iteration
	 * @throws E if there is an error while iterating
	 */
	public static <T, E extends Throwable> void each(final Iteration<T,E> theIteration, final Predicate<T> thePred) throws E {
		try {
			while (theIteration.hasNext()) {
				thePred.apply(theIteration.next());
			}
		}
		finally {
			theIteration.close();
		}
	}

	/**
	 * Return a new iteration which is a filtered view of the original iteration. The original iteration will be exhausted by this process
	 * @param theIteration the iteration
	 * @param thePred the predicate to use for filtering
	 * @param <T> the type of the iteration
	 * @param <E> the error type
	 * @return a filtered view of the original iteration
	 * @throws E if there is an error while iterating
	 */
	public static <T, E extends Throwable> Iteration<T,E> filter(final Iteration<T,E> theIteration, final Predicate<T> thePred) throws E {
		return new FilterIteration<T,E>(theIteration, thePred);
	}

	/**
	 * Apply the function to the results of the Iteration and return a new Iteration with the transformed results
	 * @param theIteration the iteration
	 * @param theFunc the transforming function
	 * @param <I> the input type
	 * @param <O> the output type
	 * @param <E> the error type
	 * @return the transformed iteration
	 */
	public static <I, O, E extends Throwable> Iteration<O, E> transform(final Iteration<I, E> theIteration, final Function<I, O> theFunc) {
		return new TransformIteration<I,O,E>(theFunc, theIteration);
	}

	/**
	 * Apply the function to to transform the exceptions of an inner iterator to a different exception type while iteration
	 * elements are returned unchanged.
	 * 
	 * @param theIteration the iteration
	 * @param theFunc the transforming function for exceptions
	 * @param <T> the iteration element type
	 * @param <IE> the input exception type
	 * @param <OE> the output exception type
	 * @return the transformed iteration
	 */
	public static <T, IE extends Throwable, OE extends Throwable> Iteration<T, OE> transformException(final Iteration<T, IE> theIteration, final Function<IE ,OE> theFunc) {
		return new TransformException<T,IE,OE>(theFunc, theIteration);
	}

	/**
	 * Return the size of the iteration.  The iteration will be exhausted (and closed) when this method returns.
	 * @param theIteration the iteration whose size we are to retrieve
	 * @param <E> the type of exception thrown
	 * @return the number of elements in the iteration
	 * @throws E if there is an error while iterating
	 */
	public static <E extends Exception> int size(final Iteration<?, E> theIteration) throws E {
		try {
			int aSize = 0;
			while (theIteration.hasNext()) {
				aSize++;
				theIteration.next();
			}
			return aSize;
		}
		finally {
			theIteration.close();
		}
	}

	/**
	 * Create an Iteration over a single object
	 * @param theObj the object to iterate over
	 * @param <T> the object type
	 * @param <E> the exception
	 * @return a singleton iterator over the obj
	 */
	public static <T,E extends Throwable> Iteration<T, E> singletonIteration(final T theObj) {
		return new SingletonIteration<T,E>(theObj);
	}

	/**
	 * An Iteration over a single element
	 * @param <T> the element type
	 * @param <E> the exception type
	 * @author Michael Grove
	 * @since 0.6
	 * @version 0.6
	 */
	private static class SingletonIteration<T, E extends Throwable> extends AbstractIteration<T, E> {
		private final T mObj;
		private boolean hasNext = true;

		private SingletonIteration(final T theObj) {
			mObj = theObj;
		}

		protected T computeNext() throws E {
			if (hasNext) {
				hasNext = false;
				return mObj;
			}

			return endOfData();
		}
	}

	/**
	 * An implementation of an Iteration which has a child iteration which it provides a filtered view of via the supplied Predicate.
	 *
	 * @author Michael Grove
	 * @param <T> the type of the iteration
	 * @param <E> the error type
	 * @since 0.3.1
	 * @version 0.3.1
	 */
	private static class FilterIteration<T,E extends Throwable> extends AbstractIteration<T,E> {

		/**
		 * The iteration being filtered
		 */
		private Iteration<T,E> mIter;

		/**
		 * The filter
		 */
		private Predicate<T> mFilter;

		/**
		 * Create a new FilterIteration
		 * @param theIter the iteration to be filtered
		 * @param theFilter the predicate to use for filtering
		 */
		private FilterIteration(final Iteration<T, E> theIter, final Predicate<T> theFilter) {
			mIter = theIter;
			mFilter = theFilter;
		}

		/**
		 * @inheritDoc
		 */
		protected T computeNext() throws E {
			boolean passesFilter = false;
			T aObj = null;

			if (mIter.hasNext()) {
				aObj = mIter.next();

				passesFilter = mFilter.apply(aObj);
				while (!passesFilter && mIter.hasNext()) {
					aObj = mIter.next();
					passesFilter = mFilter.apply(aObj);
				}
			}

			if (passesFilter) {
				return aObj;
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
			mIter.close();
		}
	}

	/**
	 * Create an Iteration that is backed by a standard Java Iterator
	 *
	 * @param <T> the type of the iterator
	 * @author Michael Grove
	 * @since 0.3.3
	 * @version 0.3.
	 */
	private static class IteratorIteration<T, E extends Exception> extends AbstractIteration<T, E> {

		/**
		 * The base iterator
		 */
		private Iterator<T> mIter;

		/**
		 * Create a new IteratorIteration
		 * @param theIter the base iterator
		 */
		private IteratorIteration(final Iterator<T> theIter) {
			mIter = theIter;
		}

		/**
		 * @inheritDoc
		 */
		protected T computeNext() throws E {
			if (mIter.hasNext()) {
				return mIter.next();
			}
			else {
				return endOfData();
			}
		}
	}
}
