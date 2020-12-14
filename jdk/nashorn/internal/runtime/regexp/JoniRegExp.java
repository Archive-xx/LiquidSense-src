package jdk.nashorn.internal.runtime.regexp;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.regexp.joni.Matcher;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import jdk.nashorn.internal.runtime.regexp.joni.Region;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import jdk.nashorn.internal.runtime.regexp.joni.exception.JOniException;

public class JoniRegExp extends RegExp {
   private Regex regex;

   public JoniRegExp(String pattern, String flags) throws ParserException {
      super(pattern, flags);
      int option = 8;
      if (this.isIgnoreCase()) {
         option |= 1;
      }

      if (this.isMultiline()) {
         option &= -9;
         option |= 64;
      }

      try {
         RegExpScanner parsed;
         try {
            parsed = RegExpScanner.scan(pattern);
         } catch (PatternSyntaxException var6) {
            Pattern.compile(pattern, 0);
            throw var6;
         }

         if (parsed != null) {
            char[] javaPattern = parsed.getJavaPattern().toCharArray();
            this.regex = new Regex(javaPattern, 0, javaPattern.length, option, Syntax.JAVASCRIPT);
            this.groupsInNegativeLookahead = parsed.getGroupsInNegativeLookahead();
         }
      } catch (JOniException | PatternSyntaxException var7) {
         throwParserException("syntax", var7.getMessage());
      }

   }

   public RegExpMatcher match(String input) {
      return this.regex == null ? null : new JoniRegExp.JoniMatcher(input);
   }

   class JoniMatcher implements RegExpMatcher {
      final String input;
      final Matcher joniMatcher;

      JoniMatcher(String input) {
         this.input = input;
         this.joniMatcher = JoniRegExp.this.regex.matcher(input.toCharArray());
      }

      public boolean search(int start) {
         return this.joniMatcher.search(start, this.input.length(), 0) > -1;
      }

      public String getInput() {
         return this.input;
      }

      public int start() {
         return this.joniMatcher.getBegin();
      }

      public int start(int group) {
         return group == 0 ? this.start() : this.joniMatcher.getRegion().beg[group];
      }

      public int end() {
         return this.joniMatcher.getEnd();
      }

      public int end(int group) {
         return group == 0 ? this.end() : this.joniMatcher.getRegion().end[group];
      }

      public String group() {
         return this.input.substring(this.joniMatcher.getBegin(), this.joniMatcher.getEnd());
      }

      public String group(int group) {
         if (group == 0) {
            return this.group();
         } else {
            Region region = this.joniMatcher.getRegion();
            return this.input.substring(region.beg[group], region.end[group]);
         }
      }

      public int groupCount() {
         Region region = this.joniMatcher.getRegion();
         return region == null ? 0 : region.numRegs - 1;
      }
   }

   public static class Factory extends RegExpFactory {
      public RegExp compile(String pattern, String flags) throws ParserException {
         return new JoniRegExp(pattern, flags);
      }
   }
}
