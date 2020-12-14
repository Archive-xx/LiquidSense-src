package net.ccbluex.liquidbounce.script.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.script.api.module.AdaptedModule;
import net.ccbluex.liquidbounce.script.api.module.ScriptModule;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0007J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007¨\u0006\u0012"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/ModuleManager;", "", "()V", "getModule", "Lnet/ccbluex/liquidbounce/script/api/module/AdaptedModule;", "moduleName", "", "getModules", "", "registerModule", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scriptObjectMirror", "Ljdk/nashorn/api/scripting/ScriptObjectMirror;", "unregisterModule", "", "module", "autoDisable", "", "LiquidSense"}
)
public final class ModuleManager {
   // $FF: synthetic field
   public static final ModuleManager INSTANCE;

   @JvmStatic
   @NotNull
   public static final Module registerModule(@NotNull ScriptObjectMirror llllllllllllllllllllIlIIIlllllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIIlllllII, "scriptObjectMirror");
      ScriptModule llllllllllllllllllllIlIIIlllllIl = new ScriptModule(llllllllllllllllllllIlIIIlllllII);
      LiquidBounce.INSTANCE.getModuleManager().registerModule((Module)llllllllllllllllllllIlIIIlllllIl);
      return (Module)llllllllllllllllllllIlIIIlllllIl;
   }

   @JvmStatic
   @NotNull
   public static final AdaptedModule getModule(@NotNull String llllllllllllllllllllIlIIIlIIllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIIlIIllII, "moduleName");
      AdaptedModule var10000 = new AdaptedModule;
      Module var10002 = LiquidBounce.INSTANCE.getModuleManager().getModule(llllllllllllllllllllIlIIIlIIllII);
      if (var10002 == null) {
         Intrinsics.throwNpe();
      }

      var10000.<init>(var10002);
      return var10000;
   }

   @JvmStatic
   @NotNull
   public static final List<AdaptedModule> getModules() {
      long llllllllllllllllllllIlIIIIlIIllI = (Iterable)LiquidBounce.INSTANCE.getModuleManager().getModules();
      int llllllllllllllllllllIlIIIIlIIlll = false;
      boolean llllllllllllllllllllIlIIIIlIIIIl = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(llllllllllllllllllllIlIIIIlIIllI, 10)));
      int llllllllllllllllllllIlIIIIlIlIll = false;

      boolean var10001;
      for(Iterator llllllllllllllllllllIlIIIIIlllll = llllllllllllllllllllIlIIIIlIIllI.iterator(); llllllllllllllllllllIlIIIIIlllll.hasNext(); var10001 = false) {
         boolean llllllllllllllllllllIlIIIIIllllI = llllllllllllllllllllIlIIIIIlllll.next();
         Exception llllllllllllllllllllIlIIIIIlllIl = (Module)llllllllllllllllllllIlIIIIIllllI;
         int llllllllllllllllllllIlIIIIIlllII = false;
         Exception llllllllllllllllllllIlIIIIIllIlI = new AdaptedModule(llllllllllllllllllllIlIIIIIlllIl);
         llllllllllllllllllllIlIIIIlIIIIl.add(llllllllllllllllllllIlIIIIIllIlI);
      }

      return (List)llllllllllllllllllllIlIIIIlIIIIl;
   }

   private ModuleManager() {
   }

   static {
      ModuleManager llllllllllllllllllllIlIIIIIlIlIl = new ModuleManager();
      INSTANCE = llllllllllllllllllllIlIIIIIlIlIl;
   }

   @JvmStatic
   public static final void unregisterModule(@NotNull Module llllllllllllllllllllIlIIIlllIIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIIlllIIIl, "module");
      unregisterModule(llllllllllllllllllllIlIIIlllIIIl, true);
   }

   @JvmStatic
   public static final void unregisterModule(@NotNull Module llllllllllllllllllllIlIIIlIllIIl, boolean llllllllllllllllllllIlIIIlIllIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIIlIllIIl, "module");
      if (llllllllllllllllllllIlIIIlIllIll && llllllllllllllllllllIlIIIlIllIIl.getState()) {
         llllllllllllllllllllIlIIIlIllIIl.setState(false);
      }

      LiquidBounce.INSTANCE.getModuleManager().unregisterModule(llllllllllllllllllllIlIIIlIllIIl);
   }

   @JvmStatic
   public static final void unregisterModule(@NotNull ScriptObjectMirror llllllllllllllllllllIlIIIllIIlIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllllIlIIIllIIlIl, "scriptObjectMirror");
      ScriptModule llllllllllllllllllllIlIIIllIIlll = new ScriptModule(llllllllllllllllllllIlIIIllIIlIl);
      Module var10000 = LiquidBounce.INSTANCE.getModuleManager().getModule(llllllllllllllllllllIlIIIllIIlll.getName());
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      unregisterModule(var10000, true);
   }
}
