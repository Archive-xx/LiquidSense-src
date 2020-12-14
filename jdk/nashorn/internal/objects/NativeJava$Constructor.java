package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

final class NativeJava$Constructor extends ScriptObject {
   private Object isType = ScriptFunction.createBuiltin("isType", "isType");
   private Object synchronizedFunc = ScriptFunction.createBuiltin("synchronized", "synchronizedFunc");
   private Object isJavaMethod = ScriptFunction.createBuiltin("isJavaMethod", "isJavaMethod");
   private Object isJavaFunction = ScriptFunction.createBuiltin("isJavaFunction", "isJavaFunction");
   private Object isJavaObject = ScriptFunction.createBuiltin("isJavaObject", "isJavaObject");
   private Object isScriptObject = ScriptFunction.createBuiltin("isScriptObject", "isScriptObject");
   private Object isScriptFunction = ScriptFunction.createBuiltin("isScriptFunction", "isScriptFunction");
   private Object type = ScriptFunction.createBuiltin("type", "type");
   private Object typeName = ScriptFunction.createBuiltin("typeName", "typeName");
   private Object to = ScriptFunction.createBuiltin("to", "to");
   private Object from = ScriptFunction.createBuiltin("from", "from");
   private Object extend = ScriptFunction.createBuiltin("extend", "extend");
   private Object _super = ScriptFunction.createBuiltin("super", "_super");
   private Object asJSONCompatible = ScriptFunction.createBuiltin("asJSONCompatible", "asJSONCompatible");
   private static final PropertyMap $nasgenmap$;

   public Object G$isType() {
      return this.isType;
   }

   public void S$isType(Object var1) {
      this.isType = var1;
   }

   public Object G$synchronizedFunc() {
      return this.synchronizedFunc;
   }

   public void S$synchronizedFunc(Object var1) {
      this.synchronizedFunc = var1;
   }

   public Object G$isJavaMethod() {
      return this.isJavaMethod;
   }

   public void S$isJavaMethod(Object var1) {
      this.isJavaMethod = var1;
   }

   public Object G$isJavaFunction() {
      return this.isJavaFunction;
   }

   public void S$isJavaFunction(Object var1) {
      this.isJavaFunction = var1;
   }

   public Object G$isJavaObject() {
      return this.isJavaObject;
   }

   public void S$isJavaObject(Object var1) {
      this.isJavaObject = var1;
   }

   public Object G$isScriptObject() {
      return this.isScriptObject;
   }

   public void S$isScriptObject(Object var1) {
      this.isScriptObject = var1;
   }

   public Object G$isScriptFunction() {
      return this.isScriptFunction;
   }

   public void S$isScriptFunction(Object var1) {
      this.isScriptFunction = var1;
   }

   public Object G$type() {
      return this.type;
   }

   public void S$type(Object var1) {
      this.type = var1;
   }

   public Object G$typeName() {
      return this.typeName;
   }

   public void S$typeName(Object var1) {
      this.typeName = var1;
   }

   public Object G$to() {
      return this.to;
   }

   public void S$to(Object var1) {
      this.to = var1;
   }

   public Object G$from() {
      return this.from;
   }

   public void S$from(Object var1) {
      this.from = var1;
   }

   public Object G$extend() {
      return this.extend;
   }

   public void S$extend(Object var1) {
      this.extend = var1;
   }

   public Object G$_super() {
      return this._super;
   }

   public void S$_super(Object var1) {
      this._super = var1;
   }

   public Object G$asJSONCompatible() {
      return this.asJSONCompatible;
   }

   public void S$asJSONCompatible(Object var1) {
      this.asJSONCompatible = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(14);
      var10000.add(AccessorProperty.create("isType", 2, "G$isType", "S$isType"));
      var10000.add(AccessorProperty.create("synchronized", 2, "G$synchronizedFunc", "S$synchronizedFunc"));
      var10000.add(AccessorProperty.create("isJavaMethod", 2, "G$isJavaMethod", "S$isJavaMethod"));
      var10000.add(AccessorProperty.create("isJavaFunction", 2, "G$isJavaFunction", "S$isJavaFunction"));
      var10000.add(AccessorProperty.create("isJavaObject", 2, "G$isJavaObject", "S$isJavaObject"));
      var10000.add(AccessorProperty.create("isScriptObject", 2, "G$isScriptObject", "S$isScriptObject"));
      var10000.add(AccessorProperty.create("isScriptFunction", 2, "G$isScriptFunction", "S$isScriptFunction"));
      var10000.add(AccessorProperty.create("type", 2, "G$type", "S$type"));
      var10000.add(AccessorProperty.create("typeName", 2, "G$typeName", "S$typeName"));
      var10000.add(AccessorProperty.create("to", 2, "G$to", "S$to"));
      var10000.add(AccessorProperty.create("from", 2, "G$from", "S$from"));
      var10000.add(AccessorProperty.create("extend", 2, "G$extend", "S$extend"));
      var10000.add(AccessorProperty.create("super", 2, "G$_super", "S$_super"));
      var10000.add(AccessorProperty.create("asJSONCompatible", 2, "G$asJSONCompatible", "S$asJSONCompatible"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeJava$Constructor() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Java";
   }
}
