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

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @version 0
 * @since 0
 */
public class TestPredicates2 {

	@Test
	public final void testWithPrefix() {
		Predicate<String> aPrefixPred = Predicates2.Strings.withPrefix("foo");

		assertTrue(aPrefixPred.apply("foo"));
		assertTrue(aPrefixPred.apply("foobar"));
		assertTrue(aPrefixPred.apply("foofoo"));

		assertFalse(aPrefixPred.apply("barfoo"));
		assertFalse(aPrefixPred.apply(" foo"));
		assertFalse(aPrefixPred.apply("baz"));

		final ImmutableList<String> aList = ImmutableList.of("foo", "foobar", "bar", "baz", "barbaz");
		final ImmutableList<String> aExpected = ImmutableList.of("foo", "foobar");

		assertTrue(Iterables.elementsEqual(aExpected, Iterables.filter(aList, aPrefixPred)));
	}
}
