/*
 * Copyright (c) 2005-2013 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

/**
 * <p>Utility class for Optional</p>
 *
 * @author  Michael Grove
 * @since   2.4
 * @version 3.1.2
 */
public final class Optionals {

    private Optionals() {
        throw new AssertionError();
    }

	public static <T> Predicate<Optional<T>> present() {
		return new Predicate<Optional<T>>() {
			@Override
			public boolean apply(final Optional<T> input) {
				return input.isPresent();
			}
		};
	}

	public static <T> Function<Optional<T>, T> map() {
		return new Function<Optional<T>, T>() {
			@Override
			public T apply(final Optional<T> input) {
				return require(input);
			}
		};
	}

	public static <T> List<T> all(final Collection<Optional<T>> theCollection) {
		List<T> aList = Lists.newArrayList();
		for (Optional<T> aOpt : theCollection) {
			if (aOpt.isPresent()) {
				aList.add(aOpt.get());
			}
		}

		return aList;
	}

    public static <T> T require(final Optional<T> theOptional) throws IllegalArgumentException {
        return require(theOptional, "The required optional value is not present.");
    }

    public static <T> T require(final Optional<T> theOptional, final String theMsg) throws IllegalArgumentException {
        if (theOptional.isPresent()) {
            return theOptional.get();
        }

        throw new IllegalArgumentException(theMsg);
    }

    public static <T, E extends Exception> T require(final Optional<T> theOptional, final Class<E> theExceptionType, final String theMsg) throws E {
        if (theOptional.isPresent()) {
            return theOptional.get();
        }

        try {
            throw theExceptionType.getConstructor(String.class).newInstance(theMsg);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Invalid exception type provided");
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException("Invalid exception type provided");
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Invalid exception type provided");
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Invalid exception type provided");
        }
    }
}
