package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\u0002\u0010\u0005J\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"},
   d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalContinuationMigration;", "T", "Lkotlin/coroutines/experimental/Continuation;", "continuation", "Lkotlin/coroutines/Continuation;", "(Lkotlin/coroutines/Continuation;)V", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "getContinuation", "()Lkotlin/coroutines/Continuation;", "resume", "", "value", "(Ljava/lang/Object;)V", "resumeWithException", "exception", "", "kotlin-stdlib-coroutines"}
)
final class ExperimentalContinuationMigration<T> implements Continuation<T> {
   @NotNull
   private final CoroutineContext context;
   @NotNull
   private final kotlin.coroutines.Continuation<T> continuation;

   @NotNull
   public CoroutineContext getContext() {
      return this.context;
   }

   public void resume(T value) {
      kotlin.coroutines.Continuation var2 = this.continuation;
      boolean var3 = false;
      Result.Companion var4 = Result.Companion;
      boolean var6 = false;
      Object var7 = Result.constructor-impl(value);
      var2.resumeWith(var7);
   }

   public void resumeWithException(@NotNull Throwable exception) {
      Intrinsics.checkParameterIsNotNull(exception, "exception");
      kotlin.coroutines.Continuation var2 = this.continuation;
      boolean var3 = false;
      Result.Companion var4 = Result.Companion;
      boolean var6 = false;
      Object var7 = Result.constructor-impl(ResultKt.createFailure(exception));
      var2.resumeWith(var7);
   }

   @NotNull
   public final kotlin.coroutines.Continuation<T> getContinuation() {
      return this.continuation;
   }

   public ExperimentalContinuationMigration(@NotNull kotlin.coroutines.Continuation<? super T> continuation) {
      Intrinsics.checkParameterIsNotNull(continuation, "continuation");
      super();
      this.continuation = continuation;
      this.context = CoroutinesMigrationKt.toExperimentalCoroutineContext(this.continuation.getContext());
   }
}
