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
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader;", "Lnet/ccbluex/liquidbounce/utils/render/shader/Shader;", "Ljava/io/Closeable;", "()V", "<set-?>", "", "isInUse", "()Z", "offset", "", "getOffset", "()F", "setOffset", "(F)V", "strengthX", "getStrengthX", "setStrengthX", "strengthY", "getStrengthY", "setStrengthY", "close", "", "setupUniforms", "startShader", "stopShader", "updateUniforms", "Companion", "LiquidSense"}
)
public final class RainbowShader extends Shader implements Closeable {
   // $FF: synthetic field
   private float offset;
   // $FF: synthetic field
   @JvmField
   @NotNull
   public static final RainbowShader INSTANCE = new RainbowShader();
   // $FF: synthetic field
   private float strengthY;
   // $FF: synthetic field
   public static final RainbowShader.Companion Companion = new RainbowShader.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   private float strengthX;
   // $FF: synthetic field
   private boolean isInUse;

   public final void setStrengthY(float lllllllllllllllllIllIllllIIlllII) {
      lllllllllllllllllIllIllllIIlllIl.strengthY = lllllllllllllllllIllIllllIIlllII;
   }

   public final float getStrengthY() {
      return lllllllllllllllllIllIllllIlIIllI.strengthY;
   }

   public void updateUniforms() {
      GL20.glUniform2f(lllllllllllllllllIllIlllIlllllII.getUniform("strength"), lllllllllllllllllIllIlllIlllllII.strengthX, lllllllllllllllllIllIlllIlllllII.strengthY);
      GL20.glUniform1f(lllllllllllllllllIllIlllIlllllII.getUniform("offset"), lllllllllllllllllIllIlllIlllllII.offset);
   }

   public void close() {
      if (lllllllllllllllllIllIlllIllIllII.isInUse) {
         lllllllllllllllllIllIlllIllIllII.stopShader();
      }

   }

   public void setupUniforms() {
      lllllllllllllllllIllIlllIllllllI.setupUniform("offset");
      lllllllllllllllllIllIlllIllllllI.setupUniform("strength");
   }

   public final void setStrengthX(float lllllllllllllllllIllIllllIlIlIII) {
      lllllllllllllllllIllIllllIlIlIll.strengthX = lllllllllllllllllIllIllllIlIlIII;
   }

   public void stopShader() {
      super.stopShader();
      lllllllllllllllllIllIlllIlllIIll.isInUse = false;
   }

   public final void setOffset(float lllllllllllllllllIllIllllIIIIllI) {
      lllllllllllllllllIllIllllIIIIlII.offset = lllllllllllllllllIllIllllIIIIllI;
   }

   public void startShader() {
      super.startShader();
      lllllllllllllllllIllIlllIllllIII.isInUse = true;
   }

   public final boolean isInUse() {
      return lllllllllllllllllIllIllllIllIIlI.isInUse;
   }

   public final float getOffset() {
      return lllllllllllllllllIllIllllIIlIIll.offset;
   }

   public RainbowShader() {
      super("rainbow_shader.frag");
   }

   public final float getStrengthX() {
      return lllllllllllllllllIllIllllIlIllll.strengthX;
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0086\bR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader$Companion;", "", "()V", "INSTANCE", "Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RainbowShader;", "begin", "enable", "", "x", "", "y", "offset", "LiquidSense"}
   )
   public static final class Companion {
      private Companion() {
      }

      @NotNull
      public final RainbowShader begin(boolean lIllllllIllIlI, float lIllllllIlIlII, float lIllllllIlIIll, float lIllllllIlIIlI) {
         int lIllllllIlIllI = false;
         long lIllllllIlIIII = RainbowShader.INSTANCE;
         if (lIllllllIllIlI) {
            lIllllllIlIIII.setStrengthX(lIllllllIlIlII);
            lIllllllIlIIII.setStrengthY(lIllllllIlIIll);
            lIllllllIlIIII.setOffset(lIllllllIlIIlI);
            lIllllllIlIIII.startShader();
         }

         return lIllllllIlIIII;
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lIllllllIIlIlI) {
         this();
      }
   }
}
