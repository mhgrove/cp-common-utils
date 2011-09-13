/*
 * Copyright (c) 2005-2011 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.clarkparsia.common.base;

/**
 * Tuple with 2 elements
 *
 * @author Pedro Oliveira
 * @version 2.0
 * @param <K1>
 * @param <K2>
 * @since 2.0
 */
public final class Pair<K1, K2> {

	public final K1 first;

	public final K2 second;

	private Pair(final K1 a, final K2 b) {
		this.first = a;
		this.second = b;
	}

	public static <S, T> Pair<S, T> create(S a, T b) {
		return new Pair<S, T>(a, b);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if ((o instanceof Pair<?, ?>)) {
			Pair<?, ?> other = (Pair<?, ?>) o;
			if (((other.first == null && this.first == null) || (other.first != null && other.first.equals(this.first))) && ((other.second == null && this.second == null) || (other.second != null && other.second.equals(this.second)))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		return (first == null ? 37 : first.hashCode()) ^ (second == null ? 7 : second.hashCode());
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		return "[" + first + " , " + second + "]";
	}
}
