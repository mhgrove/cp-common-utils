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

import java.util.Collection;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @version 0
 * @since 0
 */
public class TestFunctions2 {
	@Test
	public void testCast() {
		Collection c = Sets.newHashSet();

		// this assertion would always be true, c is a Set, but are just doing it to be sure
		// this should complete w/o cast exception and c should still be a Set
		assertTrue(Functions2.cast(Set.class).apply(c) instanceof Set);

		try {
			Functions2.cast(String.class).apply(c);
			fail("Should not be able to cast a Collection as a String");
		}
		catch (ClassCastException e) {
			// expected
		}
	}

	@Test
	public void testSubstring() {
		Function<String, String> aFunc = Functions2.Strings.substring(3);

		assertEquals("bar", aFunc.apply("foobar"));
		assertEquals("", aFunc.apply("abc"));

		try {
			aFunc.apply("ab");
		}
		catch (Exception e) {
			// expected
		}

		final ImmutableList<String> aList = ImmutableList.of("test1", "test11", "test111", "test1111", "test11111");
		final ImmutableList<String> aExpected = ImmutableList.of("t1", "t11", "t111", "t1111", "t11111");

		assertTrue(Iterables.elementsEqual(aExpected, Iterables.transform(aList, aFunc)));

		aFunc = Functions2.Strings.substringAfter(".");

		assertEquals("bar", aFunc.apply("foo.bar"));
		assertEquals("foo_bar", aFunc.apply("foo_bar"));
	}
}
