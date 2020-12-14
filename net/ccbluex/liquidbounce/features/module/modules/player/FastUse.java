//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u0004\u0018\u00010\u000fJ\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u00020\u001b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020 H\u0007J\u0006\u0010!\u001a\u00020\u001bJ\u0006\u0010\"\u001a\u00020\u001bR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/FastUse;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacDelay", "", "getAacDelay", "()Z", "setAacDelay", "(Z)V", "customSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "customTimer", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "delayValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "noMoveValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "usedTimer", "getModeValue", "onDisable", "", "onMove", "event", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "resetTimer", "shabi", "LiquidSense"}
)
@ModuleInfo(
   name = "FastUse",
   description = "Allows you to use items faster.",
   category = ModuleCategory.PLAYER
)
public final class FastUse extends Module {
   // $FF: synthetic field
   private final IntegerValue delayValue = new IntegerValue("CustomDelay", 0, 0, 300);
   // $FF: synthetic field
   private final ListValue modeValue = new ListValue("Mode", new String[]{"Instant", "NCP", "AAC", "CustomDelay"}, "NCP");
   // $FF: synthetic field
   private boolean aacDelay;
   // $FF: synthetic field
   private boolean usedTimer;
   // $FF: synthetic field
   private final MSTimer msTimer = new MSTimer();
   // $FF: synthetic field
   private final IntegerValue customSpeedValue = new IntegerValue("CustomSpeed", 2, 1, 35);
   // $FF: synthetic field
   private final FloatValue customTimer = new FloatValue("CustomTimer", 1.1F, 0.5F, 2.0F);
   // $FF: synthetic field
   private final BoolValue noMoveValue = new BoolValue("NoMove", false);

   @Nullable
   public final ListValue getModeValue() {
      return lIIIIlllIlIIllI.modeValue;
   }

   public void onDisable() {
      if (lIIIIllIllIIIII.usedTimer) {
         access$getMc$p$s1046033730().timer.timerSpeed = 1.0F;
         lIIIIllIllIIIII.usedTimer = false;
      }

      lIIIIllIllIIIII.aacDelay = false;
   }

   public final void resetTimer() {
      if (lIIIIllIllIIIll.msTimer.hasTimePassed(1000L)) {
         lIIIIllIllIIIll.msTimer.reset();
      }

   }

   @Nullable
   public String getTag() {
      return (String)lIIIIllIlIllllI.modeValue.get();
   }

