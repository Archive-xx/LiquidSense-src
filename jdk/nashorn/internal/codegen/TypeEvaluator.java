package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.ir.AccessNode;
import jdk.nashorn.internal.ir.CallNode;
import jdk.nashorn.internal.ir.Expression;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.IndexNode;
import jdk.nashorn.internal.ir.Optimistic;
import jdk.nashorn.internal.objects.ArrayBufferView;
import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

final class TypeEvaluator {
   private static final MethodType EMPTY_INVOCATION_TYPE = MethodType.methodType(Object.class, ScriptFunction.class, Object.class);
   private final Compiler compiler;
   private final ScriptObject runtimeScope;

   TypeEvaluator(Compiler compiler, ScriptObject runtimeScope) {
      this.compiler = compiler;
      this.runtimeScope = runtimeScope;
   }

   boolean hasStringPropertyIterator(Expression expr) {
      return this.evaluateSafely(expr) instanceof ScriptObject;
   }

   Type getOptimisticType(Optimistic node) {
      assert this.compiler.useOptimisticTypes();

      int programPoint = node.getProgramPoint();
      Type validType = this.compiler.getInvalidatedProgramPointType(programPoint);
      if (validType != null) {
         return validType;
      } else {
         Type mostOptimisticType = node.getMostOptimisticType();
         Type evaluatedType = this.getEvaluatedType(node);
         if (evaluatedType == null) {
            return mostOptimisticType;
         } else {
            if (evaluatedType.widerThan(mostOptimisticType)) {
               Type newValidType = !evaluatedType.isObject() && !evaluatedType.isBoolean() ? evaluatedType : Type.OBJECT;
               this.compiler.addInvalidatedProgramPoint(node.getProgramPoint(), newValidType);
            }

            return evaluatedType;
         }
      }
   }

   private static Type getPropertyType(ScriptObject sobj, String name) {
      FindProperty find = sobj.findProperty(name, true);
      if (find == null) {
         return null;
      } else {
         Property property = find.getProperty();
         Class<?> propertyClass = property.getType();
         if (propertyClass == null) {
            return null;
         } else if (propertyClass.isPrimitive()) {
            return Type.typeFor(propertyClass);
         } else {
            ScriptObject owner = find.getOwner();
            if (property.hasGetterFunction(owner)) {
               return Type.OBJECT;
            } else {
               Object value = property.needsDeclaration() ? ScriptRuntime.UNDEFINED : property.getObjectValue(owner, owner);
               return value == ScriptRuntime.UNDEFINED ? null : Type.typeFor(JSType.unboxedFieldType(value));
            }
         }
      }
   }

   void declareLocalSymbol(String symbolName) {
      assert this.compiler.useOptimisticTypes() && this.compiler.isOnDemandCompilation() && this.runtimeScope != null : "useOptimistic=" + this.compiler.useOptimisticTypes() + " isOnDemand=" + this.compiler.isOnDemandCompilation() + " scope=" + this.runtimeScope;

      if (this.runtimeScope.findProperty(symbolName, false) == null) {
         this.runtimeScope.addOwnProperty(symbolName, 7, ScriptRuntime.UNDEFINED);
      }

   }

   private Object evaluateSafely(Expression expr) {
      if (expr instanceof IdentNode) {
         return this.runtimeScope == null ? null : evaluatePropertySafely(this.runtimeScope, ((IdentNode)expr).getName());
      } else if (expr instanceof AccessNode) {
         AccessNode accessNode = (AccessNode)expr;
         Object base = this.evaluateSafely(accessNode.getBase());
         return !(base instanceof ScriptObject) ? null : evaluatePropertySafely((ScriptObject)base, accessNode.getProperty());
      } else {
         return null;
      }
   }

   private static Object evaluatePropertySafely(ScriptObject sobj, String name) {
      FindProperty find = sobj.findProperty(name, true);
      if (find == null) {
         return null;
      } else {
         Property property = find.getProperty();
         ScriptObject owner = find.getOwner();
         return property.hasGetterFunction(owner) ? null : property.getObjectValue(owner, owner);
      }
   }

   private Type getEvaluatedType(Optimistic expr) {
      if (expr instanceof IdentNode) {
         return this.runtimeScope == null ? null : getPropertyType(this.runtimeScope, ((IdentNode)expr).getName());
      } else {
         Object base;
         if (expr instanceof AccessNode) {
            AccessNode accessNode = (AccessNode)expr;
            base = this.evaluateSafely(accessNode.getBase());
            return !(base instanceof ScriptObject) ? null : getPropertyType((ScriptObject)base, accessNode.getProperty());
         } else {
            if (expr instanceof IndexNode) {
               IndexNode indexNode = (IndexNode)expr;
               base = this.evaluateSafely(indexNode.getBase());
               if (base instanceof NativeArray || base instanceof ArrayBufferView) {
                  return ((ScriptObject)base).getArray().getOptimisticType();
               }
            } else if (expr instanceof CallNode) {
               CallNode callExpr = (CallNode)expr;
               Expression fnExpr = callExpr.getFunction();
               if (fnExpr instanceof FunctionNode && this.compiler.getContext().getEnv()._lazy_compilation) {
                  FunctionNode fn = (FunctionNode)fnExpr;
                  if (callExpr.getArgs().isEmpty()) {
                     RecompilableScriptFunctionData data = this.compiler.getScriptFunctionData(fn.getId());
                     if (data != null) {
                        Type returnType = Type.typeFor(data.getReturnType(EMPTY_INVOCATION_TYPE, this.runtimeScope));
                        if (returnType == Type.BOOLEAN) {
                           return Type.OBJECT;
                        }

                        assert returnType == Type.INT || returnType == Type.NUMBER || returnType == Type.OBJECT;

                        return returnType;
                     }
                  }
               }
            }

            return null;
         }
      }
   }
}
