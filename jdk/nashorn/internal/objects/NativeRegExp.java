package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import jdk.nashorn.internal.runtime.regexp.RegExpFactory;
import jdk.nashorn.internal.runtime.regexp.RegExpMatcher;
import jdk.nashorn.internal.runtime.regexp.RegExpResult;

public final class NativeRegExp extends ScriptObject {
   public Object lastIndex;
   private RegExp regexp;
   private final Global globalObject;
   private static PropertyMap $nasgenmap$;
   private static final Object REPLACE_VALUE = new Object();

   private NativeRegExp(Global global) {
      super(global.getRegExpPrototype(), $nasgenmap$);
      this.globalObject = global;
   }

   NativeRegExp(String input, String flagString, Global global, ScriptObject proto) {
      super(proto, $nasgenmap$);

      try {
         this.regexp = RegExpFactory.create(input, flagString);
      } catch (ParserException var6) {
         var6.throwAsEcmaException();
         throw new AssertionError();
      }

      this.globalObject = global;
      this.setLastIndex(0);
   }

   NativeRegExp(String input, String flagString, Global global) {
      this(input, flagString, global, global.getRegExpPrototype());
   }

   NativeRegExp(String input, String flagString) {
      this(input, flagString, Global.instance());
   }

   NativeRegExp(String string, Global global) {
      this(string, "", global);
   }

   NativeRegExp(String string) {
      this(string, Global.instance());
   }

   NativeRegExp(NativeRegExp regExp) {
      this(Global.instance());
      this.lastIndex = regExp.getLastIndexObject();
      this.regexp = regExp.getRegExp();
   }

   public String getClassName() {
      return "RegExp";
   }

   public static NativeRegExp constructor(boolean isNew, Object self, Object... args) {
      if (args.length > 1) {
         return newRegExp(args[0], args[1]);
      } else {
         return args.length > 0 ? newRegExp(args[0], ScriptRuntime.UNDEFINED) : newRegExp(ScriptRuntime.UNDEFINED, ScriptRuntime.UNDEFINED);
      }
   }

   public static NativeRegExp constructor(boolean isNew, Object self) {
      return new NativeRegExp("", "");
   }

   public static NativeRegExp constructor(boolean isNew, Object self, Object pattern) {
      return newRegExp(pattern, ScriptRuntime.UNDEFINED);
   }

   public static NativeRegExp constructor(boolean isNew, Object self, Object pattern, Object flags) {
      return newRegExp(pattern, flags);
   }

   public static NativeRegExp newRegExp(Object regexp, Object flags) {
      String patternString = "";
      String flagString = "";
      if (regexp != ScriptRuntime.UNDEFINED) {
         if (regexp instanceof NativeRegExp) {
            if (flags != ScriptRuntime.UNDEFINED) {
               throw ECMAErrors.typeError("regex.cant.supply.flags");
            }

            return (NativeRegExp)regexp;
         }

         patternString = JSType.toString(regexp);
      }

      if (flags != ScriptRuntime.UNDEFINED) {
         flagString = JSType.toString(flags);
      }

      return new NativeRegExp(patternString, flagString);
   }

   static NativeRegExp flatRegExp(String string) {
      StringBuilder sb = null;
      int length = string.length();

      for(int i = 0; i < length; ++i) {
         char c = string.charAt(i);
         switch(c) {
         case '$':
         case '(':
         case ')':
         case '*':
         case '+':
         case '.':
         case '?':
         case '[':
         case '\\':
         case '^':
         case '{':
         case '|':
            if (sb == null) {
               sb = new StringBuilder(length * 2);
               sb.append(string, 0, i);
            }

            sb.append('\\');
            sb.append(c);
            break;
         default:
            if (sb != null) {
               sb.append(c);
            }
         }
      }

      return new NativeRegExp(sb == null ? string : sb.toString(), "");
   }

   private String getFlagString() {
      StringBuilder sb = new StringBuilder(3);
      if (this.regexp.isGlobal()) {
         sb.append('g');
      }

      if (this.regexp.isIgnoreCase()) {
         sb.append('i');
      }

      if (this.regexp.isMultiline()) {
         sb.append('m');
      }

      return sb.toString();
   }

