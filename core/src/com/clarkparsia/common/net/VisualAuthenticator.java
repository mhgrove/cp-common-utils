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

import java.net.PasswordAuthentication;
import java.net.Authenticator;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

import java.awt.GridLayout;

/**
 * <p>A GUI-based authenticator for obtaining user name and password information from the user for gaining access to protected URL's over HTTP.</p>
 *
 * @author Michael Grove
 * @since 1.0
 * @version 1.0
 */
public final class VisualAuthenticator extends Authenticator {

    /**
     * @inheritDoc
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        JTextField aUser = new JTextField();
        JTextField aPassword = new JPasswordField();

        JLabel aUserLabel = new JLabel("User");
        aUserLabel.setLabelFor(aUserLabel);
        
        JLabel aPasswordLabel = new JLabel("Password");
        aPasswordLabel.setLabelFor(aPassword);

        JPanel aPanel = new JPanel(new GridLayout(2, 2));
        aPanel.add(aUserLabel);
        aPanel.add(aUser);
        aPanel.add(aPasswordLabel);
        aPanel.add(aPassword);

        int aOption = JOptionPane.showConfirmDialog(null, new Object[] { "Host: " + getRequestingHost(),"Realm: " + getRequestingPrompt(), aPanel }, "Authorization Required", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (aOption == JOptionPane.OK_OPTION) {
            return new PasswordAuthentication(aUser.getText(), aPassword.getText().toCharArray());
        }
        else {
			return null;
		}
    }
}

