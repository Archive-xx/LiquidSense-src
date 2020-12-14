package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BaseNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.BlockLexicalContext;
import jdk.nashorn.internal.ir.BlockStatement;
import jdk.nashorn.internal.ir.BreakNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.CaseNode;
import jdk.nashorn.internal.ir.CatchNode;
import jdk.nashorn.internal.ir.ContinueNode;
import jdk.nashorn.internal.ir.EmptyNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.ExpressionStatement;
import jdk.nashorn.internal.ir.ForNode;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IfNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.JoinPredecessorExpression;
import jdk.nashorn.internal.ir.JumpStatement;
import jdk.nashorn.internal.ir.JumpToInlinedFinally;
import jdk.nashorn.internal.ir.LabelNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.LoopNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ReturnNode;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.ThrowNode;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.visitor.NodeOperatorVisitor;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "lower"
)
final class Lower extends NodeOperatorVisitor<BlockLexicalContext> implements Loggable {
   private final DebugLogger log;
   private static Pattern SAFE_PROPERTY_NAME = Pattern.compile("[a-zA-Z_$][\\w$]*");

   Lower(Compiler compiler) {
      super(new BlockLexicalContext() {
         public List<Statement> popStatements() {
            List<Statement> newStatements = new ArrayList();
            boolean terminated = false;
            List<Statement> statements = super.popStatements();
            Iterator var4 = statements.iterator();

            while(true) {
               Statement statement;
               label24:
               do {
                  while(var4.hasNext()) {
                     statement = (Statement)var4.next();
                     if (!terminated) {
                        newStatements.add(statement);
                        continue label24;
                     }

                     FoldConstants.extractVarNodesFromDeadCode(statement, newStatements);
                  }

                  return newStatements;
               } while(!statement.isTerminal() && !(statement instanceof JumpStatement));

               terminated = true;
            }
         }

         protected Block afterSetStatements(Block block) {
            List<Statement> stmts = block.getStatements();
            ListIterator li = stmts.listIterator(stmts.size());

            Statement stmt;
            do {
               if (!li.hasPrevious()) {
                  return block.setIsTerminal(this, false);
               }

               stmt = (Statement)li.previous();
            } while(stmt instanceof VarNode && ((VarNode)stmt).getInit() == null);

            return block.setIsTerminal(this, stmt.isTerminal());
         }
      });
      this.log = this.initLogger(compiler.getContext());
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   public boolean enterBreakNode(BreakNode breakNode) {
      this.addStatement(breakNode);
      return false;
   }

   public Node leaveCallNode(CallNode callNode) {
      return this.checkEval(callNode.setFunction(markerFunction(callNode.getFunction())));
   }

   public Node leaveCatchNode(CatchNode catchNode) {
      return this.addStatement(catchNode);
   }

   public boolean enterContinueNode(ContinueNode continueNode) {
      this.addStatement(continueNode);
      return false;
   }

   public boolean enterJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
      this.addStatement(jumpToInlinedFinally);
      return false;
   }

   public boolean enterEmptyNode(EmptyNode emptyNode) {
      return false;
   }

   public Node leaveIndexNode(IndexNode indexNode) {
      String name = getConstantPropertyName(indexNode.getIndex());
      if (name != null) {
         assert indexNode.isIndex();

         return new AccessNode(indexNode.getToken(), indexNode.getFinish(), indexNode.getBase(), name);
      } else {
         return super.leaveIndexNode(indexNode);
      }
   }

   private static String getConstantPropertyName(Expression expression) {
      if (expression instanceof LiteralNode.PrimitiveLiteralNode) {
         Object value = ((LiteralNode)expression).getValue();
         if (value instanceof String && SAFE_PROPERTY_NAME.matcher((String)value).matches()) {
            return (String)value;
         }
      }

      return null;
   }

