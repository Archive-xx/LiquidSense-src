package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.Guards;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.TypeUtilities;

abstract class AbstractJavaLinker implements GuardingDynamicLinker {
   final Class<?> clazz;
   private final MethodHandle classGuard;
   private final MethodHandle assignableGuard;
   private final Map<String, AbstractJavaLinker.AnnotatedDynamicMethod> propertyGetters;
   private final Map<String, DynamicMethod> propertySetters;
   private final Map<String, DynamicMethod> methods;
   private static final MethodHandle IS_METHOD_HANDLE_NOT_NULL;
   private static final MethodHandle CONSTANT_NULL_DROP_METHOD_HANDLE;
   private static final Lookup privateLookup;
   private static final MethodHandle IS_ANNOTATED_METHOD_NOT_NULL;
   private static final MethodHandle CONSTANT_NULL_DROP_ANNOTATED_METHOD;
   private static final MethodHandle GET_ANNOTATED_METHOD;
   private static final MethodHandle GETTER_INVOKER;
   private static final MethodHandle IS_DYNAMIC_METHOD;
   private static final MethodHandle OBJECT_IDENTITY;
   private static MethodHandle GET_PROPERTY_GETTER_HANDLE;
   private final MethodHandle getPropertyGetterHandle;
   private static final MethodHandle GET_PROPERTY_SETTER_HANDLE;
   private final MethodHandle getPropertySetterHandle;
   private static MethodHandle GET_DYNAMIC_METHOD;
   private final MethodHandle getDynamicMethod;

   AbstractJavaLinker(Class<?> clazz, MethodHandle classGuard) {
      this(clazz, classGuard, classGuard);
   }

