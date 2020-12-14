package kotlin;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import kotlin.collections.ArraysKt;
import kotlin.collections.UByteIterator;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087@\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001-B\u0014\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006B\u0014\b\u0001\u0012\u0006\u0010\u0007\u001a\u00020\bø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\tJ\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0002H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J \u0010\u0013\u001a\u00020\u000f2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016ø\u0001\u0000¢\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u000f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\u001b\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\t\u0010\u001e\u001a\u00020\u0004HÖ\u0001J\u000f\u0010\u001f\u001a\u00020\u000fH\u0016¢\u0006\u0004\b \u0010!J\u0010\u0010\"\u001a\u00020#H\u0096\u0002¢\u0006\u0004\b$\u0010%J#\u0010&\u001a\u00020'2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0002H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b)\u0010*J\t\u0010+\u001a\u00020,HÖ\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0007\u001a\u00020\b8\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006."},
   d2 = {"Lkotlin/UByteArray;", "", "Lkotlin/UByte;", "size", "", "constructor-impl", "(I)[B", "storage", "", "([B)[B", "getSize-impl", "([B)I", "storage$annotations", "()V", "contains", "", "element", "contains-7apg3OU", "([BB)Z", "containsAll", "elements", "containsAll-impl", "([BLjava/util/Collection;)Z", "equals", "other", "", "get", "index", "get-impl", "([BI)B", "hashCode", "isEmpty", "isEmpty-impl", "([B)Z", "iterator", "Lkotlin/collections/UByteIterator;", "iterator-impl", "([B)Lkotlin/collections/UByteIterator;", "set", "", "value", "set-VurrAj0", "([BIB)V", "toString", "", "Iterator", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalUnsignedTypes
public final class UByteArray implements Collection<UByte>, KMappedMarker {
   @NotNull
   private final byte[] storage;

   public int getSize() {
      return getSize-impl(this.storage);
   }

   @NotNull
   public UByteIterator iterator() {
      return iterator-impl(this.storage);
   }

   public boolean contains_7apg3OU/* $FF was: contains-7apg3OU*/(byte var1) {
      return contains-7apg3OU(this.storage, var1);
   }

   public boolean containsAll(@NotNull Collection<? extends Object> var1) {
      return containsAll-impl(this.storage, var1);
   }

   public boolean isEmpty() {
      return isEmpty-impl(this.storage);
   }

   /** @deprecated */
   // $FF: synthetic method
   @PublishedApi
   public static void storage$annotations() {
   }

   // $FF: synthetic method
   @PublishedApi
   private UByteArray(@NotNull byte[] storage) {
      Intrinsics.checkParameterIsNotNull(storage, "storage");
      super();
      this.storage = storage;
   }

   public static final byte get_impl/* $FF was: get-impl*/(byte[] $this, int index) {
      byte var2 = $this[index];
      boolean var3 = false;
      return UByte.constructor-impl(var2);
   }

   public static final void set_VurrAj0/* $FF was: set-VurrAj0*/(byte[] $this, int index, byte value) {
      boolean var4 = false;
      $this[index] = value;
   }

   public static int getSize_impl/* $FF was: getSize-impl*/(byte[] $this) {
      return $this.length;
   }

   @NotNull
   public static UByteIterator iterator_impl/* $FF was: iterator-impl*/(byte[] $this) {
      return (UByteIterator)(new UByteArray.Iterator($this));
   }

   public static boolean contains_7apg3OU/* $FF was: contains-7apg3OU*/(byte[] $this, byte element) {
      boolean var3 = false;
      return ArraysKt.contains($this, element);
   }

   public static boolean containsAll_impl/* $FF was: containsAll-impl*/(byte[] $this, @NotNull Collection<UByte> elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      Iterable $this$all$iv = (Iterable)elements;
      int $i$f$all = false;
      boolean var10000;
      if (((Collection)$this$all$iv).isEmpty()) {
         var10000 = true;
      } else {
         java.util.Iterator var4 = $this$all$iv.iterator();

         while(true) {
            if (!var4.hasNext()) {
               var10000 = true;
               break;
            }

            label22: {
               Object element$iv = var4.next();
               int var7 = false;
               if (element$iv instanceof UByte) {
                  byte var8 = ((UByte)element$iv).unbox-impl();
                  boolean var10 = false;
                  if (ArraysKt.contains($this, var8)) {
                     var10000 = true;
                     break label22;
                  }
               }

               var10000 = false;
            }

            if (!var10000) {
               var10000 = false;
               break;
            }
         }
      }

      return var10000;
   }

   public static boolean isEmpty_impl/* $FF was: isEmpty-impl*/(byte[] $this) {
      return $this.length == 0;
   }

   @PublishedApi
   @NotNull
   public static byte[] constructor_impl/* $FF was: constructor-impl*/(@NotNull byte[] storage) {
      Intrinsics.checkParameterIsNotNull(storage, "storage");
      return storage;
   }

   @NotNull
   public static byte[] constructor_impl/* $FF was: constructor-impl*/(int size) {
      return constructor-impl(new byte[size]);
   }

   // $FF: synthetic method
   @NotNull
   public static final UByteArray box_impl/* $FF was: box-impl*/(@NotNull byte[] v) {
      Intrinsics.checkParameterIsNotNull(v, "v");
      return new UByteArray(v);
   }

   @NotNull
   public static String toString_impl/* $FF was: toString-impl*/(byte[] var0) {
      return "UByteArray(storage=" + Arrays.toString(var0) + ")";
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(byte[] var0) {
      return var0 != null ? Arrays.hashCode(var0) : 0;
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(byte[] var0, @Nullable Object var1) {
      if (var1 instanceof UByteArray) {
         byte[] var2 = ((UByteArray)var1).unbox-impl();
         if (Intrinsics.areEqual((Object)var0, (Object)var2)) {
            return true;
         }
      }

      return false;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(@NotNull byte[] p1, @NotNull byte[] p2) {
      Intrinsics.checkParameterIsNotNull(p1, "p1");
      Intrinsics.checkParameterIsNotNull(p2, "p2");
      throw null;
   }

   // $FF: synthetic method
   @NotNull
   public final byte[] unbox_impl/* $FF was: unbox-impl*/() {
      return this.storage;
   }

   public String toString() {
      return toString-impl(this.storage);
   }

   public int hashCode() {
      return hashCode-impl(this.storage);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.storage, var1);
   }

   public boolean add_7apg3OU/* $FF was: add-7apg3OU*/(byte var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(Collection<? extends UByte> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean removeAll(Collection<? extends Object> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean retainAll(Collection<? extends Object> var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   // $FF: synthetic method
   public boolean add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   public <T> T[] toArray(T[] var1) {
      return CollectionToArray.toArray(this, var1);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u0010\u0010\t\u001a\u00020\nH\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\f"},
      d2 = {"Lkotlin/UByteArray$Iterator;", "Lkotlin/collections/UByteIterator;", "array", "", "([B)V", "index", "", "hasNext", "", "nextUByte", "Lkotlin/UByte;", "()B", "kotlin-stdlib"}
   )
   private static final class Iterator extends UByteIterator {
      private int index;
      private final byte[] array;

      public boolean hasNext() {
         return this.index < this.array.length;
      }

      public byte nextUByte() {
         if (this.index < this.array.length) {
            byte[] var10000 = this.array;
            int var1;
            this.index = (var1 = this.index) + 1;
            byte var3 = var10000[var1];
            boolean var2 = false;
            return UByte.constructor-impl(var3);
         } else {
            throw (Throwable)(new NoSuchElementException(String.valueOf(this.index)));
         }
      }

      public Iterator(@NotNull byte[] array) {
         Intrinsics.checkParameterIsNotNull(array, "array");
         super();
         this.array = array;
      }
   }
}
