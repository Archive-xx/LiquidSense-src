//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0014J \u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\b\u0010\u0011\u001a\u00020\u0007H\u0016J\u0018\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\fH\u0014R\u0012\u0010\u0004\u001a\u00060\u0005R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;", "Lnet/minecraft/client/gui/GuiScreen;", "prevGui", "(Lnet/minecraft/client/gui/GuiScreen;)V", "list", "Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "actionPerformed", "", "button", "Lnet/minecraft/client/gui/GuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "GuiList", "LiquidSense"}
)
public final class GuiScripts extends GuiScreen {
   // $FF: synthetic field
   private final GuiScreen prevGui;
   // $FF: synthetic field
   private GuiScripts.GuiList list;

   public void initGui() {
      lIlllIIIllllIl.list = new GuiScripts.GuiList((GuiScreen)lIlllIIIllllIl);
      GuiScripts.GuiList var10000 = lIlllIIIllllIl.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.registerScrollButtons(7, 8);
      var10000 = lIlllIIIllllIl.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.elementClicked(-1, false, 0, 0);
      int lIlllIIIllllll = 22;
      lIlllIIIllllIl.buttonList.add(new GuiButton(0, lIlllIIIllllIl.width - 80, lIlllIIIllllIl.height - 65, 70, 20, "Back"));
      boolean var10001 = false;
      lIlllIIIllllIl.buttonList.add(new GuiButton(1, lIlllIIIllllIl.width - 80, lIlllIIIllllll + 24, 70, 20, "Import"));
      var10001 = false;
      lIlllIIIllllIl.buttonList.add(new GuiButton(2, lIlllIIIllllIl.width - 80, lIlllIIIllllll + 48, 70, 20, "Delete"));
      var10001 = false;
      lIlllIIIllllIl.buttonList.add(new GuiButton(3, lIlllIIIllllIl.width - 80, lIlllIIIllllll + 72, 70, 20, "Reload"));
      var10001 = false;
      lIlllIIIllllIl.buttonList.add(new GuiButton(4, lIlllIIIllllIl.width - 80, lIlllIIIllllll + 96, 70, 20, "Folder"));
      var10001 = false;
      lIlllIIIllllIl.buttonList.add(new GuiButton(5, lIlllIIIllllIl.width - 80, lIlllIIIllllll + 120, 70, 20, "Docs"));
      var10001 = false;
      lIlllIIIllllIl.buttonList.add(new GuiButton(6, lIlllIIIllllIl.width - 80, lIlllIIIllllll + 144, 70, 20, "Find Scripts"));
      var10001 = false;
   }

   public GuiScripts(@NotNull GuiScreen lIllIllIlllIII) {
      Intrinsics.checkParameterIsNotNull(lIllIllIlllIII, "prevGui");
      super();
      lIllIllIlllIIl.prevGui = lIllIllIlllIII;
   }

