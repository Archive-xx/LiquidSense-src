package kotlin.sequences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u001c\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0014\b\u0004\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004H\u0087\b\u001a\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002\u001a&\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0004\u001a<\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00042\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000b\u001a=\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\b2\b\u0010\f\u001a\u0004\u0018\u0001H\u00022\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u000bH\u0007¢\u0006\u0002\u0010\r\u001a+\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0012\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010\"\u0002H\u0002¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0005\u001a\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001\u001aC\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0018\u0010\u0003\u001a\u0014\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u00050\u000bH\u0002¢\u0006\u0002\b\u0016\u001a)\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00170\u0001H\u0007¢\u0006\u0002\b\u0018\u001a\"\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0001\u001a2\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0012\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0004H\u0007\u001a!\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0001H\u0087\b\u001a@\u0010\u001c\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001e0\u001d\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0015*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00150\u001d0\u0001¨\u0006\u001f"},
   d2 = {"Sequence", "Lkotlin/sequences/Sequence;", "T", "iterator", "Lkotlin/Function0;", "", "emptySequence", "generateSequence", "", "nextFunction", "seedFunction", "Lkotlin/Function1;", "seed", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Lkotlin/sequences/Sequence;", "sequenceOf", "elements", "", "([Ljava/lang/Object;)Lkotlin/sequences/Sequence;", "asSequence", "constrainOnce", "flatten", "R", "flatten$SequencesKt__SequencesKt", "", "flattenSequenceOfIterable", "ifEmpty", "defaultValue", "orEmpty", "unzip", "Lkotlin/Pair;", "", "kotlin-stdlib"},
   xs = "kotlin/sequences/SequencesKt"
)
class SequencesKt__SequencesKt extends SequencesKt__SequencesJVMKt {
   @InlineOnly
   private static final <T> Sequence<T> Sequence(final Function0<? extends Iterator<? extends T>> iterator) {
      int $i$f$Sequence = 0;
      return (Sequence)(new Sequence<T>() {
         @NotNull
         public Iterator<T> iterator() {
            return (Iterator)iterator.invoke();
         }
      });
   }

   @NotNull
   public static final <T> Sequence<T> asSequence(@NotNull Iterator<? extends T> $this$asSequence) {
      Intrinsics.checkParameterIsNotNull($this$asSequence, "$this$asSequence");
      boolean var1 = false;
      return SequencesKt.constrainOnce((Sequence)(new SequencesKt__SequencesKt$asSequence$$inlined$Sequence$1($this$asSequence)));
   }

   @NotNull
   public static final <T> Sequence<T> sequenceOf(@NotNull T... elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      boolean var2 = false;
      return elements.length == 0 ? SequencesKt.emptySequence() : ArraysKt.asSequence(elements);
   }

   @NotNull
   public static final <T> Sequence<T> emptySequence() {
      return (Sequence)EmptySequence.INSTANCE;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <T> Sequence<T> orEmpty(@Nullable Sequence<? extends T> $this$orEmpty) {
      int $i$f$orEmpty = 0;
      Sequence var10000 = $this$orEmpty;
      if ($this$orEmpty == null) {
         var10000 = SequencesKt.emptySequence();
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <T> Sequence<T> ifEmpty(@NotNull final Sequence<? extends T> $this$ifEmpty, @NotNull final Function0<? extends Sequence<? extends T>> defaultValue) {
      Intrinsics.checkParameterIsNotNull($this$ifEmpty, "$this$ifEmpty");
      Intrinsics.checkParameterIsNotNull(defaultValue, "defaultValue");
      return SequencesKt.sequence((Function2)(new Function2<SequenceScope<? super T>, Continuation<? super Unit>, Object>((Continuation)null) {
         private SequenceScope p$;
         Object L$0;
         Object L$1;
         int label;

         @Nullable
         public final Object invokeSuspend(@NotNull Object $result) {
            Object var4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            SequenceScope $this$sequence;
            Iterator iterator;
            switch(this.label) {
            case 0:
               ResultKt.throwOnFailure($result);
               $this$sequence = this.p$;
               iterator = $this$ifEmpty.iterator();
               if (iterator.hasNext()) {
                  this.L$0 = $this$sequence;
                  this.L$1 = iterator;
                  this.label = 1;
                  if ($this$sequence.yieldAll((Iterator)iterator, this) == var4) {
                     return var4;
                  }
               } else {
                  Sequence var10001 = (Sequence)defaultValue.invoke();
                  this.L$0 = $this$sequence;
                  this.L$1 = iterator;
                  this.label = 2;
                  if ($this$sequence.yieldAll((Sequence)var10001, this) == var4) {
                     return var4;
                  }
               }
               break;
            case 1:
               iterator = (Iterator)this.L$1;
               $this$sequence = (SequenceScope)this.L$0;
               ResultKt.throwOnFailure($result);
               break;
            case 2:
               iterator = (Iterator)this.L$1;
               $this$sequence = (SequenceScope)this.L$0;
               ResultKt.throwOnFailure($result);
               break;
            default:
               throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
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

   @NotNull
   public static final <T> Sequence<T> flatten(@NotNull Sequence<? extends Sequence<? extends T>> $this$flatten) {
      Intrinsics.checkParameterIsNotNull($this$flatten, "$this$flatten");
      return flatten$SequencesKt__SequencesKt($this$flatten, (Function1)null.INSTANCE);
   }

   @JvmName(
      name = "flattenSequenceOfIterable"
   )
   @NotNull
   public static final <T> Sequence<T> flattenSequenceOfIterable(@NotNull Sequence<? extends Iterable<? extends T>> $this$flatten) {
      Intrinsics.checkParameterIsNotNull($this$flatten, "$this$flatten");
      return flatten$SequencesKt__SequencesKt($this$flatten, (Function1)null.INSTANCE);
   }

   private static final <T, R> Sequence<R> flatten$SequencesKt__SequencesKt(@NotNull Sequence<? extends T> $this$flatten, Function1<? super T, ? extends Iterator<? extends R>> iterator) {
      return $this$flatten instanceof TransformingSequence ? ((TransformingSequence)$this$flatten).flatten$kotlin_stdlib(iterator) : (Sequence)(new FlatteningSequence($this$flatten, (Function1)null.INSTANCE, iterator));
   }

   @NotNull
   public static final <T, R> Pair<List<T>, List<R>> unzip(@NotNull Sequence<? extends Pair<? extends T, ? extends R>> $this$unzip) {
      Intrinsics.checkParameterIsNotNull($this$unzip, "$this$unzip");
      ArrayList listT = new ArrayList();
      ArrayList listR = new ArrayList();
      Iterator var4 = $this$unzip.iterator();

      while(var4.hasNext()) {
         Pair pair = (Pair)var4.next();
         listT.add(pair.getFirst());
         listR.add(pair.getSecond());
      }

      return TuplesKt.to(listT, listR);
   }

   @NotNull
   public static final <T> Sequence<T> constrainOnce(@NotNull Sequence<? extends T> $this$constrainOnce) {
      Intrinsics.checkParameterIsNotNull($this$constrainOnce, "$this$constrainOnce");
      return $this$constrainOnce instanceof ConstrainedOnceSequence ? $this$constrainOnce : (Sequence)(new ConstrainedOnceSequence($this$constrainOnce));
   }

   @NotNull
   public static final <T> Sequence<T> generateSequence(@NotNull final Function0<? extends T> nextFunction) {
      Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
      return SequencesKt.constrainOnce((Sequence)(new GeneratorSequence(nextFunction, (Function1)(new Function1<T, T>() {
         @Nullable
         public final T invoke(@NotNull T it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            return nextFunction.invoke();
         }
      }))));
   }

   @LowPriorityInOverloadResolution
   @NotNull
   public static final <T> Sequence<T> generateSequence(@Nullable final T seed, @NotNull Function1<? super T, ? extends T> nextFunction) {
      Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
      return seed == null ? (Sequence)EmptySequence.INSTANCE : (Sequence)(new GeneratorSequence((Function0)(new Function0<T>() {
         @Nullable
         public final T invoke() {
            return seed;
         }
      }), nextFunction));
   }

   @NotNull
   public static final <T> Sequence<T> generateSequence(@NotNull Function0<? extends T> seedFunction, @NotNull Function1<? super T, ? extends T> nextFunction) {
      Intrinsics.checkParameterIsNotNull(seedFunction, "seedFunction");
      Intrinsics.checkParameterIsNotNull(nextFunction, "nextFunction");
      return (Sequence)(new GeneratorSequence(seedFunction, nextFunction));
   }

   public SequencesKt__SequencesKt() {
   }
}
