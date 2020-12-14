package kotlin.text;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000>\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002¨\u0006\u0014"},
   d2 = {"fromInt", "", "T", "Lkotlin/text/FlagEnum;", "", "value", "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", "input", "", "matchEntire", "range", "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"}
)
public final class RegexKt {
   private static final int toInt(@NotNull Iterable<? extends FlagEnum> $this$toInt) {
      int initial$iv = 0;
      int $i$f$fold = false;
      int accumulator$iv = initial$iv;

      FlagEnum option;
      for(Iterator var5 = $this$toInt.iterator(); var5.hasNext(); accumulator$iv |= option.getValue()) {
         Object element$iv = var5.next();
         option = (FlagEnum)element$iv;
         int var9 = false;
      }

      return accumulator$iv;
   }

   // $FF: synthetic method
   private static final <T extends Enum<T> & FlagEnum> Set<T> fromInt(int value) {
      int $i$f$fromInt = 0;
      Intrinsics.reifiedOperationMarker(4, "T");
      EnumSet var2 = EnumSet.allOf(Enum.class);
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      CollectionsKt.retainAll((Iterable)var2, (Function1)(new RegexKt$fromInt$$inlined$apply$lambda$1(value)));
      Set var10000 = Collections.unmodifiableSet((Set)var2);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Collections.unmodifiable…mask == it.value }\n    })");
      return var10000;
   }

   private static final MatchResult findNext(@NotNull Matcher $this$findNext, int from, CharSequence input) {
      return !$this$findNext.find(from) ? null : (MatchResult)(new MatcherMatchResult($this$findNext, input));
   }

   private static final MatchResult matchEntire(@NotNull Matcher $this$matchEntire, CharSequence input) {
      return !$this$matchEntire.matches() ? null : (MatchResult)(new MatcherMatchResult($this$matchEntire, input));
   }

   private static final IntRange range(@NotNull java.util.regex.MatchResult $this$range) {
      return RangesKt.until($this$range.start(), $this$range.end());
   }

   private static final IntRange range(@NotNull java.util.regex.MatchResult $this$range, int groupIndex) {
      return RangesKt.until($this$range.start(groupIndex), $this$range.end(groupIndex));
   }

   // $FF: synthetic method
   public static final Set access$fromInt(int value) {
      return fromInt(value);
   }

   // $FF: synthetic method
   public static final MatchResult access$findNext(Matcher $this$access_u24findNext, int from, CharSequence input) {
      return findNext($this$access_u24findNext, from, input);
   }

   // $FF: synthetic method
   public static final MatchResult access$matchEntire(Matcher $this$access_u24matchEntire, CharSequence input) {
      return matchEntire($this$access_u24matchEntire, input);
   }

   // $FF: synthetic method
   public static final int access$toInt(Iterable $this$access_u24toInt) {
      return toInt($this$access_u24toInt);
   }

   // $FF: synthetic method
   public static final IntRange access$range(java.util.regex.MatchResult $this$access_u24range) {
      return range($this$access_u24range);
   }

   // $FF: synthetic method
   public static final IntRange access$range(java.util.regex.MatchResult $this$access_u24range, int groupIndex) {
      return range($this$access_u24range, groupIndex);
   }
}
