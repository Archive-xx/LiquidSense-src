package jdk.internal.dynalink.linker;

public interface GuardingTypeConverterFactory {
   GuardedTypeConversion convertToType(Class<?> var1, Class<?> var2) throws Exception;
}
