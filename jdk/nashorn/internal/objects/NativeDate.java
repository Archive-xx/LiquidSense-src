package jdk.nashorn.internal.objects;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.parser.DateParser;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;

public final class NativeDate extends ScriptObject {
   private static final String INVALID_DATE = "Invalid Date";
   private static final int YEAR = 0;
   private static final int MONTH = 1;
   private static final int DAY = 2;
   private static final int HOUR = 3;
   private static final int MINUTE = 4;
   private static final int SECOND = 5;
   private static final int MILLISECOND = 6;
   private static final int FORMAT_DATE_TIME = 0;
   private static final int FORMAT_DATE = 1;
   private static final int FORMAT_TIME = 2;
   private static final int FORMAT_LOCAL_DATE_TIME = 3;
   private static final int FORMAT_LOCAL_DATE = 4;
   private static final int FORMAT_LOCAL_TIME = 5;
   private static final int hoursPerDay = 24;
   private static final int minutesPerHour = 60;
   private static final int secondsPerMinute = 60;
   private static final int msPerSecond = 1000;
   private static final int msPerMinute = 60000;
   private static final double msPerHour = 3600000.0D;
   private static final double msPerDay = 8.64E7D;
   private static int[][] firstDayInMonth = new int[][]{{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334}, {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335}};
   private static String[] weekDays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
   private static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
   private static final Object TO_ISO_STRING = new Object();
   private double time;
   private final TimeZone timezone;
   private static PropertyMap $nasgenmap$;

   private static InvokeByName getTO_ISO_STRING() {
      return Global.instance().getInvokeByName(TO_ISO_STRING, new Callable<InvokeByName>() {
         public InvokeByName call() {
            return new InvokeByName("toISOString", ScriptObject.class, Object.class, new Class[]{Object.class});
         }
      });
   }

   private NativeDate(double time, ScriptObject proto, PropertyMap map) {
      super(proto, map);
      ScriptEnvironment env = Global.getEnv();
      this.time = time;
      this.timezone = env._timezone;
   }

   NativeDate(double time, ScriptObject proto) {
      this(time, proto, $nasgenmap$);
   }

   NativeDate(double time, Global global) {
      this(time, global.getDatePrototype(), $nasgenmap$);
   }

   private NativeDate(double time) {
      this(time, Global.instance());
   }

   private NativeDate() {
      this((double)System.currentTimeMillis());
   }

   public String getClassName() {
      return "Date";
   }

   public Object getDefaultValue(Class<?> hint) {
      return super.getDefaultValue(hint == null ? String.class : hint);
   }

   public static Object construct(boolean isNew, Object self) {
      NativeDate result = new NativeDate();
      return isNew ? result : toStringImpl(result, 0);
   }

   public static Object construct(boolean isNew, Object self, Object... args) {
      if (!isNew) {
         return toStringImpl(new NativeDate(), 0);
      } else {
         NativeDate result;
         switch(args.length) {
         case 0:
            result = new NativeDate();
            break;
         case 1:
            Object arg = JSType.toPrimitive(args[0]);
            double num;
            if (JSType.isString(arg)) {
               num = parseDateString(arg.toString());
            } else {
               num = timeClip(JSType.toNumber(args[0]));
            }

            result = new NativeDate(num);
            break;
         default:
            result = new NativeDate(0.0D);
            double[] d = convertCtorArgs(args);
            if (d == null) {
               result.setTime(Double.NaN);
            } else {
               double time = timeClip(utc(makeDate(d), result.getTimeZone()));
               result.setTime(time);
            }
         }

         return result;
      }
   }

   public String safeToString() {
      String str = this.isValidDate() ? toISOStringImpl(this) : "Invalid Date";
      return "[Date " + str + "]";
   }

   public String toString() {
      return this.isValidDate() ? toString(this).toString() : "Invalid Date";
   }

   public static double parse(Object self, Object string) {
      return parseDateString(JSType.toString(string));
   }

   public static double UTC(Object self, Object... args) {
      NativeDate nd = new NativeDate(0.0D);
      double[] d = convertCtorArgs(args);
      double time = d == null ? Double.NaN : timeClip(makeDate(d));
      nd.setTime(time);
      return time;
   }

