package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Locale;
import jdk.nashorn.internal.lookup.Lookup;

public final class GlobalFunctions {
   public static final MethodHandle PARSEINT;
   public static final MethodHandle PARSEINT_OI;
   public static final MethodHandle PARSEINT_Z;
   public static final MethodHandle PARSEINT_I;
   public static final MethodHandle PARSEINT_O;
   public static final MethodHandle PARSEFLOAT;
   public static final MethodHandle IS_NAN_I;
   public static final MethodHandle IS_NAN_J;
   public static final MethodHandle IS_NAN_D;
   public static final MethodHandle IS_NAN;
   public static final MethodHandle IS_FINITE;
   public static final MethodHandle ENCODE_URI;
   public static final MethodHandle ENCODE_URICOMPONENT;
   public static final MethodHandle DECODE_URI;
   public static final MethodHandle DECODE_URICOMPONENT;
   public static final MethodHandle ESCAPE;
   public static final MethodHandle UNESCAPE;
   public static final MethodHandle ANONYMOUS;
   private static final String UNESCAPED = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*_+-./";

   private GlobalFunctions() {
   }

   public static double parseInt(Object self, Object string, Object rad) {
      return parseIntInternal(JSType.trimLeft(JSType.toString(string)), JSType.toInt32(rad));
   }

   public static double parseInt(Object self, Object string, int rad) {
      return parseIntInternal(JSType.trimLeft(JSType.toString(string)), rad);
   }

   public static double parseInt(Object self, Object string) {
      return parseIntInternal(JSType.trimLeft(JSType.toString(string)), 0);
   }

   private static double parseIntInternal(String str, int rad) {
      int length = str.length();
      int radix = rad;
      if (length == 0) {
         return Double.NaN;
      } else {
         boolean negative = false;
         int idx = 0;
         char firstChar = str.charAt(idx);
         if (firstChar < '0') {
            if (firstChar == '-') {
               negative = true;
            } else if (firstChar != '+') {
               return Double.NaN;
            }

            ++idx;
         }

         boolean stripPrefix = true;
         if (rad != 0) {
            if (rad < 2 || rad > 36) {
               return Double.NaN;
            }

            if (rad != 16) {
               stripPrefix = false;
            }
         } else {
            radix = 10;
         }

         if (stripPrefix && idx + 1 < length) {
            char c1 = str.charAt(idx);
            char c2 = str.charAt(idx + 1);
            if (c1 == '0' && (c2 == 'x' || c2 == 'X')) {
               radix = 16;
               idx += 2;
            }
         }

         double result = 0.0D;

         int digit;
         boolean entered;
         for(entered = false; idx < length; result += (double)digit) {
            digit = fastDigit(str.charAt(idx++), radix);
            if (digit < 0) {
               break;
            }

            entered = true;
            result *= (double)radix;
         }

         return entered ? (negative ? -result : result) : Double.NaN;
      }
   }

   public static double parseFloat(Object self, Object string) {
      String str = JSType.trimLeft(JSType.toString(string));
      int length = str.length();
      if (length == 0) {
         return Double.NaN;
      } else {
         int start = 0;
         boolean negative = false;
         char ch = str.charAt(0);
         if (ch == '-') {
            ++start;
            negative = true;
         } else if (ch == '+') {
            ++start;
         } else if (ch == 'N' && str.startsWith("NaN")) {
            return Double.NaN;
         }

         if (start == length) {
            return Double.NaN;
         } else {
            ch = str.charAt(start);
            if (ch == 'I' && str.substring(start).startsWith("Infinity")) {
               return negative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
            } else {
               boolean dotSeen = false;
               boolean exponentOk = false;
               int exponentOffset = -1;

               int end;
               label86:
               for(end = start; end < length; ++end) {
                  ch = str.charAt(end);
                  switch(ch) {
                  case '+':
                  case '-':
                     if (exponentOffset != end - 1) {
                        break label86;
                     }
                     break;
                  case ',':
                  case '/':
                  case ':':
                  case ';':
                  case '<':
                  case '=':
                  case '>':
                  case '?':
                  case '@':
                  case 'A':
                  case 'B':
                  case 'C':
                  case 'D':
                  case 'F':
                  case 'G':
                  case 'H':
                  case 'I':
                  case 'J':
                  case 'K':
                  case 'L':
                  case 'M':
                  case 'N':
                  case 'O':
                  case 'P':
                  case 'Q':
                  case 'R':
                  case 'S':
                  case 'T':
                  case 'U':
                  case 'V':
                  case 'W':
                  case 'X':
                  case 'Y':
                  case 'Z':
                  case '[':
                  case '\\':
                  case ']':
                  case '^':
                  case '_':
                  case '`':
                  case 'a':
                  case 'b':
                  case 'c':
                  case 'd':
                  default:
                     break label86;
                  case '.':
                     if (exponentOffset != -1 || dotSeen) {
                        break label86;
                     }

                     dotSeen = true;
                     break;
                  case '0':
                  case '1':
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                  case '6':
                  case '7':
                  case '8':
                  case '9':
                     if (exponentOffset != -1) {
                        exponentOk = true;
                     }
                     break;
                  case 'E':
                  case 'e':
                     if (exponentOffset != -1) {
                        break label86;
                     }

                     exponentOffset = end;
                  }
               }

               if (exponentOffset != -1 && !exponentOk) {
                  end = exponentOffset;
               }

               if (start == end) {
                  return Double.NaN;
               } else {
                  try {
                     double result = Double.valueOf(str.substring(start, end));
                     return negative ? -result : result;
                  } catch (NumberFormatException var13) {
                     return Double.NaN;
                  }
               }
            }
         }
      }
   }

