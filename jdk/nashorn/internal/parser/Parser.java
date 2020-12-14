package jdk.nashorn.internal.parser;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.internal.dynalink.support.NameCodec;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.Namespace;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BaseNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.BlockLexicalContext;
import jdk.nashorn.internal.ir.BlockStatement;
import jdk.nashorn.internal.ir.BreakNode;
import jdk.nashorn.internal.ir.BreakableNode;
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
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.LoopNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.ir.PropertyKey;
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.ReturnNode;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.TernaryNode;
import jdk.nashorn.internal.ir.ThrowNode;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.debug.ASTWriter;
import jdk.nashorn.internal.ir.debug.PrintVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.Timing;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "parser"
)
public class Parser extends AbstractParser implements Loggable {
   private static final String ARGUMENTS_NAME;
   private final ScriptEnvironment env;
   private final boolean scripting;
   private List<Statement> functionDeclarations;
   private final BlockLexicalContext lc;
   private final Deque<Object> defaultNames;
   private final Namespace namespace;
   private final DebugLogger log;
   protected final Lexer.LineInfoReceiver lineInfoReceiver;
   private RecompilableScriptFunctionData reparsedFunction;

   public Parser(ScriptEnvironment env, Source source, ErrorManager errors) {
      this(env, source, errors, env._strict, (DebugLogger)null);
   }

   public Parser(ScriptEnvironment env, Source source, ErrorManager errors, boolean strict, DebugLogger log) {
      this(env, source, errors, strict, 0, log);
   }

