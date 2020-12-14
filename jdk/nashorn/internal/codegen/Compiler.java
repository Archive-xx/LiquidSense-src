package jdk.nashorn.internal.codegen;

import java.io.File;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Level;
import jdk.internal.dynalink.support.NameCodec;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.Optimistic;
import jdk.nashorn.internal.ir.debug.ClassHistogramElement;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import jdk.nashorn.internal.runtime.CodeInstaller;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.FunctionInitializer;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(
   name = "compiler"
)
public final class Compiler implements Loggable {
   public static final String SCRIPTS_PACKAGE = "jdk/nashorn/internal/scripts";
   public static final String OBJECTS_PACKAGE = "jdk/nashorn/internal/objects";
   private final ScriptEnvironment env;
   private final Source source;
   private final String sourceName;
   private final ErrorManager errors;
   private final boolean optimistic;
   private final Map<String, byte[]> bytecode;
   private final Set<CompileUnit> compileUnits;
   private final ConstantData constantData;
   private final CodeInstaller installer;
   private final DebugLogger log;
   private final Context context;
   private final TypeMap types;
   private final TypeEvaluator typeEvaluator;
   private final boolean strict;
   private final boolean onDemand;
   private final Map<Integer, Type> invalidatedProgramPoints;
   private final Object typeInformationFile;
   private final String firstCompileUnitName;
   private final int[] continuationEntryPoints;
   private RecompilableScriptFunctionData compiledFunction;
   private static final int COMPILE_UNIT_NAME_BUFFER_SIZE = 32;
   private static String[] RESERVED_NAMES;
   private final int compilationId;
   private final AtomicInteger nextCompileUnitId;
   private static final AtomicInteger COMPILATION_ID;
   private static final String DANGEROUS_CHARS = "\\/.;:$[]<>";

   public static Compiler forInitialCompilation(CodeInstaller installer, Source source, ErrorManager errors, boolean isStrict) {
      return new Compiler(installer.getContext(), installer, source, errors, isStrict);
   }

   public static Compiler forNoInstallerCompilation(Context context, Source source, boolean isStrict) {
      return new Compiler(context, (CodeInstaller)null, source, context.getErrorManager(), isStrict);
   }

   public static Compiler forOnDemandCompilation(CodeInstaller installer, Source source, boolean isStrict, RecompilableScriptFunctionData compiledFunction, TypeMap types, Map<Integer, Type> invalidatedProgramPoints, Object typeInformationFile, int[] continuationEntryPoints, ScriptObject runtimeScope) {
      Context context = installer.getContext();
      return new Compiler(context, installer, source, context.getErrorManager(), isStrict, true, compiledFunction, types, invalidatedProgramPoints, typeInformationFile, continuationEntryPoints, runtimeScope);
   }

   private Compiler(Context context, CodeInstaller installer, Source source, ErrorManager errors, boolean isStrict) {
      this(context, installer, source, errors, isStrict, false, (RecompilableScriptFunctionData)null, (TypeMap)null, (Map)null, (Object)null, (int[])null, (ScriptObject)null);
   }

   private Compiler(Context context, CodeInstaller installer, Source source, ErrorManager errors, boolean isStrict, boolean isOnDemand, RecompilableScriptFunctionData compiledFunction, TypeMap types, Map<Integer, Type> invalidatedProgramPoints, Object typeInformationFile, int[] continuationEntryPoints, ScriptObject runtimeScope) {
      this.compilationId = COMPILATION_ID.getAndIncrement();
      this.nextCompileUnitId = new AtomicInteger(0);
      this.context = context;
      this.env = context.getEnv();
      this.installer = installer;
      this.constantData = new ConstantData();
      this.compileUnits = CompileUnit.createCompileUnitSet();
      this.bytecode = new LinkedHashMap();
      this.log = this.initLogger(context);
      this.source = source;
      this.errors = errors;
      this.sourceName = FunctionNode.getSourceName(source);
      this.onDemand = isOnDemand;
      this.compiledFunction = compiledFunction;
      this.types = types;
      this.invalidatedProgramPoints = (Map)(invalidatedProgramPoints == null ? new HashMap() : invalidatedProgramPoints);
      this.typeInformationFile = typeInformationFile;
      this.continuationEntryPoints = continuationEntryPoints == null ? null : (int[])continuationEntryPoints.clone();
      this.typeEvaluator = new TypeEvaluator(this, runtimeScope);
      this.firstCompileUnitName = this.firstCompileUnitName();
      this.strict = isStrict;
      this.optimistic = this.env._optimistic_types;
   }

