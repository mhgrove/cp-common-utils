// Copyright (c) 2010 - 2011 -- Clark & Parsia, LLC. <http://www.clarkparsia.com>
// For more information about licensing and copyright of this software, please contact
// inquiries@clarkparsia.com or visit http://stardog.com

package com.complexible.common.collect;

import org.junit.Test;

import static org.junit.Assert.*;
import com.complexible.common.collect.BigArrayList;

/**
 * <p>{@link com.complexible.common.collect.BigArrayList} tests</p>
 *
 * @author Pedro Oliveira (pedro@clarkparsia.com)
 */
public class BigArrayListTest {

    @Test
    public void testBigArray() {

        int splitSize = 100;

        BigArrayList<Integer> list = BigArrayList.create(splitSize);

        assertEquals(list.size(), 0, 0);

        for(int i = 0; i < splitSize; i++) {
            assertTrue(list.add(i));
        }

        assertEquals(list.size(), splitSize, 0);

        try {
            list.get(splitSize + 1);
            fail("Exception expected!");
        }catch(Throwable e) {
        }

        for(int i = 0; i < splitSize; i++) {
            assertEquals(list.get(i), i, 0);
        }

        for(int i = splitSize; i < splitSize*2; i++) {
            assertTrue(list.add(i));
        }

        assertEquals(list.size(), splitSize*2, 0);

        for(int i = 0; i < splitSize*2; i++) {
            assertEquals(list.get(i), i, 0);
        }

        try {
            list.get(splitSize * 2 + 1);
            fail("Exception expected!");
        }catch(Throwable e) {
        }
    }
}
