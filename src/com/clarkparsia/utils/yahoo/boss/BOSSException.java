package com.clarkparsia.utils.yahoo.boss;

/**
 * Title: BOSSException<br/>
 * Description: Exception from working with the BOSS API<br/>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br/>
 * Created: May 14, 2009 3:23:18 PM <br/>
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class BOSSException extends Exception {

	/**
	 * Create a new BOSSException
	 * @param theMsg the error message
	 */
	public BOSSException(String theMsg) {
		super(theMsg);
	}

	/**
	 * Create a new BOSSException
	 * @param theCause the cause of the error
	 */
	public BOSSException(Throwable theCause) {
		super(theCause);
	}

	/**
	 * Create a new BOSSException
	 * @param theMsg the error message
	 * @param theCause the cause of the error
	 */
	public BOSSException(String theMsg, Throwable theCause) {
		super(theMsg, theCause);
	}
}
