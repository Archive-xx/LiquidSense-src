package jdk.nashorn.internal.codegen;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import jdk.nashorn.internal.ir.Block;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.debug.ASTWriter;
import jdk.nashorn.internal.ir.debug.PrintVisitor;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.runtime.CodeInstaller;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

abstract class CompilationPhase {
   static final CompilationPhase CONSTANT_FOLDING_PHASE = new CompilationPhase.ConstantFoldingPhase();
   static final CompilationPhase LOWERING_PHASE = new CompilationPhase.LoweringPhase();
   static final CompilationPhase APPLY_SPECIALIZATION_PHASE = new CompilationPhase.ApplySpecializationPhase();
   static final CompilationPhase SPLITTING_PHASE = new CompilationPhase.SplittingPhase();
   static final CompilationPhase PROGRAM_POINT_PHASE = new CompilationPhase.ProgramPointPhase();
   static final CompilationPhase CACHE_AST_PHASE = new CompilationPhase.CacheAstPhase();
   static final CompilationPhase SYMBOL_ASSIGNMENT_PHASE = new CompilationPhase.SymbolAssignmentPhase();
   static final CompilationPhase SCOPE_DEPTH_COMPUTATION_PHASE = new CompilationPhase.ScopeDepthComputationPhase();
   static final CompilationPhase DECLARE_LOCAL_SYMBOLS_PHASE = new CompilationPhase.DeclareLocalSymbolsPhase();
   static final CompilationPhase OPTIMISTIC_TYPE_ASSIGNMENT_PHASE = new CompilationPhase.OptimisticTypeAssignmentPhase();
   static final CompilationPhase LOCAL_VARIABLE_TYPE_CALCULATION_PHASE = new CompilationPhase.LocalVariableTypeCalculationPhase();
   static final CompilationPhase REUSE_COMPILE_UNITS_PHASE = new CompilationPhase.ReuseCompileUnitsPhase();
   static final CompilationPhase REINITIALIZE_CACHED = new CompilationPhase.ReinitializeCachedPhase();
   static final CompilationPhase BYTECODE_GENERATION_PHASE = new CompilationPhase.BytecodeGenerationPhase();
   static final CompilationPhase INSTALL_PHASE = new CompilationPhase.InstallPhase();
   private long startTime;
   private long endTime;
   private boolean isFinished;

   private CompilationPhase() {
   }

   protected FunctionNode begin(Compiler compiler, FunctionNode functionNode) {
      compiler.getLogger().indent();
      this.startTime = System.nanoTime();
      return functionNode;
   }

   protected FunctionNode end(Compiler compiler, FunctionNode functionNode) {
      compiler.getLogger().unindent();
      this.endTime = System.nanoTime();
      compiler.getScriptEnvironment()._timing.accumulateTime(this.toString(), this.endTime - this.startTime);
      this.isFinished = true;
      return functionNode;
   }

   boolean isFinished() {
      return this.isFinished;
   }

   long getStartTime() {
      return this.startTime;
   }

   long getEndTime() {
      return this.endTime;
   }

   abstract FunctionNode transform(Compiler var1, Compiler.CompilationPhases var2, FunctionNode var3) throws CompilationException;

   final FunctionNode apply(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode functionNode) throws CompilationException {
      assert phases.contains(this);

      return this.end(compiler, this.transform(compiler, phases, this.begin(compiler, functionNode)));
   }

   private static FunctionNode transformFunction(FunctionNode fn, NodeVisitor<?> visitor) {
      return (FunctionNode)fn.accept(visitor);
   }

   private static CompileUnit createNewCompileUnit(Compiler compiler, Compiler.CompilationPhases phases) {
      StringBuilder sb = new StringBuilder(compiler.nextCompileUnitName());
      if (phases.isRestOfCompilation()) {
         sb.append("$restOf");
      }

      return compiler.createCompileUnit(sb.toString(), 0L);
   }

   // $FF: synthetic method
   CompilationPhase(Object x0) {
      this();
   }

   private static final class InstallPhase extends CompilationPhase {
      private InstallPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         DebugLogger log = compiler.getLogger();
         Map<String, Class<?>> installedClasses = new LinkedHashMap();
         boolean first = true;
         Class<?> rootClass = null;
         long length = 0L;
         CodeInstaller codeInstaller = compiler.getCodeInstaller();
         Map<String, byte[]> bytecode = compiler.getBytecode();

         String className;
         Class clazz;
         for(Iterator var12 = bytecode.entrySet().iterator(); var12.hasNext(); installedClasses.put(className, clazz)) {
            Entry<String, byte[]> entry = (Entry)var12.next();
            className = (String)entry.getKey();
            byte[] code = (byte[])entry.getValue();
            length += (long)code.length;
            clazz = codeInstaller.install(className, code);
            if (first) {
               rootClass = clazz;
               first = false;
            }
         }

