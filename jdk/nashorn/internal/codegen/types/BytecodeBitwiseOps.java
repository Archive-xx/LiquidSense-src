package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

interface BytecodeBitwiseOps {
   Type shr(MethodVisitor var1);

   Type sar(MethodVisitor var1);

   Type shl(MethodVisitor var1);

   Type and(MethodVisitor var1);

   Type or(MethodVisitor var1);

   Type xor(MethodVisitor var1);

   Type cmp(MethodVisitor var1);
}
