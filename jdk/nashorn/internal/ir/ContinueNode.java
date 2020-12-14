package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public class ContinueNode extends JumpStatement {
   private static final long serialVersionUID = 1L;

   public ContinueNode(int lineNumber, long token, int finish, String labelName) {
      super(lineNumber, token, finish, labelName);
   }

   private ContinueNode(ContinueNode continueNode, LocalVariableConversion conversion) {
      super(continueNode, conversion);
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterContinueNode(this) ? visitor.leaveContinueNode(this) : this);
   }

   JumpStatement createNewJumpStatement(LocalVariableConversion conversion) {
      return new ContinueNode(this, conversion);
   }

   String getStatementName() {
      return "continue";
   }

   public BreakableNode getTarget(LexicalContext lc) {
      return lc.getContinueTo(this.getLabelName());
   }

   Label getTargetLabel(BreakableNode target) {
      return ((LoopNode)target).getContinueLabel();
   }
}
