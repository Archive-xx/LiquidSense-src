package jdk.nashorn.internal.codegen;

import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

public abstract class FieldObjectCreator<T> extends ObjectCreator<T> {
   private String fieldObjectClassName;
   private Class<? extends ScriptObject> fieldObjectClass;
   private int fieldCount;
   private int paddedFieldCount;
   private int paramCount;
   private final int callSiteFlags;
   private final boolean evalCode;

   FieldObjectCreator(CodeGenerator codegen, List<MapTuple<T>> tuples) {
      this(codegen, tuples, false, false);
   }

   FieldObjectCreator(CodeGenerator codegen, List<MapTuple<T>> tuples, boolean isScope, boolean hasArguments) {
      super(codegen, tuples, isScope, hasArguments);
      this.callSiteFlags = codegen.getCallSiteFlags();
      this.evalCode = codegen.isEvalCode();
      this.countFields();
      this.findClass();
   }

   public void createObject(MethodEmitter method) {
      this.makeMap();
      String className = this.getClassName();

      assert this.fieldObjectClass != null;

      method._new(this.fieldObjectClass).dup();
      this.loadMap(method);
      if (this.isScope()) {
         this.loadScope(method);
         if (this.hasArguments()) {
            method.loadCompilerConstant(CompilerConstants.ARGUMENTS);
            method.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class, ScriptObject.class, CompilerConstants.ARGUMENTS.type()));
         } else {
            method.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class, ScriptObject.class));
         }
      } else {
         method.invoke(CompilerConstants.constructorNoLookup(className, PropertyMap.class));
      }

   }

   public void populateRange(MethodEmitter method, Type objectType, int objectSlot, int start, int end) {
      method.load(objectType, objectSlot);

      for(int i = start; i < end; ++i) {
         MapTuple<T> tuple = (MapTuple)this.tuples.get(i);
         if (tuple.symbol != null && tuple.value != null) {
            int index = ArrayIndex.getArrayIndex(tuple.key);
            method.dup();
            if (!ArrayIndex.isValidArrayIndex(index)) {
               this.putField(method, tuple.key, tuple.symbol.getFieldIndex(), tuple);
            } else {
               this.putSlot(method, ArrayIndex.toLongIndex(index), tuple);
            }

            method.invalidateSpecialName(tuple.key);
         }
      }

   }

   protected PropertyMap makeMap() {
      assert this.propertyMap == null : "property map already initialized";

      this.propertyMap = this.newMapCreator(this.fieldObjectClass).makeFieldMap(this.hasArguments(), this.codegen.useDualFields(), this.fieldCount, this.paddedFieldCount, this.evalCode);
      return this.propertyMap;
   }

   private void putField(MethodEmitter method, String key, int fieldIndex, MapTuple<T> tuple) {
      Type fieldType = this.codegen.useDualFields() && tuple.isPrimitive() ? ObjectClassGenerator.PRIMITIVE_FIELD_TYPE : Type.OBJECT;
      String fieldClass = this.getClassName();
      String fieldName = ObjectClassGenerator.getFieldName(fieldIndex, fieldType);
      String fieldDesc = CompilerConstants.typeDescriptor(fieldType.getTypeClass());

      assert fieldName.equals(ObjectClassGenerator.getFieldName(fieldIndex, ObjectClassGenerator.PRIMITIVE_FIELD_TYPE)) || fieldType.isObject() : key + " object keys must store to L*-fields";

      assert fieldName.equals(ObjectClassGenerator.getFieldName(fieldIndex, Type.OBJECT)) || fieldType.isPrimitive() : key + " primitive keys must store to J*-fields";

      this.loadTuple(method, tuple, true);
      method.putField(fieldClass, fieldName, fieldDesc);
   }

   private void putSlot(MethodEmitter method, long index, MapTuple<T> tuple) {
      this.loadIndex(method, index);
      this.loadTuple(method, tuple, false);
      method.dynamicSetIndex(this.callSiteFlags);
   }

   private void findClass() {
      this.fieldObjectClassName = this.isScope() ? ObjectClassGenerator.getClassName(this.fieldCount, this.paramCount, this.codegen.useDualFields()) : ObjectClassGenerator.getClassName(this.paddedFieldCount, this.codegen.useDualFields());

      try {
         this.fieldObjectClass = Context.forStructureClass(Compiler.binaryName(this.fieldObjectClassName));
      } catch (ClassNotFoundException var2) {
         throw new AssertionError("Nashorn has encountered an internal error.  Structure can not be created.");
      }
   }

   protected Class<? extends ScriptObject> getAllocatorClass() {
      return this.fieldObjectClass;
   }

   String getClassName() {
      return this.fieldObjectClassName;
   }

   private void countFields() {
      Iterator var1 = this.tuples.iterator();

      while(true) {
         while(true) {
            MapTuple tuple;
            Symbol symbol;
            do {
               if (!var1.hasNext()) {
                  this.paddedFieldCount = ObjectClassGenerator.getPaddedFieldCount(this.fieldCount);
                  return;
               }

               tuple = (MapTuple)var1.next();
               symbol = tuple.symbol;
            } while(symbol == null);

            if (this.hasArguments() && symbol.isParam()) {
               symbol.setFieldIndex(this.paramCount++);
            } else if (!ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(tuple.key))) {
               symbol.setFieldIndex(this.fieldCount++);
            }
         }
      }
   }
}
