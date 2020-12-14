package jdk.internal.dynalink.support;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;

public class CompositeGuardingDynamicLinker implements GuardingDynamicLinker, Serializable {
   private static final long serialVersionUID = 1L;
   private final GuardingDynamicLinker[] linkers;

   public CompositeGuardingDynamicLinker(Iterable<? extends GuardingDynamicLinker> linkers) {
      List<GuardingDynamicLinker> l = new LinkedList();
      Iterator var3 = linkers.iterator();

      while(var3.hasNext()) {
         GuardingDynamicLinker linker = (GuardingDynamicLinker)var3.next();
         l.add(linker);
      }

      this.linkers = (GuardingDynamicLinker[])l.toArray(new GuardingDynamicLinker[l.size()]);
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      GuardingDynamicLinker[] var3 = this.linkers;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         GuardingDynamicLinker linker = var3[var5];
         GuardedInvocation invocation = linker.getGuardedInvocation(linkRequest, linkerServices);
         if (invocation != null) {
            return invocation;
         }
      }

      return null;
   }
}
