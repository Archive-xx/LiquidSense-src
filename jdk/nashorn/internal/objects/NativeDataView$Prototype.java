package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeDataView$Prototype extends PrototypeObject {
   private Object getInt8 = ScriptFunction.createBuiltin("getInt8", "getInt8", new Specialization[]{new Specialization("getInt8", false)});
   private Object getUint8 = ScriptFunction.createBuiltin("getUint8", "getUint8", new Specialization[]{new Specialization("getUint8", false)});
   private Object getInt16;
   private Object getUint16;
   private Object getInt32;
   private Object getUint32;
   private Object getFloat32;
   private Object getFloat64;
   private Object setInt8;
   private Object setUint8;
   private Object setInt16;
   private Object setUint16;
   private Object setInt32;
   private Object setUint32;
   private Object setFloat32;
   private Object setFloat64;
   private static final PropertyMap $nasgenmap$;

   public Object G$getInt8() {
      return this.getInt8;
   }

   public void S$getInt8(Object var1) {
      this.getInt8 = var1;
   }

   public Object G$getUint8() {
      return this.getUint8;
   }

   public void S$getUint8(Object var1) {
      this.getUint8 = var1;
   }

   public Object G$getInt16() {
      return this.getInt16;
   }

   public void S$getInt16(Object var1) {
      this.getInt16 = var1;
   }

   public Object G$getUint16() {
      return this.getUint16;
   }

   public void S$getUint16(Object var1) {
      this.getUint16 = var1;
   }

   public Object G$getInt32() {
      return this.getInt32;
   }

   public void S$getInt32(Object var1) {
      this.getInt32 = var1;
   }

   public Object G$getUint32() {
      return this.getUint32;
   }

   public void S$getUint32(Object var1) {
      this.getUint32 = var1;
   }

   public Object G$getFloat32() {
      return this.getFloat32;
   }

   public void S$getFloat32(Object var1) {
      this.getFloat32 = var1;
   }

   public Object G$getFloat64() {
      return this.getFloat64;
   }

   public void S$getFloat64(Object var1) {
      this.getFloat64 = var1;
   }

   public Object G$setInt8() {
      return this.setInt8;
   }

   public void S$setInt8(Object var1) {
      this.setInt8 = var1;
   }

   public Object G$setUint8() {
      return this.setUint8;
   }

   public void S$setUint8(Object var1) {
      this.setUint8 = var1;
   }

   public Object G$setInt16() {
      return this.setInt16;
   }

   public void S$setInt16(Object var1) {
      this.setInt16 = var1;
   }

   public Object G$setUint16() {
      return this.setUint16;
   }

   public void S$setUint16(Object var1) {
      this.setUint16 = var1;
   }

   public Object G$setInt32() {
      return this.setInt32;
   }

   public void S$setInt32(Object var1) {
      this.setInt32 = var1;
   }

   public Object G$setUint32() {
      return this.setUint32;
   }

   public void S$setUint32(Object var1) {
      this.setUint32 = var1;
   }

   public Object G$setFloat32() {
      return this.setFloat32;
   }

   public void S$setFloat32(Object var1) {
      this.setFloat32 = var1;
   }

   public Object G$setFloat64() {
      return this.setFloat64;
   }

   public void S$setFloat64(Object var1) {
      this.setFloat64 = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(16);
      var10000.add(AccessorProperty.create("getInt8", 2, "G$getInt8", "S$getInt8"));
      var10000.add(AccessorProperty.create("getUint8", 2, "G$getUint8", "S$getUint8"));
      var10000.add(AccessorProperty.create("getInt16", 2, "G$getInt16", "S$getInt16"));
      var10000.add(AccessorProperty.create("getUint16", 2, "G$getUint16", "S$getUint16"));
      var10000.add(AccessorProperty.create("getInt32", 2, "G$getInt32", "S$getInt32"));
      var10000.add(AccessorProperty.create("getUint32", 2, "G$getUint32", "S$getUint32"));
      var10000.add(AccessorProperty.create("getFloat32", 2, "G$getFloat32", "S$getFloat32"));
      var10000.add(AccessorProperty.create("getFloat64", 2, "G$getFloat64", "S$getFloat64"));
      var10000.add(AccessorProperty.create("setInt8", 2, "G$setInt8", "S$setInt8"));
      var10000.add(AccessorProperty.create("setUint8", 2, "G$setUint8", "S$setUint8"));
      var10000.add(AccessorProperty.create("setInt16", 2, "G$setInt16", "S$setInt16"));
      var10000.add(AccessorProperty.create("setUint16", 2, "G$setUint16", "S$setUint16"));
      var10000.add(AccessorProperty.create("setInt32", 2, "G$setInt32", "S$setInt32"));
      var10000.add(AccessorProperty.create("setUint32", 2, "G$setUint32", "S$setUint32"));
      var10000.add(AccessorProperty.create("setFloat32", 2, "G$setFloat32", "S$setFloat32"));
      var10000.add(AccessorProperty.create("setFloat64", 2, "G$setFloat64", "S$setFloat64"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeDataView$Prototype() {
      super($nasgenmap$);
      ScriptFunction var10001 = ScriptFunction.createBuiltin("getInt16", "getInt16", new Specialization[]{new Specialization("getInt16", false), new Specialization("getInt16", false)});
      var10001.setArity(1);
      this.getInt16 = var10001;
      var10001 = ScriptFunction.createBuiltin("getUint16", "getUint16", new Specialization[]{new Specialization("getUint16", false), new Specialization("getUint16", false)});
      var10001.setArity(1);
      this.getUint16 = var10001;
      var10001 = ScriptFunction.createBuiltin("getInt32", "getInt32", new Specialization[]{new Specialization("getInt32", false), new Specialization("getInt32", false)});
      var10001.setArity(1);
      this.getInt32 = var10001;
      var10001 = ScriptFunction.createBuiltin("getUint32", "getUint32", new Specialization[]{new Specialization("getUint32", false), new Specialization("getUint32", false)});
      var10001.setArity(1);
      this.getUint32 = var10001;
      var10001 = ScriptFunction.createBuiltin("getFloat32", "getFloat32", new Specialization[]{new Specialization("getFloat32", false), new Specialization("getFloat32", false)});
      var10001.setArity(1);
      this.getFloat32 = var10001;
      var10001 = ScriptFunction.createBuiltin("getFloat64", "getFloat64", new Specialization[]{new Specialization("getFloat64", false), new Specialization("getFloat64", false)});
      var10001.setArity(1);
      this.getFloat64 = var10001;
      var10001 = ScriptFunction.createBuiltin("setInt8", "setInt8", new Specialization[]{new Specialization("setInt8", false)});
      var10001.setArity(2);
      this.setInt8 = var10001;
      var10001 = ScriptFunction.createBuiltin("setUint8", "setUint8", new Specialization[]{new Specialization("setUint8", false)});
      var10001.setArity(2);
      this.setUint8 = var10001;
      var10001 = ScriptFunction.createBuiltin("setInt16", "setInt16", new Specialization[]{new Specialization("setInt16", false), new Specialization("setInt16", false)});
      var10001.setArity(2);
      this.setInt16 = var10001;
      var10001 = ScriptFunction.createBuiltin("setUint16", "setUint16", new Specialization[]{new Specialization("setUint16", false), new Specialization("setUint16", false)});
      var10001.setArity(2);
      this.setUint16 = var10001;
      var10001 = ScriptFunction.createBuiltin("setInt32", "setInt32", new Specialization[]{new Specialization("setInt32", false), new Specialization("setInt32", false)});
      var10001.setArity(2);
      this.setInt32 = var10001;
      var10001 = ScriptFunction.createBuiltin("setUint32", "setUint32", new Specialization[]{new Specialization("setUint32", false), new Specialization("setUint32", false)});
      var10001.setArity(2);
      this.setUint32 = var10001;
      var10001 = ScriptFunction.createBuiltin("setFloat32", "setFloat32", new Specialization[]{new Specialization("setFloat32", false), new Specialization("setFloat32", false)});
      var10001.setArity(2);
      this.setFloat32 = var10001;
      var10001 = ScriptFunction.createBuiltin("setFloat64", "setFloat64", new Specialization[]{new Specialization("setFloat64", false), new Specialization("setFloat64", false)});
      var10001.setArity(2);
      this.setFloat64 = var10001;
   }

   public String getClassName() {
      return "DataView";
   }
}
