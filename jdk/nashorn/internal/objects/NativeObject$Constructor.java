package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Specialization;

final class NativeObject$Constructor extends ScriptFunction {
   private Object setIndexedPropertiesToExternalArrayData = ScriptFunction.createBuiltin("setIndexedPropertiesToExternalArrayData", "setIndexedPropertiesToExternalArrayData");
   private Object getPrototypeOf = ScriptFunction.createBuiltin("getPrototypeOf", "getPrototypeOf");
   private Object setPrototypeOf = ScriptFunction.createBuiltin("setPrototypeOf", "setPrototypeOf");
   private Object getOwnPropertyDescriptor = ScriptFunction.createBuiltin("getOwnPropertyDescriptor", "getOwnPropertyDescriptor");
   private Object getOwnPropertyNames = ScriptFunction.createBuiltin("getOwnPropertyNames", "getOwnPropertyNames");
   private Object create = ScriptFunction.createBuiltin("create", "create");
   private Object defineProperty = ScriptFunction.createBuiltin("defineProperty", "defineProperty");
   private Object defineProperties = ScriptFunction.createBuiltin("defineProperties", "defineProperties");
   private Object seal = ScriptFunction.createBuiltin("seal", "seal");
   private Object freeze = ScriptFunction.createBuiltin("freeze", "freeze");
   private Object preventExtensions = ScriptFunction.createBuiltin("preventExtensions", "preventExtensions");
   private Object isSealed = ScriptFunction.createBuiltin("isSealed", "isSealed");
   private Object isFrozen = ScriptFunction.createBuiltin("isFrozen", "isFrozen");
   private Object isExtensible = ScriptFunction.createBuiltin("isExtensible", "isExtensible");
   private Object keys = ScriptFunction.createBuiltin("keys", "keys");
   private Object bindProperties = ScriptFunction.createBuiltin("bindProperties", "bindProperties");
   private static final PropertyMap $nasgenmap$;

   public Object G$setIndexedPropertiesToExternalArrayData() {
      return this.setIndexedPropertiesToExternalArrayData;
   }

   public void S$setIndexedPropertiesToExternalArrayData(Object var1) {
      this.setIndexedPropertiesToExternalArrayData = var1;
   }

   public Object G$getPrototypeOf() {
      return this.getPrototypeOf;
   }

   public void S$getPrototypeOf(Object var1) {
      this.getPrototypeOf = var1;
   }

   public Object G$setPrototypeOf() {
      return this.setPrototypeOf;
   }

   public void S$setPrototypeOf(Object var1) {
      this.setPrototypeOf = var1;
   }

   public Object G$getOwnPropertyDescriptor() {
      return this.getOwnPropertyDescriptor;
   }

   public void S$getOwnPropertyDescriptor(Object var1) {
      this.getOwnPropertyDescriptor = var1;
   }

   public Object G$getOwnPropertyNames() {
      return this.getOwnPropertyNames;
   }

   public void S$getOwnPropertyNames(Object var1) {
      this.getOwnPropertyNames = var1;
   }

   public Object G$create() {
      return this.create;
   }

   public void S$create(Object var1) {
      this.create = var1;
   }

   public Object G$defineProperty() {
      return this.defineProperty;
   }

   public void S$defineProperty(Object var1) {
      this.defineProperty = var1;
   }

   public Object G$defineProperties() {
      return this.defineProperties;
   }

   public void S$defineProperties(Object var1) {
      this.defineProperties = var1;
   }

   public Object G$seal() {
      return this.seal;
   }

   public void S$seal(Object var1) {
      this.seal = var1;
   }

   public Object G$freeze() {
      return this.freeze;
   }

   public void S$freeze(Object var1) {
      this.freeze = var1;
   }

   public Object G$preventExtensions() {
      return this.preventExtensions;
   }

   public void S$preventExtensions(Object var1) {
      this.preventExtensions = var1;
   }

   public Object G$isSealed() {
      return this.isSealed;
   }

   public void S$isSealed(Object var1) {
      this.isSealed = var1;
   }

   public Object G$isFrozen() {
      return this.isFrozen;
   }

   public void S$isFrozen(Object var1) {
      this.isFrozen = var1;
   }

   public Object G$isExtensible() {
      return this.isExtensible;
   }

   public void S$isExtensible(Object var1) {
      this.isExtensible = var1;
   }

   public Object G$keys() {
      return this.keys;
   }

   public void S$keys(Object var1) {
      this.keys = var1;
   }

   public Object G$bindProperties() {
      return this.bindProperties;
   }

   public void S$bindProperties(Object var1) {
      this.bindProperties = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(16);
      var10000.add(AccessorProperty.create("setIndexedPropertiesToExternalArrayData", 2, "G$setIndexedPropertiesToExternalArrayData", "S$setIndexedPropertiesToExternalArrayData"));
      var10000.add(AccessorProperty.create("getPrototypeOf", 2, "G$getPrototypeOf", "S$getPrototypeOf"));
      var10000.add(AccessorProperty.create("setPrototypeOf", 2, "G$setPrototypeOf", "S$setPrototypeOf"));
      var10000.add(AccessorProperty.create("getOwnPropertyDescriptor", 2, "G$getOwnPropertyDescriptor", "S$getOwnPropertyDescriptor"));
      var10000.add(AccessorProperty.create("getOwnPropertyNames", 2, "G$getOwnPropertyNames", "S$getOwnPropertyNames"));
      var10000.add(AccessorProperty.create("create", 2, "G$create", "S$create"));
      var10000.add(AccessorProperty.create("defineProperty", 2, "G$defineProperty", "S$defineProperty"));
      var10000.add(AccessorProperty.create("defineProperties", 2, "G$defineProperties", "S$defineProperties"));
      var10000.add(AccessorProperty.create("seal", 2, "G$seal", "S$seal"));
      var10000.add(AccessorProperty.create("freeze", 2, "G$freeze", "S$freeze"));
      var10000.add(AccessorProperty.create("preventExtensions", 2, "G$preventExtensions", "S$preventExtensions"));
      var10000.add(AccessorProperty.create("isSealed", 2, "G$isSealed", "S$isSealed"));
      var10000.add(AccessorProperty.create("isFrozen", 2, "G$isFrozen", "S$isFrozen"));
      var10000.add(AccessorProperty.create("isExtensible", 2, "G$isExtensible", "S$isExtensible"));
      var10000.add(AccessorProperty.create("keys", 2, "G$keys", "S$keys"));
      var10000.add(AccessorProperty.create("bindProperties", 2, "G$bindProperties", "S$bindProperties"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeObject$Constructor() {
      super((String)"Object", (MethodHandle)"construct", (PropertyMap)$nasgenmap$, (Specialization[])null);
      NativeObject$Prototype var10001 = new NativeObject$Prototype();
      PrototypeObject.setConstructor(var10001, this);
      this.setPrototype(var10001);
   }
}
