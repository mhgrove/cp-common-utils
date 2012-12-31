// Copyright (c) 2010 - 2011 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.clarkparsia.common.util.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.collect.Lists;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.Queue;

/**
 * <p>Simple wrapper around an ExecutorService which will ensure that all the tasks run are run with the same thread factory.</p>
 *
 * @author Michael Grove
 * @author Evren Sirin
 *
 * @since 0.4.7
 * @version 0.4.7
 */
public final class ParallelTaskExecutor {
	private final ThreadFactory mThreadFactory = new ThreadFactoryBuilder().setDaemon(true).setPriority(Thread.NORM_PRIORITY).build();
	private final ExecutorService mExecService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*10, mThreadFactory);

	private final Queue<Future<?>> mFutures = Lists.newLinkedList();

	public ParallelTaskExecutor() {
	}

	public <T> Future<T> submit(Callable<T> theCallable) {
		Future<T> aFuture = mExecService.submit(theCallable);
		mFutures.add(aFuture);
		return aFuture;
	}

	/**
	 * Checks if all the submitted tasks are done and returns true if there is no running task. If an error was
	 * thrown during the execution of any submitted task, it will be thrown.
	 */
	public boolean isDone() throws InterruptedException, ExecutionException {
		while (!mFutures.isEmpty()) {
			boolean isDone = mFutures.peek().isDone();
			if (!isDone) {
				return false;
			}
			else {
				mFutures.remove().get();
			}
		}

		return true;
	}

	/**
	 * Waits until the completion of all submitted tasks and throws an exception if an execution or interrupted
	 * exception occurred during the execution of any submitted task. The exception thrown here will be exactly the same
	 * one as if {@link Future#get()} function has been called. Thus, if this exception has already been handled
	 * externally it can be ignored here. There may be multiple exceptions that occurred during the execution of tasks
	 * but this function will throw only the first exception ignored.
	 */
	public void waitUntilComplete() throws ExecutionException, InterruptedException {
		Exception aException = null;
		while (!mFutures.isEmpty()) {
			try {
				mFutures.remove().get();
			}
			catch (ExecutionException e) {
				if (aException == null) {
					aException = e;
				}
			}
			catch (InterruptedException e) {
				if (aException == null) {
					aException = e;
				}
			}
		}

		if (aException != null) {
			if (aException instanceof ExecutionException)
				throw (ExecutionException) aException;
			if (aException instanceof InterruptedException)
				throw (InterruptedException) aException;
		}
	}

	/**
	 * Shut down the executor and clean up its resources
	 */
	public void shutdown() {
		mExecService.shutdown();
		mFutures.clear();
	}
}
