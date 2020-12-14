package kotlin.text;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001BY\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012:\u0010\b\u001a6\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r0\t¢\u0006\u0002\b\u000e¢\u0006\u0002\u0010\u000fJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011H\u0096\u0002RB\u0010\b\u001a6\u0012\u0004\u0012\u00020\u0004\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\r0\t¢\u0006\u0002\b\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"},
   d2 = {"Lkotlin/text/DelimitedRangesSequence;", "Lkotlin/sequences/Sequence;", "Lkotlin/ranges/IntRange;", "input", "", "startIndex", "", "limit", "getNextMatch", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "currentIndex", "Lkotlin/Pair;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/CharSequence;IILkotlin/jvm/functions/Function2;)V", "iterator", "", "kotlin-stdlib"}
)
final class DelimitedRangesSequence implements Sequence<IntRange> {
   private final CharSequence input;
   private final int startIndex;
   private final int limit;
   private final Function2<CharSequence, Integer, Pair<Integer, Integer>> getNextMatch;

   @NotNull
   public Iterator<IntRange> iterator() {
      return (Iterator)(new Iterator<IntRange>() {
         private int nextState = -1;
         private int currentStartIndex;
         private int nextSearchIndex;
         @Nullable
         private IntRange nextItem;
         private int counter;

         public final int getNextState() {
            return this.nextState;
         }

         public final void setNextState(int var1) {
            this.nextState = var1;
         }

         public final int getCurrentStartIndex() {
            return this.currentStartIndex;
         }

         public final void setCurrentStartIndex(int var1) {
            this.currentStartIndex = var1;
         }

         public final int getNextSearchIndex() {
            return this.nextSearchIndex;
         }

         public final void setNextSearchIndex(int var1) {
            this.nextSearchIndex = var1;
         }

         @Nullable
         public final IntRange getNextItem() {
            return this.nextItem;
         }

         public final void setNextItem(@Nullable IntRange var1) {
            this.nextItem = var1;
         }

         public final int getCounter() {
            return this.counter;
         }

         public final void setCounter(int var1) {
            this.counter = var1;
         }

         private final void calcNext() {
            if (this.nextSearchIndex < 0) {
               this.nextState = 0;
               this.nextItem = (IntRange)null;
            } else {
               label27: {
                  label26: {
                     if (DelimitedRangesSequence.this.limit > 0) {
                        ++this.counter;
                        if (this.counter >= DelimitedRangesSequence.this.limit) {
                           break label26;
                        }
                     }

                     if (this.nextSearchIndex <= DelimitedRangesSequence.this.input.length()) {
                        Pair match = (Pair)DelimitedRangesSequence.this.getNextMatch.invoke(DelimitedRangesSequence.this.input, this.nextSearchIndex);
                        int index;
                        if (match == null) {
                           index = this.currentStartIndex;
                           this.nextItem = new IntRange(index, StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                           this.nextSearchIndex = -1;
                        } else {
                           index = ((Number)match.component1()).intValue();
                           int length = ((Number)match.component2()).intValue();
                           this.nextItem = RangesKt.until(this.currentStartIndex, index);
                           this.currentStartIndex = index + length;
                           this.nextSearchIndex = this.currentStartIndex + (length == 0 ? 1 : 0);
                        }
                        break label27;
                     }
                  }

                  int var5 = this.currentStartIndex;
                  this.nextItem = new IntRange(var5, StringsKt.getLastIndex(DelimitedRangesSequence.this.input));
                  this.nextSearchIndex = -1;
               }

               this.nextState = 1;
            }

         }

         @NotNull
         public IntRange next() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            if (this.nextState == 0) {
               throw (Throwable)(new NoSuchElementException());
            } else {
               IntRange var10000 = this.nextItem;
               if (var10000 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type kotlin.ranges.IntRange");
               } else {
                  IntRange result = var10000;
                  this.nextItem = (IntRange)null;
                  this.nextState = -1;
                  return result;
               }
            }
         }

         public boolean hasNext() {
            if (this.nextState == -1) {
               this.calcNext();
            }

            return this.nextState == 1;
         }

         {
            this.currentStartIndex = RangesKt.coerceIn(DelimitedRangesSequence.this.startIndex, 0, DelimitedRangesSequence.this.input.length());
            this.nextSearchIndex = this.currentStartIndex;
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      });
   }

   public DelimitedRangesSequence(@NotNull CharSequence input, int startIndex, int limit, @NotNull Function2<? super CharSequence, ? super Integer, Pair<Integer, Integer>> getNextMatch) {
      Intrinsics.checkParameterIsNotNull(input, "input");
      Intrinsics.checkParameterIsNotNull(getNextMatch, "getNextMatch");
      super();
      this.input = input;
      this.startIndex = startIndex;
      this.limit = limit;
      this.getNextMatch = getNextMatch;
   }
}
