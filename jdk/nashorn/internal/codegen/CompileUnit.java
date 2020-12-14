package jdk.nashorn.internal.codegen;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;

public final class CompileUnit implements Comparable<CompileUnit>, Serializable {
   private static final long serialVersionUID = 1L;
   private final transient String className;
   private transient ClassEmitter classEmitter;
   private transient long weight;
   private transient Class<?> clazz;
   private transient Map<FunctionNode, RecompilableScriptFunctionData> functions = new IdentityHashMap();
   private transient boolean isUsed;
   private static int emittedUnitCount;

   CompileUnit(String className, ClassEmitter classEmitter, long initialWeight) {
      this.className = className;
      this.weight = initialWeight;
      this.classEmitter = classEmitter;
   }

   static Set<CompileUnit> createCompileUnitSet() {
      return new TreeSet();
   }

   static void increaseEmitCount() {
      ++emittedUnitCount;
   }

   public static int getEmittedUnitCount() {
      return emittedUnitCount;
   }

   public boolean isUsed() {
      return this.isUsed;
   }

   public boolean hasCode() {
      return this.classEmitter.getMethodCount() - this.classEmitter.getInitCount() - this.classEmitter.getClinitCount() > 0;
   }

   public void setUsed() {
      this.isUsed = true;
   }

   public Class<?> getCode() {
      return this.clazz;
   }

   void setCode(Class<?> clazz) {
      this.clazz = (Class)Objects.requireNonNull(clazz);
      this.classEmitter = null;
   }

   void addFunctionInitializer(RecompilableScriptFunctionData data, FunctionNode functionNode) {
      this.functions.put(functionNode, data);
   }

   public boolean isInitializing(RecompilableScriptFunctionData data, FunctionNode functionNode) {
      return this.functions.get(functionNode) == data;
   }

   void initializeFunctionsCode() {
      Iterator var1 = this.functions.entrySet().iterator();

      while(var1.hasNext()) {
         Entry<FunctionNode, RecompilableScriptFunctionData> entry = (Entry)var1.next();
         ((RecompilableScriptFunctionData)entry.getValue()).initializeCode((FunctionNode)entry.getKey());
      }

   }

   Collection<FunctionNode> getFunctionNodes() {
      return Collections.unmodifiableCollection(this.functions.keySet());
   }

   void addWeight(long w) {
      this.weight += w;
   }

   public boolean canHold(long w) {
      return this.weight + w < Splitter.SPLIT_THRESHOLD;
   }

   public ClassEmitter getClassEmitter() {
      return this.classEmitter;
   }

   public String getUnitClassName() {
      return this.className;
   }

   private static String shortName(String name) {
      return name == null ? null : (name.lastIndexOf(47) == -1 ? name : name.substring(name.lastIndexOf(47) + 1));
   }

   public String toString() {
      String methods = this.classEmitter != null ? this.classEmitter.getMethodNames().toString() : "<anon>";
      return "[CompileUnit className=" + shortName(this.className) + " weight=" + this.weight + '/' + Splitter.SPLIT_THRESHOLD + " hasCode=" + methods + ']';
   }

   public int compareTo(CompileUnit o) {
      return this.className.compareTo(o.className);
   }
}
