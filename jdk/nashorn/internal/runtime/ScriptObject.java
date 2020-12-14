package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.concurrent.atomic.LongAdder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.linker.NashornGuards;

public abstract class ScriptObject implements PropertyAccess, Cloneable {
   public static final String PROTO_PROPERTY_NAME = "__proto__";
   public static final String NO_SUCH_METHOD_NAME = "__noSuchMethod__";
   public static final String NO_SUCH_PROPERTY_NAME = "__noSuchProperty__";
   public static final int IS_ARRAY = 1;
   public static final int IS_ARGUMENTS = 2;
   public static final int IS_LENGTH_NOT_WRITABLE = 4;
   public static final int IS_BUILTIN = 8;
   public static final int SPILL_RATE = 8;
   private PropertyMap map;
   private ScriptObject proto;
   private int flags;
   protected long[] primitiveSpill;
   protected Object[] objectSpill;
   private ArrayData arrayData;
   public static final MethodHandle GETPROTO = findOwnMH_V("getProto", ScriptObject.class);
   static final MethodHandle MEGAMORPHIC_GET;
   static final MethodHandle GLOBALFILTER;
   static final MethodHandle DECLARE_AND_SET;
   private static final MethodHandle TRUNCATINGFILTER;
   private static final MethodHandle KNOWNFUNCPROPGUARDSELF;
   private static final MethodHandle KNOWNFUNCPROPGUARDPROTO;
   private static final ArrayList<MethodHandle> PROTO_FILTERS;
   public static final CompilerConstants.Call GET_ARRAY;
   public static final CompilerConstants.Call GET_ARGUMENT;
   public static final CompilerConstants.Call SET_ARGUMENT;
   public static final CompilerConstants.Call GET_PROTO;
   public static final CompilerConstants.Call GET_PROTO_DEPTH;
   public static final CompilerConstants.Call SET_GLOBAL_OBJECT_PROTO;
   public static final CompilerConstants.Call SET_PROTO_FROM_LITERAL;
   public static final CompilerConstants.Call SET_USER_ACCESSORS;
   static final MethodHandle[] SET_SLOW;
   public static final CompilerConstants.Call SET_MAP;
   static final MethodHandle CAS_MAP;
   static final MethodHandle EXTENSION_CHECK;
   static final MethodHandle ENSURE_SPILL_SIZE;
   private static LongAdder count;

   public ScriptObject() {
      this((PropertyMap)null);
   }

   public ScriptObject(PropertyMap map) {
      if (Context.DEBUG) {
         count.increment();
      }

      this.arrayData = ArrayData.EMPTY_ARRAY;
      this.setMap(map == null ? PropertyMap.newMap() : map);
   }

   protected ScriptObject(ScriptObject proto, PropertyMap map) {
      this(map);
      this.proto = proto;
   }

   public ScriptObject(PropertyMap map, long[] primitiveSpill, Object[] objectSpill) {
      this(map);
      this.primitiveSpill = primitiveSpill;
      this.objectSpill = objectSpill;

      assert primitiveSpill == null || primitiveSpill.length == objectSpill.length : " primitive spill pool size is not the same length as object spill pool size";

   }

   protected boolean isGlobal() {
      return false;
   }

   private static int alignUp(int size, int alignment) {
      return size + alignment - 1 & ~(alignment - 1);
   }

   public static int spillAllocationLength(int nProperties) {
      return alignUp(nProperties, 8);
   }

   public void addBoundProperties(ScriptObject source) {
      this.addBoundProperties(source, source.getMap().getProperties());
   }

   public void addBoundProperties(ScriptObject source, Property[] properties) {
      PropertyMap newMap = this.getMap();
      boolean extensible = newMap.isExtensible();
      Property[] var5 = properties;
      int var6 = properties.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Property property = var5[var7];
         newMap = this.addBoundProperty(newMap, source, property, extensible);
      }