   public Node leaveExpressionStatement(ExpressionStatement expressionStatement) {
      Expression expr = expressionStatement.getExpression();
      ExpressionStatement node = expressionStatement;
      FunctionNode currentFunction = ((BlockLexicalContext)this.lc).getCurrentFunction();
      if (currentFunction.isProgram() && !isInternalExpression(expr) && !isEvalResultAssignment(expr)) {
         node = expressionStatement.setExpression(new BinaryNode(Token.recast(expressionStatement.getToken(), TokenType.ASSIGN), this.compilerConstant(CompilerConstants.RETURN), expr));
      }

      return this.addStatement(node);
   }

   public Node leaveBlockStatement(BlockStatement blockStatement) {
      return this.addStatement(blockStatement);
   }

   public Node leaveForNode(ForNode forNode) {
      ForNode newForNode = forNode;
      Expression test = forNode.getTest();
      if (!forNode.isForIn() && Expression.isAlwaysTrue(test)) {
         newForNode = forNode.setTest(this.lc, (JoinPredecessorExpression)null);
      }

      newForNode = (ForNode)this.checkEscape(newForNode);
      if (newForNode.isForIn()) {
         this.addStatementEnclosedInBlock(newForNode);
      } else {
         this.addStatement(newForNode);
      }

      return newForNode;
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      this.log.info("END FunctionNode: ", functionNode.getName());
      return functionNode;
   }

   public Node leaveIfNode(IfNode ifNode) {
      return this.addStatement(ifNode);
   }

   public Node leaveIN(BinaryNode binaryNode) {
      return new RuntimeNode(binaryNode);
   }

   public Node leaveINSTANCEOF(BinaryNode binaryNode) {
      return new RuntimeNode(binaryNode);
   }

   public Node leaveLabelNode(LabelNode labelNode) {
      return this.addStatement(labelNode);
   }

   public Node leaveReturnNode(ReturnNode returnNode) {
      this.addStatement(returnNode);
      return returnNode;
   }

   public Node leaveCaseNode(CaseNode caseNode) {
      Node test = caseNode.getTest();
      if (test instanceof LiteralNode) {
         LiteralNode<?> lit = (LiteralNode)test;
         if (lit.isNumeric() && !(lit.getValue() instanceof Integer) && JSType.isRepresentableAsInt(lit.getNumber())) {
            return caseNode.setTest((Expression)LiteralNode.newInstance(lit, (Number)lit.getInt32()).accept(this));
         }
      }

      return caseNode;
   }

   public Node leaveSwitchNode(SwitchNode switchNode) {
      if (!switchNode.isUniqueInteger()) {
         this.addStatementEnclosedInBlock(switchNode);
      } else {
         this.addStatement(switchNode);
      }

      return switchNode;
   }

   public Node leaveThrowNode(ThrowNode throwNode) {
      return this.addStatement(throwNode);
   }

   private static <T extends Node> T ensureUniqueNamesIn(T node) {
      return node.accept(new SimpleNodeVisitor() {
         public Node leaveFunctionNode(FunctionNode functionNode) {
            String name = functionNode.getName();
            return functionNode.setName(this.lc, this.lc.getCurrentFunction().uniqueName(name));
         }

         public Node leaveDefault(Node labelledNode) {
            return labelledNode.ensureUniqueLabels(this.lc);
         }
      });
   }

   private static Block createFinallyBlock(Block finallyBody) {
      List<Statement> newStatements = new ArrayList();
      Iterator var2 = finallyBody.getStatements().iterator();

      while(var2.hasNext()) {
         Statement statement = (Statement)var2.next();
         newStatements.add(statement);
         if (statement.hasTerminalFlags()) {
            break;
         }
      }

      return finallyBody.setStatements((LexicalContext)null, newStatements);
   }

   private Block catchAllBlock(TryNode tryNode) {
      int lineNumber = tryNode.getLineNumber();
      long token = tryNode.getToken();
      int finish = tryNode.getFinish();
      IdentNode exception = new IdentNode(token, finish, ((BlockLexicalContext)this.lc).getCurrentFunction().uniqueName(CompilerConstants.EXCEPTION_PREFIX.symbolName()));
      Block catchBody = new Block(token, finish, new Statement[]{new ThrowNode(lineNumber, token, finish, new IdentNode(exception), true)});

      assert catchBody.isTerminal();

      CatchNode catchAllNode = new CatchNode(lineNumber, token, finish, new IdentNode(exception), (Expression)null, catchBody, true);
      Block catchAllBlock = new Block(token, finish, new Statement[]{catchAllNode});
      return (Block)catchAllBlock.accept(this);
   }

