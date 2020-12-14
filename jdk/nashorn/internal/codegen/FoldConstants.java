package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.BlockStatement;
import jdk.nashorn.internal.ir.CaseNode;
import jdk.nashorn.internal.ir.EmptyNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IfNode;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.TernaryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "fold"
)
final class FoldConstants extends SimpleNodeVisitor implements Loggable {
   private final DebugLogger log;

   FoldConstants(Compiler compiler) {
      this.log = this.initLogger(compiler.getContext());
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   public Node leaveUnaryNode(UnaryNode unaryNode) {
      LiteralNode<?> literalNode = (new FoldConstants.UnaryNodeConstantEvaluator(unaryNode)).eval();
      if (literalNode != null) {
         this.log.info("Unary constant folded ", unaryNode, " to ", literalNode);
         return literalNode;
      } else {
         return unaryNode;
      }
   }

   public Node leaveBinaryNode(BinaryNode binaryNode) {
      LiteralNode<?> literalNode = (new FoldConstants.BinaryNodeConstantEvaluator(binaryNode)).eval();
      if (literalNode != null) {
         this.log.info("Binary constant folded ", binaryNode, " to ", literalNode);
         return literalNode;
      } else {
         return binaryNode;
      }
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      return functionNode;
   }

   public Node leaveIfNode(IfNode ifNode) {
      Node test = ifNode.getTest();
      if (test instanceof LiteralNode.PrimitiveLiteralNode) {
         boolean isTrue = ((LiteralNode.PrimitiveLiteralNode)test).isTrue();
         Block executed = isTrue ? ifNode.getPass() : ifNode.getFail();
         Block dropped = isTrue ? ifNode.getFail() : ifNode.getPass();
         List<Statement> statements = new ArrayList();
         if (executed != null) {
            statements.addAll(executed.getStatements());
         }

         if (dropped != null) {
            extractVarNodesFromDeadCode(dropped, statements);
         }

         return (Node)(statements.isEmpty() ? new EmptyNode(ifNode) : BlockStatement.createReplacement(ifNode, ifNode.getFinish(), statements));
      } else {
         return ifNode;
      }
   }

   public Node leaveTernaryNode(TernaryNode ternaryNode) {
      Node test = ternaryNode.getTest();
      return (Node)(test instanceof LiteralNode.PrimitiveLiteralNode ? (((LiteralNode.PrimitiveLiteralNode)test).isTrue() ? ternaryNode.getTrueExpression() : ternaryNode.getFalseExpression()).getExpression() : ternaryNode);
   }

   public Node leaveSwitchNode(SwitchNode switchNode) {
      return switchNode.setUniqueInteger(this.lc, isUniqueIntegerSwitchNode(switchNode));
   }

   private static boolean isUniqueIntegerSwitchNode(SwitchNode switchNode) {
      Set<Integer> alreadySeen = new HashSet();
      Iterator var2 = switchNode.getCases().iterator();

      Expression test;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         CaseNode caseNode = (CaseNode)var2.next();
         test = caseNode.getTest();
      } while(test == null || isUniqueIntegerLiteral(test, alreadySeen));

      return false;
   }

   private static boolean isUniqueIntegerLiteral(Expression expr, Set<Integer> alreadySeen) {
      if (expr instanceof LiteralNode) {
         Object value = ((LiteralNode)expr).getValue();
         if (value instanceof Integer) {
            return alreadySeen.add((Integer)value);
         }
      }

      return false;
   }

   static void extractVarNodesFromDeadCode(Node deadCodeRoot, final List<Statement> statements) {
      deadCodeRoot.accept(new SimpleNodeVisitor() {
         public boolean enterVarNode(VarNode varNode) {
            statements.add(varNode.setInit((Expression)null));
            return false;
         }

         public boolean enterFunctionNode(FunctionNode functionNode) {
            return false;
         }
      });
   }

   private static class BinaryNodeConstantEvaluator extends FoldConstants.ConstantEvaluator<BinaryNode> {
      BinaryNodeConstantEvaluator(BinaryNode parent) {
         super(parent);
      }

      protected LiteralNode<?> eval() {
         LiteralNode<?> result = this.reduceTwoLiterals();
         if (result != null) {
            return result;
         } else {
            result = this.reduceOneLiteral();
            return result != null ? result : null;
         }
      }

      private LiteralNode<?> reduceOneLiteral() {
         return null;
      }

