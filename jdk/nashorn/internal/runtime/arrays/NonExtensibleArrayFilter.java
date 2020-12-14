package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class NonExtensibleArrayFilter extends ArrayFilter {
   NonExtensibleArrayFilter(ArrayData underlying) {
      super(underlying);
   }

   public ArrayData copy() {
      return new NonExtensibleArrayFilter(this.underlying.copy());
   }

   public ArrayData slice(long from, long to) {
      return new NonExtensibleArrayFilter(this.underlying.slice(from, to));
   }

   private ArrayData extensionCheck(boolean strict, int index) {
      if (!strict) {
         return this;
      } else {
         throw ECMAErrors.typeError(Global.instance(), "object.non.extensible", String.valueOf(index), ScriptRuntime.safeToString(this));
      }
   }

   public ArrayData set(int index, Object value, boolean strict) {
      return this.has(index) ? this.underlying.set(index, value, strict) : this.extensionCheck(strict, index);
   }

   public ArrayData set(int index, int value, boolean strict) {
      return this.has(index) ? this.underlying.set(index, value, strict) : this.extensionCheck(strict, index);
   }

   public ArrayData set(int index, double value, boolean strict) {
      return this.has(index) ? this.underlying.set(index, value, strict) : this.extensionCheck(strict, index);
   }
}