         if (rootClass == null) {
            throw new CompilationException("Internal compiler error: root class not found!");
         } else {
            Object[] constants = compiler.getConstantData().toArray();
            codeInstaller.initialize(installedClasses.values(), compiler.getSource(), constants);
            Object[] var18 = constants;
            int var21 = constants.length;

            for(int var23 = 0; var23 < var21; ++var23) {
               Object constant = var18[var23];
               if (constant instanceof RecompilableScriptFunctionData) {
                  ((RecompilableScriptFunctionData)constant).initTransients(compiler.getSource(), codeInstaller);
               }
            }

            Iterator var19 = compiler.getCompileUnits().iterator();

            while(var19.hasNext()) {
               CompileUnit unit = (CompileUnit)var19.next();
               if (unit.isUsed()) {
                  unit.setCode((Class)installedClasses.get(unit.getUnitClassName()));
                  unit.initializeFunctionsCode();
               }
            }

            if (log.isEnabled()) {
               StringBuilder sb = new StringBuilder();
               sb.append("Installed class '").append(rootClass.getSimpleName()).append('\'').append(" [").append(rootClass.getName()).append(", size=").append(length).append(" bytes, ").append(compiler.getCompileUnits().size()).append(" compile unit(s)]");
               log.fine(sb.toString());
            }

            return fn.setRootClass((LexicalContext)null, rootClass);
         }
      }

      public String toString() {
         return "'Class Installation'";
      }