      private LiteralNode<?> reduceTwoLiterals() {
         if (((BinaryNode)this.parent).lhs() instanceof LiteralNode && ((BinaryNode)this.parent).rhs() instanceof LiteralNode) {
            LiteralNode<?> lhs = (LiteralNode)((BinaryNode)this.parent).lhs();
            LiteralNode<?> rhs = (LiteralNode)((BinaryNode)this.parent).rhs();
            if (!(lhs instanceof LiteralNode.ArrayLiteralNode) && !(rhs instanceof LiteralNode.ArrayLiteralNode)) {
               Type widest = Type.widest(lhs.getType(), rhs.getType());
               boolean isInteger = widest.isInteger();
               double value;
               switch(((BinaryNode)this.parent).tokenType()) {
               case ADD:
                  if (!lhs.isString() && !rhs.isNumeric() || !rhs.isString() && !rhs.isNumeric()) {
                     return null;
                  }

                  Object res = ScriptRuntime.ADD(lhs.getObject(), rhs.getObject());
                  if (!(res instanceof Number)) {
                     assert res instanceof CharSequence : res + " was not a CharSequence, it was a " + res.getClass();

                     return LiteralNode.newInstance(this.token, this.finish, res.toString());
                  }

                  value = ((Number)res).doubleValue();
                  break;
               case SUB:
                  value = lhs.getNumber() - rhs.getNumber();
                  break;
               case NOT:
               case BIT_NOT:
               default:
                  return null;
               case DIV:
                  value = lhs.getNumber() / rhs.getNumber();
                  break;
               case MUL:
                  value = lhs.getNumber() * rhs.getNumber();
                  break;
               case MOD:
                  value = lhs.getNumber() % rhs.getNumber();
                  break;
               case SHR:
                  long result = JSType.toUint32(lhs.getInt32() >>> rhs.getInt32());
                  return LiteralNode.newInstance(this.token, this.finish, JSType.toNarrowestNumber(result));
               case SAR:
                  return LiteralNode.newInstance(this.token, this.finish, (Number)(lhs.getInt32() >> rhs.getInt32()));
               case SHL:
                  return LiteralNode.newInstance(this.token, this.finish, (Number)(lhs.getInt32() << rhs.getInt32()));
               case BIT_XOR:
                  return LiteralNode.newInstance(this.token, this.finish, (Number)(lhs.getInt32() ^ rhs.getInt32()));
               case BIT_AND:
                  return LiteralNode.newInstance(this.token, this.finish, (Number)(lhs.getInt32() & rhs.getInt32()));
               case BIT_OR:
                  return LiteralNode.newInstance(this.token, this.finish, (Number)(lhs.getInt32() | rhs.getInt32()));
               case GE:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.GE(lhs.getObject(), rhs.getObject()));
               case LE:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.LE(lhs.getObject(), rhs.getObject()));
               case GT:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.GT(lhs.getObject(), rhs.getObject()));
               case LT:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.LT(lhs.getObject(), rhs.getObject()));
               case NE:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.NE(lhs.getObject(), rhs.getObject()));
               case NE_STRICT:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.NE_STRICT(lhs.getObject(), rhs.getObject()));
               case EQ:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.EQ(lhs.getObject(), rhs.getObject()));
               case EQ_STRICT:
                  return LiteralNode.newInstance(this.token, this.finish, ScriptRuntime.EQ_STRICT(lhs.getObject(), rhs.getObject()));
               }

               isInteger &= JSType.isStrictlyRepresentableAsInt(value);
               if (isInteger) {
                  return LiteralNode.newInstance(this.token, this.finish, (Number)((int)value));
               } else {
                  return LiteralNode.newInstance(this.token, this.finish, (Number)value);
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private static class UnaryNodeConstantEvaluator extends FoldConstants.ConstantEvaluator<UnaryNode> {
      UnaryNodeConstantEvaluator(UnaryNode parent) {
         super(parent);
      }

      protected LiteralNode<?> eval() {
         Node rhsNode = ((UnaryNode)this.parent).getExpression();
         if (!(rhsNode instanceof LiteralNode)) {
            return null;
         } else if (rhsNode instanceof LiteralNode.ArrayLiteralNode) {
            return null;
         } else {
            LiteralNode<?> rhs = (LiteralNode)rhsNode;
            Type rhsType = rhs.getType();
            boolean rhsInteger = rhsType.isInteger() || rhsType.isBoolean();
            LiteralNode literalNode;
            switch(((UnaryNode)this.parent).tokenType()) {
            case ADD:
               if (rhsInteger) {
                  literalNode = LiteralNode.newInstance(this.token, this.finish, (Number)rhs.getInt32());
               } else if (rhsType.isLong()) {
                  literalNode = LiteralNode.newInstance(this.token, this.finish, (Number)rhs.getLong());
               } else {
                  literalNode = LiteralNode.newInstance(this.token, this.finish, (Number)rhs.getNumber());
               }
               break;
            case SUB:
               if (rhsInteger && rhs.getInt32() != 0) {
                  literalNode = LiteralNode.newInstance(this.token, this.finish, (Number)(-rhs.getInt32()));
               } else if (rhsType.isLong() && rhs.getLong() != 0L) {
                  literalNode = LiteralNode.newInstance(this.token, this.finish, (Number)(-rhs.getLong()));
               } else {
                  literalNode = LiteralNode.newInstance(this.token, this.finish, (Number)(-rhs.getNumber()));
               }
               break;
            case NOT:
               literalNode = LiteralNode.newInstance(this.token, this.finish, !rhs.getBoolean());
               break;
            case BIT_NOT:
               literalNode = LiteralNode.newInstance(this.token, this.finish, (Number)(~rhs.getInt32()));
               break;
            default:
               return null;
            }

            return literalNode;
         }
      }
   }

   abstract static class ConstantEvaluator<T extends Node> {
      protected T parent;
      protected final long token;
      protected final int finish;

      protected ConstantEvaluator(T parent) {
         this.parent = parent;
         this.token = parent.getToken();
         this.finish = parent.getFinish();
      }

      protected abstract LiteralNode<?> eval();
   }
}
