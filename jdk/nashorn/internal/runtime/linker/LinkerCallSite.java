package jdk.nashorn.internal.runtime.linker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import jdk.internal.dynalink.ChainedCallSite;
import jdk.internal.dynalink.DynamicLinker;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.options.Options;

public class LinkerCallSite extends ChainedCallSite {
   public static final int ARGLIMIT = 250;
   private static final String PROFILEFILE = Options.getStringProperty("nashorn.profilefile", "NashornProfile.txt");
   private static final MethodHandle INCREASE_MISS_COUNTER;
   private static final MethodHandle ON_CATCH_INVALIDATION;
   private int catchInvalidations;
   private static LongAdder count;
   private static final HashMap<String, AtomicInteger> missCounts;
   private static LongAdder missCount;
   private static final Random r;
   private static final int missSamplingPercentage;

   LinkerCallSite(NashornCallSiteDescriptor descriptor) {
      super(descriptor);
      if (Context.DEBUG) {
         count.increment();
      }

   }

   protected MethodHandle getPruneCatches() {
      return Lookup.MH.filterArguments(super.getPruneCatches(), 0, ON_CATCH_INVALIDATION);
   }

   private static ChainedCallSite onCatchInvalidation(LinkerCallSite callSite) {
      ++callSite.catchInvalidations;
      return callSite;
   }

   public static int getCatchInvalidationCount(Object callSiteToken) {
      return callSiteToken instanceof LinkerCallSite ? ((LinkerCallSite)callSiteToken).catchInvalidations : 0;
   }

   static LinkerCallSite newLinkerCallSite(java.lang.invoke.MethodHandles.Lookup lookup, String name, MethodType type, int flags) {
      NashornCallSiteDescriptor desc = NashornCallSiteDescriptor.get(lookup, name, type, flags);
      if (desc.isProfile()) {
         return LinkerCallSite.ProfilingLinkerCallSite.newProfilingLinkerCallSite(desc);
      } else {
         return (LinkerCallSite)(desc.isTrace() ? new LinkerCallSite.TracingLinkerCallSite(desc) : new LinkerCallSite(desc));
      }
   }

   public String toString() {
      return this.getDescriptor().toString();
   }

   public NashornCallSiteDescriptor getNashornDescriptor() {
      return (NashornCallSiteDescriptor)this.getDescriptor();
   }

   public void relink(GuardedInvocation invocation, MethodHandle relink) {
      super.relink(invocation, this.getDebuggingRelink(relink));
   }

   public void resetAndRelink(GuardedInvocation invocation, MethodHandle relink) {
      super.resetAndRelink(invocation, this.getDebuggingRelink(relink));
   }

   private MethodHandle getDebuggingRelink(MethodHandle relink) {
      return Context.DEBUG ? Lookup.MH.filterArguments(relink, 0, this.getIncreaseMissCounter(relink.type().parameterType(0))) : relink;
   }

   private MethodHandle getIncreaseMissCounter(Class<?> type) {
      MethodHandle missCounterWithDesc = Lookup.MH.bindTo(INCREASE_MISS_COUNTER, this.getDescriptor().getName() + " @ " + getScriptLocation());
      return type == Object.class ? missCounterWithDesc : Lookup.MH.asType(missCounterWithDesc, missCounterWithDesc.type().changeParameterType(0, type).changeReturnType(type));
   }

   private static String getScriptLocation() {
      StackTraceElement caller = DynamicLinker.getLinkedCallSiteLocation();
      return caller == null ? "unknown location" : caller.getFileName() + ":" + caller.getLineNumber();
   }

   public static Object increaseMissCount(String desc, Object self) {
      missCount.increment();
      if (r.nextInt(100) < missSamplingPercentage) {
         AtomicInteger i = (AtomicInteger)missCounts.get(desc);
         if (i == null) {
            missCounts.put(desc, new AtomicInteger(1));
         } else {
            i.incrementAndGet();
         }
      }

      return self;
   }

