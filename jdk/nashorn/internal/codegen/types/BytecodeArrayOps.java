package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

interface BytecodeArrayOps {
   Type aload(MethodVisitor var1);

   void astore(MethodVisitor var1);

   Type arraylength(MethodVisitor var1);

   Type newarray(MethodVisitor var1);

   Type newarray(MethodVisitor var1, int var2);
}
