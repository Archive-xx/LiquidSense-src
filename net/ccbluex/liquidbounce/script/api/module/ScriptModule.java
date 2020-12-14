package net.ccbluex.liquidbounce.script.api.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u0016\u0010\u0019\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00072\u0006\u0010\u001a\u001a\u00020\nH\u0016J\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\b\u0010\u001e\u001a\u00020\u0015H\u0016J\b\u0010\u001f\u001a\u00020\u0015H\u0016J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020%H\u0007J\u0010\u0010&\u001a\u00020\u00152\u0006\u0010'\u001a\u00020(H\u0007J\u0010\u0010)\u001a\u00020\u00152\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020\u00152\u0006\u00103\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020\u00152\u0006\u00106\u001a\u000207H\u0007J\u0010\u00108\u001a\u00020\u00152\u0006\u00109\u001a\u00020:H\u0007J\u0010\u0010;\u001a\u00020\u00152\u0006\u0010<\u001a\u00020=H\u0007J\u0010\u0010>\u001a\u00020\u00152\u0006\u0010?\u001a\u00020@H\u0007J\u0010\u0010A\u001a\u00020\u00152\u0006\u0010B\u001a\u00020CH\u0007R\u0018\u0010\u0005\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tj\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00070\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006D"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/module/ScriptModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "scriptObjectMirror", "Ljdk/nashorn/api/scripting/ScriptObjectMirror;", "(Ljdk/nashorn/api/scripting/ScriptObjectMirror;)V", "_values", "", "Lnet/ccbluex/liquidbounce/value/Value;", "eventCache", "Ljava/util/HashMap;", "", "", "Lkotlin/collections/HashMap;", "tag", "getTag", "()Ljava/lang/String;", "values", "", "getValues", "()Ljava/util/List;", "call", "", "member", "event", "", "getValue", "valueName", "onAttack", "attackEvent", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onEnable", "onJump", "jumpEvent", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onKey", "keyEvent", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onMotion", "motionEvent", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "moveEvent", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "packetEvent", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "render2DEvent", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "render3DEvent", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onSession", "sessionEvent", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "onStep", "stepEvent", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onStepConfirm", "stepConfirmEvent", "Lnet/ccbluex/liquidbounce/event/StepConfirmEvent;", "onUpdate", "updateEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "worldEvent", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "LiquidSense"}
)
@ModuleInfo(
   name = "ScriptModule",
   description = "Empty",
   category = ModuleCategory.MISC
)
public final class ScriptModule extends Module {
   // $FF: synthetic field
   private final ScriptObjectMirror scriptObjectMirror;
   // $FF: synthetic field
   private final List<Value<?>> _values;
   // $FF: synthetic field
   private final HashMap<String, Boolean> eventCache;

