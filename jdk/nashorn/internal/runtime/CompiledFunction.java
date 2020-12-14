package jdk.nashorn.internal.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.logging.Level;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.TypeMap;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.events.RecompilationEvent;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

final class CompiledFunction {
   private static final MethodHandle NEWFILTER = findOwnMH("newFilter", Object.class, Object.class, Object.class);
   private static final MethodHandle RELINK_COMPOSABLE_INVOKER;
   private static final MethodHandle HANDLE_REWRITE_EXCEPTION;
   private static final MethodHandle RESTOF_INVOKER;
   private final DebugLogger log;
   static final Collection<CompiledFunction> NO_FUNCTIONS;
   private MethodHandle invoker;
   private MethodHandle constructor;
   private CompiledFunction.OptimismInfo optimismInfo;
   private final int flags;
   private final MethodType callSiteType;
   private final Specialization specialization;

   CompiledFunction(MethodHandle invoker) {
      this(invoker, (MethodHandle)null, (Specialization)null);
   }

   static CompiledFunction createBuiltInConstructor(MethodHandle invoker, Specialization specialization) {
      return new CompiledFunction(Lookup.MH.insertArguments(invoker, 0, false), createConstructorFromInvoker(Lookup.MH.insertArguments(invoker, 0, true)), specialization);
   }

   CompiledFunction(MethodHandle invoker, MethodHandle constructor, Specialization specialization) {
      this(invoker, constructor, 0, (MethodType)null, specialization, DebugLogger.DISABLED_LOGGER);
   }

   CompiledFunction(MethodHandle invoker, MethodHandle constructor, int flags, MethodType callSiteType, Specialization specialization, DebugLogger log) {
      this.specialization = specialization;
      if (specialization != null && specialization.isOptimistic()) {
         this.invoker = Lookup.MH.insertArguments(invoker, invoker.type().parameterCount() - 1, 1);
         throw new AssertionError("Optimistic (UnwarrantedOptimismException throwing) builtin functions are currently not in use");
      } else {
         this.invoker = invoker;
         this.constructor = constructor;
         this.flags = flags;
         this.callSiteType = callSiteType;
         this.log = log;
      }
   }

   CompiledFunction(MethodHandle invoker, RecompilableScriptFunctionData functionData, Map<Integer, Type> invalidatedProgramPoints, MethodType callSiteType, int flags) {
      this(invoker, (MethodHandle)null, flags, callSiteType, (Specialization)null, functionData.getLogger());
      if ((flags & 2048) != 0) {
         this.optimismInfo = new CompiledFunction.OptimismInfo(functionData, invalidatedProgramPoints);
      } else {
         this.optimismInfo = null;
      }

   }

   static CompiledFunction createBuiltInConstructor(MethodHandle invoker) {
      return new CompiledFunction(Lookup.MH.insertArguments(invoker, 0, false), createConstructorFromInvoker(Lookup.MH.insertArguments(invoker, 0, true)), (Specialization)null);
   }

   boolean isSpecialization() {
      return this.specialization != null;
   }

   boolean hasLinkLogic() {
      return this.getLinkLogicClass() != null;
   }

   Class<? extends SpecializedFunction.LinkLogic> getLinkLogicClass() {
      if (this.isSpecialization()) {
         Class<? extends SpecializedFunction.LinkLogic> linkLogicClass = this.specialization.getLinkLogicClass();

         assert !SpecializedFunction.LinkLogic.isEmpty(linkLogicClass) : "empty link logic classes should have been removed by nasgen";

         return linkLogicClass;
      } else {
         return null;
      }
   }

   int getFlags() {
      return this.flags;
   }

   boolean isOptimistic() {
      return this.isSpecialization() ? this.specialization.isOptimistic() : false;
   }

   boolean isApplyToCall() {
      return (this.flags & 4096) != 0;
   }

   boolean isVarArg() {
      return isVarArgsType(this.invoker.type());
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      Class<? extends SpecializedFunction.LinkLogic> linkLogicClass = this.getLinkLogicClass();
      sb.append("[invokerType=").append(this.invoker.type()).append(" ctor=").append(this.constructor).append(" weight=").append(this.weight()).append(" linkLogic=").append(linkLogicClass != null ? linkLogicClass.getSimpleName() : "none");
      return sb.toString();
   }

