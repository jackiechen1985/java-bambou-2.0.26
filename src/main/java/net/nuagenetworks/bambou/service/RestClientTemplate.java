/*
  Copyright (c) 2015, Alcatel-Lucent Inc
  All rights reserved.

  Redistribution and use in source and binary forms, with or without
  modification, are permitted provided that the following conditions are met:
      * Redistributions of source code must retain the above copyright
        notice, this list of conditions and the following disclaimer.
      * Redistributions in binary form must reproduce the above copyright
        notice, this list of conditions and the following disclaimer in the
        documentation and/or other materials provided with the distribution.
      * Neither the name of the copyright holder nor the names of its contributors
        may be used to endorse or promote products derived from this software without
        specific prior written permission.

  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY
  DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.nuagenetworks.bambou.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;

public class RestClientTemplate extends RestTemplate {

    private static final int DEFAULT_SOCKET_TIMEOUT_IN_MS = 60 * 1000;

    public RestClientTemplate() {
        super(new HttpComponentsClientHttpRequestFactory());
//        init();
        setSocketTimeout(DEFAULT_SOCKET_TIMEOUT_IN_MS);
        ResponseErrorHandlerImpl responseErrorHandler = new ResponseErrorHandlerImpl();
        setErrorHandler(responseErrorHandler);
    }

//    private void init() throws NoSuchAlgorithmException, KeyManagementException {
//        HttpComponentsClientHttpRequestFactory requestFactory =
//                (HttpComponentsClientHttpRequestFactory) getRequestFactory();
//        TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates,
//                                                   String s) {
//                    }
//
//                    @Override
//                    public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) {
//                    }
//
//                    @Override
//                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                        return null;
//                    }
//                }
//        };
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
//            @Override
//            public boolean verify(String s, SSLSession sslSession) {
//                return true;
//            }
//        };
//        CloseableHttpClient httpclient =
//                HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(hostnameVerifier).build();
//        requestFactory.setHttpClient(httpclient);
//    }

    public void setSocketTimeout(int socketTimeout) {
        if (socketTimeout > 0) {
            // Debug
            logger.debug("Using socket timeout for REST connection: " + socketTimeout);

            // Set connect and read timeouts
            HttpComponentsClientHttpRequestFactory requestFactory =
                    (HttpComponentsClientHttpRequestFactory) getRequestFactory();
            requestFactory.setConnectTimeout(socketTimeout);
            requestFactory.setReadTimeout(socketTimeout);
        }
    }
}
