package org.slf4j.helpers;

import java.util.HashMap;
import java.util.Map;

public final class MessageFormatter {
   static final char DELIM_START = '{';
   static final char DELIM_STOP = '}';
   static final String DELIM_STR = "{}";
   private static final char ESCAPE_CHAR = '\\';

   public static final FormattingTuple format(String messagePattern, Object arg) {
      return arrayFormat(messagePattern, new Object[]{arg});
   }

   public static final FormattingTuple format(String messagePattern, Object arg1, Object arg2) {
      return arrayFormat(messagePattern, new Object[]{arg1, arg2});
   }

   static final Throwable getThrowableCandidate(Object[] argArray) {
      if (argArray != null && argArray.length != 0) {
         Object lastEntry = argArray[argArray.length - 1];
         return lastEntry instanceof Throwable ? (Throwable)lastEntry : null;
      } else {
         return null;
      }
   }

   public static final FormattingTuple arrayFormat(String messagePattern, Object[] argArray) {
      Throwable throwableCandidate = getThrowableCandidate(argArray);
      Object[] args = argArray;
      if (throwableCandidate != null) {
         args = trimmedCopy(argArray);
      }

      return arrayFormat(messagePattern, args, throwableCandidate);
   }

   private static Object[] trimmedCopy(Object[] argArray) {
      if (argArray != null && argArray.length != 0) {
         int trimemdLen = argArray.length - 1;
         Object[] trimmed = new Object[trimemdLen];
         System.arraycopy(argArray, 0, trimmed, 0, trimemdLen);
         return trimmed;
      } else {
         throw new IllegalStateException("non-sensical empty or null argument array");
      }
   }

   public static final FormattingTuple arrayFormat(String messagePattern, Object[] argArray, Throwable throwable) {
      if (messagePattern == null) {
         return new FormattingTuple((String)null, argArray, throwable);
      } else if (argArray == null) {
         return new FormattingTuple(messagePattern);
      } else {
         int i = 0;
         StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);

         for(int L = 0; L < argArray.length; ++L) {
            int j = messagePattern.indexOf("{}", i);
            if (j == -1) {
               if (i == 0) {
                  return new FormattingTuple(messagePattern, argArray, throwable);
               }

               sbuf.append(messagePattern, i, messagePattern.length());
               return new FormattingTuple(sbuf.toString(), argArray, throwable);
            }

            if (isEscapedDelimeter(messagePattern, j)) {
               if (!isDoubleEscaped(messagePattern, j)) {
                  --L;
                  sbuf.append(messagePattern, i, j - 1);
                  sbuf.append('{');
                  i = j + 1;
               } else {
                  sbuf.append(messagePattern, i, j - 1);
                  deeplyAppendParameter(sbuf, argArray[L], new HashMap());
                  i = j + 2;
               }
            } else {
               sbuf.append(messagePattern, i, j);
               deeplyAppendParameter(sbuf, argArray[L], new HashMap());
               i = j + 2;
            }
         }

         sbuf.append(messagePattern, i, messagePattern.length());
         return new FormattingTuple(sbuf.toString(), argArray, throwable);
      }
   }

   static final boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {
      if (delimeterStartIndex == 0) {
         return false;
      } else {
         char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
         return potentialEscape == '\\';
      }
   }

   static final boolean isDoubleEscaped(String messagePattern, int delimeterStartIndex) {
      return delimeterStartIndex >= 2 && messagePattern.charAt(delimeterStartIndex - 2) == '\\';
   }

   private static void deeplyAppendParameter(StringBuilder sbuf, Object o, Map<Object[], Object> seenMap) {
      if (o == null) {
         sbuf.append("null");
      } else {
         if (!o.getClass().isArray()) {
            safeObjectAppend(sbuf, o);
         } else if (o instanceof boolean[]) {
            booleanArrayAppend(sbuf, (boolean[])((boolean[])o));
         } else if (o instanceof byte[]) {
            byteArrayAppend(sbuf, (byte[])((byte[])o));
         } else if (o instanceof char[]) {
            charArrayAppend(sbuf, (char[])((char[])o));
         } else if (o instanceof short[]) {
            shortArrayAppend(sbuf, (short[])((short[])o));
         } else if (o instanceof int[]) {
            intArrayAppend(sbuf, (int[])((int[])o));
         } else if (o instanceof long[]) {
            longArrayAppend(sbuf, (long[])((long[])o));
         } else if (o instanceof float[]) {
            floatArrayAppend(sbuf, (float[])((float[])o));
         } else if (o instanceof double[]) {
            doubleArrayAppend(sbuf, (double[])((double[])o));
         } else {
            objectArrayAppend(sbuf, (Object[])((Object[])o), seenMap);
         }

      }
   }

   private static void safeObjectAppend(StringBuilder sbuf, Object o) {
      try {
         String oAsString = o.toString();
         sbuf.append(oAsString);
      } catch (Throwable var3) {
         Util.report("SLF4J: Failed toString() invocation on an object of type [" + o.getClass().getName() + "]", var3);
         sbuf.append("[FAILED toString()]");
      }

   }

   private static void objectArrayAppend(StringBuilder sbuf, Object[] a, Map<Object[], Object> seenMap) {
      sbuf.append('[');
      if (!seenMap.containsKey(a)) {
         seenMap.put(a, (Object)null);
         int len = a.length;

         for(int i = 0; i < len; ++i) {
            deeplyAppendParameter(sbuf, a[i], seenMap);
            if (i != len - 1) {
               sbuf.append(", ");
            }
         }

         seenMap.remove(a);
      } else {
         sbuf.append("...");
      }

      sbuf.append(']');
   }

   private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }

   private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }

   private static void charArrayAppend(StringBuilder sbuf, char[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }

   private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }

   private static void intArrayAppend(StringBuilder sbuf, int[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }

   private static void longArrayAppend(StringBuilder sbuf, long[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }

   private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }

   private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
      sbuf.append('[');
      int len = a.length;

      for(int i = 0; i < len; ++i) {
         sbuf.append(a[i]);
         if (i != len - 1) {
            sbuf.append(", ");
         }
      }

      sbuf.append(']');
   }
}
