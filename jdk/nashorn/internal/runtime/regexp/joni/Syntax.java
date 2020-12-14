package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.constants.SyntaxProperties;

public final class Syntax implements SyntaxProperties {
   private final int op;
   private final int op2;
   private final int behavior;
   public final int options;
   public final Syntax.MetaCharTable metaCharTable;
   public static final Syntax RUBY = new Syntax(2146948438, 736218, -2086665253, 0, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
   public static final Syntax DEFAULT;
   public static final Syntax ASIS;
   public static final Syntax PosixBasic;
   public static final Syntax PosixExtended;
   public static final Syntax Emacs;
   public static final Syntax Grep;
   public static final Syntax GnuRegex;
   public static final Syntax Java;
   public static final Syntax Perl;
   public static final Syntax PerlNG;
   public static final Syntax JAVASCRIPT;

   public Syntax(int op, int op2, int behavior, int options, Syntax.MetaCharTable metaCharTable) {
      this.op = op;
      this.op2 = op2;
      this.behavior = behavior;
      this.options = options;
      this.metaCharTable = metaCharTable;
   }

   protected boolean isOp(int opm) {
      return (this.op & opm) != 0;
   }

   public boolean opVariableMetaCharacters() {
      return this.isOp(1);
   }

   public boolean opDotAnyChar() {
      return this.isOp(2);
   }

   public boolean opAsteriskZeroInf() {
      return this.isOp(4);
   }

   public boolean opEscAsteriskZeroInf() {
      return this.isOp(8);
   }

   public boolean opPlusOneInf() {
      return this.isOp(16);
   }

   public boolean opEscPlusOneInf() {
      return this.isOp(32);
   }

   public boolean opQMarkZeroOne() {
      return this.isOp(64);
   }

   public boolean opEscQMarkZeroOne() {
      return this.isOp(128);
   }

   public boolean opBraceInterval() {
      return this.isOp(256);
   }

   public boolean opEscBraceInterval() {
      return this.isOp(512);
   }

   public boolean opVBarAlt() {
      return this.isOp(1024);
   }

   public boolean opEscVBarAlt() {
      return this.isOp(2048);
   }

   public boolean opLParenSubexp() {
      return this.isOp(4096);
   }

   public boolean opEscLParenSubexp() {
      return this.isOp(8192);
   }

   public boolean opEscAZBufAnchor() {
      return this.isOp(16384);
   }

   public boolean opEscCapitalGBeginAnchor() {
      return this.isOp(32768);
   }

   public boolean opDecimalBackref() {
      return this.isOp(65536);
   }

   public boolean opBracketCC() {
      return this.isOp(131072);
   }

   public boolean opEscWWord() {
      return this.isOp(262144);
   }

   public boolean opEscLtGtWordBeginEnd() {
      return this.isOp(524288);
   }

   public boolean opEscBWordBound() {
      return this.isOp(1048576);
   }

   public boolean opEscSWhiteSpace() {
      return this.isOp(2097152);
   }

   public boolean opEscDDigit() {
      return this.isOp(4194304);
   }

   public boolean opLineAnchor() {
      return this.isOp(8388608);
   }

   public boolean opPosixBracket() {
      return this.isOp(16777216);
   }

   public boolean opQMarkNonGreedy() {
      return this.isOp(33554432);
   }

   public boolean opEscControlChars() {
      return this.isOp(67108864);
   }

   public boolean opEscCControl() {
      return this.isOp(134217728);
   }

   public boolean opEscOctal3() {
      return this.isOp(268435456);
   }

   public boolean opEscXHex2() {
      return this.isOp(536870912);
   }

   public boolean opEscXBraceHex8() {
      return this.isOp(1073741824);
   }

   protected boolean isOp2(int opm) {
      return (this.op2 & opm) != 0;
   }

   public boolean op2EscCapitalQQuote() {
      return this.isOp2(1);
   }

   public boolean op2QMarkGroupEffect() {
      return this.isOp2(2);
   }

   public boolean op2OptionPerl() {
      return this.isOp2(4);
   }

   public boolean op2OptionRuby() {
      return this.isOp2(8);
   }

   public boolean op2PlusPossessiveRepeat() {
      return this.isOp2(16);
   }

   public boolean op2PlusPossessiveInterval() {
      return this.isOp2(32);
   }

   public boolean op2CClassSetOp() {
      return this.isOp2(64);
   }

   public boolean op2QMarkLtNamedGroup() {
      return this.isOp2(128);
   }

   public boolean op2EscKNamedBackref() {
      return this.isOp2(256);
   }

   public boolean op2EscGSubexpCall() {
      return this.isOp2(512);
   }

   public boolean op2AtMarkCaptureHistory() {
      return this.isOp2(1024);
   }

   public boolean op2EscCapitalCBarControl() {
      return this.isOp2(2048);
   }

   public boolean op2EscCapitalMBarMeta() {
      return this.isOp2(4096);
   }

   public boolean op2EscVVtab() {
      return this.isOp2(8192);
   }

   public boolean op2EscUHex4() {
      return this.isOp2(16384);
   }

   public boolean op2EscGnuBufAnchor() {
      return this.isOp2(32768);
   }

   public boolean op2EscPBraceCharProperty() {
      return this.isOp2(65536);
   }

   public boolean op2EscPBraceCircumflexNot() {
      return this.isOp2(131072);
   }

   public boolean op2EscHXDigit() {
      return this.isOp2(524288);
   }

   public boolean op2IneffectiveEscape() {
      return this.isOp2(1048576);
   }

   protected boolean isBehavior(int bvm) {
      return (this.behavior & bvm) != 0;
   }

   public boolean contextIndepRepeatOps() {
      return this.isBehavior(1);
   }

   public boolean contextInvalidRepeatOps() {
      return this.isBehavior(2);
   }

   public boolean allowUnmatchedCloseSubexp() {
      return this.isBehavior(4);
   }

   public boolean allowInvalidInterval() {
      return this.isBehavior(8);
   }

   public boolean allowIntervalLowAbbrev() {
      return this.isBehavior(16);
   }

   public boolean strictCheckBackref() {
      return this.isBehavior(32);
   }

   public boolean differentLengthAltLookBehind() {
      return this.isBehavior(64);
   }

   public boolean captureOnlyNamedGroup() {
      return this.isBehavior(128);
   }

   public boolean allowMultiplexDefinitionName() {
      return this.isBehavior(256);
   }

   public boolean fixedIntervalIsGreedyOnly() {
      return this.isBehavior(512);
   }

   public boolean notNewlineInNegativeCC() {
      return this.isBehavior(1048576);
   }

   public boolean backSlashEscapeInCC() {
      return this.isBehavior(2097152);
   }

   public boolean allowEmptyRangeInCC() {
      return this.isBehavior(4194304);
   }

   public boolean allowDoubleRangeOpInCC() {
      return this.isBehavior(8388608);
   }

   public boolean warnCCOpNotEscaped() {
      return this.isBehavior(16777216);
   }

   public boolean warnReduntantNestedRepeat() {
      return this.isBehavior(33554432);
   }

   static {
      DEFAULT = RUBY;
      ASIS = new Syntax(0, 1048576, 0, 0, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      PosixBasic = new Syntax(92480006, 0, 0, 12, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      PosixExtended = new Syntax(92476758, 0, -2139095033, 12, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      Emacs = new Syntax(75704918, 32768, 4194304, 0, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      Grep = new Syntax(27208358, 0, 5242880, 0, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      GnuRegex = new Syntax(33543510, 0, -2136997877, 0, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      Java = new Syntax(1073206614, 90231, -2136997813, 8, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      Perl = new Syntax(2146948438, 196615, -2136997877, 8, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      PerlNG = new Syntax(2146948438, 197511, -2136997493, 8, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
      JAVASCRIPT = new Syntax(804771158, 24578, -2136997813, 8, new Syntax.MetaCharTable(92, 0, 0, 0, 0, 0));
   }

   public static class MetaCharTable {
      public final int esc;
      public final int anyChar;
      public final int anyTime;
      public final int zeroOrOneTime;
      public final int oneOrMoreTime;
      public final int anyCharAnyTime;

      public MetaCharTable(int esc, int anyChar, int anyTime, int zeroOrOneTime, int oneOrMoreTime, int anyCharAnyTime) {
         this.esc = esc;
         this.anyChar = anyChar;
         this.anyTime = anyTime;
         this.zeroOrOneTime = zeroOrOneTime;
         this.oneOrMoreTime = oneOrMoreTime;
         this.anyCharAnyTime = anyCharAnyTime;
      }
   }
}
