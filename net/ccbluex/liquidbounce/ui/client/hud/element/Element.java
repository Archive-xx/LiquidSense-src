package net.ccbluex.liquidbounce.ui.client.hud.element;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010>\u001a\u00020\u0011H\u0016J\b\u0010?\u001a\u00020@H\u0016J\n\u0010A\u001a\u0004\u0018\u00010\u000bH&J\u0018\u0010B\u001a\u00020@2\u0006\u0010C\u001a\u00020D2\u0006\u0010E\u001a\u00020FH\u0016J \u0010G\u001a\u00020@2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010H\u001a\u00020FH\u0016J\u0018\u0010I\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0016J\b\u0010J\u001a\u00020@H\u0016R\u001c\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010#\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R$\u0010'\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R$\u0010,\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b-\u0010)\"\u0004\b.\u0010+R&\u0010\u0005\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010 \"\u0004\b0\u0010\"R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001e\u00105\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u000307068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u00109R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010)\"\u0004\b;\u0010+R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010)\"\u0004\b=\u0010+¨\u0006K"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "border", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "getBorder", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setBorder", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;)V", "drag", "", "getDrag", "()Z", "setDrag", "(Z)V", "info", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "getInfo", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/ElementInfo;", "name", "", "getName", "()Ljava/lang/String;", "prevMouseX", "getPrevMouseX", "()F", "setPrevMouseX", "(F)V", "prevMouseY", "getPrevMouseY", "setPrevMouseY", "value", "renderX", "getRenderX", "()D", "setRenderX", "(D)V", "renderY", "getRenderY", "setRenderY", "getScale", "setScale", "getSide", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "setSide", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "getX", "setX", "getY", "setY", "createElement", "destroyElement", "", "drawElement", "handleKey", "c", "", "keyCode", "", "handleMouseClick", "mouseButton", "isInBorder", "updateElement", "LiquidSense"}
)
public abstract class Element extends MinecraftInstance {
   // $FF: synthetic field
   @NotNull
   private Side side;
   // $FF: synthetic field
   private float scale;
   // $FF: synthetic field
   private float prevMouseX;
   // $FF: synthetic field
   private boolean drag;
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   @Nullable
   private Border border;
   // $FF: synthetic field
   @NotNull
   private final ElementInfo info;
   // $FF: synthetic field
   private float prevMouseY;
   // $FF: synthetic field
   private double y;

   public final double getX() {
      return lllllllllllllllllllIIIIIIlllIlII.x;
   }

   // $FF: synthetic method
   public Element(double var1, double var3, float var5, Side var6, int lllllllllllllllllllIIIIIIIllIllI, DefaultConstructorMarker var8) {
      if ((lllllllllllllllllllIIIIIIIllIllI & 1) != 0) {
         var1 = 2.0D;
      }

      if ((lllllllllllllllllllIIIIIIIllIllI & 2) != 0) {
         var3 = 2.0D;
      }

      if ((lllllllllllllllllllIIIIIIIllIllI & 4) != 0) {
         var5 = 1.0F;
      }

      if ((lllllllllllllllllllIIIIIIIllIllI & 8) != 0) {
         var6 = Side.Companion.default();
      }

      this(var1, var3, var5, var6);
   }

   public final void setX(double lllllllllllllllllllIIIIIIllIlIll) {
      lllllllllllllllllllIIIIIIllIlIIl.x = lllllllllllllllllllIIIIIIllIlIll;
   }

   public final void setBorder(@Nullable Border lllllllllllllllllllIIIIlIIllIIll) {
      lllllllllllllllllllIIIIlIIllIlII.border = lllllllllllllllllllIIIIlIIllIIll;
   }

   public final boolean getDrag() {
      return lllllllllllllllllllIIIIlIIllIIII.drag;
   }

