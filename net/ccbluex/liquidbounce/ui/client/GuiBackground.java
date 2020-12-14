//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u0014J \u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\nH\u0016J\u0018\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0018"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiBackground;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "enabledButton", "Lnet/minecraft/client/gui/GuiButton;", "particlesButton", "getPrevGui", "()Lnet/minecraft/client/gui/GuiScreen;", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "Companion", "LiquidSense"}
)
public final class GuiBackground extends GuiScreen {
   // $FF: synthetic field
   @NotNull
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private GuiButton particlesButton;
   // $FF: synthetic field
   private static boolean enabled = true;
   // $FF: synthetic field
   public static final GuiBackground.Companion Companion = new GuiBackground.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private static boolean particles;
   // $FF: synthetic field
   private GuiButton enabledButton;

   public void initGui() {
      llIlIllIIlIIll.enabledButton = new GuiButton(1, llIlIllIIlIIll.width / 2 - 100, llIlIllIIlIIll.height / 4 + 35, String.valueOf((new StringBuilder()).append("Enabled (").append(enabled ? "On" : "Off").append(')')));
      List var10000 = llIlIllIIlIIll.buttonList;
      GuiButton var10001 = llIlIllIIlIIll.enabledButton;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
      }

      var10000.add(var10001);
      boolean var1 = false;
      llIlIllIIlIIll.particlesButton = new GuiButton(2, llIlIllIIlIIll.width / 2 - 100, llIlIllIIlIIll.height / 4 + 50 + 25, String.valueOf((new StringBuilder()).append("Particles (").append(particles ? "On" : "Off").append(')')));
      var10000 = llIlIllIIlIIll.buttonList;
      var10001 = llIlIllIIlIIll.particlesButton;
      if (var10001 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
      }

      var10000.add(var10001);
      var1 = false;
      llIlIllIIlIIll.buttonList.add(new GuiButton(3, llIlIllIIlIIll.width / 2 - 100, llIlIllIIlIIll.height / 4 + 50 + 50, 98, 20, "Change wallpaper"));
      var1 = false;
      llIlIllIIlIIll.buttonList.add(new GuiButton(4, llIlIllIIlIIll.width / 2 + 2, llIlIllIIlIIll.height / 4 + 50 + 50, 98, 20, "Reset wallpaper"));
      var1 = false;
      llIlIllIIlIIll.buttonList.add(new GuiButton(0, llIlIllIIlIIll.width / 2 - 100, llIlIllIIlIIll.height / 4 + 55 + 100 + 5, "Back"));
      var1 = false;
   }

   @NotNull
   public final GuiScreen getPrevGui() {
      return llIlIlIllIIIll.prevGui;
   }

   public void drawScreen(int llIlIlIlllIlII, int llIlIlIllIllll, float llIlIlIllIlllI) {
      llIlIlIlllIlIl.drawBackground(0);
      Fonts.fontBold180.drawCenteredString("Background", (float)llIlIlIlllIlIl.width / 2.0F, (float)llIlIlIlllIlIl.height / 8.0F + 5.0F, 4673984, true);
      boolean var10001 = false;
      super.drawScreen(llIlIlIlllIlII, llIlIlIllIllll, llIlIlIllIlllI);
   }

   protected void keyTyped(char llIlIlIllIlIIl, int llIlIlIllIlIII) {
      if (1 == llIlIlIllIlIII) {
         llIlIlIllIIlll.mc.displayGuiScreen(llIlIlIllIIlll.prevGui);
      } else {
         super.keyTyped(llIlIlIllIlIIl, llIlIlIllIlIII);
      }
   }

   public GuiBackground(@NotNull GuiScreen llIlIlIlIlllII) {
      Intrinsics.checkParameterIsNotNull(llIlIlIlIlllII, "prevGui");
      super();
      llIlIlIlIlllll.prevGui = llIlIlIlIlllII;
   }

   protected void actionPerformed(@NotNull GuiButton llIlIllIIIIlII) {
      Intrinsics.checkParameterIsNotNull(llIlIllIIIIlII, "button");
      GuiButton var16;
      boolean var10001;
      switch(llIlIllIIIIlII.id) {
      case 0:
         llIlIllIIIIIll.mc.displayGuiScreen(llIlIllIIIIIll.prevGui);
         break;
      case 1:
         enabled = !enabled;
         var16 = llIlIllIIIIIll.enabledButton;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
         }

         var16.displayString = String.valueOf((new StringBuilder()).append("Enabled (").append(enabled ? "On" : "Off").append(')'));
         break;
      case 2:
         particles = !particles;
         var16 = llIlIllIIIIIll.particlesButton;
         if (var16 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
         }

         var16.displayString = String.valueOf((new StringBuilder()).append("Particles (").append(particles ? "On" : "Off").append(')'));
         break;
      case 3:
         File var10000 = MiscUtils.openFileChooser();
         if (var10000 == null) {
            var10001 = false;
            return;
         }

         File llIlIllIIIIllI = var10000;
         if (llIlIllIIIIllI.isDirectory()) {
            return;
         }

         try {
            Files.copy(llIlIllIIIIllI.toPath(), (OutputStream)(new FileOutputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile)));
            String llIlIllIIIIIII = ImageIO.read((InputStream)(new FileInputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile)));
            LiquidBounce var13 = LiquidBounce.INSTANCE;
            StringBuilder var17 = new StringBuilder();
            char llIlIlIlllllll = "LiquidSense";
            float llIlIlIlllllII = var17;
            boolean llIlIlIlllllIl = var13;
            double llIlIlIllllllI = false;
            String var14 = llIlIlIlllllll.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var14, "(this as java.lang.String).toLowerCase()");
            char llIlIlIllllIll = var14;
            byte llIlIlIllllIlI = String.valueOf(llIlIlIlllllII.append(llIlIlIllllIll).append("/background.png"));
            llIlIlIlllllIl.setBackground(new ResourceLocation(llIlIlIllllIlI));
            Minecraft var15 = llIlIllIIIIIll.mc;
            Intrinsics.checkExpressionValueIsNotNull(var15, "mc");
            var15.getTextureManager().loadTexture(LiquidBounce.INSTANCE.getBackground(), (ITextureObject)(new DynamicTexture(llIlIllIIIIIII)));
            var10001 = false;
         } catch (Exception var12) {
            var12.printStackTrace();
            MiscUtils.showErrorPopup("Error", String.valueOf((new StringBuilder()).append("Exception class: ").append(var12.getClass().getName()).append("\nMessage: ").append(var12.getMessage())));
            LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
            var10001 = false;
         }
         break;
      case 4:
         LiquidBounce.INSTANCE.setBackground((ResourceLocation)null);
         LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
         var10001 = false;
      }

   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\f"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiBackground$Companion;", "", "()V", "enabled", "", "getEnabled", "()Z", "setEnabled", "(Z)V", "particles", "getParticles", "setParticles", "LiquidSense"}
   )
   public static final class Companion {
      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lIIllIIIllIlII) {
         this();
      }

      public final boolean getParticles() {
         return GuiBackground.particles;
      }

      public final boolean getEnabled() {
         return GuiBackground.enabled;
      }

      private Companion() {
      }

      public final void setEnabled(boolean lIIllIIlIIIIII) {
         GuiBackground.enabled = lIIllIIlIIIIII;
      }

      public final void setParticles(boolean lIIllIIIlllIll) {
         GuiBackground.particles = lIIllIIIlllIll;
      }
   }
}
