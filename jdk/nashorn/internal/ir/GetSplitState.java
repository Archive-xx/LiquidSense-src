package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.runtime.Scope;

public final class GetSplitState extends Expression {
   private static final long serialVersionUID = 1L;
   public static final GetSplitState INSTANCE = new GetSplitState();

   private GetSplitState() {
      super(0L, 0);
   }

   public Type getType() {
      return Type.INT;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterGetSplitState(this) ? visitor.leaveGetSplitState(this) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      if (printType) {
         sb.append("{I}");
      }

      sb.append(CompilerConstants.SCOPE.symbolName()).append('.').append(Scope.GET_SPLIT_STATE.name()).append("()");
   }

   private Object readResolve() {
      return INSTANCE;
   }
}