   public String safeToString() {
      return "[RegExp " + this.toString() + "]";
   }

   public String toString() {
      return "/" + this.regexp.getSource() + "/" + this.getFlagString();
   }

   public static ScriptObject compile(Object self, Object pattern, Object flags) {
      NativeRegExp regExp = checkRegExp(self);
      NativeRegExp compiled = newRegExp(pattern, flags);
      regExp.setRegExp(compiled.getRegExp());
      return regExp;
   }

   public static ScriptObject exec(Object self, Object string) {
      return checkRegExp(self).exec(JSType.toString(string));
   }

   public static boolean test(Object self, Object string) {
      return checkRegExp(self).test(JSType.toString(string));
   }

   public static String toString(Object self) {
      return checkRegExp(self).toString();
   }

   public static Object source(Object self) {
      return checkRegExp(self).getRegExp().getSource();
   }

   public static Object global(Object self) {
      return checkRegExp(self).getRegExp().isGlobal();
   }

   public static Object ignoreCase(Object self) {
      return checkRegExp(self).getRegExp().isIgnoreCase();
   }

   public static Object multiline(Object self) {
      return checkRegExp(self).getRegExp().isMultiline();
   }

   public static Object getLastInput(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getInput();
   }

   public static Object getLastMultiline(Object self) {
      return false;
   }

   public static Object getLastMatch(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(0);
   }

   public static Object getLastParen(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getLastParen();
   }

   public static Object getLeftContext(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getInput().substring(0, match.getIndex());
   }

   public static Object getRightContext(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getInput().substring(match.getIndex() + match.length());
   }

   public static Object getGroup1(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(1);
   }

   public static Object getGroup2(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(2);
   }

   public static Object getGroup3(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(3);
   }

   public static Object getGroup4(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(4);
   }

   public static Object getGroup5(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(5);
   }

   public static Object getGroup6(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(6);
   }

   public static Object getGroup7(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(7);
   }

   public static Object getGroup8(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(8);
   }

   public static Object getGroup9(Object self) {
      RegExpResult match = Global.instance().getLastRegExpResult();
      return match == null ? "" : match.getGroup(9);
   }

   private RegExpResult execInner(String string) {
      boolean isGlobal = this.regexp.isGlobal();
      int start = this.getLastIndex();
      if (!isGlobal) {
         start = 0;
      }

      if (start >= 0 && start <= string.length()) {
         RegExpMatcher matcher = this.regexp.match(string);
         if (matcher != null && matcher.search(start)) {
            if (isGlobal) {
               this.setLastIndex(matcher.end());
            }

            RegExpResult match = new RegExpResult(string, matcher.start(), this.groups(matcher));
            this.globalObject.setLastRegExpResult(match);
            return match;
         } else {
            if (isGlobal) {
               this.setLastIndex(0);
            }

            return null;
         }
      } else {
         if (isGlobal) {
            this.setLastIndex(0);
         }

         return null;
      }
   }

