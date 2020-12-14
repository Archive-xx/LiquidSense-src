package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

interface BytecodeOps {
   Type dup(MethodVisitor var1, int var2);

   Type pop(MethodVisitor var1);

   Type swap(MethodVisitor var1, Type var2);

   Type add(MethodVisitor var1, int var2);

   Type load(MethodVisitor var1, int var2);

   void store(MethodVisitor var1, int var2);

   Type ldc(MethodVisitor var1, Object var2);

   Type loadUndefined(MethodVisitor var1);

   Type loadForcedInitializer(MethodVisitor var1);

   Type loadEmpty(MethodVisitor var1);

   Type convert(MethodVisitor var1, Type var2);

   void _return(MethodVisitor var1);
}
