package jdk.nashorn.internal.runtime;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.ref.WeakReference;
import java.util.Collection;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;

public final class AllocationStrategy implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final Lookup LOOKUP = MethodHandles.lookup();
   private final int fieldCount;
   private final boolean dualFields;
   private transient String allocatorClassName;
   private transient MethodHandle allocator;
   private transient AllocationStrategy.AllocatorMap lastMap;

   public AllocationStrategy(int fieldCount, boolean dualFields) {
      this.fieldCount = fieldCount;
      this.dualFields = dualFields;
   }

   private String getAllocatorClassName() {
      if (this.allocatorClassName == null) {
         this.allocatorClassName = Compiler.binaryName(ObjectClassGenerator.getClassName(this.fieldCount, this.dualFields)).intern();
      }

      return this.allocatorClassName;
   }

   synchronized PropertyMap getAllocatorMap(ScriptObject prototype) {
      assert prototype != null;

      PropertyMap protoMap = prototype.getMap();
      PropertyMap allocatorMap;
      if (this.lastMap != null) {
         if (!this.lastMap.hasSharedProtoMap()) {
            if (this.lastMap.hasSamePrototype(prototype)) {
               return this.lastMap.allocatorMap;
            }

            if (this.lastMap.hasSameProtoMap(protoMap) && this.lastMap.hasUnchangedProtoMap()) {
               allocatorMap = PropertyMap.newMap((Collection)null, this.getAllocatorClassName(), 0, this.fieldCount, 0);
               SharedPropertyMap sharedProtoMap = new SharedPropertyMap(protoMap);
               allocatorMap.setSharedProtoMap(sharedProtoMap);
               prototype.setMap(sharedProtoMap);
               this.lastMap = new AllocationStrategy.AllocatorMap(prototype, protoMap, allocatorMap);
               return allocatorMap;
            }
         }

         if (this.lastMap.hasValidSharedProtoMap() && this.lastMap.hasSameProtoMap(protoMap)) {
            prototype.setMap(this.lastMap.getSharedProtoMap());
            return this.lastMap.allocatorMap;
         }
      }

      allocatorMap = PropertyMap.newMap((Collection)null, this.getAllocatorClassName(), 0, this.fieldCount, 0);
      this.lastMap = new AllocationStrategy.AllocatorMap(prototype, protoMap, allocatorMap);
      return allocatorMap;
   }

   ScriptObject allocate(PropertyMap map) {
      try {
         if (this.allocator == null) {
            this.allocator = jdk.nashorn.internal.lookup.Lookup.MH.findStatic(LOOKUP, Context.forStructureClass(this.getAllocatorClassName()), CompilerConstants.ALLOCATE.symbolName(), jdk.nashorn.internal.lookup.Lookup.MH.type(ScriptObject.class, PropertyMap.class));
         }

         return this.allocator.invokeExact(map);
      } catch (Error | RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   public String toString() {
      return "AllocationStrategy[fieldCount=" + this.fieldCount + "]";
   }

   static class AllocatorMap {
      private final WeakReference<ScriptObject> prototype;
      private final WeakReference<PropertyMap> prototypeMap;
      private PropertyMap allocatorMap;

      AllocatorMap(ScriptObject prototype, PropertyMap protoMap, PropertyMap allocMap) {
         this.prototype = new WeakReference(prototype);
         this.prototypeMap = new WeakReference(protoMap);
         this.allocatorMap = allocMap;
      }

      boolean hasSamePrototype(ScriptObject proto) {
         return this.prototype.get() == proto;
      }

      boolean hasSameProtoMap(PropertyMap protoMap) {
         return this.prototypeMap.get() == protoMap || this.allocatorMap.getSharedProtoMap() == protoMap;
      }

      boolean hasUnchangedProtoMap() {
         ScriptObject proto = (ScriptObject)this.prototype.get();
         return proto != null && proto.getMap() == this.prototypeMap.get();
      }

      boolean hasSharedProtoMap() {
         return this.getSharedProtoMap() != null;
      }

      boolean hasValidSharedProtoMap() {
         return this.hasSharedProtoMap() && this.getSharedProtoMap().isValidSharedProtoMap();
      }

      PropertyMap getSharedProtoMap() {
         return this.allocatorMap.getSharedProtoMap();
      }
   }
}
