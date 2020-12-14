package net.ccbluex.liquidbounce.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u000eR(\u0010\u0003\u001a\u001c\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/EventManager;", "", "()V", "registry", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/event/Event;", "", "Lnet/ccbluex/liquidbounce/event/EventHook;", "callEvent", "", "event", "registerListener", "listener", "Lnet/ccbluex/liquidbounce/event/Listenable;", "unregisterListener", "listenable", "LiquidSense"}
)
public final class EventManager {
   // $FF: synthetic field
   private final HashMap<Class<? extends Event>, List<EventHook>> registry = new HashMap();

   public final void callEvent(@NotNull Event lIlIlllllIIIlI) {
      Intrinsics.checkParameterIsNotNull(lIlIlllllIIIlI, "event");
      List var10000 = (List)lIlIlllllIIlIl.registry.get(lIlIlllllIIIlI.getClass());
      boolean var10001;
      if (var10000 == null) {
         var10001 = false;
      } else {
         Intrinsics.checkExpressionValueIsNotNull(var10000, "registry[event.javaClass] ?: return");
         List lIlIlllllIIllI = var10000;
         Iterator lIlIllllIlllll = lIlIlllllIIllI.iterator();

         while(lIlIllllIlllll.hasNext()) {
            EventHook lIlIlllllIIlll = (EventHook)lIlIllllIlllll.next();

            try {
               if (lIlIlllllIIlll.getEventClass().handleEvents() || lIlIlllllIIlll.isIgnoreCondition()) {
                  lIlIlllllIIlll.getMethod().invoke(lIlIlllllIIlll.getEventClass(), lIlIlllllIIIlI);
                  var10001 = false;
               }
            } catch (Throwable var7) {
               var7.printStackTrace();
            }
         }

      }
   }

   public final void registerListener(@NotNull Listenable lIllIIIIIIllII) {
      Intrinsics.checkParameterIsNotNull(lIllIIIIIIllII, "listener");
      Exception lIllIIIIIIIlll = lIllIIIIIIllII.getClass().getDeclaredMethods();
      double lIllIIIIIIIllI = lIllIIIIIIIlll.length;

      for(int lIllIIIIIIlIII = 0; lIllIIIIIIlIII < lIllIIIIIIIllI; ++lIllIIIIIIlIII) {
         int lIllIIIIIIlIIl = lIllIIIIIIIlll[lIllIIIIIIlIII];
         if (lIllIIIIIIlIIl.isAnnotationPresent(EventTarget.class)) {
            Intrinsics.checkExpressionValueIsNotNull(lIllIIIIIIlIIl, "method");
            if (lIllIIIIIIlIIl.getParameterTypes().length == 1) {
               if (!lIllIIIIIIlIIl.isAccessible()) {
                  lIllIIIIIIlIIl.setAccessible(true);
               }

               Class var10000 = lIllIIIIIIlIIl.getParameterTypes()[0];
               if (var10000 == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.event.Event>");
               }

               Class lIllIIIIIIllll = var10000;
               EventTarget lIllIIIIIlIIII = (EventTarget)lIllIIIIIIlIIl.getAnnotation(EventTarget.class);
               Object var9 = lIllIIIIIIllIl.registry.getOrDefault(lIllIIIIIIllll, new ArrayList());
               Intrinsics.checkExpressionValueIsNotNull(var9, "registry.getOrDefault(eventClass, ArrayList())");
               int lIllIIIIIIIIll = (List)var9;
               Intrinsics.checkExpressionValueIsNotNull(lIllIIIIIlIIII, "eventTarget");
               lIllIIIIIIIIll.add(new EventHook(lIllIIIIIIllII, lIllIIIIIIlIIl, lIllIIIIIlIIII));
               boolean var10001 = false;
               ((Map)lIllIIIIIIllIl.registry).put(lIllIIIIIIllll, lIllIIIIIIIIll);
               var10001 = false;
            }
         }
      }

   }

   public final void unregisterListener(@NotNull final Listenable lIlIllllllIlIl) {
      Intrinsics.checkParameterIsNotNull(lIlIllllllIlIl, "listenable");
      String lIlIllllllIIlI = (Map)lIlIlllllllIII.registry;
      int lIlIllllllIIIl = false;

      boolean var10001;
      for(Iterator lIlIllllllIIll = lIlIllllllIIlI.entrySet().iterator(); lIlIllllllIIll.hasNext(); var10001 = false) {
         byte lIlIllllllIlII = (Entry)lIlIllllllIIll.next();
         short lIlIlllllIllll = false;
         String lIlIllllllIIlI = (Class)lIlIllllllIlII.getKey();
         lIlIlllllIllll = false;
         List lIlIlllllllIlI = (List)lIlIllllllIlII.getValue();
         lIlIlllllllIlI.removeIf((Predicate)(new Predicate<EventHook>() {
            public final boolean test(@NotNull EventHook llIlIlIIlIlIIIl) {
               Intrinsics.checkParameterIsNotNull(llIlIlIIlIlIIIl, "it");
               return Intrinsics.areEqual((Object)llIlIlIIlIlIIIl.getEventClass(), (Object)lIlIllllllIlIl);
            }
         }));
         var10001 = false;
         ((Map)lIlIlllllllIII.registry).put(lIlIllllllIIlI, lIlIlllllllIlI);
      }

   }
}
