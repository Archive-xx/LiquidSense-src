package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;

final class NativeDate$Prototype extends PrototypeObject {
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private Object toDateString = ScriptFunction.createBuiltin("toDateString", "toDateString");
   private Object toTimeString = ScriptFunction.createBuiltin("toTimeString", "toTimeString");
   private Object toLocaleString = ScriptFunction.createBuiltin("toLocaleString", "toLocaleString");
   private Object toLocaleDateString = ScriptFunction.createBuiltin("toLocaleDateString", "toLocaleDateString");
   private Object toLocaleTimeString = ScriptFunction.createBuiltin("toLocaleTimeString", "toLocaleTimeString");
   private Object valueOf = ScriptFunction.createBuiltin("valueOf", "valueOf");
   private Object getTime = ScriptFunction.createBuiltin("getTime", "getTime");
   private Object getFullYear = ScriptFunction.createBuiltin("getFullYear", "getFullYear");
   private Object getUTCFullYear = ScriptFunction.createBuiltin("getUTCFullYear", "getUTCFullYear");
   private Object getYear = ScriptFunction.createBuiltin("getYear", "getYear");
   private Object getMonth = ScriptFunction.createBuiltin("getMonth", "getMonth");
   private Object getUTCMonth = ScriptFunction.createBuiltin("getUTCMonth", "getUTCMonth");
   private Object getDate = ScriptFunction.createBuiltin("getDate", "getDate");
   private Object getUTCDate = ScriptFunction.createBuiltin("getUTCDate", "getUTCDate");
   private Object getDay = ScriptFunction.createBuiltin("getDay", "getDay");
   private Object getUTCDay = ScriptFunction.createBuiltin("getUTCDay", "getUTCDay");
   private Object getHours = ScriptFunction.createBuiltin("getHours", "getHours");
   private Object getUTCHours = ScriptFunction.createBuiltin("getUTCHours", "getUTCHours");
   private Object getMinutes = ScriptFunction.createBuiltin("getMinutes", "getMinutes");
   private Object getUTCMinutes = ScriptFunction.createBuiltin("getUTCMinutes", "getUTCMinutes");
   private Object getSeconds = ScriptFunction.createBuiltin("getSeconds", "getSeconds");
   private Object getUTCSeconds = ScriptFunction.createBuiltin("getUTCSeconds", "getUTCSeconds");
   private Object getMilliseconds = ScriptFunction.createBuiltin("getMilliseconds", "getMilliseconds");
   private Object getUTCMilliseconds = ScriptFunction.createBuiltin("getUTCMilliseconds", "getUTCMilliseconds");
   private Object getTimezoneOffset = ScriptFunction.createBuiltin("getTimezoneOffset", "getTimezoneOffset");
   private Object setTime = ScriptFunction.createBuiltin("setTime", "setTime");
   private Object setMilliseconds;
   private Object setUTCMilliseconds;
   private Object setSeconds;
   private Object setUTCSeconds;
   private Object setMinutes;
   private Object setUTCMinutes;
   private Object setHours;
   private Object setUTCHours;
   private Object setDate;
   private Object setUTCDate;
   private Object setMonth;
   private Object setUTCMonth;
   private Object setFullYear;
   private Object setUTCFullYear;
   private Object setYear;
   private Object toUTCString;
   private Object toGMTString;
   private Object toISOString;
   private Object toJSON;
   private static final PropertyMap $nasgenmap$;

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   public Object G$toDateString() {
      return this.toDateString;
   }

   public void S$toDateString(Object var1) {
      this.toDateString = var1;
   }

   public Object G$toTimeString() {
      return this.toTimeString;
   }

   public void S$toTimeString(Object var1) {
      this.toTimeString = var1;
   }

   public Object G$toLocaleString() {
      return this.toLocaleString;
   }

   public void S$toLocaleString(Object var1) {
      this.toLocaleString = var1;
   }

   public Object G$toLocaleDateString() {
      return this.toLocaleDateString;
   }

   public void S$toLocaleDateString(Object var1) {
      this.toLocaleDateString = var1;
   }

   public Object G$toLocaleTimeString() {
      return this.toLocaleTimeString;
   }

   public void S$toLocaleTimeString(Object var1) {
      this.toLocaleTimeString = var1;
   }

   public Object G$valueOf() {
      return this.valueOf;
   }

   public void S$valueOf(Object var1) {
      this.valueOf = var1;
   }

   public Object G$getTime() {
      return this.getTime;
   }

   public void S$getTime(Object var1) {
      this.getTime = var1;
   }

   public Object G$getFullYear() {
      return this.getFullYear;
   }

   public void S$getFullYear(Object var1) {
      this.getFullYear = var1;
   }

   public Object G$getUTCFullYear() {
      return this.getUTCFullYear;
   }

