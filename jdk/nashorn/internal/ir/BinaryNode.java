package jdk.nashorn.internal.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Ignore;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
public final class BinaryNode extends Expression implements Assignment<Expression>, Optimistic {
   private static final long serialVersionUID = 1L;
   private static final Type OPTIMISTIC_UNDECIDED_TYPE = Type.typeFor((new Object() {
   }).getClass());
   private final Expression lhs;
   private final Expression rhs;
   private final int programPoint;
   private final Type type;
   private transient Type cachedType;
   @Ignore
   private static final Set<TokenType> CAN_OVERFLOW;

   public BinaryNode(long token, Expression lhs, Expression rhs) {
      super(token, lhs.getStart(), rhs.getFinish());

      assert !this.isTokenType(TokenType.AND) && !this.isTokenType(TokenType.OR) || lhs instanceof JoinPredecessorExpression;

      this.lhs = lhs;
      this.rhs = rhs;
      this.programPoint = -1;
      this.type = null;
   }

   private BinaryNode(BinaryNode binaryNode, Expression lhs, Expression rhs, Type type, int programPoint) {
      super(binaryNode);
      this.lhs = lhs;
      this.rhs = rhs;
      this.programPoint = programPoint;
      this.type = type;
   }

   public boolean isComparison() {
      switch(this.tokenType()) {
      case EQ:
      case EQ_STRICT:
      case NE:
      case NE_STRICT:
      case LE:
      case LT:
      case GE:
      case GT:
         return true;
      default:
         return false;
      }
   }

   public boolean isRelational() {
      switch(this.tokenType()) {
      case LE:
      case LT:
      case GE:
      case GT:
         return true;
      default:
         return false;
      }
   }

   public boolean isLogical() {
      return isLogical(this.tokenType());
   }

   public static boolean isLogical(TokenType tokenType) {
      switch(tokenType) {
      case AND:
      case OR:
         return true;
      default:
         return false;
      }
   }

   public Type getWidestOperandType() {
      switch(this.tokenType()) {
      case SHR:
      case ASSIGN_SHR:
         return Type.INT;
      case INSTANCEOF:
         return Type.OBJECT;
      default:
         return this.isComparison() ? Type.OBJECT : this.getWidestOperationType();
      }
   }

   public Type getWidestOperationType() {
      Type lhsType;
      Type rhsType;
      switch(this.tokenType()) {
      case AND:
      case OR:
         return Type.widestReturnType(this.lhs.getType(), this.rhs.getType());
      case SHR:
      case ASSIGN_SHR:
         return Type.NUMBER;
      case INSTANCEOF:
         return Type.BOOLEAN;
      case ADD:
      case ASSIGN_ADD:
         lhsType = this.lhs.getType();
         rhsType = this.rhs.getType();
         if (lhsType == Type.BOOLEAN && rhsType == Type.BOOLEAN) {
            return Type.INT;
         } else {
            if (!isString(lhsType) && !isString(rhsType)) {
               Type widestOperandType = Type.widest(undefinedToNumber(booleanToInt(lhsType)), undefinedToNumber(booleanToInt(rhsType)));
               if (widestOperandType.isNumeric()) {
                  return Type.NUMBER;
               }

               return Type.OBJECT;
            }

            return Type.CHARSEQUENCE;
         }
      case ASSIGN_SAR:
      case ASSIGN_SHL:
      case BIT_AND:
      case BIT_OR:
      case BIT_XOR:
      case ASSIGN_BIT_AND:
      case ASSIGN_BIT_OR:
      case ASSIGN_BIT_XOR:
      case SAR:
      case SHL:
         return Type.INT;
      case DIV:
      case MOD:
      case ASSIGN_DIV:
      case ASSIGN_MOD:
         return Type.NUMBER;
      case MUL:
      case SUB:
      case ASSIGN_MUL:
      case ASSIGN_SUB:
         lhsType = this.lhs.getType();
         rhsType = this.rhs.getType();
         if (lhsType == Type.BOOLEAN && rhsType == Type.BOOLEAN) {
            return Type.INT;
         }

         return Type.NUMBER;
      case VOID:
         return Type.UNDEFINED;
      case ASSIGN:
         return this.rhs.getType();
      case COMMALEFT:
         return this.lhs.getType();
      case COMMARIGHT:
         return this.rhs.getType();
      default:
         return this.isComparison() ? Type.BOOLEAN : Type.OBJECT;
      }
   }

