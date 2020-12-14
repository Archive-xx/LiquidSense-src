//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.login;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.net.Proxy;
import java.util.Base64;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007J\u0012\u0010\b\u001a\u00020\t2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0006H\u0007¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/login/LoginUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "login", "Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "username", "", "password", "loginCracked", "", "loginSessionId", "sessionId", "LoginResult", "LiquidSense"}
)
public final class LoginUtils extends MinecraftInstance {
   // $FF: synthetic field
   public static final LoginUtils INSTANCE;

   @JvmStatic
   @NotNull
   public static final LoginUtils.LoginResult loginSessionId(@NotNull String lllllllllllllllllllIIlIIIIIlIlII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIlIIIIIlIlII, "sessionId");

      String lllllllllllllllllllIIlIIIIIlIIIl;
      try {
         byte[] var10000 = Base64.getDecoder().decode((String)StringsKt.split$default((CharSequence)lllllllllllllllllllIIlIIIIIlIlII, new String[]{"."}, false, 0, 6, (Object)null).get(1));
         Intrinsics.checkExpressionValueIsNotNull(var10000, "Base64.getDecoder().deco…(sessionId.split(\".\")[1])");
         String lllllllllllllllllllIIlIIIIIlIIIl = var10000;
         short lllllllllllllllllllIIlIIIIIlIIII = Charsets.UTF_8;
         long lllllllllllllllllllIIlIIIIIIllll = false;
         lllllllllllllllllllIIlIIIIIlIIIl = new String(lllllllllllllllllllIIlIIIIIlIIIl, lllllllllllllllllllIIlIIIIIlIIII);
      } catch (Exception var6) {
         return LoginUtils.LoginResult.FAILED_PARSE_TOKEN;
      }

