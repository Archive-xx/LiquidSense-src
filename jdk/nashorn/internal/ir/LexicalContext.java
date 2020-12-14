package jdk.nashorn.internal.ir;

import java.io.File;
import java.util.Iterator;
import java.util.NoSuchElementException;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.Source;

public class LexicalContext {
   private LexicalContextNode[] stack = new LexicalContextNode[16];
   private int[] flags = new int[16];
   private int sp;

   public void setFlag(LexicalContextNode node, int flag) {
      if (flag != 0) {
         assert flag != 1 || !(node instanceof Block);

         for(int i = this.sp - 1; i >= 0; --i) {
            if (this.stack[i] == node) {
               int[] var10000 = this.flags;
               var10000[i] |= flag;
               return;
            }
         }
      }

      assert false;

   }

   public void setBlockNeedsScope(Block block) {
      for(int i = this.sp - 1; i >= 0; --i) {
         if (this.stack[i] == block) {
            int[] var10000 = this.flags;
            var10000[i] |= 1;

            for(int j = i - 1; j >= 0; --j) {
               if (this.stack[j] instanceof FunctionNode) {
                  var10000 = this.flags;
                  var10000[j] |= 128;
                  return;
               }
            }
         }
      }

      assert false;

   }

   public int getFlags(LexicalContextNode node) {
      for(int i = this.sp - 1; i >= 0; --i) {
         if (this.stack[i] == node) {
            return this.flags[i];
         }
      }

      throw new AssertionError("flag node not on context stack");
   }

   public Block getFunctionBody(FunctionNode functionNode) {
      for(int i = this.sp - 1; i >= 0; --i) {
         if (this.stack[i] == functionNode) {
            return (Block)this.stack[i + 1];
         }
      }

      throw new AssertionError(functionNode.getName() + " not on context stack");
   }

   public Iterator<LexicalContextNode> getAllNodes() {
      return new LexicalContext.NodeIterator(LexicalContextNode.class);
   }

   public FunctionNode getOutermostFunction() {
      return (FunctionNode)this.stack[0];
   }

   public <T extends LexicalContextNode> T push(T node) {
      assert !this.contains(node);

      if (this.sp == this.stack.length) {
         LexicalContextNode[] newStack = new LexicalContextNode[this.sp * 2];
         System.arraycopy(this.stack, 0, newStack, 0, this.sp);
         this.stack = newStack;
         int[] newFlags = new int[this.sp * 2];
         System.arraycopy(this.flags, 0, newFlags, 0, this.sp);
         this.flags = newFlags;
      }

      this.stack[this.sp] = node;
      this.flags[this.sp] = 0;
      ++this.sp;
      return node;
   }

   public boolean isEmpty() {
      return this.sp == 0;
   }

   public int size() {
      return this.sp;
   }

   public <T extends Node> T pop(T node) {
      --this.sp;
      LexicalContextNode popped = this.stack[this.sp];
      this.stack[this.sp] = null;
      return popped instanceof Flags ? (Node)((Flags)popped).setFlag(this, this.flags[this.sp]) : (Node)popped;
   }

   public <T extends LexicalContextNode & Flags<T>> T applyTopFlags(T node) {
      assert node == this.peek();

      return ((Flags)node).setFlag(this, this.flags[this.sp - 1]);
   }

   public LexicalContextNode peek() {
      return this.stack[this.sp - 1];
   }

   public boolean contains(LexicalContextNode node) {
      for(int i = 0; i < this.sp; ++i) {
         if (this.stack[i] == node) {
            return true;
         }
      }

      return false;
   }

   public LexicalContextNode replace(LexicalContextNode oldNode, LexicalContextNode newNode) {
      for(int i = this.sp - 1; i >= 0; --i) {
         if (this.stack[i] == oldNode) {
            assert i == this.sp - 1 : "violation of contract - we always expect to find the replacement node on top of the lexical context stack: " + newNode + " has " + this.stack[i + 1].getClass() + " above it";

            this.stack[i] = newNode;
            break;
         }
      }

      return newNode;
   }

   public Iterator<Block> getBlocks() {
      return new LexicalContext.NodeIterator(Block.class);
   }

   public Iterator<FunctionNode> getFunctions() {
      return new LexicalContext.NodeIterator(FunctionNode.class);
   }

   public Block getParentBlock() {
      Iterator<Block> iter = new LexicalContext.NodeIterator(Block.class, this.getCurrentFunction());
      iter.next();
      return iter.hasNext() ? (Block)iter.next() : null;
   }

