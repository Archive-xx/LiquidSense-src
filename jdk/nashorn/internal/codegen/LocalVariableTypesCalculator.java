package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.BinaryNode;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.BreakNode;
import jdk.nashorn.internal.ir.BreakableNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.CaseNode;
import jdk.nashorn.internal.ir.CatchNode;
import jdk.nashorn.internal.ir.ContinueNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.ExpressionStatement;
import jdk.nashorn.internal.ir.ForNode;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.GetSplitState;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IfNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.JoinPredecessor;
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
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.ReturnNode;
import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.ir.SplitReturn;
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
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;

final class LocalVariableTypesCalculator extends SimpleNodeVisitor {
   private static final Map<Type, LocalVariableTypesCalculator.LvarType> TO_LVAR_TYPE = new IdentityHashMap();
   private final Compiler compiler;
   private final Map<Label, LocalVariableTypesCalculator.JumpTarget> jumpTargets = new IdentityHashMap();
   private Map<Symbol, LocalVariableTypesCalculator.LvarType> localVariableTypes = Collections.emptyMap();
   private final Set<Symbol> invalidatedSymbols = new HashSet();
   private final Deque<LocalVariableTypesCalculator.LvarType> typeStack = new ArrayDeque();
   private boolean reachable = true;
   private Type returnType;
   private ReturnNode syntheticReturn;
   private boolean alreadyEnteredTopLevelFunction;
   private final Map<JoinPredecessor, LocalVariableConversion> localVariableConversions;
   private final Map<IdentNode, LocalVariableTypesCalculator.LvarType> identifierLvarTypes;
   private final Map<Symbol, LocalVariableTypesCalculator.SymbolConversions> symbolConversions;
   private final Deque<Label> catchLabels;

   private static HashMap<Symbol, LocalVariableTypesCalculator.LvarType> cloneMap(Map<Symbol, LocalVariableTypesCalculator.LvarType> map) {
      return (HashMap)((HashMap)map).clone();
   }

   private LocalVariableConversion createConversion(Symbol symbol, LocalVariableTypesCalculator.LvarType branchLvarType, Map<Symbol, LocalVariableTypesCalculator.LvarType> joinLvarTypes, LocalVariableConversion next) {
      if (this.invalidatedSymbols.contains(symbol)) {
         return next;
      } else {
         LocalVariableTypesCalculator.LvarType targetType = (LocalVariableTypesCalculator.LvarType)joinLvarTypes.get(symbol);

         assert targetType != null;

         if (targetType == branchLvarType) {
            return next;
         } else {
            this.symbolIsConverted(symbol, branchLvarType, targetType);
            return new LocalVariableConversion(symbol, branchLvarType.type, targetType.type, next);
         }
      }
   }

   private Map<Symbol, LocalVariableTypesCalculator.LvarType> getUnionTypes(Map<Symbol, LocalVariableTypesCalculator.LvarType> types1, Map<Symbol, LocalVariableTypesCalculator.LvarType> types2) {
      if (types1 != types2 && !types1.isEmpty()) {
         if (types2.isEmpty()) {
            return types1;
         } else {
            Set<Symbol> commonSymbols = new HashSet(types1.keySet());
            commonSymbols.retainAll(types2.keySet());
            int commonSize = commonSymbols.size();
            int types1Size = types1.size();
            int types2Size = types2.size();
            if (commonSize == types1Size && commonSize == types2Size) {
               boolean matches1 = true;
               boolean matches2 = true;
               Map<Symbol, LocalVariableTypesCalculator.LvarType> union = null;
               Iterator var18 = commonSymbols.iterator();

               while(var18.hasNext()) {
                  Symbol symbol = (Symbol)var18.next();
                  LocalVariableTypesCalculator.LvarType type1 = (LocalVariableTypesCalculator.LvarType)types1.get(symbol);
                  LocalVariableTypesCalculator.LvarType type2 = (LocalVariableTypesCalculator.LvarType)types2.get(symbol);
                  LocalVariableTypesCalculator.LvarType widest = widestLvarType(type1, type2);
                  if (widest != type1 && matches1) {
                     matches1 = false;
                     if (!matches2) {
                        union = cloneMap(types1);
                     }
                  }

                  if (widest != type2 && matches2) {
                     matches2 = false;
                     if (!matches1) {
                        union = cloneMap(types2);
                     }
                  }

                  if (!matches1 && !matches2) {
                     assert union != null;

                     union.put(symbol, widest);
                  }
               }

               return (Map)(matches1 ? types1 : (matches2 ? types2 : union));
            } else {
               HashMap union;
               if (types1Size > types2Size) {
                  union = cloneMap(types1);
                  union.putAll(types2);
               } else {
                  union = cloneMap(types2);
                  union.putAll(types1);
               }

               Iterator var8 = commonSymbols.iterator();

               while(var8.hasNext()) {
                  Symbol symbol = (Symbol)var8.next();
                  LocalVariableTypesCalculator.LvarType type1 = (LocalVariableTypesCalculator.LvarType)types1.get(symbol);
                  LocalVariableTypesCalculator.LvarType type2 = (LocalVariableTypesCalculator.LvarType)types2.get(symbol);
                  union.put(symbol, widestLvarType(type1, type2));
               }

               union.keySet().removeAll(this.invalidatedSymbols);
               return union;
            }
         }
      } else {
         return types2;
      }
   }

   private static void symbolIsUsed(Symbol symbol, LocalVariableTypesCalculator.LvarType type) {
      if (type != LocalVariableTypesCalculator.LvarType.UNDEFINED) {
         symbol.setHasSlotFor(type.type);
      }

   }

   private void symbolIsConverted(Symbol symbol, LocalVariableTypesCalculator.LvarType from, LocalVariableTypesCalculator.LvarType to) {
      LocalVariableTypesCalculator.SymbolConversions conversions = (LocalVariableTypesCalculator.SymbolConversions)this.symbolConversions.get(symbol);
      if (conversions == null) {
         conversions = new LocalVariableTypesCalculator.SymbolConversions();
         this.symbolConversions.put(symbol, conversions);
      }

      conversions.recordConversion(from, to);
   }

   private static LocalVariableTypesCalculator.LvarType toLvarType(Type type) {
      assert type != null;

      LocalVariableTypesCalculator.LvarType lvarType = (LocalVariableTypesCalculator.LvarType)TO_LVAR_TYPE.get(type);
      if (lvarType != null) {
         return lvarType;
      } else {
         assert type.isObject() : "Unsupported primitive type: " + type;

         return LocalVariableTypesCalculator.LvarType.OBJECT;
      }
   }

