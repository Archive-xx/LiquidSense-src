package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class ThrowNode extends Statement implements JoinPredecessor {
   private static final long serialVersionUID = 1L;
   private final Expression expression;
   private final LocalVariableConversion conversion;
   private final boolean isSyntheticRethrow;

   public ThrowNode(int lineNumber, long token, int finish, Expression expression, boolean isSyntheticRethrow) {
      super(lineNumber, token, finish);
      this.expression = expression;
      this.isSyntheticRethrow = isSyntheticRethrow;
      this.conversion = null;
   }

   private ThrowNode(ThrowNode node, Expression expression, boolean isSyntheticRethrow, LocalVariableConversion conversion) {
      super(node);
      this.expression = expression;
      this.isSyntheticRethrow = isSyntheticRethrow;
      this.conversion = conversion;
   }

   public boolean isTerminal() {
      return true;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterThrowNode(this) ? visitor.leaveThrowNode(this.setExpression((Expression)this.expression.accept(visitor))) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append("throw ");
      if (this.expression != null) {
         this.expression.toString(sb, printType);
      }

      if (this.conversion != null) {
         this.conversion.toString(sb);
      }

   }

   public Expression getExpression() {
      return this.expression;
   }

   public ThrowNode setExpression(Expression expression) {
      return this.expression == expression ? this : new ThrowNode(this, expression, this.isSyntheticRethrow, this.conversion);
   }

   public boolean isSyntheticRethrow() {
      return this.isSyntheticRethrow;
   }

   public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
      return this.conversion == conversion ? this : new ThrowNode(this, this.expression, this.isSyntheticRethrow, conversion);
   }

   public LocalVariableConversion getLocalVariableConversion() {
      return this.conversion;
   }
}
