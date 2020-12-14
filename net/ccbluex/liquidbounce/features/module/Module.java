//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.value.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0017\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010@\u001a\u00020\u00152\u0006\u0010A\u001a\u00020\u0005J\u0016\u0010B\u001a\b\u0012\u0002\b\u0003\u0018\u00010=2\u0006\u0010C\u001a\u00020\u0015H\u0016J\b\u0010D\u001a\u00020\u0005H\u0016J\b\u0010E\u001a\u00020FH\u0016J\b\u0010G\u001a\u00020FH\u0016J\u0010\u0010H\u001a\u00020F2\u0006\u00104\u001a\u00020\u0005H\u0016J\u0006\u0010A\u001a\u00020FR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u000e\u0010\r\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0017\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR$\u0010$\u001a\u00020%2\u0006\u0010$\u001a\u00020%@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0017\"\u0004\b,\u0010\u001bR\u001a\u0010-\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001f\"\u0004\b/\u0010!R\u001a\u00100\u001a\u00020\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u001f\"\u0004\b2\u0010!R$\u00104\u001a\u00020\u00052\u0006\u00103\u001a\u00020\u0005@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0007\"\u0004\b6\u0010\tR\u0016\u00107\u001a\u0004\u0018\u00010\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u0010\u0017R\u0011\u00109\u001a\u00020\u00158F¢\u0006\u0006\u001a\u0004\b:\u0010\u0017R\u001e\u0010;\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030=0<8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?¨\u0006I"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/Module;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "BreakName", "", "getBreakName", "()Z", "setBreakName", "(Z)V", "array", "getArray", "setArray", "canEnable", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "getCategory", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "setCategory", "(Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;)V", "colorlessTagName", "", "getColorlessTagName", "()Ljava/lang/String;", "description", "getDescription", "setDescription", "(Ljava/lang/String;)V", "higt", "", "getHigt", "()F", "setHigt", "(F)V", "hue", "getHue", "keyBind", "", "getKeyBind", "()I", "setKeyBind", "(I)V", "name", "getName", "setName", "slide", "getSlide", "setSlide", "slideStep", "getSlideStep", "setSlideStep", "value", "state", "getState", "setState", "tag", "getTag", "tagName", "getTagName", "values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "getValues", "()Ljava/util/List;", "breakname", "toggle", "getValue", "valueName", "handleEvents", "onDisable", "", "onEnable", "onToggle", "LiquidSense"}
)
public class Module extends MinecraftInstance implements Listenable {
   // $FF: synthetic field
   private final float hue;
   // $FF: synthetic field
   private final boolean canEnable;
   // $FF: synthetic field
   private float slideStep;
   // $FF: synthetic field
   private boolean array;
   // $FF: synthetic field
   private int keyBind;
   // $FF: synthetic field
   private boolean state;
   // $FF: synthetic field
   @NotNull
   private String name;
   // $FF: synthetic field
   private float slide;
   // $FF: synthetic field
   @NotNull
   private String description;
   // $FF: synthetic field
   private float higt;
   // $FF: synthetic field
   private boolean BreakName;
   // $FF: synthetic field
   @NotNull
   private ModuleCategory category;

   public final void setBreakName(boolean lllllllllllllllllIllIlIlllIllIIl) {
      lllllllllllllllllIllIlIlllIllIII.BreakName = lllllllllllllllllIllIlIlllIllIIl;
   }

   public final boolean getState() {
      return lllllllllllllllllIllIlIllllllIll.state;
   }

   @NotNull
   public final String getDescription() {
      return lllllllllllllllllIllIllIIIlIIIII.description;
   }

   public final boolean getBreakName() {
      return lllllllllllllllllIllIlIlllIllllI.BreakName;
   }

   public final void setSlideStep(float lllllllllllllllllIllIllIIIIIIIII) {
      lllllllllllllllllIllIlIlllllllll.slideStep = lllllllllllllllllIllIllIIIIIIIII;
   }

