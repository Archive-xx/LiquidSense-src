package jdk.nashorn.internal.ir.debug;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.BlockStatement;
import jdk.nashorn.internal.ir.BreakNode;
import jdk.nashorn.internal.ir.CaseNode;
import jdk.nashorn.internal.ir.CatchNode;
import jdk.nashorn.internal.ir.ContinueNode;
import jdk.nashorn.internal.ir.ExpressionStatement;
import jdk.nashorn.internal.ir.ForNode;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IfNode;
import jdk.nashorn.internal.ir.JoinPredecessor;
import jdk.nashorn.internal.ir.JoinPredecessorExpression;
import jdk.nashorn.internal.ir.LabelNode;
import jdk.nashorn.internal.ir.LocalVariableConversion;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.SplitNode;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.ThrowNode;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;

public final class PrintVisitor extends SimpleNodeVisitor {
   private static final int TABWIDTH = 4;
   private final StringBuilder sb;
   private int indent;
   private final String EOLN;
   private final boolean printLineNumbers;
   private final boolean printTypes;
   private int lastLineNumber;

   public PrintVisitor() {
      this(true, true);
   }

   public PrintVisitor(boolean printLineNumbers, boolean printTypes) {
      this.lastLineNumber = -1;
      this.EOLN = System.lineSeparator();
      this.sb = new StringBuilder();
      this.printLineNumbers = printLineNumbers;
      this.printTypes = printTypes;
   }

   public PrintVisitor(Node root) {
      this(root, true, true);
   }

   public PrintVisitor(Node root, boolean printLineNumbers, boolean printTypes) {
      this(printLineNumbers, printTypes);
      this.visit(root);
   }

   private void visit(Node root) {
      root.accept(this);
   }

   public String toString() {
      return this.sb.append(this.EOLN).toString();
   }

   private void indent() {
      for(int i = this.indent; i > 0; --i) {
         this.sb.append(' ');
      }

   }

   public boolean enterDefault(Node node) {
      node.toString(this.sb, this.printTypes);
      return false;
   }

   public boolean enterContinueNode(ContinueNode node) {
      node.toString(this.sb, this.printTypes);
      this.printLocalVariableConversion(node);
      return false;
   }

   public boolean enterBreakNode(BreakNode node) {
      node.toString(this.sb, this.printTypes);
      this.printLocalVariableConversion(node);
      return false;
   }

   public boolean enterThrowNode(ThrowNode node) {
      node.toString(this.sb, this.printTypes);
      this.printLocalVariableConversion(node);
      return false;
   }

   public boolean enterBlock(Block block) {
      this.sb.append(' ');
      this.sb.append('{');
      this.indent += 4;
      List<Statement> statements = block.getStatements();
      Iterator var3 = statements.iterator();

      while(var3.hasNext()) {
         Statement statement = (Statement)var3.next();
         int lastIndex;
         if (this.printLineNumbers) {
            lastIndex = statement.getLineNumber();
            this.sb.append('\n');
            if (lastIndex != this.lastLineNumber) {
               this.indent();
               this.sb.append("[|").append(lastIndex).append("|];").append('\n');
            }

            this.lastLineNumber = lastIndex;
         }

         this.indent();
         statement.accept(this);
         lastIndex = this.sb.length() - 1;

         char lastChar;
         for(lastChar = this.sb.charAt(lastIndex); Character.isWhitespace(lastChar) && lastIndex >= 0; lastChar = this.sb.charAt(lastIndex)) {
            --lastIndex;
         }

         if (lastChar != '}' && lastChar != ';') {
            this.sb.append(';');
         }

         if (statement.hasGoto()) {
            this.sb.append(" [GOTO]");
         }

         if (statement.isTerminal()) {
            this.sb.append(" [TERMINAL]");
         }
      }

      this.indent -= 4;
      this.sb.append(this.EOLN);
      this.indent();
      this.sb.append('}');
      this.printLocalVariableConversion(block);
      return false;
   }

   public boolean enterBlockStatement(BlockStatement statement) {
      statement.getBlock().accept(this);
      return false;
   }

   public boolean enterBinaryNode(BinaryNode binaryNode) {
      binaryNode.lhs().accept(this);
      this.sb.append(' ');
      this.sb.append(binaryNode.tokenType());
      this.sb.append(' ');
      binaryNode.rhs().accept(this);
      return false;
   }

   public boolean enterJoinPredecessorExpression(JoinPredecessorExpression expr) {
      expr.getExpression().accept(this);
      this.printLocalVariableConversion(expr);
      return false;
   }

   public boolean enterIdentNode(IdentNode identNode) {
      identNode.toString(this.sb, this.printTypes);
      this.printLocalVariableConversion(identNode);
      return true;
   }

   private void printLocalVariableConversion(JoinPredecessor joinPredecessor) {
      LocalVariableConversion.toString(joinPredecessor.getLocalVariableConversion(), this.sb);
   }

