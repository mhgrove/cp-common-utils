/*
 * Copyright (c) 2005-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.clarkparsia.utils;

import java.util.Collection;
import java.util.Arrays;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @since 1.0
 * @version 1.1
 */
@Deprecated // use guava
public class PredicateUtil {
	public static <T> Predicate<T> True() {
		return new Predicate<T>() {
			public boolean accept(final T theValue) {
				return false;
			}
		};
	}

	public static <T> Predicate<T> False() {
		return not( (Predicate<T>) True());
	}

	public static <T> Predicate<T> or(Predicate<T>... thePreds) {
		return new Or<T>(thePreds);
	}

	public static <T> Predicate<T> and(Predicate<T>... thePreds) {
		return new And<T>(thePreds);
	}

	public static <T> Predicate<T> not(Predicate<T> thePred) {
		return new Not<T>(thePred);
	}

	private static class Not<T> extends Unary<T> {
		private Not(Predicate<T> thePredicate) {
			super(thePredicate);
		}

		public boolean accept(final T theValue) {
			return !getPredicate().accept(theValue);
		}
	}

	private static class And<T> extends Op<T> {
		private And(Predicate<T>... thePredicates) {
			super(thePredicates);
		}

		public boolean accept(final T theValue) {
			for (Predicate<T> aPredicate : getPredicates()) {
				if (!aPredicate.accept(theValue)) {
					return false;
				}
			}

			return true;
		}
	}

	private static class Or<T> extends Op<T> {
		private Or(Predicate<T>... thePredicates) {
			super(thePredicates);
		}

		public boolean accept(final T theValue) {
			for (Predicate<T> aPredicate : getPredicates()) {
				if (aPredicate.accept(theValue)) {
					return true;
				}
			}

			return false;
		}
	}

	private static abstract class Op<T> implements Predicate<T> {
		private Collection<Predicate<T>> mPredicates;

		private Op(Predicate<T>... thePredicates) {
			this(Arrays.asList(thePredicates));
		}

		private Op(Collection<Predicate<T>> thePredicates) {
			mPredicates = thePredicates;
		}

		protected Collection<Predicate<T>> getPredicates() {
			return mPredicates;
		}
	}

	private static abstract class Unary<T> implements Predicate<T> {
		private Predicate<T> mPredicate;

		private Unary(Predicate<T> thePredicate) {
			mPredicate = thePredicate;
		}

		protected Predicate<T> getPredicate() {
			return mPredicate;
		}
	}
}
