package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerLoginJWTPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "token", "", "allowMessages", "", "(Ljava/lang/String;Z)V", "getAllowMessages", "()Z", "getToken", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "other", "", "hashCode", "", "toString", "LiquidSense"}
)
public final class ServerLoginJWTPacket implements Packet {
   // $FF: synthetic field
   @SerializedName("token")
   @NotNull
   private final String token;
   // $FF: synthetic field
   @SerializedName("allow_messages")
   private final boolean allowMessages;

   public int hashCode() {
      String var10000 = llllllllllllllllllIllIIlllIIIlIl.token;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      byte var2 = llllllllllllllllllIllIIlllIIIlIl.allowMessages;
      if (var2 != 0) {
         boolean var10002 = false;
         var2 = 1;
      }

      return var1 + var2;
   }

   @NotNull
   public final ServerLoginJWTPacket copy(@NotNull String llllllllllllllllllIllIIlllIlIIII, boolean llllllllllllllllllIllIIlllIIllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIllIIlllIlIIII, "token");
      return new ServerLoginJWTPacket(llllllllllllllllllIllIIlllIlIIII, llllllllllllllllllIllIIlllIIllll);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ServerLoginJWTPacket) {
            ServerLoginJWTPacket var2 = (ServerLoginJWTPacket)var1;
            if (Intrinsics.areEqual((Object)this.token, (Object)var2.token) && this.allowMessages == var2.allowMessages) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   // $FF: synthetic method
   public static ServerLoginJWTPacket copy$default(ServerLoginJWTPacket llllllllllllllllllIllIIlllIIlIlI, String var1, boolean var2, int llllllllllllllllllIllIIlllIIIlll, Object var4) {
      if ((llllllllllllllllllIllIIlllIIIlll & 1) != 0) {
         var1 = llllllllllllllllllIllIIlllIIlIlI.token;
      }

      if ((llllllllllllllllllIllIIlllIIIlll & 2) != 0) {
         var2 = llllllllllllllllllIllIIlllIIlIlI.allowMessages;
      }

      return llllllllllllllllllIllIIlllIIlIlI.copy(var1, var2);
   }

   public ServerLoginJWTPacket(@NotNull String llllllllllllllllllIllIIlllIlllIl, boolean llllllllllllllllllIllIIlllIlllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIllIIlllIlllIl, "token");
      super();
      llllllllllllllllllIllIIllllIIIIl.token = llllllllllllllllllIllIIlllIlllIl;
      llllllllllllllllllIllIIllllIIIIl.allowMessages = llllllllllllllllllIllIIlllIlllll;
   }

   public final boolean getAllowMessages() {
      return llllllllllllllllllIllIIllllIIllI.allowMessages;
   }

   public final boolean component2() {
      return llllllllllllllllllIllIIlllIlIllI.allowMessages;
   }

   @NotNull
   public final String component1() {
      return llllllllllllllllllIllIIlllIllIIl.token;
   }

   @NotNull
   public final String getToken() {
      return llllllllllllllllllIllIIllllIlIIl.token;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("ServerLoginJWTPacket(token=").append(this.token).append(", allowMessages=").append(this.allowMessages).append(")"));
   }
}
