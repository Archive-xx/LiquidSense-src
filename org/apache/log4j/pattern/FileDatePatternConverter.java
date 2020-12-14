package org.apache.log4j.pattern;

public final class FileDatePatternConverter {
   private FileDatePatternConverter() {
   }

   public static PatternConverter newInstance(String[] options) {
      return options != null && options.length != 0 ? DatePatternConverter.newInstance(options) : DatePatternConverter.newInstance(new String[]{"yyyy-MM-dd"});
   }
}
