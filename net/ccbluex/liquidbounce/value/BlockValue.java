package net.ccbluex.liquidbounce.value;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/value/BlockValue;", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "name", "", "value", "", "(Ljava/lang/String;I)V", "LiquidSense"}
)
public final class BlockValue extends IntegerValue {
   public BlockValue(@NotNull String lllllllllllllllllIlllllIIlIlllIl, int lllllllllllllllllIlllllIIlIlllll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIlllllIIlIlllIl, "name");
      super(lllllllllllllllllIlllllIIlIlllIl, lllllllllllllllllIlllllIIlIlllll, 1, 197);
   }
}
