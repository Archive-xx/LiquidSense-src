package jdk.internal.dynalink.support;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;

public class CompositeTypeBasedGuardingDynamicLinker implements TypeBasedGuardingDynamicLinker, Serializable {
   private static final long serialVersionUID = 1L;
   private final ClassValue<List<TypeBasedGuardingDynamicLinker>> classToLinker;

   public CompositeTypeBasedGuardingDynamicLinker(Iterable<? extends TypeBasedGuardingDynamicLinker> linkers) {
      List<TypeBasedGuardingDynamicLinker> l = new LinkedList();
      Iterator var3 = linkers.iterator();

      while(var3.hasNext()) {
         TypeBasedGuardingDynamicLinker linker = (TypeBasedGuardingDynamicLinker)var3.next();
         l.add(linker);
      }

      this.classToLinker = new CompositeTypeBasedGuardingDynamicLinker.ClassToLinker((TypeBasedGuardingDynamicLinker[])l.toArray(new TypeBasedGuardingDynamicLinker[l.size()]));
   }

   public boolean canLinkType(Class<?> type) {
      return !((List)this.classToLinker.get(type)).isEmpty();
   }

   public GuardedInvocation getGuardedInvocation(LinkRequest linkRequest, LinkerServices linkerServices) throws Exception {
      Object obj = linkRequest.getReceiver();
      if (obj == null) {
         return null;
      } else {
         Iterator var4 = ((List)this.classToLinker.get(obj.getClass())).iterator();

         GuardedInvocation invocation;
         do {
            if (!var4.hasNext()) {
               return null;
            }

            TypeBasedGuardingDynamicLinker linker = (TypeBasedGuardingDynamicLinker)var4.next();
            invocation = linker.getGuardedInvocation(linkRequest, linkerServices);
         } while(invocation == null);

         return invocation;
      }
   }

   public static List<GuardingDynamicLinker> optimize(Iterable<? extends GuardingDynamicLinker> linkers) {
      List<GuardingDynamicLinker> llinkers = new LinkedList();
      List<TypeBasedGuardingDynamicLinker> tblinkers = new LinkedList();
      Iterator var3 = linkers.iterator();

      while(var3.hasNext()) {
         GuardingDynamicLinker linker = (GuardingDynamicLinker)var3.next();
         if (linker instanceof TypeBasedGuardingDynamicLinker) {
            tblinkers.add((TypeBasedGuardingDynamicLinker)linker);
         } else {
            addTypeBased(llinkers, tblinkers);
            llinkers.add(linker);
         }
      }

      addTypeBased(llinkers, tblinkers);
      return llinkers;
   }

   private static void addTypeBased(List<GuardingDynamicLinker> llinkers, List<TypeBasedGuardingDynamicLinker> tblinkers) {
      switch(tblinkers.size()) {
      case 0:
         break;
      case 1:
         llinkers.addAll(tblinkers);
         tblinkers.clear();
         break;
      default:
         llinkers.add(new CompositeTypeBasedGuardingDynamicLinker(tblinkers));
         tblinkers.clear();
      }

   }

   private static class ClassToLinker extends ClassValue<List<TypeBasedGuardingDynamicLinker>> {
      private static final List<TypeBasedGuardingDynamicLinker> NO_LINKER = Collections.emptyList();
      private final TypeBasedGuardingDynamicLinker[] linkers;
      private final List<TypeBasedGuardingDynamicLinker>[] singletonLinkers;

      ClassToLinker(TypeBasedGuardingDynamicLinker[] linkers) {
         this.linkers = linkers;
         this.singletonLinkers = new List[linkers.length];

         for(int i = 0; i < linkers.length; ++i) {
            this.singletonLinkers[i] = Collections.singletonList(linkers[i]);
         }

      }

      protected List<TypeBasedGuardingDynamicLinker> computeValue(Class<?> clazz) {
         List<TypeBasedGuardingDynamicLinker> list = NO_LINKER;

         for(int i = 0; i < this.linkers.length; ++i) {
            TypeBasedGuardingDynamicLinker linker = this.linkers[i];
            if (linker.canLinkType(clazz)) {
               switch(((List)list).size()) {
               case 0:
                  list = this.singletonLinkers[i];
                  break;
               case 1:
                  list = new LinkedList((Collection)list);
               default:
                  ((List)list).add(linker);
               }
            }
         }

         return (List)list;
      }
   }
}
