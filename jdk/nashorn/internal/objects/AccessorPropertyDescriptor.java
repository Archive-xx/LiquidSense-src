package jdk.nashorn.internal.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

public final class AccessorPropertyDescriptor extends ScriptObject implements PropertyDescriptor {
   public Object configurable;
   public Object enumerable;
   public Object get;
   public Object set;
   private static PropertyMap $nasgenmap$;

   AccessorPropertyDescriptor(boolean configurable, boolean enumerable, Object get, Object set, Global global) {
      super(global.getObjectPrototype(), $nasgenmap$);
      this.configurable = configurable;
      this.enumerable = enumerable;
      this.get = get;
      this.set = set;
   }

   public boolean isConfigurable() {
      return JSType.toBoolean(this.configurable);
   }

   public boolean isEnumerable() {
      return JSType.toBoolean(this.enumerable);
   }

   public boolean isWritable() {
      return true;
   }

   public Object getValue() {
      throw new UnsupportedOperationException("value");
   }

   public ScriptFunction getGetter() {
      return this.get instanceof ScriptFunction ? (ScriptFunction)this.get : null;
   }

   public ScriptFunction getSetter() {
      return this.set instanceof ScriptFunction ? (ScriptFunction)this.set : null;
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
      this.get = getter;
   }

   public void setSetter(Object setter) {
      this.set = setter;
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

      Object setter;
      if (sobj.has("get")) {
         setter = sobj.get("get");
         if (setter != ScriptRuntime.UNDEFINED && !(setter instanceof ScriptFunction)) {
            throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(setter));
         }

         this.get = setter;
      } else {
         this.delete("get", false);
      }

      if (sobj.has("set")) {
         setter = sobj.get("set");
         if (setter != ScriptRuntime.UNDEFINED && !(setter instanceof ScriptFunction)) {
            throw ECMAErrors.typeError("not.a.function", ScriptRuntime.safeToString(setter));
         }

         this.set = setter;
      } else {
         this.delete("set", false);
      }

      return this;
   }

   public int type() {
      return 2;
   }

   public boolean hasAndEquals(PropertyDescriptor otherDesc) {
      if (!(otherDesc instanceof AccessorPropertyDescriptor)) {
         return false;
      } else {
         AccessorPropertyDescriptor other = (AccessorPropertyDescriptor)otherDesc;
         return (!this.has("configurable") || ScriptRuntime.sameValue(this.configurable, other.configurable)) && (!this.has("enumerable") || ScriptRuntime.sameValue(this.enumerable, other.enumerable)) && (!this.has("get") || ScriptRuntime.sameValue(this.get, other.get)) && (!this.has("set") || ScriptRuntime.sameValue(this.set, other.set));
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof AccessorPropertyDescriptor)) {
         return false;
      } else {
         AccessorPropertyDescriptor other = (AccessorPropertyDescriptor)obj;
         return ScriptRuntime.sameValue(this.configurable, other.configurable) && ScriptRuntime.sameValue(this.enumerable, other.enumerable) && ScriptRuntime.sameValue(this.get, other.get) && ScriptRuntime.sameValue(this.set, other.set);
      }
   }

   public String toString() {
      return '[' + this.getClass().getSimpleName() + " {configurable=" + this.configurable + " enumerable=" + this.enumerable + " getter=" + this.get + " setter=" + this.set + "}]";
   }

   public int hashCode() {
      int hash = 7;
      int hash = 41 * hash + Objects.hashCode(this.configurable);
      hash = 41 * hash + Objects.hashCode(this.enumerable);
      hash = 41 * hash + Objects.hashCode(this.get);
      hash = 41 * hash + Objects.hashCode(this.set);
      return hash;
   }

   static {
      $clinit$();
   }

   public static void $clinit$() {
      ArrayList var10000 = new ArrayList(4);
      var10000.add(AccessorProperty.create("configurable", 0, "G$configurable", "S$configurable"));
      var10000.add(AccessorProperty.create("enumerable", 0, "G$enumerable", "S$enumerable"));
      var10000.add(AccessorProperty.create("get", 0, "G$get", "S$get"));
      var10000.add(AccessorProperty.create("set", 0, "G$set", "S$set"));
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

   public Object G$get() {
      return this.get;
   }

   public void S$get(Object var1) {
      this.get = var1;
   }

   public Object G$set() {
      return this.set;
   }

   public void S$set(Object var1) {
      this.set = var1;
   }
}
