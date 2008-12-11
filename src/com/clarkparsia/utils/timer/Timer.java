//The MIT License
//
// Copyright (c) 2003 Ron Alford, Mike Grove, Bijan Parsia, Evren Sirin
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to
// deal in the Software without restriction, including without limitation the
// rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
// sell copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

/*
 * Created on Sep 23, 2004
 */
package com.clarkparsia.utils.timer;

/**
 * <p>Class used to keep track how much time is spent for a specific operation. Timers are
 * primarily used to display info about performance. A timer is started at the beginning
 * of a function and is stopped at the end of that function (special care needed when
 * there are multiple return commands in a function because the status of unstopped timers
 * is undefined). A timer also stores how many times the timer has been started so average
 * time spent in a function can be computed.</p> 
 * 
 * <p>When a timer is used in a recursive function it will typically be started multiple times.
 * Timer class will only measure the time spent in the first call. This is done by counting 
 * how many times a timer is started and time spent is computed only when the number of 
 * stop() calls evens out the start() calls. It
 * is the programmer's responsibility to make sure each start() is
 * stopped by a stop() call.</p> 
 * 
 * <p>Each timer may be associated with a timeout limit. This means that time spent between 
 * start() and stop() calls should be less than the 
 * timeout specified. Timeouts will only be checked when check() function
 * is called. If check() function is not called setting timeouts has no
 * effect. It is up to the programmer to decide when and how many times a timer will be checked.</p> 
 * 
 * <p>There may be a dependency between timers. For example, classification, realization and entailment
 * operations all use consistency checks. If something goes wrong inside a consistency check and
 * that operation does not finish in a reasonable time, the timeout on the dependant timer may 
 * expire. To handle such cases, a timer may be associated with a dependant timer so every time
 * a timer is checked for a timeout,  its dependant timer will also be checked. Normally, we would 
 * like to associate many dependants with a timer but for efficency reasons (looping over an array
 * each time is expensive) each timer is allowed to have only one dependant. </p> 
 *  
 * <p>{@link Timers Timers} class stores a set of timers and provides functions to start, stop and
 * check timers.  </p> 
 * 
 * @see Timers
 * @author Evren Sirin
 */
public class Timer {
	public final static long NOT_STARTED = -1;
	public final static long NO_TIMEOUT  = 0;
	
	private String name; 		// name to identify what we are timing 
	private long totalTime;		// total time that has elapsed when the timer was running		
	private long startTime;		// last time timer was started
	private long count;			// number of times the timer was started and stopped
	private long startCount;	// if we are timing recursive functions timer may be started 
								// multiple times. we only want to measure time spent in the 
								// upper most function call so we need to discard other starts
	private long timeout;		// Point at which a call to check throws an exception
	private long lastTime;		// time that has elapsed between last start()-stop() period
	
	private Timer dependant;	// timers that depends on this timer

	/**
	 * Create a timer with no dependant.
	 * 
	 * @param name
	 */
	public Timer(String name) {
	    this(name, null);
	}
	
	/**
	 * Create a timer that has the specified dependant timer.
	 * 
	 * @param name
	 * @param dependant
	 */
	public Timer(String name, Timer dependant) {
		this.name = name;
		this.dependant = dependant;

		timeout = NO_TIMEOUT;
		reset();
	}
	
	/**
	 * Update the total time elapsed and number of counts by by adding the values from another
	 * timer. This is especially useful if we are running
	 * @param timer
	 */
	public void add( Timer timer ) {
	    totalTime += timer.totalTime;
	    count += timer.count;
	}
	
