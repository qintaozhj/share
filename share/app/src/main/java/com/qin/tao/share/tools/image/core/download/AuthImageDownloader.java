/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.qin.tao.share.tools.image.core.download;

import android.content.Context;

import com.qin.tao.share.tools.image.utils.TrustManager;

import java.io.IOException;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class AuthImageDownloader extends BaseImageDownloader {
    private SSLSocketFactory mSSLSocketFactory;

    public AuthImageDownloader(Context context) {
        this(context, DEFAULT_HTTP_CONNECT_TIMEOUT, DEFAULT_HTTP_READ_TIMEOUT);
    }

    public AuthImageDownloader(Context context, int connectTimeout, int readTimeout) {
        super(context, connectTimeout, readTimeout);

        SSLContext sslContext = TrustManager.createSslContextForTrustedCertificates();
        mSSLSocketFactory = sslContext.getSocketFactory();

        HttpsURLConnection.setDefaultSSLSocketFactory(this.mSSLSocketFactory);
        HttpsURLConnection.setDefaultHostnameVerifier(TrustManager.getHostnameVerifier());
    }

    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        HttpURLConnection conn = super.createConnection(url, extra);
        if (conn instanceof HttpsURLConnection) {
            if (((HttpsURLConnection) conn).getSSLSocketFactory() != this.mSSLSocketFactory)
                ((HttpsURLConnection) conn).setSSLSocketFactory(this.mSSLSocketFactory);
            if (((HttpsURLConnection) conn).getHostnameVerifier() != TrustManager.getHostnameVerifier())
                ((HttpsURLConnection) conn).setHostnameVerifier(TrustManager.getHostnameVerifier());
        }

        return conn;
    }

}
