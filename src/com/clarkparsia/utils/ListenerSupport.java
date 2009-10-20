// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils;

import java.util.EventListener;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Iterator;

/**
 * Title: ListenerSupport<br/>
 * Description: Base class for a collection of listeners<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Dec 23, 2008 2:05:17 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class ListenerSupport<T extends EventListener> implements Iterable<T> {

    /**
     * The listeners
     */
    private Set<T> mListeners = Collections.synchronizedSet(new HashSet<T>());

    /**
     * Adds the specified listener to the list
     * @param theListener the listener to add
     */
    public void addListener(T theListener) {
        mListeners.add(theListener);
    }

    /**
     * Removes the specified listener from the list
     * @param theListener the listener to remove
     */
    public void removeListener(T theListener) {
        mListeners.remove(theListener);
    }

    /**
     * Return the registered listeners.
     * @return the listeners
     */
    protected Set<T> getListeners() {
        return Collections.unmodifiableSet(mListeners);
    }

    /**
     * @inheritDoc
     */
    public Iterator<T> iterator() {
        return getListeners().iterator();
    }
}
