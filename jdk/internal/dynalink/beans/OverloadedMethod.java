package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.TypeUtilities;

class OverloadedMethod {
   private final Map<ClassString, MethodHandle> argTypesToMethods = new ConcurrentHashMap();
   private final OverloadedDynamicMethod parent;
   private final MethodType callSiteType;
   private final MethodHandle invoker;
   private final LinkerServices linkerServices;
   private final ArrayList<MethodHandle> fixArgMethods;
   private final ArrayList<MethodHandle> varArgMethods;
   private static final MethodHandle SELECT_METHOD = Lookup.findOwnSpecial(MethodHandles.lookup(), "selectMethod", MethodHandle.class, Object[].class);
   private static final MethodHandle THROW_NO_SUCH_METHOD;
   private static final MethodHandle THROW_AMBIGUOUS_METHOD;

   OverloadedMethod(List<MethodHandle> methodHandles, OverloadedDynamicMethod parent, MethodType callSiteType, LinkerServices linkerServices) {
      this.parent = parent;
      Class<?> commonRetType = getCommonReturnType(methodHandles);
      this.callSiteType = callSiteType.changeReturnType(commonRetType);
      this.linkerServices = linkerServices;
      this.fixArgMethods = new ArrayList(methodHandles.size());
      this.varArgMethods = new ArrayList(methodHandles.size());
      int argNum = callSiteType.parameterCount();
      Iterator var7 = methodHandles.iterator();

      MethodHandle mh;
      while(var7.hasNext()) {
         mh = (MethodHandle)var7.next();
         if (mh.isVarargsCollector()) {
            MethodHandle asFixed = mh.asFixedArity();
            if (argNum == asFixed.type().parameterCount()) {
               this.fixArgMethods.add(asFixed);
            }

            this.varArgMethods.add(mh);
         } else {
            this.fixArgMethods.add(mh);
         }
      }

      this.fixArgMethods.trimToSize();
      this.varArgMethods.trimToSize();
      MethodHandle bound = SELECT_METHOD.bindTo(this);
      mh = SingleDynamicMethod.collectArguments(bound, argNum).asType(callSiteType.changeReturnType(MethodHandle.class));
      this.invoker = linkerServices.asTypeLosslessReturn(MethodHandles.foldArguments(MethodHandles.exactInvoker(this.callSiteType), mh), callSiteType);
   }

   MethodHandle getInvoker() {
      return this.invoker;
   }

   private MethodHandle selectMethod(Object[] args) throws NoSuchMethodException {
      Class<?>[] argTypes = new Class[args.length];

      for(int i = 0; i < argTypes.length; ++i) {
         Object arg = args[i];
         argTypes[i] = arg == null ? ClassString.NULL_CLASS : arg.getClass();
      }

      ClassString classString = new ClassString(argTypes);
      MethodHandle method = (MethodHandle)this.argTypesToMethods.get(classString);
      if (method == null) {
         List<MethodHandle> methods = classString.getMaximallySpecifics(this.fixArgMethods, this.linkerServices, false);
         if (methods.isEmpty()) {
            methods = classString.getMaximallySpecifics(this.varArgMethods, this.linkerServices, true);
         }

         switch(methods.size()) {
         case 0:
            method = this.getNoSuchMethodThrower(argTypes);
            break;
         case 1:
            method = SingleDynamicMethod.getInvocation((MethodHandle)methods.get(0), this.callSiteType, this.linkerServices);
            break;
         default:
            method = this.getAmbiguousMethodThrower(argTypes, methods);
         }

         if (classString.isVisibleFrom(this.parent.getClassLoader())) {
            this.argTypesToMethods.put(classString, method);
         }
      }

      return method;
   }

   private MethodHandle getNoSuchMethodThrower(Class<?>[] argTypes) {
      return this.adaptThrower(MethodHandles.insertArguments(THROW_NO_SUCH_METHOD, 0, new Object[]{this, argTypes}));
   }

   private void throwNoSuchMethod(Class<?>[] argTypes) throws NoSuchMethodException {
      if (this.varArgMethods.isEmpty()) {
         throw new NoSuchMethodException("None of the fixed arity signatures " + getSignatureList(this.fixArgMethods) + " of method " + this.parent.getName() + " match the argument types " + argTypesString(argTypes));
      } else {
         throw new NoSuchMethodException("None of the fixed arity signatures " + getSignatureList(this.fixArgMethods) + " or the variable arity signatures " + getSignatureList(this.varArgMethods) + " of the method " + this.parent.getName() + " match the argument types " + argTypesString(argTypes));
      }
   }

   private MethodHandle getAmbiguousMethodThrower(Class<?>[] argTypes, List<MethodHandle> methods) {
      return this.adaptThrower(MethodHandles.insertArguments(THROW_AMBIGUOUS_METHOD, 0, new Object[]{this, argTypes, methods}));
   }

   private MethodHandle adaptThrower(MethodHandle rawThrower) {
      return MethodHandles.dropArguments(rawThrower, 0, this.callSiteType.parameterList()).asType(this.callSiteType);
   }

   private void throwAmbiguousMethod(Class<?>[] argTypes, List<MethodHandle> methods) throws NoSuchMethodException {
      String arity = ((MethodHandle)methods.get(0)).isVarargsCollector() ? "variable" : "fixed";
      throw new NoSuchMethodException("Can't unambiguously select between " + arity + " arity signatures " + getSignatureList(methods) + " of the method " + this.parent.getName() + " for argument types " + argTypesString(argTypes));
   }

   private static String argTypesString(Class<?>[] classes) {
      StringBuilder b = (new StringBuilder()).append('[');
      appendTypes(b, classes, false);
      return b.append(']').toString();
   }

   private static String getSignatureList(List<MethodHandle> methods) {
      StringBuilder b = (new StringBuilder()).append('[');
      Iterator<MethodHandle> it = methods.iterator();
      if (it.hasNext()) {
         appendSig(b, (MethodHandle)it.next());

         while(it.hasNext()) {
            appendSig(b.append(", "), (MethodHandle)it.next());
         }
      }

      return b.append(']').toString();
   }

   private static void appendSig(StringBuilder b, MethodHandle m) {
      b.append('(');
      appendTypes(b, m.type().parameterArray(), m.isVarargsCollector());
      b.append(')');
   }

   private static void appendTypes(StringBuilder b, Class<?>[] classes, boolean varArg) {
      int l = classes.length;
      int i;
      if (!varArg) {
         if (l > 1) {
            b.append(classes[1].getCanonicalName());

            for(i = 2; i < l; ++i) {
               b.append(", ").append(classes[i].getCanonicalName());
            }
         }
      } else {
         for(i = 1; i < l - 1; ++i) {
            b.append(classes[i].getCanonicalName()).append(", ");
         }

         b.append(classes[l - 1].getComponentType().getCanonicalName()).append("...");
      }

   }

   private static Class<?> getCommonReturnType(List<MethodHandle> methodHandles) {
      Iterator<MethodHandle> it = methodHandles.iterator();

      Class retType;
      for(retType = ((MethodHandle)it.next()).type().returnType(); it.hasNext(); retType = TypeUtilities.getCommonLosslessConversionType(retType, ((MethodHandle)it.next()).type().returnType())) {
      }

      return retType;
   }

   static {
      THROW_NO_SUCH_METHOD = Lookup.findOwnSpecial(MethodHandles.lookup(), "throwNoSuchMethod", Void.TYPE, Class[].class);
      THROW_AMBIGUOUS_METHOD = Lookup.findOwnSpecial(MethodHandles.lookup(), "throwAmbiguousMethod", Void.TYPE, Class[].class, List.class);
   }
}
