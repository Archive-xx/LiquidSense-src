package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.Lookup;

class StaticClassLinker implements TypeBasedGuardingDynamicLinker {
   private static final ClassValue<StaticClassLinker.SingleClassStaticsLinker> linkers = new ClassValue<StaticClassLinker.SingleClassStaticsLinker>() {
      protected StaticClassLinker.SingleClassStaticsLinker computeValue(Class<?> clazz) {
         return new StaticClassLinker.SingleClassStaticsLinker(clazz);
      }
   };
   static final MethodHandle GET_CLASS;
   static final MethodHandle IS_CLASS;
   static final MethodHandle ARRAY_CTOR;

   static Object getConstructorMethod(Class<?> clazz, String signature) {
      return ((StaticClassLinker.SingleClassStaticsLinker)linkers.get(clazz)).getConstructorMethod(signature);
   }

   static Collection<String> getReadableStaticPropertyNames(Class<?> clazz) {
      return ((StaticClassLinker.SingleClassStaticsLinker)linkers.get(clazz)).getReadablePropertyNames();
   }

   static Collection<String> getWritableStaticPropertyNames(Class<?> clazz) {
      return ((StaticClassLinker.SingleClassStaticsLinker)linkers.get(clazz)).getWritablePropertyNames();
   }

   static Collection<String> getStaticMethodNames(Class<?> clazz) {
      return ((StaticClassLinker.SingleClassStaticsLinker)linkers.get(clazz)).getMethodNames();
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
      Object receiver = request.getReceiver();
      return receiver instanceof StaticClass ? ((StaticClassLinker.SingleClassStaticsLinker)linkers.get(((StaticClass)receiver).getRepresentedClass())).getGuardedInvocation(request, linkerServices) : null;
   }

   public boolean canLinkType(Class<?> type) {
      return type == StaticClass.class;
   }

   private static boolean isClass(Class<?> clazz, Object obj) {
      return obj instanceof StaticClass && ((StaticClass)obj).getRepresentedClass() == clazz;
   }

   static {
      ARRAY_CTOR = Lookup.PUBLIC.findStatic(Array.class, "newInstance", MethodType.methodType(Object.class, Class.class, Integer.TYPE));
      Lookup lookup = new Lookup(MethodHandles.lookup());
      GET_CLASS = lookup.findVirtual(StaticClass.class, "getRepresentedClass", MethodType.methodType(Class.class));
      IS_CLASS = lookup.findOwnStatic("isClass", Boolean.TYPE, Class.class, Object.class);
   }

   private static class SingleClassStaticsLinker extends AbstractJavaLinker {
      private final DynamicMethod constructor;

      SingleClassStaticsLinker(Class<?> clazz) {
         super(clazz, StaticClassLinker.IS_CLASS.bindTo(clazz));
         this.setPropertyGetter("class", StaticClassLinker.GET_CLASS, GuardedInvocationComponent.ValidationType.INSTANCE_OF);
         this.constructor = createConstructorMethod(clazz);
      }

      private static DynamicMethod createConstructorMethod(Class<?> clazz) {
         if (clazz.isArray()) {
            MethodHandle boundArrayCtor = StaticClassLinker.ARRAY_CTOR.bindTo(clazz.getComponentType());
            return new SimpleDynamicMethod(StaticClassIntrospector.editConstructorMethodHandle(boundArrayCtor.asType(boundArrayCtor.type().changeReturnType(clazz))), clazz, "<init>");
         } else {
            return CheckRestrictedPackage.isRestrictedClass(clazz) ? null : createDynamicMethod(Arrays.asList(clazz.getConstructors()), clazz, "<init>");
         }
      }

      FacetIntrospector createFacetIntrospector() {
         return new StaticClassIntrospector(this.clazz);
      }

      public GuardedInvocation getGuardedInvocation(LinkRequest request, LinkerServices linkerServices) throws Exception {
         GuardedInvocation gi = super.getGuardedInvocation(request, linkerServices);
         if (gi != null) {
            return gi;
         } else {
            CallSiteDescriptor desc = request.getCallSiteDescriptor();
            String op = desc.getNameToken(1);
            if ("new" == op && this.constructor != null) {
               MethodHandle ctorInvocation = this.constructor.getInvocation(desc, linkerServices);
               if (ctorInvocation != null) {
                  return new GuardedInvocation(ctorInvocation, this.getClassGuard(desc.getMethodType()));
               }
            }

            return null;
         }
      }

      SingleDynamicMethod getConstructorMethod(String signature) {
         return this.constructor != null ? this.constructor.getMethodForExactParamTypes(signature) : null;
      }
   }
}
