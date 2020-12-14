package jdk.nashorn.internal.ir.debug;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
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
import jdk.nashorn.internal.ir.LabelNode;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.ReturnNode;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.ir.SplitNode;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.TernaryNode;
import jdk.nashorn.internal.ir.ThrowNode;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Source;

public final class JSONWriter extends SimpleNodeVisitor {
   private final StringBuilder buf = new StringBuilder();
   private final boolean includeLocation;

   public static String parse(Context context, String code, String name, boolean includeLoc) {
      Parser parser = new Parser(context.getEnv(), Source.sourceFor(name, code), new Context.ThrowErrorManager(), context.getEnv()._strict, context.getLogger(Parser.class));
      JSONWriter jsonWriter = new JSONWriter(includeLoc);

      try {
         FunctionNode functionNode = parser.parse();
         functionNode.accept(jsonWriter);
         return jsonWriter.getString();
      } catch (ParserException var7) {
         var7.throwAsEcmaException();
         return null;
      }
   }

   public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinPredecessorExpression) {
      Expression expr = joinPredecessorExpression.getExpression();
      if (expr != null) {
         expr.accept(this);
      } else {
         this.nullValue();
      }

      return false;
   }

   protected boolean enterDefault(Node node) {
      this.objectStart();
      this.location(node);
      return true;
   }

   private boolean leave() {
      this.objectEnd();
      return false;
   }

   protected Node leaveDefault(Node node) {
      this.objectEnd();
      return null;
   }

   public boolean enterAccessNode(AccessNode accessNode) {
      this.enterDefault(accessNode);
      this.type("MemberExpression");
      this.comma();
      this.property("object");
      accessNode.getBase().accept(this);
      this.comma();
      this.property("property", accessNode.getProperty());
      this.comma();
      this.property("computed", false);
      return this.leave();
   }

   public boolean enterBlock(Block block) {
      this.enterDefault(block);
      this.type("BlockStatement");
      this.comma();
      this.array("body", block.getStatements());
      return this.leave();
   }

   public boolean enterBinaryNode(BinaryNode binaryNode) {
      this.enterDefault(binaryNode);
      String name;
      if (binaryNode.isAssignment()) {
         name = "AssignmentExpression";
      } else if (binaryNode.isLogical()) {
         name = "LogicalExpression";
      } else {
         name = "BinaryExpression";
      }

      this.type(name);
      this.comma();
      this.property("operator", binaryNode.tokenType().getName());
      this.comma();
      this.property("left");
      binaryNode.lhs().accept(this);
      this.comma();
      this.property("right");
      binaryNode.rhs().accept(this);
      return this.leave();
   }

   public boolean enterBreakNode(BreakNode breakNode) {
      this.enterDefault(breakNode);
      this.type("BreakStatement");
      this.comma();
      String label = breakNode.getLabelName();
      if (label != null) {
         this.property("label", label);
      } else {
         this.property("label");
         this.nullValue();
      }

      return this.leave();
   }

   public boolean enterCallNode(CallNode callNode) {
      this.enterDefault(callNode);
      this.type("CallExpression");
      this.comma();
      this.property("callee");
      callNode.getFunction().accept(this);
      this.comma();
      this.array("arguments", callNode.getArgs());
      return this.leave();
   }

   public boolean enterCaseNode(CaseNode caseNode) {
      this.enterDefault(caseNode);
      this.type("SwitchCase");
      this.comma();
      Node test = caseNode.getTest();
      this.property("test");
      if (test != null) {
         test.accept(this);
      } else {
         this.nullValue();
      }

      this.comma();
      this.array("consequent", caseNode.getBody().getStatements());
      return this.leave();
   }

   public boolean enterCatchNode(CatchNode catchNode) {
      this.enterDefault(catchNode);
      this.type("CatchClause");
      this.comma();
      this.property("param");
      catchNode.getException().accept(this);
      this.comma();
      Node guard = catchNode.getExceptionCondition();
      if (guard != null) {
         this.property("guard");
         guard.accept(this);
         this.comma();
      }

      this.property("body");
      catchNode.getBody().accept(this);
      return this.leave();
   }

   public boolean enterContinueNode(ContinueNode continueNode) {
      this.enterDefault(continueNode);
      this.type("ContinueStatement");
      this.comma();
      String label = continueNode.getLabelName();
      if (label != null) {
         this.property("label", label);
      } else {
         this.property("label");
         this.nullValue();
      }

      return this.leave();
   }

   public boolean enterEmptyNode(EmptyNode emptyNode) {
      this.enterDefault(emptyNode);
      this.type("EmptyStatement");
      return this.leave();
   }

   public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
      Node expression = expressionStatement.getExpression();
      if (expression instanceof RuntimeNode) {
         expression.accept(this);
         return false;
      } else {
         this.enterDefault(expressionStatement);
         this.type("ExpressionStatement");
         this.comma();
         this.property("expression");
         expression.accept(this);
         return this.leave();
      }
   }

   public boolean enterBlockStatement(BlockStatement blockStatement) {
      this.enterDefault(blockStatement);
      this.type("BlockStatement");
      this.comma();
      this.property("block");
      blockStatement.getBlock().accept(this);
      return this.leave();
   }

   public boolean enterForNode(ForNode forNode) {
      this.enterDefault(forNode);
      Expression init;
      JoinPredecessorExpression test;
      if (!forNode.isForIn() && (!forNode.isForEach() || forNode.getInit() == null)) {
         this.type("ForStatement");
         this.comma();
         init = forNode.getInit();
         this.property("init");
         if (init != null) {
            init.accept(this);
         } else {
            this.nullValue();
         }

         this.comma();
         test = forNode.getTest();
         this.property("test");
         if (test != null) {
            test.accept(this);
         } else {
            this.nullValue();
         }

         this.comma();
         Node update = forNode.getModify();
         this.property("update");
         if (update != null) {
            update.accept(this);
         } else {
            this.nullValue();
         }

         this.comma();
         this.property("body");
         forNode.getBody().accept(this);
      } else {
         this.type("ForInStatement");
         this.comma();
         init = forNode.getInit();

         assert init != null;

         this.property("left");
         init.accept(this);
         this.comma();
         test = forNode.getModify();

         assert test != null;

         this.property("right");
         test.accept(this);
         this.comma();
         this.property("body");
         forNode.getBody().accept(this);
         this.comma();
         this.property("each", forNode.isForEach());
      }

      return this.leave();
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      boolean program = functionNode.isProgram();
      if (program) {
         return this.emitProgram(functionNode);
      } else {
         this.enterDefault(functionNode);
         String name;
         if (functionNode.isDeclared()) {
            name = "FunctionDeclaration";
         } else {
            name = "FunctionExpression";
         }

         this.type(name);
         this.comma();
         this.property("id");
         FunctionNode.Kind kind = functionNode.getKind();
         if (!functionNode.isAnonymous() && kind != FunctionNode.Kind.GETTER && kind != FunctionNode.Kind.SETTER) {
            functionNode.getIdent().accept(this);
         } else {
            this.nullValue();
         }

         this.comma();
         this.array("params", functionNode.getParameters());
         this.comma();
         this.arrayStart("defaults");
         this.arrayEnd();
         this.comma();
         this.property("rest");
         this.nullValue();
         this.comma();
         this.property("body");
         functionNode.getBody().accept(this);
         this.comma();
         this.property("generator", false);
         this.comma();
         this.property("expression", false);
         return this.leave();
      }
   }

   private boolean emitProgram(FunctionNode functionNode) {
      this.enterDefault(functionNode);
      this.type("Program");
      this.comma();
      List<Statement> stats = functionNode.getBody().getStatements();
      int size = stats.size();
      int idx = 0;
      this.arrayStart("body");

      for(Iterator var5 = stats.iterator(); var5.hasNext(); ++idx) {
         Node stat = (Node)var5.next();
         stat.accept(this);
         if (idx != size - 1) {
            this.comma();
         }
      }

      this.arrayEnd();
      return this.leave();
   }

   public boolean enterIdentNode(IdentNode identNode) {
      this.enterDefault(identNode);
      String name = identNode.getName();
      if ("this".equals(name)) {
         this.type("ThisExpression");
      } else {
         this.type("Identifier");
         this.comma();
         this.property("name", identNode.getName());
      }

      return this.leave();
   }

   public boolean enterIfNode(IfNode ifNode) {
      this.enterDefault(ifNode);
      this.type("IfStatement");
      this.comma();
      this.property("test");
      ifNode.getTest().accept(this);
      this.comma();
      this.property("consequent");
      ifNode.getPass().accept(this);
      Node elsePart = ifNode.getFail();
      this.comma();
      this.property("alternate");
      if (elsePart != null) {
         elsePart.accept(this);
      } else {
         this.nullValue();
      }

      return this.leave();
   }

   public boolean enterIndexNode(IndexNode indexNode) {
      this.enterDefault(indexNode);
      this.type("MemberExpression");
      this.comma();
      this.property("object");
      indexNode.getBase().accept(this);
      this.comma();
      this.property("property");
      indexNode.getIndex().accept(this);
      this.comma();
      this.property("computed", true);
      return this.leave();
   }

   public boolean enterLabelNode(LabelNode labelNode) {
      this.enterDefault(labelNode);
      this.type("LabeledStatement");
      this.comma();
      this.property("label", labelNode.getLabelName());
      this.comma();
      this.property("body");
      labelNode.getBody().accept(this);
      return this.leave();
   }

   public boolean enterLiteralNode(LiteralNode literalNode) {
      this.enterDefault(literalNode);
      if (literalNode instanceof LiteralNode.ArrayLiteralNode) {
         this.type("ArrayExpression");
         this.comma();
         this.array("elements", ((LiteralNode.ArrayLiteralNode)literalNode).getElementExpressions());
      } else {
         this.type("Literal");
         this.comma();
         this.property("value");
         Object value = literalNode.getValue();
         if (value instanceof Lexer.RegexToken) {
            Lexer.RegexToken regex = (Lexer.RegexToken)value;
            StringBuilder regexBuf = new StringBuilder();
            regexBuf.append('/');
            regexBuf.append(regex.getExpression());
            regexBuf.append('/');
            regexBuf.append(regex.getOptions());
            this.buf.append(quote(regexBuf.toString()));
         } else {
            String str = literalNode.getString();
            this.buf.append(literalNode.isString() ? quote("$" + str) : str);
         }
      }

      return this.leave();
   }

   public boolean enterObjectNode(ObjectNode objectNode) {
      this.enterDefault(objectNode);
      this.type("ObjectExpression");
      this.comma();
      this.array("properties", objectNode.getElements());
      return this.leave();
   }

   public boolean enterPropertyNode(PropertyNode propertyNode) {
      Node key = propertyNode.getKey();
      Node value = propertyNode.getValue();
      if (value != null) {
         this.objectStart();
         this.location(propertyNode);
         this.property("key");
         key.accept(this);
         this.comma();
         this.property("value");
         value.accept(this);
         this.comma();
         this.property("kind", "init");
         this.objectEnd();
      } else {
         Node getter = propertyNode.getGetter();
         if (getter != null) {
            this.objectStart();
            this.location(propertyNode);
            this.property("key");
            key.accept(this);
            this.comma();
            this.property("value");
            getter.accept(this);
            this.comma();
            this.property("kind", "get");
            this.objectEnd();
         }

         Node setter = propertyNode.getSetter();
         if (setter != null) {
            if (getter != null) {
               this.comma();
            }

            this.objectStart();
            this.location(propertyNode);
            this.property("key");
            key.accept(this);
            this.comma();
            this.property("value");
            setter.accept(this);
            this.comma();
            this.property("kind", "set");
            this.objectEnd();
         }
      }

      return false;
   }

   public boolean enterReturnNode(ReturnNode returnNode) {
      this.enterDefault(returnNode);
      this.type("ReturnStatement");
      this.comma();
      Node arg = returnNode.getExpression();
      this.property("argument");
      if (arg != null) {
         arg.accept(this);
      } else {
         this.nullValue();
      }

      return this.leave();
   }

   public boolean enterRuntimeNode(RuntimeNode runtimeNode) {
      RuntimeNode.Request req = runtimeNode.getRequest();
      if (req == RuntimeNode.Request.DEBUGGER) {
         this.enterDefault(runtimeNode);
         this.type("DebuggerStatement");
         return this.leave();
      } else {
         return false;
      }
   }

   public boolean enterSplitNode(SplitNode splitNode) {
      return false;
   }

   public boolean enterSwitchNode(SwitchNode switchNode) {
      this.enterDefault(switchNode);
      this.type("SwitchStatement");
      this.comma();
      this.property("discriminant");
      switchNode.getExpression().accept(this);
      this.comma();
      this.array("cases", switchNode.getCases());
      return this.leave();
   }

   public boolean enterTernaryNode(TernaryNode ternaryNode) {
      this.enterDefault(ternaryNode);
      this.type("ConditionalExpression");
      this.comma();
      this.property("test");
      ternaryNode.getTest().accept(this);
      this.comma();
      this.property("consequent");
      ternaryNode.getTrueExpression().accept(this);
      this.comma();
      this.property("alternate");
      ternaryNode.getFalseExpression().accept(this);
      return this.leave();
   }

   public boolean enterThrowNode(ThrowNode throwNode) {
      this.enterDefault(throwNode);
      this.type("ThrowStatement");
      this.comma();
      this.property("argument");
      throwNode.getExpression().accept(this);
      return this.leave();
   }

   public boolean enterTryNode(TryNode tryNode) {
      this.enterDefault(tryNode);
      this.type("TryStatement");
      this.comma();
      this.property("block");
      tryNode.getBody().accept(this);
      this.comma();
      List<? extends Node> catches = tryNode.getCatches();
      List<CatchNode> guarded = new ArrayList();
      CatchNode unguarded = null;
      if (catches != null) {
         Iterator var5 = catches.iterator();

         while(var5.hasNext()) {
            Node n = (Node)var5.next();
            CatchNode cn = (CatchNode)n;
            if (cn.getExceptionCondition() != null) {
               guarded.add(cn);
            } else {
               assert unguarded == null : "too many unguarded?";

               unguarded = cn;
            }
         }
      }

      this.array("guardedHandlers", guarded);
      this.comma();
      this.property("handler");
      if (unguarded != null) {
         unguarded.accept(this);
      } else {
         this.nullValue();
      }

      this.comma();
      this.property("finalizer");
      Node finallyNode = tryNode.getFinallyBody();
      if (finallyNode != null) {
         finallyNode.accept(this);
      } else {
         this.nullValue();
      }

      return this.leave();
   }

   public boolean enterUnaryNode(UnaryNode unaryNode) {
      this.enterDefault(unaryNode);
      TokenType tokenType = unaryNode.tokenType();
      if (tokenType == TokenType.NEW) {
         this.type("NewExpression");
         this.comma();
         CallNode callNode = (CallNode)unaryNode.getExpression();
         this.property("callee");
         callNode.getFunction().accept(this);
         this.comma();
         this.array("arguments", callNode.getArgs());
      } else {
         boolean prefix;
         String operator;
         switch(tokenType) {
         case INCPOSTFIX:
            prefix = false;
            operator = "++";
            break;
         case DECPOSTFIX:
            prefix = false;
            operator = "--";
            break;
         case INCPREFIX:
            operator = "++";
            prefix = true;
            break;
         case DECPREFIX:
            operator = "--";
            prefix = true;
            break;
         default:
            prefix = true;
            operator = tokenType.getName();
         }

         this.type(unaryNode.isAssignment() ? "UpdateExpression" : "UnaryExpression");
         this.comma();
         this.property("operator", operator);
         this.comma();
         this.property("prefix", prefix);
         this.comma();
         this.property("argument");
         unaryNode.getExpression().accept(this);
      }

      return this.leave();
   }

   public boolean enterVarNode(VarNode varNode) {
      Node init = varNode.getInit();
      if (init instanceof FunctionNode && ((FunctionNode)init).isDeclared()) {
         init.accept(this);
         return false;
      } else {
         this.enterDefault(varNode);
         this.type("VariableDeclaration");
         this.comma();
         this.arrayStart("declarations");
         this.objectStart();
         this.location(varNode.getName());
         this.type("VariableDeclarator");
         this.comma();
         this.property("id");
         varNode.getName().accept(this);
         this.comma();
         this.property("init");
         if (init != null) {
            init.accept(this);
         } else {
            this.nullValue();
         }

         this.objectEnd();
         this.arrayEnd();
         return this.leave();
      }
   }

   public boolean enterWhileNode(WhileNode whileNode) {
      this.enterDefault(whileNode);
      this.type(whileNode.isDoWhile() ? "DoWhileStatement" : "WhileStatement");
      this.comma();
      if (whileNode.isDoWhile()) {
         this.property("body");
         whileNode.getBody().accept(this);
         this.comma();
         this.property("test");
         whileNode.getTest().accept(this);
      } else {
         this.property("test");
         whileNode.getTest().accept(this);
         this.comma();
         this.property("body");
         whileNode.getBody().accept(this);
      }

      return this.leave();
   }

   public boolean enterWithNode(WithNode withNode) {
      this.enterDefault(withNode);
      this.type("WithStatement");
      this.comma();
      this.property("object");
      withNode.getExpression().accept(this);
      this.comma();
      this.property("body");
      withNode.getBody().accept(this);
      return this.leave();
   }

   private JSONWriter(boolean includeLocation) {
      this.includeLocation = includeLocation;
   }

   private String getString() {
      return this.buf.toString();
   }

   private void property(String key, String value, boolean escape) {
      this.buf.append('"');
      this.buf.append(key);
      this.buf.append("\":");
      if (value != null) {
         if (escape) {
            this.buf.append('"');
         }

         this.buf.append(value);
         if (escape) {
            this.buf.append('"');
         }
      }

   }

   private void property(String key, String value) {
      this.property(key, value, true);
   }

   private void property(String key, boolean value) {
      this.property(key, Boolean.toString(value), false);
   }

   private void property(String key, int value) {
      this.property(key, Integer.toString(value), false);
   }

   private void property(String key) {
      this.property(key, (String)null);
   }

   private void type(String value) {
      this.property("type", value);
   }

   private void objectStart(String name) {
      this.buf.append('"');
      this.buf.append(name);
      this.buf.append("\":{");
   }

   private void objectStart() {
      this.buf.append('{');
   }

   private void objectEnd() {
      this.buf.append('}');
   }

   private void array(String name, List<? extends Node> nodes) {
      int size = nodes.size();
      int idx = 0;
      this.arrayStart(name);

      for(Iterator var5 = nodes.iterator(); var5.hasNext(); ++idx) {
         Node node = (Node)var5.next();
         if (node != null) {
            node.accept(this);
         } else {
            this.nullValue();
         }

         if (idx != size - 1) {
            this.comma();
         }
      }

      this.arrayEnd();
   }

   private void arrayStart(String name) {
      this.buf.append('"');
      this.buf.append(name);
      this.buf.append('"');
      this.buf.append(':');
      this.buf.append('[');
   }

   private void arrayEnd() {
      this.buf.append(']');
   }

   private void comma() {
      this.buf.append(',');
   }

   private void nullValue() {
      this.buf.append("null");
   }

   private void location(Node node) {
      if (this.includeLocation) {
         this.objectStart("loc");
         Source src = this.lc.getCurrentFunction().getSource();
         this.property("source", src.getName());
         this.comma();
         this.objectStart("start");
         int start = node.getStart();
         this.property("line", src.getLine(start));
         this.comma();
         this.property("column", src.getColumn(start));
         this.objectEnd();
         this.comma();
         this.objectStart("end");
         int end = node.getFinish();
         this.property("line", src.getLine(end));
         this.comma();
         this.property("column", src.getColumn(end));
         this.objectEnd();
         this.objectEnd();
         this.comma();
      }

   }

   private static String quote(String str) {
      return JSONParser.quote(str);
   }
}