   protected int getMaxChainLength() {
      return 8;
   }

   public static long getCount() {
      return count.longValue();
   }

   public static long getMissCount() {
      return missCount.longValue();
   }

   public static int getMissSamplingPercentage() {
      return missSamplingPercentage;
   }

   public static void getMissCounts(PrintWriter out) {
      ArrayList<Entry<String, AtomicInteger>> entries = new ArrayList(missCounts.entrySet());
      Collections.sort(entries, new Comparator<Entry<String, AtomicInteger>>() {
         public int compare(Entry<String, AtomicInteger> o1, Entry<String, AtomicInteger> o2) {
            return ((AtomicInteger)o2.getValue()).get() - ((AtomicInteger)o1.getValue()).get();
         }
      });
      Iterator var2 = entries.iterator();

      while(var2.hasNext()) {
         Entry<String, AtomicInteger> entry = (Entry)var2.next();
         out.println("  " + (String)entry.getKey() + "\t" + ((AtomicInteger)entry.getValue()).get());
      }

   }

   static {
      INCREASE_MISS_COUNTER = Lookup.MH.findStatic(MethodHandles.lookup(), LinkerCallSite.class, "increaseMissCount", Lookup.MH.type(Object.class, String.class, Object.class));
      ON_CATCH_INVALIDATION = Lookup.MH.findStatic(MethodHandles.lookup(), LinkerCallSite.class, "onCatchInvalidation", Lookup.MH.type(ChainedCallSite.class, LinkerCallSite.class));
      missCounts = new HashMap();
      r = new Random();
      missSamplingPercentage = Options.getIntProperty("nashorn.tcs.miss.samplePercent", 1);
      if (Context.DEBUG) {
         count = new LongAdder();
         missCount = new LongAdder();
      }

   }

   private static class TracingLinkerCallSite extends LinkerCallSite {
      private static final java.lang.invoke.MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
      private static final MethodHandle TRACEOBJECT;
      private static final MethodHandle TRACEVOID;
      private static final MethodHandle TRACEMISS;

      TracingLinkerCallSite(NashornCallSiteDescriptor desc) {
         super(desc);
      }

      public void setTarget(MethodHandle newTarget) {
         if (!this.getNashornDescriptor().isTraceEnterExit()) {
            super.setTarget(newTarget);
         } else {
            MethodType type = this.type();
            boolean isVoid = type.returnType() == Void.TYPE;
            MethodHandle traceMethodHandle = isVoid ? TRACEVOID : TRACEOBJECT;
            traceMethodHandle = Lookup.MH.bindTo(traceMethodHandle, this);
            traceMethodHandle = Lookup.MH.bindTo(traceMethodHandle, newTarget);
            traceMethodHandle = Lookup.MH.asCollector(traceMethodHandle, Object[].class, type.parameterCount());
            traceMethodHandle = Lookup.MH.asType(traceMethodHandle, type);
            super.setTarget(traceMethodHandle);
         }
      }

      public void initialize(MethodHandle relinkAndInvoke) {
         super.initialize(this.getFallbackLoggingRelink(relinkAndInvoke));
      }

      public void relink(GuardedInvocation invocation, MethodHandle relink) {
         super.relink(invocation, this.getFallbackLoggingRelink(relink));
      }

      public void resetAndRelink(GuardedInvocation invocation, MethodHandle relink) {
         super.resetAndRelink(invocation, this.getFallbackLoggingRelink(relink));
      }

      private MethodHandle getFallbackLoggingRelink(MethodHandle relink) {
         if (!this.getNashornDescriptor().isTraceMisses()) {
            return relink;
         } else {
            MethodType type = relink.type();
            return Lookup.MH.foldArguments(relink, Lookup.MH.asType(Lookup.MH.asCollector(Lookup.MH.insertArguments(TRACEMISS, 0, this, "MISS " + LinkerCallSite.getScriptLocation() + " "), Object[].class, type.parameterCount()), type.changeReturnType(Void.TYPE)));
         }
      }