   @NotNull
   public final String getColorlessTagName() {
      return String.valueOf((new StringBuilder()).append(lllllllllllllllllIllIlIlllIIlIII.breakname(lllllllllllllllllIllIlIlllIIlIII.BreakName)).append(lllllllllllllllllIllIlIlllIIlIII.getTag() == null ? "" : String.valueOf((new StringBuilder()).append(" ").append(ColorUtils.stripColor(lllllllllllllllllIllIlIlllIIlIII.getTag())))));
   }

   @Nullable
   public Value<?> getValue(@NotNull String lllllllllllllllllIllIIlllIlIllII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIIlllIlIllII, "valueName");
      Field[] var10000 = lllllllllllllllllIllIIlllIlIllIl.getClass().getDeclaredFields();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "javaClass.declaredFields");
      Object[] lllllllllllllllllIllIIlllIllIlll = var10000;
      String lllllllllllllllllIllIIlllIlIlIII = false;
      Collection lllllllllllllllllIllIIlllIllIIll = (Collection)(new ArrayList(lllllllllllllllllIllIIlllIllIlll.length));
      boolean lllllllllllllllllIllIIlllIlIIlIl = false;
      byte lllllllllllllllllIllIIlllIlIIlII = lllllllllllllllllIllIIlllIllIlll;
      float lllllllllllllllllIllIIlllIlIIIll = lllllllllllllllllIllIIlllIllIlll.length;

      boolean var10001;
      for(int lllllllllllllllllIllIIlllIlIIIlI = 0; lllllllllllllllllIllIIlllIlIIIlI < lllllllllllllllllIllIIlllIlIIIll; ++lllllllllllllllllIllIIlllIlIIIlI) {
         int lllllllllllllllllIllIIlllIlIIIIl = lllllllllllllllllIllIIlllIlIIlII[lllllllllllllllllIllIIlllIlIIIlI];
         int lllllllllllllllllIllIIlllIllllII = false;
         Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIIlllIlIIIIl, "valueField");
         lllllllllllllllllIllIIlllIlIIIIl.setAccessible(true);
         boolean lllllllllllllllllIllIIlllIIlllIl = lllllllllllllllllIllIIlllIlIIIIl.get(lllllllllllllllllIllIIlllIlIllIl);
         lllllllllllllllllIllIIlllIllIIll.add(lllllllllllllllllIllIIlllIIlllIl);
         var10001 = false;
      }

      Iterable lllllllllllllllllIllIIlllIlIlIIl = (Iterable)((List)lllllllllllllllllIllIIlllIllIIll);
      lllllllllllllllllIllIIlllIlIlIII = false;
      lllllllllllllllllIllIIlllIllIIll = (Collection)(new ArrayList());
      lllllllllllllllllIllIIlllIlIIlIl = false;
      Iterator lllllllllllllllllIllIIlllIlIIlII = lllllllllllllllllIllIIlllIlIlIIl.iterator();

      while(lllllllllllllllllIllIIlllIlIIlII.hasNext()) {
         float lllllllllllllllllIllIIlllIlIIIll = lllllllllllllllllIllIIlllIlIIlII.next();
         if (lllllllllllllllllIllIIlllIlIIIll instanceof Value) {
            lllllllllllllllllIllIIlllIllIIll.add(lllllllllllllllllIllIIlllIlIIIll);
            var10001 = false;
         }
      }

      lllllllllllllllllIllIIlllIlIlIIl = (Iterable)((List)lllllllllllllllllIllIIlllIllIIll);
      lllllllllllllllllIllIIlllIlIlIII = false;
      String lllllllllllllllllIllIIlllIlIIllI = false;
      Iterator lllllllllllllllllIllIIlllIlIIlIl = lllllllllllllllllIllIIlllIlIlIIl.iterator();

      Object var23;
      while(true) {
         if (lllllllllllllllllIllIIlllIlIIlIl.hasNext()) {
            byte lllllllllllllllllIllIIlllIlIIlII = lllllllllllllllllIllIIlllIlIIlIl.next();
            float lllllllllllllllllIllIIlllIlIIIll = (Value)lllllllllllllllllIllIIlllIlIIlII;
            int lllllllllllllllllIllIIlllIlIlllI = false;
            if (!StringsKt.equals(lllllllllllllllllIllIIlllIlIIIll.getName(), lllllllllllllllllIllIIlllIlIllII, true)) {
               continue;
            }

            var23 = lllllllllllllllllIllIIlllIlIIlII;
            break;
         }

         var23 = null;
         break;
      }

      return (Value)var23;
   }

   public boolean handleEvents() {
      return lllllllllllllllllIllIIllIlllIIII.state;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public final float getSlide() {
      return lllllllllllllllllIllIlIllllIllll.slide;
   }

   public final void setHigt(float lllllllllllllllllIllIlIlllIIlllI) {
      lllllllllllllllllIllIlIlllIlIIIl.higt = lllllllllllllllllIllIlIlllIIlllI;
   }

   public final void setArray(boolean lllllllllllllllllIllIlIllllIIIlI) {
      lllllllllllllllllIllIlIllllIIIll.array = lllllllllllllllllIllIlIllllIIIlI;
   }

   @NotNull
   public final String breakname(boolean lllllllllllllllllIllIIllllIllIIl) {
      String lllllllllllllllllIllIIllllIllIll = lllllllllllllllllIllIIllllIllIlI.name;
      if (lllllllllllllllllIllIIllllIllIIl) {
         int lllllllllllllllllIllIIllllIlIlII = false;
         if (lllllllllllllllllIllIIllllIllIll == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         String var10000 = lllllllllllllllllIllIIllllIllIll.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         short lllllllllllllllllIllIIllllIlIlIl = var10000;
         switch(lllllllllllllllllIllIIllllIlIlIl.hashCode()) {
         case -2117682893:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("antiblind")) {
               return "Anti Blind";
            }
            break;
         case -2073748310:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("longjump")) {
               return "Long Jump";
            }
            break;
         case -1958347623:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autorespawn")) {
               return "Auto Respawn";
            }
            break;
         case -1940969246:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("potionsaver")) {
               return "Potion Saver";
            }
            break;
         case -1745954712:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("keepalive")) {
               return "Keep Alive";
            }
            break;
         case -1684590384:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("highjump")) {
               return "High Jump";
            }
            break;
         case -1673170764:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("nofriends")) {
               return "No Friends";
            }
            break;
         case -1650930624:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("midclick")) {
               return "Mid Click";
            }
            break;
         case -1641764582:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("speedmine")) {
               return "Speed Mine";
            }
            break;
         case -1618493804:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("liquidchat")) {
               return "Liquid Chat";
            }
            break;
         case -1617904379:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("liquidwalk")) {
               return "Liquid Walk";
            }
            break;
         case -1411744210:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("itemrotate")) {
               return "Item Rotate";
            }
            break;
         case -1217012392:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("hitbox")) {
               return "Hit Box";
            }
            break;
         case -1205362161:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("anticactus")) {
               return "Anti Cactus";
            }
            break;
         case -1202220504:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("hytrun")) {
               return "HYT Run";
            }
            break;
         case -1077118994:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("fastbow")) {
               return "Fast Bow";
            }
            break;
         case -1077100629:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("fastuse")) {
               return "Fast Use";
            }
            break;
         case -1040193391:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("noclip")) {
               return "No Clip";
            }
            break;
         case -1040114500:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("nofall")) {
               return "No Fall";
            }
            break;
         case -1018771300:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("icespeed")) {
               return "Ice Speed";
            }
            break;
         case -1006627833:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("bufferspeed")) {
               return "Buffer Speed";
            }
            break;
         case -846896572:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("antiafk")) {
               return "Anti AFK";
            }
            break;
         case -846895323:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("antibot")) {
               return "Anti Bot";
            }
            break;
         case -815567418:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("targethud")) {
               return "Target HUD";
            }
            break;
         case -664576555:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("blockesp")) {
               return "Block ESP";
            }
            break;
         case -664575802:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("blockfly")) {
               return "Block Fly";
            }
            break;
         case -646312517:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autobow")) {
               return "Auto Bow";
            }
            break;
         case -646299066:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autopot")) {
               return "Auto Potion";
            }
            break;
         case -603799069:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("freecam")) {
               return "Free Cam";
            }
            break;
         case -584901197:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("abortbreaking")) {
               return "Abort Breaking";
            }
            break;
         case -483845667:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("antifall")) {
               return "Anti Fall";
            }
            break;
         case -360334121:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("pingspoof")) {
               return "Ping Spoof";
            }
            break;
         case -260519514:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("faststairs")) {
               return "Fast Stairs";
            }
            break;
         case -246859590:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("consolespammer")) {
               return "Console Spammer";
            }
            break;
         case -60613723:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("invcleaner")) {
               return "Inv Cleaner";
            }
            break;
         case -58684039:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("lagback")) {
               return "Lag Back";
            }
            break;
         case -24159709:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("fastbreak")) {
               return "Fast Break";
            }
            break;
         case -23410727:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("fastclimb")) {
               return "Fast Climb";
            }
            break;
         case -11412949:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("fastplace")) {
               return "Fast Place";
            }
            break;
         case 11068132:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("noslowdown")) {
               return "No Slow";
            }
            break;
         case 105011699:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("noweb")) {
               return "No Web";
            }
            break;
         case 257638572:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("bowaimbot")) {
               return "Bow Aimbot";
            }
            break;
         case 326577179:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("cheststealer")) {
               return "Chest Stealer";
            }
            break;
         case 375554528:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("targetstrafe")) {
               return "Target Strafe";
            }
            break;
         case 633626763:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autoweapon")) {
               return "Auto Weapon";
            }
            break;
         case 786282275:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("blockoverlay")) {
               return "Block Overlay";
            }
            break;
         case 865360868:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("chestaura")) {
               return "Chest Aura";
            }
            break;
         case 873482198:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("blockwalk")) {
               return "Block Walk";
            }
            break;
         case 1068975772:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("keepcontainer")) {
               return "Keep Container";
            }
            break;
         case 1271744037:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("pointeresp")) {
               return "Pointer ESP";
            }
            break;
         case 1424958241:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("memoryfixer")) {
               return "Memory Fixer";
            }
            break;
         case 1439261831:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autofish")) {
               return "Auto Fish";
            }
            break;
         case 1439317007:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autohead")) {
               return "Auto Head";
            }
            break;
         case 1439392349:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autojump")) {
               return "Auto Jump";
            }
            break;
         case 1439654950:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autosoup")) {
               return "Auto Soup";
            }
            break;
         case 1439684551:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autotool")) {
               return "Auto Tool";
            }
            break;
         case 1439760376:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autowalk")) {
               return "Auto Walk";
            }
            break;
         case 1663088880:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autoarmor")) {
               return "Auto Armor";
            }
            break;
         case 1679863214:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autosword")) {
               return "Auto Sword";
            }
            break;
         case 1765327574:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("safewalk")) {
               return "Safe Walk";
            }
            break;
         case 1841525028:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("nametags")) {
               return "Name Tags";
            }
            break;
         case 1960145730:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("invmove")) {
               return "Inv Move";
            }
            break;
         case 2096371902:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("playerface")) {
               return "Player Face";
            }
            break;
         case 2099756966:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("autoclicker")) {
               return "Auto Clicker";
            }
            break;
         case 2116211087:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("itemesp")) {
               return "Item ESP";
            }
            break;
         case 2128847325:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("noswing")) {
               return "No Swing";
            }
            break;
         case 2144066940:
            if (lllllllllllllllllIllIIllllIlIlIl.equals("skinderp")) {
               return "Skin Derp";
            }
         }
      }

      return lllllllllllllllllIllIIllllIllIll;
   }

   public final float getHue() {
      return lllllllllllllllllIllIlIlllllIIlI.hue;
   }

   public final void setCategory(@NotNull ModuleCategory lllllllllllllllllIllIllIIIIlIIII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIllIIIIlIIII, "<set-?>");
      lllllllllllllllllIllIllIIIIlIIIl.category = lllllllllllllllllIllIllIIIIlIIII;
   }

   @NotNull
   public List<Value<?>> getValues() {
      Field[] var10000 = lllllllllllllllllIllIIllIlllllll.getClass().getDeclaredFields();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "javaClass.declaredFields");
      short lllllllllllllllllIllIIllIllllllI = var10000;
      int lllllllllllllllllIllIIllIlllllIl = false;
      Collection lllllllllllllllllIllIIlllIIIIlII = (Collection)(new ArrayList(lllllllllllllllllIllIIllIllllllI.length));
      float lllllllllllllllllIllIIllIllllIlI = false;
      Exception lllllllllllllllllIllIIllIllllIIl = lllllllllllllllllIllIIllIllllllI;
      int lllllllllllllllllIllIIllIllllIII = lllllllllllllllllIllIIllIllllllI.length;

      boolean var10001;
      for(int lllllllllllllllllIllIIllIlllIlll = 0; lllllllllllllllllIllIIllIlllIlll < lllllllllllllllllIllIIllIllllIII; ++lllllllllllllllllIllIIllIlllIlll) {
         Object lllllllllllllllllIllIIlllIIIllII = lllllllllllllllllIllIIllIllllIIl[lllllllllllllllllIllIIllIlllIlll];
         int lllllllllllllllllIllIIlllIIIllIl = false;
         Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIIlllIIIllII, "valueField");
         lllllllllllllllllIllIIlllIIIllII.setAccessible(true);
         float lllllllllllllllllIllIIllIlllIIlI = lllllllllllllllllIllIIlllIIIllII.get(lllllllllllllllllIllIIllIlllllll);
         lllllllllllllllllIllIIlllIIIIlII.add(lllllllllllllllllIllIIllIlllIIlI);
         var10001 = false;
      }

      short lllllllllllllllllIllIIllIllllllI = (Iterable)((List)lllllllllllllllllIllIIlllIIIIlII);
      lllllllllllllllllIllIIllIlllllIl = false;
      lllllllllllllllllIllIIlllIIIIlII = (Collection)(new ArrayList());
      lllllllllllllllllIllIIllIllllIlI = false;
      Iterator lllllllllllllllllIllIIllIllllIIl = lllllllllllllllllIllIIllIllllllI.iterator();

      while(lllllllllllllllllIllIIllIllllIIl.hasNext()) {
         Object lllllllllllllllllIllIIlllIIIIllI = lllllllllllllllllIllIIllIllllIIl.next();
         if (lllllllllllllllllIllIIlllIIIIllI instanceof Value) {
            lllllllllllllllllIllIIlllIIIIlII.add(lllllllllllllllllIllIIlllIIIIllI);
            var10001 = false;
         }
      }

      return (List)lllllllllllllllllIllIIlllIIIIlII;
   }

   @NotNull
   public final String getTagName() {
      return String.valueOf((new StringBuilder()).append(lllllllllllllllllIllIlIlllIIlIlI.breakname(lllllllllllllllllIllIlIlllIIlIlI.BreakName)).append(lllllllllllllllllIllIlIlllIIlIlI.getTag() == null ? "" : String.valueOf((new StringBuilder()).append(" §7").append(lllllllllllllllllIllIlIlllIIlIlI.getTag()))));
   }

   public final void setKeyBind(int lllllllllllllllllIllIllIIIIIlIIl) {
      lllllllllllllllllIllIllIIIIIlIII.keyBind = lllllllllllllllllIllIllIIIIIlIIl;
      LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
   }

   public final void toggle() {
      lllllllllllllllllIllIIllllIlIIlI.setState(!lllllllllllllllllIllIIllllIlIIlI.state);
   }

   public Module() {
      Annotation var10000 = lllllllllllllllllIllIIllIllIlIll.getClass().getAnnotation(ModuleInfo.class);
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      ModuleInfo lllllllllllllllllIllIIllIllIllII = (ModuleInfo)var10000;
      lllllllllllllllllIllIIllIllIlIll.name = lllllllllllllllllIllIIllIllIllII.name();
      lllllllllllllllllIllIIllIllIlIll.description = lllllllllllllllllIllIIllIllIllII.description();
      lllllllllllllllllIllIIllIllIlIll.category = lllllllllllllllllIllIIllIllIllII.category();
      lllllllllllllllllIllIIllIllIlIll.setKeyBind(lllllllllllllllllIllIIllIllIllII.keyBind());
      lllllllllllllllllIllIIllIllIlIll.canEnable = lllllllllllllllllIllIIllIllIllII.canEnable();
      lllllllllllllllllIllIIllIllIlIll.hue = (float)Math.random();
      lllllllllllllllllIllIIllIllIlIll.array = true;
   }

   @NotNull
   public final ModuleCategory getCategory() {
      return lllllllllllllllllIllIllIIIIlIlll.category;
   }

   public void onToggle(boolean lllllllllllllllllIllIIllllIIllll) {
   }

   public final float getSlideStep() {
      return lllllllllllllllllIllIllIIIIIIlII.slideStep;
   }

   public final boolean getArray() {
      return lllllllllllllllllIllIlIllllIIllI.array;
   }

   public final int getKeyBind() {
      return lllllllllllllllllIllIllIIIIIlllI.keyBind;
   }

   public final float getHigt() {
      return lllllllllllllllllIllIlIlllIlIlII.higt;
   }

   public final void setSlide(float lllllllllllllllllIllIlIllllIlIIl) {
      lllllllllllllllllIllIlIllllIllII.slide = lllllllllllllllllIllIlIllllIlIIl;
   }

   @NotNull
   public final String getName() {
      return lllllllllllllllllIllIllIIIlIlIIl.name;
   }

   public final void setName(@NotNull String lllllllllllllllllIllIllIIIlIIIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIllIIIlIIIlI, "<set-?>");
      lllllllllllllllllIllIllIIIlIIlIl.name = lllllllllllllllllIllIllIIIlIIIlI;
   }

   public final void setDescription(@NotNull String lllllllllllllllllIllIllIIIIllIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIllIIIIllIIl, "<set-?>");
      lllllllllllllllllIllIllIIIIlllII.description = lllllllllllllllllIllIllIIIIllIIl;
   }

   @Nullable
   public String getTag() {
      return null;
   }

   public final void setState(boolean lllllllllllllllllIllIlIlllllIlll) {
      if (lllllllllllllllllIllIlIllllllIII.state != lllllllllllllllllIllIlIlllllIlll) {
         lllllllllllllllllIllIlIllllllIII.onToggle(lllllllllllllllllIllIlIlllllIlll);
         if (!LiquidBounce.INSTANCE.isStarting()) {
            Minecraft var10000 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
            var10000.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("random.click"), 1.0F));
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification(String.valueOf((new StringBuilder()).append(lllllllllllllllllIllIlIlllllIlll ? "Enabled " : "Disabled ").append(lllllllllllllllllIllIlIllllllIII.name))));
            boolean var10001 = false;
         }

         if (lllllllllllllllllIllIlIlllllIlll) {
            lllllllllllllllllIllIlIllllllIII.onEnable();
            if (lllllllllllllllllIllIlIllllllIII.canEnable) {
               lllllllllllllllllIllIlIllllllIII.state = true;
            }
         } else {
            lllllllllllllllllIllIlIllllllIII.onDisable();
            lllllllllllllllllIllIlIllllllIII.state = false;
         }

         LiquidBounce.INSTANCE.getFileManager().saveConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
      }
   }
}
