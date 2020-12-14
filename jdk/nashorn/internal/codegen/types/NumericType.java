package jdk.nashorn.internal.codegen.types;

public abstract class NumericType extends Type implements BytecodeNumericOps {
   private static final long serialVersionUID = 1L;

   protected NumericType(String name, Class<?> clazz, int weight, int slots) {
      super(name, clazz, weight, slots);
   }
}
