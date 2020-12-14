package jdk.nashorn.internal.runtime.regexp.joni.encoding;

public interface CharacterType {
   int NEWLINE = 0;
   int ALPHA = 1;
   int BLANK = 2;
   int CNTRL = 3;
   int DIGIT = 4;
   int GRAPH = 5;
   int LOWER = 6;
   int PRINT = 7;
   int PUNCT = 8;
   int SPACE = 9;
   int UPPER = 10;
   int XDIGIT = 11;
   int WORD = 12;
   int ALNUM = 13;
   int ASCII = 14;
   int SPECIAL_MASK = 256;
   int S = 265;
   int D = 260;
   int W = 268;
   int LETTER_MASK = 62;
   int ALPHA_MASK = 510;
   int ALNUM_MASK = 1022;
   int WORD_MASK = 8389630;
   int PUNCT_MASK = 1643118592;
   int CNTRL_MASK = 884736;
   int SPACE_MASK = 28672;
   int GRAPH_MASK = 585728;
   int PRINT_MASK = 557056;
}
