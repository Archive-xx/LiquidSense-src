package jdk.nashorn.internal.codegen;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.WithNode;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "scopedepths"
)
final class FindScopeDepths extends SimpleNodeVisitor implements Loggable {
   private final Compiler compiler;
   private final Map<Integer, Map<Integer, RecompilableScriptFunctionData>> fnIdToNestedFunctions = new HashMap();
   private final Map<Integer, Map<String, Integer>> externalSymbolDepths = new HashMap();
   private final Map<Integer, Set<String>> internalSymbols = new HashMap();
   private final Set<Block> withBodies = new HashSet();
   private final DebugLogger log;
   private int dynamicScopeCount;

   FindScopeDepths(Compiler compiler) {
      this.compiler = compiler;
      this.log = this.initLogger(compiler.getContext());
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context context) {
      return context.getLogger(this.getClass());
   }

   static int findScopesToStart(LexicalContext lc, FunctionNode fn, Block block) {
      Block bodyBlock = findBodyBlock(lc, fn, block);
      Iterator<Block> iter = lc.getBlocks(block);
      Block b = (Block)iter.next();
      int scopesToStart = 0;

      while(true) {
         if (b.needsScope()) {
            ++scopesToStart;
         }

         if (b == bodyBlock) {
            return scopesToStart;
         }

         b = (Block)iter.next();
      }
   }

   static int findInternalDepth(LexicalContext lc, FunctionNode fn, Block block, Symbol symbol) {
      Block bodyBlock = findBodyBlock(lc, fn, block);
      Iterator<Block> iter = lc.getBlocks(block);
      Block b = (Block)iter.next();

      int scopesToStart;
      for(scopesToStart = 0; !definedInBlock(b, symbol); b = (Block)iter.next()) {
         if (b.needsScope()) {
            ++scopesToStart;
         }

         if (b == bodyBlock) {
            return -1;
         }
      }

      return scopesToStart;
   }

   private static boolean definedInBlock(Block block, Symbol symbol) {
      if (symbol.isGlobal()) {
         return block.isGlobalScope();
      } else {
         return block.getExistingSymbol(symbol.getName()) == symbol;
      }
   }

   static Block findBodyBlock(LexicalContext lc, FunctionNode fn, Block block) {
      Iterator iter = lc.getBlocks(block);

      Block next;
      do {
         if (!iter.hasNext()) {
            return null;
         }

         next = (Block)iter.next();
      } while(fn.getBody() != next);

      return next;
   }

   private static Block findGlobalBlock(LexicalContext lc, Block block) {
      Iterator<Block> iter = lc.getBlocks(block);

      Block globalBlock;
      for(globalBlock = null; iter.hasNext(); globalBlock = (Block)iter.next()) {
      }

      return globalBlock;
   }

   private static boolean isDynamicScopeBoundary(FunctionNode fn) {
      return fn.needsDynamicScope();
   }

   private boolean isDynamicScopeBoundary(Block block) {
      return this.withBodies.contains(block);
   }

   public boolean enterFunctionNode(FunctionNode functionNode) {
      if (this.compiler.isOnDemandCompilation()) {
         return true;
      } else {
         if (isDynamicScopeBoundary(functionNode)) {
            this.increaseDynamicScopeCount(functionNode);
         }

         int fnId = functionNode.getId();
         Map<Integer, RecompilableScriptFunctionData> nestedFunctions = (Map)this.fnIdToNestedFunctions.get(fnId);
         if (nestedFunctions == null) {
            Map<Integer, RecompilableScriptFunctionData> nestedFunctions = new HashMap();
            this.fnIdToNestedFunctions.put(fnId, nestedFunctions);
         }

         return true;
      }
   }

   public Node leaveFunctionNode(FunctionNode functionNode) {
      String name = functionNode.getName();
      FunctionNode newFunctionNode = functionNode;
      if (this.compiler.isOnDemandCompilation()) {
         RecompilableScriptFunctionData data = this.compiler.getScriptFunctionData(functionNode.getId());
         if (data.inDynamicContext()) {
            this.log.fine("Reviving scriptfunction ", DebugLogger.quote(name), " as defined in previous (now lost) dynamic scope.");
            newFunctionNode = functionNode.setInDynamicContext(this.lc);
         }

         if (newFunctionNode == this.lc.getOutermostFunction() && !newFunctionNode.hasApplyToCallSpecialization()) {
            data.setCachedAst(newFunctionNode);
         }

         return newFunctionNode;
      } else {
         if (this.inDynamicScope()) {
            this.log.fine("Tagging ", DebugLogger.quote(name), " as defined in dynamic scope");
            newFunctionNode = functionNode.setInDynamicContext(this.lc);
         }

         int fnId = newFunctionNode.getId();
         Map<Integer, RecompilableScriptFunctionData> nestedFunctions = (Map)this.fnIdToNestedFunctions.remove(fnId);

         assert nestedFunctions != null;

         RecompilableScriptFunctionData data = new RecompilableScriptFunctionData(newFunctionNode, this.compiler.getCodeInstaller(), ObjectClassGenerator.createAllocationStrategy(newFunctionNode.getThisProperties(), this.compiler.getContext().useDualFields()), nestedFunctions, (Map)this.externalSymbolDepths.get(fnId), (Set)this.internalSymbols.get(fnId));
         if (this.lc.getOutermostFunction() != newFunctionNode) {
            FunctionNode parentFn = this.lc.getParentFunction(newFunctionNode);
            if (parentFn != null) {
               ((Map)this.fnIdToNestedFunctions.get(parentFn.getId())).put(fnId, data);
            }
         } else {
            this.compiler.setData(data);
         }

         if (isDynamicScopeBoundary(functionNode)) {
            this.decreaseDynamicScopeCount(functionNode);
         }

         return newFunctionNode;
      }
   }

