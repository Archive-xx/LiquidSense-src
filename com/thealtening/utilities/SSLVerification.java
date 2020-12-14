package com.thealtening.utilities;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLVerification {
   private boolean verified = false;

   public void verify() {
      if (!this.verified) {
         this.bypassSSL();
         this.whitelistTheAltening();
         this.verified = true;
      }

   }

   private void bypassSSL() {
      TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
         public X509Certificate[] getAcceptedIssuers() {
            return null;
         }

         public void checkClientTrusted(X509Certificate[] certs, String authType) {
         }

         public void checkServerTrusted(X509Certificate[] certs, String authType) {
         }
      }};

      try {
         SSLContext sc = SSLContext.getInstance("SSL");
         sc.init((KeyManager[])null, trustAllCerts, new SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      } catch (Exception var3) {
      }

   }

   private void whitelistTheAltening() {
      HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> {
         return hostname.equals("authserver.thealtening.com") || hostname.equals("sessionserver.thealtening.com");
      });
   }
}
