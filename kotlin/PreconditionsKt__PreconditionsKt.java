package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0004\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a/\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\t\u001a=\u0010\u0007\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\n\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0087\b\u001a\u001c\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a*\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001a/\u0010\u000f\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\t\u001a=\u0010\u000f\u001a\u0002H\b\"\b\b\u0000\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001¢\u0006\u0002\u0010\n¨\u0006\u0010"},
   d2 = {"check", "", "value", "", "lazyMessage", "Lkotlin/Function0;", "", "checkNotNull", "T", "(Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "error", "", "message", "require", "requireNotNull", "kotlin-stdlib"},
   xs = "kotlin/PreconditionsKt"
)
class PreconditionsKt__PreconditionsKt extends PreconditionsKt__AssertionsJVMKt {
   @InlineOnly
   private static final void require(boolean value) {
      int $i$f$require = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (!value) {
         int var4 = false;
         String var5 = "Failed requirement.";
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      }
   }

   @InlineOnly
   private static final void require(boolean value, Function0<? extends Object> lazyMessage) {
      int $i$f$require = 0;
      boolean var3 = false;
      if (!value) {
         Object message = lazyMessage.invoke();
         throw (Throwable)(new IllegalArgumentException(message.toString()));
      }
   }

   @InlineOnly
   private static final <T> T requireNotNull(T value) {
      int $i$f$requireNotNull = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (value == null) {
         int var4 = false;
         String var5 = "Required value was null.";
         throw (Throwable)(new IllegalArgumentException(var5.toString()));
      } else {
         return value;
      }
   }

   @InlineOnly
   private static final <T> T requireNotNull(T value, Function0<? extends Object> lazyMessage) {
      int $i$f$requireNotNull = 0;
      boolean var3 = false;
      if (value == null) {
         Object message = lazyMessage.invoke();
         throw (Throwable)(new IllegalArgumentException(message.toString()));
      } else {
         return value;
      }
   }

   @InlineOnly
   private static final void check(boolean value) {
      int $i$f$check = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (!value) {
         int var4 = false;
         String var5 = "Check failed.";
         throw (Throwable)(new IllegalStateException(var5.toString()));
      }
   }

   @InlineOnly
   private static final void check(boolean value, Function0<? extends Object> lazyMessage) {
      int $i$f$check = 0;
      boolean var3 = false;
      if (!value) {
         Object message = lazyMessage.invoke();
         throw (Throwable)(new IllegalStateException(message.toString()));
      }
   }

   @InlineOnly
   private static final <T> T checkNotNull(T value) {
      int $i$f$checkNotNull = 0;
      boolean var2 = false;
      var2 = false;
      boolean var3 = false;
      if (value == null) {
         int var4 = false;
         String var5 = "Required value was null.";
         throw (Throwable)(new IllegalStateException(var5.toString()));
      } else {
         return value;
      }
   }

   @InlineOnly
   private static final <T> T checkNotNull(T value, Function0<? extends Object> lazyMessage) {
      int $i$f$checkNotNull = 0;
      boolean var3 = false;
      if (value == null) {
         Object message = lazyMessage.invoke();
         throw (Throwable)(new IllegalStateException(message.toString()));
      } else {
         return value;
      }
   }

   @InlineOnly
   private static final Void error(Object message) {
      int $i$f$error = 0;
      throw (Throwable)(new IllegalStateException(message.toString()));
   }

   public PreconditionsKt__PreconditionsKt() {
   }
}
