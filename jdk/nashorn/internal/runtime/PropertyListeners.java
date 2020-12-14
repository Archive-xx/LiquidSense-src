package jdk.nashorn.internal.runtime;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.LongAdder;

public class PropertyListeners {
   private Map<String, PropertyListeners.WeakPropertyMapSet> listeners;
   private static LongAdder listenersAdded;
   private static LongAdder listenersRemoved;

   PropertyListeners(PropertyListeners listener) {
      if (listener != null && listener.listeners != null) {
         this.listeners = new WeakHashMap();
         synchronized(listener) {
            Iterator var3 = listener.listeners.entrySet().iterator();

            while(var3.hasNext()) {
               Entry<String, PropertyListeners.WeakPropertyMapSet> entry = (Entry)var3.next();
               this.listeners.put(entry.getKey(), new PropertyListeners.WeakPropertyMapSet((PropertyListeners.WeakPropertyMapSet)entry.getValue()));
            }
         }
      }

   }

   public static long getListenersAdded() {
      return listenersAdded.longValue();
   }

   public static long getListenersRemoved() {
      return listenersRemoved.longValue();
   }

   public static int getListenerCount(ScriptObject obj) {
      return obj.getMap().getListenerCount();
   }

   public int getListenerCount() {
      return this.listeners == null ? 0 : this.listeners.size();
   }

   public static PropertyListeners addListener(PropertyListeners listeners, String key, PropertyMap propertyMap) {
      if (listeners != null && listeners.containsListener(key, propertyMap)) {
         return listeners;
      } else {
         PropertyListeners newListeners = new PropertyListeners(listeners);
         newListeners.addListener(key, propertyMap);
         return newListeners;
      }
   }

   synchronized boolean containsListener(String key, PropertyMap propertyMap) {
      if (this.listeners == null) {
         return false;
      } else {
         PropertyListeners.WeakPropertyMapSet set = (PropertyListeners.WeakPropertyMapSet)this.listeners.get(key);
         return set != null && set.contains(propertyMap);
      }
   }

   final synchronized void addListener(String key, PropertyMap propertyMap) {
      if (Context.DEBUG) {
         listenersAdded.increment();
      }

      if (this.listeners == null) {
         this.listeners = new WeakHashMap();
      }

      PropertyListeners.WeakPropertyMapSet set = (PropertyListeners.WeakPropertyMapSet)this.listeners.get(key);
      if (set == null) {
         set = new PropertyListeners.WeakPropertyMapSet();
         this.listeners.put(key, set);
      }

      if (!set.contains(propertyMap)) {
         set.add(propertyMap);
      }

   }

   public synchronized void propertyAdded(Property prop) {
      if (this.listeners != null) {
         PropertyListeners.WeakPropertyMapSet set = (PropertyListeners.WeakPropertyMapSet)this.listeners.get(prop.getKey());
         if (set != null) {
            Iterator var3 = set.elements().iterator();

            while(var3.hasNext()) {
               PropertyMap propertyMap = (PropertyMap)var3.next();
               propertyMap.propertyAdded(prop, false);
            }

            this.listeners.remove(prop.getKey());
            if (Context.DEBUG) {
               listenersRemoved.increment();
            }
         }
      }

   }

   public synchronized void propertyDeleted(Property prop) {
      if (this.listeners != null) {
         PropertyListeners.WeakPropertyMapSet set = (PropertyListeners.WeakPropertyMapSet)this.listeners.get(prop.getKey());
         if (set != null) {
            Iterator var3 = set.elements().iterator();

            while(var3.hasNext()) {
               PropertyMap propertyMap = (PropertyMap)var3.next();
               propertyMap.propertyDeleted(prop, false);
            }

            this.listeners.remove(prop.getKey());
            if (Context.DEBUG) {
               listenersRemoved.increment();
            }
         }
      }

   }

   public synchronized void propertyModified(Property oldProp, Property newProp) {
      if (this.listeners != null) {
         PropertyListeners.WeakPropertyMapSet set = (PropertyListeners.WeakPropertyMapSet)this.listeners.get(oldProp.getKey());
         if (set != null) {
            Iterator var4 = set.elements().iterator();

            while(var4.hasNext()) {
               PropertyMap propertyMap = (PropertyMap)var4.next();
               propertyMap.propertyModified(oldProp, newProp, false);
            }

            this.listeners.remove(oldProp.getKey());
            if (Context.DEBUG) {
               listenersRemoved.increment();
            }
         }
      }

   }

   public synchronized void protoChanged() {
      if (this.listeners != null) {
         Iterator var1 = this.listeners.values().iterator();

         while(var1.hasNext()) {
            PropertyListeners.WeakPropertyMapSet set = (PropertyListeners.WeakPropertyMapSet)var1.next();
            Iterator var3 = set.elements().iterator();

            while(var3.hasNext()) {
               PropertyMap propertyMap = (PropertyMap)var3.next();
               propertyMap.protoChanged(false);
            }
         }

         this.listeners.clear();
      }

   }

   static {
      if (Context.DEBUG) {
         listenersAdded = new LongAdder();
         listenersRemoved = new LongAdder();
      }

   }

   private static class WeakPropertyMapSet {
      private final WeakHashMap<PropertyMap, Boolean> map;

      WeakPropertyMapSet() {
         this.map = new WeakHashMap();
      }

      WeakPropertyMapSet(PropertyListeners.WeakPropertyMapSet set) {
         this.map = new WeakHashMap(set.map);
      }

      void add(PropertyMap propertyMap) {
         this.map.put(propertyMap, Boolean.TRUE);
      }

      boolean contains(PropertyMap propertyMap) {
         return this.map.containsKey(propertyMap);
      }

      Set<PropertyMap> elements() {
         return this.map.keySet();
      }
   }
}
