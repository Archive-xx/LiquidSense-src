package jdk.nashorn.internal.runtime.regexp.joni.constants;

public interface RegexState {
   int NORMAL = 0;
   int SEARCHING = 1;
   int COMPILING = -1;
   int MODIFY = -2;
}
