package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.NashornGuards;
import jdk.nashorn.internal.runtime.linker.PrimitiveLookup;

public final class NativeNumber extends ScriptObject {
   static final MethodHandle WRAPFILTER;
   private static final MethodHandle PROTOFILTER;
   public static final double MAX_VALUE = Double.MAX_VALUE;
   public static final double MIN_VALUE = Double.MIN_VALUE;
   public static final double NaN = Double.NaN;
   public static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
   public static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
   private final double value;
   private static PropertyMap $nasgenmap$;

   private NativeNumber(double value, ScriptObject proto, PropertyMap map) {
      super(proto, map);
      this.value = value;
   }

   NativeNumber(double value, Global global) {
      this(value, global.getNumberPrototype(), $nasgenmap$);
   }

   private NativeNumber(double value) {
      this(value, Global.instance());
   }

   public String safeToString() {
      return "[Number " + this.toString() + "]";
   }

   public String toString() {
      return Double.toString(this.getValue());
   }

   public double getValue() {
      return this.doubleValue();
   }

   public double doubleValue() {
      return this.value;
   }

   public String getClassName() {
      return "Number";
   }

   public static Object constructor(boolean newObj, Object self, Object... args) {
      double num = args.length > 0 ? JSType.toNumber(args[0]) : 0.0D;
      return newObj ? new NativeNumber(num) : num;
   }

   public static String toFixed(Object self, Object fractionDigits) {
      return toFixed(self, JSType.toInteger(fractionDigits));
   }

   public static String toFixed(Object self, int fractionDigits) {
      if (fractionDigits >= 0 && fractionDigits <= 20) {
         double x = getNumberValue(self);
         if (Double.isNaN(x)) {
            return "NaN";
         } else if (Math.abs(x) >= 1.0E21D) {
            return JSType.toString(x);
         } else {
            NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
            format.setMinimumFractionDigits(fractionDigits);
            format.setMaximumFractionDigits(fractionDigits);
            format.setGroupingUsed(false);
            format.setRoundingMode(RoundingMode.HALF_UP);
            return format.format(x);
         }
      } else {
         throw ECMAErrors.rangeError("invalid.fraction.digits", "toFixed");
      }
   }

   public static String toExponential(Object self, Object fractionDigits) {
      double x = getNumberValue(self);
      boolean trimZeros = fractionDigits == ScriptRuntime.UNDEFINED;
      int f = trimZeros ? 16 : JSType.toInteger(fractionDigits);
      if (Double.isNaN(x)) {
         return "NaN";
      } else if (Double.isInfinite(x)) {
         return x > 0.0D ? "Infinity" : "-Infinity";
      } else if (fractionDigits == ScriptRuntime.UNDEFINED || f >= 0 && f <= 20) {
         String res = String.format(Locale.US, "%1." + f + "e", x);
         return fixExponent(res, trimZeros);
      } else {
         throw ECMAErrors.rangeError("invalid.fraction.digits", "toExponential");
      }
   }

   public static String toPrecision(Object self, Object precision) {
      double x = getNumberValue(self);
      return precision == ScriptRuntime.UNDEFINED ? JSType.toString(x) : toPrecision(x, JSType.toInteger(precision));
   }

   public static String toPrecision(Object self, int precision) {
      return toPrecision(getNumberValue(self), precision);
   }

   private static String toPrecision(double x, int p) {
      if (Double.isNaN(x)) {
         return "NaN";
      } else if (Double.isInfinite(x)) {
         return x > 0.0D ? "Infinity" : "-Infinity";
      } else if (p >= 1 && p <= 21) {
         return x == 0.0D && p <= 1 ? "0" : fixExponent(String.format(Locale.US, "%." + p + "g", x), false);
      } else {
         throw ECMAErrors.rangeError("invalid.precision");
      }
   }

   public static String toString(Object self, Object radix) {
      if (radix != ScriptRuntime.UNDEFINED) {
         int intRadix = JSType.toInteger(radix);
         if (intRadix != 10) {
            if (intRadix >= 2 && intRadix <= 36) {
               return JSType.toString(getNumberValue(self), intRadix);
            }

            throw ECMAErrors.rangeError("invalid.radix");
         }
      }

      return JSType.toString(getNumberValue(self));
   }

   public static String toLocaleString(Object self) {
      return JSType.toString(getNumberValue(self));
   }

   public static double valueOf(Object self) {
      return getNumberValue(self);
   }

   public static GuardedInvocation lookupPrimitive(LinkRequest request, Object receiver) {
      return PrimitiveLookup.lookupPrimitive(request, (MethodHandle)NashornGuards.getNumberGuard(), new NativeNumber(((Number)receiver).doubleValue()), WRAPFILTER, PROTOFILTER);
   }

   private static NativeNumber wrapFilter(Object receiver) {
      return new NativeNumber(((Number)receiver).doubleValue());
   }

   private static Object protoFilter(Object object) {
      return Global.instance().getNumberPrototype();
   }

   private static double getNumberValue(Object self) {
      if (self instanceof Number) {
         return ((Number)self).doubleValue();
      } else if (self instanceof NativeNumber) {
         return ((NativeNumber)self).getValue();
      } else if (self != null && self == Global.instance().getNumberPrototype()) {
         return 0.0D;
      } else {
         throw ECMAErrors.typeError("not.a.number", ScriptRuntime.safeToString(self));
      }
   }

   private static String fixExponent(String str, boolean trimZeros) {
      int index = str.indexOf(101);
      if (index < 1) {
         return str;
      } else {
         int expPadding = str.charAt(index + 2) == '0' ? 3 : 2;
         int fractionOffset = index;
         if (trimZeros) {
            assert index > 0;

            for(char c = str.charAt(index - 1); fractionOffset > 1 && (c == '0' || c == '.'); c = str.charAt(fractionOffset - 1)) {
               --fractionOffset;
            }
         }

         return fractionOffset >= index && expPadding != 3 ? str : str.substring(0, fractionOffset) + str.substring(index, index + 2) + str.substring(index + expPadding);
      }
   }

   private static MethodHandle findOwnMH(String name, MethodType type) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), NativeNumber.class, name, type);
   }

   static {
      WRAPFILTER = findOwnMH("wrapFilter", Lookup.MH.type(NativeNumber.class, Object.class));
      PROTOFILTER = findOwnMH("protoFilter", Lookup.MH.type(Object.class, Object.class));
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }
}