   public LabelNode getCurrentBlockLabelNode() {
      assert this.stack[this.sp - 1] instanceof Block;

      if (this.sp < 2) {
         return null;
      } else {
         LexicalContextNode parent = this.stack[this.sp - 2];
         return parent instanceof LabelNode ? (LabelNode)parent : null;
      }
   }

   public Iterator<Block> getAncestorBlocks(Block block) {
      Iterator iter = this.getBlocks();

      Block b;
      do {
         if (!iter.hasNext()) {
            throw new AssertionError("Block is not on the current lexical context stack");
         }

         b = (Block)iter.next();
      } while(block != b);

      return iter;
   }

   public Iterator<Block> getBlocks(final Block block) {
      final Iterator<Block> iter = this.getAncestorBlocks(block);
      return new Iterator<Block>() {
         boolean blockReturned = false;

         public boolean hasNext() {
            return iter.hasNext() || !this.blockReturned;
         }

         public Block next() {
            if (this.blockReturned) {
               return (Block)iter.next();
            } else {
               this.blockReturned = true;
               return block;
            }
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public FunctionNode getFunction(Block block) {
      LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(LexicalContextNode.class);

      while(true) {
         LexicalContextNode next;
         do {
            if (!iter.hasNext()) {
               assert false;

               return null;
            }

            next = (LexicalContextNode)iter.next();
         } while(next != block);

         while(iter.hasNext()) {
            LexicalContextNode next2 = (LexicalContextNode)iter.next();
            if (next2 instanceof FunctionNode) {
               return (FunctionNode)next2;
            }
         }
      }
   }

   public Block getCurrentBlock() {
      return (Block)this.getBlocks().next();
   }

   public FunctionNode getCurrentFunction() {
      for(int i = this.sp - 1; i >= 0; --i) {
         if (this.stack[i] instanceof FunctionNode) {
            return (FunctionNode)this.stack[i];
         }
      }

      return null;
   }

   public Block getDefiningBlock(Symbol symbol) {
      String name = symbol.getName();
      Iterator it = this.getBlocks();

      Block next;
      do {
         if (!it.hasNext()) {
            throw new AssertionError("Couldn't find symbol " + name + " in the context");
         }

         next = (Block)it.next();
      } while(next.getExistingSymbol(name) != symbol);

      return next;
   }

   public FunctionNode getDefiningFunction(Symbol symbol) {
      String name = symbol.getName();
      LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(LexicalContextNode.class);

      LexicalContextNode next;
      do {
         if (!iter.hasNext()) {
            throw new AssertionError("Couldn't find symbol " + name + " in the context");
         }

         next = (LexicalContextNode)iter.next();
      } while(!(next instanceof Block) || ((Block)next).getExistingSymbol(name) != symbol);

      LexicalContextNode next2;
      do {
         if (!iter.hasNext()) {
            throw new AssertionError("Defining block for symbol " + name + " has no function in the context");
         }

         next2 = (LexicalContextNode)iter.next();
      } while(!(next2 instanceof FunctionNode));

      return (FunctionNode)next2;
   }

   public boolean isFunctionBody() {
      return this.getParentBlock() == null;
   }

   public boolean isSplitBody() {
      return this.sp >= 2 && this.stack[this.sp - 1] instanceof Block && this.stack[this.sp - 2] instanceof SplitNode;
   }

   public FunctionNode getParentFunction(FunctionNode functionNode) {
      LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(FunctionNode.class);

      FunctionNode next;
      do {
         if (!iter.hasNext()) {
            assert false;

            return null;
         }

         next = (FunctionNode)iter.next();
      } while(next != functionNode);

      return iter.hasNext() ? (FunctionNode)iter.next() : null;
   }

   public int getScopeNestingLevelTo(LexicalContextNode until) {
      assert until != null;

      int n = 0;
      Iterator iter = this.getAllNodes();

      while(iter.hasNext()) {
         LexicalContextNode node = (LexicalContextNode)iter.next();
         if (node == until) {
            break;
         }

         assert !(node instanceof FunctionNode);

         if (node instanceof WithNode || node instanceof Block && ((Block)node).needsScope()) {
            ++n;
         }
      }

      return n;
   }

   private BreakableNode getBreakable() {
      LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(BreakableNode.class, this.getCurrentFunction());

      BreakableNode next;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         next = (BreakableNode)iter.next();
      } while(!next.isBreakableWithoutLabel());

      return next;
   }

   public boolean inLoop() {
      return this.getCurrentLoop() != null;
   }

   public LoopNode getCurrentLoop() {
      Iterator<LoopNode> iter = new LexicalContext.NodeIterator(LoopNode.class, this.getCurrentFunction());
      return iter.hasNext() ? (LoopNode)iter.next() : null;
   }

   public BreakableNode getBreakable(String labelName) {
      if (labelName == null) {
         return this.getBreakable();
      } else {
         LabelNode foundLabel = this.findLabel(labelName);
         if (foundLabel == null) {
            return null;
         } else {
            BreakableNode breakable = null;

            for(LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(BreakableNode.class, foundLabel); iter.hasNext(); breakable = (BreakableNode)iter.next()) {
            }

            return breakable;
         }
      }
   }

   private LoopNode getContinueTo() {
      return this.getCurrentLoop();
   }

   public LoopNode getContinueTo(String labelName) {
      if (labelName == null) {
         return this.getContinueTo();
      } else {
         LabelNode foundLabel = this.findLabel(labelName);
         if (foundLabel == null) {
            return null;
         } else {
            LoopNode loop = null;

            for(LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(LoopNode.class, foundLabel); iter.hasNext(); loop = (LoopNode)iter.next()) {
            }

            return loop;
         }
      }
   }

   public Block getInlinedFinally(String labelName) {
      LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(TryNode.class);

      Block inlinedFinally;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         inlinedFinally = ((TryNode)iter.next()).getInlinedFinally(labelName);
      } while(inlinedFinally == null);

      return inlinedFinally;
   }

   public TryNode getTryNodeForInlinedFinally(String labelName) {
      LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(TryNode.class);

      TryNode tryNode;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         tryNode = (TryNode)iter.next();
      } while(tryNode.getInlinedFinally(labelName) == null);

      return tryNode;
   }