   private static boolean isString(Type type) {
      return type == Type.STRING || type == Type.CHARSEQUENCE;
   }

   private static Type booleanToInt(Type type) {
      return (Type)(type == Type.BOOLEAN ? Type.INT : type);
   }

   private static Type undefinedToNumber(Type type) {
      return (Type)(type == Type.UNDEFINED ? Type.NUMBER : type);
   }

   public boolean isAssignment() {
      switch(this.tokenType()) {
      case ASSIGN_SHR:
      case ASSIGN_ADD:
      case ASSIGN_SAR:
      case ASSIGN_SHL:
      case ASSIGN_BIT_AND:
      case ASSIGN_BIT_OR:
      case ASSIGN_BIT_XOR:
      case ASSIGN_DIV:
      case ASSIGN_MOD:
      case ASSIGN_MUL:
      case ASSIGN_SUB:
      case ASSIGN:
         return true;
      case INSTANCEOF:
      case ADD:
      case BIT_AND:
      case BIT_OR:
      case BIT_XOR:
      case SAR:
      case SHL:
      case DIV:
      case MOD:
      case MUL:
      case SUB:
      case VOID:
      default:
         return false;
      }
   }

   public boolean isSelfModifying() {
      return this.isAssignment() && !this.isTokenType(TokenType.ASSIGN);
   }

   public Expression getAssignmentDest() {
      return this.isAssignment() ? this.lhs() : null;
   }

   public BinaryNode setAssignmentDest(Expression n) {
      return this.setLHS(n);
   }

