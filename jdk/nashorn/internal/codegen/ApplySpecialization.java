package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(
   name = "apply2call"
)
public final class ApplySpecialization extends SimpleNodeVisitor implements Loggable {
   private static final boolean USE_APPLY2CALL = Options.getBooleanProperty("nashorn.apply2call", true);
   private final DebugLogger log;
   private final Compiler compiler;
   private final Set<Integer> changed = new HashSet();
   private final Deque<List<IdentNode>> explodedArguments = new ArrayDeque();
   private final Deque<MethodType> callSiteTypes = new ArrayDeque();
   private static final String ARGUMENTS;
   private static final ApplySpecialization.AppliesFoundException HAS_APPLIES;

   public ApplySpecialization(Compiler compiler) {
      this.compiler = compiler;
      this.log = this.initLogger(compiler.getContext());
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   private boolean hasApplies(final FunctionNode functionNode) {
      try {
         functionNode.accept(new SimpleNodeVisitor() {
            public boolean enterFunctionNode(FunctionNode fn) {
               return fn == functionNode;
            }

            public boolean enterCallNode(CallNode callNode) {
               if (ApplySpecialization.isApply(callNode)) {
                  throw ApplySpecialization.HAS_APPLIES;
               } else {
                  return true;
               }
            }
         });
      } catch (ApplySpecialization.AppliesFoundException var3) {
         return true;
      }

      this.log.fine("There are no applies in ", DebugLogger.quote(functionNode.getName()), " - nothing to do.");
      return false;
   }

   private static void checkValidTransform(final FunctionNode functionNode) {
      final Set<Expression> argumentsFound = new HashSet();
      final Deque<Set<Expression>> stack = new ArrayDeque();
      functionNode.accept(new SimpleNodeVisitor() {
         private boolean isCurrentArg(Expression expr) {
            return !stack.isEmpty() && ((Set)stack.peek()).contains(expr);
         }

         private boolean isArguments(Expression expr) {
            if (expr instanceof IdentNode && ApplySpecialization.ARGUMENTS.equals(((IdentNode)expr).getName())) {
               argumentsFound.add(expr);
               return true;
            } else {
               return false;
            }
         }

         private boolean isParam(String name) {
            Iterator var2 = functionNode.getParameters().iterator();

            IdentNode param;
            do {
               if (!var2.hasNext()) {
                  return false;
               }

               param = (IdentNode)var2.next();
            } while(!param.getName().equals(name));

            return true;
         }

         public Node leaveIdentNode(IdentNode identNode) {
            if (this.isParam(identNode.getName())) {
               throw new ApplySpecialization.TransformFailedException(this.lc.getCurrentFunction(), "parameter: " + identNode.getName());
            } else if (this.isArguments(identNode) && !this.isCurrentArg(identNode)) {
               throw new ApplySpecialization.TransformFailedException(this.lc.getCurrentFunction(), "is 'arguments': " + identNode.getName());
            } else {
               return identNode;
            }
         }

         public boolean enterCallNode(CallNode callNode) {
            Set<Expression> callArgs = new HashSet();
            if (ApplySpecialization.isApply(callNode)) {
               List<Expression> argList = callNode.getArgs();
               if (argList.size() != 2 || !this.isArguments((Expression)argList.get(argList.size() - 1))) {
                  throw new ApplySpecialization.TransformFailedException(this.lc.getCurrentFunction(), "argument pattern not matched: " + argList);
               }

               callArgs.addAll(callNode.getArgs());
            }

            stack.push(callArgs);
            return true;
         }

         public Node leaveCallNode(CallNode callNode) {
            stack.pop();
            return callNode;
         }
      });
   }

   public boolean enterCallNode(CallNode callNode) {
      return !this.explodedArguments.isEmpty();
   }

   public Node leaveCallNode(CallNode callNode) {
      List<IdentNode> newParams = (List)this.explodedArguments.peek();
      if (!isApply(callNode)) {
         return callNode;
      } else {
         List<Expression> newArgs = new ArrayList();
         Iterator var4 = callNode.getArgs().iterator();

         while(true) {
            while(var4.hasNext()) {
               Expression arg = (Expression)var4.next();
               if (arg instanceof IdentNode && ARGUMENTS.equals(((IdentNode)arg).getName())) {
                  newArgs.addAll(newParams);
               } else {
                  newArgs.add(arg);
               }
            }

            this.changed.add(this.lc.getCurrentFunction().getId());
            CallNode newCallNode = callNode.setArgs(newArgs).setIsApplyToCall();
            if (this.log.isEnabled()) {
               this.log.fine("Transformed ", callNode, " from apply to call => ", newCallNode, " in ", DebugLogger.quote(this.lc.getCurrentFunction().getName()));
            }

            return newCallNode;
         }
      }
   }

   private void pushExplodedArgs(FunctionNode functionNode) {
      int start = 0;
      MethodType actualCallSiteType = this.compiler.getCallSiteType(functionNode);
      if (actualCallSiteType == null) {
         throw new ApplySpecialization.TransformFailedException(this.lc.getCurrentFunction(), "No callsite type");
      } else {
         assert actualCallSiteType.parameterType(actualCallSiteType.parameterCount() - 1) != Object[].class : "error vararg callsite passed to apply2call " + functionNode.getName() + " " + actualCallSiteType;

         TypeMap ptm = this.compiler.getTypeMap();
         if (ptm.needsCallee()) {
            ++start;
         }

         ++start;

         assert functionNode.getNumOfParams() == 0 : "apply2call on function with named paramaters!";

         List<IdentNode> newParams = new ArrayList();
         long to = (long)(actualCallSiteType.parameterCount() - start);

         for(int i = 0; (long)i < to; ++i) {
            newParams.add(new IdentNode(functionNode.getToken(), functionNode.getFinish(), CompilerConstants.EXPLODED_ARGUMENT_PREFIX.symbolName() + i));
         }

         this.callSiteTypes.push(actualCallSiteType);
         this.explodedArguments.push(newParams);
      }
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      if (USE_APPLY2CALL && this.compiler.isOnDemandCompilation() && functionNode.needsArguments() && !functionNode.hasEval() && functionNode.getNumOfParams() == 0) {
         if (!Global.isBuiltinFunctionPrototypeApply()) {
            this.log.fine("Apply transform disabled: apply/call overridden");

            assert !Global.isBuiltinFunctionPrototypeCall() : "call and apply should have the same SwitchPoint";

            return false;
         } else if (!this.hasApplies(functionNode)) {
            return false;
         } else {
            if (this.log.isEnabled()) {
               this.log.info("Trying to specialize apply to call in '", functionNode.getName(), "' params=", functionNode.getParameters(), " id=", functionNode.getId(), " source=", massageURL(functionNode.getSource().getURL()));
            }

            try {
               checkValidTransform(functionNode);
               this.pushExplodedArgs(functionNode);
               return true;
            } catch (ApplySpecialization.TransformFailedException var3) {
               this.log.info("Failure: ", var3.getMessage());
               return false;
            }
         }
      } else {
         return false;
      }
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      FunctionNode newFunctionNode = functionNode;
      String functionName = functionNode.getName();
      if (this.changed.contains(functionNode.getId())) {
         newFunctionNode = functionNode.clearFlag(this.lc, 8).setFlag(this.lc, 4096).setParameters(this.lc, (List)this.explodedArguments.peek());
         if (this.log.isEnabled()) {
            this.log.info("Success: ", massageURL(newFunctionNode.getSource().getURL()), '.', functionName, "' id=", newFunctionNode.getId(), " params=", this.callSiteTypes.peek());
         }
      }

      this.callSiteTypes.pop();
      this.explodedArguments.pop();
      return newFunctionNode;
   }

   private static boolean isApply(CallNode callNode) {
      Expression f = callNode.getFunction();
      return f instanceof AccessNode && "apply".equals(((AccessNode)f).getProperty());
   }

   private static String massageURL(URL url) {
      if (url == null) {
         return "<null>";
      } else {
         String str = url.toString();
         int slash = str.lastIndexOf(47);
         return slash == -1 ? str : str.substring(slash + 1);
      }
   }

   static {
      ARGUMENTS = CompilerConstants.ARGUMENTS_VAR.symbolName();
      HAS_APPLIES = new ApplySpecialization.AppliesFoundException();
   }

   private static class AppliesFoundException extends RuntimeException {
      AppliesFoundException() {
         super("applies_found", (Throwable)null, false, false);
      }
   }

   private static class TransformFailedException extends RuntimeException {
      TransformFailedException(FunctionNode fn, String message) {
         super(ApplySpecialization.massageURL(fn.getSource().getURL()) + '.' + fn.getName() + " => " + message, (Throwable)null, false, false);
      }
   }
}
