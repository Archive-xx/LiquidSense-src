package jdk.nashorn.internal.codegen;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.BreakNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.CatchNode;
import jdk.nashorn.internal.ir.ContinueNode;
import jdk.nashorn.internal.ir.ExpressionStatement;
import jdk.nashorn.internal.ir.ForNode;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IfNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.JumpToInlinedFinally;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.ReturnNode;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.ir.SplitNode;
import jdk.nashorn.internal.ir.Splittable;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.ThrowNode;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.visitor.NodeOperatorVisitor;

final class WeighNodes extends NodeOperatorVisitor<LexicalContext> {
   static final long FUNCTION_WEIGHT = 40L;
   static final long AASTORE_WEIGHT = 2L;
   static final long ACCESS_WEIGHT = 4L;
   static final long ADD_WEIGHT = 10L;
   static final long BREAK_WEIGHT = 1L;
   static final long CALL_WEIGHT = 10L;
   static final long CATCH_WEIGHT = 10L;
   static final long COMPARE_WEIGHT = 6L;
   static final long CONTINUE_WEIGHT = 1L;
   static final long IF_WEIGHT = 2L;
   static final long LITERAL_WEIGHT = 10L;
   static final long LOOP_WEIGHT = 4L;
   static final long NEW_WEIGHT = 6L;
   static final long FUNC_EXPR_WEIGHT = 20L;
   static final long RETURN_WEIGHT = 2L;
   static final long SPLIT_WEIGHT = 40L;
   static final long SWITCH_WEIGHT = 8L;
   static final long THROW_WEIGHT = 2L;
   static final long VAR_WEIGHT = 40L;
   static final long WITH_WEIGHT = 8L;
   static final long OBJECT_WEIGHT = 16L;
   static final long SETPROP_WEIGHT = 5L;
   private long weight;
   private final Map<Node, Long> weightCache;
   private final FunctionNode topFunction;

   private WeighNodes(FunctionNode topFunction, Map<Node, Long> weightCache) {
      super(new LexicalContext());
      this.topFunction = topFunction;
      this.weightCache = weightCache;
   }

   static long weigh(Node node) {
      return weigh(node, (Map)null);
   }

   static long weigh(Node node, Map<Node, Long> weightCache) {
      WeighNodes weighNodes = new WeighNodes(node instanceof FunctionNode ? (FunctionNode)node : null, weightCache);
      node.accept(weighNodes);
      return weighNodes.weight;
   }

   public Node leaveAccessNode(AccessNode accessNode) {
      this.weight += 4L;
      return accessNode;
   }

   public boolean enterBlock(Block block) {
      if (this.weightCache != null && this.weightCache.containsKey(block)) {
         this.weight += (Long)this.weightCache.get(block);
         return false;
      } else {
         return true;
      }
   }

   public Node leaveBreakNode(BreakNode breakNode) {
      ++this.weight;
      return breakNode;
   }

   public Node leaveCallNode(CallNode callNode) {
      this.weight += 10L;
      return callNode;
   }

   public Node leaveCatchNode(CatchNode catchNode) {
      this.weight += 10L;
      return catchNode;
   }

   public Node leaveContinueNode(ContinueNode continueNode) {
      ++this.weight;
      return continueNode;
   }

   public Node leaveExpressionStatement(ExpressionStatement expressionStatement) {
      return expressionStatement;
   }

   public Node leaveForNode(ForNode forNode) {
      this.weight += 4L;
      return forNode;
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      if (functionNode == this.topFunction) {
         return true;
      } else {
         this.weight += 20L;
         return false;
      }
   }

   public Node leaveIdentNode(IdentNode identNode) {
      this.weight += 4L;
      return identNode;
   }

   public Node leaveIfNode(IfNode ifNode) {
      this.weight += 2L;
      return ifNode;
   }

   public Node leaveIndexNode(IndexNode indexNode) {
      this.weight += 4L;
      return indexNode;
   }

