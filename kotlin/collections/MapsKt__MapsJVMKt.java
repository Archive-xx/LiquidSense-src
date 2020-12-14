package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000D\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005\u001aY\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0001\u0010\u00032*\u0010\t\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00050\n\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005¢\u0006\u0002\u0010\u000b\u001a@\u0010\f\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\r2\u0006\u0010\u000e\u001a\u0002H\u00022\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0010H\u0086\b¢\u0006\u0002\u0010\u0011\u001a\u0019\u0010\u0012\u001a\u00020\u0013*\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00140\u0001H\u0087\b\u001a2\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0000\u001a1\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0081\b\u001a:\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\u001a@\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u000e\u0010\u0018\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0019¨\u0006\u001a"},
   d2 = {"mapOf", "", "K", "V", "pair", "Lkotlin/Pair;", "sortedMapOf", "Ljava/util/SortedMap;", "", "pairs", "", "([Lkotlin/Pair;)Ljava/util/SortedMap;", "getOrPut", "Ljava/util/concurrent/ConcurrentMap;", "key", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/concurrent/ConcurrentMap;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toProperties", "Ljava/util/Properties;", "", "toSingletonMap", "toSingletonMapOrSelf", "toSortedMap", "comparator", "Ljava/util/Comparator;", "kotlin-stdlib"},
   xs = "kotlin/collections/MapsKt"
)
class MapsKt__MapsJVMKt extends MapsKt__MapWithDefaultKt {
   @NotNull
   public static final <K, V> Map<K, V> mapOf(@NotNull Pair<? extends K, ? extends V> pair) {
      Intrinsics.checkParameterIsNotNull(pair, "pair");
      Map var10000 = Collections.singletonMap(pair.getFirst(), pair.getSecond());
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Collections.si…(pair.first, pair.second)");
      return var10000;
   }

   public static final <K, V> V getOrPut(@NotNull ConcurrentMap<K, V> $this$getOrPut, K key, @NotNull Function0<? extends V> defaultValue) {
      int $i$f$getOrPut = 0;
      Intrinsics.checkParameterIsNotNull($this$getOrPut, "$this$getOrPut");
      Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
      Object var10000 = $this$getOrPut.get(key);
      if (var10000 == null) {
         Object var4 = defaultValue.invoke();
         boolean var5 = false;
         boolean var6 = false;
         int var8 = false;
         var10000 = $this$getOrPut.putIfAbsent(key, var4);
         if (var10000 == null) {
            var10000 = var4;
         }
      }

      return var10000;
   }

   @NotNull
   public static final <K extends Comparable<? super K>, V> SortedMap<K, V> toSortedMap(@NotNull Map<? extends K, ? extends V> $this$toSortedMap) {
      Intrinsics.checkParameterIsNotNull($this$toSortedMap, "$this$toSortedMap");
      return (SortedMap)(new TreeMap($this$toSortedMap));
   }

   @NotNull
   public static final <K, V> SortedMap<K, V> toSortedMap(@NotNull Map<? extends K, ? extends V> $this$toSortedMap, @NotNull Comparator<? super K> comparator) {
      Intrinsics.checkParameterIsNotNull($this$toSortedMap, "$this$toSortedMap");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      TreeMap var2 = new TreeMap(comparator);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      var2.putAll($this$toSortedMap);
      return (SortedMap)var2;
   }

   @NotNull
   public static final <K extends Comparable<? super K>, V> SortedMap<K, V> sortedMapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
      Intrinsics.checkParameterIsNotNull(pairs, "pairs");
      TreeMap var1 = new TreeMap();
      boolean var2 = false;
      boolean var3 = false;
      int var5 = false;
      MapsKt.putAll((Map)var1, pairs);
      return (SortedMap)var1;
   }

   @InlineOnly
   private static final Properties toProperties(@NotNull Map<String, String> $this$toProperties) {
      int $i$f$toProperties = 0;
      Properties var2 = new Properties();
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      var2.putAll($this$toProperties);
      return var2;
   }

   @InlineOnly
   private static final <K, V> Map<K, V> toSingletonMapOrSelf(@NotNull Map<K, ? extends V> $this$toSingletonMapOrSelf) {
      int $i$f$toSingletonMapOrSelf = 0;
      return MapsKt.toSingletonMap($this$toSingletonMapOrSelf);
   }

   @NotNull
   public static final <K, V> Map<K, V> toSingletonMap(@NotNull Map<? extends K, ? extends V> $this$toSingletonMap) {
      Intrinsics.checkParameterIsNotNull($this$toSingletonMap, "$this$toSingletonMap");
      Object var1 = $this$toSingletonMap.entrySet().iterator().next();
      boolean var2 = false;
      boolean var3 = false;
      Entry $this$with = (Entry)var1;
      int var5 = false;
      Map var10000 = Collections.singletonMap($this$with.getKey(), $this$with.getValue());
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Collections.singletonMap(key, value)");
      Intrinsics.checkExpressionValueIsNotNull(var10000, "with(entries.iterator().…ingletonMap(key, value) }");
      return var10000;
   }

   public MapsKt__MapsJVMKt() {
   }
}
