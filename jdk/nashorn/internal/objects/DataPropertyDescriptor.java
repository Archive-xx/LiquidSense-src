package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

public final class DataPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
   public Object configurable;
   public Object enumerable;
   public Object writable;
   public Object value;
   private static PropertyMap $nasgenmap$;

   DataPropertyDescriptor(boolean configurable, boolean enumerable, boolean writable, Object value, Global global) {
      super(global.getObjectPrototype(), $nasgenmap$);
      this.configurable = configurable;
      this.enumerable = enumerable;
      this.writable = writable;
      this.value = value;
   }

   public boolean isConfigurable() {
      return JSType.toBoolean(this.configurable);
   }

   public boolean isEnumerable() {
      return JSType.toBoolean(this.enumerable);
   }

   public boolean isWritable() {
      return JSType.toBoolean(this.writable);
   }

   public Object getValue() {
      return this.value;
   }

   public ScriptFunction getGetter() {
      throw new UnsupportedOperationException("getter");
   }

   public ScriptFunction getSetter() {
      throw new UnsupportedOperationException("setter");
   }

   public void setConfigurable(boolean flag) {
      this.configurable = flag;
   }

   public void setEnumerable(boolean flag) {
      this.enumerable = flag;
   }

   public void setWritable(boolean flag) {
      this.writable = flag;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public void setGetter(Object getter) {
      throw new UnsupportedOperationException("set getter");
   }

   public void setSetter(Object setter) {
      throw new UnsupportedOperationException("set setter");
   }

   public PropertyDescriptor fillFrom(ScriptObject sobj) {
      if (sobj.has("configurable")) {
         this.configurable = JSType.toBoolean(sobj.get("configurable"));
      } else {
         this.delete("configurable", false);
      }

      if (sobj.has("enumerable")) {
         this.enumerable = JSType.toBoolean(sobj.get("enumerable"));
      } else {
         this.delete("enumerable", false);
      }

      if (sobj.has("writable")) {
         this.writable = JSType.toBoolean(sobj.get("writable"));
      } else {
         this.delete("writable", false);
      }

      if (sobj.has("value")) {
         this.value = sobj.get("value");
      } else {
         this.delete("value", false);
      }

      return this;
   }

   public int type() {
      return 1;
   }

   public boolean hasAndEquals(PropertyDescriptor otherDesc) {
      if (!(otherDesc instanceof DataPropertyDescriptor)) {
         return false;
      } else {
         DataPropertyDescriptor other = (DataPropertyDescriptor)otherDesc;
         return (!this.has("configurable") || ScriptRuntime.sameValue(this.configurable, other.configurable)) && (!this.has("enumerable") || ScriptRuntime.sameValue(this.enumerable, other.enumerable)) && (!this.has("writable") || ScriptRuntime.sameValue(this.writable, other.writable)) && (!this.has("value") || ScriptRuntime.sameValue(this.value, other.value));
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof DataPropertyDescriptor)) {
         return false;
      } else {
         DataPropertyDescriptor other = (DataPropertyDescriptor)obj;
         return ScriptRuntime.sameValue(this.configurable, other.configurable) && ScriptRuntime.sameValue(this.enumerable, other.enumerable) && ScriptRuntime.sameValue(this.writable, other.writable) && ScriptRuntime.sameValue(this.value, other.value);
      }
   }

   public String toString() {
      return '[' + this.getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + " writable=" + this.writable + " value=" + this.value + "}]";
   }

   public int hashCode() {
      int hash = 5;
      int hash = 43 * hash + Objects.hashCode(this.configurable);
      hash = 43 * hash + Objects.hashCode(this.enumerable);
      hash = 43 * hash + Objects.hashCode(this.writable);
      hash = 43 * hash + Objects.hashCode(this.value);
      return hash;
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(4);
      var10000.add(AccessorProperty.create("configurable", 0, "G$configurable", "S$configurable"));
      var10000.add(AccessorProperty.create("enumerable", 0, "G$enumerable", "S$enumerable"));
      var10000.add(AccessorProperty.create("writable", 0, "G$writable", "S$writable"));
      var10000.add(AccessorProperty.create("value", 0, "G$value", "S$value"));
      $nasgenmap$ = PropertyMap.newMap((Collection)var10000);
   }

   public Object G$configurable() {
      return this.configurable;
   }

   public void S$configurable(Object var1) {
      this.configurable = var1;
   }

   public Object G$enumerable() {
      return this.enumerable;
   }

   public void S$enumerable(Object var1) {
      this.enumerable = var1;
   }

   public Object G$writable() {
      return this.writable;
   }

   public void S$writable(Object var1) {
      this.writable = var1;
   }

   public Object G$value() {
      return this.value;
   }

   public void S$value(Object var1) {
      this.value = var1;
   }
}
