package net.ccbluex.liquidbounce.script.remapper.injection.transformers;

import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class AbstractJavaLinkerTransformer implements IClassTransformer {
   public byte[] transform(String lIIIl, String lIIII, byte[] llIl) {
      if (lIIIl.equals("jdk.internal.dynalink.beans.AbstractJavaLinker")) {
         try {
            long llII = ClassUtils.INSTANCE.toClassNode(llIl);
            llII.methods.forEach((llI) -> {
               float lIl = String.valueOf((new StringBuilder()).append(llI.name).append(llI.desc));
               boolean lII = -1;
               switch(lIl.hashCode()) {
               case -2098129779:
                  if (lIl.equals("addMember(Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;Ljava/util/Map;)V")) {
                     lII = 0;
                  }
                  break;
               case -218897209:
                  if (lIl.equals("setPropertyGetter(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljdk/internal/dynalink/beans/GuardedInvocationComponent$ValidationType;)V")) {
                     lII = 2;
                  }
                  break;
               case 1744451451:
                  if (lIl.equals("addMember(Ljava/lang/String;Ljdk/internal/dynalink/beans/SingleDynamicMethod;Ljava/util/Map;)V")) {
                     lII = 1;
                  }
               }

               switch(lII) {
               case 0:
                  llI.instructions.insertBefore(llI.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", "Ljava/lang/Class;"), new VarInsnNode(25, 1), new VarInsnNode(25, 2), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/reflect/AccessibleObject;)Ljava/lang/String;", false), new VarInsnNode(58, 1)));
                  break;
               case 1:
                  llI.instructions.insertBefore(llI.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", "Ljava/lang/Class;"), new VarInsnNode(25, 1), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "addMember", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)));
                  break;
               case 2:
                  llI.instructions.insertBefore(llI.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new VarInsnNode(25, 0), new FieldInsnNode(180, "jdk/internal/dynalink/beans/AbstractJavaLinker", "clazz", "Ljava/lang/Class;"), new VarInsnNode(25, 1), new MethodInsnNode(184, "net/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler", "setPropertyGetter", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/String;", false), new VarInsnNode(58, 1)));
               }

            });
            return ClassUtils.INSTANCE.toBytes(llII);
         } catch (Throwable var6) {
            var6.printStackTrace();
         }
      }

      return llIl;
   }
}
