package jdk.nashorn.internal.lookup;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

public final class MethodHandleFactory {
   private static final java.lang.invoke.MethodHandles.Lookup PUBLIC_LOOKUP = MethodHandles.publicLookup();
   private static final java.lang.invoke.MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
   private static final Level TRACE_LEVEL;
   private static final MethodHandleFunctionality FUNC;
   private static final boolean PRINT_STACKTRACE;
   private static final MethodHandle TRACE;
   private static final MethodHandle TRACE_RETURN;
   private static final MethodHandle TRACE_RETURN_VOID;
   private static final String VOID_TAG = "[VOID]";

   private MethodHandleFactory() {
   }

   public static String stripName(Object obj) {
      if (obj == null) {
         return "null";
      } else {
         return obj instanceof Class ? ((Class)obj).getSimpleName() : obj.toString();
      }
   }

   public static MethodHandleFunctionality getFunctionality() {
      return FUNC;
   }

   private static void err(String str) {
      Context.getContext().getErr().println(str);
   }

   static Object traceReturn(DebugLogger logger, Object value) {
      String str = "    return" + ("[VOID]".equals(value) ? ";" : " " + stripName(value) + "; // [type=" + (value == null ? "null]" : stripName(value.getClass()) + ']'));
      if (logger == null) {
         err(str);
      } else if (logger.isEnabled()) {
         logger.log(TRACE_LEVEL, str);
      }

      return value;
   }

   static void traceReturnVoid(DebugLogger logger) {
      traceReturn(logger, "[VOID]");
   }

   static void traceArgs(DebugLogger logger, String tag, int paramStart, Object... args) {
      StringBuilder sb = new StringBuilder();
      sb.append(tag);

      for(int i = paramStart; i < args.length; ++i) {
         if (i == paramStart) {
            sb.append(" => args: ");
         }

         sb.append('\'').append(stripName(argString(args[i]))).append('\'').append(' ').append('[').append("type=").append(args[i] == null ? "null" : stripName(args[i].getClass())).append(']');
         if (i + 1 < args.length) {
            sb.append(", ");
         }
      }

      if (logger == null) {
         err(sb.toString());
      } else {
         logger.log(TRACE_LEVEL, sb);
      }

      stacktrace(logger);
   }

   private static void stacktrace(DebugLogger logger) {
      if (PRINT_STACKTRACE) {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         PrintStream ps = new PrintStream(baos);
         (new Throwable()).printStackTrace(ps);
         String st = baos.toString();
         if (logger == null) {
            err(st);
         } else {
            logger.log(TRACE_LEVEL, st);
         }

      }
   }