   public final float getScale() {
      return lllllllllllllllllllIIIIlIllllIII.info.disableScale() ? 1.0F : lllllllllllllllllllIIIIlIllllIII.scale;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public void updateElement() {
   }

   public boolean isInBorder(double lllllllllllllllllllIIIIIlIIllllI, double lllllllllllllllllllIIIIIlIIlIlIl) {
      Border var10000 = lllllllllllllllllllIIIIIlIlIIIII.border;
      if (var10000 == null) {
         boolean var10001 = false;
         return false;
      } else {
         long lllllllllllllllllllIIIIIlIIlIIll = var10000;
         int lllllllllllllllllllIIIIIlIlIlIII = lllllllllllllllllllIIIIIlIIlIIll.getX();
         short lllllllllllllllllllIIIIIlIIIlIlI = lllllllllllllllllllIIIIIlIIlIIll.getX2();
         short lllllllllllllllllllIIIIIlIIIIllI = false;
         double lllllllllllllllllllIIIIIlIIlIIII = Math.min(lllllllllllllllllllIIIIIlIlIlIII, lllllllllllllllllllIIIIIlIIIlIlI);
         lllllllllllllllllllIIIIIlIIIlIlI = lllllllllllllllllllIIIIIlIIlIIll.getY();
         short lllllllllllllllllllIIIIIlIlIlIll = lllllllllllllllllllIIIIIlIIlIIll.getY2();
         boolean lllllllllllllllllllIIIIIlIIIIIll = false;
         lllllllllllllllllllIIIIIlIlIlIII = Math.min(lllllllllllllllllllIIIIIlIIIlIlI, lllllllllllllllllllIIIIIlIlIlIll);
         lllllllllllllllllllIIIIIlIlIlIll = lllllllllllllllllllIIIIIlIIlIIll.getX();
         boolean lllllllllllllllllllIIIIIlIIIIIll = lllllllllllllllllllIIIIIlIIlIIll.getX2();
         short lllllllllllllllllllIIIIIlIIIIIlI = false;
         lllllllllllllllllllIIIIIlIIIlIlI = Math.max(lllllllllllllllllllIIIIIlIlIlIll, lllllllllllllllllllIIIIIlIIIIIll);
         lllllllllllllllllllIIIIIlIIIIIll = lllllllllllllllllllIIIIIlIIlIIll.getY();
         short lllllllllllllllllllIIIIIlIIIIIlI = lllllllllllllllllllIIIIIlIIlIIll.getY2();
         boolean lllllllllllllllllllIIIIIlIIIIIIl = false;
         lllllllllllllllllllIIIIIlIlIlIll = Math.max(lllllllllllllllllllIIIIIlIIIIIll, lllllllllllllllllllIIIIIlIIIIIlI);
         return (double)lllllllllllllllllllIIIIIlIIlIIII <= lllllllllllllllllllIIIIIlIIllllI && (double)lllllllllllllllllllIIIIIlIlIlIII <= lllllllllllllllllllIIIIIlIIlIlIl && (double)lllllllllllllllllllIIIIIlIIIlIlI >= lllllllllllllllllllIIIIIlIIllllI && (double)lllllllllllllllllllIIIIIlIlIlIll >= lllllllllllllllllllIIIIIlIIlIlIl;
      }
   }

   public void handleMouseClick(double lllllllllllllllllllIIIIIIlllllll, double lllllllllllllllllllIIIIIIllllllI, int lllllllllllllllllllIIIIIIlllllIl) {
   }

   public final void setDrag(boolean lllllllllllllllllllIIIIlIIlIlIlI) {
      lllllllllllllllllllIIIIlIIlIllIl.drag = lllllllllllllllllllIIIIlIIlIlIlI;
   }

   @NotNull
   public final Side getSide() {
      return lllllllllllllllllllIIIIIIlIlIlll.side;
   }

   public final void setPrevMouseX(float lllllllllllllllllllIIIIlIIlIIIIl) {
      lllllllllllllllllllIIIIlIIlIIlII.prevMouseX = lllllllllllllllllllIIIIlIIlIIIIl;
   }

   @NotNull
   public final ElementInfo getInfo() {
      return lllllllllllllllllllIIIIlIlllllII.info;
   }

   @Nullable
   public final Border getBorder() {
      return lllllllllllllllllllIIIIlIIlllIlI.border;
   }

   public final float getPrevMouseY() {
      return lllllllllllllllllllIIIIlIIIllllI.prevMouseY;
   }

   public final void setY(double lllllllllllllllllllIIIIIIlIllIlI) {
      lllllllllllllllllllIIIIIIlIllIll.y = lllllllllllllllllllIIIIIIlIllIlI;
   }

   public boolean createElement() {
      return true;
   }

   @NotNull
   public final String getName() {
      return lllllllllllllllllllIIIIlIlIlllII.info.name();
   }

   public final float getPrevMouseX() {
      return lllllllllllllllllllIIIIlIIlIIlll.prevMouseX;
   }

   public void handleKey(char lllllllllllllllllllIIIIIIllllIlI, int lllllllllllllllllllIIIIIIllllIIl) {
   }

   public final double getRenderY() {
      // $FF: Couldn't be decompiled
   }

   public final void setSide(@NotNull Side lllllllllllllllllllIIIIIIlIlIIll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIIIIlIlIIll, "<set-?>");
      lllllllllllllllllllIIIIIIlIlIIlI.side = lllllllllllllllllllIIIIIIlIlIIll;
   }

