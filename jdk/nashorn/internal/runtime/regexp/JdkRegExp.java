package jdk.nashorn.internal.runtime.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import jdk.nashorn.internal.runtime.ParserException;

public class JdkRegExp extends RegExp {
   private Pattern pattern;

   public JdkRegExp(String source, String flags) throws ParserException {
      super(source, flags);
      int intFlags = 0;
      if (this.isIgnoreCase()) {
         intFlags |= 66;
      }

      if (this.isMultiline()) {
         intFlags |= 8;
      }

      try {
         RegExpScanner parsed;
         try {
            parsed = RegExpScanner.scan(source);
         } catch (PatternSyntaxException var6) {
            Pattern.compile(source, intFlags);
            throw var6;
         }

         if (parsed != null) {
            this.pattern = Pattern.compile(parsed.getJavaPattern(), intFlags);
            this.groupsInNegativeLookahead = parsed.getGroupsInNegativeLookahead();
         }
      } catch (PatternSyntaxException var7) {
         throwParserException("syntax", var7.getMessage());
      }

   }

   public RegExpMatcher match(String str) {
      return this.pattern == null ? null : new JdkRegExp.DefaultMatcher(str);
   }

   class DefaultMatcher implements RegExpMatcher {
      final String input;
      final Matcher defaultMatcher;

      DefaultMatcher(String input) {
         this.input = input;
         this.defaultMatcher = JdkRegExp.this.pattern.matcher(input);
      }

      public boolean search(int start) {
         return this.defaultMatcher.find(start);
      }

      public String getInput() {
         return this.input;
      }

      public int start() {
         return this.defaultMatcher.start();
      }

      public int start(int group) {
         return this.defaultMatcher.start(group);
      }

      public int end() {
         return this.defaultMatcher.end();
      }

      public int end(int group) {
         return this.defaultMatcher.end(group);
      }

      public String group() {
         return this.defaultMatcher.group();
      }

      public String group(int group) {
         return this.defaultMatcher.group(group);
      }

      public int groupCount() {
         return this.defaultMatcher.groupCount();
      }
   }
}
