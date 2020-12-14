package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004\u001a,\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0086\u0002¢\u0006\u0002\u0010\u0004\u001a4\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0006H\u0086\u0002¢\u0006\u0002\u0010\u0007\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\bH\u0086\u0002\u001a-\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\tH\u0086\u0002\u001a,\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0003\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\u0004¨\u0006\r"},
   d2 = {"minus", "", "T", "element", "(Ljava/util/Set;Ljava/lang/Object;)Ljava/util/Set;", "elements", "", "(Ljava/util/Set;[Ljava/lang/Object;)Ljava/util/Set;", "", "Lkotlin/sequences/Sequence;", "minusElement", "plus", "plusElement", "kotlin-stdlib"},
   xs = "kotlin/collections/SetsKt"
)
class SetsKt___SetsKt extends SetsKt__SetsKt {
   @NotNull
   public static final <T> Set<T> minus(@NotNull Set<? extends T> $this$minus, T element) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity($this$minus.size()));
      boolean removed = false;
      Iterable $this$filterTo$iv = (Iterable)$this$minus;
      int $i$f$filterTo = false;
      Iterator var6 = $this$filterTo$iv.iterator();

      while(var6.hasNext()) {
         Object element$iv = var6.next();
         int var9 = false;
         boolean var10000;
         if (!removed && Intrinsics.areEqual(element$iv, element)) {
            removed = true;
            var10000 = false;
         } else {
            var10000 = true;
         }

         if (var10000) {
            ((Collection)result).add(element$iv);
         }
      }

      return (Set)((Collection)result);
   }

   @NotNull
   public static final <T> Set<T> minus(@NotNull Set<? extends T> $this$minus, @NotNull T[] elements) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      LinkedHashSet result = new LinkedHashSet((Collection)$this$minus);
      CollectionsKt.removeAll((Collection)result, elements);
      return (Set)result;
   }

   @NotNull
   public static final <T> Set<T> minus(@NotNull Set<? extends T> $this$minus, @NotNull Iterable<? extends T> elements) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      Collection other = CollectionsKt.convertToSetForSetOperationWith(elements, (Iterable)$this$minus);
      if (other.isEmpty()) {
         return CollectionsKt.toSet((Iterable)$this$minus);
      } else if (other instanceof Set) {
         Iterable $this$filterNotTo$iv = (Iterable)$this$minus;
         Collection destination$iv = (Collection)(new LinkedHashSet());
         int $i$f$filterNotTo = false;
         Iterator var6 = $this$filterNotTo$iv.iterator();

         while(var6.hasNext()) {
            Object element$iv = var6.next();
            int var9 = false;
            if (!other.contains(element$iv)) {
               destination$iv.add(element$iv);
            }
         }

         return (Set)destination$iv;
      } else {
         LinkedHashSet result = new LinkedHashSet((Collection)$this$minus);
         result.removeAll(other);
         return (Set)result;
      }
   }

   @NotNull
   public static final <T> Set<T> minus(@NotNull Set<? extends T> $this$minus, @NotNull Sequence<? extends T> elements) {
      Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      LinkedHashSet result = new LinkedHashSet((Collection)$this$minus);
      CollectionsKt.removeAll((Collection)result, elements);
      return (Set)result;
   }

   @InlineOnly
   private static final <T> Set<T> minusElement(@NotNull Set<? extends T> $this$minusElement, T element) {
      int $i$f$minusElement = 0;
      return SetsKt.minus($this$minusElement, element);
   }

   @NotNull
   public static final <T> Set<T> plus(@NotNull Set<? extends T> $this$plus, T element) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity($this$plus.size() + 1));
      result.addAll((Collection)$this$plus);
      result.add(element);
      return (Set)result;
   }

   @NotNull
   public static final <T> Set<T> plus(@NotNull Set<? extends T> $this$plus, @NotNull T[] elements) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity($this$plus.size() + elements.length));
      result.addAll((Collection)$this$plus);
      CollectionsKt.addAll((Collection)result, elements);
      return (Set)result;
   }

   @NotNull
   public static final <T> Set<T> plus(@NotNull Set<? extends T> $this$plus, @NotNull Iterable<? extends T> elements) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      Integer var10000 = CollectionsKt.collectionSizeOrNull(elements);
      int var12;
      if (var10000 != null) {
         Integer var3 = var10000;
         boolean var4 = false;
         boolean var5 = false;
         int it = ((Number)var3).intValue();
         int var7 = false;
         int var10 = $this$plus.size() + it;
         var12 = var10;
      } else {
         var12 = $this$plus.size() * 2;
      }

      int var11 = MapsKt.mapCapacity(var12);
      LinkedHashSet result = new LinkedHashSet(var11);
      result.addAll((Collection)$this$plus);
      CollectionsKt.addAll((Collection)result, elements);
      return (Set)result;
   }

   @NotNull
   public static final <T> Set<T> plus(@NotNull Set<? extends T> $this$plus, @NotNull Sequence<? extends T> elements) {
      Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      LinkedHashSet result = new LinkedHashSet(MapsKt.mapCapacity($this$plus.size() * 2));
      result.addAll((Collection)$this$plus);
      CollectionsKt.addAll((Collection)result, elements);
      return (Set)result;
   }

   @InlineOnly
   private static final <T> Set<T> plusElement(@NotNull Set<? extends T> $this$plusElement, T element) {
      int $i$f$plusElement = 0;
      return SetsKt.plus($this$plusElement, element);
   }

   public SetsKt___SetsKt() {
   }
}
