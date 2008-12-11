package com.clarkparsia.utils.net;

import javax.net.ssl.X509TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManager;

import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.KeyStoreException;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

/**
 * Title: <br>
 * Description: <br>
 * Company: Clark & Parsia, LLC. <http://www.clarkparsia.com> <br>
 * Created: Dec 21, 2006 3:19:09 PM
 *
 * @author Michael Grove <mhgrove@hotmail.com>
 */
public class BasicX509TrustManager implements X509TrustManager {
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
