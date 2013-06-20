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

package com.complexible.common.base;

import java.io.IOException;

import com.complexible.common.base.Optionals;
import com.google.common.base.Optional;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   2.4
 * @version 2.4
 */
public class TestOptionals {
    @Test
    public void testRequireWithValue() {
        Optionals.require(Optional.of(""));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRequireWithoutValue() {
        Optionals.require(Optional.absent());
    }

    @Test
    public void testRequireWithoutValueAndMessage() {
        final String aMsg = "custom error message";
        try {
            Optionals.require(Optional.absent(), aMsg);
            fail("Should not have gotten here");
        }
        catch (IllegalArgumentException e) {
            assertEquals(aMsg, e.getMessage());
        }
    }

    @Test
    public void testRequireWithCustomException() {
        final String aMsg = "custom error message";
        try {
            Optionals.require(Optional.absent(), IOException.class, aMsg);
            fail("Should not have gotten here");
        }
        catch (IOException e) {
            assertEquals(aMsg, e.getMessage());
        }
    }
}
