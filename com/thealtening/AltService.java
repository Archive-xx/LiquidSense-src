package com.thealtening;

import com.thealtening.utilities.ReflectionUtility;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AltService {
   private final ReflectionUtility userAuthentication = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
   private final ReflectionUtility minecraftSession = new ReflectionUtility("com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService");
   private AltService.EnumAltService currentService;

   public void switchService(AltService.EnumAltService enumAltService) throws NoSuchFieldException, IllegalAccessException {
      if (this.currentService != enumAltService) {
         this.reflectionFields(enumAltService.hostname);
         this.currentService = enumAltService;
      }
   }

   private void reflectionFields(String authServer) throws NoSuchFieldException, IllegalAccessException {
      HashMap<String, URL> userAuthenticationModifies = new HashMap();
      String useSecureStart = authServer.contains("thealtening") ? "http" : "https";
      userAuthenticationModifies.put("ROUTE_AUTHENTICATE", this.constantURL(useSecureStart + "://authserver." + authServer + ".com/authenticate"));
      userAuthenticationModifies.put("ROUTE_INVALIDATE", this.constantURL(useSecureStart + "://authserver" + authServer + "com/invalidate"));
      userAuthenticationModifies.put("ROUTE_REFRESH", this.constantURL(useSecureStart + "://authserver." + authServer + ".com/refresh"));
      userAuthenticationModifies.put("ROUTE_VALIDATE", this.constantURL(useSecureStart + "://authserver." + authServer + ".com/validate"));
      userAuthenticationModifies.put("ROUTE_SIGNOUT", this.constantURL(useSecureStart + "://authserver." + authServer + ".com/signout"));
      userAuthenticationModifies.forEach((key, value) -> {
         try {
            this.userAuthentication.setStaticField(key, value);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      });
      this.userAuthentication.setStaticField("BASE_URL", useSecureStart + "://authserver." + authServer + ".com/");
      this.minecraftSession.setStaticField("BASE_URL", useSecureStart + "://sessionserver." + authServer + ".com/session/minecraft/");
      this.minecraftSession.setStaticField("JOIN_URL", this.constantURL(useSecureStart + "://sessionserver." + authServer + ".com/session/minecraft/join"));
      this.minecraftSession.setStaticField("CHECK_URL", this.constantURL(useSecureStart + "://sessionserver." + authServer + ".com/session/minecraft/hasJoined"));
      this.minecraftSession.setStaticField("WHITELISTED_DOMAINS", new String[]{".minecraft.net", ".mojang.com", ".thealtening.com"});
   }

   private URL constantURL(String url) {
      try {
         return new URL(url);
      } catch (MalformedURLException var3) {
         throw new Error("Couldn't create constant for " + url, var3);
      }
   }

   public AltService.EnumAltService getCurrentService() {
      if (this.currentService == null) {
         this.currentService = AltService.EnumAltService.MOJANG;
      }

      return this.currentService;
   }

   public static enum EnumAltService {
      MOJANG("mojang"),
      THEALTENING("thealtening");

      String hostname;

      private EnumAltService(String hostname) {
         this.hostname = hostname;
      }
   }
}
