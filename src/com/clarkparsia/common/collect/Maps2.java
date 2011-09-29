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

package com.clarkparsia.common.collect;

import java.util.Map;
import java.util.Collection;

/**
 * <p>Static utility functions pertaining to {@link Map} instances not already covered in {@link Maps}</p>
 *
 * @author Michael Grove
 * @version 2.0
 * @since 2.0
 */
public final class Maps2 {

	/**
	 * Private constructor
	 */
	private Maps2() {
	}

	/**
	 * Remove all the keys from the map
	 * @param theMap the map
	 * @param theKeysToRemove the keys to remove from the map
	 * @param <K> the key type
	 * @param <V> the value type
	 */
	public static <K, V> void removeKeys(final Map<K, V> theMap, final Collection<K> theKeysToRemove) {
		for (K aKey : theKeysToRemove) {
			theMap.remove(aKey);
		}
	}
}