   private IdentNode compilerConstant(CompilerConstants cc) {
      FunctionNode functionNode = ((BlockLexicalContext)this.lc).getCurrentFunction();
      return new IdentNode(functionNode.getToken(), functionNode.getFinish(), cc.symbolName());
   }

   private static boolean isTerminalFinally(Block finallyBlock) {
      return finallyBlock.getLastStatement().hasTerminalFlags();
   }

   private TryNode spliceFinally(TryNode tryNode, final ThrowNode rethrow, Block finallyBody) {
      assert tryNode.getFinallyBody() == null;

      final Block finallyBlock = createFinallyBlock(finallyBody);
      final ArrayList<Block> inlinedFinallies = new ArrayList();
      final FunctionNode fn = ((BlockLexicalContext)this.lc).getCurrentFunction();
      TryNode newTryNode = (TryNode)tryNode.accept(new SimpleNodeVisitor() {
         public boolean enterFunctionNode(FunctionNode functionNode) {
            return false;
         }

         public Node leaveThrowNode(ThrowNode throwNode) {
            return (Node)(rethrow == throwNode ? new BlockStatement(Lower.prependFinally(finallyBlock, throwNode)) : throwNode);
         }

         public Node leaveBreakNode(BreakNode breakNode) {
            return this.leaveJumpStatement(breakNode);
         }

         public Node leaveContinueNode(ContinueNode continueNode) {
            return this.leaveJumpStatement(continueNode);
         }

         private Node leaveJumpStatement(JumpStatement jump) {
            return (Node)(jump.getTarget(this.lc) == null ? Lower.createJumpToInlinedFinally(fn, inlinedFinallies, Lower.prependFinally(finallyBlock, jump)) : jump);
         }

         public Node leaveReturnNode(ReturnNode returnNode) {
            Expression expr = returnNode.getExpression();
            ArrayList newStatements;
            int retLineNumber;
            long retToken;
            if (Lower.isTerminalFinally(finallyBlock)) {
               if (expr == null) {
                  return Lower.createJumpToInlinedFinally(fn, inlinedFinallies, (Block)Lower.ensureUniqueNamesIn(finallyBlock));
               } else {
                  newStatements = new ArrayList(2);
                  retLineNumber = returnNode.getLineNumber();
                  retToken = returnNode.getToken();
                  newStatements.add(new ExpressionStatement(retLineNumber, retToken, returnNode.getFinish(), expr));
                  newStatements.add(Lower.createJumpToInlinedFinally(fn, inlinedFinallies, (Block)Lower.ensureUniqueNamesIn(finallyBlock)));
                  return new BlockStatement(retLineNumber, new Block(retToken, finallyBlock.getFinish(), newStatements));
               }
            } else if (expr != null && !(expr instanceof LiteralNode.PrimitiveLiteralNode) && (!(expr instanceof IdentNode) || !CompilerConstants.RETURN.symbolName().equals(((IdentNode)expr).getName()))) {
               newStatements = new ArrayList();
               retLineNumber = returnNode.getLineNumber();
               retToken = returnNode.getToken();
               int retFinish = returnNode.getFinish();
               Expression resultNode = new IdentNode(expr.getToken(), expr.getFinish(), CompilerConstants.RETURN.symbolName());
               newStatements.add(new ExpressionStatement(retLineNumber, retToken, retFinish, new BinaryNode(Token.recast(returnNode.getToken(), TokenType.ASSIGN), resultNode, expr)));
               newStatements.add(Lower.createJumpToInlinedFinally(fn, inlinedFinallies, Lower.prependFinally(finallyBlock, returnNode.setExpression(resultNode))));
               return new BlockStatement(retLineNumber, new Block(retToken, retFinish, newStatements));
            } else {
               return Lower.createJumpToInlinedFinally(fn, inlinedFinallies, Lower.prependFinally(finallyBlock, returnNode));
            }
         }
      });
      this.addStatement(inlinedFinallies.isEmpty() ? newTryNode : newTryNode.setInlinedFinallies(this.lc, inlinedFinallies));
      this.addStatement(new BlockStatement(finallyBlock));
      return newTryNode;
   }

