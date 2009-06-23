//The MIT License
//
// Copyright (c) 2003 Ron Alford, Mike Grove, Bijan Parsia, Evren Sirin
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to
// deal in the Software without restriction, including without limitation the
// rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
// sell copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

package com.clarkparsia.utils;

import java.util.Comparator;

/*
 * Created on Sep 27, 2004
 */

/**
 * This is a comparator to perform a mix of alphabetical+numeric comparison. For
 * example, if there is a list {"test10", "test2", "test150", "test25", "test1"}
 * then what we generally expect from the ordering is the result {"test1",
 * "test2", "test10", "test25", "test150"}. However, standard lexigraphic
 * ordering does not do that and "test10" comes before "test2". This class is
 * provided to overcome that problem. This functionality is useful to sort the
 * benchmark files (like the ones in in DL-benchmark-suite) from smallest to the
 * largest. Comparisons are done on the String values retuned by toString() so
 * care should be taken when this comparator is used to sort arbitrary Java
 * objects.
 * 
 * 
 * @author Evren Sirin
 */
public class AlphaNumericComparator<T> implements Comparator<T> {
    /**
     * A static instantiation of a case sensitive AlphaNumericComparator
     */
    public static final AlphaNumericComparator CASE_SENSITIVE   = new AlphaNumericComparator<Object>(true);
    /**
     * A static instantiation of a case insensitive AlphaNumericComparator
     */
    public static final AlphaNumericComparator CASE_INSENSITIVE = new AlphaNumericComparator<Object>(false);

    private boolean caseSensitive;

    /**
     * Create a case sensitive AlphaNumericComparator
     *
     */
    public AlphaNumericComparator() {
        this.caseSensitive = true;
    }

    /**
     * Create an AlphaNumericComparator
     * 
     * @param caseSensitive if true comparisons are case sensitive
     */
    public AlphaNumericComparator(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

	/**
	 * @inheritDoc
	 */
    public int compare(T o1, T o2) {
        String s1 = o1.toString();
        String s2 = o2.toString();
        int n1 = s1.length(), n2 = s2.length();
        int i1 = 0, i2 = 0;
        for(; i1 < n1 && i2 < n2; i1++, i2++) {
            char c1 = s1.charAt(i1);
            char c2 = s2.charAt(i2);
            if(c1 != c2) {
                if(Character.isDigit(c1) && Character.isDigit(c2)) {
                    int value1 = 0, value2 = 0;
                    while(i1 < n1 && Character.isDigit(c1 = s1.charAt(i1++)))
                        value1 = 10 * value1 + (c1 - '0');
                    while(i2 < n2 && Character.isDigit(c2 = s2.charAt(i2++)))
                        value2 = 10 * value2 + (c2 - '0');
                    if(value1 != value2) 
                        return value1 - value2;
                }
                if(!caseSensitive) {
                    c1 = Character.toUpperCase(c1);
                    c2 = Character.toUpperCase(c2);
                    if(c1 != c2) {
                        c1 = Character.toLowerCase(c1);
                        c2 = Character.toLowerCase(c2);
                        if(c1 != c2) {
                            return c1 - c2;
                        }
                    }
                }
                else {
                    return c1 - c2;
                }
            }
        }

        return n1 - n2;
    }
}