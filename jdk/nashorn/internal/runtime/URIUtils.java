package jdk.nashorn.internal.runtime;

public final class URIUtils {
   private static final String URI_UNESCAPED_NONALPHANUMERIC = "-_.!~*'()";
   private static final String URI_RESERVED = ";/?:@&=+$,#";

   private URIUtils() {
   }

   static String encodeURI(Object self, String string) {
      return encode(self, string, false);
   }

   static String encodeURIComponent(Object self, String string) {
      return encode(self, string, true);
   }

   static String decodeURI(Object self, String string) {
      return decode(self, string, false);
   }

   static String decodeURIComponent(Object self, String string) {
      return decode(self, string, true);
   }

   private static String encode(Object self, String string, boolean component) {
      if (string.isEmpty()) {
         return string;
      } else {
         int len = string.length();
         StringBuilder sb = new StringBuilder();

         for(int k = 0; k < len; ++k) {
            char C = string.charAt(k);
            if (isUnescaped(C, component)) {
               sb.append(C);
            } else {
               if (C >= '\udc00' && C <= '\udfff') {
                  return error(string, k);
               }

               int V;
               if (C >= '\ud800' && C <= '\udbff') {
                  ++k;
                  if (k == len) {
                     return error(string, k);
                  }

                  char kChar = string.charAt(k);
                  if (kChar < '\udc00' || kChar > '\udfff') {
                     return error(string, k);
                  }

                  V = (C - '\ud800') * 1024 + (kChar - '\udc00') + 65536;
               } else {
                  V = C;
               }

               try {
                  sb.append(toHexEscape(V));
               } catch (Exception var9) {
                  throw ECMAErrors.uriError((Throwable)var9, "bad.uri", string, Integer.toString(k));
               }
            }
         }

         return sb.toString();
      }
   }

   private static String decode(Object self, String string, boolean component) {
      if (string.isEmpty()) {
         return string;
      } else {
         int len = string.length();
         StringBuilder sb = new StringBuilder();

         for(int k = 0; k < len; ++k) {
            char ch = string.charAt(k);
            if (ch != '%') {
               sb.append(ch);
            } else {
               int start = k;
               if (k + 2 >= len) {
                  return error(string, k);
               }

               int B = toHexByte(string.charAt(k + 1), string.charAt(k + 2));
               if (B < 0) {
                  return error(string, k + 1);
               }

               k += 2;
               char C;
               if ((B & 128) == 0) {
                  C = (char)B;
                  if (!component && ";/?:@&=+$,#".indexOf(C) >= 0) {
                     for(int j = start; j <= k; ++j) {
                        sb.append(string.charAt(j));
                     }
                  } else {
                     sb.append(C);
                  }
               } else {
                  if ((B & 192) == 128) {
                     return error(string, k);
                  }

                  byte n;
                  int V;
                  int minV;
                  if ((B & 32) == 0) {
                     n = 2;
                     V = B & 31;
                     minV = 128;
                  } else if ((B & 16) == 0) {
                     n = 3;
                     V = B & 15;
                     minV = 2048;
                  } else if ((B & 8) == 0) {
                     n = 4;
                     V = B & 7;
                     minV = 65536;
                  } else if ((B & 4) == 0) {
                     n = 5;
                     V = B & 3;
                     minV = 2097152;
                  } else {
                     if ((B & 2) != 0) {
                        return error(string, k);
                     }

                     n = 6;
                     V = B & 1;
                     minV = 67108864;
                  }

                  if (k + 3 * (n - 1) >= len) {
                     return error(string, k);
                  }

                  int j;
                  for(j = 1; j < n; ++j) {
                     ++k;
                     if (string.charAt(k) != '%') {
                        return error(string, k);
                     }

                     B = toHexByte(string.charAt(k + 1), string.charAt(k + 2));
                     if (B < 0 || (B & 192) != 128) {
                        return error(string, k + 1);
                     }

                     V = V << 6 | B & 63;
                     k += 2;
                  }

                  if (V < minV || V >= 55296 && V <= 57343) {
                     V = Integer.MAX_VALUE;
                  }

                  if (V < 65536) {
                     C = (char)V;
                     if (!component && ";/?:@&=+$,#".indexOf(C) >= 0) {
                        for(j = start; j != k; ++j) {
                           sb.append(string.charAt(j));
                        }
                     } else {
                        sb.append(C);
                     }
                  } else {
                     if (V > 1114111) {
                        return error(string, k);
                     }

                     j = (V - 65536 & 1023) + '\udc00';
                     int H = (V - 65536 >> 10 & 1023) + '\ud800';
                     sb.append((char)H);
                     sb.append((char)j);
                  }
               }
            }
         }

         return sb.toString();
      }
   }

   private static int hexDigit(char ch) {
      char chu = Character.toUpperCase(ch);
      if (chu >= '0' && chu <= '9') {
         return chu - 48;
      } else {
         return chu >= 'A' && chu <= 'F' ? chu - 65 + 10 : -1;
      }
   }

   private static int toHexByte(char ch1, char ch2) {
      int i1 = hexDigit(ch1);
      int i2 = hexDigit(ch2);
      return i1 >= 0 && i2 >= 0 ? i1 << 4 | i2 : -1;
   }

   private static String toHexEscape(int u0) {
      int u = u0;
      byte[] b = new byte[6];
      int len;
      if (u0 <= 127) {
         b[0] = (byte)u0;
         len = 1;
      } else {
         len = 2;

         int i;
         for(i = u0 >>> 11; i != 0; i >>>= 5) {
            ++len;
         }

         for(i = len - 1; i > 0; --i) {
            b[i] = (byte)(128 | u & 63);
            u >>>= 6;
         }

         b[0] = (byte)(~((1 << 8 - len) - 1) | u);
      }

      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < len; ++i) {
         sb.append('%');
         if ((b[i] & 255) < 16) {
            sb.append('0');
         }

         sb.append(Integer.toHexString(b[i] & 255).toUpperCase());
      }

      return sb.toString();
   }

   private static String error(String string, int index) {
      throw ECMAErrors.uriError("bad.uri", string, Integer.toString(index));
   }

   private static boolean isUnescaped(char ch, boolean component) {
      if (('A' > ch || ch > 'Z') && ('a' > ch || ch > 'z') && ('0' > ch || ch > '9')) {
         if ("-_.!~*'()".indexOf(ch) >= 0) {
            return true;
         } else if (!component) {
            return ";/?:@&=+$,#".indexOf(ch) >= 0;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }
}
