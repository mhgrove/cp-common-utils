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

import com.google.common.base.Predicate;

/**
 * <p>Utility methods for working with Guava Predicates</p>
 *
 * @author Michael Grove
 * @since 2.2.1
 * @version 2.2.1
 */
public final class Predicates2 {

	/**
	 * No instances
	 */
	private Predicates2() {
	}

	public static final class Strings {

		public static Predicate<String> withPrefix(final String theString) {
			return new PrefixPredicate(theString);
		}

		private static class PrefixPredicate implements Predicate<String> {
			private String mPrefix;

			private PrefixPredicate(final String thePrefix) {
				mPrefix = thePrefix;
			}

			/**
			 * @inheritDoc
			 */
			public boolean apply(final String theValue) {
				return theValue.startsWith(mPrefix);
			}
		}
	}
}
