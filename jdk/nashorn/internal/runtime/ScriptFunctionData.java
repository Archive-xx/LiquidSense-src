package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.nashorn.internal.lookup.Lookup;

public abstract class ScriptFunctionData implements Serializable {
   static final int MAX_ARITY = 250;
   protected final String name;
   protected transient LinkedList<CompiledFunction> code = new LinkedList();
   protected int flags;
   private int arity;
   private transient volatile ScriptFunctionData.GenericInvokers genericInvokers;
   private static final MethodHandle BIND_VAR_ARGS = findOwnMH("bindVarArgs", Object[].class, Object[].class, Object[].class);
   public static final int IS_STRICT = 1;
   public static final int IS_BUILTIN = 2;
   public static final int IS_CONSTRUCTOR = 4;
   public static final int NEEDS_CALLEE = 8;
   public static final int USES_THIS = 16;
   public static final int IS_VARIABLE_ARITY = 32;
   public static final int IS_PROPERTY_ACCESSOR = 64;
   public static final int IS_STRICT_OR_BUILTIN = 3;
   public static final int IS_BUILTIN_CONSTRUCTOR = 6;
   private static final long serialVersionUID = 4252901245508769114L;

   ScriptFunctionData(String name, int arity, int flags) {
      this.name = name;
      this.flags = flags;
      this.setArity(arity);
   }

   final int getArity() {
      return this.arity;
   }

   final boolean isVariableArity() {
      return (this.flags & 32) != 0;
   }

   final boolean isPropertyAccessor() {
      return (this.flags & 64) != 0;
   }

   void setArity(int arity) {
      if (arity >= 0 && arity <= 250) {
         this.arity = arity;
      } else {
         throw new IllegalArgumentException(String.valueOf(arity));
      }
   }

   CompiledFunction bind(CompiledFunction originalInv, ScriptFunction fn, Object self, Object[] args) {
      MethodHandle boundInvoker = this.bindInvokeHandle(originalInv.createComposableInvoker(), fn, self, args);
      return this.isConstructor() ? new CompiledFunction(boundInvoker, bindConstructHandle(originalInv.createComposableConstructor(), fn, args), (Specialization)null) : new CompiledFunction(boundInvoker);
   }

   public final boolean isStrict() {
      return (this.flags & 1) != 0;
   }

   protected String getFunctionName() {
      return this.getName();
   }

   final boolean isBuiltin() {
      return (this.flags & 2) != 0;
   }

   final boolean isConstructor() {
      return (this.flags & 4) != 0;
   }

   abstract boolean needsCallee();

   final boolean needsWrappedThis() {
      return (this.flags & 16) != 0 && (this.flags & 3) == 0;
   }

   String toSource() {
      return "function " + (this.name == null ? "" : this.name) + "() { [native code] }";
   }

   String getName() {
      return this.name;
   }

   public String toString() {
      return this.name.isEmpty() ? "<anonymous>" : this.name;
   }

   public String toStringVerbose() {
      StringBuilder sb = new StringBuilder();
      sb.append("name='").append(this.name.isEmpty() ? "<anonymous>" : this.name).append("' ").append(this.code.size()).append(" invokers=").append(this.code);
      return sb.toString();
   }

   final CompiledFunction getBestInvoker(MethodType callSiteType, ScriptObject runtimeScope) {
      return this.getBestInvoker(callSiteType, runtimeScope, CompiledFunction.NO_FUNCTIONS);
   }

   final CompiledFunction getBestInvoker(MethodType callSiteType, ScriptObject runtimeScope, Collection<CompiledFunction> forbidden) {
      CompiledFunction cf = this.getBest(callSiteType, runtimeScope, forbidden);

      assert cf != null;

      return cf;
   }

   final CompiledFunction getBestConstructor(MethodType callSiteType, ScriptObject runtimeScope, Collection<CompiledFunction> forbidden) {
      if (!this.isConstructor()) {
         throw ECMAErrors.typeError("not.a.constructor", this.toSource());
      } else {
         CompiledFunction cf = this.getBest(callSiteType.insertParameterTypes(1, new Class[]{Object.class}), runtimeScope, forbidden);
         return cf;
      }
   }

