package kotlin.sequences;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\b\u0012\u0004\u0012\u0002H\u00030\u0004BA\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00020\tH\u0096\u0002R \u0010\b\u001a\u0014\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"},
   d2 = {"Lkotlin/sequences/FlatteningSequence;", "T", "R", "E", "Lkotlin/sequences/Sequence;", "sequence", "transformer", "Lkotlin/Function1;", "iterator", "", "(Lkotlin/sequences/Sequence;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "kotlin-stdlib"}
)
public final class FlatteningSequence<T, R, E> implements Sequence<E> {
   private final Sequence<T> sequence;
   private final Function1<T, R> transformer;
   private final Function1<R, Iterator<E>> iterator;

   @NotNull
   public Iterator<E> iterator() {
      return (Iterator)(new Iterator<E>() {
         @NotNull
         private final Iterator<T> iterator;
         @Nullable
         private Iterator<? extends E> itemIterator;

         @NotNull
         public final Iterator<T> getIterator() {
            return this.iterator;
         }

         @Nullable
         public final Iterator<E> getItemIterator() {
            return this.itemIterator;
         }

         public final void setItemIterator(@Nullable Iterator<? extends E> var1) {
            this.itemIterator = var1;
         }

         public E next() {
            if (!this.ensureItemIterator()) {
               throw (Throwable)(new NoSuchElementException());
            } else {
               Iterator var10000 = this.itemIterator;
               if (var10000 == null) {
                  Intrinsics.throwNpe();
               }

               return var10000.next();
            }
         }

         public boolean hasNext() {
            return this.ensureItemIterator();
         }

         private final boolean ensureItemIterator() {
            Iterator var10000 = this.itemIterator;
            if (var10000 != null) {
               if (!var10000.hasNext()) {
                  this.itemIterator = (Iterator)null;
               }
            }

            Iterator nextItemIterator;
            do {
               if (this.itemIterator != null) {
                  return true;
               }

               if (!this.iterator.hasNext()) {
                  return false;
               }

               Object element = this.iterator.next();
               nextItemIterator = (Iterator)FlatteningSequence.this.iterator.invoke(FlatteningSequence.this.transformer.invoke(element));
            } while(!nextItemIterator.hasNext());

            this.itemIterator = nextItemIterator;
            return true;
         }

         {
            this.iterator = FlatteningSequence.this.sequence.iterator();
         }

         public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
         }
      });
   }

   public FlatteningSequence(@NotNull Sequence<? extends T> sequence, @NotNull Function1<? super T, ? extends R> transformer, @NotNull Function1<? super R, ? extends Iterator<? extends E>> iterator) {
      Intrinsics.checkParameterIsNotNull(sequence, "sequence");
      Intrinsics.checkParameterIsNotNull(transformer, "transformer");
      Intrinsics.checkParameterIsNotNull(iterator, "iterator");
      super();
      this.sequence = sequence;
      this.transformer = transformer;
      this.iterator = iterator;
   }
}