   private String safeSourceName() {
      String baseName = (new File(this.source.getName())).getName();
      int index = baseName.lastIndexOf(".js");
      if (index != -1) {
         baseName = baseName.substring(0, index);
      }

      baseName = baseName.replace('.', '_').replace('-', '_');
      if (!this.env._loader_per_compile) {
         baseName = baseName + this.installer.getUniqueScriptId();
      }

      String mangled = this.env._verify_code ? replaceDangerChars(baseName) : NameCodec.encode(baseName);
      return mangled != null ? mangled : baseName;
   }

   private static String replaceDangerChars(String name) {
      int len = name.length();
      StringBuilder buf = new StringBuilder();

      for(int i = 0; i < len; ++i) {
         char ch = name.charAt(i);
         if ("\\/.;:$[]<>".indexOf(ch) != -1) {
            buf.append('_');
         } else {
            buf.append(ch);
         }
      }

      return buf.toString();
   }

   private String firstCompileUnitName() {
      StringBuilder sb = (new StringBuilder("jdk/nashorn/internal/scripts")).append('/').append(CompilerConstants.DEFAULT_SCRIPT_NAME.symbolName()).append('$');
      if (this.isOnDemandCompilation()) {
         sb.append("Recompilation$");
      }

      if (this.compilationId > 0) {
         sb.append(this.compilationId).append('$');
      }

      if (this.types != null && this.compiledFunction.getFunctionNodeId() > 0) {
         sb.append(this.compiledFunction.getFunctionNodeId());
         Type[] paramTypes = this.types.getParameterTypes(this.compiledFunction.getFunctionNodeId());
         Type[] var3 = paramTypes;
         int var4 = paramTypes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Type t = var3[var5];
            sb.append(Type.getShortSignatureDescriptor(t));
         }

         sb.append('$');
      }

