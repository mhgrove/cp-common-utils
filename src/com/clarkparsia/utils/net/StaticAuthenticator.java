package com.clarkparsia.utils.net;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Title: <br>
 * Description: <br>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Sep 5, 2007 6:52:06 PM
 *
 * @author Michael Grove <mike@clarkparsia.com>
 */
public class StaticAuthenticator extends Authenticator {
    private static String mUser;
    private static String mPass;

    public StaticAuthenticator(String theUser, String thePass) {
        mUser = theUser;
        mPass = thePass;
    }

    public String getUser() {
        return mUser;
    }

    public String getPassword() {
        return mPass;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(mUser, mPass.toCharArray());
    }
}