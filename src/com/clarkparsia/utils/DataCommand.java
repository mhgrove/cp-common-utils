package com.clarkparsia.utils;

/**
 * Title: <br/>
 * Description: <br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Oct 29, 2009 10:10:30 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public interface DataCommand<T> extends Command {
	public void setData(T theData);
	public T getData();
}
