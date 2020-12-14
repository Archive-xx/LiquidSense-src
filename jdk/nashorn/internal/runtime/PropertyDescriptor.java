package jdk.nashorn.internal.runtime;

public interface PropertyDescriptor {
   int GENERIC = 0;
   int DATA = 1;
   int ACCESSOR = 2;
   String CONFIGURABLE = "configurable";
   String ENUMERABLE = "enumerable";
   String WRITABLE = "writable";
   String VALUE = "value";
   String GET = "get";
   String SET = "set";

   boolean isConfigurable();

   boolean isEnumerable();

   boolean isWritable();

   Object getValue();

   ScriptFunction getGetter();

   ScriptFunction getSetter();

   void setConfigurable(boolean var1);

   void setEnumerable(boolean var1);

   void setWritable(boolean var1);

   void setValue(Object var1);

   void setGetter(Object var1);

   void setSetter(Object var1);

   PropertyDescriptor fillFrom(ScriptObject var1);

   int type();

   boolean has(Object var1);

   boolean hasAndEquals(PropertyDescriptor var1);
}
