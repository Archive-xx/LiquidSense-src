package jdk.nashorn.internal.ir;

import jdk.nashorn.internal.codegen.Label;

public interface BreakableNode extends LexicalContextNode, JoinPredecessor, Labels {
   Node ensureUniqueLabels(LexicalContext var1);

   boolean isBreakableWithoutLabel();

   Label getBreakLabel();
}
