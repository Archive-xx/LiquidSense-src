//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.Random;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;

@ModuleInfo(
   name = "SpammerMessage",
   description = "Spams the chat with a given message.",
   category = ModuleCategory.MISC
)
public class Spammer extends Module {
   // $FF: synthetic field
   private final IntegerValue maxDelayValue;
   // $FF: synthetic field
   private final MSTimer msTimer;
   // $FF: synthetic field
   private final TextValue messageValue;
   // $FF: synthetic field
   private long delay;
   // $FF: synthetic field
   private final IntegerValue minDelayValue;
   // $FF: synthetic field
   private final BoolValue customValue;

   @EventTarget
   public void onUpdate(UpdateEvent llIlIllllIIIlI) {
      if (llIlIllllIIIIl.msTimer.hasTimePassed(llIlIllllIIIIl.delay)) {
         mc.thePlayer.sendChatMessage((Boolean)llIlIllllIIIIl.customValue.get() ? llIlIllllIIIIl.replace((String)llIlIllllIIIIl.messageValue.get()) : String.valueOf((new StringBuilder()).append((String)llIlIllllIIIIl.messageValue.get()).append(" >").append(RandomUtils.randomString(5 + (new Random()).nextInt(5))).append("<")));
         llIlIllllIIIIl.msTimer.reset();
         llIlIllllIIIIl.delay = TimeUtils.randomDelay((Integer)llIlIllllIIIIl.minDelayValue.get(), (Integer)llIlIllllIIIIl.maxDelayValue.get());
      }

   }

   private String replace(String llIlIlllIlllIl) {
      Random llIlIlllIllIlI;
      for(llIlIlllIllIlI = new Random(); llIlIlllIlllIl.contains("%f"); llIlIlllIlllIl = String.valueOf((new StringBuilder()).append(llIlIlllIlllIl.substring(0, llIlIlllIlllIl.indexOf("%f"))).append(llIlIlllIllIlI.nextFloat()).append(llIlIlllIlllIl.substring(llIlIlllIlllIl.indexOf("%f") + "%f".length())))) {
      }

      while(llIlIlllIlllIl.contains("%i")) {
         llIlIlllIlllIl = String.valueOf((new StringBuilder()).append(llIlIlllIlllIl.substring(0, llIlIlllIlllIl.indexOf("%i"))).append(llIlIlllIllIlI.nextInt(10000)).append(llIlIlllIlllIl.substring(llIlIlllIlllIl.indexOf("%i") + "%i".length())));
      }

      while(llIlIlllIlllIl.contains("%s")) {
         llIlIlllIlllIl = String.valueOf((new StringBuilder()).append(llIlIlllIlllIl.substring(0, llIlIlllIlllIl.indexOf("%s"))).append(RandomUtils.randomString(llIlIlllIllIlI.nextInt(8) + 1)).append(llIlIlllIlllIl.substring(llIlIlllIlllIl.indexOf("%s") + "%s".length())));
      }

      while(llIlIlllIlllIl.contains("%ss")) {
         llIlIlllIlllIl = String.valueOf((new StringBuilder()).append(llIlIlllIlllIl.substring(0, llIlIlllIlllIl.indexOf("%ss"))).append(RandomUtils.randomString(llIlIlllIllIlI.nextInt(4) + 1)).append(llIlIlllIlllIl.substring(llIlIlllIlllIl.indexOf("%ss") + "%ss".length())));
      }

      while(llIlIlllIlllIl.contains("%ls")) {
         llIlIlllIlllIl = String.valueOf((new StringBuilder()).append(llIlIlllIlllIl.substring(0, llIlIlllIlllIl.indexOf("%ls"))).append(RandomUtils.randomString(llIlIlllIllIlI.nextInt(15) + 1)).append(llIlIlllIlllIl.substring(llIlIlllIlllIl.indexOf("%ls") + "%ls".length())));
      }

      return llIlIlllIlllIl;
   }

   public Spammer() {
      llIlIllllIIlIl.maxDelayValue = new IntegerValue("MaxDelay", 1000, 0, 5000) {
         protected void onChanged(Integer lIIlIIIllIIIII, Integer lIIlIIIlIlllll) {
            int lIIlIIIlIllllI = (Integer)llIlIllllIIlIl.minDelayValue.get();
            if (lIIlIIIlIllllI > lIIlIIIlIlllll) {
               lIIlIIIllIIIIl.set(lIIlIIIlIllllI);
            }

            llIlIllllIIlIl.delay = TimeUtils.randomDelay((Integer)llIlIllllIIlIl.minDelayValue.get(), (Integer)llIlIllllIIlIl.maxDelayValue.get());
         }
      };
      llIlIllllIIlIl.minDelayValue = new IntegerValue("MinDelay", 500, 0, 5000) {
         protected void onChanged(Integer lIlllllIlIIlll, Integer lIlllllIlIIllI) {
            int lIlllllIlIIlIl = (Integer)llIlIllllIIlIl.maxDelayValue.get();
            if (lIlllllIlIIlIl < lIlllllIlIIllI) {
               lIlllllIlIlIII.set(lIlllllIlIIlIl);
            }

            llIlIllllIIlIl.delay = TimeUtils.randomDelay((Integer)llIlIllllIIlIl.minDelayValue.get(), (Integer)llIlIllllIIlIl.maxDelayValue.get());
         }
      };
      llIlIllllIIlIl.messageValue = new TextValue("Message", "LiquidSense Client | liquidbounce(.net) | CCBlueX on yt");
      llIlIllllIIlIl.customValue = new BoolValue("Custom", false);
      llIlIllllIIlIl.msTimer = new MSTimer();
      llIlIllllIIlIl.delay = TimeUtils.randomDelay((Integer)llIlIllllIIlIl.minDelayValue.get(), (Integer)llIlIllllIIlIl.maxDelayValue.get());
   }
}
