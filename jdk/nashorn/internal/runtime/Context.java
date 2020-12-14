package jdk.nashorn.internal.runtime;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import javax.script.ScriptEngine;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.util.CheckClassAdapter;
import jdk.nashorn.api.scripting.ClassFilter;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.debug.ASTWriter;
import jdk.nashorn.internal.ir.debug.PrintVisitor;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.events.RuntimeEvent;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import jdk.nashorn.internal.runtime.options.Options;

public final class Context {
   public static final String NASHORN_SET_CONFIG = "nashorn.setConfig";
   public static final String NASHORN_CREATE_CONTEXT = "nashorn.createContext";
   public static final String NASHORN_CREATE_GLOBAL = "nashorn.createGlobal";
   public static final String NASHORN_GET_CONTEXT = "nashorn.getContext";
   public static final String NASHORN_JAVA_REFLECTION = "nashorn.JavaReflection";
   public static final String NASHORN_DEBUG_MODE = "nashorn.debugMode";
   private static final String LOAD_CLASSPATH = "classpath:";
   private static final String LOAD_FX = "fx:";
   private static final String LOAD_NASHORN = "nashorn:";
   private static Lookup LOOKUP = MethodHandles.lookup();
   private static MethodType CREATE_PROGRAM_FUNCTION_TYPE = MethodType.methodType(ScriptFunction.class, ScriptObject.class);
   private final Context.FieldMode fieldMode;
   private final Map<String, SwitchPoint> builtinSwitchPoints;
   public static final boolean DEBUG;
   private static final ThreadLocal<Global> currentGlobal;
   private Context.ClassCache classCache;
   private CodeStore codeStore;
   private final AtomicReference<GlobalConstants> globalConstantsRef;
   private final ScriptEnvironment env;
   final boolean _strict;
   private final ClassLoader appLoader;
   private final ScriptLoader scriptLoader;
   private final ErrorManager errors;
   private final AtomicLong uniqueScriptId;
   private final ClassFilter classFilter;
   private static final ClassLoader myLoader;
   private static final StructureLoader theStructLoader;
   private static final AccessControlContext NO_PERMISSIONS_ACC_CTXT;
   private static final AccessControlContext CREATE_LOADER_ACC_CTXT;
   private static final AccessControlContext CREATE_GLOBAL_ACC_CTXT;
   private final Map<String, DebugLogger> loggers;

   public static Global getGlobal() {
      return (Global)currentGlobal.get();
   }

   public static void setGlobal(ScriptObject global) {
      if (global != null && !(global instanceof Global)) {
         throw new IllegalArgumentException("not a global!");
      } else {
         setGlobal((Global)global);
      }
   }

   public static void setGlobal(Global global) {
      assert getGlobal() != global;

      if (global != null) {
         GlobalConstants globalConstants = getContext(global).getGlobalConstants();
         if (globalConstants != null) {
            globalConstants.invalidateAll();
         }
      }

      currentGlobal.set(global);
   }

