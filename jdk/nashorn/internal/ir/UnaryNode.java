package jdk.nashorn.internal.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Ignore;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
public final class UnaryNode extends Expression implements Assignment<Expression>, Optimistic {
   private static final long serialVersionUID = 1L;
   private final Expression expression;
   private final int programPoint;
   private final Type type;
   @Ignore
   private static final List<TokenType> CAN_OVERFLOW;

   public UnaryNode(long token, Expression rhs) {
      this(token, Math.min(rhs.getStart(), Token.descPosition(token)), Math.max(Token.descPosition(token) + Token.descLength(token), rhs.getFinish()), rhs);
   }

   public UnaryNode(long token, int start, int finish, Expression expression) {
      super(token, start, finish);
      this.expression = expression;
      this.programPoint = -1;
      this.type = null;
   }

   private UnaryNode(UnaryNode unaryNode, Expression expression, Type type, int programPoint) {
      super(unaryNode);
      this.expression = expression;
      this.programPoint = programPoint;
      this.type = type;
   }

   public boolean isAssignment() {
      switch(this.tokenType()) {
      case DECPOSTFIX:
      case DECPREFIX:
      case INCPOSTFIX:
      case INCPREFIX:
         return true;
      default:
         return false;
      }
   }

   public boolean isSelfModifying() {
      return this.isAssignment();
   }

   public Type getWidestOperationType() {
      switch(this.tokenType()) {
      case ADD:
         Type operandType = this.getExpression().getType();
         if (operandType == Type.BOOLEAN) {
            return Type.INT;
         } else if (operandType.isObject()) {
            return Type.NUMBER;
         } else {
            assert operandType.isNumeric();

            return operandType;
         }
      case SUB:
         return Type.NUMBER;
      case NOT:
      case DELETE:
         return Type.BOOLEAN;
      case BIT_NOT:
         return Type.INT;
      case VOID:
         return Type.UNDEFINED;
      default:
         return (Type)(this.isAssignment() ? Type.NUMBER : Type.OBJECT);
      }
   }

   public Expression getAssignmentDest() {
      return this.isAssignment() ? this.getExpression() : null;
   }

   public UnaryNode setAssignmentDest(Expression n) {
      return this.setExpression(n);
   }

   public Expression getAssignmentSource() {
      return this.getAssignmentDest();
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterUnaryNode(this) ? visitor.leaveUnaryNode(this.setExpression((Expression)this.expression.accept(visitor))) : this);
   }

   public boolean isLocal() {
      switch(this.tokenType()) {
      case DECPOSTFIX:
      case DECPREFIX:
      case INCPOSTFIX:
      case INCPREFIX:
         return this.expression instanceof IdentNode && this.expression.isLocal() && this.expression.getType().isJSPrimitive();
      case ADD:
      case SUB:
      case NOT:
      case BIT_NOT:
         return this.expression.isLocal() && this.expression.getType().isJSPrimitive();
      case DELETE:
      case VOID:
      default:
         return this.expression.isLocal();
      case NEW:
         return false;
      }
   }

   public void toString(final StringBuilder sb, final boolean printType) {
      this.toString(sb, new Runnable() {
         public void run() {
            UnaryNode.this.getExpression().toString(sb, printType);
         }
      }, printType);
   }

   public void toString(StringBuilder sb, Runnable rhsStringBuilder, boolean printType) {
      TokenType tokenType = this.tokenType();
      String name = tokenType.getName();
      boolean isPostfix = tokenType == TokenType.DECPOSTFIX || tokenType == TokenType.INCPOSTFIX;
      if (this.isOptimistic()) {
         sb.append("%");
      }

      boolean rhsParen = tokenType.needsParens(this.getExpression().tokenType(), false);
      if (!isPostfix) {
         if (name == null) {
            sb.append(tokenType.name());
            rhsParen = true;
         } else {
            sb.append(name);
            if (tokenType.ordinal() > TokenType.BIT_NOT.ordinal()) {
               sb.append(' ');
            }
         }
      }

      if (rhsParen) {
         sb.append('(');
      }

      rhsStringBuilder.run();
      if (rhsParen) {
         sb.append(')');
      }

      if (isPostfix) {
         sb.append(tokenType == TokenType.DECPOSTFIX ? "--" : "++");
      }

   }

   public Expression getExpression() {
      return this.expression;
   }

   public UnaryNode setExpression(Expression expression) {
      return this.expression == expression ? this : new UnaryNode(this, expression, this.type, this.programPoint);
   }

   public int getProgramPoint() {
      return this.programPoint;
   }

   public UnaryNode setProgramPoint(int programPoint) {
      return this.programPoint == programPoint ? this : new UnaryNode(this, this.expression, this.type, programPoint);
   }

   public boolean canBeOptimistic() {
      return this.getMostOptimisticType() != this.getMostPessimisticType();
   }

   public Type getMostOptimisticType() {
      return (Type)(CAN_OVERFLOW.contains(this.tokenType()) ? Type.INT : this.getMostPessimisticType());
   }

   public Type getMostPessimisticType() {
      return this.getWidestOperationType();
   }

   public Type getType() {
      Type widest = this.getWidestOperationType();
      return this.type == null ? widest : Type.narrowest(widest, Type.widest(this.type, this.expression.getType()));
   }

   public UnaryNode setType(Type type) {
      return this.type == type ? this : new UnaryNode(this, this.expression, type, this.programPoint);
   }

   static {
      CAN_OVERFLOW = Collections.unmodifiableList(Arrays.asList(TokenType.ADD, TokenType.SUB, TokenType.DECPREFIX, TokenType.DECPOSTFIX, TokenType.INCPREFIX, TokenType.INCPOSTFIX));
   }
}
