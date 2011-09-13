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

import javax.net.ssl.X509TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManager;

import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

/**
 * <p></p>
 *
 * @author Michael Grove
 * @since 1.0
 * @version 2.0
 */
public final class BasicX509TrustManager implements X509TrustManager {
    private X509TrustManager mDefaultTrustManager = null;

    public BasicX509TrustManager(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
        super();

        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

        factory.init(keystore);

        TrustManager[] trustmanagers = factory.getTrustManagers();

        if (trustmanagers.length == 0) {
            throw new NoSuchAlgorithmException("no trust manager found");
        }

        mDefaultTrustManager = (X509TrustManager)trustmanagers[0];
    }

    public void checkClientTrusted(X509Certificate[] certificates,String authType) throws CertificateException {
        throw new UnsupportedOperationException("NYI");
        //mDefaultTrustManager.checkClientTrusted(certificates,authType);
    }

    public void checkServerTrusted(X509Certificate[] certificates,String authType) throws CertificateException {
        if ((certificates != null) && (certificates.length == 1)) {
            certificates[0].checkValidity();
        } else {
            mDefaultTrustManager.checkServerTrusted(certificates,authType);
        }
    }

    public X509Certificate[] getAcceptedIssuers() {
        throw new UnsupportedOperationException("NYI");
        //return this.mDefaultTrustManager.getAcceptedIssuers();
    }
}
