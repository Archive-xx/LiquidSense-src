package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import jdk.nashorn.internal.runtime.PropertyMap;

final class ConstantData {
   final List<Object> constants = new ArrayList();
   final Map<String, Integer> stringMap = new HashMap();
   final Map<Object, Integer> objectMap = new HashMap();

   public int add(String string) {
      Integer value = (Integer)this.stringMap.get(string);
      if (value != null) {
         return value;
      } else {
         this.constants.add(string);
         int index = this.constants.size() - 1;
         this.stringMap.put(string, index);
         return index;
      }
   }

   public int add(Object object) {
      assert object != null;

      Object entry;
      if (object.getClass().isArray()) {
         entry = new ConstantData.ArrayWrapper(object);
      } else if (object instanceof PropertyMap) {
         entry = new ConstantData.PropertyMapWrapper((PropertyMap)object);
      } else {
         entry = object;
      }

      Integer value = (Integer)this.objectMap.get(entry);
      if (value != null) {
         return value;
      } else {
         this.constants.add(object);
         int index = this.constants.size() - 1;
         this.objectMap.put(entry, index);
         return index;
      }
   }

   Object[] toArray() {
      return this.constants.toArray();
   }

   private static class PropertyMapWrapper {
      private final PropertyMap propertyMap;
      private final int hashCode;

      public PropertyMapWrapper(PropertyMap map) {
         this.hashCode = Arrays.hashCode(map.getProperties()) + 31 * Objects.hashCode(map.getClassName());
         this.propertyMap = map;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object other) {
         if (!(other instanceof ConstantData.PropertyMapWrapper)) {
            return false;
         } else {
            PropertyMap otherMap = ((ConstantData.PropertyMapWrapper)other).propertyMap;
            return this.propertyMap == otherMap || Arrays.equals(this.propertyMap.getProperties(), otherMap.getProperties()) && Objects.equals(this.propertyMap.getClassName(), otherMap.getClassName());
         }
      }
   }

   private static class ArrayWrapper {
      private final Object array;
      private final int hashCode;

      public ArrayWrapper(Object array) {
         this.array = array;
         this.hashCode = this.calcHashCode();
      }

      private int calcHashCode() {
         Class<?> cls = this.array.getClass();
         if (!cls.getComponentType().isPrimitive()) {
            return Arrays.hashCode((Object[])((Object[])this.array));
         } else if (cls == double[].class) {
            return Arrays.hashCode((double[])((double[])this.array));
         } else if (cls == long[].class) {
            return Arrays.hashCode((long[])((long[])this.array));
         } else if (cls == int[].class) {
            return Arrays.hashCode((int[])((int[])this.array));
         } else {
            throw new AssertionError("ConstantData doesn't support " + cls);
         }
      }

      public boolean equals(Object other) {
         if (!(other instanceof ConstantData.ArrayWrapper)) {
            return false;
         } else {
            Object otherArray = ((ConstantData.ArrayWrapper)other).array;
            if (this.array == otherArray) {
               return true;
            } else {
               Class<?> cls = this.array.getClass();
               if (cls == otherArray.getClass()) {
                  if (!cls.getComponentType().isPrimitive()) {
                     return Arrays.equals((Object[])((Object[])this.array), (Object[])((Object[])otherArray));
                  }

                  if (cls == double[].class) {
                     return Arrays.equals((double[])((double[])this.array), (double[])((double[])otherArray));
                  }

                  if (cls == long[].class) {
                     return Arrays.equals((long[])((long[])this.array), (long[])((long[])otherArray));
                  }

                  if (cls == int[].class) {
                     return Arrays.equals((int[])((int[])this.array), (int[])((int[])otherArray));
                  }
               }

               return false;
            }
         }
      }

      public int hashCode() {
         return this.hashCode;
      }
   }
}
