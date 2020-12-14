package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;

class IntType extends BitwiseType {
   private static final long serialVersionUID = 1L;
   private static final CompilerConstants.Call TO_STRING;
   private static final CompilerConstants.Call VALUE_OF;

   protected IntType() {
      super("int", Integer.TYPE, 2, 1);
   }

   public Type nextWider() {
      return NUMBER;
   }

   public Class<?> getBoxedType() {
      return Integer.class;
   }

   public char getBytecodeStackType() {
      return 'I';
   }

   public Type ldc(MethodVisitor method, Object c) {
      assert c instanceof Integer;

      int value = (Integer)c;
      switch(value) {
      case -1:
         method.visitInsn(2);
         break;
      case 0:
         method.visitInsn(3);
         break;
      case 1:
         method.visitInsn(4);
         break;
      case 2:
         method.visitInsn(5);
         break;
      case 3:
         method.visitInsn(6);
         break;
      case 4:
         method.visitInsn(7);
         break;
      case 5:
         method.visitInsn(8);
         break;
      default:
         if (value == (byte)value) {
            method.visitIntInsn(16, value);
         } else if (value == (short)value) {
            method.visitIntInsn(17, value);
         } else {
            method.visitLdcInsn(c);
         }
      }

      return Type.INT;
   }

   public Type convert(MethodVisitor method, Type to) {
      if (to.isEquivalentTo(this)) {
         return to;
      } else {
         if (to.isNumber()) {
            method.visitInsn(135);
         } else if (to.isLong()) {
            method.visitInsn(133);
         } else if (!to.isBoolean()) {
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

   public Type shr(MethodVisitor method) {
      method.visitInsn(124);
      return INT;
   }

   public Type sar(MethodVisitor method) {
      method.visitInsn(122);
      return INT;
   }

   public Type shl(MethodVisitor method) {
      method.visitInsn(120);
      return INT;
   }

   public Type and(MethodVisitor method) {
      method.visitInsn(126);
      return INT;
   }

   public Type or(MethodVisitor method) {
      method.visitInsn(128);
      return INT;
   }

   public Type xor(MethodVisitor method) {
      method.visitInsn(130);
      return INT;
   }

   public Type load(MethodVisitor method, int slot) {
      assert slot != -1;

      method.visitVarInsn(21, slot);
      return INT;
   }

   public void store(MethodVisitor method, int slot) {
      assert slot != -1;

      method.visitVarInsn(54, slot);
   }

   public Type sub(MethodVisitor method, int programPoint) {
      if (programPoint == -1) {
         method.visitInsn(100);
      } else {
         method.visitInvokeDynamicInsn("isub", "(II)I", MATHBOOTSTRAP, new Object[]{programPoint});
      }

      return INT;
   }

   public Type mul(MethodVisitor method, int programPoint) {
      if (programPoint == -1) {
         method.visitInsn(104);
      } else {
         method.visitInvokeDynamicInsn("imul", "(II)I", MATHBOOTSTRAP, new Object[]{programPoint});
      }

      return INT;
   }

   public Type div(MethodVisitor method, int programPoint) {
      if (programPoint == -1) {
         JSType.DIV_ZERO.invoke(method);
      } else {
         method.visitInvokeDynamicInsn("idiv", "(II)I", MATHBOOTSTRAP, new Object[]{programPoint});
      }

      return INT;
   }

   public Type rem(MethodVisitor method, int programPoint) {
      if (programPoint == -1) {
         JSType.REM_ZERO.invoke(method);
      } else {
         method.visitInvokeDynamicInsn("irem", "(II)I", MATHBOOTSTRAP, new Object[]{programPoint});
      }

      return INT;
   }

   public Type neg(MethodVisitor method, int programPoint) {
      if (programPoint == -1) {
         method.visitInsn(116);
      } else {
         method.visitInvokeDynamicInsn("ineg", "(I)I", MATHBOOTSTRAP, new Object[]{programPoint});
      }

      return INT;
   }

   public void _return(MethodVisitor method) {
      method.visitInsn(172);
   }

   public Type loadUndefined(MethodVisitor method) {
      method.visitLdcInsn(0);
      return INT;
   }

   public Type loadForcedInitializer(MethodVisitor method) {
      method.visitInsn(3);
      return INT;
   }

   public Type cmp(MethodVisitor method, boolean isCmpG) {
      throw new UnsupportedOperationException("cmp" + (isCmpG ? 'g' : 'l'));
   }

   public Type cmp(MethodVisitor method) {
      throw new UnsupportedOperationException("cmp");
   }

   static {
      TO_STRING = CompilerConstants.staticCallNoLookup(Integer.class, "toString", String.class, Integer.TYPE);
      VALUE_OF = CompilerConstants.staticCallNoLookup(Integer.class, "valueOf", Integer.class, Integer.TYPE);
   }
}
