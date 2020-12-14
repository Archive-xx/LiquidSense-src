package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;

public final class ArrayIndex {
   private static final int INVALID_ARRAY_INDEX = -1;
   private static final long MAX_ARRAY_INDEX = 4294967294L;

   private ArrayIndex() {
   }

   private static long fromString(String key) {
      long value = 0L;
      int length = key.length();
      if (length != 0 && (length <= 1 || key.charAt(0) != '0')) {
         for(int i = 0; i < length; ++i) {
            char digit = key.charAt(i);
            if (digit < '0' || digit > '9') {
               return -1L;
            }

            value = value * 10L + (long)digit - 48L;
            if (value > 4294967294L) {
               return -1L;
            }
         }

         return value;
      } else {
         return -1L;
      }
   }

   public static int getArrayIndex(Object key) {
      if (key instanceof Integer) {
         return getArrayIndex((Integer)key);
      } else if (key instanceof Double) {
         return getArrayIndex((Double)key);
      } else if (key instanceof String) {
         return (int)fromString((String)key);
      } else if (key instanceof Long) {
         return getArrayIndex((Long)key);
      } else if (key instanceof ConsString) {
         return (int)fromString(key.toString());
      } else {
         assert !(key instanceof ScriptObject);

         return -1;
      }
   }

   public static int getArrayIndex(int key) {
      return key >= 0 ? key : -1;
   }

   public static int getArrayIndex(long key) {
      return key >= 0L && key <= 4294967294L ? (int)key : -1;
   }

   public static int getArrayIndex(double key) {
      if (JSType.isRepresentableAsInt(key)) {
         return getArrayIndex((int)key);
      } else {
         return JSType.isRepresentableAsLong(key) ? getArrayIndex((long)key) : -1;
      }
   }

   public static int getArrayIndex(String key) {
      return (int)fromString(key);
   }

   public static boolean isValidArrayIndex(int index) {
      return index != -1;
   }

   public static long toLongIndex(int index) {
      return JSType.toUint32(index);
   }

   public static String toKey(int index) {
      return Long.toString(JSType.toUint32(index));
   }
}
