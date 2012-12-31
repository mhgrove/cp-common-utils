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

package com.clarkparsia.common.collect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * <p>A composite iterator</p>
 *
 * @author Michael Grove
 * @author Evren Sirin
 * @since 1.0
 * @version 2.0
 */
public final class MultiIterator<T> implements Iterator<T> {

	/**
	 * The list of iterators that make up this MultiIterator
	 */
    private List<Iterator<T>> mIteratorList = new ArrayList<Iterator<T>>();

	/**
	 * The index of the current iterator
	 */
    private int mIndex = 0;

	/**
	 * The current iterator
	 */
    private Iterator<T> mCurrIterator;

	/**
	 * Create a new MultiIterator
	 * @param theFirst the iterator backing this one
	 */
    public MultiIterator(Iterator<T> theFirst) {
        mCurrIterator = theFirst;
    }

	/**
	 * Create a new MultiIterator
	 * @param theIterList the array of iterators to back this one
	 */
    public MultiIterator(Iterator<T>... theIterList) {
        mCurrIterator = theIterList[0];

		if (theIterList.length > 1) {
			mIteratorList.addAll(Arrays.asList(theIterList).subList(1, theIterList.length));
		}
    }

	/**
	 * Create a new MultiIterator
	 * @param theIterList the array of iterators to back this one
	 */
    public MultiIterator(Iterable<T>... theIterList) {
        mCurrIterator = theIterList[0].iterator();

		if (theIterList.length > 1) {
			for (Iterable<T> aIterable : Arrays.asList(theIterList).subList(1, theIterList.length)) {
				mIteratorList.add(aIterable.iterator());
			}
		}
    }

	/**
	 * Create a new MultiIterator
	 * @param theList the list of iterators backing this one
	 */
	public MultiIterator(List<? extends Iterator<T>> theList) {
        mCurrIterator = theList.get(0);

		if (theList.size() > 1) {
			mIteratorList.addAll(theList.subList(1, theList.size()));
		}
	}

	/**
	 * @inheritDoc
	 */
	public boolean hasNext() {
        while (!mCurrIterator.hasNext() && mIndex < mIteratorList.size()) {
            mCurrIterator = mIteratorList.get(mIndex++);
		}

        return mCurrIterator.hasNext();
    }

	/**
	 * @inheritDoc
	 */
    public T next() {
        if(!hasNext())
            throw new NoSuchElementException("MultiIterator: No Elements Left in any embedded iterator");

        return mCurrIterator.next();
    }

	/**
	 * Append the Iterator to the end of the MultiIterator
	 * @param theIter the iterator to append
	 */
    public void append(Iterator<T> theIter) {
		if (theIter == null || !hasNext()) {
			return;
		}

        if (theIter instanceof MultiIterator) {
            mIteratorList.addAll( ((MultiIterator<T>) theIter).mIteratorList );
		}
        else {
			mIteratorList.add(theIter);
		}
    }

	/**
	 * @inheritDoc
	 */
    public void remove() {
        mCurrIterator.remove();
    }
}
