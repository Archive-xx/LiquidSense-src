package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a\u001f\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b¨\u0006\u0007"},
   d2 = {"assert", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "kotlin-stdlib"},
   xs = "kotlin/PreconditionsKt"
)
class PreconditionsKt__AssertionsJVMKt {
   @InlineOnly
   private static final void assert(boolean value) {
      int $i$f$assert = 0;
      boolean var2 = false;
      if (_Assertions.ENABLED && !value) {
         int var3 = false;
         String var4 = "Assertion failed";
         throw (Throwable)(new AssertionError(var4));
      }
   }

   @InlineOnly
   private static final void assert(boolean value, Function0<? extends Object> lazyMessage) {
      int $i$f$assert = 0;
      if (_Assertions.ENABLED && !value) {
         Object message = lazyMessage.invoke();
         throw (Throwable)(new AssertionError(message));
      }
   }

   public PreconditionsKt__AssertionsJVMKt() {
   }
}
