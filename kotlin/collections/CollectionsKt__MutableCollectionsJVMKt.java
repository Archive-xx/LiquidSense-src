package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\u000f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0005\u001a\u0019\u0010\u0006\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b\u001a!\u0010\u0006\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0087\b\u001a\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000bH\u0007\u001a&\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00020\n\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001a \u0010\f\u001a\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r*\b\u0012\u0004\u0012\u0002H\u00020\u0003\u001a3\u0010\f\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00100\u000fH\u0087\b\u001a5\u0010\f\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0013H\u0087\b\u001a2\u0010\u0014\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\u0011\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0012j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0013¨\u0006\u0015"},
   d2 = {"fill", "", "T", "", "value", "(Ljava/util/List;Ljava/lang/Object;)V", "shuffle", "random", "Ljava/util/Random;", "shuffled", "", "", "sort", "", "comparison", "Lkotlin/Function2;", "", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "sortWith", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__MutableCollectionsJVMKt extends CollectionsKt__IteratorsKt {
   /** @deprecated */
   @Deprecated(
      message = "Use sortWith(comparator) instead.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "this.sortWith(comparator)"
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final <T> void sort(@NotNull List<T> $this$sort, Comparator<? super T> comparator) {
      int $i$f$sort = 0;
      throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
   }

   /** @deprecated */
   @Deprecated(
      message = "Use sortWith(Comparator(comparison)) instead.",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "this.sortWith(Comparator(comparison))"
),
      level = DeprecationLevel.ERROR
   )
   @InlineOnly
   private static final <T> void sort(@NotNull List<T> $this$sort, Function2<? super T, ? super T, Integer> comparison) {
      int $i$f$sort = 0;
      throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
   }

   public static final <T extends Comparable<? super T>> void sort(@NotNull List<T> $this$sort) {
      Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
      if ($this$sort.size() > 1) {
         Collections.sort($this$sort);
      }

   }

   public static final <T> void sortWith(@NotNull List<T> $this$sortWith, @NotNull Comparator<? super T> comparator) {
      Intrinsics.checkParameterIsNotNull($this$sortWith, "$this$sortWith");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      if ($this$sortWith.size() > 1) {
         Collections.sort($this$sortWith, comparator);
      }

   }

   @InlineOnly
   @SinceKotlin(
      version = "1.2"
   )
   private static final <T> void fill(@NotNull List<T> $this$fill, T value) {
      int $i$f$fill = 0;
      Collections.fill($this$fill, value);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.2"
   )
   private static final <T> void shuffle(@NotNull List<T> $this$shuffle) {
      int $i$f$shuffle = 0;
      Collections.shuffle($this$shuffle);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.2"
   )
   private static final <T> void shuffle(@NotNull List<T> $this$shuffle, Random random) {
      int $i$f$shuffle = 0;
      Collections.shuffle($this$shuffle, random);
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final <T> List<T> shuffled(@NotNull Iterable<? extends T> $this$shuffled) {
      Intrinsics.checkParameterIsNotNull($this$shuffled, "$this$shuffled");
      List var1 = CollectionsKt.toMutableList($this$shuffled);
      boolean var2 = false;
      boolean var3 = false;
      int var5 = false;
      boolean var7 = false;
      Collections.shuffle(var1);
      return var1;
   }

   @SinceKotlin(
      version = "1.2"
   )
   @NotNull
   public static final <T> List<T> shuffled(@NotNull Iterable<? extends T> $this$shuffled, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$shuffled, "$this$shuffled");
      Intrinsics.checkParameterIsNotNull(random, "random");
      List var2 = CollectionsKt.toMutableList($this$shuffled);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      boolean var9 = false;
      Collections.shuffle(var2, random);
      return var2;
   }

   public CollectionsKt__MutableCollectionsJVMKt() {
   }
}
