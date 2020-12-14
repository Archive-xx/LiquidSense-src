package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.linker.LinkerServices;

public class Guards {
   private static final Logger LOG = Logger.getLogger(Guards.class.getName(), "jdk.internal.dynalink.support.messages");
   private static final MethodHandle IS_INSTANCE;
   private static final MethodHandle IS_OF_CLASS;
   private static final MethodHandle IS_ARRAY;
   private static final MethodHandle IS_IDENTICAL;
   private static final MethodHandle IS_NULL;
   private static final MethodHandle IS_NOT_NULL;

   private Guards() {
   }

   public static MethodHandle isOfClass(Class<?> clazz, MethodType type) {
      Class<?> declaredType = type.parameterType(0);
      if (clazz == declaredType) {
         LOG.log(Level.WARNING, "isOfClassGuardAlwaysTrue", new Object[]{clazz.getName(), 0, type, DynamicLinker.getLinkedCallSiteLocation()});
         return constantTrue(type);
      } else if (!declaredType.isAssignableFrom(clazz)) {
         LOG.log(Level.WARNING, "isOfClassGuardAlwaysFalse", new Object[]{clazz.getName(), 0, type, DynamicLinker.getLinkedCallSiteLocation()});
         return constantFalse(type);
      } else {
         return getClassBoundArgumentTest(IS_OF_CLASS, clazz, 0, type);
      }
   }

   public static MethodHandle isInstance(Class<?> clazz, MethodType type) {
      return isInstance(clazz, 0, type);
   }

   public static MethodHandle isInstance(Class<?> clazz, int pos, MethodType type) {
      Class<?> declaredType = type.parameterType(pos);
      if (clazz.isAssignableFrom(declaredType)) {
         LOG.log(Level.WARNING, "isInstanceGuardAlwaysTrue", new Object[]{clazz.getName(), pos, type, DynamicLinker.getLinkedCallSiteLocation()});
         return constantTrue(type);
      } else if (!declaredType.isAssignableFrom(clazz)) {
         LOG.log(Level.WARNING, "isInstanceGuardAlwaysFalse", new Object[]{clazz.getName(), pos, type, DynamicLinker.getLinkedCallSiteLocation()});
         return constantFalse(type);
      } else {
         return getClassBoundArgumentTest(IS_INSTANCE, clazz, pos, type);
      }
   }

   public static MethodHandle isArray(int pos, MethodType type) {
      Class<?> declaredType = type.parameterType(pos);
      if (declaredType.isArray()) {
         LOG.log(Level.WARNING, "isArrayGuardAlwaysTrue", new Object[]{pos, type, DynamicLinker.getLinkedCallSiteLocation()});
         return constantTrue(type);
      } else if (!declaredType.isAssignableFrom(Object[].class)) {
         LOG.log(Level.WARNING, "isArrayGuardAlwaysFalse", new Object[]{pos, type, DynamicLinker.getLinkedCallSiteLocation()});
         return constantFalse(type);
      } else {
         return asType(IS_ARRAY, pos, type);
      }
   }

   public static boolean canReferenceDirectly(ClassLoader referrerLoader, ClassLoader referredLoader) {
      if (referredLoader == null) {
         return true;
      } else if (referrerLoader == null) {
         return false;
      } else {
         ClassLoader referrer = referrerLoader;

         while(referrer != referredLoader) {
            referrer = referrer.getParent();
            if (referrer == null) {
               return false;
            }
         }

         return true;
      }
   }

   private static MethodHandle getClassBoundArgumentTest(MethodHandle test, Class<?> clazz, int pos, MethodType type) {
      return asType(test.bindTo(clazz), pos, type);
   }

   public static MethodHandle asType(MethodHandle test, MethodType type) {
      return test.asType(getTestType(test, type));
   }

   public static MethodHandle asType(LinkerServices linkerServices, MethodHandle test, MethodType type) {
      return linkerServices.asType(test, getTestType(test, type));
   }

   private static MethodType getTestType(MethodHandle test, MethodType type) {
      return type.dropParameterTypes(test.type().parameterCount(), type.parameterCount()).changeReturnType(Boolean.TYPE);
   }

   private static MethodHandle asType(MethodHandle test, int pos, MethodType type) {
      assert test != null;

      assert type != null;

      assert type.parameterCount() > 0;

      assert pos >= 0 && pos < type.parameterCount();

      assert test.type().parameterCount() == 1;

      assert test.type().returnType() == Boolean.TYPE;

      return MethodHandles.permuteArguments(test.asType(test.type().changeParameterType(0, type.parameterType(pos))), type.changeReturnType(Boolean.TYPE), new int[]{pos});
   }

   public static MethodHandle getClassGuard(Class<?> clazz) {
      return IS_OF_CLASS.bindTo(clazz);
   }

   public static MethodHandle getInstanceOfGuard(Class<?> clazz) {
      return IS_INSTANCE.bindTo(clazz);
   }

   public static MethodHandle getIdentityGuard(Object obj) {
      return IS_IDENTICAL.bindTo(obj);
   }

   public static MethodHandle isNull() {
      return IS_NULL;
   }

   public static MethodHandle isNotNull() {
      return IS_NOT_NULL;
   }

   private static boolean isNull(Object obj) {
      return obj == null;
   }

   private static boolean isNotNull(Object obj) {
      return obj != null;
   }

   private static boolean isArray(Object o) {
      return o != null && o.getClass().isArray();
   }

   private static boolean isOfClass(Class<?> c, Object o) {
      return o != null && o.getClass() == c;
   }

   private static boolean isIdentical(Object o1, Object o2) {
      return o1 == o2;
   }

   private static MethodHandle constantTrue(MethodType type) {
      return constantBoolean(Boolean.TRUE, type);
   }

   private static MethodHandle constantFalse(MethodType type) {
      return constantBoolean(Boolean.FALSE, type);
   }

   private static MethodHandle constantBoolean(Boolean value, MethodType type) {
      return MethodHandles.permuteArguments(MethodHandles.constant(Boolean.TYPE, value), type.changeReturnType(Boolean.TYPE), new int[0]);
   }

   static {
      IS_INSTANCE = Lookup.PUBLIC.findVirtual(Class.class, "isInstance", MethodType.methodType(Boolean.TYPE, Object.class));
      Lookup lookup = new Lookup(MethodHandles.lookup());
      IS_OF_CLASS = lookup.findOwnStatic("isOfClass", Boolean.TYPE, Class.class, Object.class);
      IS_ARRAY = lookup.findOwnStatic("isArray", Boolean.TYPE, Object.class);
      IS_IDENTICAL = lookup.findOwnStatic("isIdentical", Boolean.TYPE, Object.class, Object.class);
      IS_NULL = lookup.findOwnStatic("isNull", Boolean.TYPE, Object.class);
      IS_NOT_NULL = lookup.findOwnStatic("isNotNull", Boolean.TYPE, Object.class);
   }
}