   public final void setAacDelay(boolean lIIIIlllIlIlIIl) {
      lIIIIlllIlIllII.aacDelay = lIIIIlllIlIlIIl;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent lIIIIllIlllIlll) {
      Intrinsics.checkParameterIsNotNull(lIIIIllIlllIlll, "event");
      MSTimer var10000 = lIIIIllIllllIII.msTimer;
      boolean var10001 = false;
      if (lIIIIllIllllIII.usedTimer) {
         access$getMc$p$s1046033730().timer.timerSpeed = 1.0F;
         lIIIIllIllllIII.usedTimer = false;
      }

      EntityPlayerSP var15 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var15, "mc.thePlayer");
      if (!var15.isUsingItem()) {
         lIIIIllIllllIII.msTimer.reset();
      } else {
         if (((String)lIIIIllIllllIII.modeValue.get()).equals("AAC")) {
            if (lIIIIllIllllIII.aacDelay) {
               KeyBinding.setKeyBindState(access$getMc$p$s1046033730().gameSettings.keyBindUseItem.getKeyCode(), true);
               if (lIIIIllIllllIII.msTimer.hasTimePassed(1000L)) {
                  lIIIIllIllllIII.aacDelay = false;
               }
            } else {
               lIIIIllIllllIII.msTimer.reset();
            }
         }

         var15 = access$getMc$p$s1046033730().thePlayer;
         Intrinsics.checkExpressionValueIsNotNull(var15, "mc.thePlayer");
         ItemStack var16 = var15.getItemInUse();
         Intrinsics.checkExpressionValueIsNotNull(var16, "mc.thePlayer.itemInUse");
         Item lIIIIllIllllIll = var16.getItem();
         if (lIIIIllIllllIll instanceof ItemFood || lIIIIllIllllIll instanceof ItemBucketMilk || lIIIIllIllllIll instanceof ItemPotion) {
            Exception lIIIIllIlllIlIl = (String)lIIIIllIllllIII.modeValue.get();
            double lIIIIllIlllIlII = false;
            if (lIIIIllIlllIlIl == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            String var17 = lIIIIllIlllIlIl.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var17, "(this as java.lang.String).toLowerCase()");
            lIIIIllIlllIlIl = var17;
            boolean lIIIIllIlllIIll;
            boolean lIIIIllIlllIIlI;
            byte lIIIIllIlllIIIl;
            boolean lIIIIlllIIIllII;
            byte lIIIIllIlllIlII;
            int lIIIIllIlllIIlI;
            Minecraft var18;
            switch(lIIIIllIlllIlIl.hashCode()) {
            case -1773359950:
               if (lIIIIllIlllIlIl.equals("customdelay")) {
                  access$getMc$p$s1046033730().timer.timerSpeed = ((Number)lIIIIllIllllIII.customTimer.get()).floatValue();
                  lIIIIllIllllIII.usedTimer = true;
                  if (!lIIIIllIllllIII.msTimer.hasTimePassed((long)((Number)lIIIIllIllllIII.delayValue.get()).intValue())) {
                     return;
                  }

                  double lIIIIllIlllIlII = ((Number)lIIIIllIllllIII.customSpeedValue.get()).intValue();
                  lIIIIllIlllIIll = false;
                  lIIIIllIlllIIlI = false;
                  lIIIIllIlllIIlI = 0;

                  for(int lIIIIllIlllIIIl = lIIIIllIlllIlII; lIIIIllIlllIIlI < lIIIIllIlllIIIl; ++lIIIIllIlllIIlI) {
                     lIIIIlllIIIllII = false;
                     var18 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var18, "mc");
                     var18.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer(access$getMc$p$s1046033730().thePlayer.onGround)));
                  }

                  lIIIIllIllllIII.msTimer.reset();
               }
               break;
            case 96323:
               if (lIIIIllIlllIlIl.equals("aac") && lIIIIllIllllIII.msTimer.hasTimePassed(1000L)) {
                  access$getMc$p$s1046033730().timer.timerSpeed = 1.22F;
                  lIIIIllIllllIII.usedTimer = true;
               }
               break;
            case 108891:
               if (lIIIIllIlllIlIl.equals("ncp")) {
                  var15 = access$getMc$p$s1046033730().thePlayer;
                  Intrinsics.checkExpressionValueIsNotNull(var15, "mc.thePlayer");
                  if (var15.getItemInUseDuration() > 14) {
                     lIIIIllIlllIlII = 20;
                     lIIIIllIlllIIll = false;
                     lIIIIllIlllIIlI = false;
                     lIIIIllIlllIIlI = 0;

                     for(lIIIIllIlllIIIl = lIIIIllIlllIlII; lIIIIllIlllIIlI < lIIIIllIlllIIIl; ++lIIIIllIlllIIlI) {
                        lIIIIlllIIIllII = false;
                        var18 = access$getMc$p$s1046033730();
                        Intrinsics.checkExpressionValueIsNotNull(var18, "mc");
                        var18.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer()));
                     }

                     access$getMc$p$s1046033730().playerController.onStoppedUsingItem((EntityPlayer)access$getMc$p$s1046033730().thePlayer);
                  }
               }
               break;
            case 1957570017:
               if (lIIIIllIlllIlIl.equals("instant")) {
                  lIIIIllIlllIlII = 35;
                  lIIIIllIlllIIll = false;
                  lIIIIllIlllIIlI = false;
                  lIIIIllIlllIIlI = 0;

                  for(lIIIIllIlllIIIl = lIIIIllIlllIlII; lIIIIllIlllIIlI < lIIIIllIlllIIIl; ++lIIIIllIlllIIlI) {
                     lIIIIlllIIIllII = false;
                     var18 = access$getMc$p$s1046033730();
                     Intrinsics.checkExpressionValueIsNotNull(var18, "mc");
                     var18.getNetHandler().addToSendQueue((Packet)(new C03PacketPlayer()));
                  }

                  access$getMc$p$s1046033730().playerController.onStoppedUsingItem((EntityPlayer)access$getMc$p$s1046033730().thePlayer);
               }
            }
         }

      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public final boolean getAacDelay() {
      return lIIIIlllIlIllll.aacDelay;
   }

   public final void shabi() {
      lIIIIlllIlIIlII.aacDelay = true;
   }

   @EventTarget
   public final void onMove(@Nullable MoveEvent lIIIIllIllIlIIl) {
      if (lIIIIllIllIlIIl != null) {
         if (lIIIIllIllIlIlI.getState()) {
            EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
            if (var10000.isUsingItem() && (Boolean)lIIIIllIllIlIlI.noMoveValue.get()) {
               var10000 = access$getMc$p$s1046033730().thePlayer;
               Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
               ItemStack var4 = var10000.getItemInUse();
               Intrinsics.checkExpressionValueIsNotNull(var4, "mc.thePlayer.itemInUse");
               double lIIIIllIllIIllI = var4.getItem();
               if (lIIIIllIllIIllI instanceof ItemFood || lIIIIllIllIIllI instanceof ItemBucketMilk || lIIIIllIllIIllI instanceof ItemPotion) {
                  lIIIIllIllIlIIl.zero();
               }

               return;
            }
         }

      }
   }
}