   boolean needsCallee() {
      return ScriptFunctionData.needsCallee(this.invoker);
   }

   MethodHandle createComposableInvoker() {
      return this.createComposableInvoker(false);
   }

   private MethodHandle getConstructor() {
      if (this.constructor == null) {
         this.constructor = createConstructorFromInvoker(this.createInvokerForPessimisticCaller());
      }

      return this.constructor;
   }

   private MethodHandle createInvokerForPessimisticCaller() {
      return this.createInvoker(Object.class, -1);
   }

   private static MethodHandle createConstructorFromInvoker(MethodHandle invoker) {
      boolean needsCallee = ScriptFunctionData.needsCallee(invoker);
      MethodHandle swapped = needsCallee ? swapCalleeAndThis(invoker) : invoker;
      MethodHandle returnsObject = Lookup.MH.asType(swapped, swapped.type().changeReturnType(Object.class));
      MethodType ctorType = returnsObject.type();
      Class<?>[] ctorArgs = ctorType.dropParameterTypes(0, 1).parameterArray();
      MethodHandle filtered = Lookup.MH.foldArguments(Lookup.MH.dropArguments(NEWFILTER, 2, (Class[])ctorArgs), returnsObject);
      return needsCallee ? Lookup.MH.foldArguments(filtered, ScriptFunction.ALLOCATE) : Lookup.MH.filterArguments(filtered, 0, ScriptFunction.ALLOCATE);
   }

   private static MethodHandle swapCalleeAndThis(MethodHandle mh) {
      MethodType type = mh.type();

      assert type.parameterType(0) == ScriptFunction.class : type;

      assert type.parameterType(1) == Object.class : type;

      MethodType newType = type.changeParameterType(0, Object.class).changeParameterType(1, ScriptFunction.class);
      int[] reorder = new int[type.parameterCount()];
      reorder[0] = 1;

      assert reorder[1] == 0;

      for(int i = 2; i < reorder.length; reorder[i] = i++) {
      }

      return MethodHandles.permuteArguments(mh, newType, reorder);
   }

   MethodHandle createComposableConstructor() {
      return this.createComposableInvoker(true);
   }

   boolean hasConstructor() {
      return this.constructor != null;
   }

   MethodType type() {
      return this.invoker.type();
   }

   int weight() {
      return weight(this.type());
   }

   private static int weight(MethodType type) {
      if (isVarArgsType(type)) {
         return Integer.MAX_VALUE;
      } else {
         int weight = Type.typeFor(type.returnType()).getWeight();

         for(int i = 0; i < type.parameterCount(); ++i) {
            Class<?> paramType = type.parameterType(i);
            int pweight = Type.typeFor(paramType).getWeight() * 2;
            weight += pweight;
         }

         weight += type.parameterCount();
         return weight;
      }
   }

   static boolean isVarArgsType(MethodType type) {
      assert type.parameterCount() >= 1 : type;

      return type.parameterType(type.parameterCount() - 1) == Object[].class;
   }

   static boolean moreGenericThan(MethodType mt0, MethodType mt1) {
      return weight(mt0) > weight(mt1);
   }

   boolean betterThanFinal(CompiledFunction other, MethodType callSiteMethodType) {
      return other == null ? true : betterThanFinal(this, other, callSiteMethodType);
   }

