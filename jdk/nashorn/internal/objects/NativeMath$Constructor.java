package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeMath$Constructor extends ScriptObject {
   private Object abs = ScriptFunction.createBuiltin("abs", "abs", new Specialization[]{new Specialization("abs", false), new Specialization("abs", false), new Specialization("abs", false)});
   private Object acos = ScriptFunction.createBuiltin("acos", "acos", new Specialization[]{new Specialization("acos", false)});
   private Object asin = ScriptFunction.createBuiltin("asin", "asin", new Specialization[]{new Specialization("asin", false)});
   private Object atan = ScriptFunction.createBuiltin("atan", "atan", new Specialization[]{new Specialization("atan", false)});
   private Object atan2 = ScriptFunction.createBuiltin("atan2", "atan2", new Specialization[]{new Specialization("atan2", false)});
   private Object ceil = ScriptFunction.createBuiltin("ceil", "ceil", new Specialization[]{new Specialization("ceil", false), new Specialization("ceil", false), new Specialization("ceil", false)});
   private Object cos = ScriptFunction.createBuiltin("cos", "cos", new Specialization[]{new Specialization("cos", false)});
   private Object exp = ScriptFunction.createBuiltin("exp", "exp");
   private Object floor = ScriptFunction.createBuiltin("floor", "floor", new Specialization[]{new Specialization("floor", false), new Specialization("floor", false), new Specialization("floor", false)});
   private Object log = ScriptFunction.createBuiltin("log", "log", new Specialization[]{new Specialization("log", false)});
   private Object max;
   private Object min;
   private Object pow;
   private Object random;
   private Object round;
   private Object sin;
   private Object sqrt;
   private Object tan;
   private static final PropertyMap $nasgenmap$;

   public double G$E() {
      return NativeMath.E;
   }

   public double G$LN10() {
      return NativeMath.LN10;
   }

   public double G$LN2() {
      return NativeMath.LN2;
   }

   public double G$LOG2E() {
      return NativeMath.LOG2E;
   }

   public double G$LOG10E() {
      return NativeMath.LOG10E;
   }

   public double G$PI() {
      return NativeMath.PI;
   }

   public double G$SQRT1_2() {
      return NativeMath.SQRT1_2;
   }

   public double G$SQRT2() {
      return NativeMath.SQRT2;
   }

   public Object G$abs() {
      return this.abs;
   }

   public void S$abs(Object var1) {
      this.abs = var1;
   }

   public Object G$acos() {
      return this.acos;
   }

   public void S$acos(Object var1) {
      this.acos = var1;
   }

   public Object G$asin() {
      return this.asin;
   }

   public void S$asin(Object var1) {
      this.asin = var1;
   }

   public Object G$atan() {
      return this.atan;
   }

   public void S$atan(Object var1) {
      this.atan = var1;
   }

   public Object G$atan2() {
      return this.atan2;
   }

   public void S$atan2(Object var1) {
      this.atan2 = var1;
   }

   public Object G$ceil() {
      return this.ceil;
   }

   public void S$ceil(Object var1) {
      this.ceil = var1;
   }

   public Object G$cos() {
      return this.cos;
   }

   public void S$cos(Object var1) {
      this.cos = var1;
   }

   public Object G$exp() {
      return this.exp;
   }

   public void S$exp(Object var1) {
      this.exp = var1;
   }

   public Object G$floor() {
      return this.floor;
   }

   public void S$floor(Object var1) {
      this.floor = var1;
   }

   public Object G$log() {
      return this.log;
   }

   public void S$log(Object var1) {
      this.log = var1;
   }

   public Object G$max() {
      return this.max;
   }

   public void S$max(Object var1) {
      this.max = var1;
   }

   public Object G$min() {
      return this.min;
   }

   public void S$min(Object var1) {
      this.min = var1;
   }

   public Object G$pow() {
      return this.pow;
   }

   public void S$pow(Object var1) {
      this.pow = var1;
   }

   public Object G$random() {
      return this.random;
   }

   public void S$random(Object var1) {
      this.random = var1;
   }

   public Object G$round() {
      return this.round;
   }

   public void S$round(Object var1) {
      this.round = var1;
   }

   public Object G$sin() {
      return this.sin;
   }

   public void S$sin(Object var1) {
      this.sin = var1;
   }

   public Object G$sqrt() {
      return this.sqrt;
   }

   public void S$sqrt(Object var1) {
      this.sqrt = var1;
   }

   public Object G$tan() {
      return this.tan;
   }

   public void S$tan(Object var1) {
      this.tan = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(26);
      var10000.add(AccessorProperty.create("E", 7, "G$E", (MethodHandle)null));
      var10000.add(AccessorProperty.create("LN10", 7, "G$LN10", (MethodHandle)null));
      var10000.add(AccessorProperty.create("LN2", 7, "G$LN2", (MethodHandle)null));
      var10000.add(AccessorProperty.create("LOG2E", 7, "G$LOG2E", (MethodHandle)null));
      var10000.add(AccessorProperty.create("LOG10E", 7, "G$LOG10E", (MethodHandle)null));
      var10000.add(AccessorProperty.create("PI", 7, "G$PI", (MethodHandle)null));
      var10000.add(AccessorProperty.create("SQRT1_2", 7, "G$SQRT1_2", (MethodHandle)null));
      var10000.add(AccessorProperty.create("SQRT2", 7, "G$SQRT2", (MethodHandle)null));
      var10000.add(AccessorProperty.create("abs", 2, "G$abs", "S$abs"));
      var10000.add(AccessorProperty.create("acos", 2, "G$acos", "S$acos"));
      var10000.add(AccessorProperty.create("asin", 2, "G$asin", "S$asin"));
      var10000.add(AccessorProperty.create("atan", 2, "G$atan", "S$atan"));
      var10000.add(AccessorProperty.create("atan2", 2, "G$atan2", "S$atan2"));
      var10000.add(AccessorProperty.create("ceil", 2, "G$ceil", "S$ceil"));
      var10000.add(AccessorProperty.create("cos", 2, "G$cos", "S$cos"));
      var10000.add(AccessorProperty.create("exp", 2, "G$exp", "S$exp"));
      var10000.add(AccessorProperty.create("floor", 2, "G$floor", "S$floor"));
      var10000.add(AccessorProperty.create("log", 2, "G$log", "S$log"));
      var10000.add(AccessorProperty.create("max", 2, "G$max", "S$max"));
      var10000.add(AccessorProperty.create("min", 2, "G$min", "S$min"));
      var10000.add(AccessorProperty.create("pow", 2, "G$pow", "S$pow"));
      var10000.add(AccessorProperty.create("random", 2, "G$random", "S$random"));
      var10000.add(AccessorProperty.create("round", 2, "G$round", "S$round"));
      var10000.add(AccessorProperty.create("sin", 2, "G$sin", "S$sin"));
      var10000.add(AccessorProperty.create("sqrt", 2, "G$sqrt", "S$sqrt"));
      var10000.add(AccessorProperty.create("tan", 2, "G$tan", "S$tan"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeMath$Constructor() {
      super($nasgenmap$);
      ScriptFunction var10001 = ScriptFunction.createBuiltin("max", "max", new Specialization[]{new Specialization("max", false), new Specialization("max", false), new Specialization("max", false), new Specialization("max", false), new Specialization("max", false)});
      var10001.setArity(2);
      this.max = var10001;
      var10001 = ScriptFunction.createBuiltin("min", "min", new Specialization[]{new Specialization("min", false), new Specialization("min", false), new Specialization("min", false), new Specialization("min", false), new Specialization("min", false)});
      var10001.setArity(2);
      this.min = var10001;
      this.pow = ScriptFunction.createBuiltin("pow", "pow", new Specialization[]{new Specialization("pow", false)});
      this.random = ScriptFunction.createBuiltin("random", "random");
      this.round = ScriptFunction.createBuiltin("round", "round");
      this.sin = ScriptFunction.createBuiltin("sin", "sin", new Specialization[]{new Specialization("sin", false)});
      this.sqrt = ScriptFunction.createBuiltin("sqrt", "sqrt", new Specialization[]{new Specialization("sqrt", false)});
      this.tan = ScriptFunction.createBuiltin("tan", "tan", new Specialization[]{new Specialization("tan", false)});
   }

   public String getClassName() {
      return "Math";
   }
}
