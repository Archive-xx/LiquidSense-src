//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.client.hud.designer.GuiHudDesigner;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\n\u0010\f\u001a\u0004\u0018\u00010\rH\u0016R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notifications;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "exampleNotification", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "LiquidSense"}
)
@ElementInfo(
   name = "Notifications",
   single = true
)
public final class Notifications extends Element {
   // $FF: synthetic field
   private final Notification exampleNotification;

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public Notifications() {
      this(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
   }

   public Notifications(double lIIllIIlllIlllI, double lIIllIIlllIlIII, float lIIllIIlllIllII, @NotNull Side lIIllIIlllIlIll) {
      Intrinsics.checkParameterIsNotNull(lIIllIIlllIlIll, "side");
      super(lIIllIIlllIlllI, lIIllIIlllIlIII, lIIllIIlllIllII, lIIllIIlllIlIll);
      lIIllIIlllIllll.exampleNotification = new Notification("Example Notification");
   }

   // $FF: synthetic method
   public Notifications(double var1, double var3, float var5, Side var6, int lIIllIIllIllIlI, DefaultConstructorMarker var8) {
      if ((lIIllIIllIllIlI & 1) != 0) {
         var1 = 0.0D;
      }

      if ((lIIllIIllIllIlI & 2) != 0) {
         var3 = 30.0D;
      }

      if ((lIIllIIllIllIlI & 4) != 0) {
         var5 = 1.0F;
      }

      if ((lIIllIIllIllIlI & 8) != 0) {
         var6 = new Side(Side.Horizontal.RIGHT, Side.Vertical.DOWN);
      }

      this(var1, var3, var5, var6);
   }

   @Nullable
   public Border drawElement() {
      if (LiquidBounce.INSTANCE.getHud().getNotifications().size() > 0) {
         ((Notification)LiquidBounce.INSTANCE.getHud().getNotifications().get(0)).drawNotification();
      }

      if (access$getMc$p$s1046033730().currentScreen instanceof GuiHudDesigner) {
         if (!LiquidBounce.INSTANCE.getHud().getNotifications().contains(lIIllIIllllIllI.exampleNotification)) {
            LiquidBounce.INSTANCE.getHud().addNotification(lIIllIIllllIllI.exampleNotification);
            boolean var10001 = false;
         }

         lIIllIIllllIllI.exampleNotification.setFadeState(Notification.FadeState.STAY);
         lIIllIIllllIllI.exampleNotification.setX((float)lIIllIIllllIllI.exampleNotification.getTextLength() + 8.0F);
         return new Border(-95.0F, -20.0F, 0.0F, 0.0F);
      } else {
         return null;
      }
   }
}
