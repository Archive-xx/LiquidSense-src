//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "BowAimbot",
   description = "Automatically aims at players when using a bow.",
   category = ModuleCategory.COMBAT
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u000e\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0006\u0010\u0013\u001a\u00020\u0010J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u001aH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/BowAimbot;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "markValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "predictSizeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "predictValue", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "silentValue", "target", "Lnet/minecraft/entity/Entity;", "throughWallsValue", "getTarget", "throughWalls", "", "priorityMode", "", "hasTarget", "onDisable", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "LiquidSense"}
)
public final class BowAimbot extends Module {
   // $FF: synthetic field
   private final ListValue priorityValue = new ListValue("Priority", new String[]{"Health", "Distance", "Direction"}, "Direction");
   // $FF: synthetic field
   private final BoolValue markValue = new BoolValue("Mark", true);
   // $FF: synthetic field
   private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
   // $FF: synthetic field
   private Entity target;
   // $FF: synthetic field
   private final BoolValue silentValue = new BoolValue("Silent", true);
   // $FF: synthetic field
   private final FloatValue predictSizeValue = new FloatValue("PredictSize", 2.0F, 0.1F, 5.0F);
   // $FF: synthetic field
   private final BoolValue predictValue = new BoolValue("Predict", true);

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent llllllllllllllllllIIlIlIIlllllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIlIIlllllII, "event");
      if (llllllllllllllllllIIlIlIIlllllIl.target != null && !StringsKt.equals((String)llllllllllllllllllIIlIlIIlllllIl.priorityValue.get(), "Multi", true) && (Boolean)llllllllllllllllllIIlIlIIlllllIl.markValue.get()) {
         RenderUtils.drawPlatform(llllllllllllllllllIIlIlIIlllllIl.target, new Color(37, 126, 255, 70));
      }

   }

   public void onDisable() {
      llllllllllllllllllIIlIlIlIlIIllI.target = (Entity)null;
   }

   private final Entity getTarget(boolean llllllllllllllllllIIlIlIIIlIllIl, String llllllllllllllllllIIlIlIIIlIlllI) {
      List var10000 = access$getMc$p$s1046033730().theWorld.loadedEntityList;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.theWorld.loadedEntityList");
      byte llllllllllllllllllIIlIlIIIlIlIlI = (Iterable)var10000;
      int llllllllllllllllllIIlIlIIIlIlIIl = false;
      String llllllllllllllllllIIlIlIIIlIIlll = (Collection)(new ArrayList());
      int llllllllllllllllllIIlIlIIllIIIlI = false;
      Iterator llllllllllllllllllIIlIlIIIlIIlIl = llllllllllllllllllIIlIlIIIlIlIlI.iterator();

      Object llllllllllllllllllIIlIlIIIlllIll;
      Entity llllllllllllllllllIIlIlIIIlllllI;
      boolean llllllllllllllllllIIlIlIIIllllIl;
      while(llllllllllllllllllIIlIlIIIlIIlIl.hasNext()) {
         llllllllllllllllllIIlIlIIIlllIll = llllllllllllllllllIIlIlIIIlIIlIl.next();
         llllllllllllllllllIIlIlIIIlllllI = (Entity)llllllllllllllllllIIlIlIIIlllIll;
         llllllllllllllllllIIlIlIIIllllIl = false;
         if (llllllllllllllllllIIlIlIIIlllllI instanceof EntityLivingBase && EntityUtils.isSelected(llllllllllllllllllIIlIlIIIlllllI, true) && (llllllllllllllllllIIlIlIIIlIllIl || access$getMc$p$s1046033730().thePlayer.canEntityBeSeen(llllllllllllllllllIIlIlIIIlllllI))) {
            llllllllllllllllllIIlIlIIIlIIlll.add(llllllllllllllllllIIlIlIIIlllIll);
            boolean var10001 = false;
         }
      }

      List llllllllllllllllllIIlIlIIIllIIll = (List)llllllllllllllllllIIlIlIIIlIIlll;
      llllllllllllllllllIIlIlIIIlIlIIl = false;
      if (llllllllllllllllllIIlIlIIIlIlllI == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         String var25 = llllllllllllllllllIIlIlIIIlIlllI.toUpperCase();
         Intrinsics.checkExpressionValueIsNotNull(var25, "(this as java.lang.String).toUpperCase()");
         byte llllllllllllllllllIIlIlIIIlIlIlI = var25;
         boolean llllllllllllllllllIIlIlIIIllIlII;
         Iterable llllllllllllllllllIIlIlIIIllIlIl;
         Iterator llllllllllllllllllIIlIlIIIlIIlll;
         Object llllllllllllllllllIIlIlIIIlIIllI;
         Entity llllllllllllllllllIIlIlIIIlIIlIl;
         float llllllllllllllllllIIlIlIIIlIIlIl;
         boolean llllllllllllllllllIIlIlIIIlIIlII;
         float llllllllllllllllllIIlIlIIIllllII;
         Object var26;
         Entity var27;
         switch(llllllllllllllllllIIlIlIIIlIlIlI.hashCode()) {
         case 1071086581:
            if (llllllllllllllllllIIlIlIIIlIlIlI.equals("DISTANCE")) {
               llllllllllllllllllIIlIlIIIllIlIl = (Iterable)llllllllllllllllllIIlIlIIIllIIll;
               llllllllllllllllllIIlIlIIIllIlII = false;
               llllllllllllllllllIIlIlIIIlIIlll = llllllllllllllllllIIlIlIIIllIlIl.iterator();
               if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                  var26 = null;
               } else {
                  llllllllllllllllllIIlIlIIIlIIllI = llllllllllllllllllIIlIlIIIlIIlll.next();
                  if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                     var26 = llllllllllllllllllIIlIlIIIlIIllI;
                  } else {
                     llllllllllllllllllIIlIlIIIlIIlIl = (Entity)llllllllllllllllllIIlIlIIIlIIllI;
                     llllllllllllllllllIIlIlIIIlIIlII = false;
                     llllllllllllllllllIIlIlIIIlIIlIl = access$getMc$p$s1046033730().thePlayer.getDistanceToEntity(llllllllllllllllllIIlIlIIIlIIlIl);

                     while(true) {
                        llllllllllllllllllIIlIlIIIlllIll = llllllllllllllllllIIlIlIIIlIIlll.next();
                        llllllllllllllllllIIlIlIIIlllllI = (Entity)llllllllllllllllllIIlIlIIIlllIll;
                        llllllllllllllllllIIlIlIIIllllIl = false;
                        llllllllllllllllllIIlIlIIIllllII = access$getMc$p$s1046033730().thePlayer.getDistanceToEntity(llllllllllllllllllIIlIlIIIlllllI);
                        if (Float.compare(llllllllllllllllllIIlIlIIIlIIlIl, llllllllllllllllllIIlIlIIIllllII) > 0) {
                           llllllllllllllllllIIlIlIIIlIIllI = llllllllllllllllllIIlIlIIIlllIll;
                           llllllllllllllllllIIlIlIIIlIIlIl = llllllllllllllllllIIlIlIIIllllII;
                        }

                        if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                           var26 = llllllllllllllllllIIlIlIIIlIIllI;
                           break;
                        }
                     }
                  }
               }

               var27 = (Entity)var26;
               return var27;
            }
            break;
         case 1824003935:
            if (llllllllllllllllllIIlIlIIIlIlIlI.equals("DIRECTION")) {
               llllllllllllllllllIIlIlIIIllIlIl = (Iterable)llllllllllllllllllIIlIlIIIllIIll;
               llllllllllllllllllIIlIlIIIllIlII = false;
               llllllllllllllllllIIlIlIIIlIIlll = llllllllllllllllllIIlIlIIIllIlIl.iterator();
               if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                  var26 = null;
               } else {
                  llllllllllllllllllIIlIlIIIlIIllI = llllllllllllllllllIIlIlIIIlIIlll.next();
                  if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                     var26 = llllllllllllllllllIIlIlIIIlIIllI;
                  } else {
                     llllllllllllllllllIIlIlIIIlIIlIl = (Entity)llllllllllllllllllIIlIlIIIlIIllI;
                     llllllllllllllllllIIlIlIIIlIIlII = false;
                     double llllllllllllllllllIIlIlIIIlIIlIl = RotationUtils.getRotationDifference(llllllllllllllllllIIlIlIIIlIIlIl);

                     while(true) {
                        llllllllllllllllllIIlIlIIIlllIll = llllllllllllllllllIIlIlIIIlIIlll.next();
                        llllllllllllllllllIIlIlIIIlllllI = (Entity)llllllllllllllllllIIlIlIIIlllIll;
                        llllllllllllllllllIIlIlIIIllllIl = false;
                        Exception llllllllllllllllllIIlIlIIIlIIIll = RotationUtils.getRotationDifference(llllllllllllllllllIIlIlIIIlllllI);
                        if (Double.compare(llllllllllllllllllIIlIlIIIlIIlIl, llllllllllllllllllIIlIlIIIlIIIll) > 0) {
                           llllllllllllllllllIIlIlIIIlIIllI = llllllllllllllllllIIlIlIIIlllIll;
                           llllllllllllllllllIIlIlIIIlIIlIl = llllllllllllllllllIIlIlIIIlIIIll;
                        }

                        if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                           var26 = llllllllllllllllllIIlIlIIIlIIllI;
                           break;
                        }
                     }
                  }
               }

               var27 = (Entity)var26;
               return var27;
            }
            break;
         case 2127033948:
            if (llllllllllllllllllIIlIlIIIlIlIlI.equals("HEALTH")) {
               llllllllllllllllllIIlIlIIIllIlIl = (Iterable)llllllllllllllllllIIlIlIIIllIIll;
               llllllllllllllllllIIlIlIIIllIlII = false;
               llllllllllllllllllIIlIlIIIlIIlll = llllllllllllllllllIIlIlIIIllIlIl.iterator();
               if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                  var26 = null;
               } else {
                  llllllllllllllllllIIlIlIIIlIIllI = llllllllllllllllllIIlIlIIIlIIlll.next();
                  if (!llllllllllllllllllIIlIlIIIlIIlll.hasNext()) {
                     var26 = llllllllllllllllllIIlIlIIIlIIllI;
                  } else {
                     llllllllllllllllllIIlIlIIIlIIlIl = (Entity)llllllllllllllllllIIlIlIIIlIIllI;
                     llllllllllllllllllIIlIlIIIlIIlII = false;
                     if (llllllllllllllllllIIlIlIIIlIIlIl == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                     }

                     llllllllllllllllllIIlIlIIIlIIlIl = ((EntityLivingBase)llllllllllllllllllIIlIlIIIlIIlIl).getHealth();

                     do {
                        llllllllllllllllllIIlIlIIIlllIll = llllllllllllllllllIIlIlIIIlIIlll.next();
                        llllllllllllllllllIIlIlIIIlllllI = (Entity)llllllllllllllllllIIlIlIIIlllIll;
                        llllllllllllllllllIIlIlIIIllllIl = false;
                        if (llllllllllllllllllIIlIlIIIlllllI == null) {
                           throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
                        }

                        llllllllllllllllllIIlIlIIIllllII = ((EntityLivingBase)llllllllllllllllllIIlIlIIIlllllI).getHealth();
                        if (Float.compare(llllllllllllllllllIIlIlIIIlIIlIl, llllllllllllllllllIIlIlIIIllllII) > 0) {
                           llllllllllllllllllIIlIlIIIlIIllI = llllllllllllllllllIIlIlIIIlllIll;
                           llllllllllllllllllIIlIlIIIlIIlIl = llllllllllllllllllIIlIlIIIllllII;
                        }
                     } while(llllllllllllllllllIIlIlIIIlIIlll.hasNext());

                     var26 = llllllllllllllllllIIlIlIIIlIIllI;
                  }
               }

               var27 = (Entity)var26;
               return var27;
            }
         }

         var27 = null;
         return var27;
      }
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllllllllllllllIIlIlIlIIlIIII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIlIlIlIIlIIII, "event");
      llllllllllllllllllIIlIlIlIIlIllI.target = (Entity)null;
      EntityPlayerSP var10000 = access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      ItemStack var3 = var10000.getItemInUse();
      boolean var10001;
      Item var4;
      if (var3 != null) {
         var4 = var3.getItem();
      } else {
         var10001 = false;
         var4 = null;
      }

      if (var4 instanceof ItemBow) {
         Entity var5 = llllllllllllllllllIIlIlIlIIlIllI.getTarget((Boolean)llllllllllllllllllIIlIlIlIIlIllI.throughWallsValue.get(), (String)llllllllllllllllllIIlIlIlIIlIllI.priorityValue.get());
         if (var5 == null) {
            var10001 = false;
            return;
         }

         byte llllllllllllllllllIIlIlIlIIIlllI = var5;
         llllllllllllllllllIIlIlIlIIlIllI.target = llllllllllllllllllIIlIlIlIIIlllI;
         RotationUtils.faceBow(llllllllllllllllllIIlIlIlIIlIllI.target, (Boolean)llllllllllllllllllIIlIlIlIIlIllI.silentValue.get(), (Boolean)llllllllllllllllllIIlIlIlIIlIllI.predictValue.get(), ((Number)llllllllllllllllllIIlIlIlIIlIllI.predictSizeValue.get()).floatValue());
      }

   }

   public final boolean hasTarget() {
      return llllllllllllllllllIIlIlIIIlIIIII.target != null && access$getMc$p$s1046033730().thePlayer.canEntityBeSeen(llllllllllllllllllIIlIlIIIlIIIII.target);
   }
}