   protected void ensureCompiled() {
   }

   final MethodHandle getGenericInvoker(ScriptObject runtimeScope) {
      ScriptFunctionData.GenericInvokers lgenericInvokers = this.ensureGenericInvokers();
      MethodHandle invoker = lgenericInvokers.invoker;
      if (invoker == null) {
         lgenericInvokers.invoker = invoker = this.createGenericInvoker(runtimeScope);
      }

      return invoker;
   }

   private MethodHandle createGenericInvoker(ScriptObject runtimeScope) {
      return makeGenericMethod(this.getGeneric(runtimeScope).createComposableInvoker());
   }

   final MethodHandle getGenericConstructor(ScriptObject runtimeScope) {
      ScriptFunctionData.GenericInvokers lgenericInvokers = this.ensureGenericInvokers();
      MethodHandle constructor = lgenericInvokers.constructor;
      if (constructor == null) {
         lgenericInvokers.constructor = constructor = this.createGenericConstructor(runtimeScope);
      }

      return constructor;
   }

   private MethodHandle createGenericConstructor(ScriptObject runtimeScope) {
      return makeGenericMethod(this.getGeneric(runtimeScope).createComposableConstructor());
   }

   private ScriptFunctionData.GenericInvokers ensureGenericInvokers() {
      ScriptFunctionData.GenericInvokers lgenericInvokers = this.genericInvokers;
      if (lgenericInvokers == null) {
         this.genericInvokers = lgenericInvokers = new ScriptFunctionData.GenericInvokers();
      }

      return lgenericInvokers;
   }

   private static MethodType widen(MethodType cftype) {
      Class<?>[] paramTypes = new Class[cftype.parameterCount()];

      for(int i = 0; i < cftype.parameterCount(); ++i) {
         paramTypes[i] = cftype.parameterType(i).isPrimitive() ? cftype.parameterType(i) : Object.class;
      }

      return Lookup.MH.type(cftype.returnType(), paramTypes);
   }

   CompiledFunction lookupExactApplyToCall(MethodType type) {
      Iterator var2 = this.code.iterator();

      while(var2.hasNext()) {
         CompiledFunction cf = (CompiledFunction)var2.next();
         if (cf.isApplyToCall()) {
            MethodType cftype = cf.type();
            if (cftype.parameterCount() == type.parameterCount() && widen(cftype).equals(widen(type))) {
               return cf;
            }
         }
      }

      return null;
   }

   CompiledFunction pickFunction(MethodType callSiteType, boolean canPickVarArg) {
      Iterator var3 = this.code.iterator();

      CompiledFunction candidate;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         candidate = (CompiledFunction)var3.next();
      } while(!candidate.matchesCallSite(callSiteType, canPickVarArg));

