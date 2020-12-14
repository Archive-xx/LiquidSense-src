package net.ccbluex.liquidbounce.event;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/EventHook;", "", "eventClass", "Lnet/ccbluex/liquidbounce/event/Listenable;", "method", "Ljava/lang/reflect/Method;", "eventTarget", "Lnet/ccbluex/liquidbounce/event/EventTarget;", "(Lnet/ccbluex/liquidbounce/event/Listenable;Ljava/lang/reflect/Method;Lnet/ccbluex/liquidbounce/event/EventTarget;)V", "getEventClass", "()Lnet/ccbluex/liquidbounce/event/Listenable;", "isIgnoreCondition", "", "()Z", "getMethod", "()Ljava/lang/reflect/Method;", "LiquidSense"}
)
public final class EventHook {
   // $FF: synthetic field
   @NotNull
   private final Method method;
   // $FF: synthetic field
   private final boolean isIgnoreCondition;
   // $FF: synthetic field
   @NotNull
   private final Listenable eventClass;

   @NotNull
   public final Method getMethod() {
      return lIIlIIllIIlIIl.method;
   }

   public final boolean isIgnoreCondition() {
      return lIIlIIllIlIIII.isIgnoreCondition;
   }

   public EventHook(@NotNull Listenable lIIlIIlIllllll, @NotNull Method lIIlIIllIIIIlI, @NotNull EventTarget lIIlIIllIIIIIl) {
      Intrinsics.checkParameterIsNotNull(lIIlIIlIllllll, "eventClass");
      Intrinsics.checkParameterIsNotNull(lIIlIIllIIIIlI, "method");
      Intrinsics.checkParameterIsNotNull(lIIlIIllIIIIIl, "eventTarget");
      super();
      lIIlIIllIIIlII.eventClass = lIIlIIlIllllll;
      lIIlIIllIIIlII.method = lIIlIIllIIIIlI;
      lIIlIIllIIIlII.isIgnoreCondition = lIIlIIllIIIIIl.ignoreCondition();
   }

   @NotNull
   public final Listenable getEventClass() {
      return lIIlIIllIIllII.eventClass;
   }
}
