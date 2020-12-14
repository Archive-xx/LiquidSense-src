package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodType;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.support.TypeUtilities;

class ApplicableOverloadedMethods {
   private final List<SingleDynamicMethod> methods = new LinkedList();
   private final boolean varArgs;
   static final ApplicableOverloadedMethods.ApplicabilityTest APPLICABLE_BY_SUBTYPING = new ApplicableOverloadedMethods.ApplicabilityTest() {
      boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
         MethodType methodType = method.getMethodType();
         int methodArity = methodType.parameterCount();
         if (methodArity != callSiteType.parameterCount()) {
            return false;
         } else {
            for(int i = 1; i < methodArity; ++i) {
               if (!TypeUtilities.isSubtype(callSiteType.parameterType(i), methodType.parameterType(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   };
   static final ApplicableOverloadedMethods.ApplicabilityTest APPLICABLE_BY_METHOD_INVOCATION_CONVERSION = new ApplicableOverloadedMethods.ApplicabilityTest() {
      boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
         MethodType methodType = method.getMethodType();
         int methodArity = methodType.parameterCount();
         if (methodArity != callSiteType.parameterCount()) {
            return false;
         } else {
            for(int i = 1; i < methodArity; ++i) {
               if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i), methodType.parameterType(i))) {
                  return false;
               }
            }

            return true;
         }
      }
   };
   static final ApplicableOverloadedMethods.ApplicabilityTest APPLICABLE_BY_VARIABLE_ARITY = new ApplicableOverloadedMethods.ApplicabilityTest() {
      boolean isApplicable(MethodType callSiteType, SingleDynamicMethod method) {
         if (!method.isVarArgs()) {
            return false;
         } else {
            MethodType methodType = method.getMethodType();
            int methodArity = methodType.parameterCount();
            int fixArity = methodArity - 1;
            int callSiteArity = callSiteType.parameterCount();
            if (fixArity > callSiteArity) {
               return false;
            } else {
               for(int i = 1; i < fixArity; ++i) {
                  if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(i), methodType.parameterType(i))) {
                     return false;
                  }
               }

               Class<?> varArgType = methodType.parameterType(fixArity).getComponentType();

               for(int ix = fixArity; ix < callSiteArity; ++ix) {
                  if (!TypeUtilities.isMethodInvocationConvertible(callSiteType.parameterType(ix), varArgType)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   };

   ApplicableOverloadedMethods(List<SingleDynamicMethod> methods, MethodType callSiteType, ApplicableOverloadedMethods.ApplicabilityTest test) {
      Iterator var4 = methods.iterator();

      while(var4.hasNext()) {
         SingleDynamicMethod m = (SingleDynamicMethod)var4.next();
         if (test.isApplicable(callSiteType, m)) {
            this.methods.add(m);
         }
      }

      this.varArgs = test == APPLICABLE_BY_VARIABLE_ARITY;
   }

   List<SingleDynamicMethod> getMethods() {
      return this.methods;
   }

   List<SingleDynamicMethod> findMaximallySpecificMethods() {
      return MaximallySpecific.getMaximallySpecificMethods(this.methods, this.varArgs);
   }

   abstract static class ApplicabilityTest {
      abstract boolean isApplicable(MethodType var1, SingleDynamicMethod var2);
   }
}
