package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"},
   d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"},
   xs = "kotlin/text/StringsKt"
)
class StringsKt__IndentKt {
   @NotNull
   public static final String trimMargin(@NotNull String $this$trimMargin, @NotNull String marginPrefix) {
      Intrinsics.checkParameterIsNotNull($this$trimMargin, "$this$trimMargin");
      Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
      return StringsKt.replaceIndentByMargin($this$trimMargin, "", marginPrefix);
   }

   // $FF: synthetic method
   public static String trimMargin$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "|";
      }

      return StringsKt.trimMargin(var0, var1);
   }

   @NotNull
   public static final String replaceIndentByMargin(@NotNull String $this$replaceIndentByMargin, @NotNull String newIndent, @NotNull String marginPrefix) {
      Intrinsics.checkParameterIsNotNull($this$replaceIndentByMargin, "$this$replaceIndentByMargin");
      Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
      Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
      CharSequence var3 = (CharSequence)marginPrefix;
      boolean var4 = false;
      boolean var45 = !StringsKt.isBlank(var3);
      var4 = false;
      boolean var5 = false;
      if (!var45) {
         int var49 = false;
         String var48 = "marginPrefix must be non-blank string.";
         throw (Throwable)(new IllegalArgumentException(var48.toString()));
      } else {
         List lines = StringsKt.lines((CharSequence)$this$replaceIndentByMargin);
         int resultSizeEstimate$iv = $this$replaceIndentByMargin.length() + newIndent.length() * lines.size();
         Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
         int $i$f$reindent$StringsKt__IndentKt = false;
         int lastIndex$iv = CollectionsKt.getLastIndex(lines);
         Iterable $this$mapIndexedNotNull$iv$iv = (Iterable)lines;
         int $i$f$mapIndexedNotNull = false;
         Collection destination$iv$iv$iv = (Collection)(new ArrayList());
         int $i$f$mapIndexedNotNullTo = false;
         int $i$f$forEachIndexed = false;
         int index$iv$iv$iv$iv = 0;
         Iterator var17 = $this$mapIndexedNotNull$iv$iv.iterator();

         String var50;
         while(var17.hasNext()) {
            Object item$iv$iv$iv$iv = var17.next();
            int var19 = index$iv$iv$iv$iv++;
            boolean var20 = false;
            if (var19 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            int var24 = false;
            String value$iv = (String)item$iv$iv$iv$iv;
            int var27 = false;
            if ((var19 == 0 || var19 == lastIndex$iv) && StringsKt.isBlank((CharSequence)value$iv)) {
               var50 = null;
            } else {
               label91: {
                  int var29 = false;
                  CharSequence $this$indexOfFirst$iv = (CharSequence)value$iv;
                  int $i$f$indexOfFirst = false;
                  int index$iv = 0;
                  int var33 = $this$indexOfFirst$iv.length();

                  int var10000;
                  while(true) {
                     if (index$iv >= var33) {
                        var10000 = -1;
                        break;
                     }

                     char it = $this$indexOfFirst$iv.charAt(index$iv);
                     int var35 = false;
                     if (!CharsKt.isWhitespace(it)) {
                        var10000 = index$iv;
                        break;
                     }

                     ++index$iv;
                  }

                  int firstNonWhitespaceIndex = var10000;
                  if (firstNonWhitespaceIndex == -1) {
                     var50 = null;
                  } else if (StringsKt.startsWith$default(value$iv, marginPrefix, firstNonWhitespaceIndex, false, 4, (Object)null)) {
                     int var51 = firstNonWhitespaceIndex + marginPrefix.length();
                     boolean var52 = false;
                     if (value$iv == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                     }

                     var50 = value$iv.substring(var51);
                     Intrinsics.checkExpressionValueIsNotNull(var50, "(this as java.lang.String).substring(startIndex)");
                  } else {
                     var50 = null;
                  }

                  if (var50 != null) {
                     String var37 = var50;
                     boolean var38 = false;
                     boolean var39 = false;
                     var50 = (String)indentAddFunction$iv.invoke(var37);
                     if (var50 != null) {
                        break label91;
                     }
                  }

                  var50 = value$iv;
               }
            }

            if (var50 != null) {
               String var40 = var50;
               boolean var41 = false;
               boolean var42 = false;
               int var44 = false;
               destination$iv$iv$iv.add(var40);
            }
         }

         var50 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)destination$iv$iv$iv), (Appendable)(new StringBuilder(resultSizeEstimate$iv)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
         Intrinsics.checkExpressionValueIsNotNull(var50, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
         return var50;
      }
   }

   // $FF: synthetic method
   public static String replaceIndentByMargin$default(String var0, String var1, String var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = "";
      }

      if ((var3 & 2) != 0) {
         var2 = "|";
      }

      return StringsKt.replaceIndentByMargin(var0, var1, var2);
   }

   @NotNull
   public static final String trimIndent(@NotNull String $this$trimIndent) {
      Intrinsics.checkParameterIsNotNull($this$trimIndent, "$this$trimIndent");
      return StringsKt.replaceIndent($this$trimIndent, "");
   }

   @NotNull
   public static final String replaceIndent(@NotNull String $this$replaceIndent, @NotNull String newIndent) {
      Intrinsics.checkParameterIsNotNull($this$replaceIndent, "$this$replaceIndent");
      Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
      List lines = StringsKt.lines((CharSequence)$this$replaceIndent);
      Iterable $this$map$iv = (Iterable)lines;
      int $i$f$map = false;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$mapTo = false;
      Iterator var9 = $this$map$iv.iterator();

      Object item$iv$iv;
      String p1;
      boolean var12;
      while(var9.hasNext()) {
         item$iv$iv = var9.next();
         p1 = (String)item$iv$iv;
         var12 = false;
         CharSequence var13 = (CharSequence)p1;
         boolean var14 = false;
         if (!StringsKt.isBlank(var13)) {
            destination$iv$iv.add(item$iv$iv);
         }
      }

      $this$map$iv = (Iterable)((List)destination$iv$iv);
      $i$f$map = false;
      destination$iv$iv = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)));
      $i$f$mapTo = false;
      var9 = $this$map$iv.iterator();

      while(var9.hasNext()) {
         item$iv$iv = var9.next();
         p1 = (String)item$iv$iv;
         var12 = false;
         Integer var39 = indentWidth$StringsKt__IndentKt(p1);
         destination$iv$iv.add(var39);
      }

      Integer var10000 = (Integer)CollectionsKt.min((Iterable)((List)destination$iv$iv));
      int minCommonIndent = var10000 != null ? var10000 : 0;
      int resultSizeEstimate$iv = $this$replaceIndent.length() + newIndent.length() * lines.size();
      Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
      int $i$f$reindent$StringsKt__IndentKt = false;
      int lastIndex$iv = CollectionsKt.getLastIndex(lines);
      Iterable $this$mapIndexedNotNull$iv$iv = (Iterable)lines;
      int $i$f$mapIndexedNotNull = false;
      Collection destination$iv$iv$iv = (Collection)(new ArrayList());
      int $i$f$mapIndexedNotNullTo = false;
      int $i$f$forEachIndexed = false;
      int index$iv$iv$iv$iv = 0;
      Iterator var17 = $this$mapIndexedNotNull$iv$iv.iterator();

      String var48;
      while(var17.hasNext()) {
         Object item$iv$iv$iv$iv = var17.next();
         int var19 = index$iv$iv$iv$iv++;
         boolean var20 = false;
         if (var19 < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         int var24 = false;
         String value$iv = (String)item$iv$iv$iv$iv;
         int var27 = false;
         if ((var19 == 0 || var19 == lastIndex$iv) && StringsKt.isBlank((CharSequence)value$iv)) {
            var48 = null;
         } else {
            label79: {
               int var29 = false;
               var48 = StringsKt.drop(value$iv, minCommonIndent);
               if (var48 != null) {
                  String var30 = var48;
                  boolean var31 = false;
                  boolean var32 = false;
                  var48 = (String)indentAddFunction$iv.invoke(var30);
                  if (var48 != null) {
                     break label79;
                  }
               }

               var48 = value$iv;
            }
         }

         if (var48 != null) {
            String var33 = var48;
            boolean var34 = false;
            boolean var35 = false;
            int var37 = false;
            destination$iv$iv$iv.add(var33);
         }
      }

      var48 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)destination$iv$iv$iv), (Appendable)(new StringBuilder(resultSizeEstimate$iv)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
      Intrinsics.checkExpressionValueIsNotNull(var48, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return var48;
   }

   // $FF: synthetic method
   public static String replaceIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "";
      }

      return StringsKt.replaceIndent(var0, var1);
   }

   @NotNull
   public static final String prependIndent(@NotNull String $this$prependIndent, @NotNull final String indent) {
      Intrinsics.checkParameterIsNotNull($this$prependIndent, "$this$prependIndent");
      Intrinsics.checkParameterIsNotNull(indent, "indent");
      return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence((CharSequence)$this$prependIndent), (Function1)(new Function1<String, String>() {
         @NotNull
         public final String invoke(@NotNull String it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return StringsKt.isBlank((CharSequence)it) ? (it.length() < indent.length() ? indent : it) : indent + it;
         }
      })), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null);
   }

   // $FF: synthetic method
   public static String prependIndent$default(String var0, String var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = "    ";
      }

      return StringsKt.prependIndent(var0, var1);
   }

   private static final int indentWidth$StringsKt__IndentKt(@NotNull String $this$indentWidth) {
      CharSequence $this$indexOfFirst$iv = (CharSequence)$this$indentWidth;
      int $i$f$indexOfFirst = false;
      int index$iv = 0;
      int var4 = $this$indexOfFirst$iv.length();

      int var10000;
      while(true) {
         if (index$iv >= var4) {
            var10000 = -1;
            break;
         }

         char it = $this$indexOfFirst$iv.charAt(index$iv);
         int var6 = false;
         if (!CharsKt.isWhitespace(it)) {
            var10000 = index$iv;
            break;
         }

         ++index$iv;
      }

      int var7 = var10000;
      $i$f$indexOfFirst = false;
      boolean var8 = false;
      int var9 = false;
      return var7 == -1 ? $this$indentWidth.length() : var7;
   }

   private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(final String indent) {
      CharSequence var1 = (CharSequence)indent;
      boolean var2 = false;
      return var1.length() == 0 ? (Function1)null.INSTANCE : (Function1)(new Function1<String, String>() {
         @NotNull
         public final String invoke(@NotNull String line) {
            Intrinsics.checkParameterIsNotNull(line, "line");
            return indent + line;
         }
      });
   }

   private static final String reindent$StringsKt__IndentKt(@NotNull List<String> $this$reindent, int resultSizeEstimate, Function1<? super String, String> indentAddFunction, Function1<? super String, String> indentCutFunction) {
      int $i$f$reindent$StringsKt__IndentKt = 0;
      int lastIndex = CollectionsKt.getLastIndex($this$reindent);
      Iterable $this$mapIndexedNotNull$iv = (Iterable)$this$reindent;
      int $i$f$mapIndexedNotNull = false;
      Collection destination$iv$iv = (Collection)(new ArrayList());
      int $i$f$mapIndexedNotNullTo = false;
      int $i$f$forEachIndexed = false;
      int index$iv$iv$iv = 0;
      Iterator var14 = $this$mapIndexedNotNull$iv.iterator();

      String var33;
      while(var14.hasNext()) {
         Object item$iv$iv$iv = var14.next();
         int var16 = index$iv$iv$iv++;
         boolean var17 = false;
         if (var16 < 0) {
            if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
               throw (Throwable)(new ArithmeticException("Index overflow has happened."));
            }

            CollectionsKt.throwIndexOverflow();
         }

         int var21 = false;
         String value = (String)item$iv$iv$iv;
         int var24 = false;
         if ((var16 == 0 || var16 == lastIndex) && StringsKt.isBlank((CharSequence)value)) {
            var33 = null;
         } else {
            label47: {
               var33 = (String)indentCutFunction.invoke(value);
               if (var33 != null) {
                  String var25 = var33;
                  boolean var26 = false;
                  boolean var27 = false;
                  var33 = (String)indentAddFunction.invoke(var25);
                  if (var33 != null) {
                     break label47;
                  }
               }

               var33 = value;
            }
         }

         if (var33 != null) {
            String var28 = var33;
            boolean var29 = false;
            boolean var30 = false;
            int var32 = false;
            destination$iv$iv.add(var28);
         }
      }

      var33 = ((StringBuilder)CollectionsKt.joinTo$default((Iterable)((List)destination$iv$iv), (Appendable)(new StringBuilder(resultSizeEstimate)), (CharSequence)"\n", (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null)).toString();
      Intrinsics.checkExpressionValueIsNotNull(var33, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
      return var33;
   }

   public StringsKt__IndentKt() {
   }
}
