package net.ccbluex.liquidbounce.script.remapper.injection.transformers.handlers;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.script.remapper.Remapper;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Type;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u001c\u0010\n\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007¨\u0006\u000b"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/injection/transformers/handlers/AbstractJavaLinkerHandler;", "", "()V", "addMember", "", "clazz", "Ljava/lang/Class;", "name", "accessibleObject", "Ljava/lang/reflect/AccessibleObject;", "setPropertyGetter", "LiquidSense"}
)
public final class AbstractJavaLinkerHandler {
   // $FF: synthetic field
   public static final AbstractJavaLinkerHandler INSTANCE;

   static {
      AbstractJavaLinkerHandler lllIlIllllIIIIl = new AbstractJavaLinkerHandler();
      INSTANCE = lllIlIllllIIIIl;
   }

   @JvmStatic
   @NotNull
   public static final String setPropertyGetter(@NotNull Class<?> lllIlIllllIlIll, @NotNull String lllIlIllllIlIII) {
      Intrinsics.checkParameterIsNotNull(lllIlIllllIlIll, "clazz");
      Intrinsics.checkParameterIsNotNull(lllIlIllllIlIII, "name");

      Class var10000;
      for(Class lllIlIllllIIlll = lllIlIllllIlIll; Intrinsics.areEqual((Object)lllIlIllllIIlll.getName(), (Object)"java.lang.Object") ^ true; lllIlIllllIIlll = var10000) {
         String lllIlIllllIllIl = Remapper.INSTANCE.remapField(lllIlIllllIIlll, lllIlIllllIlIII);
         if (Intrinsics.areEqual((Object)lllIlIllllIllIl, (Object)lllIlIllllIlIII) ^ true) {
            return lllIlIllllIllIl;
         }

         if (lllIlIllllIIlll.getSuperclass() == null) {
            break;
         }

         var10000 = lllIlIllllIIlll.getSuperclass();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "currentClass.superclass");
      }

      return lllIlIllllIlIII;
   }

   @JvmStatic
   @NotNull
   public static final String addMember(@NotNull Class<?> lllIllIIIIIIIlI, @NotNull String lllIllIIIIIIlII, @NotNull AccessibleObject lllIllIIIIIIIll) {
      Intrinsics.checkParameterIsNotNull(lllIllIIIIIIIlI, "clazz");
      Intrinsics.checkParameterIsNotNull(lllIllIIIIIIlII, "name");
      Intrinsics.checkParameterIsNotNull(lllIllIIIIIIIll, "accessibleObject");
      if (!(lllIllIIIIIIIll instanceof Method)) {
         return lllIllIIIIIIlII;
      } else {
         Class var5;
         for(Class lllIllIIIIIIllI = lllIllIIIIIIIlI; Intrinsics.areEqual((Object)lllIllIIIIIIllI.getName(), (Object)"java.lang.Object") ^ true; lllIllIIIIIIllI = var5) {
            Remapper var10000 = Remapper.INSTANCE;
            String var10003 = Type.getMethodDescriptor((Method)lllIllIIIIIIIll);
            Intrinsics.checkExpressionValueIsNotNull(var10003, "Type.getMethodDescriptor(accessibleObject)");
            String lllIllIIIIIIlll = var10000.remapMethod(lllIllIIIIIIllI, lllIllIIIIIIlII, var10003);
            if (Intrinsics.areEqual((Object)lllIllIIIIIIlll, (Object)lllIllIIIIIIlII) ^ true) {
               return lllIllIIIIIIlll;
            }

            if (lllIllIIIIIIllI.getSuperclass() == null) {
               break;
            }

            var5 = lllIllIIIIIIllI.getSuperclass();
            Intrinsics.checkExpressionValueIsNotNull(var5, "currentClass.superclass");
         }

         return lllIllIIIIIIlII;
      }
   }

   private AbstractJavaLinkerHandler() {
   }

   @JvmStatic
   @NotNull
   public static final String addMember(@NotNull Class<?> lllIlIlllllIlIl, @NotNull String lllIlIlllllIllI) {
      Intrinsics.checkParameterIsNotNull(lllIlIlllllIlIl, "clazz");
      Intrinsics.checkParameterIsNotNull(lllIlIlllllIllI, "name");

      Class var10000;
      for(Class lllIlIlllllIIll = lllIlIlllllIlIl; Intrinsics.areEqual((Object)lllIlIlllllIIll.getName(), (Object)"java.lang.Object") ^ true; lllIlIlllllIIll = var10000) {
         double lllIlIlllllIIlI = Remapper.INSTANCE.remapField(lllIlIlllllIIll, lllIlIlllllIllI);
         if (Intrinsics.areEqual((Object)lllIlIlllllIIlI, (Object)lllIlIlllllIllI) ^ true) {
            return lllIlIlllllIIlI;
         }

         if (lllIlIlllllIIll.getSuperclass() == null) {
            break;
         }

         var10000 = lllIlIlllllIIll.getSuperclass();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "currentClass.superclass");
      }

      return lllIlIlllllIllI;
   }
}
