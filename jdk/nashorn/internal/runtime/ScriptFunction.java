package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.codegen.ApplySpecialization;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeFunction;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

public class ScriptFunction extends ScriptObject {
   public static final MethodHandle G$PROTOTYPE = findOwnMH_S("G$prototype", Object.class, Object.class);
   public static final MethodHandle S$PROTOTYPE;
   public static final MethodHandle G$LENGTH;
   public static final MethodHandle G$NAME;
   public static final MethodHandle INVOKE_SYNC;
   static final MethodHandle ALLOCATE;
   private static final MethodHandle WRAPFILTER;
   private static final MethodHandle SCRIPTFUNCTION_GLOBALFILTER;
   public static final CompilerConstants.Call GET_SCOPE;
   private static final MethodHandle IS_FUNCTION_MH;
   private static final MethodHandle IS_APPLY_FUNCTION;
   private static final MethodHandle IS_NONSTRICT_FUNCTION;
   private static final MethodHandle ADD_ZEROTH_ELEMENT;
   private static final MethodHandle WRAP_THIS;
   private static final PropertyMap anonmap$;
   private static final PropertyMap strictmodemap$;
   private static final PropertyMap boundfunctionmap$;
   private static final PropertyMap map$;
   private static final Object LAZY_PROTOTYPE;
   private final ScriptObject scope;
   private final ScriptFunctionData data;
   protected PropertyMap allocatorMap;
   protected Object prototype;
   private static LongAdder constructorCount;
   private static LongAdder invokes;
   private static LongAdder allocations;

   private static PropertyMap createStrictModeMap(PropertyMap map) {
      int flags = true;
      PropertyMap newMap = map.addPropertyNoHistory(map.newUserAccessors("arguments", 6));
      newMap = newMap.addPropertyNoHistory(map.newUserAccessors("caller", 6));
      return newMap;
   }

   private static PropertyMap createBoundFunctionMap(PropertyMap strictModeMap) {
      return strictModeMap.deleteProperty(strictModeMap.findProperty("prototype"));
   }

   private static boolean isStrict(int flags) {
      return (flags & 1) != 0;
   }

   private static PropertyMap getMap(boolean strict) {
      return strict ? strictmodemap$ : map$;
   }

   private ScriptFunction(ScriptFunctionData data, PropertyMap map, ScriptObject scope, Global global) {
      super(map);
      if (Context.DEBUG) {
         constructorCount.increment();
      }

      this.data = data;
      this.scope = scope;
      this.setInitialProto(global.getFunctionPrototype());
      this.prototype = LAZY_PROTOTYPE;

      assert this.objectSpill == null;

      if (this.isStrict() || this.isBoundFunction()) {
         ScriptFunction typeErrorThrower = global.getTypeErrorThrower();
         this.initUserAccessors("arguments", 6, typeErrorThrower, typeErrorThrower);
         this.initUserAccessors("caller", 6, typeErrorThrower, typeErrorThrower);
      }

   }

   private ScriptFunction(String name, MethodHandle methodHandle, PropertyMap map, ScriptObject scope, Specialization[] specs, int flags, Global global) {
      this((ScriptFunctionData)(new FinalScriptFunctionData(name, methodHandle, specs, flags)), (PropertyMap)map, (ScriptObject)scope, (Global)global);
   }

   private ScriptFunction(String name, MethodHandle methodHandle, ScriptObject scope, Specialization[] specs, int flags) {
      this(name, methodHandle, getMap(isStrict(flags)), scope, specs, flags, Global.instance());
   }

   protected ScriptFunction(String name, MethodHandle invokeHandle, Specialization[] specs) {
      this(name, invokeHandle, map$, (ScriptObject)null, specs, 6, Global.instance());
   }

   protected ScriptFunction(String name, MethodHandle invokeHandle, PropertyMap map, Specialization[] specs) {
      this(name, invokeHandle, map.addAll(map$), (ScriptObject)null, specs, 6, Global.instance());
   }

