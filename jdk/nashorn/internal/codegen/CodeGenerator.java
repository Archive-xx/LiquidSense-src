package jdk.nashorn.internal.codegen;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;
import jdk.nashorn.internal.AssertsEnabled;
import jdk.nashorn.internal.IntDeque;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BaseNode;
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
import jdk.nashorn.internal.ir.GetSplitState;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IfNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.JoinPredecessorExpression;
import jdk.nashorn.internal.ir.JumpStatement;
import jdk.nashorn.internal.ir.JumpToInlinedFinally;
import jdk.nashorn.internal.ir.LabelNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LexicalContextNode;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.LocalVariableConversion;
import jdk.nashorn.internal.ir.LoopNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.ir.Optimistic;
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.ReturnNode;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.ir.SetSplitState;
import jdk.nashorn.internal.ir.SplitReturn;
import jdk.nashorn.internal.ir.Splittable;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.SwitchNode;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.TernaryNode;
import jdk.nashorn.internal.ir.ThrowNode;
import jdk.nashorn.internal.ir.TryNode;
import jdk.nashorn.internal.ir.UnaryNode;
import jdk.nashorn.internal.ir.VarNode;
import jdk.nashorn.internal.ir.WhileNode;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.visitor.NodeOperatorVisitor;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticReturnFilters;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.RewriteException;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(
   name = "codegen"
)
final class CodeGenerator extends NodeOperatorVisitor<CodeGeneratorLexicalContext> implements Loggable {
   private static final Type SCOPE_TYPE = Type.typeFor(ScriptObject.class);
   private static final String GLOBAL_OBJECT = Type.getInternalName(Global.class);
   private static final CompilerConstants.Call CREATE_REWRITE_EXCEPTION = CompilerConstants.staticCallNoLookup(RewriteException.class, "create", RewriteException.class, UnwarrantedOptimismException.class, Object[].class, String[].class);
   private static final CompilerConstants.Call CREATE_REWRITE_EXCEPTION_REST_OF = CompilerConstants.staticCallNoLookup(RewriteException.class, "create", RewriteException.class, UnwarrantedOptimismException.class, Object[].class, String[].class, int[].class);
   private static final CompilerConstants.Call ENSURE_INT;
   private static final CompilerConstants.Call ENSURE_NUMBER;
   private static final CompilerConstants.Call CREATE_FUNCTION_OBJECT;
   private static final CompilerConstants.Call CREATE_FUNCTION_OBJECT_NO_SCOPE;
   private static final CompilerConstants.Call TO_NUMBER_FOR_EQ;
   private static final CompilerConstants.Call TO_NUMBER_FOR_STRICT_EQ;
   private static final Class<?> ITERATOR_CLASS;
   private static final Type ITERATOR_TYPE;
   private static final Type EXCEPTION_TYPE;
   private static final Integer INT_ZERO;
   private final Compiler compiler;
   private final boolean evalCode;
   private final int callSiteFlags;
   private int regexFieldCount;
   private int lastLineNumber = -1;
   private static final int MAX_REGEX_FIELDS = 2048;
   private MethodEmitter method;
   private CompileUnit unit;
   private final DebugLogger log;
   static final int OBJECT_SPILL_THRESHOLD;
   private final Set<String> emittedMethods = new HashSet();
   private CodeGenerator.ContinuationInfo continuationInfo;
   private final Deque<Label> scopeEntryLabels = new ArrayDeque();
   private static final Label METHOD_BOUNDARY;
   private final Deque<Label> catchLabels = new ArrayDeque();
   private final IntDeque labeledBlockBreakLiveLocals = new IntDeque();
   private final int[] continuationEntryPoints;

   CodeGenerator(Compiler compiler, int[] continuationEntryPoints) {
      super(new CodeGeneratorLexicalContext());
      this.compiler = compiler;
      this.evalCode = compiler.getSource().isEvalCode();
      this.continuationEntryPoints = continuationEntryPoints;
      this.callSiteFlags = compiler.getScriptEnvironment()._callsite_flags;
      this.log = this.initLogger(compiler.getContext());
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   int getCallSiteFlags() {
      return ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction().getCallSiteFlags() | this.callSiteFlags;
   }

   private int getScopeCallSiteFlags(Symbol symbol) {
      assert symbol.isScope();

      int flags = this.getCallSiteFlags() | 1;
      if (this.isEvalCode() && symbol.isGlobal()) {
         return flags;
      } else {
         return this.isFastScope(symbol) ? flags | 4 : flags;
      }
   }

   boolean isEvalCode() {
      return this.evalCode;
   }

   boolean useDualFields() {
      return this.compiler.getContext().useDualFields();
   }

   private MethodEmitter loadIdent(IdentNode identNode, final CodeGenerator.TypeBounds resultBounds) {
      this.checkTemporalDeadZone(identNode);
      final Symbol symbol = identNode.getSymbol();
      if (!symbol.isScope()) {
         Type type = identNode.getType();
         if (type == Type.UNDEFINED) {
            return this.method.loadUndefined(resultBounds.widest);
         } else {
            assert symbol.hasSlot() || symbol.isParam();

            return this.method.load(identNode);
         }
      } else {
         assert identNode.getSymbol().isScope() : identNode + " is not in scope!";

         final int flags = this.getScopeCallSiteFlags(symbol);
         if (this.isFastScope(symbol)) {
            if (symbol.getUseCount() > 200 && !identNode.isOptimistic()) {
               (new CodeGenerator.OptimisticOperation(identNode, CodeGenerator.TypeBounds.OBJECT) {
                  void loadStack() {
                     CodeGenerator.this.method.loadCompilerConstant(CompilerConstants.SCOPE);
                  }

                  void consumeStack() {
                     CodeGenerator.this.loadSharedScopeVar(resultBounds.widest, symbol, flags);
                  }
               }).emit();
            } else {
               (new CodeGenerator.LoadFastScopeVar(identNode, resultBounds, flags)).emit();
            }
         } else {
            (new CodeGenerator.LoadScopeVar(identNode, resultBounds, flags)).emit();
         }

         return this.method;
      }
   }

   private void checkTemporalDeadZone(IdentNode identNode) {
      if (identNode.isDead()) {
         this.method.load(identNode.getSymbol().getName()).invoke(ScriptRuntime.THROW_REFERENCE_ERROR);
      }

   }

   private void checkAssignTarget(Expression expression) {
      if (expression instanceof IdentNode && ((IdentNode)expression).getSymbol().isConst()) {
         this.method.load(((IdentNode)expression).getSymbol().getName()).invoke(ScriptRuntime.THROW_CONST_TYPE_ERROR);
      }

   }

   private boolean isRestOf() {
      return this.continuationEntryPoints != null;
   }

   private boolean isCurrentContinuationEntryPoint(int programPoint) {
      return this.isRestOf() && this.getCurrentContinuationEntryPoint() == programPoint;
   }

   private int[] getContinuationEntryPoints() {
      return this.isRestOf() ? this.continuationEntryPoints : null;
   }

   private int getCurrentContinuationEntryPoint() {
      return this.isRestOf() ? this.continuationEntryPoints[0] : -1;
   }

   private boolean isContinuationEntryPoint(int programPoint) {
      if (this.isRestOf()) {
         assert this.continuationEntryPoints != null;

         int[] var2 = this.continuationEntryPoints;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            int cep = var2[var4];
            if (cep == programPoint) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isFastScope(Symbol symbol) {
      if (!symbol.isScope()) {
         return false;
      } else if (!((CodeGeneratorLexicalContext)this.lc).inDynamicScope()) {
         assert symbol.isGlobal() || ((CodeGeneratorLexicalContext)this.lc).getDefiningBlock(symbol).needsScope() : symbol.getName();

         return true;
      } else if (symbol.isGlobal()) {
         return false;
      } else {
         String name = symbol.getName();
         boolean previousWasBlock = false;
         Iterator it = ((CodeGeneratorLexicalContext)this.lc).getAllNodes();

         while(true) {
            while(it.hasNext()) {
               LexicalContextNode node = (LexicalContextNode)it.next();
               if (!(node instanceof Block)) {
                  if (node instanceof WithNode && previousWasBlock || node instanceof FunctionNode && ((FunctionNode)node).needsDynamicScope()) {
                     return false;
                  }

                  previousWasBlock = false;
               } else {
                  Block block = (Block)node;
                  if (block.getExistingSymbol(name) == symbol) {
                     assert block.needsScope();

                     return true;
                  }

                  previousWasBlock = true;
               }
            }

            throw new AssertionError();
         }
      }
   }

   private MethodEmitter loadSharedScopeVar(Type valueType, Symbol symbol, int flags) {
      assert this.isFastScope(symbol);

      this.method.load(this.getScopeProtoDepth(((CodeGeneratorLexicalContext)this.lc).getCurrentBlock(), symbol));
      return ((CodeGeneratorLexicalContext)this.lc).getScopeGet(this.unit, symbol, valueType, flags).generateInvoke(this.method);
   }

   private MethodEmitter storeFastScopeVar(Symbol symbol, int flags) {
      this.loadFastScopeProto(symbol, true);
      this.method.dynamicSet(symbol.getName(), flags, false);
      return this.method;
   }

   private int getScopeProtoDepth(Block startingBlock, Symbol symbol) {
      FunctionNode fn = ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction();
      int externalDepth = this.compiler.getScriptFunctionData(fn.getId()).getExternalSymbolDepth(symbol.getName());
      int internalDepth = FindScopeDepths.findInternalDepth(this.lc, fn, startingBlock, symbol);
      int scopesToStart = FindScopeDepths.findScopesToStart(this.lc, fn, startingBlock);
      int depth = false;
      int depth;
      if (internalDepth == -1) {
         depth = scopesToStart + externalDepth;
      } else {
         assert internalDepth <= scopesToStart;

         depth = internalDepth;
      }

      return depth;
   }

   private void loadFastScopeProto(Symbol symbol, boolean swap) {
      int depth = this.getScopeProtoDepth(((CodeGeneratorLexicalContext)this.lc).getCurrentBlock(), symbol);

      assert depth != -1 : "Couldn't find scope depth for symbol " + symbol.getName() + " in " + ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction();

      if (depth > 0) {
         if (swap) {
            this.method.swap();
         }

         for(int i = 0; i < depth; ++i) {
            this.method.invoke(ScriptObject.GET_PROTO);
         }

         if (swap) {
            this.method.swap();
         }
      }

   }

   private MethodEmitter loadExpressionUnbounded(Expression expr) {
      return this.loadExpression(expr, CodeGenerator.TypeBounds.UNBOUNDED);
   }

   private MethodEmitter loadExpressionAsObject(Expression expr) {
      return this.loadExpression(expr, CodeGenerator.TypeBounds.OBJECT);
   }

   MethodEmitter loadExpressionAsBoolean(Expression expr) {
      return this.loadExpression(expr, CodeGenerator.TypeBounds.BOOLEAN);
   }

   private static boolean noToPrimitiveConversion(Type source, Type target) {
      return source.isJSPrimitive() || !target.isJSPrimitive() || target.isBoolean();
   }

   MethodEmitter loadBinaryOperands(BinaryNode binaryNode) {
      return this.loadBinaryOperands(binaryNode.lhs(), binaryNode.rhs(), CodeGenerator.TypeBounds.UNBOUNDED.notWiderThan(binaryNode.getWidestOperandType()), false, false);
   }

   private MethodEmitter loadBinaryOperands(Expression lhs, Expression rhs, CodeGenerator.TypeBounds explicitOperandBounds, boolean baseAlreadyOnStack, boolean forceConversionSeparation) {
      Type lhsType = undefinedToNumber(lhs.getType());
      Type rhsType = undefinedToNumber(rhs.getType());
      Type narrowestOperandType = Type.narrowest(Type.widest(lhsType, rhsType), explicitOperandBounds.widest);
      CodeGenerator.TypeBounds operandBounds = explicitOperandBounds.notNarrowerThan(narrowestOperandType);
      CodeGenerator.TypeBounds safeConvertBounds;
      if (!noToPrimitiveConversion(lhsType, explicitOperandBounds.widest) && !rhs.isLocal()) {
         safeConvertBounds = CodeGenerator.TypeBounds.UNBOUNDED.notNarrowerThan(narrowestOperandType);
         this.loadExpression(lhs, safeConvertBounds, baseAlreadyOnStack);
         Type lhsLoadedType = this.method.peekType();
         this.loadExpression(rhs, safeConvertBounds, false);
         Type convertedLhsType = operandBounds.within(this.method.peekType());
         if (convertedLhsType != lhsLoadedType) {
            this.method.swap().convert(convertedLhsType).swap();
         }

         this.method.convert(operandBounds.within(this.method.peekType()));
      } else if (forceConversionSeparation) {
         safeConvertBounds = CodeGenerator.TypeBounds.UNBOUNDED.notNarrowerThan(narrowestOperandType);
         this.loadExpression(lhs, safeConvertBounds, baseAlreadyOnStack);
         this.method.convert(operandBounds.within(this.method.peekType()));
         this.loadExpression(rhs, safeConvertBounds, false);
         this.method.convert(operandBounds.within(this.method.peekType()));
      } else {
         this.loadExpression(lhs, operandBounds, baseAlreadyOnStack);
         this.loadExpression(rhs, operandBounds, false);
      }

      assert Type.generic(this.method.peekType()) == operandBounds.narrowest;

      assert Type.generic(this.method.peekType(1)) == operandBounds.narrowest;

      return this.method;
   }

   MethodEmitter loadComparisonOperands(BinaryNode cmp) {
      Expression lhs = cmp.lhs();
      Expression rhs = cmp.rhs();
      Type lhsType = lhs.getType();
      Type rhsType = rhs.getType();

      assert !lhsType.isObject() || !rhsType.isObject();

      if (!lhsType.isObject() && !rhsType.isObject()) {
         return this.loadBinaryOperands(cmp);
      } else {
         boolean canReorder = lhsType.isPrimitive() || rhs.isLocal();
         boolean canCombineLoadAndConvert = canReorder && cmp.isRelational();
         this.loadExpression(lhs, canCombineLoadAndConvert && !lhs.isOptimistic() ? CodeGenerator.TypeBounds.NUMBER : CodeGenerator.TypeBounds.UNBOUNDED);
         Type lhsLoadedType = this.method.peekType();
         TokenType tt = cmp.tokenType();
         if (canReorder) {
            emitObjectToNumberComparisonConversion(this.method, tt);
            this.loadExpression(rhs, canCombineLoadAndConvert && !rhs.isOptimistic() ? CodeGenerator.TypeBounds.NUMBER : CodeGenerator.TypeBounds.UNBOUNDED);
         } else {
            this.loadExpression(rhs, CodeGenerator.TypeBounds.UNBOUNDED);
            if (lhsLoadedType != Type.NUMBER) {
               this.method.swap();
               emitObjectToNumberComparisonConversion(this.method, tt);
               this.method.swap();
            }
         }

         emitObjectToNumberComparisonConversion(this.method, tt);
         return this.method;
      }
   }

   private static void emitObjectToNumberComparisonConversion(MethodEmitter method, TokenType tt) {
      switch(tt) {
      case EQ:
      case NE:
         if (method.peekType().isObject()) {
            TO_NUMBER_FOR_EQ.invoke(method);
            return;
         }
         break;
      case EQ_STRICT:
      case NE_STRICT:
         if (method.peekType().isObject()) {
            TO_NUMBER_FOR_STRICT_EQ.invoke(method);
            return;
         }
      }

      method.convert(Type.NUMBER);
   }

   private static final Type undefinedToNumber(Type type) {
      return (Type)(type == Type.UNDEFINED ? Type.NUMBER : type);
   }

   private static Type booleanToInt(Type t) {
      return (Type)(t == Type.BOOLEAN ? Type.INT : t);
   }

   private static Type objectToNumber(Type t) {
      return (Type)(t.isObject() ? Type.NUMBER : t);
   }

   MethodEmitter loadExpressionAsType(Expression expr, Type type) {
      if (type == Type.BOOLEAN) {
         return this.loadExpressionAsBoolean(expr);
      } else if (type == Type.UNDEFINED) {
         assert expr.getType() == Type.UNDEFINED;

         return this.loadExpressionAsObject(expr);
      } else {
         return this.loadExpression(expr, CodeGenerator.TypeBounds.UNBOUNDED.notNarrowerThan(type)).convert(type);
      }
   }

   private MethodEmitter loadExpression(Expression expr, CodeGenerator.TypeBounds resultBounds) {
      return this.loadExpression(expr, resultBounds, false);
   }

   private MethodEmitter loadExpression(Expression expr, final CodeGenerator.TypeBounds resultBounds, final boolean baseAlreadyOnStack) {
      boolean isCurrentDiscard = ((CodeGeneratorLexicalContext)this.lc).isCurrentDiscard(expr);
      expr.accept(new NodeOperatorVisitor<LexicalContext>(new LexicalContext()) {
         public boolean enterIdentNode(IdentNode identNode) {
            CodeGenerator.this.loadIdent(identNode, resultBounds);
            return false;
         }

         public boolean enterAccessNode(final AccessNode accessNode) {
            (new CodeGenerator.OptimisticOperation(accessNode, resultBounds) {
               void loadStack() {
                  if (!baseAlreadyOnStack) {
                     CodeGenerator.this.loadExpressionAsObject(accessNode.getBase());
                  }

                  assert CodeGenerator.this.method.peekType().isObject();

               }

               void consumeStack() {
                  int flags = CodeGenerator.this.getCallSiteFlags();
                  this.dynamicGet(accessNode.getProperty(), flags, accessNode.isFunction(), accessNode.isIndex());
               }
            }).emit(baseAlreadyOnStack ? 1 : 0);
            return false;
         }

         public boolean enterIndexNode(final IndexNode indexNode) {
            (new CodeGenerator.OptimisticOperation(indexNode, resultBounds) {
               void loadStack() {
                  if (!baseAlreadyOnStack) {
                     CodeGenerator.this.loadExpressionAsObject(indexNode.getBase());
                     CodeGenerator.this.loadExpressionUnbounded(indexNode.getIndex());
                  }

               }

               void consumeStack() {
                  int flags = CodeGenerator.this.getCallSiteFlags();
                  this.dynamicGetIndex(flags, indexNode.isFunction());
               }
            }).emit(baseAlreadyOnStack ? 2 : 0);
            return false;
         }

         public boolean enterFunctionNode(FunctionNode functionNode) {
            this.lc.pop(functionNode);
            functionNode.accept(CodeGenerator.this);
            this.lc.push(functionNode);
            return false;
         }

         public boolean enterASSIGN(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN(binaryNode);
            return false;
         }

         public boolean enterASSIGN_ADD(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_ADD(binaryNode);
            return false;
         }

         public boolean enterASSIGN_BIT_AND(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_BIT_AND(binaryNode);
            return false;
         }

         public boolean enterASSIGN_BIT_OR(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_BIT_OR(binaryNode);
            return false;
         }

         public boolean enterASSIGN_BIT_XOR(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_BIT_XOR(binaryNode);
            return false;
         }

         public boolean enterASSIGN_DIV(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_DIV(binaryNode);
            return false;
         }

         public boolean enterASSIGN_MOD(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_MOD(binaryNode);
            return false;
         }

         public boolean enterASSIGN_MUL(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_MUL(binaryNode);
            return false;
         }

         public boolean enterASSIGN_SAR(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_SAR(binaryNode);
            return false;
         }

         public boolean enterASSIGN_SHL(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_SHL(binaryNode);
            return false;
         }

         public boolean enterASSIGN_SHR(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_SHR(binaryNode);
            return false;
         }

         public boolean enterASSIGN_SUB(BinaryNode binaryNode) {
            CodeGenerator.this.checkAssignTarget(binaryNode.lhs());
            CodeGenerator.this.loadASSIGN_SUB(binaryNode);
            return false;
         }

         public boolean enterCallNode(CallNode callNode) {
            return CodeGenerator.this.loadCallNode(callNode, resultBounds);
         }

         public boolean enterLiteralNode(LiteralNode<?> literalNode) {
            CodeGenerator.this.loadLiteral(literalNode, resultBounds);
            return false;
         }

         public boolean enterTernaryNode(TernaryNode ternaryNode) {
            CodeGenerator.this.loadTernaryNode(ternaryNode, resultBounds);
            return false;
         }

         public boolean enterADD(BinaryNode binaryNode) {
            CodeGenerator.this.loadADD(binaryNode, resultBounds);
            return false;
         }

         public boolean enterSUB(UnaryNode unaryNode) {
            CodeGenerator.this.loadSUB(unaryNode, resultBounds);
            return false;
         }

         public boolean enterSUB(BinaryNode binaryNode) {
            CodeGenerator.this.loadSUB(binaryNode, resultBounds);
            return false;
         }

         public boolean enterMUL(BinaryNode binaryNode) {
            CodeGenerator.this.loadMUL(binaryNode, resultBounds);
            return false;
         }

         public boolean enterDIV(BinaryNode binaryNode) {
            CodeGenerator.this.loadDIV(binaryNode, resultBounds);
            return false;
         }

         public boolean enterMOD(BinaryNode binaryNode) {
            CodeGenerator.this.loadMOD(binaryNode, resultBounds);
            return false;
         }

         public boolean enterSAR(BinaryNode binaryNode) {
            CodeGenerator.this.loadSAR(binaryNode);
            return false;
         }

         public boolean enterSHL(BinaryNode binaryNode) {
            CodeGenerator.this.loadSHL(binaryNode);
            return false;
         }

         public boolean enterSHR(BinaryNode binaryNode) {
            CodeGenerator.this.loadSHR(binaryNode);
            return false;
         }

         public boolean enterCOMMALEFT(BinaryNode binaryNode) {
            CodeGenerator.this.loadCOMMALEFT(binaryNode, resultBounds);
            return false;
         }

         public boolean enterCOMMARIGHT(BinaryNode binaryNode) {
            CodeGenerator.this.loadCOMMARIGHT(binaryNode, resultBounds);
            return false;
         }

         public boolean enterAND(BinaryNode binaryNode) {
            CodeGenerator.this.loadAND_OR(binaryNode, resultBounds, true);
            return false;
         }

         public boolean enterOR(BinaryNode binaryNode) {
            CodeGenerator.this.loadAND_OR(binaryNode, resultBounds, false);
            return false;
         }

         public boolean enterNOT(UnaryNode unaryNode) {
            CodeGenerator.this.loadNOT(unaryNode);
            return false;
         }

         public boolean enterADD(UnaryNode unaryNode) {
            CodeGenerator.this.loadADD(unaryNode, resultBounds);
            return false;
         }

         public boolean enterBIT_NOT(UnaryNode unaryNode) {
            CodeGenerator.this.loadBIT_NOT(unaryNode);
            return false;
         }

         public boolean enterBIT_AND(BinaryNode binaryNode) {
            CodeGenerator.this.loadBIT_AND(binaryNode);
            return false;
         }

         public boolean enterBIT_OR(BinaryNode binaryNode) {
            CodeGenerator.this.loadBIT_OR(binaryNode);
            return false;
         }

         public boolean enterBIT_XOR(BinaryNode binaryNode) {
            CodeGenerator.this.loadBIT_XOR(binaryNode);
            return false;
         }

         public boolean enterVOID(UnaryNode unaryNode) {
            CodeGenerator.this.loadVOID(unaryNode, resultBounds);
            return false;
         }

         public boolean enterEQ(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.EQ);
            return false;
         }

         public boolean enterEQ_STRICT(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.EQ);
            return false;
         }

         public boolean enterGE(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.GE);
            return false;
         }

         public boolean enterGT(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.GT);
            return false;
         }

         public boolean enterLE(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.LE);
            return false;
         }

         public boolean enterLT(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.LT);
            return false;
         }

