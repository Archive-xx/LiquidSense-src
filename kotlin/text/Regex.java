package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 ,2\u00060\u0001j\u0002`\u0002:\u0002,-B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u001d\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n¢\u0006\u0002\u0010\u000bB\u000f\b\u0001\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00190\u001d2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010\u001a\u001a\u00020\u001bJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0016\u001a\u00020\u0017J\u0011\u0010\u001f\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086\u0004J\"\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00170\"J\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u0016\u0010$\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004J\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040&2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010'\u001a\u00020\u001bJ\u0006\u0010(\u001a\u00020\rJ\b\u0010)\u001a\u00020\u0004H\u0016J\b\u0010*\u001a\u00020+H\u0002R\u0016\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00070\n8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006."},
   d2 = {"Lkotlin/text/Regex;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "(Ljava/lang/String;)V", "option", "Lkotlin/text/RegexOption;", "(Ljava/lang/String;Lkotlin/text/RegexOption;)V", "options", "", "(Ljava/lang/String;Ljava/util/Set;)V", "nativePattern", "Ljava/util/regex/Pattern;", "(Ljava/util/regex/Pattern;)V", "_options", "getOptions", "()Ljava/util/Set;", "getPattern", "()Ljava/lang/String;", "containsMatchIn", "", "input", "", "find", "Lkotlin/text/MatchResult;", "startIndex", "", "findAll", "Lkotlin/sequences/Sequence;", "matchEntire", "matches", "replace", "transform", "Lkotlin/Function1;", "replacement", "replaceFirst", "split", "", "limit", "toPattern", "toString", "writeReplace", "", "Companion", "Serialized", "kotlin-stdlib"}
)
public final class Regex implements Serializable {
   private Set<? extends RegexOption> _options;
   private final Pattern nativePattern;
   public static final Regex.Companion Companion = new Regex.Companion((DefaultConstructorMarker)null);

   @NotNull
   public final String getPattern() {
      String var10000 = this.nativePattern.pattern();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "nativePattern.pattern()");
      return var10000;
   }

   @NotNull
   public final Set<RegexOption> getOptions() {
      Set var10000 = this._options;
      if (var10000 == null) {
         int value$iv = this.nativePattern.flags();
         int $i$f$fromInt = false;
         EnumSet var3 = EnumSet.allOf(RegexOption.class);
         boolean var4 = false;
         boolean var5 = false;
         int var7 = false;
         CollectionsKt.retainAll((Iterable)var3, (Function1)(new Regex$fromInt$$inlined$apply$lambda$1(value$iv)));
         var10000 = Collections.unmodifiableSet((Set)var3);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "Collections.unmodifiable…mask == it.value }\n    })");
         Set var8 = var10000;
         $i$f$fromInt = false;
         boolean var9 = false;
         var5 = false;
         this._options = var8;
         var10000 = var8;
      }

      return var10000;
   }

   public final boolean matches(@NotNull CharSequence input) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      return this.nativePattern.matcher(input).matches();
   }

   public final boolean containsMatchIn(@NotNull CharSequence input) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      return this.nativePattern.matcher(input).find();
   }

   @Nullable
   public final MatchResult find(@NotNull CharSequence input, int startIndex) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      Matcher var10000 = this.nativePattern.matcher(input);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "nativePattern.matcher(input)");
      return RegexKt.access$findNext(var10000, startIndex, input);
   }

   // $FF: synthetic method
   public static MatchResult find$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.find(var1, var2);
   }

   @NotNull
   public final Sequence<MatchResult> findAll(@NotNull final CharSequence input, final int startIndex) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      return SequencesKt.generateSequence((Function0)(new Function0<MatchResult>() {
         @Nullable
         public final MatchResult invoke() {
            return Regex.this.find(input, startIndex);
         }
      }), (Function1)null.INSTANCE);
   }

   // $FF: synthetic method
   public static Sequence findAll$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.findAll(var1, var2);
   }

   @Nullable
   public final MatchResult matchEntire(@NotNull CharSequence input) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      Matcher var10000 = this.nativePattern.matcher(input);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "nativePattern.matcher(input)");
      return RegexKt.access$matchEntire(var10000, input);
   }

   @NotNull
   public final String replace(@NotNull CharSequence input, @NotNull String replacement) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      String var10000 = this.nativePattern.matcher(input).replaceAll(replacement);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "nativePattern.matcher(in…).replaceAll(replacement)");
      return var10000;
   }

   @NotNull
   public final String replace(@NotNull CharSequence input, @NotNull Function1<? super MatchResult, ? extends CharSequence> transform) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      Intrinsics.checkParameterIsNotNull(transform, "transform");
      MatchResult var10000 = find$default(this, input, 0, 2, (Object)null);
      if (var10000 == null) {
         return input.toString();
      } else {
         MatchResult match = var10000;
         int lastStart = 0;
         int length = input.length();
         StringBuilder sb = new StringBuilder(length);

         do {
            if (match == null) {
               Intrinsics.throwNpe();
            }

            sb.append(input, lastStart, match.getRange().getStart());
            sb.append((CharSequence)transform.invoke(match));
            lastStart = match.getRange().getEndInclusive() + 1;
            match = match.next();
         } while(lastStart < length && match != null);

         if (lastStart < length) {
            sb.append(input, lastStart, length);
         }

         String var8 = sb.toString();
         Intrinsics.checkExpressionValueIsNotNull(var8, "sb.toString()");
         return var8;
      }
   }

   @NotNull
   public final String replaceFirst(@NotNull CharSequence input, @NotNull String replacement) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      Intrinsics.checkParameterIsNotNull(replacement, "replacement");
      String var10000 = this.nativePattern.matcher(input).replaceFirst(replacement);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "nativePattern.matcher(in…replaceFirst(replacement)");
      return var10000;
   }

   @NotNull
   public final List<String> split(@NotNull CharSequence input, int limit) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      boolean var3 = limit >= 0;
      boolean var4 = false;
      boolean var5 = false;
      if (!var3) {
         int var16 = false;
         String var15 = "Limit must be non-negative, but was " + limit + '.';
         throw (Throwable)(new IllegalArgumentException(var15.toString()));
      } else {
         Matcher matcher = this.nativePattern.matcher(input);
         if (matcher.find() && limit != 1) {
            ArrayList result = new ArrayList(limit > 0 ? RangesKt.coerceAtMost(limit, 10) : 10);
            int lastStart = 0;
            int lastSplit = limit - 1;

            int var8;
            boolean var9;
            String var11;
            do {
               var8 = matcher.start();
               var9 = false;
               var11 = input.subSequence(lastStart, var8).toString();
               result.add(var11);
               lastStart = matcher.end();
            } while((lastSplit < 0 || result.size() != lastSplit) && matcher.find());

            var8 = input.length();
            var9 = false;
            var11 = input.subSequence(lastStart, var8).toString();
            result.add(var11);
            return (List)result;
         } else {
            return CollectionsKt.listOf(input.toString());
         }
      }
   }

   // $FF: synthetic method
   public static List split$default(Regex var0, CharSequence var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 0;
      }

      return var0.split(var1, var2);
   }

   @NotNull
   public String toString() {
      String var10000 = this.nativePattern.toString();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "nativePattern.toString()");
      return var10000;
   }

   @NotNull
   public final Pattern toPattern() {
      return this.nativePattern;
   }

   private final Object writeReplace() {
      String var10002 = this.nativePattern.pattern();
      Intrinsics.checkExpressionValueIsNotNull(var10002, "nativePattern.pattern()");
      return new Regex.Serialized(var10002, this.nativePattern.flags());
   }

   @PublishedApi
   public Regex(@NotNull Pattern nativePattern) {
      Intrinsics.checkParameterIsNotNull(nativePattern, "nativePattern");
      super();
      this.nativePattern = nativePattern;
   }

   public Regex(@NotNull String pattern) {
      Intrinsics.checkParameterIsNotNull(pattern, "pattern");
      Pattern var10001 = Pattern.compile(pattern);
      Intrinsics.checkExpressionValueIsNotNull(var10001, "Pattern.compile(pattern)");
      this(var10001);
   }

   public Regex(@NotNull String pattern, @NotNull RegexOption option) {
      Intrinsics.checkParameterIsNotNull(pattern, "pattern");
      Intrinsics.checkParameterIsNotNull(option, "option");
      Pattern var10001 = Pattern.compile(pattern, Companion.ensureUnicodeCase(option.getValue()));
      Intrinsics.checkExpressionValueIsNotNull(var10001, "Pattern.compile(pattern,…nicodeCase(option.value))");
      this(var10001);
   }

   public Regex(@NotNull String pattern, @NotNull Set<? extends RegexOption> options) {
      Intrinsics.checkParameterIsNotNull(pattern, "pattern");
      Intrinsics.checkParameterIsNotNull(options, "options");
      Pattern var10001 = Pattern.compile(pattern, Companion.ensureUnicodeCase(RegexKt.access$toInt((Iterable)options)));
      Intrinsics.checkExpressionValueIsNotNull(var10001, "Pattern.compile(pattern,…odeCase(options.toInt()))");
      this(var10001);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0002\b\u0002\b\u0002\u0018\u0000 \u000e2\u00060\u0001j\u0002`\u0002:\u0001\u000eB\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\f\u001a\u00020\rH\u0002R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u000f"},
      d2 = {"Lkotlin/text/Regex$Serialized;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "pattern", "", "flags", "", "(Ljava/lang/String;I)V", "getFlags", "()I", "getPattern", "()Ljava/lang/String;", "readResolve", "", "Companion", "kotlin-stdlib"}
   )
   private static final class Serialized implements Serializable {
      @NotNull
      private final String pattern;
      private final int flags;
      private static final long serialVersionUID = 0L;
      public static final Regex.Serialized.Companion Companion = new Regex.Serialized.Companion((DefaultConstructorMarker)null);

      private final Object readResolve() {
         Pattern var10002 = Pattern.compile(this.pattern, this.flags);
         Intrinsics.checkExpressionValueIsNotNull(var10002, "Pattern.compile(pattern, flags)");
         return new Regex(var10002);
      }

      @NotNull
      public final String getPattern() {
         return this.pattern;
      }

      public final int getFlags() {
         return this.flags;
      }

      public Serialized(@NotNull String pattern, int flags) {
         Intrinsics.checkParameterIsNotNull(pattern, "pattern");
         super();
         this.pattern = pattern;
         this.flags = flags;
      }

      @Metadata(
         mv = {1, 1, 15},
         bv = {1, 0, 3},
         k = 1,
         d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"},
         d2 = {"Lkotlin/text/Regex$Serialized$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}
      )
      public static final class Companion {
         private Companion() {
         }

         // $FF: synthetic method
         public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
         }
      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0007¨\u0006\f"},
      d2 = {"Lkotlin/text/Regex$Companion;", "", "()V", "ensureUnicodeCase", "", "flags", "escape", "", "literal", "escapeReplacement", "fromLiteral", "Lkotlin/text/Regex;", "kotlin-stdlib"}
   )
   public static final class Companion {
      @NotNull
      public final Regex fromLiteral(@NotNull String literal) {
         Intrinsics.checkParameterIsNotNull(literal, "literal");
         RegexOption var3 = RegexOption.LITERAL;
         boolean var4 = false;
         return new Regex(literal, var3);
      }

      @NotNull
      public final String escape(@NotNull String literal) {
         Intrinsics.checkParameterIsNotNull(literal, "literal");
         String var10000 = Pattern.quote(literal);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "Pattern.quote(literal)");
         return var10000;
      }

      @NotNull
      public final String escapeReplacement(@NotNull String literal) {
         Intrinsics.checkParameterIsNotNull(literal, "literal");
         String var10000 = Matcher.quoteReplacement(literal);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "Matcher.quoteReplacement(literal)");
         return var10000;
      }

      private final int ensureUnicodeCase(int flags) {
         return (flags & 2) != 0 ? flags | 64 : flags;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
