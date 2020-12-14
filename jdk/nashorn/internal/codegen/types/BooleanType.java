package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;

public final class BooleanType extends Type {
   private static final long serialVersionUID = 1L;
   private static final CompilerConstants.Call VALUE_OF;
   private static final CompilerConstants.Call TO_STRING;

   protected BooleanType() {
      super("boolean", Boolean.TYPE, 1, 1);
   }

   public Type nextWider() {
      return INT;
   }

   public Class<?> getBoxedType() {
      return Boolean.class;
   }

   public char getBytecodeStackType() {
      return 'I';
   }

   public Type loadUndefined(MethodVisitor method) {
      method.visitLdcInsn(0);
      return BOOLEAN;
   }

   public Type loadForcedInitializer(MethodVisitor method) {
      method.visitInsn(3);
      return BOOLEAN;
   }

   public void _return(MethodVisitor method) {
      method.visitInsn(172);
   }

   public Type load(MethodVisitor method, int slot) {
      assert slot != -1;

      method.visitVarInsn(21, slot);
      return BOOLEAN;
   }

   public void store(MethodVisitor method, int slot) {
      assert slot != -1;

      method.visitVarInsn(54, slot);
   }

   public Type ldc(MethodVisitor method, Object c) {
      assert c instanceof Boolean;

      method.visitInsn((Boolean)c ? 4 : 3);
      return BOOLEAN;
   }

   public Type convert(MethodVisitor method, Type to) {
      if (this.isEquivalentTo(to)) {
         return to;
      } else {
         if (to.isNumber()) {
            method.visitInsn(135);
         } else if (to.isLong()) {
            method.visitInsn(133);
         } else if (!to.isInteger()) {
            if (to.isString()) {
               invokestatic(method, TO_STRING);
            } else {
               if (!to.isObject()) {
                  throw new UnsupportedOperationException("Illegal conversion " + this + " -> " + to);
               }

               invokestatic(method, VALUE_OF);
            }
         }

         return to;
      }
   }

   public Type add(MethodVisitor method, int programPoint) {
      if (programPoint == -1) {
         method.visitInsn(96);
      } else {
         method.visitInvokeDynamicInsn("iadd", "(II)I", MATHBOOTSTRAP, new Object[]{programPoint});
      }

      return INT;
   }

   static {
      VALUE_OF = CompilerConstants.staticCallNoLookup(Boolean.class, "valueOf", Boolean.class, Boolean.TYPE);
      TO_STRING = CompilerConstants.staticCallNoLookup(Boolean.class, "toString", String.class, Boolean.TYPE);
   }
}
