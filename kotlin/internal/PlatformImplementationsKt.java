package kotlin.internal;

import kotlin.KotlinVersion;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0004\u001a \u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005H\u0001\u001a\"\u0010\b\u001a\u0002H\t\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0083\b¢\u0006\u0002\u0010\f\u001a\b\u0010\r\u001a\u00020\u0005H\u0002\"\u0010\u0010\u0000\u001a\u00020\u00018\u0000X\u0081\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"},
   d2 = {"IMPLEMENTATIONS", "Lkotlin/internal/PlatformImplementations;", "apiVersionIsAtLeast", "", "major", "", "minor", "patch", "castToBaseType", "T", "", "instance", "(Ljava/lang/Object;)Ljava/lang/Object;", "getJavaVersion", "kotlin-stdlib"}
)
public final class PlatformImplementationsKt {
   @JvmField
   @NotNull
   public static final PlatformImplementations IMPLEMENTATIONS;

   // $FF: synthetic method
   @InlineOnly
   private static final <T> T castToBaseType(Object instance) {
      byte var1 = 0;

      try {
         Intrinsics.reifiedOperationMarker(1, "T");
         return (Object)instance;
      } catch (ClassCastException var5) {
         ClassLoader instanceCL = instance.getClass().getClassLoader();
         Intrinsics.reifiedOperationMarker(4, "T");
         ClassLoader baseTypeCL = Object.class.getClassLoader();
         Throwable var10000 = (new ClassCastException("Instance classloader: " + instanceCL + ", base type classloader: " + baseTypeCL)).initCause((Throwable)var5);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
         throw var10000;
      }
   }

   private static final int getJavaVersion() {
      int var0 = 65542;
      String var10000 = System.getProperty("java.specification.version");
      if (var10000 != null) {
         String version = var10000;
         int firstDot = StringsKt.indexOf$default((CharSequence)version, '.', 0, false, 6, (Object)null);
         int secondDot;
         if (firstDot < 0) {
            try {
               boolean var13 = false;
               secondDot = Integer.parseInt(version) * 65536;
            } catch (NumberFormatException var11) {
               secondDot = var0;
            }

            return secondDot;
         } else {
            secondDot = StringsKt.indexOf$default((CharSequence)version, '.', firstDot + 1, false, 4, (Object)null);
            if (secondDot < 0) {
               secondDot = version.length();
            }

            byte var6 = 0;
            boolean var7 = false;
            if (version == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
               var10000 = version.substring(var6, firstDot);
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               String firstPart = var10000;
               int var15 = firstDot + 1;
               boolean var8 = false;
               if (version == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               } else {
                  var10000 = version.substring(var15, secondDot);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                  String secondPart = var10000;

                  int var14;
                  try {
                     var7 = false;
                     int var9 = Integer.parseInt(firstPart) * 65536;
                     var7 = false;
                     int var10 = Integer.parseInt(secondPart);
                     var14 = var9 + var10;
                  } catch (NumberFormatException var12) {
                     var14 = var0;
                  }

                  return var14;
               }
            }
         }
      } else {
         return var0;
      }
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.2"
   )
   public static final boolean apiVersionIsAtLeast(int major, int minor, int patch) {
      return KotlinVersion.CURRENT.isAtLeast(major, minor, patch);
   }

   static {
      PlatformImplementations var10000;
      label74: {
         boolean var0 = false;
         boolean var1 = false;
         int var2 = false;
         int version = getJavaVersion();
         Object var4;
         boolean var5;
         ClassLoader var7;
         ClassLoader var8;
         Object var17;
         Throwable var18;
         if (version >= 65544) {
            try {
               var17 = Class.forName("kotlin.internal.jdk8.JDK8PlatformImplementations").newInstance();
               Intrinsics.checkExpressionValueIsNotNull(var17, "Class.forName(\"kotlin.in…entations\").newInstance()");
               var4 = var17;
               var5 = false;

               try {
                  if (var4 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                  }

                  var10000 = (PlatformImplementations)var4;
                  break label74;
               } catch (ClassCastException var12) {
                  var7 = var4.getClass().getClassLoader();
                  var8 = PlatformImplementations.class.getClassLoader();
                  var18 = (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var12);
                  Intrinsics.checkExpressionValueIsNotNull(var18, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                  throw var18;
               }
            } catch (ClassNotFoundException var16) {
               try {
                  var17 = Class.forName("kotlin.internal.JRE8PlatformImplementations").newInstance();
                  Intrinsics.checkExpressionValueIsNotNull(var17, "Class.forName(\"kotlin.in…entations\").newInstance()");
                  var4 = var17;
                  var5 = false;

                  try {
                     if (var4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                     }

                     var10000 = (PlatformImplementations)var4;
                     break label74;
                  } catch (ClassCastException var11) {
                     var7 = var4.getClass().getClassLoader();
                     var8 = PlatformImplementations.class.getClassLoader();
                     var18 = (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var11);
                     Intrinsics.checkExpressionValueIsNotNull(var18, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                     throw var18;
                  }
               } catch (ClassNotFoundException var15) {
               }
            }
         }

         if (version >= 65543) {
            try {
               var17 = Class.forName("kotlin.internal.jdk7.JDK7PlatformImplementations").newInstance();
               Intrinsics.checkExpressionValueIsNotNull(var17, "Class.forName(\"kotlin.in…entations\").newInstance()");
               var4 = var17;
               var5 = false;

               try {
                  if (var4 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                  }

                  var10000 = (PlatformImplementations)var4;
                  break label74;
               } catch (ClassCastException var10) {
                  var7 = var4.getClass().getClassLoader();
                  var8 = PlatformImplementations.class.getClassLoader();
                  var18 = (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var10);
                  Intrinsics.checkExpressionValueIsNotNull(var18, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                  throw var18;
               }
            } catch (ClassNotFoundException var14) {
               try {
                  var17 = Class.forName("kotlin.internal.JRE7PlatformImplementations").newInstance();
                  Intrinsics.checkExpressionValueIsNotNull(var17, "Class.forName(\"kotlin.in…entations\").newInstance()");
                  var4 = var17;
                  var5 = false;

                  try {
                     if (var4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type kotlin.internal.PlatformImplementations");
                     }

                     var10000 = (PlatformImplementations)var4;
                     break label74;
                  } catch (ClassCastException var9) {
                     var7 = var4.getClass().getClassLoader();
                     var8 = PlatformImplementations.class.getClassLoader();
                     var18 = (new ClassCastException("Instance classloader: " + var7 + ", base type classloader: " + var8)).initCause((Throwable)var9);
                     Intrinsics.checkExpressionValueIsNotNull(var18, "ClassCastException(\"Inst…baseTypeCL\").initCause(e)");
                     throw var18;
                  }
               } catch (ClassNotFoundException var13) {
               }
            }
         }

         var10000 = new PlatformImplementations();
      }

      IMPLEMENTATIONS = var10000;
   }
}
