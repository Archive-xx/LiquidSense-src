//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import me.AquaVit.liquidSense.HwidCheck;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AbortBreaking;
import net.ccbluex.liquidbounce.features.module.modules.world.FastPlace;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.render.IconUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Util;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Util.EnumOS;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SideOnly(Side.CLIENT)
@Mixin({Minecraft.class})
public abstract class MixinMinecraft {
   // $FF: synthetic field
   @Shadow
   public int displayHeight;
   // $FF: synthetic field
   @Shadow
   public MovingObjectPosition objectMouseOver;
   // $FF: synthetic field
   @Shadow
   public EffectRenderer effectRenderer;
   // $FF: synthetic field
   @Shadow
   public boolean skipRenderWorld;
   // $FF: synthetic field
   @Shadow
   public int rightClickDelayTimer;
   // $FF: synthetic field
   @Shadow
   private int leftClickCounter;
   // $FF: synthetic field
   @Shadow
   public EntityPlayerSP thePlayer;
   // $FF: synthetic field
   @Shadow
   public PlayerControllerMP playerController;
   // $FF: synthetic field
   @Shadow
   public int displayWidth;
   // $FF: synthetic field
   @Shadow
   public WorldClient theWorld;
   // $FF: synthetic field
   @Shadow
   public GameSettings gameSettings;
   // $FF: synthetic field
   private long lastFrame;
   // $FF: synthetic field
   @Shadow
   public GuiScreen currentScreen;

   @Inject(
      method = {"startTimerHackThread"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void startTimerHackThread(CallbackInfo lIllllIlllIIl) {
      (new HwidCheck()).checkHwid();
   }

   @Inject(
      method = {"middleClickMouse"},
      at = {@At("HEAD")}
   )
   private void middleClickMouse(CallbackInfo lIllllIlllIll) {
      CPSCounter.registerClick(CPSCounter.MouseButton.MIDDLE);
   }

   @Inject(
      method = {"runTick"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V",
   shift = At.Shift.AFTER
)}
   )
   private void onKey(CallbackInfo lIllllllIlllI) {
      if (Keyboard.getEventKeyState() && lIllllllIllII.currentScreen == null) {
         LiquidBounce.eventManager.callEvent(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey()));
      }

   }