      return candidate;
   }

   abstract CompiledFunction getBest(MethodType var1, ScriptObject var2, Collection<CompiledFunction> var3, boolean var4);

   final CompiledFunction getBest(MethodType callSiteType, ScriptObject runtimeScope, Collection<CompiledFunction> forbidden) {
      return this.getBest(callSiteType, runtimeScope, forbidden, true);
   }

   boolean isValidCallSite(MethodType callSiteType) {
      return callSiteType.parameterCount() >= 2 && callSiteType.parameterType(0).isAssignableFrom(ScriptFunction.class);
   }

   CompiledFunction getGeneric(ScriptObject runtimeScope) {
      return this.getBest(this.getGenericType(), runtimeScope, CompiledFunction.NO_FUNCTIONS, false);
   }

   abstract MethodType getGenericType();

   ScriptObject allocate(PropertyMap map) {
      return null;
   }

   PropertyMap getAllocatorMap(ScriptObject prototype) {
      return null;
   }

   ScriptFunctionData makeBoundFunctionData(ScriptFunction fn, Object self, Object[] args) {
      Object[] allArgs = args == null ? ScriptRuntime.EMPTY_ARRAY : args;
      int length = args == null ? 0 : args.length;
      int boundFlags = this.flags & -9 & -17;
      List<CompiledFunction> boundList = new LinkedList();
      ScriptObject runtimeScope = fn.getScope();
      CompiledFunction bindTarget = new CompiledFunction(this.getGenericInvoker(runtimeScope), this.getGenericConstructor(runtimeScope), (Specialization)null);
      boundList.add(this.bind(bindTarget, fn, self, allArgs));
      return new FinalScriptFunctionData(this.name, Math.max(0, this.getArity() - length), boundList, boundFlags);
   }

   private Object convertThisObject(Object thiz) {
      return this.needsWrappedThis() ? wrapThis(thiz) : thiz;
   }

   static Object wrapThis(Object thiz) {
      if (!(thiz instanceof ScriptObject)) {
         if (JSType.nullOrUndefined(thiz)) {
            return Context.getGlobal();
         }

         if (isPrimitiveThis(thiz)) {
            return Context.getGlobal().wrapAsObject(thiz);
         }
      }

      return thiz;
   }

   static boolean isPrimitiveThis(Object obj) {
      return JSType.isString(obj) || obj instanceof Number || obj instanceof Boolean;
   }

   private MethodHandle bindInvokeHandle(MethodHandle originalInvoker, ScriptFunction targetFn, Object self, Object[] args) {
      boolean isTargetBound = targetFn.isBoundFunction();
      boolean needsCallee = needsCallee(originalInvoker);

      assert needsCallee == this.needsCallee() : "callee contract violation 2";

      assert !isTargetBound || !needsCallee;

      Object boundSelf = isTargetBound ? null : this.convertThisObject(self);
      MethodHandle boundInvoker;
      if (isVarArg(originalInvoker)) {
         MethodHandle noArgBoundInvoker;
         if (isTargetBound) {
            noArgBoundInvoker = originalInvoker;
         } else if (needsCallee) {
            noArgBoundInvoker = Lookup.MH.insertArguments(originalInvoker, 0, targetFn, boundSelf);
         } else {
            noArgBoundInvoker = Lookup.MH.bindTo(originalInvoker, boundSelf);
         }

         if (args.length > 0) {
            boundInvoker = varArgBinder(noArgBoundInvoker, args);
         } else {
            boundInvoker = noArgBoundInvoker;
         }
      } else {
         int argInsertPos = isTargetBound ? 1 : 0;
         Object[] boundArgs = new Object[Math.min(originalInvoker.type().parameterCount() - argInsertPos, args.length + (isTargetBound ? 0 : (needsCallee ? 2 : 1)))];
         int next = 0;
         if (!isTargetBound) {
            if (needsCallee) {
               boundArgs[next++] = targetFn;
            }

            boundArgs[next++] = boundSelf;
         }

         System.arraycopy(args, 0, boundArgs, next, boundArgs.length - next);
         boundInvoker = Lookup.MH.insertArguments(originalInvoker, argInsertPos, boundArgs);
      }

      return isTargetBound ? boundInvoker : Lookup.MH.dropArguments(boundInvoker, 0, (Class[])(Object.class));
   }

   private static MethodHandle bindConstructHandle(MethodHandle originalConstructor, ScriptFunction fn, Object[] args) {
      assert originalConstructor != null;

      MethodHandle calleeBoundConstructor = fn.isBoundFunction() ? originalConstructor : Lookup.MH.dropArguments(Lookup.MH.bindTo(originalConstructor, fn), 0, (Class[])(ScriptFunction.class));
      if (args.length == 0) {
         return calleeBoundConstructor;
      } else if (isVarArg(calleeBoundConstructor)) {
         return varArgBinder(calleeBoundConstructor, args);
      } else {
         int maxArgCount = calleeBoundConstructor.type().parameterCount() - 1;
         Object[] boundArgs;
         if (args.length <= maxArgCount) {
            boundArgs = args;
         } else {
            boundArgs = new Object[maxArgCount];
            System.arraycopy(args, 0, boundArgs, 0, maxArgCount);
         }

         return Lookup.MH.insertArguments(calleeBoundConstructor, 1, boundArgs);
      }
   }

   private static MethodHandle makeGenericMethod(MethodHandle mh) {
      MethodType type = mh.type();
      MethodType newType = makeGenericType(type);
      return type.equals(newType) ? mh : mh.asType(newType);
   }

   private static MethodType makeGenericType(MethodType type) {
      MethodType newType = type.generic();
      if (isVarArg(type)) {
         newType = newType.changeParameterType(type.parameterCount() - 1, Object[].class);
      }

      if (needsCallee(type)) {
         newType = newType.changeParameterType(0, ScriptFunction.class);
      }

      return newType;
   }

   Object invoke(ScriptFunction fn, Object self, Object... arguments) throws Throwable {
      MethodHandle mh = this.getGenericInvoker(fn.getScope());
      Object selfObj = this.convertThisObject(self);
      Object[] args = arguments == null ? ScriptRuntime.EMPTY_ARRAY : arguments;
      DebuggerSupport.notifyInvoke(mh);
      if (isVarArg(mh)) {
         return needsCallee(mh) ? mh.invokeExact(fn, selfObj, args) : mh.invokeExact(selfObj, args);
      } else {
         int paramCount = mh.type().parameterCount();
         if (needsCallee(mh)) {
            switch(paramCount) {
            case 2:
               return mh.invokeExact(fn, selfObj);
            case 3:
               return mh.invokeExact(fn, selfObj, getArg(args, 0));
            case 4:
               return mh.invokeExact(fn, selfObj, getArg(args, 0), getArg(args, 1));
            case 5:
               return mh.invokeExact(fn, selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2));
            case 6:
               return mh.invokeExact(fn, selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3));
            case 7:
               return mh.invokeExact(fn, selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4));
            case 8:
               return mh.invokeExact(fn, selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4), getArg(args, 5));
            default:
               return mh.invokeWithArguments(withArguments(fn, selfObj, paramCount, args));
            }
         } else {
            switch(paramCount) {
            case 1:
               return mh.invokeExact(selfObj);
            case 2:
               return mh.invokeExact(selfObj, getArg(args, 0));
            case 3:
               return mh.invokeExact(selfObj, getArg(args, 0), getArg(args, 1));
            case 4:
               return mh.invokeExact(selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2));
            case 5:
               return mh.invokeExact(selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3));
            case 6:
               return mh.invokeExact(selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4));
            case 7:
               return mh.invokeExact(selfObj, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4), getArg(args, 5));
            default:
               return mh.invokeWithArguments(withArguments((ScriptFunction)null, selfObj, paramCount, args));
            }
         }
      }
   }

   Object construct(ScriptFunction fn, Object... arguments) throws Throwable {
      MethodHandle mh = this.getGenericConstructor(fn.getScope());
      Object[] args = arguments == null ? ScriptRuntime.EMPTY_ARRAY : arguments;
      DebuggerSupport.notifyInvoke(mh);
      if (isVarArg(mh)) {
         return needsCallee(mh) ? mh.invokeExact(fn, args) : mh.invokeExact(args);
      } else {
         int paramCount = mh.type().parameterCount();
         if (needsCallee(mh)) {
            switch(paramCount) {
            case 1:
               return mh.invokeExact(fn);
            case 2:
               return mh.invokeExact(fn, getArg(args, 0));
            case 3:
               return mh.invokeExact(fn, getArg(args, 0), getArg(args, 1));
            case 4:
               return mh.invokeExact(fn, getArg(args, 0), getArg(args, 1), getArg(args, 2));
            case 5:
               return mh.invokeExact(fn, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3));
            case 6:
               return mh.invokeExact(fn, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4));
            case 7:
               return mh.invokeExact(fn, getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4), getArg(args, 5));
            default:
               return mh.invokeWithArguments(withArguments(fn, paramCount, args));
            }
         } else {
            switch(paramCount) {
            case 0:
               return mh.invokeExact();
            case 1:
               return mh.invokeExact(getArg(args, 0));
            case 2:
               return mh.invokeExact(getArg(args, 0), getArg(args, 1));
            case 3:
               return mh.invokeExact(getArg(args, 0), getArg(args, 1), getArg(args, 2));
            case 4:
               return mh.invokeExact(getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3));
            case 5:
               return mh.invokeExact(getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4));
            case 6:
               return mh.invokeExact(getArg(args, 0), getArg(args, 1), getArg(args, 2), getArg(args, 3), getArg(args, 4), getArg(args, 5));
            default:
               return mh.invokeWithArguments(withArguments((ScriptFunction)null, paramCount, args));
            }
         }
      }
   }

   private static Object getArg(Object[] args, int i) {
      return i < args.length ? args[i] : ScriptRuntime.UNDEFINED;
   }

   private static Object[] withArguments(ScriptFunction fn, int argCount, Object[] args) {
      Object[] finalArgs = new Object[argCount];
      int nextArg = 0;
      if (fn != null) {
         finalArgs[nextArg++] = fn;
      }

      for(int i = 0; i < args.length && nextArg < argCount; finalArgs[nextArg++] = args[i++]) {
      }

      while(nextArg < argCount) {
         finalArgs[nextArg++] = ScriptRuntime.UNDEFINED;
      }

      return finalArgs;
   }

   private static Object[] withArguments(ScriptFunction fn, Object self, int argCount, Object[] args) {
      Object[] finalArgs = new Object[argCount];
      int nextArg = 0;
      if (fn != null) {
         finalArgs[nextArg++] = fn;
      }

      finalArgs[nextArg++] = self;

      for(int i = 0; i < args.length && nextArg < argCount; finalArgs[nextArg++] = args[i++]) {
      }

      while(nextArg < argCount) {
         finalArgs[nextArg++] = ScriptRuntime.UNDEFINED;
      }

      return finalArgs;
   }

   private static MethodHandle varArgBinder(MethodHandle mh, Object[] args) {
      assert args != null;

      assert args.length > 0;

      return Lookup.MH.filterArguments(mh, mh.type().parameterCount() - 1, Lookup.MH.bindTo(BIND_VAR_ARGS, args));
   }

   protected static boolean needsCallee(MethodHandle mh) {
      return needsCallee(mh.type());
   }

   static boolean needsCallee(MethodType type) {
      int length = type.parameterCount();
      if (length == 0) {
         return false;
      } else {
         Class<?> param0 = type.parameterType(0);
         return param0 == ScriptFunction.class || param0 == Boolean.TYPE && length > 1 && type.parameterType(1) == ScriptFunction.class;
      }
   }

   protected static boolean isVarArg(MethodHandle mh) {
      return isVarArg(mh.type());
   }

   static boolean isVarArg(MethodType type) {
      return type.parameterType(type.parameterCount() - 1).isArray();
   }

   public boolean inDynamicContext() {
      return false;
   }

   private static Object[] bindVarArgs(Object[] array1, Object[] array2) {
      if (array2 == null) {
         return (Object[])array1.clone();
      } else {
         int l2 = array2.length;
         if (l2 == 0) {
            return (Object[])array1.clone();
         } else {
            int l1 = array1.length;
            Object[] concat = new Object[l1 + l2];
            System.arraycopy(array1, 0, concat, 0, l1);
            System.arraycopy(array2, 0, concat, l1, l2);
            return concat;
         }
      }
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), ScriptFunctionData.class, name, Lookup.MH.type(rtype, types));
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.code = new LinkedList();
   }

   private static final class GenericInvokers {
      volatile MethodHandle invoker;
      volatile MethodHandle constructor;

      private GenericInvokers() {
      }

      // $FF: synthetic method
      GenericInvokers(Object x0) {
         this();
      }
   }
}