      JsonElement var11 = (new JsonParser()).parse(lllllllllllllllllllIIlIIIIIlIIIl);
      Intrinsics.checkExpressionValueIsNotNull(var11, "JsonParser().parse(decodedSessionData)");
      String lllllllllllllllllllIIlIIIIIlIIIl = var11.getAsJsonObject();
      var11 = lllllllllllllllllllIIlIIIIIlIIIl.get("spr");
      Intrinsics.checkExpressionValueIsNotNull(var11, "sessionObject.get(\"spr\")");
      String lllllllllllllllllllIIlIIIIIlIlll = var11.getAsString();
      var11 = lllllllllllllllllllIIlIIIIIlIIIl.get("yggt");
      Intrinsics.checkExpressionValueIsNotNull(var11, "sessionObject.get(\"yggt\")");
      String lllllllllllllllllllIIlIIIIIllIII = var11.getAsString();
      UserUtils var12 = UserUtils.INSTANCE;
      Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIlIIIIIllIII, "accessToken");
      if (!var12.isValidToken(lllllllllllllllllllIIlIIIIIllIII)) {
         return LoginUtils.LoginResult.INVALID_ACCOUNT_DATA;
      } else {
         var12 = UserUtils.INSTANCE;
         Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIlIIIIIlIlll, "uuid");
         String var13 = var12.getUsername(lllllllllllllllllllIIlIIIIIlIlll);
         if (var13 != null) {
            float lllllllllllllllllllIIlIIIIIIlllI = var13;
            access$getMc$p$s1046033730().session = new Session(lllllllllllllllllllIIlIIIIIIlllI, lllllllllllllllllllIIlIIIIIlIlll, lllllllllllllllllllIIlIIIIIllIII, "mojang");
            LiquidBounce.INSTANCE.getEventManager().callEvent((Event)(new SessionEvent()));
            return LoginUtils.LoginResult.LOGGED;
         } else {
            boolean var10001 = false;
            return LoginUtils.LoginResult.INVALID_ACCOUNT_DATA;
         }
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   static {
      LoginUtils lllllllllllllllllllIIlIIIIIIlIIl = new LoginUtils();
      INSTANCE = lllllllllllllllllllIIlIIIIIIlIIl;
   }

   @JvmStatic
   public static final void loginCracked(@Nullable String lllllllllllllllllllIIlIIIIlIIIlI) {
      Minecraft var10000 = access$getMc$p$s1046033730();
      Session var10001 = new Session;
      UserUtils var10004 = UserUtils.INSTANCE;
      if (lllllllllllllllllllIIlIIIIlIIIlI == null) {
         Intrinsics.throwNpe();
      }

      var10001.<init>(lllllllllllllllllllIIlIIIIlIIIlI, var10004.getUUID(lllllllllllllllllllIIlIIIIlIIIlI), "-", "legacy");
      var10000.session = var10001;
      LiquidBounce.INSTANCE.getEventManager().callEvent((Event)(new SessionEvent()));
   }

   @JvmStatic
   @NotNull
   public static final LoginUtils.LoginResult login(@Nullable String lllllllllllllllllllIIlIIIIlIlIIl, @Nullable String lllllllllllllllllllIIlIIIIlIlIlI) {
      UserAuthentication var10000 = (new YggdrasilAuthenticationService(Proxy.NO_PROXY, "")).createUserAuthentication(Agent.MINECRAFT);
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication");
      } else {
         YggdrasilUserAuthentication lllllllllllllllllllIIlIIIIlIllII = (YggdrasilUserAuthentication)var10000;
         lllllllllllllllllllIIlIIIIlIllII.setUsername(lllllllllllllllllllIIlIIIIlIlIIl);
         lllllllllllllllllllIIlIIIIlIllII.setPassword(lllllllllllllllllllIIlIIIIlIlIlI);

         LoginUtils.LoginResult lllllllllllllllllllIIlIIIIlIIllI;
         try {
            lllllllllllllllllllIIlIIIIlIllII.logIn();
            Minecraft var10 = access$getMc$p$s1046033730();
            GameProfile var10003 = lllllllllllllllllllIIlIIIIlIllII.getSelectedProfile();
            Intrinsics.checkExpressionValueIsNotNull(var10003, "userAuthentication.selectedProfile");
            String var11 = var10003.getName();
            GameProfile var10004 = lllllllllllllllllllIIlIIIIlIllII.getSelectedProfile();
            Intrinsics.checkExpressionValueIsNotNull(var10004, "userAuthentication.selectedProfile");
            var10.session = new Session(var11, var10004.getId().toString(), lllllllllllllllllllIIlIIIIlIllII.getAuthenticatedToken(), "mojang");
            LiquidBounce.INSTANCE.getEventManager().callEvent((Event)(new SessionEvent()));
            lllllllllllllllllllIIlIIIIlIIllI = LoginUtils.LoginResult.LOGGED;
         } catch (AuthenticationUnavailableException var6) {
            lllllllllllllllllllIIlIIIIlIIllI = LoginUtils.LoginResult.NO_CONTACT;
         } catch (AuthenticationException var7) {
            String var9 = var7.getMessage();
            if (var9 == null) {
               Intrinsics.throwNpe();
            }

            String lllllllllllllllllllIIlIIIIlIllll = var9;
            lllllllllllllllllllIIlIIIIlIIllI = StringsKt.contains((CharSequence)lllllllllllllllllllIIlIIIIlIllll, (CharSequence)"invalid username or password.", true) ? LoginUtils.LoginResult.INVALID_ACCOUNT_DATA : (StringsKt.contains((CharSequence)lllllllllllllllllllIIlIIIIlIllll, (CharSequence)"account migrated", true) ? LoginUtils.LoginResult.MIGRATED : LoginUtils.LoginResult.NO_CONTACT);
         } catch (NullPointerException var8) {
            lllllllllllllllllllIIlIIIIlIIllI = LoginUtils.LoginResult.WRONG_PASSWORD;
         }

         return lllllllllllllllllllIIlIIIIlIIllI;
      }
   }

   private LoginUtils() {
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b¨\u0006\t"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/login/LoginUtils$LoginResult;", "", "(Ljava/lang/String;I)V", "WRONG_PASSWORD", "NO_CONTACT", "INVALID_ACCOUNT_DATA", "MIGRATED", "LOGGED", "FAILED_PARSE_TOKEN", "LiquidSense"}
   )
   public static enum LoginResult {
      // $FF: synthetic field
      INVALID_ACCOUNT_DATA,
      // $FF: synthetic field
      MIGRATED,
      // $FF: synthetic field
      NO_CONTACT,
      // $FF: synthetic field
      LOGGED,
      // $FF: synthetic field
      WRONG_PASSWORD,
      // $FF: synthetic field
      FAILED_PARSE_TOKEN;
   }
}
