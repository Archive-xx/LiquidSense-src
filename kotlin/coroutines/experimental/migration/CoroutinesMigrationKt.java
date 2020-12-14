package kotlin.coroutines.experimental.migration;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.coroutines.experimental.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0007\u001a\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0006H\u0007\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\u0006*\u00020\u0005H\u0007\u001a\f\u0010\f\u001a\u00020\t*\u00020\bH\u0007\u001a^\u0010\r\u001a\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000e\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0010\"\u0004\b\u0002\u0010\u0011*\"\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000eH\u0000\u001aL\u0010\r\u001a\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u0011*\u001c\u0012\u0004\u0012\u0002H\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0013H\u0000\u001a:\u0010\r\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014\"\u0004\b\u0000\u0010\u0011*\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00110\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0014H\u0000¨\u0006\u0015"},
   d2 = {"toContinuation", "Lkotlin/coroutines/Continuation;", "T", "Lkotlin/coroutines/experimental/Continuation;", "toContinuationInterceptor", "Lkotlin/coroutines/ContinuationInterceptor;", "Lkotlin/coroutines/experimental/ContinuationInterceptor;", "toCoroutineContext", "Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/experimental/CoroutineContext;", "toExperimentalContinuation", "toExperimentalContinuationInterceptor", "toExperimentalCoroutineContext", "toExperimentalSuspendFunction", "Lkotlin/Function3;", "T1", "T2", "R", "", "Lkotlin/Function2;", "Lkotlin/Function1;", "kotlin-stdlib-coroutines"}
)
public final class CoroutinesMigrationKt {
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <T> Continuation<T> toExperimentalContinuation(@NotNull kotlin.coroutines.Continuation<? super T> $this$toExperimentalContinuation) {
      Intrinsics.checkParameterIsNotNull($this$toExperimentalContinuation, "$this$toExperimentalContinuation");
      kotlin.coroutines.Continuation var10000 = $this$toExperimentalContinuation;
      if (!($this$toExperimentalContinuation instanceof ContinuationMigration)) {
         var10000 = null;
      }

      ContinuationMigration var1 = (ContinuationMigration)var10000;
      Continuation var2;
      if (var1 != null) {
         var2 = var1.getContinuation();
         if (var2 != null) {
            return var2;
         }
      }

      var2 = (Continuation)(new ExperimentalContinuationMigration($this$toExperimentalContinuation));
      return var2;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final <T> kotlin.coroutines.Continuation<T> toContinuation(@NotNull Continuation<? super T> $this$toContinuation) {
      Intrinsics.checkParameterIsNotNull($this$toContinuation, "$this$toContinuation");
      Continuation var10000 = $this$toContinuation;
      if (!($this$toContinuation instanceof ExperimentalContinuationMigration)) {
         var10000 = null;
      }

      ExperimentalContinuationMigration var1 = (ExperimentalContinuationMigration)var10000;
      kotlin.coroutines.Continuation var2;
      if (var1 != null) {
         var2 = var1.getContinuation();
         if (var2 != null) {
            return var2;
         }
      }

      var2 = (kotlin.coroutines.Continuation)(new ContinuationMigration($this$toContinuation));
      return var2;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final CoroutineContext toExperimentalCoroutineContext(@NotNull kotlin.coroutines.CoroutineContext $this$toExperimentalCoroutineContext) {
      CoroutineContext var10000;
      ContinuationInterceptor interceptor;
      kotlin.coroutines.CoroutineContext remainder;
      label22: {
         Intrinsics.checkParameterIsNotNull($this$toExperimentalCoroutineContext, "$this$toExperimentalCoroutineContext");
         interceptor = (ContinuationInterceptor)$this$toExperimentalCoroutineContext.get((kotlin.coroutines.CoroutineContext.Key)ContinuationInterceptor.Key);
         ContextMigration migration = (ContextMigration)$this$toExperimentalCoroutineContext.get((kotlin.coroutines.CoroutineContext.Key)ContextMigration.Key);
         remainder = $this$toExperimentalCoroutineContext.minusKey((kotlin.coroutines.CoroutineContext.Key)ContinuationInterceptor.Key).minusKey((kotlin.coroutines.CoroutineContext.Key)ContextMigration.Key);
         if (migration != null) {
            var10000 = migration.getContext();
            if (var10000 != null) {
               break label22;
            }
         }

         var10000 = (CoroutineContext)EmptyCoroutineContext.INSTANCE;
      }

      CoroutineContext original = var10000;
      CoroutineContext result = remainder == kotlin.coroutines.EmptyCoroutineContext.INSTANCE ? original : original.plus((CoroutineContext)(new ExperimentalContextMigration(remainder)));
      return interceptor == null ? result : result.plus((CoroutineContext)toExperimentalContinuationInterceptor(interceptor));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final kotlin.coroutines.CoroutineContext toCoroutineContext(@NotNull CoroutineContext $this$toCoroutineContext) {
      kotlin.coroutines.CoroutineContext var10000;
      kotlin.coroutines.experimental.ContinuationInterceptor interceptor;
      CoroutineContext remainder;
      label22: {
         Intrinsics.checkParameterIsNotNull($this$toCoroutineContext, "$this$toCoroutineContext");
         interceptor = (kotlin.coroutines.experimental.ContinuationInterceptor)$this$toCoroutineContext.get((CoroutineContext.Key)kotlin.coroutines.experimental.ContinuationInterceptor.Key);
         ExperimentalContextMigration migration = (ExperimentalContextMigration)$this$toCoroutineContext.get((CoroutineContext.Key)ExperimentalContextMigration.Key);
         remainder = $this$toCoroutineContext.minusKey((CoroutineContext.Key)kotlin.coroutines.experimental.ContinuationInterceptor.Key).minusKey((CoroutineContext.Key)ExperimentalContextMigration.Key);
         if (migration != null) {
            var10000 = migration.getContext();
            if (var10000 != null) {
               break label22;
            }
         }

         var10000 = (kotlin.coroutines.CoroutineContext)kotlin.coroutines.EmptyCoroutineContext.INSTANCE;
      }

      kotlin.coroutines.CoroutineContext original = var10000;
      kotlin.coroutines.CoroutineContext result = remainder == EmptyCoroutineContext.INSTANCE ? original : original.plus((kotlin.coroutines.CoroutineContext)(new ContextMigration(remainder)));
      return interceptor == null ? result : result.plus((kotlin.coroutines.CoroutineContext)toContinuationInterceptor(interceptor));
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final kotlin.coroutines.experimental.ContinuationInterceptor toExperimentalContinuationInterceptor(@NotNull ContinuationInterceptor $this$toExperimentalContinuationInterceptor) {
      Intrinsics.checkParameterIsNotNull($this$toExperimentalContinuationInterceptor, "$this$toExperimentalContinuationInterceptor");
      ContinuationInterceptor var10000 = $this$toExperimentalContinuationInterceptor;
      if (!($this$toExperimentalContinuationInterceptor instanceof ContinuationInterceptorMigration)) {
         var10000 = null;
      }

      ContinuationInterceptorMigration var1 = (ContinuationInterceptorMigration)var10000;
      kotlin.coroutines.experimental.ContinuationInterceptor var2;
      if (var1 != null) {
         var2 = var1.getInterceptor();
         if (var2 != null) {
            return var2;
         }
      }

      var2 = (kotlin.coroutines.experimental.ContinuationInterceptor)(new ExperimentalContinuationInterceptorMigration($this$toExperimentalContinuationInterceptor));
      return var2;
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final ContinuationInterceptor toContinuationInterceptor(@NotNull kotlin.coroutines.experimental.ContinuationInterceptor $this$toContinuationInterceptor) {
      Intrinsics.checkParameterIsNotNull($this$toContinuationInterceptor, "$this$toContinuationInterceptor");
      kotlin.coroutines.experimental.ContinuationInterceptor var10000 = $this$toContinuationInterceptor;
      if (!($this$toContinuationInterceptor instanceof ExperimentalContinuationInterceptorMigration)) {
         var10000 = null;
      }

      ExperimentalContinuationInterceptorMigration var1 = (ExperimentalContinuationInterceptorMigration)var10000;
      ContinuationInterceptor var2;
      if (var1 != null) {
         var2 = var1.getInterceptor();
         if (var2 != null) {
            return var2;
         }
      }

      var2 = (ContinuationInterceptor)(new ContinuationInterceptorMigration($this$toContinuationInterceptor));
      return var2;
   }

   @NotNull
   public static final <R> Function1<Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function1<? super kotlin.coroutines.Continuation<? super R>, ? extends Object> $this$toExperimentalSuspendFunction) {
      Intrinsics.checkParameterIsNotNull($this$toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
      return (Function1)(new ExperimentalSuspendFunction0Migration($this$toExperimentalSuspendFunction));
   }

   @NotNull
   public static final <T1, R> Function2<T1, Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function2<? super T1, ? super kotlin.coroutines.Continuation<? super R>, ? extends Object> $this$toExperimentalSuspendFunction) {
      Intrinsics.checkParameterIsNotNull($this$toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
      return (Function2)(new ExperimentalSuspendFunction1Migration($this$toExperimentalSuspendFunction));
   }

   @NotNull
   public static final <T1, T2, R> Function3<T1, T2, Continuation<? super R>, Object> toExperimentalSuspendFunction(@NotNull Function3<? super T1, ? super T2, ? super kotlin.coroutines.Continuation<? super R>, ? extends Object> $this$toExperimentalSuspendFunction) {
      Intrinsics.checkParameterIsNotNull($this$toExperimentalSuspendFunction, "$this$toExperimentalSuspendFunction");
      return (Function3)(new ExperimentalSuspendFunction2Migration($this$toExperimentalSuspendFunction));
   }
}
