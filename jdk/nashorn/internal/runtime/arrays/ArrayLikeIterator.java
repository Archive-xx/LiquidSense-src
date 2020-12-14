package jdk.nashorn.internal.runtime.arrays;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

public abstract class ArrayLikeIterator<T> implements Iterator<T> {
   protected long index;
   protected final boolean includeUndefined;

   ArrayLikeIterator(boolean includeUndefined) {
      this.includeUndefined = includeUndefined;
      this.index = 0L;
   }

   public boolean isReverse() {
      return false;
   }

   protected long bumpIndex() {
      return (long)(this.index++);
   }

   public long nextIndex() {
      return this.index;
   }

   public void remove() {
      throw new UnsupportedOperationException("remove");
   }

   public abstract long getLength();

   public static ArrayLikeIterator<Object> arrayLikeIterator(Object object) {
      return arrayLikeIterator(object, false);
   }

   public static ArrayLikeIterator<Object> reverseArrayLikeIterator(Object object) {
      return reverseArrayLikeIterator(object, false);
   }

   public static ArrayLikeIterator<Object> arrayLikeIterator(Object object, boolean includeUndefined) {
      if (ScriptObject.isArray(object)) {
         return new ScriptArrayIterator((ScriptObject)object, includeUndefined);
      } else {
         Object obj = JSType.toScriptObject(object);
         if (obj instanceof ScriptObject) {
            return new ScriptObjectIterator((ScriptObject)obj, includeUndefined);
         } else if (obj instanceof JSObject) {
            return new JSObjectIterator((JSObject)obj, includeUndefined);
         } else if (obj instanceof List) {
            return new JavaListIterator((List)obj, includeUndefined);
         } else {
            return (ArrayLikeIterator)(obj != null && obj.getClass().isArray() ? new JavaArrayIterator(obj, includeUndefined) : new EmptyArrayLikeIterator());
         }
      }
   }

   public static ArrayLikeIterator<Object> reverseArrayLikeIterator(Object object, boolean includeUndefined) {
      if (ScriptObject.isArray(object)) {
         return new ReverseScriptArrayIterator((ScriptObject)object, includeUndefined);
      } else {
         Object obj = JSType.toScriptObject(object);
         if (obj instanceof ScriptObject) {
            return new ReverseScriptObjectIterator((ScriptObject)obj, includeUndefined);
         } else if (obj instanceof JSObject) {
            return new ReverseJSObjectIterator((JSObject)obj, includeUndefined);
         } else if (obj instanceof List) {
            return new ReverseJavaListIterator((List)obj, includeUndefined);
         } else {
            return (ArrayLikeIterator)(obj != null && obj.getClass().isArray() ? new ReverseJavaArrayIterator(obj, includeUndefined) : new EmptyArrayLikeIterator());
         }
      }
   }
}
