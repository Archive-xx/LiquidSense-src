package jdk.nashorn.internal.ir;

import java.io.Serializable;
import java.util.List;
import jdk.nashorn.internal.codegen.CompileUnit;

public interface Splittable {
   List<Splittable.SplitRange> getSplitRanges();

   public static final class SplitRange implements CompileUnitHolder, Serializable {
      private static final long serialVersionUID = 1L;
      private final CompileUnit compileUnit;
      private final int low;
      private final int high;

      public SplitRange(CompileUnit compileUnit, int low, int high) {
         this.compileUnit = compileUnit;
         this.low = low;
         this.high = high;
      }

      public int getHigh() {
         return this.high;
      }

      public int getLow() {
         return this.low;
      }

      public CompileUnit getCompileUnit() {
         return this.compileUnit;
      }
   }
}
