package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.SplitNode;
import jdk.nashorn.internal.ir.Splittable;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(
   name = "splitter"
)
final class Splitter extends SimpleNodeVisitor implements Loggable {
   private final Compiler compiler;
   private final FunctionNode outermost;
   private final CompileUnit outermostCompileUnit;
   private final Map<Node, Long> weightCache = new HashMap();
   public static final long SPLIT_THRESHOLD = (long)Options.getIntProperty("nashorn.compiler.splitter.threshold", 32768);
   private final DebugLogger log;

   public Splitter(Compiler compiler, FunctionNode functionNode, CompileUnit outermostCompileUnit) {
      this.compiler = compiler;
      this.outermost = functionNode;
      this.outermostCompileUnit = outermostCompileUnit;
      this.log = this.initLogger(compiler.getContext());
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   FunctionNode split(FunctionNode fn, boolean top) {
      FunctionNode functionNode = fn;
      this.log.fine("Initiating split of '", fn.getName(), "'");
      long weight = WeighNodes.weigh(fn);

      assert this.lc.isEmpty() : "LexicalContext not empty";

      if (weight >= SPLIT_THRESHOLD) {
         this.log.info("Splitting '", fn.getName(), "' as its weight ", weight, " exceeds split threshold ", SPLIT_THRESHOLD);
         functionNode = (FunctionNode)fn.accept(this);
         if (functionNode.isSplit()) {
            weight = WeighNodes.weigh(functionNode, this.weightCache);
            functionNode = functionNode.setBody((LexicalContext)null, functionNode.getBody().setNeedsScope((LexicalContext)null));
         }

         if (weight >= SPLIT_THRESHOLD) {
            functionNode = functionNode.setBody((LexicalContext)null, this.splitBlock(functionNode.getBody(), functionNode));
            functionNode = functionNode.setFlag((LexicalContext)null, 16);
            weight = WeighNodes.weigh(functionNode.getBody(), this.weightCache);
         }
      }

      assert functionNode.getCompileUnit() == null : "compile unit already set for " + functionNode.getName();

      if (top) {
         assert this.outermostCompileUnit != null : "outermost compile unit is null";

         functionNode = functionNode.setCompileUnit((LexicalContext)null, this.outermostCompileUnit);
         this.outermostCompileUnit.addWeight(weight + 40L);
      } else {
         functionNode = functionNode.setCompileUnit((LexicalContext)null, this.findUnit(weight));
      }

      Block body = functionNode.getBody();
      final List<FunctionNode> dc = directChildren(functionNode);
      Block newBody = (Block)body.accept(new SimpleNodeVisitor() {
         public boolean enterFunctionNode(FunctionNode nestedFunction) {
            return dc.contains(nestedFunction);
         }

         public Node leaveFunctionNode(FunctionNode nestedFunction) {
            FunctionNode split = (new Splitter(Splitter.this.compiler, nestedFunction, Splitter.this.outermostCompileUnit)).split(nestedFunction, false);
            this.lc.replace(nestedFunction, split);
            return split;
         }
      });
      functionNode = functionNode.setBody((LexicalContext)null, newBody);

      assert functionNode.getCompileUnit() != null;

      return functionNode;
   }

   private static List<FunctionNode> directChildren(final FunctionNode functionNode) {
      final List<FunctionNode> dc = new ArrayList();
      functionNode.accept(new SimpleNodeVisitor() {
         public boolean enterFunctionNode(FunctionNode child) {
            if (child == functionNode) {
               return true;
            } else {
               if (this.lc.getParentFunction(child) == functionNode) {
                  dc.add(child);
               }

               return false;
            }
         }
      });
      return dc;
   }

   protected CompileUnit findUnit(long weight) {
      return this.compiler.findUnit(weight);
   }

   private Block splitBlock(Block block, FunctionNode function) {
      List<Statement> splits = new ArrayList();
      List<Statement> statements = new ArrayList();
      long statementsWeight = 0L;
      Iterator var7 = block.getStatements().iterator();

      while(var7.hasNext()) {
         Statement statement = (Statement)var7.next();
         long weight = WeighNodes.weigh(statement, this.weightCache);
         if ((statementsWeight + weight >= SPLIT_THRESHOLD || statement.isTerminal()) && !statements.isEmpty()) {
            splits.add(this.createBlockSplitNode(block, function, statements, statementsWeight));
            statements = new ArrayList();
            statementsWeight = 0L;
         }

         if (statement.isTerminal()) {
            splits.add(statement);
         } else {
            statements.add(statement);
            statementsWeight += weight;
         }
      }

      if (!statements.isEmpty()) {
         splits.add(this.createBlockSplitNode(block, function, statements, statementsWeight));
      }

      return block.setStatements(this.lc, splits);
   }

   private SplitNode createBlockSplitNode(Block parent, FunctionNode function, List<Statement> statements, long weight) {
      long token = parent.getToken();
      int finish = parent.getFinish();
      String name = function.uniqueName(CompilerConstants.SPLIT_PREFIX.symbolName());
      Block newBlock = new Block(token, finish, statements);
      return new SplitNode(name, newBlock, this.compiler.findUnit(weight + 40L));
   }

   public boolean enterBlock(Block block) {
      if (block.isCatchBlock()) {
         return false;
      } else {
         long weight = WeighNodes.weigh(block, this.weightCache);
         if (weight < SPLIT_THRESHOLD) {
            this.weightCache.put(block, weight);
            return false;
         } else {
            return true;
         }
      }
   }

   public Node leaveBlock(Block block) {
      assert !block.isCatchBlock();

      Block newBlock = block;
      long weight = WeighNodes.weigh(block, this.weightCache);
      if (weight >= SPLIT_THRESHOLD) {
         FunctionNode currentFunction = this.lc.getCurrentFunction();
         newBlock = this.splitBlock(block, currentFunction);
         weight = WeighNodes.weigh(newBlock, this.weightCache);
         this.lc.setFlag(currentFunction, 16);
      }

      this.weightCache.put(newBlock, weight);
      return newBlock;
   }

   public Node leaveLiteralNode(LiteralNode literal) {
      long weight = WeighNodes.weigh(literal);
      if (weight < SPLIT_THRESHOLD) {
         return literal;
      } else {
         FunctionNode functionNode = this.lc.getCurrentFunction();
         this.lc.setFlag(functionNode, 16);
         if (literal instanceof LiteralNode.ArrayLiteralNode) {
            LiteralNode.ArrayLiteralNode arrayLiteralNode = (LiteralNode.ArrayLiteralNode)literal;
            Node[] value = (Node[])arrayLiteralNode.getValue();
            int[] postsets = arrayLiteralNode.getPostsets();
            List<Splittable.SplitRange> ranges = new ArrayList();
            long totalWeight = 0L;
            int lo = 0;

            for(int i = 0; i < postsets.length; ++i) {
               int postset = postsets[i];
               Node element = value[postset];
               weight = WeighNodes.weigh(element);
               totalWeight += 2L + weight;
               if (totalWeight >= SPLIT_THRESHOLD) {
                  CompileUnit unit = this.compiler.findUnit(totalWeight - weight);
                  ranges.add(new Splittable.SplitRange(unit, lo, i));
                  lo = i;
                  totalWeight = weight;
               }
            }

            if (lo != postsets.length) {
               CompileUnit unit = this.compiler.findUnit(totalWeight);
               ranges.add(new Splittable.SplitRange(unit, lo, postsets.length));
            }

            return arrayLiteralNode.setSplitRanges(this.lc, ranges);
         } else {
            return literal;
         }
      }
   }

   public Node leaveObjectNode(ObjectNode objectNode) {
      long weight = WeighNodes.weigh(objectNode);
      if (weight < SPLIT_THRESHOLD) {
         return objectNode;
      } else {
         FunctionNode functionNode = this.lc.getCurrentFunction();
         this.lc.setFlag(functionNode, 16);
         List<Splittable.SplitRange> ranges = new ArrayList();
         List<PropertyNode> properties = objectNode.getElements();
         boolean isSpillObject = properties.size() > CodeGenerator.OBJECT_SPILL_THRESHOLD;
         long totalWeight = 0L;
         int lo = 0;

         for(int i = 0; i < properties.size(); ++i) {
            PropertyNode property = (PropertyNode)properties.get(i);
            boolean isConstant = LiteralNode.isConstant(property.getValue());
            if (!isConstant || !isSpillObject) {
               weight = isConstant ? 0L : WeighNodes.weigh(property.getValue());
               totalWeight += 2L + weight;
               if (totalWeight >= SPLIT_THRESHOLD) {
                  CompileUnit unit = this.compiler.findUnit(totalWeight - weight);
                  ranges.add(new Splittable.SplitRange(unit, lo, i));
                  lo = i;
                  totalWeight = weight;
               }
            }
         }

         if (lo != properties.size()) {
            CompileUnit unit = this.compiler.findUnit(totalWeight);
            ranges.add(new Splittable.SplitRange(unit, lo, properties.size()));
         }

         return objectNode.setSplitRanges(this.lc, ranges);
      }
   }

   public boolean enterFunctionNode(FunctionNode node) {
      return node == this.outermost;
   }
}
