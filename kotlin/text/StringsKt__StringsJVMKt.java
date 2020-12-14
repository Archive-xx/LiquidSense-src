package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000~\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\n\u0010\u0017\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010'\u001a\u00020(H\u0087\b\u001a\n\u0010)\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u00100\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00105\u001a:\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00106\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b¢\u0006\u0002\u00107\u001a\r\u00108\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u00109\u001a\u00020#*\u00020(\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010@\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\u0011H\u0087\b\u001a4\u0010B\u001a\u00020#*\u00020(2\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020(2\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010B\u001a\u00020#*\u00020\u00022\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010E\u001a\u00020\u0002*\u00020(2\u0006\u0010F\u001a\u00020\u0011\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00020N*\u00020(2\u0006\u0010O\u001a\u00020P2\b\b\u0002\u0010Q\u001a\u00020\u0011\u001a\u001c\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010U\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010V\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010V\u001a\u00020\u0014*\u00020\u00022\u0006\u0010W\u001a\u00020\u00142\b\b\u0002\u0010X\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010V\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010Y\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010Y\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010Z\u001a\u00020P*\u00020\u00022\b\b\u0002\u0010[\u001a\u00020\u0011H\u0087\b\u001a\r\u0010\\\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\u0000\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006]"},
   d2 = {"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "charSequence", "", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "nativeIndexOf", "ch", "", "fromIndex", "str", "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
   @InlineOnly
   private static final int nativeIndexOf(@NotNull String $this$nativeIndexOf, char ch, int fromIndex) {
      return $this$nativeIndexOf.indexOf(ch, fromIndex);
   }

   @InlineOnly
   private static final int nativeIndexOf(@NotNull String $this$nativeIndexOf, String str, int fromIndex) {
      return $this$nativeIndexOf.indexOf(str, fromIndex);
   }

   @InlineOnly
   private static final int nativeLastIndexOf(@NotNull String $this$nativeLastIndexOf, char ch, int fromIndex) {
      return $this$nativeLastIndexOf.lastIndexOf(ch, fromIndex);
   }

   @InlineOnly
   private static final int nativeLastIndexOf(@NotNull String $this$nativeLastIndexOf, String str, int fromIndex) {
      return $this$nativeLastIndexOf.lastIndexOf(str, fromIndex);
   }

   public static final boolean equals(@Nullable String $this$equals, @Nullable String other, boolean ignoreCase) {
      if ($this$equals == null) {
         return other == null;
      } else {
         return !ignoreCase ? $this$equals.equals(other) : $this$equals.equalsIgnoreCase(other);
      }
   }

   // $FF: synthetic method
   public static boolean equals$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.equals(var0, var1, var2);
   }

   @NotNull
   public static final String replace(@NotNull String $this$replace, char oldChar, char newChar, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$replace, "$this$replace");
      if (!ignoreCase) {
         String var7 = $this$replace.replace(oldChar, newChar);
         Intrinsics.checkExpressionValueIsNotNull(var7, "(this as java.lang.Strin…replace(oldChar, newChar)");
         return var7;
      } else {
         CharSequence var10000 = (CharSequence)$this$replace;
         char[] var10002 = new char[]{oldChar};
         byte var4 = 0;
         char[] var5 = var10002;
         return SequencesKt.joinToString$default(StringsKt.splitToSequence$default(var10000, var5, ignoreCase, var4, 4, (Object)null), (CharSequence)String.valueOf(newChar), (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
      }
   }

   // $FF: synthetic method
   public static String replace$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replace(@NotNull String $this$replace, @NotNull String oldValue, @NotNull String newValue, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$replace, "$this$replace");
      Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
      Intrinsics.checkParameterIsNotNull(newValue, "newValue");
      CharSequence var10000 = (CharSequence)$this$replace;
      String[] var10002 = new String[]{oldValue};
      byte var4 = 0;
      String[] var5 = var10002;
      return SequencesKt.joinToString$default(StringsKt.splitToSequence$default(var10000, var5, ignoreCase, var4, 4, (Object)null), (CharSequence)newValue, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
   }

   // $FF: synthetic method
   public static String replace$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replace(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceFirst(@NotNull String $this$replaceFirst, char oldChar, char newChar, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$replaceFirst, "$this$replaceFirst");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceFirst, oldChar, 0, ignoreCase, 2, (Object)null);
      String var10000;
      if (index < 0) {
         var10000 = $this$replaceFirst;
      } else {
         int var6 = index + 1;
         CharSequence var7 = (CharSequence)String.valueOf(newChar);
         boolean var8 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceFirst, index, var6, var7).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceFirst$default(String var0, char var1, char var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   @NotNull
   public static final String replaceFirst(@NotNull String $this$replaceFirst, @NotNull String oldValue, @NotNull String newValue, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$replaceFirst, "$this$replaceFirst");
      Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
      Intrinsics.checkParameterIsNotNull(newValue, "newValue");
      int index = StringsKt.indexOf$default((CharSequence)$this$replaceFirst, oldValue, 0, ignoreCase, 2, (Object)null);
      String var10000;
      if (index < 0) {
         var10000 = $this$replaceFirst;
      } else {
         int var6 = index + oldValue.length();
         boolean var7 = false;
         var10000 = StringsKt.replaceRange((CharSequence)$this$replaceFirst, index, var6, (CharSequence)newValue).toString();
      }

      return var10000;
   }

   // $FF: synthetic method
   public static String replaceFirst$default(String var0, String var1, String var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.replaceFirst(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final String toUpperCase(@NotNull String $this$toUpperCase) {
      String var10000 = $this$toUpperCase.toUpperCase();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toUpperCase()");
      return var10000;
   }

   @InlineOnly
   private static final String toLowerCase(@NotNull String $this$toLowerCase) {
      String var10000 = $this$toLowerCase.toLowerCase();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String concatToString(@NotNull char[] $this$concatToString) {
      Intrinsics.checkParameterIsNotNull($this$concatToString, "$this$concatToString");
      boolean var1 = false;
      return new String($this$concatToString);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String concatToString(@NotNull char[] $this$concatToString, int startIndex, int endIndex) {
      Intrinsics.checkParameterIsNotNull($this$concatToString, "$this$concatToString");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$concatToString.length);
      int var3 = endIndex - startIndex;
      boolean var4 = false;
      return new String($this$concatToString, startIndex, var3);
   }

   // $FF: synthetic method
   public static String concatToString$default(char[] var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length;
      }

      return StringsKt.concatToString(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final char[] toCharArray(@NotNull String $this$toCharArray, int startIndex, int endIndex) {
      Intrinsics.checkParameterIsNotNull($this$toCharArray, "$this$toCharArray");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$toCharArray.length());
      char[] var4 = new char[endIndex - startIndex];
      byte var5 = 0;
      boolean var6 = false;
      $this$toCharArray.getChars(startIndex, endIndex, var4, var5);
      return var4;
   }

   // $FF: synthetic method
   public static char[] toCharArray$default(String var0, int var1, int var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = 0;
      }

      if ((var3 & 2) != 0) {
         var2 = var0.length();
      }

      return StringsKt.toCharArray(var0, var1, var2);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String decodeToString(@NotNull byte[] $this$decodeToString) {
      Intrinsics.checkParameterIsNotNull($this$decodeToString, "$this$decodeToString");
      boolean var1 = false;
      return new String($this$decodeToString, Charsets.UTF_8);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final String decodeToString(@NotNull byte[] $this$decodeToString, int startIndex, int endIndex, boolean throwOnInvalidSequence) {
      Intrinsics.checkParameterIsNotNull($this$decodeToString, "$this$decodeToString");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$decodeToString.length);
      if (!throwOnInvalidSequence) {
         int var6 = endIndex - startIndex;
         boolean var5 = false;
         return new String($this$decodeToString, startIndex, var6, Charsets.UTF_8);
      } else {
         CharsetDecoder decoder = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
         String var10000 = decoder.decode(ByteBuffer.wrap($this$decodeToString, startIndex, endIndex - startIndex)).toString();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "decoder.decode(ByteBuffe…- startIndex)).toString()");
         return var10000;
      }
   }

   // $FF: synthetic method
   public static String decodeToString$default(byte[] var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length;
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.decodeToString(var0, var1, var2, var3);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final byte[] encodeToByteArray(@NotNull String $this$encodeToByteArray) {
      Intrinsics.checkParameterIsNotNull($this$encodeToByteArray, "$this$encodeToByteArray");
      Charset var2 = Charsets.UTF_8;
      boolean var3 = false;
      byte[] var10000 = $this$encodeToByteArray.getBytes(var2);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @NotNull
   public static final byte[] encodeToByteArray(@NotNull String $this$encodeToByteArray, int startIndex, int endIndex, boolean throwOnInvalidSequence) {
      Intrinsics.checkParameterIsNotNull($this$encodeToByteArray, "$this$encodeToByteArray");
      AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(startIndex, endIndex, $this$encodeToByteArray.length());
      byte[] var15;
      if (!throwOnInvalidSequence) {
         boolean var12 = false;
         String var16 = $this$encodeToByteArray.substring(startIndex, endIndex);
         Intrinsics.checkExpressionValueIsNotNull(var16, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         String var11 = var16;
         Charset var13 = Charsets.UTF_8;
         boolean var14 = false;
         if (var11 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         } else {
            var15 = var11.getBytes(var13);
            Intrinsics.checkExpressionValueIsNotNull(var15, "(this as java.lang.String).getBytes(charset)");
            return var15;
         }
      } else {
         CharsetEncoder encoder = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
         ByteBuffer byteBuffer = encoder.encode(CharBuffer.wrap((CharSequence)$this$encodeToByteArray, startIndex, endIndex));
         if (byteBuffer.hasArray() && byteBuffer.arrayOffset() == 0) {
            int var10000 = byteBuffer.remaining();
            byte[] var10001 = byteBuffer.array();
            if (var10001 == null) {
               Intrinsics.throwNpe();
            }

            if (var10000 == var10001.length) {
               var15 = byteBuffer.array();
               Intrinsics.checkExpressionValueIsNotNull(var15, "byteBuffer.array()");
               return var15;
            }
         }

         byte[] var6 = new byte[byteBuffer.remaining()];
         boolean var7 = false;
         boolean var8 = false;
         int var10 = false;
         byteBuffer.get(var6);
         var15 = var6;
         return var15;
      }
   }

   // $FF: synthetic method
   public static byte[] encodeToByteArray$default(String var0, int var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 1) != 0) {
         var1 = 0;
      }

      if ((var4 & 2) != 0) {
         var2 = var0.length();
      }

      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.encodeToByteArray(var0, var1, var2, var3);
   }

   @InlineOnly
   private static final char[] toCharArray(@NotNull String $this$toCharArray) {
      char[] var10000 = $this$toCharArray.toCharArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toCharArray()");
      return var10000;
   }

   @InlineOnly
   private static final char[] toCharArray(@NotNull String $this$toCharArray, char[] destination, int destinationOffset, int startIndex, int endIndex) {
      $this$toCharArray.getChars(startIndex, endIndex, destination, destinationOffset);
      return destination;
   }

   // $FF: synthetic method
   static char[] toCharArray$default(String $this$toCharArray, char[] destination, int destinationOffset, int startIndex, int endIndex, int var5, Object var6) {
      if ((var5 & 2) != 0) {
         destinationOffset = 0;
      }

      if ((var5 & 4) != 0) {
         startIndex = 0;
      }

      if ((var5 & 8) != 0) {
         endIndex = $this$toCharArray.length();
      }

      int $i$f$toCharArray = false;
      if ($this$toCharArray == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         $this$toCharArray.getChars(startIndex, endIndex, destination, destinationOffset);
         return destination;
      }
   }

   @InlineOnly
   private static final String format(@NotNull String $this$format, Object... args) {
      int $i$f$format = 0;
      String var10000 = String.format($this$format, Arrays.copyOf(args, args.length));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.String.format(this, *args)");
      return var10000;
   }

   @InlineOnly
   private static final String format(@NotNull StringCompanionObject $this$format, String format, Object... args) {
      int $i$f$format = 0;
      String var10000 = String.format(format, Arrays.copyOf(args, args.length));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.String.format(format, *args)");
      return var10000;
   }

   @InlineOnly
   private static final String format(@NotNull String $this$format, Locale locale, Object... args) {
      int $i$f$format = 0;
      String var10000 = String.format(locale, $this$format, Arrays.copyOf(args, args.length));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.String.format(locale, this, *args)");
      return var10000;
   }

   @InlineOnly
   private static final String format(@NotNull StringCompanionObject $this$format, Locale locale, String format, Object... args) {
      int $i$f$format = 0;
      String var10000 = String.format(locale, format, Arrays.copyOf(args, args.length));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.String.format(locale, format, *args)");
      return var10000;
   }

   @NotNull
   public static final List<String> split(@NotNull CharSequence $this$split, @NotNull Pattern regex, int limit) {
      Intrinsics.checkParameterIsNotNull($this$split, "$this$split");
      Intrinsics.checkParameterIsNotNull(regex, "regex");
      boolean var3 = limit >= 0;
      boolean var4 = false;
      boolean var5 = false;
      if (!var3) {
         int var6 = false;
         String var7 = "Limit must be non-negative, but was " + limit + '.';
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      } else {
         String[] var10000 = regex.split($this$split, limit == 0 ? -1 : limit);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "regex.split(this, if (limit == 0) -1 else limit)");
         return ArraysKt.asList(var10000);
      }
   }

   // $FF: synthetic method
   public static List split$default(CharSequence var0, Pattern var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return StringsKt.split(var0, var1, var2);
   }

   @InlineOnly
   private static final String substring(@NotNull String $this$substring, int startIndex) {
      String var10000 = $this$substring.substring(startIndex);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
      return var10000;
   }

   @InlineOnly
   private static final String substring(@NotNull String $this$substring, int startIndex, int endIndex) {
      String var10000 = $this$substring.substring(startIndex, endIndex);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
      return var10000;
   }

   public static final boolean startsWith(@NotNull String $this$startsWith, @NotNull String prefix, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$startsWith, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      return !ignoreCase ? $this$startsWith.startsWith(prefix) : StringsKt.regionMatches($this$startsWith, 0, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.startsWith(var0, var1, var2);
   }

   public static final boolean startsWith(@NotNull String $this$startsWith, @NotNull String prefix, int startIndex, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$startsWith, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      return !ignoreCase ? $this$startsWith.startsWith(prefix, startIndex) : StringsKt.regionMatches($this$startsWith, startIndex, prefix, 0, prefix.length(), ignoreCase);
   }

   // $FF: synthetic method
   public static boolean startsWith$default(String var0, String var1, int var2, boolean var3, int var4, Object var5) {
      if ((var4 & 4) != 0) {
         var3 = false;
      }

      return StringsKt.startsWith(var0, var1, var2, var3);
   }

   public static final boolean endsWith(@NotNull String $this$endsWith, @NotNull String suffix, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$endsWith, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(suffix, "suffix");
      return !ignoreCase ? $this$endsWith.endsWith(suffix) : StringsKt.regionMatches($this$endsWith, $this$endsWith.length() - suffix.length(), suffix, 0, suffix.length(), true);
   }

   // $FF: synthetic method
   public static boolean endsWith$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.endsWith(var0, var1, var2);
   }

   @InlineOnly
   private static final String String(byte[] bytes, int offset, int length, Charset charset) {
      int $i$f$String = 0;
      return new String(bytes, offset, length, charset);
   }

   @InlineOnly
   private static final String String(byte[] bytes, Charset charset) {
      int $i$f$String = 0;
      return new String(bytes, charset);
   }

   @InlineOnly
   private static final String String(byte[] bytes, int offset, int length) {
      int $i$f$String = 0;
      return new String(bytes, offset, length, Charsets.UTF_8);
   }

   @InlineOnly
   private static final String String(byte[] bytes) {
      int $i$f$String = 0;
      return new String(bytes, Charsets.UTF_8);
   }

   @InlineOnly
   private static final String String(char[] chars) {
      int $i$f$String = 0;
      return new String(chars);
   }

   @InlineOnly
   private static final String String(char[] chars, int offset, int length) {
      int $i$f$String = 0;
      return new String(chars, offset, length);
   }

   @InlineOnly
   private static final String String(int[] codePoints, int offset, int length) {
      int $i$f$String = 0;
      return new String(codePoints, offset, length);
   }

   @InlineOnly
   private static final String String(StringBuffer stringBuffer) {
      int $i$f$String = 0;
      return new String(stringBuffer);
   }

   @InlineOnly
   private static final String String(StringBuilder stringBuilder) {
      int $i$f$String = 0;
      return new String(stringBuilder);
   }

   @InlineOnly
   private static final int codePointAt(@NotNull String $this$codePointAt, int index) {
      return $this$codePointAt.codePointAt(index);
   }

   @InlineOnly
   private static final int codePointBefore(@NotNull String $this$codePointBefore, int index) {
      return $this$codePointBefore.codePointBefore(index);
   }

   @InlineOnly
   private static final int codePointCount(@NotNull String $this$codePointCount, int beginIndex, int endIndex) {
      return $this$codePointCount.codePointCount(beginIndex, endIndex);
   }

   public static final int compareTo(@NotNull String $this$compareTo, @NotNull String other, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$compareTo, "$this$compareTo");
      Intrinsics.checkParameterIsNotNull(other, "other");
      return ignoreCase ? $this$compareTo.compareToIgnoreCase(other) : $this$compareTo.compareTo(other);
   }

   // $FF: synthetic method
   public static int compareTo$default(String var0, String var1, boolean var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = false;
      }

      return StringsKt.compareTo(var0, var1, var2);
   }

   @InlineOnly
   private static final boolean contentEquals(@NotNull String $this$contentEquals, CharSequence charSequence) {
      return $this$contentEquals.contentEquals(charSequence);
   }

   @InlineOnly
   private static final boolean contentEquals(@NotNull String $this$contentEquals, StringBuffer stringBuilder) {
      return $this$contentEquals.contentEquals(stringBuilder);
   }

   @InlineOnly
   private static final String intern(@NotNull String $this$intern) {
      String var10000 = $this$intern.intern();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).intern()");
      return var10000;
   }

   public static final boolean isBlank(@NotNull CharSequence $this$isBlank) {
      Intrinsics.checkParameterIsNotNull($this$isBlank, "$this$isBlank");
      boolean var10000;
      if ($this$isBlank.length() != 0) {
         Iterable $this$all$iv = (Iterable)StringsKt.getIndices($this$isBlank);
         int $i$f$all = false;
         if ($this$all$iv instanceof Collection && ((Collection)$this$all$iv).isEmpty()) {
            var10000 = true;
         } else {
            Iterator var3 = $this$all$iv.iterator();

            while(true) {
               if (!var3.hasNext()) {
                  var10000 = true;
                  break;
               }

               int element$iv = ((IntIterator)var3).nextInt();
               int var6 = false;
               if (!CharsKt.isWhitespace($this$isBlank.charAt(element$iv))) {
                  var10000 = false;
                  break;
               }
            }
         }

         if (!var10000) {
            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }

   @InlineOnly
   private static final int offsetByCodePoints(@NotNull String $this$offsetByCodePoints, int index, int codePointOffset) {
      return $this$offsetByCodePoints.offsetByCodePoints(index, codePointOffset);
   }

   public static final boolean regionMatches(@NotNull CharSequence $this$regionMatches, int thisOffset, @NotNull CharSequence other, int otherOffset, int length, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$regionMatches, "$this$regionMatches");
      Intrinsics.checkParameterIsNotNull(other, "other");
      return $this$regionMatches instanceof String && other instanceof String ? StringsKt.regionMatches((String)$this$regionMatches, thisOffset, (String)other, otherOffset, length, ignoreCase) : StringsKt.regionMatchesImpl($this$regionMatches, thisOffset, other, otherOffset, length, ignoreCase);
   }

   // $FF: synthetic method
   public static boolean regionMatches$default(CharSequence var0, int var1, CharSequence var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   public static final boolean regionMatches(@NotNull String $this$regionMatches, int thisOffset, @NotNull String other, int otherOffset, int length, boolean ignoreCase) {
      Intrinsics.checkParameterIsNotNull($this$regionMatches, "$this$regionMatches");
      Intrinsics.checkParameterIsNotNull(other, "other");
      return !ignoreCase ? $this$regionMatches.regionMatches(thisOffset, other, otherOffset, length) : $this$regionMatches.regionMatches(ignoreCase, thisOffset, other, otherOffset, length);
   }

   // $FF: synthetic method
   public static boolean regionMatches$default(String var0, int var1, String var2, int var3, int var4, boolean var5, int var6, Object var7) {
      if ((var6 & 16) != 0) {
         var5 = false;
      }

      return StringsKt.regionMatches(var0, var1, var2, var3, var4, var5);
   }

   @InlineOnly
   private static final String toLowerCase(@NotNull String $this$toLowerCase, Locale locale) {
      String var10000 = $this$toLowerCase.toLowerCase(locale);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase(locale)");
      return var10000;
   }

   @InlineOnly
   private static final String toUpperCase(@NotNull String $this$toUpperCase, Locale locale) {
      String var10000 = $this$toUpperCase.toUpperCase(locale);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toUpperCase(locale)");
      return var10000;
   }

   @InlineOnly
   private static final byte[] toByteArray(@NotNull String $this$toByteArray, Charset charset) {
      byte[] var10000 = $this$toByteArray.getBytes(charset);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
      return var10000;
   }

   // $FF: synthetic method
   static byte[] toByteArray$default(String $this$toByteArray, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$toByteArray = false;
      if ($this$toByteArray == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         byte[] var10000 = $this$toByteArray.getBytes(charset);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
         return var10000;
      }
   }

   @InlineOnly
   private static final Pattern toPattern(@NotNull String $this$toPattern, int flags) {
      int $i$f$toPattern = 0;
      Pattern var10000 = Pattern.compile($this$toPattern, flags);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.regex.Pattern.compile(this, flags)");
      return var10000;
   }

   // $FF: synthetic method
   static Pattern toPattern$default(String $this$toPattern, int flags, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         flags = 0;
      }

      int $i$f$toPattern = false;
      Pattern var10000 = Pattern.compile($this$toPattern, flags);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.regex.Pattern.compile(this, flags)");
      return var10000;
   }

   @NotNull
   public static final String capitalize(@NotNull String $this$capitalize) {
      Intrinsics.checkParameterIsNotNull($this$capitalize, "$this$capitalize");
      CharSequence var1 = (CharSequence)$this$capitalize;
      boolean var2 = false;
      String var10000;
      if (var1.length() > 0) {
         char var7 = $this$capitalize.charAt(0);
         var2 = false;
         if (Character.isLowerCase(var7)) {
            StringBuilder var10 = new StringBuilder();
            byte var8 = 0;
            byte var3 = 1;
            StringBuilder var5 = var10;
            boolean var4 = false;
            var10000 = $this$capitalize.substring(var8, var3);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String var6 = var10000;
            var2 = false;
            if (var6 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = var6.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toUpperCase()");
            var6 = var10000;
            var10 = var5.append(var6);
            var8 = 1;
            var5 = var10;
            boolean var9 = false;
            var10000 = $this$capitalize.substring(var8);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            var6 = var10000;
            var10000 = var5.append(var6).toString();
            return var10000;
         }
      }

      var10000 = $this$capitalize;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @LowPriorityInOverloadResolution
   @NotNull
   public static final String capitalize(@NotNull String $this$capitalize, @NotNull Locale locale) {
      Intrinsics.checkParameterIsNotNull($this$capitalize, "$this$capitalize");
      Intrinsics.checkParameterIsNotNull(locale, "locale");
      CharSequence var2 = (CharSequence)$this$capitalize;
      boolean var3 = false;
      if (var2.length() > 0) {
         char firstChar = $this$capitalize.charAt(0);
         boolean var4 = false;
         if (Character.isLowerCase(firstChar)) {
            var3 = false;
            StringBuilder var17 = new StringBuilder();
            boolean var5 = false;
            boolean var6 = false;
            int var8 = false;
            boolean var10 = false;
            char titleChar = Character.toTitleCase(firstChar);
            var10 = false;
            char var13 = Character.toUpperCase(firstChar);
            String var10000;
            byte var18;
            String var19;
            boolean var20;
            if (titleChar != var13) {
               var17.append(titleChar);
            } else {
               var18 = 0;
               byte var14 = 1;
               boolean var15 = false;
               var10000 = $this$capitalize.substring(var18, var14);
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
               var19 = var10000;
               var20 = false;
               if (var19 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var10000 = var19.toUpperCase(locale);
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toUpperCase(locale)");
               var19 = var10000;
               var17.append(var19);
            }

            var18 = 1;
            var20 = false;
            var10000 = $this$capitalize.substring(var18);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            var19 = var10000;
            var17.append(var19);
            var10000 = var17.toString();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "StringBuilder().apply(builderAction).toString()");
            return var10000;
         }
      }

      return $this$capitalize;
   }

   @NotNull
   public static final String decapitalize(@NotNull String $this$decapitalize) {
      Intrinsics.checkParameterIsNotNull($this$decapitalize, "$this$decapitalize");
      CharSequence var1 = (CharSequence)$this$decapitalize;
      boolean var2 = false;
      String var10000;
      if (var1.length() > 0) {
         char var7 = $this$decapitalize.charAt(0);
         var2 = false;
         if (Character.isUpperCase(var7)) {
            StringBuilder var10 = new StringBuilder();
            byte var8 = 0;
            byte var3 = 1;
            StringBuilder var5 = var10;
            boolean var4 = false;
            var10000 = $this$decapitalize.substring(var8, var3);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String var6 = var10000;
            var2 = false;
            if (var6 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = var6.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
            var6 = var10000;
            var10 = var5.append(var6);
            var8 = 1;
            var5 = var10;
            boolean var9 = false;
            var10000 = $this$decapitalize.substring(var8);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            var6 = var10000;
            var10000 = var5.append(var6).toString();
            return var10000;
         }
      }

      var10000 = $this$decapitalize;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @ExperimentalStdlibApi
   @LowPriorityInOverloadResolution
   @NotNull
   public static final String decapitalize(@NotNull String $this$decapitalize, @NotNull Locale locale) {
      Intrinsics.checkParameterIsNotNull($this$decapitalize, "$this$decapitalize");
      Intrinsics.checkParameterIsNotNull(locale, "locale");
      CharSequence var2 = (CharSequence)$this$decapitalize;
      boolean var3 = false;
      String var10000;
      if (var2.length() > 0) {
         char var8 = $this$decapitalize.charAt(0);
         var3 = false;
         if (!Character.isLowerCase(var8)) {
            StringBuilder var11 = new StringBuilder();
            byte var9 = 0;
            byte var4 = 1;
            StringBuilder var6 = var11;
            boolean var5 = false;
            var10000 = $this$decapitalize.substring(var9, var4);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            String var7 = var10000;
            var3 = false;
            if (var7 == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = var7.toLowerCase(locale);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase(locale)");
            var7 = var10000;
            var11 = var6.append(var7);
            var9 = 1;
            var6 = var11;
            boolean var10 = false;
            var10000 = $this$decapitalize.substring(var9);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            var7 = var10000;
            var10000 = var6.append(var7).toString();
            return var10000;
         }
      }

      var10000 = $this$decapitalize;
      return var10000;
   }

   @NotNull
   public static final String repeat(@NotNull CharSequence $this$repeat, int n) {
      Intrinsics.checkParameterIsNotNull($this$repeat, "$this$repeat");
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var20 = false;
         String var19 = "Count 'n' must be non-negative, but was " + n + '.';
         throw (Throwable)(new IllegalArgumentException(var19.toString()));
      } else {
         String var10000;
         switch(n) {
         case 0:
            var10000 = "";
            break;
         case 1:
            var10000 = $this$repeat.toString();
            break;
         default:
            switch($this$repeat.length()) {
            case 0:
               var10000 = "";
               break;
            case 1:
               char var15 = $this$repeat.charAt(0);
               var3 = false;
               var4 = false;
               char var5 = var15;
               int var6 = false;
               int var7 = n;
               char[] var8 = new char[n];

               for(int var9 = 0; var9 < var7; ++var9) {
                  int var13 = false;
                  var8[var9] = var5;
               }

               boolean var22 = false;
               var10000 = new String(var8);
               break;
            default:
               StringBuilder sb = new StringBuilder(n * $this$repeat.length());
               int i = 1;
               int var18 = n;
               if (i <= n) {
                  while(true) {
                     sb.append($this$repeat);
                     if (i == var18) {
                        break;
                     }

                     ++i;
                  }
               }

               var10000 = sb.toString();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "sb.toString()");
            }
         }

         return var10000;
      }
   }

   @NotNull
   public static final Comparator<String> getCASE_INSENSITIVE_ORDER(@NotNull StringCompanionObject $this$CASE_INSENSITIVE_ORDER) {
      Intrinsics.checkParameterIsNotNull($this$CASE_INSENSITIVE_ORDER, "$this$CASE_INSENSITIVE_ORDER");
      Comparator var10000 = String.CASE_INSENSITIVE_ORDER;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.String.CASE_INSENSITIVE_ORDER");
      return var10000;
   }

   public StringsKt__StringsJVMKt() {
   }
}
