package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a0\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0001\u0012\u0002\u0010\u0002\u001a/\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\rH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u000e\u001aH\u0010\u000f\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u000b2\u0006\u0010\u0011\u001a\u0002H\u00102\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u000b0\t¢\u0006\u0002\b\u0012H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001¢\u0006\u0002\u0010\u0013\u001a9\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\u0002H\u00102\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00050\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0013\u001a>\u0010\u0015\u001a\u0002H\u0010\"\u0004\b\u0000\u0010\u0010*\u0002H\u00102\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00050\t¢\u0006\u0002\b\u0012H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0013\u001a?\u0010\u0016\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u000b*\u0002H\u00102\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u000b0\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0013\u001aD\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u0010\"\u0004\b\u0001\u0010\u000b*\u0002H\u00102\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u000b0\t¢\u0006\u0002\b\u0012H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0013\u001a;\u0010\u0017\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\u0002H\u00102\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00190\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0013\u001a;\u0010\u001a\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\u0000\u0010\u0010*\u0002H\u00102\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00190\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001¢\u0006\u0002\u0010\u0013¨\u0006\u001b"},
   d2 = {"TODO", "", "reason", "", "repeat", "", "times", "", "action", "Lkotlin/Function1;", "run", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "with", "T", "receiver", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "also", "apply", "let", "takeIf", "predicate", "", "takeUnless", "kotlin-stdlib"},
   xs = "kotlin/StandardKt"
)
class StandardKt__StandardKt {
   @InlineOnly
   private static final Void TODO() {
      int $i$f$TODO = 0;
      throw (Throwable)(new NotImplementedError((String)null, 1, (DefaultConstructorMarker)null));
   }

   @InlineOnly
   private static final Void TODO(String reason) {
      int $i$f$TODO = 0;
      throw (Throwable)(new NotImplementedError("An operation is not implemented: " + reason));
   }

   @InlineOnly
   private static final <R> R run(Function0<? extends R> block) {
      int $i$f$run = 0;
      boolean var2 = false;
      return block.invoke();
   }

   @InlineOnly
   private static final <T, R> R run(T $this$run, Function1<? super T, ? extends R> block) {
      int $i$f$run = 0;
      boolean var3 = false;
      return block.invoke($this$run);
   }

   @InlineOnly
   private static final <T, R> R with(T receiver, Function1<? super T, ? extends R> block) {
      int $i$f$with = 0;
      boolean var3 = false;
      return block.invoke(receiver);
   }

   @InlineOnly
   private static final <T> T apply(T $this$apply, Function1<? super T, Unit> block) {
      int $i$f$apply = 0;
      boolean var3 = false;
      block.invoke($this$apply);
      return $this$apply;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.1"
   )
   private static final <T> T also(T $this$also, Function1<? super T, Unit> block) {
      int $i$f$also = 0;
      boolean var3 = false;
      block.invoke($this$also);
      return $this$also;
   }

   @InlineOnly
   private static final <T, R> R let(T $this$let, Function1<? super T, ? extends R> block) {
      int $i$f$let = 0;
      boolean var3 = false;
      return block.invoke($this$let);
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.1"
   )
   private static final <T> T takeIf(T $this$takeIf, Function1<? super T, Boolean> predicate) {
      int $i$f$takeIf = 0;
      boolean var3 = false;
      return (Boolean)predicate.invoke($this$takeIf) ? $this$takeIf : null;
   }

   @InlineOnly
   @SinceKotlin(
      version = "1.1"
   )
   private static final <T> T takeUnless(T $this$takeUnless, Function1<? super T, Boolean> predicate) {
      int $i$f$takeUnless = 0;
      boolean var3 = false;
      return !(Boolean)predicate.invoke($this$takeUnless) ? $this$takeUnless : null;
   }

   @InlineOnly
   private static final void repeat(int times, Function1<? super Integer, Unit> action) {
      int $i$f$repeat = 0;
      boolean var3 = false;
      int index = 0;

      for(int var4 = times; index < var4; ++index) {
         action.invoke(index);
      }

   }

   public StandardKt__StandardKt() {
   }
}