   public LabelNode findLabel(String name) {
      LexicalContext.NodeIterator iter = new LexicalContext.NodeIterator(LabelNode.class, this.getCurrentFunction());

      LabelNode next;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         next = (LabelNode)iter.next();
      } while(!next.getLabelName().equals(name));

      return next;
   }

   public boolean isExternalTarget(SplitNode splitNode, BreakableNode target) {
      int i = this.sp;

      while(i-- > 0) {
         LexicalContextNode next = this.stack[i];
         if (next == splitNode) {
            return true;
         }

         if (next == target) {
            return false;
         }

         if (next instanceof TryNode) {
            Iterator var5 = ((TryNode)next).getInlinedFinallies().iterator();

            while(var5.hasNext()) {
               Block inlinedFinally = (Block)var5.next();
               if (TryNode.getLabelledInlinedFinallyBlock(inlinedFinally) == target) {
                  return false;
               }
            }
         }
      }

      throw new AssertionError(target + " was expected in lexical context " + this + " but wasn't");
   }

   public boolean inUnprotectedSwitchContext() {
      for(int i = this.sp; i > 0; --i) {
         LexicalContextNode next = this.stack[i];
         if (next instanceof Block) {
            return this.stack[i - 1] instanceof SwitchNode;
         }
      }

      return false;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("[ ");

      for(int i = 0; i < this.sp; ++i) {
         Object node = this.stack[i];
         sb.append(node.getClass().getSimpleName());
         sb.append('@');
         sb.append(Debug.id(node));
         sb.append(':');
         if (node instanceof FunctionNode) {
            FunctionNode fn = (FunctionNode)node;
            Source source = fn.getSource();
            String src = source.toString();
            if (src.contains(File.pathSeparator)) {
               src = src.substring(src.lastIndexOf(File.pathSeparator));
            }

            src = src + ' ';
            src = src + fn.getLineNumber();
            sb.append(src);
         }

         sb.append(' ');
      }

      sb.append(" ==> ]");
      return sb.toString();
   }

   private class NodeIterator<T extends LexicalContextNode> implements Iterator<T> {
      private int index;
      private T next;
      private final Class<T> clazz;
      private LexicalContextNode until;

      NodeIterator(Class<T> clazz) {
         this(clazz, (LexicalContextNode)null);
      }

      NodeIterator(Class<T> clazz, LexicalContextNode until) {
         this.index = LexicalContext.this.sp - 1;
         this.clazz = clazz;
         this.until = until;
         this.next = this.findNext();
      }

      public boolean hasNext() {
         return this.next != null;
      }

      public T next() {
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            T lnext = this.next;
            this.next = this.findNext();
            return lnext;
         }
      }

      private T findNext() {
         for(int i = this.index; i >= 0; --i) {
            Object node = LexicalContext.this.stack[i];
            if (node == this.until) {
               return null;
            }

            if (this.clazz.isAssignableFrom(node.getClass())) {
               this.index = i - 1;
               return (LexicalContextNode)node;
            }
         }

         return null;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