   public Element() {
      this(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
   }

   public final void setRenderY(double lllllllllllllllllllIIIIlIIlllllI) {
      // $FF: Couldn't be decompiled
   }

   public final void setRenderX(double lllllllllllllllllllIIIIlIlIIIlIl) {
      // $FF: Couldn't be decompiled
   }

   @Nullable
   public abstract Border drawElement();

   public final void setScale(float lllllllllllllllllllIIIIlIllIlIlI) {
      if (!lllllllllllllllllllIIIIlIllIllII.info.disableScale()) {
         lllllllllllllllllllIIIIlIllIllII.scale = lllllllllllllllllllIIIIlIllIlIlI;
      }
   }

   public Element(double lllllllllllllllllllIIIIIIlIIlIlI, double lllllllllllllllllllIIIIIIlIIIlII, float lllllllllllllllllllIIIIIIlIIlIII, @NotNull Side lllllllllllllllllllIIIIIIlIIIIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIIIIlIIIIlI, "side");
      super();
      lllllllllllllllllllIIIIIIlIIlIll.x = lllllllllllllllllllIIIIIIlIIlIlI;
      lllllllllllllllllllIIIIIIlIIlIll.y = lllllllllllllllllllIIIIIIlIIIlII;
      lllllllllllllllllllIIIIIIlIIlIll.side = lllllllllllllllllllIIIIIIlIIIIlI;
      ElementInfo var10001 = (ElementInfo)lllllllllllllllllllIIIIIIlIIlIll.getClass().getAnnotation(ElementInfo.class);
      if (var10001 != null) {
         lllllllllllllllllllIIIIIIlIIlIll.info = var10001;
         lllllllllllllllllllIIIIIIlIIlIll.scale = 1.0F;
         lllllllllllllllllllIIIIIIlIIlIll.setScale(lllllllllllllllllllIIIIIIlIIlIII);
      } else {
         boolean var10002 = false;
         throw (Throwable)(new IllegalArgumentException("Passed element with missing element info"));
      }
   }

   public void destroyElement() {
   }

   @NotNull
   public List<Value<?>> getValues() {
      Field[] var10000 = lllllllllllllllllllIIIIIlllIlllI.getClass().getDeclaredFields();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "javaClass.declaredFields");
      Object[] lllllllllllllllllllIIIIIlllllIIl = var10000;
      boolean lllllllllllllllllllIIIIIlllIlIIl = false;
      boolean lllllllllllllllllllIIIIIllllIlIl = (Collection)(new ArrayList(lllllllllllllllllllIIIIIlllllIIl.length));
      Exception lllllllllllllllllllIIIIIllllIIll = false;
      boolean lllllllllllllllllllIIIIIlllIIIll = lllllllllllllllllllIIIIIlllllIIl;
      int lllllllllllllllllllIIIIIlllIIIIl = lllllllllllllllllllIIIIIlllllIIl.length;

      boolean var10001;
      for(int lllllllllllllllllllIIIIIllIlllll = 0; lllllllllllllllllllIIIIIllIlllll < lllllllllllllllllllIIIIIlllIIIIl; ++lllllllllllllllllllIIIIIllIlllll) {
         Object lllllllllllllllllllIIIIIllllllIl = lllllllllllllllllllIIIIIlllIIIll[lllllllllllllllllllIIIIIllIlllll];
         int lllllllllllllllllllIIIIIlllllllI = false;
         Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIIIIIllllllIl, "valueField");
         lllllllllllllllllllIIIIIllllllIl.setAccessible(true);
         byte lllllllllllllllllllIIIIIllIlIlIl = lllllllllllllllllllIIIIIllllllIl.get(lllllllllllllllllllIIIIIlllIlllI);
         lllllllllllllllllllIIIIIllllIlIl.add(lllllllllllllllllllIIIIIllIlIlIl);
         var10001 = false;
      }

      Iterable lllllllllllllllllllIIIIIllllIIIl = (Iterable)((List)lllllllllllllllllllIIIIIllllIlIl);
      lllllllllllllllllllIIIIIlllIlIIl = false;
      lllllllllllllllllllIIIIIllllIlIl = (Collection)(new ArrayList());
      lllllllllllllllllllIIIIIllllIIll = false;
      Iterator lllllllllllllllllllIIIIIlllIIIll = lllllllllllllllllllIIIIIllllIIIl.iterator();

      while(lllllllllllllllllllIIIIIlllIIIll.hasNext()) {
         Object lllllllllllllllllllIIIIIllllIlll = lllllllllllllllllllIIIIIlllIIIll.next();
         if (lllllllllllllllllllIIIIIllllIlll instanceof Value) {
            lllllllllllllllllllIIIIIllllIlIl.add(lllllllllllllllllllIIIIIllllIlll);
            var10001 = false;
         }
      }

      return (List)lllllllllllllllllllIIIIIllllIlIl;
   }

   public final double getRenderX() {
      // $FF: Couldn't be decompiled
   }

   public final void setPrevMouseY(float lllllllllllllllllllIIIIlIIIllIII) {
      lllllllllllllllllllIIIIlIIIllIll.prevMouseY = lllllllllllllllllllIIIIlIIIllIII;
   }

   public final double getY() {
      return lllllllllllllllllllIIIIIIllIIlII.y;
   }
}
