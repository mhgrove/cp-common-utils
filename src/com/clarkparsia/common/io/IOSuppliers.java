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

package com.clarkparsia.common.io;

import java.io.IOException;

import com.google.common.base.Supplier;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;

/**
 * <p>Utility class for creating Guava {@link InputSupplier} and {@link OutputSupplier} objects.</p>
 *
 * @author  Michael Grove
 * @since   2.3.1
 * @version 2.3.1
 */
public final class IOSuppliers {

    /**
     * No instances
     */
    private IOSuppliers() {
        throw new AssertionError();
    }

    /**
     * Return a {@link InputSupplier} that will provide the constant value as the input channel
     * @param theObj    the input
     * @param <T>       the input type
     * @return          the new InputSupplier
     */
    public static <T> InputSupplier<T> inputOf(final T theObj) {
        return new InputSupplier<T>() {
            @Override
            public T getInput() throws IOException {
                return theObj;
            }
        };
    }

    /**
     * Return an {@link InputSupplier} which is an adapter to the provided {@link Supplier} which provides the actual input
     *
     * @param theObj    the supplier to provide the input
     * @param <T>       the input type
     * @return          a new InputSupplier
     */
    public static <T> InputSupplier<T> inputOf(final Supplier<T> theObj) {
        return new InputSupplier<T>() {
            @Override
            public T getInput() throws IOException {
                return theObj.get();
            }
        };
    }

    /**
     * Return an {@link OutputSupplier} that will provide the constant value as the output channel
     *
     * @param theObj    the object to serve as output
     * @param <T>       the object type
     * @return          a new OutputSupplier
     */
    public static <T> OutputSupplier<T> outputOf(final T theObj) {
        return new OutputSupplier<T>() {
            @Override
            public T getOutput() throws IOException {
                return theObj;
            }
        };
    }

    /**
     * Return an {@link OutputSupplier} which is an adapter to the provided {@link Supplier} which provides the actual output
     *
     * @param theObj    the supplier to provide the output
     * @param <T>       the output type
     * @return          a new OutputSupplier
     */
    public static <T> OutputSupplier<T> outputOf(final Supplier<T> theObj) {
        return new OutputSupplier<T>() {
            @Override
            public T getOutput() throws IOException {
                return theObj.get();
            }
        };
    }
}
