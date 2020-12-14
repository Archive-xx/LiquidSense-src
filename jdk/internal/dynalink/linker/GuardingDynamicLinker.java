package jdk.internal.dynalink.linker;

public interface GuardingDynamicLinker {
   GuardedInvocation getGuardedInvocation(LinkRequest var1, LinkerServices var2) throws Exception;
}
