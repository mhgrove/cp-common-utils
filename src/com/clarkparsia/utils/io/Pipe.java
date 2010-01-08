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

package com.clarkparsia.utils.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * A Pipe class which takes the input from a stream and writes it to an output stream as long as the input stream
 * is open.
 */
@Deprecated
public class Pipe implements Runnable {
    private InputStream mIn;
    private OutputStream mOut;

    /**
     * Create a new Pipe
     * @param theIn the input to read from
     * @param theOut the output to write the data from theIn to.
     */
    public Pipe(InputStream theIn, OutputStream theOut) {
        this.mIn = theIn;
        this.mOut = theOut;
    }

    /**
     * Performs the pipe operation.
     * @inheritDoc
     */
    public void run() {
        try {
            int c;
            while ((c = mIn.read()) != -1) {
                mOut.write(c);
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
