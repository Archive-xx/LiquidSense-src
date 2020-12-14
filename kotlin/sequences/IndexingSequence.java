package kotlin.sequences;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0005J\u0015\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00030\u0007H\u0096\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"},
   d2 = {"Lkotlin/sequences/IndexingSequence;", "T", "Lkotlin/sequences/Sequence;", "Lkotlin/collections/IndexedValue;", "sequence", "(Lkotlin/sequences/Sequence;)V", "iterator", "", "kotlin-stdlib"}
)
public final class IndexingSequence<T> implements Sequence<IndexedValue<? extends T>> {
   private final Sequence<T> sequence;

   @NotNull
   public Iterator<IndexedValue<T>> iterator() {
      return (Iterator)(new Iterator<IndexedValue<? extends T>>() {
         @NotNull
         private final Iterator<T> iterator;
         private int index;

         @NotNull
         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         public final int getIndex() {
            return this.index;
         }

         public final void setIndex(int var1) {
            this.index = var1;
         }

         @NotNull
         public IndexedValue<T> next() {
            int var1;
            this.index = (var1 = this.index) + 1;
            boolean var2 = false;
            if (var1 < 0) {
               CollectionsKt.throwIndexOverflow();
            }

            Object var6 = this.iterator.next();
            return new IndexedValue(var1, var6);
         }

         public boolean hasNext() {
            return this.iterator.hasNext();
         }

         {
            this.iterator = IndexingSequence.this.sequence.iterator();
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      });
   }

   public IndexingSequence(@NotNull Sequence<? extends T> sequence) {
      Intrinsics.checkParameterIsNotNull(sequence, "sequence");
      super();
      this.sequence = sequence;
   }
}
