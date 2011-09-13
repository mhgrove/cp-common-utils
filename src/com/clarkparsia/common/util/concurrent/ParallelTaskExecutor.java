// Copyright (c) 2010 - 2011 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.clarkparsia.common.util.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * <p>Simple wrapper around an ExecutorService which will ensure that all the tasks run are run with the same thread factory.</p>
 *
 * @author Michael Grove
 * @since 0.4.7
 * @version 0.4.7
 */
public final class ParallelTaskExecutor {
	private final ThreadFactory tf = new ThreadFactoryBuilder().setDaemon(true).setPriority(Thread.NORM_PRIORITY).build();
	private final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*10, tf);

	private final CountDownLatch barrier;

	public ParallelTaskExecutor(int theTasks) {
		barrier = new CountDownLatch(theTasks);
	}

	public ParallelTaskExecutor() {
		barrier = null;
	}

	public <T> Future<T> submit(Callable<T> theCallable) {
		if (barrier != null) {
			return es.submit(new CountdownCallable<T>(theCallable, barrier));
		}
		else {
			return es.submit(theCallable);
		}
	}

	public void waitUntilComplete() throws InterruptedException {
		if (barrier != null) {
			barrier.await();
		}
		else {
			es.shutdown();
			es.awaitTermination(1, TimeUnit.DAYS);
		}
	}

	public void shutdown() {
		es.shutdown();
	}
}
