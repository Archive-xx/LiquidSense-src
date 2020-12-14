package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

class SealedArrayFilter extends ArrayFilter {
   SealedArrayFilter(ArrayData underlying) {
      super(underlying);
   }

   public ArrayData copy() {
      return new SealedArrayFilter(this.underlying.copy());
   }

   public ArrayData slice(long from, long to) {
      return this.getUnderlying().slice(from, to);
   }

   public boolean canDelete(int index, boolean strict) {
      return this.canDelete(ArrayIndex.toLongIndex(index), strict);
   }

   public boolean canDelete(long longIndex, boolean strict) {
      if (strict) {
         throw ECMAErrors.typeError("cant.delete.property", Long.toString(longIndex), "sealed array");
      } else {
         return false;
      }
   }

   public PropertyDescriptor getDescriptor(Global global, int index) {
      return global.newDataDescriptor(this.getObject(index), false, true, true);
   }
}
