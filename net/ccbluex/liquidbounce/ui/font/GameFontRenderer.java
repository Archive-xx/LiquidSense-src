//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL20;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 02\u00020\u0001:\u00010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J&\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ.\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 J&\u0010!\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eJ0\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020 H\u0016J(\u0010#\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000eH\u0016J<\u0010$\u001a\u00020\u000e2\b\u0010\"\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020 2\b\b\u0002\u0010&\u001a\u00020 H\u0002J\u0010\u0010'\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020)H\u0016J\u0010\u0010,\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020\u001aH\u0016J\u0010\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020/H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0012\u001a\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010¨\u00061"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer;", "Lnet/minecraft/client/gui/FontRenderer;", "font", "Ljava/awt/Font;", "(Ljava/awt/Font;)V", "boldFont", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "boldItalicFont", "defaultFont", "getDefaultFont", "()Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "setDefaultFont", "(Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;)V", "height", "", "getHeight", "()I", "italicFont", "size", "getSize", "bindTexture", "", "location", "Lnet/minecraft/util/ResourceLocation;", "drawCenteredString", "s", "", "x", "", "y", "color", "shadow", "", "drawString", "text", "drawStringWithShadow", "drawText", "ignoreColor", "rainbow", "getCharWidth", "character", "", "getColorCode", "charCode", "getStringWidth", "onResourceManagerReload", "resourceManager", "Lnet/minecraft/client/resources/IResourceManager;", "Companion", "LiquidSense"}
)
public final class GameFontRenderer extends FontRenderer {
   // $FF: synthetic field
   @NotNull
   private AWTFontRenderer defaultFont;
   // $FF: synthetic field
   private AWTFontRenderer boldItalicFont;
   // $FF: synthetic field
   public static final GameFontRenderer.Companion Companion = new GameFontRenderer.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private AWTFontRenderer italicFont;
   // $FF: synthetic field
   private AWTFontRenderer boldFont;