         public boolean enterNE(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.NE);
            return false;
         }

         public boolean enterNE_STRICT(BinaryNode binaryNode) {
            CodeGenerator.this.loadCmp(binaryNode, Condition.NE);
            return false;
         }

         public boolean enterObjectNode(ObjectNode objectNode) {
            CodeGenerator.this.loadObjectNode(objectNode);
            return false;
         }

         public boolean enterRuntimeNode(RuntimeNode runtimeNode) {
            CodeGenerator.this.loadRuntimeNode(runtimeNode);
            return false;
         }

         public boolean enterNEW(UnaryNode unaryNode) {
            CodeGenerator.this.loadNEW(unaryNode);
            return false;
         }

         public boolean enterDECINC(UnaryNode unaryNode) {
            CodeGenerator.this.checkAssignTarget(unaryNode.getExpression());
            CodeGenerator.this.loadDECINC(unaryNode);
            return false;
         }

         public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinExpr) {
            CodeGenerator.this.loadMaybeDiscard(joinExpr, joinExpr.getExpression(), resultBounds);
            return false;
         }

         public boolean enterGetSplitState(GetSplitState getSplitState) {
            CodeGenerator.this.method.loadScope();
            CodeGenerator.this.method.invoke(Scope.GET_SPLIT_STATE);
            return false;
         }

         public boolean enterDefault(Node otherNode) {
            throw new AssertionError(otherNode.getClass().getName());
         }
      });
      if (!isCurrentDiscard) {
         this.coerceStackTop(resultBounds);
      }

      return this.method;
   }

   private MethodEmitter coerceStackTop(CodeGenerator.TypeBounds typeBounds) {
      return this.method.convert(typeBounds.within(this.method.peekType()));
   }

   private void closeBlockVariables(Block block) {
      Iterator var2 = block.getSymbols().iterator();

      while(var2.hasNext()) {
         Symbol symbol = (Symbol)var2.next();
         if (symbol.isBytecodeLocal()) {
            this.method.closeLocalVariable(symbol, block.getBreakLabel());
         }
      }

   }

   public boolean enterBlock(Block block) {
      Label entryLabel = block.getEntryLabel();
      if (entryLabel.isBreakTarget()) {
         assert !this.method.isReachable();

         this.method.breakLabel(entryLabel, ((CodeGeneratorLexicalContext)this.lc).getUsedSlotCount());
      } else {
         this.method.label(entryLabel);
      }

      if (!this.method.isReachable()) {
         return false;
      } else if (((CodeGeneratorLexicalContext)this.lc).isFunctionBody() && this.emittedMethods.contains(((CodeGeneratorLexicalContext)this.lc).getCurrentFunction().getName())) {
         return false;
      } else {
         this.initLocals(block);

         assert ((CodeGeneratorLexicalContext)this.lc).getUsedSlotCount() == this.method.getFirstTemp();

         return true;
      }
   }

   boolean useOptimisticTypes() {
      return !((CodeGeneratorLexicalContext)this.lc).inSplitNode() && this.compiler.useOptimisticTypes();
   }

   public Node leaveBlock(Block block) {
      this.popBlockScope(block);
      this.method.beforeJoinPoint(block);
      this.closeBlockVariables(block);
      ((CodeGeneratorLexicalContext)this.lc).releaseSlots();

      assert !this.method.isReachable() || (((CodeGeneratorLexicalContext)this.lc).isFunctionBody() ? 0 : ((CodeGeneratorLexicalContext)this.lc).getUsedSlotCount()) == this.method.getFirstTemp() : "reachable=" + this.method.isReachable() + " isFunctionBody=" + ((CodeGeneratorLexicalContext)this.lc).isFunctionBody() + " usedSlotCount=" + ((CodeGeneratorLexicalContext)this.lc).getUsedSlotCount() + " firstTemp=" + this.method.getFirstTemp();

      return block;
   }

   private void popBlockScope(Block block) {
      Label breakLabel = block.getBreakLabel();
      if (block.needsScope() && !((CodeGeneratorLexicalContext)this.lc).isFunctionBody()) {
         Label beginTryLabel = (Label)this.scopeEntryLabels.pop();
         Label recoveryLabel = new Label("block_popscope_catch");
         this.emitBlockBreakLabel(breakLabel);
         boolean bodyCanThrow = breakLabel.isAfter(beginTryLabel);
         if (bodyCanThrow) {
            this.method._try(beginTryLabel, breakLabel, recoveryLabel);
         }

         Label afterCatchLabel = null;
         if (this.method.isReachable()) {
            this.popScope();
            if (bodyCanThrow) {
               afterCatchLabel = new Label("block_after_catch");
               this.method._goto(afterCatchLabel);
            }
         }

         if (bodyCanThrow) {
            assert !this.method.isReachable();

            this.method._catch(recoveryLabel);
            this.popScopeException();
            this.method.athrow();
         }

         if (afterCatchLabel != null) {
            this.method.label(afterCatchLabel);
         }

      } else {
         this.emitBlockBreakLabel(breakLabel);
      }
   }

   private void emitBlockBreakLabel(Label breakLabel) {
      LabelNode labelNode = ((CodeGeneratorLexicalContext)this.lc).getCurrentBlockLabelNode();
      if (labelNode != null) {
         assert labelNode.getLocalVariableConversion() == null || this.method.isReachable();

         this.method.beforeJoinPoint(labelNode);
         this.method.breakLabel(breakLabel, this.labeledBlockBreakLiveLocals.pop());
      } else {
         this.method.label(breakLabel);
      }

   }

   private void popScope() {
      this.popScopes(1);
   }

   private void popScopeException() {
      this.popScope();
      CodeGenerator.ContinuationInfo ci = this.getContinuationInfo();
      if (ci != null) {
         Label catchLabel = ci.catchLabel;
         if (catchLabel != METHOD_BOUNDARY && catchLabel == this.catchLabels.peek()) {
            ++ci.exceptionScopePops;
         }
      }

   }

   private void popScopesUntil(LexicalContextNode until) {
      this.popScopes(((CodeGeneratorLexicalContext)this.lc).getScopeNestingLevelTo(until));
   }

   private void popScopes(int count) {
      if (count != 0) {
         assert count > 0;

         if (this.method.hasScope()) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);

            for(int i = 0; i < count; ++i) {
               this.method.invoke(ScriptObject.GET_PROTO);
            }

            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
         }
      }
   }

   public boolean enterBreakNode(BreakNode breakNode) {
      return this.enterJumpStatement(breakNode);
   }

   public boolean enterJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
      return this.enterJumpStatement(jumpToInlinedFinally);
   }

   private boolean enterJumpStatement(JumpStatement jump) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(jump);
         this.method.beforeJoinPoint(jump);
         this.popScopesUntil(jump.getPopScopeLimit(this.lc));
         Label targetLabel = jump.getTargetLabel(this.lc);
         targetLabel.markAsBreakTarget();
         this.method._goto(targetLabel);
         return false;
      }
   }

   private int loadArgs(List<Expression> args) {
      int argCount = args.size();
      if (argCount > 250) {
         this.loadArgsArray(args);
         return 1;
      } else {
         Iterator var3 = args.iterator();

         while(var3.hasNext()) {
            Expression arg = (Expression)var3.next();

            assert arg != null;

            this.loadExpressionUnbounded(arg);
         }

         return argCount;
      }
   }

   private boolean loadCallNode(final CallNode callNode, final CodeGenerator.TypeBounds resultBounds) {
      this.lineNumber(callNode.getLineNumber());
      final List<Expression> args = callNode.getArgs();
      final Expression function = callNode.getFunction();
      final Block currentBlock = ((CodeGeneratorLexicalContext)this.lc).getCurrentBlock();
      final CodeGeneratorLexicalContext codegenLexicalContext = (CodeGeneratorLexicalContext)this.lc;
      function.accept(new SimpleNodeVisitor() {
         private MethodEmitter sharedScopeCall(final IdentNode identNode, final int flags) {
            final Symbol symbol = identNode.getSymbol();
            final boolean isFastScope = CodeGenerator.this.isFastScope(symbol);
            (new CodeGenerator.OptimisticOperation(callNode, resultBounds) {
               void loadStack() {
                  CodeGenerator.this.method.loadCompilerConstant(CompilerConstants.SCOPE);
                  if (isFastScope) {
                     CodeGenerator.this.method.load(CodeGenerator.this.getScopeProtoDepth(currentBlock, symbol));
                  } else {
                     CodeGenerator.this.method.load(-1);
                  }

                  CodeGenerator.this.loadArgs(args);
               }

               void consumeStack() {
                  Type[] paramTypes = CodeGenerator.this.method.getTypesFromStack(args.size());

                  for(int i = 0; i < paramTypes.length; ++i) {
                     paramTypes[i] = Type.generic(paramTypes[i]);
                  }

                  SharedScopeCall scopeCall = codegenLexicalContext.getScopeCall(CodeGenerator.this.unit, symbol, identNode.getType(), resultBounds.widest, paramTypes, flags);
                  scopeCall.generateInvoke(CodeGenerator.this.method);
               }
            }).emit();
            return CodeGenerator.this.method;
         }

         private void scopeCall(final IdentNode ident, final int flags) {
            (new CodeGenerator.OptimisticOperation(callNode, resultBounds) {
               int argsCount;

               void loadStack() {
                  CodeGenerator.this.loadExpressionAsObject(ident);
                  CodeGenerator.this.method.loadUndefined(Type.OBJECT);
                  this.argsCount = CodeGenerator.this.loadArgs(args);
               }

               void consumeStack() {
                  this.dynamicCall(2 + this.argsCount, flags, ident.getName());
               }
            }).emit();
         }

         private void evalCall(final IdentNode ident, final int flags) {
            final Label invoke_direct_eval = new Label("invoke_direct_eval");
            final Label is_not_eval = new Label("is_not_eval");
            final Label eval_done = new Label("eval_done");
            (new CodeGenerator.OptimisticOperation(callNode, resultBounds) {
               int argsCount;

               void loadStack() {
                  CodeGenerator.this.loadExpressionAsObject(ident.setIsNotFunction());
                  CodeGenerator.this.globalIsEval();
                  CodeGenerator.this.method.ifeq(is_not_eval);
                  CodeGenerator.this.method.loadCompilerConstant(CompilerConstants.SCOPE);
                  List<Expression> evalArgs = callNode.getEvalArgs().getArgs();
                  CodeGenerator.this.loadExpressionAsObject((Expression)evalArgs.get(0));
                  int numArgs = evalArgs.size();

                  for(int i = 1; i < numArgs; ++i) {
                     CodeGenerator.this.loadAndDiscard((Expression)evalArgs.get(i));
                  }

                  CodeGenerator.this.method._goto(invoke_direct_eval);
                  CodeGenerator.this.method.label(is_not_eval);
                  CodeGenerator.this.loadExpressionAsObject(ident);
                  CodeGenerator.this.method.loadNull();
                  this.argsCount = CodeGenerator.this.loadArgs(callNode.getArgs());
               }

               void consumeStack() {
                  this.dynamicCall(2 + this.argsCount, flags, "eval");
                  CodeGenerator.this.method._goto(eval_done);
                  CodeGenerator.this.method.label(invoke_direct_eval);
                  CodeGenerator.this.method.loadCompilerConstant(CompilerConstants.THIS);
                  CodeGenerator.this.method.load(callNode.getEvalArgs().getLocation());
                  CodeGenerator.this.method.load(((CodeGeneratorLexicalContext)CodeGenerator.this.lc).getCurrentFunction().isStrict());
                  CodeGenerator.this.globalDirectEval();
                  this.convertOptimisticReturnValue();
                  CodeGenerator.this.coerceStackTop(resultBounds);
               }
            }).emit();
            CodeGenerator.this.method.label(eval_done);
         }

         public boolean enterIdentNode(IdentNode node) {
            Symbol symbol = node.getSymbol();
            if (symbol.isScope()) {
               int flags = CodeGenerator.this.getScopeCallSiteFlags(symbol);
               int useCount = symbol.getUseCount();
               if (callNode.isEval()) {
                  this.evalCall(node, flags);
               } else if (useCount > 4 && (CodeGenerator.this.isFastScope(symbol) || useCount > 500) && !((CodeGeneratorLexicalContext)CodeGenerator.this.lc).inDynamicScope() && !callNode.isOptimistic()) {
                  this.sharedScopeCall(node, flags);
               } else {
                  this.scopeCall(node, flags);
               }

               assert CodeGenerator.this.method.peekType().equals(resultBounds.within(callNode.getType())) : CodeGenerator.this.method.peekType() + " != " + resultBounds + "(" + callNode.getType() + ")";
            } else {
               this.enterDefault(node);
            }

            return false;
         }

         public boolean enterAccessNode(final AccessNode node) {
            final int flags = CodeGenerator.this.getCallSiteFlags() | (callNode.isApplyToCall() ? 16 : 0);
            (new CodeGenerator.OptimisticOperation(callNode, resultBounds) {
               int argCount;

               void loadStack() {
                  CodeGenerator.this.loadExpressionAsObject(node.getBase());
                  CodeGenerator.this.method.dup();

                  assert !node.isOptimistic();

                  CodeGenerator.this.method.dynamicGet(node.getType(), node.getProperty(), flags, true, node.isIndex());
                  CodeGenerator.this.method.swap();
                  this.argCount = CodeGenerator.this.loadArgs(args);
               }

               void consumeStack() {
                  this.dynamicCall(2 + this.argCount, flags, node.toString(false));
               }
            }).emit();
            return false;
         }

         public boolean enterFunctionNode(final FunctionNode origCallee) {
            (new CodeGenerator.OptimisticOperation(callNode, resultBounds) {
               FunctionNode callee;
               int argsCount;

               void loadStack() {
                  this.callee = (FunctionNode)origCallee.accept(CodeGenerator.this);
                  if (this.callee.isStrict()) {
                     CodeGenerator.this.method.loadUndefined(Type.OBJECT);
                  } else {
                     CodeGenerator.this.globalInstance();
                  }

                  this.argsCount = CodeGenerator.this.loadArgs(args);
               }

               void consumeStack() {
                  this.dynamicCall(2 + this.argsCount, CodeGenerator.this.getCallSiteFlags(), (String)null);
               }
            }).emit();
            return false;
         }

         public boolean enterIndexNode(final IndexNode node) {
            (new CodeGenerator.OptimisticOperation(callNode, resultBounds) {
               int argsCount;

               void loadStack() {
                  CodeGenerator.this.loadExpressionAsObject(node.getBase());
                  CodeGenerator.this.method.dup();
                  Type indexType = node.getIndex().getType();
                  if (!indexType.isObject() && !indexType.isBoolean()) {
                     CodeGenerator.this.loadExpressionUnbounded(node.getIndex());
                  } else {
                     CodeGenerator.this.loadExpressionAsObject(node.getIndex());
                  }

                  assert !node.isOptimistic();

                  CodeGenerator.this.method.dynamicGetIndex(node.getType(), CodeGenerator.this.getCallSiteFlags(), true);
                  CodeGenerator.this.method.swap();
                  this.argsCount = CodeGenerator.this.loadArgs(args);
               }

               void consumeStack() {
                  this.dynamicCall(2 + this.argsCount, CodeGenerator.this.getCallSiteFlags(), node.toString(false));
               }
            }).emit();
            return false;
         }

         protected boolean enterDefault(final Node node) {
            (new CodeGenerator.OptimisticOperation(callNode, resultBounds) {
               int argsCount;

               void loadStack() {
                  CodeGenerator.this.loadExpressionAsObject(function);
                  CodeGenerator.this.method.loadUndefined(Type.OBJECT);
                  this.argsCount = CodeGenerator.this.loadArgs(args);
               }

               void consumeStack() {
                  int flags = CodeGenerator.this.getCallSiteFlags() | 1;
                  this.dynamicCall(2 + this.argsCount, flags, node.toString(false));
               }
            }).emit();
            return false;
         }
      });
      return false;
   }

   static int nonOptimisticFlags(int flags) {
      return flags & 2039;
   }

   public boolean enterContinueNode(ContinueNode continueNode) {
      return this.enterJumpStatement(continueNode);
   }

   public boolean enterEmptyNode(EmptyNode emptyNode) {
      return false;
   }

   public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(expressionStatement);
         this.loadAndDiscard(expressionStatement.getExpression());

         assert this.method.getStackSize() == 0 : "stack not empty in " + expressionStatement;

         return false;
      }
   }

   public boolean enterBlockStatement(BlockStatement blockStatement) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(blockStatement);
         blockStatement.getBlock().accept(this);
         return false;
      }
   }

   public boolean enterForNode(ForNode forNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(forNode);
         if (forNode.isForIn()) {
            this.enterForIn(forNode);
         } else {
            Expression init = forNode.getInit();
            if (init != null) {
               this.loadAndDiscard(init);
            }

            this.enterForOrWhile(forNode, forNode.getModify());
         }

         return false;
      }
   }

   private void enterForIn(final ForNode forNode) {
      this.loadExpression(forNode.getModify(), CodeGenerator.TypeBounds.OBJECT);
      this.method.invoke(forNode.isForEach() ? ScriptRuntime.TO_VALUE_ITERATOR : ScriptRuntime.TO_PROPERTY_ITERATOR);
      Symbol iterSymbol = forNode.getIterator();
      final int iterSlot = iterSymbol.getSlot(Type.OBJECT);
      this.method.store(iterSymbol, ITERATOR_TYPE);
      this.method.beforeJoinPoint(forNode);
      Label continueLabel = forNode.getContinueLabel();
      Label breakLabel = forNode.getBreakLabel();
      this.method.label(continueLabel);
      this.method.load(ITERATOR_TYPE, iterSlot);
      this.method.invoke(CompilerConstants.interfaceCallNoLookup(ITERATOR_CLASS, "hasNext", Boolean.TYPE));
      JoinPredecessorExpression test = forNode.getTest();
      Block body = forNode.getBody();
      if (LocalVariableConversion.hasLiveConversion(test)) {
         Label afterConversion = new Label("for_in_after_test_conv");
         this.method.ifne(afterConversion);
         this.method.beforeJoinPoint(test);
         this.method._goto(breakLabel);
         this.method.label(afterConversion);
      } else {
         this.method.ifeq(breakLabel);
      }

      (new CodeGenerator.Store<Expression>(forNode.getInit()) {
         protected void storeNonDiscard() {
         }

         protected void evaluate() {
            (new CodeGenerator.OptimisticOperation((Optimistic)forNode.getInit(), CodeGenerator.TypeBounds.UNBOUNDED) {
               void loadStack() {
                  CodeGenerator.this.method.load(CodeGenerator.ITERATOR_TYPE, iterSlot);
               }

               void consumeStack() {
                  CodeGenerator.this.method.invoke(CompilerConstants.interfaceCallNoLookup(CodeGenerator.ITERATOR_CLASS, "next", Object.class));
                  this.convertOptimisticReturnValue();
               }
            }).emit();
         }
      }).store();
      body.accept(this);
      if (this.method.isReachable()) {
         this.method._goto(continueLabel);
      }

      this.method.label(breakLabel);
   }

   private void initLocals(Block block) {
      ((CodeGeneratorLexicalContext)this.lc).onEnterBlock(block);
      boolean isFunctionBody = ((CodeGeneratorLexicalContext)this.lc).isFunctionBody();
      FunctionNode function = ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction();
      if (isFunctionBody) {
         this.initializeMethodParameters(function);
         if (!function.isVarArg()) {
            this.expandParameterSlots(function);
         }

         if (this.method.hasScope()) {
            if (function.needsParentScope()) {
               this.method.loadCompilerConstant(CompilerConstants.CALLEE);
               this.method.invoke(ScriptFunction.GET_SCOPE);
            } else {
               assert function.hasScopeBlock();

               this.method.loadNull();
            }

            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
         }

         if (function.needsArguments()) {
            this.initArguments(function);
         }
      }

      if (block.needsScope()) {
         boolean varsInScope = function.allVarsInScope();
         boolean hasArguments = function.needsArguments();
         List<MapTuple<Symbol>> tuples = new ArrayList();
         Iterator<IdentNode> paramIter = function.getParameters().iterator();
         Iterator var8 = block.getSymbols().iterator();

         label158:
         while(true) {
            while(true) {
               while(true) {
                  Symbol symbol;
                  do {
                     do {
                        if (!var8.hasNext()) {
                           (new FieldObjectCreator<Symbol>(this, tuples, true, hasArguments) {
                              protected void loadValue(Symbol value, Type type) {
                                 CodeGenerator.this.method.load(value, type);
                              }
                           }).makeObject(this.method);
                           if (isFunctionBody && function.isProgram()) {
                              this.method.invoke(ScriptRuntime.MERGE_SCOPE);
                           }

                           this.method.storeCompilerConstant(CompilerConstants.SCOPE);
                           if (!isFunctionBody) {
                              Label scopeEntryLabel = new Label("scope_entry");
                              this.scopeEntryLabels.push(scopeEntryLabel);
                              this.method.label(scopeEntryLabel);
                           }
                           break label158;
                        }

                        symbol = (Symbol)var8.next();
                     } while(symbol.isInternal());
                  } while(symbol.isThis());

                  if (symbol.isVar()) {
                     assert !varsInScope || symbol.isScope();

                     if (!varsInScope && !symbol.isScope()) {
                        assert symbol.hasSlot() || symbol.slotCount() == 0 : symbol + " should have a slot only, no scope";
                     } else {
                        assert symbol.isScope() : "scope for " + symbol + " should have been set in Lower already " + function.getName();

                        assert !symbol.hasSlot() : "slot for " + symbol + " should have been removed in Lower already" + function.getName();

                        tuples.add(new MapTuple(symbol.getName(), symbol, (Type)null));
                     }
                  } else if (symbol.isParam() && (varsInScope || hasArguments || symbol.isScope())) {
                     assert symbol.isScope() : "scope for " + symbol + " should have been set in AssignSymbols already " + function.getName() + " varsInScope=" + varsInScope + " hasArguments=" + hasArguments + " symbol.isScope()=" + symbol.isScope();

                     assert !hasArguments || !symbol.hasSlot() : "slot for " + symbol + " should have been removed in Lower already " + function.getName();

                     final Type paramType;
                     Symbol paramSymbol;
                     if (hasArguments) {
                        assert !symbol.hasSlot() : "slot for " + symbol + " should have been removed in Lower already ";

                        paramSymbol = null;
                        paramType = null;
                     } else {
                        paramSymbol = symbol;

                        IdentNode nextParam;
                        do {
                           nextParam = (IdentNode)paramIter.next();
                        } while(!nextParam.getName().equals(symbol.getName()));

                        paramType = nextParam.getType();
                     }

                     tuples.add(new MapTuple<Symbol>(symbol.getName(), symbol, paramType, paramSymbol) {
                        public Class<?> getValueType() {
                           return CodeGenerator.this.useDualFields() && this.value != null && paramType != null && !paramType.isBoolean() ? paramType.getTypeClass() : Object.class;
                        }
                     });
                  }
               }
            }
         }
      } else if (isFunctionBody && function.isVarArg()) {
         int nextParam = 0;
         Iterator var14 = function.getParameters().iterator();

         while(var14.hasNext()) {
            IdentNode param = (IdentNode)var14.next();
            param.getSymbol().setFieldIndex(nextParam++);
         }
      }

      this.printSymbols(block, function, (isFunctionBody ? "Function " : "Block in ") + (function.getIdent() == null ? "<anonymous>" : function.getIdent().getName()));
   }

   private void initializeMethodParameters(FunctionNode function) {
      Label functionStart = new Label("fn_start");
      this.method.label(functionStart);
      int nextSlot = 0;
      if (function.needsCallee()) {
         this.initializeInternalFunctionParameter(CompilerConstants.CALLEE, function, functionStart, nextSlot++);
      }

      this.initializeInternalFunctionParameter(CompilerConstants.THIS, function, functionStart, nextSlot++);
      if (function.isVarArg()) {
         this.initializeInternalFunctionParameter(CompilerConstants.VARARGS, function, functionStart, nextSlot++);
      } else {
         Iterator var4 = function.getParameters().iterator();

         while(var4.hasNext()) {
            IdentNode param = (IdentNode)var4.next();
            Symbol symbol = param.getSymbol();
            if (symbol.isBytecodeLocal()) {
               this.method.initializeMethodParameter(symbol, param.getType(), functionStart);
            }
         }
      }

   }

   private void initializeInternalFunctionParameter(CompilerConstants cc, FunctionNode fn, Label functionStart, int slot) {
      Symbol symbol = this.initializeInternalFunctionOrSplitParameter(cc, fn, functionStart, slot);

      assert symbol.getFirstSlot() == slot;

   }

   private Symbol initializeInternalFunctionOrSplitParameter(CompilerConstants cc, FunctionNode fn, Label functionStart, int slot) {
      Symbol symbol = fn.getBody().getExistingSymbol(cc.symbolName());
      Type type = Type.typeFor(cc.type());
      this.method.initializeMethodParameter(symbol, type, functionStart);
      this.method.onLocalStore(type, slot);
      return symbol;
   }

   private void expandParameterSlots(FunctionNode function) {
      List<IdentNode> parameters = function.getParameters();
      int currentIncomingSlot = function.needsCallee() ? 2 : 1;

      IdentNode parameter;
      for(Iterator var4 = parameters.iterator(); var4.hasNext(); currentIncomingSlot += parameter.getType().getSlots()) {
         parameter = (IdentNode)var4.next();
      }

      int i = parameters.size();

      while(i-- > 0) {
         parameter = (IdentNode)parameters.get(i);
         Type parameterType = parameter.getType();
         int typeWidth = parameterType.getSlots();
         currentIncomingSlot -= typeWidth;
         Symbol symbol = parameter.getSymbol();
         int slotCount = symbol.slotCount();

         assert slotCount > 0;

         assert symbol.isBytecodeLocal() || slotCount == typeWidth;

         this.method.onLocalStore(parameterType, currentIncomingSlot);
         if (currentIncomingSlot != symbol.getSlot(parameterType)) {
            this.method.load(parameterType, currentIncomingSlot);
            this.method.store(symbol, parameterType);
         }
      }

   }

   private void initArguments(FunctionNode function) {
      this.method.loadCompilerConstant(CompilerConstants.VARARGS);
      if (function.needsCallee()) {
         this.method.loadCompilerConstant(CompilerConstants.CALLEE);
      } else {
         assert function.isStrict();

         this.method.loadNull();
      }

      this.method.load(function.getParameters().size());
      this.globalAllocateArguments();
      this.method.storeCompilerConstant(CompilerConstants.ARGUMENTS);
   }

   private boolean skipFunction(FunctionNode functionNode) {
      ScriptEnvironment env = this.compiler.getScriptEnvironment();
      boolean lazy = env._lazy_compilation;
      boolean onDemand = this.compiler.isOnDemandCompilation();
      if ((onDemand || lazy) && ((CodeGeneratorLexicalContext)this.lc).getOutermostFunction() != functionNode) {
         return true;
      } else {
         return !onDemand && lazy && env._optimistic_types && functionNode.isProgram();
      }
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      if (this.skipFunction(functionNode)) {
         this.newFunctionObject(functionNode, false);
         return false;
      } else {
         String fnName = functionNode.getName();
         if (!this.emittedMethods.contains(fnName)) {
            this.log.info("=== BEGIN ", fnName);

            assert functionNode.getCompileUnit() != null : "no compile unit for " + fnName + " " + Debug.id(functionNode);

            this.unit = ((CodeGeneratorLexicalContext)this.lc).pushCompileUnit(functionNode.getCompileUnit());

            assert ((CodeGeneratorLexicalContext)this.lc).hasCompileUnits();

            ClassEmitter classEmitter = this.unit.getClassEmitter();
            this.pushMethodEmitter(this.isRestOf() ? classEmitter.restOfMethod(functionNode) : classEmitter.method(functionNode));
            this.method.setPreventUndefinedLoad();
            if (this.useOptimisticTypes()) {
               ((CodeGeneratorLexicalContext)this.lc).pushUnwarrantedOptimismHandlers();
            }

            this.lastLineNumber = -1;
            this.method.begin();
            if (this.isRestOf()) {
               assert this.continuationInfo == null;

               this.continuationInfo = new CodeGenerator.ContinuationInfo();
               this.method.gotoLoopStart(this.continuationInfo.getHandlerLabel());
            }
         }

         return true;
      }
   }

   private void pushMethodEmitter(MethodEmitter newMethod) {
      this.method = ((CodeGeneratorLexicalContext)this.lc).pushMethodEmitter(newMethod);
      this.catchLabels.push(METHOD_BOUNDARY);
   }

   private void popMethodEmitter() {
      this.method = ((CodeGeneratorLexicalContext)this.lc).popMethodEmitter(this.method);

      assert this.catchLabels.peek() == METHOD_BOUNDARY;

      this.catchLabels.pop();
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      try {
         boolean markOptimistic;
         if (this.emittedMethods.add(functionNode.getName())) {
            markOptimistic = this.generateUnwarrantedOptimismExceptionHandlers(functionNode);
            this.generateContinuationHandler();
            this.method.end();
            this.unit = ((CodeGeneratorLexicalContext)this.lc).popCompileUnit(functionNode.getCompileUnit());
            this.popMethodEmitter();
            this.log.info("=== END ", functionNode.getName());
         } else {
            markOptimistic = false;
         }

         FunctionNode newFunctionNode = functionNode;
         if (markOptimistic) {
            newFunctionNode = functionNode.setFlag(this.lc, 2048);
         }

         this.newFunctionObject(newFunctionNode, true);
         return newFunctionNode;
      } catch (Throwable var4) {
         Context.printStackTrace(var4);
         VerifyError e = new VerifyError("Code generation bug in \"" + functionNode.getName() + "\": likely stack misaligned: " + var4 + " " + functionNode.getSource().getName());
         e.initCause(var4);
         throw e;
      }
   }

   public boolean enterIfNode(IfNode ifNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(ifNode);
         Expression test = ifNode.getTest();
         Block pass = ifNode.getPass();
         Block fail = ifNode.getFail();
         if (Expression.isAlwaysTrue(test)) {
            this.loadAndDiscard(test);
            pass.accept(this);
            return false;
         } else if (Expression.isAlwaysFalse(test)) {
            this.loadAndDiscard(test);
            if (fail != null) {
               fail.accept(this);
            }

            return false;
         } else {
            boolean hasFailConversion = LocalVariableConversion.hasLiveConversion(ifNode);
            Label failLabel = new Label("if_fail");
            Label afterLabel = fail == null && !hasFailConversion ? null : new Label("if_done");
            this.emitBranch(test, failLabel, false);
            pass.accept(this);
            if (this.method.isReachable() && afterLabel != null) {
               this.method._goto(afterLabel);
            }

            this.method.label(failLabel);
            if (fail != null) {
               fail.accept(this);
            } else if (hasFailConversion) {
               this.method.beforeJoinPoint(ifNode);
            }

            if (afterLabel != null && afterLabel.isReachable()) {
               this.method.label(afterLabel);
            }

            return false;
         }
      }
   }

   private void emitBranch(Expression test, Label label, boolean jumpWhenTrue) {
      (new BranchOptimizer(this, this.method)).execute(test, label, jumpWhenTrue);
   }

   private void enterStatement(Statement statement) {
      this.lineNumber(statement);
   }

   private void lineNumber(Statement statement) {
      this.lineNumber(statement.getLineNumber());
   }

   private void lineNumber(int lineNumber) {
      if (lineNumber != this.lastLineNumber && lineNumber != -1) {
         this.method.lineNumber(lineNumber);
         this.lastLineNumber = lineNumber;
      }

   }

   int getLastLineNumber() {
      return this.lastLineNumber;
   }

   private void loadArray(LiteralNode.ArrayLiteralNode arrayLiteralNode, ArrayType arrayType) {
      assert arrayType == Type.INT_ARRAY || arrayType == Type.NUMBER_ARRAY || arrayType == Type.OBJECT_ARRAY;

      final Expression[] nodes = (Expression[])arrayLiteralNode.getValue();
      Object presets = arrayLiteralNode.getPresets();
      final int[] postsets = arrayLiteralNode.getPostsets();
      List<Splittable.SplitRange> ranges = arrayLiteralNode.getSplitRanges();
      this.loadConstant(presets);
      final Type elementType = arrayType.getElementType();
      if (ranges != null) {
         this.loadSplitLiteral(new CodeGenerator.SplitLiteralCreator() {
            public void populateRange(MethodEmitter method, Type type, int slot, int start, int end) {
               for(int i = start; i < end; ++i) {
                  method.load(type, slot);
                  CodeGenerator.this.storeElement(nodes, elementType, postsets[i]);
               }

               method.load(type, slot);
            }
         }, ranges, arrayType);
      } else {
         if (postsets.length > 0) {
            int arraySlot = this.method.getUsedSlotsWithLiveTemporaries();
            this.method.storeTemp(arrayType, arraySlot);
            int[] var9 = postsets;
            int var10 = postsets.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               int postset = var9[var11];
               this.method.load(arrayType, arraySlot);
               this.storeElement(nodes, elementType, postset);
            }

            this.method.load(arrayType, arraySlot);
         }

      }
   }

   private void storeElement(Expression[] nodes, Type elementType, int index) {
      this.method.load(index);
      Expression element = nodes[index];
      if (element == null) {
         this.method.loadEmpty(elementType);
      } else {
         this.loadExpressionAsType(element, elementType);
      }

      this.method.arraystore();
   }

   private MethodEmitter loadArgsArray(List<Expression> args) {
      Object[] array = new Object[args.size()];
      this.loadConstant((Object)array);

      for(int i = 0; i < args.size(); ++i) {
         this.method.dup();
         this.method.load(i);
         this.loadExpression((Expression)args.get(i), CodeGenerator.TypeBounds.OBJECT);
         this.method.arraystore();
      }

      return this.method;
   }

   void loadConstant(String string) {
      String unitClassName = this.unit.getUnitClassName();
      ClassEmitter classEmitter = this.unit.getClassEmitter();
      int index = this.compiler.getConstantData().add(string);
      this.method.load(index);
      this.method.invokestatic(unitClassName, CompilerConstants.GET_STRING.symbolName(), CompilerConstants.methodDescriptor(String.class, Integer.TYPE));
      classEmitter.needGetConstantMethod(String.class);
   }

   void loadConstant(Object object) {
      this.loadConstant(object, this.unit, this.method);
   }

   private void loadConstant(Object object, CompileUnit compileUnit, MethodEmitter methodEmitter) {
      String unitClassName = compileUnit.getUnitClassName();
      ClassEmitter classEmitter = compileUnit.getClassEmitter();
      int index = this.compiler.getConstantData().add(object);
      Class<?> cls = object.getClass();
      if (cls == PropertyMap.class) {
         methodEmitter.load(index);
         methodEmitter.invokestatic(unitClassName, CompilerConstants.GET_MAP.symbolName(), CompilerConstants.methodDescriptor(PropertyMap.class, Integer.TYPE));
         classEmitter.needGetConstantMethod(PropertyMap.class);
      } else if (cls.isArray()) {
         methodEmitter.load(index);
         String methodName = ClassEmitter.getArrayMethodName(cls);
         methodEmitter.invokestatic(unitClassName, methodName, CompilerConstants.methodDescriptor(cls, Integer.TYPE));
         classEmitter.needGetConstantMethod(cls);
      } else {
         methodEmitter.loadConstants().load(index).arrayload();
         if (object instanceof ArrayData) {
            methodEmitter.checkcast(ArrayData.class);
            methodEmitter.invoke(CompilerConstants.virtualCallNoLookup(ArrayData.class, "copy", ArrayData.class));
         } else if (cls != Object.class) {
            methodEmitter.checkcast(cls);
         }
      }

   }

   private void loadConstantsAndIndex(Object object, MethodEmitter methodEmitter) {
      methodEmitter.loadConstants().load(this.compiler.getConstantData().add(object));
   }

   private void loadLiteral(LiteralNode<?> node, CodeGenerator.TypeBounds resultBounds) {
      Object value = node.getValue();
      if (value == null) {
         this.method.loadNull();
      } else if (value instanceof Undefined) {
         this.method.loadUndefined(resultBounds.within(Type.OBJECT));
      } else if (value instanceof String) {
         String string = (String)value;
         if (string.length() > 10922) {
            this.loadConstant(string);
         } else {
            this.method.load(string);
         }
      } else if (value instanceof Lexer.RegexToken) {
         this.loadRegex((Lexer.RegexToken)value);
      } else if (value instanceof Boolean) {
         this.method.load((Boolean)value);
      } else if (value instanceof Integer) {
         if (!resultBounds.canBeNarrowerThan(Type.OBJECT)) {
            this.method.load((Integer)value);
            this.method.convert(Type.OBJECT);
         } else if (!resultBounds.canBeNarrowerThan(Type.NUMBER)) {
            this.method.load(((Integer)value).doubleValue());
         } else {
            this.method.load((Integer)value);
         }
      } else if (value instanceof Double) {
         if (!resultBounds.canBeNarrowerThan(Type.OBJECT)) {
            this.method.load((Double)value);
            this.method.convert(Type.OBJECT);
         } else {
            this.method.load((Double)value);
         }
      } else {
         if (!(node instanceof LiteralNode.ArrayLiteralNode)) {
            throw new UnsupportedOperationException("Unknown literal for " + node.getClass() + " " + value.getClass() + " " + value);
         }

         LiteralNode.ArrayLiteralNode arrayLiteral = (LiteralNode.ArrayLiteralNode)node;
         ArrayType atype = arrayLiteral.getArrayType();
         this.loadArray(arrayLiteral, atype);
         this.globalAllocateArray(atype);
      }

   }

   private MethodEmitter loadRegexToken(Lexer.RegexToken value) {
      this.method.load(value.getExpression());
      this.method.load(value.getOptions());
      return this.globalNewRegExp();
   }

   private MethodEmitter loadRegex(Lexer.RegexToken regexToken) {
      if (this.regexFieldCount > 2048) {
         return this.loadRegexToken(regexToken);
      } else {
         String regexName = ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction().uniqueName(CompilerConstants.REGEX_PREFIX.symbolName());
         ClassEmitter classEmitter = this.unit.getClassEmitter();
         classEmitter.field(EnumSet.of(ClassEmitter.Flag.PRIVATE, ClassEmitter.Flag.STATIC), regexName, Object.class);
         ++this.regexFieldCount;
         this.method.getStatic(this.unit.getUnitClassName(), regexName, CompilerConstants.typeDescriptor(Object.class));
         this.method.dup();
         Label cachedLabel = new Label("cached");
         this.method.ifnonnull(cachedLabel);
         this.method.pop();
         this.loadRegexToken(regexToken);
         this.method.dup();
         this.method.putStatic(this.unit.getUnitClassName(), regexName, CompilerConstants.typeDescriptor(Object.class));
         this.method.label(cachedLabel);
         this.globalRegExpCopy();
         return this.method;
      }
   }

   private static boolean propertyValueContains(final Expression value, final int pp) {
      return (new Supplier<Boolean>() {
         boolean contains;

         public Boolean get() {
            value.accept(new SimpleNodeVisitor() {
               public boolean enterFunctionNode(FunctionNode functionNode) {
                  return false;
               }

               public boolean enterDefault(Node node) {
                  if (contains) {
                     return false;
                  } else if (node instanceof Optimistic && ((Optimistic)node).getProgramPoint() == pp) {
                     contains = true;
                     return false;
                  } else {
                     return true;
                  }
               }
            });
            return this.contains;
         }
      }).get();
   }

   private void loadObjectNode(ObjectNode objectNode) {
      List<PropertyNode> elements = objectNode.getElements();
      List<MapTuple<Expression>> tuples = new ArrayList();
      List<PropertyNode> gettersSetters = new ArrayList();
      int ccp = this.getCurrentContinuationEntryPoint();
      List<Splittable.SplitRange> ranges = objectNode.getSplitRanges();
      Expression protoNode = null;
      boolean restOfProperty = false;
      Iterator var9 = elements.iterator();

      while(true) {
         while(var9.hasNext()) {
            PropertyNode propertyNode = (PropertyNode)var9.next();
            Expression value = propertyNode.getValue();
            String key = propertyNode.getKeyName();
            Symbol symbol = value == null ? null : new Symbol(key, 0);
            if (value == null) {
               gettersSetters.add(propertyNode);
            } else if (propertyNode.getKey() instanceof IdentNode && key.equals("__proto__")) {
               protoNode = value;
               continue;
            }

            restOfProperty |= value != null && UnwarrantedOptimismException.isValid(ccp) && propertyValueContains(value, ccp);
            Class<?> valueType = this.useDualFields() && value != null && !value.getType().isBoolean() ? value.getType().getTypeClass() : Object.class;
            tuples.add(new MapTuple<Expression>(key, symbol, Type.typeFor(valueType), value) {
               public Class<?> getValueType() {
                  return this.type.getTypeClass();
               }
            });
         }

         Object oc;
         if (elements.size() > OBJECT_SPILL_THRESHOLD) {
            oc = new SpillObjectCreator(this, tuples);
         } else {
            oc = new FieldObjectCreator<Expression>(this, tuples) {
               protected void loadValue(Expression node, Type type) {
                  CodeGenerator.this.loadExpressionAsType(node, Type.generic(type));
               }
            };
         }

         if (ranges != null) {
            ((ObjectCreator)oc).createObject(this.method);
            this.loadSplitLiteral((CodeGenerator.SplitLiteralCreator)oc, ranges, Type.typeFor(((ObjectCreator)oc).getAllocatorClass()));
         } else {
            ((ObjectCreator)oc).makeObject(this.method);
         }

         if (restOfProperty) {
            CodeGenerator.ContinuationInfo ci = this.getContinuationInfo();
            ci.setObjectLiteralMap(this.method.getStackSize(), ((ObjectCreator)oc).getMap());
         }

         this.method.dup();
         if (protoNode != null) {
            this.loadExpressionAsObject(protoNode);
            this.method.convert(Type.OBJECT);
            this.method.invoke(ScriptObject.SET_PROTO_FROM_LITERAL);
         } else {
            this.method.invoke(ScriptObject.SET_GLOBAL_OBJECT_PROTO);
         }

         for(Iterator var17 = gettersSetters.iterator(); var17.hasNext(); this.method.invoke(ScriptObject.SET_USER_ACCESSORS)) {
            PropertyNode propertyNode = (PropertyNode)var17.next();
            FunctionNode getter = propertyNode.getGetter();
            FunctionNode setter = propertyNode.getSetter();

            assert getter != null || setter != null;

            this.method.dup().loadKey(propertyNode.getKey());
            if (getter == null) {
               this.method.loadNull();
            } else {
               getter.accept(this);
            }

            if (setter == null) {
               this.method.loadNull();
            } else {
               setter.accept(this);
            }
         }

         return;
      }
   }

   public boolean enterReturnNode(ReturnNode returnNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(returnNode);
         Type returnType = ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction().getReturnType();
         Expression expression = returnNode.getExpression();
         if (expression != null) {
            this.loadExpressionUnbounded(expression);
         } else {
            this.method.loadUndefined(returnType);
         }

         this.method._return(returnType);
         return false;
      }
   }

   private boolean undefinedCheck(RuntimeNode runtimeNode, List<Expression> args) {
      RuntimeNode.Request request = runtimeNode.getRequest();
      if (!RuntimeNode.Request.isUndefinedCheck(request)) {
         return false;
      } else {
         Expression lhs = (Expression)args.get(0);
         Expression rhs = (Expression)args.get(1);
         Symbol lhsSymbol = lhs instanceof IdentNode ? ((IdentNode)lhs).getSymbol() : null;
         Symbol rhsSymbol = rhs instanceof IdentNode ? ((IdentNode)rhs).getSymbol() : null;

         assert lhsSymbol != null || rhsSymbol != null;

         Symbol undefinedSymbol;
         if (isUndefinedSymbol(lhsSymbol)) {
            undefinedSymbol = lhsSymbol;
         } else {
            assert isUndefinedSymbol(rhsSymbol);

            undefinedSymbol = rhsSymbol;
         }

         assert undefinedSymbol != null;

         if (!undefinedSymbol.isScope()) {
            return false;
         } else if (lhsSymbol == undefinedSymbol && lhs.getType().isPrimitive()) {
            return false;
         } else if (this.isDeoptimizedExpression(lhs)) {
            return false;
         } else if (!this.compiler.isGlobalSymbol(((CodeGeneratorLexicalContext)this.lc).getCurrentFunction(), "undefined")) {
            return false;
         } else {
            boolean isUndefinedCheck = request == RuntimeNode.Request.IS_UNDEFINED;
            Expression expr = undefinedSymbol == lhsSymbol ? rhs : lhs;
            if (expr.getType().isPrimitive()) {
               this.loadAndDiscard(expr);
               this.method.load(!isUndefinedCheck);
            } else {
               Label checkTrue = new Label("ud_check_true");
               Label end = new Label("end");
               this.loadExpressionAsObject(expr);
               this.method.loadUndefined(Type.OBJECT);
               this.method.if_acmpeq(checkTrue);
               this.method.load(!isUndefinedCheck);
               this.method._goto(end);
               this.method.label(checkTrue);
               this.method.load(isUndefinedCheck);
               this.method.label(end);
            }

            return true;
         }
      }
   }

   private static boolean isUndefinedSymbol(Symbol symbol) {
      return symbol != null && "undefined".equals(symbol.getName());
   }

   private static boolean isNullLiteral(Node node) {
      return node instanceof LiteralNode && ((LiteralNode)node).isNull();
   }

   private boolean nullCheck(RuntimeNode runtimeNode, List<Expression> args) {
      RuntimeNode.Request request = runtimeNode.getRequest();
      if (!RuntimeNode.Request.isEQ(request) && !RuntimeNode.Request.isNE(request)) {
         return false;
      } else {
         assert args.size() == 2 : "EQ or NE or TYPEOF need two args";

         Expression lhs = (Expression)args.get(0);
         Expression rhs = (Expression)args.get(1);
         if (isNullLiteral(lhs)) {
            Expression tmp = lhs;
            lhs = rhs;
            rhs = tmp;
         }

         if (!isNullLiteral(rhs)) {
            return false;
         } else if (!lhs.getType().isObject()) {
            return false;
         } else if (this.isDeoptimizedExpression(lhs)) {
            return false;
         } else {
            Label trueLabel = new Label("trueLabel");
            Label falseLabel = new Label("falseLabel");
            Label endLabel = new Label("end");
            this.loadExpressionUnbounded(lhs);
            Label popLabel;
            if (!RuntimeNode.Request.isStrict(request)) {
               this.method.dup();
               popLabel = new Label("pop");
            } else {
               popLabel = null;
            }

            if (RuntimeNode.Request.isEQ(request)) {
               this.method.ifnull(!RuntimeNode.Request.isStrict(request) ? popLabel : trueLabel);
               if (!RuntimeNode.Request.isStrict(request)) {
                  this.method.loadUndefined(Type.OBJECT);
                  this.method.if_acmpeq(trueLabel);
               }

               this.method.label(falseLabel);
               this.method.load(false);
               this.method._goto(endLabel);
               if (!RuntimeNode.Request.isStrict(request)) {
                  this.method.label(popLabel);
                  this.method.pop();
               }

               this.method.label(trueLabel);
               this.method.load(true);
               this.method.label(endLabel);
            } else if (RuntimeNode.Request.isNE(request)) {
               this.method.ifnull(!RuntimeNode.Request.isStrict(request) ? popLabel : falseLabel);
               if (!RuntimeNode.Request.isStrict(request)) {
                  this.method.loadUndefined(Type.OBJECT);
                  this.method.if_acmpeq(falseLabel);
               }

               this.method.label(trueLabel);
               this.method.load(true);
               this.method._goto(endLabel);
               if (!RuntimeNode.Request.isStrict(request)) {
                  this.method.label(popLabel);
                  this.method.pop();
               }

               this.method.label(falseLabel);
               this.method.load(false);
               this.method.label(endLabel);
            }

            assert runtimeNode.getType().isBoolean();

            this.method.convert(runtimeNode.getType());
            return true;
         }
      }
   }

   private boolean isDeoptimizedExpression(final Expression rootExpr) {
      return !this.isRestOf() ? false : (new Supplier<Boolean>() {
         boolean contains;

         public Boolean get() {
            rootExpr.accept(new SimpleNodeVisitor() {
               public boolean enterFunctionNode(FunctionNode functionNode) {
                  return false;
               }

               public boolean enterDefault(Node node) {
                  if (!contains && node instanceof Optimistic) {
                     int pp = ((Optimistic)node).getProgramPoint();
                     contains = UnwarrantedOptimismException.isValid(pp) && CodeGenerator.this.isContinuationEntryPoint(pp);
                  }

                  return !contains;
               }
            });
            return this.contains;
         }
      }).get();
   }

   private void loadRuntimeNode(RuntimeNode runtimeNode) {
      List<Expression> args = new ArrayList(runtimeNode.getArgs());
      if (!this.nullCheck(runtimeNode, args)) {
         if (!this.undefinedCheck(runtimeNode, args)) {
            RuntimeNode.Request request = runtimeNode.getRequest();
            RuntimeNode newRuntimeNode;
            if (RuntimeNode.Request.isUndefinedCheck(request)) {
               newRuntimeNode = runtimeNode.setRequest(request == RuntimeNode.Request.IS_UNDEFINED ? RuntimeNode.Request.EQ_STRICT : RuntimeNode.Request.NE_STRICT);
            } else {
               newRuntimeNode = runtimeNode;
            }

            Iterator var5 = args.iterator();

            while(var5.hasNext()) {
               Expression arg = (Expression)var5.next();
               this.loadExpression(arg, CodeGenerator.TypeBounds.OBJECT);
            }

            this.method.invokestatic(CompilerConstants.className(ScriptRuntime.class), newRuntimeNode.getRequest().toString(), (new FunctionSignature(false, false, newRuntimeNode.getType(), args.size())).toString());
            this.method.convert(newRuntimeNode.getType());
         }
      }
   }

   private void defineCommonSplitMethodParameters() {
      this.defineSplitMethodParameter(0, (CompilerConstants)CompilerConstants.CALLEE);
      this.defineSplitMethodParameter(1, (CompilerConstants)CompilerConstants.THIS);
      this.defineSplitMethodParameter(2, (CompilerConstants)CompilerConstants.SCOPE);
   }

   private void defineSplitMethodParameter(int slot, CompilerConstants cc) {
      this.defineSplitMethodParameter(slot, Type.typeFor(cc.type()));
   }

   private void defineSplitMethodParameter(int slot, Type type) {
      this.method.defineBlockLocalVariable(slot, slot + type.getSlots());
      this.method.onLocalStore(type, slot);
   }

   private void loadSplitLiteral(CodeGenerator.SplitLiteralCreator creator, List<Splittable.SplitRange> ranges, Type literalType) {
      assert ranges != null;

      MethodEmitter savedMethod = this.method;
      FunctionNode currentFunction = ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction();

      for(Iterator var6 = ranges.iterator(); var6.hasNext(); this.unit = ((CodeGeneratorLexicalContext)this.lc).popCompileUnit(this.unit)) {
         Splittable.SplitRange splitRange = (Splittable.SplitRange)var6.next();
         this.unit = ((CodeGeneratorLexicalContext)this.lc).pushCompileUnit(splitRange.getCompileUnit());

         assert this.unit != null;

         String className = this.unit.getUnitClassName();
         String name = currentFunction.uniqueName(CompilerConstants.SPLIT_PREFIX.symbolName());
         Class<?> clazz = literalType.getTypeClass();
         String signature = CompilerConstants.methodDescriptor(clazz, ScriptFunction.class, Object.class, ScriptObject.class, clazz);
         this.pushMethodEmitter(this.unit.getClassEmitter().method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), name, signature));
         this.method.setFunctionNode(currentFunction);
         this.method.begin();
         this.defineCommonSplitMethodParameters();
         this.defineSplitMethodParameter(CompilerConstants.SPLIT_ARRAY_ARG.slot(), literalType);
         int literalSlot = this.fixScopeSlot(currentFunction, 3);
         ((CodeGeneratorLexicalContext)this.lc).enterSplitNode();
         creator.populateRange(this.method, literalType, literalSlot, splitRange.getLow(), splitRange.getHigh());
         this.method._return();
         ((CodeGeneratorLexicalContext)this.lc).exitSplitNode();
         this.method.end();
         ((CodeGeneratorLexicalContext)this.lc).releaseSlots();
         this.popMethodEmitter();

         assert this.method == savedMethod;

         this.method.loadCompilerConstant(CompilerConstants.CALLEE).swap();
         this.method.loadCompilerConstant(CompilerConstants.THIS).swap();
         this.method.loadCompilerConstant(CompilerConstants.SCOPE).swap();
         this.method.invokestatic(className, name, signature);
      }

   }

   private int fixScopeSlot(FunctionNode functionNode, int extraSlot) {
      int actualScopeSlot = functionNode.compilerConstant(CompilerConstants.SCOPE).getSlot(SCOPE_TYPE);
      int defaultScopeSlot = CompilerConstants.SCOPE.slot();
      int newExtraSlot = extraSlot;
      if (actualScopeSlot != defaultScopeSlot) {
         if (actualScopeSlot == extraSlot) {
            newExtraSlot = extraSlot + 1;
            this.method.defineBlockLocalVariable(newExtraSlot, newExtraSlot + 1);
            this.method.load(Type.OBJECT, extraSlot);
            this.method.storeHidden(Type.OBJECT, newExtraSlot);
         } else {
            this.method.defineBlockLocalVariable(actualScopeSlot, actualScopeSlot + 1);
         }

         this.method.load(SCOPE_TYPE, defaultScopeSlot);
         this.method.storeCompilerConstant(CompilerConstants.SCOPE);
      }

      return newExtraSlot;
   }

   public boolean enterSplitReturn(SplitReturn splitReturn) {
      if (this.method.isReachable()) {
         this.method.loadUndefined(((CodeGeneratorLexicalContext)this.lc).getCurrentFunction().getReturnType())._return();
      }

      return false;
   }

   public boolean enterSetSplitState(SetSplitState setSplitState) {
      if (this.method.isReachable()) {
         this.method.setSplitState(setSplitState.getState());
      }

      return false;
   }

   public boolean enterSwitchNode(SwitchNode switchNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(switchNode);
         Expression expression = switchNode.getExpression();
         List<CaseNode> cases = switchNode.getCases();
         if (cases.isEmpty()) {
            this.loadAndDiscard(expression);
            return false;
         } else {
            CaseNode defaultCase = switchNode.getDefaultCase();
            Label breakLabel = switchNode.getBreakLabel();
            int liveLocalsOnBreak = this.method.getUsedSlotsWithLiveTemporaries();
            if (defaultCase != null && cases.size() == 1) {
               assert cases.get(0) == defaultCase;

               this.loadAndDiscard(expression);
               defaultCase.getBody().accept(this);
               this.method.breakLabel(breakLabel, liveLocalsOnBreak);
               return false;
            } else {
               Label defaultLabel = defaultCase != null ? defaultCase.getEntry() : breakLabel;
               boolean hasSkipConversion = LocalVariableConversion.hasLiveConversion(switchNode);
               int size;
               if (switchNode.isUniqueInteger()) {
                  TreeMap<Integer, Label> tree = new TreeMap();
                  Iterator var10 = cases.iterator();

                  while(var10.hasNext()) {
                     CaseNode caseNode = (CaseNode)var10.next();
                     Node test = caseNode.getTest();
                     if (test != null) {
                        Integer value = (Integer)((LiteralNode)test).getValue();
                        Label entry = caseNode.getEntry();
                        if (!tree.containsKey(value)) {
                           tree.put(value, entry);
                        }
                     }
                  }

                  size = tree.size();
                  Integer[] values = (Integer[])tree.keySet().toArray(new Integer[size]);
                  Label[] labels = (Label[])tree.values().toArray(new Label[size]);
                  int lo = values[0];
                  int hi = values[size - 1];
                  long range = (long)hi - (long)lo + 1L;
                  int deflt = Integer.MIN_VALUE;
                  Integer[] var18 = values;
                  int var19 = values.length;

                  int i;
                  int value;
                  for(i = 0; i < var19; ++i) {
                     value = var18[i];
                     if (deflt == value) {
                        ++deflt;
                     } else if (deflt < value) {
                        break;
                     }
                  }

                  this.loadExpressionUnbounded(expression);
                  Type type = expression.getType();
                  if (!type.isInteger()) {
                     this.method.load(deflt);
                     Class<?> exprClass = type.getTypeClass();
                     this.method.invoke(CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "switchTagAsInt", Integer.TYPE, exprClass.isPrimitive() ? exprClass : Object.class, Integer.TYPE));
                  }

                  if (hasSkipConversion) {
                     assert defaultLabel == breakLabel;

                     defaultLabel = new Label("switch_skip");
                  }

                  if (range + 1L <= (long)(size * 2) && range <= 2147483647L) {
                     Label[] table = new Label[(int)range];
                     Arrays.fill(table, defaultLabel);

                     for(i = 0; i < size; ++i) {
                        value = values[i];
                        table[value - lo] = labels[i];
                     }

                     this.method.tableswitch(lo, hi, defaultLabel, table);
                  } else {
                     int[] ints = new int[size];

                     for(i = 0; i < size; ++i) {
                        ints[i] = values[i];
                     }

                     this.method.lookupswitch(defaultLabel, ints, labels);
                  }

                  if (hasSkipConversion) {
                     this.method.label(defaultLabel);
                     this.method.beforeJoinPoint(switchNode);
                     this.method._goto(breakLabel);
                  }
               } else {
                  Symbol tagSymbol = switchNode.getTag();
                  size = tagSymbol.getSlot(Type.OBJECT);
                  this.loadExpressionAsObject(expression);
                  this.method.store(tagSymbol, Type.OBJECT);
                  Iterator var27 = cases.iterator();

                  while(var27.hasNext()) {
                     CaseNode caseNode = (CaseNode)var27.next();
                     Expression test = caseNode.getTest();
                     if (test != null) {
                        this.method.load(Type.OBJECT, size);
                        this.loadExpressionAsObject(test);
                        this.method.invoke(ScriptRuntime.EQ_STRICT);
                        this.method.ifne(caseNode.getEntry());
                     }
                  }

                  if (defaultCase != null) {
                     this.method._goto(defaultLabel);
                  } else {
                     this.method.beforeJoinPoint(switchNode);
                     this.method._goto(breakLabel);
                  }
               }

               assert !this.method.isReachable();

               CaseNode caseNode;
               for(Iterator var23 = cases.iterator(); var23.hasNext(); caseNode.getBody().accept(this)) {
                  caseNode = (CaseNode)var23.next();
                  Label fallThroughLabel;
                  if (caseNode.getLocalVariableConversion() != null && this.method.isReachable()) {
                     fallThroughLabel = new Label("fallthrough");
                     this.method._goto(fallThroughLabel);
                  } else {
                     fallThroughLabel = null;
                  }

                  this.method.label(caseNode.getEntry());
                  this.method.beforeJoinPoint(caseNode);
                  if (fallThroughLabel != null) {
                     this.method.label(fallThroughLabel);
                  }
               }

               this.method.breakLabel(breakLabel, liveLocalsOnBreak);
               return false;
            }
         }
      }
   }

   public boolean enterThrowNode(ThrowNode throwNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(throwNode);
         if (throwNode.isSyntheticRethrow()) {
            this.method.beforeJoinPoint(throwNode);
            IdentNode exceptionExpr = (IdentNode)throwNode.getExpression();
            Symbol exceptionSymbol = exceptionExpr.getSymbol();
            this.method.load(exceptionSymbol, EXCEPTION_TYPE);
            this.method.checkcast(EXCEPTION_TYPE.getTypeClass());
            this.method.athrow();
            return false;
         } else {
            Source source = this.getCurrentSource();
            Expression expression = throwNode.getExpression();
            int position = throwNode.position();
            int line = throwNode.getLineNumber();
            int column = source.getColumn(position);
            this.loadExpressionAsObject(expression);
            this.method.load(source.getName());
            this.method.load(line);
            this.method.load(column);
            this.method.invoke(ECMAException.CREATE);
            this.method.beforeJoinPoint(throwNode);
            this.method.athrow();
            return false;
         }
      }
   }

   private Source getCurrentSource() {
      return ((CodeGeneratorLexicalContext)this.lc).getCurrentFunction().getSource();
   }

   public boolean enterTryNode(TryNode tryNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(tryNode);
         Block body = tryNode.getBody();
         List<Block> catchBlocks = tryNode.getCatchBlocks();
         final Symbol vmException = tryNode.getException();
         Label entry = new Label("try");
         Label recovery = new Label("catch");
         Label exit = new Label("end_try");
         Label skip = new Label("skip");
         this.method.canThrow(recovery);
         this.method.beforeTry(tryNode, recovery);
         this.method.label(entry);
         this.catchLabels.push(recovery);

         try {
            body.accept(this);
         } finally {
            assert this.catchLabels.peek() == recovery;

            this.catchLabels.pop();
         }

         this.method.label(exit);
         boolean var9 = exit.isAfter(entry);
         if (!var9) {
            return false;
         } else {
            this.method._try(entry, exit, recovery, Throwable.class);
            if (this.method.isReachable()) {
               this.method._goto(skip);
            }

            Iterator var10 = tryNode.getInlinedFinallies().iterator();

            do {
               if (!var10.hasNext()) {
                  this.method._catch(recovery);
                  this.method.store(vmException, EXCEPTION_TYPE);
                  int catchBlockCount = catchBlocks.size();
                  Label afterCatch = new Label("after_catch");

                  for(int i = 0; i < catchBlockCount; ++i) {
                     assert this.method.isReachable();

                     Block catchBlock = (Block)catchBlocks.get(i);
                     ((CodeGeneratorLexicalContext)this.lc).push(catchBlock);
                     this.enterBlock(catchBlock);
                     final CatchNode catchNode = (CatchNode)((Block)catchBlocks.get(i)).getStatements().get(0);
                     IdentNode exception = catchNode.getException();
                     Expression exceptionCondition = catchNode.getExceptionCondition();
                     Block catchBody = catchNode.getBody();
                     (new CodeGenerator.Store<IdentNode>(exception) {
                        protected void storeNonDiscard() {
                        }

                        protected void evaluate() {
                           if (catchNode.isSyntheticRethrow()) {
                              CodeGenerator.this.method.load(vmException, CodeGenerator.EXCEPTION_TYPE);
                           } else {
                              Label notEcmaException = new Label("no_ecma_exception");
                              CodeGenerator.this.method.load(vmException, CodeGenerator.EXCEPTION_TYPE).dup()._instanceof(ECMAException.class).ifeq(notEcmaException);
                              CodeGenerator.this.method.checkcast(ECMAException.class);
                              CodeGenerator.this.method.getField(ECMAException.THROWN);
                              CodeGenerator.this.method.label(notEcmaException);
                           }
                        }
                     }).store();
                     boolean isConditionalCatch = exceptionCondition != null;
                     Label nextCatch;
                     if (isConditionalCatch) {
                        this.loadExpressionAsBoolean(exceptionCondition);
                        nextCatch = new Label("next_catch");
                        nextCatch.markAsBreakTarget();
                        this.method.ifeq(nextCatch);
                     } else {
                        nextCatch = null;
                     }

                     catchBody.accept(this);
                     this.leaveBlock(catchBlock);
                     ((CodeGeneratorLexicalContext)this.lc).pop(catchBlock);
                     if (nextCatch != null) {
                        if (this.method.isReachable()) {
                           this.method._goto(afterCatch);
                        }

                        this.method.breakLabel(nextCatch, ((CodeGeneratorLexicalContext)this.lc).getUsedSlotCount());
                     }
                  }

                  this.method.label(afterCatch);
                  if (this.method.isReachable()) {
                     this.method.markDeadLocalVariable(vmException);
                  }

                  this.method.label(skip);

                  assert tryNode.getFinallyBody() == null;

                  return false;
               }

               Block inlinedFinally = (Block)var10.next();
               TryNode.getLabelledInlinedFinallyBlock(inlinedFinally).accept(this);
            } while($assertionsDisabled || !this.method.isReachable());

            throw new AssertionError();
         }
      }
   }

   public boolean enterVarNode(VarNode varNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         Expression init = varNode.getInit();
         IdentNode identNode = varNode.getName();
         Symbol identSymbol = identNode.getSymbol();

         assert identSymbol != null : "variable node " + varNode + " requires a name with a symbol";

         boolean needsScope = identSymbol.isScope();
         int flags;
         if (init == null) {
            if (needsScope && varNode.isBlockScoped()) {
               this.method.loadCompilerConstant(CompilerConstants.SCOPE);
               this.method.loadUndefined(Type.OBJECT);
               flags = this.getScopeCallSiteFlags(identSymbol) | (varNode.isBlockScoped() ? 32 : 0);

               assert this.isFastScope(identSymbol);

               this.storeFastScopeVar(identSymbol, flags);
            }

            return false;
         } else {
            this.enterStatement(varNode);

            assert this.method != null;

            if (needsScope) {
               this.method.loadCompilerConstant(CompilerConstants.SCOPE);
            }

            if (needsScope) {
               this.loadExpressionUnbounded(init);
               flags = this.getScopeCallSiteFlags(identSymbol) | (varNode.isBlockScoped() ? 32 : 0);
               if (this.isFastScope(identSymbol)) {
                  this.storeFastScopeVar(identSymbol, flags);
               } else {
                  this.method.dynamicSet(identNode.getName(), flags, false);
               }
            } else {
               Type identType = identNode.getType();
               if (identType == Type.UNDEFINED) {
                  assert init.getType() == Type.UNDEFINED || identNode.getSymbol().slotCount() == 0;

                  this.loadAndDiscard(init);
                  return false;
               }

               this.loadExpressionAsType(init, identType);
               this.storeIdentWithCatchConversion(identNode, identType);
            }

            return false;
         }
      }
   }

   private void storeIdentWithCatchConversion(IdentNode identNode, Type type) {
      LocalVariableConversion conversion = identNode.getLocalVariableConversion();
      Symbol symbol = identNode.getSymbol();
      if (conversion != null && conversion.isLive()) {
         assert symbol == conversion.getSymbol();

         assert symbol.isBytecodeLocal();

         assert conversion.getNext() == null;

         assert conversion.getFrom() == type;

         Label catchLabel = (Label)this.catchLabels.peek();

         assert catchLabel != METHOD_BOUNDARY;

         assert catchLabel.isReachable();

         Type joinType = conversion.getTo();
         Label.Stack catchStack = catchLabel.getStack();
         int joinSlot = symbol.getSlot(joinType);
         if (catchStack.getUsedSlotsWithLiveTemporaries() > joinSlot) {
            this.method.dup();
            this.method.convert(joinType);
            this.method.store(symbol, joinType);
            catchLabel.getStack().onLocalStore(joinType, joinSlot, true);
            this.method.canThrow(catchLabel);
            this.method.store(symbol, type, false);
            return;
         }
      }

      this.method.store(symbol, type, true);
   }

   public boolean enterWhileNode(WhileNode whileNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         if (whileNode.isDoWhile()) {
            this.enterDoWhile(whileNode);
         } else {
            this.enterStatement(whileNode);
            this.enterForOrWhile(whileNode, (JoinPredecessorExpression)null);
         }

         return false;
      }
   }

   private void enterForOrWhile(LoopNode loopNode, JoinPredecessorExpression modify) {
      int liveLocalsOnBreak = this.method.getUsedSlotsWithLiveTemporaries();
      JoinPredecessorExpression test = loopNode.getTest();
      if (Expression.isAlwaysFalse(test)) {
         this.loadAndDiscard(test);
      } else {
         this.method.beforeJoinPoint(loopNode);
         Label continueLabel = loopNode.getContinueLabel();
         Label repeatLabel = modify != null ? new Label("for_repeat") : continueLabel;
         this.method.label(repeatLabel);
         int liveLocalsOnContinue = this.method.getUsedSlotsWithLiveTemporaries();
         Block body = loopNode.getBody();
         Label breakLabel = loopNode.getBreakLabel();
         boolean testHasLiveConversion = test != null && LocalVariableConversion.hasLiveConversion(test);
         if (Expression.isAlwaysTrue(test)) {
            if (test != null) {
               this.loadAndDiscard(test);
               if (testHasLiveConversion) {
                  this.method.beforeJoinPoint(test);
               }
            }
         } else if (test != null) {
            if (testHasLiveConversion) {
               this.emitBranch(test.getExpression(), body.getEntryLabel(), true);
               this.method.beforeJoinPoint(test);
               this.method._goto(breakLabel);
            } else {
               this.emitBranch(test.getExpression(), breakLabel, false);
            }
         }

         body.accept(this);
         if (repeatLabel != continueLabel) {
            this.emitContinueLabel(continueLabel, liveLocalsOnContinue);
         }

         if (loopNode.hasPerIterationScope() && ((CodeGeneratorLexicalContext)this.lc).getCurrentBlock().needsScope()) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);
            this.method.invoke(CompilerConstants.virtualCallNoLookup(ScriptObject.class, "copy", ScriptObject.class));
            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
         }

         if (this.method.isReachable()) {
            if (modify != null) {
               this.lineNumber(loopNode);
               this.loadAndDiscard(modify);
               this.method.beforeJoinPoint(modify);
            }

            this.method._goto(repeatLabel);
         }

         this.method.breakLabel(breakLabel, liveLocalsOnBreak);
      }
   }

   private void emitContinueLabel(Label continueLabel, int liveLocals) {
      boolean reachable = this.method.isReachable();
      this.method.breakLabel(continueLabel, liveLocals);
      if (!reachable) {
         this.method.undefineLocalVariables(((CodeGeneratorLexicalContext)this.lc).getUsedSlotCount(), false);
      }

   }

   private void enterDoWhile(WhileNode whileNode) {
      int liveLocalsOnContinueOrBreak = this.method.getUsedSlotsWithLiveTemporaries();
      this.method.beforeJoinPoint(whileNode);
      Block body = whileNode.getBody();
      body.accept(this);
      this.emitContinueLabel(whileNode.getContinueLabel(), liveLocalsOnContinueOrBreak);
      if (this.method.isReachable()) {
         this.lineNumber(whileNode);
         JoinPredecessorExpression test = whileNode.getTest();
         Label bodyEntryLabel = body.getEntryLabel();
         boolean testHasLiveConversion = LocalVariableConversion.hasLiveConversion(test);
         if (Expression.isAlwaysFalse(test)) {
            this.loadAndDiscard(test);
            if (testHasLiveConversion) {
               this.method.beforeJoinPoint(test);
            }
         } else if (testHasLiveConversion) {
            Label beforeExit = new Label("do_while_preexit");
            this.emitBranch(test.getExpression(), beforeExit, false);
            this.method.beforeJoinPoint(test);
            this.method._goto(bodyEntryLabel);
            this.method.label(beforeExit);
            this.method.beforeJoinPoint(test);
         } else {
            this.emitBranch(test.getExpression(), bodyEntryLabel, true);
         }
      }

      this.method.breakLabel(whileNode.getBreakLabel(), liveLocalsOnContinueOrBreak);
   }

   public boolean enterWithNode(WithNode withNode) {
      if (!this.method.isReachable()) {
         return false;
      } else {
         this.enterStatement(withNode);
         Expression expression = withNode.getExpression();
         Block body = withNode.getBody();
         boolean hasScope = this.method.hasScope();
         if (hasScope) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);
         }

         this.loadExpressionAsObject(expression);
         Label tryLabel;
         if (hasScope) {
            this.method.invoke(ScriptRuntime.OPEN_WITH);
            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
            tryLabel = new Label("with_try");
            this.method.label(tryLabel);
         } else {
            this.globalCheckObjectCoercible();
            tryLabel = null;
         }

         body.accept(this);
         if (hasScope) {
            Label endLabel = new Label("with_end");
            Label catchLabel = new Label("with_catch");
            Label exitLabel = new Label("with_exit");
            this.method.label(endLabel);
            boolean bodyCanThrow = endLabel.isAfter(tryLabel);
            if (bodyCanThrow) {
               this.method._try(tryLabel, endLabel, catchLabel);
            }

            boolean reachable = this.method.isReachable();
            if (reachable) {
               this.popScope();
               if (bodyCanThrow) {
                  this.method._goto(exitLabel);
               }
            }

            if (bodyCanThrow) {
               this.method._catch(catchLabel);
               this.popScopeException();
               this.method.athrow();
               if (reachable) {
                  this.method.label(exitLabel);
               }
            }
         }

         return false;
      }
   }

   private void loadADD(UnaryNode unaryNode, CodeGenerator.TypeBounds resultBounds) {
      this.loadExpression(unaryNode.getExpression(), resultBounds.booleanToInt().notWiderThan(Type.NUMBER));
      if (this.method.peekType() == Type.BOOLEAN) {
         this.method.convert(Type.INT);
      }

   }

   private void loadBIT_NOT(UnaryNode unaryNode) {
      this.loadExpression(unaryNode.getExpression(), CodeGenerator.TypeBounds.INT).load(-1).xor();
   }

   private void loadDECINC(final UnaryNode unaryNode) {
      final Expression operand = unaryNode.getExpression();
      final Type type = unaryNode.getType();
      final CodeGenerator.TypeBounds typeBounds = new CodeGenerator.TypeBounds(type, Type.NUMBER);
      TokenType tokenType = unaryNode.tokenType();
      final boolean isPostfix = tokenType == TokenType.DECPOSTFIX || tokenType == TokenType.INCPOSTFIX;
      final boolean isIncrement = tokenType == TokenType.INCPREFIX || tokenType == TokenType.INCPOSTFIX;

      assert !type.isObject();

      (new CodeGenerator.SelfModifyingStore<UnaryNode>(unaryNode, operand) {
         private void loadRhs() {
            CodeGenerator.this.loadExpression(operand, typeBounds, true);
         }

         protected void evaluate() {
            if (isPostfix) {
               this.loadRhs();
            } else {
               (new CodeGenerator.OptimisticOperation(unaryNode, typeBounds) {
                  void loadStack() {
                     loadRhs();
                     loadMinusOne();
                  }

                  void consumeStack() {
                     doDecInc(this.getProgramPoint());
                  }
               }).emit(CodeGenerator.getOptimisticIgnoreCountForSelfModifyingExpression(operand));
            }

         }

         protected void storeNonDiscard() {
            super.storeNonDiscard();
            if (isPostfix) {
               (new CodeGenerator.OptimisticOperation(unaryNode, typeBounds) {
                  void loadStack() {
                     loadMinusOne();
                  }

                  void consumeStack() {
                     doDecInc(this.getProgramPoint());
                  }
               }).emit(1);
            }

         }

         private void loadMinusOne() {
            if (type.isInteger()) {
               CodeGenerator.this.method.load(isIncrement ? 1 : -1);
            } else {
               CodeGenerator.this.method.load(isIncrement ? 1.0D : -1.0D);
            }

         }

         private void doDecInc(int programPoint) {
            CodeGenerator.this.method.add(programPoint);
         }
      }).store();
   }

   private static int getOptimisticIgnoreCountForSelfModifyingExpression(Expression target) {
      return target instanceof AccessNode ? 1 : (target instanceof IndexNode ? 2 : 0);
   }

   private void loadAndDiscard(Expression expr) {
      if (expr instanceof LiteralNode.PrimitiveLiteralNode | isLocalVariable(expr)) {
         assert !((CodeGeneratorLexicalContext)this.lc).isCurrentDiscard(expr);

      } else {
         ((CodeGeneratorLexicalContext)this.lc).pushDiscard(expr);
         this.loadExpression(expr, CodeGenerator.TypeBounds.UNBOUNDED);
         if (((CodeGeneratorLexicalContext)this.lc).popDiscardIfCurrent(expr)) {
            assert !expr.isAssignment();

            this.method.pop();
         }

      }
   }

   private void loadMaybeDiscard(Expression parent, Expression expr, CodeGenerator.TypeBounds resultBounds) {
      this.loadMaybeDiscard(((CodeGeneratorLexicalContext)this.lc).popDiscardIfCurrent(parent), expr, resultBounds);
   }

   private void loadMaybeDiscard(boolean discard, Expression expr, CodeGenerator.TypeBounds resultBounds) {
      if (discard) {
         this.loadAndDiscard(expr);
      } else {
         this.loadExpression(expr, resultBounds);
      }

   }

   private void loadNEW(UnaryNode unaryNode) {
      CallNode callNode = (CallNode)unaryNode.getExpression();
      List<Expression> args = callNode.getArgs();
      Expression func = callNode.getFunction();
      this.loadExpressionAsObject(func);
      this.method.dynamicNew(1 + this.loadArgs(args), this.getCallSiteFlags(), func.toString(false));
   }

   private void loadNOT(UnaryNode unaryNode) {
      Expression expr = unaryNode.getExpression();
      if (expr instanceof UnaryNode && expr.isTokenType(TokenType.NOT)) {
         this.loadExpressionAsBoolean(((UnaryNode)expr).getExpression());
      } else {
         Label trueLabel = new Label("true");
         Label afterLabel = new Label("after");
         this.emitBranch(expr, trueLabel, true);
         this.method.load(true);
         this.method._goto(afterLabel);
         this.method.label(trueLabel);
         this.method.load(false);
         this.method.label(afterLabel);
      }

   }

   private void loadSUB(final UnaryNode unaryNode, CodeGenerator.TypeBounds resultBounds) {
      final Type type = unaryNode.getType();

      assert type.isNumeric();

      final CodeGenerator.TypeBounds numericBounds = resultBounds.booleanToInt();
      (new CodeGenerator.OptimisticOperation(unaryNode, numericBounds) {
         void loadStack() {
            Expression expr = unaryNode.getExpression();
            CodeGenerator.this.loadExpression(expr, numericBounds.notWiderThan(Type.NUMBER));
         }

         void consumeStack() {
            if (type.isNumber()) {
               CodeGenerator.this.method.convert(type);
            }

            CodeGenerator.this.method.neg(this.getProgramPoint());
         }
      }).emit();
   }

   public void loadVOID(UnaryNode unaryNode, CodeGenerator.TypeBounds resultBounds) {
      this.loadAndDiscard(unaryNode.getExpression());
      if (!((CodeGeneratorLexicalContext)this.lc).popDiscardIfCurrent(unaryNode)) {
         this.method.loadUndefined(resultBounds.widest);
      }

   }

   public void loadADD(final BinaryNode binaryNode, final CodeGenerator.TypeBounds resultBounds) {
      (new CodeGenerator.OptimisticOperation(binaryNode, resultBounds) {
         void loadStack() {
            boolean isOptimistic = UnwarrantedOptimismException.isValid(this.getProgramPoint());
            boolean forceConversionSeparation = false;
            CodeGenerator.TypeBounds operandBounds;
            if (isOptimistic) {
               operandBounds = new CodeGenerator.TypeBounds(binaryNode.getType(), Type.OBJECT);
            } else {
               Type widestOperationType = binaryNode.getWidestOperationType();
               operandBounds = new CodeGenerator.TypeBounds(Type.narrowest(binaryNode.getWidestOperandType(), resultBounds.widest), widestOperationType);
               forceConversionSeparation = widestOperationType.narrowerThan(resultBounds.widest);
            }

            CodeGenerator.this.loadBinaryOperands(binaryNode.lhs(), binaryNode.rhs(), operandBounds, false, forceConversionSeparation);
         }

         void consumeStack() {
            CodeGenerator.this.method.add(this.getProgramPoint());
         }
      }).emit();
   }

   private void loadAND_OR(BinaryNode binaryNode, CodeGenerator.TypeBounds resultBounds, boolean isAnd) {
      Type narrowestOperandType = Type.widestReturnType(binaryNode.lhs().getType(), binaryNode.rhs().getType());
      boolean isCurrentDiscard = ((CodeGeneratorLexicalContext)this.lc).popDiscardIfCurrent(binaryNode);
      Label skip = new Label("skip");
      if (narrowestOperandType == Type.BOOLEAN) {
         Label onTrue = new Label("andor_true");
         this.emitBranch(binaryNode, onTrue, true);
         if (isCurrentDiscard) {
            this.method.label(onTrue);
         } else {
            this.method.load(false);
            this.method._goto(skip);
            this.method.label(onTrue);
            this.method.load(true);
            this.method.label(skip);
         }

      } else {
         CodeGenerator.TypeBounds outBounds = resultBounds.notNarrowerThan(narrowestOperandType);
         JoinPredecessorExpression lhs = (JoinPredecessorExpression)binaryNode.lhs();
         boolean lhsConvert = LocalVariableConversion.hasLiveConversion(lhs);
         Label evalRhs = lhsConvert ? new Label("eval_rhs") : null;
         this.loadExpression(lhs, outBounds);
         if (!isCurrentDiscard) {
            this.method.dup();
         }

         this.method.convert(Type.BOOLEAN);
         if (isAnd) {
            if (lhsConvert) {
               this.method.ifne(evalRhs);
            } else {
               this.method.ifeq(skip);
            }
         } else if (lhsConvert) {
            this.method.ifeq(evalRhs);
         } else {
            this.method.ifne(skip);
         }

         if (lhsConvert) {
            this.method.beforeJoinPoint(lhs);
            this.method._goto(skip);
            this.method.label(evalRhs);
         }

         if (!isCurrentDiscard) {
            this.method.pop();
         }

         JoinPredecessorExpression rhs = (JoinPredecessorExpression)binaryNode.rhs();
         this.loadMaybeDiscard(isCurrentDiscard, rhs, outBounds);
         this.method.beforeJoinPoint(rhs);
         this.method.label(skip);
      }
   }

   private static boolean isLocalVariable(Expression lhs) {
      return lhs instanceof IdentNode && isLocalVariable((IdentNode)lhs);
   }

   private static boolean isLocalVariable(IdentNode lhs) {
      return lhs.getSymbol().isBytecodeLocal();
   }

   private void loadASSIGN(BinaryNode binaryNode) {
      Expression lhs = binaryNode.lhs();
      final Expression rhs = binaryNode.rhs();
      final Type rhsType = rhs.getType();
      if (lhs instanceof IdentNode) {
         Symbol symbol = ((IdentNode)lhs).getSymbol();
         if (!symbol.isScope() && !symbol.hasSlotFor(rhsType) && ((CodeGeneratorLexicalContext)this.lc).popDiscardIfCurrent(binaryNode)) {
            this.loadAndDiscard(rhs);
            this.method.markDeadLocalVariable(symbol);
            return;
         }
      }

      (new CodeGenerator.Store<BinaryNode>(binaryNode, lhs) {
         protected void evaluate() {
            CodeGenerator.this.loadExpressionAsType(rhs, rhsType);
         }
      }).store();
   }

   private void loadASSIGN_ADD(final BinaryNode binaryNode) {
      (new CodeGenerator.BinaryOptimisticSelfAssignment(binaryNode) {
         protected void op(CodeGenerator.OptimisticOperation oo) {
            assert !binaryNode.getType().isObject() || !oo.isOptimistic;

            CodeGenerator.this.method.add(oo.getProgramPoint());
         }
      }).store();
   }

   private void loadASSIGN_BIT_AND(BinaryNode binaryNode) {
      (new CodeGenerator.BinarySelfAssignment(binaryNode) {
         protected void op() {
            CodeGenerator.this.method.and();
         }
      }).store();
   }

   private void loadASSIGN_BIT_OR(BinaryNode binaryNode) {
      (new CodeGenerator.BinarySelfAssignment(binaryNode) {
         protected void op() {
            CodeGenerator.this.method.or();
         }
      }).store();
   }

   private void loadASSIGN_BIT_XOR(BinaryNode binaryNode) {
      (new CodeGenerator.BinarySelfAssignment(binaryNode) {
         protected void op() {
            CodeGenerator.this.method.xor();
         }
      }).store();
   }

   private void loadASSIGN_DIV(BinaryNode binaryNode) {
      (new CodeGenerator.BinaryOptimisticSelfAssignment(binaryNode) {
         protected void op(CodeGenerator.OptimisticOperation oo) {
            CodeGenerator.this.method.div(oo.getProgramPoint());
         }
      }).store();
   }

   private void loadASSIGN_MOD(BinaryNode binaryNode) {
      (new CodeGenerator.BinaryOptimisticSelfAssignment(binaryNode) {
         protected void op(CodeGenerator.OptimisticOperation oo) {
            CodeGenerator.this.method.rem(oo.getProgramPoint());
         }
      }).store();
   }

   private void loadASSIGN_MUL(BinaryNode binaryNode) {
      (new CodeGenerator.BinaryOptimisticSelfAssignment(binaryNode) {
         protected void op(CodeGenerator.OptimisticOperation oo) {
            CodeGenerator.this.method.mul(oo.getProgramPoint());
         }
      }).store();
   }

   private void loadASSIGN_SAR(BinaryNode binaryNode) {
      (new CodeGenerator.BinarySelfAssignment(binaryNode) {
         protected void op() {
            CodeGenerator.this.method.sar();
         }
      }).store();
   }

   private void loadASSIGN_SHL(BinaryNode binaryNode) {
      (new CodeGenerator.BinarySelfAssignment(binaryNode) {
         protected void op() {
            CodeGenerator.this.method.shl();
         }
      }).store();
   }

   private void loadASSIGN_SHR(final BinaryNode binaryNode) {
      (new CodeGenerator.SelfModifyingStore<BinaryNode>(binaryNode, binaryNode.lhs()) {
         protected void evaluate() {
            (new CodeGenerator.OptimisticOperation((Optimistic)this.assignNode, new CodeGenerator.TypeBounds(Type.INT, Type.NUMBER)) {
               void loadStack() {
                  assert ((BinaryNode)assignNode).getWidestOperandType() == Type.INT;

                  if (CodeGenerator.isRhsZero(binaryNode)) {
                     CodeGenerator.this.loadExpression(binaryNode.lhs(), CodeGenerator.TypeBounds.INT, true);
                  } else {
                     CodeGenerator.this.loadBinaryOperands(binaryNode.lhs(), binaryNode.rhs(), CodeGenerator.TypeBounds.INT, true, false);
                     CodeGenerator.this.method.shr();
                  }

               }

               void consumeStack() {
                  if (CodeGenerator.isOptimistic(binaryNode)) {
                     CodeGenerator.this.toUint32Optimistic(binaryNode.getProgramPoint());
                  } else {
                     CodeGenerator.this.toUint32Double();
                  }

               }
            }).emit(CodeGenerator.getOptimisticIgnoreCountForSelfModifyingExpression(binaryNode.lhs()));
            CodeGenerator.this.method.convert(((BinaryNode)this.assignNode).getType());
         }
      }).store();
   }

   private void doSHR(final BinaryNode binaryNode) {
      (new CodeGenerator.OptimisticOperation(binaryNode, new CodeGenerator.TypeBounds(Type.INT, Type.NUMBER)) {
         void loadStack() {
            if (CodeGenerator.isRhsZero(binaryNode)) {
               CodeGenerator.this.loadExpressionAsType(binaryNode.lhs(), Type.INT);
            } else {
               CodeGenerator.this.loadBinaryOperands(binaryNode);
               CodeGenerator.this.method.shr();
            }

         }

         void consumeStack() {
            if (CodeGenerator.isOptimistic(binaryNode)) {
               CodeGenerator.this.toUint32Optimistic(binaryNode.getProgramPoint());
            } else {
               CodeGenerator.this.toUint32Double();
            }

         }
      }).emit();
   }

   private void toUint32Optimistic(int programPoint) {
      this.method.load(programPoint);
      JSType.TO_UINT32_OPTIMISTIC.invoke(this.method);
   }

   private void toUint32Double() {
      JSType.TO_UINT32_DOUBLE.invoke(this.method);
   }

   private void loadASSIGN_SUB(BinaryNode binaryNode) {
      (new CodeGenerator.BinaryOptimisticSelfAssignment(binaryNode) {
         protected void op(CodeGenerator.OptimisticOperation oo) {
            CodeGenerator.this.method.sub(oo.getProgramPoint());
         }
      }).store();
   }

   private void loadBIT_AND(BinaryNode binaryNode) {
      this.loadBinaryOperands(binaryNode);
      this.method.and();
   }

   private void loadBIT_OR(BinaryNode binaryNode) {
      if (isRhsZero(binaryNode)) {
         this.loadExpressionAsType(binaryNode.lhs(), Type.INT);
      } else {
         this.loadBinaryOperands(binaryNode);
         this.method.or();
      }

   }

   private static boolean isRhsZero(BinaryNode binaryNode) {
      Expression rhs = binaryNode.rhs();
      return rhs instanceof LiteralNode && INT_ZERO.equals(((LiteralNode)rhs).getValue());
   }

   private void loadBIT_XOR(BinaryNode binaryNode) {
      this.loadBinaryOperands(binaryNode);
      this.method.xor();
   }

   private void loadCOMMARIGHT(BinaryNode binaryNode, CodeGenerator.TypeBounds resultBounds) {
      this.loadAndDiscard(binaryNode.lhs());
      this.loadMaybeDiscard(binaryNode, binaryNode.rhs(), resultBounds);
   }

   private void loadCOMMALEFT(BinaryNode binaryNode, CodeGenerator.TypeBounds resultBounds) {
      this.loadMaybeDiscard(binaryNode, binaryNode.lhs(), resultBounds);
      this.loadAndDiscard(binaryNode.rhs());
   }

   private void loadDIV(BinaryNode binaryNode, CodeGenerator.TypeBounds resultBounds) {
      (new CodeGenerator.BinaryArith() {
         protected void op(int programPoint) {
            CodeGenerator.this.method.div(programPoint);
         }
      }).evaluate(binaryNode, resultBounds);
   }

   private void loadCmp(BinaryNode binaryNode, Condition cond) {
      this.loadComparisonOperands(binaryNode);
      Label trueLabel = new Label("trueLabel");
      Label afterLabel = new Label("skip");
      this.method.conditionalJump(cond, trueLabel);
      this.method.load(Boolean.FALSE);
      this.method._goto(afterLabel);
      this.method.label(trueLabel);
      this.method.load(Boolean.TRUE);
      this.method.label(afterLabel);
   }

   private void loadMOD(BinaryNode binaryNode, CodeGenerator.TypeBounds resultBounds) {
      (new CodeGenerator.BinaryArith() {
         protected void op(int programPoint) {
            CodeGenerator.this.method.rem(programPoint);
         }
      }).evaluate(binaryNode, resultBounds);
   }

   private void loadMUL(BinaryNode binaryNode, CodeGenerator.TypeBounds resultBounds) {
      (new CodeGenerator.BinaryArith() {
         protected void op(int programPoint) {
            CodeGenerator.this.method.mul(programPoint);
         }
      }).evaluate(binaryNode, resultBounds);
   }

   private void loadSAR(BinaryNode binaryNode) {
      this.loadBinaryOperands(binaryNode);
      this.method.sar();
   }

   private void loadSHL(BinaryNode binaryNode) {
      this.loadBinaryOperands(binaryNode);
      this.method.shl();
   }

   private void loadSHR(BinaryNode binaryNode) {
      this.doSHR(binaryNode);
   }

   private void loadSUB(BinaryNode binaryNode, CodeGenerator.TypeBounds resultBounds) {
      (new CodeGenerator.BinaryArith() {
         protected void op(int programPoint) {
            CodeGenerator.this.method.sub(programPoint);
         }
      }).evaluate(binaryNode, resultBounds);
   }

   public boolean enterLabelNode(LabelNode labelNode) {
      this.labeledBlockBreakLiveLocals.push(((CodeGeneratorLexicalContext)this.lc).getUsedSlotCount());
      return true;
   }

   protected boolean enterDefault(Node node) {
      throw new AssertionError("Code generator entered node of type " + node.getClass().getName());
   }

   private void loadTernaryNode(TernaryNode ternaryNode, CodeGenerator.TypeBounds resultBounds) {
      Expression test = ternaryNode.getTest();
      JoinPredecessorExpression trueExpr = ternaryNode.getTrueExpression();
      JoinPredecessorExpression falseExpr = ternaryNode.getFalseExpression();
      Label falseLabel = new Label("ternary_false");
      Label exitLabel = new Label("ternary_exit");
      Type outNarrowest = Type.narrowest(resultBounds.widest, Type.generic(Type.widestReturnType(trueExpr.getType(), falseExpr.getType())));
      CodeGenerator.TypeBounds outBounds = resultBounds.notNarrowerThan(outNarrowest);
      this.emitBranch(test, falseLabel, false);
      boolean isCurrentDiscard = ((CodeGeneratorLexicalContext)this.lc).popDiscardIfCurrent(ternaryNode);
      this.loadMaybeDiscard(isCurrentDiscard, trueExpr.getExpression(), outBounds);

      assert isCurrentDiscard || Type.generic(this.method.peekType()) == outBounds.narrowest;

      this.method.beforeJoinPoint(trueExpr);
      this.method._goto(exitLabel);
      this.method.label(falseLabel);
      this.loadMaybeDiscard(isCurrentDiscard, falseExpr.getExpression(), outBounds);

      assert isCurrentDiscard || Type.generic(this.method.peekType()) == outBounds.narrowest;

      this.method.beforeJoinPoint(falseExpr);
      this.method.label(exitLabel);
   }

   void generateScopeCalls() {
      Iterator var1 = ((CodeGeneratorLexicalContext)this.lc).getScopeCalls().iterator();

      while(var1.hasNext()) {
         SharedScopeCall scopeAccess = (SharedScopeCall)var1.next();
         scopeAccess.generateScopeCall();
      }

   }

   private void printSymbols(Block block, FunctionNode function, String ident) {
      if (this.compiler.getScriptEnvironment()._print_symbols || function.getFlag(2097152)) {
         PrintWriter out = this.compiler.getScriptEnvironment().getErr();
         out.println("[BLOCK in '" + ident + "']");
         if (!block.printSymbols(out)) {
            out.println("<no symbols>");
         }

         out.println();
      }

   }

   private void newFunctionObject(FunctionNode functionNode, boolean addInitializer) {
      assert ((CodeGeneratorLexicalContext)this.lc).peek() == functionNode;

      RecompilableScriptFunctionData data = this.compiler.getScriptFunctionData(functionNode.getId());
      if (functionNode.isProgram() && !this.compiler.isOnDemandCompilation()) {
         MethodEmitter createFunction = functionNode.getCompileUnit().getClassEmitter().method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), CompilerConstants.CREATE_PROGRAM_FUNCTION.symbolName(), ScriptFunction.class, ScriptObject.class);
         createFunction.begin();
         this.loadConstantsAndIndex(data, createFunction);
         createFunction.load(SCOPE_TYPE, 0);
         createFunction.invoke(CREATE_FUNCTION_OBJECT);
         createFunction._return();
         createFunction.end();
      }

      if (addInitializer && !this.compiler.isOnDemandCompilation()) {
         functionNode.getCompileUnit().addFunctionInitializer(data, functionNode);
      }

      if (((CodeGeneratorLexicalContext)this.lc).getOutermostFunction() != functionNode) {
         this.loadConstantsAndIndex(data, this.method);
         if (functionNode.needsParentScope()) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);
            this.method.invoke(CREATE_FUNCTION_OBJECT);
         } else {
            this.method.invoke(CREATE_FUNCTION_OBJECT_NO_SCOPE);
         }

      }
   }

   private MethodEmitter globalInstance() {
      return this.method.invokestatic(GLOBAL_OBJECT, "instance", "()L" + GLOBAL_OBJECT + ';');
   }

   private MethodEmitter globalAllocateArguments() {
      return this.method.invokestatic(GLOBAL_OBJECT, "allocateArguments", CompilerConstants.methodDescriptor(ScriptObject.class, Object[].class, Object.class, Integer.TYPE));
   }

   private MethodEmitter globalNewRegExp() {
      return this.method.invokestatic(GLOBAL_OBJECT, "newRegExp", CompilerConstants.methodDescriptor(Object.class, String.class, String.class));
   }

   private MethodEmitter globalRegExpCopy() {
      return this.method.invokestatic(GLOBAL_OBJECT, "regExpCopy", CompilerConstants.methodDescriptor(Object.class, Object.class));
   }

   private MethodEmitter globalAllocateArray(ArrayType type) {
      return this.method.invokestatic(GLOBAL_OBJECT, "allocate", "(" + type.getDescriptor() + ")Ljdk/nashorn/internal/objects/NativeArray;");
   }

   private MethodEmitter globalIsEval() {
      return this.method.invokestatic(GLOBAL_OBJECT, "isEval", CompilerConstants.methodDescriptor(Boolean.TYPE, Object.class));
   }

   private MethodEmitter globalReplaceLocationPropertyPlaceholder() {
      return this.method.invokestatic(GLOBAL_OBJECT, "replaceLocationPropertyPlaceholder", CompilerConstants.methodDescriptor(Object.class, Object.class, Object.class));
   }

   private MethodEmitter globalCheckObjectCoercible() {
      return this.method.invokestatic(GLOBAL_OBJECT, "checkObjectCoercible", CompilerConstants.methodDescriptor(Void.TYPE, Object.class));
   }

   private MethodEmitter globalDirectEval() {
      return this.method.invokestatic(GLOBAL_OBJECT, "directEval", CompilerConstants.methodDescriptor(Object.class, Object.class, Object.class, Object.class, Object.class, Boolean.TYPE));
   }

   private static boolean isOptimistic(Optimistic optimistic) {
      if (!optimistic.canBeOptimistic()) {
         return false;
      } else {
         Expression expr = (Expression)optimistic;
         return expr.getType().narrowerThan(expr.getWidestOperationType());
      }
   }

   private static boolean everyLocalLoadIsValid(int[] loads, int localCount) {
      int[] var2 = loads;
      int var3 = loads.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int load = var2[var4];
         if (load < 0 || load >= localCount) {
            return false;
         }
      }

      return true;
   }

   private static boolean everyStackValueIsLocalLoad(int[] loads) {
      int[] var1 = loads;
      int var2 = loads.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         int load = var1[var3];
         if (load == -1) {
            return false;
         }
      }

      return true;
   }

   private String getLvarTypesDescriptor(List<Type> localVarTypes) {
      int count = localVarTypes.size();
      StringBuilder desc = new StringBuilder(count);

      for(int i = 0; i < count; i += appendType(desc, (Type)localVarTypes.get(i))) {
      }

      return this.method.markSymbolBoundariesInLvarTypesDescriptor(desc.toString());
   }

   private static int appendType(StringBuilder b, Type t) {
      b.append(t.getBytecodeStackType());
      return t.getSlots();
   }

   private static int countSymbolsInLvarTypeDescriptor(String lvarTypeDescriptor) {
      int count = 0;

      for(int i = 0; i < lvarTypeDescriptor.length(); ++i) {
         if (Character.isUpperCase(lvarTypeDescriptor.charAt(i))) {
            ++count;
         }
      }

      return count;
   }

   private boolean generateUnwarrantedOptimismExceptionHandlers(FunctionNode fn) {
      if (!this.useOptimisticTypes()) {
         return false;
      } else {
         Map<String, Collection<Label>> unwarrantedOptimismHandlers = ((CodeGeneratorLexicalContext)this.lc).popUnwarrantedOptimismHandlers();
         if (unwarrantedOptimismHandlers.isEmpty()) {
            return false;
         } else {
            this.method.lineNumber(0);
            List<CodeGenerator.OptimismExceptionHandlerSpec> handlerSpecs = new ArrayList(unwarrantedOptimismHandlers.size() * 4 / 3);
            Iterator var4 = unwarrantedOptimismHandlers.keySet().iterator();

            while(var4.hasNext()) {
               String spec = (String)var4.next();
               handlerSpecs.add(new CodeGenerator.OptimismExceptionHandlerSpec(spec, true));
            }

            Collections.sort(handlerSpecs, Collections.reverseOrder());
            Map<String, Label> delegationLabels = new HashMap();

            for(int handlerIndex = 0; handlerIndex < handlerSpecs.size(); ++handlerIndex) {
               CodeGenerator.OptimismExceptionHandlerSpec spec = (CodeGenerator.OptimismExceptionHandlerSpec)handlerSpecs.get(handlerIndex);
               String lvarSpec = spec.lvarSpec;
               if (spec.catchTarget) {
                  assert !this.method.isReachable();

                  this.method._catch((Collection)unwarrantedOptimismHandlers.get(lvarSpec));
                  this.method.load(countSymbolsInLvarTypeDescriptor(lvarSpec));
                  this.method.newarray(Type.OBJECT_ARRAY);
               }

               if (spec.delegationTarget) {
                  this.method.label((Label)delegationLabels.get(lvarSpec));
               }

               boolean lastHandler = handlerIndex == handlerSpecs.size() - 1;
               int lvarIndex;
               int firstArrayIndex;
               int firstLvarIndex;
               Label delegationLabel;
               String commonLvarSpec;
               int args;
               int j;
               if (lastHandler) {
                  lvarIndex = 0;
                  firstLvarIndex = 0;
                  firstArrayIndex = 0;
                  delegationLabel = null;
                  commonLvarSpec = null;
               } else {
                  args = handlerIndex + 1;
                  String nextLvarSpec = ((CodeGenerator.OptimismExceptionHandlerSpec)handlerSpecs.get(args)).lvarSpec;
                  commonLvarSpec = commonPrefix(lvarSpec, nextLvarSpec);

                  assert Character.isUpperCase(commonLvarSpec.charAt(commonLvarSpec.length() - 1));

                  boolean addNewHandler = true;

                  int commonHandlerIndex;
                  for(commonHandlerIndex = args; commonHandlerIndex < handlerSpecs.size(); ++commonHandlerIndex) {
                     CodeGenerator.OptimismExceptionHandlerSpec forwardHandlerSpec = (CodeGenerator.OptimismExceptionHandlerSpec)handlerSpecs.get(commonHandlerIndex);
                     String forwardLvarSpec = forwardHandlerSpec.lvarSpec;
                     if (forwardLvarSpec.equals(commonLvarSpec)) {
                        addNewHandler = false;
                        forwardHandlerSpec.delegationTarget = true;
                        break;
                     }

                     if (!forwardLvarSpec.startsWith(commonLvarSpec)) {
                        break;
                     }
                  }

                  if (addNewHandler) {
                     handlerSpecs.add(commonHandlerIndex, new CodeGenerator.OptimismExceptionHandlerSpec(commonLvarSpec, false));
                  }

                  firstArrayIndex = countSymbolsInLvarTypeDescriptor(commonLvarSpec);
                  lvarIndex = 0;

                  for(j = 0; j < commonLvarSpec.length(); ++j) {
                     lvarIndex += CodeGeneratorLexicalContext.getTypeForSlotDescriptor(commonLvarSpec.charAt(j)).getSlots();
                  }

                  firstLvarIndex = lvarIndex;
                  delegationLabel = (Label)delegationLabels.get(commonLvarSpec);
                  if (delegationLabel == null) {
                     delegationLabel = new Label("uo_pa_" + commonLvarSpec);
                     delegationLabels.put(commonLvarSpec, delegationLabel);
                  }
               }

               args = 0;
               boolean symbolHadValue = false;

               for(j = commonLvarSpec == null ? 0 : commonLvarSpec.length(); j < lvarSpec.length(); ++j) {
                  char typeDesc = lvarSpec.charAt(j);
                  Type lvarType = CodeGeneratorLexicalContext.getTypeForSlotDescriptor(typeDesc);
                  if (!lvarType.isUnknown()) {
                     this.method.load(lvarType, lvarIndex);
                     symbolHadValue = true;
                     ++args;
                  } else if (typeDesc == 'U' && !symbolHadValue) {
                     if (this.method.peekType() == Type.UNDEFINED) {
                        this.method.dup();
                     } else {
                        this.method.loadUndefined(Type.OBJECT);
                     }

                     ++args;
                  }

                  if (Character.isUpperCase(typeDesc)) {
                     symbolHadValue = false;
                  }

                  lvarIndex += lvarType.getSlots();
               }

               assert args > 0;

               this.method.dynamicArrayPopulatorCall(args + 1, firstArrayIndex);
               if (delegationLabel != null) {
                  assert !lastHandler;

                  assert commonLvarSpec != null;

                  this.method.undefineLocalVariables(firstLvarIndex, true);
                  CodeGenerator.OptimismExceptionHandlerSpec nextSpec = (CodeGenerator.OptimismExceptionHandlerSpec)handlerSpecs.get(handlerIndex + 1);
                  if (!nextSpec.lvarSpec.equals(commonLvarSpec) || nextSpec.catchTarget) {
                     this.method._goto(delegationLabel);
                  }
               } else {
                  assert lastHandler;

                  this.loadConstant((Object)getByteCodeSymbolNames(fn));
                  if (this.isRestOf()) {
                     this.loadConstant((Object)this.getContinuationEntryPoints());
                     this.method.invoke(CREATE_REWRITE_EXCEPTION_REST_OF);
                  } else {
                     this.method.invoke(CREATE_REWRITE_EXCEPTION);
                  }

                  this.method.athrow();
               }
            }

            return true;
         }
      }
   }

   private static String[] getByteCodeSymbolNames(FunctionNode fn) {
      List<String> names = new ArrayList();
      Iterator var2 = fn.getBody().getSymbols().iterator();

      while(var2.hasNext()) {
         Symbol symbol = (Symbol)var2.next();
         if (symbol.hasSlot()) {
            if (symbol.isScope()) {
               assert symbol.isParam();

               names.add((Object)null);
            } else {
               names.add(symbol.getName());
            }
         }
      }

      return (String[])names.toArray(new String[names.size()]);
   }

   private static String commonPrefix(String s1, String s2) {
      int l1 = s1.length();
      int l = Math.min(l1, s2.length());
      int lms = -1;

      for(int i = 0; i < l; ++i) {
         char c1 = s1.charAt(i);
         if (c1 != s2.charAt(i)) {
            return s1.substring(0, lms + 1);
         }

         if (Character.isUpperCase(c1)) {
            lms = i;
         }
      }

      return l == l1 ? s1 : s2;
   }

   private CodeGenerator.ContinuationInfo getContinuationInfo() {
      return this.continuationInfo;
   }

   private void generateContinuationHandler() {
      if (this.isRestOf()) {
         CodeGenerator.ContinuationInfo ci = this.getContinuationInfo();
         this.method.label(ci.getHandlerLabel());
         this.method.lineNumber(0);
         Label.Stack stack = ci.getTargetLabel().getStack();
         List<Type> lvarTypes = stack.getLocalVariableTypesCopy();
         BitSet symbolBoundary = stack.getSymbolBoundaryCopy();
         int lvarCount = ci.lvarCount;
         Type rewriteExceptionType = Type.typeFor(RewriteException.class);
         this.method.load(rewriteExceptionType, 0);
         this.method.storeTemp(rewriteExceptionType, lvarCount);
         this.method.load(rewriteExceptionType, 0);
         this.method.invoke(RewriteException.GET_BYTECODE_SLOTS);
         int arrayIndex = 0;

         int nextLvarIndex;
         for(int lvarIndex = 0; lvarIndex < lvarCount; lvarIndex = nextLvarIndex) {
            Type lvarType = (Type)lvarTypes.get(lvarIndex);
            if (!lvarType.isUnknown()) {
               this.method.dup();
               this.method.load(arrayIndex).arrayload();
               Class<?> typeClass = lvarType.getTypeClass();
               if (typeClass == long[].class) {
                  this.method.load(rewriteExceptionType, lvarCount);
                  this.method.invoke(RewriteException.TO_LONG_ARRAY);
               } else if (typeClass == double[].class) {
                  this.method.load(rewriteExceptionType, lvarCount);
                  this.method.invoke(RewriteException.TO_DOUBLE_ARRAY);
               } else if (typeClass == Object[].class) {
                  this.method.load(rewriteExceptionType, lvarCount);
                  this.method.invoke(RewriteException.TO_OBJECT_ARRAY);
               } else {
                  if (!typeClass.isPrimitive() && typeClass != Object.class) {
                     this.method.loadType(Type.getInternalName(typeClass));
                     this.method.invoke(RewriteException.INSTANCE_OR_NULL);
                  }

                  this.method.convert(lvarType);
               }

               this.method.storeHidden(lvarType, lvarIndex, false);
            }

            nextLvarIndex = lvarIndex + lvarType.getSlots();
            if (symbolBoundary.get(nextLvarIndex - 1)) {
               ++arrayIndex;
            }
         }

         if (AssertsEnabled.assertsEnabled()) {
            this.method.load(arrayIndex);
            this.method.invoke(RewriteException.ASSERT_ARRAY_LENGTH);
         } else {
            this.method.pop();
         }

         int[] stackStoreSpec = ci.getStackStoreSpec();
         Type[] stackTypes = ci.getStackTypes();
         boolean isStackEmpty = stackStoreSpec.length == 0;
         int replacedObjectLiteralMaps = 0;
         if (!isStackEmpty) {
            for(int i = 0; i < stackStoreSpec.length; ++i) {
               int slot = stackStoreSpec[i];
               this.method.load((Type)lvarTypes.get(slot), slot);
               this.method.convert(stackTypes[i]);
               PropertyMap map = ci.getObjectLiteralMap(i);
               if (map != null) {
                  this.method.dup();

                  assert ScriptObject.class.isAssignableFrom(this.method.peekType().getTypeClass()) : this.method.peekType().getTypeClass() + " is not a script object";

                  this.loadConstant((Object)map);
                  this.method.invoke(ScriptObject.SET_MAP);
                  ++replacedObjectLiteralMaps;
               }
            }
         }

         assert ci.objectLiteralMaps == null || ci.objectLiteralMaps.size() == replacedObjectLiteralMaps;

         this.method.load(rewriteExceptionType, lvarCount);
         this.method.loadNull();
         this.method.storeHidden(Type.OBJECT, lvarCount);
         this.method.markDeadSlots(lvarCount, Type.OBJECT.getSlots());
         this.method.invoke(RewriteException.GET_RETURN_VALUE);
         Type returnValueType = ci.getReturnValueType();
         boolean needsCatch = false;
         Label targetCatchLabel = ci.catchLabel;
         Label _try = null;
         if (returnValueType.isPrimitive()) {
            this.method.lineNumber(ci.lineNumber);
            if (targetCatchLabel != METHOD_BOUNDARY) {
               _try = new Label("");
               this.method.label(_try);
               needsCatch = true;
            }
         }

         this.method.convert(returnValueType);
         int scopePopCount = needsCatch ? ci.exceptionScopePops : 0;
         Label catchLabel = scopePopCount > 0 ? new Label("") : targetCatchLabel;
         if (needsCatch) {
            Label _end_try = new Label("");
            this.method.label(_end_try);
            this.method._try(_try, _end_try, catchLabel);
         }

         this.method._goto(ci.getTargetLabel());
         if (catchLabel != targetCatchLabel) {
            this.method.lineNumber(0);

            assert scopePopCount > 0;

            this.method._catch(catchLabel);
            this.popScopes(scopePopCount);
            this.method.uncheckedGoto(targetCatchLabel);
         }

      }
   }

   static {
      ENSURE_INT = CompilerConstants.staticCallNoLookup(OptimisticReturnFilters.class, "ensureInt", Integer.TYPE, Object.class, Integer.TYPE);
      ENSURE_NUMBER = CompilerConstants.staticCallNoLookup(OptimisticReturnFilters.class, "ensureNumber", Double.TYPE, Object.class, Integer.TYPE);
      CREATE_FUNCTION_OBJECT = CompilerConstants.staticCallNoLookup(ScriptFunction.class, "create", ScriptFunction.class, Object[].class, Integer.TYPE, ScriptObject.class);
      CREATE_FUNCTION_OBJECT_NO_SCOPE = CompilerConstants.staticCallNoLookup(ScriptFunction.class, "create", ScriptFunction.class, Object[].class, Integer.TYPE);
      TO_NUMBER_FOR_EQ = CompilerConstants.staticCallNoLookup(JSType.class, "toNumberForEq", Double.TYPE, Object.class);
      TO_NUMBER_FOR_STRICT_EQ = CompilerConstants.staticCallNoLookup(JSType.class, "toNumberForStrictEq", Double.TYPE, Object.class);
      ITERATOR_CLASS = Iterator.class;

      assert ITERATOR_CLASS == CompilerConstants.ITERATOR_PREFIX.type();

      ITERATOR_TYPE = Type.typeFor(ITERATOR_CLASS);
      EXCEPTION_TYPE = Type.typeFor(CompilerConstants.EXCEPTION_PREFIX.type());
      INT_ZERO = 0;
      OBJECT_SPILL_THRESHOLD = Options.getIntProperty("nashorn.spill.threshold", 256);
      METHOD_BOUNDARY = new Label("");
   }

   interface SplitLiteralCreator {
      void populateRange(MethodEmitter var1, Type var2, int var3, int var4, int var5);
   }

   private static class ContinuationInfo {
      private final Label handlerLabel = new Label("continuation_handler");
      private Label targetLabel;
      int lvarCount;
      private int[] stackStoreSpec;
      private Type[] stackTypes;
      private Type returnValueType;
      private Map<Integer, PropertyMap> objectLiteralMaps;
      private int lineNumber;
      private Label catchLabel;
      private int exceptionScopePops;

      ContinuationInfo() {
      }

      Label getHandlerLabel() {
         return this.handlerLabel;
      }

      boolean hasTargetLabel() {
         return this.targetLabel != null;
      }

      Label getTargetLabel() {
         return this.targetLabel;
      }

      void setTargetLabel(Label targetLabel) {
         this.targetLabel = targetLabel;
      }

      int[] getStackStoreSpec() {
         return (int[])this.stackStoreSpec.clone();
      }

      void setStackStoreSpec(int[] stackStoreSpec) {
         this.stackStoreSpec = stackStoreSpec;
      }

      Type[] getStackTypes() {
         return (Type[])this.stackTypes.clone();
      }

      void setStackTypes(Type[] stackTypes) {
         this.stackTypes = stackTypes;
      }

      Type getReturnValueType() {
         return this.returnValueType;
      }

      void setReturnValueType(Type returnValueType) {
         this.returnValueType = returnValueType;
      }

      void setObjectLiteralMap(int objectLiteralStackDepth, PropertyMap objectLiteralMap) {
         if (this.objectLiteralMaps == null) {
            this.objectLiteralMaps = new HashMap();
         }

         this.objectLiteralMaps.put(objectLiteralStackDepth, objectLiteralMap);
      }

      PropertyMap getObjectLiteralMap(int stackDepth) {
         return this.objectLiteralMaps == null ? null : (PropertyMap)this.objectLiteralMaps.get(stackDepth);
      }

      public String toString() {
         return "[localVariableTypes=" + this.targetLabel.getStack().getLocalVariableTypesCopy() + ", stackStoreSpec=" + Arrays.toString(this.stackStoreSpec) + ", returnValueType=" + this.returnValueType + "]";
      }
   }

   private static class OptimismExceptionHandlerSpec implements Comparable<CodeGenerator.OptimismExceptionHandlerSpec> {
      private final String lvarSpec;
      private final boolean catchTarget;
      private boolean delegationTarget;

      OptimismExceptionHandlerSpec(String lvarSpec, boolean catchTarget) {
         this.lvarSpec = lvarSpec;
         this.catchTarget = catchTarget;
         if (!catchTarget) {
            this.delegationTarget = true;
         }

      }

      public int compareTo(CodeGenerator.OptimismExceptionHandlerSpec o) {
         return this.lvarSpec.compareTo(o.lvarSpec);
      }

      public String toString() {
         StringBuilder b = (new StringBuilder(64)).append("[HandlerSpec ").append(this.lvarSpec);
         if (this.catchTarget) {
            b.append(", catchTarget");
         }

         if (this.delegationTarget) {
            b.append(", delegationTarget");
         }

         return b.append("]").toString();
      }
   }

   private abstract class OptimisticOperation {
      private final boolean isOptimistic;
      private final Expression expression;
      private final Optimistic optimistic;
      private final CodeGenerator.TypeBounds resultBounds;

      OptimisticOperation(Optimistic optimistic, CodeGenerator.TypeBounds resultBounds) {
         this.optimistic = optimistic;
         this.expression = (Expression)optimistic;
         this.resultBounds = resultBounds;
         this.isOptimistic = CodeGenerator.isOptimistic(optimistic) && CodeGenerator.this.useOptimisticTypes() && resultBounds.within(Type.generic(((Expression)optimistic).getType())).narrowerThan(resultBounds.widest);
      }

      MethodEmitter emit() {
         return this.emit(0);
      }

      MethodEmitter emit(int ignoredArgCount) {
         int programPoint = this.optimistic.getProgramPoint();
         boolean optimisticOrContinuation = this.isOptimistic || CodeGenerator.this.isContinuationEntryPoint(programPoint);
         boolean currentContinuationEntryPoint = CodeGenerator.this.isCurrentContinuationEntryPoint(programPoint);
         int stackSizeOnEntry = CodeGenerator.this.method.getStackSize() - ignoredArgCount;
         this.storeStack(ignoredArgCount, optimisticOrContinuation);
         this.loadStack();
         int liveLocalsCount = this.storeStack(CodeGenerator.this.method.getStackSize() - stackSizeOnEntry, optimisticOrContinuation);

         assert optimisticOrContinuation == (liveLocalsCount != -1);

         Label afterConsumeStack = !this.isOptimistic && !currentContinuationEntryPoint ? null : new Label("after_consume_stack");
         Label beginTry;
         Label catchLabel;
         if (this.isOptimistic) {
            beginTry = new Label("try_optimistic");
            String catchLabelName = (afterConsumeStack == null ? "" : afterConsumeStack.toString()) + "_handler";
            catchLabel = new Label(catchLabelName);
            CodeGenerator.this.method.label(beginTry);
         } else {
            catchLabel = null;
            beginTry = null;
         }

         this.consumeStack();
         if (this.isOptimistic) {
            CodeGenerator.this.method._try(beginTry, afterConsumeStack, catchLabel, UnwarrantedOptimismException.class);
         }

         if (this.isOptimistic || currentContinuationEntryPoint) {
            CodeGenerator.this.method.label(afterConsumeStack);
            int[] localLoads = CodeGenerator.this.method.getLocalLoadsOnStack(0, stackSizeOnEntry);

            assert CodeGenerator.everyStackValueIsLocalLoad(localLoads) : Arrays.toString(localLoads) + ", " + stackSizeOnEntry + ", " + ignoredArgCount;

            List<Type> localTypesList = CodeGenerator.this.method.getLocalVariableTypes();
            int usedLocals = CodeGenerator.this.method.getUsedSlotsWithLiveTemporaries();
            List<Type> localTypes = CodeGenerator.this.method.getWidestLiveLocals(localTypesList.subList(0, usedLocals));

            assert CodeGenerator.everyLocalLoadIsValid(localLoads, usedLocals) : Arrays.toString(localLoads) + " ~ " + localTypes;

            if (this.isOptimistic) {
               this.addUnwarrantedOptimismHandlerLabel(localTypes, catchLabel);
            }

            if (currentContinuationEntryPoint) {
               CodeGenerator.ContinuationInfo ci = CodeGenerator.this.getContinuationInfo();

               assert ci != null : "no continuation info found for " + ((CodeGeneratorLexicalContext)CodeGenerator.this.lc).getCurrentFunction();

               assert !ci.hasTargetLabel();

               ci.setTargetLabel(afterConsumeStack);
               ci.getHandlerLabel().markAsOptimisticContinuationHandlerFor(afterConsumeStack);
               ci.lvarCount = localTypes.size();
               ci.setStackStoreSpec(localLoads);
               ci.setStackTypes((Type[])Arrays.copyOf(CodeGenerator.this.method.getTypesFromStack(CodeGenerator.this.method.getStackSize()), stackSizeOnEntry));

               assert ci.getStackStoreSpec().length == ci.getStackTypes().length;

               ci.setReturnValueType(CodeGenerator.this.method.peekType());
               ci.lineNumber = CodeGenerator.this.getLastLineNumber();
               ci.catchLabel = (Label)CodeGenerator.this.catchLabels.peek();
            }
         }

         return CodeGenerator.this.method;
      }

      private int storeStack(int ignoreArgCount, boolean optimisticOrContinuation) {
         if (!optimisticOrContinuation) {
            return -1;
         } else {
            int stackSize = CodeGenerator.this.method.getStackSize();
            Type[] stackTypes = CodeGenerator.this.method.getTypesFromStack(stackSize);
            int[] localLoadsOnStack = CodeGenerator.this.method.getLocalLoadsOnStack(0, stackSize);
            int usedSlots = CodeGenerator.this.method.getUsedSlotsWithLiveTemporaries();
            int firstIgnored = stackSize - ignoreArgCount;

            int firstNonLoad;
            for(firstNonLoad = 0; firstNonLoad < firstIgnored && localLoadsOnStack[firstNonLoad] != -1; ++firstNonLoad) {
            }

            if (firstNonLoad >= firstIgnored) {
               return usedSlots;
            } else {
               int tempSlotsNeeded = 0;

               int lastTempSlot;
               for(lastTempSlot = firstNonLoad; lastTempSlot < stackSize; ++lastTempSlot) {
                  if (localLoadsOnStack[lastTempSlot] == -1) {
                     tempSlotsNeeded += stackTypes[lastTempSlot].getSlots();
                  }
               }

               lastTempSlot = usedSlots + tempSlotsNeeded;
               int ignoreSlotCount = 0;
               int i = stackSize;

               int ix;
               while(i-- > firstNonLoad) {
                  ix = localLoadsOnStack[i];
                  if (ix == -1) {
                     Type type = stackTypes[i];
                     int slots = type.getSlots();
                     lastTempSlot -= slots;
                     if (i >= firstIgnored) {
                        ignoreSlotCount += slots;
                     }

                     CodeGenerator.this.method.storeTemp(type, lastTempSlot);
                  } else {
                     CodeGenerator.this.method.pop();
                  }
               }

               assert lastTempSlot == usedSlots;

               List<Type> localTypesList = CodeGenerator.this.method.getLocalVariableTypes();

               for(ix = firstNonLoad; ix < stackSize; ++ix) {
                  int loadSlot = localLoadsOnStack[ix];
                  Type stackType = stackTypes[ix];
                  boolean isLoad = loadSlot != -1;
                  int lvarSlot = isLoad ? loadSlot : lastTempSlot;
                  Type lvarType = (Type)localTypesList.get(lvarSlot);
                  CodeGenerator.this.method.load(lvarType, lvarSlot);
                  if (isLoad) {
                     CodeGenerator.this.method.convert(stackType);
                  } else {
                     assert lvarType == stackType;

                     lastTempSlot += lvarType.getSlots();
                  }
               }

               assert lastTempSlot == usedSlots + tempSlotsNeeded;

               return lastTempSlot - ignoreSlotCount;
            }
         }
      }

      private void addUnwarrantedOptimismHandlerLabel(List<Type> localTypes, Label label) {
         String lvarTypesDescriptor = CodeGenerator.this.getLvarTypesDescriptor(localTypes);
         Map<String, Collection<Label>> unwarrantedOptimismHandlers = ((CodeGeneratorLexicalContext)CodeGenerator.this.lc).getUnwarrantedOptimismHandlers();
         Collection<Label> labels = (Collection)unwarrantedOptimismHandlers.get(lvarTypesDescriptor);
         if (labels == null) {
            labels = new LinkedList();
            unwarrantedOptimismHandlers.put(lvarTypesDescriptor, labels);
         }

         CodeGenerator.this.method.markLabelAsOptimisticCatchHandler(label, localTypes.size());
         ((Collection)labels).add(label);
      }

      abstract void loadStack();

      abstract void consumeStack();

      MethodEmitter dynamicGet(String name, int flags, boolean isMethod, boolean isIndex) {
         return this.isOptimistic ? CodeGenerator.this.method.dynamicGet(this.getOptimisticCoercedType(), name, this.getOptimisticFlags(flags), isMethod, isIndex) : CodeGenerator.this.method.dynamicGet(this.resultBounds.within(this.expression.getType()), name, CodeGenerator.nonOptimisticFlags(flags), isMethod, isIndex);
      }

      MethodEmitter dynamicGetIndex(int flags, boolean isMethod) {
         return this.isOptimistic ? CodeGenerator.this.method.dynamicGetIndex(this.getOptimisticCoercedType(), this.getOptimisticFlags(flags), isMethod) : CodeGenerator.this.method.dynamicGetIndex(this.resultBounds.within(this.expression.getType()), CodeGenerator.nonOptimisticFlags(flags), isMethod);
      }

      MethodEmitter dynamicCall(int argCount, int flags, String msg) {
         return this.isOptimistic ? CodeGenerator.this.method.dynamicCall(this.getOptimisticCoercedType(), argCount, this.getOptimisticFlags(flags), msg) : CodeGenerator.this.method.dynamicCall(this.resultBounds.within(this.expression.getType()), argCount, CodeGenerator.nonOptimisticFlags(flags), msg);
      }

      int getOptimisticFlags(int flags) {
         return flags | 8 | this.optimistic.getProgramPoint() << 11;
      }

      int getProgramPoint() {
         return this.isOptimistic ? this.optimistic.getProgramPoint() : -1;
      }

      void convertOptimisticReturnValue() {
         if (this.isOptimistic) {
            Type optimisticType = this.getOptimisticCoercedType();
            if (!optimisticType.isObject()) {
               CodeGenerator.this.method.load(this.optimistic.getProgramPoint());
               if (optimisticType.isInteger()) {
                  CodeGenerator.this.method.invoke(CodeGenerator.ENSURE_INT);
               } else {
                  if (!optimisticType.isNumber()) {
                     throw new AssertionError(optimisticType);
                  }

                  CodeGenerator.this.method.invoke(CodeGenerator.ENSURE_NUMBER);
               }
            }
         }

      }

      void replaceCompileTimeProperty() {
         IdentNode identNode = (IdentNode)this.expression;
         String name = identNode.getSymbol().getName();
         if (CompilerConstants.__FILE__.name().equals(name)) {
            this.replaceCompileTimeProperty(CodeGenerator.this.getCurrentSource().getName());
         } else if (CompilerConstants.__DIR__.name().equals(name)) {
            this.replaceCompileTimeProperty(CodeGenerator.this.getCurrentSource().getBase());
         } else if (CompilerConstants.__LINE__.name().equals(name)) {
            this.replaceCompileTimeProperty(CodeGenerator.this.getCurrentSource().getLine(identNode.position()));
         }

      }

      private void replaceCompileTimeProperty(Object propertyValue) {
         assert CodeGenerator.this.method.peekType().isObject();

         if (!(propertyValue instanceof String) && propertyValue != null) {
            if (!(propertyValue instanceof Integer)) {
               throw new AssertionError();
            }

            CodeGenerator.this.method.load((Integer)propertyValue);
            CodeGenerator.this.method.convert(Type.OBJECT);
         } else {
            CodeGenerator.this.method.load((String)propertyValue);
         }

         CodeGenerator.this.globalReplaceLocationPropertyPlaceholder();
         this.convertOptimisticReturnValue();
      }

      private Type getOptimisticCoercedType() {
         Type optimisticType = this.expression.getType();

         assert this.resultBounds.widest.widerThan(optimisticType);

         Type narrowest = this.resultBounds.narrowest;
         if (!narrowest.isBoolean() && !narrowest.narrowerThan(optimisticType)) {
            assert !narrowest.isObject();

            return narrowest;
         } else {
            assert !optimisticType.isObject();

            return optimisticType;
         }
      }
   }

   private abstract class Store<T extends Expression> {
      protected final T assignNode;
      private final Expression target;
      private int depth;
      private IdentNode quick;

      protected Store(T assignNode, Expression target) {
         this.assignNode = assignNode;
         this.target = target;
      }

      protected Store(T assignNode) {
         this(assignNode, assignNode);
      }

      protected boolean isSelfModifying() {
         return false;
      }

      private void prologue() {
         this.target.accept(new SimpleNodeVisitor() {
            public boolean enterIdentNode(IdentNode node) {
               if (node.getSymbol().isScope()) {
                  CodeGenerator.this.method.loadCompilerConstant(CompilerConstants.SCOPE);
                  Store.this.depth = Store.this.depth + Type.SCOPE.getSlots();

                  assert Store.this.depth == 1;
               }

               return false;
            }

            private void enterBaseNode() {
               assert Store.this.target instanceof BaseNode : "error - base node " + Store.this.target + " must be instanceof BaseNode";

               BaseNode baseNode = (BaseNode)Store.this.target;
               Expression base = baseNode.getBase();
               CodeGenerator.this.loadExpressionAsObject(base);
               Store.this.depth = Store.this.depth + Type.OBJECT.getSlots();

               assert Store.this.depth == 1;

               if (Store.this.isSelfModifying()) {
                  CodeGenerator.this.method.dup();
               }

            }

            public boolean enterAccessNode(AccessNode node) {
               this.enterBaseNode();
               return false;
            }

            public boolean enterIndexNode(IndexNode node) {
               this.enterBaseNode();
               Expression index = node.getIndex();
               if (!index.getType().isNumeric()) {
                  CodeGenerator.this.loadExpressionAsObject(index);
               } else {
                  CodeGenerator.this.loadExpressionUnbounded(index);
               }

               Store.this.depth = Store.this.depth + index.getType().getSlots();
               if (Store.this.isSelfModifying()) {
                  CodeGenerator.this.method.dup(1);
               }

               return false;
            }
         });
      }

      private IdentNode quickLocalVariable(Type type) {
         String name = ((CodeGeneratorLexicalContext)CodeGenerator.this.lc).getCurrentFunction().uniqueName(CompilerConstants.QUICK_PREFIX.symbolName());
         Symbol symbol = new Symbol(name, 1088);
         symbol.setHasSlotFor(type);
         symbol.setFirstSlot(((CodeGeneratorLexicalContext)CodeGenerator.this.lc).quickSlot(type));
         IdentNode quickIdent = IdentNode.createInternalIdentifier(symbol).setType(type);
         return quickIdent;
      }

      protected void storeNonDiscard() {
         if (((CodeGeneratorLexicalContext)CodeGenerator.this.lc).popDiscardIfCurrent(this.assignNode)) {
            assert this.assignNode.isAssignment();

         } else {
            if (CodeGenerator.this.method.dup(this.depth) == null) {
               CodeGenerator.this.method.dup();
               Type quickType = CodeGenerator.this.method.peekType();
               this.quick = this.quickLocalVariable(quickType);
               Symbol quickSymbol = this.quick.getSymbol();
               CodeGenerator.this.method.storeTemp(quickType, quickSymbol.getFirstSlot());
            }

         }
      }

      private void epilogue() {
         this.target.accept(new SimpleNodeVisitor() {
            protected boolean enterDefault(Node node) {
               throw new AssertionError("Unexpected node " + node + " in store epilogue");
            }

            public boolean enterIdentNode(IdentNode node) {
               Symbol symbol = node.getSymbol();

               assert symbol != null;

               if (symbol.isScope()) {
                  int flags = CodeGenerator.this.getScopeCallSiteFlags(symbol);
                  if (CodeGenerator.this.isFastScope(symbol)) {
                     CodeGenerator.this.storeFastScopeVar(symbol, flags);
                  } else {
                     CodeGenerator.this.method.dynamicSet(node.getName(), flags, false);
                  }
               } else {
                  Type storeType = Store.this.assignNode.getType();

                  assert storeType != Type.LONG;

                  if (symbol.hasSlotFor(storeType)) {
                     CodeGenerator.this.method.convert(storeType);
                  }

                  CodeGenerator.this.storeIdentWithCatchConversion(node, storeType);
               }

               return false;
            }

            public boolean enterAccessNode(AccessNode node) {
               CodeGenerator.this.method.dynamicSet(node.getProperty(), CodeGenerator.this.getCallSiteFlags(), node.isIndex());
               return false;
            }

            public boolean enterIndexNode(IndexNode node) {
               CodeGenerator.this.method.dynamicSetIndex(CodeGenerator.this.getCallSiteFlags());
               return false;
            }
         });
      }

      protected abstract void evaluate();

      void store() {
         if (this.target instanceof IdentNode) {
            CodeGenerator.this.checkTemporalDeadZone((IdentNode)this.target);
         }

         this.prologue();
         this.evaluate();
         this.storeNonDiscard();
         this.epilogue();
         if (this.quick != null) {
            CodeGenerator.this.method.load(this.quick);
         }

      }
   }

   private abstract class SelfModifyingStore<T extends Expression> extends CodeGenerator.Store<T> {
      protected SelfModifyingStore(T assignNode, Expression target) {
         super(assignNode, target);
      }

      protected boolean isSelfModifying() {
         return true;
      }
   }

   private abstract class BinaryArith {
      private BinaryArith() {
      }

      protected abstract void op(int var1);

      protected void evaluate(final BinaryNode node, CodeGenerator.TypeBounds resultBounds) {
         final CodeGenerator.TypeBounds numericBounds = resultBounds.booleanToInt().objectToNumber();
         (new CodeGenerator.OptimisticOperation(node, numericBounds) {
            void loadStack() {
               boolean forceConversionSeparation = false;
               CodeGenerator.TypeBounds operandBounds;
               if (numericBounds.narrowest == Type.NUMBER) {
                  assert numericBounds.widest == Type.NUMBER;

                  operandBounds = numericBounds;
               } else {
                  boolean isOptimistic = UnwarrantedOptimismException.isValid(this.getProgramPoint());
                  if (!isOptimistic && !node.isTokenType(TokenType.DIV) && !node.isTokenType(TokenType.MOD)) {
                     operandBounds = new CodeGenerator.TypeBounds(Type.narrowest(node.getWidestOperandType(), numericBounds.widest), Type.NUMBER);
                     forceConversionSeparation = true;
                  } else {
                     operandBounds = new CodeGenerator.TypeBounds(node.getType(), Type.NUMBER);
                  }
               }

               CodeGenerator.this.loadBinaryOperands(node.lhs(), node.rhs(), operandBounds, false, forceConversionSeparation);
            }

            void consumeStack() {
               BinaryArith.this.op(this.getProgramPoint());
            }
         }).emit();
      }

      // $FF: synthetic method
      BinaryArith(Object x1) {
         this();
      }
   }

   private abstract class BinarySelfAssignment extends CodeGenerator.SelfModifyingStore<BinaryNode> {
      BinarySelfAssignment(BinaryNode node) {
         super(node, node.lhs());
      }

      protected abstract void op();

      protected void evaluate() {
         CodeGenerator.this.loadBinaryOperands(((BinaryNode)this.assignNode).lhs(), ((BinaryNode)this.assignNode).rhs(), CodeGenerator.TypeBounds.UNBOUNDED.notWiderThan(((BinaryNode)this.assignNode).getWidestOperandType()), true, false);
         this.op();
      }
   }

   private abstract class BinaryOptimisticSelfAssignment extends CodeGenerator.SelfModifyingStore<BinaryNode> {
      BinaryOptimisticSelfAssignment(BinaryNode node) {
         super(node, node.lhs());
      }

      protected abstract void op(CodeGenerator.OptimisticOperation var1);

      protected void evaluate() {
         final Expression lhs = ((BinaryNode)this.assignNode).lhs();
         final Expression rhs = ((BinaryNode)this.assignNode).rhs();
         final Type widestOperationType = ((BinaryNode)this.assignNode).getWidestOperationType();
         final CodeGenerator.TypeBounds bounds = new CodeGenerator.TypeBounds(((BinaryNode)this.assignNode).getType(), widestOperationType);
         (new CodeGenerator.OptimisticOperation((Optimistic)this.assignNode, bounds) {
            void loadStack() {
               boolean forceConversionSeparation;
               if (!UnwarrantedOptimismException.isValid(this.getProgramPoint()) && widestOperationType != Type.NUMBER) {
                  Type operandType = Type.widest(CodeGenerator.booleanToInt(CodeGenerator.objectToNumber(lhs.getType())), CodeGenerator.booleanToInt(CodeGenerator.objectToNumber(rhs.getType())));
                  forceConversionSeparation = operandType.narrowerThan(widestOperationType);
               } else {
                  forceConversionSeparation = false;
               }

               CodeGenerator.this.loadBinaryOperands(lhs, rhs, bounds, true, forceConversionSeparation);
            }

            void consumeStack() {
               BinaryOptimisticSelfAssignment.this.op(this);
            }
         }).emit(CodeGenerator.getOptimisticIgnoreCountForSelfModifyingExpression(lhs));
         CodeGenerator.this.method.convert(((BinaryNode)this.assignNode).getType());
      }
   }

   private static final class TypeBounds {
      final Type narrowest;
      final Type widest;
      static final CodeGenerator.TypeBounds UNBOUNDED;
      static final CodeGenerator.TypeBounds INT;
      static final CodeGenerator.TypeBounds NUMBER;
      static final CodeGenerator.TypeBounds OBJECT;
      static final CodeGenerator.TypeBounds BOOLEAN;

      static CodeGenerator.TypeBounds exact(Type type) {
         return new CodeGenerator.TypeBounds(type, type);
      }

      TypeBounds(Type narrowest, Type widest) {
         assert widest != null && widest != Type.UNDEFINED && widest != Type.UNKNOWN : widest;

         assert narrowest != null && narrowest != Type.UNDEFINED : narrowest;

         assert !narrowest.widerThan(widest) : narrowest + " wider than " + widest;

         assert !widest.narrowerThan(narrowest);

         this.narrowest = Type.generic(narrowest);
         this.widest = Type.generic(widest);
      }

      CodeGenerator.TypeBounds notNarrowerThan(Type type) {
         return this.maybeNew(Type.narrowest(Type.widest(this.narrowest, type), this.widest), this.widest);
      }

      CodeGenerator.TypeBounds notWiderThan(Type type) {
         return this.maybeNew(Type.narrowest(this.narrowest, type), Type.narrowest(this.widest, type));
      }

      boolean canBeNarrowerThan(Type type) {
         return this.narrowest.narrowerThan(type);
      }

      CodeGenerator.TypeBounds maybeNew(Type newNarrowest, Type newWidest) {
         return newNarrowest == this.narrowest && newWidest == this.widest ? this : new CodeGenerator.TypeBounds(newNarrowest, newWidest);
      }

      CodeGenerator.TypeBounds booleanToInt() {
         return this.maybeNew(CodeGenerator.booleanToInt(this.narrowest), CodeGenerator.booleanToInt(this.widest));
      }

      CodeGenerator.TypeBounds objectToNumber() {
         return this.maybeNew(CodeGenerator.objectToNumber(this.narrowest), CodeGenerator.objectToNumber(this.widest));
      }

      Type within(Type type) {
         if (type.narrowerThan(this.narrowest)) {
            return this.narrowest;
         } else {
            return type.widerThan(this.widest) ? this.widest : type;
         }
      }

      public String toString() {
         return "[" + this.narrowest + ", " + this.widest + "]";
      }

      static {
         UNBOUNDED = new CodeGenerator.TypeBounds(Type.UNKNOWN, Type.OBJECT);
         INT = exact(Type.INT);
         NUMBER = exact(Type.NUMBER);
         OBJECT = exact(Type.OBJECT);
         BOOLEAN = exact(Type.BOOLEAN);
      }
   }

   private class LoadFastScopeVar extends CodeGenerator.LoadScopeVar {
      LoadFastScopeVar(IdentNode identNode, CodeGenerator.TypeBounds resultBounds, int flags) {
         super(identNode, resultBounds, flags);
      }

      void getProto() {
         CodeGenerator.this.loadFastScopeProto(this.identNode.getSymbol(), false);
      }
   }

   private class LoadScopeVar extends CodeGenerator.OptimisticOperation {
      final IdentNode identNode;
      private final int flags;

      LoadScopeVar(IdentNode identNode, CodeGenerator.TypeBounds resultBounds, int flags) {
         super(identNode, resultBounds);
         this.identNode = identNode;
         this.flags = flags;
      }

      void loadStack() {
         CodeGenerator.this.method.loadCompilerConstant(CompilerConstants.SCOPE);
         this.getProto();
      }

      void getProto() {
      }

      void consumeStack() {
         if (this.identNode.isCompileTimePropertyName()) {
            CodeGenerator.this.method.dynamicGet(Type.OBJECT, this.identNode.getSymbol().getName(), this.flags, this.identNode.isFunction(), false);
            this.replaceCompileTimeProperty();
         } else {
            this.dynamicGet(this.identNode.getSymbol().getName(), this.flags, this.identNode.isFunction(), false);
         }

      }
   }
}
