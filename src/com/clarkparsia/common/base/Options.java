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

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author Evren Sirin
 * 
 * @since 2.0
 * @version 2.0
 */
public class Options {
	private final Map<Option<Object>, Object> options;

	private Options() {
		this.options = Maps.newHashMap();
	}

	private Options(Options other) {
		this.options = Maps.newHashMap(other.options);
	}
	
	/**
	 * Creates a copy of the options instance.
	 */
	public static Options copyOf(Options options) {
		return new Options(options);
	}

	/**
	 * Creates a new empty options instance.
	 */
	public static Options create() {
		return new Options();
	}

	/**
	 * Creates a new options instance with the given single mapping.
	 */
	public static <V> Options of(Option<V> key, V value) {
		return create().set(key, value);
	}

	/**
	 * Creates a new options instance with the given two mappings.
	 */
	public static <V1, V2> Options of(Option<V1> key1, V1 value1, Option<V2> key2, V2 value2) {
		return create().set(key1, value1).set(key2, value2);
	}

	/**
	 * Creates a new options instance with the given three mappings.
	 */
	public static <V1, V2, V3> Options of(Option<V1> key1, V1 value1, Option<V2> key2, V2 value2, Option<V3> key3,
	                V3 value3) {
		return create().set(key1, value1).set(key2, value2).set(key3, value3);
	}
	
	/**
	 * Returns <tt>true</tt> if this collection contains a value for the specified option.
	 */
	public <V> boolean contains(Option<V> option) {
		return options.containsKey(option);
	}

	/**
	 * Returns the value associated with the given option or the default value of the option if there is no associated
	 * value. The default value for an option might be <code>null</code> so there are cases this function will return
	 * <code>null</code> values. Must be used with care in autoboxing. 
	 * 
	 * @see Options#is(Option)  
	 */
	@SuppressWarnings("unchecked")
	public <V> V get(Option<V> option) {
		Object value = options.get(option);
		return value != null ? (V) value : option.getDefaultValue();
	}

	/**
	 * Returns the value associated with the given boolean option or the default value of the option if there is no associated
	 * value and the default value is not <code>null</code> or <code>false</code> otherwise.
	 */
	public boolean is(Option<Boolean> option) {
		Boolean value = (Boolean) options.get(option);
		if (value == null)
			value = option.getDefaultValue();
		return value != null && value.booleanValue();
	}
	
	/**
	 * Associate the given value with the given option overriding any previous value.
	 */
	@SuppressWarnings("unchecked")
	public <V> Options set(Option<V> option, V value) {
		options.put((Option<Object>) option, value);
		return this;
	}

	/**
	 * Removes any previous value associated with this option.
	 */
	@SuppressWarnings("unchecked")
	public <V> V remove(Option<V> option) {
		Object oldValue = options.remove(option);
		return oldValue != null ? (V) oldValue : option.getDefaultValue();
	}

}
