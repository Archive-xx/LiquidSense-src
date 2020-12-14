package jdk.nashorn.internal.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;

public final class Lookup {
   public static final MethodHandleFunctionality MH = MethodHandleFactory.getFunctionality();
   public static final MethodHandle EMPTY_GETTER = findOwnMH("emptyGetter", Object.class, Object.class);
   public static final MethodHandle EMPTY_SETTER;
   public static final MethodHandle TYPE_ERROR_THROWER_GETTER;
   public static final MethodHandle TYPE_ERROR_THROWER_SETTER;
   public static final MethodType GET_OBJECT_TYPE;
   public static final MethodType SET_OBJECT_TYPE;
   public static final MethodType GET_PRIMITIVE_TYPE;
   public static final MethodType SET_PRIMITIVE_TYPE;

   private Lookup() {
   }

   public static Object emptyGetter(Object self) {
      return ScriptRuntime.UNDEFINED;
   }

   public static void emptySetter(Object self, Object value) {
   }

   public static MethodHandle emptyGetter(Class<?> type) {
      return filterReturnType(EMPTY_GETTER, type);
   }

   public static Object typeErrorThrowerGetter(Object self) {
      throw ECMAErrors.typeError("strict.getter.setter.poison", ScriptRuntime.safeToString(self));
   }

   public static void typeErrorThrowerSetter(Object self, Object value) {
      throw ECMAErrors.typeError("strict.getter.setter.poison", ScriptRuntime.safeToString(self));
   }

   public static MethodHandle filterArgumentType(MethodHandle mh, int n, Class<?> from) {
      Class<?> to = mh.type().parameterType(n);
      if (from != Integer.TYPE) {
         if (from == Long.TYPE) {
            if (to == Integer.TYPE) {
               return MH.filterArguments(mh, n, JSType.TO_INT32_L.methodHandle());
            }
         } else if (from == Double.TYPE) {
            if (to == Integer.TYPE) {
               return MH.filterArguments(mh, n, JSType.TO_INT32_D.methodHandle());
            }

            if (to == Long.TYPE) {
               return MH.filterArguments(mh, n, JSType.TO_UINT32_D.methodHandle());
            }
         } else if (!from.isPrimitive()) {
            if (to == Integer.TYPE) {
               return MH.filterArguments(mh, n, JSType.TO_INT32.methodHandle());
            }

            if (to == Long.TYPE) {
               return MH.filterArguments(mh, n, JSType.TO_UINT32.methodHandle());
            }

            if (to == Double.TYPE) {
               return MH.filterArguments(mh, n, JSType.TO_NUMBER.methodHandle());
            }

            if (!to.isPrimitive()) {
               return mh;
            }

            assert false : "unsupported Lookup.filterReturnType type " + from + " -> " + to;
         }
      }

      return MH.explicitCastArguments(mh, mh.type().changeParameterType(n, from));
   }

   public static MethodHandle filterReturnType(MethodHandle mh, Class<?> type) {
      Class<?> retType = mh.type().returnType();
      if (retType != Integer.TYPE) {
         if (retType == Long.TYPE) {
            if (type == Integer.TYPE) {
               return MH.filterReturnValue(mh, JSType.TO_INT32_L.methodHandle());
            }
         } else if (retType == Double.TYPE) {
            if (type == Integer.TYPE) {
               return MH.filterReturnValue(mh, JSType.TO_INT32_D.methodHandle());
            }

            if (type == Long.TYPE) {
               return MH.filterReturnValue(mh, JSType.TO_UINT32_D.methodHandle());
            }
         } else if (!retType.isPrimitive()) {
            if (type == Integer.TYPE) {
               return MH.filterReturnValue(mh, JSType.TO_INT32.methodHandle());
            }

            if (type == Long.TYPE) {
               return MH.filterReturnValue(mh, JSType.TO_UINT32.methodHandle());
            }

            if (type == Double.TYPE) {
               return MH.filterReturnValue(mh, JSType.TO_NUMBER.methodHandle());
            }

            if (!type.isPrimitive()) {
               return mh;
            }

            assert false : "unsupported Lookup.filterReturnType type " + retType + " -> " + type;
         }
      }

      return MH.explicitCastArguments(mh, mh.type().changeReturnType(type));
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return MH.findStatic(MethodHandles.lookup(), Lookup.class, name, MH.type(rtype, types));
   }

   static {
      EMPTY_SETTER = findOwnMH("emptySetter", Void.TYPE, Object.class, Object.class);
      TYPE_ERROR_THROWER_GETTER = findOwnMH("typeErrorThrowerGetter", Object.class, Object.class);
      TYPE_ERROR_THROWER_SETTER = findOwnMH("typeErrorThrowerSetter", Void.TYPE, Object.class, Object.class);
      GET_OBJECT_TYPE = MH.type(Object.class, Object.class);
      SET_OBJECT_TYPE = MH.type(Void.TYPE, Object.class, Object.class);
      GET_PRIMITIVE_TYPE = MH.type(Long.TYPE, Object.class);
      SET_PRIMITIVE_TYPE = MH.type(Void.TYPE, Object.class, Long.TYPE);
   }
}
