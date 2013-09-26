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

package com.complexible.common.base;

import com.google.common.base.Predicate;

/**
 * <p>Utility methods for working with Guava Predicates</p>
 *
 * @author  Michael Grove
 * @since   2.2.1
 * @version 3.0
 */
public final class Predicates2 {

	/**
	 * No instances
	 */
	private Predicates2() {
        throw new AssertionError();
	}

	public static final class Strings {
        public static Predicate<String> contains(final String theString) {
            return new ContainsPredicate(theString);
        }

		public static Predicate<String> startsWith(final String theString) {
			return new StartsWith(theString);
		}

		private static class StartsWith extends StringPredicate {
			private StartsWith(final String thePrefix) {
				super(thePrefix);
			}

			/**
			 * @inheritDoc
			 */
            @Override
			public boolean apply(final String theValue) {
				return theValue.startsWith(mString);
			}
		}

        private static class EndsWith extends StringPredicate {
            private EndsWith(final String theString) {
                super(theString);
            }

            /**
             * @inheritDoc
             */
            @Override
            public boolean apply(final String theValue) {
                return theValue.endsWith(mString);
            }
        }

        private static class ContainsPredicate extends StringPredicate {
            private ContainsPredicate(final String thePrefix) {
                super(thePrefix);
            }

            /**
             * @inheritDoc
             */
            @Override
            public boolean apply(final String theValue) {
                return theValue.contains(mString);
            }
        }

        private static abstract class StringPredicate implements Predicate<String> {
            protected final String mString;

            private StringPredicate(final String theString) {
                super();
                mString = theString;
            }
        }
	}
}
