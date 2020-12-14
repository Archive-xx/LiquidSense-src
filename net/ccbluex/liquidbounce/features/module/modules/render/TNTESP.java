//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/TNTESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "TNTESP",
   description = "Allows you to see ignited TNT blocks through walls.",
   category = ModuleCategory.RENDER
)
public final class TNTESP extends Module {
   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent lllllllIIIlIllI) {
      Intrinsics.checkParameterIsNotNull(lllllllIIIlIllI, "event");
      List var10000 = access$getMc$p$s1046033730().theWorld.loadedEntityList;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.theWorld.loadedEntityList");
      byte lllllllIIIlIlII = (Iterable)var10000;
      int lllllllIIIlIIll = false;
      Collection lllllllIIlIIIII = (Collection)(new ArrayList());
      boolean lllllllIIIlIIII = false;
      Iterator lllllllIIIIllll = lllllllIIIlIlII.iterator();

      while(lllllllIIIIllll.hasNext()) {
         Object lllllllIIlIIIlI = lllllllIIIIllll.next();
         if (lllllllIIlIIIlI instanceof EntityTNTPrimed) {
            lllllllIIlIIIII.add(lllllllIIlIIIlI);
            boolean var10001 = false;
         }
      }

      lllllllIIIlIlII = (Iterable)((List)lllllllIIlIIIII);
      lllllllIIIlIIll = false;
      Iterator lllllllIIIlIIlI = lllllllIIIlIlII.iterator();

      while(lllllllIIIlIIlI.hasNext()) {
         int lllllllIIIlIIIl = lllllllIIIlIIlI.next();
         boolean lllllllIIIlIIII = (EntityTNTPrimed)lllllllIIIlIIIl;
         int lllllllIIIllIll = false;
         RenderUtils.drawEntityBox((Entity)lllllllIIIlIIII, Color.RED, false);
      }

   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }
}
