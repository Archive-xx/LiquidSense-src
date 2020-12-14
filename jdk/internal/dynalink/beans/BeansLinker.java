package jdk.internal.dynalink.beans;

import java.util.Collection;
import java.util.Collections;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;

public class BeansLinker implements GuardingDynamicLinker {
   private static final ClassValue<TypeBasedGuardingDynamicLinker> linkers = new ClassValue<TypeBasedGuardingDynamicLinker>() {
      protected TypeBasedGuardingDynamicLinker computeValue(Class<?> clazz) {
         return (TypeBasedGuardingDynamicLinker)(clazz == Class.class ? new ClassLinker() : (clazz == StaticClass.class ? new StaticClassLinker() : (DynamicMethod.class.isAssignableFrom(clazz) ? new DynamicMethodLinker() : new BeanLinker(clazz))));
      }
   };

   public static TypeBasedGuardingDynamicLinker getLinkerForClass(Class<?> clazz) {
      return (TypeBasedGuardingDynamicLinker)linkers.get(clazz);
   }

   public static boolean isDynamicMethod(Object obj) {
      return obj instanceof DynamicMethod;
   }

   public static boolean isDynamicConstructor(Object obj) {
      return obj instanceof DynamicMethod && ((DynamicMethod)obj).isConstructor();
   }

   public static Object getConstructorMethod(Class<?> clazz, String signature) {
      return StaticClassLinker.getConstructorMethod(clazz, signature);
   }

   public static Collection<String> getReadableInstancePropertyNames(Class<?> clazz) {
      TypeBasedGuardingDynamicLinker linker = getLinkerForClass(clazz);
      return (Collection)(linker instanceof BeanLinker ? ((BeanLinker)linker).getReadablePropertyNames() : Collections.emptySet());
   }

   public static Collection<String> getWritableInstancePropertyNames(Class<?> clazz) {
      TypeBasedGuardingDynamicLinker linker = getLinkerForClass(clazz);
      return (Collection)(linker instanceof BeanLinker ? ((BeanLinker)linker).getWritablePropertyNames() : Collections.emptySet());
   }

   public static Collection<String> getInstanceMethodNames(Class<?> clazz) {
      TypeBasedGuardingDynamicLinker linker = getLinkerForClass(clazz);
      return (Collection)(linker instanceof BeanLinker ? ((BeanLinker)linker).getMethodNames() : Collections.emptySet());
   }

   public static Collection<String> getReadableStaticPropertyNames(Class<?> clazz) {
      return StaticClassLinker.getReadableStaticPropertyNames(clazz);
   }

   public static Collection<String> getWritableStaticPropertyNames(Class<?> clazz) {
      return StaticClassLinker.getWritableStaticPropertyNames(clazz);
   }

   public static Collection<String> getStaticMethodNames(Class<?> clazz) {
      return StaticClassLinker.getStaticMethodNames(clazz);
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
      CallSiteDescriptor callSiteDescriptor = request.getCallSiteDescriptor();
      int l = callSiteDescriptor.getNameTokenCount();
      if (l >= 2 && "dyn" == callSiteDescriptor.getNameToken(0)) {
         Object receiver = request.getReceiver();
         return receiver == null ? null : getLinkerForClass(receiver.getClass()).getGuardedInvocation(request, linkerServices);
      } else {
         return null;
      }
   }
}
