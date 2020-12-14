package kotlin.jvm;

import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u0018\u001a\u00020\u0019\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\r*\u0006\u0012\u0002\b\u00030\u001a¢\u0006\u0002\u0010\u001b\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"-\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G¢\u0006\f\u0012\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"&\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\u0002H\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000e\";\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018Ç\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u000f\u0010\t\u001a\u0004\b\u0010\u0010\u000b\"+\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b\"-\u0010\u0013\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000b\"+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006\u001c"},
   d2 = {"annotationClass", "Lkotlin/reflect/KClass;", "T", "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "java", "Ljava/lang/Class;", "java$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "javaClass$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", "kotlin", "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", "kotlin-stdlib"}
)
@JvmName(
   name = "JvmClassMappingKt"
)
public final class JvmClassMappingKt {
   /** @deprecated */
   // $FF: synthetic method
   public static void java$annotations(KClass var0) {
   }

   @JvmName(
      name = "getJavaClass"
   )
   @NotNull
   public static final <T> Class<T> getJavaClass(@NotNull KClass<T> $this$java) {
      Intrinsics.checkParameterIsNotNull($this$java, "$this$java");
      return ((ClassBasedDeclarationContainer)$this$java).getJClass();
   }

   @Nullable
   public static final <T> Class<T> getJavaPrimitiveType(@NotNull KClass<T> $this$javaPrimitiveType) {
      Intrinsics.checkParameterIsNotNull($this$javaPrimitiveType, "$this$javaPrimitiveType");
      Class thisJClass = ((ClassBasedDeclarationContainer)$this$javaPrimitiveType).getJClass();
      if (thisJClass.isPrimitive()) {
         if (thisJClass == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<T>");
         } else {
            return thisJClass;
         }
      } else {
         String var10000 = thisJClass.getName();
         Class var3;
         if (var10000 != null) {
            String var2 = var10000;
            switch(var2.hashCode()) {
            case -2056817302:
               if (var2.equals("java.lang.Integer")) {
                  var3 = Integer.TYPE;
                  return var3;
               }
               break;
            case -527879800:
               if (var2.equals("java.lang.Float")) {
                  var3 = Float.TYPE;
                  return var3;
               }
               break;
            case -515992664:
               if (var2.equals("java.lang.Short")) {
                  var3 = Short.TYPE;
                  return var3;
               }
               break;
            case 155276373:
               if (var2.equals("java.lang.Character")) {
                  var3 = Character.TYPE;
                  return var3;
               }
               break;
            case 344809556:
               if (var2.equals("java.lang.Boolean")) {
                  var3 = Boolean.TYPE;
                  return var3;
               }
               break;
            case 398507100:
               if (var2.equals("java.lang.Byte")) {
                  var3 = Byte.TYPE;
                  return var3;
               }
               break;
            case 398795216:
               if (var2.equals("java.lang.Long")) {
                  var3 = Long.TYPE;
                  return var3;
               }
               break;
            case 399092968:
               if (var2.equals("java.lang.Void")) {
                  var3 = Void.TYPE;
                  return var3;
               }
               break;
            case 761287205:
               if (var2.equals("java.lang.Double")) {
                  var3 = Double.TYPE;
                  return var3;
               }
            }
         }

         var3 = null;
         return var3;
      }
   }

   @NotNull
   public static final <T> Class<T> getJavaObjectType(@NotNull KClass<T> $this$javaObjectType) {
      Intrinsics.checkParameterIsNotNull($this$javaObjectType, "$this$javaObjectType");
      Class thisJClass = ((ClassBasedDeclarationContainer)$this$javaObjectType).getJClass();
      if (!thisJClass.isPrimitive()) {
         return thisJClass;
      } else {
         String var10000 = thisJClass.getName();
         Class var3;
         if (var10000 != null) {
            String var2 = var10000;
            switch(var2.hashCode()) {
            case -1325958191:
               if (var2.equals("double")) {
                  var3 = Double.class;
                  return var3;
               }
               break;
            case 104431:
               if (var2.equals("int")) {
                  var3 = Integer.class;
                  return var3;
               }
               break;
            case 3039496:
               if (var2.equals("byte")) {
                  var3 = Byte.class;
                  return var3;
               }
               break;
            case 3052374:
               if (var2.equals("char")) {
                  var3 = Character.class;
                  return var3;
               }
               break;
            case 3327612:
               if (var2.equals("long")) {
                  var3 = Long.class;
                  return var3;
               }
               break;
            case 3625364:
               if (var2.equals("void")) {
                  var3 = Void.class;
                  return var3;
               }
               break;
            case 64711720:
               if (var2.equals("boolean")) {
                  var3 = Boolean.class;
                  return var3;
               }
               break;
            case 97526364:
               if (var2.equals("float")) {
                  var3 = Float.class;
                  return var3;
               }
               break;
            case 109413500:
               if (var2.equals("short")) {
                  var3 = Short.class;
                  return var3;
               }
            }
         }

         var3 = thisJClass;
         return var3;
      }
   }

   @JvmName(
      name = "getKotlinClass"
   )
   @NotNull
   public static final <T> KClass<T> getKotlinClass(@NotNull Class<T> $this$kotlin) {
      Intrinsics.checkParameterIsNotNull($this$kotlin, "$this$kotlin");
      return Reflection.getOrCreateKotlinClass($this$kotlin);
   }

   @NotNull
   public static final <T> Class<T> getJavaClass(@NotNull T $this$javaClass) {
      int $i$f$getJavaClass = 0;
      Intrinsics.checkParameterIsNotNull($this$javaClass, "$this$javaClass");
      return $this$javaClass.getClass();
   }

   /** @deprecated */
   // $FF: synthetic method
   @Deprecated(
      message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "(this as Any).javaClass"
),
      level = DeprecationLevel.ERROR
   )
   public static void javaClass$annotations(KClass var0) {
   }

   /** @deprecated */
   @JvmName(
      name = "getRuntimeClassOfKClassInstance"
   )
   @NotNull
   public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(@NotNull KClass<T> $this$javaClass) {
      int $i$f$getRuntimeClassOfKClassInstance = 0;
      Intrinsics.checkParameterIsNotNull($this$javaClass, "$this$javaClass");
      return ((Object)$this$javaClass).getClass();
   }

   // $FF: synthetic method
   public static final <T> boolean isArrayOf(@NotNull Object[] $this$isArrayOf) {
      Intrinsics.checkParameterIsNotNull($this$isArrayOf, "$this$isArrayOf");
      Intrinsics.reifiedOperationMarker(4, "T");
      return Object.class.isAssignableFrom($this$isArrayOf.getClass().getComponentType());
   }

   @NotNull
   public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(@NotNull T $this$annotationClass) {
      Intrinsics.checkParameterIsNotNull($this$annotationClass, "$this$annotationClass");
      Class var10000 = $this$annotationClass.annotationType();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.annot…otation).annotationType()");
      return getKotlinClass(var10000);
   }
}
