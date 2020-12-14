package net.ccbluex.liquidbounce.script.api.module;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\bJ\u0006\u0010\n\u001a\u00020\bJ\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\bJ\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u0006J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\fJ\u0006\u0010\u0015\u001a\u00020\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/module/AdaptedModule;", "", "module", "Lnet/ccbluex/liquidbounce/features/module/Module;", "(Lnet/ccbluex/liquidbounce/features/module/Module;)V", "getBind", "", "getCategory", "", "getDescription", "getName", "getState", "", "getValue", "name", "register", "", "setBind", "key", "setState", "state", "unregister", "LiquidSense"}
)
public final class AdaptedModule {
   // $FF: synthetic field
   private final Module module;

   @NotNull
   public final String getName() {
      return llIlIIllllIII.module.getName();
   }

   @NotNull
   public final String getDescription() {
      return llIlIIlllIllI.module.getDescription();
   }

   public final void unregister() {
      LiquidBounce.INSTANCE.getModuleManager().unregisterModule(llIlIIlIIIllI.module);
   }

   @NotNull
   public final Object getValue(@NotNull String llIlIIlIlIlII) {
      Intrinsics.checkParameterIsNotNull(llIlIIlIlIlII, "name");
      AdaptedValue var10000 = new AdaptedValue;
      Value var10002 = llIlIIlIlIlIl.module.getValue(llIlIIlIlIlII);
      if (var10002 == null) {
         throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.value.Value<kotlin.Any>");
      } else {
         var10000.<init>(var10002);
         return var10000;
      }
   }

   public final int getBind() {
      return llIlIIllIIllI.module.getKeyBind();
   }

   public final void register() {
      LiquidBounce.INSTANCE.getModuleManager().registerModule(llIlIIlIIllIl.module);
   }

   @NotNull
   public final String getCategory() {
      return llIlIIlllIIlI.module.getCategory().getDisplayName();
   }

   public final void setBind(int llIlIIllIIIlI) {
      llIlIIllIIIll.module.setKeyBind(llIlIIllIIIlI);
   }

   public final boolean getState() {
      return llIlIIlllIIII.module.getState();
   }

   public AdaptedModule(@NotNull Module llIlIIIlllIII) {
      Intrinsics.checkParameterIsNotNull(llIlIIIlllIII, "module");
      super();
      llIlIIIlllllI.module = llIlIIIlllIII;
   }

   public final void setState(boolean llIlIIllIlIIl) {
      llIlIIllIllII.module.setState(llIlIIllIlIIl);
   }
}
