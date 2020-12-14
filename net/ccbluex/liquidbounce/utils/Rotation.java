//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u000e\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cJ\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\t¨\u0006\u001f"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/Rotation;", "", "yaw", "", "pitch", "(FF)V", "getPitch", "()F", "setPitch", "(F)V", "getYaw", "setYaw", "applyStrafeToPlayer", "", "event", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "component1", "component2", "copy", "equals", "", "other", "fixedSensitivity", "sensitivity", "hashCode", "", "toPlayer", "player", "Lnet/minecraft/entity/player/EntityPlayer;", "toString", "", "LiquidSense"}
)
public final class Rotation {
   // $FF: synthetic field
   private float yaw;
   // $FF: synthetic field
   private float pitch;

   public final void setPitch(float lllIllIllIIlII) {
      lllIllIllIIlll.pitch = lllIllIllIIlII;
   }

   public final float component2() {
      return lllIllIlIlIllI.pitch;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("Rotation(yaw=").append(this.yaw).append(", pitch=").append(this.pitch).append(")"));
   }

   // $FF: synthetic method
   public static Rotation copy$default(Rotation lllIllIlIIlIIl, float var1, float var2, int lllIllIlIIIllI, Object var4) {
      if ((lllIllIlIIIllI & 1) != 0) {
         var1 = lllIllIlIIlIIl.yaw;
      }

      if ((lllIllIlIIIllI & 2) != 0) {
         var2 = lllIllIlIIlIIl.pitch;
      }

      return lllIllIlIIlIIl.copy(var1, var2);
   }

   public Rotation(float lllIllIlIlllII, float lllIllIlIllIll) {
      lllIllIllIIIII.yaw = lllIllIlIlllII;
      lllIllIllIIIII.pitch = lllIllIlIllIll;
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof Rotation) {
            Rotation var2 = (Rotation)var1;
            if (Float.compare(this.yaw, var2.yaw) == 0 && Float.compare(this.pitch, var2.pitch) == 0) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public final float getYaw() {
      return lllIllIlllIIll.yaw;
   }

   @NotNull
   public final Rotation copy(float lllIllIlIlIIIl, float lllIllIlIlIIII) {
      return new Rotation(lllIllIlIlIIIl, lllIllIlIlIIII);
   }

   public final void applyStrafeToPlayer(@NotNull StrafeEvent lllIlllIIIIIll) {
      Intrinsics.checkParameterIsNotNull(lllIlllIIIIIll, "event");
      EntityPlayerSP lllIlllIIIIlIl = MinecraftInstance.mc.thePlayer;
      int lllIlllIIIIllI = (int)((MathHelper.wrapAngleTo180_float(lllIlllIIIIlIl.rotationYaw - lllIlllIIIIlII.yaw - 23.5F - (float)135) + (float)180) / (float)45);
      float lllIlllIIIIlll = lllIlllIIIIlII.yaw;
      double lllIllIlllllIl = lllIlllIIIIIll.getStrafe();
      float lllIlllIIIlIIl = lllIlllIIIIIll.getForward();
      int lllIllIllllIll = lllIlllIIIIIll.getFriction();
      long lllIllIllllIlI = 0.0F;
      float lllIlllIIIllII = 0.0F;
      switch(lllIlllIIIIllI) {
      case 0:
         lllIllIllllIlI = lllIlllIIIlIIl;
         lllIlllIIIllII = lllIllIlllllIl;
         break;
      case 1:
         lllIllIllllIlI += lllIlllIIIlIIl;
         lllIlllIIIllII -= lllIlllIIIlIIl;
         lllIllIllllIlI += lllIllIlllllIl;
         lllIlllIIIllII += lllIllIlllllIl;
         break;
      case 2:
         lllIllIllllIlI = lllIllIlllllIl;
         lllIlllIIIllII = -lllIlllIIIlIIl;
         break;
      case 3:
         lllIllIllllIlI -= lllIlllIIIlIIl;
         lllIlllIIIllII -= lllIlllIIIlIIl;
         lllIllIllllIlI += lllIllIlllllIl;
         lllIlllIIIllII -= lllIllIlllllIl;
         break;
      case 4:
         lllIllIllllIlI = -lllIlllIIIlIIl;
         lllIlllIIIllII = -lllIllIlllllIl;
         break;
      case 5:
         lllIllIllllIlI -= lllIlllIIIlIIl;
         lllIlllIIIllII += lllIlllIIIlIIl;
         lllIllIllllIlI -= lllIllIlllllIl;
         lllIlllIIIllII -= lllIllIlllllIl;
         break;
      case 6:
         lllIllIllllIlI = -lllIllIlllllIl;
         lllIlllIIIllII = lllIlllIIIlIIl;
         break;
      case 7:
         lllIllIllllIlI += lllIlllIIIlIIl;
         lllIlllIIIllII += lllIlllIIIlIIl;
         lllIllIllllIlI -= lllIllIlllllIl;
         lllIlllIIIllII += lllIllIlllllIl;
      }

      if (lllIllIllllIlI > 1.0F || lllIllIllllIlI < 0.9F && lllIllIllllIlI > 0.3F || lllIllIllllIlI < -1.0F || lllIllIllllIlI > -0.9F && lllIllIllllIlI < -0.3F) {
         lllIllIllllIlI *= 0.5F;
      }

      if (lllIlllIIIllII > 1.0F || lllIlllIIIllII < 0.9F && lllIlllIIIllII > 0.3F || lllIlllIIIllII < -1.0F || lllIlllIIIllII > -0.9F && lllIlllIIIllII < -0.3F) {
         lllIlllIIIllII *= 0.5F;
      }

      float lllIlllIIIllIl = lllIlllIIIllII * lllIlllIIIllII + lllIllIllllIlI * lllIllIllllIlI;
      if (lllIlllIIIllIl >= 1.0E-4F) {
         lllIlllIIIllIl = MathHelper.sqrt_float(lllIlllIIIllIl);
         if (lllIlllIIIllIl < 1.0F) {
            lllIlllIIIllIl = 1.0F;
         }

         lllIlllIIIllIl = lllIllIllllIll / lllIlllIIIllIl;
         lllIlllIIIllII *= lllIlllIIIllIl;
         lllIllIllllIlI *= lllIlllIIIllIl;
         float lllIllIlllIlll = MathHelper.sin((float)((double)lllIlllIIIIlll * 3.141592653589793D / (double)180.0F));
         int lllIllIlllIllI = MathHelper.cos((float)((double)lllIlllIIIIlll * 3.141592653589793D / (double)180.0F));
         lllIlllIIIIlIl.motionX += (double)(lllIlllIIIllII * lllIllIlllIllI) - (double)lllIllIllllIlI * (double)lllIllIlllIlll;
         lllIlllIIIIlIl.motionZ += (double)(lllIllIllllIlI * lllIllIlllIllI) + (double)lllIlllIIIllII * (double)lllIllIlllIlll;
      }

   }

   public int hashCode() {
      return Float.hashCode(this.yaw) * 31 + Float.hashCode(this.pitch);
   }

   public final void setYaw(float lllIllIllIllIl) {
      lllIllIllIlllI.yaw = lllIllIllIllIl;
   }

   public final float component1() {
      return lllIllIlIllIII.yaw;
   }

   public final void fixedSensitivity(float lllIlllIlIIIIl) {
      float lllIlllIlIIIll = lllIlllIlIIIIl * 0.6F + 0.2F;
      char lllIlllIIlllIl = lllIlllIlIIIll * lllIlllIlIIIll * lllIlllIlIIIll * 1.2F;
      lllIlllIlIIIlI.yaw -= lllIlllIlIIIlI.yaw % lllIlllIIlllIl;
      lllIlllIlIIIlI.pitch -= lllIlllIlIIIlI.pitch % lllIlllIIlllIl;
   }

   public final float getPitch() {
      return lllIllIllIlIlI.pitch;
   }

   public final void toPlayer(@NotNull EntityPlayer lllIlllIlIlIll) {
      Intrinsics.checkParameterIsNotNull(lllIlllIlIlIll, "player");
      double lllIlllIlIlIlI = lllIlllIlIlllI.yaw;
      byte lllIlllIlIlIIl = false;
      if (!Float.isNaN(lllIlllIlIlIlI)) {
         lllIlllIlIlIlI = lllIlllIlIlllI.pitch;
         lllIlllIlIlIIl = false;
         if (!Float.isNaN(lllIlllIlIlIlI)) {
            lllIlllIlIlllI.fixedSensitivity(MinecraftInstance.mc.gameSettings.mouseSensitivity);
            lllIlllIlIlIll.rotationYaw = lllIlllIlIlllI.yaw;
            lllIlllIlIlIll.rotationPitch = lllIlllIlIlllI.pitch;
            return;
         }
      }

   }
}
