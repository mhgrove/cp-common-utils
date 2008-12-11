// The MIT License
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

package com.clarkparsia.utils.timer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.clarkparsia.utils.TableFormatter;

/**
 * 
 * @author Evren Sirin
 */
public class Timers  {
	private Map timers = new LinkedHashMap();
	
	public Timer mainTimer;
	
	public Timers() {
	    mainTimer = createTimer("main");
	    mainTimer.start();
	}
	
	public void addAll( Timers other ) {
	    for( Iterator i = other.timers.keySet().iterator(); i.hasNext(); ) {
            String name = (String) i.next();
            Timer otherTimer = other.getTimer( name );
            Timer thisTimer = getTimer( name );
            if( thisTimer == null )
                timers.put( name, otherTimer );
            else 
                thisTimer.add( otherTimer );            
        }	    
	}

	public Timer createTimer(String name) {
		Timer t = new Timer(name, mainTimer);
		timers.put(name, t);
		return t;
	}

	public Timer startTimer(String name) {
		Timer t = getTimer(name);
		if(t == null) t = createTimer(name);
		t.start();
		return t;
	}

	public void checkTimer(String name) {
		Timer t = getTimer(name);
		if (t == null)
			throw new UnsupportedOperationException("Timer " + name + " does not exist!");
		
		t.check();
	}
	
	public void resetTimer(String name) {
		Timer t = getTimer(name);
		if (t == null)
		    throw new UnsupportedOperationException("Timer " + name + " does not exist!");
		
		t.start();		
	}
	
	public void setTimeout(String name, long timeout) {
		Timer t = getTimer(name);
		if (t == null)
			t = createTimer(name);
		t.setTimeout(timeout);
	}
		
	public void stopTimer(String name) {
		Timer t = getTimer(name);
		if (t == null)
		    throw new UnsupportedOperationException("Timer " + name + " does not exist!");
		
		t.stop();
	}

	public void resetAll() {
	    Iterator i = timers.values().iterator();
	    while(i.hasNext()) {
	        Timer timer = (Timer) i.next();
	        timer.reset();
	    }
        mainTimer.start();
	}

    public long getTimerTotal(String name) {
        Timer timer = getTimer(name);
        return (timer == null) ? 0 : timer.getTotal();
    }

    public double getTimerAverage(String name) {
        Timer timer = getTimer(name);
        return (timer == null) ? 0 : timer.getAverage();
    }

	public Timer getTimer(String name) {
		return (Timer) timers.get(name);
	}

	public void print() {
	    print( false );
	}

    public void print( boolean shortForm ) {
        print( shortForm, "Total" );
    }
    
	public void print( boolean shortForm, final String sortBy ) {
		String[] colNames = shortForm 
			? new String[] {"Name", "Total (s)" }
		    : new String[] {"Name", "Count", "Avg", "Total (s)" };
            
        boolean[] alignment = shortForm
            ? new boolean[] { false, true }
            : new boolean[] { false, true, true, true };
			
		List list = new ArrayList( timers.values() );
        if( sortBy != null ) {
    		Collections.sort(list, new Comparator() {
    			public int compare(Object o1, Object o2) {
                    if( sortBy.equalsIgnoreCase( "Total" ) )
                        return (int)(((Timer)o2).getTotal() - ((Timer)o1).getTotal());
                    else if( sortBy.equalsIgnoreCase( "Avg" ) )
                        return (int)(((Timer)o2).getAverage() - ((Timer)o1).getAverage());
                    else if( sortBy.equalsIgnoreCase( "Count" ) )
                        return (int)(((Timer)o2).getCount() - ((Timer)o1).getCount());
                    else 
                        return ((Timer)o2).getName().compareTo(((Timer)o1).getName());
    			}			
    		});		
        }
		
        NumberFormat nf = new DecimalFormat("0.00");
		TableFormatter table = new TableFormatter( Arrays.asList( colNames ) );
        table.setAlignment( alignment );
		Iterator i = list.iterator();
		while(i.hasNext()) {
			Timer timer = (Timer) i.next();
			if(timer.getCount() == 0)
			    continue;
			List row = new ArrayList();
			row.add(timer.getName());
			if(!shortForm) {
				row.add(String.valueOf(timer.getCount()));
				row.add(nf.format(timer.getAverage()));
			}
			row.add(String.valueOf(timer.getTotal()));
			table.add(row);
		}
		
		table.print(System.out);
	}
	
	public String toString() {
		return timers.values().toString();
	}
}
