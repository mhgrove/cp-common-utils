/*
 * Copyright (c) 2009-2011 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

import com.google.common.base.Function;

/**
 * <p>Additional utility methods for working with Guava Functions</p>
 *
 * @author Michael Grove
 * @version 2.0.1
 * @since 2.0.1
 */
public class Functions2 {

	/**
	 * Return a Function that will cast the parameter of the Function to the specified output type.
	 *
	 * @param theClass the class type to cast to
	 *
	 * @return a function that will perform the cast
	 */
	public static <I, O> Function<I,O> cast(final Class<O> theClass) {
		return new CastFunction<I,O>(theClass);
	}

	private static class CastFunction<I, O> implements Function<I, O> {

		private final Class<O> mClass;

		public CastFunction(final Class<O> theClass) {
			mClass = theClass;
		}

		/**
		 * @inheritDoc
		 */
		public O apply(final I theInput) {
			return mClass.cast(theInput);
		}
	}
}
