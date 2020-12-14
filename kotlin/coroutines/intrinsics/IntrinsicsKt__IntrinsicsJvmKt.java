package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u001aF\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u00012\u001c\b\u0004\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006H\u0083\b¢\u0006\u0002\b\b\u001aD\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\n\u001a]\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aA\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u0003*\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001aZ\u0010\u0011\u001a\u0004\u0018\u00010\u0007\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0003*#\b\u0001\u0012\u0004\u0012\u0002H\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00070\f¢\u0006\u0002\b\r2\u0006\u0010\u000e\u001a\u0002H\u000b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"},
   d2 = {"createCoroutineFromSuspendFunction", "Lkotlin/coroutines/Continuation;", "", "T", "completion", "block", "Lkotlin/Function1;", "", "createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt", "createCoroutineUnintercepted", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "R", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "receiver", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Lkotlin/coroutines/Continuation;", "intercepted", "startCoroutineUninterceptedOrReturn", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"},
   xs = "kotlin/coroutines/intrinsics/IntrinsicsKt"
)
class IntrinsicsKt__IntrinsicsJvmKt {
   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <T> Object startCoroutineUninterceptedOrReturn(@NotNull Function1<? super Continuation<? super T>, ? extends Object> $this$startCoroutineUninterceptedOrReturn, Continuation<? super T> completion) {
      return ((Function1)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUninterceptedOrReturn, 1)).invoke(completion);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @InlineOnly
   private static final <R, T> Object startCoroutineUninterceptedOrReturn(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$startCoroutineUninterceptedOrReturn, R receiver, Continuation<? super T> completion) {
      return ((Function2)TypeIntrinsics.beforeCheckcastToFunctionOfArity($this$startCoroutineUninterceptedOrReturn, 2)).invoke(receiver, completion);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <T> Continuation<Unit> createCoroutineUnintercepted(@NotNull Function1<? super Continuation<? super T>, ? extends Object> $this$createCoroutineUnintercepted, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$createCoroutineUnintercepted, "$this$createCoroutineUnintercepted");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      Continuation probeCompletion = DebugProbesKt.probeCoroutineCreated(completion);
      Continuation var10000;
      if ($this$createCoroutineUnintercepted instanceof BaseContinuationImpl) {
         var10000 = ((BaseContinuationImpl)$this$createCoroutineUnintercepted).create(probeCompletion);
      } else {
         int $i$f$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt = false;
         CoroutineContext context$iv = probeCompletion.getContext();
         if (context$iv == EmptyCoroutineContext.INSTANCE) {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1 var5 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$1;
            if (probeCompletion == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var5.<init>(probeCompletion, probeCompletion, $this$createCoroutineUnintercepted);
            var10000 = (Continuation)var5;
         } else {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2 var6 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$2;
            if (probeCompletion == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var6.<init>(probeCompletion, context$iv, probeCompletion, context$iv, $this$createCoroutineUnintercepted);
            var10000 = (Continuation)var6;
         }
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <R, T> Continuation<Unit> createCoroutineUnintercepted(@NotNull Function2<? super R, ? super Continuation<? super T>, ? extends Object> $this$createCoroutineUnintercepted, R receiver, @NotNull Continuation<? super T> completion) {
      Intrinsics.checkParameterIsNotNull($this$createCoroutineUnintercepted, "$this$createCoroutineUnintercepted");
      Intrinsics.checkParameterIsNotNull(completion, "completion");
      Continuation probeCompletion = DebugProbesKt.probeCoroutineCreated(completion);
      Continuation var10000;
      if ($this$createCoroutineUnintercepted instanceof BaseContinuationImpl) {
         var10000 = ((BaseContinuationImpl)$this$createCoroutineUnintercepted).create(receiver, probeCompletion);
      } else {
         int $i$f$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt = false;
         CoroutineContext context$iv = probeCompletion.getContext();
         if (context$iv == EmptyCoroutineContext.INSTANCE) {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3 var6 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3;
            if (probeCompletion == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var6.<init>(probeCompletion, probeCompletion, $this$createCoroutineUnintercepted, receiver);
            var10000 = (Continuation)var6;
         } else {
            IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4 var7 = new IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4;
            if (probeCompletion == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
            }

            var7.<init>(probeCompletion, context$iv, probeCompletion, context$iv, $this$createCoroutineUnintercepted, receiver);
            var10000 = (Continuation)var7;
         }
      }

      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <T> Continuation<T> intercepted(@NotNull Continuation<? super T> $this$intercepted) {
      Intrinsics.checkParameterIsNotNull($this$intercepted, "$this$intercepted");
      Continuation var10000 = $this$intercepted;
      if (!($this$intercepted instanceof ContinuationImpl)) {
         var10000 = null;
      }

      ContinuationImpl var1 = (ContinuationImpl)var10000;
      if (var1 != null) {
         var10000 = var1.intercepted();
         if (var10000 != null) {
            return var10000;
         }
      }

      var10000 = $this$intercepted;
      return var10000;
   }

   @SinceKotlin(
      version = "1.3"
   )
   private static final <T> Continuation<Unit> createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt(Continuation<? super T> completion, Function1<? super Continuation<? super T>, ? extends Object> block) {
      int $i$f$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt = 0;
      CoroutineContext context = completion.getContext();
      Continuation var4;
      if (context == EmptyCoroutineContext.INSTANCE) {
         RestrictedContinuationImpl var10000 = new RestrictedContinuationImpl() {
            private int label;
            // $FF: synthetic field
            final Continuation $completion;

            @Nullable
            protected Object invokeSuspend(@NotNull Object result) {
               Object var10000;
               boolean var3;
               switch(this.label) {
               case 0:
                  this.label = 1;
                  var3 = false;
                  ResultKt.throwOnFailure(result);
                  var10000 = IntrinsicsKt__IntrinsicsJvmKt.this.invoke(this);
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

            public {
               this.$completion = $captured_local_variable$1;
            }
         };
         if (completion == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
         }

         var10000.<init>(completion, completion);
         var4 = (Continuation)var10000;
      } else {
         ContinuationImpl var5 = new ContinuationImpl() {
            private int label;
            // $FF: synthetic field
            final Continuation $completion;
            // $FF: synthetic field
            final CoroutineContext $context;

            @Nullable
            protected Object invokeSuspend(@NotNull Object result) {
               Object var10000;
               boolean var3;
               switch(this.label) {
               case 0:
                  this.label = 1;
                  var3 = false;
                  ResultKt.throwOnFailure(result);
                  var10000 = IntrinsicsKt__IntrinsicsJvmKt.this.invoke(this);
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

            public {
               this.$completion = $captured_local_variable$1;
               this.$context = $captured_local_variable$2;
            }
         };
         if (completion == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.Continuation<kotlin.Any?>");
         }

         var5.<init>(completion, context, completion, context);
         var4 = (Continuation)var5;
      }

      return var4;
   }

   public IntrinsicsKt__IntrinsicsJvmKt() {
   }
}
