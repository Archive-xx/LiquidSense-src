package jdk.internal.dynalink.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public interface MethodTypeConversionStrategy {
   MethodHandle asType(MethodHandle var1, MethodType var2);
}
