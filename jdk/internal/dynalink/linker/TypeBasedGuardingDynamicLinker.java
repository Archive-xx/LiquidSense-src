package jdk.internal.dynalink.linker;

public interface TypeBasedGuardingDynamicLinker extends GuardingDynamicLinker {
   boolean canLinkType(Class<?> var1);
}
