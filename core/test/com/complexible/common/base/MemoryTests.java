// Copyright (c) 2010 - 2014, Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.base;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * <p></p>
 *
 * @author Michael Grove
 */
public class MemoryTests {
	@Test
	public void testFromReadable() {
		assertEquals(2048, Memory.fromReadable("2K"));
		assertEquals((long)(2.9*Memory.KB), Memory.fromReadable("2.9K"));
		assertEquals((long)(3.1*Memory.MB), Memory.fromReadable("3.1M"));
		assertEquals((long)(15.3*Memory.GB), Memory.fromReadable("15.3G"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFromReadableBadValue() {
		Memory.fromReadable("4F");
	}
}
