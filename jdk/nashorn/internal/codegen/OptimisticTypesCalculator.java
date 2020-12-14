package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.CatchNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.ExpressionStatement;
import jdk.nashorn.internal.ir.ForNode;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IfNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.JoinPredecessorExpression;
import jdk.nashorn.internal.ir.LoopNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Optimistic;
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.TernaryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

final class OptimisticTypesCalculator extends SimpleNodeVisitor {
   final Compiler compiler;
   final Deque<BitSet> neverOptimistic = new ArrayDeque();

   OptimisticTypesCalculator(Compiler compiler) {
      this.compiler = compiler;
   }

   public boolean enterAccessNode(AccessNode accessNode) {
      this.tagNeverOptimistic(accessNode.getBase());
      return true;
   }

   public boolean enterPropertyNode(PropertyNode propertyNode) {
      if (propertyNode.getKeyName().equals("__proto__")) {
         this.tagNeverOptimistic(propertyNode.getValue());
      }

      return super.enterPropertyNode(propertyNode);
   }

   public boolean enterBinaryNode(BinaryNode binaryNode) {
      if (binaryNode.isAssignment()) {
         Expression lhs = binaryNode.lhs();
         if (!binaryNode.isSelfModifying()) {
            this.tagNeverOptimistic(lhs);
         }

         if (lhs instanceof IdentNode) {
            Symbol symbol = ((IdentNode)lhs).getSymbol();
            if (symbol.isInternal() && !binaryNode.rhs().isSelfModifying()) {
               this.tagNeverOptimistic(binaryNode.rhs());
            }
         }
      } else if (binaryNode.isTokenType(TokenType.INSTANCEOF)) {
         this.tagNeverOptimistic(binaryNode.lhs());
         this.tagNeverOptimistic(binaryNode.rhs());
      }

      return true;
   }

   public boolean enterCallNode(CallNode callNode) {
      this.tagNeverOptimistic(callNode.getFunction());
      return true;
   }

   public boolean enterCatchNode(CatchNode catchNode) {
      this.tagNeverOptimistic(catchNode.getExceptionCondition());
      return true;
   }

   public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
      Expression expr = expressionStatement.getExpression();
      if (!expr.isSelfModifying()) {
         this.tagNeverOptimistic(expr);
      }

      return true;
   }

   public boolean enterForNode(ForNode forNode) {
      if (forNode.isForIn()) {
         this.tagNeverOptimistic(forNode.getModify());
      } else {
         this.tagNeverOptimisticLoopTest(forNode);
      }

      return true;
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      if (!this.neverOptimistic.isEmpty() && this.compiler.isOnDemandCompilation()) {
         return false;
      } else {
         this.neverOptimistic.push(new BitSet());
         return true;
      }
   }

   public boolean enterIfNode(IfNode ifNode) {
      this.tagNeverOptimistic(ifNode.getTest());
      return true;
   }

   public boolean enterIndexNode(IndexNode indexNode) {
      this.tagNeverOptimistic(indexNode.getBase());
      return true;
   }

   public boolean enterTernaryNode(TernaryNode ternaryNode) {
      this.tagNeverOptimistic(ternaryNode.getTest());
      return true;
   }

   public boolean enterUnaryNode(UnaryNode unaryNode) {
      if (unaryNode.isTokenType(TokenType.NOT) || unaryNode.isTokenType(TokenType.NEW)) {
         this.tagNeverOptimistic(unaryNode.getExpression());
      }

      return true;
   }

   public boolean enterVarNode(VarNode varNode) {
      this.tagNeverOptimistic(varNode.getName());
      return true;
   }

   public boolean enterWhileNode(WhileNode whileNode) {
      this.tagNeverOptimisticLoopTest(whileNode);
      return true;
   }

   protected Node leaveDefault(Node node) {
      return (Node)(node instanceof Optimistic ? this.leaveOptimistic((Optimistic)node) : node);
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      this.neverOptimistic.pop();
      return functionNode;
   }

   public Node leaveIdentNode(IdentNode identNode) {
      Symbol symbol = identNode.getSymbol();
      if (symbol == null) {
         assert identNode.isPropertyName();

         return identNode;
      } else if (symbol.isBytecodeLocal()) {
         return identNode;
      } else if (symbol.isParam() && this.lc.getCurrentFunction().isVarArg()) {
         return identNode.setType(identNode.getMostPessimisticType());
      } else {
         assert symbol.isScope();

         return this.leaveOptimistic(identNode);
      }
   }

   private Expression leaveOptimistic(Optimistic opt) {
      int pp = opt.getProgramPoint();
      return UnwarrantedOptimismException.isValid(pp) && !((BitSet)this.neverOptimistic.peek()).get(pp) ? (Expression)opt.setType(this.compiler.getOptimisticType(opt)) : (Expression)opt;
   }

   private void tagNeverOptimistic(Expression expr) {
      if (expr instanceof Optimistic) {
         int pp = ((Optimistic)expr).getProgramPoint();
         if (UnwarrantedOptimismException.isValid(pp)) {
            ((BitSet)this.neverOptimistic.peek()).set(pp);
         }
      }

   }

   private void tagNeverOptimisticLoopTest(LoopNode loopNode) {
      JoinPredecessorExpression test = loopNode.getTest();
      if (test != null) {
         this.tagNeverOptimistic(test.getExpression());
      }

   }
}
