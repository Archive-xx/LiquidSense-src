package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.scripts.JO;

public final class NativeJSAdapter extends ScriptObject {
   public static final String __get__ = "__get__";
   public static final String __put__ = "__put__";
   public static final String __call__ = "__call__";
   public static final String __new__ = "__new__";
   public static final String __getIds__ = "__getIds__";
   public static final String __getKeys__ = "__getKeys__";
   public static final String __getValues__ = "__getValues__";
   public static final String __has__ = "__has__";
   public static final String __delete__ = "__delete__";
   public static final String __preventExtensions__ = "__preventExtensions__";
   public static final String __isExtensible__ = "__isExtensible__";
   public static final String __seal__ = "__seal__";
   public static final String __isSealed__ = "__isSealed__";
   public static final String __freeze__ = "__freeze__";
   public static final String __isFrozen__ = "__isFrozen__";
   private final ScriptObject adaptee;
   private final boolean overrides;
   private static final MethodHandle IS_JSADAPTOR;
   private static PropertyMap $nasgenmap$;

   NativeJSAdapter(Object overrides, ScriptObject adaptee, ScriptObject proto, PropertyMap map) {
      super(proto, map);
      this.adaptee = wrapAdaptee(adaptee);
      if (overrides instanceof ScriptObject) {
         this.overrides = true;
         ScriptObject sobj = (ScriptObject)overrides;
         this.addBoundProperties(sobj);
      } else {
         this.overrides = false;
      }

   }

   private static ScriptObject wrapAdaptee(ScriptObject adaptee) {
      return new JO(adaptee);
   }

   public String getClassName() {
      return "JSAdapter";
   }

   public int getInt(Object key, int programPoint) {
      return this.overrides && super.hasOwnProperty(key) ? super.getInt(key, programPoint) : this.callAdapteeInt(programPoint, "__get__", key);
   }

   public int getInt(double key, int programPoint) {
      return this.overrides && super.hasOwnProperty(key) ? super.getInt(key, programPoint) : this.callAdapteeInt(programPoint, "__get__", key);
   }

   public int getInt(int key, int programPoint) {
      return this.overrides && super.hasOwnProperty(key) ? super.getInt(key, programPoint) : this.callAdapteeInt(programPoint, "__get__", key);
   }

   public double getDouble(Object key, int programPoint) {
      return this.overrides && super.hasOwnProperty(key) ? super.getDouble(key, programPoint) : this.callAdapteeDouble(programPoint, "__get__", key);
   }

   public double getDouble(double key, int programPoint) {
      return this.overrides && super.hasOwnProperty(key) ? super.getDouble(key, programPoint) : this.callAdapteeDouble(programPoint, "__get__", key);
   }

   public double getDouble(int key, int programPoint) {
      return this.overrides && super.hasOwnProperty(key) ? super.getDouble(key, programPoint) : this.callAdapteeDouble(programPoint, "__get__", key);
   }

   public Object get(Object key) {
      return this.overrides && super.hasOwnProperty(key) ? super.get(key) : this.callAdaptee("__get__", key);
   }

   public Object get(double key) {
      return this.overrides && super.hasOwnProperty(key) ? super.get(key) : this.callAdaptee("__get__", key);
   }

   public Object get(int key) {
      return this.overrides && super.hasOwnProperty(key) ? super.get(key) : this.callAdaptee("__get__", key);
   }

