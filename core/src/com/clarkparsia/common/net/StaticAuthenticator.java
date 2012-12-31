/*
 * Copyright (c) 2005-2011 Clark & Parsia, LLC. <http://www.clarkparsia.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clarkparsia.common.net;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * <p>Implementation of the {@link Authenticator} interface which has a static username and password</p>
 *
 * @author Michael Grove
 * @since 1.0
 * @version 1.0
 */
public final class StaticAuthenticator extends Authenticator {

	/**
	 * the user name for auth
	 */
    private String mUser;

	/**
	 * The password for auth
	 */
    private String mPass;

	/**
	 * Create a new StaticAuthenticator
	 * @param theUser the username
	 * @param thePass the password
	 */
    public StaticAuthenticator(String theUser, String thePass) {
        mUser = theUser;
        mPass = thePass;
    }

	/**
	 * Return the authenticator user
	 * @return the user
	 */
    public String getUser() {
        return mUser;
    }

	/**
	 * Return the authenticator password
	 * @return the password
	 */
    public String getPassword() {
        return mPass;
    }

	/**
	 * @inheritDoc
	 */
	@Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(mUser, mPass.toCharArray());
    }
}