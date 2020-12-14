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

public final class GenericPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
   public Object configurable;
   public Object enumerable;
   private static PropertyMap $nasgenmap$;

   GenericPropertyDescriptor(boolean configurable, boolean enumerable, Global global) {
      super(global.getObjectPrototype(), $nasgenmap$);
      this.configurable = configurable;
      this.enumerable = enumerable;
   }

   public boolean isConfigurable() {
      return JSType.toBoolean(this.configurable);
   }

   public boolean isEnumerable() {
      return JSType.toBoolean(this.enumerable);
   }

   public boolean isWritable() {
      return false;
   }

   public Object getValue() {
      throw new UnsupportedOperationException("value");
   }

   public ScriptFunction getGetter() {
      throw new UnsupportedOperationException("get");
   }

   public ScriptFunction getSetter() {
      throw new UnsupportedOperationException("set");
   }

   public void setConfigurable(boolean flag) {
      this.configurable = flag;
   }

   public void setEnumerable(boolean flag) {
      this.enumerable = flag;
   }

   public void setWritable(boolean flag) {
      throw new UnsupportedOperationException("set writable");
   }

   public void setValue(Object value) {
      throw new UnsupportedOperationException("set value");
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

      return this;
   }

   public int type() {
      return 0;
   }

   public boolean hasAndEquals(PropertyDescriptor other) {
      if (this.has("configurable") && other.has("configurable") && this.isConfigurable() != other.isConfigurable()) {
         return false;
      } else {
         return !this.has("enumerable") || !other.has("enumerable") || this.isEnumerable() == other.isEnumerable();
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof GenericPropertyDescriptor)) {
         return false;
      } else {
         GenericPropertyDescriptor other = (GenericPropertyDescriptor)obj;
         return ScriptRuntime.sameValue(this.configurable, other.configurable) && ScriptRuntime.sameValue(this.enumerable, other.enumerable);
      }
   }

   public String toString() {
      return '[' + this.getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + "}]";
   }

   public int hashCode() {
      int hash = 7;
      int hash = 97 * hash + Objects.hashCode(this.configurable);
      hash = 97 * hash + Objects.hashCode(this.enumerable);
      return hash;
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(2);
      var10000.add(AccessorProperty.create("configurable", 0, "G$configurable", "S$configurable"));
      var10000.add(AccessorProperty.create("enumerable", 0, "G$enumerable", "S$enumerable"));
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
}