   private static JumpToInlinedFinally createJumpToInlinedFinally(FunctionNode fn, List<Block> inlinedFinallies, Block finallyBlock) {
      String labelName = fn.uniqueName(":finally");
      long token = finallyBlock.getToken();
      int finish = finallyBlock.getFinish();
      inlinedFinallies.add(new Block(token, finish, new Statement[]{new LabelNode(finallyBlock.getFirstStatementLineNumber(), token, finish, labelName, finallyBlock)}));
      return new JumpToInlinedFinally(labelName);
   }

   private static Block prependFinally(Block finallyBlock, Statement statement) {
      Block inlinedFinally = (Block)ensureUniqueNamesIn(finallyBlock);
      if (isTerminalFinally(finallyBlock)) {
         return inlinedFinally;
      } else {
         List<Statement> stmts = inlinedFinally.getStatements();
         List<Statement> newStmts = new ArrayList(stmts.size() + 1);
         newStmts.addAll(stmts);
         newStmts.add(statement);
         return new Block(inlinedFinally.getToken(), statement.getFinish(), newStmts);
      }
   }

   public Node leaveTryNode(TryNode tryNode) {
      Block finallyBody = tryNode.getFinallyBody();
      TryNode newTryNode = tryNode.setFinallyBody(this.lc, (Block)null);
      if (finallyBody != null && finallyBody.getStatementCount() != 0) {
         Block catchAll = this.catchAllBlock(tryNode);
         final List<ThrowNode> rethrows = new ArrayList(1);
         catchAll.accept(new SimpleNodeVisitor() {
            public boolean enterThrowNode(ThrowNode throwNode) {
               rethrows.add(throwNode);
               return true;
            }
         });

         assert rethrows.size() == 1;

         if (!tryNode.getCatchBlocks().isEmpty()) {
            Block outerBody = new Block(newTryNode.getToken(), newTryNode.getFinish(), new Statement[]{this.ensureUnconditionalCatch(newTryNode)});
            newTryNode = newTryNode.setBody(this.lc, outerBody).setCatchBlocks(this.lc, (List)null);
         }

         newTryNode = newTryNode.setCatchBlocks(this.lc, Arrays.asList(catchAll));
         return (TryNode)((BlockLexicalContext)this.lc).replace(tryNode, this.spliceFinally(newTryNode, (ThrowNode)rethrows.get(0), finallyBody));
      } else {
         List<CatchNode> catches = newTryNode.getCatches();
         return catches != null && !catches.isEmpty() ? this.addStatement(this.ensureUnconditionalCatch(newTryNode)) : this.addStatement(new BlockStatement(tryNode.getBody()));
      }
   }

   private TryNode ensureUnconditionalCatch(TryNode tryNode) {
      List<CatchNode> catches = tryNode.getCatches();
      if (catches != null && !catches.isEmpty() && ((CatchNode)catches.get(catches.size() - 1)).getExceptionCondition() != null) {
         List<Block> newCatchBlocks = new ArrayList(tryNode.getCatchBlocks());
         newCatchBlocks.add(this.catchAllBlock(tryNode));
         return tryNode.setCatchBlocks(this.lc, newCatchBlocks);
      } else {
         return tryNode;
      }
   }

   public Node leaveVarNode(VarNode varNode) {
      this.addStatement(varNode);
      if (varNode.getFlag(4) && ((BlockLexicalContext)this.lc).getCurrentFunction().isProgram()) {
         (new ExpressionStatement(varNode.getLineNumber(), varNode.getToken(), varNode.getFinish(), new IdentNode(varNode.getName()))).accept(this);
      }

      return varNode;
   }