   public static Context getContext() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("nashorn.getContext"));
      }

      return getContextTrusted();
   }

   public static PrintWriter getCurrentErr() {
      ScriptObject global = getGlobal();
      return global != null ? global.getContext().getErr() : new PrintWriter(System.err);
   }

   public static void err(String str) {
      err(str, true);
   }

   public static void err(String str, boolean crlf) {
      PrintWriter err = getCurrentErr();
      if (err != null) {
         if (crlf) {
            err.println(str);
         } else {
            err.print(str);
         }
      }

   }

   ClassLoader getAppLoader() {
      return this.appLoader;
   }

   ClassLoader getStructLoader() {
      return theStructLoader;
   }

   private static AccessControlContext createNoPermAccCtxt() {
      return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, new Permissions())});
   }

   private static AccessControlContext createPermAccCtxt(String permName) {
      Permissions perms = new Permissions();
      perms.add(new RuntimePermission(permName));
      return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, perms)});
   }

   public Context(Options options, ErrorManager errors, ClassLoader appLoader) {
      this(options, errors, appLoader, (ClassFilter)null);
   }

   public Context(Options options, ErrorManager errors, ClassLoader appLoader, ClassFilter classFilter) {
      this(options, errors, new PrintWriter(System.out, true), new PrintWriter(System.err, true), appLoader, classFilter);
   }

   public Context(Options options, ErrorManager errors, PrintWriter out, PrintWriter err, ClassLoader appLoader) {
      this(options, errors, out, err, appLoader, (ClassFilter)null);
   }

   public Context(Options options, ErrorManager errors, PrintWriter out, PrintWriter err, ClassLoader appLoader, ClassFilter classFilter) {
      this.builtinSwitchPoints = new HashMap();
      this.globalConstantsRef = new AtomicReference();
      this.loggers = new HashMap();
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(new RuntimePermission("nashorn.createContext"));
      }

      this.classFilter = classFilter;
      this.env = new ScriptEnvironment(options, out, err);
      this._strict = this.env._strict;
      if (this.env._loader_per_compile) {
         this.scriptLoader = null;
         this.uniqueScriptId = null;
      } else {
         this.scriptLoader = this.createNewLoader();
         this.uniqueScriptId = new AtomicLong();
      }

      this.errors = errors;
      String classPath = options.getString("classpath");
      if (!this.env._compile_only && classPath != null && !classPath.isEmpty()) {
         if (sm != null) {
            sm.checkCreateClassLoader();
         }

         this.appLoader = NashornLoader.createClassLoader(classPath, appLoader);
      } else {
         this.appLoader = appLoader;
      }

      int cacheSize = this.env._class_cache_size;
      if (cacheSize > 0) {
         this.classCache = new Context.ClassCache(cacheSize);
      }

      if (this.env._persistent_cache) {
         this.codeStore = CodeStore.newCodeStore(this);
      }

      if (this.env._version) {
         this.getErr().println("nashorn " + Version.version());
      }

      if (this.env._fullversion) {
         this.getErr().println("nashorn full version " + Version.fullVersion());
      }

      if (Options.getBooleanProperty("nashorn.fields.dual")) {
         this.fieldMode = Context.FieldMode.DUAL;
      } else if (Options.getBooleanProperty("nashorn.fields.objects")) {
         this.fieldMode = Context.FieldMode.OBJECTS;
      } else {
         this.fieldMode = Context.FieldMode.AUTO;
      }

      this.initLoggers();
   }

   public ClassFilter getClassFilter() {
      return this.classFilter;
   }

   GlobalConstants getGlobalConstants() {
      return (GlobalConstants)this.globalConstantsRef.get();
   }

   public ErrorManager getErrorManager() {
      return this.errors;
   }

   public ScriptEnvironment getEnv() {
      return this.env;
   }

   public PrintWriter getOut() {
      return this.env.getOut();
   }

   public PrintWriter getErr() {
      return this.env.getErr();
   }

   public boolean useDualFields() {
      return this.fieldMode == Context.FieldMode.DUAL || this.fieldMode == Context.FieldMode.AUTO && this.env._optimistic_types;
   }

   public static PropertyMap getGlobalMap() {
      return getGlobal().getMap();
   }

   public ScriptFunction compileScript(Source source, ScriptObject scope) {
      return this.compileScript(source, scope, this.errors);
   }

   public Context.MultiGlobalCompiledScript compileScript(Source source) {
      Class<?> clazz = this.compile(source, this.errors, this._strict);
      final MethodHandle createProgramFunctionHandle = getCreateProgramFunctionHandle(clazz);
      return new Context.MultiGlobalCompiledScript() {
         public ScriptFunction getFunction(Global newGlobal) {
            return Context.invokeCreateProgramFunctionHandle(createProgramFunctionHandle, newGlobal);
         }
      };
   }

   public Object eval(ScriptObject initialScope, String string, Object callThis, Object location) {
      return this.eval(initialScope, string, callThis, location, false, false);
   }

   public Object eval(ScriptObject initialScope, String string, Object callThis, Object location, boolean strict, boolean evalCall) {
      String file = location != ScriptRuntime.UNDEFINED && location != null ? location.toString() : "<eval>";
      Source source = Source.sourceFor(file, string, evalCall);
      boolean directEval = evalCall && location != ScriptRuntime.UNDEFINED;
      Global global = getGlobal();
      ScriptObject scope = initialScope;
      boolean strictFlag = strict || this._strict;
      Class clazz = null;

      try {
         clazz = this.compile(source, new Context.ThrowErrorManager(), strictFlag);
      } catch (ParserException var17) {
         var17.throwAsEcmaException(global);
         return null;
      }

      if (!strictFlag) {
         try {
            strictFlag = clazz.getField(CompilerConstants.STRICT_MODE.symbolName()).getBoolean((Object)null);
         } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var16) {
            strictFlag = false;
         }
      }

      if (strictFlag) {
         scope = newScope(initialScope);
      }

      ScriptFunction func = getProgramFunction(clazz, scope);
      Object evalThis;
      if (directEval) {
         evalThis = (callThis == ScriptRuntime.UNDEFINED || callThis == null) && !strictFlag ? global : callThis;
      } else {
         evalThis = callThis;
      }

      return ScriptRuntime.apply(func, evalThis);
   }

   private static ScriptObject newScope(ScriptObject callerScope) {
      return new Scope(callerScope, PropertyMap.newMap(Scope.class));
   }

   private static Source loadInternal(final String srcStr, String prefix, String resourcePath) {
      if (srcStr.startsWith(prefix)) {
         final String resource = resourcePath + srcStr.substring(prefix.length());
         return (Source)AccessController.doPrivileged(new PrivilegedAction<Source>() {
            public Source run() {
               try {
                  URL resURL = Context.class.getResource(resource);
                  return resURL != null ? Source.sourceFor(srcStr, resURL) : null;
               } catch (IOException var2) {
                  return null;
               }
            }
         });
      } else {
         return null;
      }
   }

   public Object load(Object scope, Object from) throws IOException {
      Object src = from instanceof ConsString ? from.toString() : from;
      Source source = null;
      ScriptObject sobj;
      if (src instanceof String) {
         String srcStr = (String)src;
         if (srcStr.startsWith("classpath:")) {
            URL url = this.getResourceURL(srcStr.substring(10));
            source = url != null ? Source.sourceFor(url.toString(), url) : null;
         } else {
            File file = new File(srcStr);
            if (srcStr.indexOf(58) != -1) {
               if ((source = loadInternal(srcStr, "nashorn:", "resources/")) == null && (source = loadInternal(srcStr, "fx:", "resources/fx/")) == null) {
                  URL url;
                  try {
                     url = new URL(srcStr);
                  } catch (MalformedURLException var9) {
                     url = file.toURI().toURL();
                  }

                  source = Source.sourceFor(url.toString(), url);
               }
            } else if (file.isFile()) {
               source = Source.sourceFor(srcStr, file);
            }
         }
      } else if (src instanceof File && ((File)src).isFile()) {
         File file = (File)src;
         source = Source.sourceFor(file.getName(), file);
      } else if (src instanceof URL) {
         URL url = (URL)src;
         source = Source.sourceFor(url.toString(), url);
      } else {
         String script;
         String name;
         if (src instanceof ScriptObject) {
            sobj = (ScriptObject)src;
            if (sobj.has("script") && sobj.has("name")) {
               script = JSType.toString(sobj.get("script"));
               name = JSType.toString(sobj.get("name"));
               source = Source.sourceFor(name, script);
            }
         } else if (src instanceof Map) {
            Map<?, ?> map = (Map)src;
            if (map.containsKey("script") && map.containsKey("name")) {
               script = JSType.toString(map.get("script"));
               name = JSType.toString(map.get("name"));
               source = Source.sourceFor(name, script);
            }
         }
      }

      if (source != null) {
         if (scope instanceof ScriptObject && ((ScriptObject)scope).isScope()) {
            sobj = (ScriptObject)scope;

            assert sobj.isGlobal() : "non-Global scope object!!";

            return this.evaluateSource(source, sobj, sobj);
         } else {
            Global global;
            if (scope != null && scope != ScriptRuntime.UNDEFINED) {
               global = getGlobal();
               ScriptObject evalScope = newScope(global);
               ScriptObject withObj = ScriptRuntime.openWith(evalScope, scope);
               return this.evaluateSource(source, withObj, global);
            } else {
               global = getGlobal();
               return this.evaluateSource(source, global, global);
            }
         }
      } else {
         throw ECMAErrors.typeError("cant.load.script", ScriptRuntime.safeToString(from));
      }
   }

   public Object loadWithNewGlobal(Object from, Object... args) throws IOException {
      Global oldGlobal = getGlobal();
      Global newGlobal = (Global)AccessController.doPrivileged(new PrivilegedAction<Global>() {
         public Global run() {
            try {
               return Context.this.newGlobal();
            } catch (RuntimeException var2) {
               if (Context.DEBUG) {
                  var2.printStackTrace();
               }

               throw var2;
            }
         }
      }, CREATE_GLOBAL_ACC_CTXT);
      this.initGlobal(newGlobal);
      setGlobal(newGlobal);
      Object[] wrapped = args == null ? ScriptRuntime.EMPTY_ARRAY : ScriptObjectMirror.wrapArray(args, oldGlobal);
      newGlobal.put("arguments", newGlobal.wrapAsObject(wrapped), this.env._strict);

      Object var6;
      try {
         var6 = ScriptObjectMirror.unwrap(ScriptObjectMirror.wrap(this.load(newGlobal, from), newGlobal), oldGlobal);
      } finally {
         setGlobal(oldGlobal);
      }

      return var6;
   }

   public static Class<? extends ScriptObject> forStructureClass(String fullName) throws ClassNotFoundException {
      if (System.getSecurityManager() != null && !StructureLoader.isStructureClass(fullName)) {
         throw new ClassNotFoundException(fullName);
      } else {
         return Class.forName(fullName, true, theStructLoader);
      }
   }

   public static void checkPackageAccess(Class<?> clazz) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         Class bottomClazz;
         for(bottomClazz = clazz; bottomClazz.isArray(); bottomClazz = bottomClazz.getComponentType()) {
         }

         checkPackageAccess(sm, bottomClazz.getName());
      }

   }

   public static void checkPackageAccess(String pkgName) {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         checkPackageAccess(sm, pkgName.endsWith(".") ? pkgName : pkgName + ".");
      }

   }

   private static void checkPackageAccess(final SecurityManager sm, String fullName) {
      Objects.requireNonNull(sm);
      int index = fullName.lastIndexOf(46);
      if (index != -1) {
         final String pkgName = fullName.substring(0, index);
         AccessController.doPrivileged(new PrivilegedAction<Void>() {
            public Void run() {
               sm.checkPackageAccess(pkgName);
               return null;
            }
         }, NO_PERMISSIONS_ACC_CTXT);
      }

   }

   public static boolean isStructureClass(String className) {
      return StructureLoader.isStructureClass(className);
   }

   private static boolean isAccessiblePackage(Class<?> clazz) {
      try {
         checkPackageAccess(clazz);
         return true;
      } catch (SecurityException var2) {
         return false;
      }
   }

   public static boolean isAccessibleClass(Class<?> clazz) {
      return Modifier.isPublic(clazz.getModifiers()) && isAccessiblePackage(clazz);
   }

   public Class<?> findClass(String fullName) throws ClassNotFoundException {
      if (fullName.indexOf(91) == -1 && fullName.indexOf(47) == -1) {
         if (this.classFilter != null && !this.classFilter.exposeToScripts(fullName)) {
            throw new ClassNotFoundException(fullName);
         } else {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
               checkPackageAccess(sm, fullName);
            }

            if (this.appLoader != null) {
               return Class.forName(fullName, true, this.appLoader);
            } else {
               Class<?> cl = Class.forName(fullName);
               if (cl.getClassLoader() == null) {
                  return cl;
               } else {
                  throw new ClassNotFoundException(fullName);
               }
            }
         }
      } else {
         throw new ClassNotFoundException(fullName);
      }
   }

   public static void printStackTrace(Throwable t) {
      if (DEBUG) {
         t.printStackTrace(getCurrentErr());
      }

   }

   public void verify(byte[] bytecode) {
      if (this.env._verify_code && System.getSecurityManager() == null) {
         CheckClassAdapter.verify(new ClassReader(bytecode), theStructLoader, false, new PrintWriter(System.err, true));
      }

   }

   public Global createGlobal() {
      return this.initGlobal(this.newGlobal());
   }

   public Global newGlobal() {
      this.createOrInvalidateGlobalConstants();
      return new Global(this);
   }

   private void createOrInvalidateGlobalConstants() {
      GlobalConstants newGlobalConstants;
      do {
         GlobalConstants currentGlobalConstants = this.getGlobalConstants();
         if (currentGlobalConstants != null) {
            currentGlobalConstants.invalidateForever();
            return;
         }

         newGlobalConstants = new GlobalConstants(this.getLogger(GlobalConstants.class));
      } while(!this.globalConstantsRef.compareAndSet((Object)null, newGlobalConstants));

   }

   public Global initGlobal(Global global, ScriptEngine engine) {
      if (!this.env._compile_only) {
         Global oldGlobal = getGlobal();

         try {
            setGlobal(global);
            global.initBuiltinObjects(engine);
         } finally {
            setGlobal(oldGlobal);
         }
      }

      return global;
   }

   public Global initGlobal(Global global) {
      return this.initGlobal(global, (ScriptEngine)null);
   }

   static Context getContextTrusted() {
      return getContext(getGlobal());
   }

   static Context getContextTrustedOrNull() {
      Global global = getGlobal();
      return global == null ? null : getContext(global);
   }

   private static Context getContext(Global global) {
      return global.getContext();
   }

   static Context fromClass(Class<?> clazz) {
      ClassLoader loader = clazz.getClassLoader();
      return loader instanceof ScriptLoader ? ((ScriptLoader)loader).getContext() : getContextTrusted();
   }

   private URL getResourceURL(String resName) {
      return this.appLoader != null ? this.appLoader.getResource(resName) : ClassLoader.getSystemResource(resName);
   }

   private Object evaluateSource(Source source, ScriptObject scope, ScriptObject thiz) {
      ScriptFunction script = null;

      try {
         script = this.compileScript(source, scope, new Context.ThrowErrorManager());
      } catch (ParserException var6) {
         var6.throwAsEcmaException();
      }

      return ScriptRuntime.apply(script, thiz);
   }

   private static ScriptFunction getProgramFunction(Class<?> script, ScriptObject scope) {
      return script == null ? null : invokeCreateProgramFunctionHandle(getCreateProgramFunctionHandle(script), scope);
   }

   private static MethodHandle getCreateProgramFunctionHandle(Class<?> script) {
      try {
         return LOOKUP.findStatic(script, CompilerConstants.CREATE_PROGRAM_FUNCTION.symbolName(), CREATE_PROGRAM_FUNCTION_TYPE);
      } catch (IllegalAccessException | NoSuchMethodException var2) {
         throw new AssertionError("Failed to retrieve a handle for the program function for " + script.getName(), var2);
      }
   }

   private static ScriptFunction invokeCreateProgramFunctionHandle(MethodHandle createProgramFunctionHandle, ScriptObject scope) {
      try {
         return createProgramFunctionHandle.invokeExact(scope);
      } catch (Error | RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new AssertionError("Failed to create a program function", var4);
      }
   }

   private ScriptFunction compileScript(Source source, ScriptObject scope, ErrorManager errMan) {
      return getProgramFunction(this.compile(source, errMan, this._strict), scope);
   }

   private synchronized Class<?> compile(Source source, ErrorManager errMan, boolean strict) {
      errMan.reset();
      Class<?> script = this.findCachedClass(source);
      if (script != null) {
         DebugLogger log = this.getLogger(Compiler.class);
         if (log.isEnabled()) {
            log.fine(new RuntimeEvent(Level.INFO, source), "Code cache hit for ", source, " avoiding recompile.");
         }

         return script;
      } else {
         StoredScript storedScript = null;
         FunctionNode functionNode = null;
         boolean useCodeStore = this.codeStore != null && !this.env._parse_only && (!this.env._optimistic_types || this.env._lazy_compilation);
         String cacheKey = useCodeStore ? CodeStore.getCacheKey("script", (Type[])null) : null;
         if (useCodeStore) {
            storedScript = this.codeStore.load(source, cacheKey);
         }

         if (storedScript == null) {
            if (this.env._dest_dir != null) {
               source.dump(this.env._dest_dir);
            }

            functionNode = (new Parser(this.env, source, errMan, strict, this.getLogger(Parser.class))).parse();
            if (errMan.hasErrors()) {
               return null;
            }

            if (this.env._print_ast || functionNode.getFlag(524288)) {
               this.getErr().println(new ASTWriter(functionNode));
            }

            if (this.env._print_parse || functionNode.getFlag(131072)) {
               this.getErr().println(new PrintVisitor(functionNode, true, false));
            }
         }

         if (this.env._parse_only) {
            return null;
         } else {
            URL url = source.getURL();
            ScriptLoader loader = this.env._loader_per_compile ? this.createNewLoader() : this.scriptLoader;
            CodeSource cs = new CodeSource(url, (CodeSigner[])null);
            CodeInstaller installer = new Context.ContextCodeInstaller(this, loader, cs);
            if (storedScript == null) {
               Compiler.CompilationPhases phases = Compiler.CompilationPhases.COMPILE_ALL;
               Compiler compiler = Compiler.forInitialCompilation(installer, source, errMan, strict | functionNode.isStrict());
               FunctionNode compiledFunction = compiler.compile(functionNode, phases);
               if (errMan.hasErrors()) {
                  return null;
               }

               script = compiledFunction.getRootClass();
               compiler.persistClassInfo(cacheKey, compiledFunction);
            } else {
               Compiler.updateCompilationId(storedScript.getCompilationId());
               script = storedScript.installScript(source, installer);
            }

            this.cacheClass(source, script);
            return script;
         }
      }
   }

   private ScriptLoader createNewLoader() {
      return (ScriptLoader)AccessController.doPrivileged(new PrivilegedAction<ScriptLoader>() {
         public ScriptLoader run() {
            return new ScriptLoader(Context.this);
         }
      }, CREATE_LOADER_ACC_CTXT);
   }

   private long getUniqueScriptId() {
      return this.uniqueScriptId.getAndIncrement();
   }

   private Class<?> findCachedClass(Source source) {
      Context.ClassReference ref = this.classCache == null ? null : this.classCache.get(source);
      return ref != null ? (Class)ref.get() : null;
   }

   private void cacheClass(Source source, Class<?> clazz) {
      if (this.classCache != null) {
         this.classCache.cache(source, clazz);
      }

   }

   private void initLoggers() {
      ((Loggable)MethodHandleFactory.getFunctionality()).initLogger(this);
   }

   public DebugLogger getLogger(Class<? extends Loggable> clazz) {
      return this.getLogger(clazz, (Consumer)null);
   }

   public DebugLogger getLogger(Class<? extends Loggable> clazz, Consumer<DebugLogger> initHook) {
      String name = getLoggerName(clazz);
      DebugLogger logger = (DebugLogger)this.loggers.get(name);
      if (logger == null) {
         if (!this.env.hasLogger(name)) {
            return DebugLogger.DISABLED_LOGGER;
         }

         LoggingOption.LoggerInfo info = (LoggingOption.LoggerInfo)this.env._loggers.get(name);
         logger = new DebugLogger(name, info.getLevel(), info.isQuiet());
         if (initHook != null) {
            initHook.accept(logger);
         }

         this.loggers.put(name, logger);
      }

      return logger;
   }

   public MethodHandle addLoggingToHandle(Class<? extends Loggable> clazz, MethodHandle mh, Supplier<String> text) {
      return this.addLoggingToHandle(clazz, Level.INFO, mh, Integer.MAX_VALUE, false, text);
   }

   public MethodHandle addLoggingToHandle(Class<? extends Loggable> clazz, Level level, MethodHandle mh, int paramStart, boolean printReturnValue, Supplier<String> text) {
      DebugLogger log = this.getLogger(clazz);
      return log.isEnabled() ? MethodHandleFactory.addDebugPrintout(log, level, mh, paramStart, printReturnValue, text.get()) : mh;
   }

   private static String getLoggerName(Class<?> clazz) {
      for(Class current = clazz; current != null; current = current.getSuperclass()) {
         Logger log = (Logger)current.getAnnotation(Logger.class);
         if (log != null) {
            assert !"".equals(log.name());

            return log.name();
         }
      }

      assert false;

      return null;
   }

   public SwitchPoint newBuiltinSwitchPoint(String name) {
      assert this.builtinSwitchPoints.get(name) == null;

      SwitchPoint sp = new Context.BuiltinSwitchPoint();
      this.builtinSwitchPoints.put(name, sp);
      return sp;
   }

   public SwitchPoint getBuiltinSwitchPoint(String name) {
      return (SwitchPoint)this.builtinSwitchPoints.get(name);
   }

   static {
      DebuggerSupport.FORCELOAD = true;
      DEBUG = Options.getBooleanProperty("nashorn.debug");
      currentGlobal = new ThreadLocal();
      myLoader = Context.class.getClassLoader();
      NO_PERMISSIONS_ACC_CTXT = createNoPermAccCtxt();
      CREATE_LOADER_ACC_CTXT = createPermAccCtxt("createClassLoader");
      CREATE_GLOBAL_ACC_CTXT = createPermAccCtxt("nashorn.createGlobal");
      theStructLoader = (StructureLoader)AccessController.doPrivileged(new PrivilegedAction<StructureLoader>() {
         public StructureLoader run() {
            return new StructureLoader(Context.myLoader);
         }
      }, CREATE_LOADER_ACC_CTXT);
   }

   public static final class BuiltinSwitchPoint extends SwitchPoint {
   }

   private static class ClassReference extends SoftReference<Class<?>> {
      private final Source source;

      ClassReference(Class<?> clazz, ReferenceQueue<Class<?>> queue, Source source) {
         super(clazz, queue);
         this.source = source;
      }
   }

   private static class ClassCache extends LinkedHashMap<Source, Context.ClassReference> {
      private final int size;
      private final ReferenceQueue<Class<?>> queue;

      ClassCache(int size) {
         super(size, 0.75F, true);
         this.size = size;
         this.queue = new ReferenceQueue();
      }

      void cache(Source source, Class<?> clazz) {
         this.put(source, new Context.ClassReference(clazz, this.queue, source));
      }

      protected boolean removeEldestEntry(Entry<Source, Context.ClassReference> eldest) {
         return this.size() > this.size;
      }

      public Context.ClassReference get(Object key) {
         Context.ClassReference ref;
         while((ref = (Context.ClassReference)this.queue.poll()) != null) {
            this.remove(ref.source);
         }

         return (Context.ClassReference)super.get(key);
      }
   }

   public interface MultiGlobalCompiledScript {
      ScriptFunction getFunction(Global var1);
   }

   public static class ThrowErrorManager extends ErrorManager {
      public void error(String message) {
         throw new ParserException(message);
      }

      public void error(ParserException e) {
         throw e;
      }
   }

   public static class ContextCodeInstaller implements CodeInstaller {
      private final Context context;
      private final ScriptLoader loader;
      private final CodeSource codeSource;
      private int usageCount;
      private int bytesDefined;
      private static final int MAX_USAGES = 10;
      private static final int MAX_BYTES_DEFINED = 200000;

      private ContextCodeInstaller(Context context, ScriptLoader loader, CodeSource codeSource) {
         this.usageCount = 0;
         this.bytesDefined = 0;
         this.context = context;
         this.loader = loader;
         this.codeSource = codeSource;
      }

      public Context getContext() {
         return this.context;
      }

      public Class<?> install(String className, byte[] bytecode) {
         ++this.usageCount;
         this.bytesDefined += bytecode.length;
         String binaryName = Compiler.binaryName(className);
         return this.loader.installClass(binaryName, bytecode, this.codeSource);
      }

      public void initialize(final Collection<Class<?>> classes, final Source source, final Object[] constants) {
         try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
               public Void run() throws Exception {
                  Iterator var1 = classes.iterator();

                  while(var1.hasNext()) {
                     Class<?> clazz = (Class)var1.next();
                     Field sourceField = clazz.getDeclaredField(CompilerConstants.SOURCE.symbolName());
                     sourceField.setAccessible(true);
                     sourceField.set((Object)null, source);
                     Field constantsField = clazz.getDeclaredField(CompilerConstants.CONSTANTS.symbolName());
                     constantsField.setAccessible(true);
                     constantsField.set((Object)null, constants);
                  }

                  return null;
               }
            });
         } catch (PrivilegedActionException var5) {
            throw new RuntimeException(var5);
         }
      }

      public void verify(byte[] code) {
         this.context.verify(code);
      }

      public long getUniqueScriptId() {
         return this.context.getUniqueScriptId();
      }

      public void storeScript(String cacheKey, Source source, String mainClassName, Map<String, byte[]> classBytes, Map<Integer, FunctionInitializer> initializers, Object[] constants, int compilationId) {
         if (this.context.codeStore != null) {
            this.context.codeStore.store(cacheKey, source, mainClassName, classBytes, initializers, constants, compilationId);
         }

      }

      public StoredScript loadScript(Source source, String functionKey) {
         return this.context.codeStore != null ? this.context.codeStore.load(source, functionKey) : null;
      }

      public CodeInstaller withNewLoader() {
         return this.usageCount < 10 && this.bytesDefined < 200000 ? this : new Context.ContextCodeInstaller(this.context, this.context.createNewLoader(), this.codeSource);
      }

      public boolean isCompatibleWith(CodeInstaller other) {
         if (!(other instanceof Context.ContextCodeInstaller)) {
            return false;
         } else {
            Context.ContextCodeInstaller cci = (Context.ContextCodeInstaller)other;
            return cci.context == this.context && cci.codeSource == this.codeSource;
         }
      }

      // $FF: synthetic method
      ContextCodeInstaller(Context x0, ScriptLoader x1, CodeSource x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static enum FieldMode {
      AUTO,
      OBJECTS,
      DUAL;
   }
}
