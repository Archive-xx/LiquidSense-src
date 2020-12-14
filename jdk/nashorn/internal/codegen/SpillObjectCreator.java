package jdk.nashorn.internal.codegen;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.LiteralNode;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.scripts.JD;
import jdk.nashorn.internal.scripts.JO;

public final class SpillObjectCreator extends ObjectCreator<Expression> {
   SpillObjectCreator(CodeGenerator codegen, List<MapTuple<Expression>> tuples) {
      super(codegen, tuples, false, false);
      this.makeMap();
   }

   public void createObject(MethodEmitter method) {
      assert !this.isScope() : "spill scope objects are not currently supported";

      int length = this.tuples.size();
      boolean dualFields = this.codegen.useDualFields();
      int spillLength = ScriptObject.spillAllocationLength(length);
      long[] jpresetValues = dualFields ? new long[spillLength] : null;
      Object[] opresetValues = new Object[spillLength];
      Class<?> objectClass = this.getAllocatorClass();
      ArrayData arrayData = ArrayData.allocate(ScriptRuntime.EMPTY_ARRAY);
      int pos = 0;

      for(Iterator var10 = this.tuples.iterator(); var10.hasNext(); ++pos) {
         MapTuple<Expression> tuple = (MapTuple)var10.next();
         String key = tuple.key;
         Expression value = (Expression)tuple.value;
         method.invalidateSpecialName(tuple.key);
         if (value != null) {
            Object constantValue = LiteralNode.objectAsConstant(value);
            if (constantValue != LiteralNode.POSTSET_MARKER) {
               Property property = this.propertyMap.findProperty(key);
               if (property != null) {
                  property.setType(dualFields ? JSType.unboxedFieldType(constantValue) : Object.class);
                  int slot = property.getSlot();
                  if (dualFields && constantValue instanceof Number) {
                     jpresetValues[slot] = ObjectClassGenerator.pack((Number)constantValue);
                  } else {
                     opresetValues[slot] = constantValue;
                  }
               } else {
                  long oldLength = arrayData.length();
                  int index = ArrayIndex.getArrayIndex(key);
                  long longIndex = ArrayIndex.toLongIndex(index);

                  assert ArrayIndex.isValidArrayIndex(index);

                  if (longIndex >= oldLength) {
                     arrayData = arrayData.ensure(longIndex);
                  }

                  if (constantValue instanceof Integer) {
                     arrayData = arrayData.set(index, (Integer)constantValue, false);
                  } else if (constantValue instanceof Double) {
                     arrayData = arrayData.set(index, (Double)constantValue, false);
                  } else {
                     arrayData = arrayData.set(index, constantValue, false);
                  }

                  if (longIndex > oldLength) {
                     arrayData = arrayData.delete(oldLength, longIndex - 1L);
                  }
               }
            }
         }
      }

      method._new(objectClass).dup();
      this.codegen.loadConstant((Object)this.propertyMap);
      if (dualFields) {
         this.codegen.loadConstant((Object)jpresetValues);
      } else {
         method.loadNull();
      }

      this.codegen.loadConstant((Object)opresetValues);
      method.invoke(CompilerConstants.constructorNoLookup(objectClass, PropertyMap.class, long[].class, Object[].class));
      if (arrayData.length() > 0L) {
         method.dup();
         this.codegen.loadConstant((Object)arrayData);
         method.invoke(CompilerConstants.virtualCallNoLookup(ScriptObject.class, "setArray", Void.TYPE, ArrayData.class));
      }

   }

   public void populateRange(MethodEmitter method, Type objectType, int objectSlot, int start, int end) {
      int callSiteFlags = this.codegen.getCallSiteFlags();
      method.load(objectType, objectSlot);

      for(int i = start; i < end; ++i) {
         MapTuple<Expression> tuple = (MapTuple)this.tuples.get(i);
         if (!LiteralNode.isConstant(tuple.value)) {
            Property property = this.propertyMap.findProperty(tuple.key);
            if (property == null) {
               int index = ArrayIndex.getArrayIndex(tuple.key);

               assert ArrayIndex.isValidArrayIndex(index);

               method.dup();
               this.loadIndex(method, ArrayIndex.toLongIndex(index));
               this.loadTuple(method, tuple, false);
               method.dynamicSetIndex(callSiteFlags);
            } else {
               method.dup();
               this.loadTuple(method, tuple, false);
               method.dynamicSet(property.getKey(), this.codegen.getCallSiteFlags(), false);
            }
         }
      }

   }

   protected PropertyMap makeMap() {
      assert this.propertyMap == null : "property map already initialized";

      Class<? extends ScriptObject> clazz = this.getAllocatorClass();
      this.propertyMap = (new MapCreator(clazz, this.tuples)).makeSpillMap(false, this.codegen.useDualFields());
      return this.propertyMap;
   }

   protected void loadValue(Expression expr, Type type) {
      this.codegen.loadExpressionAsType(expr, Type.generic(type));
   }

   protected Class<? extends ScriptObject> getAllocatorClass() {
      return this.codegen.useDualFields() ? JD.class : JO.class;
   }
}
