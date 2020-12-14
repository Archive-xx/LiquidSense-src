package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSONFunctions;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;

public final class NativeJSON extends ScriptObject {
   private static final Object TO_JSON = new Object();
   private static final Object JSOBJECT_INVOKER = new Object();
   private static final Object REPLACER_INVOKER = new Object();
   private static PropertyMap $nasgenmap$;

   private static InvokeByName getTO_JSON() {
      return Global.instance().getInvokeByName(TO_JSON, new Callable<InvokeByName>() {
         public InvokeByName call() {
            return new InvokeByName("toJSON", ScriptObject.class, Object.class, new Class[]{Object.class});
         }
      });
   }

   private static MethodHandle getJSOBJECT_INVOKER() {
      return Global.instance().getDynamicInvoker(JSOBJECT_INVOKER, new Callable<MethodHandle>() {
         public MethodHandle call() {
            return Bootstrap.createDynamicInvoker("dyn:call", Object.class, Object.class, Object.class);
         }
      });
   }

   private static MethodHandle getREPLACER_INVOKER() {
      return Global.instance().getDynamicInvoker(REPLACER_INVOKER, new Callable<MethodHandle>() {
         public MethodHandle call() {
            return Bootstrap.createDynamicInvoker("dyn:call", Object.class, Object.class, Object.class, Object.class, Object.class);
         }
      });
   }

   private NativeJSON() {
      throw new UnsupportedOperationException();
   }

   public static Object parse(Object self, Object text, Object reviver) {
      return JSONFunctions.parse(text, reviver);
   }

   public static Object stringify(Object self, Object value, Object replacer, Object space) {
      NativeJSON.StringifyState state = new NativeJSON.StringifyState();
      if (Bootstrap.isCallable(replacer)) {
         state.replacerFunction = replacer;
      } else if (isArray(replacer) || isJSObjectArray(replacer) || replacer instanceof Iterable || replacer != null && replacer.getClass().isArray()) {
         state.propertyList = new ArrayList();
         ArrayLikeIterator iter = ArrayLikeIterator.arrayLikeIterator(replacer);

         while(iter.hasNext()) {
            String item = null;
            Object v = iter.next();
            if (v instanceof String) {
               item = (String)v;
            } else if (v instanceof ConsString) {
               item = v.toString();
            } else if (v instanceof Number || v instanceof NativeNumber || v instanceof NativeString) {
               item = JSType.toString(v);
            }

            if (item != null) {
               state.propertyList.add(item);
            }
         }
      }

      Object modSpace = space;
      if (space instanceof NativeNumber) {
         modSpace = JSType.toNumber(JSType.toPrimitive(space, Number.class));
      } else if (space instanceof NativeString) {
         modSpace = JSType.toString(JSType.toPrimitive(space, String.class));
      }

      String gap;
      if (modSpace instanceof Number) {
         int indent = Math.min(10, JSType.toInteger(modSpace));
         if (indent < 1) {
            gap = "";
         } else {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < indent; ++i) {
               sb.append(' ');
            }

            gap = sb.toString();
         }
      } else if (JSType.isString(modSpace)) {
         String str = modSpace.toString();
         gap = str.substring(0, Math.min(10, str.length()));
      } else {
         gap = "";
      }

