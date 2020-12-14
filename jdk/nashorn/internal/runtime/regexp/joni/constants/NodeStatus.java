package jdk.nashorn.internal.runtime.regexp.joni.constants;

public interface NodeStatus {
   int NST_MIN_FIXED = 1;
   int NST_MAX_FIXED = 2;
   int NST_CLEN_FIXED = 4;
   int NST_MARK1 = 8;
   int NST_MARK2 = 16;
   int NST_MEM_BACKREFED = 32;
   int NST_STOP_BT_SIMPLE_REPEAT = 64;
   int NST_RECURSION = 128;
   int NST_CALLED = 256;
   int NST_ADDR_FIXED = 512;
   int NST_NAMED_GROUP = 1024;
   int NST_NAME_REF = 2048;
   int NST_IN_REPEAT = 4096;
   int NST_NEST_LEVEL = 8192;
   int NST_BY_NUMBER = 16384;
}