   private RegExpResult execSplit(String string, int start) {
      if (start >= 0 && start <= string.length()) {
         RegExpMatcher matcher = this.regexp.match(string);
         if (matcher != null && matcher.search(start)) {
            RegExpResult match = new RegExpResult(string, matcher.start(), this.groups(matcher));
            this.globalObject.setLastRegExpResult(match);
            return match;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private Object[] groups(RegExpMatcher matcher) {
      int groupCount = matcher.groupCount();
      Object[] groups = new Object[groupCount + 1];
      BitVector groupsInNegativeLookahead = this.regexp.getGroupsInNegativeLookahead();
      int i = 0;

      for(int lastGroupStart = matcher.start(); i <= groupCount; ++i) {
         int groupStart = matcher.start(i);
         if (lastGroupStart > groupStart || groupsInNegativeLookahead != null && groupsInNegativeLookahead.isSet((long)i)) {
            groups[i] = ScriptRuntime.UNDEFINED;
         } else {
            String group = matcher.group(i);
            groups[i] = group == null ? ScriptRuntime.UNDEFINED : group;
            lastGroupStart = groupStart;
         }
      }

      return groups;
   }

   public NativeRegExpExecResult exec(String string) {
      RegExpResult match = this.execInner(string);
      return match == null ? null : new NativeRegExpExecResult(match, this.globalObject);
   }

   public boolean test(String string) {
      return this.execInner(string) != null;
   }

   String replace(String string, String replacement, Object function) throws Throwable {
      RegExpMatcher matcher = this.regexp.match(string);
      if (matcher == null) {
         return string;
      } else if (!this.regexp.isGlobal()) {
         if (!matcher.search(0)) {
            return string;
         } else {
            StringBuilder sb = new StringBuilder();
            sb.append(string, 0, matcher.start());
            if (function != null) {
               Object self = Bootstrap.isStrictCallable(function) ? ScriptRuntime.UNDEFINED : Global.instance();
               sb.append(this.callReplaceValue(getReplaceValueInvoker(), function, self, matcher, string));
            } else {
               this.appendReplacement(matcher, string, replacement, sb);
            }

            sb.append(string, matcher.end(), string.length());
            return sb.toString();
         }
      } else {
         this.setLastIndex(0);
         if (!matcher.search(0)) {
            return string;
         } else {
            int thisIndex = 0;
            int previousLastIndex = 0;
            StringBuilder sb = new StringBuilder();
            MethodHandle invoker = function == null ? null : getReplaceValueInvoker();
            Object self = function != null && !Bootstrap.isStrictCallable(function) ? Global.instance() : ScriptRuntime.UNDEFINED;

            do {
               sb.append(string, thisIndex, matcher.start());
               if (function != null) {
                  sb.append(this.callReplaceValue(invoker, function, self, matcher, string));
               } else {
                  this.appendReplacement(matcher, string, replacement, sb);
               }

               thisIndex = matcher.end();
               if (thisIndex == string.length() && matcher.start() == matcher.end()) {
                  break;
               }

               if (thisIndex == previousLastIndex) {
                  this.setLastIndex(thisIndex + 1);
                  previousLastIndex = thisIndex + 1;
               } else {
                  previousLastIndex = thisIndex;
               }
            } while(previousLastIndex <= string.length() && matcher.search(previousLastIndex));

            sb.append(string, thisIndex, string.length());
            return sb.toString();
         }
      }
   }

   private void appendReplacement(RegExpMatcher matcher, String text, String replacement, StringBuilder sb) {
      int cursor = 0;
      Object[] groups = null;

      while(cursor < replacement.length()) {
         char nextChar = replacement.charAt(cursor);
         if (nextChar == '$') {
            ++cursor;
            if (cursor == replacement.length()) {
               sb.append('$');
               break;
            }

            nextChar = replacement.charAt(cursor);
            int firstDigit = nextChar - 48;
            if (firstDigit >= 0 && firstDigit <= 9 && firstDigit <= matcher.groupCount()) {
               int refNum = firstDigit;
               ++cursor;
               if (cursor < replacement.length() && firstDigit < matcher.groupCount()) {
                  int secondDigit = replacement.charAt(cursor) - 48;
                  if (secondDigit >= 0 && secondDigit <= 9) {
                     int newRefNum = firstDigit * 10 + secondDigit;
                     if (newRefNum <= matcher.groupCount() && newRefNum > 0) {
                        refNum = newRefNum;
                        ++cursor;
                     }
                  }
               }

               if (refNum > 0) {
                  if (groups == null) {
                     groups = this.groups(matcher);
                  }

                  if (groups[refNum] != ScriptRuntime.UNDEFINED) {
                     sb.append((String)groups[refNum]);
                  }
               } else {
                  assert refNum == 0;

                  sb.append("$0");
               }
            } else if (nextChar == '$') {
               sb.append('$');
               ++cursor;
            } else if (nextChar == '&') {
               sb.append(matcher.group());
               ++cursor;
            } else if (nextChar == '`') {
               sb.append(text, 0, matcher.start());
               ++cursor;
            } else if (nextChar == '\'') {
               sb.append(text, matcher.end(), text.length());
               ++cursor;
            } else {
               sb.append('$');
            }
         } else {
            sb.append(nextChar);
            ++cursor;
         }
      }

   }

   private static final MethodHandle getReplaceValueInvoker() {
      return Global.instance().getDynamicInvoker(REPLACE_VALUE, new Callable<MethodHandle>() {
         public MethodHandle call() {
            return Bootstrap.createDynamicInvoker("dyn:call", String.class, Object.class, Object.class, Object[].class);
         }
      });
   }

   private String callReplaceValue(MethodHandle invoker, Object function, Object self, RegExpMatcher matcher, String string) throws Throwable {
      Object[] groups = this.groups(matcher);
      Object[] args = Arrays.copyOf(groups, groups.length + 2);
      args[groups.length] = matcher.start();
      args[groups.length + 1] = string;
      return invoker.invokeExact(function, self, args);
   }

   NativeArray split(String string, long limit) {
      if (limit == 0L) {
         return new NativeArray();
      } else {
         List<Object> matches = new ArrayList();
         int inputLength = string.length();
         int splitLastLength = -1;
         int splitLastIndex = 0;
         int splitLastLastIndex = 0;

         RegExpResult match;
         while((match = this.execSplit(string, splitLastIndex)) != null) {
            splitLastIndex = match.getIndex() + match.length();
            if (splitLastIndex > splitLastLastIndex) {
               matches.add(string.substring(splitLastLastIndex, match.getIndex()));
               Object[] groups = match.getGroups();
               if (groups.length > 1 && match.getIndex() < inputLength) {
                  for(int index = 1; index < groups.length && (long)matches.size() < limit; ++index) {
                     matches.add(groups[index]);
                  }
               }

               splitLastLength = match.length();
               if ((long)matches.size() >= limit) {
                  break;
               }
            }

            if (splitLastIndex == splitLastLastIndex) {
               ++splitLastIndex;
            } else {
               splitLastLastIndex = splitLastIndex;
            }
         }

         if ((long)matches.size() < limit) {
            if (splitLastLastIndex == string.length()) {
               if (splitLastLength > 0 || this.execSplit("", 0) == null) {
                  matches.add("");
               }
            } else {
               matches.add(string.substring(splitLastLastIndex, inputLength));
            }
         }

         return new NativeArray(matches.toArray());
      }
   }

   int search(String string) {
      RegExpResult match = this.execInner(string);
      return match == null ? -1 : match.getIndex();
   }

   public int getLastIndex() {
      return JSType.toInteger(this.lastIndex);
   }

   public Object getLastIndexObject() {
      return this.lastIndex;
   }

   public void setLastIndex(int lastIndex) {
      this.lastIndex = JSType.toObject(lastIndex);
   }

   private static NativeRegExp checkRegExp(Object self) {
      if (self instanceof NativeRegExp) {
         return (NativeRegExp)self;
      } else if (self != null && self == Global.instance().getRegExpPrototype()) {
         return Global.instance().getDefaultRegExp();
      } else {
         throw ECMAErrors.typeError("not.a.regexp", ScriptRuntime.safeToString(self));
      }
   }

   boolean getGlobal() {
      return this.regexp.isGlobal();
   }

   private RegExp getRegExp() {
      return this.regexp;
   }

   private void setRegExp(RegExp regexp) {
      this.regexp = regexp;
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(5);
      var10000.add(AccessorProperty.create("lastIndex", 6, "G$lastIndex", "S$lastIndex"));
      var10000.add(AccessorProperty.create("source", 7, "source", (MethodHandle)null));
      var10000.add(AccessorProperty.create("global", 7, "global", (MethodHandle)null));
      var10000.add(AccessorProperty.create("ignoreCase", 7, "ignoreCase", (MethodHandle)null));
      var10000.add(AccessorProperty.create("multiline", 7, "multiline", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   public Object G$lastIndex() {
      return this.lastIndex;
   }

   public void S$lastIndex(Object var1) {
      this.lastIndex = var1;
   }
}
