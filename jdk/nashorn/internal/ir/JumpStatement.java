package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.Label;

public abstract class JumpStatement extends Statement implements JoinPredecessor {
   private static final long serialVersionUID = 1L;
   private final String labelName;
   private final LocalVariableConversion conversion;

   protected JumpStatement(int lineNumber, long token, int finish, String labelName) {
      super(lineNumber, token, finish);
      this.labelName = labelName;
      this.conversion = null;
   }

   protected JumpStatement(JumpStatement jumpStatement, LocalVariableConversion conversion) {
      super(jumpStatement);
      this.labelName = jumpStatement.labelName;
      this.conversion = conversion;
   }

   public boolean hasGoto() {
      return true;
   }

   public String getLabelName() {
      return this.labelName;
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append(this.getStatementName());
      if (this.labelName != null) {
         sb.append(' ').append(this.labelName);
      }

   }

   abstract String getStatementName();

   public abstract BreakableNode getTarget(LexicalContext var1);

   abstract Label getTargetLabel(BreakableNode var1);

   public Label getTargetLabel(LexicalContext lc) {
      return this.getTargetLabel(this.getTarget(lc));
   }

   public LexicalContextNode getPopScopeLimit(LexicalContext lc) {
      return this.getTarget(lc);
   }

   public JumpStatement setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
      return this.conversion == conversion ? this : this.createNewJumpStatement(conversion);
   }

   abstract JumpStatement createNewJumpStatement(LocalVariableConversion var1);

   public LocalVariableConversion getLocalVariableConversion() {
      return this.conversion;
   }
}
