package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.DynamicLinkerFactory;
import jdk.internal.dynalink.GuardedInvocationFilter;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;
import jdk.internal.dynalink.support.TypeUtilities;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.lookup.MethodHandleFunctionality;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticReturnFilters;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.options.Options;

public final class Bootstrap {
   public static final CompilerConstants.Call BOOTSTRAP;
   private static final MethodHandleFunctionality MH;
   private static final MethodHandle VOID_TO_OBJECT;
   private static final int NASHORN_DEFAULT_UNSTABLE_RELINK_THRESHOLD = 16;
   private static final DynamicLinker dynamicLinker;

   private Bootstrap() {
   }

   public static boolean isCallable(Object obj) {
      if (obj != ScriptRuntime.UNDEFINED && obj != null) {
         return obj instanceof ScriptFunction || isJSObjectFunction(obj) || BeansLinker.isDynamicMethod(obj) || obj instanceof BoundCallable || isFunctionalInterfaceObject(obj) || obj instanceof StaticClass;
      } else {
         return false;
      }
   }

   public static boolean isStrictCallable(Object callable) {
      if (callable instanceof ScriptFunction) {
         return ((ScriptFunction)callable).isStrict();
      } else if (isJSObjectFunction(callable)) {
         return ((JSObject)callable).isStrictFunction();
      } else if (callable instanceof BoundCallable) {
         return isStrictCallable(((BoundCallable)callable).getCallable());
      } else if (!BeansLinker.isDynamicMethod(callable) && !(callable instanceof StaticClass) && !isFunctionalInterfaceObject(callable)) {
         throw notFunction(callable);
      } else {
         return false;
      }
   }

