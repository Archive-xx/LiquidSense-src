package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class VarNode extends Statement implements Assignment<IdentNode> {
   private static final long serialVersionUID = 1L;
   private final IdentNode name;
   private final Expression init;
   private final int flags;
   public static final int IS_LET = 1;
   public static final int IS_CONST = 2;
   public static final int IS_LAST_FUNCTION_DECLARATION = 4;

   public VarNode(int lineNumber, long token, int finish, IdentNode name, Expression init) {
      this(lineNumber, token, finish, name, init, 0);
   }

   private VarNode(VarNode varNode, IdentNode name, Expression init, int flags) {
      super(varNode);
      this.name = init == null ? name : name.setIsInitializedHere();
      this.init = init;
      this.flags = flags;
   }

   public VarNode(int lineNumber, long token, int finish, IdentNode name, Expression init, int flags) {
      super(lineNumber, token, finish);
      this.name = init == null ? name : name.setIsInitializedHere();
      this.init = init;
      this.flags = flags;
   }

   public boolean isAssignment() {
      return this.hasInit();
   }

   public IdentNode getAssignmentDest() {
      return this.isAssignment() ? this.name : null;
   }

   public VarNode setAssignmentDest(IdentNode n) {
      return this.setName(n);
   }

   public Expression getAssignmentSource() {
      return this.isAssignment() ? this.getInit() : null;
   }

   public boolean isBlockScoped() {
      return this.getFlag(1) || this.getFlag(2);
   }

   public boolean isLet() {
      return this.getFlag(1);
   }

   public boolean isConst() {
      return this.getFlag(2);
   }

   public int getSymbolFlags() {
      if (this.isLet()) {
         return 18;
      } else {
         return this.isConst() ? 34 : 2;
      }
   }

   public boolean hasInit() {
      return this.init != null;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterVarNode(this)) {
         Expression newInit = this.init == null ? null : (Expression)this.init.accept(visitor);
         IdentNode newName = (IdentNode)this.name.accept(visitor);
         VarNode newThis;
         if (this.name == newName && this.init == newInit) {
            newThis = this;
         } else {
            newThis = new VarNode(this, newName, newInit, this.flags);
         }

         return visitor.leaveVarNode(newThis);
      } else {
         return this;
      }
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append(this.tokenType().getName()).append(' ');
      this.name.toString(sb, printType);
      if (this.init != null) {
         sb.append(" = ");
         this.init.toString(sb, printType);
      }

   }

   public Expression getInit() {
      return this.init;
   }

   public VarNode setInit(Expression init) {
      return this.init == init ? this : new VarNode(this, this.name, init, this.flags);
   }

   public IdentNode getName() {
      return this.name;
   }

   public VarNode setName(IdentNode name) {
      return this.name == name ? this : new VarNode(this, name, this.init, this.flags);
   }

   private VarNode setFlags(int flags) {
      return this.flags == flags ? this : new VarNode(this, this.name, this.init, flags);
   }

   public boolean getFlag(int flag) {
      return (this.flags & flag) == flag;
   }

   public VarNode setFlag(int flag) {
      return this.setFlags(this.flags | flag);
   }

   public boolean isFunctionDeclaration() {
      return this.init instanceof FunctionNode && ((FunctionNode)this.init).isDeclared();
   }
}
