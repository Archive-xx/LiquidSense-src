package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u0096\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\t\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"},
   d2 = {"Lkotlin/sequences/DropSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/sequences/DropTakeSequence;", "sequence", "count", "", "(Lkotlin/sequences/Sequence;I)V", "drop", "n", "iterator", "", "take", "kotlin-stdlib"}
)
public final class DropSequence<T> implements Sequence<T>, DropTakeSequence<T> {
   private final Sequence<T> sequence;
   private final int count;

   @NotNull
   public Sequence<T> drop(int n) {
      int var2 = this.count + n;
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 < 0 ? (Sequence)(new DropSequence((Sequence)this, n)) : (Sequence)(new DropSequence(this.sequence, var2));
   }

   @NotNull
   public Sequence<T> take(int n) {
      int var2 = this.count + n;
      boolean var3 = false;
      boolean var4 = false;
      int var6 = false;
      return var2 < 0 ? (Sequence)(new TakeSequence((Sequence)this, n)) : (Sequence)(new SubSequence(this.sequence, this.count, var2));
   }

   @NotNull
   public Iterator<T> iterator() {
      return (Iterator)(new Iterator<T>() {
         @NotNull
         private final Iterator<T> iterator;
         private int left;

         @NotNull
         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getLeft() {
            return this.left;
         }

         public final void setLeft(int var1) {
            this.left = var1;
         }

         private final void drop() {
            while(this.left > 0 && this.iterator.hasNext()) {
               this.iterator.next();
               this.left += -1;
            }

         }

         public T next() {
            this.drop();
            return this.iterator.next();
         }

         public boolean hasNext() {
            this.drop();
            return this.iterator.hasNext();
         }

         {
            this.iterator = DropSequence.this.sequence.iterator();
            this.left = DropSequence.this.count;
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      });
   }

   public DropSequence(@NotNull Sequence<? extends T> sequence, int count) {
      Intrinsics.checkParameterIsNotNull(sequence, "sequence");
      super();
      this.sequence = sequence;
      this.count = count;
      boolean var3 = this.count >= 0;
      boolean var4 = false;
      boolean var5 = false;
      if (!var3) {
         int var6 = false;
         String var7 = "count must be non-negative, but was " + this.count + '.';
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      }
   }
}
