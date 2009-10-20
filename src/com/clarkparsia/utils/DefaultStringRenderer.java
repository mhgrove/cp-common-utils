// Copyright (c) 2005 - 2009, Clark & Parsia, LLC. <http://www.clarkparsia.com>

package com.clarkparsia.utils;

/**
 * Title: DefaultStringRenderer<br>
 * Description: Default implements of the StringRenderer interface<br>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Oct 13, 2006 9:05:46 AM
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class DefaultStringRenderer<T> implements StringRenderer<T> {

    /**
     * @inheritDoc
     */
    public String render(Object theObject) {
        return theObject.toString();
    }
}
