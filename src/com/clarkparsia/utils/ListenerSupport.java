/*
 * Copyright (c) 2005-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.clarkparsia.utils;

import java.util.EventListener;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.Iterator;

/**
 * <p>Base class for a collection of listeners</p>
 *
 * @author Michael Grove
 * @since 1.0
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