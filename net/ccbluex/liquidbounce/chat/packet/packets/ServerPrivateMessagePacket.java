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
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fHÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0013"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/packets/ServerPrivateMessagePacket;", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "receiver", "", "content", "(Ljava/lang/String;Ljava/lang/String;)V", "getContent", "()Ljava/lang/String;", "getReceiver", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "LiquidSense"}
)
public final class ServerPrivateMessagePacket implements Packet {
   // $FF: synthetic field
   @SerializedName("content")
   @NotNull
   private final String content;
   // $FF: synthetic field
   @SerializedName("receiver")
   @NotNull
   private final String receiver;

   public ServerPrivateMessagePacket(@NotNull String lllIlIIIlllllll, @NotNull String lllIlIIlIIIIIIl) {
      Intrinsics.checkParameterIsNotNull(lllIlIIIlllllll, "receiver");
      Intrinsics.checkParameterIsNotNull(lllIlIIlIIIIIIl, "content");
      super();
      lllIlIIlIIIIIII.receiver = lllIlIIIlllllll;
      lllIlIIlIIIIIII.content = lllIlIIlIIIIIIl;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof ServerPrivateMessagePacket) {
            ServerPrivateMessagePacket var2 = (ServerPrivateMessagePacket)var1;
            if (Intrinsics.areEqual((Object)this.receiver, (Object)var2.receiver) && Intrinsics.areEqual((Object)this.content, (Object)var2.content)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @NotNull
   public final ServerPrivateMessagePacket copy(@NotNull String lllIlIIIlllIIlI, @NotNull String lllIlIIIlllIIll) {
      Intrinsics.checkParameterIsNotNull(lllIlIIIlllIIlI, "receiver");
      Intrinsics.checkParameterIsNotNull(lllIlIIIlllIIll, "content");
      return new ServerPrivateMessagePacket(lllIlIIIlllIIlI, lllIlIIIlllIIll);
   }

   @NotNull
   public final String component2() {
      return lllIlIIIllllIIl.content;
   }

   @NotNull
   public final String component1() {
      return lllIlIIIllllIll.receiver;
   }

   public int hashCode() {
      String var10000 = lllIlIIIllIIlll.receiver;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      String var2 = lllIlIIIllIIlll.content;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         boolean var10002 = false;
         var3 = 0;
      }

      return var1 + var3;
   }

   // $FF: synthetic method
   public static ServerPrivateMessagePacket copy$default(ServerPrivateMessagePacket lllIlIIIllIllII, String var1, String var2, int lllIlIIIllIlIIl, Object var4) {
      if ((lllIlIIIllIlIIl & 1) != 0) {
         var1 = lllIlIIIllIllII.receiver;
      }

      if ((lllIlIIIllIlIIl & 2) != 0) {
         var2 = lllIlIIIllIllII.content;
      }

      return lllIlIIIllIllII.copy(var1, var2);
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("ServerPrivateMessagePacket(receiver=").append(this.receiver).append(", content=").append(this.content).append(")"));
   }

   @NotNull
   public final String getReceiver() {
      return lllIlIIlIIIlIlI.receiver;
   }

   @NotNull
   public final String getContent() {
      return lllIlIIlIIIIlll.content;
   }
}
