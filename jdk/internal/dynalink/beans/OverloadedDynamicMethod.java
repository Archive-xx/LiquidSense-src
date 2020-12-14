package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.TypeUtilities;

class OverloadedDynamicMethod extends DynamicMethod {
   private final LinkedList<SingleDynamicMethod> methods;
   private final ClassLoader classLoader;

   OverloadedDynamicMethod(Class<?> clazz, String name) {
      this(new LinkedList(), clazz.getClassLoader(), getClassAndMethodName(clazz, name));
   }

   private OverloadedDynamicMethod(LinkedList<SingleDynamicMethod> methods, ClassLoader classLoader, String name) {
      super(name);
      this.methods = methods;
      this.classLoader = classLoader;
   }

   SingleDynamicMethod getMethodForExactParamTypes(String paramTypes) {
      LinkedList<SingleDynamicMethod> matchingMethods = new LinkedList();
      Iterator var3 = this.methods.iterator();

      while(var3.hasNext()) {
         SingleDynamicMethod method = (SingleDynamicMethod)var3.next();
         SingleDynamicMethod matchingMethod = method.getMethodForExactParamTypes(paramTypes);
         if (matchingMethod != null) {
            matchingMethods.add(matchingMethod);
         }
      }

      switch(matchingMethods.size()) {
      case 0:
         return null;
      case 1:
         return (SingleDynamicMethod)matchingMethods.getFirst();
      default:
         throw new BootstrapMethodError("Can't choose among " + matchingMethods + " for argument types " + paramTypes + " for method " + this.getName());
      }
   }

   public MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
      MethodType callSiteType = callSiteDescriptor.getMethodType();
      ApplicableOverloadedMethods subtypingApplicables = this.getApplicables(callSiteType, ApplicableOverloadedMethods.APPLICABLE_BY_SUBTYPING);
      ApplicableOverloadedMethods methodInvocationApplicables = this.getApplicables(callSiteType, ApplicableOverloadedMethods.APPLICABLE_BY_METHOD_INVOCATION_CONVERSION);
      ApplicableOverloadedMethods variableArityApplicables = this.getApplicables(callSiteType, ApplicableOverloadedMethods.APPLICABLE_BY_VARIABLE_ARITY);
      List<SingleDynamicMethod> maximallySpecifics = subtypingApplicables.findMaximallySpecificMethods();
      if (maximallySpecifics.isEmpty()) {
         maximallySpecifics = methodInvocationApplicables.findMaximallySpecificMethods();
         if (maximallySpecifics.isEmpty()) {
            maximallySpecifics = variableArityApplicables.findMaximallySpecificMethods();
         }
      }

      List<SingleDynamicMethod> invokables = (List)this.methods.clone();
      invokables.removeAll(subtypingApplicables.getMethods());
      invokables.removeAll(methodInvocationApplicables.getMethods());
      invokables.removeAll(variableArityApplicables.getMethods());
      Iterator it = invokables.iterator();

      while(it.hasNext()) {
         SingleDynamicMethod m = (SingleDynamicMethod)it.next();
         if (!isApplicableDynamically(linkerServices, callSiteType, m)) {
            it.remove();
         }
      }