   public static double now(Object self) {
      return (double)System.currentTimeMillis();
   }

   public static String toString(Object self) {
      return toStringImpl(self, 0);
   }

   public static String toDateString(Object self) {
      return toStringImpl(self, 1);
   }

   public static String toTimeString(Object self) {
      return toStringImpl(self, 2);
   }

   public static String toLocaleString(Object self) {
      return toStringImpl(self, 3);
   }

   public static String toLocaleDateString(Object self) {
      return toStringImpl(self, 4);
   }

   public static String toLocaleTimeString(Object self) {
      return toStringImpl(self, 5);
   }

   public static double valueOf(Object self) {
      NativeDate nd = getNativeDate(self);
      return nd != null ? nd.getTime() : Double.NaN;
   }

   public static double getTime(Object self) {
      NativeDate nd = getNativeDate(self);
      return nd != null ? nd.getTime() : Double.NaN;
   }

   public static Object getFullYear(Object self) {
      return getField(self, 0);
   }

   public static double getUTCFullYear(Object self) {
      return getUTCField(self, 0);
   }

   public static double getYear(Object self) {
      NativeDate nd = getNativeDate(self);
      return nd != null && nd.isValidDate() ? (double)(yearFromTime(nd.getLocalTime()) - 1900) : Double.NaN;
   }

   public static double getMonth(Object self) {
      return getField(self, 1);
   }

   public static double getUTCMonth(Object self) {
      return getUTCField(self, 1);
   }

   public static double getDate(Object self) {
      return getField(self, 2);
   }

   public static double getUTCDate(Object self) {
      return getUTCField(self, 2);
   }

   public static double getDay(Object self) {
      NativeDate nd = getNativeDate(self);
      return nd != null && nd.isValidDate() ? (double)weekDay(nd.getLocalTime()) : Double.NaN;
   }

   public static double getUTCDay(Object self) {
      NativeDate nd = getNativeDate(self);
      return nd != null && nd.isValidDate() ? (double)weekDay(nd.getTime()) : Double.NaN;
   }

   public static double getHours(Object self) {
      return getField(self, 3);
   }

   public static double getUTCHours(Object self) {
      return getUTCField(self, 3);
   }

   public static double getMinutes(Object self) {
      return getField(self, 4);
   }

   public static double getUTCMinutes(Object self) {
      return getUTCField(self, 4);
   }

   public static double getSeconds(Object self) {
      return getField(self, 5);
   }

   public static double getUTCSeconds(Object self) {
      return getUTCField(self, 5);
   }

   public static double getMilliseconds(Object self) {
      return getField(self, 6);
   }

   public static double getUTCMilliseconds(Object self) {
      return getUTCField(self, 6);
   }

   public static double getTimezoneOffset(Object self) {
      NativeDate nd = getNativeDate(self);
      if (nd != null && nd.isValidDate()) {
         long msec = (long)nd.getTime();
         return (double)(-nd.getTimeZone().getOffset(msec) / '\uea60');
      } else {
         return Double.NaN;
      }
   }

   public static double setTime(Object self, Object time) {
      NativeDate nd = getNativeDate(self);
      double num = timeClip(JSType.toNumber(time));
      nd.setTime(num);
      return num;
   }

