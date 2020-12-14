package kotlin.internal;

import java.lang.reflect.Method;
import java.util.regex.MatchResult;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.FallbackThreadLocalRandom;
import kotlin.random.Random;
import kotlin.text.MatchGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0010\u0018\u00002\u00020\u0001:\u0001\u0010B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\b\u0010\b\u001a\u00020\tH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0011"},
   d2 = {"Lkotlin/internal/PlatformImplementations;", "", "()V", "addSuppressed", "", "cause", "", "exception", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "ReflectAddSuppressedMethod", "kotlin-stdlib"}
)
public class PlatformImplementations {
   public void addSuppressed(@NotNull Throwable cause, @NotNull Throwable exception) {
      Intrinsics.checkParameterIsNotNull(cause, "cause");
      Intrinsics.checkParameterIsNotNull(exception, "exception");
      Method var10000 = PlatformImplementations.ReflectAddSuppressedMethod.method;
      if (var10000 != null) {
         var10000.invoke(cause, exception);
      }

   }

   @Nullable
   public MatchGroup getMatchResultNamedGroup(@NotNull MatchResult matchResult, @NotNull String name) {
      Intrinsics.checkParameterIsNotNull(matchResult, "matchResult");
      Intrinsics.checkParameterIsNotNull(name, "name");
      throw (Throwable)(new UnsupportedOperationException("Retrieving groups by name is not supported on this platform."));
   }

   @NotNull
   public Random defaultPlatformRandom() {
      return (Random)(new FallbackThreadLocalRandom());
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
      d2 = {"Lkotlin/internal/PlatformImplementations$ReflectAddSuppressedMethod;", "", "()V", "method", "Ljava/lang/reflect/Method;", "kotlin-stdlib"}
   )
   private static final class ReflectAddSuppressedMethod {
      @JvmField
      @Nullable
      public static final Method method;
      public static final PlatformImplementations.ReflectAddSuppressedMethod INSTANCE;

      static {
         PlatformImplementations.ReflectAddSuppressedMethod var0 = new PlatformImplementations.ReflectAddSuppressedMethod();
         INSTANCE = var0;
         Class var1 = Throwable.class;
         boolean var2 = false;
         boolean var3 = false;
         Class throwableClass = var1;
         int var5 = false;
         Method[] var10000 = var1.getMethods();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "throwableClass.methods");
         Method[] var6 = var10000;
         boolean var7 = false;
         boolean var9 = false;
         Method[] var10 = var6;
         int var11 = var6.length;
         int var12 = 0;

         Method var18;
         while(true) {
            if (var12 >= var11) {
               var18 = null;
               break;
            }

            Method var13;
            boolean var17;
            label22: {
               var13 = var10[var12];
               int var15 = false;
               Intrinsics.checkExpressionValueIsNotNull(var13, "it");
               if (Intrinsics.areEqual((Object)var13.getName(), (Object)"addSuppressed")) {
                  Class[] var16 = var13.getParameterTypes();
                  Intrinsics.checkExpressionValueIsNotNull(var16, "it.parameterTypes");
                  if (Intrinsics.areEqual((Object)((Class)ArraysKt.singleOrNull(var16)), (Object)throwableClass)) {
                     var17 = true;
                     break label22;
                  }
               }

               var17 = false;
            }

            if (var17) {
               var18 = var13;
               break;
            }

            ++var12;
         }

         method = var18;
      }
   }
}
