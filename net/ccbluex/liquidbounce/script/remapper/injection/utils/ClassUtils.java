package net.ccbluex.liquidbounce.script.remapper.injection.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/utils/ClassUtils;", "", "()V", "toBytes", "", "classNode", "Lorg/objectweb/asm/tree/ClassNode;", "toClassNode", "bytes", "LiquidSense"}
)
public final class ClassUtils {
   // $FF: synthetic field
   public static final ClassUtils INSTANCE;

   private ClassUtils() {
   }

   static {
      ClassUtils lIIlIlllIIIIllI = new ClassUtils();
      INSTANCE = lIIlIlllIIIIllI;
   }

   @NotNull
   public final byte[] toBytes(@NotNull ClassNode lIIlIlllIIllIII) {
      Intrinsics.checkParameterIsNotNull(lIIlIlllIIllIII, "classNode");
      ClassWriter lIIlIlllIIllIll = new ClassWriter(1);
      lIIlIlllIIllIII.accept((ClassVisitor)lIIlIlllIIllIll);
      byte[] var10000 = lIIlIlllIIllIll.toByteArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "classWriter.toByteArray()");
      return var10000;
   }

   @NotNull
   public final ClassNode toClassNode(@NotNull byte[] lIIlIlllIllIIlI) {
      Intrinsics.checkParameterIsNotNull(lIIlIlllIllIIlI, "bytes");
      ClassReader lIIlIlllIllIllI = new ClassReader(lIIlIlllIllIIlI);
      ClassNode lIIlIlllIlllIII = new ClassNode();
      lIIlIlllIllIllI.accept((ClassVisitor)lIIlIlllIlllIII, 0);
      return lIIlIlllIlllIII;
   }
}
