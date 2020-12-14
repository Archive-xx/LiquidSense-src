package net.ccbluex.liquidbounce.script.remapper.injection.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/utils/NodeUtils;", "", "()V", "toNodes", "Lorg/objectweb/asm/tree/InsnList;", "nodes", "", "Lorg/objectweb/asm/tree/AbstractInsnNode;", "([Lorg/objectweb/asm/tree/AbstractInsnNode;)Lorg/objectweb/asm/tree/InsnList;", "LiquidSense"}
)
public final class NodeUtils {
   // $FF: synthetic field
   public static final NodeUtils INSTANCE;

   private NodeUtils() {
   }

   @NotNull
   public final InsnList toNodes(@NotNull AbstractInsnNode... lIIllIIlIlllI) {
      Intrinsics.checkParameterIsNotNull(lIIllIIlIlllI, "nodes");
      InsnList lIIllIIllIIII = new InsnList();
      double lIIllIIlIlIIl = lIIllIIlIlllI;
      byte lIIllIIlIlIII = lIIllIIlIlllI.length;

      for(int lIIllIIlIlIlI = 0; lIIllIIlIlIlI < lIIllIIlIlIII; ++lIIllIIlIlIlI) {
         Exception lIIllIIlIlIll = lIIllIIlIlIIl[lIIllIIlIlIlI];
         lIIllIIllIIII.add(lIIllIIlIlIll);
      }

      return lIIllIIllIIII;
   }

   static {
      NodeUtils lIIllIIlIIIll = new NodeUtils();
      INSTANCE = lIIllIIlIIIll;
   }
}
