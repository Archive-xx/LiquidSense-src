package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000&\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0000\u001a0\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u0005H\u0007\u001aW\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\t\"\u0004\b\u0002\u0010\b*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\u00072\u001e\u0010\n\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\t0\f\u0012\u0004\u0012\u0002H\b0\u000bH\u0081\b¨\u0006\r"},
   d2 = {"eachCount", "", "K", "", "T", "Lkotlin/collections/Grouping;", "mapValuesInPlace", "", "R", "V", "f", "Lkotlin/Function1;", "", "kotlin-stdlib"},
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K> Map<K, Integer> eachCount(@NotNull Grouping<T, ? extends K> $this$eachCount) {
      Intrinsics.checkParameterIsNotNull($this$eachCount, "$this$eachCount");
      boolean var2 = false;
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$foldTo = false;
      Grouping $this$aggregateTo$iv$iv = $this$eachCount;
      int $i$f$aggregateTo = false;
      Iterator var6 = $this$eachCount.sourceIterator();
      boolean var7 = false;
      Iterator var8 = var6;

      boolean first$iv;
      Object key$iv$iv;
      while(var8.hasNext()) {
         Object e$iv$iv = var8.next();
         key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv.get(key$iv$iv);
         first$iv = accumulator$iv$iv == null && !destination$iv.containsKey(key$iv$iv);
         int var16 = false;
         Object var10001;
         if (first$iv) {
            int var19 = false;
            Ref.IntRef var27 = new Ref.IntRef();
            var10001 = var27;
         } else {
            var10001 = accumulator$iv$iv;
         }

         Ref.IntRef acc = (Ref.IntRef)var10001;
         int var20 = false;
         boolean var22 = false;
         boolean var23 = false;
         int var25 = false;
         ++acc.element;
         destination$iv.put(key$iv$iv, acc);
      }

      Map var1 = destination$iv;
      var2 = false;
      Iterable var29 = (Iterable)var1.entrySet();
      boolean var30 = false;
      Iterator var31 = var29.iterator();

      while(var31.hasNext()) {
         key$iv$iv = var31.next();
         Entry var34 = (Entry)key$iv$iv;
         boolean var35 = false;
         if (var34 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
         }

         Entry var26 = TypeIntrinsics.asMutableMapEntry(var34);
         first$iv = false;
         Integer var36 = ((Ref.IntRef)var34.getValue()).element;
         var26.setValue(var36);
      }

      return TypeIntrinsics.asMutableMap(var1);
   }

   @PublishedApi
   @InlineOnly
   private static final <K, V, R> Map<K, R> mapValuesInPlace(@NotNull Map<K, V> $this$mapValuesInPlace, Function1<? super Entry<? extends K, ? extends V>, ? extends R> f) {
      int $i$f$mapValuesInPlace = 0;
      Iterable $this$forEach$iv = (Iterable)$this$mapValuesInPlace.entrySet();
      int $i$f$forEach = false;
      Iterator var5 = $this$forEach$iv.iterator();

      while(var5.hasNext()) {
         Object element$iv = var5.next();
         Entry it = (Entry)element$iv;
         int var8 = false;
         if (it == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap.MutableEntry<K, R>");
         }

         TypeIntrinsics.asMutableMapEntry(it).setValue(f.invoke(it));
      }

      if ($this$mapValuesInPlace == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, R>");
      } else {
         return TypeIntrinsics.asMutableMap($this$mapValuesInPlace);
      }
   }

   public GroupingKt__GroupingJVMKt() {
   }
}
