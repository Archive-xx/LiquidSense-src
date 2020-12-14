package jdk.internal.dynalink.linker;

public interface ConversionComparator {
   ConversionComparator.Comparison compareConversion(Class<?> var1, Class<?> var2, Class<?> var3);

   public static enum Comparison {
      INDETERMINATE,
      TYPE_1_BETTER,
      TYPE_2_BETTER;
   }
}
