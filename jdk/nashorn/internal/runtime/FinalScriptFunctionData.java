package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

final class FinalScriptFunctionData extends ScriptFunctionData {
   private static final long serialVersionUID = -930632846167768864L;

   FinalScriptFunctionData(String name, int arity, List<CompiledFunction> functions, int flags) {
      super(name, arity, flags);
      this.code.addAll(functions);

      assert !this.needsCallee();

   }

   FinalScriptFunctionData(String name, MethodHandle mh, Specialization[] specs, int flags) {
      super(name, methodHandleArity(mh), flags);
      this.addInvoker(mh);
      if (specs != null) {
         Specialization[] var5 = specs;
         int var6 = specs.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Specialization spec = var5[var7];
            this.addInvoker(spec.getMethodHandle(), spec);
         }
      }

   }

   protected boolean needsCallee() {
      boolean needsCallee = ((CompiledFunction)this.code.getFirst()).needsCallee();

      assert this.allNeedCallee(needsCallee);

      return needsCallee;
   }

   private boolean allNeedCallee(boolean needCallee) {
      Iterator var2 = this.code.iterator();

      CompiledFunction inv;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         inv = (CompiledFunction)var2.next();
      } while(inv.needsCallee() == needCallee);

      return false;
   }

   CompiledFunction getBest(MethodType callSiteType, ScriptObject runtimeScope, Collection<CompiledFunction> forbidden, boolean linkLogicOkay) {
      assert this.isValidCallSite(callSiteType) : callSiteType;

      CompiledFunction best = null;
      Iterator var6 = this.code.iterator();

      while(true) {
         CompiledFunction candidate;
         do {
            if (!var6.hasNext()) {
               return best;
            }

            candidate = (CompiledFunction)var6.next();
         } while(!linkLogicOkay && candidate.hasLinkLogic());

         if (!forbidden.contains(candidate) && candidate.betterThanFinal(best, callSiteType)) {
            best = candidate;
         }
      }
   }

   MethodType getGenericType() {
      int max = 0;
      Iterator var2 = this.code.iterator();

      while(var2.hasNext()) {
         CompiledFunction fn = (CompiledFunction)var2.next();
         MethodType t = fn.type();
         if (ScriptFunctionData.isVarArg(t)) {
            return MethodType.genericMethodType(2, true);
         }

         int paramCount = t.parameterCount() - (ScriptFunctionData.needsCallee(t) ? 1 : 0);
         if (paramCount > max) {
            max = paramCount;
         }
      }

      return MethodType.genericMethodType(max + 1);
   }

   private CompiledFunction addInvoker(MethodHandle mh, Specialization specialization) {
      assert !needsCallee(mh);

      CompiledFunction invoker;
      if (isConstructor(mh)) {
         assert this.isConstructor();

         invoker = CompiledFunction.createBuiltInConstructor(mh);
      } else {
         invoker = new CompiledFunction(mh, (MethodHandle)null, specialization);
      }

      this.code.add(invoker);
      return invoker;
   }

   private CompiledFunction addInvoker(MethodHandle mh) {
      return this.addInvoker(mh, (Specialization)null);
   }

   private static int methodHandleArity(MethodHandle mh) {
      return isVarArg(mh) ? 250 : mh.type().parameterCount() - 1 - (needsCallee(mh) ? 1 : 0) - (isConstructor(mh) ? 1 : 0);
   }

   private static boolean isConstructor(MethodHandle mh) {
      return mh.type().parameterCount() >= 1 && mh.type().parameterType(0) == Boolean.TYPE;
   }
}
