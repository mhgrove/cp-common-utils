// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils;

import java.util.Comparator;

public class ToStringComparator<T> implements Comparator<T> {

	/**
	 * @inheritDoc
	 */
    public int compare(T one, T two) {
        return one.toString().toLowerCase().compareTo(two.toString().toLowerCase());
    }
}