   public void set(Object key, int value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(Object key, double value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(Object key, Object value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(double key, int value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(double key, double value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(double key, Object value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(int key, int value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(int key, double value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public void set(int key, Object value, int flags) {
      if (this.overrides && super.hasOwnProperty(key)) {
         super.set(key, value, flags);
      } else {
         this.callAdaptee("__put__", key, value, flags);
      }

   }

   public boolean has(Object key) {
      return this.overrides && super.hasOwnProperty(key) ? true : JSType.toBoolean(this.callAdaptee(Boolean.FALSE, "__has__", key));
   }

   public boolean has(int key) {
      return this.overrides && super.hasOwnProperty(key) ? true : JSType.toBoolean(this.callAdaptee(Boolean.FALSE, "__has__", key));
   }

   public boolean has(double key) {
      return this.overrides && super.hasOwnProperty(key) ? true : JSType.toBoolean(this.callAdaptee(Boolean.FALSE, "__has__", key));
   }

   public boolean delete(int key, boolean strict) {
      return this.overrides && super.hasOwnProperty(key) ? super.delete(key, strict) : JSType.toBoolean(this.callAdaptee(Boolean.TRUE, "__delete__", key, strict));
   }

   public boolean delete(double key, boolean strict) {
      return this.overrides && super.hasOwnProperty(key) ? super.delete(key, strict) : JSType.toBoolean(this.callAdaptee(Boolean.TRUE, "__delete__", key, strict));
   }

   public boolean delete(Object key, boolean strict) {
      return this.overrides && super.hasOwnProperty(key) ? super.delete(key, strict) : JSType.toBoolean(this.callAdaptee(Boolean.TRUE, "__delete__", key, strict));
   }

   public Iterator<String> propertyIterator() {
      Object func = this.adaptee.get("__getIds__");
      if (!(func instanceof ScriptFunction)) {
         func = this.adaptee.get("__getKeys__");
      }

      Object obj;
      if (func instanceof ScriptFunction) {
         obj = ScriptRuntime.apply((ScriptFunction)func, this.adaptee);
      } else {
         obj = new NativeArray(0L);
      }

      List<String> array = new ArrayList();
      ArrayLikeIterator iter = ArrayLikeIterator.arrayLikeIterator(obj);

      while(iter.hasNext()) {
         array.add((String)iter.next());
      }

      return array.iterator();
   }

   public Iterator<Object> valueIterator() {
      Object obj = this.callAdaptee(new NativeArray(0L), "__getValues__");
      return ArrayLikeIterator.arrayLikeIterator(obj);
   }

   public ScriptObject preventExtensions() {
      this.callAdaptee("__preventExtensions__");
      return this;
   }

   public boolean isExtensible() {
      return JSType.toBoolean(this.callAdaptee(Boolean.TRUE, "__isExtensible__"));
   }

   public ScriptObject seal() {
      this.callAdaptee("__seal__");
      return this;
   }

   public boolean isSealed() {
      return JSType.toBoolean(this.callAdaptee(Boolean.FALSE, "__isSealed__"));
   }

   public ScriptObject freeze() {
      this.callAdaptee("__freeze__");
      return this;
   }

   public boolean isFrozen() {
      return JSType.toBoolean(this.callAdaptee(Boolean.FALSE, "__isFrozen__"));
   }

   public static NativeJSAdapter construct(boolean isNew, Object self, Object... args) {
      Object proto = ScriptRuntime.UNDEFINED;
      Object overrides = ScriptRuntime.UNDEFINED;
      if (args != null && args.length != 0) {
         Object adaptee;
         switch(args.length) {
         case 1:
            adaptee = args[0];
            break;
         case 2:
            overrides = args[0];
            adaptee = args[1];
            break;
         case 3:
         default:
            proto = args[0];
            overrides = args[1];
            adaptee = args[2];
         }

         if (!(adaptee instanceof ScriptObject)) {
            throw ECMAErrors.typeError("not.an.object", ScriptRuntime.safeToString(adaptee));
         } else {
            Global global = Global.instance();
            if (proto != null && !(proto instanceof ScriptObject)) {
               proto = global.getJSAdapterPrototype();
            }

            return new NativeJSAdapter(overrides, (ScriptObject)adaptee, (ScriptObject)proto, $nasgenmap$);
         }
      } else {
         throw ECMAErrors.typeError("not.an.object", "null");
      }
   }

   protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
      return this.findHook(desc, "__new__", false);
   }

   protected GuardedInvocation findCallMethodMethod(CallSiteDescriptor desc, LinkRequest request) {
      if (this.overrides && super.hasOwnProperty(desc.getNameToken(2))) {
         try {
            GuardedInvocation inv = super.findCallMethodMethod(desc, request);
            if (inv != null) {
               return inv;
            }
         } catch (Exception var4) {
         }
      }

      return this.findHook(desc, "__call__");
   }

   protected GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operation) {
      String name = desc.getNameToken(2);
      if (this.overrides && super.hasOwnProperty(name)) {
         try {
            GuardedInvocation inv = super.findGetMethod(desc, request, operation);
            if (inv != null) {
               return inv;
            }
         } catch (Exception var10) {
         }
      }

      byte var6 = -1;
      switch(operation.hashCode()) {
      case -75566075:
         if (operation.equals("getElem")) {
            var6 = 1;
         }
         break;
      case -75232295:
         if (operation.equals("getProp")) {
            var6 = 0;
         }
         break;
      case 618460119:
         if (operation.equals("getMethod")) {
            var6 = 2;
         }
      }

      switch(var6) {
      case 0:
      case 1:
         return this.findHook(desc, "__get__");
      case 2:
         FindProperty find = this.adaptee.findProperty("__call__", true);
         if (find != null) {
            Object value = find.getObjectValue();
            if (value instanceof ScriptFunction) {
               ScriptFunction func = (ScriptFunction)value;
               return new GuardedInvocation(Lookup.MH.dropArguments(Lookup.MH.constant(Object.class, func.createBound(this, new Object[]{name})), 0, (Class[])(Object.class)), testJSAdaptor(this.adaptee, (MethodHandle)null, (Object)null, (ScriptFunction)null), this.adaptee.getProtoSwitchPoints("__call__", find.getOwner()), (Class)null);
            }
         }

         throw ECMAErrors.typeError("no.such.function", desc.getNameToken(2), ScriptRuntime.safeToString(this));
      default:
         throw new AssertionError("should not reach here");
      }
   }

   protected GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
      if (this.overrides && super.hasOwnProperty(desc.getNameToken(2))) {
         try {
            GuardedInvocation inv = super.findSetMethod(desc, request);
            if (inv != null) {
               return inv;
            }
         } catch (Exception var4) {
         }
      }

      return this.findHook(desc, "__put__");
   }

   private Object callAdaptee(String name, Object... args) {
      return this.callAdaptee(ScriptRuntime.UNDEFINED, name, args);
   }

   private double callAdapteeDouble(int programPoint, String name, Object... args) {
      return JSType.toNumberMaybeOptimistic(this.callAdaptee(name, args), programPoint);
   }

   private int callAdapteeInt(int programPoint, String name, Object... args) {
      return JSType.toInt32MaybeOptimistic(this.callAdaptee(name, args), programPoint);
   }

   private Object callAdaptee(Object retValue, String name, Object... args) {
      Object func = this.adaptee.get(name);
      return func instanceof ScriptFunction ? ScriptRuntime.apply((ScriptFunction)func, this.adaptee, args) : retValue;
   }

   private GuardedInvocation findHook(CallSiteDescriptor desc, String hook) {
      return this.findHook(desc, hook, true);
   }

   private GuardedInvocation findHook(CallSiteDescriptor desc, String hook, boolean useName) {
      FindProperty findData = this.adaptee.findProperty(hook, true);
      MethodType type = desc.getMethodType();
      if (findData != null) {
         String name = desc.getNameTokenCount() > 2 ? desc.getNameToken(2) : null;
         Object value = findData.getObjectValue();
         if (value instanceof ScriptFunction) {
            ScriptFunction func = (ScriptFunction)value;
            MethodHandle methodHandle = this.getCallMethodHandle(findData, type, useName ? name : null);
            if (methodHandle != null) {
               return new GuardedInvocation(methodHandle, testJSAdaptor(this.adaptee, findData.getGetter(Object.class, -1, (LinkRequest)null), findData.getOwner(), func), this.adaptee.getProtoSwitchPoints(hook, findData.getOwner()), (Class)null);
            }
         }
      }

      byte var10 = -1;
      switch(hook.hashCode()) {
      case -596047202:
         if (hook.equals("__call__")) {
            var10 = 0;
         }
      default:
         switch(var10) {
         case 0:
            throw ECMAErrors.typeError("no.such.function", desc.getNameToken(2), ScriptRuntime.safeToString(this));
         default:
            MethodHandle methodHandle = hook.equals("__put__") ? Lookup.MH.asType(Lookup.EMPTY_SETTER, type) : Lookup.emptyGetter(type.returnType());
            return new GuardedInvocation(methodHandle, testJSAdaptor(this.adaptee, (MethodHandle)null, (Object)null, (ScriptFunction)null), this.adaptee.getProtoSwitchPoints(hook, (ScriptObject)null), (Class)null);
         }
      }
   }

   private static MethodHandle testJSAdaptor(Object adaptee, MethodHandle getter, Object where, ScriptFunction func) {
      return Lookup.MH.insertArguments(IS_JSADAPTOR, 1, adaptee, getter, where, func);
   }

   private static boolean isJSAdaptor(Object self, Object adaptee, MethodHandle getter, Object where, ScriptFunction func) {
      boolean res = self instanceof NativeJSAdapter && ((NativeJSAdapter)self).getAdaptee() == adaptee;
      if (res && getter != null) {
         try {
            return getter.invokeExact(where) == func;
         } catch (Error | RuntimeException var7) {
            throw var7;
         } catch (Throwable var8) {
            throw new RuntimeException(var8);
         }
      } else {
         return res;
      }
   }

   public ScriptObject getAdaptee() {
      return this.adaptee;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), NativeJSAdapter.class, name, Lookup.MH.type(rtype, types));
   }

   static {
      IS_JSADAPTOR = findOwnMH("isJSAdaptor", Boolean.TYPE, Object.class, Object.class, MethodHandle.class, Object.class, ScriptFunction.class);
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }
}