   @EventTarget
   public final void onRender2D(@NotNull Render2DEvent llllllllllllllllllIIIIlIIIlIIlIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIlIIlIl, "render2DEvent");
      llllllllllllllllllIIIIlIIIlIIllI.call("onRender2D", llllllllllllllllllIIIIlIIIlIIlIl);
   }

   private final void call(String llllllllllllllllllIIIIIllllIIIlI, Object llllllllllllllllllIIIIIlllIlllIl) {
      boolean var10001;
      if (llllllllllllllllllIIIIIllllIIIll.eventCache.get(llllllllllllllllllIIIIIllllIIIlI) == null) {
         ((Map)llllllllllllllllllIIIIIllllIIIll.eventCache).put(llllllllllllllllllIIIIIllllIIIlI, llllllllllllllllllIIIIIllllIIIll.scriptObjectMirror.hasMember(llllllllllllllllllIIIIIllllIIIlI));
         var10001 = false;
      }

      Object var10000 = llllllllllllllllllIIIIIllllIIIll.eventCache.get(llllllllllllllllllIIIIIllllIIIlI);
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      Intrinsics.checkExpressionValueIsNotNull(var10000, "eventCache[member]!!");
      if ((Boolean)var10000) {
         try {
            llllllllllllllllllIIIIIllllIIIll.scriptObjectMirror.callMember(llllllllllllllllllIIIIIllllIIIlI, llllllllllllllllllIIIIIlllIlllIl);
            var10001 = false;
         } catch (Throwable var5) {
            ClientUtils.getLogger().error(String.valueOf((new StringBuilder()).append("An error occurred inside script module: ").append(llllllllllllllllllIIIIIllllIIIll.getName())), var5);
         }
      }

   }

   @Nullable
   public String getTag() {
      String var1;
      if (llllllllllllllllllIIIIlIIIllllIl.scriptObjectMirror.hasMember("getTag")) {
         Object var10000 = llllllllllllllllllIIIIlIIIllllIl.scriptObjectMirror.callMember("getTag");
         if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
         }

         var1 = (String)var10000;
      } else {
         var1 = null;
      }

      return var1;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllllllllllllllllIIIIlIIIllIIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIllIIIl, "updateEvent");
      call$default(llllllllllllllllllIIIIlIIIllIIlI, "onUpdate", (Object)null, 2, (Object)null);
   }

   @EventTarget
   public final void onSession(@NotNull SessionEvent llllllllllllllllllIIIIIllllIlIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIllllIlIIl, "sessionEvent");
      call$default(llllllllllllllllllIIIIIllllIlIlI, "onSession", (Object)null, 2, (Object)null);
   }

   @EventTarget
   public final void onRender3D(@NotNull Render3DEvent llllllllllllllllllIIIIlIIIIlllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIIlllll, "render3DEvent");
      llllllllllllllllllIIIIlIIIlIIIlI.call("onRender3D", llllllllllllllllllIIIIlIIIIlllll);
   }

   @Nullable
   public Value<?> getValue(@NotNull String llllllllllllllllllIIIIlIIlIIlIlI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIlIIlIlI, "valueName");
      String llllllllllllllllllIIIIlIIlIIIlll = (Iterable)llllllllllllllllllIIIIlIIlIIlIIl._values;
      Exception llllllllllllllllllIIIIlIIlIIIllI = false;
      float llllllllllllllllllIIIIlIIlIIIlII = false;
      Iterator llllllllllllllllllIIIIlIIlIIIIll = llllllllllllllllllIIIIlIIlIIIlll.iterator();

      Object var10000;
      while(true) {
         if (llllllllllllllllllIIIIlIIlIIIIll.hasNext()) {
            char llllllllllllllllllIIIIlIIlIIIIlI = llllllllllllllllllIIIIlIIlIIIIll.next();
            Value llllllllllllllllllIIIIlIIlIIllIl = (Value)llllllllllllllllllIIIIlIIlIIIIlI;
            double llllllllllllllllllIIIIlIIlIIIIII = false;
            if (!StringsKt.equals(llllllllllllllllllIIIIlIIlIIllIl.getName(), llllllllllllllllllIIIIlIIlIIlIlI, true)) {
               continue;
            }

            var10000 = llllllllllllllllllIIIIlIIlIIIIlI;
            break;
         }

         var10000 = null;
         break;
      }

      return (Value)var10000;
   }

   @EventTarget
   public final void onAttack(@NotNull AttackEvent llllllllllllllllllIIIIlIIIIIllll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIIIllll, "attackEvent");
      llllllllllllllllllIIIIlIIIIIlllI.call("onAttack", llllllllllllllllllIIIIlIIIIIllll);
   }

   @NotNull
   public List<Value<?>> getValues() {
      return llllllllllllllllllIIIIlIIlIllIII._values;
   }

   @EventTarget
   public final void onJump(@NotNull JumpEvent llllllllllllllllllIIIIlIIIIlIlIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIIlIlIl, "jumpEvent");
      llllllllllllllllllIIIIlIIIIlIllI.call("onJump", llllllllllllllllllIIIIlIIIIlIlIl);
   }

   public void onEnable() {
      call$default(llllllllllllllllllIIIIlIIIlllIll, "onEnable", (Object)null, 2, (Object)null);
   }

   @EventTarget
   public final void onMotion(@NotNull MotionEvent llllllllllllllllllIIIIlIIIlIlIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIlIlIll, "motionEvent");
      llllllllllllllllllIIIIlIIIlIllII.call("onMotion", llllllllllllllllllIIIIlIIIlIlIll);
   }

   @EventTarget
   public final void onStepConfirm(@NotNull StepConfirmEvent llllllllllllllllllIIIIIlllllIlll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIlllllIlll, "stepConfirmEvent");
      call$default(llllllllllllllllllIIIIIllllllIII, "onStepConfirm", (Object)null, 2, (Object)null);
   }

   // $FF: synthetic method
   static void call$default(ScriptModule var0, String var1, Object var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = null;
      }

      var0.call(var1, var2);
   }

   @EventTarget
   public final void onKey(@NotNull KeyEvent llllllllllllllllllIIIIlIIIIIlIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIIIlIIl, "keyEvent");
      llllllllllllllllllIIIIlIIIIIlIII.call("onKey", llllllllllllllllllIIIIlIIIIIlIIl);
   }

   public ScriptModule(@NotNull ScriptObjectMirror llllllllllllllllllIIIIIllIllIllI) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIllIllIllI, "scriptObjectMirror");
      super();
      llllllllllllllllllIIIIIllIllIlIl.scriptObjectMirror = llllllllllllllllllIIIIIllIllIllI;
      boolean llllllllllllllllllIIIIIllIllIIll = false;
      short llllllllllllllllllIIIIIllIlIllIl = (List)(new ArrayList());
      llllllllllllllllllIIIIIllIllIlIl._values = llllllllllllllllllIIIIIllIlIllIl;
      llllllllllllllllllIIIIIllIllIlIl.eventCache = new HashMap();
      Object var10001 = llllllllllllllllllIIIIIllIllIlIl.scriptObjectMirror.callMember("getName");
      if (var10001 == null) {
         throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
      } else {
         llllllllllllllllllIIIIIllIllIlIl.setName((String)var10001);
         var10001 = llllllllllllllllllIIIIIllIllIlIl.scriptObjectMirror.callMember("getDescription");
         if (var10001 == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
         } else {
            llllllllllllllllllIIIIIllIllIlIl.setDescription((String)var10001);
            if (llllllllllllllllllIIIIIllIllIlIl.scriptObjectMirror.hasMember("addValues")) {
               Exception llllllllllllllllllIIIIIllIllIIlI = false;
               List llllllllllllllllllIIIIIllIlllIlI = (List)(new ArrayList());
               llllllllllllllllllIIIIIllIllIlIl.scriptObjectMirror.callMember("addValues", llllllllllllllllllIIIIIllIlllIlI);
               boolean var14 = false;

               for(Iterator llllllllllllllllllIIIIIllIllIIIl = llllllllllllllllllIIIIIllIlllIlI.iterator(); llllllllllllllllllIIIIIllIllIIIl.hasNext(); var14 = false) {
                  Exception llllllllllllllllllIIIIIllIllIIlI = (AdaptedValue)llllllllllllllllllIIIIIllIllIIIl.next();
                  llllllllllllllllllIIIIIllIllIlIl._values.add(llllllllllllllllllIIIIIllIllIIlI.getValue());
               }
            }

            Object var10000 = llllllllllllllllllIIIIIllIllIlIl.scriptObjectMirror.callMember("getCategory");
            if (var10000 == null) {
               throw new TypeCastException("null cannot be cast to non-null type kotlin.String");
            } else {
               String llllllllllllllllllIIIIIllIlllIII = (String)var10000;
               short llllllllllllllllllIIIIIllIllIIII = ModuleCategory.values();
               String llllllllllllllllllIIIIIllIlIllll = llllllllllllllllllIIIIIllIllIIII.length;

               for(int llllllllllllllllllIIIIIllIllIIIl = 0; llllllllllllllllllIIIIIllIllIIIl < llllllllllllllllllIIIIIllIlIllll; ++llllllllllllllllllIIIIIllIllIIIl) {
                  Exception llllllllllllllllllIIIIIllIllIIlI = llllllllllllllllllIIIIIllIllIIII[llllllllllllllllllIIIIIllIllIIIl];
                  if (StringsKt.equals(llllllllllllllllllIIIIIllIlllIII, llllllllllllllllllIIIIIllIllIIlI.getDisplayName(), true)) {
                     llllllllllllllllllIIIIIllIllIlIl.setCategory(llllllllllllllllllIIIIIllIllIIlI);
                  }
               }

            }
         }
      }
   }

   @EventTarget
   public final void onStep(@NotNull StepEvent llllllllllllllllllIIIIIlllllllIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIlllllllIl, "stepEvent");
      llllllllllllllllllIIIIIlllllllII.call("onStep", llllllllllllllllllIIIIIlllllllIl);
   }

   @EventTarget
   public final void onWorld(@NotNull WorldEvent llllllllllllllllllIIIIIlllllIIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIIlllllIIIl, "worldEvent");
      llllllllllllllllllIIIIIlllllIIlI.call("onWorld", llllllllllllllllllIIIIIlllllIIIl);
   }

   @EventTarget
   public final void onPacket(@NotNull PacketEvent llllllllllllllllllIIIIlIIIIllIIl) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIIllIIl, "packetEvent");
      llllllllllllllllllIIIIlIIIIlllII.call("onPacket", llllllllllllllllllIIIIlIIIIllIIl);
   }

   public void onDisable() {
      call$default(llllllllllllllllllIIIIlIIIlllIII, "onDisable", (Object)null, 2, (Object)null);
   }

   @EventTarget
   public final void onMove(@NotNull MoveEvent llllllllllllllllllIIIIlIIIIIIIll) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIIIIlIIIIIIIll, "moveEvent");
      llllllllllllllllllIIIIlIIIIIIlII.call("onMove", llllllllllllllllllIIIIlIIIIIIIll);
   }
}
