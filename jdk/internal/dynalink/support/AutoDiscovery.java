package jdk.internal.dynalink.support;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;

public class AutoDiscovery {
   private AutoDiscovery() {
   }

   public static List<GuardingDynamicLinker> loadLinkers() {
      return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class));
   }

   public static List<GuardingDynamicLinker> loadLinkers(ClassLoader cl) {
      return getLinkers(ServiceLoader.load(GuardingDynamicLinker.class, cl));
   }

   private static <T> List<T> getLinkers(ServiceLoader<T> loader) {
      List<T> list = new LinkedList();
      Iterator var2 = loader.iterator();

      while(var2.hasNext()) {
         T linker = var2.next();
         list.add(linker);
      }

      return list;
   }
}
