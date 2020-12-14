package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeArray$Prototype extends PrototypeObject {
   private Object toString = ScriptFunction.createBuiltin("toString", "toString");
   private Object assertNumeric = ScriptFunction.createBuiltin("assertNumeric", "assertNumeric");
   private Object toLocaleString = ScriptFunction.createBuiltin("toLocaleString", "toLocaleString");
   private Object concat;
   private Object join;
   private Object pop;
   private Object push;
   private Object reverse;
   private Object shift;
   private Object slice;
   private Object sort;
   private Object splice;
   private Object unshift;
   private Object indexOf;
   private Object lastIndexOf;
   private Object every;
   private Object some;
   private Object forEach;
   private Object map;
   private Object filter;
   private Object reduce;
   private Object reduceRight;
   private static final PropertyMap $nasgenmap$;

   public Object G$toString() {
      return this.toString;
   }

   public void S$toString(Object var1) {
      this.toString = var1;
   }

   public Object G$assertNumeric() {
      return this.assertNumeric;
   }

   public void S$assertNumeric(Object var1) {
      this.assertNumeric = var1;
   }

   public Object G$toLocaleString() {
      return this.toLocaleString;
   }

   public void S$toLocaleString(Object var1) {
      this.toLocaleString = var1;
   }

   public Object G$concat() {
      return this.concat;
   }

   public void S$concat(Object var1) {
      this.concat = var1;
   }

   public Object G$join() {
      return this.join;
   }

   public void S$join(Object var1) {
      this.join = var1;
   }

   public Object G$pop() {
      return this.pop;
   }

   public void S$pop(Object var1) {
      this.pop = var1;
   }

   public Object G$push() {
      return this.push;
   }

   public void S$push(Object var1) {
      this.push = var1;
   }

   public Object G$reverse() {
      return this.reverse;
   }

   public void S$reverse(Object var1) {
      this.reverse = var1;
   }

   public Object G$shift() {
      return this.shift;
   }

   public void S$shift(Object var1) {
      this.shift = var1;
   }

   public Object G$slice() {
      return this.slice;
   }

   public void S$slice(Object var1) {
      this.slice = var1;
   }

   public Object G$sort() {
      return this.sort;
   }

   public void S$sort(Object var1) {
      this.sort = var1;
   }

   public Object G$splice() {
      return this.splice;
   }

   public void S$splice(Object var1) {
      this.splice = var1;
   }

   public Object G$unshift() {
      return this.unshift;
   }

   public void S$unshift(Object var1) {
      this.unshift = var1;
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

   public Object G$every() {
      return this.every;
   }

   public void S$every(Object var1) {
      this.every = var1;
   }

   public Object G$some() {
      return this.some;
   }

   public void S$some(Object var1) {
      this.some = var1;
   }

   public Object G$forEach() {
      return this.forEach;
   }

   public void S$forEach(Object var1) {
      this.forEach = var1;
   }

   public Object G$map() {
      return this.map;
   }

   public void S$map(Object var1) {
      this.map = var1;
   }

   public Object G$filter() {
      return this.filter;
   }

   public void S$filter(Object var1) {
      this.filter = var1;
   }

   public Object G$reduce() {
      return this.reduce;
   }

   public void S$reduce(Object var1) {
      this.reduce = var1;
   }

   public Object G$reduceRight() {
      return this.reduceRight;
   }

   public void S$reduceRight(Object var1) {
      this.reduceRight = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(23);
      var10000.add(AccessorProperty.create("length", 6, "getProtoLength", "setProtoLength"));
      var10000.add(AccessorProperty.create("toString", 2, "G$toString", "S$toString"));
      var10000.add(AccessorProperty.create("assertNumeric", 2, "G$assertNumeric", "S$assertNumeric"));
      var10000.add(AccessorProperty.create("toLocaleString", 2, "G$toLocaleString", "S$toLocaleString"));
      var10000.add(AccessorProperty.create("concat", 2, "G$concat", "S$concat"));
      var10000.add(AccessorProperty.create("join", 2, "G$join", "S$join"));
      var10000.add(AccessorProperty.create("pop", 2, "G$pop", "S$pop"));
      var10000.add(AccessorProperty.create("push", 2, "G$push", "S$push"));
      var10000.add(AccessorProperty.create("reverse", 2, "G$reverse", "S$reverse"));
      var10000.add(AccessorProperty.create("shift", 2, "G$shift", "S$shift"));
      var10000.add(AccessorProperty.create("slice", 2, "G$slice", "S$slice"));
      var10000.add(AccessorProperty.create("sort", 2, "G$sort", "S$sort"));
      var10000.add(AccessorProperty.create("splice", 2, "G$splice", "S$splice"));
      var10000.add(AccessorProperty.create("unshift", 2, "G$unshift", "S$unshift"));
      var10000.add(AccessorProperty.create("indexOf", 2, "G$indexOf", "S$indexOf"));
      var10000.add(AccessorProperty.create("lastIndexOf", 2, "G$lastIndexOf", "S$lastIndexOf"));
      var10000.add(AccessorProperty.create("every", 2, "G$every", "S$every"));
      var10000.add(AccessorProperty.create("some", 2, "G$some", "S$some"));
      var10000.add(AccessorProperty.create("forEach", 2, "G$forEach", "S$forEach"));
      var10000.add(AccessorProperty.create("map", 2, "G$map", "S$map"));
      var10000.add(AccessorProperty.create("filter", 2, "G$filter", "S$filter"));
      var10000.add(AccessorProperty.create("reduce", 2, "G$reduce", "S$reduce"));
      var10000.add(AccessorProperty.create("reduceRight", 2, "G$reduceRight", "S$reduceRight"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeArray$Prototype() {
      super($nasgenmap$);
      ScriptFunction var10001 = ScriptFunction.createBuiltin("concat", "concat", new Specialization[]{new Specialization("concat", NativeArray.ConcatLinkLogic.class, false), new Specialization("concat", NativeArray.ConcatLinkLogic.class, false), new Specialization("concat", NativeArray.ConcatLinkLogic.class, false), new Specialization("concat", NativeArray.ConcatLinkLogic.class, false)});
      var10001.setArity(1);
      this.concat = var10001;
      this.join = ScriptFunction.createBuiltin("join", "join");
      this.pop = ScriptFunction.createBuiltin("pop", "pop", new Specialization[]{new Specialization("popInt", NativeArray.PopLinkLogic.class, false), new Specialization("popDouble", NativeArray.PopLinkLogic.class, false), new Specialization("popObject", NativeArray.PopLinkLogic.class, false)});
      var10001 = ScriptFunction.createBuiltin("push", "push", new Specialization[]{new Specialization("push", NativeArray.PushLinkLogic.class, false), new Specialization("push", NativeArray.PushLinkLogic.class, false), new Specialization("push", NativeArray.PushLinkLogic.class, false), new Specialization("pushObject", NativeArray.PushLinkLogic.class, false), new Specialization("push", false)});
      var10001.setArity(1);
      this.push = var10001;
      this.reverse = ScriptFunction.createBuiltin("reverse", "reverse");
      this.shift = ScriptFunction.createBuiltin("shift", "shift");
      this.slice = ScriptFunction.createBuiltin("slice", "slice");
      this.sort = ScriptFunction.createBuiltin("sort", "sort");
      var10001 = ScriptFunction.createBuiltin("splice", "splice");
      var10001.setArity(2);
      this.splice = var10001;
      var10001 = ScriptFunction.createBuiltin("unshift", "unshift");
      var10001.setArity(1);
      this.unshift = var10001;
      var10001 = ScriptFunction.createBuiltin("indexOf", "indexOf");
      var10001.setArity(1);
      this.indexOf = var10001;
      var10001 = ScriptFunction.createBuiltin("lastIndexOf", "lastIndexOf");
      var10001.setArity(1);
      this.lastIndexOf = var10001;
      var10001 = ScriptFunction.createBuiltin("every", "every");
      var10001.setArity(1);
      this.every = var10001;
      var10001 = ScriptFunction.createBuiltin("some", "some");
      var10001.setArity(1);
      this.some = var10001;
      var10001 = ScriptFunction.createBuiltin("forEach", "forEach");
      var10001.setArity(1);
      this.forEach = var10001;
      var10001 = ScriptFunction.createBuiltin("map", "map");
      var10001.setArity(1);
      this.map = var10001;
      var10001 = ScriptFunction.createBuiltin("filter", "filter");
      var10001.setArity(1);
      this.filter = var10001;
      var10001 = ScriptFunction.createBuiltin("reduce", "reduce");
      var10001.setArity(1);
      this.reduce = var10001;
      var10001 = ScriptFunction.createBuiltin("reduceRight", "reduceRight");
      var10001.setArity(1);
      this.reduceRight = var10001;
   }

   public String getClassName() {
      return "Array";
   }
}
