package jdk.nashorn.internal.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Method;
import java.util.List;

public interface MethodHandleFunctionality {
   MethodHandle filterArguments(MethodHandle var1, int var2, MethodHandle... var3);

   MethodHandle filterReturnValue(MethodHandle var1, MethodHandle var2);

   MethodHandle guardWithTest(MethodHandle var1, MethodHandle var2, MethodHandle var3);

   MethodHandle insertArguments(MethodHandle var1, int var2, Object... var3);

   MethodHandle dropArguments(MethodHandle var1, int var2, Class<?>... var3);

   MethodHandle dropArguments(MethodHandle var1, int var2, List<Class<?>> var3);

   MethodHandle foldArguments(MethodHandle var1, MethodHandle var2);

   MethodHandle explicitCastArguments(MethodHandle var1, MethodType var2);

   MethodHandle arrayElementGetter(Class<?> var1);

   MethodHandle arrayElementSetter(Class<?> var1);

   MethodHandle throwException(Class<?> var1, Class<? extends Throwable> var2);

   MethodHandle catchException(MethodHandle var1, Class<? extends Throwable> var2, MethodHandle var3);

   MethodHandle constant(Class<?> var1, Object var2);

   MethodHandle identity(Class<?> var1);

   MethodHandle asType(MethodHandle var1, MethodType var2);

   MethodHandle asCollector(MethodHandle var1, Class<?> var2, int var3);

   MethodHandle asSpreader(MethodHandle var1, Class<?> var2, int var3);

   MethodHandle bindTo(MethodHandle var1, Object var2);

   MethodHandle getter(java.lang.invoke.MethodHandles.Lookup var1, Class<?> var2, String var3, Class<?> var4);

   MethodHandle staticGetter(java.lang.invoke.MethodHandles.Lookup var1, Class<?> var2, String var3, Class<?> var4);

   MethodHandle setter(java.lang.invoke.MethodHandles.Lookup var1, Class<?> var2, String var3, Class<?> var4);

   MethodHandle staticSetter(java.lang.invoke.MethodHandles.Lookup var1, Class<?> var2, String var3, Class<?> var4);

   MethodHandle find(Method var1);

   MethodHandle findStatic(java.lang.invoke.MethodHandles.Lookup var1, Class<?> var2, String var3, MethodType var4);

   MethodHandle findVirtual(java.lang.invoke.MethodHandles.Lookup var1, Class<?> var2, String var3, MethodType var4);

   MethodHandle findSpecial(java.lang.invoke.MethodHandles.Lookup var1, Class<?> var2, String var3, MethodType var4, Class<?> var5);

   SwitchPoint createSwitchPoint();

   MethodHandle guardWithTest(SwitchPoint var1, MethodHandle var2, MethodHandle var3);

   MethodType type(Class<?> var1, Class<?>... var2);
}
