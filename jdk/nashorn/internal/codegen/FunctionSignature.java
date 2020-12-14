package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ScriptFunction;

public final class FunctionSignature {
   private final Type[] paramTypes;
   private final Type returnType;
   private final String descriptor;
   private final MethodType methodType;

   public FunctionSignature(boolean hasSelf, boolean hasCallee, Type retType, List<? extends Expression> args) {
      this(hasSelf, hasCallee, retType, typeArray(args));
   }

   public FunctionSignature(boolean hasSelf, boolean hasCallee, Type retType, int nArgs) {
      this(hasSelf, hasCallee, retType, objectArgs(nArgs));
   }

   private FunctionSignature(boolean hasSelf, boolean hasCallee, Type retType, Type... argTypes) {
      int count = 1;
      boolean isVarArg;
      if (argTypes == null) {
         isVarArg = true;
      } else {
         isVarArg = argTypes.length > 250;
         count = isVarArg ? 1 : argTypes.length;
      }

      if (hasCallee) {
         ++count;
      }

      if (hasSelf) {
         ++count;
      }

      this.paramTypes = new Type[count];
      int next = 0;
      if (hasCallee) {
         this.paramTypes[next++] = Type.typeFor(ScriptFunction.class);
      }

      if (hasSelf) {
         this.paramTypes[next++] = Type.OBJECT;
      }

      if (isVarArg) {
         this.paramTypes[next] = Type.OBJECT_ARRAY;
      } else {
         Type type;
         if (argTypes != null) {
            for(int var8 = 0; next < count; this.paramTypes[next++] = type.isObject() ? Type.OBJECT : type) {
               type = argTypes[var8++];
            }
         } else {
            assert false : "isVarArgs cannot be false when argTypes are null";
         }
      }

      this.returnType = retType;
      this.descriptor = Type.getMethodDescriptor(this.returnType, this.paramTypes);
      List<Class<?>> paramTypeList = new ArrayList();
      Type[] var14 = this.paramTypes;
      int var10 = var14.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         Type paramType = var14[var11];
         paramTypeList.add(paramType.getTypeClass());
      }

      this.methodType = Lookup.MH.type(this.returnType.getTypeClass(), (Class[])paramTypeList.toArray(new Class[this.paramTypes.length]));
   }

   public FunctionSignature(FunctionNode functionNode) {
      this(true, functionNode.needsCallee(), functionNode.getReturnType(), functionNode.isVarArg() && !functionNode.isProgram() ? null : functionNode.getParameters());
   }

   private static Type[] typeArray(List<? extends Expression> args) {
      if (args == null) {
         return null;
      } else {
         Type[] typeArray = new Type[args.size()];
         int pos = 0;

         Expression arg;
         for(Iterator var3 = args.iterator(); var3.hasNext(); typeArray[pos++] = arg.getType()) {
            arg = (Expression)var3.next();
         }

         return typeArray;
      }
   }

   public String toString() {
      return this.descriptor;
   }

   public int size() {
      return this.paramTypes.length;
   }

   public Type[] getParamTypes() {
      return (Type[])this.paramTypes.clone();
   }

   public MethodType getMethodType() {
      return this.methodType;
   }

   public Type getReturnType() {
      return this.returnType;
   }

   private static Type[] objectArgs(int nArgs) {
      Type[] array = new Type[nArgs];

      for(int i = 0; i < nArgs; ++i) {
         array[i] = Type.OBJECT;
      }

      return array;
   }
}
