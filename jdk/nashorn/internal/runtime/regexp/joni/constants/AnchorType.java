package jdk.nashorn.internal.runtime.regexp.joni.constants;

public interface AnchorType {
   int BEGIN_BUF = 1;
   int BEGIN_LINE = 2;
   int BEGIN_POSITION = 4;
   int END_BUF = 8;
   int SEMI_END_BUF = 16;
   int END_LINE = 32;
   int WORD_BOUND = 64;
   int NOT_WORD_BOUND = 128;
   int WORD_BEGIN = 256;
   int WORD_END = 512;
   int PREC_READ = 1024;
   int PREC_READ_NOT = 2048;
   int LOOK_BEHIND = 4096;
   int LOOK_BEHIND_NOT = 8192;
   int ANYCHAR_STAR = 16384;
   int ANYCHAR_STAR_ML = 32768;
   int ANYCHAR_STAR_MASK = 49152;
   int END_BUF_MASK = 24;
   int ALLOWED_IN_LB = 4135;
   int ALLOWED_IN_LB_NOT = 12327;
}
