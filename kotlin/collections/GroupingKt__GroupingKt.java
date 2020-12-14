package kotlin.collections;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000@\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u001a\u009b\u0001\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b\u001a´\u0001\u0010\u000f\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102b\u0010\u0006\u001a^\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0015\u0012\u0013\u0018\u0001H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0013\u0012\u00110\r¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u0002H\u00030\u0007H\u0087\b¢\u0006\u0002\u0010\u0013\u001aI\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0016\b\u0002\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00150\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u0010H\u0007¢\u0006\u0002\u0010\u0016\u001a¼\u0001\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u000526\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b\u001a|\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b¢\u0006\u0002\u0010\u001c\u001aÕ\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u001026\u0010\u0018\u001a2\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u00192K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u001aH\u0087\b¢\u0006\u0002\u0010\u001e\u001a\u0090\u0001\u0010\u001d\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0004\"\u0004\b\u0001\u0010\u0002\"\u0004\b\u0002\u0010\u0003\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102\u0006\u0010\u001b\u001a\u0002H\u000326\u0010\u0006\u001a2\u0012\u0013\u0012\u0011H\u0003¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u00030\u0019H\u0087\b¢\u0006\u0002\u0010\u001f\u001a\u0088\u0001\u0010 \u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0001\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b\u001a¡\u0001\u0010\"\u001a\u0002H\u0010\"\u0004\b\u0000\u0010!\"\b\b\u0001\u0010\u0004*\u0002H!\"\u0004\b\u0002\u0010\u0002\"\u0016\b\u0003\u0010\u0010*\u0010\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H!0\u0011*\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u0002H\u00020\u00052\u0006\u0010\u0012\u001a\u0002H\u00102K\u0010\u0006\u001aG\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\n\u0012\u0013\u0012\u0011H!¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\u000b\u0012\u0013\u0012\u0011H\u0004¢\u0006\f\b\b\u0012\b\b\t\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H!0\u001aH\u0087\b¢\u0006\u0002\u0010#¨\u0006$"},
   d2 = {"aggregate", "", "K", "R", "T", "Lkotlin/collections/Grouping;", "operation", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "key", "accumulator", "element", "", "first", "aggregateTo", "M", "", "destination", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function4;)Ljava/util/Map;", "eachCountTo", "", "(Lkotlin/collections/Grouping;Ljava/util/Map;)Ljava/util/Map;", "fold", "initialValueSelector", "Lkotlin/Function2;", "Lkotlin/Function3;", "initialValue", "(Lkotlin/collections/Grouping;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "foldTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "(Lkotlin/collections/Grouping;Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/Map;", "reduce", "S", "reduceTo", "(Lkotlin/collections/Grouping;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Ljava/util/Map;", "kotlin-stdlib"},
   xs = "kotlin/collections/GroupingKt"
)
class GroupingKt__GroupingKt extends GroupingKt__GroupingJVMKt {
   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K, R> Map<K, R> aggregate(@NotNull Grouping<T, ? extends K> $this$aggregate, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
      int $i$f$aggregate = 0;
      Intrinsics.checkParameterIsNotNull($this$aggregate, "$this$aggregate");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      Grouping $this$aggregateTo$iv = $this$aggregate;
      boolean var4 = false;
      Map destination$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = false;
      Iterator var6 = $this$aggregate.sourceIterator();
      boolean var7 = false;
      Iterator var8 = var6;

      while(var8.hasNext()) {
         Object e$iv = var8.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination$iv.get(key$iv);
         destination$iv.put(key$iv, operation.invoke(key$iv, accumulator$iv, e$iv, accumulator$iv == null && !destination$iv.containsKey(key$iv)));
      }

      return destination$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K, R, M extends Map<? super K, R>> M aggregateTo(@NotNull Grouping<T, ? extends K> $this$aggregateTo, @NotNull M destination, @NotNull Function4<? super K, ? super R, ? super T, ? super Boolean, ? extends R> operation) {
      int $i$f$aggregateTo = 0;
      Intrinsics.checkParameterIsNotNull($this$aggregateTo, "$this$aggregateTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      Iterator var6 = $this$aggregateTo.sourceIterator();
      boolean var7 = false;
      Iterator var5 = var6;

      while(var5.hasNext()) {
         Object e = var5.next();
         Object key = $this$aggregateTo.keyOf(e);
         Object accumulator = destination.get(key);
         destination.put(key, operation.invoke(key, accumulator, e, accumulator == null && !destination.containsKey(key)));
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $this$fold, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
      int $i$f$fold = 0;
      Intrinsics.checkParameterIsNotNull($this$fold, "$this$fold");
      Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int $i$f$aggregate = false;
      Grouping $this$aggregateTo$iv$iv = $this$fold;
      boolean var7 = false;
      Map destination$iv$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = false;
      Iterator var9 = $this$fold.sourceIterator();
      boolean var10 = false;
      Iterator var11 = var9;

      while(var11.hasNext()) {
         Object e$iv$iv = var11.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
         boolean first = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
         int var17 = false;
         Object var20 = operation.invoke(key$iv$iv, first ? initialValueSelector.invoke(key$iv$iv, e$iv$iv) : accumulator$iv$iv, e$iv$iv);
         destination$iv$iv.put(key$iv$iv, var20);
      }

      return destination$iv$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $this$foldTo, @NotNull M destination, @NotNull Function2<? super K, ? super T, ? extends R> initialValueSelector, @NotNull Function3<? super K, ? super R, ? super T, ? extends R> operation) {
      int $i$f$foldTo = 0;
      Intrinsics.checkParameterIsNotNull($this$foldTo, "$this$foldTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(initialValueSelector, "initialValueSelector");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      Grouping $this$aggregateTo$iv = $this$foldTo;
      int $i$f$aggregateTo = false;
      Iterator var7 = $this$foldTo.sourceIterator();
      boolean var8 = false;
      Iterator var9 = var7;

      while(var9.hasNext()) {
         Object e$iv = var9.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination.get(key$iv);
         boolean first = accumulator$iv == null && !destination.containsKey(key$iv);
         int var15 = false;
         Object var18 = operation.invoke(key$iv, first ? initialValueSelector.invoke(key$iv, e$iv) : accumulator$iv, e$iv);
         destination.put(key$iv, var18);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K, R> Map<K, R> fold(@NotNull Grouping<T, ? extends K> $this$fold, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
      int $i$f$fold = 0;
      Intrinsics.checkParameterIsNotNull($this$fold, "$this$fold");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int $i$f$aggregate = false;
      Grouping $this$aggregateTo$iv$iv = $this$fold;
      boolean var7 = false;
      Map destination$iv$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = false;
      Iterator var9 = $this$fold.sourceIterator();
      boolean var10 = false;
      Iterator var11 = var9;

      while(var11.hasNext()) {
         Object e$iv$iv = var11.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
         boolean first = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
         int var17 = false;
         Object var20 = operation.invoke(first ? initialValue : accumulator$iv$iv, e$iv$iv);
         destination$iv$iv.put(key$iv$iv, var20);
      }

      return destination$iv$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K, R, M extends Map<? super K, R>> M foldTo(@NotNull Grouping<T, ? extends K> $this$foldTo, @NotNull M destination, R initialValue, @NotNull Function2<? super R, ? super T, ? extends R> operation) {
      int $i$f$foldTo = 0;
      Intrinsics.checkParameterIsNotNull($this$foldTo, "$this$foldTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      Grouping $this$aggregateTo$iv = $this$foldTo;
      int $i$f$aggregateTo = false;
      Iterator var7 = $this$foldTo.sourceIterator();
      boolean var8 = false;
      Iterator var9 = var7;

      while(var9.hasNext()) {
         Object e$iv = var9.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination.get(key$iv);
         boolean first = accumulator$iv == null && !destination.containsKey(key$iv);
         int var15 = false;
         Object var18 = operation.invoke(first ? initialValue : accumulator$iv, e$iv);
         destination.put(key$iv, var18);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <S, T extends S, K> Map<K, S> reduce(@NotNull Grouping<T, ? extends K> $this$reduce, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
      int $i$f$reduce = 0;
      Intrinsics.checkParameterIsNotNull($this$reduce, "$this$reduce");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      int $i$f$aggregate = false;
      Grouping $this$aggregateTo$iv$iv = $this$reduce;
      boolean var6 = false;
      Map destination$iv$iv = (Map)(new LinkedHashMap());
      int $i$f$aggregateTo = false;
      Iterator var8 = $this$reduce.sourceIterator();
      boolean var9 = false;
      Iterator var10 = var8;

      while(var10.hasNext()) {
         Object e$iv$iv = var10.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination$iv$iv.get(key$iv$iv);
         boolean first = accumulator$iv$iv == null && !destination$iv$iv.containsKey(key$iv$iv);
         int var16 = false;
         Object var19 = first ? e$iv$iv : operation.invoke(key$iv$iv, accumulator$iv$iv, e$iv$iv);
         destination$iv$iv.put(key$iv$iv, var19);
      }

      return destination$iv$iv;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <S, T extends S, K, M extends Map<? super K, S>> M reduceTo(@NotNull Grouping<T, ? extends K> $this$reduceTo, @NotNull M destination, @NotNull Function3<? super K, ? super S, ? super T, ? extends S> operation) {
      int $i$f$reduceTo = 0;
      Intrinsics.checkParameterIsNotNull($this$reduceTo, "$this$reduceTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      Grouping $this$aggregateTo$iv = $this$reduceTo;
      int $i$f$aggregateTo = false;
      Iterator var6 = $this$reduceTo.sourceIterator();
      boolean var7 = false;
      Iterator var8 = var6;

      while(var8.hasNext()) {
         Object e$iv = var8.next();
         Object key$iv = $this$aggregateTo$iv.keyOf(e$iv);
         Object accumulator$iv = destination.get(key$iv);
         boolean first = accumulator$iv == null && !destination.containsKey(key$iv);
         int var14 = false;
         Object var17 = first ? e$iv : operation.invoke(key$iv, accumulator$iv, e$iv);
         destination.put(key$iv, var17);
      }

      return destination;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T, K, M extends Map<? super K, Integer>> M eachCountTo(@NotNull Grouping<T, ? extends K> $this$eachCountTo, @NotNull M destination) {
      Intrinsics.checkParameterIsNotNull($this$eachCountTo, "$this$eachCountTo");
      Intrinsics.checkParameterIsNotNull(destination, "destination");
      Object initialValue$iv = 0;
      int $i$f$foldTo = false;
      Grouping $this$aggregateTo$iv$iv = $this$eachCountTo;
      int $i$f$aggregateTo = false;
      Iterator var7 = $this$eachCountTo.sourceIterator();
      boolean var8 = false;
      Iterator var9 = var7;

      while(var9.hasNext()) {
         Object e$iv$iv = var9.next();
         Object key$iv$iv = $this$aggregateTo$iv$iv.keyOf(e$iv$iv);
         Object accumulator$iv$iv = destination.get(key$iv$iv);
         boolean first$iv = accumulator$iv$iv == null && !destination.containsKey(key$iv$iv);
         int var17 = false;
         int acc = ((Number)(first$iv ? initialValue$iv : accumulator$iv$iv)).intValue();
         int var20 = false;
         Integer var23 = acc + 1;
         destination.put(key$iv$iv, var23);
      }

      return destination;
   }

   public GroupingKt__GroupingKt() {
   }
}
