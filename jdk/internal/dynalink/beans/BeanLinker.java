package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.TypeUtilities;

class BeanLinker extends AbstractJavaLinker implements TypeBasedGuardingDynamicLinker {
   private static MethodHandle GET_LIST_ELEMENT;
   private static MethodHandle GET_MAP_ELEMENT;
   private static MethodHandle LIST_GUARD;
   private static MethodHandle MAP_GUARD;
   private static MethodHandle RANGE_CHECK_ARRAY;
   private static MethodHandle RANGE_CHECK_LIST;
   private static MethodHandle CONTAINS_MAP;
   private static MethodHandle SET_LIST_ELEMENT;
   private static MethodHandle PUT_MAP_ELEMENT;
   private static MethodHandle GET_ARRAY_LENGTH;
   private static MethodHandle GET_COLLECTION_LENGTH;
   private static MethodHandle GET_MAP_LENGTH;
   private static MethodHandle COLLECTION_GUARD;

   BeanLinker(Class<?> clazz) {
      super(clazz, Guards.getClassGuard(clazz), Guards.getInstanceOfGuard(clazz));
      if (clazz.isArray()) {
         this.setPropertyGetter("length", GET_ARRAY_LENGTH, GuardedInvocationComponent.ValidationType.IS_ARRAY);
      } else if (List.class.isAssignableFrom(clazz)) {
         this.setPropertyGetter("length", GET_COLLECTION_LENGTH, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
      }

   }

   public boolean canLinkType(Class<?> type) {
      return type == this.clazz;
   }

   FacetIntrospector createFacetIntrospector() {
      return new BeanIntrospector(this.clazz);
   }

   protected GuardedInvocationComponent getGuardedInvocationComponent(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> operations) throws Exception {
      GuardedInvocationComponent superGic = super.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
      if (superGic != null) {
         return superGic;
      } else if (operations.isEmpty()) {
         return null;
      } else {
         String op = (String)operations.get(0);
         if ("getElem".equals(op)) {
            return this.getElementGetter(callSiteDescriptor, linkerServices, pop(operations));
         } else if ("setElem".equals(op)) {
            return this.getElementSetter(callSiteDescriptor, linkerServices, pop(operations));
         } else {
            return "getLength".equals(op) ? this.getLengthGetter(callSiteDescriptor) : null;
         }
      }
   }

   private GuardedInvocationComponent getElementGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> operations) throws Exception {
      MethodType callSiteType = callSiteDescriptor.getMethodType();
      Class<?> declaredType = callSiteType.parameterType(0);
      GuardedInvocationComponent nextComponent = this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
      GuardedInvocationComponent gic;
      BeanLinker.CollectionType collectionType;
      if (declaredType.isArray()) {
         gic = createInternalFilteredGuardedInvocationComponent(MethodHandles.arrayElementGetter(declaredType), linkerServices);
         collectionType = BeanLinker.CollectionType.ARRAY;
      } else if (List.class.isAssignableFrom(declaredType)) {
         gic = createInternalFilteredGuardedInvocationComponent(GET_LIST_ELEMENT, linkerServices);
         collectionType = BeanLinker.CollectionType.LIST;
      } else if (Map.class.isAssignableFrom(declaredType)) {
         gic = createInternalFilteredGuardedInvocationComponent(GET_MAP_ELEMENT, linkerServices);
         collectionType = BeanLinker.CollectionType.MAP;
      } else if (this.clazz.isArray()) {
         gic = this.getClassGuardedInvocationComponent(linkerServices.filterInternalObjects(MethodHandles.arrayElementGetter(this.clazz)), callSiteType);
         collectionType = BeanLinker.CollectionType.ARRAY;
      } else if (List.class.isAssignableFrom(this.clazz)) {
         gic = createInternalFilteredGuardedInvocationComponent(GET_LIST_ELEMENT, Guards.asType(LIST_GUARD, callSiteType), List.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
         collectionType = BeanLinker.CollectionType.LIST;
      } else {
         if (!Map.class.isAssignableFrom(this.clazz)) {
            return nextComponent;
         }

         gic = createInternalFilteredGuardedInvocationComponent(GET_MAP_ELEMENT, Guards.asType(MAP_GUARD, callSiteType), Map.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
         collectionType = BeanLinker.CollectionType.MAP;
      }

      String fixedKey = getFixedKey(callSiteDescriptor);
      Object typedFixedKey;
      if (collectionType != BeanLinker.CollectionType.MAP && fixedKey != null) {
         typedFixedKey = convertKeyToInteger(fixedKey, linkerServices);
         if (typedFixedKey == null) {
            return nextComponent;
         }
      } else {
         typedFixedKey = fixedKey;
      }

      GuardedInvocation gi = gic.getGuardedInvocation();
      BeanLinker.Binder binder = new BeanLinker.Binder(linkerServices, callSiteType, typedFixedKey);
      MethodHandle invocation = gi.getInvocation();
      if (nextComponent == null) {
         return gic.replaceInvocation(binder.bind(invocation));
      } else {
         MethodHandle checkGuard;
         switch(collectionType) {
         case LIST:
            checkGuard = convertArgToInt(RANGE_CHECK_LIST, linkerServices, callSiteDescriptor);
            break;
         case MAP:
            checkGuard = linkerServices.filterInternalObjects(CONTAINS_MAP);
            break;
         case ARRAY:
            checkGuard = convertArgToInt(RANGE_CHECK_ARRAY, linkerServices, callSiteDescriptor);
            break;
         default:
            throw new AssertionError();
         }

         AbstractJavaLinker.MethodPair matchedInvocations = matchReturnTypes(binder.bind(invocation), nextComponent.getGuardedInvocation().getInvocation());
         return nextComponent.compose(matchedInvocations.guardWithTest(binder.bindTest(checkGuard)), gi.getGuard(), gic.getValidatorClass(), gic.getValidationType());
      }
   }

   private static GuardedInvocationComponent createInternalFilteredGuardedInvocationComponent(MethodHandle invocation, LinkerServices linkerServices) {
      return new GuardedInvocationComponent(linkerServices.filterInternalObjects(invocation));
   }

   private static GuardedInvocationComponent createInternalFilteredGuardedInvocationComponent(MethodHandle invocation, MethodHandle guard, Class<?> validatorClass, GuardedInvocationComponent.ValidationType validationType, LinkerServices linkerServices) {
      return new GuardedInvocationComponent(linkerServices.filterInternalObjects(invocation), guard, validatorClass, validationType);
   }

   private static String getFixedKey(CallSiteDescriptor callSiteDescriptor) {
      return callSiteDescriptor.getNameTokenCount() == 2 ? null : callSiteDescriptor.getNameToken(2);
   }

   private static Object convertKeyToInteger(String fixedKey, LinkerServices linkerServices) throws Exception {
      try {
         if (linkerServices.canConvert(String.class, Number.class)) {
            try {
               Object val = linkerServices.getTypeConverter(String.class, Number.class).invoke(fixedKey);
               if (!(val instanceof Number)) {
                  return null;
               } else {
                  Number n = (Number)val;
                  if (n instanceof Integer) {
                     return n;
                  } else {
                     int intIndex = n.intValue();
                     double doubleValue = n.doubleValue();
                     return (double)intIndex != doubleValue && !Double.isInfinite(doubleValue) ? null : intIndex;
                  }
               }
            } catch (Error | Exception var7) {
               throw var7;
            } catch (Throwable var8) {
               throw new RuntimeException(var8);
            }
         } else {
            return Integer.valueOf(fixedKey);
         }
      } catch (NumberFormatException var9) {
         return null;
      }
   }

   private static MethodHandle convertArgToInt(MethodHandle mh, LinkerServices ls, CallSiteDescriptor desc) {
      Class<?> sourceType = desc.getMethodType().parameterType(1);
      if (TypeUtilities.isMethodInvocationConvertible(sourceType, Number.class)) {
         return mh;
      } else if (ls.canConvert(sourceType, Number.class)) {
         MethodHandle converter = ls.getTypeConverter(sourceType, Number.class);
         return MethodHandles.filterArguments(mh, 1, new MethodHandle[]{converter.asType(converter.type().changeReturnType(mh.type().parameterType(1)))});
      } else {
         return mh;
      }
   }

   private static MethodHandle findRangeCheck(Class<?> collectionType) {
      return Lookup.findOwnStatic(MethodHandles.lookup(), "rangeCheck", Boolean.TYPE, collectionType, Object.class);
   }

   private static final boolean rangeCheck(Object array, Object index) {
      if (!(index instanceof Number)) {
         return false;
      } else {
         Number n = (Number)index;
         int intIndex = n.intValue();
         double doubleValue = n.doubleValue();
         if ((double)intIndex != doubleValue && !Double.isInfinite(doubleValue)) {
            return false;
         } else if (0 <= intIndex && intIndex < Array.getLength(array)) {
            return true;
         } else {
            throw new ArrayIndexOutOfBoundsException("Array index out of range: " + n);
         }
      }
   }

   private static final boolean rangeCheck(List<?> list, Object index) {
      if (!(index instanceof Number)) {
         return false;
      } else {
         Number n = (Number)index;
         int intIndex = n.intValue();
         double doubleValue = n.doubleValue();
         if ((double)intIndex != doubleValue && !Double.isInfinite(doubleValue)) {
            return false;
         } else if (0 <= intIndex && intIndex < list.size()) {
            return true;
         } else {
            throw new IndexOutOfBoundsException("Index: " + n + ", Size: " + list.size());
         }
      }
   }

   private GuardedInvocationComponent getElementSetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> operations) throws Exception {
      MethodType callSiteType = callSiteDescriptor.getMethodType();
      Class<?> declaredType = callSiteType.parameterType(0);
      GuardedInvocationComponent gic;
      BeanLinker.CollectionType collectionType;
      if (declaredType.isArray()) {
         gic = createInternalFilteredGuardedInvocationComponent(MethodHandles.arrayElementSetter(declaredType), linkerServices);
         collectionType = BeanLinker.CollectionType.ARRAY;
      } else if (List.class.isAssignableFrom(declaredType)) {
         gic = createInternalFilteredGuardedInvocationComponent(SET_LIST_ELEMENT, linkerServices);
         collectionType = BeanLinker.CollectionType.LIST;
      } else if (Map.class.isAssignableFrom(declaredType)) {
         gic = createInternalFilteredGuardedInvocationComponent(PUT_MAP_ELEMENT, linkerServices);
         collectionType = BeanLinker.CollectionType.MAP;
      } else if (this.clazz.isArray()) {
         gic = this.getClassGuardedInvocationComponent(linkerServices.filterInternalObjects(MethodHandles.arrayElementSetter(this.clazz)), callSiteType);
         collectionType = BeanLinker.CollectionType.ARRAY;
      } else if (List.class.isAssignableFrom(this.clazz)) {
         gic = createInternalFilteredGuardedInvocationComponent(SET_LIST_ELEMENT, Guards.asType(LIST_GUARD, callSiteType), List.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
         collectionType = BeanLinker.CollectionType.LIST;
      } else if (Map.class.isAssignableFrom(this.clazz)) {
         gic = createInternalFilteredGuardedInvocationComponent(PUT_MAP_ELEMENT, Guards.asType(MAP_GUARD, callSiteType), Map.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF, linkerServices);
         collectionType = BeanLinker.CollectionType.MAP;
      } else {
         gic = null;
         collectionType = null;
      }

      GuardedInvocationComponent nextComponent = collectionType == BeanLinker.CollectionType.MAP ? null : this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
      if (gic == null) {
         return nextComponent;
      } else {
         String fixedKey = getFixedKey(callSiteDescriptor);
         Object typedFixedKey;
         if (collectionType != BeanLinker.CollectionType.MAP && fixedKey != null) {
            typedFixedKey = convertKeyToInteger(fixedKey, linkerServices);
            if (typedFixedKey == null) {
               return nextComponent;
            }
         } else {
            typedFixedKey = fixedKey;
         }

         GuardedInvocation gi = gic.getGuardedInvocation();
         BeanLinker.Binder binder = new BeanLinker.Binder(linkerServices, callSiteType, typedFixedKey);
         MethodHandle invocation = gi.getInvocation();
         if (nextComponent == null) {
            return gic.replaceInvocation(binder.bind(invocation));
         } else {
            assert collectionType == BeanLinker.CollectionType.LIST || collectionType == BeanLinker.CollectionType.ARRAY;

            MethodHandle checkGuard = convertArgToInt(collectionType == BeanLinker.CollectionType.LIST ? RANGE_CHECK_LIST : RANGE_CHECK_ARRAY, linkerServices, callSiteDescriptor);
            AbstractJavaLinker.MethodPair matchedInvocations = matchReturnTypes(binder.bind(invocation), nextComponent.getGuardedInvocation().getInvocation());
            return nextComponent.compose(matchedInvocations.guardWithTest(binder.bindTest(checkGuard)), gi.getGuard(), gic.getValidatorClass(), gic.getValidationType());
         }
      }
   }

   private GuardedInvocationComponent getLengthGetter(CallSiteDescriptor callSiteDescriptor) {
      assertParameterCount(callSiteDescriptor, 1);
      MethodType callSiteType = callSiteDescriptor.getMethodType();
      Class<?> declaredType = callSiteType.parameterType(0);
      if (declaredType.isArray()) {
         return new GuardedInvocationComponent(GET_ARRAY_LENGTH.asType(callSiteType));
      } else if (Collection.class.isAssignableFrom(declaredType)) {
         return new GuardedInvocationComponent(GET_COLLECTION_LENGTH.asType(callSiteType));
      } else if (Map.class.isAssignableFrom(declaredType)) {
         return new GuardedInvocationComponent(GET_MAP_LENGTH.asType(callSiteType));
      } else if (this.clazz.isArray()) {
         return new GuardedInvocationComponent(GET_ARRAY_LENGTH.asType(callSiteType), Guards.isArray(0, callSiteType), GuardedInvocationComponent.ValidationType.IS_ARRAY);
      } else if (Collection.class.isAssignableFrom(this.clazz)) {
         return new GuardedInvocationComponent(GET_COLLECTION_LENGTH.asType(callSiteType), Guards.asType(COLLECTION_GUARD, callSiteType), Collection.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
      } else {
         return Map.class.isAssignableFrom(this.clazz) ? new GuardedInvocationComponent(GET_MAP_LENGTH.asType(callSiteType), Guards.asType(MAP_GUARD, callSiteType), Map.class, GuardedInvocationComponent.ValidationType.INSTANCE_OF) : null;
      }
   }

   private static void assertParameterCount(CallSiteDescriptor descriptor, int paramCount) {
      if (descriptor.getMethodType().parameterCount() != paramCount) {
         throw new BootstrapMethodError(descriptor.getName() + " must have exactly " + paramCount + " parameters.");
      }
   }

   static {
      GET_LIST_ELEMENT = Lookup.PUBLIC.findVirtual(List.class, "get", MethodType.methodType(Object.class, Integer.TYPE));
      GET_MAP_ELEMENT = Lookup.PUBLIC.findVirtual(Map.class, "get", MethodType.methodType(Object.class, Object.class));
      LIST_GUARD = Guards.getInstanceOfGuard(List.class);
      MAP_GUARD = Guards.getInstanceOfGuard(Map.class);
      RANGE_CHECK_ARRAY = findRangeCheck(Object.class);
      RANGE_CHECK_LIST = findRangeCheck(List.class);
      CONTAINS_MAP = Lookup.PUBLIC.findVirtual(Map.class, "containsKey", MethodType.methodType(Boolean.TYPE, Object.class));
      SET_LIST_ELEMENT = Lookup.PUBLIC.findVirtual(List.class, "set", MethodType.methodType(Object.class, Integer.TYPE, Object.class));
      PUT_MAP_ELEMENT = Lookup.PUBLIC.findVirtual(Map.class, "put", MethodType.methodType(Object.class, Object.class, Object.class));
      GET_ARRAY_LENGTH = Lookup.PUBLIC.findStatic(Array.class, "getLength", MethodType.methodType(Integer.TYPE, Object.class));
      GET_COLLECTION_LENGTH = Lookup.PUBLIC.findVirtual(Collection.class, "size", MethodType.methodType(Integer.TYPE));
      GET_MAP_LENGTH = Lookup.PUBLIC.findVirtual(Map.class, "size", MethodType.methodType(Integer.TYPE));
      COLLECTION_GUARD = Guards.getInstanceOfGuard(Collection.class);
   }

   private static class Binder {
      private final LinkerServices linkerServices;
      private final MethodType methodType;
      private final Object fixedKey;

      Binder(LinkerServices linkerServices, MethodType methodType, Object fixedKey) {
         this.linkerServices = linkerServices;
         this.methodType = fixedKey == null ? methodType : methodType.insertParameterTypes(1, new Class[]{fixedKey.getClass()});
         this.fixedKey = fixedKey;
      }

      MethodHandle bind(MethodHandle handle) {
         return this.bindToFixedKey(this.linkerServices.asTypeLosslessReturn(handle, this.methodType));
      }

      MethodHandle bindTest(MethodHandle handle) {
         return this.bindToFixedKey(Guards.asType(handle, this.methodType));
      }

      private MethodHandle bindToFixedKey(MethodHandle handle) {
         return this.fixedKey == null ? handle : MethodHandles.insertArguments(handle, 1, new Object[]{this.fixedKey});
      }
   }

   private static enum CollectionType {
      ARRAY,
      LIST,
      MAP;
   }
}
