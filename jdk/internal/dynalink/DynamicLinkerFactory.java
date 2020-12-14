package jdk.internal.dynalink;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.GuardingTypeConverterFactory;
import jdk.internal.dynalink.linker.MethodHandleTransformer;
import jdk.internal.dynalink.linker.MethodTypeConversionStrategy;
import jdk.internal.dynalink.support.AutoDiscovery;
import jdk.internal.dynalink.support.BottomGuardingDynamicLinker;
import jdk.internal.dynalink.support.ClassLoaderGetterContextProvider;
import jdk.internal.dynalink.support.CompositeGuardingDynamicLinker;
import jdk.internal.dynalink.support.CompositeTypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.DefaultPrelinkFilter;
import jdk.internal.dynalink.support.LinkerServicesImpl;
import jdk.internal.dynalink.support.TypeConverterFactory;

public class DynamicLinkerFactory {
   public static final int DEFAULT_UNSTABLE_RELINK_THRESHOLD = 8;
   private boolean classLoaderExplicitlySet = false;
   private ClassLoader classLoader;
   private List<? extends GuardingDynamicLinker> prioritizedLinkers;
   private List<? extends GuardingDynamicLinker> fallbackLinkers;
   private int runtimeContextArgCount = 0;
   private boolean syncOnRelink = false;
   private int unstableRelinkThreshold = 8;
   private GuardedInvocationFilter prelinkFilter;
   private MethodTypeConversionStrategy autoConversionStrategy;
   private MethodHandleTransformer internalObjectsFilter;

   public void setClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
      this.classLoaderExplicitlySet = true;
   }

   public void setPrioritizedLinkers(List<? extends GuardingDynamicLinker> prioritizedLinkers) {
      this.prioritizedLinkers = prioritizedLinkers == null ? null : new ArrayList(prioritizedLinkers);
   }

   public void setPrioritizedLinkers(GuardingDynamicLinker... prioritizedLinkers) {
      this.setPrioritizedLinkers(Arrays.asList(prioritizedLinkers));
   }

   public void setPrioritizedLinker(GuardingDynamicLinker prioritizedLinker) {
      if (prioritizedLinker == null) {
         throw new IllegalArgumentException("prioritizedLinker == null");
      } else {
         this.prioritizedLinkers = Collections.singletonList(prioritizedLinker);
      }
   }

   public void setFallbackLinkers(List<? extends GuardingDynamicLinker> fallbackLinkers) {
      this.fallbackLinkers = fallbackLinkers == null ? null : new ArrayList(fallbackLinkers);
   }

   public void setFallbackLinkers(GuardingDynamicLinker... fallbackLinkers) {
      this.setFallbackLinkers(Arrays.asList(fallbackLinkers));
   }

   public void setRuntimeContextArgCount(int runtimeContextArgCount) {
      if (runtimeContextArgCount < 0) {
         throw new IllegalArgumentException("runtimeContextArgCount < 0");
      } else {
         this.runtimeContextArgCount = runtimeContextArgCount;
      }
   }

   public void setSyncOnRelink(boolean syncOnRelink) {
      this.syncOnRelink = syncOnRelink;
   }

   public void setUnstableRelinkThreshold(int unstableRelinkThreshold) {
      if (unstableRelinkThreshold < 0) {
         throw new IllegalArgumentException("unstableRelinkThreshold < 0");
      } else {
         this.unstableRelinkThreshold = unstableRelinkThreshold;
      }
   }

   public void setPrelinkFilter(GuardedInvocationFilter prelinkFilter) {
      this.prelinkFilter = prelinkFilter;
   }

   public void setAutoConversionStrategy(MethodTypeConversionStrategy autoConversionStrategy) {
      this.autoConversionStrategy = autoConversionStrategy;
   }

   public void setInternalObjectsFilter(MethodHandleTransformer internalObjectsFilter) {
      this.internalObjectsFilter = internalObjectsFilter;
   }

   public DynamicLinker createLinker() {
      if (this.prioritizedLinkers == null) {
         this.prioritizedLinkers = Collections.emptyList();
      }

      if (this.fallbackLinkers == null) {
         this.fallbackLinkers = Collections.singletonList(new BeansLinker());
      }

      Set<Class<? extends GuardingDynamicLinker>> knownLinkerClasses = new HashSet();
      addClasses(knownLinkerClasses, this.prioritizedLinkers);
      addClasses(knownLinkerClasses, this.fallbackLinkers);
      ClassLoader effectiveClassLoader = this.classLoaderExplicitlySet ? this.classLoader : getThreadContextClassLoader();
      List<GuardingDynamicLinker> discovered = AutoDiscovery.loadLinkers(effectiveClassLoader);
      List<GuardingDynamicLinker> linkers = new ArrayList(this.prioritizedLinkers.size() + discovered.size() + this.fallbackLinkers.size());
      linkers.addAll(this.prioritizedLinkers);
      Iterator var5 = discovered.iterator();

      while(var5.hasNext()) {
         GuardingDynamicLinker linker = (GuardingDynamicLinker)var5.next();
         if (!knownLinkerClasses.contains(linker.getClass())) {
            linkers.add(linker);
         }
      }

      linkers.addAll(this.fallbackLinkers);
      List<GuardingDynamicLinker> optimized = CompositeTypeBasedGuardingDynamicLinker.optimize(linkers);
      Object composite;
      switch(linkers.size()) {
      case 0:
         composite = BottomGuardingDynamicLinker.INSTANCE;
         break;
      case 1:
         composite = (GuardingDynamicLinker)optimized.get(0);
         break;
      default:
         composite = new CompositeGuardingDynamicLinker(optimized);
      }

      List<GuardingTypeConverterFactory> typeConverters = new LinkedList();
      Iterator var8 = linkers.iterator();

      while(var8.hasNext()) {
         GuardingDynamicLinker linker = (GuardingDynamicLinker)var8.next();
         if (linker instanceof GuardingTypeConverterFactory) {
            typeConverters.add((GuardingTypeConverterFactory)linker);
         }
      }

      if (this.prelinkFilter == null) {
         this.prelinkFilter = new DefaultPrelinkFilter();
      }

      return new DynamicLinker(new LinkerServicesImpl(new TypeConverterFactory(typeConverters, this.autoConversionStrategy), (GuardingDynamicLinker)composite, this.internalObjectsFilter), this.prelinkFilter, this.runtimeContextArgCount, this.syncOnRelink, this.unstableRelinkThreshold);
   }

   private static ClassLoader getThreadContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
         public ClassLoader run() {
            return Thread.currentThread().getContextClassLoader();
         }
      }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT);
   }

   private static void addClasses(Set<Class<? extends GuardingDynamicLinker>> knownLinkerClasses, List<? extends GuardingDynamicLinker> linkers) {
      Iterator var2 = linkers.iterator();

      while(var2.hasNext()) {
         GuardingDynamicLinker linker = (GuardingDynamicLinker)var2.next();
         knownLinkerClasses.add(linker.getClass());
      }

   }
}
