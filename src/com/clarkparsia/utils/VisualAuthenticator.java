package com.clarkparsia.utils;

import java.net.PasswordAuthentication;
import java.net.Authenticator;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

import java.awt.GridLayout;

/**
 * <p>Title: VisualAuthenticator</p>
 * <p>Description: A GUI-based authenticator for obtaining user name and password information from the user for gaining access to protected URL's over HTTP.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 * @author Michael Grove <mhgrove@hotmail.com>
 * @version 1.0
 */
public class VisualAuthenticator extends Authenticator {
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
        else return null;
    }
}