   private static LocalVariableTypesCalculator.LvarType widestLvarType(LocalVariableTypesCalculator.LvarType t1, LocalVariableTypesCalculator.LvarType t2) {
      if (t1 == t2) {
         return t1;
      } else {
         return t1.ordinal() >= LocalVariableTypesCalculator.LvarType.INT.ordinal() && t2.ordinal() >= LocalVariableTypesCalculator.LvarType.INT.ordinal() ? LocalVariableTypesCalculator.LvarType.values()[Math.max(t1.ordinal(), t2.ordinal())] : LocalVariableTypesCalculator.LvarType.OBJECT;
      }
   }

   LocalVariableTypesCalculator(Compiler compiler) {
      this.returnType = Type.UNKNOWN;
      this.localVariableConversions = new IdentityHashMap();
      this.identifierLvarTypes = new IdentityHashMap();
      this.symbolConversions = new IdentityHashMap();
      this.catchLabels = new ArrayDeque();
      this.compiler = compiler;
   }

   private LocalVariableTypesCalculator.JumpTarget createJumpTarget(Label label) {
      assert !this.jumpTargets.containsKey(label);

      LocalVariableTypesCalculator.JumpTarget jumpTarget = new LocalVariableTypesCalculator.JumpTarget();
      this.jumpTargets.put(label, jumpTarget);
      return jumpTarget;
   }

   private void doesNotContinueSequentially() {
      this.reachable = false;
      this.localVariableTypes = Collections.emptyMap();
      this.assertTypeStackIsEmpty();
   }

   private boolean pushExpressionType(Expression expr) {
      this.typeStack.push(toLvarType(expr.getType()));
      return false;
   }

   public boolean enterAccessNode(AccessNode accessNode) {
      this.visitExpression(accessNode.getBase());
      return this.pushExpressionType(accessNode);
   }

   public boolean enterBinaryNode(BinaryNode binaryNode) {
      Expression lhs = binaryNode.lhs();
      LocalVariableTypesCalculator.LvarType lhsType;
      if (lhs instanceof IdentNode && binaryNode.isTokenType(TokenType.ASSIGN)) {
         lhsType = LocalVariableTypesCalculator.LvarType.UNDEFINED;
      } else {
         lhsType = this.visitExpression(lhs);
      }

      boolean isLogical = binaryNode.isLogical();
      Label joinLabel = isLogical ? new Label("") : null;
      if (isLogical) {
         this.jumpToLabel((JoinPredecessor)lhs, joinLabel);
      }

      Expression rhs = binaryNode.rhs();
      LocalVariableTypesCalculator.LvarType rhsType = this.visitExpression(rhs);
      if (isLogical) {
         this.jumpToLabel((JoinPredecessor)rhs, joinLabel);
      }

      this.joinOnLabel(joinLabel);
      LocalVariableTypesCalculator.LvarType type = toLvarType(binaryNode.setOperands(lhsType.typeExpression, rhsType.typeExpression).getType());
      if (binaryNode.isAssignment() && lhs instanceof IdentNode) {
         if (binaryNode.isSelfModifying()) {
            this.onSelfAssignment((IdentNode)lhs, type);
         } else {
            this.onAssignment((IdentNode)lhs, type);
         }
      }

      this.typeStack.push(type);
      return false;
   }

   public boolean enterBlock(Block block) {
      boolean cloned = false;
      Iterator var3 = block.getSymbols().iterator();

      while(var3.hasNext()) {
         Symbol symbol = (Symbol)var3.next();
         if (symbol.isBytecodeLocal()) {
            if (this.getLocalVariableTypeOrNull(symbol) == null) {
               if (!cloned) {
                  this.cloneOrNewLocalVariableTypes();
                  cloned = true;
               }

               this.localVariableTypes.put(symbol, LocalVariableTypesCalculator.LvarType.UNDEFINED);
            }

            this.invalidatedSymbols.remove(symbol);
         }
      }

      return true;
   }

   public boolean enterBreakNode(BreakNode breakNode) {
      return this.enterJumpStatement(breakNode);
   }

   public boolean enterCallNode(CallNode callNode) {
      this.visitExpression(callNode.getFunction());
      this.visitExpressions(callNode.getArgs());
      CallNode.EvalArgs evalArgs = callNode.getEvalArgs();
      if (evalArgs != null) {
         this.visitExpressions(evalArgs.getArgs());
      }

      return this.pushExpressionType(callNode);
   }

   public boolean enterContinueNode(ContinueNode continueNode) {
      return this.enterJumpStatement(continueNode);
   }

   private boolean enterJumpStatement(JumpStatement jump) {
      if (!this.reachable) {
         return false;
      } else {
         this.assertTypeStackIsEmpty();
         this.jumpToLabel(jump, jump.getTargetLabel(this.lc), this.getBreakTargetTypes(jump.getPopScopeLimit(this.lc)));
         this.doesNotContinueSequentially();
         return false;
      }
   }

   protected boolean enterDefault(Node node) {
      return this.reachable;
   }

   private void enterDoWhileLoop(WhileNode loopNode) {
      this.assertTypeStackIsEmpty();
      JoinPredecessorExpression test = loopNode.getTest();
      Block body = loopNode.getBody();
      Label continueLabel = loopNode.getContinueLabel();
      Label breakLabel = loopNode.getBreakLabel();
      Map<Symbol, LocalVariableTypesCalculator.LvarType> beforeLoopTypes = this.localVariableTypes;
      Label repeatLabel = new Label("");

      while(true) {
         this.jumpToLabel(loopNode, repeatLabel, beforeLoopTypes);
         Map<Symbol, LocalVariableTypesCalculator.LvarType> beforeRepeatTypes = this.localVariableTypes;
         body.accept(this);
         if (this.reachable) {
            this.jumpToLabel(body, continueLabel);
         }

         this.joinOnLabel(continueLabel);
         if (!this.reachable) {
            break;
         }

         this.visitExpressionOnEmptyStack(test);
         this.jumpToLabel(test, breakLabel);
         if (Expression.isAlwaysFalse(test)) {
            break;
         }

         this.jumpToLabel(test, repeatLabel);
         this.joinOnLabel(repeatLabel);
         if (this.localVariableTypes.equals(beforeRepeatTypes)) {
            break;
         }

         this.resetJoinPoint(continueLabel);
         this.resetJoinPoint(breakLabel);
         this.resetJoinPoint(repeatLabel);
      }

      if (Expression.isAlwaysTrue(test)) {
         this.doesNotContinueSequentially();
      }

      this.leaveBreakable(loopNode);
   }

