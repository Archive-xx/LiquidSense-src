package jdk.nashorn.api.scripting;

import java.lang.invoke.MethodHandle;
import jdk.Exported;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

@Exported
public final class ScriptUtils {
   private ScriptUtils() {
   }

   public static String parse(String code, String name, boolean includeLoc) {
      return ScriptRuntime.parse(code, name, includeLoc);
   }

   public static String format(String format, Object[] args) {
      return Formatter.format(format, args);
   }

   public static Object makeSynchronizedFunction(Object func, Object sync) {
      Object unwrapped = unwrap(func);
      if (unwrapped instanceof ScriptFunction) {
         return ((ScriptFunction)unwrapped).createSynchronized(unwrap(sync));
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static ScriptObjectMirror wrap(Object obj) {
      if (obj instanceof ScriptObjectMirror) {
         return (ScriptObjectMirror)obj;
      } else if (obj instanceof ScriptObject) {
         ScriptObject sobj = (ScriptObject)obj;
         return (ScriptObjectMirror)ScriptObjectMirror.wrap(sobj, Context.getGlobal());
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static Object unwrap(Object obj) {
      return obj instanceof ScriptObjectMirror ? ScriptObjectMirror.unwrap(obj, Context.getGlobal()) : obj;
   }

   public static Object[] wrapArray(Object[] args) {
      return args != null && args.length != 0 ? ScriptObjectMirror.wrapArray(args, Context.getGlobal()) : args;
   }

   public static Object[] unwrapArray(Object[] args) {
      return args != null && args.length != 0 ? ScriptObjectMirror.unwrapArray(args, Context.getGlobal()) : args;
   }

   public static Object convert(Object obj, Object type) {
      if (obj == null) {
         return null;
      } else {
         Class clazz;
         if (type instanceof Class) {
            clazz = (Class)type;
         } else {
            if (!(type instanceof StaticClass)) {
               throw new IllegalArgumentException("type expected");
            }

            clazz = ((StaticClass)type).getRepresentedClass();
         }

         LinkerServices linker = Bootstrap.getLinkerServices();
         Object objToConvert = unwrap(obj);
         MethodHandle converter = linker.getTypeConverter(objToConvert.getClass(), clazz);
         if (converter == null) {
            throw new UnsupportedOperationException("conversion not supported");
         } else {
            try {
               return converter.invoke(objToConvert);
            } catch (Error | RuntimeException var7) {
               throw var7;
            } catch (Throwable var8) {
               throw new RuntimeException(var8);
            }
         }
      }
   }
}
