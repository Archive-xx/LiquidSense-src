//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0019\u001a\u00020\u001aR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "", "message", "", "(Ljava/lang/String;)V", "fadeState", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;", "getFadeState", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;", "setFadeState", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;)V", "fadeStep", "", "stay", "textLength", "", "getTextLength", "()I", "setTextLength", "(I)V", "x", "getX", "()F", "setX", "(F)V", "drawNotification", "", "FadeState", "LiquidSense"}
)
public final class Notification {
   // $FF: synthetic field
   private final String message;
   // $FF: synthetic field
   private float fadeStep;
   // $FF: synthetic field
   private int textLength;
   // $FF: synthetic field
   private float x;
   // $FF: synthetic field
   @NotNull
   private Notification.FadeState fadeState;
   // $FF: synthetic field
   private float stay;

   public final float getX() {
      return lllIlllIIIlII.x;
   }

   public final void setX(float lllIllIllllll) {
      lllIlllIIIIII.x = lllIllIllllll;
   }

   public final void setFadeState(@NotNull Notification.FadeState lllIllIlIllIl) {
      Intrinsics.checkParameterIsNotNull(lllIllIlIllIl, "<set-?>");
      lllIllIlIlllI.fadeState = lllIllIlIllIl;
   }

   public Notification(@NotNull String lllIllIIllllI) {
      Intrinsics.checkParameterIsNotNull(lllIllIIllllI, "message");
      super();
      lllIllIIlllll.message = lllIllIIllllI;
      lllIllIIlllll.fadeState = Notification.FadeState.IN;
      lllIllIIlllll.textLength = Fonts.font35.getStringWidth(lllIllIIlllll.message);
   }

   public final int getTextLength() {
      return lllIllIlllIlI.textLength;
   }

   public final void setTextLength(int lllIllIllIlII) {
      lllIllIllIlIl.textLength = lllIllIllIlII;
   }

   @NotNull
   public final Notification.FadeState getFadeState() {
      return lllIllIllIIlI.fadeState;
   }

   public final void drawNotification() {
      // $FF: Couldn't be decompiled
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$FadeState;", "", "(Ljava/lang/String;I)V", "IN", "STAY", "OUT", "END", "LiquidSense"}
   )
   public static enum FadeState {
      // $FF: synthetic field
      OUT,
      // $FF: synthetic field
      IN,
      // $FF: synthetic field
      END,
      // $FF: synthetic field
      STAY;
   }
}
