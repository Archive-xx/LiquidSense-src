package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeString$Prototype extends PrototypeObject {
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private Object valueOf = ScriptFunction.createBuiltin("valueOf", "valueOf");
   private Object charAt = ScriptFunction.createBuiltin("charAt", "charAt", new Specialization[]{new Specialization("charAt", false), new Specialization("charAt", false)});
   private Object charCodeAt = ScriptFunction.createBuiltin("charCodeAt", "charCodeAt", new Specialization[]{new Specialization("charCodeAt", NativeString.CharCodeAtLinkLogic.class, false), new Specialization("charCodeAt", NativeString.CharCodeAtLinkLogic.class, false), new Specialization("charCodeAt", NativeString.CharCodeAtLinkLogic.class, false)});
   private Object concat;
   private Object indexOf;
   private Object lastIndexOf;
   private Object localeCompare;
   private Object match;
   private Object replace;
   private Object search;
   private Object slice;
   private Object split;
   private Object substr;
   private Object substring;
   private Object toLowerCase;
   private Object toLocaleLowerCase;
   private Object toUpperCase;
   private Object toLocaleUpperCase;
   private Object trim;
   private Object trimLeft;
   private Object trimRight;
   private static final PropertyMap $nasgenmap$;

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   public Object G$valueOf() {
      return this.valueOf;
   }

   public void S$valueOf(Object var1) {
      this.valueOf = var1;
   }

   public Object G$charAt() {
      return this.charAt;
   }

   public void S$charAt(Object var1) {
      this.charAt = var1;
   }

   public Object G$charCodeAt() {
      return this.charCodeAt;
   }

   public void S$charCodeAt(Object var1) {
      this.charCodeAt = var1;
   }

   public Object G$concat() {
      return this.concat;
   }

   public void S$concat(Object var1) {
      this.concat = var1;
   }

   public Object G$indexOf() {
      return this.indexOf;
   }

   public void S$indexOf(Object var1) {
      this.indexOf = var1;
   }

   public Object G$lastIndexOf() {
      return this.lastIndexOf;
   }

   public void S$lastIndexOf(Object var1) {
      this.lastIndexOf = var1;
   }

   public Object G$localeCompare() {
      return this.localeCompare;
   }

   public void S$localeCompare(Object var1) {
      this.localeCompare = var1;
   }

   public Object G$match() {
      return this.match;
   }

   public void S$match(Object var1) {
      this.match = var1;
   }

   public Object G$replace() {
      return this.replace;
   }

   public void S$replace(Object var1) {
      this.replace = var1;
   }

   public Object G$search() {
      return this.search;
   }

   public void S$search(Object var1) {
      this.search = var1;
   }

   public Object G$slice() {
      return this.slice;
   }

   public void S$slice(Object var1) {
      this.slice = var1;
   }

   public Object G$split() {
      return this.split;
   }

   public void S$split(Object var1) {
      this.split = var1;
   }

   public Object G$substr() {
      return this.substr;
   }

   public void S$substr(Object var1) {
      this.substr = var1;
   }

   public Object G$substring() {
      return this.substring;
   }

   public void S$substring(Object var1) {
      this.substring = var1;
   }

   public Object G$toLowerCase() {
      return this.toLowerCase;
   }

   public void S$toLowerCase(Object var1) {
      this.toLowerCase = var1;
   }

   public Object G$toLocaleLowerCase() {
      return this.toLocaleLowerCase;
   }

   public void S$toLocaleLowerCase(Object var1) {
      this.toLocaleLowerCase = var1;
   }

   public Object G$toUpperCase() {
      return this.toUpperCase;
   }

   public void S$toUpperCase(Object var1) {
      this.toUpperCase = var1;
   }

   public Object G$toLocaleUpperCase() {
      return this.toLocaleUpperCase;
   }

   public void S$toLocaleUpperCase(Object var1) {
      this.toLocaleUpperCase = var1;
   }

   public Object G$trim() {
      return this.trim;
   }

   public void S$trim(Object var1) {
      this.trim = var1;
   }

   public Object G$trimLeft() {
      return this.trimLeft;
   }

   public void S$trimLeft(Object var1) {
      this.trimLeft = var1;
   }

   public Object G$trimRight() {
      return this.trimRight;
   }

   public void S$trimRight(Object var1) {
      this.trimRight = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(22);
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      var10000.add(AccessorProperty.create("valueOf", 2, "G$valueOf", "S$valueOf"));
      var10000.add(AccessorProperty.create("charAt", 2, "G$charAt", "S$charAt"));
      var10000.add(AccessorProperty.create("charCodeAt", 2, "G$charCodeAt", "S$charCodeAt"));
      var10000.add(AccessorProperty.create("concat", 2, "G$concat", "S$concat"));
      var10000.add(AccessorProperty.create("indexOf", 2, "G$indexOf", "S$indexOf"));
      var10000.add(AccessorProperty.create("lastIndexOf", 2, "G$lastIndexOf", "S$lastIndexOf"));
      var10000.add(AccessorProperty.create("localeCompare", 2, "G$localeCompare", "S$localeCompare"));
      var10000.add(AccessorProperty.create("match", 2, "G$match", "S$match"));
      var10000.add(AccessorProperty.create("replace", 2, "G$replace", "S$replace"));
      var10000.add(AccessorProperty.create("search", 2, "G$search", "S$search"));
      var10000.add(AccessorProperty.create("slice", 2, "G$slice", "S$slice"));
      var10000.add(AccessorProperty.create("split", 2, "G$split", "S$split"));
      var10000.add(AccessorProperty.create("substr", 2, "G$substr", "S$substr"));
      var10000.add(AccessorProperty.create("substring", 2, "G$substring", "S$substring"));
      var10000.add(AccessorProperty.create("toLowerCase", 2, "G$toLowerCase", "S$toLowerCase"));
      var10000.add(AccessorProperty.create("toLocaleLowerCase", 2, "G$toLocaleLowerCase", "S$toLocaleLowerCase"));
      var10000.add(AccessorProperty.create("toUpperCase", 2, "G$toUpperCase", "S$toUpperCase"));
      var10000.add(AccessorProperty.create("toLocaleUpperCase", 2, "G$toLocaleUpperCase", "S$toLocaleUpperCase"));
      var10000.add(AccessorProperty.create("trim", 2, "G$trim", "S$trim"));
      var10000.add(AccessorProperty.create("trimLeft", 2, "G$trimLeft", "S$trimLeft"));
      var10000.add(AccessorProperty.create("trimRight", 2, "G$trimRight", "S$trimRight"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeString$Prototype() {
      super($nasgenmap$);
      ScriptFunction var10001 = ScriptFunction.createBuiltin("concat", "concat");
      var10001.setArity(1);
      this.concat = var10001;
      var10001 = ScriptFunction.createBuiltin("indexOf", "indexOf", new Specialization[]{new Specialization("indexOf", false), new Specialization("indexOf", false), new Specialization("indexOf", false)});
      var10001.setArity(1);
      this.indexOf = var10001;
      var10001 = ScriptFunction.createBuiltin("lastIndexOf", "lastIndexOf");
      var10001.setArity(1);
      this.lastIndexOf = var10001;
      this.localeCompare = ScriptFunction.createBuiltin("localeCompare", "localeCompare");
      this.match = ScriptFunction.createBuiltin("match", "match");
      this.replace = ScriptFunction.createBuiltin("replace", "replace");
      this.search = ScriptFunction.createBuiltin("search", "search");
      this.slice = ScriptFunction.createBuiltin("slice", "slice", new Specialization[]{new Specialization("slice", false), new Specialization("slice", false), new Specialization("slice", false), new Specialization("slice", false)});
      this.split = ScriptFunction.createBuiltin("split", "split");
      this.substr = ScriptFunction.createBuiltin("substr", "substr");
      this.substring = ScriptFunction.createBuiltin("substring", "substring", new Specialization[]{new Specialization("substring", false), new Specialization("substring", false), new Specialization("substring", false), new Specialization("substring", false)});
      this.toLowerCase = ScriptFunction.createBuiltin("toLowerCase", "toLowerCase");
      this.toLocaleLowerCase = ScriptFunction.createBuiltin("toLocaleLowerCase", "toLocaleLowerCase");
      this.toUpperCase = ScriptFunction.createBuiltin("toUpperCase", "toUpperCase");
      this.toLocaleUpperCase = ScriptFunction.createBuiltin("toLocaleUpperCase", "toLocaleUpperCase");
      this.trim = ScriptFunction.createBuiltin("trim", "trim");
      this.trimLeft = ScriptFunction.createBuiltin("trimLeft", "trimLeft");
      this.trimRight = ScriptFunction.createBuiltin("trimRight", "trimRight");
   }

   public String getClassName() {
      return "String";
   }
}
