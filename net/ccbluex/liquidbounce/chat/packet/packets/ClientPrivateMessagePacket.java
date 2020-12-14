package net.ccbluex.liquidbounce.chat.packet.packets;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.chat.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ClientPrivateMessagePacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "id", "", "user", "Lnet/ccbluex/liquidbounce/chat/User;", "content", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/chat/User;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "getId", "getUser", "()Lnet/ccbluex/liquidbounce/chat/User;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidSense"}
)
public final class ClientPrivateMessagePacket implements Packet {
   // $FF: synthetic field
   @SerializedName("author_info")
   @NotNull
   private final User user;
   // $FF: synthetic field
   @SerializedName("author_id")
   @NotNull
   private final String id;
   // $FF: synthetic field
   @SerializedName("content")
   @NotNull
   private final String content;

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("ClientPrivateMessagePacket(id=").append(this.id).append(", user=").append(this.user).append(", content=").append(this.content).append(")"));
   }

   public ClientPrivateMessagePacket(@NotNull String lllllllllllllllllllIllllIllllIlI, @NotNull User lllllllllllllllllllIllllIlllIlIl, @NotNull String lllllllllllllllllllIllllIllllIII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllllIllllIlI, "id");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllllIlllIlIl, "user");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllllIllllIII, "content");
      super();
      lllllllllllllllllllIllllIllllIll.id = lllllllllllllllllllIllllIllllIlI;
      lllllllllllllllllllIllllIllllIll.user = lllllllllllllllllllIllllIlllIlIl;
      lllllllllllllllllllIllllIllllIll.content = lllllllllllllllllllIllllIllllIII;
   }

   // $FF: synthetic method
   public static ClientPrivateMessagePacket copy$default(ClientPrivateMessagePacket lllllllllllllllllllIllllIlIllIll, String var1, User var2, String var3, int lllllllllllllllllllIllllIlIlIlll, Object var5) {
      if ((lllllllllllllllllllIllllIlIlIlll & 1) != 0) {
         var1 = lllllllllllllllllllIllllIlIllIll.id;
      }

      if ((lllllllllllllllllllIllllIlIlIlll & 2) != 0) {
         var2 = lllllllllllllllllllIllllIlIllIll.user;
      }

      if ((lllllllllllllllllllIllllIlIlIlll & 4) != 0) {
         var3 = lllllllllllllllllllIllllIlIllIll.content;
      }

      return lllllllllllllllllllIllllIlIllIll.copy(var1, var2, var3);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ClientPrivateMessagePacket) {
            ClientPrivateMessagePacket var2 = (ClientPrivateMessagePacket)var1;
            if (Intrinsics.areEqual((Object)this.id, (Object)var2.id) && Intrinsics.areEqual((Object)this.user, (Object)var2.user) && Intrinsics.areEqual((Object)this.content, (Object)var2.content)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @NotNull
   public final ClientPrivateMessagePacket copy(@NotNull String lllllllllllllllllllIllllIllIIIll, @NotNull User lllllllllllllllllllIllllIllIIlIl, @NotNull String lllllllllllllllllllIllllIllIIlII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllllIllIIIll, "id");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllllIllIIlIl, "user");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllllIllIIlII, "content");
      return new ClientPrivateMessagePacket(lllllllllllllllllllIllllIllIIIll, lllllllllllllllllllIllllIllIIlIl, lllllllllllllllllllIllllIllIIlII);
   }

   @NotNull
   public final User component2() {
      return lllllllllllllllllllIllllIllIllll.user;
   }

   @NotNull
   public final User getUser() {
      return lllllllllllllllllllIlllllIIIIlII.user;
   }

   @NotNull
   public final String getContent() {
      return lllllllllllllllllllIlllllIIIIIII.content;
   }

   public int hashCode() {
      String var10000 = lllllllllllllllllllIllllIlIlIlIl.id;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      User var2 = lllllllllllllllllllIllllIlIlIlIl.user;
      boolean var10002;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         var10002 = false;
         var3 = 0;
      }

      var1 = (var1 + var3) * 31;
      String var4 = lllllllllllllllllllIllllIlIlIlIl.content;
      if (var4 != null) {
         var3 = var4.hashCode();
      } else {
         var10002 = false;
         var3 = 0;
      }

      return var1 + var3;
   }

   @NotNull
   public final String getId() {
      return lllllllllllllllllllIlllllIIIIllI.id;
   }

   @NotNull
   public final String component3() {
      return lllllllllllllllllllIllllIllIllII.content;
   }

   @NotNull
   public final String component1() {
      return lllllllllllllllllllIllllIlllIIlI.id;
   }
}