   public static ScriptFunction create(Object[] constants, int index, ScriptObject scope) {
      RecompilableScriptFunctionData data = (RecompilableScriptFunctionData)constants[index];
      return new ScriptFunction(data, getMap(data.isStrict()), scope, Global.instance());
   }

   public static ScriptFunction create(Object[] constants, int index) {
      return create(constants, index, (ScriptObject)null);
   }

   public static ScriptFunction createAnonymous() {
      return new ScriptFunction("", GlobalFunctions.ANONYMOUS, anonmap$, (Specialization[])null);
   }

   private static ScriptFunction createBuiltin(String name, MethodHandle methodHandle, Specialization[] specs, int flags) {
      ScriptFunction func = new ScriptFunction(name, methodHandle, (ScriptObject)null, specs, flags);
      func.setPrototype(ScriptRuntime.UNDEFINED);
      func.deleteOwnProperty(func.getMap().findProperty("prototype"));
      return func;
   }

   public static ScriptFunction createBuiltin(String name, MethodHandle methodHandle, Specialization[] specs) {
      return createBuiltin(name, methodHandle, specs, 2);
   }

   public static ScriptFunction createBuiltin(String name, MethodHandle methodHandle) {
      return createBuiltin(name, methodHandle, (Specialization[])null);
   }

   public static ScriptFunction createStrictBuiltin(String name, MethodHandle methodHandle) {
      return createBuiltin(name, methodHandle, (Specialization[])null, 3);
   }

   public final ScriptFunction createBound(Object self, Object[] args) {
      return new ScriptFunction.Bound(this.data.makeBoundFunctionData(this, self, args), this.getTargetFunction());
   }

   public final ScriptFunction createSynchronized(Object sync) {
      MethodHandle mh = Lookup.MH.insertArguments(INVOKE_SYNC, 0, this, sync);
      return createBuiltin(this.getName(), mh);
   }

   public String getClassName() {
      return "Function";
   }

   public boolean isInstance(ScriptObject instance) {
      Object basePrototype = this.getTargetFunction().getPrototype();
      if (!(basePrototype instanceof ScriptObject)) {
         throw ECMAErrors.typeError("prototype.not.an.object", ScriptRuntime.safeToString(this.getTargetFunction()), ScriptRuntime.safeToString(basePrototype));
      } else {
         for(ScriptObject proto = instance.getProto(); proto != null; proto = proto.getProto()) {
            if (proto == basePrototype) {
               return true;
            }
         }

         return false;
      }
   }

   protected ScriptFunction getTargetFunction() {
      return this;
   }

   final boolean isBoundFunction() {
      return this.getTargetFunction() != this;
   }

   public final void setArity(int arity) {
      this.data.setArity(arity);
   }

   public final boolean isStrict() {
      return this.data.isStrict();
   }

   public final boolean needsWrappedThis() {
      return this.data.needsWrappedThis();
   }

   private static boolean needsWrappedThis(Object fn) {
      return fn instanceof ScriptFunction ? ((ScriptFunction)fn).needsWrappedThis() : false;
   }

   final Object invoke(Object self, Object... arguments) throws Throwable {
      if (Context.DEBUG) {
         invokes.increment();
      }

      return this.data.invoke(this, self, arguments);
   }

   final Object construct(Object... arguments) throws Throwable {
      return this.data.construct(this, arguments);
   }

   private Object allocate() {
      if (Context.DEBUG) {
         allocations.increment();
      }

      assert !this.isBoundFunction();

      ScriptObject prototype = this.getAllocatorPrototype();
      ScriptObject object = this.data.allocate(this.getAllocatorMap(prototype));
      if (object != null) {
         object.setInitialProto(prototype);
      }

      return object;
   }

   private synchronized PropertyMap getAllocatorMap(ScriptObject prototype) {
      if (this.allocatorMap == null || this.allocatorMap.isInvalidSharedMapFor(prototype)) {
         this.allocatorMap = this.data.getAllocatorMap(prototype);
      }

      return this.allocatorMap;
   }

