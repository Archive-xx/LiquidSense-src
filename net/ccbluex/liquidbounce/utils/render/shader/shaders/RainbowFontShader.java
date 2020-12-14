package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import java.io.Closeable;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL20;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000 \u001a2\u00020\u00012\u00020\u0002:\u0001\u001aB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0015H\u0016J\b\u0010\u0018\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\u0015H\u0016R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000b\"\u0004\b\u0010\u0010\rR\u001a\u0010\u0011\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\r¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowFontShader;", "Lnet/ccbluex/liquidbounce/utils/render/shader/Shader;", "Ljava/io/Closeable;", "()V", "<set-?>", "", "isInUse", "()Z", "offset", "", "getOffset", "()F", "setOffset", "(F)V", "strengthX", "getStrengthX", "setStrengthX", "strengthY", "getStrengthY", "setStrengthY", "close", "", "setupUniforms", "startShader", "stopShader", "updateUniforms", "Companion", "LiquidSense"}
)
public final class RainbowFontShader extends Shader implements Closeable {
   // $FF: synthetic field
   private float strengthY;
   // $FF: synthetic field
   private float strengthX;
   // $FF: synthetic field
   @JvmField
   @NotNull
   public static final RainbowFontShader INSTANCE = new RainbowFontShader();
   // $FF: synthetic field
   private float offset;
   // $FF: synthetic field
   private boolean isInUse;
   // $FF: synthetic field
   public static final RainbowFontShader.Companion Companion = new RainbowFontShader.Companion((DefaultConstructorMarker)null);

   public final void setStrengthX(float lIIlIlIIlIlllI) {
      lIIlIlIIlIllll.strengthX = lIIlIlIIlIlllI;
   }

   public RainbowFontShader() {
      super("rainbow_font_shader.frag");
   }

   public final void setStrengthY(float lIIlIlIIlIIlll) {
      lIIlIlIIlIlIII.strengthY = lIIlIlIIlIIlll;
   }

   public void startShader() {
      super.startShader();
      lIIlIlIIIlIIll.isInUse = true;
   }

   public final float getStrengthY() {
      return lIIlIlIIlIllII.strengthY;
   }

   public void updateUniforms() {
      GL20.glUniform2f(lIIlIlIIIlIllI.getUniform("strength"), lIIlIlIIIlIllI.strengthX, lIIlIlIIIlIllI.strengthY);
      GL20.glUniform1f(lIIlIlIIIlIllI.getUniform("offset"), lIIlIlIIIlIllI.offset);
   }

   public final float getStrengthX() {
      return lIIlIlIIllIlIl.strengthX;
   }

   public final boolean isInUse() {
      return lIIlIlIIlllIII.isInUse;
   }

   public final float getOffset() {
      return lIIlIlIIlIIIll.offset;
   }

   public void stopShader() {
      super.stopShader();
      lIIlIlIIIlIIII.isInUse = false;
   }

   public void close() {
      if (lIIlIlIIIIllIl.isInUse) {
         lIIlIlIIIIllIl.stopShader();
      }

   }

   public void setupUniforms() {
      lIIlIlIIIllIIl.setupUniform("offset");
      lIIlIlIIIllIIl.setupUniform("strength");
   }

   public final void setOffset(float lIIlIlIIIllllI) {
      lIIlIlIIIlllll.offset = lIIlIlIIIllllI;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0086\bR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowFontShader$Companion;", "", "()V", "INSTANCE", "Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowFontShader;", "begin", "enable", "", "x", "", "y", "offset", "LiquidSense"}
   )
   public static final class Companion {
      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lllllllllllllllllllllIlIIIllIIlI) {
         this();
      }

      @NotNull
      public final RainbowFontShader begin(boolean lllllllllllllllllllllIlIIIllllIl, float lllllllllllllllllllllIlIIlIIIIIl, float lllllllllllllllllllllIlIIIlllIll, float lllllllllllllllllllllIlIIIllllll) {
         int lllllllllllllllllllllIlIIIlllllI = false;
         RainbowFontShader lllllllllllllllllllllIlIIlIIIlII = RainbowFontShader.INSTANCE;
         if (lllllllllllllllllllllIlIIIllllIl) {
            lllllllllllllllllllllIlIIlIIIlII.setStrengthX(lllllllllllllllllllllIlIIlIIIIIl);
            lllllllllllllllllllllIlIIlIIIlII.setStrengthY(lllllllllllllllllllllIlIIIlllIll);
            lllllllllllllllllllllIlIIlIIIlII.setOffset(lllllllllllllllllllllIlIIIllllll);
            lllllllllllllllllllllIlIIlIIIlII.startShader();
         }

         return lllllllllllllllllllllIlIIlIIIlII;
      }

      private Companion() {
      }
   }
}
