package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007H\u0014ø\u0001\u0000¢\u0006\u0002\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\t¸\u0006\u0000"},
   d2 = {"kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$2", "Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "label", "", "invokeSuspend", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}
)
public final class IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4 extends ContinuationImpl {
   private int label;
   // $FF: synthetic field
   final Continuation $completion;
   // $FF: synthetic field
   final CoroutineContext $context;
   // $FF: synthetic field
   final Function2 $this_createCoroutineUnintercepted$inlined;
   // $FF: synthetic field
   final Object $receiver$inlined;

   public IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4(Continuation $captured_local_variable$1, CoroutineContext $captured_local_variable$2, Continuation $super_call_param$3, CoroutineContext $super_call_param$4, Function2 var5, Object var6) {
      super($super_call_param$3, $super_call_param$4);
      this.$completion = $captured_local_variable$1;
      this.$context = $captured_local_variable$2;
      this.$this_createCoroutineUnintercepted$inlined = var5;
      this.$receiver$inlined = var6;
   }

   @Nullable
   protected Object invokeSuspend(@NotNull Object result) {
      Object var10000;
      boolean var3;
      switch(this.label) {
      case 0:
         this.label = 1;
         var3 = false;
         ResultKt.throwOnFailure(result);
         Continuation it = (Continuation)this;
         int var5 = false;
         Function2 var6 = this.$this_createCoroutineUnintercepted$inlined;
         if (var6 == null) {
            throw new TypeCastException("null cannot be cast to non-null type (R, kotlin.coroutines.Continuation<T>) -> kotlin.Any?");
         }

         var10000 = ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity(var6, 2)).invoke(this.$receiver$inlined, it);
         break;
      case 1:
         this.label = 2;
         var3 = false;
         ResultKt.throwOnFailure(result);
         var10000 = result;
         break;
      default:
         String var2 = "This coroutine had already completed";
         var3 = false;
         throw (Throwable)(new IllegalStateException(var2.toString()));
      }

      return var10000;
   }
}
