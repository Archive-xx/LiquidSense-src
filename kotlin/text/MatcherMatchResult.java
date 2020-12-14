package kotlin.text;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001b¨\u0006\u001d"},
   d2 = {"Lkotlin/text/MatcherMatchResult;", "Lkotlin/text/MatchResult;", "matcher", "Ljava/util/regex/Matcher;", "input", "", "(Ljava/util/regex/Matcher;Ljava/lang/CharSequence;)V", "groupValues", "", "", "getGroupValues", "()Ljava/util/List;", "groupValues_", "groups", "Lkotlin/text/MatchGroupCollection;", "getGroups", "()Lkotlin/text/MatchGroupCollection;", "matchResult", "Ljava/util/regex/MatchResult;", "getMatchResult", "()Ljava/util/regex/MatchResult;", "range", "Lkotlin/ranges/IntRange;", "getRange", "()Lkotlin/ranges/IntRange;", "value", "getValue", "()Ljava/lang/String;", "next", "kotlin-stdlib"}
)
final class MatcherMatchResult implements MatchResult {
   @NotNull
   private final MatchGroupCollection groups;
   private List<String> groupValues_;
   private final Matcher matcher;
   private final CharSequence input;

   private final java.util.regex.MatchResult getMatchResult() {
      return (java.util.regex.MatchResult)this.matcher;
   }

   @NotNull
   public IntRange getRange() {
      return RegexKt.access$range(this.getMatchResult());
   }

   @NotNull
   public String getValue() {
      String var10000 = this.getMatchResult().group();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "matchResult.group()");
      return var10000;
   }

   @NotNull
   public MatchGroupCollection getGroups() {
      return this.groups;
   }

   @NotNull
   public List<String> getGroupValues() {
      if (this.groupValues_ == null) {
         this.groupValues_ = (List)(new AbstractList<String>() {
            public int getSize() {
               return MatcherMatchResult.this.getMatchResult().groupCount() + 1;
            }

            @NotNull
            public String get(int index) {
               String var10000 = MatcherMatchResult.this.getMatchResult().group(index);
               if (var10000 == null) {
                  var10000 = "";
               }

               return var10000;
            }
         });
      }

      List var10000 = this.groupValues_;
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      return var10000;
   }

   @Nullable
   public MatchResult next() {
      int nextIndex = this.getMatchResult().end() + (this.getMatchResult().end() == this.getMatchResult().start() ? 1 : 0);
      MatchResult var2;
      if (nextIndex <= this.input.length()) {
         Matcher var10000 = this.matcher.pattern().matcher(this.input);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "matcher.pattern().matcher(input)");
         var2 = RegexKt.access$findNext(var10000, nextIndex, this.input);
      } else {
         var2 = null;
      }

      return var2;
   }

   public MatcherMatchResult(@NotNull Matcher matcher, @NotNull CharSequence input) {
      Intrinsics.checkParameterIsNotNull(matcher, "matcher");
      Intrinsics.checkParameterIsNotNull(input, "input");
      super();
      this.matcher = matcher;
      this.input = input;
      this.groups = (MatchGroupCollection)(new MatchNamedGroupCollection() {
         public int getSize() {
            return MatcherMatchResult.this.getMatchResult().groupCount() + 1;
         }

         public boolean isEmpty() {
            return false;
         }

         @NotNull
         public Iterator<MatchGroup> iterator() {
            return SequencesKt.map(CollectionsKt.asSequence((Iterable)CollectionsKt.getIndices(this)), (Function1)(new Function1<Integer, MatchGroup>() {
               @Nullable
               public final MatchGroup invoke(int it) {
                  return get(it);
               }
            })).iterator();
         }

         @Nullable
         public MatchGroup get(int index) {
            IntRange range = RegexKt.access$range(MatcherMatchResult.this.getMatchResult(), index);
            MatchGroup var10000;
            if (range.getStart() >= 0) {
               String var10002 = MatcherMatchResult.this.getMatchResult().group(index);
               Intrinsics.checkExpressionValueIsNotNull(var10002, "matchResult.group(index)");
               var10000 = new MatchGroup(var10002, range);
            } else {
               var10000 = null;
            }

            return var10000;
         }

         @Nullable
         public MatchGroup get(@NotNull String name) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            return PlatformImplementationsKt.IMPLEMENTATIONS.getMatchResultNamedGroup(MatcherMatchResult.this.getMatchResult(), name);
         }
      });
   }

   @NotNull
   public MatchResult.Destructured getDestructured() {
      return MatchResult.DefaultImpls.getDestructured(this);
   }
}
