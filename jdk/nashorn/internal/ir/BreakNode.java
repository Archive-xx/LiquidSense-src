package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class BreakNode extends JumpStatement {
   private static final long serialVersionUID = 1L;

   public BreakNode(int lineNumber, long token, int finish, String labelName) {
      super(lineNumber, token, finish, labelName);
   }

   private BreakNode(BreakNode breakNode, LocalVariableConversion conversion) {
      super(breakNode, conversion);
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterBreakNode(this) ? visitor.leaveBreakNode(this) : this);
   }

   JumpStatement createNewJumpStatement(LocalVariableConversion conversion) {
      return new BreakNode(this, conversion);
   }

   String getStatementName() {
      return "break";
   }

   public BreakableNode getTarget(LexicalContext lc) {
      return lc.getBreakable(this.getLabelName());
   }

   Label getTargetLabel(BreakableNode target) {
      return target.getBreakLabel();
   }
}
