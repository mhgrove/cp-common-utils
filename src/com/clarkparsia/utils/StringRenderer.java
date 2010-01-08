/*
 * Copyright (c) 2005-2010 Clark & Parsia, LLC. <http://www.clarkparsia.com>
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

package com.clarkparsia.utils;

/**
 * <p>An interface for a class which renders any given object into a string representation. Intended for use
 * when the default representation returned by an objects toString() method is unsuitable for the operation.</p>
 *
 * @author Michael Grove
 */
public interface StringRenderer<T> {

    /**
     * Renders the object into a String value.  Similar to the toString method.
     * @param theObject the object to render
     * @return a string representation of the object.
     */
    public String render(T theObject);
}
