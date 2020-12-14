package jdk.nashorn.internal.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;

public abstract class LoopNode extends BreakableStatement {
   private static final long serialVersionUID = 1L;
   protected final Label continueLabel;
   protected final JoinPredecessorExpression test;
   protected final Block body;
   protected final boolean controlFlowEscapes;

   protected LoopNode(int lineNumber, long token, int finish, Block body, boolean controlFlowEscapes) {
      super(lineNumber, token, finish, new Label("while_break"));
      this.continueLabel = new Label("while_continue");
      this.test = null;
      this.body = body;
      this.controlFlowEscapes = controlFlowEscapes;
   }

   protected LoopNode(LoopNode loopNode, JoinPredecessorExpression test, Block body, boolean controlFlowEscapes, LocalVariableConversion conversion) {
      super(loopNode, conversion);
      this.continueLabel = new Label(loopNode.continueLabel);
      this.test = test;
      this.body = body;
      this.controlFlowEscapes = controlFlowEscapes;
   }

   public abstract Node ensureUniqueLabels(LexicalContext var1);

   public boolean controlFlowEscapes() {
      return this.controlFlowEscapes;
   }

   public boolean isTerminal() {
      if (!this.mustEnter()) {
         return false;
      } else if (this.controlFlowEscapes) {
         return false;
      } else if (this.body.isTerminal()) {
         return true;
      } else {
         return this.test == null;
      }
   }

   public abstract boolean mustEnter();

   public Label getContinueLabel() {
      return this.continueLabel;
   }

   public List<Label> getLabels() {
      return Collections.unmodifiableList(Arrays.asList(this.breakLabel, this.continueLabel));
   }

   public boolean isLoop() {
      return true;
   }

   public abstract Block getBody();

   public abstract LoopNode setBody(LexicalContext var1, Block var2);

   public final JoinPredecessorExpression getTest() {
      return this.test;
   }

   public abstract LoopNode setTest(LexicalContext var1, JoinPredecessorExpression var2);

   public abstract LoopNode setControlFlowEscapes(LexicalContext var1, boolean var2);

   public abstract boolean hasPerIterationScope();
}
