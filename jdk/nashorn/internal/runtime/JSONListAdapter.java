package jdk.nashorn.internal.runtime;

import java.util.Collection;
import java.util.Set;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.Global;

public final class JSONListAdapter extends ListAdapter implements JSObject {
   public JSONListAdapter(JSObject obj, Global global) {
      super(obj, global);
   }

   public Object unwrap(Object homeGlobal) {
      Object unwrapped = ScriptObjectMirror.unwrap(this.obj, homeGlobal);
      return unwrapped != this.obj ? unwrapped : this;
   }

   public Object call(Object thiz, Object... args) {
      return this.obj.call(thiz, args);
   }

   public Object newObject(Object... args) {
      return this.obj.newObject(args);
   }

   public Object eval(String s) {
      return this.obj.eval(s);
   }

   public Object getMember(String name) {
      return this.obj.getMember(name);
   }

   public Object getSlot(int index) {
      return this.obj.getSlot(index);
   }

   public boolean hasMember(String name) {
      return this.obj.hasMember(name);
   }

   public boolean hasSlot(int slot) {
      return this.obj.hasSlot(slot);
   }

   public void removeMember(String name) {
      this.obj.removeMember(name);
   }

   public void setMember(String name, Object value) {
      this.obj.setMember(name, value);
   }

   public void setSlot(int index, Object value) {
      this.obj.setSlot(index, value);
   }

   public Set<String> keySet() {
      return this.obj.keySet();
   }

   public Collection<Object> values() {
      return this.obj.values();
   }

   public boolean isInstance(Object instance) {
      return this.obj.isInstance(instance);
   }

   public boolean isInstanceOf(Object clazz) {
      return this.obj.isInstanceOf(clazz);
   }

   public String getClassName() {
      return this.obj.getClassName();
   }

   public boolean isFunction() {
      return this.obj.isFunction();
   }

   public boolean isStrictFunction() {
      return this.obj.isStrictFunction();
   }

   public boolean isArray() {
      return this.obj.isArray();
   }

   /** @deprecated */
   @Deprecated
   public double toNumber() {
      return this.obj.toNumber();
   }

   public Object getDefaultValue(Class<?> hint) {
      return AbstractJSObject.getDefaultValue(this.obj, hint);
   }
}
