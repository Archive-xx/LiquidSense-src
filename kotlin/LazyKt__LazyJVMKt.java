package kotlin;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a*\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004\u001a(\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0004¨\u0006\t"},
   d2 = {"lazy", "Lkotlin/Lazy;", "T", "initializer", "Lkotlin/Function0;", "lock", "", "mode", "Lkotlin/LazyThreadSafetyMode;", "kotlin-stdlib"},
   xs = "kotlin/LazyKt"
)
class LazyKt__LazyJVMKt {
   @NotNull
   public static final <T> Lazy<T> lazy(@NotNull Function0<? extends T> initializer) {
      Intrinsics.checkParameterIsNotNull(initializer, "initializer");
      return (Lazy)(new SynchronizedLazyImpl(initializer, (Object)null, 2, (DefaultConstructorMarker)null));
   }

   @NotNull
   public static final <T> Lazy<T> lazy(@NotNull LazyThreadSafetyMode mode, @NotNull Function0<? extends T> initializer) {
      // $FF: Couldn't be decompiled
   }

   @NotNull
   public static final <T> Lazy<T> lazy(@Nullable Object lock, @NotNull Function0<? extends T> initializer) {
      Intrinsics.checkParameterIsNotNull(initializer, "initializer");
      return (Lazy)(new SynchronizedLazyImpl(initializer, lock));
   }

   public LazyKt__LazyJVMKt() {
   }
}