   public Node leaveJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
      ++this.weight;
      return jumpToInlinedFinally;
   }

   public boolean enterLiteralNode(LiteralNode literalNode) {
      this.weight += 10L;
      if (!(literalNode instanceof LiteralNode.ArrayLiteralNode)) {
         return true;
      } else {
         LiteralNode.ArrayLiteralNode arrayLiteralNode = (LiteralNode.ArrayLiteralNode)literalNode;
         Node[] value = (Node[])arrayLiteralNode.getValue();
         int[] postsets = arrayLiteralNode.getPostsets();
         List<Splittable.SplitRange> units = arrayLiteralNode.getSplitRanges();
         if (units == null) {
            int[] var6 = postsets;
            int var7 = postsets.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               int postset = var6[var8];
               this.weight += 2L;
               Node element = value[postset];
               if (element != null) {
                  element.accept(this);
               }
            }
         }

         return false;
      }
   }

   public boolean enterObjectNode(ObjectNode objectNode) {
      this.weight += 16L;
      List<PropertyNode> properties = objectNode.getElements();
      boolean isSpillObject = properties.size() > CodeGenerator.OBJECT_SPILL_THRESHOLD;
      Iterator var4 = properties.iterator();

      while(var4.hasNext()) {
         PropertyNode property = (PropertyNode)var4.next();
         if (!LiteralNode.isConstant(property.getValue())) {
            this.weight += 5L;
            property.getValue().accept(this);
         } else if (!isSpillObject) {
            this.weight += 5L;
         }
      }

      return false;
   }

   public Node leavePropertyNode(PropertyNode propertyNode) {
      this.weight += 10L;
      return propertyNode;
   }

   public Node leaveReturnNode(ReturnNode returnNode) {
      this.weight += 2L;
      return returnNode;
   }

   public Node leaveRuntimeNode(RuntimeNode runtimeNode) {
      this.weight += 10L;
      return runtimeNode;
   }

   public boolean enterSplitNode(SplitNode splitNode) {
      this.weight += 40L;
      return false;
   }

   public Node leaveSwitchNode(SwitchNode switchNode) {
      this.weight += 8L;
      return switchNode;
   }

   public Node leaveThrowNode(ThrowNode throwNode) {
      this.weight += 2L;
      return throwNode;
   }

   public Node leaveTryNode(TryNode tryNode) {
      this.weight += 2L;
      return tryNode;
   }

   public Node leaveVarNode(VarNode varNode) {
      this.weight += 40L;
      return varNode;
   }

   public Node leaveWhileNode(WhileNode whileNode) {
      this.weight += 4L;
      return whileNode;
   }

   public Node leaveWithNode(WithNode withNode) {
      this.weight += 8L;
      return withNode;
   }

   public Node leaveADD(UnaryNode unaryNode) {
      return this.unaryNodeWeight(unaryNode);
   }

   public Node leaveBIT_NOT(UnaryNode unaryNode) {
      return this.unaryNodeWeight(unaryNode);
   }

   public Node leaveDECINC(UnaryNode unaryNode) {
      return this.unaryNodeWeight(unaryNode);
   }

   public Node leaveDELETE(UnaryNode unaryNode) {
      return this.runtimeNodeWeight(unaryNode);
   }

   public Node leaveNEW(UnaryNode unaryNode) {
      this.weight += 6L;
      return unaryNode;
   }

   public Node leaveNOT(UnaryNode unaryNode) {
      return this.unaryNodeWeight(unaryNode);
   }

   public Node leaveSUB(UnaryNode unaryNode) {
      return this.unaryNodeWeight(unaryNode);
   }

   public Node leaveTYPEOF(UnaryNode unaryNode) {
      return this.runtimeNodeWeight(unaryNode);
   }

   public Node leaveVOID(UnaryNode unaryNode) {
      return this.unaryNodeWeight(unaryNode);
   }

   public Node leaveADD(BinaryNode binaryNode) {
      this.weight += 10L;
      return binaryNode;
   }

   public Node leaveAND(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_ADD(BinaryNode binaryNode) {
      this.weight += 10L;
      return binaryNode;
   }

   public Node leaveASSIGN_BIT_AND(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_BIT_OR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_BIT_XOR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_DIV(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_MOD(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_MUL(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_SAR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_SHL(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_SHR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveASSIGN_SUB(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveBIND(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveBIT_AND(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveBIT_OR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveBIT_XOR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveCOMMALEFT(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveCOMMARIGHT(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveDIV(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveEQ(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveEQ_STRICT(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveGE(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveGT(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveIN(BinaryNode binaryNode) {
      this.weight += 10L;
      return binaryNode;
   }

   public Node leaveINSTANCEOF(BinaryNode binaryNode) {
      this.weight += 10L;
      return binaryNode;
   }

   public Node leaveLE(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveLT(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveMOD(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveMUL(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveNE(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveNE_STRICT(BinaryNode binaryNode) {
      return this.compareWeight(binaryNode);
   }

   public Node leaveOR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveSAR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveSHL(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveSHR(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   public Node leaveSUB(BinaryNode binaryNode) {
      return this.binaryNodeWeight(binaryNode);
   }

   private Node unaryNodeWeight(UnaryNode unaryNode) {
      ++this.weight;
      return unaryNode;
   }

   private Node binaryNodeWeight(BinaryNode binaryNode) {
      ++this.weight;
      return binaryNode;
   }

   private Node runtimeNodeWeight(UnaryNode unaryNode) {
      this.weight += 10L;
      return unaryNode;
   }

   private Node compareWeight(BinaryNode binaryNode) {
      this.weight += 6L;
      return binaryNode;
   }
}
