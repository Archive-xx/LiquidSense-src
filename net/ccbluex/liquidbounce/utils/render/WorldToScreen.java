package net.ccbluex.liquidbounce.utils.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class WorldToScreen {
   public static Vector2f worldToScreen(Vector3f lIlllIllIIIIlI, Matrix4f lIlllIllIIIIIl, Matrix4f lIlllIllIIlIIl, int lIlllIllIIlIII, int lIlllIlIlllllI) {
      Vector4f lIlllIllIIIllI = multiply(multiply(new Vector4f(lIlllIllIIIIlI.x, lIlllIllIIIIlI.y, lIlllIllIIIIlI.z, 1.0F), lIlllIllIIIIIl), lIlllIllIIlIIl);
      Vector3f lIlllIllIIIlIl = new Vector3f(lIlllIllIIIllI.x / lIlllIllIIIllI.w, lIlllIllIIIllI.y / lIlllIllIIIllI.w, lIlllIllIIIllI.z / lIlllIllIIIllI.w);
      float lIlllIllIIIlII = (lIlllIllIIIlIl.x + 1.0F) / 2.0F * (float)lIlllIllIIlIII;
      float lIlllIlIlllIlI = (1.0F - lIlllIllIIIlIl.y) / 2.0F * (float)lIlllIlIlllllI;
      return !((double)lIlllIllIIIlIl.z < -1.0D) && !((double)lIlllIllIIIlIl.z > 1.0D) ? new Vector2f(lIlllIllIIIlII, lIlllIlIlllIlI) : null;
   }

   public static Vector4f multiply(Vector4f lIlllIlIllIlIl, Matrix4f lIlllIlIllIllI) {
      return new Vector4f(lIlllIlIllIlIl.x * lIlllIlIllIllI.m00 + lIlllIlIllIlIl.y * lIlllIlIllIllI.m10 + lIlllIlIllIlIl.z * lIlllIlIllIllI.m20 + lIlllIlIllIlIl.w * lIlllIlIllIllI.m30, lIlllIlIllIlIl.x * lIlllIlIllIllI.m01 + lIlllIlIllIlIl.y * lIlllIlIllIllI.m11 + lIlllIlIllIlIl.z * lIlllIlIllIllI.m21 + lIlllIlIllIlIl.w * lIlllIlIllIllI.m31, lIlllIlIllIlIl.x * lIlllIlIllIllI.m02 + lIlllIlIllIlIl.y * lIlllIlIllIllI.m12 + lIlllIlIllIlIl.z * lIlllIlIllIllI.m22 + lIlllIlIllIlIl.w * lIlllIlIllIllI.m32, lIlllIlIllIlIl.x * lIlllIlIllIllI.m03 + lIlllIlIllIlIl.y * lIlllIlIllIllI.m13 + lIlllIlIllIlIl.z * lIlllIlIllIllI.m23 + lIlllIlIllIlIl.w * lIlllIlIllIllI.m33);
   }

   public static Matrix4f getMatrix(int lIlllIlllIIIIl) {
      double lIlllIllIllllI = BufferUtils.createFloatBuffer(16);
      GL11.glGetFloat(lIlllIlllIIIIl, lIlllIllIllllI);
      return (Matrix4f)(new Matrix4f()).load(lIlllIllIllllI);
   }

   public static Vector2f worldToScreen(Vector3f lIlllIllIllIlI, int lIlllIllIllIIl, int lIlllIllIllIII) {
      return worldToScreen(lIlllIllIllIlI, getMatrix(2982), getMatrix(2983), lIlllIllIllIIl, lIlllIllIllIII);
   }
}
