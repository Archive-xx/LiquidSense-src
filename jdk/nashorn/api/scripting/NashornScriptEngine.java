package jdk.nashorn.api.scripting;

import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import jdk.Exported;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;
import jdk.nashorn.internal.runtime.options.Options;

@Exported
public final class NashornScriptEngine extends AbstractScriptEngine implements Compilable, Invocable {
   public static final String NASHORN_GLOBAL = "nashorn.global";
   private static final AccessControlContext CREATE_CONTEXT_ACC_CTXT = createPermAccCtxt("nashorn.createContext");
   private static final AccessControlContext CREATE_GLOBAL_ACC_CTXT = createPermAccCtxt("nashorn.createGlobal");
   private final ScriptEngineFactory factory;
   private final Context nashornContext;
   private final boolean _global_per_engine;
   private final Global global;
   private static final String MESSAGES_RESOURCE = "jdk.nashorn.api.scripting.resources.Messages";
   private static final ResourceBundle MESSAGES_BUNDLE = ResourceBundle.getBundle("jdk.nashorn.api.scripting.resources.Messages", Locale.getDefault());

   private static AccessControlContext createPermAccCtxt(String permName) {
      Permissions perms = new Permissions();
      perms.add(new RuntimePermission(permName));
      return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain((CodeSource)null, perms)});
   }

   private static String getMessage(String msgId, String... args) {
      try {
         return (new MessageFormat(MESSAGES_BUNDLE.getString(msgId))).format(args);
      } catch (MissingResourceException var3) {
         throw new RuntimeException("no message resource found for message id: " + msgId);
      }
   }

   NashornScriptEngine(NashornScriptEngineFactory factory, String[] args, final ClassLoader appLoader, final ClassFilter classFilter) {
      assert args != null : "null argument array";

      this.factory = factory;
      final Options options = new Options("nashorn");
      options.process(args);
      final ErrorManager errMgr = new Context.ThrowErrorManager();
      this.nashornContext = (Context)AccessController.doPrivileged(new PrivilegedAction<Context>() {
         public Context run() {
            try {
               return new Context(options, errMgr, appLoader, classFilter);
            } catch (RuntimeException var2) {
               if (Context.DEBUG) {
                  var2.printStackTrace();
               }

               throw var2;
            }
         }
      }, CREATE_CONTEXT_ACC_CTXT);
      this._global_per_engine = this.nashornContext.getEnv()._global_per_engine;
      this.global = this.createNashornGlobal();
      this.context.setBindings(new ScriptObjectMirror(this.global, this.global), 100);
   }

   public Object eval(Reader reader, ScriptContext ctxt) throws ScriptException {
      return this.evalImpl(makeSource(reader, ctxt), ctxt);
   }

   public Object eval(String script, ScriptContext ctxt) throws ScriptException {
      return this.evalImpl(makeSource(script, ctxt), ctxt);
   }

   public ScriptEngineFactory getFactory() {
      return this.factory;
   }

   public Bindings createBindings() {
      return (Bindings)(this._global_per_engine ? new SimpleBindings() : this.createGlobalMirror());
   }

   public CompiledScript compile(Reader reader) throws ScriptException {
      return this.asCompiledScript(makeSource(reader, this.context));
   }

   public CompiledScript compile(String str) throws ScriptException {
      return this.asCompiledScript(makeSource(str, this.context));
   }

   public Object invokeFunction(String name, Object... args) throws ScriptException, NoSuchMethodException {
      return this.invokeImpl((Object)null, name, args);
   }

   public Object invokeMethod(Object thiz, String name, Object... args) throws ScriptException, NoSuchMethodException {
      if (thiz == null) {
         throw new IllegalArgumentException(getMessage("thiz.cannot.be.null"));
      } else {
         return this.invokeImpl(thiz, name, args);
      }
   }

   public <T> T getInterface(Class<T> clazz) {
      return this.getInterfaceInner((Object)null, clazz);
   }

   public <T> T getInterface(Object thiz, Class<T> clazz) {
      if (thiz == null) {
         throw new IllegalArgumentException(getMessage("thiz.cannot.be.null"));
      } else {
         return this.getInterfaceInner(thiz, clazz);
      }
   }

   private static Source makeSource(Reader reader, ScriptContext ctxt) throws ScriptException {
      try {
         return Source.sourceFor(getScriptName(ctxt), reader);
      } catch (IOException var3) {
         throw new ScriptException(var3);
      }
   }

   private static Source makeSource(String src, ScriptContext ctxt) {
      return Source.sourceFor(getScriptName(ctxt), src);
   }

   private static String getScriptName(ScriptContext ctxt) {
      Object val = ctxt.getAttribute("javax.script.filename");
      return val != null ? val.toString() : "<eval>";
   }

   private <T> T getInterfaceInner(Object thiz, Class<T> clazz) {
      assert !(thiz instanceof ScriptObject) : "raw ScriptObject not expected here";

      if (clazz != null && clazz.isInterface()) {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            if (!Modifier.isPublic(clazz.getModifiers())) {
               throw new SecurityException(getMessage("implementing.non.public.interface", clazz.getName()));
            }

            Context.checkPackageAccess(clazz);
         }

         ScriptObject realSelf = null;
         Global realGlobal = null;
         if (thiz == null) {
            realSelf = realGlobal = this.getNashornGlobalFrom(this.context);
         } else if (thiz instanceof ScriptObjectMirror) {
            ScriptObjectMirror mirror = (ScriptObjectMirror)thiz;
            realSelf = mirror.getScriptObject();
            realGlobal = mirror.getHomeGlobal();
            if (!isOfContext(realGlobal, this.nashornContext)) {
               throw new IllegalArgumentException(getMessage("script.object.from.another.engine"));
            }
         }

         if (realSelf == null) {
            throw new IllegalArgumentException(getMessage("interface.on.non.script.object"));
         } else {
            try {
               Global oldGlobal = Context.getGlobal();
               boolean globalChanged = oldGlobal != realGlobal;

               Object var8;
               try {
                  if (globalChanged) {
                     Context.setGlobal(realGlobal);
                  }

                  if (isInterfaceImplemented(clazz, (ScriptObject)realSelf)) {
                     var8 = clazz.cast(JavaAdapterFactory.getConstructor(realSelf.getClass(), clazz, MethodHandles.publicLookup()).invoke((ScriptObject)realSelf));
                     return var8;
                  }

                  var8 = null;
               } finally {
                  if (globalChanged) {
                     Context.setGlobal(oldGlobal);
                  }

               }

               return var8;
            } catch (Error | RuntimeException var14) {
               throw var14;
            } catch (Throwable var15) {
               throw new RuntimeException(var15);
            }
         }
      } else {
         throw new IllegalArgumentException(getMessage("interface.class.expected"));
      }
   }

   private Global getNashornGlobalFrom(ScriptContext ctxt) {
      if (this._global_per_engine) {
         return this.global;
      } else {
         Bindings bindings = ctxt.getBindings(100);
         if (bindings instanceof ScriptObjectMirror) {
            Global glob = this.globalFromMirror((ScriptObjectMirror)bindings);
            if (glob != null) {
               return glob;
            }
         }

         Object scope = bindings.get("nashorn.global");
         if (scope instanceof ScriptObjectMirror) {
            Global glob = this.globalFromMirror((ScriptObjectMirror)scope);
            if (glob != null) {
               return glob;
            }
         }

         ScriptObjectMirror mirror = this.createGlobalMirror();
         bindings.put("nashorn.global", mirror);
         mirror.getHomeGlobal().setInitScriptContext(ctxt);
         return mirror.getHomeGlobal();
      }
   }

   private Global globalFromMirror(ScriptObjectMirror mirror) {
      ScriptObject sobj = mirror.getScriptObject();
      return sobj instanceof Global && isOfContext((Global)sobj, this.nashornContext) ? (Global)sobj : null;
   }

   private ScriptObjectMirror createGlobalMirror() {
      Global newGlobal = this.createNashornGlobal();
      return new ScriptObjectMirror(newGlobal, newGlobal);
   }

   private Global createNashornGlobal() {
      Global newGlobal = (Global)AccessController.doPrivileged(new PrivilegedAction<Global>() {
         public Global run() {
            try {
               return NashornScriptEngine.this.nashornContext.newGlobal();
            } catch (RuntimeException var2) {
               if (Context.DEBUG) {
                  var2.printStackTrace();
               }

               throw var2;
            }
         }
      }, CREATE_GLOBAL_ACC_CTXT);
      this.nashornContext.initGlobal(newGlobal, this);
      return newGlobal;
   }

   private Object invokeImpl(Object selfObject, String name, Object... args) throws ScriptException, NoSuchMethodException {
      Objects.requireNonNull(name);

      assert !(selfObject instanceof ScriptObject) : "raw ScriptObject not expected here";

      Global invokeGlobal = null;
      ScriptObjectMirror selfMirror = null;
      if (selfObject instanceof ScriptObjectMirror) {
         selfMirror = (ScriptObjectMirror)selfObject;
         if (!isOfContext(selfMirror.getHomeGlobal(), this.nashornContext)) {
            throw new IllegalArgumentException(getMessage("script.object.from.another.engine"));
         }

         invokeGlobal = selfMirror.getHomeGlobal();
      } else if (selfObject == null) {
         Global ctxtGlobal = this.getNashornGlobalFrom(this.context);
         invokeGlobal = ctxtGlobal;
         selfMirror = (ScriptObjectMirror)ScriptObjectMirror.wrap(ctxtGlobal, ctxtGlobal);
      }

      if (selfMirror != null) {
         try {
            return ScriptObjectMirror.translateUndefined(selfMirror.callMember(name, args));
         } catch (Exception var8) {
            Throwable cause = var8.getCause();
            if (cause instanceof NoSuchMethodException) {
               throw (NoSuchMethodException)cause;
            } else {
               throwAsScriptException(var8, invokeGlobal);
               throw new AssertionError("should not reach here");
            }
         }
      } else {
         throw new IllegalArgumentException(getMessage("interface.on.non.script.object"));
      }
   }

   private Object evalImpl(Source src, ScriptContext ctxt) throws ScriptException {
      return this.evalImpl(this.compileImpl(src, ctxt), ctxt);
   }

   private Object evalImpl(ScriptFunction script, ScriptContext ctxt) throws ScriptException {
      return this.evalImpl(script, ctxt, this.getNashornGlobalFrom(ctxt));
   }

   private Object evalImpl(Context.MultiGlobalCompiledScript mgcs, ScriptContext ctxt, Global ctxtGlobal) throws ScriptException {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != ctxtGlobal;

      Object var8;
      try {
         if (globalChanged) {
            Context.setGlobal(ctxtGlobal);
         }

         ScriptFunction script = mgcs.getFunction(ctxtGlobal);
         ScriptContext oldCtxt = ctxtGlobal.getScriptContext();
         ctxtGlobal.setScriptContext(ctxt);

         try {
            var8 = ScriptObjectMirror.translateUndefined(ScriptObjectMirror.wrap(ScriptRuntime.apply(script, ctxtGlobal), ctxtGlobal));
         } finally {
            ctxtGlobal.setScriptContext(oldCtxt);
         }
      } catch (Exception var18) {
         throwAsScriptException(var18, ctxtGlobal);
         throw new AssertionError("should not reach here");
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return var8;
   }

   private Object evalImpl(ScriptFunction script, ScriptContext ctxt, Global ctxtGlobal) throws ScriptException {
      if (script == null) {
         return null;
      } else {
         Global oldGlobal = Context.getGlobal();
         boolean globalChanged = oldGlobal != ctxtGlobal;

         Object var7;
         try {
            if (globalChanged) {
               Context.setGlobal(ctxtGlobal);
            }

            ScriptContext oldCtxt = ctxtGlobal.getScriptContext();
            ctxtGlobal.setScriptContext(ctxt);

            try {
               var7 = ScriptObjectMirror.translateUndefined(ScriptObjectMirror.wrap(ScriptRuntime.apply(script, ctxtGlobal), ctxtGlobal));
            } finally {
               ctxtGlobal.setScriptContext(oldCtxt);
            }
         } catch (Exception var17) {
            throwAsScriptException(var17, ctxtGlobal);
            throw new AssertionError("should not reach here");
         } finally {
            if (globalChanged) {
               Context.setGlobal(oldGlobal);
            }

         }

         return var7;
      }
   }

   private static void throwAsScriptException(Exception e, Global global) throws ScriptException {
      if (e instanceof ScriptException) {
         throw (ScriptException)e;
      } else if (e instanceof NashornException) {
         NashornException ne = (NashornException)e;
         ScriptException se = new ScriptException(ne.getMessage(), ne.getFileName(), ne.getLineNumber(), ne.getColumnNumber());
         ne.initEcmaError(global);
         se.initCause(e);
         throw se;
      } else if (e instanceof RuntimeException) {
         throw (RuntimeException)e;
      } else {
         throw new ScriptException(e);
      }
   }

   private CompiledScript asCompiledScript(Source source) throws ScriptException {
      Global oldGlobal = Context.getGlobal();
      Global newGlobal = this.getNashornGlobalFrom(this.context);
      boolean globalChanged = oldGlobal != newGlobal;

      final Context.MultiGlobalCompiledScript mgcs;
      final ScriptFunction func;
      try {
         if (globalChanged) {
            Context.setGlobal(newGlobal);
         }

         mgcs = this.nashornContext.compileScript(source);
         func = mgcs.getFunction(newGlobal);
      } catch (Exception var11) {
         throwAsScriptException(var11, newGlobal);
         throw new AssertionError("should not reach here");
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return new CompiledScript() {
         public Object eval(ScriptContext ctxt) throws ScriptException {
            Global globalObject = NashornScriptEngine.this.getNashornGlobalFrom(ctxt);
            return func.getScope() == globalObject ? NashornScriptEngine.this.evalImpl(func, ctxt, globalObject) : NashornScriptEngine.this.evalImpl(mgcs, ctxt, globalObject);
         }

         public ScriptEngine getEngine() {
            return NashornScriptEngine.this;
         }
      };
   }

   private ScriptFunction compileImpl(Source source, ScriptContext ctxt) throws ScriptException {
      return this.compileImpl(source, this.getNashornGlobalFrom(ctxt));
   }

   private ScriptFunction compileImpl(Source source, Global newGlobal) throws ScriptException {
      Global oldGlobal = Context.getGlobal();
      boolean globalChanged = oldGlobal != newGlobal;

      ScriptFunction var5;
      try {
         if (globalChanged) {
            Context.setGlobal(newGlobal);
         }

         var5 = this.nashornContext.compileScript(source, newGlobal);
      } catch (Exception var9) {
         throwAsScriptException(var9, newGlobal);
         throw new AssertionError("should not reach here");
      } finally {
         if (globalChanged) {
            Context.setGlobal(oldGlobal);
         }

      }

      return var5;
   }

   private static boolean isInterfaceImplemented(Class<?> iface, ScriptObject sobj) {
      Method[] var2 = iface.getMethods();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method method = var2[var4];
         if (method.getDeclaringClass() != Object.class && Modifier.isAbstract(method.getModifiers())) {
            Object obj = sobj.get(method.getName());
            if (!(obj instanceof ScriptFunction)) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean isOfContext(Global global, Context context) {
      return global.isOfContext(context);
   }
}
