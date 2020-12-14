package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a+\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\t\u001a\u0084\u0001\u0010\n\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\f\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\r2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\u0014\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0000¢\u0006\u0002\u0010\u0012\u001a3\u0010\u0013\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052\u0006\u0010\u0014\u001a\u0002H\u0006H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a[\u0010\u0016\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001a!\u0010\u0018\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0005H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a]\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aP\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001aW\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u001e0\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aW\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u001e0\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aa\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0002\u0010\u0017\u001aT\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a@\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\u0006*\u0002H\u000b2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00060\r¢\u0006\u0002\b!H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u0017\u001a\u0018\u0010\"\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u0005H\u0001ø\u0001\u0000¢\u0006\u0002\u0010#\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006$"},
   d2 = {"createFailure", "", "exception", "", "runCatching", "Lkotlin/Result;", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "fold", "T", "onSuccess", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "onFailure", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrDefault", "defaultValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrThrow", "(Ljava/lang/Object;)Ljava/lang/Object;", "map", "transform", "mapCatching", "action", "", "recover", "recoverCatching", "Lkotlin/ExtensionFunctionType;", "throwOnFailure", "(Ljava/lang/Object;)V", "kotlin-stdlib"}
)
public final class ResultKt {
   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final Object createFailure(@NotNull Throwable exception) {
      Intrinsics.checkParameterIsNotNull(exception, "exception");
      return new Result.Failure(exception);
   }

   @PublishedApi
   @SinceKotlin(
      version = "1.3"
   )
   public static final void throwOnFailure(@NotNull Object $this$throwOnFailure) {
      if ($this$throwOnFailure instanceof Result.Failure) {
         throw ((Result.Failure)$this$throwOnFailure).exception;
      }
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R> Object runCatching(Function0<? extends R> block) {
      byte var1 = 0;

      Object var2;
      try {
         Result.Companion var7 = Result.Companion;
         Object var3 = block.invoke();
         boolean var8 = false;
         var2 = Result.constructor-impl(var3);
      } catch (Throwable var6) {
         Result.Companion var4 = Result.Companion;
         boolean var5 = false;
         var2 = Result.constructor-impl(createFailure(var6));
      }

      return var2;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <T, R> Object runCatching(T $this$runCatching, Function1<? super T, ? extends R> block) {
      byte var2 = 0;

      Object var3;
      try {
         Result.Companion var8 = Result.Companion;
         Object var4 = block.invoke($this$runCatching);
         boolean var9 = false;
         var3 = Result.constructor-impl(var4);
      } catch (Throwable var7) {
         Result.Companion var5 = Result.Companion;
         boolean var6 = false;
         var3 = Result.constructor-impl(createFailure(var7));
      }

      return var3;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <T> T getOrThrow(@NotNull Object $this$getOrThrow) {
      int $i$f$getOrThrow = 0;
      throwOnFailure($this$getOrThrow);
      return $this$getOrThrow;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R, T extends R> R getOrElse(@NotNull Object $this$getOrElse, Function1<? super Throwable, ? extends R> onFailure) {
      int $i$f$getOrElse = 0;
      boolean var3 = false;
      Throwable exception = Result.exceptionOrNull-impl($this$getOrElse);
      return exception == null ? $this$getOrElse : onFailure.invoke(exception);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R, T extends R> R getOrDefault(@NotNull Object $this$getOrDefault, R defaultValue) {
      int $i$f$getOrDefault = 0;
      return Result.isFailure-impl($this$getOrDefault) ? defaultValue : $this$getOrDefault;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R, T> R fold(@NotNull Object $this$fold, Function1<? super T, ? extends R> onSuccess, Function1<? super Throwable, ? extends R> onFailure) {
      int $i$f$fold = 0;
      boolean var4 = false;
      Throwable exception = Result.exceptionOrNull-impl($this$fold);
      return exception == null ? onSuccess.invoke($this$fold) : onFailure.invoke(exception);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R, T> Object map(@NotNull Object $this$map, Function1<? super T, ? extends R> transform) {
      int $i$f$map = 0;
      boolean var3 = false;
      Object var10000;
      if (Result.isSuccess-impl($this$map)) {
         Result.Companion var6 = Result.Companion;
         Object var4 = transform.invoke($this$map);
         boolean var5 = false;
         var10000 = Result.constructor-impl(var4);
      } else {
         var10000 = Result.constructor-impl($this$map);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R, T> Object mapCatching(@NotNull Object $this$mapCatching, Function1<? super T, ? extends R> transform) {
      int $i$f$mapCatching = 0;
      Object var10000;
      if (Result.isSuccess-impl($this$mapCatching)) {
         Object var3 = $this$mapCatching;
         boolean var4 = false;

         Object var5;
         try {
            Result.Companion var10 = Result.Companion;
            int var11 = false;
            Object var6 = transform.invoke(var3);
            var11 = false;
            var5 = Result.constructor-impl(var6);
         } catch (Throwable var9) {
            Result.Companion var7 = Result.Companion;
            boolean var8 = false;
            var5 = Result.constructor-impl(createFailure(var9));
         }

         var10000 = var5;
      } else {
         var10000 = Result.constructor-impl($this$mapCatching);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R, T extends R> Object recover(@NotNull Object $this$recover, Function1<? super Throwable, ? extends R> transform) {
      int $i$f$recover = 0;
      boolean var3 = false;
      Throwable exception = Result.exceptionOrNull-impl($this$recover);
      Object var10000;
      if (exception == null) {
         var10000 = $this$recover;
      } else {
         Result.Companion var4 = Result.Companion;
         Object var5 = transform.invoke(exception);
         boolean var6 = false;
         var10000 = Result.constructor-impl(var5);
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <R, T extends R> Object recoverCatching(@NotNull Object $this$recoverCatching, Function1<? super Throwable, ? extends R> transform) {
      int $i$f$recoverCatching = 0;
      Throwable exception = Result.exceptionOrNull-impl($this$recoverCatching);
      Object var10000;
      if (exception == null) {
         var10000 = $this$recoverCatching;
      } else {
         boolean var6 = false;

         Object var7;
         try {
            Result.Companion var12 = Result.Companion;
            int var13 = false;
            Object var8 = transform.invoke(exception);
            var13 = false;
            var7 = Result.constructor-impl(var8);
         } catch (Throwable var11) {
            Result.Companion var9 = Result.Companion;
            boolean var10 = false;
            var7 = Result.constructor-impl(createFailure(var11));
         }

         var10000 = var7;
      }

      return var10000;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <T> Object onFailure(@NotNull Object $this$onFailure, Function1<? super Throwable, Unit> action) {
      int $i$f$onFailure = 0;
      boolean var3 = false;
      Throwable var10000 = Result.exceptionOrNull-impl($this$onFailure);
      if (var10000 != null) {
         Throwable var8 = var10000;
         boolean var4 = false;
         boolean var5 = false;
         int var7 = false;
         action.invoke(var8);
      }

      return $this$onFailure;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.3"
   )
   private static final <T> Object onSuccess(@NotNull Object $this$onSuccess, Function1<? super T, Unit> action) {
      int $i$f$onSuccess = 0;
      boolean var3 = false;
      if (Result.isSuccess-impl($this$onSuccess)) {
         action.invoke($this$onSuccess);
      }

      return $this$onSuccess;
   }
}
