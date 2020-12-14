package kotlin.coroutines.jvm.internal;

import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bÂ\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bR\u0014\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"},
   d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever;", "", "()V", "cache", "Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "notOnJava9", "buildCache", "continuation", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "getModuleName", "", "Cache", "kotlin-stdlib"}
)
final class ModuleNameRetriever {
   private static final ModuleNameRetriever.Cache notOnJava9;
   @JvmField
   @Nullable
   public static ModuleNameRetriever.Cache cache;
   public static final ModuleNameRetriever INSTANCE;

   @Nullable
   public final String getModuleName(@NotNull BaseContinuationImpl continuation) {
      Intrinsics.checkParameterIsNotNull(continuation, "continuation");
      ModuleNameRetriever.Cache var10000 = ModuleNameRetriever.cache;
      if (var10000 == null) {
         var10000 = this.buildCache(continuation);
      }

      ModuleNameRetriever.Cache cache = var10000;
      if (cache == notOnJava9) {
         return null;
      } else {
         Method var5 = cache.getModuleMethod;
         if (var5 != null) {
            Object var6 = var5.invoke(continuation.getClass());
            if (var6 != null) {
               Object module = var6;
               var5 = cache.getDescriptorMethod;
               if (var5 != null) {
                  var6 = var5.invoke(module);
                  if (var6 != null) {
                     Object descriptor = var6;
                     var5 = cache.nameMethod;
                     var6 = var5 != null ? var5.invoke(descriptor) : null;
                     if (!(var6 instanceof String)) {
                        var6 = null;
                     }

                     return (String)var6;
                  }
               }

               return null;
            }
         }

         return null;
      }
   }

   private final ModuleNameRetriever.Cache buildCache(BaseContinuationImpl continuation) {
      try {
         Method getModuleMethod = Class.class.getDeclaredMethod("getModule");
         Class methodClass = continuation.getClass().getClassLoader().loadClass("java.lang.Module");
         Method getDescriptorMethod = methodClass.getDeclaredMethod("getDescriptor");
         Class moduleDescriptorClass = continuation.getClass().getClassLoader().loadClass("java.lang.module.ModuleDescriptor");
         Method nameMethod = moduleDescriptorClass.getDeclaredMethod("name");
         ModuleNameRetriever.Cache var16 = new ModuleNameRetriever.Cache(getModuleMethod, getDescriptorMethod, nameMethod);
         boolean var8 = false;
         boolean var9 = false;
         int var11 = false;
         cache = var16;
         return var16;
      } catch (Exception var12) {
         ModuleNameRetriever.Cache var3 = notOnJava9;
         boolean var4 = false;
         boolean var5 = false;
         int var7 = false;
         cache = var3;
         return var3;
      }
   }

   private ModuleNameRetriever() {
   }

   static {
      ModuleNameRetriever var0 = new ModuleNameRetriever();
      INSTANCE = var0;
      notOnJava9 = new ModuleNameRetriever.Cache((Method)null, (Method)null, (Method)null);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B#\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0006R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"},
      d2 = {"Lkotlin/coroutines/jvm/internal/ModuleNameRetriever$Cache;", "", "getModuleMethod", "Ljava/lang/reflect/Method;", "getDescriptorMethod", "nameMethod", "(Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)V", "kotlin-stdlib"}
   )
   private static final class Cache {
      @JvmField
      @Nullable
      public final Method getModuleMethod;
      @JvmField
      @Nullable
      public final Method getDescriptorMethod;
      @JvmField
      @Nullable
      public final Method nameMethod;

      public Cache(@Nullable Method getModuleMethod, @Nullable Method getDescriptorMethod, @Nullable Method nameMethod) {
         this.getModuleMethod = getModuleMethod;
         this.getDescriptorMethod = getDescriptorMethod;
         this.nameMethod = nameMethod;
      }
   }
}
