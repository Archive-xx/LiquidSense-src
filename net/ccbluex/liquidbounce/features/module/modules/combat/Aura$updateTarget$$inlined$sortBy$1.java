//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}
)
public final class Aura$updateTarget$$inlined$sortBy$1<T> implements Comparator<T> {
   public final int compare(T llllllllllllllllllIllIllIIIlIlll, T llllllllllllllllllIllIllIIIlIllI) {
      int llllllllllllllllllIllIllIIIlIlIl = false;
      EntityLivingBase llllllllllllllllllIllIllIIIlIlII = (EntityLivingBase)llllllllllllllllllIllIllIIIlIlll;
      char llllllllllllllllllIllIllIIIlIIll = false;
      EntityPlayerSP var10000 = Aura.access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      Comparable var9 = (Comparable)PlayerExtensionKt.getDistanceToEntityBox((Entity)var10000, (Entity)llllllllllllllllllIllIllIIIlIlII);
      llllllllllllllllllIllIllIIIlIlII = (EntityLivingBase)llllllllllllllllllIllIllIIIlIllI;
      int llllllllllllllllllIllIllIIIlIIlI = var9;
      llllllllllllllllllIllIllIIIlIIll = false;
      var10000 = Aura.access$getMc$p$s1046033730().thePlayer;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc.thePlayer");
      long llllllllllllllllllIllIllIIIlIIIl = PlayerExtensionKt.getDistanceToEntityBox((Entity)var10000, (Entity)llllllllllllllllllIllIllIIIlIlII);
      return ComparisonsKt.compareValues(llllllllllllllllllIllIllIIIlIIlI, (Comparable)llllllllllllllllllIllIllIIIlIIIl);
   }
}
