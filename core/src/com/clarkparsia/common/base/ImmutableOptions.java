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

import java.util.Iterator;

import com.google.common.collect.Iterators;

/**
 * <p>Extends {@link Options} to provide an immutable version.</p>
 *
 * @author Michael Grove
 * @version 2.0
 * @since 2.0
 * @deprecated Use {@link Options#unmodifiable(Options)} instead
 */
public final class ImmutableOptions extends Options {

	private ImmutableOptions(final Options other) {
		super(other);
	}

	/** 
	 * @deprecated Use {@link Options#immutable()} instead
	 */
	public static ImmutableOptions of(final Options theOptions) {
		if (theOptions instanceof ImmutableOptions) {
			return (ImmutableOptions) theOptions;
		}
		else {
			return new ImmutableOptions(theOptions);
		}
	}

	@Override
    public Iterator<Option<Object>> iterator() {
	    return Iterators.unmodifiableIterator(super.iterator());
    }

	/**
	 * @inheritDoc
	 */
	@Override
	public <V> V remove(final Option<V> option) {
		throw new UnsupportedOperationException("Cannot edit immutable options");
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public <V> Options set(final Option<V> option, final V value) {
		throw new UnsupportedOperationException("Cannot edit immutable options");
	}

	@Override
    public Options setAll(Options theOptions) {
		throw new UnsupportedOperationException("Cannot edit immutable options");
    }
	
}
