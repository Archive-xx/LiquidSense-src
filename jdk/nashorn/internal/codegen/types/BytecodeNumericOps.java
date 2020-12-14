package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

interface BytecodeNumericOps {
   Type neg(MethodVisitor var1, int var2);

   Type sub(MethodVisitor var1, int var2);

   Type mul(MethodVisitor var1, int var2);

   Type div(MethodVisitor var1, int var2);

   Type rem(MethodVisitor var1, int var2);

   Type cmp(MethodVisitor var1, boolean var2);
}
