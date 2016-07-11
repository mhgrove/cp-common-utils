// Copyright (c) 2010 - 2014, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pavel Klinov
 */
public class NumberTests {
	@Test
	public void testFromReadable() {
		assertEquals(2000, Numbers.fromReadable("2K"));
		assertEquals(2900, Numbers.fromReadable("2.9K"));
		assertEquals(3100000, Numbers.fromReadable("3.1M"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFromReadableBadValue() {
		Numbers.fromReadable("4F");
	}
}