   private static boolean betterThanFinal(CompiledFunction cf, CompiledFunction other, MethodType callSiteMethodType) {
      MethodType thisMethodType = cf.type();
      MethodType otherMethodType = other.type();
      int thisParamCount = getParamCount(thisMethodType);
      int otherParamCount = getParamCount(otherMethodType);
      int callSiteRawParamCount = getParamCount(callSiteMethodType);
      boolean csVarArg = callSiteRawParamCount == Integer.MAX_VALUE;
      int callSiteParamCount = csVarArg ? callSiteRawParamCount : callSiteRawParamCount - 1;
      int thisDiscardsParams = Math.max(callSiteParamCount - thisParamCount, 0);
      int otherDiscardsParams = Math.max(callSiteParamCount - otherParamCount, 0);
      if (thisDiscardsParams < otherDiscardsParams) {
         return true;
      } else if (thisDiscardsParams > otherDiscardsParams) {
         return false;
      } else {
         boolean thisVarArg = thisParamCount == Integer.MAX_VALUE;
         boolean otherVarArg = otherParamCount == Integer.MAX_VALUE;
         int otherRetWeightDelta;
         int widenRetDelta;
         int narrowRetDelta;
         if (!thisVarArg || !otherVarArg || !csVarArg) {
            Type[] thisType = toTypeWithoutCallee(thisMethodType, 0);
            Type[] otherType = toTypeWithoutCallee(otherMethodType, 0);
            Type[] callSiteType = toTypeWithoutCallee(callSiteMethodType, 1);
            otherRetWeightDelta = 0;
            widenRetDelta = 0;
            narrowRetDelta = Math.min(Math.min(thisParamCount, otherParamCount), callSiteParamCount);

            int i;
            for(i = 0; i < narrowRetDelta; ++i) {
               int callSiteParamWeight = getParamType(i, callSiteType, csVarArg).getWeight();
               int thisParamWeightDelta = getParamType(i, thisType, thisVarArg).getWeight() - callSiteParamWeight;
               int otherParamWeightDelta = getParamType(i, otherType, otherVarArg).getWeight() - callSiteParamWeight;
               otherRetWeightDelta += Math.max(-thisParamWeightDelta, 0) - Math.max(-otherParamWeightDelta, 0);
               widenRetDelta += Math.max(thisParamWeightDelta, 0) - Math.max(otherParamWeightDelta, 0);
            }

            if (!thisVarArg) {
               for(i = callSiteParamCount; i < thisParamCount; ++i) {
                  otherRetWeightDelta += Math.max(Type.OBJECT.getWeight() - thisType[i].getWeight(), 0);
               }
            }

            if (!otherVarArg) {
               for(i = callSiteParamCount; i < otherParamCount; ++i) {
                  otherRetWeightDelta -= Math.max(Type.OBJECT.getWeight() - otherType[i].getWeight(), 0);
               }
            }

            if (otherRetWeightDelta < 0) {
               return true;
            }

            if (otherRetWeightDelta > 0) {
               return false;
            }

            if (widenRetDelta < 0) {
               return true;
            }

            if (widenRetDelta > 0) {
               return false;
            }
         }

         if (thisParamCount == callSiteParamCount && otherParamCount != callSiteParamCount) {
            return true;
         } else if (thisParamCount != callSiteParamCount && otherParamCount == callSiteParamCount) {
            return false;
         } else {
            if (thisVarArg) {
               if (!otherVarArg) {
                  return true;
               }
            } else if (otherVarArg) {
               return false;
            }

            int fnParamDelta = thisParamCount - otherParamCount;
            if (fnParamDelta < 0) {
               return true;
            } else if (fnParamDelta > 0) {
               return false;
            } else {
               int callSiteRetWeight = Type.typeFor(callSiteMethodType.returnType()).getWeight();
               int thisRetWeightDelta = Type.typeFor(thisMethodType.returnType()).getWeight() - callSiteRetWeight;
               otherRetWeightDelta = Type.typeFor(otherMethodType.returnType()).getWeight() - callSiteRetWeight;
               widenRetDelta = Math.max(thisRetWeightDelta, 0) - Math.max(otherRetWeightDelta, 0);
               if (widenRetDelta < 0) {
                  return true;
               } else if (widenRetDelta > 0) {
                  return false;
               } else {
                  narrowRetDelta = Math.max(-thisRetWeightDelta, 0) - Math.max(-otherRetWeightDelta, 0);
                  if (narrowRetDelta < 0) {
                     return true;
                  } else if (narrowRetDelta > 0) {
                     return false;
                  } else if (cf.isSpecialization() != other.isSpecialization()) {
                     return cf.isSpecialization();
                  } else if (cf.isSpecialization() && other.isSpecialization()) {
                     return cf.getLinkLogicClass() != null;
                  } else {
                     throw new AssertionError(thisMethodType + " identically applicable to " + otherMethodType + " for " + callSiteMethodType);
                  }
               }
            }
         }
      }
   }