   public static boolean isNaN(Object self, Object number) {
      return Double.isNaN(JSType.toNumber(number));
   }

   public static boolean isFinite(Object self, Object number) {
      double value = JSType.toNumber(number);
      return !Double.isInfinite(value) && !Double.isNaN(value);
   }

   public static Object encodeURI(Object self, Object uri) {
      return URIUtils.encodeURI(self, JSType.toString(uri));
   }

   public static Object encodeURIComponent(Object self, Object uri) {
      return URIUtils.encodeURIComponent(self, JSType.toString(uri));
   }

   public static Object decodeURI(Object self, Object uri) {
      return URIUtils.decodeURI(self, JSType.toString(uri));
   }

   public static Object decodeURIComponent(Object self, Object uri) {
      return URIUtils.decodeURIComponent(self, JSType.toString(uri));
   }

   public static String escape(Object self, Object string) {
      String str = JSType.toString(string);
      int length = str.length();
      if (length == 0) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder();

         for(int k = 0; k < length; ++k) {
            char ch = str.charAt(k);
            if ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@*_+-./".indexOf(ch) != -1) {
               sb.append(ch);
            } else if (ch < 256) {
               sb.append('%');
               if (ch < 16) {
                  sb.append('0');
               }

               sb.append(Integer.toHexString(ch).toUpperCase(Locale.ENGLISH));
            } else {
               sb.append("%u");
               if (ch < 4096) {
                  sb.append('0');
               }

               sb.append(Integer.toHexString(ch).toUpperCase(Locale.ENGLISH));
            }
         }

         return sb.toString();
      }
   }

   public static String unescape(Object self, Object string) {
      String str = JSType.toString(string);
      int length = str.length();
      if (length == 0) {
         return str;
      } else {
         StringBuilder sb = new StringBuilder();

         for(int k = 0; k < length; ++k) {
            char ch = str.charAt(k);
            if (ch != '%') {
               sb.append(ch);
            } else {
               if (k < length - 5 && str.charAt(k + 1) == 'u') {
                  try {
                     ch = (char)Integer.parseInt(str.substring(k + 2, k + 6), 16);
                     sb.append(ch);
                     k += 5;
                     continue;
                  } catch (NumberFormatException var9) {
                  }
               }

               if (k < length - 2) {
                  try {
                     ch = (char)Integer.parseInt(str.substring(k + 1, k + 3), 16);
                     sb.append(ch);
                     k += 2;
                     continue;
                  } catch (NumberFormatException var8) {
                  }
               }

               sb.append(ch);
            }
         }

         return sb.toString();
      }
   }

   public static Object anonymous(Object self) {
      return ScriptRuntime.UNDEFINED;
   }

   private static int fastDigit(int ch, int radix) {
      int n = -1;
      if (ch >= 48 && ch <= 57) {
         n = ch - 48;
      } else if (radix > 10) {
         if (ch >= 97 && ch <= 122) {
            n = ch - 97 + 10;
         } else if (ch >= 65 && ch <= 90) {
            n = ch - 65 + 10;
         }
      }

      return n < radix ? n : -1;
   }

   private static MethodHandle findOwnMH(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), GlobalFunctions.class, name, Lookup.MH.type(rtype, types));
   }

   static {
      PARSEINT = findOwnMH("parseInt", Double.TYPE, Object.class, Object.class, Object.class);
      PARSEINT_OI = findOwnMH("parseInt", Double.TYPE, Object.class, Object.class, Integer.TYPE);
      PARSEINT_Z = Lookup.MH.dropArguments(Lookup.MH.dropArguments(Lookup.MH.constant(Double.TYPE, Double.NaN), 0, (Class[])(Boolean.TYPE)), 0, (Class[])(Object.class));
      PARSEINT_I = Lookup.MH.dropArguments(Lookup.MH.identity(Integer.TYPE), 0, (Class[])(Object.class));
      PARSEINT_O = findOwnMH("parseInt", Double.TYPE, Object.class, Object.class);
      PARSEFLOAT = findOwnMH("parseFloat", Double.TYPE, Object.class, Object.class);
      IS_NAN_I = Lookup.MH.dropArguments(Lookup.MH.constant(Boolean.TYPE, false), 0, (Class[])(Object.class));
      IS_NAN_J = Lookup.MH.dropArguments(Lookup.MH.constant(Boolean.TYPE, false), 0, (Class[])(Object.class));
      IS_NAN_D = Lookup.MH.dropArguments(Lookup.MH.findStatic(MethodHandles.lookup(), Double.class, "isNaN", Lookup.MH.type(Boolean.TYPE, Double.TYPE)), 0, (Class[])(Object.class));
      IS_NAN = findOwnMH("isNaN", Boolean.TYPE, Object.class, Object.class);
      IS_FINITE = findOwnMH("isFinite", Boolean.TYPE, Object.class, Object.class);
      ENCODE_URI = findOwnMH("encodeURI", Object.class, Object.class, Object.class);
      ENCODE_URICOMPONENT = findOwnMH("encodeURIComponent", Object.class, Object.class, Object.class);
      DECODE_URI = findOwnMH("decodeURI", Object.class, Object.class, Object.class);
      DECODE_URICOMPONENT = findOwnMH("decodeURIComponent", Object.class, Object.class, Object.class);
      ESCAPE = findOwnMH("escape", String.class, Object.class, Object.class);
      UNESCAPE = findOwnMH("unescape", String.class, Object.class, Object.class);
      ANONYMOUS = findOwnMH("anonymous", Object.class, Object.class);
   }
}
