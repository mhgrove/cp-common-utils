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

package com.complexible.common.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.complexible.common.io.PeekingDataInputStream;
import com.complexible.common.io.PeekingInputStream;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   2.3.1
 * @version 2.3.1
 */
public class MiscIOTests {
    @Test
    public void testPeekingImpls() throws IOException {

        byte[] bytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        InputStream aStream = new ByteArrayInputStream(bytes);
        int i = aStream.read();
        int c = 0;
        while (i != -1) {
            if (bytes[c++] != i) {
                fail();
            }
            i = aStream.read();
        }

        PeekingInputStream aPeekingInputStream = new PeekingInputStream(new ByteArrayInputStream(bytes));
        c = 0;
        while (aPeekingInputStream.peek() != -1) {
            int peek = aPeekingInputStream.peek();
            i = aPeekingInputStream.read();
            if (peek != i) {
                fail();
            }
            if (bytes[c++] != i) {
                fail();
            }
        }

        PeekingDataInputStream aPeekingDataInputStream = new PeekingDataInputStream(new ByteArrayInputStream(bytes));
        c = 0;
        while (aPeekingDataInputStream.peek() != -1) {
            int peek = aPeekingDataInputStream.peek();
            i = aPeekingDataInputStream.readByte();
            if (peek != i) {
                fail();
            }
            if (bytes[c++] != i) {
                fail();
            }
        }
    }
}
