package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class WhileNode extends LoopNode {
   private static final long serialVersionUID = 1L;
   private final boolean isDoWhile;

   public WhileNode(int lineNumber, long token, int finish, boolean isDoWhile) {
      super(lineNumber, token, finish, (Block)null, false);
      this.isDoWhile = isDoWhile;
   }

   private WhileNode(WhileNode whileNode, JoinPredecessorExpression test, Block body, boolean controlFlowEscapes, LocalVariableConversion conversion) {
      super(whileNode, test, body, controlFlowEscapes, conversion);
      this.isDoWhile = whileNode.isDoWhile;
   }

   public Node ensureUniqueLabels(LexicalContext lc) {
      return (Node)Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, this.controlFlowEscapes, this.conversion));
   }

   public boolean hasGoto() {
      return this.test == null;
   }

   public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterWhileNode(this)) {
         return this.isDoWhile() ? visitor.leaveWhileNode(this.setBody(lc, (Block)this.body.accept(visitor)).setTest(lc, (JoinPredecessorExpression)this.test.accept(visitor))) : visitor.leaveWhileNode(this.setTest(lc, (JoinPredecessorExpression)this.test.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor)));
      } else {
         return this;
      }
   }

   public WhileNode setTest(LexicalContext lc, JoinPredecessorExpression test) {
      return this.test == test ? this : (WhileNode)Node.replaceInLexicalContext(lc, this, new WhileNode(this, test, this.body, this.controlFlowEscapes, this.conversion));
   }

   public Block getBody() {
      return this.body;
   }

   public WhileNode setBody(LexicalContext lc, Block body) {
      return this.body == body ? this : (WhileNode)Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, body, this.controlFlowEscapes, this.conversion));
   }

   public WhileNode setControlFlowEscapes(LexicalContext lc, boolean controlFlowEscapes) {
      return this.controlFlowEscapes == controlFlowEscapes ? this : (WhileNode)Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, controlFlowEscapes, this.conversion));
   }

   JoinPredecessor setLocalVariableConversionChanged(LexicalContext lc, LocalVariableConversion conversion) {
      return (JoinPredecessor)Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, this.controlFlowEscapes, conversion));
   }

   public boolean isDoWhile() {
      return this.isDoWhile;
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append("while (");
      this.test.toString(sb, printType);
      sb.append(')');
   }

   public boolean mustEnter() {
      if (this.isDoWhile()) {
         return true;
      } else {
         return this.test == null;
      }
   }

   public boolean hasPerIterationScope() {
      return false;
   }
}
