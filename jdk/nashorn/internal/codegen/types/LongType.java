package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;

class LongType extends Type {
   private static final long serialVersionUID = 1L;
   private static final CompilerConstants.Call VALUE_OF;

   protected LongType(String name) {
      super(name, Long.TYPE, 3, 2);
   }

   protected LongType() {
      this("long");
   }

   public Type nextWider() {
      return NUMBER;
   }

   public Class<?> getBoxedType() {
      return Long.class;
   }

   public char getBytecodeStackType() {
      return 'J';
   }

   public Type load(MethodVisitor method, int slot) {
      assert slot != -1;

      method.visitVarInsn(22, slot);
      return LONG;
   }

   public void store(MethodVisitor method, int slot) {
      assert slot != -1;

      method.visitVarInsn(55, slot);
   }

   public Type ldc(MethodVisitor method, Object c) {
      assert c instanceof Long;

      long value = (Long)c;
      if (value == 0L) {
         method.visitInsn(9);
      } else if (value == 1L) {
         method.visitInsn(10);
      } else {
         method.visitLdcInsn(c);
      }

      return Type.LONG;
   }

   public Type convert(MethodVisitor method, Type to) {
      if (this.isEquivalentTo(to)) {
         return to;
      } else {
         if (to.isNumber()) {
            method.visitInsn(138);
         } else if (to.isInteger()) {
            invokestatic(method, JSType.TO_INT32_L);
         } else if (to.isBoolean()) {
            method.visitInsn(136);
         } else if (to.isObject()) {
            invokestatic(method, VALUE_OF);
         } else {
            assert false : "Illegal conversion " + this + " -> " + to;
         }

         return to;
      }
   }

   public Type add(MethodVisitor method, int programPoint) {
      if (programPoint == -1) {
         method.visitInsn(97);
      } else {
         method.visitInvokeDynamicInsn("ladd", "(JJ)J", MATHBOOTSTRAP, new Object[]{programPoint});
      }

      return LONG;
   }

   public void _return(MethodVisitor method) {
      method.visitInsn(173);
   }

   public Type loadUndefined(MethodVisitor method) {
      method.visitLdcInsn(0L);
      return LONG;
   }

   public Type loadForcedInitializer(MethodVisitor method) {
      method.visitInsn(9);
      return LONG;
   }

   static {
      VALUE_OF = CompilerConstants.staticCallNoLookup(Long.class, "valueOf", Long.class, Long.TYPE);
   }
}
