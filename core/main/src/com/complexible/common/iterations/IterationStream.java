package com.complexible.common.iterations;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

import com.google.common.base.Throwables;

/**
 * <p>Class which wraps a {@link Iteration} as a {@link Spliterator}.  Resulting iterator is
 * defined to be {@link Spliterator#IMMUTABLE}, {@link Spliterator#NONNULL}, and {@link Spliterator#SORTED}.</p>
 *
 * <p>Note that this does close the underlying {@link Iteration}, but only when iteration has completed, ie
 * only once you've iterated over all of the elements.  So if something like {@code findFirst} is used, and only
 * half the elements are iterated over, the underlying {@code Iteration} will not be closed.  So care should be taken
 * that the stream backed by this iterator is always closed.</p>
 *
 * <p>If an exception is thrown while iterating, if that exception is not a {@link RuntimeException} then it is
 * wrapped as a {@link RuntimeException} and re-thrown.</p>
 *
 * @author  Michael Grove
 * @since   4.0
 * @version 4.0
 */
public final class IterationStream<T> extends Spliterators.AbstractSpliterator<T> {
	private final Iteration<T, ? extends Exception> mIteration;

	public <I extends Iteration<T, ? extends Exception>> IterationStream(final I theIter) {
		super(Long.MAX_VALUE, Spliterator.IMMUTABLE | Spliterator.NONNULL);

		mIteration = theIter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean tryAdvance(final Consumer<? super T> theAction) {
		boolean aHasNext = false;
		try {
			aHasNext = mIteration.hasNext();

			if (aHasNext) {
				theAction.accept(mIteration.next());
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			Throwables.propagateIfInstanceOf(e, RuntimeException.class);

			throw Throwables.propagate(e);
		}
		finally {
			if (!aHasNext) {
				Iterations.closeQuietly(mIteration);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEachRemaining(final Consumer<? super T> theAction) {
		try {
			while (mIteration.hasNext()) {
				theAction.accept(mIteration.next());
			}
		}
		catch (Exception e) {
			Throwables.propagateIfInstanceOf(e, RuntimeException.class);

			throw Throwables.propagate(e);
		}
		finally {
			try {
				mIteration.close();
			}
			catch (Exception e) {
				Throwables.propagateIfInstanceOf(e, RuntimeException.class);

				throw Throwables.propagate(e);
			}
		}
	}
}
