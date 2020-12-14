package jdk.nashorn.internal.runtime.regexp.joni;

public abstract class SearchAlgorithm {
   public static final SearchAlgorithm NONE = new SearchAlgorithm() {
      public final String getName() {
         return "NONE";
      }

      public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
         return textP;
      }

      public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
         return textP;
      }
   };
   public static final SearchAlgorithm SLOW = new SearchAlgorithm() {
      public final String getName() {
         return "EXACT";
      }

      public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
         char[] target = regex.exact;
         int targetP = regex.exactP;
         int targetEnd = regex.exactEnd;
         int end = textEnd - (targetEnd - targetP - 1);
         if (end > textRange) {
            end = textRange;
         }

         for(int s = textP; s < end; ++s) {
            if (text[s] == target[targetP]) {
               int p = s + 1;

               int t;
               for(t = targetP + 1; t < targetEnd && target[t] == text[p++]; ++t) {
               }

               if (t == targetEnd) {
                  return s;
               }
            }
         }

         return -1;
      }

      public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
         char[] target = regex.exact;
         int targetP = regex.exactP;
         int targetEnd = regex.exactEnd;
         int s = textEnd - (targetEnd - targetP);
         if (s > textStart) {
            s = textStart;
         }

         for(; s >= textP; --s) {
            if (text[s] == target[targetP]) {
               int p = s + 1;

               int t;
               for(t = targetP + 1; t < targetEnd && target[t] == text[p++]; ++t) {
               }

               if (t == targetEnd) {
                  return s;
               }
            }
         }

         return -1;
      }
   };
   public static final SearchAlgorithm BM = new SearchAlgorithm() {
      private static final int BM_BACKWARD_SEARCH_LENGTH_THRESHOLD = 100;

      public final String getName() {
         return "EXACT_BM";
      }

      public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
         char[] target = regex.exact;
         int targetP = regex.exactP;
         int targetEnd = regex.exactEnd;
         int end = textRange + (targetEnd - targetP) - 1;
         if (end > textEnd) {
            end = textEnd;
         }

         int tail = targetEnd - 1;
         int s = textP + (targetEnd - targetP) - 1;
         int p;
         int t;
         if (regex.intMap == null) {
            while(s < end) {
               p = s;

               for(t = tail; text[p] == target[t]; --t) {
                  if (t == targetP) {
                     return p;
                  }

                  --p;
               }

               s += regex.map[text[s] & 255];
            }
         } else {
            while(s < end) {
               p = s;

               for(t = tail; text[p] == target[t]; --t) {
                  if (t == targetP) {
                     return p;
                  }

                  --p;
               }

               s += regex.intMap[text[s] & 255];
            }
         }

         return -1;
      }

      public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
         char[] target = regex.exact;
         int targetP = regex.exactP;
         int targetEnd = regex.exactEnd;
         if (regex.intMapBackward == null) {
            if (s_ - range_ < 100) {
               return SLOW.searchBackward(regex, text, textP, adjustText, textEnd, textStart, s_, range_);
            }

            this.setBmBackwardSkip(regex, target, targetP, targetEnd);
         }

         int s = textEnd - (targetEnd - targetP);
         if (textStart < s) {
            s = textStart;
         }

         while(s >= textP) {
            int p = s;

            int t;
            for(t = targetP; t < targetEnd && text[p] == target[t]; ++t) {
               ++p;
            }

            if (t == targetEnd) {
               return s;
            }

            s -= regex.intMapBackward[text[s] & 255];
         }

         return -1;
      }

      private void setBmBackwardSkip(Regex regex, char[] chars, int p, int end) {
         int[] skip;
         if (regex.intMapBackward == null) {
            skip = new int[256];
            regex.intMapBackward = skip;
         } else {
            skip = regex.intMapBackward;
         }

         int len = end - p;

         int i;
         for(i = 0; i < 256; ++i) {
            skip[i] = len;
         }

         for(i = len - 1; i > 0; skip[chars[i] & 255] = i--) {
         }

      }
   };
   public static final SearchAlgorithm MAP = new SearchAlgorithm() {
      public final String getName() {
         return "MAP";
      }

      public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
         byte[] map = regex.map;

         for(int s = textP; s < textRange; ++s) {
            if (text[s] > 255 || map[text[s]] != 0) {
               return s;
            }
         }

         return -1;
      }

      public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
         byte[] map = regex.map;
         int s = textStart;
         if (textStart >= textEnd) {
            s = textEnd - 1;
         }

         while(s >= textP) {
            if (text[s] > 255 || map[text[s]] != 0) {
               return s;
            }

            --s;
         }

         return -1;
      }
   };

   public abstract String getName();

   public abstract int search(Regex var1, char[] var2, int var3, int var4, int var5);

   public abstract int searchBackward(Regex var1, char[] var2, int var3, int var4, int var5, int var6, int var7, int var8);

   public static final class SLOW_IC extends SearchAlgorithm {
      public SLOW_IC(Regex regex) {
      }

      public final String getName() {
         return "EXACT_IC";
      }

      public final int search(Regex regex, char[] text, int textP, int textEnd, int textRange) {
         char[] target = regex.exact;
         int targetP = regex.exactP;
         int targetEnd = regex.exactEnd;
         int end = textEnd - (targetEnd - targetP - 1);
         if (end > textRange) {
            end = textRange;
         }

         for(int s = textP; s < end; ++s) {
            if (lowerCaseMatch(target, targetP, targetEnd, text, s, textEnd)) {
               return s;
            }
         }

         return -1;
      }

      public final int searchBackward(Regex regex, char[] text, int textP, int adjustText, int textEnd, int textStart, int s_, int range_) {
         char[] target = regex.exact;
         int targetP = regex.exactP;
         int targetEnd = regex.exactEnd;
         int s = textEnd - (targetEnd - targetP);
         if (s > textStart) {
            s = textStart;
         }

         while(s >= textP) {
            if (lowerCaseMatch(target, targetP, targetEnd, text, s, textEnd)) {
               return s;
            }

            s = EncodingHelper.prevCharHead(adjustText, s);
         }

         return -1;
      }

      private static boolean lowerCaseMatch(char[] t, int tPp, int tEnd, char[] chars, int pp, int end) {
         int tP = tPp;
         int var7 = pp;

         do {
            if (tP >= tEnd) {
               return true;
            }
         } while(t[tP++] == EncodingHelper.toLowerCase(chars[var7++]));

         return false;
      }
   }
}