   public boolean enterUnaryNode(final UnaryNode unaryNode) {
      unaryNode.toString(this.sb, new Runnable() {
         public void run() {
            unaryNode.getExpression().accept(PrintVisitor.this);
         }
      }, this.printTypes);
      return false;
   }

   public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
      expressionStatement.getExpression().accept(this);
      return false;
   }

   public boolean enterForNode(ForNode forNode) {
      forNode.toString(this.sb, this.printTypes);
      forNode.getBody().accept(this);
      return false;
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      functionNode.toString(this.sb, this.printTypes);
      this.enterBlock(functionNode.getBody());
      return false;
   }

   public boolean enterIfNode(IfNode ifNode) {
      ifNode.toString(this.sb, this.printTypes);
      ifNode.getPass().accept(this);
      Block fail = ifNode.getFail();
      if (fail != null) {
         this.sb.append(" else ");
         fail.accept(this);
      }

      if (ifNode.getLocalVariableConversion() != null) {
         assert fail == null;

         this.sb.append(" else ");
         this.printLocalVariableConversion(ifNode);
         this.sb.append(";");
      }

      return false;
   }

   public boolean enterLabelNode(LabelNode labeledNode) {
      this.indent -= 4;
      this.indent();
      this.indent += 4;
      labeledNode.toString(this.sb, this.printTypes);
      labeledNode.getBody().accept(this);
      this.printLocalVariableConversion(labeledNode);
      return false;
   }

   public boolean enterSplitNode(SplitNode splitNode) {
      splitNode.toString(this.sb, this.printTypes);
      this.sb.append(this.EOLN);
      this.indent += 4;
      this.indent();
      return true;
   }

   public Node leaveSplitNode(SplitNode splitNode) {
      this.sb.append("</split>");
      this.sb.append(this.EOLN);
      this.indent -= 4;
      this.indent();
      return splitNode;
   }

   public boolean enterSwitchNode(SwitchNode switchNode) {
      switchNode.toString(this.sb, this.printTypes);
      this.sb.append(" {");
      List<CaseNode> cases = switchNode.getCases();
      Iterator var3 = cases.iterator();

      while(var3.hasNext()) {
         CaseNode caseNode = (CaseNode)var3.next();
         this.sb.append(this.EOLN);
         this.indent();
         caseNode.toString(this.sb, this.printTypes);
         this.printLocalVariableConversion(caseNode);
         this.indent += 4;
         caseNode.getBody().accept(this);
         this.indent -= 4;
         this.sb.append(this.EOLN);
      }

      if (switchNode.getLocalVariableConversion() != null) {
         this.sb.append(this.EOLN);
         this.indent();
         this.sb.append("default: ");
         this.printLocalVariableConversion(switchNode);
         this.sb.append("{}");
      }

      this.sb.append(this.EOLN);
      this.indent();
      this.sb.append("}");
      return false;
   }

   public boolean enterTryNode(TryNode tryNode) {
      tryNode.toString(this.sb, this.printTypes);
      this.printLocalVariableConversion(tryNode);
      tryNode.getBody().accept(this);
      List<Block> catchBlocks = tryNode.getCatchBlocks();
      Iterator var3 = catchBlocks.iterator();

      while(var3.hasNext()) {
         Block catchBlock = (Block)var3.next();
         CatchNode catchNode = (CatchNode)catchBlock.getStatements().get(0);
         catchNode.toString(this.sb, this.printTypes);
         catchNode.getBody().accept(this);
      }

      Block finallyBody = tryNode.getFinallyBody();
      if (finallyBody != null) {
         this.sb.append(" finally ");
         finallyBody.accept(this);
      }

      Iterator var7 = tryNode.getInlinedFinallies().iterator();

      while(var7.hasNext()) {
         Block inlinedFinally = (Block)var7.next();
         inlinedFinally.accept(this);
      }

      return false;
   }

   public boolean enterVarNode(VarNode varNode) {
      this.sb.append(varNode.isConst() ? "const " : (varNode.isLet() ? "let " : "var "));
      varNode.getName().toString(this.sb, this.printTypes);
      this.printLocalVariableConversion(varNode.getName());
      Node init = varNode.getInit();
      if (init != null) {
         this.sb.append(" = ");
         init.accept(this);
      }

      return false;
   }

   public boolean enterWhileNode(WhileNode whileNode) {
      this.printLocalVariableConversion(whileNode);
      if (whileNode.isDoWhile()) {
         this.sb.append("do");
         whileNode.getBody().accept(this);
         this.sb.append(' ');
         whileNode.toString(this.sb, this.printTypes);
      } else {
         whileNode.toString(this.sb, this.printTypes);
         whileNode.getBody().accept(this);
      }

      return false;
   }

   public boolean enterWithNode(WithNode withNode) {
      withNode.toString(this.sb, this.printTypes);
      withNode.getBody().accept(this);
      return false;
   }
}
