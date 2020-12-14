package net.ccbluex.liquidbounce.ui.client.hud.element;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0006\u0010\u0012\u001a\u00020\u0013J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "", "x", "", "y", "x2", "y2", "(FFFF)V", "getX", "()F", "getX2", "getY", "getY2", "component1", "component2", "component3", "component4", "copy", "draw", "", "equals", "", "other", "hashCode", "", "toString", "", "LiquidSense"}
)
public final class Border {
   // $FF: synthetic field
   private final float x2;
   // $FF: synthetic field
   private final float y2;
   // $FF: synthetic field
   private final float x;
   // $FF: synthetic field
   private final float y;

   public int hashCode() {
      return ((Float.hashCode(this.x) * 31 + Float.hashCode(this.y)) * 31 + Float.hashCode(this.x2)) * 31 + Float.hashCode(this.y2);
   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof Border) {
            Border var2 = (Border)var1;
            if (Float.compare(this.x, var2.x) == 0 && Float.compare(this.y, var2.y) == 0 && Float.compare(this.x2, var2.x2) == 0 && Float.compare(this.y2, var2.y2) == 0) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public final void draw() {
      RenderUtils.drawBorderedRect(lIlIllIIIIlI.x, lIlIllIIIIlI.y, lIlIllIIIIlI.x2, lIlIllIIIIlI.y2, 3.0F, Integer.MIN_VALUE, 0);
   }

   public final float component2() {
      return lIlIlIlIIIIl.y;
   }

   public final float component4() {
      return lIlIlIIlllII.y2;
   }

   public final float getY2() {
      return lIlIlIllIllI.y2;
   }

   // $FF: synthetic method
   public static Border copy$default(Border lIlIlIIIIlll, float var1, float var2, float var3, float var4, int lIlIlIIIIIlI, Object var6) {
      if ((lIlIlIIIIIlI & 1) != 0) {
         var1 = lIlIlIIIIlll.x;
      }

      if ((lIlIlIIIIIlI & 2) != 0) {
         var2 = lIlIlIIIIlll.y;
      }

      if ((lIlIlIIIIIlI & 4) != 0) {
         var3 = lIlIlIIIIlll.x2;
      }

      if ((lIlIlIIIIIlI & 8) != 0) {
         var4 = lIlIlIIIIlll.y2;
      }

      return lIlIlIIIIlll.copy(var1, var2, var3, var4);
   }

   @NotNull
   public final Border copy(float lIlIlIIlIIIl, float lIlIlIIlIIII, float lIlIlIIlIIll, float lIlIlIIlIIlI) {
      return new Border(lIlIlIIlIIIl, lIlIlIIlIIII, lIlIlIIlIIll, lIlIlIIlIIlI);
   }

   public final float getX2() {
      return lIlIlIlllIlI.x2;
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("Border(x=").append(this.x).append(", y=").append(this.y).append(", x2=").append(this.x2).append(", y2=").append(this.y2).append(")"));
   }

   public final float component1() {
      return lIlIlIlIIlII.x;
   }

   public final float getX() {
      return lIlIlIllllll.x;
   }

   public Border(float lIlIlIlIllll, float lIlIlIlIlIIl, float lIlIlIlIllIl, float lIlIlIlIIlll) {
      lIlIlIlIlIll.x = lIlIlIlIllll;
      lIlIlIlIlIll.y = lIlIlIlIlIIl;
      lIlIlIlIlIll.x2 = lIlIlIlIllIl;
      lIlIlIlIlIll.y2 = lIlIlIlIIlll;
   }

   public final float getY() {
      return lIlIlIllllII.y;
   }

   public final float component3() {
      return lIlIlIIlllll.x2;
   }
}
