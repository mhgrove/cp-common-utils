//The MIT License
//
//Copyright (c) 2003 Ron Alford, Mike Grove, Bijan Parsia, Evren Sirin
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to
//deal in the Software without restriction, including without limitation the
//rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
//sell copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//IN THE SOFTWARE.

package com.clarkparsia.utils;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Format a table data to be printed to console or as an HTML table. Data can be given at once 
 * or the rows can be added incrementally.
 * 
 * @author Evren Sirin
 */
public class TableFormatter {
    Collection data;

    List colNames;

    boolean[] rightAligned;
    boolean autoAlign = true;

    int colWidths[] = null;

    String colSep = " | ";

    public TableFormatter( Collection data, List colNames ) {
        this.data = data;
        this.colNames = colNames;

        int cols = colNames.size();
        colWidths = new int[cols];
        rightAligned = new boolean[cols];
    }

    public TableFormatter( List colNames ) {
        this.data = new ArrayList();
        this.colNames = colNames;

        int cols = colNames.size();
        colWidths = new int[cols];
        rightAligned = new boolean[cols];
    }

    public TableFormatter( String[] colNames ) {
        this( Arrays.asList( colNames ) );
    }
        
    public void setAlignment( boolean[] rightAligned ) {
        if( rightAligned.length != colNames.size() )
            throw new IllegalArgumentException( "Alignment has " + rightAligned.length
                + " elements but table has " + colNames.size() + " columns" );
        
        this.rightAligned = rightAligned;
        this.autoAlign = false;
    }

    public void setRightAligned( int colIndex, boolean rightAligned ) {
        this.rightAligned[colIndex] = rightAligned;
        this.autoAlign = false;
    }
    
    /**
     * Sets the auto-align behavior for the formatter. If turned on all the columns
     * with Number elements will be right formatted right aligned. Only the first
     * row is checked to determine the type of the elements in a column. 
     * 
     * @param autoAlign
     */
    public void setAutoAlign( boolean autoAlign ) {
        this.autoAlign = autoAlign;
    }
    
    /**
     * Set the table data that will be formatted. Each element should be of
     * equals size and this should match the size of column names
     * 
     * @param data A collection of List objects
     */
    public void setData( Collection data ) {
        this.data = data;
    }

    /**
     * Adds a new row to the data.
     * 
     * @param row A list of elements
     */
    public void add( List row ) {
        if( row.size() != colNames.size() )
            throw new IllegalArgumentException( "Row has " + row.size()
                + " elements but table has " + colNames.size() + " columns" );

        data.add( row );
    }

    public void print( OutputStream writer ) {
        print( new PrintWriter( writer ), false );
    }

    public void print( Writer writer ) {
        print( writer, false );
    }

    public void print( OutputStream writer, boolean formatHTML ) {
        print( new PrintWriter( writer ), formatHTML );
    }

    public void print( Writer writer, boolean formatHTML ) {
        if( formatHTML )
            printHTML( writer );
        else
            printText( writer );
    }

    private void printHTML( Writer writer ) {
        PrintWriter pw = (writer instanceof PrintWriter) 
            ? (PrintWriter) writer  
            : new PrintWriter( writer );

        pw.println( "<table border=1>" );
        // Column headings
        pw.println( "  <tr>" );
        for( int col = 0; col < colNames.size(); col++ ) {
            String s = (String) colNames.get( col );
            pw.print( "    <th>" );
            pw.print( s );
            pw.print( "</th>" );
            pw.println();
        }
        pw.println( "  </tr>" );

        for( Iterator i = data.iterator(); i.hasNext(); ) {
            Collection rowData = (Collection) i.next();
            pw.println( "  <tr>" );
            Iterator j = rowData.iterator();
            for( int col = 0; j.hasNext(); col++ ) {
                Object value = j.next();
                String s = (value == null) ? "<null>" : value.toString();

                pw.print( "    <td>" );
                pw.print( s );
                pw.print( "</td>" );
                pw.println();
            }
            pw.println( "  </tr>" );
        }
        pw.println( "</table>" );

        pw.flush();
    }

