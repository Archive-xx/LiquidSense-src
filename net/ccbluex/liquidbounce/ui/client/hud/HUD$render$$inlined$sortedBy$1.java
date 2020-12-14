package net.ccbluex.liquidbounce.ui.client.hud;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.comparisons.ComparisonsKt;
import net.ccbluex.liquidbounce.ui.client.hud.element.Element;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}
)
public final class HUD$render$$inlined$sortedBy$1<T> implements Comparator<T> {
   public final int compare(T lllllllllllllllllIllIlllllllllIl, T lllllllllllllllllIllIllllllllIlI) {
      int lllllllllllllllllIllIllllllllIIl = false;
      long lllllllllllllllllIllIllllllllIII = (Element)lllllllllllllllllIllIlllllllllIl;
      int lllllllllllllllllIllIlllllllllll = false;
      Comparable var10000 = (Comparable)-lllllllllllllllllIllIllllllllIII.getInfo().priority();
      lllllllllllllllllIllIllllllllIII = (Element)lllllllllllllllllIllIllllllllIlI;
      float lllllllllllllllllIllIlllllllIlIl = var10000;
      lllllllllllllllllIllIlllllllllll = false;
      byte lllllllllllllllllIllIlllllllIlII = -lllllllllllllllllIllIllllllllIII.getInfo().priority();
      return ComparisonsKt.compareValues(lllllllllllllllllIllIlllllllIlIl, (Comparable)lllllllllllllllllIllIlllllllIlII);
   }
}
