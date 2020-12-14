package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class EmptyNode extends Statement {
   private static final long serialVersionUID = 1L;

   public EmptyNode(Statement node) {
      super(node);
   }

   public EmptyNode(int lineNumber, long token, int finish) {
      super(lineNumber, token, finish);
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterEmptyNode(this) ? visitor.leaveEmptyNode(this) : this);
   }

   public void toString(StringBuilder sb, boolean printTypes) {
      sb.append(';');
   }
}