    private void printText( Writer writer ) {
        PrintWriter pw = (writer instanceof PrintWriter) 
            ? (PrintWriter) writer  
            : new PrintWriter( writer );

        if( autoAlign && !data.isEmpty() ) {
            Collection rowData = (Collection) data.iterator().next();
            Iterator j = rowData.iterator();
            for( int col = 0; j.hasNext(); col++ ) {
                Object value = j.next();
                rightAligned[col] = (value instanceof Number);
            }
        }

        computeHeaderWidths();
        computeRowWidths();

        int lineWidth = computeLineWidth();
        int numCols = colNames.size();

        String[] row = new String[numCols];
        for( int col = 0; col < row.length; col++ ) {
            row[col] = colNames.get( col ).toString();
        }
        printRow( pw, row );

        for( int i = 0; i < lineWidth; i++ )
            pw.print( '=' );
        pw.println();


        for( Iterator i = data.iterator(); i.hasNext(); ) {
            Collection rowData = (Collection) i.next();

            Iterator j = rowData.iterator();
            for( int col = 0; j.hasNext(); col++ ) {
                Object value = j.next();
                row[col] = value == null ? "<null>" : value.toString();
            }
            printRow( pw, row );
        }

        pw.flush();
    }

    private void printRow( PrintWriter pw, String[] row ) {
        for( int col = 0; col < row.length; col++ ) {
            String s = row[col];
            int pad = colWidths[col];
            StringBuffer sbuff = new StringBuffer( 120 );

            if( col > 0 )
                sbuff.append( colSep );

            if( !rightAligned[col] )
                sbuff.append( s );
            
            for( int j = 0; j < pad - s.length(); j++ )
                sbuff.append( ' ' );
            
            if( rightAligned[col] )
                sbuff.append( s );

            pw.print( sbuff );
        }
        pw.println();
    }

    private int computeLineWidth() {
        int numCols = colWidths.length;
        int lineWidth = 0;
        for( int i = 0; i < numCols; i++ )
            lineWidth += colWidths[i];

        lineWidth += (numCols - 1) * colSep.length();

        return lineWidth;
    }

    private void computeHeaderWidths() {
        Iterator k = colNames.iterator();
        for( int col = 0; k.hasNext(); col++ ) {
            Object value = k.next();
            String str = (value == null) ? "<null>" : value.toString();
            colWidths[col] = str.length();
        }
    }

    private void computeRowWidths() {
        for( Iterator i = data.iterator(); i.hasNext(); ) {
            Collection rowData = (Collection) i.next();

            Iterator j = rowData.iterator();
            for( int col = 0; j.hasNext(); col++ ) {
                Object value = j.next();
                String str = (value == null) ? "<null>" : value.toString();

                if( colWidths[col] < str.length() )
                    colWidths[col] = str.length();
            }
        }
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColCount() {
        return colNames.size();
    }
    
    public void sort( String colName ) {
        sort( colNames.indexOf( colName ) );
    }

    public void sort( final int col ) {
        Object a[] = data.toArray();
        Arrays.sort(a, new Comparator() {
            public int compare( Object l1, Object l2 ) {
                return ((Comparable)((List) l1).get(col)).compareTo(((List) l2).get(col));
            }            
        });
        data = Arrays.asList( a );
    }
    
    public void sort( final int col, final Comparator c ) {
        Object a[] = data.toArray();
        Arrays.sort(a, new Comparator() {
            public int compare( Object l1, Object l2 ) {
                return c.compare(((List) l1).get(col), ((List) l2).get(col));
            }            
        });
        data = Arrays.asList( a );
    }

    public String toString() {
        StringWriter sw = new StringWriter();
        printText( sw );

        return sw.toString();
    }
}