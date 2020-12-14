package org.apache.log4j.pattern;

public final class BridgePatternParser extends org.apache.log4j.helpers.PatternParser {
   public BridgePatternParser(String conversionPattern) {
      super(conversionPattern);
   }

   public org.apache.log4j.helpers.PatternConverter parse() {
      return new BridgePatternConverter(this.pattern);
   }
}