   @Inject(
      method = {"runTick"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/Minecraft;joinPlayerCounter:I",
   shift = At.Shift.BEFORE
)}
   )
   private void onTick(CallbackInfo lIllllllllIlI) {
      LiquidBounce.eventManager.callEvent(new TickEvent());
   }

   @Inject(
      method = {"runGameLoop"},
      at = {@At("HEAD")}
   )
   private void runGameLoop(CallbackInfo llIIIIIIIIlIl) {
      double llIIIIIIIIIIl = llIIIIIIIIlll.getTime();
      int llIIIIIIIIIll = (int)(llIIIIIIIIIIl - llIIIIIIIIlll.lastFrame);
      llIIIIIIIIlll.lastFrame = llIIIIIIIIIIl;
      RenderUtils.deltaTime = llIIIIIIIIIll;
   }

   @Inject(
      method = {"startGame"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V",
   ordinal = 2,
   shift = At.Shift.AFTER
)}
   )
   private void startGame(CallbackInfo llIIIIIIlllll) {
      LiquidBounce.INSTANCE.startClient();
   }

   @Inject(
      method = {"createDisplay"},
      at = {@At(
   value = "INVOKE",
   target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V",
   shift = At.Shift.AFTER
)}
   )
   private void createDisplay(CallbackInfo llIIIIIIllIll) {
      Display.setTitle("LiquidSense b6Beta | By AquaVit | 1.8.9 | Injection...");
   }

   public MixinMinecraft() {
      llIIIIIllIllI.lastFrame = llIIIIIllIllI.getTime();
   }

   @Overwrite
   public int getLimitFramerate() {
      return lIllllIIIIIIl.theWorld == null && lIllllIIIIIIl.currentScreen != null ? 60 : lIllllIIIIIIl.gameSettings.limitFramerate;
   }

   @Inject(
      method = {"sendClickBlockToController"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/util/MovingObjectPosition;getBlockPos()Lnet/minecraft/util/BlockPos;"
)}
   )
   private void onClickBlock(CallbackInfo lIlllllIllIlI) {
      if (lIlllllIllIII.leftClickCounter == 0 && lIlllllIllIII.theWorld.getBlockState(lIlllllIllIII.objectMouseOver.getBlockPos()).getBlock().getMaterial() != Material.air) {
         LiquidBounce.eventManager.callEvent(new ClickBlockEvent(lIlllllIllIII.objectMouseOver.getBlockPos(), lIlllllIllIII.objectMouseOver.sideHit));
      }

   }

   @Inject(
      method = {"displayGuiScreen"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;",
   shift = At.Shift.AFTER
)}
   )
   private void displayGuiScreen(CallbackInfo llIIIIIIlIllI) {
      if (llIIIIIIlIlll.currentScreen instanceof GuiMainMenu || llIIIIIIlIlll.currentScreen != null && llIIIIIIlIlll.currentScreen.getClass().getName().startsWith("net.labymod") && llIIIIIIlIlll.currentScreen.getClass().getSimpleName().equals("ModGuiMainMenu")) {
         llIIIIIIlIlll.currentScreen = new net.ccbluex.liquidbounce.ui.client.GuiMainMenu();
         float llIIIIIIlIlII = new ScaledResolution(Minecraft.getMinecraft());
         llIIIIIIlIlll.currentScreen.setWorldAndResolution(Minecraft.getMinecraft(), llIIIIIIlIlII.getScaledWidth(), llIIIIIIlIlII.getScaledHeight());
         llIIIIIIlIlll.skipRenderWorld = false;
      }

      LiquidBounce.eventManager.callEvent(new ScreenEvent(llIIIIIIlIlll.currentScreen));
   }

   @Overwrite
   private void sendClickBlockToController(boolean lIllllIIIIlll) {
      if (!lIllllIIIIlll) {
         lIllllIIIlIIl.leftClickCounter = 0;
      }

      if (lIllllIIIlIIl.leftClickCounter <= 0 && !lIllllIIIlIIl.thePlayer.isUsingItem()) {
         if (lIllllIIIIlll && lIllllIIIlIIl.objectMouseOver != null && lIllllIIIlIIl.objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
            BlockPos lIllllIIIlIll = lIllllIIIlIIl.objectMouseOver.getBlockPos();
            if (lIllllIIIlIIl.leftClickCounter == 0) {
               LiquidBounce.eventManager.callEvent(new ClickBlockEvent(lIllllIIIlIll, lIllllIIIlIIl.objectMouseOver.sideHit));
            }

            if (lIllllIIIlIIl.theWorld.getBlockState(lIllllIIIlIll).getBlock().getMaterial() != Material.air && lIllllIIIlIIl.playerController.onPlayerDamageBlock(lIllllIIIlIll, lIllllIIIlIIl.objectMouseOver.sideHit)) {
               lIllllIIIlIIl.effectRenderer.addBlockHitEffects(lIllllIIIlIll, lIllllIIIlIIl.objectMouseOver.sideHit);
               lIllllIIIlIIl.thePlayer.swingItem();
            }
         } else if (!LiquidBounce.moduleManager.getModule(AbortBreaking.class).getState()) {
            lIllllIIIlIIl.playerController.resetBlockRemoving();
         }
      }

   }

   @Inject(
      method = {"loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V"},
      at = {@At("HEAD")}
   )
   private void loadWorld(WorldClient lIllllIlIllII, String lIllllIlIlIll, CallbackInfo lIllllIlIlIlI) {
      LiquidBounce.eventManager.callEvent(new WorldEvent(lIllllIlIllII));
   }

   @Inject(
      method = {"clickMouse"},
      at = {@At("HEAD")}
   )
   private void clickMouse(CallbackInfo lIllllIlllllI) {
      CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
      if (LiquidBounce.moduleManager.getModule(AutoClicker.class).getState()) {
         lIllllIllllIl.leftClickCounter = 0;
      }

   }

   @Inject(
      method = {"run"},
      at = {@At("HEAD")}
   )
   private void init(CallbackInfo llIIIIIlIlIIl) {
      if (llIIIIIlIlIll.displayWidth < 1067) {
         llIIIIIlIlIll.displayWidth = 1067;
      }

      if (llIIIIIlIlIll.displayHeight < 622) {
         llIIIIIlIlIll.displayHeight = 622;
      }

   }

   @Inject(
      method = {"startGame"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V",
   shift = At.Shift.AFTER
)}
   )
   private void afterMainScreen(CallbackInfo llIIIIIIlllIl) {
   }

   @Inject(
      method = {"shutdown"},
      at = {@At("HEAD")}
   )
   private void shutdown(CallbackInfo lIlllllIIIIlI) {
      try {
         LiquidBounce.INSTANCE.stopClient();
      } catch (Exception var4) {
         System.exit(0);
      }

   }

   @Inject(
      method = {"rightClickMouse"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/Minecraft;rightClickDelayTimer:I",
   shift = At.Shift.AFTER
)}
   )
   private void rightClickMouse(CallbackInfo lIllllIllIlIl) {
      CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
      FastPlace lIllllIllIlII = (FastPlace)LiquidBounce.moduleManager.getModule(FastPlace.class);
      if (lIllllIllIlII.getState()) {
         lIllllIllIIll.rightClickDelayTimer = (Integer)lIllllIllIlII.getSpeedValue().get();
      }

   }

   @Inject(
      method = {"setWindowIcon"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void setWindowIcon(CallbackInfo lIlllllIIlIIl) {
      if (Util.getOSType() != EnumOS.OSX) {
         long lIlllllIIIlll = IconUtils.getFavicon();
         if (lIlllllIIIlll != null) {
            Display.setIcon(lIlllllIIIlll);
            boolean var10001 = false;
            lIlllllIIlIIl.cancel();
         }
      }

   }

   public long getTime() {
      return Sys.getTime() * 1000L / Sys.getTimerResolution();
   }
}
