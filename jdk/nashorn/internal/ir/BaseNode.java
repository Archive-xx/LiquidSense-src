package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.parser.TokenType;

@Immutable
public abstract class BaseNode extends Expression implements FunctionCall, Optimistic {
   private static final long serialVersionUID = 1L;
   protected final Expression base;
   private final boolean isFunction;
   protected final Type type;
   protected final int programPoint;

   public BaseNode(long token, int finish, Expression base, boolean isFunction) {
      super(token, base.getStart(), finish);
      this.base = base;
      this.isFunction = isFunction;
      this.type = null;
      this.programPoint = -1;
   }

   protected BaseNode(BaseNode baseNode, Expression base, boolean isFunction, Type callSiteType, int programPoint) {
      super(baseNode);
      this.base = base;
      this.isFunction = isFunction;
      this.type = callSiteType;
      this.programPoint = programPoint;
   }

   public Expression getBase() {
      return this.base;
   }

   public boolean isFunction() {
      return this.isFunction;
   }

   public Type getType() {
      return this.type == null ? this.getMostPessimisticType() : this.type;
   }

   public int getProgramPoint() {
      return this.programPoint;
   }

   public Type getMostOptimisticType() {
      return Type.INT;
   }

   public Type getMostPessimisticType() {
      return Type.OBJECT;
   }

   public boolean canBeOptimistic() {
      return true;
   }

   public boolean isIndex() {
      return this.isTokenType(TokenType.LBRACKET);
   }

   public abstract BaseNode setIsFunction();
}
