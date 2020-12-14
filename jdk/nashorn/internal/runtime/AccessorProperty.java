package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SwitchPoint;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.function.Supplier;
import java.util.logging.Level;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.Global;

public class AccessorProperty extends Property {
   private static final Lookup LOOKUP = MethodHandles.lookup();
   private static final MethodHandle REPLACE_MAP = findOwnMH_S("replaceMap", Object.class, Object.class, PropertyMap.class);
   private static final MethodHandle INVALIDATE_SP = findOwnMH_S("invalidateSwitchPoint", Object.class, AccessorProperty.class, Object.class);
   private static final int NOOF_TYPES = JSType.getNumberOfAccessorTypes();
   private static final long serialVersionUID = 3371720170182154920L;
   private static ClassValue<AccessorProperty.Accessors> GETTERS_SETTERS = new ClassValue<AccessorProperty.Accessors>() {
      protected AccessorProperty.Accessors computeValue(Class<?> structure) {
         return new AccessorProperty.Accessors(structure);
      }
   };
   private transient MethodHandle[] GETTER_CACHE;
   transient MethodHandle primitiveGetter;
   transient MethodHandle primitiveSetter;
   transient MethodHandle objectGetter;
   transient MethodHandle objectSetter;

   public static AccessorProperty create(String key, int propertyFlags, MethodHandle getter, MethodHandle setter) {
      return new AccessorProperty(key, propertyFlags, -1, getter, setter);
   }

   AccessorProperty(AccessorProperty property, Object delegate) {
      super(property, property.getFlags() | 256);
      this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
      this.primitiveGetter = bindTo(property.primitiveGetter, delegate);
      this.primitiveSetter = bindTo(property.primitiveSetter, delegate);
      this.objectGetter = bindTo(property.objectGetter, delegate);
      this.objectSetter = bindTo(property.objectSetter, delegate);
      property.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
      this.setType(property.getType());
   }

   protected AccessorProperty(String key, int flags, int slot, MethodHandle primitiveGetter, MethodHandle primitiveSetter, MethodHandle objectGetter, MethodHandle objectSetter) {
      super(key, flags, slot);
      this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];

      assert this.getClass() != AccessorProperty.class;

