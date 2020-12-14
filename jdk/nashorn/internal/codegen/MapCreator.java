package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.SpillProperty;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

public class MapCreator<T> {
   private final Class<?> structure;
   private final List<MapTuple<T>> tuples;

   MapCreator(Class<? extends ScriptObject> structure, List<MapTuple<T>> tuples) {
      this.structure = structure;
      this.tuples = tuples;
   }

   PropertyMap makeFieldMap(boolean hasArguments, boolean dualFields, int fieldCount, int fieldMaximum, boolean evalCode) {
      List<Property> properties = new ArrayList();

      assert this.tuples != null;

      Iterator var7 = this.tuples.iterator();

      while(var7.hasNext()) {
         MapTuple<T> tuple = (MapTuple)var7.next();
         String key = tuple.key;
         Symbol symbol = tuple.symbol;
         Class<?> initialType = dualFields ? tuple.getValueType() : Object.class;
         if (symbol != null && !ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(key))) {
            int flags = getPropertyFlags(symbol, hasArguments, evalCode, dualFields);
            Property property = new AccessorProperty(key, flags, this.structure, symbol.getFieldIndex(), initialType);
            properties.add(property);
         }
      }

      return PropertyMap.newMap(properties, this.structure.getName(), fieldCount, fieldMaximum, 0);
   }

   PropertyMap makeSpillMap(boolean hasArguments, boolean dualFields) {
      List<Property> properties = new ArrayList();
      int spillIndex = 0;

      assert this.tuples != null;

      Iterator var5 = this.tuples.iterator();

      while(var5.hasNext()) {
         MapTuple<T> tuple = (MapTuple)var5.next();
         String key = tuple.key;
         Symbol symbol = tuple.symbol;
         Class<?> initialType = dualFields ? tuple.getValueType() : Object.class;
         if (symbol != null && !ArrayIndex.isValidArrayIndex(ArrayIndex.getArrayIndex(key))) {
            int flags = getPropertyFlags(symbol, hasArguments, false, dualFields);
            properties.add(new SpillProperty(key, flags, spillIndex++, initialType));
         }
      }

      return PropertyMap.newMap(properties, this.structure.getName(), 0, 0, spillIndex);
   }

   static int getPropertyFlags(Symbol symbol, boolean hasArguments, boolean evalCode, boolean dualFields) {
      int flags = 0;
      if (symbol.isParam()) {
         flags |= 8;
      }

      if (hasArguments) {
         flags |= 16;
      }

      if (symbol.isScope() && !evalCode) {
         flags |= 4;
      }

      if (symbol.isFunctionDeclaration()) {
         flags |= 32;
      }

      if (symbol.isConst()) {
         flags |= 1;
      }

      if (symbol.isBlockScoped()) {
         flags |= 1024;
      }

      if (symbol.isBlockScoped() && symbol.isScope()) {
         flags |= 512;
      }

      if (dualFields) {
         flags |= 2048;
      }

      return flags;
   }
}
