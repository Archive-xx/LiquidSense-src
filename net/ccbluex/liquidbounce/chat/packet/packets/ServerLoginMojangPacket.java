package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerLoginMojangPacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "name", "", "uuid", "Ljava/util/UUID;", "allowMessages", "", "(Ljava/lang/String;Ljava/util/UUID;Z)V", "getAllowMessages", "()Z", "getName", "()Ljava/lang/String;", "getUuid", "()Ljava/util/UUID;", "component1", "component2", "component3", "copy", "equals", "other", "", "hashCode", "", "toString", "LiquidSense"}
)
public final class ServerLoginMojangPacket implements Packet {
   // $FF: synthetic field
   @SerializedName("uuid")
   @NotNull
   private final UUID uuid;
   // $FF: synthetic field
   @SerializedName("allow_messages")
   private final boolean allowMessages;
   // $FF: synthetic field
   @SerializedName("name")
   @NotNull
   private final String name;

   @NotNull
   public final ServerLoginMojangPacket copy(@NotNull String lllllllllllllllllllIlIlIlllllIll, @NotNull UUID lllllllllllllllllllIlIlIlllllIlI, boolean lllllllllllllllllllIlIlIllllIllI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIlIlllllIll, "name");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIlIlllllIlI, "uuid");
      return new ServerLoginMojangPacket(lllllllllllllllllllIlIlIlllllIll, lllllllllllllllllllIlIlIlllllIlI, lllllllllllllllllllIlIlIllllIllI);
   }

   @NotNull
   public final String component1() {
      return lllllllllllllllllllIlIllIIIIIllI.name;
   }

   public final boolean component3() {
      return lllllllllllllllllllIlIllIIIIIIII.allowMessages;
   }

   public ServerLoginMojangPacket(@NotNull String lllllllllllllllllllIlIllIIIIlIll, @NotNull UUID lllllllllllllllllllIlIllIIIIlllI, boolean lllllllllllllllllllIlIllIIIIllIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIllIIIIlIll, "name");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIllIIIIlllI, "uuid");
      super();
      lllllllllllllllllllIlIllIIIIllII.name = lllllllllllllllllllIlIllIIIIlIll;
      lllllllllllllllllllIlIllIIIIllII.uuid = lllllllllllllllllllIlIllIIIIlllI;
      lllllllllllllllllllIlIllIIIIllII.allowMessages = lllllllllllllllllllIlIllIIIIllIl;
   }

   public final boolean getAllowMessages() {
      return lllllllllllllllllllIlIllIIIlIllI.allowMessages;
   }

   @NotNull
   public final String getName() {
      return lllllllllllllllllllIlIllIIIlllII.name;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ServerLoginMojangPacket) {
            ServerLoginMojangPacket var2 = (ServerLoginMojangPacket)var1;
            if (Intrinsics.areEqual((Object)this.name, (Object)var2.name) && Intrinsics.areEqual((Object)this.uuid, (Object)var2.uuid) && this.allowMessages == var2.allowMessages) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("ServerLoginMojangPacket(name=").append(this.name).append(", uuid=").append(this.uuid).append(", allowMessages=").append(this.allowMessages).append(")"));
   }

   @NotNull
   public final UUID getUuid() {
      return lllllllllllllllllllIlIllIIIllIII.uuid;
   }

   @NotNull
   public final UUID component2() {
      return lllllllllllllllllllIlIllIIIIIlII.uuid;
   }

   public int hashCode() {
      String var10000 = lllllllllllllllllllIlIlIlllIlIlI.name;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      UUID var2 = lllllllllllllllllllIlIlIlllIlIlI.uuid;
      boolean var10002;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         var10002 = false;
         var3 = 0;
      }

      var1 = (var1 + var3) * 31;
      byte var4 = lllllllllllllllllllIlIlIlllIlIlI.allowMessages;
      if (var4 != 0) {
         var10002 = false;
         var4 = 1;
      }

      return var1 + var4;
   }

   // $FF: synthetic method
   public static ServerLoginMojangPacket copy$default(ServerLoginMojangPacket lllllllllllllllllllIlIlIllllIIII, String var1, UUID var2, boolean var3, int lllllllllllllllllllIlIlIlllIllII, Object var5) {
      if ((lllllllllllllllllllIlIlIlllIllII & 1) != 0) {
         var1 = lllllllllllllllllllIlIlIllllIIII.name;
      }

      if ((lllllllllllllllllllIlIlIlllIllII & 2) != 0) {
         var2 = lllllllllllllllllllIlIlIllllIIII.uuid;
      }

      if ((lllllllllllllllllllIlIlIlllIllII & 4) != 0) {
         var3 = lllllllllllllllllllIlIlIllllIIII.allowMessages;
      }

      return lllllllllllllllllllIlIlIllllIIII.copy(var1, var2, var3);
   }
}
