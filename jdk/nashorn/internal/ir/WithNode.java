package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class WithNode extends LexicalContextStatement {
   private static final long serialVersionUID = 1L;
   private final Expression expression;
   private final Block body;

   public WithNode(int lineNumber, long token, int finish) {
      super(lineNumber, token, finish);
      this.expression = null;
      this.body = null;
   }

   private WithNode(WithNode node, Expression expression, Block body) {
      super(node);
      this.expression = expression;
      this.body = body;
   }

   public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterWithNode(this) ? visitor.leaveWithNode(this.setExpression(lc, (Expression)this.expression.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor))) : this);
   }

   public boolean isTerminal() {
      return this.body.isTerminal();
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append("with (");
      this.expression.toString(sb, printType);
      sb.append(')');
   }

   public Block getBody() {
      return this.body;
   }

   public WithNode setBody(LexicalContext lc, Block body) {
      return this.body == body ? this : (WithNode)Node.replaceInLexicalContext(lc, this, new WithNode(this, this.expression, body));
   }

   public Expression getExpression() {
      return this.expression;
   }

   public WithNode setExpression(LexicalContext lc, Expression expression) {
      return this.expression == expression ? this : (WithNode)Node.replaceInLexicalContext(lc, this, new WithNode(this, expression, this.body));
   }
}