      this.primitiveGetter = primitiveGetter;
      this.primitiveSetter = primitiveSetter;
      this.objectGetter = objectGetter;
      this.objectSetter = objectSetter;
      this.initializeType();
   }

   private AccessorProperty(String key, int flags, int slot, MethodHandle getter, MethodHandle setter) {
      super(key, flags | 128 | 2048 | (getter.type().returnType().isPrimitive() ? 64 : 0), slot);
      this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];

      assert !this.isSpill();

      Class<?> getterType = getter.type().returnType();
      Class<?> setterType = setter == null ? null : setter.type().parameterType(1);

      assert setterType == null || setterType == getterType;

      if (getterType == Integer.TYPE) {
         this.primitiveGetter = jdk.nashorn.internal.lookup.Lookup.MH.asType(getter, jdk.nashorn.internal.lookup.Lookup.GET_PRIMITIVE_TYPE);
         this.primitiveSetter = setter == null ? null : jdk.nashorn.internal.lookup.Lookup.MH.asType(setter, jdk.nashorn.internal.lookup.Lookup.SET_PRIMITIVE_TYPE);
      } else if (getterType == Double.TYPE) {
         this.primitiveGetter = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.filterReturnValue(getter, ObjectClassGenerator.PACK_DOUBLE), jdk.nashorn.internal.lookup.Lookup.GET_PRIMITIVE_TYPE);
         this.primitiveSetter = setter == null ? null : jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(setter, 1, ObjectClassGenerator.UNPACK_DOUBLE), jdk.nashorn.internal.lookup.Lookup.SET_PRIMITIVE_TYPE);
      } else {
         this.primitiveGetter = this.primitiveSetter = null;
      }

      assert this.primitiveGetter == null || this.primitiveGetter.type() == jdk.nashorn.internal.lookup.Lookup.GET_PRIMITIVE_TYPE : this.primitiveGetter + "!=" + jdk.nashorn.internal.lookup.Lookup.GET_PRIMITIVE_TYPE;

      assert this.primitiveSetter == null || this.primitiveSetter.type() == jdk.nashorn.internal.lookup.Lookup.SET_PRIMITIVE_TYPE : this.primitiveSetter;

      this.objectGetter = getter.type() != jdk.nashorn.internal.lookup.Lookup.GET_OBJECT_TYPE ? jdk.nashorn.internal.lookup.Lookup.MH.asType(getter, jdk.nashorn.internal.lookup.Lookup.GET_OBJECT_TYPE) : getter;
      this.objectSetter = setter != null && setter.type() != jdk.nashorn.internal.lookup.Lookup.SET_OBJECT_TYPE ? jdk.nashorn.internal.lookup.Lookup.MH.asType(setter, jdk.nashorn.internal.lookup.Lookup.SET_OBJECT_TYPE) : setter;
      this.setType(getterType);
   }

   public AccessorProperty(String key, int flags, Class<?> structure, int slot) {
      super(key, flags, slot);
      this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
      this.initGetterSetter(structure);
      this.initializeType();
   }

   private void initGetterSetter(Class<?> structure) {
      int slot = this.getSlot();
      if (this.isParameter() && this.hasArguments()) {
         MethodHandle arguments = jdk.nashorn.internal.lookup.Lookup.MH.getter(LOOKUP, structure, "arguments", ScriptObject.class);
         this.objectGetter = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(ScriptObject.GET_ARGUMENT.methodHandle(), 0, arguments), 1, slot), jdk.nashorn.internal.lookup.Lookup.GET_OBJECT_TYPE);
         this.objectSetter = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(ScriptObject.SET_ARGUMENT.methodHandle(), 0, arguments), 1, slot), jdk.nashorn.internal.lookup.Lookup.SET_OBJECT_TYPE);
         this.primitiveGetter = null;
         this.primitiveSetter = null;
      } else {
         AccessorProperty.Accessors gs = (AccessorProperty.Accessors)GETTERS_SETTERS.get(structure);
         this.objectGetter = gs.objectGetters[slot];
         this.primitiveGetter = gs.primitiveGetters[slot];
         this.objectSetter = gs.objectSetters[slot];
         this.primitiveSetter = gs.primitiveSetters[slot];
      }

      assert this.hasDualFields() != StructureLoader.isSingleFieldStructure(structure.getName());

   }

   protected AccessorProperty(String key, int flags, int slot, ScriptObject owner, Object initialValue) {
      this(key, flags, owner.getClass(), slot);
      this.setInitialValue(owner, initialValue);
   }

   public AccessorProperty(String key, int flags, Class<?> structure, int slot, Class<?> initialType) {
      this(key, flags, structure, slot);
      this.setType(this.hasDualFields() ? initialType : Object.class);
   }

   protected AccessorProperty(AccessorProperty property, Class<?> newType) {
      super(property, property.getFlags());
      this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
      this.GETTER_CACHE = newType != property.getLocalType() ? new MethodHandle[NOOF_TYPES] : property.GETTER_CACHE;
      this.primitiveGetter = property.primitiveGetter;
      this.primitiveSetter = property.primitiveSetter;
      this.objectGetter = property.objectGetter;
      this.objectSetter = property.objectSetter;
      this.setType(newType);
   }

   protected AccessorProperty(AccessorProperty property) {
      this(property, property.getLocalType());
   }

   protected final void setInitialValue(ScriptObject owner, Object initialValue) {
      this.setType(this.hasDualFields() ? JSType.unboxedFieldType(initialValue) : Object.class);
      if (initialValue instanceof Integer) {
         this.invokeSetter(owner, (Integer)initialValue);
      } else if (initialValue instanceof Double) {
         this.invokeSetter(owner, (Double)initialValue);
      } else {
         this.invokeSetter(owner, initialValue);
      }

   }

   protected final void initializeType() {
      this.setType(!this.hasDualFields() ? Object.class : null);
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
   }

   private static MethodHandle bindTo(MethodHandle mh, Object receiver) {
      return mh == null ? null : jdk.nashorn.internal.lookup.Lookup.MH.dropArguments(jdk.nashorn.internal.lookup.Lookup.MH.bindTo(mh, receiver), 0, (Class[])(Object.class));
   }

   public Property copy() {
      return new AccessorProperty(this);
   }

   public Property copy(Class<?> newType) {
      return new AccessorProperty(this, newType);
   }

   public int getIntValue(ScriptObject self, ScriptObject owner) {
      try {
         return this.getGetter(Integer.TYPE).invokeExact(self);
      } catch (RuntimeException | Error var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   public double getDoubleValue(ScriptObject self, ScriptObject owner) {
      try {
         return this.getGetter(Double.TYPE).invokeExact(self);
      } catch (RuntimeException | Error var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   public Object getObjectValue(ScriptObject self, ScriptObject owner) {
      try {
         return this.getGetter(Object.class).invokeExact(self);
      } catch (RuntimeException | Error var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   protected final void invokeSetter(ScriptObject self, int value) {
      try {
         this.getSetter(Integer.TYPE, self.getMap()).invokeExact(self, value);
      } catch (RuntimeException | Error var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   protected final void invokeSetter(ScriptObject self, double value) {
      try {
         this.getSetter(Double.TYPE, self.getMap()).invokeExact(self, value);
      } catch (RuntimeException | Error var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RuntimeException(var6);
      }
   }

   protected final void invokeSetter(ScriptObject self, Object value) {
      try {
         this.getSetter(Object.class, self.getMap()).invokeExact(self, value);
      } catch (RuntimeException | Error var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   public void setValue(ScriptObject self, ScriptObject owner, int value, boolean strict) {
      assert this.isConfigurable() || this.isWritable() : this.getKey() + " is not writable or configurable";

      this.invokeSetter(self, value);
   }

   public void setValue(ScriptObject self, ScriptObject owner, double value, boolean strict) {
      assert this.isConfigurable() || this.isWritable() : this.getKey() + " is not writable or configurable";

      this.invokeSetter(self, value);
   }

   public void setValue(ScriptObject self, ScriptObject owner, Object value, boolean strict) {
      this.invokeSetter(self, value);
   }

   void initMethodHandles(Class<?> structure) {
      if (ScriptObject.class.isAssignableFrom(structure) && StructureLoader.isStructureClass(structure.getName())) {
         assert !this.isSpill();

         this.initGetterSetter(structure);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public MethodHandle getGetter(Class<?> type) {
      int i = JSType.getAccessorTypeIndex(type);

      assert type == Integer.TYPE || type == Double.TYPE || type == Object.class : "invalid getter type " + type + " for " + this.getKey();

      this.checkUndeclared();
      MethodHandle[] getterCache = this.GETTER_CACHE;
      MethodHandle cachedGetter = getterCache[i];
      MethodHandle getter;
      if (cachedGetter != null) {
         getter = cachedGetter;
      } else {
         getter = this.debug(ObjectClassGenerator.createGetter(this.getLocalType(), type, this.primitiveGetter, this.objectGetter, -1), this.getLocalType(), type, "get");
         getterCache[i] = getter;
      }

      assert getter.type().returnType() == type && getter.type().parameterType(0) == Object.class;

      return getter;
   }

   public MethodHandle getOptimisticGetter(Class<?> type, int programPoint) {
      if (this.objectGetter == null) {
         return this.getOptimisticPrimitiveGetter(type, programPoint);
      } else {
         this.checkUndeclared();
         return this.debug(ObjectClassGenerator.createGetter(this.getLocalType(), type, this.primitiveGetter, this.objectGetter, programPoint), this.getLocalType(), type, "get");
      }
   }

   private MethodHandle getOptimisticPrimitiveGetter(Class<?> type, int programPoint) {
      MethodHandle g = this.getGetter(this.getLocalType());
      return jdk.nashorn.internal.lookup.Lookup.MH.asType(OptimisticReturnFilters.filterOptimisticReturnValue(g, type, programPoint), g.type().changeReturnType(type));
   }

   private Property getWiderProperty(Class<?> type) {
      return this.copy(type);
   }

   private PropertyMap getWiderMap(PropertyMap oldMap, Property newProperty) {
      PropertyMap newMap = oldMap.replaceProperty(this, newProperty);

      assert oldMap.size() > 0;

      assert newMap.size() == oldMap.size();

      return newMap;
   }

   private void checkUndeclared() {
      if ((this.getFlags() & 512) != 0) {
         throw ECMAErrors.referenceError("not.defined", this.getKey());
      }
   }

   private static Object replaceMap(Object sobj, PropertyMap newMap) {
      ((ScriptObject)sobj).setMap(newMap);
      return sobj;
   }

   private static Object invalidateSwitchPoint(AccessorProperty property, Object obj) {
      if (!property.builtinSwitchPoint.hasBeenInvalidated()) {
         SwitchPoint.invalidateAll(new SwitchPoint[]{property.builtinSwitchPoint});
      }

      return obj;
   }

   private MethodHandle generateSetter(Class<?> forType, Class<?> type) {
      return this.debug(ObjectClassGenerator.createSetter(forType, type, this.primitiveSetter, this.objectSetter), this.getLocalType(), type, "set");
   }

   protected final boolean isUndefined() {
      return this.getLocalType() == null;
   }

   public MethodHandle getSetter(Class<?> type, PropertyMap currentMap) {
      this.checkUndeclared();
      int typeIndex = JSType.getAccessorTypeIndex(type);
      int currentTypeIndex = JSType.getAccessorTypeIndex(this.getLocalType());
      MethodHandle mh;
      if (this.needsInvalidator(typeIndex, currentTypeIndex)) {
         Property newProperty = this.getWiderProperty(type);
         PropertyMap newMap = this.getWiderMap(currentMap, newProperty);
         MethodHandle widerSetter = newProperty.getSetter(type, newMap);
         Class<?> ct = this.getLocalType();
         mh = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(widerSetter, 0, jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(this.debugReplace(ct, type, currentMap, newMap), 1, newMap));
         if (ct != null && ct.isPrimitive() && !type.isPrimitive()) {
            mh = ObjectClassGenerator.createGuardBoxedPrimitiveSetter(ct, this.generateSetter(ct, ct), mh);
         }
      } else {
         Class<?> forType = this.isUndefined() ? type : this.getLocalType();
         mh = this.generateSetter(!forType.isPrimitive() ? Object.class : forType, type);
      }

      if (this.isBuiltin()) {
         mh = jdk.nashorn.internal.lookup.Lookup.MH.filterArguments(mh, 0, debugInvalidate(jdk.nashorn.internal.lookup.Lookup.MH.insertArguments(INVALIDATE_SP, 0, this), this.getKey()));
      }

      assert mh.type().returnType() == Void.TYPE : mh.type();

      return mh;
   }

   public final boolean canChangeType() {
      if (!this.hasDualFields()) {
         return false;
      } else {
         return this.getLocalType() == null || this.getLocalType() != Object.class && (this.isConfigurable() || this.isWritable());
      }
   }

   private boolean needsInvalidator(int typeIndex, int currentTypeIndex) {
      return this.canChangeType() && typeIndex > currentTypeIndex;
   }

   private MethodHandle debug(MethodHandle mh, final Class<?> forType, final Class<?> type, final String tag) {
      if (Context.DEBUG && Global.hasInstance()) {
         Context context = Context.getContextTrusted();

         assert context != null;

         return context.addLoggingToHandle(ObjectClassGenerator.class, Level.INFO, mh, 0, true, new Supplier<String>() {
            public String get() {
               return tag + " '" + AccessorProperty.this.getKey() + "' (property=" + Debug.id(this) + ", slot=" + AccessorProperty.this.getSlot() + " " + this.getClass().getSimpleName() + " forType=" + MethodHandleFactory.stripName(forType) + ", type=" + MethodHandleFactory.stripName(type) + ')';
            }
         });
      } else {
         return mh;
      }
   }

   private MethodHandle debugReplace(final Class<?> oldType, final Class<?> newType, final PropertyMap oldMap, final PropertyMap newMap) {
      if (Context.DEBUG && Global.hasInstance()) {
         Context context = Context.getContextTrusted();

         assert context != null;

         MethodHandle mh = context.addLoggingToHandle(ObjectClassGenerator.class, REPLACE_MAP, new Supplier<String>() {
            public String get() {
               return "Type change for '" + AccessorProperty.this.getKey() + "' " + oldType + "=>" + newType;
            }
         });
         mh = context.addLoggingToHandle(ObjectClassGenerator.class, Level.FINEST, mh, Integer.MAX_VALUE, false, new Supplier<String>() {
            public String get() {
               return "Setting map " + Debug.id(oldMap) + " => " + Debug.id(newMap) + " " + oldMap + " => " + newMap;
            }
         });
         return mh;
      } else {
         return REPLACE_MAP;
      }
   }

   private static MethodHandle debugInvalidate(MethodHandle invalidator, final String key) {
      if (Context.DEBUG && Global.hasInstance()) {
         Context context = Context.getContextTrusted();

         assert context != null;

         return context.addLoggingToHandle(ObjectClassGenerator.class, invalidator, new Supplier<String>() {
            public String get() {
               return "Field change callback for " + key + " triggered ";
            }
         });
      } else {
         return invalidator;
      }
   }

   private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
      return jdk.nashorn.internal.lookup.Lookup.MH.findStatic(LOOKUP, AccessorProperty.class, name, jdk.nashorn.internal.lookup.Lookup.MH.type(rtype, types));
   }

   private static class Accessors {
      final MethodHandle[] objectGetters;
      final MethodHandle[] objectSetters;
      final MethodHandle[] primitiveGetters;
      final MethodHandle[] primitiveSetters;

      Accessors(Class<?> structure) {
         int fieldCount = ObjectClassGenerator.getFieldCount(structure);
         this.objectGetters = new MethodHandle[fieldCount];
         this.objectSetters = new MethodHandle[fieldCount];
         this.primitiveGetters = new MethodHandle[fieldCount];
         this.primitiveSetters = new MethodHandle[fieldCount];

         int i;
         String fieldNamePrimitive;
         Class typeClass;
         for(i = 0; i < fieldCount; ++i) {
            fieldNamePrimitive = ObjectClassGenerator.getFieldName(i, Type.OBJECT);
            typeClass = Type.OBJECT.getTypeClass();
            this.objectGetters[i] = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.getter(AccessorProperty.LOOKUP, structure, fieldNamePrimitive, typeClass), jdk.nashorn.internal.lookup.Lookup.GET_OBJECT_TYPE);
            this.objectSetters[i] = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.setter(AccessorProperty.LOOKUP, structure, fieldNamePrimitive, typeClass), jdk.nashorn.internal.lookup.Lookup.SET_OBJECT_TYPE);
         }

         if (!StructureLoader.isSingleFieldStructure(structure.getName())) {
            for(i = 0; i < fieldCount; ++i) {
               fieldNamePrimitive = ObjectClassGenerator.getFieldName(i, ObjectClassGenerator.PRIMITIVE_FIELD_TYPE);
               typeClass = ObjectClassGenerator.PRIMITIVE_FIELD_TYPE.getTypeClass();
               this.primitiveGetters[i] = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.getter(AccessorProperty.LOOKUP, structure, fieldNamePrimitive, typeClass), jdk.nashorn.internal.lookup.Lookup.GET_PRIMITIVE_TYPE);
               this.primitiveSetters[i] = jdk.nashorn.internal.lookup.Lookup.MH.asType(jdk.nashorn.internal.lookup.Lookup.MH.setter(AccessorProperty.LOOKUP, structure, fieldNamePrimitive, typeClass), jdk.nashorn.internal.lookup.Lookup.SET_PRIMITIVE_TYPE);
            }
         }

      }
   }
}