      sb.append(this.safeSourceName());
      return sb.toString();
   }

   void declareLocalSymbol(String symbolName) {
      this.typeEvaluator.declareLocalSymbol(symbolName);
   }

   void setData(RecompilableScriptFunctionData data) {
      assert this.compiledFunction == null : data;

      this.compiledFunction = data;
   }

   public DebugLogger getLogger() {
      return this.log;
   }

   public DebugLogger initLogger(Context ctxt) {
      final boolean optimisticTypes = this.env._optimistic_types;
      final boolean lazyCompilation = this.env._lazy_compilation;
      return ctxt.getLogger(this.getClass(), new Consumer<DebugLogger>() {
         public void accept(DebugLogger newLogger) {
            if (!lazyCompilation) {
               newLogger.warning("WARNING: Running with lazy compilation switched off. This is not a default setting.");
            }

            newLogger.warning("Optimistic types are ", optimisticTypes ? "ENABLED." : "DISABLED.");
         }
      });
   }

   ScriptEnvironment getScriptEnvironment() {
      return this.env;
   }

   boolean isOnDemandCompilation() {
      return this.onDemand;
   }

   boolean useOptimisticTypes() {
      return this.optimistic;
   }

   Context getContext() {
      return this.context;
   }

   Type getOptimisticType(Optimistic node) {
      return this.typeEvaluator.getOptimisticType(node);
   }

   boolean hasStringPropertyIterator(Expression expr) {
      return this.typeEvaluator.hasStringPropertyIterator(expr);
   }

   void addInvalidatedProgramPoint(int programPoint, Type type) {
      this.invalidatedProgramPoints.put(programPoint, type);
   }

   public Map<Integer, Type> getInvalidatedProgramPoints() {
      return this.invalidatedProgramPoints.isEmpty() ? null : new TreeMap(this.invalidatedProgramPoints);
   }

   TypeMap getTypeMap() {
      return this.types;
   }

   MethodType getCallSiteType(FunctionNode fn) {
      return this.types != null && this.isOnDemandCompilation() ? this.types.getCallSiteType(fn) : null;
   }

   Type getParamType(FunctionNode fn, int pos) {
      return this.types == null ? null : this.types.get(fn, pos);
   }

   public FunctionNode compile(FunctionNode functionNode, Compiler.CompilationPhases phases) throws CompilationException {
      if (this.log.isEnabled()) {
         this.log.info(">> Starting compile job for ", DebugLogger.quote(functionNode.getName()), " phases=", DebugLogger.quote(phases.getDesc()));
         this.log.indent();
      }

      String name = DebugLogger.quote(functionNode.getName());
      FunctionNode newFunctionNode = functionNode;
      String[] var5 = RESERVED_NAMES;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String reservedName = var5[var7];
         newFunctionNode.uniqueName(reservedName);
      }

      boolean info = this.log.isLoggable(Level.INFO);
      DebugLogger timeLogger = this.env.isTimingEnabled() ? this.env._timing.getLogger() : null;
      long time = 0L;

      CompilationPhase phase;
      for(Iterator var9 = phases.iterator(); var9.hasNext(); time += this.env.isTimingEnabled() ? phase.getEndTime() - phase.getStartTime() : 0L) {
         phase = (CompilationPhase)var9.next();
         this.log.fine(phase, " starting for ", name);

         try {
            newFunctionNode = phase.apply(this, phases, newFunctionNode);
         } catch (ParserException var12) {
            this.errors.error(var12);
            if (this.env._dump_on_error) {
               var12.printStackTrace(this.env.getErr());
            }

            return null;
         }

         this.log.fine(phase, " done for function ", DebugLogger.quote(name));
         if (this.env._print_mem_usage) {
            this.printMemoryUsage(functionNode, phase.toString());
         }
      }

      if (this.typeInformationFile != null && !phases.isRestOfCompilation()) {
         OptimisticTypesPersistence.store(this.typeInformationFile, this.invalidatedProgramPoints);
      }

      this.log.unindent();
      if (info) {
         StringBuilder sb = new StringBuilder("<< Finished compile job for ");
         sb.append(newFunctionNode.getSource()).append(':').append(DebugLogger.quote(newFunctionNode.getName()));
         if (time > 0L && timeLogger != null) {
            assert this.env.isTimingEnabled();

            sb.append(" in ").append(TimeUnit.NANOSECONDS.toMillis(time)).append(" ms");
         }

         this.log.info(sb);
      }

      return newFunctionNode;
   }

   Source getSource() {
      return this.source;
   }

   Map<String, byte[]> getBytecode() {
      return Collections.unmodifiableMap(this.bytecode);
   }

   void clearBytecode() {
      this.bytecode.clear();
   }

   CompileUnit getFirstCompileUnit() {
      assert !this.compileUnits.isEmpty();

      return (CompileUnit)this.compileUnits.iterator().next();
   }

   Set<CompileUnit> getCompileUnits() {
      return this.compileUnits;
   }

   ConstantData getConstantData() {
      return this.constantData;
   }

   CodeInstaller getCodeInstaller() {
      return this.installer;
   }

   void addClass(String name, byte[] code) {
      this.bytecode.put(name, code);
   }

   String nextCompileUnitName() {
      StringBuilder sb = new StringBuilder(32);
      sb.append(this.firstCompileUnitName);
      int cuid = this.nextCompileUnitId.getAndIncrement();
      if (cuid > 0) {
         sb.append("$cu").append(cuid);
      }

      return sb.toString();
   }

   public void persistClassInfo(String cacheKey, FunctionNode functionNode) {
      if (cacheKey != null && this.env._persistent_cache) {
         Map<Integer, FunctionInitializer> initializers = new HashMap();
         if (this.isOnDemandCompilation()) {
            initializers.put(functionNode.getId(), new FunctionInitializer(functionNode, this.getInvalidatedProgramPoints()));
         } else {
            Iterator var4 = this.getCompileUnits().iterator();

            while(var4.hasNext()) {
               CompileUnit compileUnit = (CompileUnit)var4.next();
               Iterator var6 = compileUnit.getFunctionNodes().iterator();

               while(var6.hasNext()) {
                  FunctionNode fn = (FunctionNode)var6.next();
                  initializers.put(fn.getId(), new FunctionInitializer(fn));
               }
            }
         }

         String mainClassName = this.getFirstCompileUnit().getUnitClassName();
         this.installer.storeScript(cacheKey, this.source, mainClassName, this.bytecode, initializers, this.constantData.toArray(), this.compilationId);
      }

   }

   public static void updateCompilationId(int value) {
      if (value >= COMPILATION_ID.get()) {
         COMPILATION_ID.set(value + 1);
      }

   }

   CompileUnit addCompileUnit(long initialWeight) {
      CompileUnit compileUnit = this.createCompileUnit(initialWeight);
      this.compileUnits.add(compileUnit);
      this.log.fine("Added compile unit ", compileUnit);
      return compileUnit;
   }

   CompileUnit createCompileUnit(String unitClassName, long initialWeight) {
      ClassEmitter classEmitter = new ClassEmitter(this.context, this.sourceName, unitClassName, this.isStrict());
      CompileUnit compileUnit = new CompileUnit(unitClassName, classEmitter, initialWeight);
      classEmitter.begin();
      return compileUnit;
   }

   private CompileUnit createCompileUnit(long initialWeight) {
      return this.createCompileUnit(this.nextCompileUnitName(), initialWeight);
   }

   boolean isStrict() {
      return this.strict;
   }

   void replaceCompileUnits(Set<CompileUnit> newUnits) {
      this.compileUnits.clear();
      this.compileUnits.addAll(newUnits);
   }

   CompileUnit findUnit(long weight) {
      Iterator var3 = this.compileUnits.iterator();

      CompileUnit unit;
      do {
         if (!var3.hasNext()) {
            return this.addCompileUnit(weight);
         }

         unit = (CompileUnit)var3.next();
      } while(!unit.canHold(weight));

      unit.addWeight(weight);
      return unit;
   }

   public static String binaryName(String name) {
      return name.replace('/', '.');
   }

   RecompilableScriptFunctionData getScriptFunctionData(int functionId) {
      assert this.compiledFunction != null;

      RecompilableScriptFunctionData fn = this.compiledFunction.getScriptFunctionData(functionId);

      assert fn != null : functionId;

      return fn;
   }

   boolean isGlobalSymbol(FunctionNode fn, String name) {
      return this.getScriptFunctionData(fn.getId()).isGlobalSymbol(fn, name);
   }

   int[] getContinuationEntryPoints() {
      return this.continuationEntryPoints;
   }

   Type getInvalidatedProgramPointType(int programPoint) {
      return (Type)this.invalidatedProgramPoints.get(programPoint);
   }

   private void printMemoryUsage(FunctionNode functionNode, String phaseName) {
      if (this.log.isEnabled()) {
         this.log.info(phaseName, "finished. Doing IR size calculation...");
         ObjectSizeCalculator osc = new ObjectSizeCalculator(ObjectSizeCalculator.getEffectiveMemoryLayoutSpecification());
         osc.calculateObjectSize(functionNode);
         List<ClassHistogramElement> list = osc.getClassHistogram();
         StringBuilder sb = new StringBuilder();
         long totalSize = osc.calculateObjectSize(functionNode);
         sb.append(phaseName).append(" Total size = ").append(totalSize / 1024L / 1024L).append("MB");
         this.log.info(sb);
         Collections.sort(list, new Comparator<ClassHistogramElement>() {
            public int compare(ClassHistogramElement o1, ClassHistogramElement o2) {
               long diff = o1.getBytes() - o2.getBytes();
               if (diff < 0L) {
                  return 1;
               } else {
                  return diff > 0L ? -1 : 0;
               }
            }
         });
         Iterator var8 = list.iterator();

         while(var8.hasNext()) {
            ClassHistogramElement e = (ClassHistogramElement)var8.next();
            String line = String.format("    %-48s %10d bytes (%8d instances)", e.getClazz(), e.getBytes(), e.getInstances());
            this.log.info(line);
            if (e.getBytes() < totalSize / 200L) {
               this.log.info("    ...");
               break;
            }
         }

      }
   }

   static {
      RESERVED_NAMES = new String[]{CompilerConstants.SCOPE.symbolName(), CompilerConstants.THIS.symbolName(), CompilerConstants.RETURN.symbolName(), CompilerConstants.CALLEE.symbolName(), CompilerConstants.VARARGS.symbolName(), CompilerConstants.ARGUMENTS.symbolName()};
      COMPILATION_ID = new AtomicInteger(0);
   }

   public static class CompilationPhases implements Iterable<CompilationPhase> {
      private static final Compiler.CompilationPhases COMPILE_UPTO_CACHED;
      private static final Compiler.CompilationPhases COMPILE_CACHED_UPTO_BYTECODE;
      public static final Compiler.CompilationPhases RECOMPILE_CACHED_UPTO_BYTECODE;
      public static final Compiler.CompilationPhases GENERATE_BYTECODE_AND_INSTALL;
      public static final Compiler.CompilationPhases COMPILE_UPTO_BYTECODE;
      public static final Compiler.CompilationPhases COMPILE_ALL_NO_INSTALL;
      public static final Compiler.CompilationPhases COMPILE_ALL;
      public static final Compiler.CompilationPhases COMPILE_ALL_CACHED;
      public static final Compiler.CompilationPhases GENERATE_BYTECODE_AND_INSTALL_RESTOF;
      public static final Compiler.CompilationPhases COMPILE_ALL_RESTOF;
      public static final Compiler.CompilationPhases COMPILE_CACHED_RESTOF;
      private final List<CompilationPhase> phases;
      private final String desc;

      private CompilationPhases(String desc, CompilationPhase... phases) {
         this(desc, Arrays.asList(phases));
      }

      private CompilationPhases(String desc, Compiler.CompilationPhases base, CompilationPhase... phases) {
         this(desc, concat(base.phases, Arrays.asList(phases)));
      }

      private CompilationPhases(String desc, CompilationPhase first, Compiler.CompilationPhases rest) {
         this(desc, concat(Collections.singletonList(first), rest.phases));
      }

      private CompilationPhases(String desc, Compiler.CompilationPhases base) {
         this(desc, base.phases);
      }

      private CompilationPhases(String desc, Compiler.CompilationPhases... bases) {
         this(desc, concatPhases(bases));
      }

      private CompilationPhases(String desc, List<CompilationPhase> phases) {
         this.desc = desc;
         this.phases = phases;
      }

      private static List<CompilationPhase> concatPhases(Compiler.CompilationPhases[] bases) {
         ArrayList<CompilationPhase> l = new ArrayList();
         Compiler.CompilationPhases[] var2 = bases;
         int var3 = bases.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Compiler.CompilationPhases base = var2[var4];
            l.addAll(base.phases);
         }

         l.trimToSize();
         return l;
      }

      private static <T> List<T> concat(List<T> l1, List<T> l2) {
         ArrayList<T> l = new ArrayList(l1);
         l.addAll(l2);
         l.trimToSize();
         return l;
      }

      public String toString() {
         return "'" + this.desc + "' " + this.phases.toString();
      }

      boolean contains(CompilationPhase phase) {
         return this.phases.contains(phase);
      }

      public Iterator<CompilationPhase> iterator() {
         return this.phases.iterator();
      }

      boolean isRestOfCompilation() {
         return this == COMPILE_ALL_RESTOF || this == GENERATE_BYTECODE_AND_INSTALL_RESTOF || this == COMPILE_CACHED_RESTOF;
      }

      String getDesc() {
         return this.desc;
      }

      String toString(String prefix) {
         StringBuilder sb = new StringBuilder();
         Iterator var3 = this.phases.iterator();

         while(var3.hasNext()) {
            CompilationPhase phase = (CompilationPhase)var3.next();
            sb.append(prefix).append(phase).append('\n');
         }

         return sb.toString();
      }

      static {
         COMPILE_UPTO_CACHED = new Compiler.CompilationPhases("Common initial phases", new CompilationPhase[]{CompilationPhase.CONSTANT_FOLDING_PHASE, CompilationPhase.LOWERING_PHASE, CompilationPhase.APPLY_SPECIALIZATION_PHASE, CompilationPhase.SPLITTING_PHASE, CompilationPhase.PROGRAM_POINT_PHASE, CompilationPhase.SYMBOL_ASSIGNMENT_PHASE, CompilationPhase.SCOPE_DEPTH_COMPUTATION_PHASE, CompilationPhase.CACHE_AST_PHASE});
         COMPILE_CACHED_UPTO_BYTECODE = new Compiler.CompilationPhases("After common phases, before bytecode generator", new CompilationPhase[]{CompilationPhase.OPTIMISTIC_TYPE_ASSIGNMENT_PHASE, CompilationPhase.LOCAL_VARIABLE_TYPE_CALCULATION_PHASE});
         RECOMPILE_CACHED_UPTO_BYTECODE = new Compiler.CompilationPhases("Recompile cached function up to bytecode", CompilationPhase.REINITIALIZE_CACHED, COMPILE_CACHED_UPTO_BYTECODE);
         GENERATE_BYTECODE_AND_INSTALL = new Compiler.CompilationPhases("Generate bytecode and install", new CompilationPhase[]{CompilationPhase.BYTECODE_GENERATION_PHASE, CompilationPhase.INSTALL_PHASE});
         COMPILE_UPTO_BYTECODE = new Compiler.CompilationPhases("Compile upto bytecode", new Compiler.CompilationPhases[]{COMPILE_UPTO_CACHED, COMPILE_CACHED_UPTO_BYTECODE});
         COMPILE_ALL_NO_INSTALL = new Compiler.CompilationPhases("Compile without install", COMPILE_UPTO_BYTECODE, new CompilationPhase[]{CompilationPhase.BYTECODE_GENERATION_PHASE});
         COMPILE_ALL = new Compiler.CompilationPhases("Full eager compilation", new Compiler.CompilationPhases[]{COMPILE_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL});
         COMPILE_ALL_CACHED = new Compiler.CompilationPhases("Eager compilation from serializaed state", new Compiler.CompilationPhases[]{RECOMPILE_CACHED_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL});
         GENERATE_BYTECODE_AND_INSTALL_RESTOF = new Compiler.CompilationPhases("Generate bytecode and install - RestOf method", CompilationPhase.REUSE_COMPILE_UNITS_PHASE, GENERATE_BYTECODE_AND_INSTALL);
         COMPILE_ALL_RESTOF = new Compiler.CompilationPhases("Compile all, rest of", new Compiler.CompilationPhases[]{COMPILE_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL_RESTOF});
         COMPILE_CACHED_RESTOF = new Compiler.CompilationPhases("Compile serialized, rest of", new Compiler.CompilationPhases[]{RECOMPILE_CACHED_UPTO_BYTECODE, GENERATE_BYTECODE_AND_INSTALL_RESTOF});
      }
   }
}
