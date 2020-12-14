package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticBuiltins;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.NashornGuards;
import jdk.nashorn.internal.runtime.linker.PrimitiveLookup;

public final class NativeString extends ScriptObject implements OptimisticBuiltins {
   private final CharSequence value;
   static final MethodHandle WRAPFILTER;
   private static final MethodHandle PROTOFILTER;
   private static PropertyMap $nasgenmap$;

   private NativeString(CharSequence value) {
      this(value, Global.instance());
   }

   NativeString(CharSequence value, Global global) {
      this(value, global.getStringPrototype(), $nasgenmap$);
   }

   private NativeString(CharSequence value, ScriptObject proto, PropertyMap map) {
      super(proto, map);

      assert JSType.isString(value);

      this.value = value;
   }

   public String safeToString() {
      return "[String " + this.toString() + "]";
   }

   public String toString() {
      return this.getStringValue();
   }

   public boolean equals(Object other) {
      return other instanceof NativeString ? this.getStringValue().equals(((NativeString)other).getStringValue()) : false;
   }

   public int hashCode() {
      return this.getStringValue().hashCode();
   }

   private String getStringValue() {
      return this.value instanceof String ? (String)this.value : this.value.toString();
   }

   private CharSequence getValue() {
      return this.value;
   }

   public String getClassName() {
      return "String";
   }

   public Object getLength() {
      return this.value.length();
   }

