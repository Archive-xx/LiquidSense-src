package net.ccbluex.liquidbounce.features.module;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000e¨\u0006\u000f"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "", "displayName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "COMBAT", "PLAYER", "MOVEMENT", "RENDER", "WORLD", "MISC", "EXPLOIT", "FUN", "LiquidSense"}
)
public enum ModuleCategory {
   // $FF: synthetic field
   PLAYER,
   // $FF: synthetic field
   EXPLOIT,
   // $FF: synthetic field
   MOVEMENT,
   // $FF: synthetic field
   COMBAT;

   // $FF: synthetic field
   @NotNull
   private final String displayName;
   // $FF: synthetic field
   WORLD,
   // $FF: synthetic field
   RENDER,
   // $FF: synthetic field
   MISC,
   // $FF: synthetic field
   FUN;

   private ModuleCategory(String llIIlllIlIIlIlI) {
      llIIlllIlIIllIl.displayName = llIIlllIlIIlIlI;
   }

   @NotNull
   public final String getDisplayName() {
      return llIIlllIlIlIIll.displayName;
   }
}
