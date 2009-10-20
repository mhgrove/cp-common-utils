// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils;

/**
 * Title: StringRenderer<br>
 * Description: An interface for a class which renders any given object into a string representation. Intended for use
 * when the default representation returned by an objects toString() method is unsuitable for the operation.<br>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Oct 13, 2006 9:05:26 AM
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public interface StringRenderer<T> {

    /**
     * Renders the object into a String value.  Similar to the toString method.
     * @param theObject the object to render
     * @return a string representation of the object.
     */
    public String render(T theObject);
}
