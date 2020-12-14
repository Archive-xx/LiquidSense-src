package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.internal.HidesMembers;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000h\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001aG\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a$\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010\b\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001a9\u0010\t\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\n\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001a6\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070\f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001a'\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004H\u0087\b\u001aG\u0010\r\u001a\u00020\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aY\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b\u001ar\u0010\u0013\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142$\u0010\u0012\u001a \u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\n0\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001aG\u0010\u0018\u001a\u00020\u0019\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b\u001aS\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001aY\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b\u001ar\u0010\u001e\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\b\b\u0002\u0010\u0011*\u00020\u001d\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142 \u0010\u0012\u001a\u001c\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\u00110\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001al\u0010\u001f\u001a\u0002H\u0014\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0011\"\u0010\b\u0003\u0010\u0014*\n\u0012\u0006\b\u0000\u0012\u0002H\u00110\u0015*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u0006\u0010\u0016\u001a\u0002H\u00142\u001e\u0010\u0012\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b¢\u0006\u0002\u0010\u0017\u001ae\u0010 \u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0087\b\u001ai\u0010#\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&H\u0087\b\u001ae\u0010'\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u000e\b\u0002\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110!*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\"\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u0002H\u00110\u0006H\u0086\b\u001af\u0010(\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u000422\u0010$\u001a.\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00070%j\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007`&\u001a$\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004\u001aG\u0010)\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00042\u001e\u0010\u0005\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0086\b\u001aV\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0016\b\u0002\u0010+*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004*\u0002H+2\u001e\u0010\u001a\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0007\u0012\u0004\u0012\u00020\u00190\u0006H\u0087\b¢\u0006\u0002\u0010,\u001a6\u0010-\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030.0\u0010\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0004¨\u0006/"},
   d2 = {"all", "", "K", "V", "", "predicate", "Lkotlin/Function1;", "", "any", "asIterable", "", "asSequence", "Lkotlin/sequences/Sequence;", "count", "", "flatMap", "", "R", "transform", "flatMapTo", "C", "", "destination", "(Ljava/util/Map;Ljava/util/Collection;Lkotlin/jvm/functions/Function1;)Ljava/util/Collection;", "forEach", "", "action", "map", "mapNotNull", "", "mapNotNullTo", "mapTo", "maxBy", "", "selector", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "minBy", "minWith", "none", "onEach", "M", "(Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "toList", "Lkotlin/Pair;", "kotlin-stdlib"},
   xs = "kotlin/collections/MapsKt"
)
class MapsKt___MapsKt extends MapsKt__MapsKt {
   @NotNull
   public static final <K, V> List<Pair<K, V>> toList(@NotNull Map<? extends K, ? extends V> $this$toList) {
      Intrinsics.checkParameterIsNotNull($this$toList, "$this$toList");
      if ($this$toList.size() == 0) {
         return CollectionsKt.emptyList();
      } else {
         Iterator iterator = $this$toList.entrySet().iterator();
         if (!iterator.hasNext()) {
            return CollectionsKt.emptyList();
         } else {
            Entry first = (Entry)iterator.next();
            if (!iterator.hasNext()) {
               boolean var8 = false;
               return CollectionsKt.listOf(new Pair(first.getKey(), first.getValue()));
            } else {
               ArrayList result = new ArrayList($this$toList.size());
               boolean var5 = false;
               Pair var7 = new Pair(first.getKey(), first.getValue());
               result.add(var7);

               do {
                  Entry var4 = (Entry)iterator.next();
                  var5 = false;
                  var7 = new Pair(var4.getKey(), var4.getValue());
                  result.add(var7);
               } while(iterator.hasNext());

               return (List)result;
            }
         }
      }
   }

