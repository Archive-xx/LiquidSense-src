package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00005\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r¸\u0006\u0000"},
   d2 = {"kotlin/coroutines/experimental/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1", "Lkotlin/coroutines/experimental/Continuation;", "", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "resume", "value", "(Lkotlin/Unit;)V", "resumeWithException", "exception", "", "kotlin-stdlib-coroutines"}
)
public final class IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$2 implements Continuation<Unit> {
   // $FF: synthetic field
   final Continuation $completion;
   // $FF: synthetic field
   final Function2 $this_createCoroutineUnchecked$inlined;
   // $FF: synthetic field
   final Object $receiver$inlined;
   // $FF: synthetic field
   final Continuation $completion$inlined;

   public IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$2(Continuation $captured_local_variable$0, Function2 var2, Object var3, Continuation var4) {
      this.$completion = $captured_local_variable$0;
      this.$this_createCoroutineUnchecked$inlined = var2;
      this.$receiver$inlined = var3;
      this.$completion$inlined = var4;
   }

   @NotNull
   public CoroutineContext getContext() {
      return this.$completion.getContext();
   }

   public void resume(@NotNull Unit value) {
      Intrinsics.checkParameterIsNotNull(value, "value");
      Continuation var2 = this.$completion;
      boolean var3 = false;

      try {
         int var4 = false;
         Function2 var10000 = this.$this_createCoroutineUnchecked$inlined;
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
         }

         Object var6 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var10000, 2)).invoke(this.$receiver$inlined, this.$completion$inlined);
         if (var6 != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            if (var2 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
            }

            var2.resume(var6);
         }
      } catch (Throwable var5) {
         var2.resumeWithException(var5);
      }

   }

   public void resumeWithException(@NotNull Throwable exception) {
      Intrinsics.checkParameterIsNotNull(exception, "exception");
      this.$completion.resumeWithException(exception);
   }
}