   private static ECMAException notFunction(Object obj) {
      return ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(obj));
   }

   private static boolean isJSObjectFunction(Object obj) {
      return obj instanceof JSObject && ((JSObject)obj).isFunction();
   }

   public static boolean isDynamicMethod(Object obj) {
      return BeansLinker.isDynamicMethod(obj instanceof BoundCallable ? ((BoundCallable)obj).getCallable() : obj);
   }

   public static boolean isFunctionalInterfaceObject(Object obj) {
      return !JSType.isPrimitive(obj) && NashornBeansLinker.getFunctionalInterfaceMethodName(obj.getClass()) != null;
   }

   public static CallSite bootstrap(Lookup lookup, String opDesc, MethodType type, int flags) {
      return (CallSite)dynamicLinker.link(LinkerCallSite.newLinkerCallSite(lookup, opDesc, type, flags));
   }

   public static CallSite mathBootstrap(Lookup lookup, String name, MethodType type, int programPoint) {
      byte var6 = -1;
      switch(name.hashCode()) {
      case 3224472:
         if (name.equals("iadd")) {
            var6 = 0;
         }
         break;
      case 3227528:
         if (name.equals("idiv")) {
            var6 = 3;
         }
         break;
      case 3236539:
         if (name.equals("imul")) {
            var6 = 2;
         }
         break;
      case 3236999:
         if (name.equals("ineg")) {
            var6 = 5;
         }
         break;
      case 3240849:
         if (name.equals("irem")) {
            var6 = 4;
         }
         break;
      case 3242295:
         if (name.equals("isub")) {
            var6 = 1;
         }
      }

      MethodHandle mh;
      switch(var6) {
      case 0:
         mh = JSType.ADD_EXACT.methodHandle();
         break;
      case 1:
         mh = JSType.SUB_EXACT.methodHandle();
         break;
      case 2:
         mh = JSType.MUL_EXACT.methodHandle();
         break;
      case 3:
         mh = JSType.DIV_EXACT.methodHandle();
         break;
      case 4:
         mh = JSType.REM_EXACT.methodHandle();
         break;
      case 5:
         mh = JSType.NEGATE_EXACT.methodHandle();
         break;
      default:
         throw new AssertionError("unsupported math intrinsic");
      }

      return new ConstantCallSite(MH.insertArguments(mh, mh.type().parameterCount() - 1, programPoint));
   }

   public static MethodHandle createDynamicInvoker(String opDesc, Class<?> rtype, Class<?>... ptypes) {
      return createDynamicInvoker(opDesc, MethodType.methodType(rtype, ptypes));
   }

   public static MethodHandle createDynamicInvoker(String opDesc, int flags, Class<?> rtype, Class<?>... ptypes) {
      return bootstrap(MethodHandles.publicLookup(), opDesc, MethodType.methodType(rtype, ptypes), flags).dynamicInvoker();
   }

   public static MethodHandle createDynamicInvoker(String opDesc, MethodType type) {
      return bootstrap(MethodHandles.publicLookup(), opDesc, type, 0).dynamicInvoker();
   }

   public static Object bindCallable(Object callable, Object boundThis, Object[] boundArgs) {
      if (callable instanceof ScriptFunction) {
         return ((ScriptFunction)callable).createBound(boundThis, boundArgs);
      } else if (callable instanceof BoundCallable) {
         return ((BoundCallable)callable).bind(boundArgs);
      } else if (isCallable(callable)) {
         return new BoundCallable(callable, boundThis, boundArgs);
      } else {
         throw notFunction(callable);
      }
   }

   public static Object createSuperAdapter(Object adapter) {
      return new JavaSuperAdapter(adapter);
   }

   public static void checkReflectionAccess(Class<?> clazz, boolean isStatic) {
      ReflectionCheckLinker.checkReflectionAccess(clazz, isStatic);
   }

   public static LinkerServices getLinkerServices() {
      return dynamicLinker.getLinkerServices();
   }

   static GuardedInvocation asTypeSafeReturn(GuardedInvocation inv, LinkerServices linkerServices, CallSiteDescriptor desc) {
      return inv == null ? null : inv.asTypeSafeReturn(linkerServices, desc.getMethodType());
   }

   private static MethodHandle unboxReturnType(MethodHandle target, MethodType newType) {
      MethodType targetType = target.type();
      Class<?> oldReturnType = targetType.returnType();
      Class<?> newReturnType = newType.returnType();
      if (TypeUtilities.isWrapperType(oldReturnType)) {
         if (newReturnType.isPrimitive()) {
            assert TypeUtilities.isMethodInvocationConvertible(oldReturnType, newReturnType);

            return MethodHandles.explicitCastArguments(target, targetType.changeReturnType(newReturnType));
         }
      } else if (oldReturnType == Void.TYPE && newReturnType == Object.class) {
         return MethodHandles.filterReturnValue(target, VOID_TO_OBJECT);
      }

      return target;
   }

   static {
      BOOTSTRAP = CompilerConstants.staticCallNoLookup(Bootstrap.class, "bootstrap", CallSite.class, Lookup.class, String.class, MethodType.class, Integer.TYPE);
      MH = MethodHandleFactory.getFunctionality();
      VOID_TO_OBJECT = MH.constant(Object.class, ScriptRuntime.UNDEFINED);
      DynamicLinkerFactory factory = new DynamicLinkerFactory();
      NashornBeansLinker nashornBeansLinker = new NashornBeansLinker();
      factory.setPrioritizedLinkers(new NashornLinker(), new NashornPrimitiveLinker(), new NashornStaticClassLinker(), new BoundCallableLinker(), new JavaSuperAdapterLinker(), new JSObjectLinker(nashornBeansLinker), new BrowserJSObjectLinker(nashornBeansLinker), new ReflectionCheckLinker());
      factory.setFallbackLinkers(nashornBeansLinker, new NashornBottomLinker());
      factory.setSyncOnRelink(true);
      factory.setPrelinkFilter(new GuardedInvocationFilter() {
         public GuardedInvocation filter(GuardedInvocation inv, LinkRequest request, LinkerServices linkerServices) {
            CallSiteDescriptor desc = request.getCallSiteDescriptor();
            return OptimisticReturnFilters.filterOptimisticReturnValue(inv, desc).asType(linkerServices, desc.getMethodType());
         }
      });
      factory.setAutoConversionStrategy(new MethodTypeConversionStrategy() {
         public MethodHandle asType(MethodHandle target, MethodType newType) {
            return Bootstrap.unboxReturnType(target, newType);
         }
      });
      factory.setInternalObjectsFilter(NashornBeansLinker.createHiddenObjectFilter());
      int relinkThreshold = Options.getIntProperty("nashorn.unstable.relink.threshold", 16);
      if (relinkThreshold > -1) {
         factory.setUnstableRelinkThreshold(relinkThreshold);
      }

      factory.setClassLoader(Bootstrap.class.getClassLoader());
      dynamicLinker = factory.createLinker();
   }
}
