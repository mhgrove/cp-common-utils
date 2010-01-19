package com.clarkparsia.utils;

/**
 * <p>Collection of utility methods and classes for using the {@link Function} interface.</p>
 *
 * @author Michael Grove
 */
public class FunctionUtil {

	/**
	 * Return a Function that is a composition of the first function followed by the second
	 * @param theFirst the first function
	 * @param theOther the second function
	 * @param <I> the input of the first function
	 * @param <T> the output of the first function, and the input of the second
	 * @param <O> the output of the second
	 * @return A function which takes the input of the first function, and returns output of the second
	 */
	public static <I, T, O> Function<I, O> compose(final Function<I, T> theFirst, final Function<T, O> theOther) {
		return new Function<I, O>() {
			public O apply(final I theIn) {
				return theOther.apply(theFirst.apply(theIn));
			}
		};
	}

	/**
	 * A function that takes an object of one type and casts it to another.
	 * <code><pre>
	 *   Collection&lt;Integer&gt; aNewCollection = transform(aCollection, new Cast&lt;Number, Integer&gt;());
	 * </pre></code>
	 * @param <I> the input type
	 * @param <O> the output type
	 */
	public static class Cast<I, O> implements Function<I, O> {
		private Class<O> mClass;

		public Cast(final Class<O> theClass) {
			mClass = theClass;
		}

		public O apply(final I theIn) {
			return mClass.cast(theIn);
		}
	}
}
