package com.clarkparsia.utils.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * A Pipe class which takes the input from a stream and writes it to an output stream as long as the input stream
 * is open.
 */
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
