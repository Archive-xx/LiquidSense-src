package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\u0000\u001aH\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u0006\"\u0004\b\u0000\u0010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\b0\u00062\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000\u001aD\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u00070\u000e\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u000e2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0000¨\u0006\u000f"},
   d2 = {"checkWindowSizeStep", "", "size", "", "step", "windowedIterator", "", "", "T", "iterator", "partialWindows", "", "reuseBuffer", "windowedSequence", "Lkotlin/sequences/Sequence;", "kotlin-stdlib"}
)
public final class SlidingWindowKt {
   public static final void checkWindowSizeStep(int size, int step) {
      boolean var2 = size > 0 && step > 0;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var5 = false;
         String var6 = size != step ? "Both size " + size + " and step " + step + " must be greater than zero." : "size " + size + " must be greater than zero.";
         throw (Throwable)(new IllegalArgumentException(var6.toString()));
      }
   }

   @NotNull
   public static final <T> Sequence<List<T>> windowedSequence(@NotNull Sequence<? extends T> $this$windowedSequence, int size, int step, boolean partialWindows, boolean reuseBuffer) {
      Intrinsics.checkParameterIsNotNull($this$windowedSequence, "$this$windowedSequence");
      checkWindowSizeStep(size, step);
      boolean var5 = false;
      return (Sequence)(new SlidingWindowKt$windowedSequence$$inlined$Sequence$1($this$windowedSequence, size, step, partialWindows, reuseBuffer));
   }

   @NotNull
   public static final <T> Iterator<List<T>> windowedIterator(@NotNull final Iterator<? extends T> iterator, final int size, final int step, final boolean partialWindows, final boolean reuseBuffer) {
      Intrinsics.checkParameterIsNotNull(iterator, "iterator");
      return !iterator.hasNext() ? (Iterator)EmptyIterator.INSTANCE : SequencesKt.iterator((Function2)(new Function2<SequenceScope<? super List<? extends T>>, Continuation<? super Unit>, Object>((Continuation)null) {
         private SequenceScope p$;
         Object L$0;
         Object L$1;
         Object L$2;
         Object L$3;
         int I$0;
         int I$1;
         int I$2;
         int label;

         @Nullable
         public final Object invokeSuspend(@NotNull Object $result) {
            SequenceScope $this$iterator;
            int bufferInitialCapacity;
            int gap;
            RingBuffer bufferx;
            Object var11;
            List var10001;
            label172: {
               Object ex;
               Iterator var7;
               label192: {
                  var11 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                  Iterator var8;
                  ArrayList buffer;
                  int skip;
                  Object e;
                  switch(this.label) {
                  case 0:
                     ResultKt.throwOnFailure($result);
                     $this$iterator = this.p$;
                     bufferInitialCapacity = RangesKt.coerceAtMost(size, 1024);
                     gap = step - size;
                     if (gap < 0) {
                        bufferx = new RingBuffer(bufferInitialCapacity);
                        var8 = iterator;
                        boolean var18 = false;
                        var7 = var8;
                        break label192;
                     }

                     buffer = new ArrayList(bufferInitialCapacity);
                     skip = 0;
                     Iterator var9 = iterator;
                     boolean var10 = false;
                     var8 = var9;
                     break;
                  case 1:
                     var8 = (Iterator)this.L$3;
                     e = this.L$2;
                     skip = this.I$2;
                     buffer = (ArrayList)this.L$1;
                     gap = this.I$1;
                     bufferInitialCapacity = this.I$0;
                     $this$iterator = (SequenceScope)this.L$0;
                     ResultKt.throwOnFailure($result);
                     if (reuseBuffer) {
                        buffer.clear();
                     } else {
                        buffer = new ArrayList(size);
                     }

                     skip = gap;
                     break;
                  case 2:
                     skip = this.I$2;
                     buffer = (ArrayList)this.L$1;
                     gap = this.I$1;
                     bufferInitialCapacity = this.I$0;
                     $this$iterator = (SequenceScope)this.L$0;
                     ResultKt.throwOnFailure($result);
                     return Unit.INSTANCE;
                  case 3:
                     var7 = (Iterator)this.L$3;
                     ex = this.L$2;
                     bufferx = (RingBuffer)this.L$1;
                     gap = this.I$1;
                     bufferInitialCapacity = this.I$0;
                     $this$iterator = (SequenceScope)this.L$0;
                     ResultKt.throwOnFailure($result);
                     bufferx.removeFirst(step);
                     break label192;
                  case 4:
                     bufferx = (RingBuffer)this.L$1;
                     gap = this.I$1;
                     bufferInitialCapacity = this.I$0;
                     $this$iterator = (SequenceScope)this.L$0;
                     ResultKt.throwOnFailure($result);
                     bufferx.removeFirst(step);
                     break label172;
                  case 5:
                     bufferx = (RingBuffer)this.L$1;
                     gap = this.I$1;
                     bufferInitialCapacity = this.I$0;
                     $this$iterator = (SequenceScope)this.L$0;
                     ResultKt.throwOnFailure($result);
                     return Unit.INSTANCE;
                  default:
                     throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                  }

                  while(var8.hasNext()) {
                     e = var8.next();
                     if (skip > 0) {
                        --skip;
                     } else {
                        buffer.add(e);
                        if (buffer.size() == size) {
                           this.L$0 = $this$iterator;
                           this.I$0 = bufferInitialCapacity;
                           this.I$1 = gap;
                           this.L$1 = buffer;
                           this.I$2 = skip;
                           this.L$2 = e;
                           this.L$3 = var8;
                           this.label = 1;
                           if ($this$iterator.yield(buffer, this) == var11) {
                              return var11;
                           }

                           if (reuseBuffer) {
                              buffer.clear();
                           } else {
                              buffer = new ArrayList(size);
                           }

                           skip = gap;
                        }
                     }
                  }

                  Collection var15 = (Collection)buffer;
                  boolean var17 = false;
                  if (!var15.isEmpty() && (partialWindows || buffer.size() == size)) {
                     this.L$0 = $this$iterator;
                     this.I$0 = bufferInitialCapacity;
                     this.I$1 = gap;
                     this.L$1 = buffer;
                     this.I$2 = skip;
                     this.label = 2;
                     if ($this$iterator.yield(buffer, this) == var11) {
                        return var11;
                     }
                  }

                  return Unit.INSTANCE;
               }

               while(var7.hasNext()) {
                  ex = var7.next();
                  bufferx.add(ex);
                  if (bufferx.isFull()) {
                     if (bufferx.size() < size) {
                        bufferx = bufferx.expanded(size);
                     } else {
                        var10001 = reuseBuffer ? (List)bufferx : (List)(new ArrayList((Collection)bufferx));
                        this.L$0 = $this$iterator;
                        this.I$0 = bufferInitialCapacity;
                        this.I$1 = gap;
                        this.L$1 = bufferx;
                        this.L$2 = ex;
                        this.L$3 = var7;
                        this.label = 3;
                        if ($this$iterator.yield(var10001, this) == var11) {
                           return var11;
                        }

                        bufferx.removeFirst(step);
                     }
                  }
               }

               if (!partialWindows) {
                  return Unit.INSTANCE;
               }
            }

            while(bufferx.size() > step) {
               var10001 = reuseBuffer ? (List)bufferx : (List)(new ArrayList((Collection)bufferx));
               this.L$0 = $this$iterator;
               this.I$0 = bufferInitialCapacity;
               this.I$1 = gap;
               this.L$1 = bufferx;
               this.label = 4;
               if ($this$iterator.yield(var10001, this) == var11) {
                  return var11;
               }

               bufferx.removeFirst(step);
            }

            Collection var19 = (Collection)bufferx;
            boolean var16 = false;
            if (!var19.isEmpty()) {
               this.L$0 = $this$iterator;
               this.I$0 = bufferInitialCapacity;
               this.I$1 = gap;
               this.L$1 = bufferx;
               this.label = 5;
               if ($this$iterator.yield(bufferx, this) == var11) {
                  return var11;
               }
            }

            return Unit.INSTANCE;
         }

         @NotNull
         public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation<?> completion) {
            Intrinsics.checkParameterIsNotNull(completion, "completion");
            Function2 var3 = new <anonymous constructor>(completion);
            var3.p$ = (SequenceScope)value;
            return var3;
         }

         public final Object invoke(Object var1, Object var2) {
            return ((<undefinedtype>)this.create(var1, (Continuation)var2)).invokeSuspend(Unit.INSTANCE);
         }
      }));
   }
}