      this.setMap(newMap);
   }

   protected PropertyMap addBoundProperty(PropertyMap propMap, ScriptObject source, Property property, boolean extensible) {
      PropertyMap newMap = propMap;
      String key = property.getKey();
      Property oldProp = propMap.findProperty(key);
      if (oldProp == null) {
         if (!extensible) {
            throw ECMAErrors.typeError("object.non.extensible", key, ScriptRuntime.safeToString(this));
         }

         if (property instanceof UserAccessorProperty) {
            UserAccessorProperty prop = this.newUserAccessors(key, property.getFlags(), property.getGetterFunction(source), property.getSetterFunction(source));
            newMap = propMap.addPropertyNoHistory(prop);
         } else {
            newMap = propMap.addPropertyBind((AccessorProperty)property, source);
         }
      } else if (property.isFunctionDeclaration() && !oldProp.isConfigurable() && (oldProp instanceof UserAccessorProperty || !oldProp.isWritable() || !oldProp.isEnumerable())) {
         throw ECMAErrors.typeError("cant.redefine.property", key, ScriptRuntime.safeToString(this));
      }

      return newMap;
   }

   public void addBoundProperties(Object source, AccessorProperty[] properties) {
      PropertyMap newMap = this.getMap();
      boolean extensible = newMap.isExtensible();
      AccessorProperty[] var5 = properties;
      int var6 = properties.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         AccessorProperty property = var5[var7];
         String key = property.getKey();
         if (newMap.findProperty(key) == null) {
            if (!extensible) {
               throw ECMAErrors.typeError("object.non.extensible", key, ScriptRuntime.safeToString(this));
            }

            newMap = newMap.addPropertyBind(property, source);
         }
      }

      this.setMap(newMap);
   }

   static MethodHandle bindTo(MethodHandle methodHandle, Object receiver) {
      return Lookup.MH.dropArguments(Lookup.MH.bindTo(methodHandle, receiver), 0, (Class[])(methodHandle.type().parameterType(0)));
   }

   public Iterator<String> propertyIterator() {
      return new ScriptObject.KeyIterator(this);
   }

   public Iterator<Object> valueIterator() {
      return new ScriptObject.ValueIterator(this);
   }

   public final boolean isAccessorDescriptor() {
      return this.has("get") || this.has("set");
   }

   public final boolean isDataDescriptor() {
      return this.has("value") || this.has("writable");
   }

   public final PropertyDescriptor toPropertyDescriptor() {
      Global global = Context.getGlobal();
      PropertyDescriptor desc;
      if (this.isDataDescriptor()) {
         if (this.has("set") || this.has("get")) {
            throw ECMAErrors.typeError(global, "inconsistent.property.descriptor");
         }

         desc = global.newDataDescriptor(ScriptRuntime.UNDEFINED, false, false, false);
      } else if (this.isAccessorDescriptor()) {
         if (this.has("value") || this.has("writable")) {
            throw ECMAErrors.typeError(global, "inconsistent.property.descriptor");
         }

         desc = global.newAccessorDescriptor(ScriptRuntime.UNDEFINED, ScriptRuntime.UNDEFINED, false, false);
      } else {
         desc = global.newGenericDescriptor(false, false);
      }

      return desc.fillFrom(this);
   }

   public static PropertyDescriptor toPropertyDescriptor(Global global, Object obj) {
      if (obj instanceof ScriptObject) {
         return ((ScriptObject)obj).toPropertyDescriptor();
      } else {
         throw ECMAErrors.typeError(global, "not.an.object", ScriptRuntime.safeToString(obj));
      }
   }

   public Object getOwnPropertyDescriptor(String key) {
      Property property = this.getMap().findProperty(key);
      Global global = Context.getGlobal();
      if (property != null) {
         ScriptFunction get = property.getGetterFunction(this);
         ScriptFunction set = property.getSetterFunction(this);
         boolean configurable = property.isConfigurable();
         boolean enumerable = property.isEnumerable();
         boolean writable = property.isWritable();
         return property instanceof UserAccessorProperty ? global.newAccessorDescriptor(get != null ? get : ScriptRuntime.UNDEFINED, set != null ? set : ScriptRuntime.UNDEFINED, configurable, enumerable) : global.newDataDescriptor(this.getWithProperty(property), configurable, enumerable, writable);
      } else {
         int index = ArrayIndex.getArrayIndex(key);
         ArrayData array = this.getArray();
         return array.has(index) ? array.getDescriptor(global, index) : ScriptRuntime.UNDEFINED;
      }
   }

   public Object getPropertyDescriptor(String key) {
      Object res = this.getOwnPropertyDescriptor(key);
      if (res != ScriptRuntime.UNDEFINED) {
         return res;
      } else {
         return this.getProto() != null ? this.getProto().getOwnPropertyDescriptor(key) : ScriptRuntime.UNDEFINED;
      }
   }

   protected void invalidateGlobalConstant(String key) {
      GlobalConstants globalConstants = this.getGlobalConstants();
      if (globalConstants != null) {
         globalConstants.delete(key);
      }

   }

   public boolean defineOwnProperty(String key, Object propertyDesc, boolean reject) {
      Global global = Context.getGlobal();
      PropertyDescriptor desc = toPropertyDescriptor(global, propertyDesc);
      Object current = this.getOwnPropertyDescriptor(key);
      String name = JSType.toString(key);
      this.invalidateGlobalConstant(key);
      if (current == ScriptRuntime.UNDEFINED) {
         if (this.isExtensible()) {
            this.addOwnProperty(key, desc);
            return true;
         } else if (reject) {
            throw ECMAErrors.typeError(global, "object.non.extensible", name, ScriptRuntime.safeToString(this));
         } else {
            return false;
         }
      } else {
         PropertyDescriptor currentDesc = (PropertyDescriptor)current;
         if (desc.type() == 0 && !desc.has("configurable") && !desc.has("enumerable")) {
            return true;
         } else if (desc.hasAndEquals(currentDesc)) {
            return true;
         } else {
            if (!currentDesc.isConfigurable()) {
               if (desc.has("configurable") && desc.isConfigurable()) {
                  if (reject) {
                     throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                  }

                  return false;
               }

               if (desc.has("enumerable") && currentDesc.isEnumerable() != desc.isEnumerable()) {
                  if (reject) {
                     throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                  }

                  return false;
               }
            }

            int propFlags = Property.mergeFlags(currentDesc, desc);
            Property property = this.getMap().findProperty(key);
            boolean value;
            if (currentDesc.type() != 1 || desc.type() != 1 && desc.type() != 0) {
               if (currentDesc.type() == 2 && (desc.type() == 2 || desc.type() == 0)) {
                  if (!currentDesc.isConfigurable() && (desc.has("get") && !ScriptRuntime.sameValue(currentDesc.getGetter(), desc.getGetter()) || desc.has("set") && !ScriptRuntime.sameValue(currentDesc.getSetter(), desc.getSetter()))) {
                     if (reject) {
                        throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                     }

                     return false;
                  }

                  this.modifyOwnProperty(property, propFlags, desc.has("get") ? desc.getGetter() : currentDesc.getGetter(), desc.has("set") ? desc.getSetter() : currentDesc.getSetter());
               } else {
                  if (!currentDesc.isConfigurable()) {
                     if (reject) {
                        throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                     }

                     return false;
                  }

                  propFlags = 0;
                  value = desc.has("configurable") ? desc.isConfigurable() : currentDesc.isConfigurable();
                  if (!value) {
                     propFlags |= 4;
                  }

                  value = desc.has("enumerable") ? desc.isEnumerable() : currentDesc.isEnumerable();
                  if (!value) {
                     propFlags |= 2;
                  }

                  int type = desc.type();
                  if (type == 1) {
                     value = desc.has("writable") && desc.isWritable();
                     if (!value) {
                        propFlags |= 1;
                     }

                     this.deleteOwnProperty(property);
                     this.addOwnProperty(key, propFlags, desc.getValue());
                  } else if (type == 2) {
                     if (property == null) {
                        this.addOwnProperty(key, propFlags, desc.has("get") ? desc.getGetter() : null, desc.has("set") ? desc.getSetter() : null);
                     } else {
                        this.modifyOwnProperty(property, propFlags, desc.has("get") ? desc.getGetter() : null, desc.has("set") ? desc.getSetter() : null);
                     }
                  }
               }
            } else {
               if (!currentDesc.isConfigurable() && !currentDesc.isWritable() && (desc.has("writable") && desc.isWritable() || desc.has("value") && !ScriptRuntime.sameValue(currentDesc.getValue(), desc.getValue()))) {
                  if (reject) {
                     throw ECMAErrors.typeError(global, "cant.redefine.property", name, ScriptRuntime.safeToString(this));
                  } else {
                     return false;
                  }
               }

               value = desc.has("value");
               Object value = value ? desc.getValue() : currentDesc.getValue();
               if (value && property != null) {
                  this.modifyOwnProperty(property, 0);
                  this.set(key, value, 0);
                  property = this.getMap().findProperty(key);
               }

               if (property == null) {
                  this.addOwnProperty(key, propFlags, value);
                  this.checkIntegerKey(key);
               } else {
                  this.modifyOwnProperty(property, propFlags);
               }
            }

            this.checkIntegerKey(key);
            return true;
         }
      }
   }

   public void defineOwnProperty(int index, Object value) {
      assert ArrayIndex.isValidArrayIndex(index) : "invalid array index";

      long longIndex = ArrayIndex.toLongIndex(index);
      long oldLength = this.getArray().length();
      if (longIndex >= oldLength) {
         this.setArray(this.getArray().ensure(longIndex).safeDelete(oldLength, longIndex - 1L, false));
      }

      this.setArray(this.getArray().set(index, value, false));
   }

   private void checkIntegerKey(String key) {
      int index = ArrayIndex.getArrayIndex(key);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.delete(index));
         }
      }

   }

   public final void addOwnProperty(String key, PropertyDescriptor propertyDesc) {
      PropertyDescriptor pdesc = propertyDesc;
      int propFlags = Property.toFlags(propertyDesc);
      if (propertyDesc.type() == 0) {
         Global global = Context.getGlobal();
         PropertyDescriptor dDesc = global.newDataDescriptor(ScriptRuntime.UNDEFINED, false, false, false);
         dDesc.fillFrom((ScriptObject)propertyDesc);
         pdesc = dDesc;
      }

      int type = pdesc.type();
      if (type == 1) {
         this.addOwnProperty(key, propFlags, pdesc.getValue());
      } else if (type == 2) {
         this.addOwnProperty(key, propFlags, pdesc.has("get") ? pdesc.getGetter() : null, pdesc.has("set") ? pdesc.getSetter() : null);
      }

      this.checkIntegerKey(key);
   }

   public final FindProperty findProperty(String key, boolean deep) {
      return this.findProperty(key, deep, this);
   }

   protected FindProperty findProperty(String key, boolean deep, ScriptObject start) {
      PropertyMap selfMap = this.getMap();
      Property property = selfMap.findProperty(key);
      if (property != null) {
         return new FindProperty(start, this, property);
      } else if (deep) {
         ScriptObject myProto = this.getProto();
         FindProperty find = myProto == null ? null : myProto.findProperty(key, true, start);
         this.checkSharedProtoMap();
         return find;
      } else {
         return null;
      }
   }

   boolean hasProperty(String key, boolean deep) {
      if (this.getMap().findProperty(key) != null) {
         return true;
      } else {
         if (deep) {
            ScriptObject myProto = this.getProto();
            if (myProto != null) {
               return myProto.hasProperty(key, true);
            }
         }

         return false;
      }
   }

   private SwitchPoint findBuiltinSwitchPoint(String key) {
      for(ScriptObject myProto = this.getProto(); myProto != null; myProto = myProto.getProto()) {
         Property prop = myProto.getMap().findProperty(key);
         if (prop != null) {
            SwitchPoint sp = prop.getBuiltinSwitchPoint();
            if (sp != null && !sp.hasBeenInvalidated()) {
               return sp;
            }
         }
      }

      return null;
   }

   public final Property addOwnProperty(String key, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
      return this.addOwnProperty(this.newUserAccessors(key, propertyFlags, getter, setter));
   }

   public final Property addOwnProperty(String key, int propertyFlags, Object value) {
      return this.addSpillProperty(key, propertyFlags, value, true);
   }

   public final Property addOwnProperty(Property newProperty) {
      PropertyMap oldMap = this.getMap();

      Property oldProperty;
      do {
         PropertyMap newMap = oldMap.addProperty(newProperty);
         if (this.compareAndSetMap(oldMap, newMap)) {
            return newProperty;
         }

         oldMap = this.getMap();
         oldProperty = oldMap.findProperty(newProperty.getKey());
      } while(oldProperty == null);

      return oldProperty;
   }

   private void erasePropertyValue(Property property) {
      if (!(property instanceof UserAccessorProperty)) {
         assert property != null;

         property.setValue(this, this, ScriptRuntime.UNDEFINED, false);
      }

   }

   public final boolean deleteOwnProperty(Property property) {
      this.erasePropertyValue(property);
      PropertyMap oldMap = this.getMap();

      while(true) {
         PropertyMap newMap = oldMap.deleteProperty(property);
         if (newMap == null) {
            return false;
         }

         if (this.compareAndSetMap(oldMap, newMap)) {
            if (property instanceof UserAccessorProperty) {
               ((UserAccessorProperty)property).setAccessors(this, this.getMap(), (UserAccessorProperty.Accessors)null);
            }

            this.invalidateGlobalConstant(property.getKey());
            return true;
         }

         oldMap = this.getMap();
      }
   }

   protected final void initUserAccessors(String key, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
      PropertyMap oldMap = this.getMap();
      int slot = oldMap.getFreeSpillSlot();
      this.ensureSpillSize(slot);
      this.objectSpill[slot] = new UserAccessorProperty.Accessors(getter, setter);

      PropertyMap newMap;
      do {
         Property newProperty = new UserAccessorProperty(key, propertyFlags, slot);
         newMap = oldMap.addProperty(newProperty);
      } while(!this.compareAndSetMap(oldMap, newMap));

   }

   public final Property modifyOwnProperty(Property oldProperty, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
      UserAccessorProperty newProperty;
      if (oldProperty instanceof UserAccessorProperty) {
         UserAccessorProperty uc = (UserAccessorProperty)oldProperty;
         int slot = uc.getSlot();

         assert uc.getLocalType() == Object.class;

         UserAccessorProperty.Accessors gs = uc.getAccessors(this);

         assert gs != null;

         gs.set(getter, setter);
         if (uc.getFlags() == propertyFlags) {
            return oldProperty;
         }

         newProperty = new UserAccessorProperty(uc.getKey(), propertyFlags, slot);
      } else {
         this.erasePropertyValue(oldProperty);
         newProperty = this.newUserAccessors(oldProperty.getKey(), propertyFlags, getter, setter);
      }

      return this.modifyOwnProperty(oldProperty, newProperty);
   }

   public final Property modifyOwnProperty(Property oldProperty, int propertyFlags) {
      return this.modifyOwnProperty(oldProperty, oldProperty.setFlags(propertyFlags));
   }

   private Property modifyOwnProperty(Property oldProperty, Property newProperty) {
      if (oldProperty == newProperty) {
         return newProperty;
      } else {
         assert newProperty.getKey().equals(oldProperty.getKey()) : "replacing property with different key";

         PropertyMap oldMap = this.getMap();

         Property oldPropertyLookup;
         do {
            PropertyMap newMap = oldMap.replaceProperty(oldProperty, newProperty);
            if (this.compareAndSetMap(oldMap, newMap)) {
               return newProperty;
            }

            oldMap = this.getMap();
            oldPropertyLookup = oldMap.findProperty(oldProperty.getKey());
         } while(oldPropertyLookup == null || !oldPropertyLookup.equals(newProperty));

         return oldPropertyLookup;
      }
   }

   public final void setUserAccessors(String key, ScriptFunction getter, ScriptFunction setter) {
      Property oldProperty = this.getMap().findProperty(key);
      if (oldProperty instanceof UserAccessorProperty) {
         this.modifyOwnProperty(oldProperty, oldProperty.getFlags(), getter, setter);
      } else {
         this.addOwnProperty(this.newUserAccessors(key, oldProperty != null ? oldProperty.getFlags() : 0, getter, setter));
      }

   }

   private static int getIntValue(FindProperty find, int programPoint) {
      MethodHandle getter = find.getGetter(Integer.TYPE, programPoint, (LinkRequest)null);
      if (getter != null) {
         try {
            return getter.invokeExact(find.getGetterReceiver());
         } catch (RuntimeException | Error var4) {
            throw var4;
         } catch (Throwable var5) {
            throw new RuntimeException(var5);
         }
      } else {
         return 0;
      }
   }

   private static double getDoubleValue(FindProperty find, int programPoint) {
      MethodHandle getter = find.getGetter(Double.TYPE, programPoint, (LinkRequest)null);
      if (getter != null) {
         try {
            return getter.invokeExact(find.getGetterReceiver());
         } catch (RuntimeException | Error var4) {
            throw var4;
         } catch (Throwable var5) {
            throw new RuntimeException(var5);
         }
      } else {
         return Double.NaN;
      }
   }

   protected MethodHandle getCallMethodHandle(FindProperty find, MethodType type, String bindName) {
      return getCallMethodHandle(find.getObjectValue(), type, bindName);
   }

   protected static MethodHandle getCallMethodHandle(Object value, MethodType type, String bindName) {
      return value instanceof ScriptFunction ? ((ScriptFunction)value).getCallMethodHandle(type, bindName) : null;
   }

   public final Object getWithProperty(Property property) {
      return (new FindProperty(this, this, property)).getObjectValue();
   }

   public final Property getProperty(String key) {
      return this.getMap().findProperty(key);
   }

   public Object getArgument(int key) {
      return this.get(key);
   }

   public void setArgument(int key, Object value) {
      this.set(key, value, 0);
   }

   protected Context getContext() {
      return Context.fromClass(this.getClass());
   }

   public final PropertyMap getMap() {
      return this.map;
   }

   public final void setMap(PropertyMap map) {
      this.map = map;
   }

   protected final boolean compareAndSetMap(PropertyMap oldMap, PropertyMap newMap) {
      if (oldMap == this.map) {
         this.map = newMap;
         return true;
      } else {
         return false;
      }
   }

   public final ScriptObject getProto() {
      return this.proto;
   }

   public final ScriptObject getProto(int n) {
      assert n > 0;

      ScriptObject p = this.getProto();

      for(int var3 = n; var3-- > 0; p = p.getProto()) {
      }

      return p;
   }

   public final void setProto(ScriptObject newProto) {
      ScriptObject oldProto = this.proto;
      if (oldProto != newProto) {
         this.proto = newProto;
         this.getMap().protoChanged(true);
         this.setMap(this.getMap().changeProto(newProto));
      }

   }

   public void setInitialProto(ScriptObject initialProto) {
      this.proto = initialProto;
   }

   public static void setGlobalObjectProto(ScriptObject obj) {
      obj.setInitialProto(Global.objectPrototype());
   }

   public final void setPrototypeOf(Object newProto) {
      if (newProto != null && !(newProto instanceof ScriptObject)) {
         throw ECMAErrors.typeError("cant.set.proto.to.non.object", ScriptRuntime.safeToString(this), ScriptRuntime.safeToString(newProto));
      } else if (!this.isExtensible()) {
         if (newProto != this.getProto()) {
            throw ECMAErrors.typeError("__proto__.set.non.extensible", ScriptRuntime.safeToString(this));
         }
      } else {
         for(ScriptObject p = (ScriptObject)newProto; p != null; p = p.getProto()) {
            if (p == this) {
               throw ECMAErrors.typeError("circular.__proto__.set", ScriptRuntime.safeToString(this));
            }
         }

         this.setProto((ScriptObject)newProto);
      }
   }

   public final void setProtoFromLiteral(Object newProto) {
      if (newProto != null && !(newProto instanceof ScriptObject)) {
         this.setPrototypeOf(Global.objectPrototype());
      } else {
         this.setPrototypeOf(newProto);
      }

   }

   public final String[] getOwnKeys(boolean all) {
      return this.getOwnKeys(all, (Set)null);
   }

   protected String[] getOwnKeys(boolean all, Set<String> nonEnumerable) {
      List<Object> keys = new ArrayList();
      PropertyMap selfMap = this.getMap();
      ArrayData array = this.getArray();
      Iterator iter = array.indexIterator();

      while(iter.hasNext()) {
         keys.add(JSType.toString((double)(Long)iter.next()));
      }

      Property[] var12 = selfMap.getProperties();
      int var7 = var12.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Property property = var12[var8];
         boolean enumerable = property.isEnumerable();
         String key = property.getKey();
         if (all) {
            keys.add(key);
         } else if (enumerable) {
            if (nonEnumerable == null || !nonEnumerable.contains(key)) {
               keys.add(key);
            }
         } else if (nonEnumerable != null) {
            nonEnumerable.add(key);
         }
      }

      return (String[])keys.toArray(new String[keys.size()]);
   }

   public boolean hasArrayEntries() {
      return this.getArray().length() > 0L || this.getMap().containsArrayKeys();
   }

   public String getClassName() {
      return "Object";
   }

   public Object getLength() {
      return this.get("length");
   }

   public String safeToString() {
      return "[object " + this.getClassName() + "]";
   }

   public Object getDefaultValue(Class<?> typeHint) {
      return Context.getGlobal().getDefaultValue(this, typeHint);
   }

   public boolean isInstance(ScriptObject instance) {
      return false;
   }

   public ScriptObject preventExtensions() {
      for(PropertyMap oldMap = this.getMap(); !this.compareAndSetMap(oldMap, this.getMap().preventExtensions()); oldMap = this.getMap()) {
      }

      ArrayData array = this.getArray();

      assert array != null;

      this.setArray(ArrayData.preventExtension(array));
      return this;
   }

   public static boolean isArray(Object obj) {
      return obj instanceof ScriptObject && ((ScriptObject)obj).isArray();
   }

   public final boolean isArray() {
      return (this.flags & 1) != 0;
   }

   public final void setIsArray() {
      this.flags |= 1;
   }

   public final boolean isArguments() {
      return (this.flags & 2) != 0;
   }

   public final void setIsArguments() {
      this.flags |= 2;
   }

   public boolean isLengthNotWritable() {
      return (this.flags & 4) != 0;
   }

   public void setIsLengthNotWritable() {
      this.flags |= 4;
   }

   public final ArrayData getArray(Class<?> elementType) {
      if (elementType == null) {
         return this.arrayData;
      } else {
         ArrayData newArrayData = this.arrayData.convert(elementType);
         if (newArrayData != this.arrayData) {
            this.arrayData = newArrayData;
         }

         return newArrayData;
      }
   }

   public final ArrayData getArray() {
      return this.arrayData;
   }

   public final void setArray(ArrayData arrayData) {
      this.arrayData = arrayData;
   }

   public boolean isExtensible() {
      return this.getMap().isExtensible();
   }

   public ScriptObject seal() {
      PropertyMap oldMap = this.getMap();

      while(true) {
         PropertyMap newMap = this.getMap().seal();
         if (this.compareAndSetMap(oldMap, newMap)) {
            this.setArray(ArrayData.seal(this.getArray()));
            return this;
         }

         oldMap = this.getMap();
      }
   }

   public boolean isSealed() {
      return this.getMap().isSealed();
   }

   public ScriptObject freeze() {
      PropertyMap oldMap = this.getMap();

      while(true) {
         PropertyMap newMap = this.getMap().freeze();
         if (this.compareAndSetMap(oldMap, newMap)) {
            this.setArray(ArrayData.freeze(this.getArray()));
            return this;
         }

         oldMap = this.getMap();
      }
   }

   public boolean isFrozen() {
      return this.getMap().isFrozen();
   }

   public boolean isScope() {
      return false;
   }

   public final void setIsBuiltin() {
      this.flags |= 8;
   }

   public final boolean isBuiltin() {
      return (this.flags & 8) != 0;
   }

   public void clear(boolean strict) {
      Iterator iter = this.propertyIterator();

      while(iter.hasNext()) {
         this.delete(iter.next(), strict);
      }

   }

   public boolean containsKey(Object key) {
      return this.has(key);
   }

   public boolean containsValue(Object value) {
      Iterator iter = this.valueIterator();

      do {
         if (!iter.hasNext()) {
            return false;
         }
      } while(!iter.next().equals(value));

      return true;
   }

   public Set<Entry<Object, Object>> entrySet() {
      Iterator<String> iter = this.propertyIterator();
      HashSet entries = new HashSet();

      while(iter.hasNext()) {
         Object key = iter.next();
         entries.add(new SimpleImmutableEntry(key, this.get(key)));
      }

      return Collections.unmodifiableSet(entries);
   }

   public boolean isEmpty() {
      return !this.propertyIterator().hasNext();
   }

   public Set<Object> keySet() {
      Iterator<String> iter = this.propertyIterator();
      HashSet keySet = new HashSet();

      while(iter.hasNext()) {
         keySet.add(iter.next());
      }

      return Collections.unmodifiableSet(keySet);
   }

   public Object put(Object key, Object value, boolean strict) {
      Object oldValue = this.get(key);
      int scriptObjectFlags = strict ? 2 : 0;
      this.set(key, value, scriptObjectFlags);
      return oldValue;
   }

   public void putAll(Map<?, ?> otherMap, boolean strict) {
      int scriptObjectFlags = strict ? 2 : 0;
      Iterator var4 = otherMap.entrySet().iterator();

      while(var4.hasNext()) {
         Entry<?, ?> entry = (Entry)var4.next();
         this.set(entry.getKey(), entry.getValue(), scriptObjectFlags);
      }

   }

   public Object remove(Object key, boolean strict) {
      Object oldValue = this.get(key);
      this.delete(key, strict);
      return oldValue;
   }

   public int size() {
      int n = 0;
      Iterator iter = this.propertyIterator();

      while(iter.hasNext()) {
         ++n;
         iter.next();
      }

      return n;
   }

   public Collection<Object> values() {
      List<Object> values = new ArrayList(this.size());
      Iterator iter = this.valueIterator();

      while(iter.hasNext()) {
         values.add(iter.next());
      }

      return Collections.unmodifiableList(values);
   }

   public GuardedInvocation lookup(CallSiteDescriptor desc, LinkRequest request) {
      int c = desc.getNameTokenCount();
      String operator = (String)CallSiteDescriptorFactory.tokenizeOperators(desc).get(0);
      byte var6 = -1;
      switch(operator.hashCode()) {
      case -75566075:
         if (operator.equals("getElem")) {
            var6 = 1;
         }
         break;
      case -75232295:
         if (operator.equals("getProp")) {
            var6 = 0;
         }
         break;
      case 108960:
         if (operator.equals("new")) {
            var6 = 6;
         }
         break;
      case 3045982:
         if (operator.equals("call")) {
            var6 = 5;
         }
         break;
      case 618460119:
         if (operator.equals("getMethod")) {
            var6 = 2;
         }
         break;
      case 1402960095:
         if (operator.equals("callMethod")) {
            var6 = 7;
         }
         break;
      case 1984543505:
         if (operator.equals("setElem")) {
            var6 = 4;
         }
         break;
      case 1984877285:
         if (operator.equals("setProp")) {
            var6 = 3;
         }
      }

      switch(var6) {
      case 0:
      case 1:
      case 2:
         return c > 2 ? this.findGetMethod(desc, request, operator) : this.findGetIndexMethod(desc, request);
      case 3:
      case 4:
         return c > 2 ? this.findSetMethod(desc, request) : this.findSetIndexMethod(desc, request);
      case 5:
         return this.findCallMethod(desc, request);
      case 6:
         return this.findNewMethod(desc, request);
      case 7:
         return this.findCallMethodMethod(desc, request);
      default:
         return null;
      }
   }

   protected GuardedInvocation findNewMethod(CallSiteDescriptor desc, LinkRequest request) {
      return this.notAFunction(desc);
   }

   protected GuardedInvocation findCallMethod(CallSiteDescriptor desc, LinkRequest request) {
      return this.notAFunction(desc);
   }

   private GuardedInvocation notAFunction(CallSiteDescriptor desc) {
      throw ECMAErrors.typeError("not.a.function", NashornCallSiteDescriptor.getFunctionErrorMessage(desc, this));
   }

   protected GuardedInvocation findCallMethodMethod(CallSiteDescriptor desc, LinkRequest request) {
      MethodType callType = desc.getMethodType();
      CallSiteDescriptor getterType = desc.changeMethodType(MethodType.methodType(Object.class, callType.parameterType(0)));
      GuardedInvocation getter = this.findGetMethod(getterType, request, "getMethod");
      MethodHandle argDroppingGetter = Lookup.MH.dropArguments(getter.getInvocation(), 1, (List)callType.parameterList().subList(1, callType.parameterCount()));
      MethodHandle invoker = Bootstrap.createDynamicInvoker("dyn:call", callType.insertParameterTypes(0, new Class[]{argDroppingGetter.type().returnType()}));
      return getter.replaceMethods(Lookup.MH.foldArguments(invoker, argDroppingGetter), getter.getGuard());
   }

   boolean hasWithScope() {
      return false;
   }

   static MethodHandle addProtoFilter(MethodHandle methodHandle, int depth) {
      if (depth == 0) {
         return methodHandle;
      } else {
         int listIndex = depth - 1;
         MethodHandle filter = listIndex < PROTO_FILTERS.size() ? (MethodHandle)PROTO_FILTERS.get(listIndex) : null;
         if (filter == null) {
            filter = addProtoFilter(GETPROTO, depth - 1);
            PROTO_FILTERS.add((Object)null);
            PROTO_FILTERS.set(listIndex, filter);
         }

         return Lookup.MH.filterArguments(methodHandle, 0, filter.asType(filter.type().changeReturnType(methodHandle.type().parameterType(0))));
      }
   }

   protected GuardedInvocation findGetMethod(CallSiteDescriptor desc, LinkRequest request, String operator) {
      boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
      String name = desc.getNameToken(2);
      if (NashornCallSiteDescriptor.isApplyToCall(desc) && Global.isBuiltinFunctionPrototypeApply()) {
         name = "call";
      }

      if (!request.isCallSiteUnstable() && !this.hasWithScope()) {
         FindProperty find = this.findProperty(name, true);
         if (find == null) {
            byte var18 = -1;
            switch(operator.hashCode()) {
            case -75566075:
               if (operator.equals("getElem")) {
                  var18 = 0;
               }
               break;
            case -75232295:
               if (operator.equals("getProp")) {
                  var18 = 1;
               }
               break;
            case 618460119:
               if (operator.equals("getMethod")) {
                  var18 = 2;
               }
            }

            switch(var18) {
            case 0:
            case 1:
               return this.noSuchProperty(desc, request);
            case 2:
               return this.noSuchMethod(desc, request);
            default:
               throw new AssertionError(operator);
            }
         } else {
            GlobalConstants globalConstants = this.getGlobalConstants();
            if (globalConstants != null) {
               GuardedInvocation cinv = globalConstants.findGetMethod(find, this, desc);
               if (cinv != null) {
                  return cinv;
               }
            }

            Class<?> returnType = desc.getMethodType().returnType();
            Property property = find.getProperty();
            int programPoint = NashornCallSiteDescriptor.isOptimistic(desc) ? NashornCallSiteDescriptor.getProgramPoint(desc) : -1;
            MethodHandle mh = find.getGetter(returnType, programPoint, request);
            MethodHandle guard = NashornGuards.getGuard(this, property, desc, explicitInstanceOfCheck);
            ScriptObject owner = find.getOwner();
            Class<ClassCastException> exception = explicitInstanceOfCheck ? null : ClassCastException.class;
            SwitchPoint[] protoSwitchPoints;
            if (mh == null) {
               mh = Lookup.emptyGetter(returnType);
               protoSwitchPoints = this.getProtoSwitchPoints(name, owner);
            } else if (!find.isSelf()) {
               assert mh.type().returnType().equals(returnType) : "return type mismatch for getter " + mh.type().returnType() + " != " + returnType;

               if (!(property instanceof UserAccessorProperty)) {
                  mh = addProtoFilter(mh, find.getProtoChainLength());
               }

               protoSwitchPoints = this.getProtoSwitchPoints(name, owner);
            } else {
               protoSwitchPoints = null;
            }

            GuardedInvocation inv = new GuardedInvocation(mh, guard, protoSwitchPoints, exception);
            return inv.addSwitchPoint(this.findBuiltinSwitchPoint(name));
         }
      } else {
         return findMegaMorphicGetMethod(desc, name, "getMethod".equals(operator));
      }
   }

   private static GuardedInvocation findMegaMorphicGetMethod(CallSiteDescriptor desc, String name, boolean isMethod) {
      Context.getContextTrusted().getLogger(ObjectClassGenerator.class).warning("Megamorphic getter: " + desc + " " + name + " " + isMethod);
      MethodHandle invoker = Lookup.MH.insertArguments(MEGAMORPHIC_GET, 1, name, isMethod, NashornCallSiteDescriptor.isScope(desc));
      MethodHandle guard = getScriptObjectGuard(desc.getMethodType(), true);
      return new GuardedInvocation(invoker, guard);
   }

   private Object megamorphicGet(String key, boolean isMethod, boolean isScope) {
      FindProperty find = this.findProperty(key, true);
      if (find != null) {
         return find.getObjectValue();
      } else {
         return isMethod ? this.getNoSuchMethod(key, isScope, -1) : this.invokeNoSuchProperty(key, isScope, -1);
      }
   }

   private void declareAndSet(String key, Object value) {
      PropertyMap oldMap = this.getMap();
      FindProperty find = this.findProperty(key, false);

      assert find != null;

      Property property = find.getProperty();

      assert property != null;

      assert property.needsDeclaration();

      PropertyMap newMap = oldMap.replaceProperty(property, property.removeFlags(512));
      this.setMap(newMap);
      this.set(key, value, 0);
   }

   protected GuardedInvocation findGetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
      MethodType callType = desc.getMethodType();
      Class<?> returnType = callType.returnType();
      Class<?> returnClass = returnType.isPrimitive() ? returnType : Object.class;
      Class<?> keyClass = callType.parameterType(1);
      boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
      String name;
      if (returnClass.isPrimitive()) {
         String returnTypeName = returnClass.getName();
         name = "get" + Character.toUpperCase(returnTypeName.charAt(0)) + returnTypeName.substring(1, returnTypeName.length());
      } else {
         name = "get";
      }

      MethodHandle mh = this.findGetIndexMethodHandle(returnClass, name, keyClass, desc);
      return new GuardedInvocation(mh, getScriptObjectGuard(callType, explicitInstanceOfCheck), (SwitchPoint)null, explicitInstanceOfCheck ? null : ClassCastException.class);
   }

   private static MethodHandle getScriptObjectGuard(MethodType type, boolean explicitInstanceOfCheck) {
      return ScriptObject.class.isAssignableFrom(type.parameterType(0)) ? null : NashornGuards.getScriptObjectGuard(explicitInstanceOfCheck);
   }

   protected MethodHandle findGetIndexMethodHandle(Class<?> returnType, String name, Class<?> elementType, CallSiteDescriptor desc) {
      return !returnType.isPrimitive() ? findOwnMH_V(this.getClass(), name, returnType, elementType) : Lookup.MH.insertArguments(findOwnMH_V(this.getClass(), name, returnType, elementType, Integer.TYPE), 2, NashornCallSiteDescriptor.isOptimistic(desc) ? NashornCallSiteDescriptor.getProgramPoint(desc) : -1);
   }

   public final SwitchPoint[] getProtoSwitchPoints(String name, ScriptObject owner) {
      if (owner != this && this.getProto() != null) {
         List<SwitchPoint> switchPoints = new ArrayList();

         for(ScriptObject obj = this; obj != owner && obj.getProto() != null; obj = obj.getProto()) {
            ScriptObject parent = obj.getProto();
            parent.getMap().addListener(name, obj.getMap());
            SwitchPoint sp = parent.getMap().getSharedProtoSwitchPoint();
            if (sp != null && !sp.hasBeenInvalidated()) {
               switchPoints.add(sp);
            }
         }

         switchPoints.add(this.getMap().getSwitchPoint(name));
         return (SwitchPoint[])switchPoints.toArray(new SwitchPoint[switchPoints.size()]);
      } else {
         return null;
      }
   }

   private void checkSharedProtoMap() {
      if (this.getMap().isInvalidSharedMapFor(this.getProto())) {
         this.setMap(this.getMap().makeUnsharedCopy());
      }

   }

   protected GuardedInvocation findSetMethod(CallSiteDescriptor desc, LinkRequest request) {
      String name = desc.getNameToken(2);
      if (!request.isCallSiteUnstable() && !this.hasWithScope()) {
         boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
         FindProperty find = this.findProperty(name, true, this);
         if (find != null && find.isInherited() && !(find.getProperty() instanceof UserAccessorProperty)) {
            if (this.isExtensible() && !find.getProperty().isWritable()) {
               return this.createEmptySetMethod(desc, explicitInstanceOfCheck, "property.not.writable", true);
            }

            if (!NashornCallSiteDescriptor.isScope(desc) || !find.getOwner().isScope()) {
               find = null;
            }
         }

         if (find != null) {
            if (!find.getProperty().isWritable() && !NashornCallSiteDescriptor.isDeclaration(desc)) {
               if (NashornCallSiteDescriptor.isScope(desc) && find.getProperty().isLexicalBinding()) {
                  throw ECMAErrors.typeError("assign.constant", name);
               }

               return this.createEmptySetMethod(desc, explicitInstanceOfCheck, "property.not.writable", true);
            }
         } else if (!this.isExtensible()) {
            return this.createEmptySetMethod(desc, explicitInstanceOfCheck, "object.non.extensible", false);
         }

         GuardedInvocation inv = (new SetMethodCreator(this, find, desc, request)).createGuardedInvocation(this.findBuiltinSwitchPoint(name));
         GlobalConstants globalConstants = this.getGlobalConstants();
         if (globalConstants != null) {
            GuardedInvocation cinv = globalConstants.findSetMethod(find, this, inv, desc, request);
            if (cinv != null) {
               return cinv;
            }
         }

         return inv;
      } else {
         return this.findMegaMorphicSetMethod(desc, name);
      }
   }

   private GlobalConstants getGlobalConstants() {
      return !this.isGlobal() ? null : this.getContext().getGlobalConstants();
   }

   private GuardedInvocation createEmptySetMethod(CallSiteDescriptor desc, boolean explicitInstanceOfCheck, String strictErrorMessage, boolean canBeFastScope) {
      String name = desc.getNameToken(2);
      if (NashornCallSiteDescriptor.isStrict(desc)) {
         throw ECMAErrors.typeError(strictErrorMessage, name, ScriptRuntime.safeToString(this));
      } else {
         assert canBeFastScope || !NashornCallSiteDescriptor.isFastScope(desc);

         return new GuardedInvocation(Lookup.EMPTY_SETTER, NashornGuards.getMapGuard(this.getMap(), explicitInstanceOfCheck), this.getProtoSwitchPoints(name, (ScriptObject)null), explicitInstanceOfCheck ? null : ClassCastException.class);
      }
   }

   private boolean extensionCheck(boolean isStrict, String name) {
      if (this.isExtensible()) {
         return true;
      } else if (isStrict) {
         throw ECMAErrors.typeError("object.non.extensible", name, ScriptRuntime.safeToString(this));
      } else {
         return false;
      }
   }

   private GuardedInvocation findMegaMorphicSetMethod(CallSiteDescriptor desc, String name) {
      MethodType type = desc.getMethodType().insertParameterTypes(1, new Class[]{Object.class});
      GuardedInvocation inv = findSetIndexMethod(this.getClass(), desc, false, type);
      return inv.replaceMethods(Lookup.MH.insertArguments(inv.getInvocation(), 1, name), inv.getGuard());
   }

   private static Object globalFilter(Object object) {
      ScriptObject sobj;
      for(sobj = (ScriptObject)object; sobj != null && !(sobj instanceof Global); sobj = sobj.getProto()) {
      }

      return sobj;
   }

   protected GuardedInvocation findSetIndexMethod(CallSiteDescriptor desc, LinkRequest request) {
      return findSetIndexMethod(this.getClass(), desc, NashornGuards.explicitInstanceOfCheck(desc, request), desc.getMethodType());
   }

   private static GuardedInvocation findSetIndexMethod(Class<? extends ScriptObject> clazz, CallSiteDescriptor desc, boolean explicitInstanceOfCheck, MethodType callType) {
      assert callType.parameterCount() == 3;

      Class<?> keyClass = callType.parameterType(1);
      Class<?> valueClass = callType.parameterType(2);
      MethodHandle methodHandle = findOwnMH_V(clazz, "set", Void.TYPE, keyClass, valueClass, Integer.TYPE);
      methodHandle = Lookup.MH.insertArguments(methodHandle, 3, NashornCallSiteDescriptor.getFlags(desc));
      return new GuardedInvocation(methodHandle, getScriptObjectGuard(callType, explicitInstanceOfCheck), (SwitchPoint)null, explicitInstanceOfCheck ? null : ClassCastException.class);
   }

   public GuardedInvocation noSuchMethod(CallSiteDescriptor desc, LinkRequest request) {
      String name = desc.getNameToken(2);
      FindProperty find = this.findProperty("__noSuchMethod__", true);
      boolean scopeCall = this.isScope() && NashornCallSiteDescriptor.isScope(desc);
      if (find == null) {
         return this.noSuchProperty(desc, request);
      } else {
         boolean explicitInstanceOfCheck = NashornGuards.explicitInstanceOfCheck(desc, request);
         Object value = find.getObjectValue();
         if (!(value instanceof ScriptFunction)) {
            return this.createEmptyGetter(desc, explicitInstanceOfCheck, name);
         } else {
            ScriptFunction func = (ScriptFunction)value;
            Object thiz = scopeCall && func.isStrict() ? ScriptRuntime.UNDEFINED : this;
            return new GuardedInvocation(Lookup.MH.dropArguments(Lookup.MH.constant(ScriptFunction.class, func.createBound(thiz, new Object[]{name})), 0, (Class[])(Object.class)), NashornGuards.combineGuards(NashornGuards.getIdentityGuard(this), NashornGuards.getMapGuard(this.getMap(), true)));
         }
      }
   }

   public GuardedInvocation noSuchProperty(CallSiteDescriptor desc, LinkRequest request) {
      String name = desc.getNameToken(2);
      FindProperty find = this.findProperty("__noSuchProperty__", true);
      boolean scopeAccess = this.isScope() && NashornCallSiteDescriptor.isScope(desc);
      if (find != null) {
         Object value = find.getObjectValue();
         ScriptFunction func = null;
         MethodHandle mh = null;
         if (value instanceof ScriptFunction) {
            func = (ScriptFunction)value;
            mh = getCallMethodHandle((Object)func, desc.getMethodType(), name);
         }

         if (mh != null) {
            assert func != null;

            if (scopeAccess && func.isStrict()) {
               mh = bindTo(mh, ScriptRuntime.UNDEFINED);
            }

            return new GuardedInvocation(mh, find.isSelf() ? getKnownFunctionPropertyGuardSelf(this.getMap(), find.getGetter(Object.class, -1, request), func) : getKnownFunctionPropertyGuardProto(this.getMap(), find.getGetter(Object.class, -1, request), find.getProtoChainLength(), func), this.getProtoSwitchPoints("__noSuchProperty__", find.getOwner()), (Class)null);
         }
      }

      if (scopeAccess) {
         throw ECMAErrors.referenceError("not.defined", name);
      } else {
         return this.createEmptyGetter(desc, NashornGuards.explicitInstanceOfCheck(desc, request), name);
      }
   }

   protected Object invokeNoSuchProperty(String name, boolean isScope, int programPoint) {
      FindProperty find = this.findProperty("__noSuchProperty__", true);
      Object func = find != null ? find.getObjectValue() : null;
      Object ret = ScriptRuntime.UNDEFINED;
      if (func instanceof ScriptFunction) {
         ScriptFunction sfunc = (ScriptFunction)func;
         Object self = isScope && sfunc.isStrict() ? ScriptRuntime.UNDEFINED : this;
         ret = ScriptRuntime.apply(sfunc, self, name);
      } else if (isScope) {
         throw ECMAErrors.referenceError("not.defined", name);
      }

      if (UnwarrantedOptimismException.isValid(programPoint)) {
         throw new UnwarrantedOptimismException(ret, programPoint);
      } else {
         return ret;
      }
   }

   private Object getNoSuchMethod(String name, boolean isScope, int programPoint) {
      FindProperty find = this.findProperty("__noSuchMethod__", true);
      if (find == null) {
         return this.invokeNoSuchProperty(name, isScope, programPoint);
      } else {
         Object value = find.getObjectValue();
         if (!(value instanceof ScriptFunction)) {
            if (isScope) {
               throw ECMAErrors.referenceError("not.defined", name);
            } else {
               return ScriptRuntime.UNDEFINED;
            }
         } else {
            ScriptFunction func = (ScriptFunction)value;
            Object self = isScope && func.isStrict() ? ScriptRuntime.UNDEFINED : this;
            return func.createBound(self, new Object[]{name});
         }
      }
   }

   private GuardedInvocation createEmptyGetter(CallSiteDescriptor desc, boolean explicitInstanceOfCheck, String name) {
      if (NashornCallSiteDescriptor.isOptimistic(desc)) {
         throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, NashornCallSiteDescriptor.getProgramPoint(desc), Type.OBJECT);
      } else {
         return new GuardedInvocation(Lookup.emptyGetter(desc.getMethodType().returnType()), NashornGuards.getMapGuard(this.getMap(), explicitInstanceOfCheck), this.getProtoSwitchPoints(name, (ScriptObject)null), explicitInstanceOfCheck ? null : ClassCastException.class);
      }
   }

   private Property addSpillProperty(String key, int flags, Object value, boolean hasInitialValue) {
      PropertyMap propertyMap = this.getMap();
      int fieldSlot = propertyMap.getFreeFieldSlot();
      int propertyFlags = flags | (this.useDualFields() ? 2048 : 0);
      Property property;
      if (fieldSlot > -1) {
         Property property = hasInitialValue ? new AccessorProperty(key, propertyFlags, fieldSlot, this, value) : new AccessorProperty(key, propertyFlags, this.getClass(), fieldSlot);
         property = this.addOwnProperty(property);
      } else {
         int spillSlot = propertyMap.getFreeSpillSlot();
         Property property = hasInitialValue ? new SpillProperty(key, propertyFlags, spillSlot, this, value) : new SpillProperty(key, propertyFlags, spillSlot);
         property = this.addOwnProperty(property);
         this.ensureSpillSize(property.getSlot());
      }

      return property;
   }

   MethodHandle addSpill(Class<?> type, String key) {
      return this.addSpillProperty(key, 0, (Object)null, false).getSetter(type, this.getMap());
   }

   protected static MethodHandle pairArguments(MethodHandle methodHandle, MethodType callType) {
      return pairArguments(methodHandle, callType, (Boolean)null);
   }

   public static MethodHandle pairArguments(MethodHandle methodHandle, MethodType callType, Boolean callerVarArg) {
      MethodType methodType = methodHandle.type();
      if (methodType.equals(callType.changeReturnType(methodType.returnType()))) {
         return methodHandle;
      } else {
         int parameterCount = methodType.parameterCount();
         int callCount = callType.parameterCount();
         boolean isCalleeVarArg = parameterCount > 0 && methodType.parameterType(parameterCount - 1).isArray();
         boolean isCallerVarArg = callerVarArg != null ? callerVarArg : callCount > 0 && callType.parameterType(callCount - 1).isArray();
         if (isCalleeVarArg) {
            return isCallerVarArg ? methodHandle : Lookup.MH.asCollector(methodHandle, Object[].class, callCount - parameterCount + 1);
         } else if (isCallerVarArg) {
            return adaptHandleToVarArgCallSite(methodHandle, callCount);
         } else {
            int discardedArgs;
            if (callCount < parameterCount) {
               discardedArgs = parameterCount - callCount;
               Object[] fillers = new Object[discardedArgs];
               Arrays.fill(fillers, ScriptRuntime.UNDEFINED);
               if (isCalleeVarArg) {
                  fillers[discardedArgs - 1] = ScriptRuntime.EMPTY_ARRAY;
               }

               return Lookup.MH.insertArguments(methodHandle, parameterCount - discardedArgs, fillers);
            } else if (callCount > parameterCount) {
               discardedArgs = callCount - parameterCount;
               Class<?>[] discards = new Class[discardedArgs];
               Arrays.fill(discards, Object.class);
               return Lookup.MH.dropArguments(methodHandle, callCount - discardedArgs, discards);
            } else {
               return methodHandle;
            }
         }
      }
   }

   static MethodHandle adaptHandleToVarArgCallSite(MethodHandle mh, int callSiteParamCount) {
      int spreadArgs = mh.type().parameterCount() - callSiteParamCount + 1;
      return Lookup.MH.filterArguments(Lookup.MH.asSpreader(mh, Object[].class, spreadArgs), callSiteParamCount - 1, Lookup.MH.insertArguments(TRUNCATINGFILTER, 0, spreadArgs));
   }

   private static Object[] truncatingFilter(int n, Object[] array) {
      int length = array == null ? 0 : array.length;
      if (n == length) {
         return array == null ? ScriptRuntime.EMPTY_ARRAY : array;
      } else {
         Object[] newArray = new Object[n];
         if (array != null) {
            System.arraycopy(array, 0, newArray, 0, Math.min(n, length));
         }

         if (length < n) {
            Object fill = ScriptRuntime.UNDEFINED;

            for(int i = length; i < n; ++i) {
               newArray[i] = fill;
            }
         }

         return newArray;
      }
   }

   public final void setLength(long newLength) {
      ArrayData data = this.getArray();
      long arrayLength = data.length();
      if (newLength != arrayLength) {
         if (newLength > arrayLength) {
            this.setArray(data.ensure(newLength - 1L).safeDelete(arrayLength, newLength - 1L, false));
         } else {
            if (newLength < arrayLength) {
               long actualLength = newLength;
               if (this.getMap().containsArrayKeys()) {
                  for(long l = arrayLength - 1L; l >= newLength; --l) {
                     FindProperty find = this.findProperty(JSType.toString((double)l), false);
                     if (find != null) {
                        if (!find.getProperty().isConfigurable()) {
                           actualLength = l + 1L;
                           break;
                        }

                        this.deleteOwnProperty(find.getProperty());
                     }
                  }
               }

               this.setArray(data.shrink(actualLength));
               data.setLength(actualLength);
            }

         }
      }
   }

   private int getInt(int index, String key, int programPoint) {
      if (ArrayIndex.isValidArrayIndex(index)) {
         ScriptObject object = this;

         while(true) {
            if (object.getMap().containsArrayKeys()) {
               FindProperty find = object.findProperty(key, false, this);
               if (find != null) {
                  return getIntValue(find, programPoint);
               }
            }

            if ((object = object.getProto()) == null) {
               break;
            }

            ArrayData array = object.getArray();
            if (array.has(index)) {
               return UnwarrantedOptimismException.isValid(programPoint) ? array.getIntOptimistic(index, programPoint) : array.getInt(index);
            }
         }
      } else {
         FindProperty find = this.findProperty(key, true);
         if (find != null) {
            return getIntValue(find, programPoint);
         }
      }

      return JSType.toInt32(this.invokeNoSuchProperty(key, false, programPoint));
   }

   public int getInt(Object key, int programPoint) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         return UnwarrantedOptimismException.isValid(programPoint) ? array.getIntOptimistic(index, programPoint) : array.getInt(index);
      } else {
         return this.getInt(index, JSType.toString(primitiveKey), programPoint);
      }
   }

   public int getInt(double key, int programPoint) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         return UnwarrantedOptimismException.isValid(programPoint) ? array.getIntOptimistic(index, programPoint) : array.getInt(index);
      } else {
         return this.getInt(index, JSType.toString(key), programPoint);
      }
   }

   public int getInt(int key, int programPoint) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         return UnwarrantedOptimismException.isValid(programPoint) ? array.getIntOptimistic(key, programPoint) : array.getInt(key);
      } else {
         return this.getInt(index, JSType.toString(key), programPoint);
      }
   }

   private double getDouble(int index, String key, int programPoint) {
      if (ArrayIndex.isValidArrayIndex(index)) {
         ScriptObject object = this;

         while(true) {
            if (object.getMap().containsArrayKeys()) {
               FindProperty find = object.findProperty(key, false, this);
               if (find != null) {
                  return getDoubleValue(find, programPoint);
               }
            }

            if ((object = object.getProto()) == null) {
               break;
            }

            ArrayData array = object.getArray();
            if (array.has(index)) {
               return UnwarrantedOptimismException.isValid(programPoint) ? array.getDoubleOptimistic(index, programPoint) : array.getDouble(index);
            }
         }
      } else {
         FindProperty find = this.findProperty(key, true);
         if (find != null) {
            return getDoubleValue(find, programPoint);
         }
      }

      return JSType.toNumber(this.invokeNoSuchProperty(key, false, -1));
   }

   public double getDouble(Object key, int programPoint) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         return UnwarrantedOptimismException.isValid(programPoint) ? array.getDoubleOptimistic(index, programPoint) : array.getDouble(index);
      } else {
         return this.getDouble(index, JSType.toString(primitiveKey), programPoint);
      }
   }

   public double getDouble(double key, int programPoint) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         return UnwarrantedOptimismException.isValid(programPoint) ? array.getDoubleOptimistic(index, programPoint) : array.getDouble(index);
      } else {
         return this.getDouble(index, JSType.toString(key), programPoint);
      }
   }

   public double getDouble(int key, int programPoint) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         return UnwarrantedOptimismException.isValid(programPoint) ? array.getDoubleOptimistic(key, programPoint) : array.getDouble(key);
      } else {
         return this.getDouble(index, JSType.toString(key), programPoint);
      }
   }

   private Object get(int index, String key) {
      if (ArrayIndex.isValidArrayIndex(index)) {
         ScriptObject object = this;

         while(true) {
            if (object.getMap().containsArrayKeys()) {
               FindProperty find = object.findProperty(key, false, this);
               if (find != null) {
                  return find.getObjectValue();
               }
            }

            if ((object = object.getProto()) == null) {
               break;
            }

            ArrayData array = object.getArray();
            if (array.has(index)) {
               return array.getObject(index);
            }
         }
      } else {
         FindProperty find = this.findProperty(key, true);
         if (find != null) {
            return find.getObjectValue();
         }
      }

      return this.invokeNoSuchProperty(key, false, -1);
   }

   public Object get(Object key) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      ArrayData array = this.getArray();
      return array.has(index) ? array.getObject(index) : this.get(index, JSType.toString(primitiveKey));
   }

   public Object get(double key) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      return array.has(index) ? array.getObject(index) : this.get(index, JSType.toString(key));
   }

   public Object get(int key) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      return array.has(index) ? array.getObject(index) : this.get(index, JSType.toString(key));
   }

   private boolean doesNotHaveCheckArrayKeys(long longIndex, int value, int callSiteFlags) {
      if (this.getMap().containsArrayKeys()) {
         String key = JSType.toString((double)longIndex);
         FindProperty find = this.findProperty(key, true);
         if (find != null) {
            this.setObject(find, callSiteFlags, key, value);
            return true;
         }
      }

      return false;
   }

   private boolean doesNotHaveCheckArrayKeys(long longIndex, long value, int callSiteFlags) {
      if (this.getMap().containsArrayKeys()) {
         String key = JSType.toString((double)longIndex);
         FindProperty find = this.findProperty(key, true);
         if (find != null) {
            this.setObject(find, callSiteFlags, key, value);
            return true;
         }
      }

      return false;
   }

   private boolean doesNotHaveCheckArrayKeys(long longIndex, double value, int callSiteFlags) {
      if (this.getMap().containsArrayKeys()) {
         String key = JSType.toString((double)longIndex);
         FindProperty find = this.findProperty(key, true);
         if (find != null) {
            this.setObject(find, callSiteFlags, key, value);
            return true;
         }
      }

      return false;
   }

   private boolean doesNotHaveCheckArrayKeys(long longIndex, Object value, int callSiteFlags) {
      if (this.getMap().containsArrayKeys()) {
         String key = JSType.toString((double)longIndex);
         FindProperty find = this.findProperty(key, true);
         if (find != null) {
            this.setObject(find, callSiteFlags, key, value);
            return true;
         }
      }

      return false;
   }

   private boolean doesNotHaveEnsureLength(long longIndex, long oldLength, int callSiteFlags) {
      if (longIndex >= oldLength) {
         if (!this.isExtensible()) {
            if (NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)) {
               throw ECMAErrors.typeError("object.non.extensible", JSType.toString((double)longIndex), ScriptRuntime.safeToString(this));
            }

            return true;
         }

         this.setArray(this.getArray().ensure(longIndex));
      }

      return false;
   }

   private void doesNotHave(int index, int value, int callSiteFlags) {
      long oldLength = this.getArray().length();
      long longIndex = ArrayIndex.toLongIndex(index);
      if (!this.doesNotHaveCheckArrayKeys(longIndex, value, callSiteFlags) && !this.doesNotHaveEnsureLength(longIndex, oldLength, callSiteFlags)) {
         boolean strict = NashornCallSiteDescriptor.isStrictFlag(callSiteFlags);
         this.setArray(this.getArray().set(index, value, strict).safeDelete(oldLength, longIndex - 1L, strict));
      }

   }

   private void doesNotHave(int index, double value, int callSiteFlags) {
      long oldLength = this.getArray().length();
      long longIndex = ArrayIndex.toLongIndex(index);
      if (!this.doesNotHaveCheckArrayKeys(longIndex, value, callSiteFlags) && !this.doesNotHaveEnsureLength(longIndex, oldLength, callSiteFlags)) {
         boolean strict = NashornCallSiteDescriptor.isStrictFlag(callSiteFlags);
         this.setArray(this.getArray().set(index, value, strict).safeDelete(oldLength, longIndex - 1L, strict));
      }

   }

   private void doesNotHave(int index, Object value, int callSiteFlags) {
      long oldLength = this.getArray().length();
      long longIndex = ArrayIndex.toLongIndex(index);
      if (!this.doesNotHaveCheckArrayKeys(longIndex, value, callSiteFlags) && !this.doesNotHaveEnsureLength(longIndex, oldLength, callSiteFlags)) {
         boolean strict = NashornCallSiteDescriptor.isStrictFlag(callSiteFlags);
         this.setArray(this.getArray().set(index, value, strict).safeDelete(oldLength, longIndex - 1L, strict));
      }

   }

   public final void setObject(FindProperty find, int callSiteFlags, String key, Object value) {
      FindProperty f = find;
      this.invalidateGlobalConstant(key);
      if (find != null && find.isInherited() && !(find.getProperty() instanceof UserAccessorProperty)) {
         boolean isScope = NashornCallSiteDescriptor.isScopeFlag(callSiteFlags);
         if (isScope && find.getSelf() != this) {
            find.getSelf().setObject((FindProperty)null, 0, key, value);
            return;
         }

         if (!isScope || !find.getOwner().isScope()) {
            f = null;
         }
      }

      if (f != null) {
         if (!f.getProperty().isWritable()) {
            if (NashornCallSiteDescriptor.isScopeFlag(callSiteFlags) && f.getProperty().isLexicalBinding()) {
               throw ECMAErrors.typeError("assign.constant", key);
            }

            if (NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)) {
               throw ECMAErrors.typeError("property.not.writable", key, ScriptRuntime.safeToString(this));
            }

            return;
         }

         f.setValue(value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags));
      } else if (!this.isExtensible()) {
         if (NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)) {
            throw ECMAErrors.typeError("object.non.extensible", key, ScriptRuntime.safeToString(this));
         }
      } else {
         ScriptObject sobj = this;
         if (this.isScope()) {
            while(sobj != null && !(sobj instanceof Global)) {
               sobj = sobj.getProto();
            }

            assert sobj != null : "no parent global object in scope";
         }

         sobj.addSpillProperty(key, 0, value, true);
      }

   }

   public void set(Object key, int value, int callSiteFlags) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(primitiveKey);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
      }
   }

   public void set(Object key, double value, int callSiteFlags) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(primitiveKey);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
      }
   }

   public void set(Object key, Object value, int callSiteFlags) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(primitiveKey);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, value);
      }
   }

   public void set(double key, int value, int callSiteFlags) {
      int index = ArrayIndex.getArrayIndex(key);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(key);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
      }
   }

   public void set(double key, double value, int callSiteFlags) {
      int index = ArrayIndex.getArrayIndex(key);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(key);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
      }
   }

   public void set(double key, Object value, int callSiteFlags) {
      int index = ArrayIndex.getArrayIndex(key);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(key);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, value);
      }
   }

   public void set(int key, int value, int callSiteFlags) {
      int index = ArrayIndex.getArrayIndex(key);
      if (ArrayIndex.isValidArrayIndex(index)) {
         if (this.getArray().has(index)) {
            ArrayData data = this.getArray();
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(key);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
      }
   }

   public void set(int key, double value, int callSiteFlags) {
      int index = ArrayIndex.getArrayIndex(key);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(key);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, JSType.toObject(value));
      }
   }

   public void set(int key, Object value, int callSiteFlags) {
      int index = ArrayIndex.getArrayIndex(key);
      if (ArrayIndex.isValidArrayIndex(index)) {
         ArrayData data = this.getArray();
         if (data.has(index)) {
            this.setArray(data.set(index, value, NashornCallSiteDescriptor.isStrictFlag(callSiteFlags)));
         } else {
            this.doesNotHave(index, value, callSiteFlags);
         }

      } else {
         String propName = JSType.toString(key);
         this.setObject(this.findProperty(propName, true), callSiteFlags, propName, value);
      }
   }

   public boolean has(Object key) {
      Object primitiveKey = JSType.toPrimitive(key);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      return ArrayIndex.isValidArrayIndex(index) ? this.hasArrayProperty(index) : this.hasProperty(JSType.toString(primitiveKey), true);
   }

   public boolean has(double key) {
      int index = ArrayIndex.getArrayIndex(key);
      return ArrayIndex.isValidArrayIndex(index) ? this.hasArrayProperty(index) : this.hasProperty(JSType.toString(key), true);
   }

   public boolean has(int key) {
      int index = ArrayIndex.getArrayIndex(key);
      return ArrayIndex.isValidArrayIndex(index) ? this.hasArrayProperty(index) : this.hasProperty(JSType.toString(key), true);
   }

   private boolean hasArrayProperty(int index) {
      boolean hasArrayKeys = false;

      for(ScriptObject self = this; self != null; self = self.getProto()) {
         if (self.getArray().has(index)) {
            return true;
         }

         hasArrayKeys = hasArrayKeys || self.getMap().containsArrayKeys();
      }

      return hasArrayKeys && this.hasProperty(ArrayIndex.toKey(index), true);
   }

   public boolean hasOwnProperty(Object key) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      return ArrayIndex.isValidArrayIndex(index) ? this.hasOwnArrayProperty(index) : this.hasProperty(JSType.toString(primitiveKey), false);
   }

   public boolean hasOwnProperty(int key) {
      int index = ArrayIndex.getArrayIndex(key);
      return ArrayIndex.isValidArrayIndex(index) ? this.hasOwnArrayProperty(index) : this.hasProperty(JSType.toString(key), false);
   }

   public boolean hasOwnProperty(double key) {
      int index = ArrayIndex.getArrayIndex(key);
      return ArrayIndex.isValidArrayIndex(index) ? this.hasOwnArrayProperty(index) : this.hasProperty(JSType.toString(key), false);
   }

   private boolean hasOwnArrayProperty(int index) {
      return this.getArray().has(index) || this.getMap().containsArrayKeys() && this.hasProperty(ArrayIndex.toKey(index), false);
   }

   public boolean delete(int key, boolean strict) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         if (array.canDelete(index, strict)) {
            this.setArray(array.delete(index));
            return true;
         } else {
            return false;
         }
      } else {
         return this.deleteObject(JSType.toObject(key), strict);
      }
   }

   public boolean delete(double key, boolean strict) {
      int index = ArrayIndex.getArrayIndex(key);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         if (array.canDelete(index, strict)) {
            this.setArray(array.delete(index));
            return true;
         } else {
            return false;
         }
      } else {
         return this.deleteObject(JSType.toObject(key), strict);
      }
   }

   public boolean delete(Object key, boolean strict) {
      Object primitiveKey = JSType.toPrimitive(key, String.class);
      int index = ArrayIndex.getArrayIndex(primitiveKey);
      ArrayData array = this.getArray();
      if (array.has(index)) {
         if (array.canDelete(index, strict)) {
            this.setArray(array.delete(index));
            return true;
         } else {
            return false;
         }
      } else {
         return this.deleteObject(primitiveKey, strict);
      }
   }

   private boolean deleteObject(Object key, boolean strict) {
      String propName = JSType.toString(key);
      FindProperty find = this.findProperty(propName, false);
      if (find == null) {
         return true;
      } else if (!find.getProperty().isConfigurable()) {
         if (strict) {
            throw ECMAErrors.typeError("cant.delete.property", propName, ScriptRuntime.safeToString(this));
         } else {
            return false;
         }
      } else {
         Property prop = find.getProperty();
         this.deleteOwnProperty(prop);
         return true;
      }
   }

   public final ScriptObject copy() {
      try {
         return this.clone();
      } catch (CloneNotSupportedException var2) {
         throw new RuntimeException(var2);
      }
   }

   protected ScriptObject clone() throws CloneNotSupportedException {
      ScriptObject clone = (ScriptObject)super.clone();
      if (this.objectSpill != null) {
         clone.objectSpill = (Object[])this.objectSpill.clone();
         if (this.primitiveSpill != null) {
            clone.primitiveSpill = (long[])this.primitiveSpill.clone();
         }
      }

      clone.arrayData = this.arrayData.copy();
      return clone;
   }

   protected final UserAccessorProperty newUserAccessors(String key, int propertyFlags, ScriptFunction getter, ScriptFunction setter) {
      UserAccessorProperty uc = this.getMap().newUserAccessors(key, propertyFlags);
      uc.setAccessors(this, this.getMap(), new UserAccessorProperty.Accessors(getter, setter));
      return uc;
   }

   protected boolean useDualFields() {
      return !StructureLoader.isSingleFieldStructure(this.getClass().getName());
   }

   Object ensureSpillSize(int slot) {
      int oldLength = this.objectSpill == null ? 0 : this.objectSpill.length;
      if (slot < oldLength) {
         return this;
      } else {
         int newLength = alignUp(slot + 1, 8);
         Object[] newObjectSpill = new Object[newLength];
         long[] newPrimitiveSpill = this.useDualFields() ? new long[newLength] : null;
         if (this.objectSpill != null) {
            System.arraycopy(this.objectSpill, 0, newObjectSpill, 0, oldLength);
            if (this.primitiveSpill != null && newPrimitiveSpill != null) {
               System.arraycopy(this.primitiveSpill, 0, newPrimitiveSpill, 0, oldLength);
            }
         }

         this.primitiveSpill = newPrimitiveSpill;
         this.objectSpill = newObjectSpill;
         return this;
      }
   }

   private static MethodHandle findOwnMH_V(Class<? extends ScriptObject> clazz, String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findVirtual(MethodHandles.lookup(), ScriptObject.class, name, Lookup.MH.type(rtype, types));
   }

   private static MethodHandle findOwnMH_V(String name, Class<?> rtype, Class<?>... types) {
      return findOwnMH_V(ScriptObject.class, name, rtype, types);
   }

   private static MethodHandle findOwnMH_S(String name, Class<?> rtype, Class<?>... types) {
      return Lookup.MH.findStatic(MethodHandles.lookup(), ScriptObject.class, name, Lookup.MH.type(rtype, types));
   }

   private static MethodHandle getKnownFunctionPropertyGuardSelf(PropertyMap map, MethodHandle getter, ScriptFunction func) {
      return Lookup.MH.insertArguments(KNOWNFUNCPROPGUARDSELF, 1, map, getter, func);
   }

   private static boolean knownFunctionPropertyGuardSelf(Object self, PropertyMap map, MethodHandle getter, ScriptFunction func) {
      if (self instanceof ScriptObject && ((ScriptObject)self).getMap() == map) {
         try {
            return getter.invokeExact(self) == func;
         } catch (Error | RuntimeException var5) {
            throw var5;
         } catch (Throwable var6) {
            throw new RuntimeException(var6);
         }
      } else {
         return false;
      }
   }

   private static MethodHandle getKnownFunctionPropertyGuardProto(PropertyMap map, MethodHandle getter, int depth, ScriptFunction func) {
      return Lookup.MH.insertArguments(KNOWNFUNCPROPGUARDPROTO, 1, map, getter, depth, func);
   }

   private static ScriptObject getProto(ScriptObject self, int depth) {
      ScriptObject proto = self;

      for(int d = 0; d < depth; ++d) {
         proto = proto.getProto();
         if (proto == null) {
            return null;
         }
      }

      return proto;
   }

   private static boolean knownFunctionPropertyGuardProto(Object self, PropertyMap map, MethodHandle getter, int depth, ScriptFunction func) {
      if (self instanceof ScriptObject && ((ScriptObject)self).getMap() == map) {
         ScriptObject proto = getProto((ScriptObject)self, depth);
         if (proto == null) {
            return false;
         } else {
            try {
               return getter.invokeExact(proto) == func;
            } catch (Error | RuntimeException var7) {
               throw var7;
            } catch (Throwable var8) {
               throw new RuntimeException(var8);
            }
         }
      } else {
         return false;
      }
   }

   public static long getCount() {
      return count.longValue();
   }

   static {
      MEGAMORPHIC_GET = findOwnMH_V("megamorphicGet", Object.class, String.class, Boolean.TYPE, Boolean.TYPE);
      GLOBALFILTER = findOwnMH_S("globalFilter", Object.class, Object.class);
      DECLARE_AND_SET = findOwnMH_V("declareAndSet", Void.TYPE, String.class, Object.class);
      TRUNCATINGFILTER = findOwnMH_S("truncatingFilter", Object[].class, Integer.TYPE, Object[].class);
      KNOWNFUNCPROPGUARDSELF = findOwnMH_S("knownFunctionPropertyGuardSelf", Boolean.TYPE, Object.class, PropertyMap.class, MethodHandle.class, ScriptFunction.class);
      KNOWNFUNCPROPGUARDPROTO = findOwnMH_S("knownFunctionPropertyGuardProto", Boolean.TYPE, Object.class, PropertyMap.class, MethodHandle.class, Integer.TYPE, ScriptFunction.class);
      PROTO_FILTERS = new ArrayList();
      GET_ARRAY = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "getArray", ArrayData.class);
      GET_ARGUMENT = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "getArgument", Object.class, Integer.TYPE);
      SET_ARGUMENT = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "setArgument", Void.TYPE, Integer.TYPE, Object.class);
      GET_PROTO = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "getProto", ScriptObject.class);
      GET_PROTO_DEPTH = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "getProto", ScriptObject.class, Integer.TYPE);
      SET_GLOBAL_OBJECT_PROTO = CompilerConstants.staticCallNoLookup(ScriptObject.class, "setGlobalObjectProto", Void.TYPE, ScriptObject.class);
      SET_PROTO_FROM_LITERAL = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "setProtoFromLiteral", Void.TYPE, Object.class);
      SET_USER_ACCESSORS = CompilerConstants.virtualCall(MethodHandles.lookup(), ScriptObject.class, "setUserAccessors", Void.TYPE, String.class, ScriptFunction.class, ScriptFunction.class);
      SET_SLOW = new MethodHandle[]{findOwnMH_V("set", Void.TYPE, Object.class, Integer.TYPE, Integer.TYPE), findOwnMH_V("set", Void.TYPE, Object.class, Double.TYPE, Integer.TYPE), findOwnMH_V("set", Void.TYPE, Object.class, Object.class, Integer.TYPE)};
      SET_MAP = CompilerConstants.virtualCallNoLookup(ScriptObject.class, "setMap", Void.TYPE, PropertyMap.class);
      CAS_MAP = findOwnMH_V("compareAndSetMap", Boolean.TYPE, PropertyMap.class, PropertyMap.class);
      EXTENSION_CHECK = findOwnMH_V("extensionCheck", Boolean.TYPE, Boolean.TYPE, String.class);
      ENSURE_SPILL_SIZE = findOwnMH_V("ensureSpillSize", Object.class, Integer.TYPE);
      if (Context.DEBUG) {
         count = new LongAdder();
      }

   }

   private static class ValueIterator extends ScriptObject.ScriptObjectIterator<Object> {
      ValueIterator(ScriptObject object) {
         super(object);
      }

      protected void init() {
         ArrayList<Object> valueList = new ArrayList();
         Set<String> nonEnumerable = new HashSet();

         for(ScriptObject self = this.object; self != null; self = self.getProto()) {
            String[] var4 = self.getOwnKeys(false, nonEnumerable);
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String key = var4[var6];
               valueList.add(self.get(key));
            }
         }

         this.values = valueList.toArray(new Object[valueList.size()]);
      }
   }

   private static class KeyIterator extends ScriptObject.ScriptObjectIterator<String> {
      KeyIterator(ScriptObject object) {
         super(object);
      }

      protected void init() {
         Set<String> keys = new LinkedHashSet();
         Set<String> nonEnumerable = new HashSet();

         for(ScriptObject self = this.object; self != null; self = self.getProto()) {
            keys.addAll(Arrays.asList(self.getOwnKeys(false, nonEnumerable)));
         }

         this.values = keys.toArray(new String[keys.size()]);
      }
   }

   private abstract static class ScriptObjectIterator<T> implements Iterator<T> {
      protected T[] values;
      protected final ScriptObject object;
      private int index;

      ScriptObjectIterator(ScriptObject object) {
         this.object = object;
      }

      protected abstract void init();

      public boolean hasNext() {
         if (this.values == null) {
            this.init();
         }

         return this.index < this.values.length;
      }

      public T next() {
         if (this.values == null) {
            this.init();
         }

         return this.values[this.index++];
      }

      public void remove() {
         throw new UnsupportedOperationException("remove");
      }
   }
}