   @NotNull
   public static final <K, V, R> List<R> flatMap(@NotNull Map<? extends K, ? extends V> $this$flatMap, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> transform) {
      int $i$f$flatMap = 0;
      Intrinsics.checkParameterIsNotNull($this$flatMap, "$this$flatMap");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$flatMapTo = false;
      boolean var7 = false;
      Iterator var8 = $this$flatMap.entrySet().iterator();

      while(var8.hasNext()) {
         Entry element$iv = (Entry)var8.next();
         Iterable list$iv = (Iterable)transform.invoke(element$iv);
         CollectionsKt.addAll(destination$iv, list$iv);
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <K, V, R, C extends Collection<? super R>> C flatMapTo(@NotNull Map<? extends K, ? extends V> $this$flatMapTo, @NotNull C destination, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends Iterable<? extends R>> transform) {
      int $i$f$flatMapTo = 0;
      Intrinsics.checkParameterIsNotNull($this$flatMapTo, "$this$flatMapTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      boolean var7 = false;
      Iterator var5 = $this$flatMapTo.entrySet().iterator();

      while(var5.hasNext()) {
         Entry element = (Entry)var5.next();
         Iterable list = (Iterable)transform.invoke(element);
         CollectionsKt.addAll(destination, list);
      }

      return destination;
   }

   @NotNull
   public static final <K, V, R> List<R> map(@NotNull Map<? extends K, ? extends V> $this$map, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$map = 0;
      Intrinsics.checkParameterIsNotNull($this$map, "$this$map");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList($this$map.size()));
      int $i$f$mapTo = false;
      boolean var7 = false;
      Iterator var8 = $this$map.entrySet().iterator();

      while(var8.hasNext()) {
         Entry item$iv = (Entry)var8.next();
         destination$iv.add(transform.invoke(item$iv));
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <K, V, R> List<R> mapNotNull(@NotNull Map<? extends K, ? extends V> $this$mapNotNull, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$mapNotNull = 0;
      Intrinsics.checkParameterIsNotNull($this$mapNotNull, "$this$mapNotNull");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      Collection destination$iv = (Collection)(new ArrayList());
      int $i$f$mapNotNullTo = false;
      int $i$f$forEach = false;
      boolean var9 = false;
      Iterator var10 = $this$mapNotNull.entrySet().iterator();

      while(var10.hasNext()) {
         Entry element$iv$iv = (Entry)var10.next();
         int var13 = false;
         Object var10000 = transform.invoke(element$iv$iv);
         if (var10000 != null) {
            Object var14 = var10000;
            boolean var15 = false;
            boolean var16 = false;
            int var18 = false;
            destination$iv.add(var14);
         }
      }

      return (List)destination$iv;
   }

   @NotNull
   public static final <K, V, R, C extends Collection<? super R>> C mapNotNullTo(@NotNull Map<? extends K, ? extends V> $this$mapNotNullTo, @NotNull C destination, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$mapNotNullTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapNotNullTo, "$this$mapNotNullTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      int $i$f$forEach = false;
      boolean var7 = false;
      Iterator var8 = $this$mapNotNullTo.entrySet().iterator();

      while(var8.hasNext()) {
         Entry element$iv = (Entry)var8.next();
         int var11 = false;
         Object var10000 = transform.invoke(element$iv);
         if (var10000 != null) {
            Object var12 = var10000;
            boolean var13 = false;
            boolean var14 = false;
            int var16 = false;
            destination.add(var12);
         }
      }

      return destination;
   }

   @NotNull
   public static final <K, V, R, C extends Collection<? super R>> C mapTo(@NotNull Map<? extends K, ? extends V> $this$mapTo, @NotNull C destination, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> transform) {
      int $i$f$mapTo = 0;
      Intrinsics.checkParameterIsNotNull($this$mapTo, "$this$mapTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      boolean var7 = false;
      Iterator var5 = $this$mapTo.entrySet().iterator();

      while(var5.hasNext()) {
         Entry item = (Entry)var5.next();
         destination.add(transform.invoke(item));
      }

      return destination;
   }

   public static final <K, V> boolean all(@NotNull Map<? extends K, ? extends V> $this$all, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$all = 0;
      Intrinsics.checkParameterIsNotNull($this$all, "$this$all");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      if ($this$all.isEmpty()) {
         return true;
      } else {
         boolean var6 = false;
         Iterator var4 = $this$all.entrySet().iterator();

         Entry element;
         do {
            if (!var4.hasNext()) {
               return true;
            }

            element = (Entry)var4.next();
         } while((Boolean)predicate.invoke(element));

         return false;
      }
   }

   public static final <K, V> boolean any(@NotNull Map<? extends K, ? extends V> $this$any) {
      Intrinsics.checkParameterIsNotNull($this$any, "$this$any");
      return !$this$any.isEmpty();
   }

   public static final <K, V> boolean any(@NotNull Map<? extends K, ? extends V> $this$any, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$any = 0;
      Intrinsics.checkParameterIsNotNull($this$any, "$this$any");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      if ($this$any.isEmpty()) {
         return false;
      } else {
         boolean var6 = false;
         Iterator var4 = $this$any.entrySet().iterator();

         Entry element;
         do {
            if (!var4.hasNext()) {
               return false;
            }

            element = (Entry)var4.next();
         } while(!(Boolean)predicate.invoke(element));

         return true;
      }
   }

   @InlineOnly
   private static final <K, V> int count(@NotNull Map<? extends K, ? extends V> $this$count) {
      int $i$f$count = 0;
      return $this$count.size();
   }

   public static final <K, V> int count(@NotNull Map<? extends K, ? extends V> $this$count, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$count = 0;
      Intrinsics.checkParameterIsNotNull($this$count, "$this$count");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      if ($this$count.isEmpty()) {
         return 0;
      } else {
         int count = 0;
         boolean var7 = false;
         Iterator var5 = $this$count.entrySet().iterator();

         while(var5.hasNext()) {
            Entry element = (Entry)var5.next();
            if ((Boolean)predicate.invoke(element)) {
               ++count;
            }
         }

         return count;
      }
   }

   @HidesMembers
   public static final <K, V> void forEach(@NotNull Map<? extends K, ? extends V> $this$forEach, @NotNull Function1<? super Entry<? extends K, ? extends V>, Unit> action) {
      int $i$f$forEach = 0;
      Intrinsics.checkParameterIsNotNull($this$forEach, "$this$forEach");
      Intrinsics.checkParameterIsNotNull(action, "action");
      boolean var6 = false;
      Iterator var4 = $this$forEach.entrySet().iterator();

      while(var4.hasNext()) {
         Entry element = (Entry)var4.next();
         action.invoke(element);
      }

   }

   @InlineOnly
   private static final <K, V, R extends Comparable<? super R>> Entry<K, V> maxBy(@NotNull Map<? extends K, ? extends V> $this$maxBy, Function1<? super Entry<? extends K, ? extends V>, ? extends R> selector) {
      int $i$f$maxBy = 0;
      Iterable $this$maxBy$iv = (Iterable)$this$maxBy.entrySet();
      int $i$f$maxBy = false;
      Iterator iterator$iv = $this$maxBy$iv.iterator();
      Object var10000;
      if (!iterator$iv.hasNext()) {
         var10000 = null;
      } else {
         Object maxElem$iv = iterator$iv.next();
         if (!iterator$iv.hasNext()) {
            var10000 = maxElem$iv;
         } else {
            Comparable maxValue$iv = (Comparable)selector.invoke(maxElem$iv);

            do {
               Object e$iv = iterator$iv.next();
               Comparable v$iv = (Comparable)selector.invoke(e$iv);
               if (maxValue$iv.compareTo(v$iv) < 0) {
                  maxElem$iv = e$iv;
                  maxValue$iv = v$iv;
               }
            } while(iterator$iv.hasNext());

            var10000 = maxElem$iv;
         }
      }

      return (Entry)var10000;
   }

   @InlineOnly
   private static final <K, V> Entry<K, V> maxWith(@NotNull Map<? extends K, ? extends V> $this$maxWith, Comparator<? super Entry<? extends K, ? extends V>> comparator) {
      int $i$f$maxWith = 0;
      return (Entry)CollectionsKt.maxWith((Iterable)$this$maxWith.entrySet(), comparator);
   }

   @Nullable
   public static final <K, V, R extends Comparable<? super R>> Entry<K, V> minBy(@NotNull Map<? extends K, ? extends V> $this$minBy, @NotNull Function1<? super Entry<? extends K, ? extends V>, ? extends R> selector) {
      int $i$f$minBy = 0;
      Intrinsics.checkParameterIsNotNull($this$minBy, "$this$minBy");
      Intrinsics.checkParameterIsNotNull(selector, "selector");
      Iterable $this$minBy$iv = (Iterable)$this$minBy.entrySet();
      int $i$f$minBy = false;
      Iterator iterator$iv = $this$minBy$iv.iterator();
      Object var10000;
      if (!iterator$iv.hasNext()) {
         var10000 = null;
      } else {
         Object minElem$iv = iterator$iv.next();
         if (!iterator$iv.hasNext()) {
            var10000 = minElem$iv;
         } else {
            Comparable minValue$iv = (Comparable)selector.invoke(minElem$iv);

            do {
               Object e$iv = iterator$iv.next();
               Comparable v$iv = (Comparable)selector.invoke(e$iv);
               if (minValue$iv.compareTo(v$iv) > 0) {
                  minElem$iv = e$iv;
                  minValue$iv = v$iv;
               }
            } while(iterator$iv.hasNext());

            var10000 = minElem$iv;
         }
      }

      return (Entry)var10000;
   }

   @Nullable
   public static final <K, V> Entry<K, V> minWith(@NotNull Map<? extends K, ? extends V> $this$minWith, @NotNull Comparator<? super Entry<? extends K, ? extends V>> comparator) {
      Intrinsics.checkParameterIsNotNull($this$minWith, "$this$minWith");
      Intrinsics.checkParameterIsNotNull(comparator, "comparator");
      return (Entry)CollectionsKt.minWith((Iterable)$this$minWith.entrySet(), comparator);
   }

   public static final <K, V> boolean none(@NotNull Map<? extends K, ? extends V> $this$none) {
      Intrinsics.checkParameterIsNotNull($this$none, "$this$none");
      return $this$none.isEmpty();
   }

   public static final <K, V> boolean none(@NotNull Map<? extends K, ? extends V> $this$none, @NotNull Function1<? super Entry<? extends K, ? extends V>, Boolean> predicate) {
      int $i$f$none = 0;
      Intrinsics.checkParameterIsNotNull($this$none, "$this$none");
      Intrinsics.checkParameterIsNotNull(predicate, "predicate");
      if ($this$none.isEmpty()) {
         return true;
      } else {
         boolean var6 = false;
         Iterator var4 = $this$none.entrySet().iterator();

         Entry element;
         do {
            if (!var4.hasNext()) {
               return true;
            }

            element = (Entry)var4.next();
         } while(!(Boolean)predicate.invoke(element));

         return false;
      }
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <K, V, M extends Map<? extends K, ? extends V>> M onEach(@NotNull M $this$onEach, @NotNull Function1<? super Entry<? extends K, ? extends V>, Unit> action) {
      int $i$f$onEach = 0;
      Intrinsics.checkParameterIsNotNull($this$onEach, "$this$onEach");
      Intrinsics.checkParameterIsNotNull(action, "action");
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      boolean var9 = false;
      Iterator var10 = $this$onEach.entrySet().iterator();

      while(var10.hasNext()) {
         Entry element = (Entry)var10.next();
         action.invoke(element);
      }

      return $this$onEach;
   }

   @InlineOnly
   private static final <K, V> Iterable<Entry<K, V>> asIterable(@NotNull Map<? extends K, ? extends V> $this$asIterable) {
      int $i$f$asIterable = 0;
      return (Iterable)$this$asIterable.entrySet();
   }

   @NotNull
   public static final <K, V> Sequence<Entry<K, V>> asSequence(@NotNull Map<? extends K, ? extends V> $this$asSequence) {
      Intrinsics.checkParameterIsNotNull($this$asSequence, "$this$asSequence");
      return CollectionsKt.asSequence((Iterable)$this$asSequence.entrySet());
   }

   public MapsKt___MapsKt() {
   }
}