      if (invokables.isEmpty() && maximallySpecifics.size() > 1) {
         throw new BootstrapMethodError("Can't choose among " + maximallySpecifics + " for argument types " + callSiteType);
      } else {
         invokables.addAll(maximallySpecifics);
         switch(invokables.size()) {
         case 0:
            return null;
         case 1:
            return ((SingleDynamicMethod)invokables.iterator().next()).getInvocation(callSiteDescriptor, linkerServices);
         default:
            List<MethodHandle> methodHandles = new ArrayList(invokables.size());
            Lookup lookup = callSiteDescriptor.getLookup();
            Iterator var11 = invokables.iterator();

            while(var11.hasNext()) {
               SingleDynamicMethod method = (SingleDynamicMethod)var11.next();
               methodHandles.add(method.getTarget(lookup));
            }

            return (new OverloadedMethod(methodHandles, this, callSiteType, linkerServices)).getInvoker();
         }
      }
   }

   public boolean contains(SingleDynamicMethod m) {
      Iterator var2 = this.methods.iterator();

      SingleDynamicMethod method;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         method = (SingleDynamicMethod)var2.next();
      } while(!method.contains(m));

      return true;
   }

   public boolean isConstructor() {
      assert !this.methods.isEmpty();

      return ((SingleDynamicMethod)this.methods.getFirst()).isConstructor();
   }

   public String toString() {
      List<String> names = new ArrayList(this.methods.size());
      int len = 0;
      Iterator var3 = this.methods.iterator();

      while(var3.hasNext()) {
         SingleDynamicMethod m = (SingleDynamicMethod)var3.next();
         String name = m.getName();
         len += name.length();
         names.add(name);
      }

      Collator collator = Collator.getInstance();
      collator.setStrength(1);
      Collections.sort(names, collator);
      String className = this.getClass().getName();
      int totalLength = className.length() + len + 2 * names.size() + 3;
      StringBuilder b = new StringBuilder(totalLength);
      b.append('[').append(className).append('\n');
      Iterator var7 = names.iterator();

      while(var7.hasNext()) {
         String name = (String)var7.next();
         b.append(' ').append(name).append('\n');
      }

      b.append(']');

      assert b.length() == totalLength;

      return b.toString();
   }

   ClassLoader getClassLoader() {
      return this.classLoader;
   }

   private static boolean isApplicableDynamically(LinkerServices linkerServices, MethodType callSiteType, SingleDynamicMethod m) {
      MethodType methodType = m.getMethodType();
      boolean varArgs = m.isVarArgs();
      int fixedArgLen = methodType.parameterCount() - (varArgs ? 1 : 0);
      int callSiteArgLen = callSiteType.parameterCount();
      if (varArgs) {
         if (callSiteArgLen < fixedArgLen) {
            return false;
         }
      } else if (callSiteArgLen != fixedArgLen) {
         return false;
      }

      for(int i = 1; i < fixedArgLen; ++i) {
         if (!isApplicableDynamically(linkerServices, callSiteType.parameterType(i), methodType.parameterType(i))) {
            return false;
         }
      }

      if (!varArgs) {
         return true;
      } else {
         Class<?> varArgArrayType = methodType.parameterType(fixedArgLen);
         Class<?> varArgType = varArgArrayType.getComponentType();
         if (fixedArgLen != callSiteArgLen - 1) {
            for(int i = fixedArgLen; i < callSiteArgLen; ++i) {
               if (!isApplicableDynamically(linkerServices, callSiteType.parameterType(i), varArgType)) {
                  return false;
               }
            }

            return true;
         } else {
            Class<?> callSiteArgType = callSiteType.parameterType(fixedArgLen);
            return isApplicableDynamically(linkerServices, callSiteArgType, varArgArrayType) || isApplicableDynamically(linkerServices, callSiteArgType, varArgType);
         }
      }
   }

   private static boolean isApplicableDynamically(LinkerServices linkerServices, Class<?> callSiteType, Class<?> methodType) {
      return TypeUtilities.isPotentiallyConvertible(callSiteType, methodType) || linkerServices.canConvert(callSiteType, methodType);
   }

   private ApplicableOverloadedMethods getApplicables(MethodType callSiteType, ApplicableOverloadedMethods.ApplicabilityTest test) {
      return new ApplicableOverloadedMethods(this.methods, callSiteType, test);
   }

   public void addMethod(SingleDynamicMethod method) {
      assert this.constructorFlagConsistent(method);

      this.methods.add(method);
   }

   private boolean constructorFlagConsistent(SingleDynamicMethod method) {
      return this.methods.isEmpty() ? true : ((SingleDynamicMethod)this.methods.getFirst()).isConstructor() == method.isConstructor();
   }
}
