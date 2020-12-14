package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;

final class NativeDebug$Constructor extends ScriptObject {
   private Object getArrayDataClass = ScriptFunction.createBuiltin("getArrayDataClass", "getArrayDataClass");
   private Object getArrayData = ScriptFunction.createBuiltin("getArrayData", "getArrayData");
   private Object getContext = ScriptFunction.createBuiltin("getContext", "getContext");
   private Object map = ScriptFunction.createBuiltin("map", "map");
   private Object identical = ScriptFunction.createBuiltin("identical", "identical");
   private Object equalWithoutType = ScriptFunction.createBuiltin("equalWithoutType", "equalWithoutType");
   private Object diffPropertyMaps = ScriptFunction.createBuiltin("diffPropertyMaps", "diffPropertyMaps");
   private Object getClass = ScriptFunction.createBuiltin("getClass", "getClass");
   private Object equals = ScriptFunction.createBuiltin("equals", "equals");
   private Object toJavaString = ScriptFunction.createBuiltin("toJavaString", "toJavaString");
   private Object toIdentString = ScriptFunction.createBuiltin("toIdentString", "toIdentString");
   private Object getListenerCount = ScriptFunction.createBuiltin("getListenerCount", "getListenerCount");
   private Object dumpCounters = ScriptFunction.createBuiltin("dumpCounters", "dumpCounters");
   private Object getEventQueueCapacity = ScriptFunction.createBuiltin("getEventQueueCapacity", "getEventQueueCapacity");
   private Object setEventQueueCapacity = ScriptFunction.createBuiltin("setEventQueueCapacity", "setEventQueueCapacity");
   private Object addRuntimeEvent = ScriptFunction.createBuiltin("addRuntimeEvent", "addRuntimeEvent");
   private Object expandEventQueueCapacity = ScriptFunction.createBuiltin("expandEventQueueCapacity", "expandEventQueueCapacity");
   private Object clearRuntimeEvents = ScriptFunction.createBuiltin("clearRuntimeEvents", "clearRuntimeEvents");
   private Object removeRuntimeEvent = ScriptFunction.createBuiltin("removeRuntimeEvent", "removeRuntimeEvent");
   private Object getRuntimeEvents = ScriptFunction.createBuiltin("getRuntimeEvents", "getRuntimeEvents");
   private Object getLastRuntimeEvent = ScriptFunction.createBuiltin("getLastRuntimeEvent", "getLastRuntimeEvent");
   private static final PropertyMap $nasgenmap$;

   public Object G$getArrayDataClass() {
      return this.getArrayDataClass;
   }

   public void S$getArrayDataClass(Object var1) {
      this.getArrayDataClass = var1;
   }

   public Object G$getArrayData() {
      return this.getArrayData;
   }

   public void S$getArrayData(Object var1) {
      this.getArrayData = var1;
   }

   public Object G$getContext() {
      return this.getContext;
   }

   public void S$getContext(Object var1) {
      this.getContext = var1;
   }

   public Object G$map() {
      return this.map;
   }

   public void S$map(Object var1) {
      this.map = var1;
   }

   public Object G$identical() {
      return this.identical;
   }

   public void S$identical(Object var1) {
      this.identical = var1;
   }

   public Object G$equalWithoutType() {
      return this.equalWithoutType;
   }

   public void S$equalWithoutType(Object var1) {
      this.equalWithoutType = var1;
   }

   public Object G$diffPropertyMaps() {
      return this.diffPropertyMaps;
   }

   public void S$diffPropertyMaps(Object var1) {
      this.diffPropertyMaps = var1;
   }

   public Object G$getClass() {
      return this.getClass;
   }

   public void S$getClass(Object var1) {
      this.getClass = var1;
   }

   public Object G$equals() {
      return this.equals;
   }

   public void S$equals(Object var1) {
      this.equals = var1;
   }

   public Object G$toJavaString() {
      return this.toJavaString;
   }

   public void S$toJavaString(Object var1) {
      this.toJavaString = var1;
   }

   public Object G$toIdentString() {
      return this.toIdentString;
   }

   public void S$toIdentString(Object var1) {
      this.toIdentString = var1;
   }

   public Object G$getListenerCount() {
      return this.getListenerCount;
   }

   public void S$getListenerCount(Object var1) {
      this.getListenerCount = var1;
   }

   public Object G$dumpCounters() {
      return this.dumpCounters;
   }

   public void S$dumpCounters(Object var1) {
      this.dumpCounters = var1;
   }

