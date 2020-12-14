//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u000fH\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoPot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "groundDistanceValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "healthValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "openInventoryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "potion", "", "simulateInventory", "findPotion", "startSlot", "endSlot", "onMotion", "", "motionEvent", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "AutoPot",
   description = "Automatically throws healing potions.",
   category = ModuleCategory.COMBAT
)
public final class AutoPot extends Module {
   // $FF: synthetic field
   private final BoolValue openInventoryValue = new BoolValue("OpenInv", false);
   // $FF: synthetic field
   private final BoolValue simulateInventory = new BoolValue("SimulateInventory", true);
   // $FF: synthetic field
   private final FloatValue healthValue = new FloatValue("Health", 15.0F, 1.0F, 20.0F);
   // $FF: synthetic field
   private final FloatValue groundDistanceValue = new FloatValue("GroundDistance", 2.0F, 0.0F, 5.0F);
   // $FF: synthetic field
   private final MSTimer msTimer = new MSTimer();
   // $FF: synthetic field
   private final IntegerValue delayValue = new IntegerValue("Delay", 500, 500, 1000);
   // $FF: synthetic field
   private int potion = -1;
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Normal", "Jump", "Port"}, "Normal");

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent lIlIlllIIII) {
      // $FF: Couldn't be decompiled
   }

   private final int findPotion(int lIlIlIlIlIl, int lIlIlIlIIIl) {
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      short lIlIlIlIIII = var10000;
      Exception lIlIlIllIII = lIlIlIlIlIl;

      for(int lIlIlIIlllI = lIlIlIlIIIl; lIlIlIllIII < lIlIlIIlllI; ++lIlIlIllIII) {
         Slot var10 = lIlIlIlIIII.inventoryContainer.getSlot(lIlIlIllIII);
         Intrinsics.checkExpressionValueIsNotNull(var10, "thePlayer.inventoryContainer.getSlot(i)");
         ItemStack lIlIlIllIIl = var10.getStack();
         if (lIlIlIllIIl != null && lIlIlIllIIl.getItem() instanceof ItemPotion && ItemPotion.isSplash(lIlIlIllIIl.getItemDamage())) {
            Item var11 = lIlIlIllIIl.getItem();
            if (var11 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.ItemPotion");
            }

            ItemPotion lIlIlIllIlI = (ItemPotion)var11;
            PotionEffect lIlIlIIlIll;
            Iterator lIlIlIIlIlI;
            if (lIlIlIlIIII.getHealth() <= ((Number)lIlIlIlIIll.healthValue.get()).floatValue()) {
               lIlIlIIlIlI = lIlIlIllIlI.getEffects(lIlIlIllIIl).iterator();

               while(lIlIlIIlIlI.hasNext()) {
                  lIlIlIIlIll = (PotionEffect)lIlIlIIlIlI.next();
                  Intrinsics.checkExpressionValueIsNotNull(lIlIlIIlIll, "potionEffect");
                  if (lIlIlIIlIll.getPotionID() == Potion.heal.id) {
                     return lIlIlIllIII;
                  }
               }

               if (!lIlIlIlIIII.isPotionActive(Potion.regeneration.id)) {
                  lIlIlIIlIlI = lIlIlIllIlI.getEffects(lIlIlIllIIl).iterator();

                  while(lIlIlIIlIlI.hasNext()) {
                     lIlIlIIlIll = (PotionEffect)lIlIlIIlIlI.next();
                     Intrinsics.checkExpressionValueIsNotNull(lIlIlIIlIll, "potionEffect");
                     if (lIlIlIIlIll.getPotionID() == Potion.regeneration.id) {
                        return lIlIlIllIII;
                     }
                  }
               }
            }

            if (!lIlIlIlIIII.isPotionActive(Potion.moveSpeed.id)) {
               lIlIlIIlIlI = lIlIlIllIlI.getEffects(lIlIlIllIIl).iterator();

               while(lIlIlIIlIlI.hasNext()) {
                  lIlIlIIlIll = (PotionEffect)lIlIlIIlIlI.next();
                  Intrinsics.checkExpressionValueIsNotNull(lIlIlIIlIll, "potionEffect");
                  if (lIlIlIIlIll.getPotionID() == Potion.moveSpeed.id) {
                     return lIlIlIllIII;
                  }
               }
            }
         }
      }

      return -1;
   }
}