   private ScriptObject getAllocatorPrototype() {
      Object prototype = this.getPrototype();
      return prototype instanceof ScriptObject ? (ScriptObject)prototype : Global.objectPrototype();
   }

   public final String safeToString() {
      return this.toSource();
   }

   public final String toString() {
      return this.data.toString();
   }

   public final String toSource() {
      return this.data.toSource();
   }

   public final Object getPrototype() {
      if (this.prototype == LAZY_PROTOTYPE) {
         this.prototype = new PrototypeObject(this);
      }

      return this.prototype;
   }

   public final synchronized void setPrototype(Object newPrototype) {
      if (newPrototype instanceof ScriptObject && newPrototype != this.prototype && this.allocatorMap != null) {
         this.allocatorMap = null;
      }

      this.prototype = newPrototype;
   }

   public final MethodHandle getBoundInvokeHandle(Object self) {
      return Lookup.MH.bindTo(this.bindToCalleeIfNeeded(this.data.getGenericInvoker(this.scope)), self);
   }

   private MethodHandle bindToCalleeIfNeeded(MethodHandle methodHandle) {
      return ScriptFunctionData.needsCallee(methodHandle) ? Lookup.MH.bindTo(methodHandle, this) : methodHandle;
   }

   public final String getName() {
      return this.data.getName();
   }

   public final ScriptObject getScope() {
      return this.scope;
   }

   public static Object G$prototype(Object self) {
      return self instanceof ScriptFunction ? ((ScriptFunction)self).getPrototype() : ScriptRuntime.UNDEFINED;
   }

   public static void S$prototype(Object self, Object prototype) {
      if (self instanceof ScriptFunction) {
         ((ScriptFunction)self).setPrototype(prototype);
      }

   }

   public static int G$length(Object self) {
      return self instanceof ScriptFunction ? ((ScriptFunction)self).data.getArity() : 0;
   }

   public static Object G$name(Object self) {
      return self instanceof ScriptFunction ? ((ScriptFunction)self).getName() : ScriptRuntime.UNDEFINED;
   }

   public static ScriptObject getPrototype(ScriptFunction constructor) {
      if (constructor != null) {
         Object proto = constructor.getPrototype();
         if (proto instanceof ScriptObject) {
            return (ScriptObject)proto;
         }
      }

      return null;
   }

   public static long getConstructorCount() {
      return constructorCount.longValue();
   }

   public static long getInvokes() {
      return invokes.longValue();
   }

   public static long getAllocations() {
      return allocations.longValue();
   }

   protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
      MethodType type = desc.getMethodType();

      assert desc.getMethodType().returnType() == Object.class && !NashornCallSiteDescriptor.isOptimistic(desc);

