package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class LabelNode extends LexicalContextStatement implements JoinPredecessor {
   private static final long serialVersionUID = 1L;
   private final String labelName;
   private final Block body;
   private final LocalVariableConversion localVariableConversion;

   public LabelNode(int lineNumber, long token, int finish, String labelName, Block body) {
      super(lineNumber, token, finish);
      this.labelName = labelName;
      this.body = body;
      this.localVariableConversion = null;
   }

   private LabelNode(LabelNode labelNode, String labelName, Block body, LocalVariableConversion localVariableConversion) {
      super(labelNode);
      this.labelName = labelName;
      this.body = body;
      this.localVariableConversion = localVariableConversion;
   }

   public boolean isTerminal() {
      return this.body.isTerminal();
   }

   public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterLabelNode(this) ? visitor.leaveLabelNode(this.setBody(lc, (Block)this.body.accept(visitor))) : this);
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append(this.labelName).append(':');
   }

   public Block getBody() {
      return this.body;
   }

   public LabelNode setBody(LexicalContext lc, Block body) {
      return this.body == body ? this : (LabelNode)Node.replaceInLexicalContext(lc, this, new LabelNode(this, this.labelName, body, this.localVariableConversion));
   }

   public String getLabelName() {
      return this.labelName;
   }

   public LocalVariableConversion getLocalVariableConversion() {
      return this.localVariableConversion;
   }

   public LabelNode setLocalVariableConversion(LexicalContext lc, LocalVariableConversion localVariableConversion) {
      return this.localVariableConversion == localVariableConversion ? this : (LabelNode)Node.replaceInLexicalContext(lc, this, new LabelNode(this, this.labelName, this.body, localVariableConversion));
   }
}
