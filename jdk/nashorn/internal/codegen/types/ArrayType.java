package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

public class ArrayType extends ObjectType implements BytecodeArrayOps {
   private static final long serialVersionUID = 1L;

   protected ArrayType(Class<?> clazz) {
      super(clazz);
   }

   public Type getElementType() {
      return Type.typeFor(this.getTypeClass().getComponentType());
   }

   public void astore(MethodVisitor method) {
      method.visitInsn(83);
   }

   public Type aload(MethodVisitor method) {
      method.visitInsn(50);
      return this.getElementType();
   }

   public Type arraylength(MethodVisitor method) {
      method.visitInsn(190);
      return INT;
   }

   public Type newarray(MethodVisitor method) {
      method.visitTypeInsn(189, this.getElementType().getInternalName());
      return this;
   }

   public Type newarray(MethodVisitor method, int dims) {
      method.visitMultiANewArrayInsn(this.getInternalName(), dims);
      return this;
   }

   public Type load(MethodVisitor method, int slot) {
      assert slot != -1;

      method.visitVarInsn(25, slot);
      return this;
   }

   public String toString() {
      return "array<elementType=" + this.getElementType().getTypeClass().getSimpleName() + '>';
   }

   public Type convert(MethodVisitor method, Type to) {
      assert to.isObject();

      assert !to.isArray() || ((ArrayType)to).getElementType() == this.getElementType();

      return to;
   }
}
