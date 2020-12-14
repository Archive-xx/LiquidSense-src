//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0006\b\u0007\u0018\u0000 ,2\u00020\u0001:\u0002+,B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u001a\u001a\u00020\u001bH\u0002J \u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\u0002J&\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020'2\u0006\u0010 \u001a\u00020'2\u0006\u0010(\u001a\u00020\u0005J\u000e\u0010)\u001a\u00020\u00052\u0006\u0010&\u001a\u00020\nJ\u0018\u0010*\u001a\u00020\u001b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0002R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000eX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006-"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "", "font", "Ljava/awt/Font;", "startChar", "", "stopChar", "(Ljava/awt/Font;II)V", "cachedStrings", "Ljava/util/HashMap;", "", "Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "Lkotlin/collections/HashMap;", "charLocations", "", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "[Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "getFont", "()Ljava/awt/Font;", "fontHeight", "height", "getHeight", "()I", "textureHeight", "textureID", "textureWidth", "collectGarbage", "", "drawChar", "char", "x", "", "y", "drawCharToImage", "Ljava/awt/image/BufferedImage;", "ch", "", "drawString", "text", "", "color", "getStringWidth", "renderBitmap", "CharLocation", "Companion", "LiquidSense"}
)
public final class AWTFontRenderer {
   // $FF: synthetic field
   private int fontHeight;
   // $FF: synthetic field
   public static final AWTFontRenderer.Companion Companion = new AWTFontRenderer.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private static final int CACHED_FONT_REMOVAL_TIME = 30000;
   // $FF: synthetic field
   private int textureHeight;
   // $FF: synthetic field
   private int textureID;
   // $FF: synthetic field
   @NotNull
   private static final ArrayList<AWTFontRenderer> activeFontRenderers = new ArrayList();
   // $FF: synthetic field
   private final HashMap<String, CachedFont> cachedStrings;
   // $FF: synthetic field
   private int textureWidth;
   // $FF: synthetic field
   @NotNull
   private final Font font;
   // $FF: synthetic field
   private static int gcTicks;
   // $FF: synthetic field
   private static boolean assumeNonVolatile;
   // $FF: synthetic field
   private final AWTFontRenderer.CharLocation[] charLocations;
   // $FF: synthetic field
   private static final int GC_TICKS = 600;

   private final void collectGarbage() {
      long llIlIIlllllIIl = System.currentTimeMillis();
      float llIlIIllllIlIl = (Map)llIlIIllllIlll.cachedStrings;
      int llIlIIllllIlII = false;
      Map llIlIlIIIIIIlI = (Map)(new LinkedHashMap());
      byte llIlIIllllIIIl = false;
      long llIlIIlllIllll = false;
      Iterator llIlIIlllIlllI = llIlIIllllIlIl.entrySet().iterator();

      boolean var10001;
      while(llIlIIlllIlllI.hasNext()) {
         Entry llIlIlIIIIIlII = (Entry)llIlIIlllIlllI.next();
         int llIlIlIIIIIlIl = false;
         if (llIlIIlllllIIl - ((CachedFont)llIlIlIIIIIlII.getValue()).getLastUsage() > (long)30000) {
            llIlIlIIIIIIlI.put(llIlIlIIIIIlII.getKey(), llIlIlIIIIIlII.getValue());
            var10001 = false;
         }
      }

      llIlIIllllIlII = false;
      double llIlIIllllIIll = llIlIlIIIIIIlI;
      Exception llIlIIllllIIlI = false;

      for(Iterator llIlIIllllIIIl = llIlIIllllIIll.entrySet().iterator(); llIlIIllllIIIl.hasNext(); var10001 = false) {
         short llIlIIllllIIII = (Entry)llIlIIllllIIIl.next();
         int llIlIIllllllIl = false;
         GL11.glDeleteLists(((CachedFont)llIlIIllllIIII.getValue()).getDisplayList(), 1);
         ((CachedFont)llIlIIllllIIII.getValue()).setDeleted(true);
         llIlIIllllIlll.cachedStrings.remove(llIlIIllllIIII.getKey());
      }

   }

