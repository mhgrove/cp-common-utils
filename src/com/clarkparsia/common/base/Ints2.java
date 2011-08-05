package com.clarkparsia.common.base;

import java.util.Iterator;

/**
 * <p>Utility methods for working with <code>int</code>s that are not already found in Ints, Integer, or Arrays.</p>
 *
 * @author Michael Grove
 * @version 2.0
 * @since 2.0
 */
public class Ints2 {

	/**
	 * <p>Provides a List of integers starting at the given index and going to the given end index (inclusive).  Useful
	 * if you want to do something X amount of times:</p>
	 * <code>
	 *   for (int aNum : range(1, 1000)) {
	 *       // do work
	 *   }
	 * </code>
	 * @param theStart the start index
	 * @param theEnd the end index
	 * @return the range of numbers from start to end (inclusive)
	 */
	public static Iterable<Integer> range(final int theStart, final int theEnd) {
		return new Iterable<Integer>() {
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>(){
					int index = theStart;
					public boolean hasNext() {
						return index <= theEnd;
					}

					public Integer next() {
						return index++;
					}

					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}
