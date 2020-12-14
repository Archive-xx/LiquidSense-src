package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.CatchNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.ForNode;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.LexicalContextNode;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.ir.Splittable;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "symbols"
)
final class AssignSymbols extends SimpleNodeVisitor implements Loggable {
   private final DebugLogger log;
   private final boolean debug;
   private final Deque<Set<String>> thisProperties = new ArrayDeque();
   private final Map<String, Symbol> globalSymbols = new HashMap();
   private final Compiler compiler;
   private final boolean isOnDemand;

   private static boolean isParamOrVar(IdentNode identNode) {
      Symbol symbol = identNode.getSymbol();
      return symbol.isParam() || symbol.isVar();
   }

   private static String name(Node node) {
      String cn = node.getClass().getName();
      int lastDot = cn.lastIndexOf(46);
      return lastDot == -1 ? cn : cn.substring(lastDot + 1);
   }

   private static FunctionNode removeUnusedSlots(FunctionNode functionNode) {
      if (!functionNode.needsCallee()) {
         functionNode.compilerConstant(CompilerConstants.CALLEE).setNeedsSlot(false);
      }

      if (!functionNode.hasScopeBlock() && !functionNode.needsParentScope()) {
         functionNode.compilerConstant(CompilerConstants.SCOPE).setNeedsSlot(false);
      }

      if (functionNode.isNamedFunctionExpression() && !functionNode.usesSelfSymbol()) {
         Symbol selfSymbol = functionNode.getBody().getExistingSymbol(functionNode.getIdent().getName());
         if (selfSymbol != null && selfSymbol.isFunctionSelf()) {
            selfSymbol.setNeedsSlot(false);
            selfSymbol.clearFlag(2);
         }
      }

      return functionNode;
   }