   public Expression getAssignmentSource() {
      return this.rhs();
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterBinaryNode(this) ? visitor.leaveBinaryNode(this.setLHS((Expression)this.lhs.accept(visitor)).setRHS((Expression)this.rhs.accept(visitor))) : this);
   }

   public boolean isLocal() {
      switch(this.tokenType()) {
      case SHR:
      case ADD:
      case BIT_AND:
      case BIT_OR:
      case BIT_XOR:
      case SAR:
      case SHL:
      case DIV:
      case MOD:
      case MUL:
      case SUB:
         return this.lhs.isLocal() && this.lhs.getType().isJSPrimitive() && this.rhs.isLocal() && this.rhs.getType().isJSPrimitive();
      case ASSIGN_SHR:
      case ASSIGN_ADD:
      case ASSIGN_SAR:
      case ASSIGN_SHL:
      case ASSIGN_BIT_AND:
      case ASSIGN_BIT_OR:
      case ASSIGN_BIT_XOR:
      case ASSIGN_DIV:
      case ASSIGN_MOD:
      case ASSIGN_MUL:
      case ASSIGN_SUB:
         return this.lhs instanceof IdentNode && this.lhs.isLocal() && this.lhs.getType().isJSPrimitive() && this.rhs.isLocal() && this.rhs.getType().isJSPrimitive();
      case INSTANCEOF:
      case VOID:
      default:
         return false;
      case ASSIGN:
         return this.lhs instanceof IdentNode && this.lhs.isLocal() && this.rhs.isLocal();
      }
   }

   public boolean isAlwaysFalse() {
      switch(this.tokenType()) {
      case COMMALEFT:
         return this.lhs.isAlwaysFalse();
      case COMMARIGHT:
         return this.rhs.isAlwaysFalse();
      default:
         return false;
      }
   }

   public boolean isAlwaysTrue() {
      switch(this.tokenType()) {
      case COMMALEFT:
         return this.lhs.isAlwaysTrue();
      case COMMARIGHT:
         return this.rhs.isAlwaysTrue();
      default:
         return false;
      }
   }

   public void toString(StringBuilder sb, boolean printType) {
      TokenType tokenType = this.tokenType();
      boolean lhsParen = tokenType.needsParens(this.lhs().tokenType(), true);
      boolean rhsParen = tokenType.needsParens(this.rhs().tokenType(), false);
      if (lhsParen) {
         sb.append('(');
      }

      this.lhs().toString(sb, printType);
      if (lhsParen) {
         sb.append(')');
      }

      sb.append(' ');
      switch(tokenType) {
      case COMMALEFT:
         sb.append(",<");
         break;
      case COMMARIGHT:
         sb.append(",>");
         break;
      case INCPREFIX:
      case DECPREFIX:
         sb.append("++");
         break;
      default:
         sb.append(tokenType.getName());
      }

      if (this.isOptimistic()) {
         sb.append("%");
      }

      sb.append(' ');
      if (rhsParen) {
         sb.append('(');
      }

      this.rhs().toString(sb, printType);
      if (rhsParen) {
         sb.append(')');
      }

   }

   public Expression lhs() {
      return this.lhs;
   }

   public Expression rhs() {
      return this.rhs;
   }

   public BinaryNode setLHS(Expression lhs) {
      return this.lhs == lhs ? this : new BinaryNode(this, lhs, this.rhs, this.type, this.programPoint);
   }

   public BinaryNode setRHS(Expression rhs) {
      return this.rhs == rhs ? this : new BinaryNode(this, this.lhs, rhs, this.type, this.programPoint);
   }

   public BinaryNode setOperands(Expression lhs, Expression rhs) {
      return this.lhs == lhs && this.rhs == rhs ? this : new BinaryNode(this, lhs, rhs, this.type, this.programPoint);
   }

   public int getProgramPoint() {
      return this.programPoint;
   }

   public boolean canBeOptimistic() {
      return this.isTokenType(TokenType.ADD) || this.getMostOptimisticType() != this.getMostPessimisticType();
   }

   public BinaryNode setProgramPoint(int programPoint) {
      return this.programPoint == programPoint ? this : new BinaryNode(this, this.lhs, this.rhs, this.type, programPoint);
   }

   public Type getMostOptimisticType() {
      TokenType tokenType = this.tokenType();
      if (tokenType != TokenType.ADD && tokenType != TokenType.ASSIGN_ADD) {
         return (Type)(CAN_OVERFLOW.contains(tokenType) ? Type.INT : this.getMostPessimisticType());
      } else {
         return OPTIMISTIC_UNDECIDED_TYPE;
      }
   }

   public Type getMostPessimisticType() {
      return this.getWidestOperationType();
   }

   public boolean isOptimisticUndecidedType() {
      return this.type == OPTIMISTIC_UNDECIDED_TYPE;
   }

   public Type getType() {
      if (this.cachedType == null) {
         this.cachedType = this.getTypeUncached();
      }

      return this.cachedType;
   }

   private Type getTypeUncached() {
      if (this.type == OPTIMISTIC_UNDECIDED_TYPE) {
         return decideType(this.lhs.getType(), this.rhs.getType());
      } else {
         Type widest = this.getWidestOperationType();
         if (this.type == null) {
            return widest;
         } else {
            return this.tokenType() != TokenType.ASSIGN_SHR && this.tokenType() != TokenType.SHR ? Type.narrowest(widest, Type.widest(this.type, Type.widest(this.lhs.getType(), this.rhs.getType()))) : this.type;
         }
      }
   }

   private static Type decideType(Type lhsType, Type rhsType) {
      if (!isString(lhsType) && !isString(rhsType)) {
         Type widest = Type.widest(undefinedToNumber(booleanToInt(lhsType)), undefinedToNumber(booleanToInt(rhsType)));
         return widest.isObject() ? Type.OBJECT : widest;
      } else {
         return Type.CHARSEQUENCE;
      }
   }

   public BinaryNode decideType() {
      assert this.type == OPTIMISTIC_UNDECIDED_TYPE;

      return this.setType(decideType(this.lhs.getType(), this.rhs.getType()));
   }

   public BinaryNode setType(Type type) {
      return this.type == type ? this : new BinaryNode(this, this.lhs, this.rhs, type, this.programPoint);
   }

   static {
      CAN_OVERFLOW = Collections.unmodifiableSet(new HashSet(Arrays.asList(TokenType.ADD, TokenType.DIV, TokenType.MOD, TokenType.MUL, TokenType.SUB, TokenType.ASSIGN_ADD, TokenType.ASSIGN_DIV, TokenType.ASSIGN_MOD, TokenType.ASSIGN_MUL, TokenType.ASSIGN_SUB, TokenType.SHR, TokenType.ASSIGN_SHR)));
   }
}
