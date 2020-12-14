package jdk.nashorn.internal.runtime.regexp.joni.constants;

public interface NodeType {
   int STR = 0;
   int CCLASS = 1;
   int CTYPE = 2;
   int CANY = 3;
   int BREF = 4;
   int QTFR = 5;
   int ENCLOSE = 6;
   int ANCHOR = 7;
   int LIST = 8;
   int ALT = 9;
   int CALL = 10;
   int BIT_STR = 1;
   int BIT_CCLASS = 2;
   int BIT_CTYPE = 4;
   int BIT_CANY = 8;
   int BIT_BREF = 16;
   int BIT_QTFR = 32;
   int BIT_ENCLOSE = 64;
   int BIT_ANCHOR = 128;
   int BIT_LIST = 256;
   int BIT_ALT = 512;
   int BIT_CALL = 1024;
   int ALLOWED_IN_LB = 2031;
   int SIMPLE = 31;
}