      state.gap = gap;
      ScriptObject wrapper = Global.newEmptyInstance();
      wrapper.set("", value, 0);
      return str("", wrapper, state);
   }

   private static Object str(Object key, Object holder, NativeJSON.StringifyState state) {
      assert holder instanceof ScriptObject || holder instanceof JSObject;

      Object value = getProperty(holder, key);

      try {
         if (value instanceof ScriptObject) {
            InvokeByName toJSONInvoker = getTO_JSON();
            ScriptObject svalue = (ScriptObject)value;
            Object toJSON = toJSONInvoker.getGetter().invokeExact(svalue);
            if (Bootstrap.isCallable(toJSON)) {
               value = toJSONInvoker.getInvoker().invokeExact(toJSON, svalue, key);
            }
         } else if (value instanceof JSObject) {
            JSObject jsObj = (JSObject)value;
            Object toJSON = jsObj.getMember("toJSON");
            if (Bootstrap.isCallable(toJSON)) {
               value = getJSOBJECT_INVOKER().invokeExact(toJSON, value);
            }
         }

         if (state.replacerFunction != null) {
            value = getREPLACER_INVOKER().invokeExact(state.replacerFunction, holder, key, value);
         }
      } catch (RuntimeException | Error var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new RuntimeException(var8);
      }

      boolean isObj = value instanceof ScriptObject;
      if (isObj) {
         if (value instanceof NativeNumber) {
            value = JSType.toNumber(value);
         } else if (value instanceof NativeString) {
            value = JSType.toString(value);
         } else if (value instanceof NativeBoolean) {
            value = ((NativeBoolean)value).booleanValue();
         }
      }

      if (value == null) {
         return "null";
      } else if (Boolean.TRUE.equals(value)) {
         return "true";
      } else if (Boolean.FALSE.equals(value)) {
         return "false";
      } else if (value instanceof String) {
         return JSONFunctions.quote((String)value);
      } else if (value instanceof ConsString) {
         return JSONFunctions.quote(value.toString());
      } else if (value instanceof Number) {
         return JSType.isFinite(((Number)value).doubleValue()) ? JSType.toString(value) : "null";
      } else {
         JSType type = JSType.of(value);
         if (type == JSType.OBJECT) {
            if (isArray(value) || isJSObjectArray(value)) {
               return JA(value, state);
            }

            if (value instanceof ScriptObject || value instanceof JSObject) {
               return JO(value, state);
            }
         }

         return ScriptRuntime.UNDEFINED;
      }
   }

   private static String JO(Object value, NativeJSON.StringifyState state) {
      assert value instanceof ScriptObject || value instanceof JSObject;

      if (state.stack.containsKey(value)) {
         throw ECMAErrors.typeError("JSON.stringify.cyclic");
      } else {
         state.stack.put(value, value);
         StringBuilder stepback = new StringBuilder(state.indent.toString());
         state.indent.append(state.gap);
         StringBuilder finalStr = new StringBuilder();
         List<Object> partial = new ArrayList();
         List<String> k = state.propertyList == null ? Arrays.asList(getOwnKeys(value)) : state.propertyList;
         Iterator var6 = k.iterator();

         while(var6.hasNext()) {
            Object p = var6.next();
            Object strP = str(p, value, state);
            if (strP != ScriptRuntime.UNDEFINED) {
               StringBuilder member = new StringBuilder();
               member.append(JSONFunctions.quote(p.toString())).append(':');
               if (!state.gap.isEmpty()) {
                  member.append(' ');
               }

               member.append(strP);
               partial.add(member);
            }
         }

         if (partial.isEmpty()) {
            finalStr.append("{}");
         } else {
            int size;
            int index;
            Iterator var12;
            Object str;
            if (state.gap.isEmpty()) {
               size = partial.size();
               index = 0;
               finalStr.append('{');

               for(var12 = partial.iterator(); var12.hasNext(); ++index) {
                  str = var12.next();
                  finalStr.append(str);
                  if (index < size - 1) {
                     finalStr.append(',');
                  }
               }

               finalStr.append('}');
            } else {
               size = partial.size();
               index = 0;
               finalStr.append("{\n");
               finalStr.append(state.indent);

               for(var12 = partial.iterator(); var12.hasNext(); ++index) {
                  str = var12.next();
                  finalStr.append(str);
                  if (index < size - 1) {
                     finalStr.append(",\n");
                     finalStr.append(state.indent);
                  }
               }

               finalStr.append('\n');
               finalStr.append(stepback);
               finalStr.append('}');
            }
         }

         state.stack.remove(value);
         state.indent = stepback;
         return finalStr.toString();
      }
   }

   private static Object JA(Object value, NativeJSON.StringifyState state) {
      assert value instanceof ScriptObject || value instanceof JSObject;

      if (state.stack.containsKey(value)) {
         throw ECMAErrors.typeError("JSON.stringify.cyclic");
      } else {
         state.stack.put(value, value);
         StringBuilder stepback = new StringBuilder(state.indent.toString());
         state.indent.append(state.gap);
         List<Object> partial = new ArrayList();
         int length = JSType.toInteger(getLength(value));

         int index;
         for(index = 0; index < length; ++index) {
            Object strP = str(index, value, state);
            if (strP == ScriptRuntime.UNDEFINED) {
               strP = "null";
            }

            partial.add(strP);
         }

         StringBuilder finalStr = new StringBuilder();
         if (partial.isEmpty()) {
            finalStr.append("[]");
         } else {
            int size;
            Iterator var8;
            Object str;
            if (state.gap.isEmpty()) {
               size = partial.size();
               index = 0;
               finalStr.append('[');

               for(var8 = partial.iterator(); var8.hasNext(); ++index) {
                  str = var8.next();
                  finalStr.append(str);
                  if (index < size - 1) {
                     finalStr.append(',');
                  }
               }

               finalStr.append(']');
            } else {
               size = partial.size();
               index = 0;
               finalStr.append("[\n");
               finalStr.append(state.indent);

               for(var8 = partial.iterator(); var8.hasNext(); ++index) {
                  str = var8.next();
                  finalStr.append(str);
                  if (index < size - 1) {
                     finalStr.append(",\n");
                     finalStr.append(state.indent);
                  }
               }

               finalStr.append('\n');
               finalStr.append(stepback);
               finalStr.append(']');
            }
         }

         state.stack.remove(value);
         state.indent = stepback;
         return finalStr.toString();
      }
   }

   private static String[] getOwnKeys(Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).getOwnKeys(false);
      } else if (obj instanceof ScriptObjectMirror) {
         return ((ScriptObjectMirror)obj).getOwnKeys(false);
      } else if (obj instanceof JSObject) {
         return (String[])((JSObject)obj).keySet().toArray(new String[0]);
      } else {
         throw new AssertionError("should not reach here");
      }
   }

   private static Object getLength(Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).getLength();
      } else if (obj instanceof JSObject) {
         return ((JSObject)obj).getMember("length");
      } else {
         throw new AssertionError("should not reach here");
      }
   }

   private static boolean isJSObjectArray(Object obj) {
      return obj instanceof JSObject && ((JSObject)obj).isArray();
   }

   private static Object getProperty(Object holder, Object key) {
      if (holder instanceof ScriptObject) {
         return ((ScriptObject)holder).get(key);
      } else if (holder instanceof JSObject) {
         JSObject jsObj = (JSObject)holder;
         return key instanceof Integer ? jsObj.getSlot((Integer)key) : jsObj.getMember(Objects.toString(key));
      } else {
         return new AssertionError("should not reach here");
      }
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }

   private static class StringifyState {
      final Map<Object, Object> stack;
      StringBuilder indent;
      String gap;
      List<String> propertyList;
      Object replacerFunction;

      private StringifyState() {
         this.stack = new IdentityHashMap();
         this.indent = new StringBuilder();
         this.gap = "";
         this.propertyList = null;
         this.replacerFunction = null;
      }

      // $FF: synthetic method
      StringifyState(Object x0) {
         this();
      }
   }
}
