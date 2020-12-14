package net.ccbluex.liquidbounce.ui.client.hud.element;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 \u000f2\u00020\u0001:\u0003\u000f\u0010\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "", "horizontal", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "vertical", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "getHorizontal", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "setHorizontal", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;)V", "getVertical", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "setVertical", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;)V", "Companion", "Horizontal", "Vertical", "LiquidSense"}
)
public final class Side {
   // $FF: synthetic field
   public static final Side.Companion Companion = new Side.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   @NotNull
   private Side.Vertical vertical;
   // $FF: synthetic field
   @NotNull
   private Side.Horizontal horizontal;

   @NotNull
   public final Side.Horizontal getHorizontal() {
      return lllllllllllllllllllIIIlIIIllllll.horizontal;
   }

   public final void setHorizontal(@NotNull Side.Horizontal lllllllllllllllllllIIIlIIIlllIII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIlIIIlllIII, "<set-?>");
      lllllllllllllllllllIIIlIIIlllIll.horizontal = lllllllllllllllllllIIIlIIIlllIII;
   }

   public final void setVertical(@NotNull Side.Vertical lllllllllllllllllllIIIlIIIlIllll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIlIIIlIllll, "<set-?>");
      lllllllllllllllllllIIIlIIIllIIlI.vertical = lllllllllllllllllllIIIlIIIlIllll;
   }

   public Side(@NotNull Side.Horizontal lllllllllllllllllllIIIlIIIlIlIlI, @NotNull Side.Vertical lllllllllllllllllllIIIlIIIlIIllI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIlIIIlIlIlI, "horizontal");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIIlIIIlIIllI, "vertical");
      super();
      lllllllllllllllllllIIIlIIIlIlIll.horizontal = lllllllllllllllllllIIIlIIIlIlIlI;
      lllllllllllllllllllIIIlIIIlIlIll.vertical = lllllllllllllllllllIIIlIIIlIIllI;
   }

   @NotNull
   public final Side.Vertical getVertical() {
      return lllllllllllllllllllIIIlIIIllIlIl.vertical;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "LiquidSense"}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker llllIllIlIIIIll) {
         this();
      }

      @NotNull
      public final Side default() {
         return new Side(Side.Horizontal.LEFT, Side.Vertical.UP);
      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "LEFT", "MIDDLE", "RIGHT", "Companion", "LiquidSense"}
   )
   public static enum Horizontal {
      // $FF: synthetic field
      @NotNull
      private final String sideName;
      // $FF: synthetic field
      MIDDLE,
      // $FF: synthetic field
      LEFT;

      // $FF: synthetic field
      public static final Side.Horizontal.Companion Companion = new Side.Horizontal.Companion((DefaultConstructorMarker)null);
      // $FF: synthetic field
      RIGHT;

      @NotNull
      public final String getSideName() {
         return llllllllllllllllllIlllIllIIlIlII.sideName;
      }

      @JvmStatic
      @Nullable
      public static final Side.Horizontal getByName(@NotNull String llllllllllllllllllIlllIlIllIllll) {
         return Companion.getByName(llllllllllllllllllIlllIlIllIllll);
      }

      private Horizontal(String llllllllllllllllllIlllIlIllllIII) {
         llllllllllllllllllIlllIlIlllllIl.sideName = llllllllllllllllllIlllIlIllllIII;
      }

      @Metadata(
         mv = {1, 1, 16},
         bv = {1, 0, 3},
         k = 1,
         d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
         d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Horizontal;", "name", "", "LiquidSense"}
      )
      public static final class Companion {
         @JvmStatic
         @Nullable
         public final Side.Horizontal getByName(@NotNull String lllIIlIIlII) {
            Intrinsics.checkParameterIsNotNull(lllIIlIIlII, "name");
            Exception lllIIlIIIll = Side.Horizontal.values();
            short lllIIlIIIlI = false;
            Exception lllIIlIIIII = false;
            byte lllIIIlllll = lllIIlIIIll;
            boolean lllIIIllllI = lllIIlIIIll.length;
            int lllIIIlllIl = 0;

            Side.Horizontal var10000;
            while(true) {
               if (lllIIIlllIl >= lllIIIllllI) {
                  var10000 = null;
                  break;
               }

               double lllIIIlllII = lllIIIlllll[lllIIIlllIl];
               int lllIIlIIlll = false;
               if (Intrinsics.areEqual((Object)lllIIIlllII.getSideName(), (Object)lllIIlIIlII)) {
                  var10000 = lllIIIlllII;
                  break;
               }

               ++lllIIIlllIl;
            }

            return var10000;
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker lllIIIlIlII) {
            this();
         }

         private Companion() {
         }
      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "", "sideName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getSideName", "()Ljava/lang/String;", "UP", "MIDDLE", "DOWN", "Companion", "LiquidSense"}
   )
   public static enum Vertical {
      // $FF: synthetic field
      DOWN,
      // $FF: synthetic field
      MIDDLE;

      // $FF: synthetic field
      public static final Side.Vertical.Companion Companion = new Side.Vertical.Companion((DefaultConstructorMarker)null);
      // $FF: synthetic field
      @NotNull
      private final String sideName;
      // $FF: synthetic field
      UP;

      @JvmStatic
      @Nullable
      public static final Side.Vertical getByName(@NotNull String lllllIIllIlIl) {
         return Companion.getByName(lllllIIllIlIl);
      }

      private Vertical(String lllllIIlllIII) {
         lllllIIlllIll.sideName = lllllIIlllIII;
      }

      @NotNull
      public final String getSideName() {
         return lllllIlIIIlIl.sideName;
      }

      @Metadata(
         mv = {1, 1, 16},
         bv = {1, 0, 3},
         k = 1,
         d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
         d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical$Companion;", "", "()V", "getByName", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side$Vertical;", "name", "", "LiquidSense"}
      )
      public static final class Companion {
         @JvmStatic
         @Nullable
         public final Side.Vertical getByName(@NotNull String llllIllIIlIllll) {
            Intrinsics.checkParameterIsNotNull(llllIllIIlIllll, "name");
            int llllIllIIlIlllI = Side.Vertical.values();
            long llllIllIIlIllIl = false;
            int llllIllIIlIlIll = false;
            float llllIllIIlIlIlI = llllIllIIlIlllI;
            byte llllIllIIlIlIIl = llllIllIIlIlllI.length;
            int llllIllIIlIlIII = 0;

            Side.Vertical var10000;
            while(true) {
               if (llllIllIIlIlIII >= llllIllIIlIlIIl) {
                  var10000 = null;
                  break;
               }

               boolean llllIllIIlIIlll = llllIllIIlIlIlI[llllIllIIlIlIII];
               Exception llllIllIIlIIlIl = false;
               if (Intrinsics.areEqual((Object)llllIllIIlIIlll.getSideName(), (Object)llllIllIIlIllll)) {
                  var10000 = llllIllIIlIIlll;
                  break;
               }

               ++llllIllIIlIlIII;
            }

            return var10000;
         }

         private Companion() {
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker llllIllIIIlllll) {
            this();
         }
      }
   }
}
