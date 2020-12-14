package net.ccbluex.liquidbounce.chat;

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
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/User;", "", "name", "", "uuid", "Ljava/util/UUID;", "(Ljava/lang/String;Ljava/util/UUID;)V", "getName", "()Ljava/lang/String;", "getUuid", "()Ljava/util/UUID;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "LiquidSense"}
)
public final class User {
   // $FF: synthetic field
   @SerializedName("uuid")
   @NotNull
   private final UUID uuid;
   // $FF: synthetic field
   @SerializedName("name")
   @NotNull
   private final String name;

   @NotNull
   public final String getName() {
      return lIIllIIIlllIIIl.name;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof User) {
            User var2 = (User)var1;
            if (Intrinsics.areEqual((Object)this.name, (Object)var2.name) && Intrinsics.areEqual((Object)this.uuid, (Object)var2.uuid)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   @NotNull
   public final User copy(@NotNull String lIIllIIIlIllIlI, @NotNull UUID lIIllIIIlIllIIl) {
      Intrinsics.checkParameterIsNotNull(lIIllIIIlIllIlI, "name");
      Intrinsics.checkParameterIsNotNull(lIIllIIIlIllIIl, "uuid");
      return new User(lIIllIIIlIllIlI, lIIllIIIlIllIIl);
   }

   public User(@NotNull String lIIllIIIllIIlIl, @NotNull UUID lIIllIIIllIIlII) {
      Intrinsics.checkParameterIsNotNull(lIIllIIIllIIlIl, "name");
      Intrinsics.checkParameterIsNotNull(lIIllIIIllIIlII, "uuid");
      super();
      lIIllIIIllIlIIl.name = lIIllIIIllIIlIl;
      lIIllIIIllIlIIl.uuid = lIIllIIIllIIlII;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("User(name=").append(this.name).append(", uuid=").append(this.uuid).append(")"));
   }

   @NotNull
   public final UUID component2() {
      return lIIllIIIlIlllll.uuid;
   }

   @NotNull
   public final UUID getUuid() {
      return lIIllIIIllIllIl.uuid;
   }

   // $FF: synthetic method
   public static User copy$default(User lIIllIIIlIlIIlI, String var1, UUID var2, int lIIllIIIlIIllll, Object var4) {
      if ((lIIllIIIlIIllll & 1) != 0) {
         var1 = lIIllIIIlIlIIlI.name;
      }

      if ((lIIllIIIlIIllll & 2) != 0) {
         var2 = lIIllIIIlIlIIlI.uuid;
      }

      return lIIllIIIlIlIIlI.copy(var1, var2);
   }

   @NotNull
   public final String component1() {
      return lIIllIIIllIIIlI.name;
   }

   public int hashCode() {
      String var10000 = lIIllIIIlIIIlll.name;
      int var1;
      if (var10000 != null) {
         var1 = var10000.hashCode();
      } else {
         boolean var10001 = false;
         var1 = 0;
      }

      var1 *= 31;
      UUID var2 = lIIllIIIlIIIlll.uuid;
      int var3;
      if (var2 != null) {
         var3 = var2.hashCode();
      } else {
         boolean var10002 = false;
         var3 = 0;
      }

      return var1 + var3;
   }
}
