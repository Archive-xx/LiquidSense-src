package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\b\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001d\u0012\u000e\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010\fJ\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000¢\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001fH\u0096\u0002J\u000e\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0006J\u0015\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014¢\u0006\u0002\u0010#J'\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\u0004\b\u0001\u0010\u00012\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\u0014¢\u0006\u0002\u0010%J\u0015\u0010&\u001a\u00020\u0006*\u00020\u00062\u0006\u0010!\u001a\u00020\u0006H\u0082\bR\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006'"},
   d2 = {"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "filledSize", "([Ljava/lang/Object;I)V", "[Ljava/lang/Object;", "<set-?>", "size", "getSize", "()I", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "expanded", "maxCapacity", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "forward", "kotlin-stdlib"}
)
final class RingBuffer<T> extends AbstractList<T> implements RandomAccess {
   private final int capacity;
   private int startIndex;
   private int size;
   private final Object[] buffer;

   public int getSize() {
      return this.size;
   }

   public T get(int index) {
      AbstractList.Companion.checkElementIndex$kotlin_stdlib(index, this.size());
      int $this$forward$iv = this.startIndex;
      Object[] var5 = this.buffer;
      int $i$f$forward = false;
      int var6 = ($this$forward$iv + index) % access$getCapacity$p(this);
      return var5[var6];
   }

   public final boolean isFull() {
      return this.size() == this.capacity;
   }

   @NotNull
   public Iterator<T> iterator() {
      return (Iterator)(new AbstractIterator<T>() {
         private int count = RingBuffer.this.size();
         private int index;

         protected void computeNext() {
            if (this.count == 0) {
               this.done();
            } else {
               this.setNext(RingBuffer.this.buffer[this.index]);
               int $this$forward$iv = this.index;
               RingBuffer this_$iv = RingBuffer.this;
               int n$iv = 1;
               int $i$f$forward = false;
               int var6 = ($this$forward$iv + n$iv) % this_$iv.capacity;
               this.index = var6;
               this.count += -1;
            }

         }

         {
            this.index = RingBuffer.this.startIndex;
         }
      });
   }

   @NotNull
   public <T> T[] toArray(@NotNull T[] array) {
      Intrinsics.checkParameterIsNotNull(array, "array");
      Object[] var10000;
      int widx;
      if (array.length < this.size()) {
         widx = this.size();
         boolean var5 = false;
         var10000 = Arrays.copyOf(array, widx);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.copyOf(this, newSize)");
      } else {
         var10000 = array;
      }

      Object[] result = var10000;
      int size = this.size();
      widx = 0;

      int idx;
      for(idx = this.startIndex; widx < size && idx < this.capacity; ++idx) {
         result[widx] = this.buffer[idx];
         ++widx;
      }

      for(idx = 0; widx < size; ++idx) {
         result[widx] = this.buffer[idx];
         ++widx;
      }

      if (result.length > this.size()) {
         result[this.size()] = null;
      }

      return result;
   }

   @NotNull
   public Object[] toArray() {
      return this.toArray(new Object[this.size()]);
   }

   @NotNull
   public final RingBuffer<T> expanded(int maxCapacity) {
      int newCapacity = RangesKt.coerceAtMost(this.capacity + (this.capacity >> 1) + 1, maxCapacity);
      Object[] var10000;
      if (this.startIndex == 0) {
         Object[] var4 = this.buffer;
         boolean var5 = false;
         var10000 = Arrays.copyOf(var4, newCapacity);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.copyOf(this, newSize)");
      } else {
         var10000 = this.toArray(new Object[newCapacity]);
      }

      Object[] newBuffer = var10000;
      return new RingBuffer(newBuffer, this.size());
   }

   public final void add(T element) {
      if (this.isFull()) {
         throw (Throwable)(new IllegalStateException("ring buffer is full"));
      } else {
         Object[] var10000 = this.buffer;
         int $this$forward$iv = this.startIndex;
         int n$iv = this.size();
         Object[] var6 = var10000;
         int $i$f$forward = false;
         int var7 = ($this$forward$iv + n$iv) % access$getCapacity$p(this);
         var6[var7] = element;
         this.size = this.size() + 1;
      }
   }

   public final void removeFirst(int n) {
      boolean var2 = n >= 0;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5;
      String var9;
      if (!var2) {
         var5 = false;
         var9 = "n shouldn't be negative but it is " + n;
         throw (Throwable)(new IllegalArgumentException(var9.toString()));
      } else {
         var2 = n <= this.size();
         var3 = false;
         var4 = false;
         if (!var2) {
            var5 = false;
            var9 = "n shouldn't be greater than the buffer size: n = " + n + ", size = " + this.size();
            throw (Throwable)(new IllegalArgumentException(var9.toString()));
         } else {
            if (n > 0) {
               int start = this.startIndex;
               int $i$f$forward = false;
               int end = (start + n) % access$getCapacity$p(this);
               if (start > end) {
                  ArraysKt.fill(this.buffer, (Object)null, start, this.capacity);
                  ArraysKt.fill(this.buffer, (Object)null, 0, end);
               } else {
                  ArraysKt.fill(this.buffer, (Object)null, start, end);
               }

               this.startIndex = end;
               this.size = this.size() - n;
            }

         }
      }
   }

   private final int forward(int $this$forward, int n) {
      int $i$f$forward = 0;
      return ($this$forward + n) % access$getCapacity$p(this);
   }

   public RingBuffer(@NotNull Object[] buffer, int filledSize) {
      Intrinsics.checkParameterIsNotNull(buffer, "buffer");
      super();
      this.buffer = buffer;
      boolean var3 = filledSize >= 0;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6;
      String var7;
      if (!var3) {
         var6 = false;
         var7 = "ring buffer filled size should not be negative but it is " + filledSize;
         throw (Throwable)(new IllegalArgumentException(var7.toString()));
      } else {
         var3 = filledSize <= this.buffer.length;
         var4 = false;
         var5 = false;
         if (!var3) {
            var6 = false;
            var7 = "ring buffer filled size: " + filledSize + " cannot be larger than the buffer size: " + this.buffer.length;
            throw (Throwable)(new IllegalArgumentException(var7.toString()));
         } else {
            this.capacity = this.buffer.length;
            this.size = filledSize;
         }
      }
   }

   public RingBuffer(int capacity) {
      this(new Object[capacity], 0);
   }

   // $FF: synthetic method
   public static final int access$forward(RingBuffer $this, int $this$access_u24forward, int n) {
      return $this.forward($this$access_u24forward, n);
   }

   // $FF: synthetic method
   public static final int access$getSize$p(RingBuffer $this) {
      return $this.size();
   }

   // $FF: synthetic method
   public static final void access$setSize$p(RingBuffer $this, int var1) {
      $this.size = var1;
   }

   // $FF: synthetic method
   public static final void access$setStartIndex$p(RingBuffer $this, int var1) {
      $this.startIndex = var1;
   }
}
