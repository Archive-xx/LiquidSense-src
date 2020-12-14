package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Array;
import java.util.StringTokenizer;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.Guards;

abstract class SingleDynamicMethod extends DynamicMethod {
   private static final MethodHandle CAN_CONVERT_TO;

   SingleDynamicMethod(String name) {
      super(name);
   }

   abstract boolean isVarArgs();

   abstract MethodType getMethodType();

   abstract MethodHandle getTarget(Lookup var1);

   MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
      return getInvocation(this.getTarget(callSiteDescriptor.getLookup()), callSiteDescriptor.getMethodType(), linkerServices);
   }

   SingleDynamicMethod getMethodForExactParamTypes(String paramTypes) {
      return typeMatchesDescription(paramTypes, this.getMethodType()) ? this : null;
   }

   boolean contains(SingleDynamicMethod method) {
      return this.getMethodType().parameterList().equals(method.getMethodType().parameterList());
   }

   static String getMethodNameWithSignature(MethodType type, String methodName, boolean withReturnType) {
      String typeStr = type.toString();
      int retTypeIndex = typeStr.lastIndexOf(41) + 1;
      int secondParamIndex = typeStr.indexOf(44) + 1;
      if (secondParamIndex == 0) {
         secondParamIndex = retTypeIndex - 1;
      }

      StringBuilder b = new StringBuilder();
      if (withReturnType) {
         b.append(typeStr, retTypeIndex, typeStr.length()).append(' ');
      }

      return b.append(methodName).append('(').append(typeStr, secondParamIndex, retTypeIndex).toString();
   }

   static MethodHandle getInvocation(MethodHandle target, MethodType callSiteType, LinkerServices linkerServices) {
      MethodHandle filteredTarget = linkerServices.filterInternalObjects(target);
      MethodType methodType = filteredTarget.type();
      int paramsLen = methodType.parameterCount();
      boolean varArgs = target.isVarargsCollector();
      MethodHandle fixTarget = varArgs ? filteredTarget.asFixedArity() : filteredTarget;
      int fixParamsLen = varArgs ? paramsLen - 1 : paramsLen;
      int argsLen = callSiteType.parameterCount();
      if (argsLen < fixParamsLen) {
         return null;
      } else if (argsLen == fixParamsLen) {
         MethodHandle matchedMethod;
         if (varArgs) {
            matchedMethod = MethodHandles.insertArguments(fixTarget, fixParamsLen, new Object[]{Array.newInstance(methodType.parameterType(fixParamsLen).getComponentType(), 0)});
         } else {
            matchedMethod = fixTarget;
         }

         return createConvertingInvocation(matchedMethod, linkerServices, callSiteType);
      } else if (!varArgs) {
         return null;
      } else {
         Class<?> varArgType = methodType.parameterType(fixParamsLen);
         if (argsLen == paramsLen) {
            Class<?> callSiteLastArgType = callSiteType.parameterType(fixParamsLen);
            if (varArgType.isAssignableFrom(callSiteLastArgType)) {
               return createConvertingInvocation(filteredTarget, linkerServices, callSiteType).asVarargsCollector(callSiteLastArgType);
            } else {
               MethodHandle varArgCollectingInvocation = createConvertingInvocation(collectArguments(fixTarget, argsLen), linkerServices, callSiteType);
               boolean isAssignableFromArray = callSiteLastArgType.isAssignableFrom(varArgType);
               boolean isCustomConvertible = linkerServices.canConvert(callSiteLastArgType, varArgType);
               if (!isAssignableFromArray && !isCustomConvertible) {
                  return varArgCollectingInvocation;
               } else {
                  MethodHandle arrayConvertingInvocation = createConvertingInvocation(MethodHandles.filterArguments(fixTarget, fixParamsLen, new MethodHandle[]{linkerServices.getTypeConverter(callSiteLastArgType, varArgType)}), linkerServices, callSiteType);
                  MethodHandle canConvertArgToArray = MethodHandles.insertArguments(CAN_CONVERT_TO, 0, new Object[]{linkerServices, varArgType});
                  MethodHandle canConvertLastArgToArray = MethodHandles.dropArguments(canConvertArgToArray, 0, MethodType.genericMethodType(fixParamsLen).parameterList()).asType(callSiteType.changeReturnType(Boolean.TYPE));
                  MethodHandle convertToArrayWhenPossible = MethodHandles.guardWithTest(canConvertLastArgToArray, arrayConvertingInvocation, varArgCollectingInvocation);
                  if (isAssignableFromArray) {
                     return MethodHandles.guardWithTest(Guards.isInstance(varArgType, fixParamsLen, callSiteType), createConvertingInvocation(fixTarget, linkerServices, callSiteType), isCustomConvertible ? convertToArrayWhenPossible : varArgCollectingInvocation);
                  } else {
                     assert isCustomConvertible;

                     return convertToArrayWhenPossible;
                  }
               }
            }
         } else {
            return createConvertingInvocation(collectArguments(fixTarget, argsLen), linkerServices, callSiteType);
         }
      }
   }

   private static boolean canConvertTo(LinkerServices linkerServices, Class<?> to, Object obj) {
      return obj == null ? false : linkerServices.canConvert(obj.getClass(), to);
   }

   static MethodHandle collectArguments(MethodHandle target, int parameterCount) {
      MethodType methodType = target.type();
      int fixParamsLen = methodType.parameterCount() - 1;
      Class<?> arrayType = methodType.parameterType(fixParamsLen);
      return target.asCollector(arrayType, parameterCount - fixParamsLen);
   }

   private static MethodHandle createConvertingInvocation(MethodHandle sizedMethod, LinkerServices linkerServices, MethodType callSiteType) {
      return linkerServices.asTypeLosslessReturn(sizedMethod, callSiteType);
   }

   private static boolean typeMatchesDescription(String paramTypes, MethodType type) {
      StringTokenizer tok = new StringTokenizer(paramTypes, ", ");

      for(int i = 1; i < type.parameterCount(); ++i) {
         if (!tok.hasMoreTokens() || !typeNameMatches(tok.nextToken(), type.parameterType(i))) {
            return false;
         }
      }

      return !tok.hasMoreTokens();
   }

   private static boolean typeNameMatches(String typeName, Class<?> type) {
      return typeName.equals(typeName.indexOf(46) == -1 ? type.getSimpleName() : type.getCanonicalName());
   }

   static {
      CAN_CONVERT_TO = jdk.internal.dynalink.support.Lookup.findOwnStatic(MethodHandles.lookup(), "canConvertTo", Boolean.TYPE, LinkerServices.class, Class.class, Object.class);
   }
}
