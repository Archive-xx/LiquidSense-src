package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;

public class SpillProperty extends AccessorProperty {
   private static final long serialVersionUID = 3028496245198669460L;
   private static final Lookup LOOKUP = MethodHandles.lookup();
   private static final MethodHandle PARRAY_GETTER;
   private static final MethodHandle OARRAY_GETTER;
   private static final MethodHandle OBJECT_GETTER;
   private static final MethodHandle PRIMITIVE_GETTER;
   private static final MethodHandle OBJECT_SETTER;
   private static final MethodHandle PRIMITIVE_SETTER;

   private static MethodHandle primitiveGetter(int slot, int flags) {
      return (flags & 2048) == 2048 ? SpillProperty.Accessors.getCached(slot, true, true) : null;
   }

   private static MethodHandle primitiveSetter(int slot, int flags) {
      return (flags & 2048) == 2048 ? SpillProperty.Accessors.getCached(slot, true, false) : null;
   }

   private static MethodHandle objectGetter(int slot) {
      return SpillProperty.Accessors.getCached(slot, false, true);
   }

   private static MethodHandle objectSetter(int slot) {
      return SpillProperty.Accessors.getCached(slot, false, false);
   }

   public SpillProperty(String key, int flags, int slot) {
      super(key, flags, slot, primitiveGetter(slot, flags), primitiveSetter(slot, flags), objectGetter(slot), objectSetter(slot));
   }

   public SpillProperty(String key, int flags, int slot, Class<?> initialType) {
      this(key, flags, slot);
      this.setType(this.hasDualFields() ? initialType : Object.class);
   }

   SpillProperty(String key, int flags, int slot, ScriptObject owner, Object initialValue) {
      this(key, flags, slot);
      this.setInitialValue(owner, initialValue);
   }

   protected SpillProperty(SpillProperty property) {
      super(property);
   }

   protected SpillProperty(SpillProperty property, Class<?> newType) {
      super(property, (Class)newType);
   }

   public Property copy() {
      return new SpillProperty(this);
   }

   public Property copy(Class<?> newType) {
      return new SpillProperty(this, newType);
   }

   public boolean isSpill() {
      return true;
   }

   void initMethodHandles(Class<?> structure) {
      int slot = this.getSlot();
      this.primitiveGetter = primitiveGetter(slot, this.getFlags());
      this.primitiveSetter = primitiveSetter(slot, this.getFlags());
      this.objectGetter = objectGetter(slot);
      this.objectSetter = objectSetter(slot);
   }

   static {
      PARRAY_GETTER = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.getter(LOOKUP, ScriptObject.class, "primitiveSpill", long[].class), jdk.nashorn.internal.lookup.Lookup.MH.type(long[].class, Object.class));
      OARRAY_GETTER = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.getter(LOOKUP, ScriptObject.class, "objectSpill", Object[].class), jdk.nashorn.internal.lookup.Lookup.MH.type(Object[].class, Object.class));
      OBJECT_GETTER = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(jdk.nashorn.internal.lookup.Lookup.MH.arrayElementGetter(Object[].class), 0, OARRAY_GETTER);
      PRIMITIVE_GETTER = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(jdk.nashorn.internal.lookup.Lookup.MH.arrayElementGetter(long[].class), 0, PARRAY_GETTER);
      OBJECT_SETTER = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(jdk.nashorn.internal.lookup.Lookup.MH.arrayElementSetter(Object[].class), 0, OARRAY_GETTER);
      PRIMITIVE_SETTER = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(jdk.nashorn.internal.lookup.Lookup.MH.arrayElementSetter(long[].class), 0, PARRAY_GETTER);
   }

   private static class Accessors {
      private MethodHandle objectGetter;
      private MethodHandle objectSetter;
      private MethodHandle primitiveGetter;
      private MethodHandle primitiveSetter;
      private final int slot;
      private final MethodHandle ensureSpillSize;
      private static SpillProperty.Accessors[] ACCESSOR_CACHE = new SpillProperty.Accessors[512];

      Accessors(int slot) {
         assert slot >= 0;

         this.slot = slot;
         this.ensureSpillSize = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(ScriptObject.ENSURE_SPILL_SIZE, 1, slot), jdk.nashorn.internal.lookup.Lookup.MH.type(Object.class, Object.class));
      }

      private static void ensure(int slot) {
         int len = ACCESSOR_CACHE.length;
         if (slot >= len) {
            do {
               len *= 2;
            } while(slot >= len);

            SpillProperty.Accessors[] newCache = new SpillProperty.Accessors[len];
            System.arraycopy(ACCESSOR_CACHE, 0, newCache, 0, ACCESSOR_CACHE.length);
            ACCESSOR_CACHE = newCache;
         }

      }

      static MethodHandle getCached(int slot, boolean isPrimitive, boolean isGetter) {
         ensure(slot);
         SpillProperty.Accessors acc = ACCESSOR_CACHE[slot];
         if (acc == null) {
            acc = new SpillProperty.Accessors(slot);
            ACCESSOR_CACHE[slot] = acc;
         }

         return acc.getOrCreate(isPrimitive, isGetter);
      }

      private static MethodHandle primordial(boolean isPrimitive, boolean isGetter) {
         if (isPrimitive) {
            return isGetter ? SpillProperty.PRIMITIVE_GETTER : SpillProperty.PRIMITIVE_SETTER;
         } else {
            return isGetter ? SpillProperty.OBJECT_GETTER : SpillProperty.OBJECT_SETTER;
         }
      }

      MethodHandle getOrCreate(boolean isPrimitive, boolean isGetter) {
         MethodHandle accessor = this.getInner(isPrimitive, isGetter);
         if (accessor != null) {
            return accessor;
         } else {
            accessor = primordial(isPrimitive, isGetter);
            accessor = jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(accessor, 1, this.slot);
            if (!isGetter) {
               accessor = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(accessor, 0, this.ensureSpillSize);
            }

            this.setInner(isPrimitive, isGetter, accessor);
            return accessor;
         }
      }

      void setInner(boolean isPrimitive, boolean isGetter, MethodHandle mh) {
         if (isPrimitive) {
            if (isGetter) {
               this.primitiveGetter = mh;
            } else {
               this.primitiveSetter = mh;
            }
         } else if (isGetter) {
            this.objectGetter = mh;
         } else {
            this.objectSetter = mh;
         }

      }

      MethodHandle getInner(boolean isPrimitive, boolean isGetter) {
         if (isPrimitive) {
            return isGetter ? this.primitiveGetter : this.primitiveSetter;
         } else {
            return isGetter ? this.objectGetter : this.objectSetter;
         }
      }
   }
}