   public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
      if (this.reachable) {
         this.visitExpressionOnEmptyStack(expressionStatement.getExpression());
      }

      return false;
   }

   private void assertTypeStackIsEmpty() {
      assert this.typeStack.isEmpty();

   }

   protected Node leaveDefault(Node node) {
      assert !(node instanceof Expression);

      assert !(node instanceof Statement) || this.typeStack.isEmpty();

      return node;
   }

   private LocalVariableTypesCalculator.LvarType visitExpressionOnEmptyStack(Expression expr) {
      this.assertTypeStackIsEmpty();
      return this.visitExpression(expr);
   }

   private LocalVariableTypesCalculator.LvarType visitExpression(Expression expr) {
      int stackSize = this.typeStack.size();
      expr.accept(this);

      assert this.typeStack.size() == stackSize + 1;

      return (LocalVariableTypesCalculator.LvarType)this.typeStack.pop();
   }

   private void visitExpressions(List<Expression> exprs) {
      Iterator var2 = exprs.iterator();

      while(var2.hasNext()) {
         Expression expr = (Expression)var2.next();
         if (expr != null) {
            this.visitExpression(expr);
         }
      }

   }

   public boolean enterForNode(ForNode forNode) {
      if (!this.reachable) {
         return false;
      } else {
         Expression init = forNode.getInit();
         if (forNode.isForIn()) {
            JoinPredecessorExpression iterable = forNode.getModify();
            this.visitExpression(iterable);
            this.enterTestFirstLoop(forNode, (JoinPredecessorExpression)null, init, !this.compiler.useOptimisticTypes() || !forNode.isForEach() && this.compiler.hasStringPropertyIterator(iterable.getExpression()));
         } else {
            if (init != null) {
               this.visitExpressionOnEmptyStack(init);
            }

            this.enterTestFirstLoop(forNode, forNode.getModify(), (Expression)null, false);
         }

         this.assertTypeStackIsEmpty();
         return false;
      }
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      if (this.alreadyEnteredTopLevelFunction) {
         this.typeStack.push(LocalVariableTypesCalculator.LvarType.OBJECT);
         return false;
      } else {
         int pos = 0;
         if (!functionNode.isVarArg()) {
            for(Iterator var3 = functionNode.getParameters().iterator(); var3.hasNext(); ++pos) {
               IdentNode param = (IdentNode)var3.next();
               Symbol symbol = param.getSymbol();

               assert symbol.hasSlot();

               Type callSiteParamType = this.compiler.getParamType(functionNode, pos);
               LocalVariableTypesCalculator.LvarType paramType = callSiteParamType == null ? LocalVariableTypesCalculator.LvarType.OBJECT : toLvarType(callSiteParamType);
               this.setType(symbol, paramType);
               this.symbolIsUsed(symbol);
               this.setIdentifierLvarType(param, paramType);
            }
         }

         this.setCompilerConstantAsObject(functionNode, CompilerConstants.THIS);
         if (functionNode.hasScopeBlock() || functionNode.needsParentScope()) {
            this.setCompilerConstantAsObject(functionNode, CompilerConstants.SCOPE);
         }

         if (functionNode.needsCallee()) {
            this.setCompilerConstantAsObject(functionNode, CompilerConstants.CALLEE);
         }

         if (functionNode.needsArguments()) {
            this.setCompilerConstantAsObject(functionNode, CompilerConstants.ARGUMENTS);
         }

         this.alreadyEnteredTopLevelFunction = true;
         return true;
      }
   }

   public boolean enterGetSplitState(GetSplitState getSplitState) {
      return this.pushExpressionType(getSplitState);
   }

   public boolean enterIdentNode(IdentNode identNode) {
      Symbol symbol = identNode.getSymbol();
      if (symbol.isBytecodeLocal()) {
         this.symbolIsUsed(symbol);
         LocalVariableTypesCalculator.LvarType type = this.getLocalVariableType(symbol);
         this.setIdentifierLvarType(identNode, type);
         this.typeStack.push(type);
      } else {
         this.pushExpressionType(identNode);
      }

      return false;
   }

   public boolean enterIfNode(IfNode ifNode) {
      this.processIfNode(ifNode);
      return false;
   }

   private void processIfNode(IfNode ifNode) {
      if (this.reachable) {
         Expression test = ifNode.getTest();
         Block pass = ifNode.getPass();
         Block fail = ifNode.getFail();
         this.visitExpressionOnEmptyStack(test);
         boolean isTestAlwaysTrue = Expression.isAlwaysTrue(test);
         Map passLvarTypes;
         boolean reachableFromPass;
         Map failLvarTypes;
         if (Expression.isAlwaysFalse(test)) {
            passLvarTypes = null;
            reachableFromPass = false;
         } else {
            failLvarTypes = this.localVariableTypes;
            pass.accept(this);
            this.assertTypeStackIsEmpty();
            if (isTestAlwaysTrue) {
               return;
            }

            passLvarTypes = this.localVariableTypes;
            reachableFromPass = this.reachable;
            this.localVariableTypes = failLvarTypes;
            this.reachable = true;
         }

         assert !isTestAlwaysTrue;

         if (fail != null) {
            fail.accept(this);
            this.assertTypeStackIsEmpty();
         }

         if (this.reachable) {
            if (reachableFromPass) {
               failLvarTypes = this.localVariableTypes;
               this.localVariableTypes = this.getUnionTypes(passLvarTypes, failLvarTypes);
               this.setConversion(pass, passLvarTypes, this.localVariableTypes);
               this.setConversion((JoinPredecessor)(fail != null ? fail : ifNode), failLvarTypes, this.localVariableTypes);
            }
         } else if (reachableFromPass) {
            assert passLvarTypes != null;

            this.localVariableTypes = passLvarTypes;
            this.reachable = true;
         }

      }
   }

   public boolean enterIndexNode(IndexNode indexNode) {
      this.visitExpression(indexNode.getBase());
      this.visitExpression(indexNode.getIndex());
      return this.pushExpressionType(indexNode);
   }

   public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinExpr) {
      Expression expr = joinExpr.getExpression();
      if (expr != null) {
         expr.accept(this);
      } else {
         this.typeStack.push(LocalVariableTypesCalculator.LvarType.UNDEFINED);
      }

      return false;
   }

   public boolean enterJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
      return this.enterJumpStatement(jumpToInlinedFinally);
   }

   public boolean enterLiteralNode(LiteralNode<?> literalNode) {
      if (literalNode instanceof LiteralNode.ArrayLiteralNode) {
         List<Expression> expressions = ((LiteralNode.ArrayLiteralNode)literalNode).getElementExpressions();
         if (expressions != null) {
            this.visitExpressions(expressions);
         }
      }

      this.pushExpressionType(literalNode);
      return false;
   }

   public boolean enterObjectNode(ObjectNode objectNode) {
      Iterator var2 = objectNode.getElements().iterator();

      while(var2.hasNext()) {
         PropertyNode propertyNode = (PropertyNode)var2.next();
         Expression value = propertyNode.getValue();
         if (value != null) {
            this.visitExpression(value);
         }
      }

      return this.pushExpressionType(objectNode);
   }

   public boolean enterPropertyNode(PropertyNode propertyNode) {
      throw new AssertionError();
   }

   public boolean enterReturnNode(ReturnNode returnNode) {
      if (!this.reachable) {
         return false;
      } else {
         Expression returnExpr = returnNode.getExpression();
         Type returnExprType;
         if (returnExpr != null) {
            returnExprType = this.visitExpressionOnEmptyStack(returnExpr).type;
         } else {
            this.assertTypeStackIsEmpty();
            returnExprType = Type.UNDEFINED;
         }

         this.returnType = Type.widestReturnType(this.returnType, returnExprType);
         this.doesNotContinueSequentially();
         return false;
      }
   }

   public boolean enterRuntimeNode(RuntimeNode runtimeNode) {
      this.visitExpressions(runtimeNode.getArgs());
      return this.pushExpressionType(runtimeNode);
   }

   public boolean enterSplitReturn(SplitReturn splitReturn) {
      this.doesNotContinueSequentially();
      return false;
   }

   public boolean enterSwitchNode(SwitchNode switchNode) {
      if (!this.reachable) {
         return false;
      } else {
         this.visitExpressionOnEmptyStack(switchNode.getExpression());
         List<CaseNode> cases = switchNode.getCases();
         if (cases.isEmpty()) {
            return false;
         } else {
            boolean isInteger = switchNode.isUniqueInteger();
            Label breakLabel = switchNode.getBreakLabel();
            boolean hasDefault = switchNode.getDefaultCase() != null;
            boolean tagUsed = false;

            CaseNode caseNode;
            for(Iterator var7 = cases.iterator(); var7.hasNext(); this.jumpToLabel(caseNode, caseNode.getBody().getEntryLabel())) {
               caseNode = (CaseNode)var7.next();
               Expression test = caseNode.getTest();
               if (!isInteger && test != null) {
                  this.visitExpressionOnEmptyStack(test);
                  if (!tagUsed) {
                     symbolIsUsed(switchNode.getTag(), LocalVariableTypesCalculator.LvarType.OBJECT);
                     tagUsed = true;
                  }
               }
            }

            if (!hasDefault) {
               this.jumpToLabel(switchNode, breakLabel);
            }

            this.doesNotContinueSequentially();
            Block previousBlock = null;

            Block body;
            for(Iterator var13 = cases.iterator(); var13.hasNext(); previousBlock = body) {
               CaseNode caseNode = (CaseNode)var13.next();
               body = caseNode.getBody();
               Label entryLabel = body.getEntryLabel();
               if (previousBlock != null && this.reachable) {
                  this.jumpToLabel(previousBlock, entryLabel);
               }

               this.joinOnLabel(entryLabel);

               assert this.reachable;

               body.accept(this);
            }

            if (previousBlock != null && this.reachable) {
               this.jumpToLabel(previousBlock, breakLabel);
            }

            this.leaveBreakable(switchNode);
            return false;
         }
      }
   }

   public boolean enterTernaryNode(TernaryNode ternaryNode) {
      Expression test = ternaryNode.getTest();
      Expression trueExpr = ternaryNode.getTrueExpression();
      Expression falseExpr = ternaryNode.getFalseExpression();
      this.visitExpression(test);
      Map<Symbol, LocalVariableTypesCalculator.LvarType> testExitLvarTypes = this.localVariableTypes;
      LocalVariableTypesCalculator.LvarType trueType;
      if (!Expression.isAlwaysFalse(test)) {
         trueType = this.visitExpression(trueExpr);
      } else {
         trueType = null;
      }

      Map<Symbol, LocalVariableTypesCalculator.LvarType> trueExitLvarTypes = this.localVariableTypes;
      this.localVariableTypes = testExitLvarTypes;
      LocalVariableTypesCalculator.LvarType falseType;
      if (!Expression.isAlwaysTrue(test)) {
         falseType = this.visitExpression(falseExpr);
      } else {
         falseType = null;
      }

      Map<Symbol, LocalVariableTypesCalculator.LvarType> falseExitLvarTypes = this.localVariableTypes;
      this.localVariableTypes = this.getUnionTypes(trueExitLvarTypes, falseExitLvarTypes);
      this.setConversion((JoinPredecessor)trueExpr, trueExitLvarTypes, this.localVariableTypes);
      this.setConversion((JoinPredecessor)falseExpr, falseExitLvarTypes, this.localVariableTypes);
      this.typeStack.push(trueType != null ? (falseType != null ? widestLvarType(trueType, falseType) : trueType) : (LocalVariableTypesCalculator.LvarType)assertNotNull(falseType));
      return false;
   }

   private static <T> T assertNotNull(T t) {
      assert t != null;

      return t;
   }

   private void enterTestFirstLoop(LoopNode loopNode, JoinPredecessorExpression modify, Expression iteratorValues, boolean iteratorValuesAreObject) {
      JoinPredecessorExpression test = loopNode.getTest();
      if (Expression.isAlwaysFalse(test)) {
         this.visitExpressionOnEmptyStack(test);
      } else {
         Label continueLabel = loopNode.getContinueLabel();
         Label breakLabel = loopNode.getBreakLabel();
         Label repeatLabel = modify == null ? continueLabel : new Label("");
         Map beforeLoopTypes = this.localVariableTypes;

         while(true) {
            this.jumpToLabel(loopNode, repeatLabel, beforeLoopTypes);
            Map<Symbol, LocalVariableTypesCalculator.LvarType> beforeRepeatTypes = this.localVariableTypes;
            if (test != null) {
               this.visitExpressionOnEmptyStack(test);
            }

            if (!Expression.isAlwaysTrue(test)) {
               this.jumpToLabel(test, breakLabel);
            }

            if (iteratorValues instanceof IdentNode) {
               IdentNode ident = (IdentNode)iteratorValues;
               this.onAssignment(ident, iteratorValuesAreObject ? LocalVariableTypesCalculator.LvarType.OBJECT : toLvarType(this.compiler.getOptimisticType(ident)));
            }

            Block body = loopNode.getBody();
            body.accept(this);
            if (this.reachable) {
               this.jumpToLabel(body, continueLabel);
            }

            this.joinOnLabel(continueLabel);
            if (!this.reachable) {
               break;
            }

            if (modify != null) {
               this.visitExpressionOnEmptyStack(modify);
               this.jumpToLabel(modify, repeatLabel);
               this.joinOnLabel(repeatLabel);
            }

            if (this.localVariableTypes.equals(beforeRepeatTypes)) {
               break;
            }

            this.resetJoinPoint(continueLabel);
            this.resetJoinPoint(breakLabel);
            this.resetJoinPoint(repeatLabel);
         }

         if (Expression.isAlwaysTrue(test) && iteratorValues == null) {
            this.doesNotContinueSequentially();
         }

         this.leaveBreakable(loopNode);
      }
   }

   public boolean enterThrowNode(ThrowNode throwNode) {
      if (!this.reachable) {
         return false;
      } else {
         this.visitExpressionOnEmptyStack(throwNode.getExpression());
         this.jumpToCatchBlock(throwNode);
         this.doesNotContinueSequentially();
         return false;
      }
   }

   public boolean enterTryNode(TryNode tryNode) {
      if (!this.reachable) {
         return false;
      } else {
         Label catchLabel = new Label("");
         this.catchLabels.push(catchLabel);
         this.jumpToLabel(tryNode, catchLabel);
         Block body = tryNode.getBody();
         body.accept(this);
         this.catchLabels.pop();
         Label endLabel = new Label("");
         boolean canExit = false;
         if (this.reachable) {
            this.jumpToLabel(body, endLabel);
            canExit = true;
         }

         this.doesNotContinueSequentially();
         Iterator var6 = tryNode.getInlinedFinallies().iterator();

         while(var6.hasNext()) {
            Block inlinedFinally = (Block)var6.next();
            Block finallyBody = TryNode.getLabelledInlinedFinallyBlock(inlinedFinally);
            this.joinOnLabel(finallyBody.getEntryLabel());
            if (this.reachable) {
               finallyBody.accept(this);

               assert !this.reachable;
            }
         }

         this.joinOnLabel(catchLabel);

         Map afterConditionTypes;
         for(var6 = tryNode.getCatches().iterator(); var6.hasNext(); this.localVariableTypes = afterConditionTypes) {
            CatchNode catchNode = (CatchNode)var6.next();
            IdentNode exception = catchNode.getException();
            this.onAssignment(exception, LocalVariableTypesCalculator.LvarType.OBJECT);
            Expression condition = catchNode.getExceptionCondition();
            if (condition != null) {
               this.visitExpression(condition);
            }

            afterConditionTypes = this.localVariableTypes;
            Block catchBody = catchNode.getBody();
            this.reachable = true;
            catchBody.accept(this);
            if (this.reachable) {
               this.jumpToLabel(catchBody, endLabel);
               canExit = true;
            }
         }

         this.doesNotContinueSequentially();
         if (canExit) {
            this.joinOnLabel(endLabel);
         }

         return false;
      }
   }

   public boolean enterUnaryNode(UnaryNode unaryNode) {
      Expression expr = unaryNode.getExpression();
      LocalVariableTypesCalculator.LvarType unaryType = toLvarType(unaryNode.setExpression(this.visitExpression(expr).typeExpression).getType());
      if (unaryNode.isSelfModifying() && expr instanceof IdentNode) {
         this.onSelfAssignment((IdentNode)expr, unaryType);
      }

      this.typeStack.push(unaryType);
      return false;
   }

   public boolean enterVarNode(VarNode varNode) {
      if (!this.reachable) {
         return false;
      } else {
         Expression init = varNode.getInit();
         if (init != null) {
            this.onAssignment(varNode.getName(), this.visitExpression(init));
         }

         return false;
      }
   }

   public boolean enterWhileNode(WhileNode whileNode) {
      if (!this.reachable) {
         return false;
      } else {
         if (whileNode.isDoWhile()) {
            this.enterDoWhileLoop(whileNode);
         } else {
            this.enterTestFirstLoop(whileNode, (JoinPredecessorExpression)null, (Expression)null, false);
         }

         return false;
      }
   }

   public boolean enterWithNode(WithNode withNode) {
      if (this.reachable) {
         this.visitExpression(withNode.getExpression());
         withNode.getBody().accept(this);
      }

      return false;
   }

   private Map<Symbol, LocalVariableTypesCalculator.LvarType> getBreakTargetTypes(LexicalContextNode target) {
      Map<Symbol, LocalVariableTypesCalculator.LvarType> types = this.localVariableTypes;
      Iterator it = this.lc.getAllNodes();

      while(it.hasNext()) {
         LexicalContextNode node = (LexicalContextNode)it.next();
         if (node instanceof Block) {
            Iterator var5 = ((Block)node).getSymbols().iterator();

            while(var5.hasNext()) {
               Symbol symbol = (Symbol)var5.next();
               if (this.localVariableTypes.containsKey(symbol)) {
                  if (types == this.localVariableTypes) {
                     types = cloneMap(this.localVariableTypes);
                  }

                  ((Map)types).remove(symbol);
               }
            }
         }

         if (node == target) {
            break;
         }
      }

      return (Map)types;
   }

   private LocalVariableTypesCalculator.LvarType getLocalVariableType(Symbol symbol) {
      LocalVariableTypesCalculator.LvarType type = this.getLocalVariableTypeOrNull(symbol);

      assert type != null;

      return type;
   }

   private LocalVariableTypesCalculator.LvarType getLocalVariableTypeOrNull(Symbol symbol) {
      return (LocalVariableTypesCalculator.LvarType)this.localVariableTypes.get(symbol);
   }

   private LocalVariableTypesCalculator.JumpTarget getOrCreateJumpTarget(Label label) {
      LocalVariableTypesCalculator.JumpTarget jumpTarget = (LocalVariableTypesCalculator.JumpTarget)this.jumpTargets.get(label);
      if (jumpTarget == null) {
         jumpTarget = this.createJumpTarget(label);
      }

      return jumpTarget;
   }

   private void joinOnLabel(Label label) {
      LocalVariableTypesCalculator.JumpTarget jumpTarget = (LocalVariableTypesCalculator.JumpTarget)this.jumpTargets.remove(label);
      if (jumpTarget != null) {
         assert !jumpTarget.origins.isEmpty();

         this.reachable = true;
         this.localVariableTypes = this.getUnionTypes(jumpTarget.types, this.localVariableTypes);
         Iterator var3 = jumpTarget.origins.iterator();

         while(var3.hasNext()) {
            LocalVariableTypesCalculator.JumpOrigin jumpOrigin = (LocalVariableTypesCalculator.JumpOrigin)var3.next();
            this.setConversion(jumpOrigin.node, jumpOrigin.types, this.localVariableTypes);
         }

      }
   }

   private void jumpToCatchBlock(JoinPredecessor jumpOrigin) {
      Label currentCatchLabel = (Label)this.catchLabels.peek();
      if (currentCatchLabel != null) {
         this.jumpToLabel(jumpOrigin, currentCatchLabel);
      }

   }

   private void jumpToLabel(JoinPredecessor jumpOrigin, Label label) {
      this.jumpToLabel(jumpOrigin, label, this.localVariableTypes);
   }

   private void jumpToLabel(JoinPredecessor jumpOrigin, Label label, Map<Symbol, LocalVariableTypesCalculator.LvarType> types) {
      this.getOrCreateJumpTarget(label).addOrigin(jumpOrigin, types, this);
   }

   public Node leaveBlock(Block block) {
      if (this.lc.isFunctionBody()) {
         if (this.reachable) {
            this.createSyntheticReturn(block);

            assert !this.reachable;
         }

         this.calculateReturnType();
      }

      boolean cloned = false;
      Iterator var3 = block.getSymbols().iterator();

      while(var3.hasNext()) {
         Symbol symbol = (Symbol)var3.next();
         if (symbol.hasSlot()) {
            if (symbol.isBytecodeLocal()) {
               if (this.localVariableTypes.containsKey(symbol) && !cloned) {
                  this.localVariableTypes = cloneMap(this.localVariableTypes);
                  cloned = true;
               }

               this.invalidateSymbol(symbol);
            }

            LocalVariableTypesCalculator.SymbolConversions conversions = (LocalVariableTypesCalculator.SymbolConversions)this.symbolConversions.get(symbol);
            if (conversions != null) {
               conversions.calculateTypeLiveness(symbol);
            }

            if (symbol.slotCount() == 0) {
               symbol.setNeedsSlot(false);
            }
         }
      }

      if (this.reachable) {
         LabelNode labelNode = this.lc.getCurrentBlockLabelNode();
         if (labelNode != null) {
            this.jumpToLabel(labelNode, block.getBreakLabel());
         }
      }

      this.leaveBreakable(block);
      return block;
   }

   private void calculateReturnType() {
      if (this.returnType.isUnknown()) {
         this.returnType = Type.OBJECT;
      }

   }

   private void createSyntheticReturn(Block body) {
      FunctionNode functionNode = this.lc.getCurrentFunction();
      long token = functionNode.getToken();
      int finish = functionNode.getFinish();
      List<Statement> statements = body.getStatements();
      int lineNumber = statements.isEmpty() ? functionNode.getLineNumber() : ((Statement)statements.get(statements.size() - 1)).getLineNumber();
      IdentNode returnExpr;
      if (functionNode.isProgram()) {
         returnExpr = (new IdentNode(token, finish, CompilerConstants.RETURN.symbolName())).setSymbol(getCompilerConstantSymbol(functionNode, CompilerConstants.RETURN));
      } else {
         returnExpr = null;
      }

      this.syntheticReturn = new ReturnNode(lineNumber, token, finish, returnExpr);
      this.syntheticReturn.accept(this);
   }

   private void leaveBreakable(BreakableNode breakable) {
      this.joinOnLabel(breakable.getBreakLabel());
      this.assertTypeStackIsEmpty();
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      SimpleNodeVisitor applyChangesVisitor = new SimpleNodeVisitor() {
         private boolean inOuterFunction = true;
         private final Deque<JoinPredecessor> joinPredecessors = new ArrayDeque();

         protected boolean enterDefault(Node node) {
            if (!this.inOuterFunction) {
               return false;
            } else {
               if (node instanceof JoinPredecessor) {
                  this.joinPredecessors.push((JoinPredecessor)node);
               }

               return this.inOuterFunction;
            }
         }

         public boolean enterFunctionNode(FunctionNode fn) {
            if (LocalVariableTypesCalculator.this.compiler.isOnDemandCompilation()) {
               return false;
            } else {
               this.inOuterFunction = false;
               return true;
            }
         }

         public Node leaveBinaryNode(BinaryNode binaryNode) {
            if (binaryNode.isComparison()) {
               Expression lhs = binaryNode.lhs();
               Expression rhs = binaryNode.rhs();
               TokenType tt = binaryNode.tokenType();
               switch(tt) {
               case EQ_STRICT:
               case NE_STRICT:
                  Expression undefinedNode = LocalVariableTypesCalculator.createIsUndefined(binaryNode, lhs, rhs, tt == TokenType.EQ_STRICT ? RuntimeNode.Request.IS_UNDEFINED : RuntimeNode.Request.IS_NOT_UNDEFINED);
                  if (undefinedNode != binaryNode) {
                     return undefinedNode;
                  }

                  if (lhs.getType().isBoolean() != rhs.getType().isBoolean()) {
                     return new RuntimeNode(binaryNode);
                  }
               default:
                  if (lhs.getType().isObject() && rhs.getType().isObject()) {
                     return new RuntimeNode(binaryNode);
                  }
               }
            } else if (binaryNode.isOptimisticUndecidedType()) {
               return binaryNode.decideType();
            }

            return binaryNode;
         }

         protected Node leaveDefault(Node node) {
            if (node instanceof JoinPredecessor) {
               JoinPredecessor original = (JoinPredecessor)this.joinPredecessors.pop();

               assert original.getClass() == node.getClass() : original.getClass().getName() + "!=" + node.getClass().getName();

               JoinPredecessor newNode = this.setLocalVariableConversion(original, (JoinPredecessor)node);
               if (newNode instanceof LexicalContextNode) {
                  this.lc.replace((LexicalContextNode)node, (LexicalContextNode)newNode);
               }

               return (Node)newNode;
            } else {
               return node;
            }
         }

         public Node leaveBlock(Block block) {
            if (this.inOuterFunction && LocalVariableTypesCalculator.this.syntheticReturn != null && this.lc.isFunctionBody()) {
               ArrayList<Statement> stmts = new ArrayList(block.getStatements());
               stmts.add((ReturnNode)LocalVariableTypesCalculator.this.syntheticReturn.accept(this));
               return block.setStatements(this.lc, stmts);
            } else {
               return super.leaveBlock(block);
            }
         }

         public Node leaveFunctionNode(FunctionNode nestedFunctionNode) {
            this.inOuterFunction = true;
            FunctionNode newNestedFunction = (FunctionNode)nestedFunctionNode.accept(new LocalVariableTypesCalculator(LocalVariableTypesCalculator.this.compiler));
            this.lc.replace(nestedFunctionNode, newNestedFunction);
            return newNestedFunction;
         }

         public Node leaveIdentNode(IdentNode identNode) {
            IdentNode original = (IdentNode)this.joinPredecessors.pop();
            Symbol symbol = identNode.getSymbol();
            if (symbol == null) {
               assert identNode.isPropertyName();

               return identNode;
            } else {
               if (symbol.hasSlot()) {
                  assert !symbol.isScope() || symbol.isParam();

                  assert original.getName().equals(identNode.getName());

                  LocalVariableTypesCalculator.LvarType lvarType = (LocalVariableTypesCalculator.LvarType)LocalVariableTypesCalculator.this.identifierLvarTypes.remove(original);
                  if (lvarType != null) {
                     return (Node)this.setLocalVariableConversion(original, identNode.setType(lvarType.type));
                  }

                  assert LocalVariableTypesCalculator.this.localVariableConversions.get(original) == null;
               } else {
                  assert LocalVariableTypesCalculator.this.identIsDeadAndHasNoLiveConversions(original);
               }

               return identNode;
            }
         }

         public Node leaveLiteralNode(LiteralNode<?> literalNode) {
            return literalNode.initialize(this.lc);
         }

         public Node leaveRuntimeNode(RuntimeNode runtimeNode) {
            RuntimeNode.Request request = runtimeNode.getRequest();
            boolean isEqStrict = request == RuntimeNode.Request.EQ_STRICT;
            return (Node)(!isEqStrict && request != RuntimeNode.Request.NE_STRICT ? runtimeNode : LocalVariableTypesCalculator.createIsUndefined(runtimeNode, (Expression)runtimeNode.getArgs().get(0), (Expression)runtimeNode.getArgs().get(1), isEqStrict ? RuntimeNode.Request.IS_UNDEFINED : RuntimeNode.Request.IS_NOT_UNDEFINED));
         }

         private <T extends JoinPredecessor> T setLocalVariableConversion(JoinPredecessor original, T jp) {
            return jp.setLocalVariableConversion(this.lc, (LocalVariableConversion)LocalVariableTypesCalculator.this.localVariableConversions.get(original));
         }
      };
      FunctionNode newFunction = functionNode.setBody(this.lc, (Block)functionNode.getBody().accept(applyChangesVisitor));
      newFunction = newFunction.setReturnType(this.lc, this.returnType);
      newFunction = newFunction.setParameters(this.lc, newFunction.visitParameters(applyChangesVisitor));
      return newFunction;
   }

   private static Expression createIsUndefined(Expression parent, Expression lhs, Expression rhs, RuntimeNode.Request request) {
      return (Expression)(!isUndefinedIdent(lhs) && !isUndefinedIdent(rhs) ? parent : new RuntimeNode(parent, request, new Expression[]{lhs, rhs}));
   }

   private static boolean isUndefinedIdent(Expression expr) {
      return expr instanceof IdentNode && "undefined".equals(((IdentNode)expr).getName());
   }

   private boolean identIsDeadAndHasNoLiveConversions(IdentNode identNode) {
      LocalVariableConversion conv = (LocalVariableConversion)this.localVariableConversions.get(identNode);
      return conv == null || !conv.isLive();
   }

   private void onAssignment(IdentNode identNode, LocalVariableTypesCalculator.LvarType type) {
      Symbol symbol = identNode.getSymbol();

      assert symbol != null : identNode.getName();

      if (symbol.isBytecodeLocal()) {
         assert type != null;

         LocalVariableTypesCalculator.LvarType finalType;
         if (type == LocalVariableTypesCalculator.LvarType.UNDEFINED && this.getLocalVariableType(symbol) != LocalVariableTypesCalculator.LvarType.UNDEFINED) {
            finalType = LocalVariableTypesCalculator.LvarType.OBJECT;
            symbol.setFlag(8192);
         } else {
            finalType = type;
         }

         this.setType(symbol, finalType);
         this.setIdentifierLvarType(identNode, finalType);
         this.jumpToCatchBlock(identNode);
      }
   }

   private void onSelfAssignment(IdentNode identNode, LocalVariableTypesCalculator.LvarType type) {
      Symbol symbol = identNode.getSymbol();

      assert symbol != null : identNode.getName();

      if (symbol.isBytecodeLocal()) {
         assert type != null && type != LocalVariableTypesCalculator.LvarType.UNDEFINED && type != LocalVariableTypesCalculator.LvarType.BOOLEAN;

         this.setType(symbol, type);
         this.jumpToCatchBlock(identNode);
      }
   }

   private void resetJoinPoint(Label label) {
      this.jumpTargets.remove(label);
   }

   private void setCompilerConstantAsObject(FunctionNode functionNode, CompilerConstants cc) {
      Symbol symbol = getCompilerConstantSymbol(functionNode, cc);
      this.setType(symbol, LocalVariableTypesCalculator.LvarType.OBJECT);
      this.symbolIsUsed(symbol);
   }

   private static Symbol getCompilerConstantSymbol(FunctionNode functionNode, CompilerConstants cc) {
      return functionNode.getBody().getExistingSymbol(cc.symbolName());
   }

   private void setConversion(JoinPredecessor node, Map<Symbol, LocalVariableTypesCalculator.LvarType> branchLvarTypes, Map<Symbol, LocalVariableTypesCalculator.LvarType> joinLvarTypes) {
      if (node != null) {
         if (branchLvarTypes.isEmpty() || joinLvarTypes.isEmpty()) {
            this.localVariableConversions.remove(node);
         }

         LocalVariableConversion conversion = null;
         Symbol symbol;
         LocalVariableTypesCalculator.LvarType branchLvarType;
         if (node instanceof IdentNode) {
            Symbol symbol = ((IdentNode)node).getSymbol();
            conversion = this.createConversion(symbol, (LocalVariableTypesCalculator.LvarType)branchLvarTypes.get(symbol), joinLvarTypes, (LocalVariableConversion)null);
         } else {
            for(Iterator var9 = branchLvarTypes.entrySet().iterator(); var9.hasNext(); conversion = this.createConversion(symbol, branchLvarType, joinLvarTypes, conversion)) {
               Entry<Symbol, LocalVariableTypesCalculator.LvarType> entry = (Entry)var9.next();
               symbol = (Symbol)entry.getKey();
               branchLvarType = (LocalVariableTypesCalculator.LvarType)entry.getValue();
            }
         }

         if (conversion != null) {
            this.localVariableConversions.put(node, conversion);
         } else {
            this.localVariableConversions.remove(node);
         }

      }
   }

   private void setIdentifierLvarType(IdentNode identNode, LocalVariableTypesCalculator.LvarType type) {
      assert type != null;

      this.identifierLvarTypes.put(identNode, type);
   }

   private void setType(Symbol symbol, LocalVariableTypesCalculator.LvarType type) {
      if (this.getLocalVariableTypeOrNull(symbol) != type) {
         assert symbol.hasSlot();

         assert !symbol.isGlobal();

         this.cloneOrNewLocalVariableTypes();
         this.localVariableTypes.put(symbol, type);
      }
   }

   private void cloneOrNewLocalVariableTypes() {
      this.localVariableTypes = this.localVariableTypes.isEmpty() ? new HashMap() : cloneMap(this.localVariableTypes);
   }

   private void invalidateSymbol(Symbol symbol) {
      this.localVariableTypes.remove(symbol);
      this.invalidatedSymbols.add(symbol);
   }

   private void symbolIsUsed(Symbol symbol) {
      symbolIsUsed(symbol, this.getLocalVariableType(symbol));
   }

   static {
      LocalVariableTypesCalculator.LvarType[] var0 = LocalVariableTypesCalculator.LvarType.values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         LocalVariableTypesCalculator.LvarType lvarType = var0[var2];
         TO_LVAR_TYPE.put(lvarType.type, lvarType);
      }

   }

   private static class SymbolConversions {
      private static final byte I2D = 1;
      private static final byte I2O = 2;
      private static final byte D2O = 4;
      private byte conversions;

      private SymbolConversions() {
      }

      void recordConversion(LocalVariableTypesCalculator.LvarType from, LocalVariableTypesCalculator.LvarType to) {
         switch(from) {
         case DOUBLE:
            if (to == LocalVariableTypesCalculator.LvarType.OBJECT) {
               this.recordConversion((byte)4);
            }

            return;
         case OBJECT:
         default:
            illegalConversion(from, to);
            return;
         case UNDEFINED:
            return;
         case INT:
         case BOOLEAN:
            switch(to) {
            case DOUBLE:
               this.recordConversion((byte)1);
               return;
            case OBJECT:
               this.recordConversion((byte)2);
               return;
            default:
               illegalConversion(from, to);
            }
         }
      }

      private static void illegalConversion(LocalVariableTypesCalculator.LvarType from, LocalVariableTypesCalculator.LvarType to) {
         throw new AssertionError("Invalid conversion from " + from + " to " + to);
      }

      void recordConversion(byte convFlag) {
         this.conversions |= convFlag;
      }

      boolean hasConversion(byte convFlag) {
         return (this.conversions & convFlag) != 0;
      }

      void calculateTypeLiveness(Symbol symbol) {
         if (symbol.hasSlotFor(Type.OBJECT)) {
            if (this.hasConversion((byte)4)) {
               symbol.setHasSlotFor(Type.NUMBER);
            }

            if (this.hasConversion((byte)2)) {
               symbol.setHasSlotFor(Type.INT);
            }
         }

         if (symbol.hasSlotFor(Type.NUMBER) && this.hasConversion((byte)1)) {
            symbol.setHasSlotFor(Type.INT);
         }

      }

      // $FF: synthetic method
      SymbolConversions(Object x0) {
         this();
      }
   }

   private static class TypeHolderExpression extends Expression {
      private static final long serialVersionUID = 1L;
      private final Type type;

      TypeHolderExpression(Type type) {
         super(0L, 0, 0);
         this.type = type;
      }

      public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
         throw new AssertionError();
      }

      public Type getType() {
         return this.type;
      }

      public void toString(StringBuilder sb, boolean printType) {
         throw new AssertionError();
      }
   }

   private static enum LvarType {
      UNDEFINED(Type.UNDEFINED),
      BOOLEAN(Type.BOOLEAN),
      INT(Type.INT),
      DOUBLE(Type.NUMBER),
      OBJECT(Type.OBJECT);

      private final Type type;
      private final LocalVariableTypesCalculator.TypeHolderExpression typeExpression;

      private LvarType(Type type) {
         this.type = type;
         this.typeExpression = new LocalVariableTypesCalculator.TypeHolderExpression(type);
      }
   }

   private static class JumpTarget {
      private final List<LocalVariableTypesCalculator.JumpOrigin> origins;
      private Map<Symbol, LocalVariableTypesCalculator.LvarType> types;

      private JumpTarget() {
         this.origins = new LinkedList();
         this.types = Collections.emptyMap();
      }

      void addOrigin(JoinPredecessor originNode, Map<Symbol, LocalVariableTypesCalculator.LvarType> originTypes, LocalVariableTypesCalculator calc) {
         this.origins.add(new LocalVariableTypesCalculator.JumpOrigin(originNode, originTypes));
         this.types = calc.getUnionTypes(this.types, originTypes);
      }

      // $FF: synthetic method
      JumpTarget(Object x0) {
         this();
      }
   }

   private static class JumpOrigin {
      final JoinPredecessor node;
      final Map<Symbol, LocalVariableTypesCalculator.LvarType> types;

      JumpOrigin(JoinPredecessor node, Map<Symbol, LocalVariableTypesCalculator.LvarType> types) {
         this.node = node;
         this.types = types;
      }
   }
}
