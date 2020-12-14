package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class ExpressionStatement extends Statement {
   private static final long serialVersionUID = 1L;
   private final Expression expression;

   public ExpressionStatement(int lineNumber, long token, int finish, Expression expression) {
      super(lineNumber, token, finish);
      this.expression = expression;
   }

   private ExpressionStatement(ExpressionStatement expressionStatement, Expression expression) {
      super(expressionStatement);
      this.expression = expression;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterExpressionStatement(this) ? visitor.leaveExpressionStatement(this.setExpression((Expression)this.expression.accept(visitor))) : this);
   }

   public void toString(StringBuilder sb, boolean printTypes) {
      this.expression.toString(sb, printTypes);
   }

   public Expression getExpression() {
      return this.expression;
   }

   public ExpressionStatement setExpression(Expression expression) {
      return this.expression == expression ? this : new ExpressionStatement(this, expression);
   }
}
