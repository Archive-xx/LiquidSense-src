package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010*\n\u0002\b\b\b'\u0018\u0000 \u001c*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0004\u001c\u001d\u001e\u001fB\u0007\b\u0004¢\u0006\u0002\u0010\u0004J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\u0016\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u0006H¦\u0002¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0006H\u0016J\u0015\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0015H\u0096\u0002J\u0015\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0013J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H\u0016J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00182\u0006\u0010\u000e\u001a\u00020\u0006H\u0016J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0006H\u0016R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006 "},
   d2 = {"Lkotlin/collections/AbstractList;", "E", "Lkotlin/collections/AbstractCollection;", "", "()V", "size", "", "getSize", "()I", "equals", "", "other", "", "get", "index", "(I)Ljava/lang/Object;", "hashCode", "indexOf", "element", "(Ljava/lang/Object;)I", "iterator", "", "lastIndexOf", "listIterator", "", "subList", "fromIndex", "toIndex", "Companion", "IteratorImpl", "ListIteratorImpl", "SubList", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.1"
)
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E>, KMappedMarker {
   public static final AbstractList.Companion Companion = new AbstractList.Companion((DefaultConstructorMarker)null);

   public abstract int getSize();

   public abstract E get(int var1);

   @NotNull
   public Iterator<E> iterator() {
      return (Iterator)(new AbstractList.IteratorImpl());
   }

   public int indexOf(Object element) {
      int $i$f$indexOfFirst = false;
      int index$iv = 0;
      Iterator var5 = this.iterator();

      int var10000;
      while(true) {
         if (!var5.hasNext()) {
            var10000 = -1;
            break;
         }

         Object item$iv = var5.next();
         int var8 = false;
         if (Intrinsics.areEqual(item$iv, element)) {
            var10000 = index$iv;
            break;
         }

         ++index$iv;
      }

      return var10000;
   }

   public int lastIndexOf(Object element) {
      int $i$f$indexOfLast = false;
      ListIterator iterator$iv = this.listIterator(this.size());

      int var10000;
      while(true) {
         if (iterator$iv.hasPrevious()) {
            Object it = iterator$iv.previous();
            int var6 = false;
            if (!Intrinsics.areEqual(it, element)) {
               continue;
            }

            var10000 = iterator$iv.nextIndex();
            break;
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   @NotNull
   public ListIterator<E> listIterator() {
      return (ListIterator)(new AbstractList.ListIteratorImpl(0));
   }

   @NotNull
   public ListIterator<E> listIterator(int index) {
      return (ListIterator)(new AbstractList.ListIteratorImpl(index));
   }

   @NotNull
   public List<E> subList(int fromIndex, int toIndex) {
      return (List)(new AbstractList.SubList(this, fromIndex, toIndex));
   }

   public boolean equals(@Nullable Object other) {
      if (other == (AbstractList)this) {
         return true;
      } else {
         return !(other instanceof List) ? false : Companion.orderedEquals$kotlin_stdlib((Collection)this, (Collection)other);
      }
   }

   public int hashCode() {
      return Companion.orderedHashCode$kotlin_stdlib((Collection)this);
   }

   protected AbstractList() {
   }

   public void add(int var1, E var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(int var1, Collection<? extends E> var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public E remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public E set(int var1, E var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B#\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u0016\u0010\u000e\u001a\u00028\u00012\u0006\u0010\u000f\u001a\u00020\u0007H\u0096\u0002¢\u0006\u0002\u0010\u0010R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0011"},
      d2 = {"Lkotlin/collections/AbstractList$SubList;", "E", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "list", "fromIndex", "", "toIndex", "(Lkotlin/collections/AbstractList;II)V", "_size", "size", "getSize", "()I", "get", "index", "(I)Ljava/lang/Object;", "kotlin-stdlib"}
   )
   private static final class SubList<E> extends AbstractList<E> implements RandomAccess {
      private int _size;
      private final AbstractList<E> list;
      private final int fromIndex;

      public E get(int index) {
         AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this._size);
         return this.list.get(this.fromIndex + index);
      }

      public int getSize() {
         return this._size;
      }

      public SubList(@NotNull AbstractList<? extends E> list, int fromIndex, int toIndex) {
         Intrinsics.checkParameterIsNotNull(list, "list");
         super();
         this.list = list;
         this.fromIndex = fromIndex;
         AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(this.fromIndex, toIndex, this.list.size());
         this._size = toIndex - this.fromIndex;
      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0092\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\t\u0010\t\u001a\u00020\nH\u0096\u0002J\u000e\u0010\u000b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\fR\u001a\u0010\u0003\u001a\u00020\u0004X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\r"},
      d2 = {"Lkotlin/collections/AbstractList$IteratorImpl;", "", "(Lkotlin/collections/AbstractList;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "hasNext", "", "next", "()Ljava/lang/Object;", "kotlin-stdlib"}
   )
   private class IteratorImpl implements Iterator<E>, KMappedMarker {
      private int index;

      protected final int getIndex() {
         return this.index;
      }

      protected final void setIndex(int var1) {
         this.index = var1;
      }

      public boolean hasNext() {
         return this.index < AbstractList.this.size();
      }

      public E next() {
         if (!this.hasNext()) {
            throw (Throwable)(new NoSuchElementException());
         } else {
            AbstractList var10000 = AbstractList.this;
            int var1;
            this.index = (var1 = this.index) + 1;
            return var10000.get(var1);
         }
      }

      public IteratorImpl() {
      }

      public void remove() {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010*\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0092\u0004\u0018\u00002\f0\u0001R\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\u0005H\u0016J\r\u0010\n\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\u0005H\u0016¨\u0006\r"},
      d2 = {"Lkotlin/collections/AbstractList$ListIteratorImpl;", "Lkotlin/collections/AbstractList$IteratorImpl;", "Lkotlin/collections/AbstractList;", "", "index", "", "(Lkotlin/collections/AbstractList;I)V", "hasPrevious", "", "nextIndex", "previous", "()Ljava/lang/Object;", "previousIndex", "kotlin-stdlib"}
   )
   private class ListIteratorImpl extends AbstractList<E>.IteratorImpl implements ListIterator<E>, KMappedMarker {
      public boolean hasPrevious() {
         return this.getIndex() > 0;
      }

      public int nextIndex() {
         return this.getIndex();
      }

      public E previous() {
         if (!this.hasPrevious()) {
            throw (Throwable)(new NoSuchElementException());
         } else {
            AbstractList var10000 = AbstractList.this;
            this.setIndex(this.getIndex() + -1);
            return var10000.get(this.getIndex());
         }
      }

      public int previousIndex() {
         return this.getIndex() - 1;
      }

      public ListIteratorImpl(int index) {
         super();
         AbstractList.Companion.checkPositionIndex$kotlin_stdlib(index, AbstractList.this.size());
         this.setIndex(index);
      }

      public void add(E var1) {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }

      public void set(E var1) {
         throw new UnsupportedOperationException("Operation is not supported for read-only collection");
      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\tJ\u001d\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\fJ\u001d\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u000eJ%\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0012J%\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000¢\u0006\u0002\b\u0018J\u0019\u0010\u0019\u001a\u00020\u00062\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0016H\u0000¢\u0006\u0002\b\u001a¨\u0006\u001b"},
      d2 = {"Lkotlin/collections/AbstractList$Companion;", "", "()V", "checkBoundsIndexes", "", "startIndex", "", "endIndex", "size", "checkBoundsIndexes$kotlin_stdlib", "checkElementIndex", "index", "checkElementIndex$kotlin_stdlib", "checkPositionIndex", "checkPositionIndex$kotlin_stdlib", "checkRangeIndexes", "fromIndex", "toIndex", "checkRangeIndexes$kotlin_stdlib", "orderedEquals", "", "c", "", "other", "orderedEquals$kotlin_stdlib", "orderedHashCode", "orderedHashCode$kotlin_stdlib", "kotlin-stdlib"}
   )
   public static final class Companion {
      public final void checkElementIndex$kotlin_stdlib(int index, int size) {
         if (index < 0 || index >= size) {
            throw (Throwable)(new IndexOutOfBoundsException("index: " + index + ", size: " + size));
         }
      }

      public final void checkPositionIndex$kotlin_stdlib(int index, int size) {
         if (index < 0 || index > size) {
            throw (Throwable)(new IndexOutOfBoundsException("index: " + index + ", size: " + size));
         }
      }

      public final void checkRangeIndexes$kotlin_stdlib(int fromIndex, int toIndex, int size) {
         if (fromIndex >= 0 && toIndex <= size) {
            if (fromIndex > toIndex) {
               throw (Throwable)(new IllegalArgumentException("fromIndex: " + fromIndex + " > toIndex: " + toIndex));
            }
         } else {
            throw (Throwable)(new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", size: " + size));
         }
      }

      public final void checkBoundsIndexes$kotlin_stdlib(int startIndex, int endIndex, int size) {
         if (startIndex >= 0 && endIndex <= size) {
            if (startIndex > endIndex) {
               throw (Throwable)(new IllegalArgumentException("startIndex: " + startIndex + " > endIndex: " + endIndex));
            }
         } else {
            throw (Throwable)(new IndexOutOfBoundsException("startIndex: " + startIndex + ", endIndex: " + endIndex + ", size: " + size));
         }
      }

      public final int orderedHashCode$kotlin_stdlib(@NotNull Collection<?> c) {
         Intrinsics.checkParameterIsNotNull(c, "c");
         int hashCode = 1;

         Object e;
         for(Iterator var4 = c.iterator(); var4.hasNext(); hashCode = 31 * hashCode + (e != null ? e.hashCode() : 0)) {
            e = var4.next();
         }

         return hashCode;
      }

      public final boolean orderedEquals$kotlin_stdlib(@NotNull Collection<?> c, @NotNull Collection<?> other) {
         Intrinsics.checkParameterIsNotNull(c, "c");
         Intrinsics.checkParameterIsNotNull(other, "other");
         if (c.size() != other.size()) {
            return false;
         } else {
            Iterator otherIterator = other.iterator();
            Iterator var5 = c.iterator();

            Object elem;
            Object elemOther;
            do {
               if (!var5.hasNext()) {
                  return true;
               }

               elem = var5.next();
               elemOther = otherIterator.next();
            } while(!(Intrinsics.areEqual(elem, elemOther) ^ true));

            return false;
         }
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker $constructor_marker) {
         this();
      }
   }
}