   protected void actionPerformed(@NotNull GuiButton lIllIlllIlIIll) {
      Intrinsics.checkParameterIsNotNull(lIllIlllIlIIll, "button");
      switch(lIllIlllIlIIll.id) {
      case 0:
         lIllIlllIlIllI.mc.displayGuiScreen(lIllIlllIlIllI.prevGui);
         break;
      case 1:
         try {
            File var26 = MiscUtils.openFileChooser();
            boolean var27;
            if (var26 == null) {
               var27 = false;
               return;
            }

            File lIllIlllIlllII = var26;
            char lIllIlllIlIIIl = lIllIlllIlllII.getName();
            Intrinsics.checkExpressionValueIsNotNull(lIllIlllIlIIIl, "fileName");
            if (StringsKt.endsWith$default(lIllIlllIlIIIl, ".js", false, 2, (Object)null)) {
               LiquidBounce.INSTANCE.getScriptManager().importScript(lIllIlllIlllII);
               LiquidBounce.INSTANCE.setClickGui(new ClickGui());
               LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
               return;
            }

            if (StringsKt.endsWith$default(lIllIlllIlIIIl, ".zip", false, 2, (Object)null)) {
               String lIllIlllIlIIII = new ZipFile(lIllIlllIlllII);
               Enumeration lIllIlllIlllll = lIllIlllIlIIII.entries();
               ArrayList lIllIlllIIlllI = new ArrayList();

               while(lIllIlllIlllll.hasMoreElements()) {
                  int lIllIlllIIllIl = (ZipEntry)lIllIlllIlllll.nextElement();
                  Intrinsics.checkExpressionValueIsNotNull(lIllIlllIIllIl, "entry");
                  String lIllIllllIIlll = lIllIlllIIllIl.getName();
                  File lIllIllllIlIII = new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), lIllIllllIIlll);
                  if (lIllIlllIIllIl.isDirectory()) {
                     lIllIllllIlIII.mkdir();
                     var27 = false;
                  } else {
                     Exception lIllIlllIIlIlI = lIllIlllIlIIII.getInputStream(lIllIlllIIllIl);
                     FileOutputStream lIllIllllIlIlI = new FileOutputStream(lIllIllllIlIII);
                     IOUtils.copy(lIllIlllIIlIlI, (OutputStream)lIllIllllIlIlI);
                     var27 = false;
                     lIllIllllIlIlI.close();
                     lIllIlllIIlIlI.close();
                     Intrinsics.checkExpressionValueIsNotNull(lIllIllllIIlll, "entryName");
                     if (!StringsKt.contains$default((CharSequence)lIllIllllIIlll, (CharSequence)"/", false, 2, (Object)null)) {
                        lIllIlllIIlllI.add(lIllIllllIlIII);
                        var27 = false;
                     }
                  }
               }

               Iterable lIllIllllIIIlI = (Iterable)lIllIlllIIlllI;
               int lIllIllllIIIIl = false;
               Iterator lIllIlllIIlIll = lIllIllllIIIlI.iterator();

               while(lIllIlllIIlIll.hasNext()) {
                  Exception lIllIlllIIlIlI = lIllIlllIIlIll.next();
                  File lIllIllllIIlIl = (File)lIllIlllIIlIlI;
                  int lIllIllllIIlII = false;
                  LiquidBounce.INSTANCE.getScriptManager().loadScript(lIllIllllIIlIl);
               }

               LiquidBounce.INSTANCE.setClickGui(new ClickGui());
               LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
               LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
               return;
            }

            MiscUtils.showErrorPopup("Wrong file extension.", "The file extension has to be .js or .zip");
         } catch (Throwable var18) {
            ClientUtils.getLogger().error("Something went wrong while importing a script.", var18);
            MiscUtils.showErrorPopup(var18.getClass().getName(), var18.getMessage());
         }
         break;
      case 2:
         try {
            GuiScripts.GuiList var10000 = lIllIlllIlIllI.list;
            if (var10000 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("list");
            }

            if (var10000.getSelectedSlot$LiquidSense() != -1) {
               List var25 = LiquidBounce.INSTANCE.getScriptManager().getScripts();
               GuiScripts.GuiList var10001 = lIllIlllIlIllI.list;
               if (var10001 == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("list");
               }

               Script lIllIlllIllIlI = (Script)var25.get(var10001.getSelectedSlot$LiquidSense());
               LiquidBounce.INSTANCE.getScriptManager().deleteScript(lIllIlllIllIlI);
               LiquidBounce.INSTANCE.setClickGui(new ClickGui());
               LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
               LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
            }
         } catch (Throwable var17) {
            ClientUtils.getLogger().error("Something went wrong while deleting a script.", var17);
            MiscUtils.showErrorPopup(var17.getClass().getName(), var17.getMessage());
         }
         break;
      case 3:
         try {
            LiquidBounce.INSTANCE.getScriptManager().reloadScripts();
         } catch (Throwable var16) {
            ClientUtils.getLogger().error("Something went wrong while reloading all scripts.", var16);
            MiscUtils.showErrorPopup(var16.getClass().getName(), var16.getMessage());
         }
         break;
      case 4:
         try {
            Desktop.getDesktop().open(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder());
         } catch (Throwable var15) {
            ClientUtils.getLogger().error("Something went wrong while trying to open your scripts folder.", var15);
            MiscUtils.showErrorPopup(var15.getClass().getName(), var15.getMessage());
         }
         break;
      case 5:
         try {
            Desktop.getDesktop().browse((new URL("https://liquidbounce.net/docs/ScriptAPI/Getting%20Started")).toURI());
         } catch (Exception var14) {
         }
         break;
      case 6:
         try {
            Desktop.getDesktop().browse((new URL("https://forum.ccbluex.net/viewforum.php?id=16")).toURI());
         } catch (Exception var13) {
         }
      }

   }

   public void drawScreen(int lIlllIIIllIIIl, int lIlllIIIllIIII, float lIlllIIIllIlII) {
      lIlllIIIllIIlI.drawBackground(0);
      GuiScripts.GuiList var10000 = lIlllIIIllIIlI.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.drawScreen(lIlllIIIllIIIl, lIlllIIIllIIII, lIlllIIIllIlII);
      lIlllIIIllIIlI.drawCenteredString((FontRenderer)Fonts.font40, "§9§lScripts", lIlllIIIllIIlI.width / 2, 28, 16777215);
      super.drawScreen(lIlllIIIllIIIl, lIlllIIIllIIII, lIlllIIIllIlII);
   }

   protected void keyTyped(char lIllIlllIIIIll, int lIllIlllIIIIlI) {
      if (1 == lIllIlllIIIIlI) {
         lIllIlllIIIIIl.mc.displayGuiScreen(lIllIlllIIIIIl.prevGui);
      } else {
         super.keyTyped(lIllIlllIIIIll, lIllIlllIIIIlI);
      }
   }

   public void handleMouseInput() {
      super.handleMouseInput();
      GuiScripts.GuiList var10000 = lIllIllIllllIl.list;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("list");
      }

      var10000.handleMouseInput();
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0014J8\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0014J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0016J\r\u0010\u0014\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0006H\u0014J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u0006H\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "Lnet/minecraft/client/gui/GuiSlot;", "gui", "Lnet/minecraft/client/gui/GuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;Lnet/minecraft/client/gui/GuiScreen;)V", "selectedSlot", "", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "doubleClick", "", "var3", "getSelectedSlot", "getSelectedSlot$LiquidSense", "getSize", "isSelected", "LiquidSense"}
   )
   private final class GuiList extends GuiSlot {
      // $FF: synthetic field
      private int selectedSlot;

      protected void drawSlot(int lllllllllllllllllIlIlllIlllIIIII, int lllllllllllllllllIlIlllIlllIIllI, int lllllllllllllllllIlIlllIlllIIlIl, int lllllllllllllllllIlIlllIlllIIlII, int lllllllllllllllllIlIlllIlllIIIll, int lllllllllllllllllIlIlllIlllIIIlI) {
         Script lllllllllllllllllIlIlllIlllIlIIl = (Script)LiquidBounce.INSTANCE.getScriptManager().getScripts().get(lllllllllllllllllIlIlllIlllIIIII);
         GuiScripts var10000 = GuiScripts.this;
         FontRenderer var10001 = (FontRenderer)Fonts.font40;
         String var10002 = String.valueOf((new StringBuilder()).append("§9").append(lllllllllllllllllIlIlllIlllIlIIl.getScriptName()).append(" §7v").append(lllllllllllllllllIlIlllIlllIlIIl.getScriptVersion()));
         int var10003 = lllllllllllllllllIlIlllIlllIIIIl.width / 2;
         int var10004 = lllllllllllllllllIlIlllIlllIIlIl + 2;
         Color var10005 = Color.LIGHT_GRAY;
         Intrinsics.checkExpressionValueIsNotNull(var10005, "Color.LIGHT_GRAY");
         var10000.drawCenteredString(var10001, var10002, var10003, var10004, var10005.getRGB());
         var10000 = GuiScripts.this;
         var10001 = (FontRenderer)Fonts.font40;
         var10002 = String.valueOf((new StringBuilder()).append("by §c").append(lllllllllllllllllIlIlllIlllIlIIl.getScriptAuthor()));
         var10003 = lllllllllllllllllIlIlllIlllIIIIl.width / 2;
         var10004 = lllllllllllllllllIlIlllIlllIIlIl + 15;
         var10005 = Color.LIGHT_GRAY;
         Intrinsics.checkExpressionValueIsNotNull(var10005, "Color.LIGHT_GRAY");
         var10000.drawCenteredString(var10001, var10002, var10003, var10004, var10005.getRGB());
      }

      protected int getSize() {
         return LiquidBounce.INSTANCE.getScriptManager().getScripts().size();
      }

      public final int getSelectedSlot$LiquidSense() {
         return lllllllllllllllllIlIlllIlllllIIl.selectedSlot > LiquidBounce.INSTANCE.getScriptManager().getScripts().size() ? -1 : lllllllllllllllllIlIlllIlllllIIl.selectedSlot;
      }

      protected boolean isSelected(int lllllllllllllllllIlIlllIlllllIll) {
         return lllllllllllllllllIlIlllIlllllllI.selectedSlot == lllllllllllllllllIlIlllIlllllIll;
      }

      protected void drawBackground() {
      }

      public void elementClicked(int lllllllllllllllllIlIlllIllllIIll, boolean lllllllllllllllllIlIlllIllllIIlI, int lllllllllllllllllIlIlllIllllIIIl, int lllllllllllllllllIlIlllIllllIIII) {
         lllllllllllllllllIlIlllIllllIlII.selectedSlot = lllllllllllllllllIlIlllIllllIIll;
      }

      public GuiList(@NotNull GuiScreen lllllllllllllllllIlIlllIllIlIlll) {
         Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlIlllIllIlIlll, "gui");
         super(GuiScripts.this.mc, lllllllllllllllllIlIlllIllIlIlll.width, lllllllllllllllllIlIlllIllIlIlll.height, 40, lllllllllllllllllIlIlllIllIlIlll.height - 40, 30);
      }
   }
}
