package jdk.nashorn.api.scripting;

import java.util.Collection;
import java.util.Set;
import jdk.Exported;

@Exported
public interface JSObject {
   Object call(Object var1, Object... var2);

   Object newObject(Object... var1);

   Object eval(String var1);

   Object getMember(String var1);

   Object getSlot(int var1);

   boolean hasMember(String var1);

   boolean hasSlot(int var1);

   void removeMember(String var1);

   void setMember(String var1, Object var2);

   void setSlot(int var1, Object var2);

   Set<String> keySet();

   Collection<Object> values();

   boolean isInstance(Object var1);

   boolean isInstanceOf(Object var1);

   String getClassName();

   boolean isFunction();

   boolean isStrictFunction();

   boolean isArray();

   /** @deprecated */
   @Deprecated
   double toNumber();
}
