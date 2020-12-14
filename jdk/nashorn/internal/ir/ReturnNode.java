package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
public class ReturnNode extends Statement {
   private static final long serialVersionUID = 1L;
   private final Expression expression;

   public ReturnNode(int lineNumber, long token, int finish, Expression expression) {
      super(lineNumber, token, finish);
      this.expression = expression;
   }

   private ReturnNode(ReturnNode returnNode, Expression expression) {
      super(returnNode);
      this.expression = expression;
   }

   public boolean isTerminal() {
      return true;
   }

   public boolean isReturn() {
      return this.isTokenType(TokenType.RETURN);
   }

   public boolean hasExpression() {
      return this.expression != null;
   }

   public boolean isYield() {
      return this.isTokenType(TokenType.YIELD);
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterReturnNode(this)) {
         return this.expression != null ? visitor.leaveReturnNode(this.setExpression((Expression)this.expression.accept(visitor))) : visitor.leaveReturnNode(this);
      } else {
         return this;
      }
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append(this.isYield() ? "yield" : "return");
      if (this.expression != null) {
         sb.append(' ');
         this.expression.toString(sb, printType);
      }

   }

   public Expression getExpression() {
      return this.expression;
   }

   public ReturnNode setExpression(Expression expression) {
      return this.expression == expression ? this : new ReturnNode(this, expression);
   }
}
