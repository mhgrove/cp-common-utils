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

import java.security.GeneralSecurityException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import com.clarkparsia.common.net.BasicX509TrustManager;


/**
 * <p>Initializes HTTPS with a less restrictive TrustManager</p>
 *
 * @author Michael Grove
 * @since 1.0
 * @version 2.0
 * @see BasicX509TrustManager
 */
public final class HTTPS {
    
    public static void setup() throws GeneralSecurityException {
        SSLContext sc = SSLContext.getInstance( "SSL" );
        sc.init( null, new TrustManager[] { new BasicX509TrustManager( null ) },
            new java.security.SecureRandom() );
        HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory() );        
    }
}