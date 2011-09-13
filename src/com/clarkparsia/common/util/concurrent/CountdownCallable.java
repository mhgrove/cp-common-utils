package com.clarkparsia.common.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Callable;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @since 0.4.7
 * @version 0.4.10
 */
public final class CountdownCallable<T> extends ForwardingCallable<T> {

	private final CountDownLatch mCountDown;

	public CountdownCallable(final Callable<T> theCallable, final CountDownLatch theCountdown) {
		super(theCallable);
		mCountDown = theCountdown;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public T call() throws Exception{
		try {
			return super.call();
		}
		finally {
			synchronized (mCountDown) {
				mCountDown.countDown();
			}
		}
	}
}
