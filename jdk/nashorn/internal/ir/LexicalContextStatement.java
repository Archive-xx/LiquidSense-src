package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.visitor.NodeVisitor;

abstract class LexicalContextStatement extends Statement implements LexicalContextNode {
   private static final long serialVersionUID = 1L;

   protected LexicalContextStatement(int lineNumber, long token, int finish) {
      super(lineNumber, token, finish);
   }

   protected LexicalContextStatement(LexicalContextStatement node) {
      super(node);
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return LexicalContextNode.Acceptor.accept(this, visitor);
   }
}
