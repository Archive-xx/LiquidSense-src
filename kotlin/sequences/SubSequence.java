package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B#\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0016J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u000fH\u0096\u0002J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\r\u001a\u00020\u0006H\u0016R\u0014\u0010\t\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lkotlin/sequences/SubSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "startIndex", "", "endIndex", "(Lkotlin/sequences/Sequence;II)V", "count", "getCount", "()I", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}
)
public final class SubSequence<T> implements Sequence<T>, DropTakeSequence<T> {
   private final Sequence<T> sequence;
   private final int startIndex;
   private final int endIndex;

   private final int getCount() {
      return this.endIndex - this.startIndex;
   }

   @NotNull
   public Sequence<T> drop(int n) {
      return n >= this.getCount() ? SequencesKt.emptySequence() : (Sequence)(new SubSequence(this.sequence, this.startIndex + n, this.endIndex));
   }

   @NotNull
   public Sequence<T> take(int n) {
      return n >= this.getCount() ? (Sequence)this : (Sequence)(new SubSequence(this.sequence, this.startIndex, this.startIndex + n));
   }

   @NotNull
   public Iterator<T> iterator() {
      return (Iterator)(new Iterator<T>() {
         @NotNull
         private final Iterator<T> iterator;
         private int position;

         @NotNull
         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getPosition() {
            return this.position;
         }

         public final void setPosition(int var1) {
            this.position = var1;
         }

         private final void drop() {
            while(this.position < SubSequence.this.startIndex && this.iterator.hasNext()) {
               this.iterator.next();
               int var10001 = this.position++;
            }

         }

         public boolean hasNext() {
            this.drop();
            return this.position < SubSequence.this.endIndex && this.iterator.hasNext();
         }

         public T next() {
            this.drop();
            if (this.position >= SubSequence.this.endIndex) {
               throw (Throwable)(new NoSuchElementException());
            } else {
               int var10001 = this.position++;
               return this.iterator.next();
            }
         }

         {
            this.iterator = SubSequence.this.sequence.iterator();
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      });
   }

   public SubSequence(@NotNull Sequence<? extends T> sequence, int startIndex, int endIndex) {
      Intrinsics.checkParameterIsNotNull(sequence, "sequence");
      super();
      this.sequence = sequence;
      this.startIndex = startIndex;
      this.endIndex = endIndex;
      boolean var4 = this.startIndex >= 0;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7;
      String var8;
      if (!var4) {
         var7 = false;
         var8 = "startIndex should be non-negative, but is " + this.startIndex;
         throw (Throwable)(new IllegalArgumentException(var8.toString()));
      } else {
         var4 = this.endIndex >= 0;
         var5 = false;
         var6 = false;
         if (!var4) {
            var7 = false;
            var8 = "endIndex should be non-negative, but is " + this.endIndex;
            throw (Throwable)(new IllegalArgumentException(var8.toString()));
         } else {
            var4 = this.endIndex >= this.startIndex;
            var5 = false;
            var6 = false;
            if (!var4) {
               var7 = false;
               var8 = "endIndex should be not less than startIndex, but was " + this.endIndex + " < " + this.startIndex;
               throw (Throwable)(new IllegalArgumentException(var8.toString()));
            }
         }
      }
   }
}
