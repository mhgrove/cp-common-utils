package com.clarkparsia.utils;

/**
 * Title: <br/>
 * Description: <br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: Oct 29, 2009 10:11:11 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public abstract class AbstractDataCommand<T> implements DataCommand<T> {
	private T mData;

	public void setData(T theData) {
		mData = theData;
	}

	public T getData() {
		return mData;
	}
}
