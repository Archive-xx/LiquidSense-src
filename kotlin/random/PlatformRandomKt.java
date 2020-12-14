package kotlin.random;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0081\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0000\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0007¨\u0006\n"},
   d2 = {"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"}
)
public final class PlatformRandomKt {
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final java.util.Random asJavaRandom(@NotNull Random $this$asJavaRandom) {
      Intrinsics.checkParameterIsNotNull($this$asJavaRandom, "$this$asJavaRandom");
      Random var10000 = $this$asJavaRandom;
      if (!($this$asJavaRandom instanceof AbstractPlatformRandom)) {
         var10000 = null;
      }

      AbstractPlatformRandom var1 = (AbstractPlatformRandom)var10000;
      java.util.Random var2;
      if (var1 != null) {
         var2 = var1.getImpl();
         if (var2 != null) {
            return var2;
         }
      }

      var2 = (java.util.Random)(new KotlinRandom($this$asJavaRandom));
      return var2;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Random asKotlinRandom(@NotNull java.util.Random $this$asKotlinRandom) {
      Intrinsics.checkParameterIsNotNull($this$asKotlinRandom, "$this$asKotlinRandom");
      java.util.Random var10000 = $this$asKotlinRandom;
      if (!($this$asKotlinRandom instanceof KotlinRandom)) {
         var10000 = null;
      }

      KotlinRandom var1 = (KotlinRandom)var10000;
      Random var2;
      if (var1 != null) {
         var2 = var1.getImpl();
         if (var2 != null) {
            return var2;
         }
      }

      var2 = (Random)(new PlatformRandom($this$asKotlinRandom));
      return var2;
   }

   @InlineOnly
   private static final Random defaultPlatformRandom() {
      int $i$f$defaultPlatformRandom = 0;
      return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
   }

   public static final double doubleFromParts(int hi26, int low27) {
      return (double)(((long)hi26 << 27) + (long)low27) / (double)9007199254740992L;
   }
}