   public Parser(ScriptEnvironment env, Source source, ErrorManager errors, boolean strict, int lineOffset, DebugLogger log) {
      super(source, errors, strict, lineOffset);
      this.lc = new BlockLexicalContext();
      this.defaultNames = new ArrayDeque();
      this.env = env;
      this.namespace = new Namespace(env.getNamespace());
      this.scripting = env._scripting;
      if (this.scripting) {
         this.lineInfoReceiver = new Lexer.LineInfoReceiver() {
            public void lineInfo(int receiverLine, int receiverLinePosition) {
               Parser.this.line = receiverLine;
               Parser.this.linePosition = receiverLinePosition;
            }
         };
      } else {
         this.lineInfoReceiver = null;
      }

      this.log = log == null ? DebugLogger.DISABLED_LOGGER : log;
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   public void setFunctionName(String name) {
      this.defaultNames.push(this.createIdentNode(0L, 0, name));
   }

   public void setReparsedFunction(RecompilableScriptFunctionData reparsedFunction) {
      this.reparsedFunction = reparsedFunction;
   }

   public FunctionNode parse() {
      return this.parse(CompilerConstants.PROGRAM.symbolName(), 0, this.source.getLength(), false);
   }

   public FunctionNode parse(String scriptName, int startPos, int len, boolean allowPropertyFunction) {
      boolean isTimingEnabled = this.env.isTimingEnabled();
      long t0 = isTimingEnabled ? System.nanoTime() : 0L;
      this.log.info(this, " begin for '", scriptName, "'");
      boolean var15 = false;

      FunctionNode var8;
      String end;
      label114: {
         try {
            var15 = true;
            this.stream = new TokenStream();
            this.lexer = new Lexer(this.source, startPos, len, this.stream, this.scripting && !this.env._no_syntax_extensions, this.reparsedFunction != null);
            this.lexer.line = this.lexer.pendingLine = this.lineOffset + 1;
            this.line = this.lineOffset;
            this.k = -1;
            this.next();
            var8 = this.program(scriptName, allowPropertyFunction);
            var15 = false;
            break label114;
         } catch (Exception var16) {
            this.handleParseException(var16);
            end = null;
            var15 = false;
         } finally {
            if (var15) {
               String end = this + " end '" + scriptName + "'";
               if (isTimingEnabled) {
                  this.env._timing.accumulateTime(this.toString(), System.nanoTime() - t0);
                  this.log.info(end, "' in ", Timing.toMillisPrint(System.nanoTime() - t0), " ms");
               } else {
                  this.log.info(end);
               }

            }
         }

         String end = this + " end '" + scriptName + "'";
         if (isTimingEnabled) {
            this.env._timing.accumulateTime(this.toString(), System.nanoTime() - t0);
            this.log.info(end, "' in ", Timing.toMillisPrint(System.nanoTime() - t0), " ms");
         } else {
            this.log.info(end);
         }

         return end;
      }

      end = this + " end '" + scriptName + "'";
      if (isTimingEnabled) {
         this.env._timing.accumulateTime(this.toString(), System.nanoTime() - t0);
         this.log.info(end, "' in ", Timing.toMillisPrint(System.nanoTime() - t0), " ms");
      } else {
         this.log.info(end);
      }

      return var8;
   }

   public List<IdentNode> parseFormalParameterList() {
      try {
         this.stream = new TokenStream();
         this.lexer = new Lexer(this.source, this.stream, this.scripting && !this.env._no_syntax_extensions);
         this.k = -1;
         this.next();
         return this.formalParameterList(TokenType.EOF);
      } catch (Exception var2) {
         this.handleParseException(var2);
         return null;
      }
   }

   public FunctionNode parseFunctionBody() {
      try {
         this.stream = new TokenStream();
         this.lexer = new Lexer(this.source, this.stream, this.scripting && !this.env._no_syntax_extensions);
         int functionLine = this.line;
         this.k = -1;
         this.next();
         long functionToken = Token.toDesc(TokenType.FUNCTION, 0, this.source.getLength());
         FunctionNode function = this.newFunctionNode(functionToken, new IdentNode(functionToken, Token.descPosition(functionToken), CompilerConstants.PROGRAM.symbolName()), new ArrayList(), FunctionNode.Kind.NORMAL, functionLine);
         this.functionDeclarations = new ArrayList();
         this.sourceElements(false);
         this.addFunctionDeclarations(function);
         this.functionDeclarations = null;
         this.expect(TokenType.EOF);
         function.setFinish(this.source.getLength() - 1);
         function = this.restoreFunctionNode(function, this.token);
         function = function.setBody(this.lc, function.getBody().setNeedsScope(this.lc));
         this.printAST(function);
         return function;
      } catch (Exception var5) {
         this.handleParseException(var5);
         return null;
      }
   }

   private void handleParseException(Exception e) {
      String message = e.getMessage();
      if (message == null) {
         message = e.toString();
      }

      if (e instanceof ParserException) {
         this.errors.error((ParserException)e);
      } else {
         this.errors.error(message);
      }

      if (this.env._dump_on_error) {
         e.printStackTrace(this.env.getErr());
      }

   }

   private void recover(Exception e) {
      if (e != null) {
         String message = e.getMessage();
         if (message == null) {
            message = e.toString();
         }

         if (e instanceof ParserException) {
            this.errors.error((ParserException)e);
         } else {
            this.errors.error(message);
         }

         if (this.env._dump_on_error) {
            e.printStackTrace(this.env.getErr());
         }
      }

      while(true) {
         switch(this.type) {
         case EOF:
            return;
         case EOL:
         case SEMICOLON:
         case RBRACE:
            this.next();
            return;
         default:
            this.nextOrEOL();
         }
      }
   }

   private Block newBlock() {
      return (Block)this.lc.push(new Block(this.token, Token.descPosition(this.token), new Statement[0]));
   }

   private FunctionNode newFunctionNode(long startToken, IdentNode ident, List<IdentNode> parameters, FunctionNode.Kind kind, int functionLine) {
      StringBuilder sb = new StringBuilder();
      FunctionNode parentFunction = this.lc.getCurrentFunction();
      if (parentFunction != null && !parentFunction.isProgram()) {
         sb.append(parentFunction.getName()).append(CompilerConstants.NESTED_FUNCTION_SEPARATOR.symbolName());
      }

      assert ident.getName() != null;

      sb.append(ident.getName());
      String name = this.namespace.uniqueName(sb.toString());

      assert parentFunction != null || name.equals(CompilerConstants.PROGRAM.symbolName()) || name.startsWith("Recompilation$") : "name = " + name;

      int flags = 0;
      if (this.isStrictMode) {
         flags |= 4;
      }

      if (parentFunction == null) {
         flags |= 8192;
      }

      FunctionNode functionNode = new FunctionNode(this.source, functionLine, this.token, Token.descPosition(this.token), startToken, this.namespace, ident, name, parameters, kind, flags);
      this.lc.push(functionNode);
      this.newBlock();
      return functionNode;
   }

   private Block restoreBlock(Block block) {
      return (Block)this.lc.pop(block);
   }

   private FunctionNode restoreFunctionNode(FunctionNode functionNode, long lastToken) {
      Block newBody = this.restoreBlock(this.lc.getFunctionBody(functionNode));
      return ((FunctionNode)this.lc.pop(functionNode)).setBody(this.lc, newBody).setLastToken(this.lc, lastToken);
   }

   private Block getBlock(boolean needsBraces) {
      Block newBlock = this.newBlock();

      try {
         if (needsBraces) {
            this.expect(TokenType.LBRACE);
         }

         this.statementList();
      } finally {
         newBlock = this.restoreBlock(newBlock);
      }

      int possibleEnd = Token.descPosition(this.token) + Token.descLength(this.token);
      if (needsBraces) {
         this.expect(TokenType.RBRACE);
      }

      newBlock.setFinish(possibleEnd);
      return newBlock;
   }

   private Block getStatement() {
      if (this.type == TokenType.LBRACE) {
         return this.getBlock(true);
      } else {
         Block newBlock = this.newBlock();

         try {
            this.statement(false, false, true);
         } finally {
            newBlock = this.restoreBlock(newBlock);
         }

         return newBlock;
      }
   }

   private void detectSpecialFunction(IdentNode ident) {
      String name = ident.getName();
      if (CompilerConstants.EVAL.symbolName().equals(name)) {
         markEval(this.lc);
      }

   }

   private void detectSpecialProperty(IdentNode ident) {
      if (isArguments(ident)) {
         this.lc.setFlag(this.lc.getCurrentFunction(), 8);
      }

   }

   private boolean useBlockScope() {
      return this.env._es6;
   }

   private static boolean isArguments(String name) {
      return ARGUMENTS_NAME.equals(name);
   }

   private static boolean isArguments(IdentNode ident) {
      return isArguments(ident.getName());
   }

   private static boolean checkIdentLValue(IdentNode ident) {
      return ident.tokenType().getKind() != TokenKind.KEYWORD;
   }

   private Expression verifyAssignment(long op, Expression lhs, Expression rhs) {
      TokenType opType = Token.descType(op);
      switch(opType) {
      case ASSIGN:
      case ASSIGN_ADD:
      case ASSIGN_BIT_AND:
      case ASSIGN_BIT_OR:
      case ASSIGN_BIT_XOR:
      case ASSIGN_DIV:
      case ASSIGN_MOD:
      case ASSIGN_MUL:
      case ASSIGN_SAR:
      case ASSIGN_SHL:
      case ASSIGN_SHR:
      case ASSIGN_SUB:
         if (!(lhs instanceof AccessNode) && !(lhs instanceof IndexNode) && !(lhs instanceof IdentNode)) {
            return this.referenceError(lhs, rhs, this.env._early_lvalue_error);
         } else if (lhs instanceof IdentNode) {
            if (!checkIdentLValue((IdentNode)lhs)) {
               return this.referenceError(lhs, rhs, false);
            } else {
               this.verifyStrictIdent((IdentNode)lhs, "assignment");
            }
         }
      default:
         return BinaryNode.isLogical(opType) ? new BinaryNode(op, new JoinPredecessorExpression(lhs), new JoinPredecessorExpression(rhs)) : new BinaryNode(op, lhs, rhs);
      }
   }

   private static UnaryNode incDecExpression(long firstToken, TokenType tokenType, Expression expression, boolean isPostfix) {
      return isPostfix ? new UnaryNode(Token.recast(firstToken, tokenType == TokenType.DECPREFIX ? TokenType.DECPOSTFIX : TokenType.INCPOSTFIX), expression.getStart(), Token.descPosition(firstToken) + Token.descLength(firstToken), expression) : new UnaryNode(firstToken, expression);
   }

   private FunctionNode program(String scriptName, boolean allowPropertyFunction) {
      long functionToken = Token.toDesc(TokenType.FUNCTION, Token.descPosition(Token.withDelimiter(this.token)), this.source.getLength());
      int functionLine = this.line;
      FunctionNode script = this.newFunctionNode(functionToken, new IdentNode(functionToken, Token.descPosition(functionToken), scriptName), new ArrayList(), FunctionNode.Kind.SCRIPT, functionLine);
      this.functionDeclarations = new ArrayList();
      this.sourceElements(allowPropertyFunction);
      this.addFunctionDeclarations(script);
      this.functionDeclarations = null;
      this.expect(TokenType.EOF);
      script.setFinish(this.source.getLength() - 1);
      script = this.restoreFunctionNode(script, this.token);
      script = script.setBody(this.lc, script.getBody().setNeedsScope(this.lc));
      return script;
   }

   private String getDirective(Node stmt) {
      if (stmt instanceof ExpressionStatement) {
         Node expr = ((ExpressionStatement)stmt).getExpression();
         if (expr instanceof LiteralNode) {
            LiteralNode<?> lit = (LiteralNode)expr;
            long litToken = lit.getToken();
            TokenType tt = Token.descType(litToken);
            if (tt == TokenType.STRING || tt == TokenType.ESCSTRING) {
               return this.source.getString(lit.getStart(), Token.descLength(litToken));
            }
         }
      }

      return null;
   }

   private void sourceElements(boolean shouldAllowPropertyFunction) {
      List<Node> directiveStmts = null;
      boolean checkDirective = true;
      boolean allowPropertyFunction = shouldAllowPropertyFunction;
      boolean oldStrictMode = this.isStrictMode;

      try {
         for(; this.type != TokenType.EOF && this.type != TokenType.RBRACE; this.stream.commit(this.k)) {
            try {
               this.statement(true, allowPropertyFunction, false);
               allowPropertyFunction = false;
               if (checkDirective) {
                  Node lastStatement = this.lc.getLastStatement();
                  String directive = this.getDirective(lastStatement);
                  checkDirective = directive != null;
                  if (checkDirective) {
                     if (!oldStrictMode) {
                        if (directiveStmts == null) {
                           directiveStmts = new ArrayList();
                        }

                        directiveStmts.add(lastStatement);
                     }

                     if ("use strict".equals(directive)) {
                        this.isStrictMode = true;
                        FunctionNode function = this.lc.getCurrentFunction();
                        this.lc.setFlag(this.lc.getCurrentFunction(), 4);
                        if (!oldStrictMode && directiveStmts != null) {
                           Iterator var9 = directiveStmts.iterator();

                           while(var9.hasNext()) {
                              Node statement = (Node)var9.next();
                              this.getValue(statement.getToken());
                           }

                           this.verifyStrictIdent(function.getIdent(), "function name");
                           var9 = function.getParameters().iterator();

                           while(var9.hasNext()) {
                              IdentNode param = (IdentNode)var9.next();
                              this.verifyStrictIdent(param, "function parameter");
                           }
                        }
                     } else if (Context.DEBUG) {
                        int flag = FunctionNode.getDirectiveFlag(directive);
                        if (flag != 0) {
                           FunctionNode function = this.lc.getCurrentFunction();
                           this.lc.setFlag(function, flag);
                        }
                     }
                  }
               }
            } catch (Exception var14) {
               this.recover(var14);
            }
         }
      } finally {
         this.isStrictMode = oldStrictMode;
      }

   }

   private void statement() {
      this.statement(false, false, false);
   }

   private void statement(boolean topLevel, boolean allowPropertyFunction, boolean singleStatement) {
      if (this.type == TokenType.FUNCTION) {
         this.functionExpression(true, topLevel);
      } else {
         switch(this.type) {
         case EOF:
         case RPAREN:
         case RBRACKET:
            this.expect(TokenType.SEMICOLON);
            break;
         case EOL:
         case RBRACE:
         case ASSIGN:
         case ASSIGN_ADD:
         case ASSIGN_BIT_AND:
         case ASSIGN_BIT_OR:
         case ASSIGN_BIT_XOR:
         case ASSIGN_DIV:
         case ASSIGN_MOD:
         case ASSIGN_MUL:
         case ASSIGN_SAR:
         case ASSIGN_SHL:
         case ASSIGN_SHR:
         case ASSIGN_SUB:
         default:
            if (!this.useBlockScope() || this.type != TokenType.LET && this.type != TokenType.CONST) {
               if (this.env._const_as_var && this.type == TokenType.CONST) {
                  this.variableStatement(TokenType.VAR, true);
               } else {
                  if (this.type == TokenType.IDENT || this.isNonStrictModeIdent()) {
                     if (this.T(this.k + 1) == TokenType.COLON) {
                        this.labelStatement();
                        return;
                     }

                     if (allowPropertyFunction) {
                        String ident = (String)this.getValue();
                        long propertyToken = this.token;
                        int propertyLine = this.line;
                        if ("get".equals(ident)) {
                           this.next();
                           this.addPropertyFunctionStatement(this.propertyGetterFunction(propertyToken, propertyLine));
                           return;
                        }

                        if ("set".equals(ident)) {
                           this.next();
                           this.addPropertyFunctionStatement(this.propertySetterFunction(propertyToken, propertyLine));
                           return;
                        }
                     }
                  }

                  this.expressionStatement();
               }
            } else {
               if (singleStatement) {
                  throw this.error(AbstractParser.message("expected.stmt", this.type.getName() + " declaration"), this.token);
               }

               this.variableStatement(this.type, true);
            }
            break;
         case SEMICOLON:
            this.emptyStatement();
            break;
         case LBRACE:
            this.block();
            break;
         case VAR:
            this.variableStatement(this.type, true);
            break;
         case IF:
            this.ifStatement();
            break;
         case FOR:
            this.forStatement();
            break;
         case WHILE:
            this.whileStatement();
            break;
         case DO:
            this.doStatement();
            break;
         case CONTINUE:
            this.continueStatement();
            break;
         case BREAK:
            this.breakStatement();
            break;
         case RETURN:
            this.returnStatement();
            break;
         case YIELD:
            this.yieldStatement();
            break;
         case WITH:
            this.withStatement();
            break;
         case SWITCH:
            this.switchStatement();
            break;
         case THROW:
            this.throwStatement();
            break;
         case TRY:
            this.tryStatement();
            break;
         case DEBUGGER:
            this.debuggerStatement();
         }

      }
   }

   private void addPropertyFunctionStatement(Parser.PropertyFunction propertyFunction) {
      FunctionNode fn = propertyFunction.functionNode;
      this.functionDeclarations.add(new ExpressionStatement(fn.getLineNumber(), fn.getToken(), this.finish, fn));
   }

   private void block() {
      this.appendStatement(new BlockStatement(this.line, this.getBlock(true)));
   }

   private void statementList() {
      while(true) {
         if (this.type != TokenType.EOF) {
            switch(this.type) {
            case EOF:
            case RBRACE:
            case CASE:
            case DEFAULT:
               break;
            default:
               this.statement();
               continue;
            }
         }

         return;
      }
   }

   private void verifyStrictIdent(IdentNode ident, String contextString) {
      if (this.isStrictMode) {
         String var3 = ident.getName();
         byte var4 = -1;
         switch(var3.hashCode()) {
         case -2035517098:
            if (var3.equals("arguments")) {
               var4 = 1;
            }
            break;
         case 3125404:
            if (var3.equals("eval")) {
               var4 = 0;
            }
         }

         switch(var4) {
         case 0:
         case 1:
            throw this.error(AbstractParser.message("strict.name", ident.getName(), contextString), ident.getToken());
         default:
            if (ident.isFutureStrictName()) {
               throw this.error(AbstractParser.message("strict.name", ident.getName(), contextString), ident.getToken());
            }
         }
      }

   }

   private List<VarNode> variableStatement(TokenType varType, boolean isStatement) {
      this.next();
      List<VarNode> vars = new ArrayList();
      int varFlags = 0;
      if (varType == TokenType.LET) {
         varFlags |= 1;
      } else if (varType == TokenType.CONST) {
         varFlags |= 2;
      }

      while(true) {
         int varLine = this.line;
         long varToken = this.token;
         IdentNode name = this.getIdent();
         this.verifyStrictIdent(name, "variable name");
         Expression init = null;
         if (this.type == TokenType.ASSIGN) {
            this.next();
            this.defaultNames.push(name);

            try {
               init = this.assignmentExpression(!isStatement);
            } finally {
               this.defaultNames.pop();
            }
         } else if (varType == TokenType.CONST) {
            throw this.error(AbstractParser.message("missing.const.assignment", name.getName()));
         }

         VarNode var = new VarNode(varLine, varToken, this.finish, name.setIsDeclaredHere(), init, varFlags);
         vars.add(var);
         this.appendStatement(var);
         if (this.type != TokenType.COMMARIGHT) {
            if (isStatement) {
               boolean semicolon = this.type == TokenType.SEMICOLON;
               this.endOfLine();
               if (semicolon) {
                  this.lc.getCurrentBlock().setFinish(this.finish);
               }
            }

            return vars;
         }

         this.next();
      }
   }

   private void emptyStatement() {
      if (this.env._empty_statements) {
         this.appendStatement(new EmptyNode(this.line, this.token, Token.descPosition(this.token) + Token.descLength(this.token)));
      }

      this.next();
   }

   private void expressionStatement() {
      int expressionLine = this.line;
      long expressionToken = this.token;
      Expression expression = this.expression();
      ExpressionStatement expressionStatement = null;
      if (expression != null) {
         expressionStatement = new ExpressionStatement(expressionLine, expressionToken, this.finish, expression);
         this.appendStatement(expressionStatement);
      } else {
         this.expect((TokenType)null);
      }

      this.endOfLine();
      if (expressionStatement != null) {
         expressionStatement.setFinish(this.finish);
         this.lc.getCurrentBlock().setFinish(this.finish);
      }

   }

   private void ifStatement() {
      int ifLine = this.line;
      long ifToken = this.token;
      this.next();
      this.expect(TokenType.LPAREN);
      Expression test = this.expression();
      this.expect(TokenType.RPAREN);
      Block pass = this.getStatement();
      Block fail = null;
      if (this.type == TokenType.ELSE) {
         this.next();
         fail = this.getStatement();
      }

      this.appendStatement(new IfNode(ifLine, ifToken, fail != null ? fail.getFinish() : pass.getFinish(), test, pass, fail));
   }

   private void forStatement() {
      int startLine = this.start;
      Block outer = this.useBlockScope() ? this.newBlock() : null;
      ForNode forNode = new ForNode(this.line, this.token, Token.descPosition(this.token), (Block)null, 0);
      this.lc.push(forNode);

      try {
         this.next();
         if (!this.env._no_syntax_extensions && this.type == TokenType.IDENT && "each".equals(this.getValue())) {
            forNode = forNode.setIsForEach(this.lc);
            this.next();
         }

         this.expect(TokenType.LPAREN);
         List<VarNode> vars = null;
         Expression init;
         switch(this.type) {
         case SEMICOLON:
            break;
         case VAR:
            vars = this.variableStatement(this.type, false);
            break;
         default:
            if (!this.useBlockScope() || this.type != TokenType.LET && this.type != TokenType.CONST) {
               if (this.env._const_as_var && this.type == TokenType.CONST) {
                  vars = this.variableStatement(TokenType.VAR, false);
               } else {
                  init = this.expression(this.unaryExpression(), TokenType.COMMARIGHT.getPrecedence(), true);
                  forNode = forNode.setInit(this.lc, init);
               }
            } else {
               if (this.type == TokenType.LET) {
                  forNode = forNode.setPerIterationScope(this.lc);
               }

               vars = this.variableStatement(this.type, false);
            }
         }

         switch(this.type) {
         case SEMICOLON:
            if (forNode.isForEach()) {
               throw this.error(AbstractParser.message("for.each.without.in"), this.token);
            }

            this.expect(TokenType.SEMICOLON);
            if (this.type != TokenType.SEMICOLON) {
               forNode = forNode.setTest(this.lc, this.joinPredecessorExpression());
            }

            this.expect(TokenType.SEMICOLON);
            if (this.type != TokenType.RPAREN) {
               forNode = forNode.setModify(this.lc, this.joinPredecessorExpression());
            }
            break;
         case IN:
            forNode = forNode.setIsForIn(this.lc).setTest(this.lc, new JoinPredecessorExpression());
            if (vars != null) {
               if (vars.size() != 1) {
                  throw this.error(AbstractParser.message("many.vars.in.for.in.loop"), ((VarNode)vars.get(1)).getToken());
               }

               forNode = forNode.setInit(this.lc, new IdentNode(((VarNode)vars.get(0)).getName()));
            } else {
               init = forNode.getInit();

               assert init != null : "for..in init expression can not be null here";

               if (!(init instanceof AccessNode) && !(init instanceof IndexNode) && !(init instanceof IdentNode)) {
                  throw this.error(AbstractParser.message("not.lvalue.for.in.loop"), init.getToken());
               }

               if (init instanceof IdentNode) {
                  if (!checkIdentLValue((IdentNode)init)) {
                     throw this.error(AbstractParser.message("not.lvalue.for.in.loop"), init.getToken());
                  }

                  this.verifyStrictIdent((IdentNode)init, "for-in iterator");
               }
            }

            this.next();
            forNode = forNode.setModify(this.lc, this.joinPredecessorExpression());
            break;
         default:
            this.expect(TokenType.SEMICOLON);
         }

         this.expect(TokenType.RPAREN);
         Block body = this.getStatement();
         forNode = forNode.setBody(this.lc, body);
         forNode.setFinish(body.getFinish());
         this.appendStatement(forNode);
      } finally {
         this.lc.pop(forNode);
      }

      if (outer != null) {
         outer.setFinish(forNode.getFinish());
         outer = this.restoreBlock(outer);
         this.appendStatement(new BlockStatement(startLine, outer));
      }

   }

   private void whileStatement() {
      long whileToken = this.token;
      this.next();
      WhileNode whileNode = new WhileNode(this.line, whileToken, Token.descPosition(whileToken), false);
      this.lc.push(whileNode);

      try {
         this.expect(TokenType.LPAREN);
         int whileLine = this.line;
         JoinPredecessorExpression test = this.joinPredecessorExpression();
         this.expect(TokenType.RPAREN);
         Block body = this.getStatement();
         this.appendStatement(whileNode = (new WhileNode(whileLine, whileToken, this.finish, false)).setTest(this.lc, test).setBody(this.lc, body));
      } finally {
         this.lc.pop(whileNode);
      }

   }

   private void doStatement() {
      long doToken = this.token;
      this.next();
      WhileNode doWhileNode = new WhileNode(-1, doToken, Token.descPosition(doToken), true);
      this.lc.push(doWhileNode);

      try {
         Block body = this.getStatement();
         this.expect(TokenType.WHILE);
         this.expect(TokenType.LPAREN);
         int doLine = this.line;
         JoinPredecessorExpression test = this.joinPredecessorExpression();
         this.expect(TokenType.RPAREN);
         if (this.type == TokenType.SEMICOLON) {
            this.endOfLine();
         }

         doWhileNode.setFinish(this.finish);
         this.appendStatement(doWhileNode = (new WhileNode(doLine, doToken, this.finish, true)).setBody(this.lc, body).setTest(this.lc, test));
      } finally {
         this.lc.pop(doWhileNode);
      }

   }

   private void continueStatement() {
      int continueLine = this.line;
      long continueToken = this.token;
      this.nextOrEOL();
      LabelNode labelNode = null;
      switch(this.type) {
      default:
         IdentNode ident = this.getIdent();
         labelNode = this.lc.findLabel(ident.getName());
         if (labelNode == null) {
            throw this.error(AbstractParser.message("undefined.label", ident.getName()), ident.getToken());
         }
      case EOF:
      case EOL:
      case SEMICOLON:
      case RBRACE:
         String labelName = labelNode == null ? null : labelNode.getLabelName();
         LoopNode targetNode = this.lc.getContinueTo(labelName);
         if (targetNode == null) {
            throw this.error(AbstractParser.message("illegal.continue.stmt"), continueToken);
         } else {
            this.endOfLine();
            this.appendStatement(new ContinueNode(continueLine, continueToken, this.finish, labelName));
         }
      }
   }

   private void breakStatement() {
      int breakLine = this.line;
      long breakToken = this.token;
      this.nextOrEOL();
      LabelNode labelNode = null;
      switch(this.type) {
      default:
         IdentNode ident = this.getIdent();
         labelNode = this.lc.findLabel(ident.getName());
         if (labelNode == null) {
            throw this.error(AbstractParser.message("undefined.label", ident.getName()), ident.getToken());
         }
      case EOF:
      case EOL:
      case SEMICOLON:
      case RBRACE:
         String labelName = labelNode == null ? null : labelNode.getLabelName();
         BreakableNode targetNode = this.lc.getBreakable(labelName);
         if (targetNode == null) {
            throw this.error(AbstractParser.message("illegal.break.stmt"), breakToken);
         } else {
            this.endOfLine();
            this.appendStatement(new BreakNode(breakLine, breakToken, this.finish, labelName));
         }
      }
   }

   private void returnStatement() {
      if (this.lc.getCurrentFunction().getKind() == FunctionNode.Kind.SCRIPT) {
         throw this.error(AbstractParser.message("invalid.return"));
      } else {
         int returnLine = this.line;
         long returnToken = this.token;
         this.nextOrEOL();
         Expression expression = null;
         switch(this.type) {
         default:
            expression = this.expression();
         case EOF:
         case EOL:
         case SEMICOLON:
         case RBRACE:
            this.endOfLine();
            this.appendStatement(new ReturnNode(returnLine, returnToken, this.finish, expression));
         }
      }
   }

   private void yieldStatement() {
      int yieldLine = this.line;
      long yieldToken = this.token;
      this.nextOrEOL();
      Expression expression = null;
      switch(this.type) {
      default:
         expression = this.expression();
      case EOF:
      case EOL:
      case SEMICOLON:
      case RBRACE:
         this.endOfLine();
         this.appendStatement(new ReturnNode(yieldLine, yieldToken, this.finish, expression));
      }
   }

   private void withStatement() {
      int withLine = this.line;
      long withToken = this.token;
      this.next();
      if (this.isStrictMode) {
         throw this.error(AbstractParser.message("strict.no.with"), withToken);
      } else {
         WithNode withNode = new WithNode(withLine, withToken, this.finish);

         try {
            this.lc.push(withNode);
            this.expect(TokenType.LPAREN);
            withNode = withNode.setExpression(this.lc, this.expression());
            this.expect(TokenType.RPAREN);
            withNode = withNode.setBody(this.lc, this.getStatement());
         } finally {
            this.lc.pop(withNode);
         }

         this.appendStatement(withNode);
      }
   }

   private void switchStatement() {
      int switchLine = this.line;
      long switchToken = this.token;
      this.next();
      SwitchNode switchNode = new SwitchNode(switchLine, switchToken, Token.descPosition(switchToken), (Expression)null, new ArrayList(), (CaseNode)null);
      this.lc.push(switchNode);

      try {
         this.expect(TokenType.LPAREN);
         switchNode = switchNode.setExpression(this.lc, this.expression());
         this.expect(TokenType.RPAREN);
         this.expect(TokenType.LBRACE);
         List<CaseNode> cases = new ArrayList();

         CaseNode defaultCase;
         CaseNode caseNode;
         for(defaultCase = null; this.type != TokenType.RBRACE; cases.add(caseNode)) {
            Expression caseExpression = null;
            long caseToken = this.token;
            switch(this.type) {
            case CASE:
               this.next();
               caseExpression = this.expression();
               break;
            case DEFAULT:
               if (defaultCase != null) {
                  throw this.error(AbstractParser.message("duplicate.default.in.switch"));
               }

               this.next();
               break;
            default:
               this.expect(TokenType.CASE);
            }

            this.expect(TokenType.COLON);
            Block statements = this.getBlock(false);
            caseNode = new CaseNode(caseToken, this.finish, caseExpression, statements);
            statements.setFinish(this.finish);
            if (caseExpression == null) {
               defaultCase = caseNode;
            }
         }

         switchNode = switchNode.setCases(this.lc, cases, defaultCase);
         this.next();
         switchNode.setFinish(this.finish);
         this.appendStatement(switchNode);
      } finally {
         this.lc.pop(switchNode);
      }

   }

   private void labelStatement() {
      long labelToken = this.token;
      IdentNode ident = this.getIdent();
      this.expect(TokenType.COLON);
      if (this.lc.findLabel(ident.getName()) != null) {
         throw this.error(AbstractParser.message("duplicate.label", ident.getName()), labelToken);
      } else {
         LabelNode labelNode = new LabelNode(this.line, labelToken, this.finish, ident.getName(), (Block)null);

         try {
            this.lc.push(labelNode);
            labelNode = labelNode.setBody(this.lc, this.getStatement());
            labelNode.setFinish(this.finish);
            this.appendStatement(labelNode);
         } finally {
            assert this.lc.peek() instanceof LabelNode;

            this.lc.pop(labelNode);
         }

      }
   }

   private void throwStatement() {
      int throwLine = this.line;
      long throwToken = this.token;
      this.nextOrEOL();
      Expression expression = null;
      switch(this.type) {
      default:
         expression = this.expression();
      case EOL:
      case SEMICOLON:
      case RBRACE:
         if (expression == null) {
            throw this.error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
         } else {
            this.endOfLine();
            this.appendStatement(new ThrowNode(throwLine, throwToken, this.finish, expression, false));
         }
      }
   }

   private void tryStatement() {
      int tryLine = this.line;
      long tryToken = this.token;
      this.next();
      int startLine = this.line;
      Block outer = this.newBlock();

      try {
         Block tryBody = this.getBlock(true);
         ArrayList catchBlocks = new ArrayList();

         while(true) {
            if (this.type == TokenType.CATCH) {
               int catchLine = this.line;
               long catchToken = this.token;
               this.next();
               this.expect(TokenType.LPAREN);
               IdentNode exception = this.getIdent();
               this.verifyStrictIdent(exception, "catch argument");
               Expression ifExpression;
               if (!this.env._no_syntax_extensions && this.type == TokenType.IF) {
                  this.next();
                  ifExpression = this.expression();
               } else {
                  ifExpression = null;
               }

               this.expect(TokenType.RPAREN);
               Block catchBlock = this.newBlock();

               try {
                  Block catchBody = this.getBlock(true);
                  CatchNode catchNode = new CatchNode(catchLine, catchToken, this.finish, exception, ifExpression, catchBody, false);
                  this.appendStatement(catchNode);
               } finally {
                  catchBlock = this.restoreBlock(catchBlock);
                  catchBlocks.add(catchBlock);
               }

               if (ifExpression != null) {
                  continue;
               }
            }

            Block finallyStatements = null;
            if (this.type == TokenType.FINALLY) {
               this.next();
               finallyStatements = this.getBlock(true);
            }

            if (catchBlocks.isEmpty() && finallyStatements == null) {
               throw this.error(AbstractParser.message("missing.catch.or.finally"), tryToken);
            }

            TryNode tryNode = new TryNode(tryLine, tryToken, Token.descPosition(tryToken), tryBody, catchBlocks, finallyStatements);

            assert this.lc.peek() == outer;

            this.appendStatement(tryNode);
            tryNode.setFinish(this.finish);
            outer.setFinish(this.finish);
            break;
         }
      } finally {
         outer = this.restoreBlock(outer);
      }

      this.appendStatement(new BlockStatement(startLine, outer));
   }

   private void debuggerStatement() {
      int debuggerLine = this.line;
      long debuggerToken = this.token;
      this.next();
      this.endOfLine();
      this.appendStatement(new ExpressionStatement(debuggerLine, debuggerToken, this.finish, new RuntimeNode(debuggerToken, this.finish, RuntimeNode.Request.DEBUGGER, new ArrayList())));
   }

   private Expression primaryExpression() {
      int primaryLine = this.line;
      long primaryToken = this.token;
      switch(this.type) {
      case LBRACE:
         return this.objectLiteral();
      case VAR:
      case IF:
      case FOR:
      case WHILE:
      case DO:
      case CONTINUE:
      case BREAK:
      case RETURN:
      case YIELD:
      case WITH:
      case SWITCH:
      case THROW:
      case TRY:
      case DEBUGGER:
      case RPAREN:
      case RBRACKET:
      case CASE:
      case DEFAULT:
      case IN:
      default:
         if (this.lexer.scanLiteral(primaryToken, this.type, this.lineInfoReceiver)) {
            this.next();
            return this.getLiteral();
         }

         if (this.isNonStrictModeIdent()) {
            return this.getIdent();
         }
         break;
      case THIS:
         String name = this.type.getName();
         this.next();
         this.lc.setFlag(this.lc.getCurrentFunction(), 32768);
         return new IdentNode(primaryToken, this.finish, name);
      case IDENT:
         IdentNode ident = this.getIdent();
         if (ident != null) {
            this.detectSpecialProperty(ident);
            return ident;
         }
         break;
      case OCTAL:
         if (this.isStrictMode) {
            throw this.error(AbstractParser.message("strict.no.octal"), this.token);
         }
      case STRING:
      case ESCSTRING:
      case DECIMAL:
      case HEXADECIMAL:
      case FLOATING:
      case REGEX:
      case XML:
         return this.getLiteral();
      case EXECSTRING:
         return this.execString(primaryLine, primaryToken);
      case FALSE:
         this.next();
         return LiteralNode.newInstance(primaryToken, this.finish, false);
      case TRUE:
         this.next();
         return LiteralNode.newInstance(primaryToken, this.finish, true);
      case NULL:
         this.next();
         return LiteralNode.newInstance(primaryToken, this.finish);
      case LBRACKET:
         return this.arrayLiteral();
      case LPAREN:
         this.next();
         Expression expression = this.expression();
         this.expect(TokenType.RPAREN);
         return expression;
      }

      return null;
   }

   CallNode execString(int primaryLine, long primaryToken) {
      IdentNode execIdent = new IdentNode(primaryToken, this.finish, "$EXEC");
      this.next();
      this.expect(TokenType.LBRACE);
      List<Expression> arguments = Collections.singletonList(this.expression());
      this.expect(TokenType.RBRACE);
      return new CallNode(primaryLine, primaryToken, this.finish, execIdent, arguments, false);
   }

   private LiteralNode<Expression[]> arrayLiteral() {
      long arrayToken = this.token;
      this.next();
      List<Expression> elements = new ArrayList();
      boolean elision = true;

      while(true) {
         switch(this.type) {
         case RBRACKET:
            this.next();
            return LiteralNode.newInstance(arrayToken, this.finish, (List)elements);
         case COMMARIGHT:
            this.next();
            if (elision) {
               elements.add((Object)null);
            }

            elision = true;
            break;
         default:
            if (!elision) {
               throw this.error(AbstractParser.message("expected.comma", this.type.getNameOrType()));
            }

            Expression expression = this.assignmentExpression(false);
            if (expression != null) {
               elements.add(expression);
            } else {
               this.expect(TokenType.RBRACKET);
            }

            elision = false;
         }
      }
   }

   private ObjectNode objectLiteral() {
      long objectToken = this.token;
      this.next();
      List<PropertyNode> elements = new ArrayList();
      Map<String, Integer> map = new HashMap();
      boolean commaSeen = true;

      while(true) {
         switch(this.type) {
         case RBRACE:
            this.next();
            return new ObjectNode(objectToken, this.finish, elements);
         case COMMARIGHT:
            if (commaSeen) {
               throw this.error(AbstractParser.message("expected.property.id", this.type.getNameOrType()));
            }

            this.next();
            commaSeen = true;
            break;
         default:
            if (!commaSeen) {
               throw this.error(AbstractParser.message("expected.comma", this.type.getNameOrType()));
            }

            commaSeen = false;
            PropertyNode property = this.propertyAssignment();
            String key = property.getKeyName();
            Integer existing = (Integer)map.get(key);
            if (existing == null) {
               map.put(key, elements.size());
               elements.add(property);
            } else {
               PropertyNode existingProperty = (PropertyNode)elements.get(existing);
               Expression value = property.getValue();
               FunctionNode getter = property.getGetter();
               FunctionNode setter = property.getSetter();
               Expression prevValue = existingProperty.getValue();
               FunctionNode prevGetter = existingProperty.getGetter();
               FunctionNode prevSetter = existingProperty.getSetter();
               if (this.isStrictMode && value != null && prevValue != null) {
                  throw this.error(AbstractParser.message("property.redefinition", key), property.getToken());
               }

               boolean isPrevAccessor = prevGetter != null || prevSetter != null;
               boolean isAccessor = getter != null || setter != null;
               if (prevValue != null && isAccessor) {
                  throw this.error(AbstractParser.message("property.redefinition", key), property.getToken());
               }

               if (isPrevAccessor && value != null) {
                  throw this.error(AbstractParser.message("property.redefinition", key), property.getToken());
               }

               if (isAccessor && isPrevAccessor && (getter != null && prevGetter != null || setter != null && prevSetter != null)) {
                  throw this.error(AbstractParser.message("property.redefinition", key), property.getToken());
               }

               if (value != null) {
                  elements.add(property);
               } else if (getter != null) {
                  elements.set(existing, existingProperty.setGetter(getter));
               } else if (setter != null) {
                  elements.set(existing, existingProperty.setSetter(setter));
               }
            }
         }
      }
   }

   private PropertyKey propertyName() {
      switch(this.type) {
      case IDENT:
         return this.getIdent().setIsPropertyName();
      case OCTAL:
         if (this.isStrictMode) {
            throw this.error(AbstractParser.message("strict.no.octal"), this.token);
         }
      case STRING:
      case ESCSTRING:
      case DECIMAL:
      case HEXADECIMAL:
      case FLOATING:
         return this.getLiteral();
      default:
         return this.getIdentifierName().setIsPropertyName();
      }
   }

   private PropertyNode propertyAssignment() {
      long propertyToken = this.token;
      int functionLine = this.line;
      Object propertyName;
      if (this.type == TokenType.IDENT) {
         String ident = (String)this.expectValue(TokenType.IDENT);
         if (this.type != TokenType.COLON) {
            byte var9 = -1;
            switch(ident.hashCode()) {
            case 102230:
               if (ident.equals("get")) {
                  var9 = 0;
               }
               break;
            case 113762:
               if (ident.equals("set")) {
                  var9 = 1;
               }
            }

            switch(var9) {
            case 0:
               Parser.PropertyFunction getter = this.propertyGetterFunction(propertyToken, functionLine);
               return new PropertyNode(propertyToken, this.finish, getter.ident, (Expression)null, getter.functionNode, (FunctionNode)null);
            case 1:
               Parser.PropertyFunction setter = this.propertySetterFunction(propertyToken, functionLine);
               return new PropertyNode(propertyToken, this.finish, setter.ident, (Expression)null, (FunctionNode)null, setter.functionNode);
            }
         }

         propertyName = this.createIdentNode(propertyToken, this.finish, ident).setIsPropertyName();
      } else {
         propertyName = this.propertyName();
      }

      this.expect(TokenType.COLON);
      this.defaultNames.push(propertyName);

      PropertyNode var15;
      try {
         var15 = new PropertyNode(propertyToken, this.finish, (PropertyKey)propertyName, this.assignmentExpression(false), (FunctionNode)null, (FunctionNode)null);
      } finally {
         this.defaultNames.pop();
      }

      return var15;
   }

   private Parser.PropertyFunction propertyGetterFunction(long getSetToken, int functionLine) {
      PropertyKey getIdent = this.propertyName();
      String getterName = getIdent.getPropertyName();
      IdentNode getNameNode = this.createIdentNode(((Node)getIdent).getToken(), this.finish, NameCodec.encode("get " + getterName));
      this.expect(TokenType.LPAREN);
      this.expect(TokenType.RPAREN);
      FunctionNode functionNode = this.functionBody(getSetToken, getNameNode, new ArrayList(), FunctionNode.Kind.GETTER, functionLine);
      return new Parser.PropertyFunction(getIdent, functionNode);
   }

   private Parser.PropertyFunction propertySetterFunction(long getSetToken, int functionLine) {
      PropertyKey setIdent = this.propertyName();
      String setterName = setIdent.getPropertyName();
      IdentNode setNameNode = this.createIdentNode(((Node)setIdent).getToken(), this.finish, NameCodec.encode("set " + setterName));
      this.expect(TokenType.LPAREN);
      IdentNode argIdent;
      if (this.type != TokenType.IDENT && !this.isNonStrictModeIdent()) {
         argIdent = null;
      } else {
         argIdent = this.getIdent();
         this.verifyStrictIdent(argIdent, "setter argument");
      }

      this.expect(TokenType.RPAREN);
      List<IdentNode> parameters = new ArrayList();
      if (argIdent != null) {
         parameters.add(argIdent);
      }

      FunctionNode functionNode = this.functionBody(getSetToken, setNameNode, parameters, FunctionNode.Kind.SETTER, functionLine);
      return new Parser.PropertyFunction(setIdent, functionNode);
   }

   private Expression leftHandSideExpression() {
      int callLine = this.line;
      long callToken = this.token;
      Expression lhs = this.memberExpression();
      List arguments;
      if (this.type == TokenType.LPAREN) {
         arguments = optimizeList(this.argumentList());
         if (lhs instanceof IdentNode) {
            this.detectSpecialFunction((IdentNode)lhs);
         }

         lhs = new CallNode(callLine, callToken, this.finish, (Expression)lhs, arguments, false);
      }

      while(true) {
         callLine = this.line;
         callToken = this.token;
         switch(this.type) {
         case LBRACKET:
            this.next();
            Expression rhs = this.expression();
            this.expect(TokenType.RBRACKET);
            lhs = new IndexNode(callToken, this.finish, (Expression)lhs, rhs);
            break;
         case LPAREN:
            arguments = optimizeList(this.argumentList());
            lhs = new CallNode(callLine, callToken, this.finish, (Expression)lhs, arguments, false);
            break;
         case COMMARIGHT:
         default:
            return (Expression)lhs;
         case PERIOD:
            this.next();
            IdentNode property = this.getIdentifierName();
            lhs = new AccessNode(callToken, this.finish, (Expression)lhs, property.getName());
         }
      }
   }

   private Expression newExpression() {
      long newToken = this.token;
      this.next();
      int callLine = this.line;
      Expression constructor = this.memberExpression();
      if (constructor == null) {
         return null;
      } else {
         ArrayList arguments;
         if (this.type == TokenType.LPAREN) {
            arguments = this.argumentList();
         } else {
            arguments = new ArrayList();
         }

         if (!this.env._no_syntax_extensions && this.type == TokenType.LBRACE) {
            arguments.add(this.objectLiteral());
         }

         CallNode callNode = new CallNode(callLine, constructor.getToken(), this.finish, constructor, optimizeList(arguments), true);
         return new UnaryNode(newToken, callNode);
      }
   }

   private Expression memberExpression() {
      Object lhs;
      switch(this.type) {
      case NEW:
         lhs = this.newExpression();
         break;
      case FUNCTION:
         lhs = this.functionExpression(false, false);
         break;
      default:
         lhs = this.primaryExpression();
      }

      while(true) {
         long callToken = this.token;
         switch(this.type) {
         case LBRACKET:
            this.next();
            Expression index = this.expression();
            this.expect(TokenType.RBRACKET);
            lhs = new IndexNode(callToken, this.finish, (Expression)lhs, index);
            break;
         case PERIOD:
            if (lhs == null) {
               throw this.error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
            }

            this.next();
            IdentNode property = this.getIdentifierName();
            lhs = new AccessNode(callToken, this.finish, (Expression)lhs, property.getName());
            break;
         default:
            return (Expression)lhs;
         }
      }
   }

   private ArrayList<Expression> argumentList() {
      ArrayList<Expression> nodeList = new ArrayList();
      this.next();

      for(boolean first = true; this.type != TokenType.RPAREN; nodeList.add(this.assignmentExpression(false))) {
         if (!first) {
            this.expect(TokenType.COMMARIGHT);
         } else {
            first = false;
         }
      }

      this.expect(TokenType.RPAREN);
      return nodeList;
   }

   private static <T> List<T> optimizeList(ArrayList<T> list) {
      switch(list.size()) {
      case 0:
         return Collections.emptyList();
      case 1:
         return Collections.singletonList(list.get(0));
      default:
         list.trimToSize();
         return list;
      }
   }

   private Expression functionExpression(boolean isStatement, boolean topLevel) {
      long functionToken = this.token;
      int functionLine = this.line;
      this.next();
      IdentNode name = null;
      if (this.type != TokenType.IDENT && !this.isNonStrictModeIdent()) {
         if (isStatement && this.env._no_syntax_extensions && this.reparsedFunction == null) {
            this.expect(TokenType.IDENT);
         }
      } else {
         name = this.getIdent();
         this.verifyStrictIdent(name, "function name");
      }

      boolean isAnonymous = false;
      if (name == null) {
         String tmpName = this.getDefaultValidFunctionName(functionLine, isStatement);
         name = new IdentNode(functionToken, Token.descPosition(functionToken), tmpName);
         isAnonymous = true;
      }

      this.expect(TokenType.LPAREN);
      List<IdentNode> parameters = this.formalParameterList();
      this.expect(TokenType.RPAREN);
      this.hideDefaultName();

      FunctionNode functionNode;
      try {
         functionNode = this.functionBody(functionToken, name, parameters, FunctionNode.Kind.NORMAL, functionLine);
      } finally {
         this.defaultNames.pop();
      }

      if (isStatement) {
         if (!topLevel && !this.useBlockScope()) {
            if (this.isStrictMode) {
               throw this.error(JSErrorType.SYNTAX_ERROR, AbstractParser.message("strict.no.func.decl.here"), functionToken);
            }

            if (this.env._function_statement == ScriptEnvironment.FunctionStatementBehavior.ERROR) {
               throw this.error(JSErrorType.SYNTAX_ERROR, AbstractParser.message("no.func.decl.here"), functionToken);
            }

            if (this.env._function_statement == ScriptEnvironment.FunctionStatementBehavior.WARNING) {
               this.warning(JSErrorType.SYNTAX_ERROR, AbstractParser.message("no.func.decl.here.warn"), functionToken);
            }
         } else {
            functionNode = functionNode.setFlag(this.lc, 2);
         }

         if (isArguments(name)) {
            this.lc.setFlag(this.lc.getCurrentFunction(), 256);
         }
      }

      if (isAnonymous) {
         functionNode = functionNode.setFlag(this.lc, 1);
      }

      int arity = parameters.size();
      boolean strict = functionNode.isStrict();
      if (arity > 1) {
         HashSet<String> parametersSet = new HashSet(arity);

         for(int i = arity - 1; i >= 0; --i) {
            IdentNode parameter = (IdentNode)parameters.get(i);
            String parameterName = parameter.getName();
            if (isArguments(parameterName)) {
               functionNode = functionNode.setFlag(this.lc, 256);
            }

            if (parametersSet.contains(parameterName)) {
               if (strict) {
                  throw this.error(AbstractParser.message("strict.param.redefinition", parameterName), parameter.getToken());
               }

               parameterName = functionNode.uniqueName(parameterName);
               long parameterToken = parameter.getToken();
               parameters.set(i, new IdentNode(parameterToken, Token.descPosition(parameterToken), functionNode.uniqueName(parameterName)));
            }

            parametersSet.add(parameterName);
         }
      } else if (arity == 1 && isArguments((IdentNode)parameters.get(0))) {
         functionNode = functionNode.setFlag(this.lc, 256);
      }

      if (isStatement) {
         if (isAnonymous) {
            this.appendStatement(new ExpressionStatement(functionLine, functionToken, this.finish, functionNode));
            return functionNode;
         }

         int varFlags = !topLevel && this.useBlockScope() ? 1 : 0;
         VarNode varNode = new VarNode(functionLine, functionToken, this.finish, name, functionNode, varFlags);
         if (topLevel) {
            this.functionDeclarations.add(varNode);
         } else if (this.useBlockScope()) {
            this.prependStatement(varNode);
         } else {
            this.appendStatement(varNode);
         }
      }

      return functionNode;
   }

   private String getDefaultValidFunctionName(int functionLine, boolean isStatement) {
      String defaultFunctionName = this.getDefaultFunctionName();
      if (isValidIdentifier(defaultFunctionName)) {
         return isStatement ? CompilerConstants.ANON_FUNCTION_PREFIX.symbolName() + defaultFunctionName : defaultFunctionName;
      } else {
         return CompilerConstants.ANON_FUNCTION_PREFIX.symbolName() + functionLine;
      }
   }

   private static boolean isValidIdentifier(String name) {
      if (name != null && !name.isEmpty()) {
         if (!Character.isJavaIdentifierStart(name.charAt(0))) {
            return false;
         } else {
            for(int i = 1; i < name.length(); ++i) {
               if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private String getDefaultFunctionName() {
      if (!this.defaultNames.isEmpty()) {
         Object nameExpr = this.defaultNames.peek();
         if (nameExpr instanceof PropertyKey) {
            this.markDefaultNameUsed();
            return ((PropertyKey)nameExpr).getPropertyName();
         }

         if (nameExpr instanceof AccessNode) {
            this.markDefaultNameUsed();
            return ((AccessNode)nameExpr).getProperty();
         }
      }

      return null;
   }

   private void markDefaultNameUsed() {
      this.defaultNames.pop();
      this.hideDefaultName();
   }

   private void hideDefaultName() {
      this.defaultNames.push("");
   }

   private List<IdentNode> formalParameterList() {
      return this.formalParameterList(TokenType.RPAREN);
   }

   private List<IdentNode> formalParameterList(TokenType endType) {
      ArrayList<IdentNode> parameters = new ArrayList();
      boolean first = true;

      while(this.type != endType) {
         if (!first) {
            this.expect(TokenType.COMMARIGHT);
         } else {
            first = false;
         }

         IdentNode ident = this.getIdent();
         this.verifyStrictIdent(ident, "function parameter");
         parameters.add(ident);
      }

      parameters.trimToSize();
      return parameters;
   }

   private FunctionNode functionBody(long firstToken, IdentNode ident, List<IdentNode> parameters, FunctionNode.Kind kind, int functionLine) {
      FunctionNode functionNode = null;
      long lastToken = 0L;
      Parser.ParserState endParserState = null;

      boolean parseBody;
      try {
         functionNode = this.newFunctionNode(firstToken, ident, parameters, kind, functionLine);

         assert functionNode != null;

         int functionId = functionNode.getId();
         parseBody = this.reparsedFunction == null || functionId <= this.reparsedFunction.getFunctionNodeId();
         if (!this.env._no_syntax_extensions && this.type != TokenType.LBRACE) {
            Expression expr = this.assignmentExpression(true);
            lastToken = this.previousToken;

            assert this.lc.getCurrentBlock() == this.lc.getFunctionBody(functionNode);

            int lastFinish = Token.descPosition(lastToken) + (Token.descType(lastToken) == TokenType.EOL ? 0 : Token.descLength(lastToken));
            if (parseBody) {
               ReturnNode returnNode = new ReturnNode(functionNode.getLineNumber(), expr.getToken(), lastFinish, expr);
               this.appendStatement(returnNode);
            }

            functionNode.setFinish(lastFinish);
         } else {
            this.expectDontAdvance(TokenType.LBRACE);
            if (parseBody || !this.skipFunctionBody(functionNode)) {
               this.next();
               List<Statement> prevFunctionDecls = this.functionDeclarations;
               this.functionDeclarations = new ArrayList();

               try {
                  this.sourceElements(false);
                  this.addFunctionDeclarations(functionNode);
               } finally {
                  this.functionDeclarations = prevFunctionDecls;
               }

               lastToken = this.token;
               if (parseBody) {
                  endParserState = new Parser.ParserState(Token.descPosition(this.token), this.line, this.linePosition);
               }
            }

            this.expect(TokenType.RBRACE);
            functionNode.setFinish(this.finish);
         }
      } finally {
         functionNode = this.restoreFunctionNode(functionNode, lastToken);
      }

      if (parseBody) {
         functionNode = functionNode.setEndParserState(this.lc, endParserState);
      } else if (functionNode.getBody().getStatementCount() > 0) {
         functionNode = functionNode.setBody((LexicalContext)null, functionNode.getBody().setStatements((LexicalContext)null, Collections.emptyList()));
      }

      if (this.reparsedFunction != null) {
         RecompilableScriptFunctionData data = this.reparsedFunction.getScriptFunctionData(functionNode.getId());
         if (data != null) {
            functionNode = functionNode.setFlags(this.lc, data.getFunctionFlags());
            if (functionNode.hasNestedEval()) {
               assert functionNode.hasScopeBlock();

               functionNode = functionNode.setBody(this.lc, functionNode.getBody().setNeedsScope((LexicalContext)null));
            }
         }
      }

      this.printAST(functionNode);
      return functionNode;
   }

   private boolean skipFunctionBody(FunctionNode functionNode) {
      if (this.reparsedFunction == null) {
         return false;
      } else {
         RecompilableScriptFunctionData data = this.reparsedFunction.getScriptFunctionData(functionNode.getId());
         if (data == null) {
            return false;
         } else {
            Parser.ParserState parserState = (Parser.ParserState)data.getEndParserState();

            assert parserState != null;

            this.stream.reset();
            this.lexer = parserState.createLexer(this.source, this.lexer, this.stream, this.scripting && !this.env._no_syntax_extensions);
            this.line = parserState.line;
            this.linePosition = parserState.linePosition;
            this.type = TokenType.SEMICOLON;
            this.k = -1;
            this.next();
            return true;
         }
      }
   }

   private void printAST(FunctionNode functionNode) {
      if (functionNode.getFlag(524288)) {
         this.env.getErr().println(new ASTWriter(functionNode));
      }

      if (functionNode.getFlag(131072)) {
         this.env.getErr().println(new PrintVisitor(functionNode, true, false));
      }

   }

   private void addFunctionDeclarations(FunctionNode functionNode) {
      VarNode lastDecl = null;

      for(int i = this.functionDeclarations.size() - 1; i >= 0; --i) {
         Statement decl = (Statement)this.functionDeclarations.get(i);
         if (lastDecl == null && decl instanceof VarNode) {
            decl = lastDecl = ((VarNode)decl).setFlag(4);
            this.lc.setFlag(functionNode, 1024);
         }

         this.prependStatement((Statement)decl);
      }

   }

   private RuntimeNode referenceError(Expression lhs, Expression rhs, boolean earlyError) {
      if (earlyError) {
         throw this.error(JSErrorType.REFERENCE_ERROR, AbstractParser.message("invalid.lvalue"), lhs.getToken());
      } else {
         ArrayList<Expression> args = new ArrayList();
         args.add(lhs);
         if (rhs == null) {
            args.add(LiteralNode.newInstance(lhs.getToken(), lhs.getFinish()));
         } else {
            args.add(rhs);
         }

         args.add(LiteralNode.newInstance(lhs.getToken(), lhs.getFinish(), lhs.toString()));
         return new RuntimeNode(lhs.getToken(), lhs.getFinish(), RuntimeNode.Request.REFERENCE_ERROR, args);
      }
   }

   private Expression unaryExpression() {
      int unaryLine = this.line;
      long unaryToken = this.token;
      Expression expr;
      TokenType opType;
      switch(this.type) {
      case DELETE:
         this.next();
         expr = this.unaryExpression();
         if (!(expr instanceof BaseNode) && !(expr instanceof IdentNode)) {
            this.appendStatement(new ExpressionStatement(unaryLine, unaryToken, this.finish, expr));
            return LiteralNode.newInstance(unaryToken, this.finish, true);
         }

         return new UnaryNode(unaryToken, expr);
      case VOID:
      case TYPEOF:
      case ADD:
      case SUB:
      case BIT_NOT:
      case NOT:
         this.next();
         expr = this.unaryExpression();
         return new UnaryNode(unaryToken, expr);
      case INCPREFIX:
      case DECPREFIX:
         opType = this.type;
         this.next();
         Expression lhs = this.leftHandSideExpression();
         if (lhs == null) {
            throw this.error(AbstractParser.message("expected.lvalue", this.type.getNameOrType()));
         } else if (!(lhs instanceof AccessNode) && !(lhs instanceof IndexNode) && !(lhs instanceof IdentNode)) {
            return this.referenceError(lhs, (Expression)null, this.env._early_lvalue_error);
         } else {
            if (lhs instanceof IdentNode) {
               if (!checkIdentLValue((IdentNode)lhs)) {
                  return this.referenceError(lhs, (Expression)null, false);
               }

               this.verifyStrictIdent((IdentNode)lhs, "operand for " + opType.getName() + " operator");
            }

            return incDecExpression(unaryToken, opType, lhs, false);
         }
      default:
         Expression expression = this.leftHandSideExpression();
         if (this.last != TokenType.EOL) {
            switch(this.type) {
            case INCPREFIX:
            case DECPREFIX:
               opType = this.type;
               if (expression == null) {
                  throw this.error(AbstractParser.message("expected.lvalue", this.type.getNameOrType()));
               }

               if (!(expression instanceof AccessNode) && !(expression instanceof IndexNode) && !(expression instanceof IdentNode)) {
                  this.next();
                  return this.referenceError((Expression)expression, (Expression)null, this.env._early_lvalue_error);
               }

               if (expression instanceof IdentNode) {
                  if (!checkIdentLValue((IdentNode)expression)) {
                     this.next();
                     return this.referenceError((Expression)expression, (Expression)null, false);
                  }

                  this.verifyStrictIdent((IdentNode)expression, "operand for " + opType.getName() + " operator");
               }

               expression = incDecExpression(this.token, this.type, (Expression)expression, true);
               this.next();
            }
         }

         if (expression == null) {
            throw this.error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
         } else {
            return (Expression)expression;
         }
      }
   }

   private Expression expression() {
      return this.expression(this.unaryExpression(), TokenType.COMMARIGHT.getPrecedence(), false);
   }

   private JoinPredecessorExpression joinPredecessorExpression() {
      return new JoinPredecessorExpression(this.expression());
   }

   private Expression expression(Expression exprLhs, int minPrecedence, boolean noIn) {
      int precedence = this.type.getPrecedence();

      Object lhs;
      for(lhs = exprLhs; this.type.isOperator(noIn) && precedence >= minPrecedence; precedence = this.type.getPrecedence()) {
         long op = this.token;
         Expression rhs;
         if (this.type == TokenType.TERNARY) {
            this.next();
            rhs = this.expression(this.unaryExpression(), TokenType.ASSIGN.getPrecedence(), false);
            this.expect(TokenType.COLON);
            Expression falseExpr = this.expression(this.unaryExpression(), TokenType.ASSIGN.getPrecedence(), noIn);
            lhs = new TernaryNode(op, (Expression)lhs, new JoinPredecessorExpression(rhs), new JoinPredecessorExpression(falseExpr));
         } else {
            this.next();
            boolean isAssign = Token.descType(op) == TokenType.ASSIGN;
            if (isAssign) {
               this.defaultNames.push(lhs);
            }

            try {
               rhs = this.unaryExpression();

               for(int nextPrecedence = this.type.getPrecedence(); this.type.isOperator(noIn) && (nextPrecedence > precedence || nextPrecedence == precedence && !this.type.isLeftAssociative()); nextPrecedence = this.type.getPrecedence()) {
                  rhs = this.expression(rhs, nextPrecedence, noIn);
               }
            } finally {
               if (isAssign) {
                  this.defaultNames.pop();
               }

            }

            lhs = this.verifyAssignment(op, (Expression)lhs, rhs);
         }
      }

      return (Expression)lhs;
   }

   private Expression assignmentExpression(boolean noIn) {
      return this.expression(this.unaryExpression(), TokenType.ASSIGN.getPrecedence(), noIn);
   }

   private void endOfLine() {
      switch(this.type) {
      case EOF:
      case RBRACE:
      case RPAREN:
      case RBRACKET:
         break;
      case EOL:
      case SEMICOLON:
         this.next();
         break;
      default:
         if (this.last != TokenType.EOL) {
            this.expect(TokenType.SEMICOLON);
         }
      }

   }

   public String toString() {
      return "'JavaScript Parsing'";
   }

   private static void markEval(LexicalContext lc) {
      Iterator<FunctionNode> iter = lc.getFunctions();

      FunctionNode fn;
      for(boolean flaggedCurrentFn = false; iter.hasNext(); lc.setBlockNeedsScope(lc.getFunctionBody(fn))) {
         fn = (FunctionNode)iter.next();
         if (!flaggedCurrentFn) {
            lc.setFlag(fn, 32);
            flaggedCurrentFn = true;
         } else {
            lc.setFlag(fn, 64);
         }
      }

   }

   private void prependStatement(Statement statement) {
      this.lc.prependStatement(statement);
   }

   private void appendStatement(Statement statement) {
      this.lc.appendStatement(statement);
   }

   static {
      ARGUMENTS_NAME = CompilerConstants.ARGUMENTS_VAR.symbolName();
   }

   private static class ParserState implements Serializable {
      private final int position;
      private final int line;
      private final int linePosition;
      private static final long serialVersionUID = -2382565130754093694L;

      ParserState(int position, int line, int linePosition) {
         this.position = position;
         this.line = line;
         this.linePosition = linePosition;
      }

      Lexer createLexer(Source source, Lexer lexer, TokenStream stream, boolean scripting) {
         Lexer newLexer = new Lexer(source, this.position, lexer.limit - this.position, stream, scripting, true);
         newLexer.restoreState(new Lexer.State(this.position, Integer.MAX_VALUE, this.line, -1, this.linePosition, TokenType.SEMICOLON));
         return newLexer;
      }
   }

   private static class PropertyFunction {
      final PropertyKey ident;
      final FunctionNode functionNode;

      PropertyFunction(PropertyKey ident, FunctionNode function) {
         this.ident = ident;
         this.functionNode = function;
      }
   }
}
