package jdk.nashorn.internal.runtime.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodHandleTransformer;
import jdk.internal.dynalink.support.DefaultInternalObjectFilter;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.options.Options;

public class NashornBeansLinker implements GuardingDynamicLinker {
   private static final boolean MIRROR_ALWAYS = Options.getBooleanProperty("nashorn.mirror.always", true);
   private static final MethodHandle EXPORT_ARGUMENT;
   private static final MethodHandle IMPORT_RESULT;
   private static final MethodHandle FILTER_CONSSTRING;
   private static final ClassValue<String> FUNCTIONAL_IFACE_METHOD_NAME;
   private final BeansLinker beansLinker = new BeansLinker();

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      Object self = linkRequest.getReceiver();
      CallSiteDescriptor desc = linkRequest.getCallSiteDescriptor();
      if (self instanceof ConsString) {
         Object[] arguments = linkRequest.getArguments();
         arguments[0] = "";
         LinkRequest forgedLinkRequest = linkRequest.replaceArguments(desc, arguments);
         GuardedInvocation invocation = getGuardedInvocation(this.beansLinker, forgedLinkRequest, linkerServices);
         return invocation == null ? null : invocation.filterArguments(0, FILTER_CONSSTRING);
      } else {
         if (self != null && "call".equals(desc.getNameToken(1))) {
            String name = getFunctionalInterfaceMethodName(self.getClass());
            if (name != null) {
               MethodType callType = desc.getMethodType();
               NashornCallSiteDescriptor newDesc = NashornCallSiteDescriptor.get(desc.getLookup(), "dyn:callMethod:" + name, desc.getMethodType().dropParameterTypes(1, 2), NashornCallSiteDescriptor.getFlags(desc));
               GuardedInvocation gi = getGuardedInvocation(this.beansLinker, linkRequest.replaceArguments(newDesc, linkRequest.getArguments()), new NashornBeansLinker.NashornBeansLinkerServices(linkerServices));
               return gi.replaceMethods(Lookup.MH.dropArguments(linkerServices.filterInternalObjects(gi.getInvocation()), 1, (Class[])(callType.parameterType(1))), gi.getGuard());
            }
         }

         return getGuardedInvocation(this.beansLinker, linkRequest, linkerServices);
      }
   }

   public static GuardedInvocation getGuardedInvocation(GuardingDynamicLinker delegateLinker, LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      return delegateLinker.getGuardedInvocation(linkRequest, new NashornBeansLinker.NashornBeansLinkerServices(linkerServices));
   }

   private static Object exportArgument(Object arg) {
      return exportArgument(arg, MIRROR_ALWAYS);
   }

   static Object exportArgument(Object arg, boolean mirrorAlways) {
      if (arg instanceof ConsString) {
         return arg.toString();
      } else {
         return mirrorAlways && arg instanceof ScriptObject ? ScriptUtils.wrap((ScriptObject)arg) : arg;
      }
   }

   private static Object importResult(Object arg) {
      return ScriptUtils.unwrap(arg);
   }

   private static Object consStringFilter(Object arg) {
      return arg instanceof ConsString ? arg.toString() : arg;
   }

   private static String findFunctionalInterfaceMethodName(Class<?> clazz) {
      if (clazz == null) {
         return null;
      } else {
         Class[] var1 = clazz.getInterfaces();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Class<?> iface = var1[var3];
            if (Context.isAccessibleClass(iface) && iface.isAnnotationPresent(FunctionalInterface.class)) {
               Method[] var5 = iface.getMethods();
               int var6 = var5.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  Method m = var5[var7];
                  if (Modifier.isAbstract(m.getModifiers()) && !isOverridableObjectMethod(m)) {
                     return m.getName();
                  }
               }
            }
         }

         return findFunctionalInterfaceMethodName(clazz.getSuperclass());
      }
   }

   private static boolean isOverridableObjectMethod(Method m) {
      String var1 = m.getName();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1776922004:
         if (var1.equals("toString")) {
            var2 = 2;
         }
         break;
      case -1295482945:
         if (var1.equals("equals")) {
            var2 = 0;
         }
         break;
      case 147696667:
         if (var1.equals("hashCode")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         if (m.getReturnType() != Boolean.TYPE) {
            return false;
         }

         Class<?>[] params = m.getParameterTypes();
         return params.length == 1 && params[0] == Object.class;
      case 1:
         return m.getReturnType() == Integer.TYPE && m.getParameterCount() == 0;
      case 2:
         return m.getReturnType() == String.class && m.getParameterCount() == 0;
      default:
         return false;
      }
   }

   static String getFunctionalInterfaceMethodName(Class<?> clazz) {
      return (String)FUNCTIONAL_IFACE_METHOD_NAME.get(clazz);
   }

   static MethodHandleTransformer createHiddenObjectFilter() {
      return new DefaultInternalObjectFilter(EXPORT_ARGUMENT, MIRROR_ALWAYS ? IMPORT_RESULT : null);
   }

   static {
      jdk.internal.dynalink.support.Lookup lookup = new jdk.internal.dynalink.support.Lookup(MethodHandles.lookup());
      EXPORT_ARGUMENT = lookup.findOwnStatic("exportArgument", Object.class, Object.class);
      IMPORT_RESULT = lookup.findOwnStatic("importResult", Object.class, Object.class);
      FILTER_CONSSTRING = lookup.findOwnStatic("consStringFilter", Object.class, Object.class);
      FUNCTIONAL_IFACE_METHOD_NAME = new ClassValue<String>() {
         protected String computeValue(Class<?> type) {
            return NashornBeansLinker.findFunctionalInterfaceMethodName(type);
         }
      };
   }

   private static class NashornBeansLinkerServices implements LinkerServices {
      private final LinkerServices linkerServices;

      NashornBeansLinkerServices(LinkerServices linkerServices) {
         this.linkerServices = linkerServices;
      }

      public MethodHandle asType(MethodHandle handle, MethodType fromType) {
         return this.linkerServices.asType(handle, fromType);
      }

      public MethodHandle asTypeLosslessReturn(MethodHandle handle, MethodType fromType) {
         return LinkerServices.Implementation.asTypeLosslessReturn(this, handle, fromType);
      }

      public MethodHandle getTypeConverter(Class<?> sourceType, Class<?> targetType) {
         return this.linkerServices.getTypeConverter(sourceType, targetType);
      }

      public boolean canConvert(Class<?> from, Class<?> to) {
         return this.linkerServices.canConvert(from, to);
      }

      public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest) throws Exception {
         return this.linkerServices.getGuardedInvocation(linkRequest);
      }

      public ConversionComparator.Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2) {
         if (sourceType == ConsString.class) {
            if (String.class == targetType1 || CharSequence.class == targetType1) {
               return ConversionComparator.Comparison.TYPE_1_BETTER;
            }

            if (String.class == targetType2 || CharSequence.class == targetType2) {
               return ConversionComparator.Comparison.TYPE_2_BETTER;
            }
         }

         return this.linkerServices.compareConversion(sourceType, targetType1, targetType2);
      }

      public MethodHandle filterInternalObjects(MethodHandle target) {
         return this.linkerServices.filterInternalObjects(target);
      }
   }
}
