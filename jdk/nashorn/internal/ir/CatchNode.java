package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class CatchNode extends Statement {
   private static final long serialVersionUID = 1L;
   private final IdentNode exception;
   private final Expression exceptionCondition;
   private final Block body;
   private final boolean isSyntheticRethrow;

   public CatchNode(int lineNumber, long token, int finish, IdentNode exception, Expression exceptionCondition, Block body, boolean isSyntheticRethrow) {
      super(lineNumber, token, finish);
      this.exception = exception == null ? null : exception.setIsInitializedHere();
      this.exceptionCondition = exceptionCondition;
      this.body = body;
      this.isSyntheticRethrow = isSyntheticRethrow;
   }

   private CatchNode(CatchNode catchNode, IdentNode exception, Expression exceptionCondition, Block body, boolean isSyntheticRethrow) {
      super(catchNode);
      this.exception = exception;
      this.exceptionCondition = exceptionCondition;
      this.body = body;
      this.isSyntheticRethrow = isSyntheticRethrow;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterCatchNode(this) ? visitor.leaveCatchNode(this.setException((IdentNode)this.exception.accept(visitor)).setExceptionCondition(this.exceptionCondition == null ? null : (Expression)this.exceptionCondition.accept(visitor)).setBody((Block)this.body.accept(visitor))) : this);
   }

   public boolean isTerminal() {
      return this.body.isTerminal();
   }

   public void toString(StringBuilder sb, boolean printTypes) {
      sb.append(" catch (");
      this.exception.toString(sb, printTypes);
      if (this.exceptionCondition != null) {
         sb.append(" if ");
         this.exceptionCondition.toString(sb, printTypes);
      }

      sb.append(')');
   }

   public IdentNode getException() {
      return this.exception;
   }

   public Expression getExceptionCondition() {
      return this.exceptionCondition;
   }

   public CatchNode setExceptionCondition(Expression exceptionCondition) {
      return this.exceptionCondition == exceptionCondition ? this : new CatchNode(this, this.exception, exceptionCondition, this.body, this.isSyntheticRethrow);
   }

   public Block getBody() {
      return this.body;
   }

   public CatchNode setException(IdentNode exception) {
      return this.exception == exception ? this : new CatchNode(this, exception, this.exceptionCondition, this.body, this.isSyntheticRethrow);
   }

   private CatchNode setBody(Block body) {
      return this.body == body ? this : new CatchNode(this, this.exception, this.exceptionCondition, body, this.isSyntheticRethrow);
   }

   public boolean isSyntheticRethrow() {
      return this.isSyntheticRethrow;
   }
}
