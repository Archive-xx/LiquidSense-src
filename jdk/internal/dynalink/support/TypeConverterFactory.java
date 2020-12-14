package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardedTypeConversion;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;

public class TypeConverterFactory {
   private final GuardingTypeConverterFactory[] factories;
   private final ConversionComparator[] comparators;
   private final MethodTypeConversionStrategy autoConversionStrategy;
   private final ClassValue<ClassMap<MethodHandle>> converterMap = new ClassValue<ClassMap<MethodHandle>>() {
      protected ClassMap<MethodHandle> computeValue(final Class<?> sourceType) {
         return new ClassMap<MethodHandle>(TypeConverterFactory.getClassLoader(sourceType)) {
            protected MethodHandle computeValue(Class<?> targetType) {
               try {
                  return TypeConverterFactory.this.createConverter(sourceType, targetType);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new RuntimeException(var4);
               }
            }
         };
      }
   };
   private final ClassValue<ClassMap<MethodHandle>> converterIdentityMap = new ClassValue<ClassMap<MethodHandle>>() {
      protected ClassMap<MethodHandle> computeValue(final Class<?> sourceType) {
         return new ClassMap<MethodHandle>(TypeConverterFactory.getClassLoader(sourceType)) {
            protected MethodHandle computeValue(Class<?> targetType) {
               if (!TypeConverterFactory.canAutoConvert(sourceType, targetType)) {
                  MethodHandle converter = TypeConverterFactory.this.getCacheableTypeConverter(sourceType, targetType);
                  if (converter != TypeConverterFactory.IDENTITY_CONVERSION) {
                     return converter;
                  }
               }

               return TypeConverterFactory.IDENTITY_CONVERSION.asType(MethodType.methodType(targetType, sourceType));
            }
         };
      }
   };
   private final ClassValue<ClassMap<Boolean>> canConvert = new ClassValue<ClassMap<Boolean>>() {
      protected ClassMap<Boolean> computeValue(final Class<?> sourceType) {
         return new ClassMap<Boolean>(TypeConverterFactory.getClassLoader(sourceType)) {
            protected Boolean computeValue(Class<?> targetType) {
               try {
                  return TypeConverterFactory.this.getTypeConverterNull(sourceType, targetType) != null;
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new RuntimeException(var4);
               }
            }
         };
      }
   };
   static final MethodHandle IDENTITY_CONVERSION = MethodHandles.identity(Object.class);

