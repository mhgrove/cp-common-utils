package com.clarkparsia.utils;

import java.security.GeneralSecurityException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.clarkparsia.utils.net.BasicX509TrustManager;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 *
 * @author Michael Grove
 * @version 1.0
 */
public class HTTPS {
    
    public static void setup() throws GeneralSecurityException {
        SSLContext sc = SSLContext.getInstance( "SSL" );
        sc.init( null, new TrustManager[] { new BasicX509TrustManager( null ) },
            new java.security.SecureRandom() );
        HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );        
    }
}