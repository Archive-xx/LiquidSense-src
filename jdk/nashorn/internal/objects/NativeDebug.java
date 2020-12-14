package jdk.nashorn.internal.objects;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyListeners;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.events.RuntimeEvent;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

public final class NativeDebug extends ScriptObject {
   private static PropertyMap $nasgenmap$;
   private static final String EVENT_QUEUE = "__eventQueue__";
   private static final String EVENT_QUEUE_CAPACITY = "__eventQueueCapacity__";

   private NativeDebug() {
      throw new UnsupportedOperationException();
   }

   public String getClassName() {
      return "Debug";
   }

   public static Object getArrayDataClass(Object self, Object obj) {
      try {
         return ((ScriptObject)obj).getArray().getClass();
      } catch (ClassCastException var3) {
         return ScriptRuntime.UNDEFINED;
      }
   }

   public static Object getArrayData(Object self, Object obj) {
      try {
         return ((ScriptObject)obj).getArray();
      } catch (ClassCastException var3) {
         return ScriptRuntime.UNDEFINED;
      }
   }

   public static Object getContext(Object self) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("nashorn.getContext"));
      }

      return Global.getThisContext();
   }

   public static Object map(Object self, Object obj) {
      return obj instanceof ScriptObject ? ((ScriptObject)obj).getMap() : ScriptRuntime.UNDEFINED;
   }

   public static boolean identical(Object self, Object obj1, Object obj2) {
      return obj1 == obj2;
   }

   public static Object equalWithoutType(Object self, Object m1, Object m2) {
      return ((PropertyMap)m1).equalsWithoutType((PropertyMap)m2);
   }

   public static Object diffPropertyMaps(Object self, Object m1, Object m2) {
      return PropertyMap.diff((PropertyMap)m1, (PropertyMap)m2);
   }

   public static Object getClass(Object self, Object obj) {
      return obj != null ? obj.getClass() : ScriptRuntime.UNDEFINED;
   }

   public static boolean equals(Object self, Object obj1, Object obj2) {
      return Objects.equals(obj1, obj2);
   }

   public static String toJavaString(Object self, Object obj) {
      return Objects.toString(obj);
   }

   public static String toIdentString(Object self, Object obj) {
      if (obj == null) {
         return "null";
      } else {
         int hash = System.identityHashCode(obj);
         return obj.getClass() + "@" + Integer.toHexString(hash);
      }
   }

   public static int getListenerCount(Object self, Object obj) {
      return obj instanceof ScriptObject ? PropertyListeners.getListenerCount((ScriptObject)obj) : 0;
   }

   public static Object dumpCounters(Object self) {
      PrintWriter out = Context.getCurrentErr();
      out.println("ScriptObject count " + ScriptObject.getCount());
      out.println("Scope count " + Scope.getScopeCount());
      out.println("ScriptObject listeners added " + PropertyListeners.getListenersAdded());
      out.println("ScriptObject listeners removed " + PropertyListeners.getListenersRemoved());
      out.println("ScriptFunction constructor calls " + ScriptFunction.getConstructorCount());
      out.println("ScriptFunction invokes " + ScriptFunction.getInvokes());
      out.println("ScriptFunction allocations " + ScriptFunction.getAllocations());
      out.println("PropertyMap count " + PropertyMap.getCount());
      out.println("PropertyMap cloned " + PropertyMap.getClonedCount());
      out.println("PropertyMap history hit " + PropertyMap.getHistoryHit());
      out.println("PropertyMap proto invalidations " + PropertyMap.getProtoInvalidations());
      out.println("PropertyMap proto history hit " + PropertyMap.getProtoHistoryHit());
      out.println("PropertyMap setProtoNewMapCount " + PropertyMap.getSetProtoNewMapCount());
      out.println("Callsite count " + LinkerCallSite.getCount());
      out.println("Callsite misses " + LinkerCallSite.getMissCount());
      out.println("Callsite misses by site at " + LinkerCallSite.getMissSamplingPercentage() + "%");
      LinkerCallSite.getMissCounts(out);
      return ScriptRuntime.UNDEFINED;
   }

   public static Object getEventQueueCapacity(Object self) {
      ScriptObject sobj = (ScriptObject)self;
      Integer cap;
      if (sobj.has("__eventQueueCapacity__")) {
         cap = JSType.toInt32(sobj.get("__eventQueueCapacity__"));
      } else {
         setEventQueueCapacity(self, cap = RuntimeEvent.RUNTIME_EVENT_QUEUE_SIZE);
      }

      return cap;
   }

   public static void setEventQueueCapacity(Object self, Object newCapacity) {
      ((ScriptObject)self).set("__eventQueueCapacity__", newCapacity, 2);
   }

   public static void addRuntimeEvent(Object self, Object event) {
      LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
      int cap = (Integer)getEventQueueCapacity(self);

      while(q.size() >= cap) {
         q.removeFirst();
      }

      q.addLast(getEvent(event));
   }

   public static void expandEventQueueCapacity(Object self, Object newCapacity) {
      LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
      int nc = JSType.toInt32(newCapacity);

      while(q.size() > nc) {
         q.removeFirst();
      }

      setEventQueueCapacity(self, nc);
   }

   public static void clearRuntimeEvents(Object self) {
      LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
      q.clear();
   }

   public static void removeRuntimeEvent(Object self, Object event) {
      LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
      RuntimeEvent<?> re = getEvent(event);
      if (!q.remove(re)) {
         throw new IllegalStateException("runtime event " + re + " was not in event queue");
      }
   }

   public static Object getRuntimeEvents(Object self) {
      LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
      return q.toArray(new RuntimeEvent[q.size()]);
   }

   public static Object getLastRuntimeEvent(Object self) {
      LinkedList<RuntimeEvent<?>> q = getEventQueue(self);
      return q.isEmpty() ? null : (RuntimeEvent)q.getLast();
   }

   private static LinkedList<RuntimeEvent<?>> getEventQueue(Object self) {
      ScriptObject sobj = (ScriptObject)self;
      LinkedList q;
      if (sobj.has("__eventQueue__")) {
         q = (LinkedList)((ScriptObject)self).get("__eventQueue__");
      } else {
         ((ScriptObject)self).set("__eventQueue__", q = new LinkedList(), 2);
      }

      return q;
   }

   private static RuntimeEvent<?> getEvent(Object event) {
      return (RuntimeEvent)event;
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }
}