   private static String argString(Object arg) {
      if (arg == null) {
         return "null";
      } else if (!arg.getClass().isArray()) {
         return arg instanceof ScriptObject ? arg.toString() + " (map=" + Debug.id(((ScriptObject)arg).getMap()) + ')' : arg.toString();
      } else {
         List<Object> list = new ArrayList();
         Object[] var2 = (Object[])((Object[])arg);
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object elem = var2[var4];
            list.add('\'' + argString(elem) + '\'');
         }

         return list.toString();
      }
   }

   public static MethodHandle addDebugPrintout(MethodHandle mh, Object tag) {
      return addDebugPrintout((DebugLogger)null, Level.OFF, mh, 0, true, tag);
   }

   public static MethodHandle addDebugPrintout(DebugLogger logger, Level level, MethodHandle mh, Object tag) {
      return addDebugPrintout(logger, level, mh, 0, true, tag);
   }

   public static MethodHandle addDebugPrintout(MethodHandle mh, int paramStart, boolean printReturnValue, Object tag) {
      return addDebugPrintout((DebugLogger)null, Level.OFF, mh, paramStart, printReturnValue, tag);
   }

   public static MethodHandle addDebugPrintout(DebugLogger logger, Level level, MethodHandle mh, int paramStart, boolean printReturnValue, Object tag) {
      MethodType type = mh.type();
      if (logger != null && logger.isLoggable(level)) {
         assert TRACE != null;

         MethodHandle trace = MethodHandles.insertArguments(TRACE, 0, new Object[]{logger, tag, paramStart});
         trace = MethodHandles.foldArguments(mh, trace.asCollector(Object[].class, type.parameterCount()).asType(type.changeReturnType(Void.TYPE)));
         Class<?> retType = type.returnType();
         if (printReturnValue) {
            if (retType != Void.TYPE) {
               MethodHandle traceReturn = MethodHandles.insertArguments(TRACE_RETURN, 0, new Object[]{logger});
               trace = MethodHandles.filterReturnValue(trace, traceReturn.asType(traceReturn.type().changeParameterType(0, retType).changeReturnType(retType)));
            } else {
               trace = MethodHandles.filterReturnValue(trace, MethodHandles.insertArguments(TRACE_RETURN_VOID, 0, new Object[]{logger}));
            }
         }

         return trace;
      } else {
         return mh;
      }
   }

   static {
      TRACE_LEVEL = Level.INFO;
      FUNC = new MethodHandleFactory.StandardMethodHandleFunctionality();
      PRINT_STACKTRACE = Options.getBooleanProperty("nashorn.methodhandles.debug.stacktrace");
      TRACE = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceArgs", MethodType.methodType(Void.TYPE, DebugLogger.class, String.class, Integer.TYPE, Object[].class));
      TRACE_RETURN = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceReturn", MethodType.methodType(Object.class, DebugLogger.class, Object.class));
      TRACE_RETURN_VOID = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceReturnVoid", MethodType.methodType(Void.TYPE, DebugLogger.class));
   }

   @Logger(
      name = "methodhandles"
   )
   private static class StandardMethodHandleFunctionality implements MethodHandleFunctionality, Loggable {
      private DebugLogger log;

      public StandardMethodHandleFunctionality() {
         this.log = DebugLogger.DISABLED_LOGGER;
      }

      public DebugLogger initLogger(Context context) {
         return this.log = context.getLogger(this.getClass());
      }

      public DebugLogger getLogger() {
         return this.log;
      }

      protected static String describe(Object... data) {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < data.length; ++i) {
            Object d = data[i];
            if (d == null) {
               sb.append("<null> ");
            } else if (JSType.isString(d)) {
               sb.append(d.toString());
               sb.append(' ');
            } else if (!d.getClass().isArray()) {
               sb.append(d).append('{').append(Integer.toHexString(System.identityHashCode(d))).append('}');
            } else {
               sb.append("[ ");
               Object[] var4 = (Object[])((Object[])d);
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Object da = var4[var6];
                  sb.append(describe(da)).append(' ');
               }

               sb.append("] ");
            }

            if (i + 1 < data.length) {
               sb.append(", ");
            }
         }

         return sb.toString();
      }

      public MethodHandle debug(MethodHandle master, String str, Object... args) {
         if (this.log.isEnabled()) {
            if (MethodHandleFactory.PRINT_STACKTRACE) {
               MethodHandleFactory.stacktrace(this.log);
            }

            return MethodHandleFactory.addDebugPrintout(this.log, Level.INFO, master, Integer.MAX_VALUE, false, str + ' ' + describe(args));
         } else {
            return master;
         }
      }

      public MethodHandle filterArguments(MethodHandle target, int pos, MethodHandle... filters) {
         MethodHandle mh = MethodHandles.filterArguments(target, pos, filters);
         return this.debug(mh, "filterArguments", target, pos, filters);
      }

      public MethodHandle filterReturnValue(MethodHandle target, MethodHandle filter) {
         MethodHandle mh = MethodHandles.filterReturnValue(target, filter);
         return this.debug(mh, "filterReturnValue", target, filter);
      }

      public MethodHandle guardWithTest(MethodHandle test, MethodHandle target, MethodHandle fallback) {
         MethodHandle mh = MethodHandles.guardWithTest(test, target, fallback);
         return this.debug(mh, "guardWithTest", test, target, fallback);
      }

      public MethodHandle insertArguments(MethodHandle target, int pos, Object... values) {
         MethodHandle mh = MethodHandles.insertArguments(target, pos, values);
         return this.debug(mh, "insertArguments", target, pos, values);
      }

      public MethodHandle dropArguments(MethodHandle target, int pos, Class<?>... values) {
         MethodHandle mh = MethodHandles.dropArguments(target, pos, values);
         return this.debug(mh, "dropArguments", target, pos, values);
      }

      public MethodHandle dropArguments(MethodHandle target, int pos, List<Class<?>> values) {
         MethodHandle mh = MethodHandles.dropArguments(target, pos, values);
         return this.debug(mh, "dropArguments", target, pos, values);
      }

      public MethodHandle asType(MethodHandle handle, MethodType type) {
         MethodHandle mh = handle.asType(type);
         return this.debug(mh, "asType", handle, type);
      }

      public MethodHandle bindTo(MethodHandle handle, Object x) {
         MethodHandle mh = handle.bindTo(x);
         return this.debug(mh, "bindTo", handle, x);
      }

      public MethodHandle foldArguments(MethodHandle target, MethodHandle combiner) {
         MethodHandle mh = MethodHandles.foldArguments(target, combiner);
         return this.debug(mh, "foldArguments", target, combiner);
      }

      public MethodHandle explicitCastArguments(MethodHandle target, MethodType type) {
         MethodHandle mh = MethodHandles.explicitCastArguments(target, type);
         return this.debug(mh, "explicitCastArguments", target, type);
      }

      public MethodHandle arrayElementGetter(Class<?> type) {
         MethodHandle mh = MethodHandles.arrayElementGetter(type);
         return this.debug(mh, "arrayElementGetter", type);
      }

      public MethodHandle arrayElementSetter(Class<?> type) {
         MethodHandle mh = MethodHandles.arrayElementSetter(type);
         return this.debug(mh, "arrayElementSetter", type);
      }

      public MethodHandle throwException(Class<?> returnType, Class<? extends Throwable> exType) {
         MethodHandle mh = MethodHandles.throwException(returnType, exType);
         return this.debug(mh, "throwException", returnType, exType);
      }

      public MethodHandle catchException(MethodHandle target, Class<? extends Throwable> exType, MethodHandle handler) {
         MethodHandle mh = MethodHandles.catchException(target, exType, handler);
         return this.debug(mh, "catchException", exType);
      }

      public MethodHandle constant(Class<?> type, Object value) {
         MethodHandle mh = MethodHandles.constant(type, value);
         return this.debug(mh, "constant", type, value);
      }

      public MethodHandle identity(Class<?> type) {
         MethodHandle mh = MethodHandles.identity(type);
         return this.debug(mh, "identity", type);
      }

      public MethodHandle asCollector(MethodHandle handle, Class<?> arrayType, int arrayLength) {
         MethodHandle mh = handle.asCollector(arrayType, arrayLength);
         return this.debug(mh, "asCollector", handle, arrayType, arrayLength);
      }

      public MethodHandle asSpreader(MethodHandle handle, Class<?> arrayType, int arrayLength) {
         MethodHandle mh = handle.asSpreader(arrayType, arrayLength);
         return this.debug(mh, "asSpreader", handle, arrayType, arrayLength);
      }

      public MethodHandle getter(java.lang.invoke.MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
         try {
            MethodHandle mh = explicitLookup.findGetter(clazz, name, type);
            return this.debug(mh, "getter", explicitLookup, clazz, name, type);
         } catch (IllegalAccessException | NoSuchFieldException var6) {
            throw new MethodHandleFactory.LookupException(var6);
         }
      }

      public MethodHandle staticGetter(java.lang.invoke.MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
         try {
            MethodHandle mh = explicitLookup.findStaticGetter(clazz, name, type);
            return this.debug(mh, "static getter", explicitLookup, clazz, name, type);
         } catch (IllegalAccessException | NoSuchFieldException var6) {
            throw new MethodHandleFactory.LookupException(var6);
         }
      }

      public MethodHandle setter(java.lang.invoke.MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
         try {
            MethodHandle mh = explicitLookup.findSetter(clazz, name, type);
            return this.debug(mh, "setter", explicitLookup, clazz, name, type);
         } catch (IllegalAccessException | NoSuchFieldException var6) {
            throw new MethodHandleFactory.LookupException(var6);
         }
      }

      public MethodHandle staticSetter(java.lang.invoke.MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, Class<?> type) {
         try {
            MethodHandle mh = explicitLookup.findStaticSetter(clazz, name, type);
            return this.debug(mh, "static setter", explicitLookup, clazz, name, type);
         } catch (IllegalAccessException | NoSuchFieldException var6) {
            throw new MethodHandleFactory.LookupException(var6);
         }
      }

      public MethodHandle find(Method method) {
         try {
            MethodHandle mh = MethodHandleFactory.PUBLIC_LOOKUP.unreflect(method);
            return this.debug(mh, "find", method);
         } catch (IllegalAccessException var3) {
            throw new MethodHandleFactory.LookupException(var3);
         }
      }

      public MethodHandle findStatic(java.lang.invoke.MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, MethodType type) {
         try {
            MethodHandle mh = explicitLookup.findStatic(clazz, name, type);
            return this.debug(mh, "findStatic", explicitLookup, clazz, name, type);
         } catch (IllegalAccessException | NoSuchMethodException var6) {
            throw new MethodHandleFactory.LookupException(var6);
         }
      }

      public MethodHandle findSpecial(java.lang.invoke.MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, MethodType type, Class<?> thisClass) {
         try {
            MethodHandle mh = explicitLookup.findSpecial(clazz, name, type, thisClass);
            return this.debug(mh, "findSpecial", explicitLookup, clazz, name, type);
         } catch (IllegalAccessException | NoSuchMethodException var7) {
            throw new MethodHandleFactory.LookupException(var7);
         }
      }

      public MethodHandle findVirtual(java.lang.invoke.MethodHandles.Lookup explicitLookup, Class<?> clazz, String name, MethodType type) {
         try {
            MethodHandle mh = explicitLookup.findVirtual(clazz, name, type);
            return this.debug(mh, "findVirtual", explicitLookup, clazz, name, type);
         } catch (IllegalAccessException | NoSuchMethodException var6) {
            throw new MethodHandleFactory.LookupException(var6);
         }
      }

      public SwitchPoint createSwitchPoint() {
         SwitchPoint sp = new SwitchPoint();
         this.log.log(MethodHandleFactory.TRACE_LEVEL, "createSwitchPoint ", sp);
         return sp;
      }

      public MethodHandle guardWithTest(SwitchPoint sp, MethodHandle before, MethodHandle after) {
         MethodHandle mh = sp.guardWithTest(before, after);
         return this.debug(mh, "guardWithTest", sp, before, after);
      }

      public MethodType type(Class<?> returnType, Class<?>... paramTypes) {
         MethodType mt = MethodType.methodType(returnType, paramTypes);
         this.log.log(MethodHandleFactory.TRACE_LEVEL, "methodType ", returnType, " ", Arrays.toString(paramTypes), " ", mt);
         return mt;
      }
   }

   public static class LookupException extends RuntimeException {
      public LookupException(Exception e) {
         super(e);
      }
   }
}