   public Object G$getEventQueueCapacity() {
      return this.getEventQueueCapacity;
   }

   public void S$getEventQueueCapacity(Object var1) {
      this.getEventQueueCapacity = var1;
   }

   public Object G$setEventQueueCapacity() {
      return this.setEventQueueCapacity;
   }

   public void S$setEventQueueCapacity(Object var1) {
      this.setEventQueueCapacity = var1;
   }

   public Object G$addRuntimeEvent() {
      return this.addRuntimeEvent;
   }

   public void S$addRuntimeEvent(Object var1) {
      this.addRuntimeEvent = var1;
   }

   public Object G$expandEventQueueCapacity() {
      return this.expandEventQueueCapacity;
   }

   public void S$expandEventQueueCapacity(Object var1) {
      this.expandEventQueueCapacity = var1;
   }

   public Object G$clearRuntimeEvents() {
      return this.clearRuntimeEvents;
   }

   public void S$clearRuntimeEvents(Object var1) {
      this.clearRuntimeEvents = var1;
   }

   public Object G$removeRuntimeEvent() {
      return this.removeRuntimeEvent;
   }

   public void S$removeRuntimeEvent(Object var1) {
      this.removeRuntimeEvent = var1;
   }

   public Object G$getRuntimeEvents() {
      return this.getRuntimeEvents;
   }

   public void S$getRuntimeEvents(Object var1) {
      this.getRuntimeEvents = var1;
   }

   public Object G$getLastRuntimeEvent() {
      return this.getLastRuntimeEvent;
   }

   public void S$getLastRuntimeEvent(Object var1) {
      this.getLastRuntimeEvent = var1;
   }

   static {
      ArrayList var10000 = new ArrayList(21);
      var10000.add(AccessorProperty.create("getArrayDataClass", 2, "G$getArrayDataClass", "S$getArrayDataClass"));
      var10000.add(AccessorProperty.create("getArrayData", 2, "G$getArrayData", "S$getArrayData"));
      var10000.add(AccessorProperty.create("getContext", 2, "G$getContext", "S$getContext"));
      var10000.add(AccessorProperty.create("map", 2, "G$map", "S$map"));
      var10000.add(AccessorProperty.create("identical", 2, "G$identical", "S$identical"));
      var10000.add(AccessorProperty.create("equalWithoutType", 2, "G$equalWithoutType", "S$equalWithoutType"));
      var10000.add(AccessorProperty.create("diffPropertyMaps", 2, "G$diffPropertyMaps", "S$diffPropertyMaps"));
      var10000.add(AccessorProperty.create("getClass", 2, "G$getClass", "S$getClass"));
      var10000.add(AccessorProperty.create("equals", 2, "G$equals", "S$equals"));
      var10000.add(AccessorProperty.create("toJavaString", 2, "G$toJavaString", "S$toJavaString"));
      var10000.add(AccessorProperty.create("toIdentString", 2, "G$toIdentString", "S$toIdentString"));
      var10000.add(AccessorProperty.create("getListenerCount", 2, "G$getListenerCount", "S$getListenerCount"));
      var10000.add(AccessorProperty.create("dumpCounters", 2, "G$dumpCounters", "S$dumpCounters"));
      var10000.add(AccessorProperty.create("getEventQueueCapacity", 2, "G$getEventQueueCapacity", "S$getEventQueueCapacity"));
      var10000.add(AccessorProperty.create("setEventQueueCapacity", 2, "G$setEventQueueCapacity", "S$setEventQueueCapacity"));
      var10000.add(AccessorProperty.create("addRuntimeEvent", 2, "G$addRuntimeEvent", "S$addRuntimeEvent"));
      var10000.add(AccessorProperty.create("expandEventQueueCapacity", 2, "G$expandEventQueueCapacity", "S$expandEventQueueCapacity"));
      var10000.add(AccessorProperty.create("clearRuntimeEvents", 2, "G$clearRuntimeEvents", "S$clearRuntimeEvents"));
      var10000.add(AccessorProperty.create("removeRuntimeEvent", 2, "G$removeRuntimeEvent", "S$removeRuntimeEvent"));
      var10000.add(AccessorProperty.create("getRuntimeEvents", 2, "G$getRuntimeEvents", "S$getRuntimeEvents"));
      var10000.add(AccessorProperty.create("getLastRuntimeEvent", 2, "G$getLastRuntimeEvent", "S$getLastRuntimeEvent"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   NativeDebug$Constructor() {
      super($nasgenmap$);
   }

   public String getClassName() {
      return "Debug";
   }
}
