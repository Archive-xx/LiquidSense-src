package kotlin.collections;

import java.util.Arrays;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import org.jetbrains.annotations.NotNull;

/** @deprecated */
@Deprecated(
   message = "Provided for binary compatibility",
   level = DeprecationLevel.HIDDEN
)
@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\t\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\t2\u0006\u0010\u0006\u001a\u00020\tH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\f2\u0006\u0010\u0006\u001a\u00020\fH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u0003\u001a\u00020\u0004*\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u000fH\u0087\u0004ø\u0001\u0000¢\u0006\u0004\b\u0010\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0016\u0010\u0017J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0019J\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b \u0010!J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b\"\u0010#J\u0016\u0010\u001c\u001a\u00020\u001d*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b$\u0010%J\u001e\u0010&\u001a\u00020'*\u00020\u00052\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b)\u0010*J\u001e\u0010&\u001a\u00020+*\u00020\t2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b,\u0010-J\u001e\u0010&\u001a\u00020.*\u00020\f2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b/\u00100J\u001e\u0010&\u001a\u000201*\u00020\u000f2\u0006\u0010&\u001a\u00020(H\u0007ø\u0001\u0000¢\u0006\u0004\b2\u00103J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020'05*\u00020\u0005H\u0007ø\u0001\u0000¢\u0006\u0004\b6\u00107J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020+05*\u00020\tH\u0007ø\u0001\u0000¢\u0006\u0004\b8\u00109J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020.05*\u00020\fH\u0007ø\u0001\u0000¢\u0006\u0004\b:\u0010;J\u001c\u00104\u001a\b\u0012\u0004\u0012\u00020105*\u00020\u000fH\u0007ø\u0001\u0000¢\u0006\u0004\b<\u0010=\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006>"},
   d2 = {"Lkotlin/collections/UArraysKt;", "", "()V", "contentEquals", "", "Lkotlin/UByteArray;", "other", "contentEquals-kdPth3s", "([B[B)Z", "Lkotlin/UIntArray;", "contentEquals-ctEhBpI", "([I[I)Z", "Lkotlin/ULongArray;", "contentEquals-us8wMrg", "([J[J)Z", "Lkotlin/UShortArray;", "contentEquals-mazbYpA", "([S[S)Z", "contentHashCode", "", "contentHashCode-GBYM_sE", "([B)I", "contentHashCode--ajY-9A", "([I)I", "contentHashCode-QwZRm1k", "([J)I", "contentHashCode-rL5Bavg", "([S)I", "contentToString", "", "contentToString-GBYM_sE", "([B)Ljava/lang/String;", "contentToString--ajY-9A", "([I)Ljava/lang/String;", "contentToString-QwZRm1k", "([J)Ljava/lang/String;", "contentToString-rL5Bavg", "([S)Ljava/lang/String;", "random", "Lkotlin/UByte;", "Lkotlin/random/Random;", "random-oSF2wD8", "([BLkotlin/random/Random;)B", "Lkotlin/UInt;", "random-2D5oskM", "([ILkotlin/random/Random;)I", "Lkotlin/ULong;", "random-JzugnMA", "([JLkotlin/random/Random;)J", "Lkotlin/UShort;", "random-s5X_as8", "([SLkotlin/random/Random;)S", "toTypedArray", "", "toTypedArray-GBYM_sE", "([B)[Lkotlin/UByte;", "toTypedArray--ajY-9A", "([I)[Lkotlin/UInt;", "toTypedArray-QwZRm1k", "([J)[Lkotlin/ULong;", "toTypedArray-rL5Bavg", "([S)[Lkotlin/UShort;", "kotlin-stdlib"}
)
public final class UArraysKt {
   public static final UArraysKt INSTANCE;

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final int random_2D5oskM/* $FF was: random-2D5oskM*/(@NotNull int[] $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");
      if (UIntArray.isEmpty-impl($this$random)) {
         throw (Throwable)(new NoSuchElementException("Array is empty."));
      } else {
         return UIntArray.get-impl($this$random, random.nextInt(UIntArray.getSize-impl($this$random)));
      }
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final long random_JzugnMA/* $FF was: random-JzugnMA*/(@NotNull long[] $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");
      if (ULongArray.isEmpty-impl($this$random)) {
         throw (Throwable)(new NoSuchElementException("Array is empty."));
      } else {
         return ULongArray.get-impl($this$random, random.nextInt(ULongArray.getSize-impl($this$random)));
      }
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final byte random_oSF2wD8/* $FF was: random-oSF2wD8*/(@NotNull byte[] $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");
      if (UByteArray.isEmpty-impl($this$random)) {
         throw (Throwable)(new NoSuchElementException("Array is empty."));
      } else {
         return UByteArray.get-impl($this$random, random.nextInt(UByteArray.getSize-impl($this$random)));
      }
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final short random_s5X_as8/* $FF was: random-s5X_as8*/(@NotNull short[] $this$random, @NotNull Random random) {
      Intrinsics.checkParameterIsNotNull($this$random, "$this$random");
      Intrinsics.checkParameterIsNotNull(random, "random");
      if (UShortArray.isEmpty-impl($this$random)) {
         throw (Throwable)(new NoSuchElementException("Array is empty."));
      } else {
         return UShortArray.get-impl($this$random, random.nextInt(UShortArray.getSize-impl($this$random)));
      }
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final boolean contentEquals_ctEhBpI/* $FF was: contentEquals-ctEhBpI*/(@NotNull int[] $this$contentEquals, @NotNull int[] other) {
      Intrinsics.checkParameterIsNotNull($this$contentEquals, "$this$contentEquals");
      Intrinsics.checkParameterIsNotNull(other, "other");
      boolean var4 = false;
      return Arrays.equals($this$contentEquals, other);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final boolean contentEquals_us8wMrg/* $FF was: contentEquals-us8wMrg*/(@NotNull long[] $this$contentEquals, @NotNull long[] other) {
      Intrinsics.checkParameterIsNotNull($this$contentEquals, "$this$contentEquals");
      Intrinsics.checkParameterIsNotNull(other, "other");
      boolean var4 = false;
      return Arrays.equals($this$contentEquals, other);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final boolean contentEquals_kdPth3s/* $FF was: contentEquals-kdPth3s*/(@NotNull byte[] $this$contentEquals, @NotNull byte[] other) {
      Intrinsics.checkParameterIsNotNull($this$contentEquals, "$this$contentEquals");
      Intrinsics.checkParameterIsNotNull(other, "other");
      boolean var4 = false;
      return Arrays.equals($this$contentEquals, other);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final boolean contentEquals_mazbYpA/* $FF was: contentEquals-mazbYpA*/(@NotNull short[] $this$contentEquals, @NotNull short[] other) {
      Intrinsics.checkParameterIsNotNull($this$contentEquals, "$this$contentEquals");
      Intrinsics.checkParameterIsNotNull(other, "other");
      boolean var4 = false;
      return Arrays.equals($this$contentEquals, other);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final int contentHashCode__ajY_9A/* $FF was: contentHashCode--ajY-9A*/(@NotNull int[] $this$contentHashCode) {
      Intrinsics.checkParameterIsNotNull($this$contentHashCode, "$this$contentHashCode");
      boolean var2 = false;
      return Arrays.hashCode($this$contentHashCode);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final int contentHashCode_QwZRm1k/* $FF was: contentHashCode-QwZRm1k*/(@NotNull long[] $this$contentHashCode) {
      Intrinsics.checkParameterIsNotNull($this$contentHashCode, "$this$contentHashCode");
      boolean var2 = false;
      return Arrays.hashCode($this$contentHashCode);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final int contentHashCode_GBYM_sE/* $FF was: contentHashCode-GBYM_sE*/(@NotNull byte[] $this$contentHashCode) {
      Intrinsics.checkParameterIsNotNull($this$contentHashCode, "$this$contentHashCode");
      boolean var2 = false;
      return Arrays.hashCode($this$contentHashCode);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   public static final int contentHashCode_rL5Bavg/* $FF was: contentHashCode-rL5Bavg*/(@NotNull short[] $this$contentHashCode) {
      Intrinsics.checkParameterIsNotNull($this$contentHashCode, "$this$contentHashCode");
      boolean var2 = false;
      return Arrays.hashCode($this$contentHashCode);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String contentToString__ajY_9A/* $FF was: contentToString--ajY-9A*/(@NotNull int[] $this$contentToString) {
      Intrinsics.checkParameterIsNotNull($this$contentToString, "$this$contentToString");
      return CollectionsKt.joinToString$default(UIntArray.box-impl($this$contentToString), (CharSequence)", ", (CharSequence)"[", (CharSequence)"]", 0, (CharSequence)null, (Function1)null, 56, (Object)null);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String contentToString_QwZRm1k/* $FF was: contentToString-QwZRm1k*/(@NotNull long[] $this$contentToString) {
      Intrinsics.checkParameterIsNotNull($this$contentToString, "$this$contentToString");
      return CollectionsKt.joinToString$default(ULongArray.box-impl($this$contentToString), (CharSequence)", ", (CharSequence)"[", (CharSequence)"]", 0, (CharSequence)null, (Function1)null, 56, (Object)null);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String contentToString_GBYM_sE/* $FF was: contentToString-GBYM_sE*/(@NotNull byte[] $this$contentToString) {
      Intrinsics.checkParameterIsNotNull($this$contentToString, "$this$contentToString");
      return CollectionsKt.joinToString$default(UByteArray.box-impl($this$contentToString), (CharSequence)", ", (CharSequence)"[", (CharSequence)"]", 0, (CharSequence)null, (Function1)null, 56, (Object)null);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final String contentToString_rL5Bavg/* $FF was: contentToString-rL5Bavg*/(@NotNull short[] $this$contentToString) {
      Intrinsics.checkParameterIsNotNull($this$contentToString, "$this$contentToString");
      return CollectionsKt.joinToString$default(UShortArray.box-impl($this$contentToString), (CharSequence)", ", (CharSequence)"[", (CharSequence)"]", 0, (CharSequence)null, (Function1)null, 56, (Object)null);
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UInt[] toTypedArray__ajY_9A/* $FF was: toTypedArray--ajY-9A*/(@NotNull int[] $this$toTypedArray) {
      Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
      int var1 = UIntArray.getSize-impl($this$toTypedArray);
      UInt[] var2 = new UInt[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         int var5 = false;
         UInt var8 = UInt.box-impl(UIntArray.get-impl($this$toTypedArray, var3));
         var2[var3] = var8;
      }

      return var2;
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final ULong[] toTypedArray_QwZRm1k/* $FF was: toTypedArray-QwZRm1k*/(@NotNull long[] $this$toTypedArray) {
      Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
      int var1 = ULongArray.getSize-impl($this$toTypedArray);
      ULong[] var2 = new ULong[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         int var5 = false;
         ULong var8 = ULong.box-impl(ULongArray.get-impl($this$toTypedArray, var3));
         var2[var3] = var8;
      }

      return var2;
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UByte[] toTypedArray_GBYM_sE/* $FF was: toTypedArray-GBYM_sE*/(@NotNull byte[] $this$toTypedArray) {
      Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
      int var1 = UByteArray.getSize-impl($this$toTypedArray);
      UByte[] var2 = new UByte[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         int var5 = false;
         UByte var8 = UByte.box-impl(UByteArray.get-impl($this$toTypedArray, var3));
         var2[var3] = var8;
      }

      return var2;
   }

   @JvmStatic
   @ExperimentalUnsignedTypes
   @NotNull
   public static final UShort[] toTypedArray_rL5Bavg/* $FF was: toTypedArray-rL5Bavg*/(@NotNull short[] $this$toTypedArray) {
      Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
      int var1 = UShortArray.getSize-impl($this$toTypedArray);
      UShort[] var2 = new UShort[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         int var5 = false;
         UShort var8 = UShort.box-impl(UShortArray.get-impl($this$toTypedArray, var3));
         var2[var3] = var8;
      }

      return var2;
   }

   private UArraysKt() {
   }

   static {
      UArraysKt var0 = new UArraysKt();
      INSTANCE = var0;
   }
}
