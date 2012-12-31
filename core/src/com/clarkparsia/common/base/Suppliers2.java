/*
 * Copyright (c) 2005-2012 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

import com.google.common.base.Supplier;

/**
 * <p>Additional methods for {@link Supplier Suppliers}.</p>
 *
 * @author	Michael Grove
 * @since	1.1
 * @version	1.1
 *
 * @see com.google.common.base.Suppliers
 */
public final class Suppliers2 {

	/**
	 * No instances
	 */
	private Suppliers2() {
	}

	/**
	 * Returns a {@link Supplier} that will create new instances of the provided class.  The class <b>MUST</b> have
	 * a no-args constructor, otherwise invocations of {@link Supplier#get} will throw {@link RuntimeException} due
	 * to the Java Reflect errors arising from invoking <code>Class.newInstance()</code> when there is not a default
	 * constructor.
	 *
	 * @param theClass	the class to create instances of
	 * @param <T>		the class type
	 * @return			a Supplier for creating new instances of the specified tyep
	 */
	public static <T> Supplier<T> ofClass(final Class<T> theClass) {
		return new ClassSupplier<T>(theClass);
	}

	private static final class ClassSupplier<T> implements Supplier<T> {
		private final Class<T> mClass;

		private ClassSupplier(final Class<T> theClass) {
			mClass = theClass;
		}

		/**
		 * @inheritDoc
		 */
		@Override
		public T get() {
			try {
				return mClass.newInstance();
			}
			catch (InstantiationException e) {
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
