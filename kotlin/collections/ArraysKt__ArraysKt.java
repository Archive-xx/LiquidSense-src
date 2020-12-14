package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001a!\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003¢\u0006\u0002\u0010\u0016\u001a8\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\b¢\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003¢\u0006\u0002\u0010!¨\u0006\""},
   d2 = {"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"},
   xs = "kotlin/collections/ArraysKt"
)
class ArraysKt__ArraysKt extends ArraysKt__ArraysJVMKt {
   @NotNull
   public static final <T> List<T> flatten(@NotNull T[][] $this$flatten) {
      Intrinsics.checkParameterIsNotNull($this$flatten, "$this$flatten");
      Object[] element = (Object[])$this$flatten;
      int $i$f$sumBy = false;
      int sum$iv = 0;
      Object[] var5 = element;
      int var6 = element.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object element$iv = var5[var7];
         Object[] it = (Object[])element$iv;
         int var10 = false;
         int var14 = it.length;
         sum$iv += var14;
      }

      ArrayList result = new ArrayList(sum$iv);
      Object[][] var17 = $this$flatten;
      int var18 = $this$flatten.length;

      for(int var16 = 0; var16 < var18; ++var16) {
         element = var17[var16];
         CollectionsKt.addAll((Collection)result, element);
      }

      return (List)result;
   }

   @NotNull
   public static final <T, R> Pair<List<T>, List<R>> unzip(@NotNull Pair<? extends T, ? extends R>[] $this$unzip) {
      Intrinsics.checkParameterIsNotNull($this$unzip, "$this$unzip");
      ArrayList listT = new ArrayList($this$unzip.length);
      ArrayList listR = new ArrayList($this$unzip.length);
      Pair[] var5 = $this$unzip;
      int var6 = $this$unzip.length;

      for(int var4 = 0; var4 < var6; ++var4) {
         Pair pair = var5[var4];
         listT.add(pair.getFirst());
         listR.add(pair.getSecond());
      }

      return TuplesKt.to(listT, listR);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final boolean isNullOrEmpty(@Nullable Object[] $this$isNullOrEmpty) {
      int $i$f$isNullOrEmpty = 0;
      boolean var2 = false;
      boolean var10000;
      if ($this$isNullOrEmpty != null) {
         boolean var3 = false;
         if ($this$isNullOrEmpty.length != 0) {
            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <C extends Object[] & R, R> R ifEmpty(C $this$ifEmpty, Function0<? extends R> defaultValue) {
      int $i$f$ifEmpty = 0;
      boolean var4 = false;
      return $this$ifEmpty.length == 0 ? defaultValue.invoke() : $this$ifEmpty;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @JvmName(
      name = "contentDeepEquals"
   )
   public static final <T> boolean contentDeepEquals(@NotNull T[] $this$contentDeepEqualsImpl, @NotNull T[] other) {
      Intrinsics.checkParameterIsNotNull($this$contentDeepEqualsImpl, "$this$contentDeepEqualsImpl");
      Intrinsics.checkParameterIsNotNull(other, "other");
      if ($this$contentDeepEqualsImpl == other) {
         return true;
      } else if ($this$contentDeepEqualsImpl.length != other.length) {
         return false;
      } else {
         int i = 0;

         for(int var3 = $this$contentDeepEqualsImpl.length; i < var3; ++i) {
            Object v1 = $this$contentDeepEqualsImpl[i];
            Object v2 = other[i];
            if (v1 != v2) {
               if (v1 == null || v2 == null) {
                  return false;
               }

               boolean var7;
               if (v1 instanceof Object[] && v2 instanceof Object[]) {
                  Object[] var15 = (Object[])v1;
                  var7 = false;
                  if (!ArraysKt.contentDeepEquals(var15, (Object[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof byte[] && v2 instanceof byte[]) {
                  byte[] var14 = (byte[])v1;
                  var7 = false;
                  if (!Arrays.equals(var14, (byte[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof short[] && v2 instanceof short[]) {
                  short[] var13 = (short[])v1;
                  var7 = false;
                  if (!Arrays.equals(var13, (short[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof int[] && v2 instanceof int[]) {
                  int[] var12 = (int[])v1;
                  var7 = false;
                  if (!Arrays.equals(var12, (int[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof long[] && v2 instanceof long[]) {
                  long[] var11 = (long[])v1;
                  var7 = false;
                  if (!Arrays.equals(var11, (long[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof float[] && v2 instanceof float[]) {
                  float[] var10 = (float[])v1;
                  var7 = false;
                  if (!Arrays.equals(var10, (float[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof double[] && v2 instanceof double[]) {
                  double[] var9 = (double[])v1;
                  var7 = false;
                  if (!Arrays.equals(var9, (double[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof char[] && v2 instanceof char[]) {
                  char[] var8 = (char[])v1;
                  var7 = false;
                  if (!Arrays.equals(var8, (char[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof boolean[] && v2 instanceof boolean[]) {
                  boolean[] var6 = (boolean[])v1;
                  var7 = false;
                  if (!Arrays.equals(var6, (boolean[])v2)) {
                     return false;
                  }
               } else if (v1 instanceof UByteArray && v2 instanceof UByteArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-kdPth3s(((UByteArray)v1).unbox-impl(), ((UByteArray)v2).unbox-impl())) {
                     return false;
                  }
               } else if (v1 instanceof UShortArray && v2 instanceof UShortArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-mazbYpA(((UShortArray)v1).unbox-impl(), ((UShortArray)v2).unbox-impl())) {
                     return false;
                  }
               } else if (v1 instanceof UIntArray && v2 instanceof UIntArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-ctEhBpI(((UIntArray)v1).unbox-impl(), ((UIntArray)v2).unbox-impl())) {
                     return false;
                  }
               } else if (v1 instanceof ULongArray && v2 instanceof ULongArray) {
                  if (!kotlin.collections.unsigned.UArraysKt.contentEquals-us8wMrg(((ULongArray)v1).unbox-impl(), ((ULongArray)v2).unbox-impl())) {
                     return false;
                  }
               } else if (Intrinsics.areEqual(v1, v2) ^ true) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   @SinceKotlin(
      version = "1.3"
   )
   @PublishedApi
   @JvmName(
      name = "contentDeepToString"
   )
   @NotNull
   public static final <T> String contentDeepToString(@NotNull T[] $this$contentDeepToStringImpl) {
      Intrinsics.checkParameterIsNotNull($this$contentDeepToStringImpl, "$this$contentDeepToStringImpl");
      int length = RangesKt.coerceAtMost($this$contentDeepToStringImpl.length, 429496729) * 5 + 2;
      boolean var2 = false;
      StringBuilder var3 = new StringBuilder(length);
      boolean var4 = false;
      boolean var5 = false;
      int var7 = false;
      boolean var10 = false;
      List var11 = (List)(new ArrayList());
      contentDeepToStringInternal$ArraysKt__ArraysKt($this$contentDeepToStringImpl, var3, var11);
      String var10000 = var3.toString();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "StringBuilder(capacity).…builderAction).toString()");
      return var10000;
   }

   private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(@NotNull T[] $this$contentDeepToStringInternal, StringBuilder result, List<Object[]> processed) {
      if (processed.contains($this$contentDeepToStringInternal)) {
         result.append("[...]");
      } else {
         processed.add($this$contentDeepToStringInternal);
         result.append('[');
         int i = 0;

         for(int var4 = $this$contentDeepToStringInternal.length; i < var4; ++i) {
            if (i != 0) {
               result.append(", ");
            }

            Object element = $this$contentDeepToStringInternal[i];
            if (element == null) {
               result.append("null");
            } else if (element instanceof Object[]) {
               contentDeepToStringInternal$ArraysKt__ArraysKt((Object[])element, result, processed);
            } else {
               boolean var8;
               String var10;
               String var10000;
               if (element instanceof byte[]) {
                  byte[] var7 = (byte[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var7);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof short[]) {
                  short[] var11 = (short[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var11);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof int[]) {
                  int[] var12 = (int[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var12);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof long[]) {
                  long[] var13 = (long[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var13);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof float[]) {
                  float[] var14 = (float[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var14);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof double[]) {
                  double[] var15 = (double[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var15);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof char[]) {
                  char[] var16 = (char[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var16);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof boolean[]) {
                  boolean[] var17 = (boolean[])element;
                  var8 = false;
                  var10000 = Arrays.toString(var17);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.toString(this)");
                  var10 = var10000;
                  result.append(var10);
               } else if (element instanceof UByteArray) {
                  result.append(kotlin.collections.unsigned.UArraysKt.contentToString-GBYM_sE(((UByteArray)element).unbox-impl()));
               } else if (element instanceof UShortArray) {
                  result.append(kotlin.collections.unsigned.UArraysKt.contentToString-rL5Bavg(((UShortArray)element).unbox-impl()));
               } else if (element instanceof UIntArray) {
                  result.append(kotlin.collections.unsigned.UArraysKt.contentToString--ajY-9A(((UIntArray)element).unbox-impl()));
               } else if (element instanceof ULongArray) {
                  result.append(kotlin.collections.unsigned.UArraysKt.contentToString-QwZRm1k(((ULongArray)element).unbox-impl()));
               } else {
                  result.append(element.toString());
               }
            }
         }

         result.append(']');
         processed.remove(CollectionsKt.getLastIndex(processed));
      }
   }

   public ArraysKt__ArraysKt() {
   }
}
