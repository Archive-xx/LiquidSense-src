package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;

class CacheAst extends SimpleNodeVisitor {
   private final Deque<RecompilableScriptFunctionData> dataStack = new ArrayDeque();
   private final Compiler compiler;

   CacheAst(Compiler compiler) {
      this.compiler = compiler;

      assert !compiler.isOnDemandCompilation();

   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      int id = functionNode.getId();
      this.dataStack.push(this.dataStack.isEmpty() ? this.compiler.getScriptFunctionData(id) : ((RecompilableScriptFunctionData)this.dataStack.peek()).getScriptFunctionData(id));
      return true;
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      RecompilableScriptFunctionData data = (RecompilableScriptFunctionData)this.dataStack.pop();
      if (functionNode.isSplit()) {
         data.setCachedAst(functionNode);
      }

      return !this.dataStack.isEmpty() && (((RecompilableScriptFunctionData)this.dataStack.peek()).getFunctionFlags() & 16) != 0 ? functionNode.setBody(this.lc, functionNode.getBody().setStatements((LexicalContext)null, Collections.emptyList())) : functionNode;
   }
}
