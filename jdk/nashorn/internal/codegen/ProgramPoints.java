package jdk.nashorn.internal.codegen;

import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.IntDeque;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Optimistic;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;

class ProgramPoints extends SimpleNodeVisitor {
   private final IntDeque nextProgramPoint = new IntDeque();
   private final Set<Node> noProgramPoint = new HashSet();

   private int next() {
      int next = this.nextProgramPoint.getAndIncrement();
      if (next > 2097151) {
         throw new AssertionError("Function has more than 2097151 program points");
      } else {
         return next;
      }
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      this.nextProgramPoint.push(1);
      return true;
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      this.nextProgramPoint.pop();
      return functionNode;
   }

   private Expression setProgramPoint(Optimistic optimistic) {
      return this.noProgramPoint.contains(optimistic) ? (Expression)optimistic : (Expression)((Expression)(optimistic.canBeOptimistic() ? optimistic.setProgramPoint(this.next()) : optimistic));
   }

   public boolean enterVarNode(VarNode varNode) {
      this.noProgramPoint.add(varNode.getName());
      return true;
   }

   public boolean enterIdentNode(IdentNode identNode) {
      if (identNode.isInternal()) {
         this.noProgramPoint.add(identNode);
      }

      return true;
   }

   public Node leaveIdentNode(IdentNode identNode) {
      return (Node)(identNode.isPropertyName() ? identNode : this.setProgramPoint(identNode));
   }

   public Node leaveCallNode(CallNode callNode) {
      return this.setProgramPoint(callNode);
   }

   public Node leaveAccessNode(AccessNode accessNode) {
      return this.setProgramPoint(accessNode);
   }

   public Node leaveIndexNode(IndexNode indexNode) {
      return this.setProgramPoint(indexNode);
   }

   public Node leaveBinaryNode(BinaryNode binaryNode) {
      return this.setProgramPoint(binaryNode);
   }

   public Node leaveUnaryNode(UnaryNode unaryNode) {
      return this.setProgramPoint(unaryNode);
   }
}
