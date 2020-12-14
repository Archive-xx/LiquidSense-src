package jdk.nashorn.api.scripting;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import jdk.Exported;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import jdk.nashorn.internal.runtime.JSType;

@Exported
public abstract class AbstractJSObject implements JSObject {
   public Object call(Object thiz, Object... args) {
      throw new UnsupportedOperationException("call");
   }

   public Object newObject(Object... args) {
      throw new UnsupportedOperationException("newObject");
   }

   public Object eval(String s) {
      throw new UnsupportedOperationException("eval");
   }

   public Object getMember(String name) {
      Objects.requireNonNull(name);
      return null;
   }

   public Object getSlot(int index) {
      return null;
   }

   public boolean hasMember(String name) {
      Objects.requireNonNull(name);
      return false;
   }

   public boolean hasSlot(int slot) {
      return false;
   }

   public void removeMember(String name) {
      Objects.requireNonNull(name);
   }

   public void setMember(String name, Object value) {
      Objects.requireNonNull(name);
   }

   public void setSlot(int index, Object value) {
   }

   public Set<String> keySet() {
      return Collections.emptySet();
   }

   public Collection<Object> values() {
      return Collections.emptySet();
   }

   public boolean isInstance(Object instance) {
      return false;
   }

   public boolean isInstanceOf(Object clazz) {
      return clazz instanceof JSObject ? ((JSObject)clazz).isInstance(this) : false;
   }

   public String getClassName() {
      return this.getClass().getName();
   }

   public boolean isFunction() {
      return false;
   }

   public boolean isStrictFunction() {
      return false;
   }

   public boolean isArray() {
      return false;
   }

   /** @deprecated */
   @Deprecated
   public double toNumber() {
      return JSType.toNumber(JSType.toPrimitive((JSObject)this, Number.class));
   }

   public Object getDefaultValue(Class<?> hint) {
      return DefaultValueImpl.getDefaultValue(this, hint);
   }

   public static Object getDefaultValue(JSObject jsobj, Class<?> hint) {
      if (jsobj instanceof AbstractJSObject) {
         return ((AbstractJSObject)jsobj).getDefaultValue(hint);
      } else {
         return jsobj instanceof JSONListAdapter ? ((JSONListAdapter)jsobj).getDefaultValue(hint) : DefaultValueImpl.getDefaultValue(jsobj, hint);
      }
   }
}
