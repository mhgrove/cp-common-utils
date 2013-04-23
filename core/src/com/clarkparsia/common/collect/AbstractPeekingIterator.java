// Copyright (c) 2010 - 2012 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.clarkparsia.common.collect;

import java.util.NoSuchElementException;

import com.google.common.collect.PeekingIterator;

/**
 * Abstract implementation of {@link PeekingIterator} interface.
 * 
 * @author Evren Sirin
 */
public abstract class AbstractPeekingIterator<T> implements PeekingIterator<T> {
	private T mNext = null;

	protected abstract T fetchNext();
	
	protected void clearNext() {
		mNext = null;
	}

    /**
     * @inheritDoc
     */
	@Override
	public T next() {
		T aResult = peek();
		mNext = null;
		return aResult;
	}

    /**
     * @inheritDoc
     */
	@Override
	public T peek() {
		if (mNext == null) {
			mNext = fetchNext();
		}
		if (mNext == null) {
			throw new NoSuchElementException();
		}
		return mNext;
	}

    /**
     * @inheritDoc
     */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

    /**
     * @inheritDoc
     */
	@Override
	public boolean hasNext() {
		if (mNext == null) {
			mNext = fetchNext();
		}
		return mNext != null;
	}
}
