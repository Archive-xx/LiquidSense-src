package org.apache.log4j;

class CategoryKey {
   String name;
   int hashCache;

   CategoryKey(String name) {
      this.name = name;
      this.hashCache = name.hashCode();
   }

   public final int hashCode() {
      return this.hashCache;
   }

   public final boolean equals(Object rArg) {
      if (this == rArg) {
         return true;
      } else {
         return rArg != null && CategoryKey.class == rArg.getClass() ? this.name.equals(((CategoryKey)rArg).name) : false;
      }
   }
}