   public AssignSymbols(Compiler compiler) {
      this.compiler = compiler;
      this.log = this.initLogger(compiler.getContext());
      this.debug = this.log.isEnabled();
      this.isOnDemand = compiler.isOnDemandCompilation();
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   private void acceptDeclarations(FunctionNode functionNode, final Block body) {
      body.accept(new SimpleNodeVisitor() {
         protected boolean enterDefault(Node node) {
            return !(node instanceof Expression);
         }

         public Node leaveVarNode(VarNode varNode) {
            IdentNode ident = varNode.getName();
            boolean blockScoped = varNode.isBlockScoped();
            if (blockScoped && this.lc.inUnprotectedSwitchContext()) {
               AssignSymbols.this.throwUnprotectedSwitchError(varNode);
            }

            Block block = blockScoped ? this.lc.getCurrentBlock() : body;
            Symbol symbol = AssignSymbols.this.defineSymbol(block, ident.getName(), ident, varNode.getSymbolFlags());
            if (varNode.isFunctionDeclaration()) {
               symbol.setIsFunctionDeclaration();
            }

            return varNode.setName(ident.setSymbol(symbol));
         }
      });
   }

   private IdentNode compilerConstantIdentifier(CompilerConstants cc) {
      return this.createImplicitIdentifier(cc.symbolName()).setSymbol(this.lc.getCurrentFunction().compilerConstant(cc));
   }

   private IdentNode createImplicitIdentifier(String name) {
      FunctionNode fn = this.lc.getCurrentFunction();
      return new IdentNode(fn.getToken(), fn.getFinish(), name);
   }

   private Symbol createSymbol(String name, int flags) {
      if ((flags & 3) == 1) {
         Symbol global = (Symbol)this.globalSymbols.get(name);
         if (global == null) {
            global = new Symbol(name, flags);
            this.globalSymbols.put(name, global);
         }

         return global;
      } else {
         return new Symbol(name, flags);
      }
   }

   private VarNode createSyntheticInitializer(IdentNode name, CompilerConstants initConstant, FunctionNode fn) {
      IdentNode init = this.compilerConstantIdentifier(initConstant);

      assert init.getSymbol() != null && init.getSymbol().isBytecodeLocal();

      VarNode synthVar = new VarNode(fn.getLineNumber(), fn.getToken(), fn.getFinish(), name, init);
      Symbol nameSymbol = fn.getBody().getExistingSymbol(name.getName());

      assert nameSymbol != null;

      return (VarNode)synthVar.setName(name.setSymbol(nameSymbol)).accept(this);
   }

   private FunctionNode createSyntheticInitializers(FunctionNode functionNode) {
      List<VarNode> syntheticInitializers = new ArrayList(2);
      Block body = functionNode.getBody();
      this.lc.push(body);

      try {
         if (functionNode.usesSelfSymbol()) {
            syntheticInitializers.add(this.createSyntheticInitializer(functionNode.getIdent(), CompilerConstants.CALLEE, functionNode));
         }

         if (functionNode.needsArguments()) {
            syntheticInitializers.add(this.createSyntheticInitializer(this.createImplicitIdentifier(CompilerConstants.ARGUMENTS_VAR.symbolName()), CompilerConstants.ARGUMENTS, functionNode));
         }

         if (syntheticInitializers.isEmpty()) {
            FunctionNode var8 = functionNode;
            return var8;
         }

         ListIterator it = syntheticInitializers.listIterator();

         while(it.hasNext()) {
            it.set((VarNode)((VarNode)it.next()).accept(this));
         }
      } finally {
         this.lc.pop(body);
      }

      List stmts = body.getStatements();
      ArrayList newStatements = new ArrayList(stmts.size() + syntheticInitializers.size());
      newStatements.addAll(syntheticInitializers);
      newStatements.addAll(stmts);
      return functionNode.setBody(this.lc, body.setStatements(this.lc, newStatements));
   }

   private Symbol defineSymbol(Block block, String name, Node origin, int symbolFlags) {
      int flags = symbolFlags;
      boolean isBlockScope = (symbolFlags & 16) != 0 || (symbolFlags & 32) != 0;
      boolean isGlobal = (symbolFlags & 3) == 1;
      Symbol symbol;
      FunctionNode function;
      if (isBlockScope) {
         symbol = block.getExistingSymbol(name);
         function = this.lc.getCurrentFunction();
      } else {
         symbol = this.findSymbol(block, name);
         function = this.lc.getFunction(block);
      }

      if (isGlobal) {
         flags = symbolFlags | 4;
      }

      if (this.lc.getCurrentFunction().isProgram()) {
         flags |= 512;
      }

      boolean isParam = (flags & 3) == 3;
      boolean isVar = (flags & 3) == 2;
      if (symbol != null) {
         if (isParam) {
            if (!this.isLocal(function, symbol)) {
               symbol = null;
            } else if (symbol.isParam()) {
               throw new AssertionError("duplicate parameter");
            }
         } else if (isVar) {
            if (isBlockScope) {
               if (symbol.hasBeenDeclared()) {
                  this.throwParserException(ECMAErrors.getMessage("syntax.error.redeclare.variable", name), origin);
               } else {
                  symbol.setHasBeenDeclared();
                  if (function.isProgram() && function.getBody() == block) {
                     symbol.setIsScope();
                  }
               }
            } else if ((flags & 64) != 0) {
               symbol = null;
            } else {
               if (symbol.isBlockScoped() && this.isLocal(this.lc.getCurrentFunction(), symbol)) {
                  this.throwParserException(ECMAErrors.getMessage("syntax.error.redeclare.variable", name), origin);
               }

               if (!this.isLocal(function, symbol) || symbol.less(2)) {
                  symbol = null;
               }
            }
         }
      }

      if (symbol == null) {
         Block symbolBlock;
         if (isVar && ((flags & 64) != 0 || isBlockScope)) {
            symbolBlock = block;
         } else if (isGlobal) {
            symbolBlock = this.lc.getOutermostFunction().getBody();
         } else {
            symbolBlock = this.lc.getFunctionBody(function);
         }

         symbol = this.createSymbol(name, flags);
         symbolBlock.putSymbol(symbol);
         if ((flags & 4) == 0) {
            symbol.setNeedsSlot(true);
         }
      } else if (symbol.less(flags)) {
         symbol.setFlags(flags);
      }

      return symbol;
   }

   private <T extends Node> T end(T node) {
      return this.end(node, true);
   }

   private <T extends Node> T end(T node, boolean printNode) {
      if (this.debug) {
         StringBuilder sb = new StringBuilder();
         sb.append("[LEAVE ").append(name(node)).append("] ").append(printNode ? node.toString() : "").append(" in '").append(this.lc.getCurrentFunction().getName()).append('\'');
         if (node instanceof IdentNode) {
            Symbol symbol = ((IdentNode)node).getSymbol();
            if (symbol == null) {
               sb.append(" <NO SYMBOL>");
            } else {
               sb.append(" <symbol=").append(symbol).append('>');
            }
         }

         this.log.unindent();
         this.log.info(sb);
      }

      return node;
   }

   public boolean enterBlock(Block block) {
      this.start(block);
      if (this.lc.isFunctionBody()) {
         assert !block.hasSymbols();

         FunctionNode fn = this.lc.getCurrentFunction();
         if (this.isUnparsedFunction(fn)) {
            Iterator var3 = this.compiler.getScriptFunctionData(fn.getId()).getExternalSymbolNames().iterator();

            while(var3.hasNext()) {
               String name = (String)var3.next();
               this.nameIsUsed(name, (IdentNode)null);
            }

            assert block.getStatements().isEmpty();

            return false;
         }

         this.enterFunctionBody();
      }

      return true;
   }

   private boolean isUnparsedFunction(FunctionNode fn) {
      return this.isOnDemand && fn != this.lc.getOutermostFunction();
   }

   public boolean enterCatchNode(CatchNode catchNode) {
      IdentNode exception = catchNode.getException();
      Block block = this.lc.getCurrentBlock();
      this.start(catchNode);
      String exname = exception.getName();
      boolean isInternal = exname.startsWith(CompilerConstants.EXCEPTION_PREFIX.symbolName());
      Symbol symbol = this.defineSymbol(block, exname, catchNode, 18 | (isInternal ? 64 : 0) | 8192);
      symbol.clearFlag(16);
      return true;
   }

   private void enterFunctionBody() {
      FunctionNode functionNode = this.lc.getCurrentFunction();
      Block body = this.lc.getCurrentBlock();
      this.initFunctionWideVariables(functionNode, body);
      this.acceptDeclarations(functionNode, body);
      this.defineFunctionSelfSymbol(functionNode, body);
   }

   private void defineFunctionSelfSymbol(FunctionNode functionNode, Block body) {
      if (functionNode.isNamedFunctionExpression()) {
         String name = functionNode.getIdent().getName();

         assert name != null;

         if (body.getExistingSymbol(name) == null) {
            this.defineSymbol(body, name, functionNode, 8322);
            if (functionNode.allVarsInScope()) {
               this.lc.setFlag(functionNode, 16384);
            }

         }
      }
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      this.start(functionNode, false);
      this.thisProperties.push(new HashSet());

      assert functionNode.getBody() != null;

      return true;
   }

   public boolean enterVarNode(VarNode varNode) {
      this.start(varNode);
      if (varNode.isFunctionDeclaration()) {
         this.defineVarIdent(varNode);
      }

      return true;
   }

   public Node leaveVarNode(VarNode varNode) {
      if (!varNode.isFunctionDeclaration()) {
         this.defineVarIdent(varNode);
      }

      return super.leaveVarNode(varNode);
   }

   private void defineVarIdent(VarNode varNode) {
      IdentNode ident = varNode.getName();
      byte flags;
      if (!varNode.isBlockScoped() && this.lc.getCurrentFunction().isProgram()) {
         flags = 4;
      } else {
         flags = 0;
      }

      this.defineSymbol(this.lc.getCurrentBlock(), ident.getName(), ident, varNode.getSymbolFlags() | flags);
   }

   private Symbol exceptionSymbol() {
      return this.newObjectInternal(CompilerConstants.EXCEPTION_PREFIX);
   }

   private FunctionNode finalizeParameters(FunctionNode functionNode) {
      List<IdentNode> newParams = new ArrayList();
      boolean isVarArg = functionNode.isVarArg();
      Block body = functionNode.getBody();
      Iterator var5 = functionNode.getParameters().iterator();

      while(var5.hasNext()) {
         IdentNode param = (IdentNode)var5.next();
         Symbol paramSymbol = body.getExistingSymbol(param.getName());

         assert paramSymbol != null;

         assert paramSymbol.isParam() : paramSymbol + " " + paramSymbol.getFlags();

         newParams.add(param.setSymbol(paramSymbol));
         if (isVarArg) {
            paramSymbol.setNeedsSlot(false);
         }
      }

      return functionNode.setParameters(this.lc, newParams);
   }

   private Symbol findSymbol(Block block, String name) {
      Iterator blocks = this.lc.getBlocks(block);

      Symbol symbol;
      do {
         if (!blocks.hasNext()) {
            return null;
         }

         symbol = ((Block)blocks.next()).getExistingSymbol(name);
      } while(symbol == null);

      return symbol;
   }

   private void functionUsesGlobalSymbol() {
      Iterator fns = this.lc.getFunctions();

      while(fns.hasNext()) {
         this.lc.setFlag((LexicalContextNode)fns.next(), 512);
      }

   }

   private void functionUsesScopeSymbol(Symbol symbol) {
      String name = symbol.getName();
      Iterator contextNodeIter = this.lc.getAllNodes();

      while(contextNodeIter.hasNext()) {
         LexicalContextNode node = (LexicalContextNode)contextNodeIter.next();
         if (node instanceof Block) {
            Block block = (Block)node;
            if (block.getExistingSymbol(name) != null) {
               assert this.lc.contains(block);

               this.lc.setBlockNeedsScope(block);
               break;
            }
         } else if (node instanceof FunctionNode) {
            this.lc.setFlag(node, 512);
         }
      }

   }

   private void functionUsesSymbol(Symbol symbol) {
      assert symbol != null;

      if (symbol.isScope()) {
         if (symbol.isGlobal()) {
            this.functionUsesGlobalSymbol();
         } else {
            this.functionUsesScopeSymbol(symbol);
         }
      } else {
         assert !symbol.isGlobal();
      }

   }

   private void initCompileConstant(CompilerConstants cc, Block block, int flags) {
      this.defineSymbol(block, cc.symbolName(), (Node)null, flags).setNeedsSlot(true);
   }

   private void initFunctionWideVariables(FunctionNode functionNode, Block body) {
      this.initCompileConstant(CompilerConstants.CALLEE, body, 8259);
      this.initCompileConstant(CompilerConstants.THIS, body, 8203);
      if (functionNode.isVarArg()) {
         this.initCompileConstant(CompilerConstants.VARARGS, body, 8259);
         if (functionNode.needsArguments()) {
            this.initCompileConstant(CompilerConstants.ARGUMENTS, body, 8258);
            this.defineSymbol(body, CompilerConstants.ARGUMENTS_VAR.symbolName(), (Node)null, 8194);
         }
      }

      this.initParameters(functionNode, body);
      this.initCompileConstant(CompilerConstants.SCOPE, body, 8258);
      this.initCompileConstant(CompilerConstants.RETURN, body, 66);
   }

   private void initParameters(FunctionNode functionNode, Block body) {
      boolean isVarArg = functionNode.isVarArg();
      boolean scopeParams = functionNode.allVarsInScope() || isVarArg;
      Iterator var5 = functionNode.getParameters().iterator();

      while(var5.hasNext()) {
         IdentNode param = (IdentNode)var5.next();
         Symbol symbol = this.defineSymbol(body, param.getName(), param, 3);
         if (scopeParams) {
            symbol.setIsScope();

            assert symbol.hasSlot();

            if (isVarArg) {
               symbol.setNeedsSlot(false);
            }
         }
      }

   }

   private boolean isLocal(FunctionNode function, Symbol symbol) {
      FunctionNode definingFn = this.lc.getDefiningFunction(symbol);

      assert definingFn != null;

      return definingFn == function;
   }

   public Node leaveBinaryNode(BinaryNode binaryNode) {
      return binaryNode.isTokenType(TokenType.ASSIGN) ? this.leaveASSIGN(binaryNode) : super.leaveBinaryNode(binaryNode);
   }

   private Node leaveASSIGN(BinaryNode binaryNode) {
      Expression lhs = binaryNode.lhs();
      if (lhs instanceof AccessNode) {
         AccessNode accessNode = (AccessNode)lhs;
         Expression base = accessNode.getBase();
         if (base instanceof IdentNode) {
            Symbol symbol = ((IdentNode)base).getSymbol();
            if (symbol.isThis()) {
               ((Set)this.thisProperties.peek()).add(accessNode.getProperty());
            }
         }
      }

      return binaryNode;
   }

   public Node leaveUnaryNode(UnaryNode unaryNode) {
      switch(unaryNode.tokenType()) {
      case DELETE:
         return this.leaveDELETE(unaryNode);
      case TYPEOF:
         return this.leaveTYPEOF(unaryNode);
      default:
         return super.leaveUnaryNode(unaryNode);
      }
   }

   private Node leaveDELETE(UnaryNode unaryNode) {
      FunctionNode currentFunctionNode = this.lc.getCurrentFunction();
      boolean strictMode = currentFunctionNode.isStrict();
      Expression rhs = unaryNode.getExpression();
      Expression strictFlagNode = (Expression)LiteralNode.newInstance(unaryNode, strictMode).accept(this);
      RuntimeNode.Request request = RuntimeNode.Request.DELETE;
      List<Expression> args = new ArrayList();
      String name;
      if (rhs instanceof IdentNode) {
         IdentNode ident = (IdentNode)rhs;
         name = ident.getName();
         Symbol symbol = ident.getSymbol();
         if (symbol.isThis()) {
            return LiteralNode.newInstance(unaryNode, true);
         }

         Expression literalNode = LiteralNode.newInstance(unaryNode, (String)name);
         boolean failDelete = strictMode || !symbol.isScope() && (symbol.isParam() || symbol.isVar() && !symbol.isProgramLevel());
         if (!failDelete) {
            args.add(this.compilerConstantIdentifier(CompilerConstants.SCOPE));
         }

         args.add(literalNode);
         args.add(strictFlagNode);
         if (failDelete) {
            request = RuntimeNode.Request.FAIL_DELETE;
         } else if (symbol.isGlobal() && !symbol.isFunctionDeclaration() || symbol.isProgramLevel()) {
            request = RuntimeNode.Request.SLOW_DELETE;
         }
      } else if (rhs instanceof AccessNode) {
         Expression base = ((AccessNode)rhs).getBase();
         name = ((AccessNode)rhs).getProperty();
         args.add(base);
         args.add(LiteralNode.newInstance(unaryNode, (String)name));
         args.add(strictFlagNode);
      } else {
         if (!(rhs instanceof IndexNode)) {
            return LiteralNode.newInstance(unaryNode, true);
         }

         IndexNode indexNode = (IndexNode)rhs;
         Expression base = indexNode.getBase();
         Expression index = indexNode.getIndex();
         args.add(base);
         args.add(index);
         args.add(strictFlagNode);
      }

      return new RuntimeNode(unaryNode, request, args);
   }

   public Node leaveForNode(ForNode forNode) {
      return (Node)(forNode.isForIn() ? forNode.setIterator(this.lc, this.newObjectInternal(CompilerConstants.ITERATOR_PREFIX)) : this.end(forNode));
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      FunctionNode finalizedFunction;
      if (this.isUnparsedFunction(functionNode)) {
         finalizedFunction = functionNode;
      } else {
         finalizedFunction = this.markProgramBlock(removeUnusedSlots(this.createSyntheticInitializers(this.finalizeParameters((FunctionNode)this.lc.applyTopFlags(functionNode)))).setThisProperties(this.lc, ((Set)this.thisProperties.pop()).size()));
      }

      return finalizedFunction;
   }

   public Node leaveIdentNode(IdentNode identNode) {
      if (identNode.isPropertyName()) {
         return identNode;
      } else {
         Symbol symbol = this.nameIsUsed(identNode.getName(), identNode);
         if (!identNode.isInitializedHere()) {
            symbol.increaseUseCount();
         }

         IdentNode newIdentNode = identNode.setSymbol(symbol);
         if (symbol.isBlockScoped() && !symbol.hasBeenDeclared() && !identNode.isDeclaredHere() && this.isLocal(this.lc.getCurrentFunction(), symbol)) {
            newIdentNode = newIdentNode.markDead();
         }

         return this.end(newIdentNode);
      }
   }

   private Symbol nameIsUsed(String name, IdentNode origin) {
      Block block = this.lc.getCurrentBlock();
      Symbol symbol = this.findSymbol(block, name);
      if (symbol != null) {
         this.log.info("Existing symbol = ", symbol);
         if (symbol.isFunctionSelf()) {
            FunctionNode functionNode = this.lc.getDefiningFunction(symbol);

            assert functionNode != null;

            assert this.lc.getFunctionBody(functionNode).getExistingSymbol(CompilerConstants.CALLEE.symbolName()) != null;

            this.lc.setFlag(functionNode, 16384);
         }

         this.maybeForceScope(symbol);
      } else {
         this.log.info("No symbol exists. Declare as global: ", name);
         symbol = this.defineSymbol(block, name, origin, 5);
      }

      this.functionUsesSymbol(symbol);
      return symbol;
   }

   public Node leaveSwitchNode(SwitchNode switchNode) {
      return !switchNode.isUniqueInteger() ? switchNode.setTag(this.lc, this.newObjectInternal(CompilerConstants.SWITCH_TAG_PREFIX)) : switchNode;
   }

   public Node leaveTryNode(TryNode tryNode) {
      assert tryNode.getFinallyBody() == null;

      this.end(tryNode);
      return tryNode.setException(this.lc, this.exceptionSymbol());
   }

   private Node leaveTYPEOF(UnaryNode unaryNode) {
      Expression rhs = unaryNode.getExpression();
      List<Expression> args = new ArrayList();
      if (rhs instanceof IdentNode && !isParamOrVar((IdentNode)rhs)) {
         args.add(this.compilerConstantIdentifier(CompilerConstants.SCOPE));
         args.add(LiteralNode.newInstance(rhs, (String)((IdentNode)rhs).getName()));
      } else {
         args.add(rhs);
         args.add(LiteralNode.newInstance(unaryNode));
      }

      Node runtimeNode = new RuntimeNode(unaryNode, RuntimeNode.Request.TYPEOF, args);
      this.end(unaryNode);
      return runtimeNode;
   }

   private FunctionNode markProgramBlock(FunctionNode functionNode) {
      return !this.isOnDemand && functionNode.isProgram() ? functionNode.setBody(this.lc, functionNode.getBody().setFlag(this.lc, 8)) : functionNode;
   }

   private void maybeForceScope(Symbol symbol) {
      if (!symbol.isScope() && this.symbolNeedsToBeScope(symbol)) {
         Symbol.setSymbolIsScope(this.lc, symbol);
      }

   }

   private Symbol newInternal(CompilerConstants cc, int flags) {
      return this.defineSymbol(this.lc.getCurrentBlock(), this.lc.getCurrentFunction().uniqueName(cc.symbolName()), (Node)null, 66 | flags);
   }

   private Symbol newObjectInternal(CompilerConstants cc) {
      return this.newInternal(cc, 8192);
   }

   private boolean start(Node node) {
      return this.start(node, true);
   }

   private boolean start(Node node, boolean printNode) {
      if (this.debug) {
         StringBuilder sb = new StringBuilder();
         sb.append("[ENTER ").append(name(node)).append("] ").append(printNode ? node.toString() : "").append(" in '").append(this.lc.getCurrentFunction().getName()).append("'");
         this.log.info(sb);
         this.log.indent();
      }

      return true;
   }

   private boolean symbolNeedsToBeScope(Symbol symbol) {
      if (!symbol.isThis() && !symbol.isInternal()) {
         FunctionNode func = this.lc.getCurrentFunction();
         if (!func.allVarsInScope() && (symbol.isBlockScoped() || !func.isProgram())) {
            boolean previousWasBlock = false;
            Iterator it = this.lc.getAllNodes();

            while(it.hasNext()) {
               LexicalContextNode node = (LexicalContextNode)it.next();
               if (node instanceof FunctionNode || isSplitLiteral(node)) {
                  return true;
               }

               if (node instanceof WithNode) {
                  if (previousWasBlock) {
                     return true;
                  }

                  previousWasBlock = false;
               } else if (node instanceof Block) {
                  if (((Block)node).getExistingSymbol(symbol.getName()) == symbol) {
                     return false;
                  }

                  previousWasBlock = true;
               } else {
                  previousWasBlock = false;
               }
            }

            throw new AssertionError();
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   private static boolean isSplitLiteral(LexicalContextNode expr) {
      return expr instanceof Splittable && ((Splittable)expr).getSplitRanges() != null;
   }

   private void throwUnprotectedSwitchError(VarNode varNode) {
      String msg = ECMAErrors.getMessage("syntax.error.unprotected.switch.declaration", varNode.isLet() ? "let" : "const");
      this.throwParserException(msg, varNode);
   }

   private void throwParserException(String message, Node origin) {
      if (origin == null) {
         throw new ParserException(message);
      } else {
         Source source = this.compiler.getSource();
         long token = origin.getToken();
         int line = source.getLine(origin.getStart());
         int column = source.getColumn(origin.getStart());
         String formatted = ErrorManager.format(message, source, line, column, token);
         throw new ParserException(JSErrorType.SYNTAX_ERROR, formatted, source, line, column, token);
      }
   }
}
