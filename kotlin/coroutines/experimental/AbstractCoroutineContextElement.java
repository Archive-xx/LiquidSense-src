package kotlin.coroutines.experimental;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b'\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lkotlin/coroutines/experimental/AbstractCoroutineContextElement;", "Lkotlin/coroutines/experimental/CoroutineContext$Element;", "key", "Lkotlin/coroutines/experimental/CoroutineContext$Key;", "(Lkotlin/coroutines/experimental/CoroutineContext$Key;)V", "getKey", "()Lkotlin/coroutines/experimental/CoroutineContext$Key;", "kotlin-stdlib-coroutines"}
)
@SinceKotlin(
   version = "1.1"
)
public abstract class AbstractCoroutineContextElement implements CoroutineContext.Element {
   @NotNull
   private final CoroutineContext.Key<?> key;

   @NotNull
   public CoroutineContext.Key<?> getKey() {
      return this.key;
   }

   public AbstractCoroutineContextElement(@NotNull CoroutineContext.Key<?> key) {
      Intrinsics.checkParameterIsNotNull(key, "key");
      super();
      this.key = key;
   }

   @Nullable
   public <E extends CoroutineContext.Element> E get(@NotNull CoroutineContext.Key<E> key) {
      Intrinsics.checkParameterIsNotNull(key, "key");
      return CoroutineContext.Element.DefaultImpls.get(this, key);
   }

   public <R> R fold(R initial, @NotNull Function2<? super R, ? super CoroutineContext.Element, ? extends R> operation) {
      Intrinsics.checkParameterIsNotNull(operation, "operation");
      return CoroutineContext.Element.DefaultImpls.fold(this, initial, operation);
   }

   @NotNull
   public CoroutineContext minusKey(@NotNull CoroutineContext.Key<?> key) {
      Intrinsics.checkParameterIsNotNull(key, "key");
      return CoroutineContext.Element.DefaultImpls.minusKey(this, key);
   }

   @NotNull
   public CoroutineContext plus(@NotNull CoroutineContext context) {
      Intrinsics.checkParameterIsNotNull(context, "context");
      return CoroutineContext.Element.DefaultImpls.plus(this, context);
   }
}