      CompiledFunction cf = this.data.getBestConstructor(type, this.scope, CompiledFunction.NO_FUNCTIONS);
      GuardedInvocation bestCtorInv = cf.createConstructorInvocation();
      return new GuardedInvocation(pairArguments(bestCtorInv.getInvocation(), type), getFunctionGuard(this, cf.getFlags()), bestCtorInv.getSwitchPoints(), (Class)null);
   }

   private static Object wrapFilter(Object obj) {
      return !(obj instanceof ScriptObject) && ScriptFunctionData.isPrimitiveThis(obj) ? Context.getGlobal().wrapAsObject(obj) : obj;
   }

   private static Object globalFilter(Object object) {
      return Context.getGlobal();
   }

   private static SpecializedFunction.LinkLogic getLinkLogic(Object self, Class<? extends SpecializedFunction.LinkLogic> linkLogicClass) {
      if (linkLogicClass == null) {
         return SpecializedFunction.LinkLogic.EMPTY_INSTANCE;
      } else if (!Context.getContextTrusted().getEnv()._optimistic_types) {
         return null;
      } else {
         Object wrappedSelf = wrapFilter(self);
         if (wrappedSelf instanceof OptimisticBuiltins) {
            return wrappedSelf != self && ((OptimisticBuiltins)wrappedSelf).hasPerInstanceAssumptions() ? null : ((OptimisticBuiltins)wrappedSelf).getLinkLogic(linkLogicClass);
         } else {
            return null;
         }
      }
   }

   protected GuardedInvocation findCallMethod(CallSiteDescriptor desc, LinkRequest request) {
      MethodType type = desc.getMethodType();
      String name = this.getName();
      boolean isUnstable = request.isCallSiteUnstable();
      boolean scopeCall = NashornCallSiteDescriptor.isScope(desc);
      boolean isCall = !scopeCall && this.data.isBuiltin() && "call".equals(name);
      boolean isApply = !scopeCall && this.data.isBuiltin() && "apply".equals(name);
      boolean isApplyOrCall = isCall | isApply;
      MethodHandle boundHandle;
      if (isUnstable && !isApplyOrCall) {
         if (type.parameterCount() == 3 && type.parameterType(2) == Object[].class) {
            boundHandle = ScriptRuntime.APPLY.methodHandle();
         } else {
            boundHandle = Lookup.MH.asCollector(ScriptRuntime.APPLY.methodHandle(), Object[].class, type.parameterCount() - 2);
         }

         return new GuardedInvocation(boundHandle, (MethodHandle)null, (SwitchPoint)null, ClassCastException.class);
      } else {
         MethodHandle guard = null;
         if (isApplyOrCall && !isUnstable) {
            Object[] args = request.getArguments();
            if (Bootstrap.isCallable(args[1])) {
               return this.createApplyOrCallCall(isApply, desc, request, args);
            }
         }

         int programPoint = -1;
         if (NashornCallSiteDescriptor.isOptimistic(desc)) {
            programPoint = NashornCallSiteDescriptor.getProgramPoint(desc);
         }

         CompiledFunction cf = this.data.getBestInvoker(type, this.scope, CompiledFunction.NO_FUNCTIONS);
         Object self = request.getArguments()[1];
         Collection<CompiledFunction> forbidden = new HashSet();
         List<SwitchPoint> sps = new ArrayList();
         Class exceptionGuard = null;

         while(cf.isSpecialization()) {
            Class<? extends SpecializedFunction.LinkLogic> linkLogicClass = cf.getLinkLogicClass();
            SpecializedFunction.LinkLogic linkLogic = getLinkLogic(self, linkLogicClass);
            if (linkLogic != null && linkLogic.checkLinkable(self, desc, request)) {
               DebugLogger log = Context.getContextTrusted().getLogger(Compiler.class);
               if (log.isEnabled()) {
                  log.info("Linking optimistic builtin function: '", name, "' args=", Arrays.toString(request.getArguments()), " desc=", desc);
               }

               exceptionGuard = linkLogic.getRelinkException();
               break;
            }

            forbidden.add(cf);
            CompiledFunction oldCf = cf;
            cf = this.data.getBestInvoker(type, this.scope, forbidden);

            assert oldCf != cf;
         }

         GuardedInvocation bestInvoker = cf.createFunctionInvocation(type.returnType(), programPoint);
         MethodHandle callHandle = bestInvoker.getInvocation();
         if (this.data.needsCallee()) {
            if (scopeCall && this.needsWrappedThis()) {
               boundHandle = Lookup.MH.filterArguments(callHandle, 1, SCRIPTFUNCTION_GLOBALFILTER);
            } else {
               boundHandle = callHandle;
            }
         } else if (this.data.isBuiltin() && "extend".equals(this.data.getName())) {
            boundHandle = Lookup.MH.dropArguments(Lookup.MH.bindTo(callHandle, desc.getLookup()), 0, (Class[])(type.parameterType(0), type.parameterType(1)));
         } else if (scopeCall && this.needsWrappedThis()) {
            boundHandle = Lookup.MH.filterArguments(callHandle, 0, SCRIPTFUNCTION_GLOBALFILTER);
            boundHandle = Lookup.MH.dropArguments(boundHandle, 0, (Class[])(type.parameterType(0)));
         } else {
            boundHandle = Lookup.MH.dropArguments(callHandle, 0, (Class[])(type.parameterType(0)));
         }

         if (!scopeCall && this.needsWrappedThis()) {
            if (ScriptFunctionData.isPrimitiveThis(request.getArguments()[1])) {
               boundHandle = Lookup.MH.filterArguments(boundHandle, 1, WRAPFILTER);
            } else {
               guard = getNonStrictFunctionGuard(this);
            }
         }

         if (isUnstable && NashornCallSiteDescriptor.isApplyToCall(desc)) {
            boundHandle = Lookup.MH.asCollector(boundHandle, Object[].class, type.parameterCount() - 2);
         }

         boundHandle = pairArguments(boundHandle, type);
         if (bestInvoker.getSwitchPoints() != null) {
            sps.addAll(Arrays.asList(bestInvoker.getSwitchPoints()));
         }

         SwitchPoint[] spsArray = sps.isEmpty() ? null : (SwitchPoint[])sps.toArray(new SwitchPoint[sps.size()]);
         return new GuardedInvocation(boundHandle, guard == null ? getFunctionGuard(this, cf.getFlags()) : guard, spsArray, exceptionGuard);
      }
   }

   private GuardedInvocation createApplyOrCallCall(boolean isApply, CallSiteDescriptor desc, LinkRequest request, Object[] args) {
      MethodType descType = desc.getMethodType();
      int paramCount = descType.parameterCount();
      if (descType.parameterType(paramCount - 1).isArray()) {
         return this.createVarArgApplyOrCallCall(isApply, desc, request, args);
      } else {
         boolean passesThis = paramCount > 2;
         boolean passesArgs = paramCount > 3;
         int realArgCount = passesArgs ? paramCount - 3 : 0;
         Object appliedFn = args[1];
         boolean appliedFnNeedsWrappedThis = needsWrappedThis(appliedFn);
         SwitchPoint applyToCallSwitchPoint = Global.getBuiltinFunctionApplySwitchPoint();
         boolean isApplyToCall = NashornCallSiteDescriptor.isApplyToCall(desc);
         boolean isFailedApplyToCall = isApplyToCall && applyToCallSwitchPoint.hasBeenInvalidated();
         MethodType appliedType = descType.dropParameterTypes(0, 1);
         if (!passesThis) {
            appliedType = appliedType.insertParameterTypes(1, new Class[]{Object.class});
         } else if (appliedFnNeedsWrappedThis) {
            appliedType = appliedType.changeParameterType(1, Object.class);
         }

         MethodType dropArgs = Lookup.MH.type(Void.TYPE);
         if (isApply && !isFailedApplyToCall) {
            int pc = appliedType.parameterCount();

            for(int i = 3; i < pc; ++i) {
               dropArgs = dropArgs.appendParameterTypes(appliedType.parameterType(i));
            }

            if (pc > 3) {
               appliedType = appliedType.dropParameterTypes(3, pc);
            }
         }

         if (isApply || isFailedApplyToCall) {
            if (passesArgs) {
               appliedType = appliedType.changeParameterType(2, Object[].class);
               if (isFailedApplyToCall) {
                  appliedType = appliedType.dropParameterTypes(3, paramCount - 1);
               }
            } else {
               appliedType = appliedType.insertParameterTypes(2, new Class[]{Object[].class});
            }
         }

         CallSiteDescriptor appliedDesc = desc.changeMethodType(appliedType);
         Object[] appliedArgs = new Object[isApply ? 3 : appliedType.parameterCount()];
         appliedArgs[0] = appliedFn;
         appliedArgs[1] = passesThis ? (appliedFnNeedsWrappedThis ? ScriptFunctionData.wrapThis(args[2]) : args[2]) : ScriptRuntime.UNDEFINED;
         if (isApply && !isFailedApplyToCall) {
            appliedArgs[2] = passesArgs ? NativeFunction.toApplyArgs(args[3]) : ScriptRuntime.EMPTY_ARRAY;
         } else if (passesArgs) {
            if (isFailedApplyToCall) {
               Object[] tmp = new Object[args.length - 3];
               System.arraycopy(args, 3, tmp, 0, tmp.length);
               appliedArgs[2] = NativeFunction.toApplyArgs(tmp);
            } else {
               assert !isApply;

               System.arraycopy(args, 3, appliedArgs, 2, args.length - 3);
            }
         } else if (isFailedApplyToCall) {
            appliedArgs[2] = ScriptRuntime.EMPTY_ARRAY;
         }

         LinkRequest appliedRequest = request.replaceArguments(appliedDesc, appliedArgs);

         GuardedInvocation appliedInvocation;
         try {
            appliedInvocation = Bootstrap.getLinkerServices().getGuardedInvocation(appliedRequest);
         } catch (Error | RuntimeException var26) {
            throw var26;
         } catch (Exception var27) {
            throw new RuntimeException(var27);
         }

         assert appliedRequest != null;

         Class<?> applyFnType = descType.parameterType(0);
         MethodHandle inv = appliedInvocation.getInvocation();
         if (isApply && !isFailedApplyToCall) {
            if (passesArgs) {
               inv = Lookup.MH.filterArguments(inv, 2, NativeFunction.TO_APPLY_ARGS);
            } else {
               inv = Lookup.MH.insertArguments(inv, 2, (Object)ScriptRuntime.EMPTY_ARRAY);
            }
         }

         if (isApplyToCall) {
            if (isFailedApplyToCall) {
               Context.getContextTrusted().getLogger(ApplySpecialization.class).info("Collection arguments to revert call to apply in " + appliedFn);
               inv = Lookup.MH.asCollector(inv, Object[].class, realArgCount);
            } else {
               appliedInvocation = appliedInvocation.addSwitchPoint(applyToCallSwitchPoint);
            }
         }

         if (!passesThis) {
            inv = bindImplicitThis(appliedFn, inv);
         } else if (appliedFnNeedsWrappedThis) {
            inv = Lookup.MH.filterArguments(inv, 1, WRAP_THIS);
         }

         inv = Lookup.MH.dropArguments(inv, 0, (Class[])(applyFnType));

         for(int i = 0; i < dropArgs.parameterCount(); ++i) {
            inv = Lookup.MH.dropArguments(inv, 4 + i, dropArgs.parameterType(i));
         }

         MethodHandle guard = appliedInvocation.getGuard();
         if (!passesThis && guard.type().parameterCount() > 1) {
            guard = bindImplicitThis(appliedFn, guard);
         }

         MethodType guardType = guard.type();
         guard = Lookup.MH.dropArguments(guard, 0, (Class[])(descType.parameterType(0)));
         MethodHandle applyFnGuard = Lookup.MH.insertArguments(IS_APPLY_FUNCTION, 2, this);
         applyFnGuard = Lookup.MH.dropArguments(applyFnGuard, 2, (Class[])guardType.parameterArray());
         guard = Lookup.MH.foldArguments(applyFnGuard, guard);
         return appliedInvocation.replaceMethods(inv, guard);
      }
   }

   private GuardedInvocation createVarArgApplyOrCallCall(boolean isApply, CallSiteDescriptor desc, LinkRequest request, Object[] args) {
      MethodType descType = desc.getMethodType();
      int paramCount = descType.parameterCount();
      Object[] varArgs = (Object[])((Object[])args[paramCount - 1]);
      int copiedArgCount = args.length - 1;
      int varArgCount = varArgs.length;
      Object[] spreadArgs = new Object[copiedArgCount + varArgCount];
      System.arraycopy(args, 0, spreadArgs, 0, copiedArgCount);
      System.arraycopy(varArgs, 0, spreadArgs, copiedArgCount, varArgCount);
      MethodType spreadType = descType.dropParameterTypes(paramCount - 1, paramCount).appendParameterTypes(Collections.nCopies(varArgCount, Object.class));
      CallSiteDescriptor spreadDesc = desc.changeMethodType(spreadType);
      LinkRequest spreadRequest = request.replaceArguments(spreadDesc, spreadArgs);
      GuardedInvocation spreadInvocation = this.createApplyOrCallCall(isApply, spreadDesc, spreadRequest, spreadArgs);
      return spreadInvocation.replaceMethods(pairArguments(spreadInvocation.getInvocation(), descType), spreadGuardArguments(spreadInvocation.getGuard(), descType));
   }

   private static MethodHandle spreadGuardArguments(MethodHandle guard, MethodType descType) {
      MethodType guardType = guard.type();
      int guardParamCount = guardType.parameterCount();
      int descParamCount = descType.parameterCount();
      int spreadCount = guardParamCount - descParamCount + 1;
      if (spreadCount <= 0) {
         return guard;
      } else {
         MethodHandle arrayConvertingGuard;
         if (guardType.parameterType(guardParamCount - 1).isArray()) {
            arrayConvertingGuard = Lookup.MH.filterArguments(guard, guardParamCount - 1, NativeFunction.TO_APPLY_ARGS);
         } else {
            arrayConvertingGuard = guard;
         }

         return ScriptObject.adaptHandleToVarArgCallSite(arrayConvertingGuard, descParamCount);
      }
   }

   private static MethodHandle bindImplicitThis(Object fn, MethodHandle mh) {
      MethodHandle bound;
      if (fn instanceof ScriptFunction && ((ScriptFunction)fn).needsWrappedThis()) {
         bound = Lookup.MH.filterArguments(mh, 1, SCRIPTFUNCTION_GLOBALFILTER);
      } else {
         bound = mh;
      }

      return Lookup.MH.insertArguments(bound, 1, ScriptRuntime.UNDEFINED);
   }

   MethodHandle getCallMethodHandle(MethodType type, String bindName) {
      return pairArguments(bindToNameIfNeeded(this.bindToCalleeIfNeeded(this.data.getGenericInvoker(this.scope)), bindName), type);
   }

   private static MethodHandle bindToNameIfNeeded(MethodHandle methodHandle, String bindName) {
      if (bindName == null) {
         return methodHandle;
      } else {
         MethodType methodType = methodHandle.type();
         int parameterCount = methodType.parameterCount();
         boolean isVarArg = parameterCount > 0 && methodType.parameterType(parameterCount - 1).isArray();
         return isVarArg ? Lookup.MH.filterArguments(methodHandle, 1, Lookup.MH.insertArguments(ADD_ZEROTH_ELEMENT, 1, bindName)) : Lookup.MH.insertArguments(methodHandle, 1, bindName);
      }
   }

   private static MethodHandle getFunctionGuard(ScriptFunction function, int flags) {
      assert function.data != null;

      return function.data.isBuiltin() ? Guards.getIdentityGuard(function) : Lookup.MH.insertArguments(IS_FUNCTION_MH, 1, function.data);
   }

   private static MethodHandle getNonStrictFunctionGuard(ScriptFunction function) {
      assert function.data != null;

      return Lookup.MH.insertArguments(IS_NONSTRICT_FUNCTION, 2, function.data);
   }

   private static boolean isFunctionMH(Object self, ScriptFunctionData data) {
      return self instanceof ScriptFunction && ((ScriptFunction)self).data == data;
   }

   private static boolean isNonStrictFunction(Object self, Object arg, ScriptFunctionData data) {
      return self instanceof ScriptFunction && ((ScriptFunction)self).data == data && arg instanceof ScriptObject;
   }

   private static boolean isApplyFunction(boolean appliedFnCondition, Object self, Object expectedSelf) {
      return appliedFnCondition && self == expectedSelf;
   }

   private static Object[] addZerothElement(Object[] args, Object value) {
      Object[] src = args == null ? ScriptRuntime.EMPTY_ARRAY : args;
      Object[] result = new Object[src.length + 1];
      System.arraycopy(src, 0, result, 1, src.length);
      result[0] = value;
      return result;
   }

   private static Object invokeSync(ScriptFunction func, Object sync, Object self, Object... args) throws Throwable {
      Object syncObj = sync == ScriptRuntime.UNDEFINED ? self : sync;
      synchronized(syncObj) {
         return func.invoke(self, args);
      }
   }

   private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), ScriptFunction.class, name, Lookup.MH.type(rtype, types));
   }

   private static MethodHandle findOwnMH_V(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findVirtual(MethodHandles.lookup(), ScriptFunction.class, name, Lookup.MH.type(rtype, types));
   }

   // $FF: synthetic method
   ScriptFunction(ScriptFunctionData x0, PropertyMap x1, ScriptObject x2, Global x3, Object x4) {
      this(x0, x1, x2, x3);
   }

   static {
      S$PROTOTYPE = findOwnMH_S("S$prototype", Void.TYPE, Object.class, Object.class);
      G$LENGTH = findOwnMH_S("G$length", Integer.TYPE, Object.class);
      G$NAME = findOwnMH_S("G$name", Object.class, Object.class);
      INVOKE_SYNC = findOwnMH_S("invokeSync", Object.class, ScriptFunction.class, Object.class, Object.class, Object[].class);
      ALLOCATE = findOwnMH_V("allocate", Object.class);
      WRAPFILTER = findOwnMH_S("wrapFilter", Object.class, Object.class);
      SCRIPTFUNCTION_GLOBALFILTER = findOwnMH_S("globalFilter", Object.class, Object.class);
      GET_SCOPE = CompilerConstants.virtualCallNoLookup(ScriptFunction.class, "getScope", ScriptObject.class);
      IS_FUNCTION_MH = findOwnMH_S("isFunctionMH", Boolean.TYPE, Object.class, ScriptFunctionData.class);
      IS_APPLY_FUNCTION = findOwnMH_S("isApplyFunction", Boolean.TYPE, Boolean.TYPE, Object.class, Object.class);
      IS_NONSTRICT_FUNCTION = findOwnMH_S("isNonStrictFunction", Boolean.TYPE, Object.class, Object.class, ScriptFunctionData.class);
      ADD_ZEROTH_ELEMENT = findOwnMH_S("addZerothElement", Object[].class, Object[].class, Object.class);
      WRAP_THIS = Lookup.MH.findStatic(MethodHandles.lookup(), ScriptFunctionData.class, "wrapThis", Lookup.MH.type(Object.class, Object.class));
      LAZY_PROTOTYPE = new Object();
      anonmap$ = PropertyMap.newMap();
      ArrayList<Property> properties = new ArrayList(3);
      properties.add(AccessorProperty.create("prototype", 6, G$PROTOTYPE, S$PROTOTYPE));
      properties.add(AccessorProperty.create("length", 7, G$LENGTH, (MethodHandle)null));
      properties.add(AccessorProperty.create("name", 7, G$NAME, (MethodHandle)null));
      map$ = PropertyMap.newMap((Collection)properties);
      strictmodemap$ = createStrictModeMap(map$);
      boundfunctionmap$ = createBoundFunctionMap(strictmodemap$);
      if (Context.DEBUG) {
         constructorCount = new LongAdder();
         invokes = new LongAdder();
         allocations = new LongAdder();
      }

   }

   private static class Bound extends ScriptFunction {
      private final ScriptFunction target;

      Bound(ScriptFunctionData boundData, ScriptFunction target) {
         super(boundData, ScriptFunction.boundfunctionmap$, (ScriptObject)null, Global.instance(), null);
         this.setPrototype(ScriptRuntime.UNDEFINED);
         this.target = target;
      }

      protected ScriptFunction getTargetFunction() {
         return this.target;
      }
   }
}
