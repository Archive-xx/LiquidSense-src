package net.ccbluex.liquidbounce.chat.packet;

import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/packet/SerializedPacket;", "", "packetName", "", "packetContent", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "(Ljava/lang/String;Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;)V", "getPacketContent", "()Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "getPacketName", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "LiquidSense"}
)
public final class SerializedPacket {
   // $FF: synthetic field
   @SerializedName("c")
   @Nullable
   private final Packet packetContent;
   // $FF: synthetic field
   @SerializedName("m")
   @NotNull
   private final String packetName;

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof SerializedPacket) {
            SerializedPacket var2 = (SerializedPacket)var1;
            if (Intrinsics.areEqual((Object)this.packetName, (Object)var2.packetName) && Intrinsics.areEqual((Object)this.packetContent, (Object)var2.packetContent)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   // $FF: synthetic method
   public static SerializedPacket copy$default(SerializedPacket llllllllllllllllllIIlIIIIlIIlIII, String var1, Packet var2, int llllllllllllllllllIIlIIIIlIIIlIl, Object var4) {
      if ((llllllllllllllllllIIlIIIIlIIIlIl & 1) != 0) {
         var1 = llllllllllllllllllIIlIIIIlIIlIII.packetName;
      }

      if ((llllllllllllllllllIIlIIIIlIIIlIl & 2) != 0) {
         var2 = llllllllllllllllllIIlIIIIlIIlIII.packetContent;
      }

      return llllllllllllllllllIIlIIIIlIIlIII.copy(var1, var2);
   }

   public SerializedPacket(@NotNull String llllllllllllllllllIIlIIIIlIllllI, @Nullable Packet llllllllllllllllllIIlIIIIlIlllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIIIIlIllllI, "packetName");
      super();
      llllllllllllllllllIIlIIIIllIIIII.packetName = llllllllllllllllllIIlIIIIlIllllI;
      llllllllllllllllllIIlIIIIllIIIII.packetContent = llllllllllllllllllIIlIIIIlIlllII;
   }

   public int hashCode() {
      String var10000 = llllllllllllllllllIIlIIIIlIIIIll.packetName;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      Packet var2 = llllllllllllllllllIIlIIIIlIIIIll.packetContent;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         boolean var10002 = false;
         var3 = 0;
      }

      return var1 + var3;
   }

   @NotNull
   public final String component1() {
      return llllllllllllllllllIIlIIIIlIllIII.packetName;
   }

   @Nullable
   public final Packet getPacketContent() {
      return llllllllllllllllllIIlIIIIlllIlll.packetContent;
   }

   @NotNull
   public final String getPacketName() {
      return llllllllllllllllllIIlIIIIllllIll.packetName;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("SerializedPacket(packetName=").append(this.packetName).append(", packetContent=").append(this.packetContent).append(")"));
   }

   @NotNull
   public final SerializedPacket copy(@NotNull String llllllllllllllllllIIlIIIIlIIlllI, @Nullable Packet llllllllllllllllllIIlIIIIlIIllIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIIIIlIIlllI, "packetName");
      return new SerializedPacket(llllllllllllllllllIIlIIIIlIIlllI, llllllllllllllllllIIlIIIIlIIllIl);
   }

   @Nullable
   public final Packet component2() {
      return llllllllllllllllllIIlIIIIlIlIlII.packetContent;
   }
}
