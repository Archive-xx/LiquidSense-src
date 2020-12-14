package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public final class UserAccessorProperty extends SpillProperty {
   private static final long serialVersionUID = -5928687246526840321L;
   private static final Lookup LOOKUP = MethodHandles.lookup();
   private static final MethodHandle INVOKE_OBJECT_GETTER = findOwnMH_S("invokeObjectGetter", Object.class, UserAccessorProperty.Accessors.class, MethodHandle.class, Object.class);
   private static final MethodHandle INVOKE_INT_GETTER;
   private static final MethodHandle INVOKE_NUMBER_GETTER;
   private static final MethodHandle INVOKE_OBJECT_SETTER;
   private static final MethodHandle INVOKE_INT_SETTER;
   private static final MethodHandle INVOKE_NUMBER_SETTER;
   private static final Object OBJECT_GETTER_INVOKER_KEY;
   private static final Object OBJECT_SETTER_INVOKER_KEY;

   private static MethodHandle getObjectGetterInvoker() {
      return Context.getGlobal().getDynamicInvoker(OBJECT_GETTER_INVOKER_KEY, new Callable<MethodHandle>() {
         public MethodHandle call() throws Exception {
            return UserAccessorProperty.getINVOKE_UA_GETTER(Object.class, -1);
         }
      });
   }

   static MethodHandle getINVOKE_UA_GETTER(Class<?> returnType, int programPoint) {
      if (UnwarrantedOptimismException.isValid(programPoint)) {
         int flags = 8 | programPoint << 11;
         return Bootstrap.createDynamicInvoker("dyn:call", flags, returnType, Object.class, Object.class);
      } else {
         return Bootstrap.createDynamicInvoker("dyn:call", Object.class, Object.class, Object.class);
      }
   }

   private static MethodHandle getObjectSetterInvoker() {
      return Context.getGlobal().getDynamicInvoker(OBJECT_SETTER_INVOKER_KEY, new Callable<MethodHandle>() {
         public MethodHandle call() throws Exception {
            return UserAccessorProperty.getINVOKE_UA_SETTER(Object.class);
         }
      });
   }

   static MethodHandle getINVOKE_UA_SETTER(Class<?> valueType) {
      return Bootstrap.createDynamicInvoker("dyn:call", Void.TYPE, Object.class, Object.class, valueType);
   }

   UserAccessorProperty(String key, int flags, int slot) {
      super(key, flags, slot);
   }

   private UserAccessorProperty(UserAccessorProperty property) {
      super(property);
   }

   private UserAccessorProperty(UserAccessorProperty property, Class<?> newType) {
      super(property, newType);
   }

   public Property copy() {
      return new UserAccessorProperty(this);
   }

   public Property copy(Class<?> newType) {
      return new UserAccessorProperty(this, newType);
   }

   void setAccessors(ScriptObject sobj, PropertyMap map, UserAccessorProperty.Accessors gs) {
      try {
         super.getSetter(Object.class, map).invokeExact(sobj, gs);
      } catch (RuntimeException | Error var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RuntimeException(var6);
      }
   }

   UserAccessorProperty.Accessors getAccessors(ScriptObject sobj) {
      try {
         Object gs = super.getGetter(Object.class).invokeExact(sobj);
         return (UserAccessorProperty.Accessors)gs;
      } catch (RuntimeException | Error var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   protected Class<?> getLocalType() {
      return Object.class;
   }

   public boolean hasGetterFunction(ScriptObject sobj) {
      return this.getAccessors(sobj).getter != null;
   }

   public boolean hasSetterFunction(ScriptObject sobj) {
      return this.getAccessors(sobj).setter != null;
   }

   public int getIntValue(ScriptObject self, ScriptObject owner) {
      return (Integer)this.getObjectValue(self, owner);
   }

   public double getDoubleValue(ScriptObject self, ScriptObject owner) {
      return (Double)this.getObjectValue(self, owner);
   }

   public Object getObjectValue(ScriptObject self, ScriptObject owner) {
      try {
         return invokeObjectGetter(this.getAccessors(owner != null ? owner : self), getObjectGetterInvoker(), self);
      } catch (RuntimeException | Error var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RuntimeException(var5);
      }
   }

   public void setValue(ScriptObject self, ScriptObject owner, int value, boolean strict) {
      this.setValue(self, owner, value, strict);
   }

   public void setValue(ScriptObject self, ScriptObject owner, double value, boolean strict) {
      this.setValue(self, owner, value, strict);
   }

   public void setValue(ScriptObject self, ScriptObject owner, Object value, boolean strict) {
      try {
         invokeObjectSetter(this.getAccessors(owner != null ? owner : self), getObjectSetterInvoker(), strict ? this.getKey() : null, self, value);
      } catch (RuntimeException | Error var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RuntimeException(var7);
      }
   }

   public MethodHandle getGetter(Class<?> type) {
      return jdk.nashorn.internal.lookup.Lookup.filterReturnType(INVOKE_OBJECT_GETTER, type);
   }

   public MethodHandle getOptimisticGetter(Class<?> type, int programPoint) {
      if (type == Integer.TYPE) {
         return INVOKE_INT_GETTER;
      } else if (type == Double.TYPE) {
         return INVOKE_NUMBER_GETTER;
      } else {
         assert type == Object.class;

         return INVOKE_OBJECT_GETTER;
      }
   }

   void initMethodHandles(Class<?> structure) {
      throw new UnsupportedOperationException();
   }

   public ScriptFunction getGetterFunction(ScriptObject sobj) {
      Object value = this.getAccessors(sobj).getter;
      return value instanceof ScriptFunction ? (ScriptFunction)value : null;
   }

   public MethodHandle getSetter(Class<?> type, PropertyMap currentMap) {
      if (type == Integer.TYPE) {
         return INVOKE_INT_SETTER;
      } else if (type == Double.TYPE) {
         return INVOKE_NUMBER_SETTER;
      } else {
         assert type == Object.class;

         return INVOKE_OBJECT_SETTER;
      }
   }

   public ScriptFunction getSetterFunction(ScriptObject sobj) {
      Object value = this.getAccessors(sobj).setter;
      return value instanceof ScriptFunction ? (ScriptFunction)value : null;
   }

   MethodHandle getAccessorsGetter() {
      return super.getGetter(Object.class).asType(MethodType.methodType(UserAccessorProperty.Accessors.class, Object.class));
   }

   private static Object invokeObjectGetter(UserAccessorProperty.Accessors gs, MethodHandle invoker, Object self) throws Throwable {
      Object func = gs.getter;
      return func instanceof ScriptFunction ? invoker.invokeExact(func, self) : ScriptRuntime.UNDEFINED;
   }

   private static int invokeIntGetter(UserAccessorProperty.Accessors gs, MethodHandle invoker, int programPoint, Object self) throws Throwable {
      Object func = gs.getter;
      if (func instanceof ScriptFunction) {
         return invoker.invokeExact(func, self);
      } else {
         throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, programPoint);
      }
   }

   private static double invokeNumberGetter(UserAccessorProperty.Accessors gs, MethodHandle invoker, int programPoint, Object self) throws Throwable {
      Object func = gs.getter;
      if (func instanceof ScriptFunction) {
         return invoker.invokeExact(func, self);
      } else {
         throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, programPoint);
      }
   }

   private static void invokeObjectSetter(UserAccessorProperty.Accessors gs, MethodHandle invoker, String name, Object self, Object value) throws Throwable {
      Object func = gs.setter;
      if (func instanceof ScriptFunction) {
         invoker.invokeExact(func, self, value);
      } else if (name != null) {
         throw ECMAErrors.typeError("property.has.no.setter", name, ScriptRuntime.safeToString(self));
      }

   }

   private static void invokeIntSetter(UserAccessorProperty.Accessors gs, MethodHandle invoker, String name, Object self, int value) throws Throwable {
      Object func = gs.setter;
      if (func instanceof ScriptFunction) {
         invoker.invokeExact(func, self, value);
      } else if (name != null) {
         throw ECMAErrors.typeError("property.has.no.setter", name, ScriptRuntime.safeToString(self));
      }

   }

   private static void invokeNumberSetter(UserAccessorProperty.Accessors gs, MethodHandle invoker, String name, Object self, double value) throws Throwable {
      Object func = gs.setter;
      if (func instanceof ScriptFunction) {
         invoker.invokeExact(func, self, value);
      } else if (name != null) {
         throw ECMAErrors.typeError("property.has.no.setter", name, ScriptRuntime.safeToString(self));
      }

   }

   private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
      return jdk.nashorn.internal.lookup.Lookup.MH.findStatic(LOOKUP, UserAccessorProperty.class, name, jdk.nashorn.internal.lookup.Lookup.MH.type(rtype, types));
   }

   static {
      INVOKE_INT_GETTER = findOwnMH_S("invokeIntGetter", Integer.TYPE, UserAccessorProperty.Accessors.class, MethodHandle.class, Integer.TYPE, Object.class);
      INVOKE_NUMBER_GETTER = findOwnMH_S("invokeNumberGetter", Double.TYPE, UserAccessorProperty.Accessors.class, MethodHandle.class, Integer.TYPE, Object.class);
      INVOKE_OBJECT_SETTER = findOwnMH_S("invokeObjectSetter", Void.TYPE, UserAccessorProperty.Accessors.class, MethodHandle.class, String.class, Object.class, Object.class);
      INVOKE_INT_SETTER = findOwnMH_S("invokeIntSetter", Void.TYPE, UserAccessorProperty.Accessors.class, MethodHandle.class, String.class, Object.class, Integer.TYPE);
      INVOKE_NUMBER_SETTER = findOwnMH_S("invokeNumberSetter", Void.TYPE, UserAccessorProperty.Accessors.class, MethodHandle.class, String.class, Object.class, Double.TYPE);
      OBJECT_GETTER_INVOKER_KEY = new Object();
      OBJECT_SETTER_INVOKER_KEY = new Object();
   }

   static final class Accessors {
      Object getter;
      Object setter;

      Accessors(Object getter, Object setter) {
         this.set(getter, setter);
      }

      final void set(Object getter, Object setter) {
         this.getter = getter;
         this.setter = setter;
      }

      public String toString() {
         return "[getter=" + this.getter + " setter=" + this.setter + ']';
      }
   }
}