   public void S$getUTCFullYear(Object var1) {
      this.getUTCFullYear = var1;
   }

   public Object G$getYear() {
      return this.getYear;
   }

   public void S$getYear(Object var1) {
      this.getYear = var1;
   }

   public Object G$getMonth() {
      return this.getMonth;
   }

   public void S$getMonth(Object var1) {
      this.getMonth = var1;
   }

   public Object G$getUTCMonth() {
      return this.getUTCMonth;
   }

   public void S$getUTCMonth(Object var1) {
      this.getUTCMonth = var1;
   }

   public Object G$getDate() {
      return this.getDate;
   }

   public void S$getDate(Object var1) {
      this.getDate = var1;
   }

   public Object G$getUTCDate() {
      return this.getUTCDate;
   }

   public void S$getUTCDate(Object var1) {
      this.getUTCDate = var1;
   }

   public Object G$getDay() {
      return this.getDay;
   }

   public void S$getDay(Object var1) {
      this.getDay = var1;
   }

   public Object G$getUTCDay() {
      return this.getUTCDay;
   }

   public void S$getUTCDay(Object var1) {
      this.getUTCDay = var1;
   }

   public Object G$getHours() {
      return this.getHours;
   }

   public void S$getHours(Object var1) {
      this.getHours = var1;
   }

   public Object G$getUTCHours() {
      return this.getUTCHours;
   }

   public void S$getUTCHours(Object var1) {
      this.getUTCHours = var1;
   }

   public Object G$getMinutes() {
      return this.getMinutes;
   }

   public void S$getMinutes(Object var1) {
      this.getMinutes = var1;
   }

   public Object G$getUTCMinutes() {
      return this.getUTCMinutes;
   }

   public void S$getUTCMinutes(Object var1) {
      this.getUTCMinutes = var1;
   }

   public Object G$getSeconds() {
      return this.getSeconds;
   }

   public void S$getSeconds(Object var1) {
      this.getSeconds = var1;
   }

   public Object G$getUTCSeconds() {
      return this.getUTCSeconds;
   }

   public void S$getUTCSeconds(Object var1) {
      this.getUTCSeconds = var1;
   }

   public Object G$getMilliseconds() {
      return this.getMilliseconds;
   }

   public void S$getMilliseconds(Object var1) {
      this.getMilliseconds = var1;
   }

   public Object G$getUTCMilliseconds() {
      return this.getUTCMilliseconds;
   }

   public void S$getUTCMilliseconds(Object var1) {
      this.getUTCMilliseconds = var1;
   }

   public Object G$getTimezoneOffset() {
      return this.getTimezoneOffset;
   }

   public void S$getTimezoneOffset(Object var1) {
      this.getTimezoneOffset = var1;
   }

   public Object G$setTime() {
      return this.setTime;
   }

   public void S$setTime(Object var1) {
      this.setTime = var1;
   }

   public Object G$setMilliseconds() {
      return this.setMilliseconds;
   }

   public void S$setMilliseconds(Object var1) {
      this.setMilliseconds = var1;
   }

   public Object G$setUTCMilliseconds() {
      return this.setUTCMilliseconds;
   }

   public void S$setUTCMilliseconds(Object var1) {
      this.setUTCMilliseconds = var1;
   }

   public Object G$setSeconds() {
      return this.setSeconds;
   }

   public void S$setSeconds(Object var1) {
      this.setSeconds = var1;
   }

   public Object G$setUTCSeconds() {
      return this.setUTCSeconds;
   }

   public void S$setUTCSeconds(Object var1) {
      this.setUTCSeconds = var1;
   }

   public Object G$setMinutes() {
      return this.setMinutes;
   }

   public void S$setMinutes(Object var1) {
      this.setMinutes = var1;
   }

   public Object G$setUTCMinutes() {
      return this.setUTCMinutes;
   }

   public void S$setUTCMinutes(Object var1) {
      this.setUTCMinutes = var1;
   }

   public Object G$setHours() {
      return this.setHours;
   }

   public void S$setHours(Object var1) {
      this.setHours = var1;
   }

   public Object G$setUTCHours() {
      return this.setUTCHours;
   }

   public void S$setUTCHours(Object var1) {
      this.setUTCHours = var1;
   }

   public Object G$setDate() {
      return this.setDate;
   }

   public void S$setDate(Object var1) {
      this.setDate = var1;
   }

   public Object G$setUTCDate() {
      return this.setUTCDate;
   }

   public void S$setUTCDate(Object var1) {
      this.setUTCDate = var1;
   }

   public Object G$setMonth() {
      return this.setMonth;
   }

   public void S$setMonth(Object var1) {
      this.setMonth = var1;
   }