      private void printObject(PrintWriter out, Object arg) {
         if (!this.getNashornDescriptor().isTraceObjects()) {
            out.print(arg instanceof ScriptObject ? "ScriptObject" : arg);
         } else {
            if (arg instanceof ScriptObject) {
               ScriptObject object = (ScriptObject)arg;
               boolean isFirst = true;
               Set<Object> keySet = object.keySet();
               if (keySet.isEmpty()) {
                  out.print(ScriptRuntime.safeToString(arg));
               } else {
                  out.print("{ ");

                  for(Iterator var6 = keySet.iterator(); var6.hasNext(); isFirst = false) {
                     Object key = var6.next();
                     if (!isFirst) {
                        out.print(", ");
                     }

                     out.print(key);
                     out.print(":");
                     Object value = object.get(key);
                     if (value instanceof ScriptObject) {
                        out.print("...");
                     } else {
                        this.printObject(out, value);
                     }
                  }

                  out.print(" }");
               }
            } else {
               out.print(ScriptRuntime.safeToString(arg));
            }

         }
      }

      private void tracePrint(PrintWriter out, String tag, Object[] args, Object result) {
         out.print(Debug.id(this) + " TAG " + tag);
         out.print(this.getDescriptor().getName() + "(");
         if (args.length > 0) {
            this.printObject(out, args[0]);

            for(int i = 1; i < args.length; ++i) {
               Object arg = args[i];
               out.print(", ");
               if (arg instanceof ScriptObject && ((ScriptObject)arg).isScope()) {
                  out.print("SCOPE");
               } else {
                  this.printObject(out, arg);
               }
            }
         }

         out.print(")");
         if (tag.equals("EXIT  ")) {
            out.print(" --> ");
            this.printObject(out, result);
         }

         out.println();
      }

      public Object traceObject(MethodHandle mh, Object... args) throws Throwable {
         PrintWriter out = Context.getCurrentErr();
         this.tracePrint(out, "ENTER ", args, (Object)null);
         Object result = mh.invokeWithArguments(args);
         this.tracePrint(out, "EXIT  ", args, result);
         return result;
      }

      public void traceVoid(MethodHandle mh, Object... args) throws Throwable {
         PrintWriter out = Context.getCurrentErr();
         this.tracePrint(out, "ENTER ", args, (Object)null);
         mh.invokeWithArguments(args);
         this.tracePrint(out, "EXIT  ", args, (Object)null);
      }

      public void traceMiss(String desc, Object... args) throws Throwable {
         this.tracePrint(Context.getCurrentErr(), desc, args, (Object)null);
      }

      static {
         TRACEOBJECT = Lookup.MH.findVirtual(LOOKUP, LinkerCallSite.TracingLinkerCallSite.class, "traceObject", Lookup.MH.type(Object.class, MethodHandle.class, Object[].class));
         TRACEVOID = Lookup.MH.findVirtual(LOOKUP, LinkerCallSite.TracingLinkerCallSite.class, "traceVoid", Lookup.MH.type(Void.TYPE, MethodHandle.class, Object[].class));
         TRACEMISS = Lookup.MH.findVirtual(LOOKUP, LinkerCallSite.TracingLinkerCallSite.class, "traceMiss", Lookup.MH.type(Void.TYPE, String.class, Object[].class));
      }
   }

   private static class ProfilingLinkerCallSite extends LinkerCallSite {
      private static LinkedList<LinkerCallSite.ProfilingLinkerCallSite> profileCallSites = null;
      private long startTime;
      private int depth;
      private long totalTime;
      private long hitCount;
      private static final java.lang.invoke.MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
      private static final MethodHandle PROFILEENTRY;
      private static final MethodHandle PROFILEEXIT;
      private static final MethodHandle PROFILEVOIDEXIT;

      ProfilingLinkerCallSite(NashornCallSiteDescriptor desc) {
         super(desc);
      }

