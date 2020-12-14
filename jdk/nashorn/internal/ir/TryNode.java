package jdk.nashorn.internal.ir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.ir.annotations.Immutable;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;

@Immutable
public final class TryNode extends LexicalContextStatement implements JoinPredecessor {
   private static final long serialVersionUID = 1L;
   private final Block body;
   private final List<Block> catchBlocks;
   private final Block finallyBody;
   private final List<Block> inlinedFinallies;
   private final Symbol exception;
   private final LocalVariableConversion conversion;

   public TryNode(int lineNumber, long token, int finish, Block body, List<Block> catchBlocks, Block finallyBody) {
      super(lineNumber, token, finish);
      this.body = body;
      this.catchBlocks = catchBlocks;
      this.finallyBody = finallyBody;
      this.conversion = null;
      this.inlinedFinallies = Collections.emptyList();
      this.exception = null;
   }

   private TryNode(TryNode tryNode, Block body, List<Block> catchBlocks, Block finallyBody, LocalVariableConversion conversion, List<Block> inlinedFinallies, Symbol exception) {
      super(tryNode);
      this.body = body;
      this.catchBlocks = catchBlocks;
      this.finallyBody = finallyBody;
      this.conversion = conversion;
      this.inlinedFinallies = inlinedFinallies;
      this.exception = exception;
   }

   public Node ensureUniqueLabels(LexicalContext lc) {
      return new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception);
   }

   public boolean isTerminal() {
      if (this.body.isTerminal()) {
         Iterator var1 = this.getCatchBlocks().iterator();

         Block catchBlock;
         do {
            if (!var1.hasNext()) {
               return true;
            }

            catchBlock = (Block)var1.next();
         } while(catchBlock.isTerminal());

         return false;
      } else {
         return false;
      }
   }

   public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterTryNode(this)) {
         Block newFinallyBody = this.finallyBody == null ? null : (Block)this.finallyBody.accept(visitor);
         Block newBody = (Block)this.body.accept(visitor);
         return visitor.leaveTryNode(this.setBody(lc, newBody).setFinallyBody(lc, newFinallyBody).setCatchBlocks(lc, Node.accept(visitor, this.catchBlocks)).setInlinedFinallies(lc, Node.accept(visitor, this.inlinedFinallies)));
      } else {
         return this;
      }
   }

   public void toString(StringBuilder sb, boolean printType) {
      sb.append("try ");
   }

   public Block getBody() {
      return this.body;
   }

   public TryNode setBody(LexicalContext lc, Block body) {
      return this.body == body ? this : (TryNode)Node.replaceInLexicalContext(lc, this, new TryNode(this, body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception));
   }

   public List<CatchNode> getCatches() {
      List<CatchNode> catches = new ArrayList(this.catchBlocks.size());
      Iterator var2 = this.catchBlocks.iterator();

      while(var2.hasNext()) {
         Block catchBlock = (Block)var2.next();
         catches.add(getCatchNodeFromBlock(catchBlock));
      }

      return Collections.unmodifiableList(catches);
   }

   private static CatchNode getCatchNodeFromBlock(Block catchBlock) {
      return (CatchNode)catchBlock.getStatements().get(0);
   }

   public List<Block> getCatchBlocks() {
      return Collections.unmodifiableList(this.catchBlocks);
   }

   public TryNode setCatchBlocks(LexicalContext lc, List<Block> catchBlocks) {
      return this.catchBlocks == catchBlocks ? this : (TryNode)Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, this.exception));
   }

   public Symbol getException() {
      return this.exception;
   }

   public TryNode setException(LexicalContext lc, Symbol exception) {
      return this.exception == exception ? this : (TryNode)Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, this.inlinedFinallies, exception));
   }

   public Block getFinallyBody() {
      return this.finallyBody;
   }

   public Block getInlinedFinally(String labelName) {
      Iterator var2 = this.inlinedFinallies.iterator();

      LabelNode labelNode;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         Block inlinedFinally = (Block)var2.next();
         labelNode = getInlinedFinallyLabelNode(inlinedFinally);
      } while(!labelNode.getLabelName().equals(labelName));

      return labelNode.getBody();
   }

   private static LabelNode getInlinedFinallyLabelNode(Block inlinedFinally) {
      return (LabelNode)inlinedFinally.getStatements().get(0);
   }

   public static Block getLabelledInlinedFinallyBlock(Block inlinedFinally) {
      return getInlinedFinallyLabelNode(inlinedFinally).getBody();
   }

   public List<Block> getInlinedFinallies() {
      return Collections.unmodifiableList(this.inlinedFinallies);
   }

   public TryNode setFinallyBody(LexicalContext lc, Block finallyBody) {
      return this.finallyBody == finallyBody ? this : (TryNode)Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, this.catchBlocks, finallyBody, this.conversion, this.inlinedFinallies, this.exception));
   }

   public TryNode setInlinedFinallies(LexicalContext lc, List<Block> inlinedFinallies) {
      if (this.inlinedFinallies == inlinedFinallies) {
         return this;
      } else {
         assert checkInlinedFinallies(inlinedFinallies);

         return (TryNode)Node.replaceInLexicalContext(lc, this, new TryNode(this, this.body, this.catchBlocks, this.finallyBody, this.conversion, inlinedFinallies, this.exception));
      }
   }

   private static boolean checkInlinedFinallies(List<Block> inlinedFinallies) {
      if (!inlinedFinallies.isEmpty()) {
         Set<String> labels = new HashSet();
         Iterator var2 = inlinedFinallies.iterator();

         while(var2.hasNext()) {
            Block inlinedFinally = (Block)var2.next();
            List<Statement> stmts = inlinedFinally.getStatements();

            assert stmts.size() == 1;

            LabelNode ln = getInlinedFinallyLabelNode(inlinedFinally);

            assert labels.add(ln.getLabelName());
         }
      }

      return true;
   }

   public JoinPredecessor setLocalVariableConversion(LexicalContext lc, LocalVariableConversion conversion) {
      return this.conversion == conversion ? this : new TryNode(this, this.body, this.catchBlocks, this.finallyBody, conversion, this.inlinedFinallies, this.exception);
   }

   public LocalVariableConversion getLocalVariableConversion() {
      return this.conversion;
   }
}