   private static Type[] toTypeWithoutCallee(MethodType type, int thisIndex) {
      int paramCount = type.parameterCount();
      Type[] t = new Type[paramCount - thisIndex];

      for(int i = thisIndex; i < paramCount; ++i) {
         t[i - thisIndex] = Type.typeFor(type.parameterType(i));
      }

      return t;
   }

   private static Type getParamType(int i, Type[] paramTypes, boolean isVarArg) {
      int fixParamCount = paramTypes.length - (isVarArg ? 1 : 0);
      if (i < fixParamCount) {
         return paramTypes[i];
      } else {
         assert isVarArg;

         return ((ArrayType)paramTypes[paramTypes.length - 1]).getElementType();
      }
   }

   boolean matchesCallSite(MethodType other, boolean pickVarArg) {
      if (other.equals(this.callSiteType)) {
         return true;
      } else {
         MethodType type = this.type();
         int fnParamCount = getParamCount(type);
         boolean isVarArg = fnParamCount == Integer.MAX_VALUE;
         if (isVarArg) {
            return pickVarArg;
         } else {
            int csParamCount = getParamCount(other);
            boolean csIsVarArg = csParamCount == Integer.MAX_VALUE;
            if (csIsVarArg && this.isApplyToCall()) {
               return false;
            } else {
               int thisThisIndex = this.needsCallee() ? 1 : 0;
               int fnParamCountNoCallee = fnParamCount - thisThisIndex;
               int minParams = Math.min(csParamCount - 1, fnParamCountNoCallee);

               int i;
               for(i = 0; i < minParams; ++i) {
                  Type fnType = Type.typeFor(type.parameterType(i + thisThisIndex));
                  Type csType = csIsVarArg ? Type.OBJECT : Type.typeFor(other.parameterType(i + 1));
                  if (!fnType.isEquivalentTo(csType)) {
                     return false;
                  }
               }

               for(i = minParams; i < fnParamCountNoCallee; ++i) {
                  if (!Type.typeFor(type.parameterType(i + thisThisIndex)).isEquivalentTo(Type.OBJECT)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private static int getParamCount(MethodType type) {
      int paramCount = type.parameterCount();
      return type.parameterType(paramCount - 1).isArray() ? Integer.MAX_VALUE : paramCount;
   }

   private boolean canBeDeoptimized() {
      return this.optimismInfo != null;
   }

   private MethodHandle createComposableInvoker(boolean isConstructor) {
      MethodHandle handle = this.getInvokerOrConstructor(isConstructor);
      if (!this.canBeDeoptimized()) {
         return handle;
      } else {
         CallSite cs = new MutableCallSite(handle.type());
         relinkComposableInvoker(cs, this, isConstructor);
         return cs.dynamicInvoker();
      }
   }

   private synchronized CompiledFunction.HandleAndAssumptions getValidOptimisticInvocation(Supplier<MethodHandle> invocationSupplier) {
      while(true) {
         MethodHandle handle = (MethodHandle)invocationSupplier.get();
         SwitchPoint assumptions = this.canBeDeoptimized() ? this.optimismInfo.optimisticAssumptions : null;
         if (assumptions == null || !assumptions.hasBeenInvalidated()) {
            return new CompiledFunction.HandleAndAssumptions(handle, assumptions);
         }

         try {
            this.wait();
         } catch (InterruptedException var5) {
         }
      }
   }

   private static void relinkComposableInvoker(CallSite cs, final CompiledFunction inv, final boolean constructor) {
      CompiledFunction.HandleAndAssumptions handleAndAssumptions = inv.getValidOptimisticInvocation(new Supplier<MethodHandle>() {
         public MethodHandle get() {
            return inv.getInvokerOrConstructor(constructor);
         }
      });
      MethodHandle handle = handleAndAssumptions.handle;
      SwitchPoint assumptions = handleAndAssumptions.assumptions;
      MethodHandle target;
      if (assumptions == null) {
         target = handle;
      } else {
         MethodHandle relink = MethodHandles.insertArguments(RELINK_COMPOSABLE_INVOKER, 0, new Object[]{cs, inv, constructor});
         target = assumptions.guardWithTest(handle, MethodHandles.foldArguments(cs.dynamicInvoker(), relink));
      }

      cs.setTarget(target.asType(cs.type()));
   }

   private MethodHandle getInvokerOrConstructor(boolean selectCtor) {
      return selectCtor ? this.getConstructor() : this.createInvokerForPessimisticCaller();
   }

   GuardedInvocation createFunctionInvocation(final Class<?> callSiteReturnType, final int callerProgramPoint) {
      return this.getValidOptimisticInvocation(new Supplier<MethodHandle>() {
         public MethodHandle get() {
            return CompiledFunction.this.createInvoker(callSiteReturnType, callerProgramPoint);
         }
      }).createInvocation();
   }

   GuardedInvocation createConstructorInvocation() {
      return this.getValidOptimisticInvocation(new Supplier<MethodHandle>() {
         public MethodHandle get() {
            return CompiledFunction.this.getConstructor();
         }
      }).createInvocation();
   }

   private MethodHandle createInvoker(Class<?> callSiteReturnType, int callerProgramPoint) {
      boolean isOptimistic = this.canBeDeoptimized();
      MethodHandle handleRewriteException = isOptimistic ? this.createRewriteExceptionHandler() : null;
      MethodHandle inv = this.invoker;
      if (UnwarrantedOptimismException.isValid(callerProgramPoint)) {
         inv = OptimisticReturnFilters.filterOptimisticReturnValue(inv, callSiteReturnType, callerProgramPoint);
         inv = changeReturnType(inv, callSiteReturnType);
         if (callSiteReturnType.isPrimitive() && handleRewriteException != null) {
            handleRewriteException = OptimisticReturnFilters.filterOptimisticReturnValue(handleRewriteException, callSiteReturnType, callerProgramPoint);
         }
      } else if (isOptimistic) {
         inv = changeReturnType(inv, callSiteReturnType);
      }

      if (isOptimistic) {
         assert handleRewriteException != null;

         MethodHandle typedHandleRewriteException = changeReturnType(handleRewriteException, inv.type().returnType());
         return Lookup.MH.catchException(inv, RewriteException.class, typedHandleRewriteException);
      } else {
         return inv;
      }
   }

   private MethodHandle createRewriteExceptionHandler() {
      return Lookup.MH.foldArguments(RESTOF_INVOKER, Lookup.MH.insertArguments(HANDLE_REWRITE_EXCEPTION, 0, this, this.optimismInfo));
   }

   private static MethodHandle changeReturnType(MethodHandle mh, Class<?> newReturnType) {
      return Bootstrap.getLinkerServices().asType(mh, mh.type().changeReturnType(newReturnType));
   }

   private static MethodHandle handleRewriteException(CompiledFunction function, CompiledFunction.OptimismInfo oldOptimismInfo, RewriteException re) {
      return function.handleRewriteException(oldOptimismInfo, re);
   }

   private static List<String> toStringInvalidations(Map<Integer, Type> ipp) {
      if (ipp == null) {
         return Collections.emptyList();
      } else {
         List<String> list = new ArrayList();
         Iterator iter = ipp.entrySet().iterator();

         while(iter.hasNext()) {
            Entry<Integer, Type> entry = (Entry)iter.next();
            char bct = ((Type)entry.getValue()).getBytecodeStackType();
            String type;
            switch(((Type)entry.getValue()).getBytecodeStackType()) {
            case 'A':
               type = "object";
               break;
            case 'B':
            case 'C':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            default:
               type = String.valueOf(bct);
               break;
            case 'D':
               type = "double";
               break;
            case 'I':
               type = "int";
               break;
            case 'J':
               type = "long";
            }

            StringBuilder sb = new StringBuilder();
            sb.append('[').append("program point: ").append(entry.getKey()).append(" -> ").append(type).append(']');
            list.add(sb.toString());
         }

         return list;
      }
   }

   private void logRecompile(String reason, FunctionNode fn, MethodType type, Map<Integer, Type> ipp) {
      if (this.log.isEnabled()) {
         this.log.info(reason, DebugLogger.quote(fn.getName()), " signature: ", type);
         this.log.indent();
         Iterator var5 = toStringInvalidations(ipp).iterator();

         while(var5.hasNext()) {
            String str = (String)var5.next();
            this.log.fine(str);
         }

         this.log.unindent();
      }

   }

   private synchronized MethodHandle handleRewriteException(CompiledFunction.OptimismInfo oldOptInfo, RewriteException re) {
      if (this.log.isEnabled()) {
         this.log.info(new RecompilationEvent(Level.INFO, re, re.getReturnValueNonDestructive()), (Object[])("caught RewriteException ", re.getMessageShort()));
         this.log.indent();
      }

      MethodType type = this.type();
      MethodType ct = type.parameterType(0) == ScriptFunction.class ? type : type.insertParameterTypes(0, new Class[]{ScriptFunction.class});
      CompiledFunction.OptimismInfo currentOptInfo = this.optimismInfo;
      boolean shouldRecompile = currentOptInfo != null && currentOptInfo.requestRecompile(re);
      CompiledFunction.OptimismInfo effectiveOptInfo = currentOptInfo != null ? currentOptInfo : oldOptInfo;
      FunctionNode fn = effectiveOptInfo.reparse();
      boolean cached = fn.isCached();
      Compiler compiler = effectiveOptInfo.getCompiler(fn, ct, re);
      if (!shouldRecompile) {
         this.logRecompile("Rest-of compilation [STANDALONE] ", fn, ct, effectiveOptInfo.invalidatedProgramPoints);
         return this.restOfHandle(effectiveOptInfo, compiler.compile(fn, cached ? Compiler.CompilationPhases.COMPILE_CACHED_RESTOF : Compiler.CompilationPhases.COMPILE_ALL_RESTOF), currentOptInfo != null);
      } else {
         this.logRecompile("Deoptimizing recompilation (up to bytecode) ", fn, ct, effectiveOptInfo.invalidatedProgramPoints);
         fn = compiler.compile(fn, cached ? Compiler.CompilationPhases.RECOMPILE_CACHED_UPTO_BYTECODE : Compiler.CompilationPhases.COMPILE_UPTO_BYTECODE);
         this.log.fine("Reusable IR generated");
         this.log.info("Generating and installing bytecode from reusable IR...");
         this.logRecompile("Rest-of compilation [CODE PIPELINE REUSE] ", fn, ct, effectiveOptInfo.invalidatedProgramPoints);
         FunctionNode normalFn = compiler.compile(fn, Compiler.CompilationPhases.GENERATE_BYTECODE_AND_INSTALL);
         if (effectiveOptInfo.data.usePersistentCodeCache()) {
            RecompilableScriptFunctionData data = effectiveOptInfo.data;
            int functionNodeId = data.getFunctionNodeId();
            TypeMap typeMap = data.typeMap(ct);
            Type[] paramTypes = typeMap == null ? null : typeMap.getParameterTypes(functionNodeId);
            String cacheKey = CodeStore.getCacheKey(functionNodeId, paramTypes);
            compiler.persistClassInfo(cacheKey, normalFn);
         }

         boolean canBeDeoptimized = normalFn.canBeDeoptimized();
         if (this.log.isEnabled()) {
            this.log.unindent();
            this.log.info("Done.");
            this.log.info("Recompiled '", fn.getName(), "' (", Debug.id(this), ") ", canBeDeoptimized ? "can still be deoptimized." : " is completely deoptimized.");
            this.log.finest("Looking up invoker...");
         }

         MethodHandle newInvoker = effectiveOptInfo.data.lookup(fn);
         this.invoker = newInvoker.asType(type.changeReturnType(newInvoker.type().returnType()));
         this.constructor = null;
         this.log.info("Done: ", this.invoker);
         MethodHandle restOf = this.restOfHandle(effectiveOptInfo, compiler.compile(fn, Compiler.CompilationPhases.GENERATE_BYTECODE_AND_INSTALL_RESTOF), canBeDeoptimized);
         if (canBeDeoptimized) {
            effectiveOptInfo.newOptimisticAssumptions();
         } else {
            this.optimismInfo = null;
         }

         this.notifyAll();
         return restOf;
      }
   }

   private MethodHandle restOfHandle(CompiledFunction.OptimismInfo info, FunctionNode restOfFunction, boolean canBeDeoptimized) {
      assert info != null;

      assert restOfFunction.getCompileUnit().getUnitClassName().contains("restOf");

      MethodHandle restOf = changeReturnType(info.data.lookupCodeMethod(restOfFunction.getCompileUnit().getCode(), Lookup.MH.type(restOfFunction.getReturnType().getTypeClass(), RewriteException.class)), Object.class);
      return !canBeDeoptimized ? restOf : Lookup.MH.catchException(restOf, RewriteException.class, this.createRewriteExceptionHandler());
   }

   private static Object newFilter(Object result, Object allocation) {
      return !(result instanceof ScriptObject) && JSType.isPrimitive(result) ? allocation : result;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), CompiledFunction.class, name, Lookup.MH.type(rtype, types));
   }

   static {
      RELINK_COMPOSABLE_INVOKER = findOwnMH("relinkComposableInvoker", Void.TYPE, CallSite.class, CompiledFunction.class, Boolean.TYPE);
      HANDLE_REWRITE_EXCEPTION = findOwnMH("handleRewriteException", MethodHandle.class, CompiledFunction.class, CompiledFunction.OptimismInfo.class, RewriteException.class);
      RESTOF_INVOKER = MethodHandles.exactInvoker(MethodType.methodType(Object.class, RewriteException.class));
      NO_FUNCTIONS = Collections.emptySet();
   }

   private static class OptimismInfo {
      private final RecompilableScriptFunctionData data;
      private final Map<Integer, Type> invalidatedProgramPoints;
      private SwitchPoint optimisticAssumptions;
      private final DebugLogger log;

      OptimismInfo(RecompilableScriptFunctionData data, Map<Integer, Type> invalidatedProgramPoints) {
         this.data = data;
         this.log = data.getLogger();
         this.invalidatedProgramPoints = (Map)(invalidatedProgramPoints == null ? new TreeMap() : invalidatedProgramPoints);
         this.newOptimisticAssumptions();
      }

      private void newOptimisticAssumptions() {
         this.optimisticAssumptions = new SwitchPoint();
      }

      boolean requestRecompile(RewriteException e) {
         Type retType = e.getReturnType();
         Type previousFailedType = (Type)this.invalidatedProgramPoints.put(e.getProgramPoint(), retType);
         if (previousFailedType != null && !previousFailedType.narrowerThan(retType)) {
            StackTraceElement[] stack = e.getStackTrace();
            String functionId = stack.length == 0 ? this.data.getName() : stack[0].getClassName() + "." + stack[0].getMethodName();
            this.log.info("RewriteException for an already invalidated program point ", e.getProgramPoint(), " in ", functionId, ". This is okay for a recursive function invocation, but a bug otherwise.");
            return false;
         } else {
            SwitchPoint.invalidateAll(new SwitchPoint[]{this.optimisticAssumptions});
            return true;
         }
      }

      Compiler getCompiler(FunctionNode fn, MethodType actualCallSiteType, RewriteException e) {
         return this.data.getCompiler(fn, actualCallSiteType, e.getRuntimeScope(), this.invalidatedProgramPoints, getEntryPoints(e));
      }

      private static int[] getEntryPoints(RewriteException e) {
         int[] prevEntryPoints = e.getPreviousContinuationEntryPoints();
         int[] entryPoints;
         if (prevEntryPoints == null) {
            entryPoints = new int[1];
         } else {
            int l = prevEntryPoints.length;
            entryPoints = new int[l + 1];
            System.arraycopy(prevEntryPoints, 0, entryPoints, 1, l);
         }

         entryPoints[0] = e.getProgramPoint();
         return entryPoints;
      }

      FunctionNode reparse() {
         return this.data.reparse();
      }
   }

   private static class HandleAndAssumptions {
      final MethodHandle handle;
      final SwitchPoint assumptions;

      HandleAndAssumptions(MethodHandle handle, SwitchPoint assumptions) {
         this.handle = handle;
         this.assumptions = assumptions;
      }

      GuardedInvocation createInvocation() {
         return new GuardedInvocation(this.handle, this.assumptions);
      }
   }
}
