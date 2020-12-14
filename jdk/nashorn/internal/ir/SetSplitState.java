package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.runtime.Scope;

public final class SetSplitState extends Statement {
   private static final long serialVersionUID = 1L;
   private final int state;

   public SetSplitState(int state, int lineNumber) {
      super(lineNumber, 0L, 0);
      this.state = state;
   }

   public int getState() {
      return this.state;
   }

   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterSetSplitState(this) ? visitor.leaveSetSplitState(this) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append(CompilerConstants.SCOPE.symbolName()).append('.').append(Scope.SET_SPLIT_STATE.name()).append('(').append(this.state).append(");");
   }
}
