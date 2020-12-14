package kotlin.coroutines;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0002\b\u0004\u001a<\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\u0006\u0010\t\u001a\u00020\u00012\u001a\b\u0004\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\f\u0012\u0004\u0012\u00020\r0\u000bH\u0087\bø\u0001\u0000\u001a3\u0010\u000e\u001a\u0002H\b\"\u0004\b\u0000\u0010\b2\u001a\b\u0004\u0010\u000f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0004\u0012\u00020\r0\u000bH\u0087Hø\u0001\u0000¢\u0006\u0002\u0010\u0010\u001aD\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\"\u0004\b\u0000\u0010\b*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a]\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0007\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\b*#\b\u0001\u0012\u0004\u0012\u0002H\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0016¢\u0006\u0002\b\u00172\u0006\u0010\u0018\u001a\u0002H\u00152\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a&\u0010\u001a\u001a\u00020\r\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00072\u0006\u0010\u001b\u001a\u0002H\bH\u0087\b¢\u0006\u0002\u0010\u001c\u001a!\u0010\u001d\u001a\u00020\r\"\u0004\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\u00072\u0006\u0010\u001e\u001a\u00020\u001fH\u0087\b\u001a>\u0010 \u001a\u00020\r\"\u0004\b\u0000\u0010\b*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010!\u001aW\u0010 \u001a\u00020\r\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010\b*#\b\u0001\u0012\u0004\u0012\u0002H\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\b0\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0016¢\u0006\u0002\b\u00172\u0006\u0010\u0018\u001a\u0002H\u00152\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\"\"\u001b\u0010\u0000\u001a\u00020\u00018Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006#"},
   d2 = {"coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "coroutineContext$annotations", "()V", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "Continuation", "Lkotlin/coroutines/Continuation;", "T", "context", "resumeWith", "Lkotlin/Function1;", "Lkotlin/Result;", "", "suspendCoroutine", "block", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createCoroutine", "", "completion", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "resume", "value", "(Lkotlin/coroutines/Continuation;Ljava/lang/Object;)V", "resumeWithException", "exception", "", "startCoroutine", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)V", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)V", "kotlin-stdlib"}
)
public final class ContinuationKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <T> void resume(@NotNull Continuation<? super T> $this$resume, T value) {
      int $i$f$resume = 0;
      Result.Companion var3 = Result.Companion;
      boolean var4 = false;
      Object var6 = Result.constructor-impl(value);
      $this$resume.resumeWith(var6);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <T> void resumeWithException(@NotNull Continuation<? super T> $this$resumeWithException, Throwable exception) {
      int $i$f$resumeWithException = 0;
      Result.Companion var3 = Result.Companion;
      boolean var4 = false;
      Object var6 = Result.constructor-impl(ResultKt.createFailure(exception));
      $this$resumeWithException.resumeWith(var6);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <T> Continuation<T> Continuation(final CoroutineContext context, final Function1<? super Result<? extends T>, Unit> resumeWith) {
      int $i$f$Continuation = 0;
      return (Continuation)(new Continuation<T>() {
         @NotNull
         public CoroutineContext getContext() {
            return context;
         }

         public void resumeWith(@NotNull Object result) {
            resumeWith.invoke(Result.box-impl(result));
         }
      });
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <T> Continuation<Unit> createCoroutine(@NotNull Function1<? super Continuation<? super T>, ? extends Object> $this$createCoroutine, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$createCoroutine, "$this$createCoroutine");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      return (Continuation)(new SafeContinuation(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$createCoroutine, completion)), IntrinsicsKt.getCOROUTINE_SUSPENDED()));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <R, T> Continuation<Unit> createCoroutine(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$createCoroutine, R receiver, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$createCoroutine, "$this$createCoroutine");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      return (Continuation)(new SafeContinuation(IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$createCoroutine, receiver, completion)), IntrinsicsKt.getCOROUTINE_SUSPENDED()));
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final <T> void startCoroutine(@NotNull Function1<? super Continuation<? super T>, ? extends Object> $this$startCoroutine, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$startCoroutine, "$this$startCoroutine");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      Continuation var2 = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$startCoroutine, completion));
      Unit var3 = Unit.INSTANCE;
      boolean var4 = false;
      Result.Companion var5 = Result.Companion;
      boolean var7 = false;
      Object var8 = Result.constructor-impl(var3);
      var2.resumeWith(var8);
   }

   @SinceKotlin(
      version = "1.3"
   )
   public static final <R, T> void startCoroutine(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$startCoroutine, R receiver, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$startCoroutine, "$this$startCoroutine");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      Continuation var3 = IntrinsicsKt.intercepted(IntrinsicsKt.createCoroutineUnintercepted($this$startCoroutine, receiver, completion));
      Unit var4 = Unit.INSTANCE;
      boolean var5 = false;
      Result.Companion var6 = Result.Companion;
      boolean var8 = false;
      Object var9 = Result.constructor-impl(var4);
      var3.resumeWith(var9);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <T> Object suspendCoroutine(Function1<? super Continuation<? super T>, Unit> block, Continuation<? super T> var1) {
      int $i$f$suspendCoroutine = 0;
      InlineMarker.mark(0);
      int var4 = false;
      SafeContinuation safe = new SafeContinuation(IntrinsicsKt.intercepted(var1));
      block.invoke(safe);
      Object var10000 = safe.getOrThrow();
      if (var10000 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
         DebugProbesKt.probeCoroutineSuspended(var1);
      }

      InlineMarker.mark(1);
      return var10000;
   }

   /** @deprecated */
   // $FF: synthetic method
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   public static void coroutineContext$annotations() {
   }

   private static final CoroutineContext getCoroutineContext() {
      int $i$f$getCoroutineContext = 0;
      throw (Throwable)(new NotImplementedError("Implemented as intrinsic"));
   }
}
