package jdk.internal.dynalink.support;

public class NameCodec {
   private static final char ESCAPE_CHAR = '\\';
   private static final char EMPTY_ESCAPE = '=';
   private static final String EMPTY_NAME = new String(new char[]{'\\', '='});
   private static final char EMPTY_CHAR = '\ufeff';
   private static final int MIN_ENCODING = 36;
   private static final int MAX_ENCODING = 93;
   private static final char[] ENCODING = new char[58];
   private static final int MIN_DECODING = 33;
   private static final int MAX_DECODING = 125;
   private static final char[] DECODING = new char[93];

   private NameCodec() {
   }

   public static String encode(String name) {
      int l = name.length();
      if (l == 0) {
         return EMPTY_NAME;
      } else {
         StringBuilder b = null;
         int lastEscape = -1;

         for(int i = 0; i < l; ++i) {
            int encodeIndex = name.charAt(i) - 36;
            if (encodeIndex >= 0 && encodeIndex < ENCODING.length) {
               char e = ENCODING[encodeIndex];
               if (e != 0) {
                  if (b == null) {
                     b = new StringBuilder(name.length() + 3);
                     if (name.charAt(0) != '\\' && i > 0) {
                        b.append(EMPTY_NAME);
                     }

                     b.append(name, 0, i);
                  } else {
                     b.append(name, lastEscape + 1, i);
                  }

                  b.append('\\').append(e);
                  lastEscape = i;
               }
            }
         }

         if (b == null) {
            return name.toString();
         } else {
            assert lastEscape != -1;

            b.append(name, lastEscape + 1, l);
            return b.toString();
         }
      }
   }

   public static String decode(String name) {
      if (name.charAt(0) != '\\') {
         return name;
      } else {
         int l = name.length();
         if (l == 2 && name.charAt(1) == '\ufeff') {
            return "";
         } else {
            StringBuilder b = new StringBuilder(name.length());
            int lastEscape = -2;
            int lastBackslash = -1;

            while(true) {
               int nextBackslash = name.indexOf(92, lastBackslash + 1);
               if (nextBackslash == -1 || nextBackslash == l - 1) {
                  b.append(name, lastEscape + 2, l);
                  return b.toString();
               }

               int decodeIndex = name.charAt(nextBackslash + 1) - 33;
               if (decodeIndex >= 0 && decodeIndex < DECODING.length) {
                  char d = DECODING[decodeIndex];
                  if (d == '\ufeff') {
                     if (nextBackslash == 0) {
                        lastEscape = 0;
                     }
                  } else if (d != 0) {
                     b.append(name, lastEscape + 2, nextBackslash).append(d);
                     lastEscape = nextBackslash;
                  }
               }

               lastBackslash = nextBackslash;
            }
         }
      }
   }

   private static void addEncoding(char from, char to) {
      ENCODING[from - 36] = to;
      DECODING[to - 33] = from;
   }

   static {
      addEncoding('/', '|');
      addEncoding('.', ',');
      addEncoding(';', '?');
      addEncoding('$', '%');
      addEncoding('<', '^');
      addEncoding('>', '_');
      addEncoding('[', '{');
      addEncoding(']', '}');
      addEncoding(':', '!');
      addEncoding('\\', '-');
      DECODING[28] = '\ufeff';
   }
}