   public Object G$setUTCMonth() {
      return this.setUTCMonth;
   }

   public void S$setUTCMonth(Object var1) {
      this.setUTCMonth = var1;
   }

   public Object G$setFullYear() {
      return this.setFullYear;
   }

   public void S$setFullYear(Object var1) {
      this.setFullYear = var1;
   }

   public Object G$setUTCFullYear() {
      return this.setUTCFullYear;
   }

   public void S$setUTCFullYear(Object var1) {
      this.setUTCFullYear = var1;
   }

   public Object G$setYear() {
      return this.setYear;
   }

   public void S$setYear(Object var1) {
      this.setYear = var1;
   }

   public Object G$toUTCString() {
      return this.toUTCString;
   }

   public void S$toUTCString(Object var1) {
      this.toUTCString = var1;
   }

   public Object G$toGMTString() {
      return this.toGMTString;
   }

   public void S$toGMTString(Object var1) {
      this.toGMTString = var1;
   }

   public Object G$toISOString() {
      return this.toISOString;
   }

   public void S$toISOString(Object var1) {
      this.toISOString = var1;
   }

   public Object G$toJSON() {
      return this.toJSON;
   }

   public void S$toJSON(Object var1) {
      this.toJSON = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(46);
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      var10000.add(AccessorProperty.create("toDateString", 2, "G$toDateString", "S$toDateString"));
      var10000.add(AccessorProperty.create("toTimeString", 2, "G$toTimeString", "S$toTimeString"));
      var10000.add(AccessorProperty.create("toLocaleString", 2, "G$toLocaleString", "S$toLocaleString"));
      var10000.add(AccessorProperty.create("toLocaleDateString", 2, "G$toLocaleDateString", "S$toLocaleDateString"));
      var10000.add(AccessorProperty.create("toLocaleTimeString", 2, "G$toLocaleTimeString", "S$toLocaleTimeString"));
      var10000.add(AccessorProperty.create("valueOf", 2, "G$valueOf", "S$valueOf"));
      var10000.add(AccessorProperty.create("getTime", 2, "G$getTime", "S$getTime"));
      var10000.add(AccessorProperty.create("getFullYear", 2, "G$getFullYear", "S$getFullYear"));
      var10000.add(AccessorProperty.create("getUTCFullYear", 2, "G$getUTCFullYear", "S$getUTCFullYear"));
      var10000.add(AccessorProperty.create("getYear", 2, "G$getYear", "S$getYear"));
      var10000.add(AccessorProperty.create("getMonth", 2, "G$getMonth", "S$getMonth"));
      var10000.add(AccessorProperty.create("getUTCMonth", 2, "G$getUTCMonth", "S$getUTCMonth"));
      var10000.add(AccessorProperty.create("getDate", 2, "G$getDate", "S$getDate"));
      var10000.add(AccessorProperty.create("getUTCDate", 2, "G$getUTCDate", "S$getUTCDate"));
      var10000.add(AccessorProperty.create("getDay", 2, "G$getDay", "S$getDay"));
      var10000.add(AccessorProperty.create("getUTCDay", 2, "G$getUTCDay", "S$getUTCDay"));
      var10000.add(AccessorProperty.create("getHours", 2, "G$getHours", "S$getHours"));
      var10000.add(AccessorProperty.create("getUTCHours", 2, "G$getUTCHours", "S$getUTCHours"));
      var10000.add(AccessorProperty.create("getMinutes", 2, "G$getMinutes", "S$getMinutes"));
      var10000.add(AccessorProperty.create("getUTCMinutes", 2, "G$getUTCMinutes", "S$getUTCMinutes"));
      var10000.add(AccessorProperty.create("getSeconds", 2, "G$getSeconds", "S$getSeconds"));
      var10000.add(AccessorProperty.create("getUTCSeconds", 2, "G$getUTCSeconds", "S$getUTCSeconds"));
      var10000.add(AccessorProperty.create("getMilliseconds", 2, "G$getMilliseconds", "S$getMilliseconds"));
      var10000.add(AccessorProperty.create("getUTCMilliseconds", 2, "G$getUTCMilliseconds", "S$getUTCMilliseconds"));
      var10000.add(AccessorProperty.create("getTimezoneOffset", 2, "G$getTimezoneOffset", "S$getTimezoneOffset"));
      var10000.add(AccessorProperty.create("setTime", 2, "G$setTime", "S$setTime"));
      var10000.add(AccessorProperty.create("setMilliseconds", 2, "G$setMilliseconds", "S$setMilliseconds"));
      var10000.add(AccessorProperty.create("setUTCMilliseconds", 2, "G$setUTCMilliseconds", "S$setUTCMilliseconds"));
      var10000.add(AccessorProperty.create("setSeconds", 2, "G$setSeconds", "S$setSeconds"));
      var10000.add(AccessorProperty.create("setUTCSeconds", 2, "G$setUTCSeconds", "S$setUTCSeconds"));
      var10000.add(AccessorProperty.create("setMinutes", 2, "G$setMinutes", "S$setMinutes"));
      var10000.add(AccessorProperty.create("setUTCMinutes", 2, "G$setUTCMinutes", "S$setUTCMinutes"));
      var10000.add(AccessorProperty.create("setHours", 2, "G$setHours", "S$setHours"));
      var10000.add(AccessorProperty.create("setUTCHours", 2, "G$setUTCHours", "S$setUTCHours"));
      var10000.add(AccessorProperty.create("setDate", 2, "G$setDate", "S$setDate"));
      var10000.add(AccessorProperty.create("setUTCDate", 2, "G$setUTCDate", "S$setUTCDate"));
      var10000.add(AccessorProperty.create("setMonth", 2, "G$setMonth", "S$setMonth"));
      var10000.add(AccessorProperty.create("setUTCMonth", 2, "G$setUTCMonth", "S$setUTCMonth"));
      var10000.add(AccessorProperty.create("setFullYear", 2, "G$setFullYear", "S$setFullYear"));
      var10000.add(AccessorProperty.create("setUTCFullYear", 2, "G$setUTCFullYear", "S$setUTCFullYear"));
      var10000.add(AccessorProperty.create("setYear", 2, "G$setYear", "S$setYear"));
      var10000.add(AccessorProperty.create("toUTCString", 2, "G$toUTCString", "S$toUTCString"));
      var10000.add(AccessorProperty.create("toGMTString", 2, "G$toGMTString", "S$toGMTString"));
      var10000.add(AccessorProperty.create("toISOString", 2, "G$toISOString", "S$toISOString"));
      var10000.add(AccessorProperty.create("toJSON", 2, "G$toJSON", "S$toJSON"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeDate$Prototype() {
      super($nasgenmap$);
      ScriptFunction var10001 = ScriptFunction.createBuiltin("setMilliseconds", "setMilliseconds");
      var10001.setArity(1);
      this.setMilliseconds = var10001;
      var10001 = ScriptFunction.createBuiltin("setUTCMilliseconds", "setUTCMilliseconds");
      var10001.setArity(1);
      this.setUTCMilliseconds = var10001;
      var10001 = ScriptFunction.createBuiltin("setSeconds", "setSeconds");
      var10001.setArity(2);
      this.setSeconds = var10001;
      var10001 = ScriptFunction.createBuiltin("setUTCSeconds", "setUTCSeconds");
      var10001.setArity(2);
      this.setUTCSeconds = var10001;
      var10001 = ScriptFunction.createBuiltin("setMinutes", "setMinutes");
      var10001.setArity(3);
      this.setMinutes = var10001;
      var10001 = ScriptFunction.createBuiltin("setUTCMinutes", "setUTCMinutes");
      var10001.setArity(3);
      this.setUTCMinutes = var10001;
      var10001 = ScriptFunction.createBuiltin("setHours", "setHours");
      var10001.setArity(4);
      this.setHours = var10001;
      var10001 = ScriptFunction.createBuiltin("setUTCHours", "setUTCHours");
      var10001.setArity(4);
      this.setUTCHours = var10001;
      var10001 = ScriptFunction.createBuiltin("setDate", "setDate");
      var10001.setArity(1);
      this.setDate = var10001;
      var10001 = ScriptFunction.createBuiltin("setUTCDate", "setUTCDate");
      var10001.setArity(1);
      this.setUTCDate = var10001;
      var10001 = ScriptFunction.createBuiltin("setMonth", "setMonth");
      var10001.setArity(2);
      this.setMonth = var10001;
      var10001 = ScriptFunction.createBuiltin("setUTCMonth", "setUTCMonth");
      var10001.setArity(2);
      this.setUTCMonth = var10001;
      var10001 = ScriptFunction.createBuiltin("setFullYear", "setFullYear");
      var10001.setArity(3);
      this.setFullYear = var10001;
      var10001 = ScriptFunction.createBuiltin("setUTCFullYear", "setUTCFullYear");
      var10001.setArity(3);
      this.setUTCFullYear = var10001;
      this.setYear = ScriptFunction.createBuiltin("setYear", "setYear");
      this.toUTCString = ScriptFunction.createBuiltin("toUTCString", "toUTCString");
      this.toGMTString = ScriptFunction.createBuiltin("toGMTString", "toGMTString");
      this.toISOString = ScriptFunction.createBuiltin("toISOString", "toISOString");
      this.toJSON = ScriptFunction.createBuiltin("toJSON", "toJSON");
   }

   public String getClassName() {
      return "Date";
   }
}
