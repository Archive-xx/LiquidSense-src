package jdk.internal.dynalink.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.support.TypeUtilities;

public interface LinkerServices {
   MethodHandle asType(MethodHandle var1, MethodType var2);

   MethodHandle asTypeLosslessReturn(MethodHandle var1, MethodType var2);

   MethodHandle getTypeConverter(Class<?> var1, Class<?> var2);

   boolean canConvert(Class<?> var1, Class<?> var2);

   GuardedInvocation getGuardedInvocation(LinkRequest var1) throws Exception;

   ConversionComparator.Comparison compareConversion(Class<?> var1, Class<?> var2, Class<?> var3);

   MethodHandle filterInternalObjects(MethodHandle var1);

   public static class Implementation {
      public static MethodHandle asTypeLosslessReturn(LinkerServices linkerServices, MethodHandle handle, MethodType fromType) {
         Class<?> handleReturnType = handle.type().returnType();
         return linkerServices.asType(handle, TypeUtilities.isConvertibleWithoutLoss(handleReturnType, fromType.returnType()) ? fromType : fromType.changeReturnType(handleReturnType));
      }
   }
}