      public static LinkerCallSite.ProfilingLinkerCallSite newProfilingLinkerCallSite(NashornCallSiteDescriptor desc) {
         if (profileCallSites == null) {
            profileCallSites = new LinkedList();
            Thread profileDumperThread = new Thread(new LinkerCallSite.ProfilingLinkerCallSite.ProfileDumper());
            Runtime.getRuntime().addShutdownHook(profileDumperThread);
         }

         LinkerCallSite.ProfilingLinkerCallSite callSite = new LinkerCallSite.ProfilingLinkerCallSite(desc);
         profileCallSites.add(callSite);
         return callSite;
      }

      public void setTarget(MethodHandle newTarget) {
         MethodType type = this.type();
         boolean isVoid = type.returnType() == Void.TYPE;
         Class<?> newSelfType = newTarget.type().parameterType(0);
         MethodHandle selfFilter = Lookup.MH.bindTo(PROFILEENTRY, this);
         if (newSelfType != Object.class) {
            MethodType selfFilterType = MethodType.methodType(newSelfType, newSelfType);
            selfFilter = selfFilter.asType(selfFilterType);
         }

         MethodHandle methodHandle = Lookup.MH.filterArguments(newTarget, 0, selfFilter);
         if (isVoid) {
            methodHandle = Lookup.MH.filterReturnValue(methodHandle, Lookup.MH.bindTo(PROFILEVOIDEXIT, this));
         } else {
            MethodType filter = Lookup.MH.type(type.returnType(), type.returnType());
            methodHandle = Lookup.MH.filterReturnValue(methodHandle, Lookup.MH.asType(Lookup.MH.bindTo(PROFILEEXIT, this), filter));
         }

         super.setTarget(methodHandle);
      }

      public Object profileEntry(Object self) {
         if (this.depth == 0) {
            this.startTime = System.nanoTime();
         }

         ++this.depth;
         ++this.hitCount;
         return self;
      }

      public Object profileExit(Object result) {
         --this.depth;
         if (this.depth == 0) {
            this.totalTime += System.nanoTime() - this.startTime;
         }

         return result;
      }

      public void profileVoidExit() {
         --this.depth;
         if (this.depth == 0) {
            this.totalTime += System.nanoTime() - this.startTime;
         }

      }

      static {
         PROFILEENTRY = Lookup.MH.findVirtual(LOOKUP, LinkerCallSite.ProfilingLinkerCallSite.class, "profileEntry", Lookup.MH.type(Object.class, Object.class));
         PROFILEEXIT = Lookup.MH.findVirtual(LOOKUP, LinkerCallSite.ProfilingLinkerCallSite.class, "profileExit", Lookup.MH.type(Object.class, Object.class));
         PROFILEVOIDEXIT = Lookup.MH.findVirtual(LOOKUP, LinkerCallSite.ProfilingLinkerCallSite.class, "profileVoidExit", Lookup.MH.type(Void.TYPE));
      }

      static class ProfileDumper implements Runnable {
         public void run() {
            PrintWriter out = null;
            boolean fileOutput = false;

            try {
               try {
                  out = new PrintWriter(new FileOutputStream(LinkerCallSite.PROFILEFILE));
                  fileOutput = true;
               } catch (FileNotFoundException var7) {
                  out = Context.getCurrentErr();
               }

               dump(out);
            } finally {
               if (out != null && fileOutput) {
                  out.close();
               }

            }

         }

         private static void dump(PrintWriter out) {
            int index = 0;
            Iterator var2 = LinkerCallSite.ProfilingLinkerCallSite.profileCallSites.iterator();

            while(var2.hasNext()) {
               LinkerCallSite.ProfilingLinkerCallSite callSite = (LinkerCallSite.ProfilingLinkerCallSite)var2.next();
               out.println("" + index++ + '\t' + callSite.getDescriptor().getName() + '\t' + callSite.totalTime + '\t' + callSite.hitCount);
            }

         }
      }
   }
}