   private boolean inDynamicScope() {
      return this.dynamicScopeCount > 0;
   }

   private void increaseDynamicScopeCount(Node node) {
      assert this.dynamicScopeCount >= 0;

      ++this.dynamicScopeCount;
      if (this.log.isEnabled()) {
         this.log.finest(DebugLogger.quote(this.lc.getCurrentFunction().getName()), " ++dynamicScopeCount = ", this.dynamicScopeCount, " at: ", node, node.getClass());
      }

   }

   private void decreaseDynamicScopeCount(Node node) {
      --this.dynamicScopeCount;

      assert this.dynamicScopeCount >= 0;

      if (this.log.isEnabled()) {
         this.log.finest(DebugLogger.quote(this.lc.getCurrentFunction().getName()), " --dynamicScopeCount = ", this.dynamicScopeCount, " at: ", node, node.getClass());
      }

   }

   public boolean enterWithNode(WithNode node) {
      this.withBodies.add(node.getBody());
      return true;
   }

   public boolean enterBlock(Block block) {
      if (this.compiler.isOnDemandCompilation()) {
         return true;
      } else {
         if (this.isDynamicScopeBoundary(block)) {
            this.increaseDynamicScopeCount(block);
         }

         if (!this.lc.isFunctionBody()) {
            return true;
         } else {
            FunctionNode fn = this.lc.getCurrentFunction();
            final Set<Symbol> symbols = new HashSet();
            block.accept(new SimpleNodeVisitor() {
               public boolean enterIdentNode(IdentNode identNode) {
                  Symbol symbol = identNode.getSymbol();
                  if (symbol != null && symbol.isScope()) {
                     symbols.add(symbol);
                  }

                  return true;
               }
            });
            Map<String, Integer> internals = new HashMap();
            Block globalBlock = findGlobalBlock(this.lc, block);
            Block bodyBlock = findBodyBlock(this.lc, fn, block);

            assert globalBlock != null;

            assert bodyBlock != null;

            Iterator var7 = symbols.iterator();

            while(true) {
               while(true) {
                  Symbol symbol;
                  boolean internal;
                  do {
                     if (!var7.hasNext()) {
                        this.addInternalSymbols(fn, internals.keySet());
                        if (this.log.isEnabled()) {
                           this.log.info(fn.getName() + " internals=" + internals + " externals=" + this.externalSymbolDepths.get(fn.getId()));
                        }

                        return true;
                     }

                     symbol = (Symbol)var7.next();
                     int internalDepth = findInternalDepth(this.lc, fn, block, symbol);
                     internal = internalDepth >= 0;
                     if (internal) {
                        internals.put(symbol.getName(), internalDepth);
                     }
                  } while(internal);

                  int depthAtStart = 0;
                  Iterator iter = this.lc.getAncestorBlocks(bodyBlock);

                  while(iter.hasNext()) {
                     Block b2 = (Block)iter.next();
                     if (definedInBlock(b2, symbol)) {
                        this.addExternalSymbol(fn, symbol, depthAtStart);
                        break;
                     }

                     if (b2.needsScope()) {
                        ++depthAtStart;
                     }
                  }
               }
            }
         }
      }
   }

   public Node leaveBlock(Block block) {
      if (this.compiler.isOnDemandCompilation()) {
         return block;
      } else {
         if (this.isDynamicScopeBoundary(block)) {
            this.decreaseDynamicScopeCount(block);
         }

         return block;
      }
   }

   private void addInternalSymbols(FunctionNode functionNode, Set<String> symbols) {
      int fnId = functionNode.getId();

      assert this.internalSymbols.get(fnId) == null || ((Set)this.internalSymbols.get(fnId)).equals(symbols);

      this.internalSymbols.put(fnId, symbols);
   }

   private void addExternalSymbol(FunctionNode functionNode, Symbol symbol, int depthAtStart) {
      int fnId = functionNode.getId();
      Map<String, Integer> depths = (Map)this.externalSymbolDepths.get(fnId);
      if (depths == null) {
         depths = new HashMap();
         this.externalSymbolDepths.put(fnId, depths);
      }

      ((Map)depths).put(symbol.getName(), depthAtStart);
   }
}
