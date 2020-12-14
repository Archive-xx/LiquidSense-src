package kotlin.coroutines.experimental.jvm.internal;

import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.ContinuationInterceptor;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0000\u001a \u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001¨\u0006\u0007"},
   d2 = {"interceptContinuationIfNeeded", "Lkotlin/coroutines/experimental/Continuation;", "T", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "continuation", "normalizeContinuation", "kotlin-stdlib-coroutines"}
)
@JvmName(
   name = "CoroutineIntrinsics"
)
public final class CoroutineIntrinsics {
   @NotNull
   public static final <T> Continuation<T> normalizeContinuation(@NotNull Continuation<? super T> continuation) {
      Intrinsics.checkParameterIsNotNull(continuation, "continuation");
      Continuation var10000 = continuation;
      if (!(continuation instanceof CoroutineImpl)) {
         var10000 = null;
      }

      CoroutineImpl var1 = (CoroutineImpl)var10000;
      if (var1 != null) {
         var10000 = var1.getFacade();
         if (var10000 != null) {
            return var10000;
         }
      }

      var10000 = continuation;
      return var10000;
   }

   @NotNull
   public static final <T> Continuation<T> interceptContinuationIfNeeded(@NotNull CoroutineContext context, @NotNull Continuation<? super T> continuation) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      Intrinsics.checkParameterIsNotNull(continuation, "continuation");
      ContinuationInterceptor var10000 = (ContinuationInterceptor)context.get((CoroutineContext.Key)ContinuationInterceptor.Key);
      Continuation var2;
      if (var10000 != null) {
         var2 = var10000.interceptContinuation(continuation);
         if (var2 != null) {
            return var2;
         }
      }

      var2 = continuation;
      return var2;
   }
}