   private static final ClassLoader getClassLoader(final Class<?> clazz) {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
         public ClassLoader run() {
            return clazz.getClassLoader();
         }
      }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
   }

   public TypeConverterFactory(Iterable<? extends GuardingTypeConverterFactory> factories, MethodTypeConversionStrategy autoConversionStrategy) {
      List<GuardingTypeConverterFactory> l = new LinkedList();
      List<ConversionComparator> c = new LinkedList();
      Iterator var5 = factories.iterator();

      while(var5.hasNext()) {
         GuardingTypeConverterFactory factory = (GuardingTypeConverterFactory)var5.next();
         l.add(factory);
         if (factory instanceof ConversionComparator) {
            c.add((ConversionComparator)factory);
         }
      }

      this.factories = (GuardingTypeConverterFactory[])l.toArray(new GuardingTypeConverterFactory[l.size()]);
      this.comparators = (ConversionComparator[])c.toArray(new ConversionComparator[c.size()]);
      this.autoConversionStrategy = autoConversionStrategy;
   }

   public MethodHandle asType(MethodHandle handle, MethodType fromType) {
      MethodHandle newHandle = handle;
      MethodType toType = handle.type();
      int l = toType.parameterCount();
      if (l != fromType.parameterCount()) {
         throw new WrongMethodTypeException("Parameter counts differ: " + handle.type() + " vs. " + fromType);
      } else {
         int pos = 0;
         List<MethodHandle> converters = new LinkedList();

         Class toRetType;
         for(int i = 0; i < l; ++i) {
            toRetType = fromType.parameterType(i);
            Class<?> toParamType = toType.parameterType(i);
            if (canAutoConvert(toRetType, toParamType)) {
               newHandle = applyConverters(newHandle, pos, converters);
            } else {
               MethodHandle converter = this.getTypeConverterNull(toRetType, toParamType);
               if (converter != null) {
                  if (converters.isEmpty()) {
                     pos = i;
                  }

                  converters.add(converter);
               } else {
                  newHandle = applyConverters(newHandle, pos, converters);
               }
            }
         }

         newHandle = applyConverters(newHandle, pos, converters);
         Class<?> fromRetType = fromType.returnType();
         toRetType = toType.returnType();
         MethodHandle converter;
         if (fromRetType != Void.TYPE && toRetType != Void.TYPE && !canAutoConvert(toRetType, fromRetType)) {
            converter = this.getTypeConverterNull(toRetType, fromRetType);
            if (converter != null) {
               newHandle = MethodHandles.filterReturnValue(newHandle, converter);
            }
         }

         converter = this.autoConversionStrategy != null ? this.autoConversionStrategy.asType(newHandle, fromType) : newHandle;
         return converter.asType(fromType);
      }
   }

   private static MethodHandle applyConverters(MethodHandle handle, int pos, List<MethodHandle> converters) {
      if (converters.isEmpty()) {
         return handle;
      } else {
         MethodHandle newHandle = MethodHandles.filterArguments(handle, pos, (MethodHandle[])converters.toArray(new MethodHandle[converters.size()]));
         converters.clear();
         return newHandle;
      }
   }

   public boolean canConvert(Class<?> from, Class<?> to) {
      return canAutoConvert(from, to) || (Boolean)((ClassMap)this.canConvert.get(from)).get(to);
   }

   public ConversionComparator.Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2) {
      ConversionComparator[] var4 = this.comparators;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ConversionComparator comparator = var4[var6];
         ConversionComparator.Comparison result = comparator.compareConversion(sourceType, targetType1, targetType2);
         if (result != ConversionComparator.Comparison.INDETERMINATE) {
            return result;
         }
      }

      if (TypeUtilities.isMethodInvocationConvertible(sourceType, targetType1)) {
         if (!TypeUtilities.isMethodInvocationConvertible(sourceType, targetType2)) {
            return ConversionComparator.Comparison.TYPE_1_BETTER;
         }
      } else if (TypeUtilities.isMethodInvocationConvertible(sourceType, targetType2)) {
         return ConversionComparator.Comparison.TYPE_2_BETTER;
      }

      return ConversionComparator.Comparison.INDETERMINATE;
   }

   static boolean canAutoConvert(Class<?> fromType, Class<?> toType) {
      return TypeUtilities.isMethodInvocationConvertible(fromType, toType);
   }

   MethodHandle getCacheableTypeConverterNull(Class<?> sourceType, Class<?> targetType) {
      MethodHandle converter = this.getCacheableTypeConverter(sourceType, targetType);
      return converter == IDENTITY_CONVERSION ? null : converter;
   }

   MethodHandle getTypeConverterNull(Class<?> sourceType, Class<?> targetType) {
      try {
         return this.getCacheableTypeConverterNull(sourceType, targetType);
      } catch (TypeConverterFactory.NotCacheableConverter var4) {
         return var4.converter;
      }
   }

   MethodHandle getCacheableTypeConverter(Class<?> sourceType, Class<?> targetType) {
      return (MethodHandle)((ClassMap)this.converterMap.get(sourceType)).get(targetType);
   }

   public MethodHandle getTypeConverter(Class<?> sourceType, Class<?> targetType) {
      try {
         return (MethodHandle)((ClassMap)this.converterIdentityMap.get(sourceType)).get(targetType);
      } catch (TypeConverterFactory.NotCacheableConverter var4) {
         return var4.converter;
      }
   }

   MethodHandle createConverter(Class<?> sourceType, Class<?> targetType) throws Exception {
      MethodType type = MethodType.methodType(targetType, sourceType);
      MethodHandle identity = IDENTITY_CONVERSION.asType(type);
      MethodHandle last = identity;
      boolean cacheable = true;
      int i = this.factories.length;

      while(true) {
         GuardedTypeConversion next;
         do {
            if (i-- <= 0) {
               if (last == identity) {
                  return IDENTITY_CONVERSION;
               }

               if (cacheable) {
                  return last;
               }

               throw new TypeConverterFactory.NotCacheableConverter(last);
            }

            next = this.factories[i].convertToType(sourceType, targetType);
         } while(next == null);

         cacheable = cacheable && next.isCacheable();
         GuardedInvocation conversionInvocation = next.getConversionInvocation();
         conversionInvocation.assertType(type);
         last = conversionInvocation.compose(last);
      }
   }

   private static class NotCacheableConverter extends RuntimeException {
      final MethodHandle converter;

      NotCacheableConverter(MethodHandle converter) {
         super("", (Throwable)null, false, false);
         this.converter = converter;
      }
   }
}
