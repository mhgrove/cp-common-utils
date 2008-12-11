package com.clarkparsia.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Title: <br>
 * Description: <br>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Sep 11, 2006 2:48:21 PM
 *
 * @author Evren Sirin
 * @author Michael Grove <mhgrove@hotmail.com>
 */
public class MultiIterator implements Iterator {
    private List mIteratorList = new ArrayList();

    private int mIndex = 0;

    private Iterator mCurrIterator;

    public MultiIterator(Iterator theFirst) {
        mCurrIterator = theFirst;
    }

    public MultiIterator(Iterator theFirst, Iterator theSecond) {
        mCurrIterator = theFirst;
        mIteratorList.add(theSecond);
    }

    public boolean hasNext() {
        while (!mCurrIterator.hasNext() && mIndex < mIteratorList.size())
            mCurrIterator = (Iterator) mIteratorList.get(mIndex++);

        return mCurrIterator.hasNext();
    }

    public Object next() {
        if(!hasNext())
            throw new NoSuchElementException("MultiIterator: No Elements Left in any embedded iterator");

        return mCurrIterator.next();
    }


    public void append(Iterator theIter) {
        if (theIter instanceof MultiIterator)
            mIteratorList.addAll( ((MultiIterator) theIter).mIteratorList );
        else mIteratorList.add(theIter);
    }

    public void remove() {
        mCurrIterator.remove();
    }
}
