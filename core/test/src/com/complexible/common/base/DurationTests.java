/*
 * Copyright (c) 2005-2013 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.complexible.common.base.Durations;

/**
 * 
 * @author Evren Sirin
 */
public class DurationTests {
	@Test
	public void basicTest() {
		roundTrip("2ms", 2 , MILLISECONDS);
		roundTrip("1000ms", 1000, MILLISECONDS, "1s");
		roundTrip("1999ms", 1999, MILLISECONDS);
		roundTrip("2000ms", 2000, MILLISECONDS, "2s");
		roundTrip("24000ms", 24000, MILLISECONDS, "24s");
		
		roundTrip("0s", 0, SECONDS, "Oms");
		roundTrip("30s", 30 , SECONDS);
		roundTrip("90s", 90, SECONDS);
		roundTrip("240s", 240, SECONDS, "4m");
		roundTrip("7200s", 7200, SECONDS, "2h");
		
		roundTrip("1m", 1, MINUTES);
		roundTrip("22m", 22, MINUTES);
		roundTrip("60m", 60, MINUTES, "1h");
		
		roundTrip("2h", 2, HOURS);
		roundTrip("20h", 20, HOURS);
		roundTrip("72h", 72, HOURS, "3d");
		
		roundTrip("2d", 2, DAYS);
		roundTrip("1500d", 1500, DAYS);
	}	
	
	private static void roundTrip(String theInput, long theExpectedValue, TimeUnit theExpectedUnit) {
		roundTrip(theInput, theExpectedValue, theExpectedUnit, theInput);
	}

	private static void roundTrip(String theInput, long theExpectedValue, TimeUnit theExpectedUnit, String theExpectedOutput) {
		roundTripWithDefault(theInput, theExpectedValue, theExpectedUnit, theExpectedOutput);
		roundTripWithDefault(theInput.toUpperCase(), theExpectedValue, theExpectedUnit, theExpectedOutput);
		roundTripWithUnit(theInput, theExpectedValue, theExpectedUnit, theExpectedOutput);
	}
	
	private static void roundTripWithDefault(String theInput, long theExpectedValue, TimeUnit theExpectedUnit, String theExpectedOutput) {
		long aActualMillis = Durations.parse(theInput);
		assertEquals(theExpectedUnit.toMillis(theExpectedValue), aActualMillis);
		
		String aActualOutput = Durations.readable(aActualMillis);
		assertEquals(theExpectedOutput, aActualOutput);		
	}
	
	private static void roundTripWithUnit(String theInput, long theExpectedValue, TimeUnit theExpectedUnit, String theExpectedOutput) {
		long aActualValue = Durations.parse(theInput, theExpectedUnit);
		assertEquals(theExpectedValue, aActualValue);
		
		String aActualOutput = Durations.readable(aActualValue, theExpectedUnit);
		assertEquals(theExpectedOutput, aActualOutput);		
	}

	@Test
	public void parseErrorTest() {
		parseErrorTest("");
		parseErrorTest("0");
		parseErrorTest("1");
		
		parseErrorTest("m");
		parseErrorTest("ms");
		
		parseErrorTest("1x");
		parseErrorTest("5min");
		parseErrorTest("1nm");
		parseErrorTest("1ns");
		
		parseErrorTest("1 m");
		parseErrorTest("5 s");		
		
		parseErrorTest("-1s");
		parseErrorTest("1m1");
	}

	private void parseErrorTest(final String theInvalidInput) {
		try {
            long theValue = Durations.parse(theInvalidInput);
            fail("Parse was not expected to return a value: " + theValue);			
		}
		catch (IllegalArgumentException e) {
			//expected
		}
	}
}
