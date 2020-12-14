package jdk.nashorn.internal.runtime;

import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public final class QuotedStringTokenizer {
   private final LinkedList<String> tokens;
   private final char[] quotes;

   public QuotedStringTokenizer(String str) {
      this(str, " ");
   }

   public QuotedStringTokenizer(String str, String delim) {
      this(str, delim, new char[]{'"', '\''});
   }

   private QuotedStringTokenizer(String str, String delim, char[] quotes) {
      this.quotes = quotes;
      boolean delimIsWhitespace = true;

      for(int i = 0; i < delim.length(); ++i) {
         if (!Character.isWhitespace(delim.charAt(i))) {
            delimIsWhitespace = false;
            break;
         }
      }

      StringTokenizer st = new StringTokenizer(str, delim);
      this.tokens = new LinkedList();

      while(st.hasMoreTokens()) {
         String token;
         for(token = st.nextToken(); this.unmatchedQuotesIn(token); token = token + (delimIsWhitespace ? " " : delim) + st.nextToken()) {
            if (!st.hasMoreTokens()) {
               throw new IndexOutOfBoundsException(token);
            }
         }

         this.tokens.add(this.stripQuotes(token));
      }

   }

   public int countTokens() {
      return this.tokens.size();
   }

   public boolean hasMoreTokens() {
      return this.countTokens() > 0;
   }

   public String nextToken() {
      return (String)this.tokens.removeFirst();
   }

   private String stripQuotes(String value0) {
      String value = value0.trim();
      char[] var3 = this.quotes;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char q = var3[var5];
         if (value.length() >= 2 && value.startsWith("" + q) && value.endsWith("" + q)) {
            value = value.substring(1, value.length() - 1);
            value = value.replace("\\" + q, "" + q);
         }
      }

      return value;
   }

   private boolean unmatchedQuotesIn(String str) {
      Stack<Character> quoteStack = new Stack();

      for(int i = 0; i < str.length(); ++i) {
         char c = str.charAt(i);
         char[] var5 = this.quotes;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            char q = var5[var7];
            if (c == q) {
               if (quoteStack.isEmpty()) {
                  quoteStack.push(c);
               } else {
                  char top = (Character)quoteStack.pop();
                  if (top != c) {
                     quoteStack.push(top);
                     quoteStack.push(c);
                  }
               }
            }
         }
      }

      return !quoteStack.isEmpty();
   }
}
