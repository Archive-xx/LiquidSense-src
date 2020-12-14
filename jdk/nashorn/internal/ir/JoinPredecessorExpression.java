package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

public class JoinPredecessorExpression extends Expression implements JoinPredecessor {
   private static final long serialVersionUID = 1L;
   private final Expression expression;
   private final LocalVariableConversion conversion;

   public JoinPredecessorExpression() {
      this((Expression)null);
   }

   public JoinPredecessorExpression(Expression expression) {
      this(expression, (LocalVariableConversion)null);
   }

   private JoinPredecessorExpression(Expression expression, LocalVariableConversion conversion) {
      super(expression == null ? 0L : expression.getToken(), expression == null ? 0 : expression.getStart(), expression == null ? 0 : expression.getFinish());
      this.expression = expression;
      this.conversion = conversion;
   }

   public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
      return conversion == this.conversion ? this : new JoinPredecessorExpression(this.expression, conversion);
   }

   public Type getType() {
      return this.expression.getType();
   }

   public boolean isAlwaysFalse() {
      return this.expression != null && this.expression.isAlwaysFalse();
   }

   public boolean isAlwaysTrue() {
      return this.expression != null && this.expression.isAlwaysTrue();
   }

   public Expression getExpression() {
      return this.expression;
   }

   public JoinPredecessorExpression setExpression(Expression expression) {
      return expression == this.expression ? this : new JoinPredecessorExpression(expression, this.conversion);
   }

   public LocalVariableConversion getLocalVariableConversion() {
      return this.conversion;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterJoinPredecessorExpression(this)) {
         Expression expr = this.getExpression();
         return visitor.leaveJoinPredecessorExpression(expr == null ? this : this.setExpression((Expression)expr.accept(visitor)));
      } else {
         return this;
      }
   }

   public void toString(StringBuilder sb, boolean printType) {
      if (this.expression != null) {
         this.expression.toString(sb, printType);
      }

      if (this.conversion != null) {
         this.conversion.toString(sb);
      }

   }
}