   AbstractJavaLinker(Class<?> clazz, MethodHandle classGuard, MethodHandle assignableGuard) {
      this.propertyGetters = new HashMap();
      this.propertySetters = new HashMap();
      this.methods = new HashMap();
      this.getPropertyGetterHandle = GET_PROPERTY_GETTER_HANDLE.bindTo(this);
      this.getPropertySetterHandle = GET_PROPERTY_SETTER_HANDLE.bindTo(this);
      this.getDynamicMethod = GET_DYNAMIC_METHOD.bindTo(this);
      this.clazz = clazz;
      this.classGuard = classGuard;
      this.assignableGuard = assignableGuard;
      FacetIntrospector introspector = this.createFacetIntrospector();
      Iterator var5 = introspector.getMethods().iterator();

      while(true) {
         String name;
         while(var5.hasNext()) {
            Method method = (Method)var5.next();
            name = method.getName();
            this.addMember(name, (AccessibleObject)method, this.methods);
            if (name.startsWith("get") && name.length() > 3 && method.getParameterTypes().length == 0) {
               this.setPropertyGetter(method, 3);
            } else if (name.startsWith("is") && name.length() > 2 && method.getParameterTypes().length == 0 && method.getReturnType() == Boolean.TYPE) {
               this.setPropertyGetter(method, 2);
            } else if (name.startsWith("set") && name.length() > 3 && method.getParameterTypes().length == 1) {
               this.addMember(decapitalize(name.substring(3)), (AccessibleObject)method, this.propertySetters);
            }
         }

         var5 = introspector.getFields().iterator();

         while(var5.hasNext()) {
            Field field = (Field)var5.next();
            name = field.getName();
            if (!this.propertyGetters.containsKey(name)) {
               this.setPropertyGetter(name, introspector.unreflectGetter(field), GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            }

            if (!Modifier.isFinal(field.getModifiers()) && !this.propertySetters.containsKey(name)) {
               this.addMember(name, (SingleDynamicMethod)(new SimpleDynamicMethod(introspector.unreflectSetter(field), clazz, name)), this.propertySetters);
            }
         }

         var5 = introspector.getInnerClassGetters().entrySet().iterator();

         while(var5.hasNext()) {
            Entry<String, MethodHandle> innerClassSpec = (Entry)var5.next();
            name = (String)innerClassSpec.getKey();
            if (!this.propertyGetters.containsKey(name)) {
               this.setPropertyGetter(name, (MethodHandle)innerClassSpec.getValue(), GuardedInvocationComponent.ValidationType.EXACT_CLASS);
            }
         }

         return;
      }
   }

   private static String decapitalize(String str) {
      assert str != null;

      if (str.isEmpty()) {
         return str;
      } else {
         char c0 = str.charAt(0);
         if (Character.isLowerCase(c0)) {
            return str;
         } else if (str.length() > 1 && Character.isUpperCase(str.charAt(1))) {
            return str;
         } else {
            char[] c = str.toCharArray();
            c[0] = Character.toLowerCase(c0);
            return new String(c);
         }
      }
   }

   abstract FacetIntrospector createFacetIntrospector();

   Collection<String> getReadablePropertyNames() {
      return getUnmodifiableKeys(this.propertyGetters);
   }

   Collection<String> getWritablePropertyNames() {
      return getUnmodifiableKeys(this.propertySetters);
   }

   Collection<String> getMethodNames() {
      return getUnmodifiableKeys(this.methods);
   }

   private static Collection<String> getUnmodifiableKeys(Map<String, ?> m) {
      return Collections.unmodifiableCollection(m.keySet());
   }

   private void setPropertyGetter(String name, SingleDynamicMethod handle, GuardedInvocationComponent.ValidationType validationType) {
      this.propertyGetters.put(name, new AbstractJavaLinker.AnnotatedDynamicMethod(handle, validationType));
   }

   private void setPropertyGetter(Method getter, int prefixLen) {
      this.setPropertyGetter(decapitalize(getter.getName().substring(prefixLen)), createDynamicMethod(getMostGenericGetter(getter)), GuardedInvocationComponent.ValidationType.INSTANCE_OF);
   }

   void setPropertyGetter(String name, MethodHandle handle, GuardedInvocationComponent.ValidationType validationType) {
      this.setPropertyGetter(name, (SingleDynamicMethod)(new SimpleDynamicMethod(handle, this.clazz, name)), validationType);
   }

   private void addMember(String name, AccessibleObject ao, Map<String, DynamicMethod> methodMap) {
      this.addMember(name, createDynamicMethod(ao), methodMap);
   }

   private void addMember(String name, SingleDynamicMethod method, Map<String, DynamicMethod> methodMap) {
      DynamicMethod existingMethod = (DynamicMethod)methodMap.get(name);
      DynamicMethod newMethod = mergeMethods(method, existingMethod, this.clazz, name);
      if (newMethod != existingMethod) {
         methodMap.put(name, newMethod);
      }

   }

   static DynamicMethod createDynamicMethod(Iterable<? extends AccessibleObject> members, Class<?> clazz, String name) {
      DynamicMethod dynMethod = null;

      AccessibleObject method;
      for(Iterator var4 = members.iterator(); var4.hasNext(); dynMethod = mergeMethods(createDynamicMethod(method), dynMethod, clazz, name)) {
         method = (AccessibleObject)var4.next();
      }

      return dynMethod;
   }

   private static SingleDynamicMethod createDynamicMethod(AccessibleObject m) {
      if (CallerSensitiveDetector.isCallerSensitive(m)) {
         return new CallerSensitiveDynamicMethod(m);
      } else {
         MethodHandle mh;
         try {
            mh = unreflectSafely(m);
         } catch (IllegalAccessError var3) {
            return new CallerSensitiveDynamicMethod(m);
         }

         Member member = (Member)m;
         return new SimpleDynamicMethod(mh, member.getDeclaringClass(), member.getName(), m instanceof Constructor);
      }
   }

   private static MethodHandle unreflectSafely(AccessibleObject m) {
      if (m instanceof Method) {
         Method reflMethod = (Method)m;
         MethodHandle handle = Lookup.PUBLIC.unreflect(reflMethod);
         return Modifier.isStatic(reflMethod.getModifiers()) ? StaticClassIntrospector.editStaticMethodHandle(handle) : handle;
      } else {
         return StaticClassIntrospector.editConstructorMethodHandle(Lookup.PUBLIC.unreflectConstructor((Constructor)m));
      }
   }

   private static DynamicMethod mergeMethods(SingleDynamicMethod method, DynamicMethod existing, Class<?> clazz, String name) {
      if (existing == null) {
         return method;
      } else if (existing.contains(method)) {
         return existing;
      } else if (existing instanceof SingleDynamicMethod) {
         OverloadedDynamicMethod odm = new OverloadedDynamicMethod(clazz, name);
         odm.addMethod((SingleDynamicMethod)existing);
         odm.addMethod(method);
         return odm;
      } else if (existing instanceof OverloadedDynamicMethod) {
         ((OverloadedDynamicMethod)existing).addMethod(method);
         return existing;
      } else {
         throw new AssertionError();
      }
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
      LinkRequest ncrequest = request.withoutRuntimeContext();
      CallSiteDescriptor callSiteDescriptor = ncrequest.getCallSiteDescriptor();
      String op = callSiteDescriptor.getNameToken(1);
      if ("callMethod" == op) {
         return this.getCallPropWithThis(callSiteDescriptor, linkerServices);
      } else {
         for(List operations = CallSiteDescriptorFactory.tokenizeOperators(callSiteDescriptor); !operations.isEmpty(); operations = pop(operations)) {
            GuardedInvocationComponent gic = this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
            if (gic != null) {
               return gic.getGuardedInvocation();
            }
         }

         return null;
      }
   }

   protected GuardedInvocationComponent getGuardedInvocationComponent(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> operations) throws Exception {
      if (operations.isEmpty()) {
         return null;
      } else {
         String op = (String)operations.get(0);
         if ("getProp".equals(op)) {
            return this.getPropertyGetter(callSiteDescriptor, linkerServices, pop(operations));
         } else if ("setProp".equals(op)) {
            return this.getPropertySetter(callSiteDescriptor, linkerServices, pop(operations));
         } else {
            return "getMethod".equals(op) ? this.getMethodGetter(callSiteDescriptor, linkerServices, pop(operations)) : null;
         }
      }
   }

   static final <T> List<T> pop(List<T> l) {
      return l.subList(1, l.size());
   }

   MethodHandle getClassGuard(CallSiteDescriptor desc) {
      return this.getClassGuard(desc.getMethodType());
   }

   MethodHandle getClassGuard(MethodType type) {
      return Guards.asType(this.classGuard, type);
   }

   GuardedInvocationComponent getClassGuardedInvocationComponent(MethodHandle invocation, MethodType type) {
      return new GuardedInvocationComponent(invocation, this.getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
   }

   SingleDynamicMethod getConstructorMethod(String signature) {
      return null;
   }

   private MethodHandle getAssignableGuard(MethodType type) {
      return Guards.asType(this.assignableGuard, type);
   }

   private GuardedInvocation getCallPropWithThis(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
      switch(callSiteDescriptor.getNameTokenCount()) {
      case 3:
         return this.createGuardedDynamicMethodInvocation(callSiteDescriptor, linkerServices, callSiteDescriptor.getNameToken(2), this.methods);
      default:
         return null;
      }
   }

   private GuardedInvocation createGuardedDynamicMethodInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, String methodName, Map<String, DynamicMethod> methodMap) {
      MethodHandle inv = this.getDynamicMethodInvocation(callSiteDescriptor, linkerServices, methodName, methodMap);
      return inv == null ? null : new GuardedInvocation(inv, this.getClassGuard(callSiteDescriptor.getMethodType()));
   }

   private MethodHandle getDynamicMethodInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, String methodName, Map<String, DynamicMethod> methodMap) {
      DynamicMethod dynaMethod = this.getDynamicMethod(methodName, methodMap);
      return dynaMethod != null ? dynaMethod.getInvocation(callSiteDescriptor, linkerServices) : null;
   }

