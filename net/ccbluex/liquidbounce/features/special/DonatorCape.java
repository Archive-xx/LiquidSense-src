//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.special;

import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.ui.client.altmanager.sub.GuiDonatorCape;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0007¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/special/DonatorCape;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "handleEvents", "", "onSession", "", "event", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "LiquidSense"}
)
public final class DonatorCape extends MinecraftInstance implements Listenable {
   public boolean handleEvents() {
      return true;
   }

   @EventTarget
   public final void onSession(@NotNull SessionEvent llllllllllllllllllIIlIIlllIIlllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIIlllIIlllI, "event");
      if (GuiDonatorCape.Companion.getCapeEnabled()) {
         Exception llllllllllllllllllIIlIIlllIIllII = (CharSequence)GuiDonatorCape.Companion.getTransferCode();
         String llllllllllllllllllIIlIIlllIIlIll = false;
         if (llllllllllllllllllIIlIIlllIIllII.length() != 0) {
            UserUtils var10000 = UserUtils.INSTANCE;
            Session var10001 = access$getMc$p$s1046033730().session;
            Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.session");
            String var4 = var10001.getToken();
            Intrinsics.checkExpressionValueIsNotNull(var4, "mc.session.token");
            if (var10000.isValidTokenOffline(var4)) {
               ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)null.INSTANCE, 31, (Object)null);
               boolean var5 = false;
               return;
            }
         }
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
