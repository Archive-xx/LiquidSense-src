package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.jvm.internal.CoroutineImpl;
import kotlin.coroutines.experimental.jvm.internal.CoroutineIntrinsics;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u00002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a:\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u00072\u0010\b\u0004\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fH\u0082\b¢\u0006\u0002\b\r\u001aD\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001a]\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012¢\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001aA\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\t*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001aZ\u0010\u0016\u001a\u0004\u0018\u00010\u0001\"\u0004\b\u0000\u0010\u0011\"\u0004\b\u0001\u0010\t*#\b\u0001\u0012\u0004\u0012\u0002H\u0011\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0012¢\u0006\u0002\b\u00132\u0006\u0010\u0014\u001a\u0002H\u00112\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\t0\u0007H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0018\"\u001a\u0010\u0000\u001a\u00020\u00018FX\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\t¨\u0006\u0019"},
   d2 = {"COROUTINE_SUSPENDED", "", "COROUTINE_SUSPENDED$annotations", "()V", "getCOROUTINE_SUSPENDED", "()Ljava/lang/Object;", "buildContinuationByInvokeCall", "Lkotlin/coroutines/experimental/Continuation;", "", "T", "completion", "block", "Lkotlin/Function0;", "buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnchecked", "Lkotlin/Function1;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Lkotlin/coroutines/experimental/Continuation;", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"},
   xs = "kotlin/coroutines/experimental/intrinsics/IntrinsicsKt"
)
class IntrinsicsKt__IntrinsicsJvmKt {
   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <T> Object startCoroutineUninterceptedOrReturn(@NotNull Function1<? super Continuation<? super T>, ? extends Object> $this$startCoroutineUninterceptedOrReturn, Continuation<? super T> completion) {
      return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUninterceptedOrReturn, 1)).invoke(completion);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @InlineOnly
   private static final <R, T> Object startCoroutineUninterceptedOrReturn(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$startCoroutineUninterceptedOrReturn, R receiver, Continuation<? super T> completion) {
      return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUninterceptedOrReturn, 2)).invoke(receiver, completion);
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <T> Continuation<Unit> createCoroutineUnchecked(@NotNull Function1<? super Continuation<? super T>, ? extends Object> $this$createCoroutineUnchecked, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$createCoroutineUnchecked, "$this$createCoroutineUnchecked");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      Continuation var10000;
      if (!($this$createCoroutineUnchecked instanceof CoroutineImpl)) {
         int $i$f$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt = false;
         IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$1 continuation$iv = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$1(completion, $this$createCoroutineUnchecked, completion);
         var10000 = CoroutineIntrinsics.interceptContinuationIfNeeded(completion.getContext(), (Continuation)continuation$iv);
      } else {
         var10000 = ((CoroutineImpl)$this$createCoroutineUnchecked).create(completion);
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
         }

         var10000 = ((CoroutineImpl)var10000).getFacade();
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.1"
   )
   @NotNull
   public static final <R, T> Continuation<Unit> createCoroutineUnchecked(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$createCoroutineUnchecked, R receiver, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$createCoroutineUnchecked, "$this$createCoroutineUnchecked");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      Continuation var10000;
      if (!($this$createCoroutineUnchecked instanceof CoroutineImpl)) {
         int $i$f$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt = false;
         IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$2 continuation$iv = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$2(completion, $this$createCoroutineUnchecked, receiver, completion);
         var10000 = CoroutineIntrinsics.interceptContinuationIfNeeded(completion.getContext(), (Continuation)continuation$iv);
      } else {
         var10000 = ((CoroutineImpl)$this$createCoroutineUnchecked).create(receiver, completion);
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.jvm.internal.CoroutineImpl");
         }

         var10000 = ((CoroutineImpl)var10000).getFacade();
      }

      return var10000;
   }

   private static final <T> Continuation<Unit> buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt(final Continuation<? super T> completion, final Function0<? extends Object> block) {
      int $i$f$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt = 0;
      <undefinedtype> continuation = new Continuation<Unit>() {
         @NotNull
         public CoroutineContext getContext() {
            return completion.getContext();
         }

         public void resume(@NotNull Unit value) {
            Intrinsics.checkParameterIsNotNull(value, "value");
            Continuation var2 = completion;
            boolean var3 = false;

            try {
               Object var4 = block.invoke();
               if (var4 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                  if (var2 == null) {
                     throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
                  }

                  var2.resume(var4);
               }
            } catch (Throwable var5) {
               var2.resumeWithException(var5);
            }

         }

         public void resumeWithException(@NotNull Throwable exception) {
            Intrinsics.checkParameterIsNotNull(exception, "exception");
            completion.resumeWithException(exception);
         }
      };
      return CoroutineIntrinsics.interceptContinuationIfNeeded(completion.getContext(), (Continuation)continuation);
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.1"
   )
   public static void COROUTINE_SUSPENDED$annotations() {
   }

   @NotNull
   public static final Object getCOROUTINE_SUSPENDED() {
      return kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED();
   }

   public IntrinsicsKt__IntrinsicsJvmKt() {
   }
}
