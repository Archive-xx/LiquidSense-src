package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import jdk.internal.dynalink.support.Lookup;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

public final class NativeFunction {
   public static final MethodHandle TO_APPLY_ARGS = Lookup.findOwnStatic(MethodHandles.lookup(), "toApplyArgs", Object[].class, Object.class);
   private static PropertyMap $nasgenmap$;

   private NativeFunction() {
      throw new UnsupportedOperationException();
   }

   public static String toString(Object self) {
      if (!(self instanceof ScriptFunction)) {
         throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(self));
      } else {
         return ((ScriptFunction)self).toSource();
      }
   }

   public static Object apply(Object self, Object thiz, Object array) {
      checkCallable(self);
      Object[] args = toApplyArgs(array);
      if (self instanceof ScriptFunction) {
         return ScriptRuntime.apply((ScriptFunction)self, thiz, args);
      } else if (self instanceof JSObject) {
         return ((JSObject)self).call(thiz, args);
      } else {
         throw new AssertionError("Should not reach here");
      }
   }

   public static Object[] toApplyArgs(Object array) {
      if (array instanceof NativeArguments) {
         return ((NativeArguments)array).getArray().asObjectArray();
      } else if (!(array instanceof ScriptObject)) {
         if (array instanceof Object[]) {
            return (Object[])((Object[])array);
         } else if (array instanceof List) {
            List<?> list = (List)array;
            return list.toArray(new Object[list.size()]);
         } else if (array != null && array != ScriptRuntime.UNDEFINED) {
            if (array instanceof JSObject) {
               JSObject jsObj = (JSObject)array;
               Object len = jsObj.hasMember("length") ? jsObj.getMember("length") : 0;
               int n = lengthToInt(len);
               Object[] args = new Object[n];

               for(int i = 0; i < args.length; ++i) {
                  args[i] = jsObj.hasSlot(i) ? jsObj.getSlot(i) : ScriptRuntime.UNDEFINED;
               }

               return args;
            } else {
               throw ECMAErrors.typeError("function.apply.expects.array");
            }
         } else {
            return ScriptRuntime.EMPTY_ARRAY;
         }
      } else {
         ScriptObject sobj = (ScriptObject)array;
         int n = lengthToInt(sobj.getLength());
         Object[] args = new Object[n];

         for(int i = 0; i < args.length; ++i) {
            args[i] = sobj.get(i);
         }

         return args;
      }
   }

   private static int lengthToInt(Object len) {
      long ln = JSType.toUint32(len);
      if (ln > 2147483647L) {
         throw ECMAErrors.rangeError("range.error.inappropriate.array.length", JSType.toString(len));
      } else {
         return (int)ln;
      }
   }

   private static void checkCallable(Object self) {
      if (!(self instanceof ScriptFunction) && (!(self instanceof JSObject) || !((JSObject)self).isFunction())) {
         throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(self));
      }
   }

   public static Object call(Object self, Object... args) {
      checkCallable(self);
      Object thiz = args.length == 0 ? ScriptRuntime.UNDEFINED : args[0];
      Object[] arguments;
      if (args.length > 1) {
         arguments = new Object[args.length - 1];
         System.arraycopy(args, 1, arguments, 0, arguments.length);
      } else {
         arguments = ScriptRuntime.EMPTY_ARRAY;
      }

      if (self instanceof ScriptFunction) {
         return ScriptRuntime.apply((ScriptFunction)self, thiz, arguments);
      } else if (self instanceof JSObject) {
         return ((JSObject)self).call(thiz, arguments);
      } else {
         throw new AssertionError("should not reach here");
      }
   }

   public static Object bind(Object self, Object... args) {
      Object thiz = args.length == 0 ? ScriptRuntime.UNDEFINED : args[0];
      Object[] arguments;
      if (args.length > 1) {
         arguments = new Object[args.length - 1];
         System.arraycopy(args, 1, arguments, 0, arguments.length);
      } else {
         arguments = ScriptRuntime.EMPTY_ARRAY;
      }

      return Bootstrap.bindCallable(self, thiz, arguments);
   }

   public static String toSource(Object self) {
      if (!(self instanceof ScriptFunction)) {
         throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(self));
      } else {
         return ((ScriptFunction)self).toSource();
      }
   }

   public static ScriptFunction function(boolean newObj, Object self, Object... args) {
      StringBuilder sb = new StringBuilder();
      sb.append("(function (");
      String funcBody;
      if (args.length > 0) {
         StringBuilder paramListBuf = new StringBuilder();

         for(int i = 0; i < args.length - 1; ++i) {
            paramListBuf.append(JSType.toString(args[i]));
            if (i < args.length - 2) {
               paramListBuf.append(",");
            }
         }

         funcBody = JSType.toString(args[args.length - 1]);
         String paramList = paramListBuf.toString();
         if (!paramList.isEmpty()) {
            checkFunctionParameters(paramList);
            sb.append(paramList);
         }
      } else {
         funcBody = null;
      }

      sb.append(") {\n");
      if (args.length > 0) {
         checkFunctionBody(funcBody);
         sb.append(funcBody);
         sb.append('\n');
      }

      sb.append("})");
      Global global = Global.instance();
      Context context = global.getContext();
      return (ScriptFunction)context.eval(global, sb.toString(), global, "<function>");
   }

   private static void checkFunctionParameters(String params) {
      Parser parser = getParser(params);

      try {
         parser.parseFormalParameterList();
      } catch (ParserException var3) {
         var3.throwAsEcmaException();
      }

   }

   private static void checkFunctionBody(String funcBody) {
      Parser parser = getParser(funcBody);

      try {
         parser.parseFunctionBody();
      } catch (ParserException var3) {
         var3.throwAsEcmaException();
      }

   }

   private static Parser getParser(String sourceText) {
      ScriptEnvironment env = Global.getEnv();
      return new Parser(env, Source.sourceFor("<function>", sourceText), new Context.ThrowErrorManager(), env._strict, (DebugLogger)null);
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }
}
