/*
 * Copyright (c) 2005-2012 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.complexible.common.base;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.complexible.common.util.EnhancedProperties;

/**
 * <p></p>
 *
 * @author  Evren Sirin
 * @since   2.4.2
 * @version 2.4.2
 */
public class PropertiesTests {
	private EnhancedProperties props;
	
	@Before
	public void before() {
		props = new EnhancedProperties();
	}
	
    @Test
    public void testPropertyAsInt() throws IOException {
    	assertEquals(5, props.getPropertyAsInt("x", 5));    	
    	props.setProperty("x", "1");
    	assertEquals(1, props.getPropertyAsInt("x"));
    	assertEquals(1, props.getPropertyAsInt("x", 5));
    }
	
    @Test
    public void testPropertyAsLong() throws IOException {
    	assertEquals(Long.MAX_VALUE, props.getPropertyAsLong("x", Long.MAX_VALUE));    	
    	props.setProperty("x", String.valueOf(Long.MIN_VALUE));
    	assertEquals(Long.MIN_VALUE, props.getPropertyAsLong("x"));
    	assertEquals(Long.MIN_VALUE, props.getPropertyAsLong("x", Long.MAX_VALUE));
    }
	
    @Test
    public void testPropertyAsFloat() throws IOException {
    	assertEquals(5.2f, props.getPropertyAsFloat("x", 5.2f), Float.MIN_VALUE);    	
    	props.setProperty("x", "1.3");
    	assertEquals(1.3f, props.getPropertyAsFloat("x"), Float.MIN_VALUE);
    	assertEquals(1.3f, props.getPropertyAsFloat("x", 5.2f), Float.MIN_VALUE);
    }
	
    @Test
    public void testPropertyAsDouble() throws IOException {
    	assertEquals(5.2, props.getPropertyAsDouble("x", 5.2), Double.MIN_VALUE);    	
    	props.setProperty("x", "1.3");
    	assertEquals(1.3, props.getPropertyAsDouble("x"), Double.MIN_VALUE);
    	assertEquals(1.3, props.getPropertyAsDouble("x", 5.2), Double.MIN_VALUE);
    }
	
    @Test
    public void testPropertyAsBoolean() throws IOException {
    	assertEquals(false, props.getPropertyAsBoolean("x",false));    	
    	props.setProperty("x", "true");
    	assertEquals(true, props.getPropertyAsBoolean("x"));
    	assertEquals(true, props.getPropertyAsBoolean("x", false));
    }
	
    @Test
    public void testVars() throws IOException {
    	props.setProperty("x", "x = ${y}");
    	props.setProperty("y", "y");
    	assertEquals("x = y", props.getProperty("x"));
    }
	
    @Test
    public void testNestedVars() throws IOException {
    	props.setProperty("x", "x = ${y}");
    	props.setProperty("y", "${z} + ${t}");
    	props.setProperty("z", "z");
    	props.setProperty("t", "3");
    	assertEquals("x = z + 3", props.getProperty("x"));
    }
	
    @Test
    public void testShortValues() throws IOException {
    	props.setProperty("fullPath", "${dirName}/${fileName}.txt");
    	props.setProperty("dirName", "test");
    	props.setProperty("fileName", "data");
    	assertEquals("test/data.txt", props.getProperty("fullPath"));
    }
	
    @Test
    public void testList() throws IOException {
    	props.setProperty("colors", "${foreground}, ${background}, red , blue, green");
    	props.setProperty("foreground", "white");
    	props.setProperty("background", "black");
    	assertEquals(Arrays.asList("white", "black", "red", "blue", "green"), props.getPropertyAsList("colors"));
    }
	
}
