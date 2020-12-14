package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
public final class TernaryNode extends Expression {
   private static final long serialVersionUID = 1L;
   private final Expression test;
   private final JoinPredecessorExpression trueExpr;
   private final JoinPredecessorExpression falseExpr;

   public TernaryNode(long token, Expression test, JoinPredecessorExpression trueExpr, JoinPredecessorExpression falseExpr) {
      super(token, falseExpr.getFinish());
      this.test = test;
      this.trueExpr = trueExpr;
      this.falseExpr = falseExpr;
   }

   private TernaryNode(TernaryNode ternaryNode, Expression test, JoinPredecessorExpression trueExpr, JoinPredecessorExpression falseExpr) {
      super(ternaryNode);
      this.test = test;
      this.trueExpr = trueExpr;
      this.falseExpr = falseExpr;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterTernaryNode(this)) {
         Expression newTest = (Expression)this.getTest().accept(visitor);
         JoinPredecessorExpression newTrueExpr = (JoinPredecessorExpression)this.trueExpr.accept(visitor);
         JoinPredecessorExpression newFalseExpr = (JoinPredecessorExpression)this.falseExpr.accept(visitor);
         return visitor.leaveTernaryNode(this.setTest(newTest).setTrueExpression(newTrueExpr).setFalseExpression(newFalseExpr));
      } else {
         return this;
      }
   }

   public void toString(StringBuilder sb, boolean printType) {
      TokenType tokenType = this.tokenType();
      boolean testParen = tokenType.needsParens(this.getTest().tokenType(), true);
      boolean trueParen = tokenType.needsParens(this.getTrueExpression().tokenType(), false);
      boolean falseParen = tokenType.needsParens(this.getFalseExpression().tokenType(), false);
      if (testParen) {
         sb.append('(');
      }

      this.getTest().toString(sb, printType);
      if (testParen) {
         sb.append(')');
      }

      sb.append(" ? ");
      if (trueParen) {
         sb.append('(');
      }

      this.getTrueExpression().toString(sb, printType);
      if (trueParen) {
         sb.append(')');
      }

      sb.append(" : ");
      if (falseParen) {
         sb.append('(');
      }

      this.getFalseExpression().toString(sb, printType);
      if (falseParen) {
         sb.append(')');
      }

   }

   public boolean isLocal() {
      return this.getTest().isLocal() && this.getTrueExpression().isLocal() && this.getFalseExpression().isLocal();
   }

   public Type getType() {
      return Type.widestReturnType(this.getTrueExpression().getType(), this.getFalseExpression().getType());
   }

   public Expression getTest() {
      return this.test;
   }

   public JoinPredecessorExpression getTrueExpression() {
      return this.trueExpr;
   }

   public JoinPredecessorExpression getFalseExpression() {
      return this.falseExpr;
   }

   public TernaryNode setTest(Expression test) {
      return this.test == test ? this : new TernaryNode(this, test, this.trueExpr, this.falseExpr);
   }

   public TernaryNode setTrueExpression(JoinPredecessorExpression trueExpr) {
      return this.trueExpr == trueExpr ? this : new TernaryNode(this, this.test, trueExpr, this.falseExpr);
   }

   public TernaryNode setFalseExpression(JoinPredecessorExpression falseExpr) {
      return this.falseExpr == falseExpr ? this : new TernaryNode(this, this.test, this.trueExpr, falseExpr);
   }
}
