package com.clarkparsia.common.util.concurrent;

import java.util.concurrent.Callable;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @since 0.4.7
 * @version 0.4.7
 */
public class ForwardingCallable<T> implements Callable<T> {
	private Callable<T> mCallable;

	public ForwardingCallable(final Callable<T> theCallable) {
		mCallable = theCallable;
	}

	/**
	 * @inheritDoc
	 */
	public T call() throws Exception {
		return mCallable.call();
	}
}
