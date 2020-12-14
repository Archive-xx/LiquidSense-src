package jdk.internal.dynalink.support;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import jdk.internal.dynalink.linker.ConversionComparator;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.MethodHandleTransformer;

public class LinkerServicesImpl implements LinkerServices {
   private static final RuntimePermission GET_CURRENT_LINK_REQUEST = new RuntimePermission("dynalink.getCurrentLinkRequest");
   private static final ThreadLocal<LinkRequest> threadLinkRequest = new ThreadLocal();
   private final TypeConverterFactory typeConverterFactory;
   private final GuardingDynamicLinker topLevelLinker;
   private final MethodHandleTransformer internalObjectsFilter;

   public LinkerServicesImpl(TypeConverterFactory typeConverterFactory, GuardingDynamicLinker topLevelLinker, MethodHandleTransformer internalObjectsFilter) {
      this.typeConverterFactory = typeConverterFactory;
      this.topLevelLinker = topLevelLinker;
      this.internalObjectsFilter = internalObjectsFilter;
   }

   public boolean canConvert(Class<?> from, Class<?> to) {
      return this.typeConverterFactory.canConvert(from, to);
   }

   public MethodHandle asType(MethodHandle handle, MethodType fromType) {
      return this.typeConverterFactory.asType(handle, fromType);
   }

   public MethodHandle asTypeLosslessReturn(MethodHandle handle, MethodType fromType) {
      return LinkerServices.Implementation.asTypeLosslessReturn(this, handle, fromType);
   }

   public MethodHandle getTypeConverter(Class<?> sourceType, Class<?> targetType) {
      return this.typeConverterFactory.getTypeConverter(sourceType, targetType);
   }

   public ConversionComparator.Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2) {
      return this.typeConverterFactory.compareConversion(sourceType, targetType1, targetType2);
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest) throws Exception {
      LinkRequest prevLinkRequest = (LinkRequest)threadLinkRequest.get();
      threadLinkRequest.set(linkRequest);

      GuardedInvocation var3;
      try {
         var3 = this.topLevelLinker.getGuardedInvocation(linkRequest, this);
      } finally {
         threadLinkRequest.set(prevLinkRequest);
      }

      return var3;
   }

   public MethodHandle filterInternalObjects(MethodHandle target) {
      return this.internalObjectsFilter != null ? this.internalObjectsFilter.transform(target) : target;
   }

   public static LinkRequest getCurrentLinkRequest() {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(GET_CURRENT_LINK_REQUEST);
      }

      return (LinkRequest)threadLinkRequest.get();
   }
}
