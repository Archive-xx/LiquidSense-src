package jdk.internal.dynalink;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.List;
import java.util.Objects;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.RuntimeContextLinkRequestImpl;

public class DynamicLinker {
   private static final String CLASS_NAME = DynamicLinker.class.getName();
   private static final String RELINK_METHOD_NAME = "relink";
   private static final String INITIAL_LINK_CLASS_NAME = "java.lang.invoke.MethodHandleNatives";
   private static final String INITIAL_LINK_METHOD_NAME = "linkCallSite";
   private final LinkerServices linkerServices;
   private final GuardedInvocationFilter prelinkFilter;
   private final int runtimeContextArgCount;
   private final boolean syncOnRelink;
   private final int unstableRelinkThreshold;
   private static final MethodHandle RELINK;

   DynamicLinker(LinkerServices linkerServices, GuardedInvocationFilter prelinkFilter, int runtimeContextArgCount, boolean syncOnRelink, int unstableRelinkThreshold) {
      if (runtimeContextArgCount < 0) {
         throw new IllegalArgumentException("runtimeContextArgCount < 0");
      } else if (unstableRelinkThreshold < 0) {
         throw new IllegalArgumentException("unstableRelinkThreshold < 0");
      } else {
         this.linkerServices = linkerServices;
         this.prelinkFilter = prelinkFilter;
         this.runtimeContextArgCount = runtimeContextArgCount;
         this.syncOnRelink = syncOnRelink;
         this.unstableRelinkThreshold = unstableRelinkThreshold;
      }
   }

   public <T extends RelinkableCallSite> T link(T callSite) {
      callSite.initialize(this.createRelinkAndInvokeMethod(callSite, 0));
      return callSite;
   }

   public LinkerServices getLinkerServices() {
      return this.linkerServices;
   }

   private MethodHandle createRelinkAndInvokeMethod(RelinkableCallSite callSite, int relinkCount) {
      MethodHandle boundRelinker = MethodHandles.insertArguments(RELINK, 0, new Object[]{this, callSite, relinkCount});
      MethodType type = callSite.getDescriptor().getMethodType();
      MethodHandle collectingRelinker = boundRelinker.asCollector(Object[].class, type.parameterCount());
      return MethodHandles.foldArguments(MethodHandles.exactInvoker(type), collectingRelinker.asType(type.changeReturnType(MethodHandle.class)));
   }

   private MethodHandle relink(RelinkableCallSite callSite, int relinkCount, Object... arguments) throws Exception {
      CallSiteDescriptor callSiteDescriptor = callSite.getDescriptor();
      boolean unstableDetectionEnabled = this.unstableRelinkThreshold > 0;
      boolean callSiteUnstable = unstableDetectionEnabled && relinkCount >= this.unstableRelinkThreshold;
      LinkRequest linkRequest = this.runtimeContextArgCount == 0 ? new LinkRequestImpl(callSiteDescriptor, callSite, relinkCount, callSiteUnstable, arguments) : new RuntimeContextLinkRequestImpl(callSiteDescriptor, callSite, relinkCount, callSiteUnstable, arguments, this.runtimeContextArgCount);
      GuardedInvocation guardedInvocation = this.linkerServices.getGuardedInvocation((LinkRequest)linkRequest);
      if (guardedInvocation == null) {
         throw new NoSuchDynamicMethodException(callSiteDescriptor.toString());
      } else {
         if (this.runtimeContextArgCount > 0) {
            MethodType origType = callSiteDescriptor.getMethodType();
            MethodHandle invocation = guardedInvocation.getInvocation();
            if (invocation.type().parameterCount() == origType.parameterCount() - this.runtimeContextArgCount) {
               List<Class<?>> prefix = origType.parameterList().subList(1, this.runtimeContextArgCount + 1);
               MethodHandle guard = guardedInvocation.getGuard();
               guardedInvocation = guardedInvocation.dropArguments(1, (List)prefix);
            }
         }

         label37: {
            guardedInvocation = this.prelinkFilter.filter(guardedInvocation, (LinkRequest)linkRequest, this.linkerServices);
            Objects.requireNonNull(guardedInvocation);
            int newRelinkCount = relinkCount;
            if (unstableDetectionEnabled && relinkCount <= this.unstableRelinkThreshold) {
               newRelinkCount = relinkCount + 1;
               if (relinkCount == this.unstableRelinkThreshold) {
                  callSite.resetAndRelink(guardedInvocation, this.createRelinkAndInvokeMethod(callSite, newRelinkCount));
                  break label37;
               }
            }

            callSite.relink(guardedInvocation, this.createRelinkAndInvokeMethod(callSite, newRelinkCount));
         }

         if (this.syncOnRelink) {
            MutableCallSite.syncAll(new MutableCallSite[]{(MutableCallSite)callSite});
         }

         return guardedInvocation.getInvocation();
      }
   }

   public static StackTraceElement getLinkedCallSiteLocation() {
      StackTraceElement[] trace = (new Throwable()).getStackTrace();

      for(int i = 0; i < trace.length - 1; ++i) {
         StackTraceElement frame = trace[i];
         if (isRelinkFrame(frame) || isInitialLinkFrame(frame)) {
            return trace[i + 1];
         }
      }

      return null;
   }

   /** @deprecated */
   @Deprecated
   public static StackTraceElement getRelinkedCallSiteLocation() {
      return getLinkedCallSiteLocation();
   }

   private static boolean isInitialLinkFrame(StackTraceElement frame) {
      return testFrame(frame, "linkCallSite", "java.lang.invoke.MethodHandleNatives");
   }

   private static boolean isRelinkFrame(StackTraceElement frame) {
      return testFrame(frame, "relink", CLASS_NAME);
   }

   private static boolean testFrame(StackTraceElement frame, String methodName, String className) {
      return methodName.equals(frame.getMethodName()) && className.equals(frame.getClassName());
   }

   static {
      RELINK = Lookup.findOwnSpecial(MethodHandles.lookup(), "relink", MethodHandle.class, RelinkableCallSite.class, Integer.TYPE, Object[].class);
   }
}
