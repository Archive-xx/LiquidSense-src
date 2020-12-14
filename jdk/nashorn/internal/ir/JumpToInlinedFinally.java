package jdk.nashorn.internal.ir;

import java.util.Objects;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class JumpToInlinedFinally extends JumpStatement {
   private static final long serialVersionUID = 1L;

   public JumpToInlinedFinally(String labelName) {
      super(-1, 0L, 0, (String)Objects.requireNonNull(labelName));
   }

   private JumpToInlinedFinally(JumpToInlinedFinally breakNode, LocalVariableConversion conversion) {
      super(breakNode, conversion);
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterJumpToInlinedFinally(this) ? visitor.leaveJumpToInlinedFinally(this) : this);
   }

   JumpStatement createNewJumpStatement(LocalVariableConversion conversion) {
      return new JumpToInlinedFinally(this, conversion);
   }

   String getStatementName() {
      return ":jumpToInlinedFinally";
   }

   public Block getTarget(LexicalContext lc) {
      return lc.getInlinedFinally(this.getLabelName());
   }

   public TryNode getPopScopeLimit(LexicalContext lc) {
      return lc.getTryNodeForInlinedFinally(this.getLabelName());
   }

   Label getTargetLabel(BreakableNode target) {
      assert target != null;

      return ((Block)target).getEntryLabel();
   }
}