   public int drawString(@NotNull String llllllllllllllllllllIIIllllIlIII, float llllllllllllllllllllIIIllllIllIl, float llllllllllllllllllllIIIllllIIllI, int llllllllllllllllllllIIIllllIIlIl, boolean llllllllllllllllllllIIIllllIIlII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIIllllIlIII, "text");
      TextEvent llllllllllllllllllllIIIlllllIIIl = new TextEvent(llllllllllllllllllllIIIllllIlIII);
      LiquidBounce.INSTANCE.getEventManager().callEvent((Event)llllllllllllllllllllIIIlllllIIIl);
      String var10000 = llllllllllllllllllllIIIlllllIIIl.getText();
      boolean var10001;
      if (var10000 != null) {
         String llllllllllllllllllllIIIlllllIIII = var10000;
         float llllllllllllllllllllIIIlllllIIlI = llllllllllllllllllllIIIllllIIllI - 3.0F;
         float llllllllllllllllllllIIIllllIIIII = RainbowFontShader.INSTANCE.isInUse();
         if (llllllllllllllllllllIIIllllIIlII) {
            GL20.glUseProgram(0);
            drawText$default(llllllllllllllllllllIIIllllIllll, llllllllllllllllllllIIIlllllIIII, llllllllllllllllllllIIIllllIllIl + 0.5F, llllllllllllllllllllIIIlllllIIlI + 0.5F, (new Color(0, 0, 0, 120)).getRGB(), true, false, 32, (Object)null);
            var10001 = false;
         }

         return llllllllllllllllllllIIIllllIllll.drawText(llllllllllllllllllllIIIlllllIIII, llllllllllllllllllllIIIllllIllIl, llllllllllllllllllllIIIlllllIIlI, llllllllllllllllllllIIIllllIIlIl, false, llllllllllllllllllllIIIllllIIIII);
      } else {
         var10001 = false;
         return 0;
      }
   }

   public void onResourceManagerReload(@NotNull IResourceManager llllllllllllllllllllIIIlIIIlllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIIlIIIlllll, "resourceManager");
   }

   @NotNull
   public final AWTFontRenderer getDefaultFont() {
      return llllllllllllllllllllIIlIIlIIlIlI.defaultFont;
   }

   @JvmStatic
   public static final int getColorIndex(char llllllllllllllllllllIIIlIIIlIlII) {
      return Companion.getColorIndex(llllllllllllllllllllIIIlIIIlIlII);
   }

   // $FF: synthetic method
   static int drawText$default(GameFontRenderer var0, String var1, float var2, float var3, int var4, boolean var5, boolean var6, int var7, Object var8) {
      if ((var7 & 32) != 0) {
         var6 = false;
      }

      return var0.drawText(var1, var2, var3, var4, var5, var6);
   }

   public GameFontRenderer(@NotNull Font llllllllllllllllllllIIIlIIIlIllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIIlIIIlIllI, "font");
      GameSettings var10001 = Minecraft.getMinecraft().gameSettings;
      ResourceLocation var10002 = new ResourceLocation("textures/font/ascii.png");
      TextureManager var10003;
      if (ClassUtils.INSTANCE.hasForge()) {
         var10003 = null;
      } else {
         Minecraft var2 = Minecraft.getMinecraft();
         Intrinsics.checkExpressionValueIsNotNull(var2, "Minecraft.getMinecraft()");
         var10003 = var2.getTextureManager();
      }

      super(var10001, var10002, var10003, false);
      llllllllllllllllllllIIIlIIIllIIl.defaultFont = new AWTFontRenderer(llllllllllllllllllllIIIlIIIlIllI, 0, 0, 6, (DefaultConstructorMarker)null);
      Font var3 = llllllllllllllllllllIIIlIIIlIllI.deriveFont(1);
      Intrinsics.checkExpressionValueIsNotNull(var3, "font.deriveFont(Font.BOLD)");
      llllllllllllllllllllIIIlIIIllIIl.boldFont = new AWTFontRenderer(var3, 0, 0, 6, (DefaultConstructorMarker)null);
      var3 = llllllllllllllllllllIIIlIIIlIllI.deriveFont(2);
      Intrinsics.checkExpressionValueIsNotNull(var3, "font.deriveFont(Font.ITALIC)");
      llllllllllllllllllllIIIlIIIllIIl.italicFont = new AWTFontRenderer(var3, 0, 0, 6, (DefaultConstructorMarker)null);
      var3 = llllllllllllllllllllIIIlIIIlIllI.deriveFont(3);
      Intrinsics.checkExpressionValueIsNotNull(var3, "font.deriveFont(Font.BOLD or Font.ITALIC)");
      llllllllllllllllllllIIIlIIIllIIl.boldItalicFont = new AWTFontRenderer(var3, 0, 0, 6, (DefaultConstructorMarker)null);
      llllllllllllllllllllIIIlIIIllIIl.FONT_HEIGHT = llllllllllllllllllllIIIlIIIllIIl.getHeight();
   }

   public int getCharWidth(char llllllllllllllllllllIIIlIIlIIIlI) {
      return llllllllllllllllllllIIIlIIlIIIll.getStringWidth(String.valueOf(llllllllllllllllllllIIIlIIlIIIlI));
   }

   public final int drawString(@NotNull String llllllllllllllllllllIIlIIIllIIIl, float llllllllllllllllllllIIlIIIllIIII, float llllllllllllllllllllIIlIIIlIllll, int llllllllllllllllllllIIlIIIllIIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIlIIIllIIIl, "s");
      return llllllllllllllllllllIIlIIIllIlll.drawString(llllllllllllllllllllIIlIIIllIIIl, llllllllllllllllllllIIlIIIllIIII, llllllllllllllllllllIIlIIIlIllll, llllllllllllllllllllIIlIIIllIIll, false);
   }

   public final int drawCenteredString(@NotNull String llllllllllllllllllllIIlIIIIlIIIl, float llllllllllllllllllllIIlIIIIlIIII, float llllllllllllllllllllIIlIIIIIllll, int llllllllllllllllllllIIlIIIIIlllI, boolean llllllllllllllllllllIIlIIIIIllIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIlIIIIlIIIl, "s");
      return llllllllllllllllllllIIlIIIIlIIlI.drawString(llllllllllllllllllllIIlIIIIlIIIl, llllllllllllllllllllIIlIIIIlIIII - (float)llllllllllllllllllllIIlIIIIlIIlI.getStringWidth(llllllllllllllllllllIIlIIIIlIIIl) / 2.0F, llllllllllllllllllllIIlIIIIIllll, llllllllllllllllllllIIlIIIIIlllI, llllllllllllllllllllIIlIIIIIllIl);
   }

   public int getStringWidth(@NotNull String llllllllllllllllllllIIIlIlIIIIII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIIlIlIIIIII, "text");
      TextEvent llllllllllllllllllllIIIlIlIIIIll = new TextEvent(llllllllllllllllllllIIIlIlIIIIII);
      LiquidBounce.INSTANCE.getEventManager().callEvent((Event)llllllllllllllllllllIIIlIlIIIIll);
      String var10000 = llllllllllllllllllllIIIlIlIIIIll.getText();
      if (var10000 == null) {
         boolean var10001 = false;
         return 0;
      } else {
         String llllllllllllllllllllIIIlIlIIIIlI = var10000;
         int var24;
         if (!StringsKt.contains$default((CharSequence)llllllllllllllllllllIIIlIlIIIIlI, (CharSequence)"§", false, 2, (Object)null)) {
            var24 = llllllllllllllllllllIIIlIlIIIIIl.defaultFont.getStringWidth(llllllllllllllllllllIIIlIlIIIIlI) / 1;
         } else {
            List llllllllllllllllllllIIIlIlIIIlII = StringsKt.split$default((CharSequence)llllllllllllllllllllIIIlIlIIIIlI, new String[]{"§"}, false, 0, 6, (Object)null);
            float llllllllllllllllllllIIIlIIlllIlI = llllllllllllllllllllIIIlIlIIIIIl.defaultFont;
            int llllllllllllllllllllIIIlIlIIIllI = 0;
            boolean llllllllllllllllllllIIIlIlIIIlll = false;
            boolean llllllllllllllllllllIIIlIlIIlIII = false;
            long llllllllllllllllllllIIIlIIllIllI = (Iterable)llllllllllllllllllllIIIlIlIIIlII;
            int llllllllllllllllllllIIIlIlIIlIIl = false;
            float llllllllllllllllllllIIIlIIllIlII = 0;
            Iterator llllllllllllllllllllIIIlIIllIIll = llllllllllllllllllllIIIlIIllIllI.iterator();

            while(true) {
               if (!llllllllllllllllllllIIIlIIllIIll.hasNext()) {
                  var24 = llllllllllllllllllllIIIlIlIIIllI / 1;
                  break;
               }

               Object llllllllllllllllllllIIIlIlIIllII = llllllllllllllllllllIIIlIIllIIll.next();
               String llllllllllllllllllllIIIlIIllIIIl = llllllllllllllllllllIIIlIIllIlII++;
               String llllllllllllllllllllIIIlIIllIIII = false;
               if (llllllllllllllllllllIIIlIIllIIIl < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               String llllllllllllllllllllIIIlIlIIlllI = (String)llllllllllllllllllllIIIlIlIIllII;
               short llllllllllllllllllllIIIlIIlIllII = false;
               Exception llllllllllllllllllllIIIlIIlIlIll = (CharSequence)llllllllllllllllllllIIIlIlIIlllI;
               boolean llllllllllllllllllllIIIlIIlIlIlI = false;
               if (llllllllllllllllllllIIIlIIlIlIll.length() != 0) {
                  if (llllllllllllllllllllIIIlIIllIIIl == 0) {
                     llllllllllllllllllllIIIlIlIIIllI += llllllllllllllllllllIIIlIIlllIlI.getStringWidth(llllllllllllllllllllIIIlIlIIlllI);
                  } else {
                     double llllllllllllllllllllIIIlIIlIlIIl = 1;
                     Exception llllllllllllllllllllIIIlIIlIlIII = false;
                     if (llllllllllllllllllllIIIlIlIIlllI == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     var10000 = llllllllllllllllllllIIIlIlIIlllI.substring(llllllllllllllllllllIIIlIIlIlIIl);
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
                     Exception llllllllllllllllllllIIIlIIlIlIll = var10000;
                     boolean llllllllllllllllllllIIIlIIlIlIlI = llllllllllllllllllllIIIlIlIIlllI.charAt(0);
                     int llllllllllllllllllllIIIlIlIlIIlI = Companion.getColorIndex(llllllllllllllllllllIIIlIIlIlIlI);
                     if (llllllllllllllllllllIIIlIlIlIIlI < 16) {
                        llllllllllllllllllllIIIlIlIIIlll = false;
                        llllllllllllllllllllIIIlIlIIlIII = false;
                     } else if (llllllllllllllllllllIIIlIlIlIIlI == 17) {
                        llllllllllllllllllllIIIlIlIIIlll = true;
                     } else if (llllllllllllllllllllIIIlIlIlIIlI == 20) {
                        llllllllllllllllllllIIIlIlIIlIII = true;
                     } else if (llllllllllllllllllllIIIlIlIlIIlI == 21) {
                        llllllllllllllllllllIIIlIlIIIlll = false;
                        llllllllllllllllllllIIIlIlIIlIII = false;
                     }

                     llllllllllllllllllllIIIlIIlllIlI = llllllllllllllllllllIIIlIlIIIlll && llllllllllllllllllllIIIlIlIIlIII ? llllllllllllllllllllIIIlIlIIIIIl.boldItalicFont : (llllllllllllllllllllIIIlIlIIIlll ? llllllllllllllllllllIIIlIlIIIIIl.boldFont : (llllllllllllllllllllIIIlIlIIlIII ? llllllllllllllllllllIIIlIlIIIIIl.italicFont : llllllllllllllllllllIIIlIlIIIIIl.defaultFont));
                     llllllllllllllllllllIIIlIlIIIllI += llllllllllllllllllllIIIlIIlllIlI.getStringWidth(llllllllllllllllllllIIIlIIlIlIll);
                  }
               }
            }
         }

         return var24;
      }
   }

   public final void setDefaultFont(@NotNull AWTFontRenderer llllllllllllllllllllIIlIIlIIIIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIlIIlIIIIll, "<set-?>");
      llllllllllllllllllllIIlIIlIIIllI.defaultFont = llllllllllllllllllllIIlIIlIIIIll;
   }

   private final int drawText(String llllllllllllllllllllIIIllIlIIllI, float llllllllllllllllllllIIIllIIllllI, float llllllllllllllllllllIIIllIIlllIl, int llllllllllllllllllllIIIllIIlllII, boolean llllllllllllllllllllIIIllIlIIIlI, boolean llllllllllllllllllllIIIllIlIIIIl) {
      if (llllllllllllllllllllIIIllIlIIllI == null) {
         return 0;
      } else {
         short llllllllllllllllllllIIIllIIllIIl = (CharSequence)llllllllllllllllllllIIIllIlIIllI;
         boolean llllllllllllllllllllIIIllIIllIII = false;
         double llllllllllllllllllllIIIllIIlIlll = false;
         if (llllllllllllllllllllIIIllIIllIIl.length() == 0) {
            return (int)llllllllllllllllllllIIIllIIllllI;
         } else {
            short llllllllllllllllllllIIIllIIllIIl = RainbowFontShader.INSTANCE.getProgramId();
            if (llllllllllllllllllllIIIllIlIIIIl) {
               GL20.glUseProgram(llllllllllllllllllllIIIllIIllIIl);
            }

            GlStateManager.translate((double)llllllllllllllllllllIIIllIIllllI - 1.5D, (double)llllllllllllllllllllIIIllIIlllIl + 0.5D, 0.0D);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.enableTexture2D();
            int llllllllllllllllllllIIIllIlIlIIl = llllllllllllllllllllIIIllIIlllII;
            if ((llllllllllllllllllllIIIllIIlllII & -67108864) == 0) {
               llllllllllllllllllllIIIllIlIlIIl = llllllllllllllllllllIIIllIIlllII | -16777216;
            }

            byte llllllllllllllllllllIIIllIIlIllI = llllllllllllllllllllIIIllIlIlIIl >> 24 & 255;
            if (StringsKt.contains$default((CharSequence)llllllllllllllllllllIIIllIlIIllI, (CharSequence)"§", false, 2, (Object)null)) {
               List llllllllllllllllllllIIIllIlIllII = StringsKt.split$default((CharSequence)llllllllllllllllllllIIIllIlIIllI, new String[]{"§"}, false, 0, 6, (Object)null);
               float llllllllllllllllllllIIIllIIlIlII = llllllllllllllllllllIIIllIlIIlll.defaultFont;
               double llllllllllllllllllllIIIllIlIlllI = 0.0D;
               boolean llllllllllllllllllllIIIllIlIllll = false;
               Exception llllllllllllllllllllIIIllIIlIIlI = false;
               Exception llllllllllllllllllllIIIllIIlIIIl = false;
               double llllllllllllllllllllIIIllIIlIIII = false;
               boolean llllllllllllllllllllIIIllIllIIll = false;
               double llllllllllllllllllllIIIllIIIlllI = (Iterable)llllllllllllllllllllIIIllIlIllII;
               int llllllllllllllllllllIIIllIllIlII = false;
               int llllllllllllllllllllIIIllIllIllI = 0;
               Iterator llllllllllllllllllllIIIllIIIlIll = llllllllllllllllllllIIIllIIIlllI.iterator();

               while(llllllllllllllllllllIIIllIIIlIll.hasNext()) {
                  Object llllllllllllllllllllIIIllIllIlll = llllllllllllllllllllIIIllIIIlIll.next();
                  float llllllllllllllllllllIIIllIIIlIIl = llllllllllllllllllllIIIllIllIllI++;
                  long llllllllllllllllllllIIIllIIIlIII = false;
                  if (llllllllllllllllllllIIIllIIIlIIl < 0) {
                     CollectionsKt.throwIndexOverflow();
                  }

                  boolean llllllllllllllllllllIIIllIIIIllI = (String)llllllllllllllllllllIIIllIllIlll;
                  Exception llllllllllllllllllllIIIllIIIIlII = false;
                  boolean llllllllllllllllllllIIIllIIIIIll = (CharSequence)llllllllllllllllllllIIIllIIIIllI;
                  char llllllllllllllllllllIIIllIIIIIlI = false;
                  if (llllllllllllllllllllIIIllIIIIIll.length() != 0) {
                     if (llllllllllllllllllllIIIllIIIlIIl == 0) {
                        llllllllllllllllllllIIIllIIlIlII.drawString(llllllllllllllllllllIIIllIIIIllI, llllllllllllllllllllIIIllIlIlllI, 0.0D, llllllllllllllllllllIIIllIlIlIIl);
                        llllllllllllllllllllIIIllIlIlllI += (double)llllllllllllllllllllIIIllIIlIlII.getStringWidth(llllllllllllllllllllIIIllIIIIllI);
                     } else {
                        int llllllllllllllllllllIIIllIIIIIIl = 1;
                        float llllllllllllllllllllIIIllIIIIIII = false;
                        if (llllllllllllllllllllIIIllIIIIllI == null) {
                           throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }

                        String llllllllllllllllllllIIIllIIIIIll;
                        label118: {
                           String var37 = llllllllllllllllllllIIIllIIIIllI.substring(llllllllllllllllllllIIIllIIIIIIl);
                           Intrinsics.checkExpressionValueIsNotNull(var37, "(this as java.lang.String).substring(startIndex)");
                           llllllllllllllllllllIIIllIIIIIll = var37;
                           char llllllllllllllllllllIIIllIIIIIlI = llllllllllllllllllllIIIllIIIIllI.charAt(0);
                           int llllllllllllllllllllIIIllIllllIl = Companion.getColorIndex(llllllllllllllllllllIIIllIIIIIlI);
                           if (0 > llllllllllllllllllllIIIllIllllIl) {
                              boolean var10001 = false;
                           } else if (15 >= llllllllllllllllllllIIIllIllllIl) {
                              if (!llllllllllllllllllllIIIllIlIIIlI) {
                                 llllllllllllllllllllIIIllIlIlIIl = ColorUtils.hexColors[llllllllllllllllllllIIIllIllllIl] | llllllllllllllllllllIIIllIIlIllI << 24;
                                 if (llllllllllllllllllllIIIllIlIIIIl) {
                                    GL20.glUseProgram(0);
                                 }
                              }

                              llllllllllllllllllllIIIllIIlIIlI = false;
                              llllllllllllllllllllIIIllIIlIIIl = false;
                              llllllllllllllllllllIIIllIlIllll = false;
                              llllllllllllllllllllIIIllIllIIll = false;
                              llllllllllllllllllllIIIllIIlIIII = false;
                              break label118;
                           }

                           if (llllllllllllllllllllIIIllIllllIl == 16) {
                              llllllllllllllllllllIIIllIlIllll = true;
                           } else if (llllllllllllllllllllIIIllIllllIl == 17) {
                              llllllllllllllllllllIIIllIIlIIlI = true;
                           } else if (llllllllllllllllllllIIIllIllllIl == 18) {
                              llllllllllllllllllllIIIllIIlIIII = true;
                           } else if (llllllllllllllllllllIIIllIllllIl == 19) {
                              llllllllllllllllllllIIIllIllIIll = true;
                           } else if (llllllllllllllllllllIIIllIllllIl == 20) {
                              llllllllllllllllllllIIIllIIlIIIl = true;
                           } else if (llllllllllllllllllllIIIllIllllIl == 21) {
                              llllllllllllllllllllIIIllIlIlIIl = llllllllllllllllllllIIIllIIlllII;
                              if ((llllllllllllllllllllIIIllIIlllII & -67108864) == 0) {
                                 llllllllllllllllllllIIIllIlIlIIl = llllllllllllllllllllIIIllIIlllII | -16777216;
                              }

                              if (llllllllllllllllllllIIIllIlIIIIl) {
                                 GL20.glUseProgram(llllllllllllllllllllIIIllIIllIIl);
                              }

                              llllllllllllllllllllIIIllIIlIIlI = false;
                              llllllllllllllllllllIIIllIIlIIIl = false;
                              llllllllllllllllllllIIIllIlIllll = false;
                              llllllllllllllllllllIIIllIllIIll = false;
                              llllllllllllllllllllIIIllIIlIIII = false;
                           }
                        }

                        llllllllllllllllllllIIIllIIlIlII = llllllllllllllllllllIIIllIIlIIlI && llllllllllllllllllllIIIllIIlIIIl ? llllllllllllllllllllIIIllIlIIlll.boldItalicFont : (llllllllllllllllllllIIIllIIlIIlI ? llllllllllllllllllllIIIllIlIIlll.boldFont : (llllllllllllllllllllIIIllIIlIIIl ? llllllllllllllllllllIIIllIlIIlll.italicFont : llllllllllllllllllllIIIllIlIIlll.defaultFont));
                        llllllllllllllllllllIIIllIIlIlII.drawString(llllllllllllllllllllIIIllIlIllll ? ColorUtils.INSTANCE.randomMagicText(llllllllllllllllllllIIIllIIIIIll) : llllllllllllllllllllIIIllIIIIIll, llllllllllllllllllllIIIllIlIlllI, 0.0D, llllllllllllllllllllIIIllIlIlIIl);
                        if (llllllllllllllllllllIIIllIIlIIII) {
                           RenderUtils.drawLine(llllllllllllllllllllIIIllIlIlllI / 2.0D + (double)1, (double)llllllllllllllllllllIIIllIIlIlII.getHeight() / 3.0D, (llllllllllllllllllllIIIllIlIlllI + (double)llllllllllllllllllllIIIllIIlIlII.getStringWidth(llllllllllllllllllllIIIllIIIIIll)) / 2.0D + (double)1, (double)llllllllllllllllllllIIIllIIlIlII.getHeight() / 3.0D, (float)llllllllllllllllllllIIIllIlIIlll.FONT_HEIGHT / 16.0F);
                        }

                        if (llllllllllllllllllllIIIllIllIIll) {
                           RenderUtils.drawLine(llllllllllllllllllllIIIllIlIlllI / 2.0D + (double)1, (double)llllllllllllllllllllIIIllIIlIlII.getHeight() / 2.0D, (llllllllllllllllllllIIIllIlIlllI + (double)llllllllllllllllllllIIIllIIlIlII.getStringWidth(llllllllllllllllllllIIIllIIIIIll)) / 2.0D + (double)1, (double)llllllllllllllllllllIIIllIIlIlII.getHeight() / 2.0D, (float)llllllllllllllllllllIIIllIlIIlll.FONT_HEIGHT / 16.0F);
                        }

                        llllllllllllllllllllIIIllIlIlllI += (double)llllllllllllllllllllIIIllIIlIlII.getStringWidth(llllllllllllllllllllIIIllIIIIIll);
                     }
                  }
               }
            } else {
               llllllllllllllllllllIIIllIlIIlll.defaultFont.drawString(llllllllllllllllllllIIIllIlIIllI, 0.0D, 0.0D, llllllllllllllllllllIIIllIlIlIIl);
            }

            GlStateManager.disableBlend();
            GlStateManager.translate(-((double)llllllllllllllllllllIIIllIIllllI - 1.5D), -((double)llllllllllllllllllllIIIllIIlllIl + 0.5D), 0.0D);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return (int)(llllllllllllllllllllIIIllIIllllI + (float)llllllllllllllllllllIIIllIlIIlll.getStringWidth(llllllllllllllllllllIIIllIlIIllI));
         }
      }
   }

   public final int getHeight() {
      return llllllllllllllllllllIIlIIlIIIIIl.defaultFont.getHeight();
   }

   public final int drawCenteredString(@NotNull String llllllllllllllllllllIIlIIIIIIllI, float llllllllllllllllllllIIlIIIIIIlIl, float llllllllllllllllllllIIIlllllllll, int llllllllllllllllllllIIIllllllllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIlIIIIIIllI, "s");
      return llllllllllllllllllllIIlIIIIIIIlI.drawStringWithShadow(llllllllllllllllllllIIlIIIIIIllI, llllllllllllllllllllIIlIIIIIIlIl - (float)llllllllllllllllllllIIlIIIIIIIlI.getStringWidth(llllllllllllllllllllIIlIIIIIIllI) / 2.0F, llllllllllllllllllllIIIlllllllll, llllllllllllllllllllIIIllllllllI);
   }

   public int getColorCode(char llllllllllllllllllllIIIlIllIlIll) {
      return ColorUtils.hexColors[Companion.getColorIndex(llllllllllllllllllllIIIlIllIlIll)];
   }

   public int drawStringWithShadow(@NotNull String llllllllllllllllllllIIlIIIlIIIlI, float llllllllllllllllllllIIlIIIlIIIIl, float llllllllllllllllllllIIlIIIlIIIII, int llllllllllllllllllllIIlIIIlIIlII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIIlIIIlIIIlI, "text");
      return llllllllllllllllllllIIlIIIlIlIII.drawString(llllllllllllllllllllIIlIIIlIIIlI, llllllllllllllllllllIIlIIIlIIIIl, llllllllllllllllllllIIlIIIlIIIII, llllllllllllllllllllIIlIIIlIIlII, true);
   }

   protected void bindTexture(@Nullable ResourceLocation llllllllllllllllllllIIIlIIIlllII) {
   }

   public final int getSize() {
      return llllllllllllllllllllIIlIIIllllIl.defaultFont.getFont().getSize();
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\f\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/font/GameFontRenderer$Companion;", "", "()V", "getColorIndex", "", "type", "", "LiquidSense"}
   )
   public static final class Companion {
      @JvmStatic
      public final int getColorIndex(char lIlIIIlIIIIl) {
         int var10000;
         boolean var10001;
         if ('0' > lIlIIIlIIIIl) {
            var10001 = false;
         } else if ('9' >= lIlIIIlIIIIl) {
            var10000 = lIlIIIlIIIIl - 48;
            return var10000;
         }

         if ('a' > lIlIIIlIIIIl) {
            var10001 = false;
         } else if ('f' >= lIlIIIlIIIIl) {
            var10000 = lIlIIIlIIIIl - 97 + 10;
            return var10000;
         }

         if ('k' > lIlIIIlIIIIl) {
            var10001 = false;
         } else if ('o' >= lIlIIIlIIIIl) {
            var10000 = lIlIIIlIIIIl - 107 + 16;
            return var10000;
         }

         var10000 = lIlIIIlIIIIl == 'r' ? 21 : -1;
         return var10000;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lIlIIIIlIllI) {
         this();
      }
   }
}
