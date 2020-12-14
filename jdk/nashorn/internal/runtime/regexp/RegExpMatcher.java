package jdk.nashorn.internal.runtime.regexp;

import java.util.regex.MatchResult;

public interface RegExpMatcher extends MatchResult {
   boolean search(int var1);

   String getInput();
}
