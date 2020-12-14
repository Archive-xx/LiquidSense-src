//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "KeepAlive",
   description = "Tries to prevent you from dying.",
   category = ModuleCategory.PLAYER
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/KeepAlive;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "runOnce", "", "onMotion", "", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidSense"}
)
public final class KeepAlive extends Module {
   // $FF: synthetic field
   @NotNull
   private final ListValue modeValue = new ListValue("Mode", new String[]{"/heal", "Soup"}, "/heal");
   // $FF: synthetic field
   private boolean runOnce;

   @EventTarget
   public final void onMotion(@NotNull MotionEvent lllIIlIlllIlIII) {
      Intrinsics.checkParameterIsNotNull(lllIIlIlllIlIII, "event");
      if (!access$getMc$p$s1046033730().thePlayer.isDead) {
         EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
         if (!(var10000.getHealth() <= (float)0)) {
            lllIIlIlllIIlll.runOnce = false;
            return;
         }
      }

      if (!lllIIlIlllIIlll.runOnce) {
         short lllIIlIlllIIlIl = (String)lllIIlIlllIIlll.modeValue.get();
         long lllIIlIlllIIlII = false;
         if (lllIIlIlllIIlIl == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } else {
            String var6 = lllIIlIlllIIlIl.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var6, "(this as java.lang.String).toLowerCase()");
            lllIIlIlllIIlIl = var6;
            switch(lllIIlIlllIIlIl.hashCode()) {
            case 3536375:
               if (lllIIlIlllIIlIl.equals("soup")) {
                  int lllIIlIlllIlIlI = InventoryUtils.findItem(36, 45, Items.mushroom_stew);
                  if (lllIIlIlllIlIlI != -1) {
                     Minecraft var7 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var7, "mc");
                     var7.getNetHandler().addToSendQueue((Packet)(new C09PacketHeldItemChange(lllIIlIlllIlIlI - 36)));
                     var7 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var7, "mc");
                     NetHandlerPlayClient var8 = var7.getNetHandler();
                     Slot var10003 = access$getMc$p$s1046033730().thePlayer.inventoryContainer.getSlot(lllIIlIlllIlIlI);
                     Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.thePlayer.inventoryCo…ner.getSlot(soupInHotbar)");
                     var8.addToSendQueue((Packet)(new C08PacketPlayerBlockPlacement(var10003.getStack())));
                     var7 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var7, "mc");
                     var7.getNetHandler().addToSendQueue((Packet)(new C09PacketHeldItemChange(access$getMc$p$s1046033730().thePlayer.inventory.currentItem)));
                  }
               }
               break;
            case 46603927:
               if (lllIIlIlllIIlIl.equals("/heal")) {
                  access$getMc$p$s1046033730().thePlayer.sendChatMessage("/heal");
               }
            }

            lllIIlIlllIIlll.runOnce = true;
         }
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public final ListValue getModeValue() {
      return lllIIlIllllIIII.modeValue;
   }
}
