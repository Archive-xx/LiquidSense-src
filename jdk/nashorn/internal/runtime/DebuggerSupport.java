package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.scripts.JS;

final class DebuggerSupport {
   static boolean FORCELOAD = true;

   static void notifyInvoke(MethodHandle mh) {
   }

   static DebuggerSupport.SourceInfo getSourceInfo(Class<?> clazz) {
      if (JS.class.isAssignableFrom(clazz)) {
         try {
            Field sourceField = clazz.getDeclaredField(CompilerConstants.SOURCE.symbolName());
            sourceField.setAccessible(true);
            Source src = (Source)sourceField.get((Object)null);
            return src.getSourceInfo();
         } catch (NoSuchFieldException | IllegalAccessException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   static Object getGlobal() {
      return Context.getGlobal();
   }

   static Object eval(ScriptObject scope, Object self, String string, boolean returnException) {
      ScriptObject global = Context.getGlobal();
      ScriptObject initialScope = scope != null ? scope : global;
      Object callThis = self != null ? self : global;
      Context context = global.getContext();

      try {
         return context.eval((ScriptObject)initialScope, string, callThis, ScriptRuntime.UNDEFINED);
      } catch (Throwable var9) {
         return returnException ? var9 : null;
      }
   }

   static DebuggerSupport.DebuggerValueDesc[] valueInfos(Object object, boolean all) {
      assert object instanceof ScriptObject;

      return getDebuggerValueDescs((ScriptObject)object, all, new HashSet());
   }

   static DebuggerSupport.DebuggerValueDesc valueInfo(String name, Object value, boolean all) {
      return valueInfo(name, value, all, new HashSet());
   }

   private static DebuggerSupport.DebuggerValueDesc valueInfo(String name, Object value, boolean all, Set<Object> duplicates) {
      if (value instanceof ScriptObject && !(value instanceof ScriptFunction)) {
         ScriptObject object = (ScriptObject)value;
         return new DebuggerSupport.DebuggerValueDesc(name, !object.isEmpty(), value, objectAsString(object, all, duplicates));
      } else {
         return new DebuggerSupport.DebuggerValueDesc(name, false, value, valueAsString(value));
      }
   }

   private static DebuggerSupport.DebuggerValueDesc[] getDebuggerValueDescs(ScriptObject object, boolean all, Set<Object> duplicates) {
      if (duplicates.contains(object)) {
         return null;
      } else {
         duplicates.add(object);
         String[] keys = object.getOwnKeys(all);
         DebuggerSupport.DebuggerValueDesc[] descs = new DebuggerSupport.DebuggerValueDesc[keys.length];

         for(int i = 0; i < keys.length; ++i) {
            String key = keys[i];
            descs[i] = valueInfo(key, object.get(key), all, duplicates);
         }

         duplicates.remove(object);
         return descs;
      }
   }

   private static String objectAsString(ScriptObject object, boolean all, Set<Object> duplicates) {
      StringBuilder sb = new StringBuilder();
      if (ScriptObject.isArray(object)) {
         sb.append('[');
         long length = (long)object.getDouble("length", -1);

         for(long i = 0L; i < length; ++i) {
            if (object.has((double)i)) {
               Object valueAsObject = object.get((double)i);
               boolean isUndefined = valueAsObject == ScriptRuntime.UNDEFINED;
               if (isUndefined) {
                  if (i != 0L) {
                     sb.append(",");
                  }
               } else {
                  if (i != 0L) {
                     sb.append(", ");
                  }

                  if (valueAsObject instanceof ScriptObject && !(valueAsObject instanceof ScriptFunction)) {
                     String objectString = objectAsString((ScriptObject)valueAsObject, all, duplicates);
                     sb.append(objectString != null ? objectString : "{...}");
                  } else {
                     sb.append(valueAsString(valueAsObject));
                  }
               }
            } else if (i != 0L) {
               sb.append(',');
            }
         }

         sb.append(']');
      } else {
         sb.append('{');
         DebuggerSupport.DebuggerValueDesc[] descs = getDebuggerValueDescs(object, all, duplicates);
         if (descs != null) {
            for(int i = 0; i < descs.length; ++i) {
               if (i != 0) {
                  sb.append(", ");
               }

               String valueAsString = descs[i].valueAsString;
               sb.append(descs[i].key);
               sb.append(": ");
               sb.append(valueAsString);
            }
         }

         sb.append('}');
      }

      return sb.toString();
   }

   static String valueAsString(Object value) {
      JSType type = JSType.of(value);
      switch(type) {
      case BOOLEAN:
         return value.toString();
      case STRING:
         return escape(value.toString());
      case NUMBER:
         return JSType.toString(((Number)value).doubleValue());
      case NULL:
         return "null";
      case UNDEFINED:
         return "undefined";
      case OBJECT:
         return ScriptRuntime.safeToString(value);
      case FUNCTION:
         if (value instanceof ScriptFunction) {
            return ((ScriptFunction)value).toSource();
         }

         return value.toString();
      default:
         return value.toString();
      }
   }

   private static String escape(String value) {
      StringBuilder sb = new StringBuilder();
      sb.append("\"");
      char[] var2 = value.toCharArray();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char ch = var2[var4];
         String hex;
         int i;
         switch(ch) {
         case '\b':
            sb.append("\\b");
            continue;
         case '\t':
            sb.append("\\t");
            continue;
         case '\n':
            sb.append("\\n");
            continue;
         case '\f':
            sb.append("\\f");
            continue;
         case '\r':
            sb.append("\\r");
            continue;
         case '"':
            sb.append("\\\"");
            continue;
         case '\'':
            sb.append("\\'");
            continue;
         case '\\':
            sb.append("\\\\");
            continue;
         default:
            if (ch >= ' ' && ch < 255) {
               sb.append(ch);
               continue;
            }

            sb.append("\\u");
            hex = Integer.toHexString(ch);
            i = hex.length();
         }

         while(i < 4) {
            sb.append('0');
            ++i;
         }

         sb.append(hex);
      }

      sb.append("\"");
      return sb.toString();
   }

   static {
      new DebuggerSupport.DebuggerValueDesc((String)null, false, (Object)null, (String)null);
      new DebuggerSupport.SourceInfo((String)null, 0, (URL)null, (char[])null);
   }

   static class SourceInfo {
      final String name;
      final URL url;
      final int hash;
      final char[] content;

      SourceInfo(String name, int hash, URL url, char[] content) {
         this.name = name;
         this.hash = hash;
         this.url = url;
         this.content = content;
      }
   }

   static class DebuggerValueDesc {
      final String key;
      final boolean expandable;
      final Object valueAsObject;
      final String valueAsString;

      DebuggerValueDesc(String key, boolean expandable, Object valueAsObject, String valueAsString) {
         this.key = key;
         this.expandable = expandable;
         this.valueAsObject = valueAsObject;
         this.valueAsString = valueAsString;
      }
   }
}