   public static double setMilliseconds(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 6, args, true);
      return nd.getTime();
   }

   public static double setUTCMilliseconds(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 6, args, false);
      return nd.getTime();
   }

   public static double setSeconds(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 5, args, true);
      return nd.getTime();
   }

   public static double setUTCSeconds(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 5, args, false);
      return nd.getTime();
   }

   public static double setMinutes(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 4, args, true);
      return nd.getTime();
   }

   public static double setUTCMinutes(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 4, args, false);
      return nd.getTime();
   }

   public static double setHours(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 3, args, true);
      return nd.getTime();
   }

   public static double setUTCHours(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 3, args, false);
      return nd.getTime();
   }

   public static double setDate(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 2, args, true);
      return nd.getTime();
   }

   public static double setUTCDate(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 2, args, false);
      return nd.getTime();
   }

   public static double setMonth(Object self, Object... args) {
      NativeDate nd = getNativeDate(self);
      setFields(nd, 1, args, true);
      return nd.getTime();
   }

   public static double setUTCMonth(Object self, Object... args) {
      NativeDate nd = ensureNativeDate(self);
      setFields(nd, 1, args, false);
      return nd.getTime();
   }

   public static double setFullYear(Object self, Object... args) {
      NativeDate nd = ensureNativeDate(self);
      if (nd.isValidDate()) {
         setFields(nd, 0, args, true);
      } else {
         double[] d = convertArgs(args, 0.0D, 0, 0, 3);
         if (d != null) {
            nd.setTime(timeClip(utc(makeDate(makeDay(d[0], d[1], d[2]), 0.0D), nd.getTimeZone())));
         } else {
            nd.setTime(Double.NaN);
         }
      }

      return nd.getTime();
   }

   public static double setUTCFullYear(Object self, Object... args) {
      NativeDate nd = ensureNativeDate(self);
      if (nd.isValidDate()) {
         setFields(nd, 0, args, false);
      } else {
         double[] d = convertArgs(args, 0.0D, 0, 0, 3);
         nd.setTime(timeClip(makeDate(makeDay(d[0], d[1], d[2]), 0.0D)));
      }

      return nd.getTime();
   }

   public static double setYear(Object self, Object year) {
      NativeDate nd = getNativeDate(self);
      if (Double.isNaN(nd.getTime())) {
         nd.setTime(utc(0.0D, nd.getTimeZone()));
      }

      double yearNum = JSType.toNumber(year);
      if (Double.isNaN(yearNum)) {
         nd.setTime(Double.NaN);
         return nd.getTime();
      } else {
         int yearInt = (int)yearNum;
         if (0 <= yearInt && yearInt <= 99) {
            yearInt += 1900;
         }

         setFields(nd, 0, new Object[]{yearInt}, true);
         return nd.getTime();
      }
   }

   public static String toUTCString(Object self) {
      return toGMTStringImpl(self);
   }

   public static String toGMTString(Object self) {
      return toGMTStringImpl(self);
   }

   public static String toISOString(Object self) {
      return toISOStringImpl(self);
   }

   public static Object toJSON(Object self, Object key) {
      Object selfObj = Global.toObject(self);
      if (!(selfObj instanceof ScriptObject)) {
         return null;
      } else {
         ScriptObject sobj = (ScriptObject)selfObj;
         Object value = sobj.getDefaultValue(Number.class);
         if (value instanceof Number) {
            double num = ((Number)value).doubleValue();
            if (Double.isInfinite(num) || Double.isNaN(num)) {
               return null;
            }
         }

         try {
            InvokeByName toIsoString = getTO_ISO_STRING();
            Object func = toIsoString.getGetter().invokeExact(sobj);
            if (Bootstrap.isCallable(func)) {
               return toIsoString.getInvoker().invokeExact(func, sobj, key);
            } else {
               throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(func));
            }
         } catch (Error | RuntimeException var7) {
            throw var7;
         } catch (Throwable var8) {
            throw new RuntimeException(var8);
         }
      }
   }

   private static double parseDateString(String str) {
      DateParser parser = new DateParser(str);
      if (parser.parse()) {
         Integer[] fields = parser.getDateFields();
         double d = makeDate(fields);
         if (fields[7] != null) {
            d -= (double)(fields[7] * '\uea60');
         } else {
            d = utc(d, Global.getEnv()._timezone);
         }

         d = timeClip(d);
         return d;
      } else {
         return Double.NaN;
      }
   }

   private static void zeroPad(StringBuilder sb, int n, int length) {
      int l = 1;

      for(int d = 10; l < length; d *= 10) {
         if (n < d) {
            sb.append('0');
         }

         ++l;
      }

      sb.append(n);
   }

   private static String toStringImpl(Object self, int format) {
      NativeDate nd = getNativeDate(self);
      if (nd != null && nd.isValidDate()) {
         StringBuilder sb = new StringBuilder(40);
         double t = nd.getLocalTime();
         switch(format) {
         case 0:
         case 1:
         case 3:
            sb.append(weekDays[weekDay(t)]).append(' ').append(months[monthFromTime(t)]).append(' ');
            zeroPad(sb, dayFromTime(t), 2);
            sb.append(' ');
            zeroPad(sb, yearFromTime(t), 4);
            if (format == 1) {
               break;
            }

            sb.append(' ');
         case 2:
            TimeZone tz = nd.getTimeZone();
            double utcTime = nd.getTime();
            int offset = tz.getOffset((long)utcTime) / '\uea60';
            boolean inDaylightTime = offset != tz.getRawOffset() / '\uea60';
            offset = offset / 60 * 100 + offset % 60;
            zeroPad(sb, hourFromTime(t), 2);
            sb.append(':');
            zeroPad(sb, minFromTime(t), 2);
            sb.append(':');
            zeroPad(sb, secFromTime(t), 2);
            sb.append(" GMT").append((char)(offset < 0 ? '-' : '+'));
            zeroPad(sb, Math.abs(offset), 4);
            sb.append(" (").append(tz.getDisplayName(inDaylightTime, 0, Locale.US)).append(')');
            break;
         case 4:
            zeroPad(sb, yearFromTime(t), 4);
            sb.append('-');
            zeroPad(sb, monthFromTime(t) + 1, 2);
            sb.append('-');
            zeroPad(sb, dayFromTime(t), 2);
            break;
         case 5:
            zeroPad(sb, hourFromTime(t), 2);
            sb.append(':');
            zeroPad(sb, minFromTime(t), 2);
            sb.append(':');
            zeroPad(sb, secFromTime(t), 2);
            break;
         default:
            throw new IllegalArgumentException("format: " + format);
         }

         return sb.toString();
      } else {
         return "Invalid Date";
      }
   }

   private static String toGMTStringImpl(Object self) {
      NativeDate nd = getNativeDate(self);
      if (nd != null && nd.isValidDate()) {
         StringBuilder sb = new StringBuilder(29);
         double t = nd.getTime();
         sb.append(weekDays[weekDay(t)]).append(", ");
         zeroPad(sb, dayFromTime(t), 2);
         sb.append(' ').append(months[monthFromTime(t)]).append(' ');
         zeroPad(sb, yearFromTime(t), 4);
         sb.append(' ');
         zeroPad(sb, hourFromTime(t), 2);
         sb.append(':');
         zeroPad(sb, minFromTime(t), 2);
         sb.append(':');
         zeroPad(sb, secFromTime(t), 2);
         sb.append(" GMT");
         return sb.toString();
      } else {
         throw ECMAErrors.rangeError("invalid.date");
      }
   }

   private static String toISOStringImpl(Object self) {
      NativeDate nd = getNativeDate(self);
      if (nd != null && nd.isValidDate()) {
         StringBuilder sb = new StringBuilder(24);
         double t = nd.getTime();
         zeroPad(sb, yearFromTime(t), 4);
         sb.append('-');
         zeroPad(sb, monthFromTime(t) + 1, 2);
         sb.append('-');
         zeroPad(sb, dayFromTime(t), 2);
         sb.append('T');
         zeroPad(sb, hourFromTime(t), 2);
         sb.append(':');
         zeroPad(sb, minFromTime(t), 2);
         sb.append(':');
         zeroPad(sb, secFromTime(t), 2);
         sb.append('.');
         zeroPad(sb, msFromTime(t), 3);
         sb.append("Z");
         return sb.toString();
      } else {
         throw ECMAErrors.rangeError("invalid.date");
      }
   }

   private static double day(double t) {
      return Math.floor(t / 8.64E7D);
   }

   private static double timeWithinDay(double t) {
      double val = t % 8.64E7D;
      return val < 0.0D ? val + 8.64E7D : val;
   }

   private static boolean isLeapYear(int y) {
      return y % 4 == 0 && (y % 100 != 0 || y % 400 == 0);
   }

   private static int daysInYear(int y) {
      return isLeapYear(y) ? 366 : 365;
   }

   private static double dayFromYear(double y) {
      return 365.0D * (y - 1970.0D) + Math.floor((y - 1969.0D) / 4.0D) - Math.floor((y - 1901.0D) / 100.0D) + Math.floor((y - 1601.0D) / 400.0D);
   }

   private static double timeFromYear(int y) {
      return dayFromYear((double)y) * 8.64E7D;
   }

   private static int yearFromTime(double t) {
      int y = (int)Math.floor(t / 3.1556952E10D) + 1970;
      double t2 = timeFromYear(y);
      if (t2 > t) {
         --y;
      } else if (t2 + 8.64E7D * (double)daysInYear(y) <= t) {
         ++y;
      }

      return y;
   }

   private static int dayWithinYear(double t, int year) {
      return (int)(day(t) - dayFromYear((double)year));
   }

   private static int monthFromTime(double t) {
      int year = yearFromTime(t);
      int day = dayWithinYear(t, year);
      int[] firstDay = firstDayInMonth[isLeapYear(year) ? 1 : 0];

      int month;
      for(month = 0; month < 11 && firstDay[month + 1] <= day; ++month) {
      }

      return month;
   }

   private static int dayFromTime(double t) {
      int year = yearFromTime(t);
      int day = dayWithinYear(t, year);
      int[] firstDay = firstDayInMonth[isLeapYear(year) ? 1 : 0];

      int month;
      for(month = 0; month < 11 && firstDay[month + 1] <= day; ++month) {
      }

      return 1 + day - firstDay[month];
   }

   private static int dayFromMonth(int month, int year) {
      assert month >= 0 && month <= 11;

      int[] firstDay = firstDayInMonth[isLeapYear(year) ? 1 : 0];
      return firstDay[month];
   }

   private static int weekDay(double time) {
      int day = (int)(day(time) + 4.0D) % 7;
      return day < 0 ? day + 7 : day;
   }

   private static double localTime(double time, TimeZone tz) {
      return time + (double)tz.getOffset((long)time);
   }

   private static double utc(double time, TimeZone tz) {
      return time - (double)tz.getOffset((long)(time - (double)tz.getRawOffset()));
   }

   private static int hourFromTime(double t) {
      int h = (int)(Math.floor(t / 3600000.0D) % 24.0D);
      return h < 0 ? h + 24 : h;
   }

   private static int minFromTime(double t) {
      int m = (int)(Math.floor(t / 60000.0D) % 60.0D);
      return m < 0 ? m + 60 : m;
   }

   private static int secFromTime(double t) {
      int s = (int)(Math.floor(t / 1000.0D) % 60.0D);
      return s < 0 ? s + 60 : s;
   }

   private static int msFromTime(double t) {
      int m = (int)(t % 1000.0D);
      return m < 0 ? m + 1000 : m;
   }

   private static int valueFromTime(int unit, double t) {
      switch(unit) {
      case 0:
         return yearFromTime(t);
      case 1:
         return monthFromTime(t);
      case 2:
         return dayFromTime(t);
      case 3:
         return hourFromTime(t);
      case 4:
         return minFromTime(t);
      case 5:
         return secFromTime(t);
      case 6:
         return msFromTime(t);
      default:
         throw new IllegalArgumentException(Integer.toString(unit));
      }
   }

   private static double makeTime(double hour, double min, double sec, double ms) {
      return hour * 3600000.0D + min * 60000.0D + sec * 1000.0D + ms;
   }

   private static double makeDay(double year, double month, double date) {
      double y = year + Math.floor(month / 12.0D);
      int m = (int)(month % 12.0D);
      if (m < 0) {
         m += 12;
      }

      double d = dayFromYear(y);
      d += (double)dayFromMonth(m, (int)y);
      return d + date - 1.0D;
   }

   private static double makeDate(double day, double time) {
      return day * 8.64E7D + time;
   }

   private static double makeDate(Integer[] d) {
      double time = makeDay((double)d[0], (double)d[1], (double)d[2]) * 8.64E7D;
      return time + makeTime((double)d[3], (double)d[4], (double)d[5], (double)d[6]);
   }

   private static double makeDate(double[] d) {
      double time = makeDay(d[0], d[1], d[2]) * 8.64E7D;
      return time + makeTime(d[3], d[4], d[5], d[6]);
   }

   private static double[] convertCtorArgs(Object[] args) {
      double[] d = new double[7];
      boolean nullReturn = false;

      for(int i = 0; i < d.length; ++i) {
         if (i >= args.length) {
            d[i] = i == 2 ? 1.0D : 0.0D;
         } else {
            double darg = JSType.toNumber(args[i]);
            if (Double.isNaN(darg) || Double.isInfinite(darg)) {
               nullReturn = true;
            }

            d[i] = (double)((long)darg);
         }
      }

      if (0.0D <= d[0] && d[0] <= 99.0D) {
         d[0] += 1900.0D;
      }

      return nullReturn ? null : d;
   }

   private static double[] convertArgs(Object[] args, double time, int fieldId, int start, int length) {
      double[] d = new double[length];
      boolean nullReturn = false;

      for(int i = start; i < start + length; ++i) {
         if (fieldId <= i && i < fieldId + args.length) {
            double darg = JSType.toNumber(args[i - fieldId]);
            if (Double.isNaN(darg) || Double.isInfinite(darg)) {
               nullReturn = true;
            }

            d[i - start] = (double)((long)darg);
         } else {
            if (i == fieldId) {
               nullReturn = true;
            }

            if (!nullReturn && !Double.isNaN(time)) {
               d[i - start] = (double)valueFromTime(i, time);
            }
         }
      }

      return nullReturn ? null : d;
   }

   private static double timeClip(double time) {
      return !Double.isInfinite(time) && !Double.isNaN(time) && !(Math.abs(time) > 8.64E15D) ? (double)((long)time) : Double.NaN;
   }

   private static NativeDate ensureNativeDate(Object self) {
      return getNativeDate(self);
   }

   private static NativeDate getNativeDate(Object self) {
      if (self instanceof NativeDate) {
         return (NativeDate)self;
      } else if (self != null && self == Global.instance().getDatePrototype()) {
         return Global.instance().getDefaultDate();
      } else {
         throw ECMAErrors.typeError("not.a.date", ScriptRuntime.safeToString(self));
      }
   }

   private static double getField(Object self, int field) {
      NativeDate nd = getNativeDate(self);
      return nd != null && nd.isValidDate() ? (double)valueFromTime(field, nd.getLocalTime()) : Double.NaN;
   }

   private static double getUTCField(Object self, int field) {
      NativeDate nd = getNativeDate(self);
      return nd != null && nd.isValidDate() ? (double)valueFromTime(field, nd.getTime()) : Double.NaN;
   }

   private static void setFields(NativeDate nd, int fieldId, Object[] args, boolean local) {
      byte start;
      byte length;
      if (fieldId < 3) {
         start = 0;
         length = 3;
      } else {
         start = 3;
         length = 4;
      }

      double time = local ? nd.getLocalTime() : nd.getTime();
      double[] d = convertArgs(args, time, fieldId, start, length);
      if (nd.isValidDate()) {
         double newTime;
         if (d == null) {
            newTime = Double.NaN;
         } else {
            if (start == 0) {
               newTime = makeDate(makeDay(d[0], d[1], d[2]), timeWithinDay(time));
            } else {
               newTime = makeDate(day(time), makeTime(d[0], d[1], d[2], d[3]));
            }

            if (local) {
               newTime = utc(newTime, nd.getTimeZone());
            }

            newTime = timeClip(newTime);
         }

         nd.setTime(newTime);
      }
   }

   private boolean isValidDate() {
      return !Double.isNaN(this.time);
   }

   private double getLocalTime() {
      return localTime(this.time, this.timezone);
   }

   private double getTime() {
      return this.time;
   }

   private void setTime(double time) {
      this.time = time;
   }

   private TimeZone getTimeZone() {
      return this.timezone;
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      $nasgenmap$ = PropertyMap.newMap((Collection)Collections.EMPTY_LIST);
   }
}