   protected GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
      String name = desc.getNameToken(2);
      return "length".equals(name) && "getMethod".equals(operator) ? null : super.findGetMethod(desc, request, operator);
   }

   protected GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
      Object self = request.getReceiver();
      Class<?> returnType = desc.getMethodType().returnType();
      if (returnType == Object.class && JSType.isString(self)) {
         try {
            return new GuardedInvocation(Lookup.MH.findStatic(MethodHandles.lookup(), NativeString.class, "get", desc.getMethodType()), NashornGuards.getStringGuard());
         } catch (MethodHandleFactory.LookupException var6) {
         }
      }

      return super.findGetIndexMethod(desc, request);
   }

   private static Object get(Object self, Object key) {
      CharSequence cs = JSType.toCharSequence(self);
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      return index >= 0 && index < cs.length() ? String.valueOf(cs.charAt(index)) : ((ScriptObject)Global.toObject(self)).get(primitiveKey);
   }

   private static Object get(Object self, double key) {
      return JSType.isRepresentableAsInt(key) ? get(self, (int)key) : ((ScriptObject)Global.toObject(self)).get(key);
   }

   private static Object get(Object self, long key) {
      CharSequence cs = JSType.toCharSequence(self);
      return key >= 0L && key < (long)cs.length() ? String.valueOf(cs.charAt((int)key)) : ((ScriptObject)Global.toObject(self)).get((double)key);
   }

   private static Object get(Object self, int key) {
      CharSequence cs = JSType.toCharSequence(self);
      return key >= 0 && key < cs.length() ? String.valueOf(cs.charAt(key)) : ((ScriptObject)Global.toObject(self)).get(key);
   }

   public Object get(Object key) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      return index >= 0 && index < this.value.length() ? String.valueOf(this.value.charAt(index)) : super.get(primitiveKey);
   }

   public Object get(double key) {
      return JSType.isRepresentableAsInt(key) ? this.get((int)key) : super.get(key);
   }

   public Object get(int key) {
      return key >= 0 && key < this.value.length() ? String.valueOf(this.value.charAt(key)) : super.get(key);
   }

   public int getInt(Object key, int programPoint) {
      return JSType.toInt32MaybeOptimistic(this.get(key), programPoint);
   }

   public int getInt(double key, int programPoint) {
      return JSType.toInt32MaybeOptimistic(this.get(key), programPoint);
   }

   public int getInt(int key, int programPoint) {
      return JSType.toInt32MaybeOptimistic(this.get(key), programPoint);
   }

   public double getDouble(Object key, int programPoint) {
      return JSType.toNumberMaybeOptimistic(this.get(key), programPoint);
   }

   public double getDouble(double key, int programPoint) {
      return JSType.toNumberMaybeOptimistic(this.get(key), programPoint);
   }

   public double getDouble(int key, int programPoint) {
      return JSType.toNumberMaybeOptimistic(this.get(key), programPoint);
   }

   public boolean has(Object key) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      return this.isValidStringIndex(index) || super.has(primitiveKey);
   }

   public boolean has(int key) {
      return this.isValidStringIndex(key) || super.has(key);
   }

   public boolean has(double key) {
      int index = ArrayIndex.getArrayIndex(key);
      return this.isValidStringIndex(index) || super.has(key);
   }

   public boolean hasOwnProperty(Object key) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      return this.isValidStringIndex(index) || super.hasOwnProperty(primitiveKey);
   }

   public boolean hasOwnProperty(int key) {
      return this.isValidStringIndex(key) || super.hasOwnProperty(key);
   }

   public boolean hasOwnProperty(double key) {
      int index = ArrayIndex.getArrayIndex(key);
      return this.isValidStringIndex(index) || super.hasOwnProperty(key);
   }

   public boolean delete(int key, boolean strict) {
      return this.checkDeleteIndex(key, strict) ? false : super.delete(key, strict);
   }

   public boolean delete(double key, boolean strict) {
      int index = ArrayIndex.getArrayIndex(key);
      return this.checkDeleteIndex(index, strict) ? false : super.delete(key, strict);
   }

   public boolean delete(Object key, boolean strict) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      return this.checkDeleteIndex(index, strict) ? false : super.delete(primitiveKey, strict);
   }

   private boolean checkDeleteIndex(int index, boolean strict) {
      if (this.isValidStringIndex(index)) {
         if (strict) {
            throw ECMAErrors.typeError("cant.delete.property", Integer.toString(index), ScriptRuntime.safeToString(this));
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public Object getOwnPropertyDescriptor(String key) {
      int index = ArrayIndex.getArrayIndex(key);
      if (index >= 0 && index < this.value.length()) {
         Global global = Global.instance();
         return global.newDataDescriptor(String.valueOf(this.value.charAt(index)), false, true, false);
      } else {
         return super.getOwnPropertyDescriptor(key);
      }
   }

   protected String[] getOwnKeys(boolean all, Set<String> nonEnumerable) {
      List<Object> keys = new ArrayList();

      for(int i = 0; i < this.value.length(); ++i) {
         keys.add(JSType.toString(i));
      }

      keys.addAll(Arrays.asList(super.getOwnKeys(all, nonEnumerable)));
      return (String[])keys.toArray(new String[keys.size()]);
   }

   public static Object length(Object self) {
      return getCharSequence(self).length();
   }

   public static String fromCharCode(Object self, Object... args) {
      char[] buf = new char[args.length];
      int index = 0;
      Object[] var4 = args;
      int var5 = args.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Object arg = var4[var6];
         buf[index++] = (char)JSType.toUint16(arg);
      }

      return new String(buf);
   }

   public static Object fromCharCode(Object self, Object value) {
      return value instanceof Integer ? fromCharCode(self, (Integer)value) : Character.toString((char)JSType.toUint16(value));
   }

   public static String fromCharCode(Object self, int value) {
      return Character.toString((char)(value & '\uffff'));
   }

   public static Object fromCharCode(Object self, int ch1, int ch2) {
      return Character.toString((char)(ch1 & '\uffff')) + Character.toString((char)(ch2 & '\uffff'));
   }

   public static Object fromCharCode(Object self, int ch1, int ch2, int ch3) {
      return Character.toString((char)(ch1 & '\uffff')) + Character.toString((char)(ch2 & '\uffff')) + Character.toString((char)(ch3 & '\uffff'));
   }

   public static String fromCharCode(Object self, int ch1, int ch2, int ch3, int ch4) {
      return Character.toString((char)(ch1 & '\uffff')) + Character.toString((char)(ch2 & '\uffff')) + Character.toString((char)(ch3 & '\uffff')) + Character.toString((char)(ch4 & '\uffff'));
   }

   public static String fromCharCode(Object self, double value) {
      return Character.toString((char)JSType.toUint16(value));
   }

   public static String toString(Object self) {
      return getString(self);
   }

   public static String valueOf(Object self) {
      return getString(self);
   }

   public static String charAt(Object self, Object pos) {
      return charAtImpl(checkObjectToString(self), JSType.toInteger(pos));
   }

   public static String charAt(Object self, double pos) {
      return charAt(self, (int)pos);
   }

   public static String charAt(Object self, int pos) {
      return charAtImpl(checkObjectToString(self), pos);
   }

   private static String charAtImpl(String str, int pos) {
      return pos >= 0 && pos < str.length() ? String.valueOf(str.charAt(pos)) : "";
   }

   private static int getValidChar(Object self, int pos) {
      try {
         return ((CharSequence)self).charAt(pos);
      } catch (IndexOutOfBoundsException var3) {
         throw new ClassCastException();
      }
   }

   public static double charCodeAt(Object self, Object pos) {
      String str = checkObjectToString(self);
      int idx = JSType.toInteger(pos);
      return idx >= 0 && idx < str.length() ? (double)str.charAt(idx) : Double.NaN;
   }

   public static int charCodeAt(Object self, double pos) {
      return charCodeAt(self, (int)pos);
   }

   public static int charCodeAt(Object self, long pos) {
      return charCodeAt(self, (int)pos);
   }

   public static int charCodeAt(Object self, int pos) {
      return getValidChar(self, pos);
   }

   public static Object concat(Object self, Object... args) {
      CharSequence cs = checkObjectToString(self);
      if (args != null) {
         Object[] var3 = args;
         int var4 = args.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object obj = var3[var5];
            cs = new ConsString((CharSequence)cs, JSType.toCharSequence(obj));
         }
      }

      return cs;
   }

   public static int indexOf(Object self, Object search, Object pos) {
      String str = checkObjectToString(self);
      return str.indexOf(JSType.toString(search), JSType.toInteger(pos));
   }

   public static int indexOf(Object self, Object search) {
      return indexOf(self, search, 0);
   }

   public static int indexOf(Object self, Object search, double pos) {
      return indexOf(self, search, (int)pos);
   }

   public static int indexOf(Object self, Object search, int pos) {
      return checkObjectToString(self).indexOf(JSType.toString(search), pos);
   }

   public static int lastIndexOf(Object self, Object search, Object pos) {
      String str = checkObjectToString(self);
      String searchStr = JSType.toString(search);
      int length = str.length();
      int end;
      if (pos == ScriptRuntime.UNDEFINED) {
         end = length;
      } else {
         double numPos = JSType.toNumber(pos);
         end = Double.isNaN(numPos) ? length : (int)numPos;
         if (end < 0) {
            end = 0;
         } else if (end > length) {
            end = length;
         }
      }

      return str.lastIndexOf(searchStr, end);
   }

   public static double localeCompare(Object self, Object that) {
      String str = checkObjectToString(self);
      Collator collator = Collator.getInstance(Global.getEnv()._locale);
      collator.setStrength(3);
      collator.setDecomposition(1);
      return (double)collator.compare(str, JSType.toString(that));
   }

   public static ScriptObject match(Object self, Object regexp) {
      String str = checkObjectToString(self);
      NativeRegExp nativeRegExp;
      if (regexp == ScriptRuntime.UNDEFINED) {
         nativeRegExp = new NativeRegExp("");
      } else {
         nativeRegExp = Global.toRegExp(regexp);
      }

      if (!nativeRegExp.getGlobal()) {
         return nativeRegExp.exec(str);
      } else {
         nativeRegExp.setLastIndex(0);
         int previousLastIndex = 0;

         ArrayList matches;
         NativeRegExpExecResult result;
         for(matches = new ArrayList(); (result = nativeRegExp.exec(str)) != null; matches.add(((ScriptObject)result).get(0))) {
            int thisIndex = nativeRegExp.getLastIndex();
            if (thisIndex == previousLastIndex) {
               nativeRegExp.setLastIndex(thisIndex + 1);
               previousLastIndex = thisIndex + 1;
            } else {
               previousLastIndex = thisIndex;
            }
         }

         if (matches.isEmpty()) {
            return null;
         } else {
            return new NativeArray(matches.toArray());
         }
      }
   }

   public static String replace(Object self, Object string, Object replacement) throws Throwable {
      String str = checkObjectToString(self);
      NativeRegExp nativeRegExp;
      if (string instanceof NativeRegExp) {
         nativeRegExp = (NativeRegExp)string;
      } else {
         nativeRegExp = NativeRegExp.flatRegExp(JSType.toString(string));
      }

      return Bootstrap.isCallable(replacement) ? nativeRegExp.replace(str, "", replacement) : nativeRegExp.replace(str, JSType.toString(replacement), (Object)null);
   }

   public static int search(Object self, Object string) {
      String str = checkObjectToString(self);
      NativeRegExp nativeRegExp = Global.toRegExp(string == ScriptRuntime.UNDEFINED ? "" : string);
      return nativeRegExp.search(str);
   }

   public static String slice(Object self, Object start, Object end) {
      String str = checkObjectToString(self);
      return end == ScriptRuntime.UNDEFINED ? slice(str, JSType.toInteger(start)) : slice(str, JSType.toInteger(start), JSType.toInteger(end));
   }

   public static String slice(Object self, int start) {
      String str = checkObjectToString(self);
      int from = start < 0 ? Math.max(str.length() + start, 0) : Math.min(start, str.length());
      return str.substring(from);
   }

   public static String slice(Object self, double start) {
      return slice(self, (int)start);
   }

   public static String slice(Object self, int start, int end) {
      String str = checkObjectToString(self);
      int len = str.length();
      int from = start < 0 ? Math.max(len + start, 0) : Math.min(start, len);
      int to = end < 0 ? Math.max(len + end, 0) : Math.min(end, len);
      return str.substring(Math.min(from, to), to);
   }

   public static String slice(Object self, double start, double end) {
      return slice(self, (int)start, (int)end);
   }

   public static ScriptObject split(Object self, Object separator, Object limit) {
      String str = checkObjectToString(self);
      long lim = limit == ScriptRuntime.UNDEFINED ? 4294967295L : JSType.toUint32(limit);
      if (separator == ScriptRuntime.UNDEFINED) {
         return lim == 0L ? new NativeArray() : new NativeArray(new Object[]{str});
      } else {
         return (ScriptObject)(separator instanceof NativeRegExp ? ((NativeRegExp)separator).split(str, lim) : splitString(str, JSType.toString(separator), lim));
      }
   }

   private static ScriptObject splitString(String str, String separator, long limit) {
      int sepLength;
      if (separator.isEmpty()) {
         int length = (int)Math.min((long)str.length(), limit);
         Object[] array = new Object[length];

         for(sepLength = 0; sepLength < length; ++sepLength) {
            array[sepLength] = String.valueOf(str.charAt(sepLength));
         }

         return new NativeArray(array);
      } else {
         List<String> elements = new LinkedList();
         int strLength = str.length();
         sepLength = separator.length();
         int pos = 0;

         int n;
         int found;
         for(n = 0; pos < strLength && (long)n < limit; pos = found + sepLength) {
            found = str.indexOf(separator, pos);
            if (found == -1) {
               break;
            }

            elements.add(str.substring(pos, found));
            ++n;
         }

         if (pos <= strLength && (long)n < limit) {
            elements.add(str.substring(pos));
         }

         return new NativeArray(elements.toArray());
      }
   }

   public static String substr(Object self, Object start, Object length) {
      String str = JSType.toString(self);
      int strLength = str.length();
      int intStart = JSType.toInteger(start);
      if (intStart < 0) {
         intStart = Math.max(intStart + strLength, 0);
      }

      int intLen = Math.min(Math.max(length == ScriptRuntime.UNDEFINED ? Integer.MAX_VALUE : JSType.toInteger(length), 0), strLength - intStart);
      return intLen <= 0 ? "" : str.substring(intStart, intStart + intLen);
   }

   public static String substring(Object self, Object start, Object end) {
      String str = checkObjectToString(self);
      return end == ScriptRuntime.UNDEFINED ? substring(str, JSType.toInteger(start)) : substring(str, JSType.toInteger(start), JSType.toInteger(end));
   }

   public static String substring(Object self, int start) {
      String str = checkObjectToString(self);
      if (start < 0) {
         return str;
      } else {
         return start >= str.length() ? "" : str.substring(start);
      }
   }

   public static String substring(Object self, double start) {
      return substring(self, (int)start);
   }

   public static String substring(Object self, int start, int end) {
      String str = checkObjectToString(self);
      int len = str.length();
      int validStart = start < 0 ? 0 : (start > len ? len : start);
      int validEnd = end < 0 ? 0 : (end > len ? len : end);
      return validStart < validEnd ? str.substring(validStart, validEnd) : str.substring(validEnd, validStart);
   }

   public static String substring(Object self, double start, double end) {
      return substring(self, (int)start, (int)end);
   }

   public static String toLowerCase(Object self) {
      return checkObjectToString(self).toLowerCase(Locale.ROOT);
   }

   public static String toLocaleLowerCase(Object self) {
      return checkObjectToString(self).toLowerCase(Global.getEnv()._locale);
   }

   public static String toUpperCase(Object self) {
      return checkObjectToString(self).toUpperCase(Locale.ROOT);
   }

   public static String toLocaleUpperCase(Object self) {
      return checkObjectToString(self).toUpperCase(Global.getEnv()._locale);
   }

   public static String trim(Object self) {
      String str = checkObjectToString(self);
      int start = 0;

      int end;
      for(end = str.length() - 1; start <= end && ScriptRuntime.isJSWhitespace(str.charAt(start)); ++start) {
      }

      while(end > start && ScriptRuntime.isJSWhitespace(str.charAt(end))) {
         --end;
      }

      return str.substring(start, end + 1);
   }

   public static String trimLeft(Object self) {
      String str = checkObjectToString(self);
      int start = 0;

      int end;
      for(end = str.length() - 1; start <= end && ScriptRuntime.isJSWhitespace(str.charAt(start)); ++start) {
      }

      return str.substring(start, end + 1);
   }

   public static String trimRight(Object self) {
      String str = checkObjectToString(self);
      int start = false;

      int end;
      for(end = str.length() - 1; end >= 0 && ScriptRuntime.isJSWhitespace(str.charAt(end)); --end) {
      }

      return str.substring(0, end + 1);
   }

   private static ScriptObject newObj(CharSequence str) {
      return new NativeString(str);
   }

   public static Object constructor(boolean newObj, Object self, Object... args) {
      CharSequence str = args.length > 0 ? JSType.toCharSequence(args[0]) : "";
      return newObj ? newObj((CharSequence)str) : ((CharSequence)str).toString();
   }

   public static Object constructor(boolean newObj, Object self) {
      return newObj ? newObj("") : "";
   }

   public static Object constructor(boolean newObj, Object self, Object arg) {
      CharSequence str = JSType.toCharSequence(arg);
      return newObj ? newObj(str) : str.toString();
   }

   public static Object constructor(boolean newObj, Object self, int arg) {
      String str = Integer.toString(arg);
      return newObj ? newObj(str) : str;
   }

   public static Object constructor(boolean newObj, Object self, long arg) {
      String str = Long.toString(arg);
      return newObj ? newObj(str) : str;
   }

   public static Object constructor(boolean newObj, Object self, double arg) {
      String str = JSType.toString(arg);
      return newObj ? newObj(str) : str;
   }

   public static Object constructor(boolean newObj, Object self, boolean arg) {
      String str = Boolean.toString(arg);
      return newObj ? newObj(str) : str;
   }

   public static GuardedInvocation lookupPrimitive(LinkRequest request, Object receiver) {
      return PrimitiveLookup.lookupPrimitive(request, (MethodHandle)NashornGuards.getStringGuard(), new NativeString((CharSequence)receiver), WRAPFILTER, PROTOFILTER);
   }

   private static NativeString wrapFilter(Object receiver) {
      return new NativeString((CharSequence)receiver);
   }

   private static Object protoFilter(Object object) {
      return Global.instance().getStringPrototype();
   }

   private static CharSequence getCharSequence(Object self) {
      if (JSType.isString(self)) {
         return (CharSequence)self;
      } else if (self instanceof NativeString) {
         return ((NativeString)self).getValue();
      } else if (self != null && self == Global.instance().getStringPrototype()) {
         return "";
      } else {
         throw ECMAErrors.typeError("not.a.string", ScriptRuntime.safeToString(self));
      }
   }

   private static String getString(Object self) {
      if (self instanceof String) {
         return (String)self;
      } else if (self instanceof ConsString) {
         return self.toString();
      } else if (self instanceof NativeString) {
         return ((NativeString)self).getStringValue();
      } else if (self != null && self == Global.instance().getStringPrototype()) {
         return "";
      } else {
         throw ECMAErrors.typeError("not.a.string", ScriptRuntime.safeToString(self));
      }
   }

   private static String checkObjectToString(Object self) {
      if (self instanceof String) {
         return (String)self;
      } else if (self instanceof ConsString) {
         return self.toString();
      } else {
         Global.checkObjectCoercible(self);
         return JSType.toString(self);
      }
   }

   private boolean isValidStringIndex(int key) {
      return key >= 0 && key < this.value.length();
   }

   private static MethodHandle findOwnMH(String name, MethodType type) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), NativeString.class, name, type);
   }

   public SpecializedFunction.LinkLogic getLinkLogic(Class<? extends SpecializedFunction.LinkLogic> clazz) {
      return clazz == NativeString.CharCodeAtLinkLogic.class ? NativeString.CharCodeAtLinkLogic.INSTANCE : null;
   }

   public boolean hasPerInstanceAssumptions() {
      return false;
   }

   static {
      WRAPFILTER = findOwnMH("wrapFilter", Lookup.MH.type(NativeString.class, Object.class));
      PROTOFILTER = findOwnMH("protoFilter", Lookup.MH.type(Object.class, Object.class));
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(1);
      var10000.add(AccessorProperty.create("length", 7, "length", (MethodHandle)null));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   private static final class CharCodeAtLinkLogic extends SpecializedFunction.LinkLogic {
      private static final NativeString.CharCodeAtLinkLogic INSTANCE = new NativeString.CharCodeAtLinkLogic();

      public boolean canLink(Object self, CallSiteDescriptor desc, LinkRequest request) {
         try {
            CharSequence cs = (CharSequence)self;
            int intIndex = JSType.toInteger(request.getArguments()[2]);
            return intIndex >= 0 && intIndex < cs.length();
         } catch (IndexOutOfBoundsException | ClassCastException var6) {
            return false;
         }
      }

      public Class<? extends Throwable> getRelinkException() {
         return ClassCastException.class;
      }
   }
}
