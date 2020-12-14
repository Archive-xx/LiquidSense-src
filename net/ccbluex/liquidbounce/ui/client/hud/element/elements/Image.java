//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import com.google.gson.JsonElement;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.TextValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u000fJ\u0010\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "()V", "height", "", "image", "Lnet/ccbluex/liquidbounce/value/TextValue;", "resourceLocation", "Lnet/minecraft/util/ResourceLocation;", "width", "createElement", "", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "setImage", "Ljava/io/File;", "", "Companion", "LiquidSense"}
)
@ElementInfo(
   name = "Image"
)
public final class Image extends Element {
   // $FF: synthetic field
   private int width;
   // $FF: synthetic field
   private final TextValue image;
   // $FF: synthetic field
   public static final Image.Companion Companion = new Image.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private int height;
   // $FF: synthetic field
   private final ResourceLocation resourceLocation;

   public boolean createElement() {
      File var10000 = MiscUtils.openFileChooser();
      boolean var10001;
      if (var10000 != null) {
         float lllIllIIllIlIII = var10000;
         if (!lllIllIIllIlIII.exists()) {
            MiscUtils.showErrorPopup("Error", "The file does not exist.");
            return false;
         } else if (lllIllIIllIlIII.isDirectory()) {
            MiscUtils.showErrorPopup("Error", "The file is a directory.");
            return false;
         } else {
            lllIllIIllIlIIl.setImage(lllIllIIllIlIII);
            var10001 = false;
            return true;
         }
      } else {
         var10001 = false;
         return false;
      }
   }

   private final Image setImage(String lllIllIIlIlllIl) {
      try {
         lllIllIIlIllllI.image.changeValue(lllIllIIlIlllIl);
         Exception lllIllIIlIlllII = new ByteArrayInputStream(Base64.getDecoder().decode(lllIllIIlIlllIl));
         BufferedImage lllIllIIllIIIll = ImageIO.read((InputStream)lllIllIIlIlllII);
         lllIllIIlIlllII.close();
         Intrinsics.checkExpressionValueIsNotNull(lllIllIIllIIIll, "bufferedImage");
         lllIllIIlIllllI.width = lllIllIIllIIIll.getWidth();
         lllIllIIlIllllI.height = lllIllIIllIIIll.getHeight();
         Minecraft var10000 = access$getMc$p$s1046033730();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
         var10000.getTextureManager().loadTexture(lllIllIIlIllllI.resourceLocation, (ITextureObject)(new DynamicTexture(lllIllIIllIIIll)));
         boolean var10001 = false;
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return lllIllIIlIllllI;
   }

   public Image() {
      super(0.0D, 0.0D, 0.0F, (Side)null, 15, (DefaultConstructorMarker)null);
      lllIllIIlIlIIII.image = (TextValue)(new TextValue("Image", "") {
         public void fromJson(@NotNull JsonElement lllllllllllllllllllIlIlIIIllIIlI) {
            Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIlIIIllIIlI, "element");
            super.fromJson(lllllllllllllllllllIlIlIIIllIIlI);
            int lllllllllllllllllllIlIlIIIllIIIl = (CharSequence)lllllllllllllllllllIlIlIIIllIIll.get();
            Exception lllllllllllllllllllIlIlIIIllIIII = false;
            if (lllllllllllllllllllIlIlIIIllIIIl.length() != 0) {
               lllIllIIlIlIIII.setImage((String)lllllllllllllllllllIlIlIIIllIIll.get());
               boolean var10001 = false;
            }
         }

         protected void onChanged(@NotNull String lllllllllllllllllllIlIlIIIlIIllI, @NotNull String lllllllllllllllllllIlIlIIIlIIlIl) {
            Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIlIIIlIIllI, "oldValue");
            Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIlIIIlIIlIl, "newValue");
            byte lllllllllllllllllllIlIlIIIlIIlII = (CharSequence)lllllllllllllllllllIlIlIIIlIlIlI.get();
            double lllllllllllllllllllIlIlIIIlIIIll = false;
            if (lllllllllllllllllllIlIlIIIlIIlII.length() != 0) {
               lllIllIIlIlIIII.setImage((String)lllllllllllllllllllIlIlIIIlIlIlI.get());
               boolean var10001 = false;
            }
         }
      });
      lllIllIIlIlIIII.resourceLocation = new ResourceLocation(RandomUtils.randomNumber(128));
      lllIllIIlIlIIII.width = 64;
      lllIllIIlIlIIII.height = 64;
   }

   @NotNull
   public final Image setImage(@NotNull File lllIllIIlIlIlIl) {
      Intrinsics.checkParameterIsNotNull(lllIllIIlIlIlIl, "image");

      try {
         String var10001 = Base64.getEncoder().encodeToString(Files.readAllBytes(lllIllIIlIlIlIl.toPath()));
         Intrinsics.checkExpressionValueIsNotNull(var10001, "Base64.getEncoder().enco…AllBytes(image.toPath()))");
         lllIllIIlIlIllI.setImage(var10001);
         boolean var5 = false;
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return lllIllIIlIlIllI;
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   @NotNull
   public Border drawElement() {
      RenderUtils.drawImage(lllIllIIllIllll.resourceLocation, 0, 0, lllIllIIllIllll.width / 2, lllIllIIllIllll.height / 2);
      return new Border(0.0F, 0.0F, (float)lllIllIIllIllll.width / 2.0F, (float)lllIllIIllIllll.height / 2.0F);
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"},
      d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image$Companion;", "", "()V", "default", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Image;", "LiquidSense"}
   )
   public static final class Companion {
      @NotNull
      public final Image default() {
         double llIlIlIIllIIIlI = new Image();
         llIlIlIIllIIIlI.setX(0.0D);
         llIlIlIIllIIIlI.setY(0.0D);
         return llIlIlIIllIIIlI;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker llIlIlIIlIllIll) {
         this();
      }
   }
}
