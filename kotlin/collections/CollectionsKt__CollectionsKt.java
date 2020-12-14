package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000x\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000f\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a@\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a@\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0006\u0010\f\u001a\u00020\u00062!\u0010\r\u001a\u001d\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u00070\u000eH\u0087\b\u001a\u001f\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a5\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u0002H\u00070\u0015j\b\u0012\u0004\u0012\u0002H\u0007`\u0016\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u0019\u001a\u0012\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007\u001a\u0015\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u001c\u001a%\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010 \u001a3\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\b\b\u0000\u0010\u0007*\u00020\u001e2\u0016\u0010\u0017\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u0001H\u00070\u0018\"\u0004\u0018\u0001H\u0007¢\u0006\u0002\u0010\u001c\u001a\u0015\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u0007H\u0087\b\u001a+\u0010!\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0013\"\u0004\b\u0000\u0010\u00072\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018\"\u0002H\u0007¢\u0006\u0002\u0010\u001c\u001a%\u0010\"\u001a\u00020#2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u00062\u0006\u0010%\u001a\u00020\u0006H\u0002¢\u0006\u0002\b&\u001a\b\u0010'\u001a\u00020#H\u0001\u001a\b\u0010(\u001a\u00020#H\u0001\u001a%\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0006\b\u0001\u0012\u0002H\u00070\u0018H\u0000¢\u0006\u0002\u0010*\u001aS\u0010+\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\u0006\u0010\u001f\u001a\u0002H\u00072\u001a\u0010,\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00070-j\n\u0012\u0006\b\u0000\u0012\u0002H\u0007`.2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006¢\u0006\u0002\u0010/\u001a>\u0010+\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0012\u00100\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u00060\u000e\u001aE\u0010+\u001a\u00020\u0006\"\u000e\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u000701*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00070\b2\b\u0010\u001f\u001a\u0004\u0018\u0001H\u00072\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006¢\u0006\u0002\u00102\u001ad\u00103\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007\"\u000e\b\u0001\u00104*\b\u0012\u0004\u0012\u0002H401*\b\u0012\u0004\u0012\u0002H\u00070\b2\b\u00105\u001a\u0004\u0018\u0001H42\b\b\u0002\u0010$\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u00062\u0016\b\u0004\u00106\u001a\u0010\u0012\u0004\u0012\u0002H\u0007\u0012\u0006\u0012\u0004\u0018\u0001H40\u000eH\u0086\b¢\u0006\u0002\u00107\u001a,\u00108\u001a\u000209\"\t\b\u0000\u0010\u0007¢\u0006\u0002\b:*\b\u0012\u0004\u0012\u0002H\u00070\u00022\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a8\u0010;\u001a\u0002H<\"\u0010\b\u0000\u0010=*\u0006\u0012\u0002\b\u00030\u0002*\u0002H<\"\u0004\b\u0001\u0010<*\u0002H=2\f\u0010>\u001a\b\u0012\u0004\u0012\u0002H<0?H\u0087\b¢\u0006\u0002\u0010@\u001a\u0019\u0010A\u001a\u000209\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0002H\u0087\b\u001a,\u0010B\u001a\u000209\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\u001e\u0010C\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\bH\u0000\u001a!\u0010D\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0002\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u0002H\u0087\b\u001a!\u0010D\u001a\b\u0012\u0004\u0012\u0002H\u00070\b\"\u0004\b\u0000\u0010\u0007*\n\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\bH\u0087\b\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"!\u0010\u0005\u001a\u00020\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006E"},
   d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/util/Collection;)Lkotlin/ranges/IntRange;", "lastIndex", "", "T", "", "getLastIndex", "(Ljava/util/List;)I", "List", "size", "init", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "index", "MutableList", "", "arrayListOf", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "elements", "", "([Ljava/lang/Object;)Ljava/util/ArrayList;", "emptyList", "listOf", "([Ljava/lang/Object;)Ljava/util/List;", "listOfNotNull", "", "element", "(Ljava/lang/Object;)Ljava/util/List;", "mutableListOf", "rangeCheck", "", "fromIndex", "toIndex", "rangeCheck$CollectionsKt__CollectionsKt", "throwCountOverflow", "throwIndexOverflow", "asCollection", "([Ljava/lang/Object;)Ljava/util/Collection;", "binarySearch", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Ljava/util/List;Ljava/lang/Object;Ljava/util/Comparator;II)I", "comparison", "", "(Ljava/util/List;Ljava/lang/Comparable;II)I", "binarySearchBy", "K", "key", "selector", "(Ljava/util/List;Ljava/lang/Comparable;IILkotlin/jvm/functions/Function1;)I", "containsAll", "", "Lkotlin/internal/OnlyInputTypes;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Collection;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "optimizeReadOnlyList", "orEmpty", "kotlin-stdlib"},
   xs = "kotlin/collections/CollectionsKt"
)
class CollectionsKt__CollectionsKt extends CollectionsKt__CollectionsJVMKt {
   @NotNull
   public static final <T> Collection<T> asCollection(@NotNull T[] $this$asCollection) {
      Intrinsics.checkParameterIsNotNull($this$asCollection, "$this$asCollection");
      return (Collection)(new ArrayAsCollection($this$asCollection, false));
   }

   @NotNull
   public static final <T> List<T> emptyList() {
      return (List)EmptyList.INSTANCE;
   }

   @NotNull
   public static final <T> List<T> listOf(@NotNull T... elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      return elements.length > 0 ? ArraysKt.asList(elements) : CollectionsKt.emptyList();
   }

   @InlineOnly
   private static final <T> List<T> listOf() {
      int $i$f$listOf = 0;
      return CollectionsKt.emptyList();
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <T> List<T> mutableListOf() {
      int $i$f$mutableListOf = 0;
      return (List)(new ArrayList());
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <T> ArrayList<T> arrayListOf() {
      int $i$f$arrayListOf = 0;
      return new ArrayList();
   }

   @NotNull
   public static final <T> List<T> mutableListOf(@NotNull T... elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      return elements.length == 0 ? (List)(new ArrayList()) : (List)(new ArrayList((Collection)(new ArrayAsCollection(elements, true))));
   }

   @NotNull
   public static final <T> ArrayList<T> arrayListOf(@NotNull T... elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      return elements.length == 0 ? new ArrayList() : new ArrayList((Collection)(new ArrayAsCollection(elements, true)));
   }

   @NotNull
   public static final <T> List<T> listOfNotNull(@Nullable T element) {
      return element != null ? CollectionsKt.listOf(element) : CollectionsKt.emptyList();
   }

   @NotNull
   public static final <T> List<T> listOfNotNull(@NotNull T... elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      return ArraysKt.filterNotNull(elements);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <T> List<T> List(int size, Function1<? super Integer, ? extends T> init) {
      int $i$f$List = 0;
      boolean var3 = false;
      ArrayList var4 = new ArrayList(size);
      boolean var5 = false;
      boolean var6 = false;
      int var10 = 0;

      for(int var7 = size; var10 < var7; ++var10) {
         boolean var9 = false;
         var4.add(init.invoke(var10));
      }

      return (List)var4;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <T> List<T> MutableList(int size, Function1<? super Integer, ? extends T> init) {
      int $i$f$MutableList = 0;
      ArrayList list = new ArrayList(size);
      boolean var4 = false;
      boolean var5 = false;
      int var9 = 0;

      for(int var6 = size; var9 < var6; ++var9) {
         int var8 = false;
         list.add(init.invoke(var9));
      }

      return (List)list;
   }

   @NotNull
   public static final IntRange getIndices(@NotNull Collection<?> $this$indices) {
      Intrinsics.checkParameterIsNotNull($this$indices, "$this$indices");
      byte var1 = 0;
      return new IntRange(var1, $this$indices.size() - 1);
   }

   public static final <T> int getLastIndex(@NotNull List<? extends T> $this$lastIndex) {
      Intrinsics.checkParameterIsNotNull($this$lastIndex, "$this$lastIndex");
      return $this$lastIndex.size() - 1;
   }

   @InlineOnly
   private static final <T> boolean isNotEmpty(@NotNull Collection<? extends T> $this$isNotEmpty) {
      int $i$f$isNotEmpty = 0;
      return !$this$isNotEmpty.isEmpty();
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <T> boolean isNullOrEmpty(@Nullable Collection<? extends T> $this$isNullOrEmpty) {
      int $i$f$isNullOrEmpty = 0;
      boolean var2 = false;
      return $this$isNullOrEmpty == null || $this$isNullOrEmpty.isEmpty();
   }

   @InlineOnly
   private static final <T> Collection<T> orEmpty(@Nullable Collection<? extends T> $this$orEmpty) {
      int $i$f$orEmpty = 0;
      Collection var10000 = $this$orEmpty;
      if ($this$orEmpty == null) {
         var10000 = (Collection)CollectionsKt.emptyList();
      }

      return var10000;
   }

   @InlineOnly
   private static final <T> List<T> orEmpty(@Nullable List<? extends T> $this$orEmpty) {
      int $i$f$orEmpty = 0;
      List var10000 = $this$orEmpty;
      if ($this$orEmpty == null) {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <C extends Collection<?> & R, R> R ifEmpty(C $this$ifEmpty, Function0<? extends R> defaultValue) {
      int $i$f$ifEmpty = 0;
      return $this$ifEmpty.isEmpty() ? defaultValue.invoke() : $this$ifEmpty;
   }

   @InlineOnly
   private static final <T> boolean containsAll(@NotNull Collection<? extends T> $this$containsAll, Collection<? extends T> elements) {
      int $i$f$containsAll = 0;
      return $this$containsAll.containsAll(elements);
   }

   @NotNull
   public static final <T> List<T> optimizeReadOnlyList(@NotNull List<? extends T> $this$optimizeReadOnlyList) {
      Intrinsics.checkParameterIsNotNull($this$optimizeReadOnlyList, "$this$optimizeReadOnlyList");
      List var10000;
      switch($this$optimizeReadOnlyList.size()) {
      case 0:
         var10000 = CollectionsKt.emptyList();
         break;
      case 1:
         var10000 = CollectionsKt.listOf($this$optimizeReadOnlyList.get(0));
         break;
      default:
         var10000 = $this$optimizeReadOnlyList;
      }

      return var10000;
   }

   public static final <T extends Comparable<? super T>> int binarySearch(@NotNull List<? extends T> $this$binarySearch, @Nullable T element, int fromIndex, int toIndex) {
      Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
      rangeCheck$CollectionsKt__CollectionsKt($this$binarySearch.size(), fromIndex, toIndex);
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         Comparable midVal = (Comparable)$this$binarySearch.get(mid);
         int cmp = ComparisonsKt.compareValues(midVal, element);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, Comparable var1, int var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = 0;
      }

      if ((var4 & 4) != 0) {
         var3 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   public static final <T> int binarySearch(@NotNull List<? extends T> $this$binarySearch, T element, @NotNull Comparator<? super T> comparator, int fromIndex, int toIndex) {
      Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      rangeCheck$CollectionsKt__CollectionsKt($this$binarySearch.size(), fromIndex, toIndex);
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         Object midVal = $this$binarySearch.get(mid);
         int cmp = comparator.compare(midVal, element);
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, Object var1, Comparator var2, int var3, int var4, int var5, Object var6) {
      if ((var5 & 4) != 0) {
         var3 = 0;
      }

      if ((var5 & 8) != 0) {
         var4 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3, var4);
   }

   public static final <T, K extends Comparable<? super K>> int binarySearchBy(@NotNull List<? extends T> $this$binarySearchBy, @Nullable K key, int fromIndex, int toIndex, @NotNull Function1<? super T, ? extends K> selector) {
      int $i$f$binarySearchBy = 0;
      Intrinsics.checkParameterIsNotNull($this$binarySearchBy, "$this$binarySearchBy");
      Intrinsics.checkParameterIsNotNull(selector, "selector");
      return CollectionsKt.binarySearch($this$binarySearchBy, fromIndex, toIndex, (Function1)(new Function1<T, Integer>() {
         public final int invoke(T it) {
            return ComparisonsKt.compareValues((Comparable)selector.invoke(it), key);
         }
      }));
   }

   // $FF: synthetic method
   public static int binarySearchBy$default(List $this$binarySearchBy, final Comparable key, int fromIndex, int toIndex, final Function1 selector, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         fromIndex = 0;
      }

      if ((var5 & 4) != 0) {
         toIndex = $this$binarySearchBy.size();
      }

      int $i$f$binarySearchBy = false;
      Intrinsics.checkParameterIsNotNull($this$binarySearchBy, "$this$binarySearchBy");
      Intrinsics.checkParameterIsNotNull(selector, "selector");
      return CollectionsKt.binarySearch($this$binarySearchBy, fromIndex, toIndex, (Function1)(new Function1<T, Integer>() {
         public final int invoke(T it) {
            return ComparisonsKt.compareValues((Comparable)selector.invoke(it), key);
         }
      }));
   }

   public static final <T> int binarySearch(@NotNull List<? extends T> $this$binarySearch, int fromIndex, int toIndex, @NotNull Function1<? super T, Integer> comparison) {
      Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
      Intrinsics.checkParameterIsNotNull(comparison, "comparison");
      rangeCheck$CollectionsKt__CollectionsKt($this$binarySearch.size(), fromIndex, toIndex);
      int low = fromIndex;
      int high = toIndex - 1;

      while(low <= high) {
         int mid = low + high >>> 1;
         Object midVal = $this$binarySearch.get(mid);
         int cmp = ((Number)comparison.invoke(midVal)).intValue();
         if (cmp < 0) {
            low = mid + 1;
         } else {
            if (cmp <= 0) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   // $FF: synthetic method
   public static int binarySearch$default(List var0, int var1, int var2, Function1 var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.size();
      }

      return CollectionsKt.binarySearch(var0, var1, var2, var3);
   }

   private static final void rangeCheck$CollectionsKt__CollectionsKt(int size, int fromIndex, int toIndex) {
      if (fromIndex > toIndex) {
         throw (Throwable)(new IllegalArgumentException("fromIndex (" + fromIndex + ") is greater than toIndex (" + toIndex + ")."));
      } else if (fromIndex < 0) {
         throw (Throwable)(new IndexOutOfBoundsException("fromIndex (" + fromIndex + ") is less than zero."));
      } else if (toIndex > size) {
         throw (Throwable)(new IndexOutOfBoundsException("toIndex (" + toIndex + ") is greater than size (" + size + ")."));
      }
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwIndexOverflow() {
      throw (Throwable)(new ArithmeticException("Index overflow has happened."));
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwCountOverflow() {
      throw (Throwable)(new ArithmeticException("Count overflow has happened."));
   }

   public CollectionsKt__CollectionsKt() {
   }
}
