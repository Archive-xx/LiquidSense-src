package jdk.nashorn.internal.runtime.regexp.joni.constants;

public interface MetaChar {
   int ESCAPE = 0;
   int ANYCHAR = 1;
   int ANYTIME = 2;
   int ZERO_OR_ONE_TIME = 3;
   int ONE_OR_MORE_TIME = 4;
   int ANYCHAR_ANYTIME = 5;
   int INEFFECTIVE_META_CHAR = 0;
}