      // $FF: synthetic method
      InstallPhase(Object x0) {
         this();
      }
   }

   private static final class BytecodeGenerationPhase extends CompilationPhase {
      private BytecodeGenerationPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         ScriptEnvironment senv = compiler.getScriptEnvironment();
         FunctionNode newFunctionNode = fn;
         fn.getCompileUnit().setUsed();
         compiler.getLogger().fine("Starting bytecode generation for ", DebugLogger.quote(fn.getName()), " - restOf=", phases.isRestOfCompilation());
         CodeGenerator codegen = new CodeGenerator(compiler, phases.isRestOfCompilation() ? compiler.getContinuationEntryPoints() : null);

         try {
            newFunctionNode = CompilationPhase.transformFunction(newFunctionNode, codegen);
            codegen.generateScopeCalls();
         } catch (VerifyError var12) {
            if (!senv._verify_code && !senv._print_code) {
               throw var12;
            }

            senv.getErr().println(var12.getClass().getSimpleName() + ": " + var12.getMessage());
            if (senv._dump_on_error) {
               var12.printStackTrace(senv.getErr());
            }
         } catch (Throwable var13) {
            throw new AssertionError("Failed generating bytecode for " + fn.getSourceName() + ":" + codegen.getLastLineNumber(), var13);
         }

         Iterator var7 = compiler.getCompileUnits().iterator();

         while(var7.hasNext()) {
            CompileUnit compileUnit = (CompileUnit)var7.next();
            ClassEmitter classEmitter = compileUnit.getClassEmitter();
            classEmitter.end();
            if (!compileUnit.isUsed()) {
               compiler.getLogger().fine("Skipping unused compile unit ", compileUnit);
            } else {
               byte[] bytecode = classEmitter.toByteArray();

               assert bytecode != null;

               String className = compileUnit.getUnitClassName();
               compiler.addClass(className, bytecode);
               CompileUnit.increaseEmitCount();
               if (senv._verify_code) {
                  compiler.getCodeInstaller().verify(bytecode);
               }

               DumpBytecode.dumpBytecode(senv, compiler.getLogger(), bytecode, className);
            }
         }

         return newFunctionNode;
      }

      public String toString() {
         return "'Bytecode Generation'";
      }

      // $FF: synthetic method
      BytecodeGenerationPhase(Object x0) {
         this();
      }
   }

   private static final class ReinitializeCachedPhase extends CompilationPhase {
      private ReinitializeCachedPhase() {
         super(null);
      }

      FunctionNode transform(final Compiler compiler, final Compiler.CompilationPhases phases, FunctionNode fn) {
         final Set<CompileUnit> unitSet = CompileUnit.createCompileUnitSet();
         final Map<CompileUnit, CompileUnit> unitMap = new HashMap();
         this.createCompileUnit(fn.getCompileUnit(), unitSet, unitMap, compiler, phases);
         FunctionNode newFn = CompilationPhase.transformFunction(fn, new ReplaceCompileUnits() {
            CompileUnit getReplacement(CompileUnit oldUnit) {
               CompileUnit existing = (CompileUnit)unitMap.get(oldUnit);
               return existing != null ? existing : ReinitializeCachedPhase.this.createCompileUnit(oldUnit, unitSet, unitMap, compiler, phases);
            }

            public Node leaveFunctionNode(FunctionNode fn2) {
               return super.leaveFunctionNode(compiler.getScriptFunctionData(fn2.getId()).restoreFlags(this.lc, fn2));
            }
         });
         compiler.replaceCompileUnits(unitSet);
         return newFn;
      }

      private CompileUnit createCompileUnit(CompileUnit oldUnit, Set<CompileUnit> unitSet, Map<CompileUnit, CompileUnit> unitMap, Compiler compiler, Compiler.CompilationPhases phases) {
         CompileUnit newUnit = CompilationPhase.createNewCompileUnit(compiler, phases);
         unitMap.put(oldUnit, newUnit);
         unitSet.add(newUnit);
         return newUnit;
      }

      public String toString() {
         return "'Reinitialize cached'";
      }

      // $FF: synthetic method
      ReinitializeCachedPhase(Object x0) {
         this();
      }
   }

   private static final class ReuseCompileUnitsPhase extends CompilationPhase {
      private ReuseCompileUnitsPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         assert phases.isRestOfCompilation() : "reuse compile units currently only used for Rest-Of methods";

         final Map<CompileUnit, CompileUnit> map = new HashMap();
         Set<CompileUnit> newUnits = CompileUnit.createCompileUnitSet();
         DebugLogger log = compiler.getLogger();
         log.fine("Clearing bytecode cache");
         compiler.clearBytecode();
         Iterator var7 = compiler.getCompileUnits().iterator();

         while(var7.hasNext()) {
            CompileUnit oldUnit = (CompileUnit)var7.next();

            assert map.get(oldUnit) == null;

            CompileUnit newUnit = CompilationPhase.createNewCompileUnit(compiler, phases);
            log.fine("Creating new compile unit ", oldUnit, " => ", newUnit);
            map.put(oldUnit, newUnit);

            assert newUnit != null;

            newUnits.add(newUnit);
         }

         log.fine("Replacing compile units in Compiler...");
         compiler.replaceCompileUnits(newUnits);
         log.fine("Done");
         FunctionNode newFunctionNode = CompilationPhase.transformFunction(fn, new ReplaceCompileUnits() {
            CompileUnit getReplacement(CompileUnit original) {
               return (CompileUnit)map.get(original);
            }

            public Node leaveDefault(Node node) {
               return node.ensureUniqueLabels(this.lc);
            }
         });
         return newFunctionNode;
      }

      public String toString() {
         return "'Reuse Compile Units'";
      }

      // $FF: synthetic method
      ReuseCompileUnitsPhase(Object x0) {
         this();
      }
   }

   private static final class LocalVariableTypeCalculationPhase extends CompilationPhase {
      private LocalVariableTypeCalculationPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         FunctionNode newFunctionNode = CompilationPhase.transformFunction(fn, new LocalVariableTypesCalculator(compiler));
         ScriptEnvironment senv = compiler.getScriptEnvironment();
         PrintWriter err = senv.getErr();
         if (senv._print_lower_ast || fn.getFlag(1048576)) {
            err.println("Lower AST for: " + DebugLogger.quote(newFunctionNode.getName()));
            err.println(new ASTWriter(newFunctionNode));
         }

         if (senv._print_lower_parse || fn.getFlag(262144)) {
            err.println("Lower AST for: " + DebugLogger.quote(newFunctionNode.getName()));
            err.println(new PrintVisitor(newFunctionNode));
         }

         return newFunctionNode;
      }

      public String toString() {
         return "'Local Variable Type Calculation'";
      }

      // $FF: synthetic method
      LocalVariableTypeCalculationPhase(Object x0) {
         this();
      }
   }

   private static final class OptimisticTypeAssignmentPhase extends CompilationPhase {
      private OptimisticTypeAssignmentPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         return compiler.useOptimisticTypes() ? CompilationPhase.transformFunction(fn, new OptimisticTypesCalculator(compiler)) : fn;
      }

      public String toString() {
         return "'Optimistic Type Assignment'";
      }

      // $FF: synthetic method
      OptimisticTypeAssignmentPhase(Object x0) {
         this();
      }
   }

   private static final class DeclareLocalSymbolsPhase extends CompilationPhase {
      private DeclareLocalSymbolsPhase() {
         super(null);
      }

      FunctionNode transform(final Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         if (compiler.useOptimisticTypes() && compiler.isOnDemandCompilation()) {
            fn.getBody().accept(new SimpleNodeVisitor() {
               public boolean enterFunctionNode(FunctionNode functionNode) {
                  return false;
               }

               public boolean enterBlock(Block block) {
                  Iterator var2 = block.getSymbols().iterator();

                  while(var2.hasNext()) {
                     Symbol symbol = (Symbol)var2.next();
                     if (!symbol.isScope()) {
                        compiler.declareLocalSymbol(symbol.getName());
                     }
                  }

                  return true;
               }
            });
         }

         return fn;
      }

      public String toString() {
         return "'Local Symbols Declaration'";
      }

      // $FF: synthetic method
      DeclareLocalSymbolsPhase(Object x0) {
         this();
      }
   }

   private static final class ScopeDepthComputationPhase extends CompilationPhase {
      private ScopeDepthComputationPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         return CompilationPhase.transformFunction(fn, new FindScopeDepths(compiler));
      }

      public String toString() {
         return "'Scope Depth Computation'";
      }

      // $FF: synthetic method
      ScopeDepthComputationPhase(Object x0) {
         this();
      }
   }

   private static final class SymbolAssignmentPhase extends CompilationPhase {
      private SymbolAssignmentPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         return CompilationPhase.transformFunction(fn, new AssignSymbols(compiler));
      }

      public String toString() {
         return "'Symbol Assignment'";
      }

      // $FF: synthetic method
      SymbolAssignmentPhase(Object x0) {
         this();
      }
   }

   private static final class CacheAstPhase extends CompilationPhase {
      private CacheAstPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         if (!compiler.isOnDemandCompilation()) {
            CompilationPhase.transformFunction(fn, new CacheAst(compiler));
         }

         return fn;
      }

      public String toString() {
         return "'Cache ASTs'";
      }

      // $FF: synthetic method
      CacheAstPhase(Object x0) {
         this();
      }
   }

   private static final class ProgramPointPhase extends CompilationPhase {
      private ProgramPointPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         return CompilationPhase.transformFunction(fn, new ProgramPoints());
      }

      public String toString() {
         return "'Program Point Calculation'";
      }

      // $FF: synthetic method
      ProgramPointPhase(Object x0) {
         this();
      }
   }

   private static final class SplittingPhase extends CompilationPhase {
      private SplittingPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         CompileUnit outermostCompileUnit = compiler.addCompileUnit(0L);
         FunctionNode newFunctionNode = CompilationPhase.transformFunction(fn, new SimpleNodeVisitor() {
            public LiteralNode<?> leaveLiteralNode(LiteralNode<?> literalNode) {
               return literalNode.initialize(this.lc);
            }
         });
         newFunctionNode = (new Splitter(compiler, newFunctionNode, outermostCompileUnit)).split(newFunctionNode, true);
         newFunctionNode = CompilationPhase.transformFunction(newFunctionNode, new SplitIntoFunctions(compiler));

         assert newFunctionNode.getCompileUnit() == outermostCompileUnit : "fn=" + fn.getName() + ", fn.compileUnit (" + newFunctionNode.getCompileUnit() + ") != " + outermostCompileUnit;

         assert newFunctionNode.isStrict() == compiler.isStrict() : "functionNode.isStrict() != compiler.isStrict() for " + DebugLogger.quote(newFunctionNode.getName());

         return newFunctionNode;
      }

      public String toString() {
         return "'Code Splitting'";
      }

      // $FF: synthetic method
      SplittingPhase(Object x0) {
         this();
      }
   }

   private static final class ApplySpecializationPhase extends CompilationPhase {
      private ApplySpecializationPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         return CompilationPhase.transformFunction(fn, new ApplySpecialization(compiler));
      }

      public String toString() {
         return "'Builtin Replacement'";
      }

      // $FF: synthetic method
      ApplySpecializationPhase(Object x0) {
         this();
      }
   }

   private static final class LoweringPhase extends CompilationPhase {
      private LoweringPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         return CompilationPhase.transformFunction(fn, new Lower(compiler));
      }

      public String toString() {
         return "'Control Flow Lowering'";
      }

      // $FF: synthetic method
      LoweringPhase(Object x0) {
         this();
      }
   }

   private static final class ConstantFoldingPhase extends CompilationPhase {
      private ConstantFoldingPhase() {
         super(null);
      }

      FunctionNode transform(Compiler compiler, Compiler.CompilationPhases phases, FunctionNode fn) {
         return CompilationPhase.transformFunction(fn, new FoldConstants(compiler));
      }

      public String toString() {
         return "'Constant Folding'";
      }

      // $FF: synthetic method
      ConstantFoldingPhase(Object x0) {
         this();
      }
   }
}
