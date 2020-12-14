package jdk.nashorn.internal.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
public class RuntimeNode extends Expression {
   private static final long serialVersionUID = 1L;
   private final RuntimeNode.Request request;
   private final List<Expression> args;

   public RuntimeNode(long token, int finish, RuntimeNode.Request request, List<Expression> args) {
      super(token, finish);
      this.request = request;
      this.args = args;
   }

   private RuntimeNode(RuntimeNode runtimeNode, RuntimeNode.Request request, List<Expression> args) {
      super(runtimeNode);
      this.request = request;
      this.args = args;
   }

   public RuntimeNode(long token, int finish, RuntimeNode.Request request, Expression... args) {
      this(token, finish, request, Arrays.asList(args));
   }

   public RuntimeNode(Expression parent, RuntimeNode.Request request, Expression... args) {
      this(parent, request, Arrays.asList(args));
   }

   public RuntimeNode(Expression parent, RuntimeNode.Request request, List<Expression> args) {
      super(parent);
      this.request = request;
      this.args = args;
   }

   public RuntimeNode(UnaryNode parent, RuntimeNode.Request request) {
      this((Expression)parent, request, (Expression[])(parent.getExpression()));
   }

   public RuntimeNode(BinaryNode parent) {
      this((Expression)parent, RuntimeNode.Request.requestFor(parent), (Expression[])(parent.lhs(), parent.rhs()));
   }

   public RuntimeNode setRequest(RuntimeNode.Request request) {
      return this.request == request ? this : new RuntimeNode(this, request, this.args);
   }

   public Type getType() {
      return this.request.getReturnType();
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterRuntimeNode(this) ? visitor.leaveRuntimeNode(this.setArgs(Node.accept(visitor, this.args))) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append("ScriptRuntime.");
      sb.append(this.request);
      sb.append('(');
      boolean first = true;

      Node arg;
      for(Iterator var4 = this.args.iterator(); var4.hasNext(); arg.toString(sb, printType)) {
         arg = (Node)var4.next();
         if (!first) {
            sb.append(", ");
         } else {
            first = false;
         }
      }

      sb.append(')');
   }

   public List<Expression> getArgs() {
      return Collections.unmodifiableList(this.args);
   }

   public RuntimeNode setArgs(List<Expression> args) {
      return this.args == args ? this : new RuntimeNode(this, this.request, args);
   }

   public RuntimeNode.Request getRequest() {
      return this.request;
   }

   public boolean isPrimitive() {
      Iterator var1 = this.args.iterator();

      Expression arg;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         arg = (Expression)var1.next();
      } while(!arg.getType().isObject());

      return false;
   }

   public static enum Request {
      ADD(TokenType.ADD, Type.OBJECT, 2, true),
      DEBUGGER,
      NEW,
      TYPEOF,
      REFERENCE_ERROR,
      DELETE(TokenType.DELETE, Type.BOOLEAN, 1),
      SLOW_DELETE(TokenType.DELETE, Type.BOOLEAN, 1, false),
      FAIL_DELETE(TokenType.DELETE, Type.BOOLEAN, 1, false),
      EQ_STRICT(TokenType.EQ_STRICT, Type.BOOLEAN, 2, true),
      EQ(TokenType.EQ, Type.BOOLEAN, 2, true),
      GE(TokenType.GE, Type.BOOLEAN, 2, true),
      GT(TokenType.GT, Type.BOOLEAN, 2, true),
      IN(TokenType.IN, Type.BOOLEAN, 2),
      INSTANCEOF(TokenType.INSTANCEOF, Type.BOOLEAN, 2),
      LE(TokenType.LE, Type.BOOLEAN, 2, true),
      LT(TokenType.LT, Type.BOOLEAN, 2, true),
      NE_STRICT(TokenType.NE_STRICT, Type.BOOLEAN, 2, true),
      NE(TokenType.NE, Type.BOOLEAN, 2, true),
      IS_UNDEFINED(TokenType.EQ_STRICT, Type.BOOLEAN, 2),
      IS_NOT_UNDEFINED(TokenType.NE_STRICT, Type.BOOLEAN, 2);

      private final TokenType tokenType;
      private final Type returnType;
      private final int arity;
      private final boolean canSpecialize;

      private Request() {
         this(TokenType.VOID, Type.OBJECT, 0);
      }

      private Request(TokenType tokenType, Type returnType, int arity) {
         this(tokenType, returnType, arity, false);
      }

      private Request(TokenType tokenType, Type returnType, int arity, boolean canSpecialize) {
         this.tokenType = tokenType;
         this.returnType = returnType;
         this.arity = arity;
         this.canSpecialize = canSpecialize;
      }

      public boolean canSpecialize() {
         return this.canSpecialize;
      }

      public int getArity() {
         return this.arity;
      }

      public Type getReturnType() {
         return this.returnType;
      }

      public TokenType getTokenType() {
         return this.tokenType;
      }

      public String nonStrictName() {
         switch(this) {
         case NE_STRICT:
            return NE.name();
         case EQ_STRICT:
            return EQ.name();
         default:
            return this.name();
         }
      }

      public static RuntimeNode.Request requestFor(Expression node) {
         switch(node.tokenType()) {
         case TYPEOF:
            return TYPEOF;
         case IN:
            return IN;
         case INSTANCEOF:
            return INSTANCEOF;
         case EQ_STRICT:
            return EQ_STRICT;
         case NE_STRICT:
            return NE_STRICT;
         case EQ:
            return EQ;
         case NE:
            return NE;
         case LT:
            return LT;
         case LE:
            return LE;
         case GT:
            return GT;
         case GE:
            return GE;
         default:
            assert false;

            return null;
         }
      }

      public static boolean isUndefinedCheck(RuntimeNode.Request request) {
         return request == IS_UNDEFINED || request == IS_NOT_UNDEFINED;
      }

      public static boolean isEQ(RuntimeNode.Request request) {
         return request == EQ || request == EQ_STRICT;
      }

      public static boolean isNE(RuntimeNode.Request request) {
         return request == NE || request == NE_STRICT;
      }

      public static boolean isStrict(RuntimeNode.Request request) {
         return request == EQ_STRICT || request == NE_STRICT;
      }

      public static RuntimeNode.Request reverse(RuntimeNode.Request request) {
         switch(request) {
         case NE_STRICT:
         case EQ_STRICT:
         case EQ:
         case NE:
            return request;
         case LE:
            return GE;
         case LT:
            return GT;
         case GE:
            return LE;
         case GT:
            return LT;
         default:
            return null;
         }
      }

      public static RuntimeNode.Request invert(RuntimeNode.Request request) {
         switch(request) {
         case NE_STRICT:
            return EQ_STRICT;
         case EQ_STRICT:
            return NE_STRICT;
         case EQ:
            return NE;
         case NE:
            return EQ;
         case LE:
            return GT;
         case LT:
            return GE;
         case GE:
            return LT;
         case GT:
            return LE;
         default:
            return null;
         }
      }

      public static boolean isComparison(RuntimeNode.Request request) {
         switch(request) {
         case NE_STRICT:
         case EQ_STRICT:
         case EQ:
         case NE:
         case LE:
         case LT:
         case GE:
         case GT:
         case IS_UNDEFINED:
         case IS_NOT_UNDEFINED:
            return true;
         default:
            return false;
         }
      }
   }
}