   private final void renderBitmap(int llIlIIIlllIlII, int llIlIIIlllIIll) {
      BufferedImage[] llIlIIIllllIIl = new BufferedImage[llIlIIIlllIIll];
      int llIlIIIllllIlI = 0;
      int llIlIIIlllIIII = 0;
      char llIlIIIllIllll = 0;
      char llIlIIIllIlllI = llIlIIIlllIlII;

      for(int llIlIIIllIllIl = llIlIIIlllIIll; llIlIIIllIlllI < llIlIIIllIllIl; ++llIlIIIllIlllI) {
         Exception llIlIIIllIllII = llIlIIIlllIlIl.drawCharToImage((char)llIlIIIllIlllI);
         boolean llIlIIIllIlIll = new AWTFontRenderer.CharLocation(llIlIIIlllIIII, llIlIIIllIllll, llIlIIIllIllII.getWidth(), llIlIIIllIllII.getHeight());
         if (llIlIIIllIlIll.getHeight() > llIlIIIlllIlIl.fontHeight) {
            llIlIIIlllIlIl.fontHeight = llIlIIIllIlIll.getHeight();
         }

         if (llIlIIIllIlIll.getHeight() > llIlIIIllllIlI) {
            llIlIIIllllIlI = llIlIIIllIlIll.getHeight();
         }

         llIlIIIlllIlIl.charLocations[llIlIIIllIlllI] = llIlIIIllIlIll;
         llIlIIIllllIIl[llIlIIIllIlllI] = llIlIIIllIllII;
         llIlIIIlllIIII += llIlIIIllIlIll.getWidth();
         if (llIlIIIlllIIII > 2048) {
            if (llIlIIIlllIIII > llIlIIIlllIlIl.textureWidth) {
               llIlIIIlllIlIl.textureWidth = llIlIIIlllIIII;
            }

            llIlIIIlllIIII = 0;
            llIlIIIllIllll += llIlIIIllllIlI;
            llIlIIIllllIlI = 0;
         }
      }

      llIlIIIlllIlIl.textureHeight = llIlIIIllIllll + llIlIIIllllIlI;
      char llIlIIIllIlllI = new BufferedImage(llIlIIIlllIlIl.textureWidth, llIlIIIlllIlIl.textureHeight, 2);
      Graphics var10000 = llIlIIIllIlllI.getGraphics();
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
      } else {
         float llIlIIIllIllIl = (Graphics2D)var10000;
         llIlIIIllIllIl.setFont(llIlIIIlllIlIl.font);
         llIlIIIllIllIl.setColor(new Color(255, 255, 255, 0));
         llIlIIIllIllIl.fillRect(0, 0, llIlIIIlllIlIl.textureWidth, llIlIIIlllIlIl.textureHeight);
         llIlIIIllIllIl.setColor(Color.white);
         Exception llIlIIIllIllII = llIlIIIlllIlII;

         for(int llIlIIIllIlIll = llIlIIIlllIIll; llIlIIIllIllII < llIlIIIllIlIll; ++llIlIIIllIllII) {
            if (llIlIIIllllIIl[llIlIIIllIllII] != null && llIlIIIlllIlIl.charLocations[llIlIIIllIllII] != null) {
               Image var10001 = (Image)llIlIIIllllIIl[llIlIIIllIllII];
               AWTFontRenderer.CharLocation var10002 = llIlIIIlllIlIl.charLocations[llIlIIIllIllII];
               if (var10002 == null) {
                  Intrinsics.throwNpe();
               }

               int var16 = var10002.getX();
               AWTFontRenderer.CharLocation var10003 = llIlIIIlllIlIl.charLocations[llIlIIIllIllII];
               if (var10003 == null) {
                  Intrinsics.throwNpe();
               }

               llIlIIIllIllIl.drawImage(var10001, var16, var10003.getY(), (ImageObserver)null);
               boolean var15 = false;
            }
         }

         llIlIIIlllIlIl.textureID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), llIlIIIllIlllI, true, true);
      }
   }

   private final BufferedImage drawCharToImage(char llIlIIIlIllIIl) {
      Graphics var10000 = (new BufferedImage(1, 1, 2)).getGraphics();
      if (var10000 == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
      } else {
         int llIlIIIlIllIII = (Graphics2D)var10000;
         llIlIIIlIllIII.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         llIlIIIlIllIII.setFont(llIlIIIlIllIlI.font);
         byte llIlIIIlIlIlll = llIlIIIlIllIII.getFontMetrics();
         char llIlIIIlIlIllI = llIlIIIlIlIlll.charWidth(llIlIIIlIllIIl) + 8;
         if (llIlIIIlIlIllI <= 0) {
            llIlIIIlIlIllI = 7;
         }

         Intrinsics.checkExpressionValueIsNotNull(llIlIIIlIlIlll, "fontMetrics");
         long llIlIIIlIlIlIl = llIlIIIlIlIlll.getHeight() + 3;
         if (llIlIIIlIlIlIl <= 0) {
            llIlIIIlIlIlIl = llIlIIIlIllIlI.font.getSize();
         }

         BufferedImage llIlIIIllIIIIl = new BufferedImage(llIlIIIlIlIllI, llIlIIIlIlIlIl, 2);
         var10000 = llIlIIIllIIIIl.getGraphics();
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.awt.Graphics2D");
         } else {
            float llIlIIIlIlIIll = (Graphics2D)var10000;
            llIlIIIlIlIIll.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            llIlIIIlIlIIll.setFont(llIlIIIlIllIlI.font);
            llIlIIIlIlIIll.setColor(Color.WHITE);
            llIlIIIlIlIIll.drawString(String.valueOf(llIlIIIlIllIIl), 3, 1 + llIlIIIlIlIlll.getAscent());
            return llIlIIIllIIIIl;
         }
      }
   }

   public final int getStringWidth(@NotNull String llIlIIIlIIIlII) {
      Intrinsics.checkParameterIsNotNull(llIlIIIlIIIlII, "text");
      String llIlIIIlIIIIll = 0;
      char llIlIIIIlllllI = false;
      char[] var10000 = llIlIIIlIIIlII.toCharArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
      byte llIlIIIlIIIIII = var10000;
      char llIlIIIIllllll = llIlIIIlIIIIII.length;

      for(int llIlIIIlIIIIIl = 0; llIlIIIlIIIIIl < llIlIIIIllllll; ++llIlIIIlIIIIIl) {
         boolean llIlIIIlIIIIlI = llIlIIIlIIIIII[llIlIIIlIIIIIl];
         AWTFontRenderer.CharLocation var8 = llIlIIIlIIIlIl.charLocations[llIlIIIlIIIIlI < llIlIIIlIIIlIl.charLocations.length ? llIlIIIlIIIIlI : 3];
         if (var8 != null) {
            char llIlIIIIlllllI = var8;
            llIlIIIlIIIIll += llIlIIIIlllllI.getWidth() - 8;
         } else {
            boolean var10001 = false;
         }
      }

      return llIlIIIlIIIIll / 2;
   }

   @NotNull
   public final Font getFont() {
      return llIlIIIIllllII.font;
   }

   public final int getHeight() {
      return (llIlIIlllIlIIl.fontHeight - 8) / 2;
   }

   // $FF: synthetic method
   public AWTFontRenderer(Font var1, int var2, int var3, int llIlIIIIlIIlIl, DefaultConstructorMarker var5) {
      if ((llIlIIIIlIIlIl & 2) != 0) {
         var2 = 0;
      }

      if ((llIlIIIIlIIlIl & 4) != 0) {
         var3 = 255;
      }

      this(var1, var2, var3);
   }

   public AWTFontRenderer(@NotNull Font llIlIIIIllIlIl, int llIlIIIIllIIII, int llIlIIIIlIllll) {
      Intrinsics.checkParameterIsNotNull(llIlIIIIllIlIl, "font");
      super();
      llIlIIIIllIllI.font = llIlIIIIllIlIl;
      llIlIIIIllIllI.fontHeight = -1;
      llIlIIIIllIllI.charLocations = new AWTFontRenderer.CharLocation[llIlIIIIlIllll];
      llIlIIIIllIllI.cachedStrings = new HashMap();
      llIlIIIIllIllI.renderBitmap(llIlIIIIllIIII, llIlIIIIlIllll);
      activeFontRenderers.add(llIlIIIIllIllI);
      boolean var10001 = false;
   }

   private final void drawChar(AWTFontRenderer.CharLocation llIlIIlIIlllII, float llIlIIlIIllIll, float llIlIIlIIllIlI) {
      float llIlIIlIIllllI = (float)llIlIIlIIlllII.getWidth();
      float llIlIIlIIlIlII = (float)llIlIIlIIlllII.getHeight();
      int llIlIIlIIlIIll = (float)llIlIIlIIlllII.getX();
      short llIlIIlIIlIIlI = (float)llIlIIlIIlllII.getY();
      float llIlIIlIlIIIlI = llIlIIlIIlIIll / (float)llIlIIlIIlllIl.textureWidth;
      float llIlIIlIlIIIll = llIlIIlIIlIIlI / (float)llIlIIlIIlllIl.textureHeight;
      float llIlIIlIlIIlII = llIlIIlIIllllI / (float)llIlIIlIIlllIl.textureWidth;
      Exception llIlIIlIIIlllI = llIlIIlIIlIlII / (float)llIlIIlIIlllIl.textureHeight;
      GL11.glTexCoord2f(llIlIIlIlIIIlI, llIlIIlIlIIIll);
      GL11.glVertex2f(llIlIIlIIllIll, llIlIIlIIllIlI);
      GL11.glTexCoord2f(llIlIIlIlIIIlI, llIlIIlIlIIIll + llIlIIlIIIlllI);
      GL11.glVertex2f(llIlIIlIIllIll, llIlIIlIIllIlI + llIlIIlIIlIlII);
      GL11.glTexCoord2f(llIlIIlIlIIIlI + llIlIIlIlIIlII, llIlIIlIlIIIll + llIlIIlIIIlllI);
      GL11.glVertex2f(llIlIIlIIllIll + llIlIIlIIllllI, llIlIIlIIllIlI + llIlIIlIIlIlII);
      GL11.glTexCoord2f(llIlIIlIlIIIlI + llIlIIlIlIIlII, llIlIIlIlIIIll);
      GL11.glVertex2f(llIlIIlIIllIll + llIlIIlIIllllI, llIlIIlIIllIlI);
   }

   public final void drawString(@NotNull String llIlIIllIIIIll, double llIlIIllIIIIlI, double llIlIIllIIIIIl, int llIlIIllIIIIII) {
      Intrinsics.checkParameterIsNotNull(llIlIIllIIIIll, "text");
      float llIlIIlIllllll = 0.5D;
      boolean llIlIIlIlllllI = (double)1 / llIlIIlIllllll;
      GlStateManager.pushMatrix();
      GlStateManager.scale(llIlIIlIllllll, llIlIIlIllllll, llIlIIlIllllll);
      GL11.glTranslated(llIlIIllIIIIlI * (double)2.0F, llIlIIllIIIIIl * 2.0D - 2.0D, 0.0D);
      GlStateManager.bindTexture(llIlIIllIIlIIl.textureID);
      Exception llIlIIlIllllIl = (float)(llIlIIllIIIIII >> 16 & 255) / 255.0F;
      float llIlIIllIIllIl = (float)(llIlIIllIIIIII >> 8 & 255) / 255.0F;
      float llIlIIllIIlllI = (float)(llIlIIllIIIIII & 255) / 255.0F;
      byte llIlIIlIlllIlI = (float)(llIlIIllIIIIII >> 24 & 255) / 255.0F;
      GlStateManager.color(llIlIIlIllllIl, llIlIIllIIllIl, llIlIIllIIlllI, llIlIIlIlllIlI);
      double llIlIIllIlIIII = 0.0D;
      char llIlIIlIlllIII = (CachedFont)llIlIIllIIlIIl.cachedStrings.get(llIlIIllIIIIll);
      if (llIlIIlIlllIII != null) {
         GL11.glCallList(llIlIIlIlllIII.getDisplayList());
         llIlIIlIlllIII.setLastUsage(System.currentTimeMillis());
         GlStateManager.popMatrix();
      } else {
         char llIlIIlIllIlll = -1;
         if (assumeNonVolatile) {
            llIlIIlIllIlll = GL11.glGenLists(1);
            GL11.glNewList(llIlIIlIllIlll, 4865);
         }

         GL11.glBegin(7);
         String llIlIIlIllIIlI = false;
         char[] var10000 = llIlIIllIIIIll.toCharArray();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
         short llIlIIlIllIlII = var10000;
         String llIlIIlIllIIll = llIlIIlIllIlII.length;

         boolean var10001;
         for(int llIlIIlIllIlIl = 0; llIlIIlIllIlIl < llIlIIlIllIIll; ++llIlIIlIllIlIl) {
            char llIlIIllIlIIll = llIlIIlIllIlII[llIlIIlIllIlIl];
            if (llIlIIllIlIIll >= llIlIIllIIlIIl.charLocations.length) {
               GL11.glEnd();
               GlStateManager.scale(llIlIIlIlllllI, llIlIIlIlllllI, llIlIIlIlllllI);
               Minecraft.getMinecraft().fontRendererObj.drawString(String.valueOf(llIlIIllIlIIll), (float)llIlIIllIlIIII * (float)llIlIIlIllllll + (float)1, 2.0F, llIlIIllIIIIII, false);
               var10001 = false;
               llIlIIllIlIIII += (double)Minecraft.getMinecraft().fontRendererObj.getStringWidth(String.valueOf(llIlIIllIlIIll)) * llIlIIlIlllllI;
               GlStateManager.scale(llIlIIlIllllll, llIlIIlIllllll, llIlIIlIllllll);
               GlStateManager.bindTexture(llIlIIllIIlIIl.textureID);
               GlStateManager.color(llIlIIlIllllIl, llIlIIllIIllIl, llIlIIllIIlllI, llIlIIlIlllIlI);
               GL11.glBegin(7);
            } else {
               AWTFontRenderer.CharLocation var24 = llIlIIllIIlIIl.charLocations[llIlIIllIlIIll];
               if (var24 != null) {
                  AWTFontRenderer.CharLocation llIlIIllIlIlII = var24;
                  llIlIIllIIlIIl.drawChar(llIlIIllIlIlII, (float)llIlIIllIlIIII, 0.0F);
                  llIlIIllIlIIII += (double)llIlIIllIlIlII.getWidth() - 8.0D;
               } else {
                  var10001 = false;
               }
            }
         }

         GL11.glEnd();
         if (assumeNonVolatile) {
            ((Map)llIlIIllIIlIIl.cachedStrings).put(llIlIIllIIIIll, new CachedFont(llIlIIlIllIlll, System.currentTimeMillis(), false, 4, (DefaultConstructorMarker)null));
            var10001 = false;
            GL11.glEndList();
         }

         GlStateManager.popMatrix();
      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\r\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\t\"\u0004\b\u000f\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\t\"\u0004\b\u0011\u0010\u000b¨\u0006\u001d"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$CharLocation;", "", "x", "", "y", "width", "height", "(IIII)V", "getHeight", "()I", "setHeight", "(I)V", "getWidth", "setWidth", "getX", "setX", "getY", "setY", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "LiquidSense"}
   )
   private static final class CharLocation {
      // $FF: synthetic field
      private int y;
      // $FF: synthetic field
      private int x;
      // $FF: synthetic field
      private int width;
      // $FF: synthetic field
      private int height;

      public final int component1() {
         return lIIlIllllllIll.x;
      }

      public final void setWidth(int lIIllIIIIlIlll) {
         lIIllIIIIlIllI.width = lIIllIIIIlIlll;
      }

      public boolean equals(@Nullable Object var1) {
         if (this != var1) {
            if (var1 instanceof AWTFontRenderer.CharLocation) {
               AWTFontRenderer.CharLocation var2 = (AWTFontRenderer.CharLocation)var1;
               if (this.x == var2.x && this.y == var2.y && this.width == var2.width && this.height == var2.height) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }

      public final int component4() {
         return lIIlIlllllIIlI.height;
      }

      public final int component3() {
         return lIIlIlllllIlIl.width;
      }

      public CharLocation(int lIIllIIIIIIlIl, int lIIlIlllllllll, int lIIlIllllllllI, int lIIllIIIIIIIlI) {
         lIIllIIIIIIllI.x = lIIllIIIIIIlIl;
         lIIllIIIIIIllI.y = lIIlIlllllllll;
         lIIllIIIIIIllI.width = lIIlIllllllllI;
         lIIllIIIIIIllI.height = lIIllIIIIIIIlI;
      }

      public final void setY(int lIIllIIIlIIIII) {
         lIIllIIIIlllll.y = lIIllIIIlIIIII;
      }

      public final int getWidth() {
         return lIIllIIIIlllII.width;
      }

      @NotNull
      public final AWTFontRenderer.CharLocation copy(int lIIlIllllIlIll, int lIIlIllllIIllI, int lIIlIllllIlIIl, int lIIlIllllIlIII) {
         return new AWTFontRenderer.CharLocation(lIIlIllllIlIll, lIIlIllllIIllI, lIIlIllllIlIIl, lIIlIllllIlIII);
      }

      @NotNull
      public String toString() {
         return String.valueOf((new StringBuilder()).append("CharLocation(x=").append(this.x).append(", y=").append(this.y).append(", width=").append(this.width).append(", height=").append(this.height).append(")"));
      }

      // $FF: synthetic method
      public static AWTFontRenderer.CharLocation copy$default(AWTFontRenderer.CharLocation lIIlIlllIlllIl, int var1, int var2, int var3, int var4, int lIIlIlllIllIII, Object var6) {
         if ((lIIlIlllIllIII & 1) != 0) {
            var1 = lIIlIlllIlllIl.x;
         }

         if ((lIIlIlllIllIII & 2) != 0) {
            var2 = lIIlIlllIlllIl.y;
         }

         if ((lIIlIlllIllIII & 4) != 0) {
            var3 = lIIlIlllIlllIl.width;
         }

         if ((lIIlIlllIllIII & 8) != 0) {
            var4 = lIIlIlllIlllIl.height;
         }

         return lIIlIlllIlllIl.copy(var1, var2, var3, var4);
      }

      public int hashCode() {
         return ((Integer.hashCode(this.x) * 31 + Integer.hashCode(this.y)) * 31 + Integer.hashCode(this.width)) * 31 + Integer.hashCode(this.height);
      }

      public final int getY() {
         return lIIllIIIlIIlIl.y;
      }

      public final int component2() {
         return lIIlIllllllIII.y;
      }

      public final void setHeight(int lIIllIIIIIlllI) {
         lIIllIIIIIllIl.height = lIIllIIIIIlllI;
      }

      public final int getHeight() {
         return lIIllIIIIlIIlI.height;
      }

      public final int getX() {
         return lIIllIIIlIlllI.x;
      }

      public final void setX(int lIIllIIIlIlIIl) {
         lIIllIIIlIlIlI.x = lIIllIIIlIlIIl;
      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0013\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0007j\b\u0012\u0004\u0012\u00020\b`\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer$Companion;", "", "()V", "CACHED_FONT_REMOVAL_TIME", "", "GC_TICKS", "activeFontRenderers", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/ui/font/AWTFontRenderer;", "Lkotlin/collections/ArrayList;", "getActiveFontRenderers", "()Ljava/util/ArrayList;", "assumeNonVolatile", "", "getAssumeNonVolatile", "()Z", "setAssumeNonVolatile", "(Z)V", "gcTicks", "garbageCollectionTick", "", "LiquidSense"}
   )
   public static final class Companion {
      @NotNull
      public final ArrayList<AWTFontRenderer> getActiveFontRenderers() {
         return AWTFontRenderer.activeFontRenderers;
      }

      public final boolean getAssumeNonVolatile() {
         return AWTFontRenderer.assumeNonVolatile;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker llIlIlIlIIllIII) {
         this();
      }

      public final void setAssumeNonVolatile(boolean llIlIlIlIlllIIl) {
         AWTFontRenderer.assumeNonVolatile = llIlIlIlIlllIIl;
      }

      private Companion() {
      }

      public final void garbageCollectionTick() {
         int llIlIlIlIlIIIll;
         AWTFontRenderer.gcTicks = (llIlIlIlIlIIIll = AWTFontRenderer.gcTicks) + 1;
         if (llIlIlIlIlIIIll > 600) {
            Iterable llIlIlIlIlIlIIl = (Iterable)((AWTFontRenderer.Companion)llIlIlIlIlIIlII).getActiveFontRenderers();
            int llIlIlIlIlIlIII = false;
            Iterator llIlIlIlIlIIIIl = llIlIlIlIlIlIIl.iterator();

            while(llIlIlIlIlIIIIl.hasNext()) {
               Object llIlIlIlIlIlIlI = llIlIlIlIlIIIIl.next();
               float llIlIlIlIIlllll = (AWTFontRenderer)llIlIlIlIlIlIlI;
               int llIlIlIlIlIllII = false;
               llIlIlIlIIlllll.collectGarbage();
            }

            AWTFontRenderer.gcTicks = 0;
         }

      }
   }
}
