package jdk.nashorn.internal.runtime.regexp;

import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ParserException;

public abstract class RegExp {
   private final String source;
   private boolean global;
   private boolean ignoreCase;
   private boolean multiline;
   protected BitVector groupsInNegativeLookahead;

   protected RegExp(String source, String flags) {
      this.source = source.length() == 0 ? "(?:)" : source;

      for(int i = 0; i < flags.length(); ++i) {
         char ch = flags.charAt(i);
         switch(ch) {
         case 'g':
            if (this.global) {
               throwParserException("repeated.flag", "g");
            }

            this.global = true;
            break;
         case 'i':
            if (this.ignoreCase) {
               throwParserException("repeated.flag", "i");
            }

            this.ignoreCase = true;
            break;
         case 'm':
            if (this.multiline) {
               throwParserException("repeated.flag", "m");
            }

            this.multiline = true;
            break;
         default:
            throwParserException("unsupported.flag", Character.toString(ch));
         }
      }

   }

   public String getSource() {
      return this.source;
   }

   public void setGlobal(boolean global) {
      this.global = global;
   }

   public boolean isGlobal() {
      return this.global;
   }

   public boolean isIgnoreCase() {
      return this.ignoreCase;
   }

   public boolean isMultiline() {
      return this.multiline;
   }

   public BitVector getGroupsInNegativeLookahead() {
      return this.groupsInNegativeLookahead;
   }

   public abstract RegExpMatcher match(String var1);

   protected static void throwParserException(String key, String str) throws ParserException {
      throw new ParserException(ECMAErrors.getMessage("parser.error.regex." + key, str));
   }
}
