package com.clarkparsia.utils;

/**
 * Title: <br/>
 * Description: <br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Oct 22, 2009 9:50:13 AM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public interface Predicate<T> {
	public boolean accept(T theValue);
}