   private DynamicMethod getDynamicMethod(String methodName, Map<String, DynamicMethod> methodMap) {
      DynamicMethod dynaMethod = (DynamicMethod)methodMap.get(methodName);
      return (DynamicMethod)(dynaMethod != null ? dynaMethod : this.getExplicitSignatureDynamicMethod(methodName, methodMap));
   }

   private SingleDynamicMethod getExplicitSignatureDynamicMethod(String fullName, Map<String, DynamicMethod> methodsMap) {
      int lastChar = fullName.length() - 1;
      if (fullName.charAt(lastChar) != ')') {
         return null;
      } else {
         int openBrace = fullName.indexOf(40);
         if (openBrace == -1) {
            return null;
         } else {
            String name = fullName.substring(0, openBrace);
            String signature = fullName.substring(openBrace + 1, lastChar);
            DynamicMethod simpleNamedMethod = (DynamicMethod)methodsMap.get(name);
            if (simpleNamedMethod == null) {
               return name.isEmpty() ? this.getConstructorMethod(signature) : null;
            } else {
               return simpleNamedMethod.getMethodForExactParamTypes(signature);
            }
         }
      }
   }

   private GuardedInvocationComponent getPropertySetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> operations) throws Exception {
      switch(callSiteDescriptor.getNameTokenCount()) {
      case 2:
         assertParameterCount(callSiteDescriptor, 3);
         MethodType origType = callSiteDescriptor.getMethodType();
         MethodType type = origType.returnType() == Void.TYPE ? origType : origType.changeReturnType(Object.class);
         MethodType setterType = type.dropParameterTypes(1, 2);
         MethodHandle boundGetter = MethodHandles.insertArguments(this.getPropertySetterHandle, 0, new Object[]{callSiteDescriptor.changeMethodType(setterType), linkerServices});
         MethodHandle typedGetter = linkerServices.asType(boundGetter, type.changeReturnType(MethodHandle.class));
         MethodHandle invokeHandle = MethodHandles.exactInvoker(setterType);
         MethodHandle invokeHandleFolded = MethodHandles.dropArguments(invokeHandle, 2, new Class[]{type.parameterType(1)});
         GuardedInvocationComponent nextComponent = this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
         MethodHandle fallbackFolded;
         if (nextComponent == null) {
            fallbackFolded = MethodHandles.dropArguments(CONSTANT_NULL_DROP_METHOD_HANDLE, 1, type.parameterList()).asType(type.insertParameterTypes(0, new Class[]{MethodHandle.class}));
         } else {
            fallbackFolded = MethodHandles.dropArguments(nextComponent.getGuardedInvocation().getInvocation(), 0, new Class[]{MethodHandle.class});
         }

         MethodHandle compositeSetter = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_METHOD_HANDLE_NOT_NULL, invokeHandleFolded, fallbackFolded), typedGetter);
         if (nextComponent == null) {
            return this.getClassGuardedInvocationComponent(compositeSetter, type);
         }

         return nextComponent.compose(compositeSetter, this.getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
      case 3:
         assertParameterCount(callSiteDescriptor, 2);
         GuardedInvocation gi = this.createGuardedDynamicMethodInvocation(callSiteDescriptor, linkerServices, callSiteDescriptor.getNameToken(2), this.propertySetters);
         if (gi != null) {
            return new GuardedInvocationComponent(gi, this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
         }

         return this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, operations);
      default:
         return null;
      }
   }

   private GuardedInvocationComponent getPropertyGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> ops) throws Exception {
      MethodHandle typedGetter;
      switch(callSiteDescriptor.getNameTokenCount()) {
      case 2:
         MethodType type = callSiteDescriptor.getMethodType().changeReturnType(Object.class);
         assertParameterCount(callSiteDescriptor, 2);
         typedGetter = linkerServices.asType(this.getPropertyGetterHandle, type.changeReturnType(AbstractJavaLinker.AnnotatedDynamicMethod.class));
         MethodHandle callSiteBoundMethodGetter = MethodHandles.insertArguments(GET_ANNOTATED_METHOD, 1, new Object[]{callSiteDescriptor.getLookup(), linkerServices});
         MethodHandle callSiteBoundInvoker = MethodHandles.filterArguments(GETTER_INVOKER, 0, new MethodHandle[]{callSiteBoundMethodGetter});
         MethodHandle invokeHandleTyped = linkerServices.asType(callSiteBoundInvoker, MethodType.methodType(type.returnType(), AbstractJavaLinker.AnnotatedDynamicMethod.class, type.parameterType(0)));
         MethodHandle invokeHandleFolded = MethodHandles.dropArguments(invokeHandleTyped, 2, new Class[]{type.parameterType(1)});
         GuardedInvocationComponent nextComponent = this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
         MethodHandle fallbackFolded;
         MethodHandle compositeGetter;
         if (nextComponent == null) {
            fallbackFolded = MethodHandles.dropArguments(CONSTANT_NULL_DROP_ANNOTATED_METHOD, 1, type.parameterList()).asType(type.insertParameterTypes(0, new Class[]{AbstractJavaLinker.AnnotatedDynamicMethod.class}));
         } else {
            compositeGetter = nextComponent.getGuardedInvocation().getInvocation();
            MethodType nextType = compositeGetter.type();
            fallbackFolded = MethodHandles.dropArguments(compositeGetter.asType(nextType.changeReturnType(Object.class)), 0, new Class[]{AbstractJavaLinker.AnnotatedDynamicMethod.class});
         }

         compositeGetter = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_ANNOTATED_METHOD_NOT_NULL, invokeHandleFolded, fallbackFolded), typedGetter);
         if (nextComponent == null) {
            return this.getClassGuardedInvocationComponent(compositeGetter, type);
         }

         return nextComponent.compose(compositeGetter, this.getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
      case 3:
         assertParameterCount(callSiteDescriptor, 1);
         AbstractJavaLinker.AnnotatedDynamicMethod annGetter = (AbstractJavaLinker.AnnotatedDynamicMethod)this.propertyGetters.get(callSiteDescriptor.getNameToken(2));
         if (annGetter == null) {
            return this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
         }

         typedGetter = annGetter.getInvocation(callSiteDescriptor, linkerServices);
         GuardedInvocationComponent.ValidationType validationType = annGetter.validationType;
         return new GuardedInvocationComponent(typedGetter, this.getGuard(validationType, callSiteDescriptor.getMethodType()), this.clazz, validationType);
      default:
         return null;
      }
   }

   private MethodHandle getGuard(GuardedInvocationComponent.ValidationType validationType, MethodType methodType) {
      switch(validationType) {
      case EXACT_CLASS:
         return this.getClassGuard(methodType);
      case INSTANCE_OF:
         return this.getAssignableGuard(methodType);
      case IS_ARRAY:
         return Guards.isArray(0, methodType);
      case NONE:
         return null;
      default:
         throw new AssertionError();
      }
   }

   private GuardedInvocationComponent getMethodGetter(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices, List<String> ops) throws Exception {
      MethodType type = callSiteDescriptor.getMethodType().changeReturnType(Object.class);
      switch(callSiteDescriptor.getNameTokenCount()) {
      case 2:
         assertParameterCount(callSiteDescriptor, 2);
         GuardedInvocationComponent nextComponent = this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
         if (nextComponent != null && TypeUtilities.areAssignable(DynamicMethod.class, nextComponent.getGuardedInvocation().getInvocation().type().returnType())) {
            MethodHandle typedGetter = linkerServices.asType(this.getDynamicMethod, type);
            MethodHandle returnMethodHandle = linkerServices.asType(MethodHandles.dropArguments(OBJECT_IDENTITY, 1, type.parameterList()), type.insertParameterTypes(0, new Class[]{Object.class}));
            MethodHandle nextComponentInvocation = nextComponent.getGuardedInvocation().getInvocation();

            assert nextComponentInvocation.type().changeReturnType(type.returnType()).equals(type);

            MethodHandle nextCombinedInvocation = MethodHandles.dropArguments(nextComponentInvocation, 0, new Class[]{Object.class});
            MethodHandle compositeGetter = MethodHandles.foldArguments(MethodHandles.guardWithTest(IS_DYNAMIC_METHOD, returnMethodHandle, nextCombinedInvocation), typedGetter);
            return nextComponent.compose(compositeGetter, this.getClassGuard(type), this.clazz, GuardedInvocationComponent.ValidationType.EXACT_CLASS);
         }

         return this.getClassGuardedInvocationComponent(linkerServices.asType(this.getDynamicMethod, type), type);
      case 3:
         assertParameterCount(callSiteDescriptor, 1);
         DynamicMethod method = this.getDynamicMethod(callSiteDescriptor.getNameToken(2));
         if (method == null) {
            return this.getGuardedInvocationComponent(callSiteDescriptor, linkerServices, ops);
         }

         return this.getClassGuardedInvocationComponent(linkerServices.asType(MethodHandles.dropArguments(MethodHandles.constant(Object.class, method), 0, new Class[]{type.parameterType(0)}), type), type);
      default:
         return null;
      }
   }

   static AbstractJavaLinker.MethodPair matchReturnTypes(MethodHandle m1, MethodHandle m2) {
      MethodType type1 = m1.type();
      MethodType type2 = m2.type();
      Class<?> commonRetType = TypeUtilities.getCommonLosslessConversionType(type1.returnType(), type2.returnType());
      return new AbstractJavaLinker.MethodPair(m1.asType(type1.changeReturnType(commonRetType)), m2.asType(type2.changeReturnType(commonRetType)));
   }

   private static void assertParameterCount(CallSiteDescriptor descriptor, int paramCount) {
      if (descriptor.getMethodType().parameterCount() != paramCount) {
         throw new BootstrapMethodError(descriptor.getName() + " must have exactly " + paramCount + " parameters.");
      }
   }

   private Object getPropertyGetterHandle(Object id) {
      return this.propertyGetters.get(String.valueOf(id));
   }

   private MethodHandle getPropertySetterHandle(CallSiteDescriptor setterDescriptor, LinkerServices linkerServices, Object id) {
      return this.getDynamicMethodInvocation(setterDescriptor, linkerServices, String.valueOf(id), this.propertySetters);
   }

   private Object getDynamicMethod(Object name) {
      return this.getDynamicMethod(String.valueOf(name), this.methods);
   }

   DynamicMethod getDynamicMethod(String name) {
      return this.getDynamicMethod(name, this.methods);
   }

   private static Method getMostGenericGetter(Method getter) {
      return getMostGenericGetter(getter.getName(), getter.getReturnType(), getter.getDeclaringClass());
   }

   private static Method getMostGenericGetter(String name, Class<?> returnType, Class<?> declaringClass) {
      if (declaringClass == null) {
         return null;
      } else {
         Class[] var3 = declaringClass.getInterfaces();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class<?> itf = var3[var5];
            Method itfGetter = getMostGenericGetter(name, returnType, itf);
            if (itfGetter != null) {
               return itfGetter;
            }
         }

         Method superGetter = getMostGenericGetter(name, returnType, declaringClass.getSuperclass());
         if (superGetter != null) {
            return superGetter;
         } else {
            if (!CheckRestrictedPackage.isRestrictedClass(declaringClass)) {
               try {
                  return declaringClass.getMethod(name);
               } catch (NoSuchMethodException var8) {
               }
            }

            return null;
         }
      }
   }

   static {
      IS_METHOD_HANDLE_NOT_NULL = Guards.isNotNull().asType(MethodType.methodType(Boolean.TYPE, MethodHandle.class));
      CONSTANT_NULL_DROP_METHOD_HANDLE = MethodHandles.dropArguments(MethodHandles.constant(Object.class, (Object)null), 0, new Class[]{MethodHandle.class});
      privateLookup = new Lookup(MethodHandles.lookup());
      IS_ANNOTATED_METHOD_NOT_NULL = Guards.isNotNull().asType(MethodType.methodType(Boolean.TYPE, AbstractJavaLinker.AnnotatedDynamicMethod.class));
      CONSTANT_NULL_DROP_ANNOTATED_METHOD = MethodHandles.dropArguments(MethodHandles.constant(Object.class, (Object)null), 0, new Class[]{AbstractJavaLinker.AnnotatedDynamicMethod.class});
      GET_ANNOTATED_METHOD = privateLookup.findVirtual(AbstractJavaLinker.AnnotatedDynamicMethod.class, "getTarget", MethodType.methodType(MethodHandle.class, java.lang.invoke.MethodHandles.Lookup.class, LinkerServices.class));
      GETTER_INVOKER = MethodHandles.invoker(MethodType.methodType(Object.class, Object.class));
      IS_DYNAMIC_METHOD = Guards.isInstance(DynamicMethod.class, MethodType.methodType(Boolean.TYPE, Object.class));
      OBJECT_IDENTITY = MethodHandles.identity(Object.class);
      GET_PROPERTY_GETTER_HANDLE = MethodHandles.dropArguments(privateLookup.findOwnSpecial("getPropertyGetterHandle", Object.class, Object.class), 1, new Class[]{Object.class});
      GET_PROPERTY_SETTER_HANDLE = MethodHandles.dropArguments(MethodHandles.dropArguments(privateLookup.findOwnSpecial("getPropertySetterHandle", MethodHandle.class, CallSiteDescriptor.class, LinkerServices.class, Object.class), 3, new Class[]{Object.class}), 5, new Class[]{Object.class});
      GET_DYNAMIC_METHOD = MethodHandles.dropArguments(privateLookup.findOwnSpecial("getDynamicMethod", Object.class, Object.class), 1, new Class[]{Object.class});
   }

   private static final class AnnotatedDynamicMethod {
      private final SingleDynamicMethod method;
      final GuardedInvocationComponent.ValidationType validationType;

      AnnotatedDynamicMethod(SingleDynamicMethod method, GuardedInvocationComponent.ValidationType validationType) {
         this.method = method;
         this.validationType = validationType;
      }

      MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
         return this.method.getInvocation(callSiteDescriptor, linkerServices);
      }

      MethodHandle getTarget(java.lang.invoke.MethodHandles.Lookup lookup, LinkerServices linkerServices) {
         MethodHandle inv = linkerServices.filterInternalObjects(this.method.getTarget(lookup));

         assert inv != null;

         return inv;
      }
   }

   static class MethodPair {
      final MethodHandle method1;
      final MethodHandle method2;

      MethodPair(MethodHandle method1, MethodHandle method2) {
         this.method1 = method1;
         this.method2 = method2;
      }

      MethodHandle guardWithTest(MethodHandle test) {
         return MethodHandles.guardWithTest(test, this.method1, this.method2);
      }
   }
}
