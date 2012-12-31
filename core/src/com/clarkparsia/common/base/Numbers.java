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
 * 
 * @author Evren Sirin
 * @since 2.0
 * @version 2.0
 */
public final class Numbers {
	/**
	 * Constant field for thousand.
	 */
	public static final long THOUSAND = 1000;
	
	/**
	 * Short-name constant field for thousand (1000).
	 */
	public static final long K = THOUSAND;

	/**
	 * Constant field for million.
	 */
	public static final long MILLION = 1000000;
	
	/**
	 * Short-name constant field for million.
	 */
	public static final long M = MILLION;

	/**
	 * Constant field for billion.
	 */
	public static final long BILLION = 1000000000;
	
	private Numbers() {
	}

	/**
	 * Returns a human-readable representation of numbers. This function is similar to {@link Memory#readable(long)} in
	 * spirit but works with powers of 10 (not 2), supports only two suffixes ('K' for thousands and 'M' for millions),
	 * and always prints one fraction digit for numbers greater then 1M. 
	 * 
	 * <pre>
	 *  500 = 500
	 *  1000 = 1.0K
	 *  6700 = 6.7K
	 *  10000 = 10K
	 *  17500 = 18K
	 *  940000 = 940K
	 *  1120000 = 1.1M
	 *  4600000 = 4.6M
	 *  12000000 = 12.0M
	 *  130000000 = 130.0M
	 *  1240000000 = 1240.0M
	 * </pre>
	 */
	public static String readable(long number) {
		if (number < THOUSAND)
			return String.valueOf(number);
		else if (number < 10 * THOUSAND)
			return String.format("%1.1fK", number / 1E3);
		else if (number < MILLION)
			return String.format("%1.0fK", number / 1E3);
		else
			return String.format("%1.1fM", number / 1E6);
	}

	public static String readable(double number) {
		return String.format("%1.3f", number);
	}

	/**
	 * Return an {@link Integer} if given value long is less than <code>Integer.MAX_VALUE</code> or a {@link Long}
	 * otherwise.
	 */
	public static Number valueOf(long value) {
		return (value < Integer.MAX_VALUE) ? (Number) Integer.valueOf((int) value) : (Number) Long.valueOf(value);
	}
}