   public Node leaveWhileNode(WhileNode whileNode) {
      Expression test = whileNode.getTest();
      Block body = whileNode.getBody();
      if (Expression.isAlwaysTrue(test)) {
         ForNode forNode = (ForNode)(new ForNode(whileNode.getLineNumber(), whileNode.getToken(), whileNode.getFinish(), body, 0)).accept(this);
         ((BlockLexicalContext)this.lc).replace(whileNode, forNode);
         return forNode;
      } else {
         return this.addStatement(this.checkEscape(whileNode));
      }
   }

   public Node leaveWithNode(WithNode withNode) {
      return this.addStatement(withNode);
   }

   private static Expression markerFunction(Expression function) {
      if (function instanceof IdentNode) {
         return ((IdentNode)function).setIsFunction();
      } else {
         return (Expression)(function instanceof BaseNode ? ((BaseNode)function).setIsFunction() : function);
      }
   }

   private String evalLocation(IdentNode node) {
      Source source = ((BlockLexicalContext)this.lc).getCurrentFunction().getSource();
      int pos = node.position();
      return source.getName() + '#' + source.getLine(pos) + ':' + source.getColumn(pos) + "<eval>";
   }

   private CallNode checkEval(CallNode callNode) {
      if (callNode.getFunction() instanceof IdentNode) {
         List<Expression> args = callNode.getArgs();
         IdentNode callee = (IdentNode)callNode.getFunction();
         if (args.size() >= 1 && CompilerConstants.EVAL.symbolName().equals(callee.getName())) {
            List<Expression> evalArgs = new ArrayList(args.size());
            Iterator var5 = args.iterator();

            while(var5.hasNext()) {
               Expression arg = (Expression)var5.next();
               evalArgs.add((Expression)((Expression)ensureUniqueNamesIn(arg)).accept(this));
            }

            return callNode.setEvalArgs(new CallNode.EvalArgs(evalArgs, this.evalLocation(callee)));
         }
      }

      return callNode;
   }

   private static boolean controlFlowEscapes(final LexicalContext lex, Block loopBody) {
      final List<Node> escapes = new ArrayList();
      loopBody.accept(new SimpleNodeVisitor() {
         public Node leaveBreakNode(BreakNode node) {
            escapes.add(node);
            return node;
         }

         public Node leaveContinueNode(ContinueNode node) {
            if (lex.contains(node.getTarget(lex))) {
               escapes.add(node);
            }

            return node;
         }
      });
      return !escapes.isEmpty();
   }

   private <T extends LoopNode> T checkEscape(T loopNode) {
      boolean escapes = controlFlowEscapes(this.lc, loopNode.getBody());
      return escapes ? loopNode.setBody(this.lc, loopNode.getBody().setIsTerminal(this.lc, false)).setControlFlowEscapes(this.lc, escapes) : loopNode;
   }

   private Node addStatement(Statement statement) {
      ((BlockLexicalContext)this.lc).appendStatement(statement);
      return statement;
   }

   private void addStatementEnclosedInBlock(Statement stmt) {
      BlockStatement b = BlockStatement.createReplacement(stmt, Collections.singletonList(stmt));
      if (stmt.isTerminal()) {
         b = b.setBlock(b.getBlock().setIsTerminal((LexicalContext)null, true));
      }

      this.addStatement(b);
   }

   private static boolean isInternalExpression(Expression expression) {
      if (!(expression instanceof IdentNode)) {
         return false;
      } else {
         Symbol symbol = ((IdentNode)expression).getSymbol();
         return symbol != null && symbol.isInternal();
      }
   }

   private static boolean isEvalResultAssignment(Node expression) {
      if (expression instanceof BinaryNode) {
         Node lhs = ((BinaryNode)expression).lhs();
         if (lhs instanceof IdentNode) {
            return ((IdentNode)lhs).getName().equals(CompilerConstants.RETURN.symbolName());
         }
      }

      return false;
   }
}
