package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class ForNode extends LoopNode {
   private static final long serialVersionUID = 1L;
   private final Expression init;
   private final JoinPredecessorExpression modify;
   private final Symbol iterator;
   public static final int IS_FOR_IN = 1;
   public static final int IS_FOR_EACH = 2;
   public static final int PER_ITERATION_SCOPE = 4;
   private final int flags;

   public ForNode(int lineNumber, long token, int finish, Block body, int flags) {
      super(lineNumber, token, finish, body, false);
      this.flags = flags;
      this.init = null;
      this.modify = null;
      this.iterator = null;
   }

   private ForNode(ForNode forNode, Expression init, JoinPredecessorExpression test, Block body, JoinPredecessorExpression modify, int flags, boolean controlFlowEscapes, LocalVariableConversion conversion, Symbol iterator) {
      super(forNode, test, body, controlFlowEscapes, conversion);
      this.init = init;
      this.modify = modify;
      this.flags = flags;
      this.iterator = iterator;
   }

   public Node ensureUniqueLabels(LexicalContext lc) {
      return (Node)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
   }

   public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterForNode(this) ? visitor.leaveForNode(this.setInit(lc, this.init == null ? null : (Expression)this.init.accept(visitor)).setTest(lc, this.test == null ? null : (JoinPredecessorExpression)this.test.accept(visitor)).setModify(lc, this.modify == null ? null : (JoinPredecessorExpression)this.modify.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor))) : this);
   }

   public void toString(StringBuilder sb, boolean printTypes) {
      sb.append("for");
      LocalVariableConversion.toString(this.conversion, sb).append(' ');
      if (this.isForIn()) {
         this.init.toString(sb, printTypes);
         sb.append(" in ");
         this.modify.toString(sb, printTypes);
      } else {
         if (this.init != null) {
            this.init.toString(sb, printTypes);
         }

         sb.append("; ");
         if (this.test != null) {
            this.test.toString(sb, printTypes);
         }

         sb.append("; ");
         if (this.modify != null) {
            this.modify.toString(sb, printTypes);
         }
      }

      sb.append(')');
   }

   public boolean hasGoto() {
      return !this.isForIn() && this.test == null;
   }

   public boolean mustEnter() {
      if (this.isForIn()) {
         return false;
      } else {
         return this.test == null;
      }
   }

   public Expression getInit() {
      return this.init;
   }

   public ForNode setInit(LexicalContext lc, Expression init) {
      return this.init == init ? this : (ForNode)Node.replaceInLexicalContext(lc, this, new ForNode(this, init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
   }

   public boolean isForIn() {
      return (this.flags & 1) != 0;
   }

   public ForNode setIsForIn(LexicalContext lc) {
      return this.setFlags(lc, this.flags | 1);
   }

   public boolean isForEach() {
      return (this.flags & 2) != 0;
   }

   public ForNode setIsForEach(LexicalContext lc) {
      return this.setFlags(lc, this.flags | 2);
   }

   public Symbol getIterator() {
      return this.iterator;
   }

   public ForNode setIterator(LexicalContext lc, Symbol iterator) {
      return this.iterator == iterator ? this : (ForNode)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, iterator));
   }

   public JoinPredecessorExpression getModify() {
      return this.modify;
   }

   public ForNode setModify(LexicalContext lc, JoinPredecessorExpression modify) {
      return this.modify == modify ? this : (ForNode)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
   }

   public ForNode setTest(LexicalContext lc, JoinPredecessorExpression test) {
      return this.test == test ? this : (ForNode)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
   }

   public Block getBody() {
      return this.body;
   }

   public ForNode setBody(LexicalContext lc, Block body) {
      return this.body == body ? this : (ForNode)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
   }

   public ForNode setControlFlowEscapes(LexicalContext lc, boolean controlFlowEscapes) {
      return this.controlFlowEscapes == controlFlowEscapes ? this : (ForNode)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, controlFlowEscapes, this.conversion, this.iterator));
   }

   private ForNode setFlags(LexicalContext lc, int flags) {
      return this.flags == flags ? this : (ForNode)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, flags, this.controlFlowEscapes, this.conversion, this.iterator));
   }

   JoinPredecessor setLocalVariableConversionChanged(LexicalContext lc, LocalVariableConversion conversion) {
      return (JoinPredecessor)Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, conversion, this.iterator));
   }

   public boolean hasPerIterationScope() {
      return (this.flags & 4) != 0;
   }

   public ForNode setPerIterationScope(LexicalContext lc) {
      return this.setFlags(lc, this.flags | 4);
   }
}