	/**
	 * Start time timer by recording the time this function is called. If timer is running when
	 * this function is called time is not recorded and only an internal counter is updated. 
	 * 
	 *
	 */
	public void start() {
		if(startCount == 0) { 
			startTime = System.currentTimeMillis();
		}
		
		startCount++;
	}

    
    /**
     * Stop the timer, increment the count and update the total time spent. If timer has been 
     * started multiple times this function will only decrement the internal counter. Time 
     * information is updated only when all starts are evened out by stops.
     * 
     * @return Return the total time spent after last start() or -1 if timer is still running
	 */
	public void stop() {
		if( !isStarted() ) {
			// BUGBUG - Should throw an exception?
			throw new UnsupportedOperationException("Cannot stop timer '"+name+"' which is not started!");				
		}
		
		// Decrement start counter.
		startCount--;
		
		if ( !isStarted() ) {			
		    lastTime = System.currentTimeMillis() - startTime;
			totalTime += lastTime;
			startTime = NOT_STARTED;						
			count++;
//            return lastTime;
		}
        
//        return -1;
	}
		
	/**
	 * Reset all the counters associated with this timer. After this function call it will be like
	 * timer has never been used.
	 *
	 */
	public void reset() {
		totalTime = 0;
		startTime = NOT_STARTED;
		startCount = 0; 
		count = 0;
	}
	
	/**
	 * Check if the elapsed time is greater than the timeout limit and throw a TimeoutException
	 * if that is the case. Check the parent timer if there is one.
	 * 
	 * @throws TimeoutException
	 */
	public void check() throws TimeoutException {
		long elapsed = getElapsed();
		
		if (timeout != NO_TIMEOUT && elapsed > timeout) {
			throw new TimeoutException("Running time of "+name+" exceeded timeout of "+timeout);
		}
		
		if (dependant != null)
		    dependant.check();		
	}	
	
	/**
	 * Return true if timer has been started with a {@link #start()} call but not has been 
	 * stopped with a {@link #stop()} call. 
	 * 
	 * @return
	 */
	public boolean isStarted() {
		return (startCount > 0);
	}
	
	/**
	 * Return the name of this timer.
	 * 
	 * @return
	 */
	public String getName() {
	    return name;
	}
	
	/**
	 * Return the time elapsed (in milliseconds) since the last time this timer was started. 
	 * If the timer is not running now 0 is returned.
	 * 
	 * @return
	 */
	public long getElapsed() {
	    return isStarted() ? (System.currentTimeMillis() - startTime) : 0;
	}
	
	/**
	 * Return the total time (in milliseconds) spent while this timer was running. If the timer
	 * is running when this function is called time elapsed will be discarded. Therefore, it is
	 * advised to use this function only with stopped timers.
	 * 
	 * @return
	 */
	public long getTotal() {
		return totalTime;
	}

	/**
	 * Return the total number of times this timer has been started and stopped. Note that
	 * recursive start operations are computed only once so actual number of times 
	 * {@link start() start()} function is called may be greater than this amount.
	 * 
	 * @return
	 */
	public long getCount() {
		return count;
	}
	
	/**
	 * Return the timeout associated with this timer.
	 * 
	 * @return
	 */
	public long getTimeout() {
		return timeout;
	}
	
	/**
	 * Retutn the total time spent (in milliseconds) divided by the number of times this timer 
	 * has been ran. If the timer is still running elapsed time is discarded. Therefore, it is
	 * advised to use this function only with stopped timers.
	 * 
	 * @return
	 */
	public double getAverage() {
		return (double) totalTime / count;
	}
	
	/**
	 * Return the total time spent between last start()-stop() period.
	 * 
	 * @return
	 */
	public long getLast() {
		return lastTime;
	}
	
	/**
	 * Set a timeout limit for this timer. Set the timeout to 0 to diable timeout checking
	 * 
	 * @param timeout
	 */
	public void setTimeout(long timeout) {
	    if(timeout < 0)
	        throw new IllegalArgumentException("Cannot set the timeout to a negative value!");
	    
		this.timeout = timeout;
	}
	
	public String toString() {		
	    if(startCount > 0)
	        return "Timer " + name + " Avg: " + getAverage() + " Count: " + count + " Total: " + getTotal() + " Still running: "+ startCount;
	    
		return "Timer " + name + " Avg: " + getAverage() + " Count: " + count + " Total: " + getTotal();
	}
}
