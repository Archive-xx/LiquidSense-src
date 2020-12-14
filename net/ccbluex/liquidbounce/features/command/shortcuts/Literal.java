package net.ccbluex.liquidbounce.features.command.shortcuts;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/shortcuts/Literal;", "Lnet/ccbluex/liquidbounce/features/command/shortcuts/Token;", "literal", "", "(Ljava/lang/String;)V", "getLiteral", "()Ljava/lang/String;", "LiquidSense"}
)
public final class Literal extends Token {
   // $FF: synthetic field
   @NotNull
   private final String literal;

   @NotNull
   public final String getLiteral() {
      return lIIlIIIllIIIl.literal;
   }

   public Literal(@NotNull String lIIlIIIlIllII) {
      Intrinsics.checkParameterIsNotNull(lIIlIIIlIllII, "literal");
      super();
      lIIlIIIlIllIl.literal = lIIlIIIlIllII;
   }
}
