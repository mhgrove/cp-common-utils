package com.clarkparsia.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Arrays;

/**
 * Title: <br>
 * Description: <br>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Sep 11, 2006 2:48:21 PM
 *
 * @author Evren Sirin
 * @author Michael Grove <mhgrove@hotmail.com>
 */
public class MultiIterator<T> implements Iterator<T> {
    private List<Iterator<T>> mIteratorList = new ArrayList<Iterator<T>>();

    private int mIndex = 0;

    private Iterator<T> mCurrIterator;

    public MultiIterator(Iterator<T> theFirst) {
        mCurrIterator = theFirst;
    }

    public MultiIterator(Iterator<T>... theIterList) {
        mCurrIterator = theIterList[0];

		if (theIterList.length > 1) {
			mIteratorList.addAll(Arrays.asList(theIterList).subList(1, theIterList.length));
		}
    }

	public MultiIterator(List<? extends Iterator<T>> theList) {
        mCurrIterator = theList.get(0);

		if (theList.size() > 1) {
			mIteratorList.addAll(theList.subList(1, theList.size()));
		}
	}

	public boolean hasNext() {
        while (!mCurrIterator.hasNext() && mIndex < mIteratorList.size()) {
            mCurrIterator = mIteratorList.get(mIndex++);
		}

        return mCurrIterator.hasNext();
    }

    public T next() {
        if(!hasNext())
            throw new NoSuchElementException("MultiIterator: No Elements Left in any embedded iterator");

        return mCurrIterator.next();
    }


    public void append(Iterator<T> theIter) {
        if (theIter instanceof MultiIterator) {
            mIteratorList.addAll( ((MultiIterator<T>) theIter).mIteratorList );
		}
        else {
			mIteratorList.add(theIter);
		}
    }

    public void remove() {
        mCurrIterator.remove();
    }
}
