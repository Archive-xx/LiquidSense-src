package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\b"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/TextEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "text", "", "(Ljava/lang/String;)V", "getText", "()Ljava/lang/String;", "setText", "LiquidSense"}
)
public final class TextEvent extends Event {
   // $FF: synthetic field
   @Nullable
   private String text;

   public TextEvent(@Nullable String lllllllllllllllllllIlllllIIIlIll) {
      lllllllllllllllllllIlllllIIIlIlI.text = lllllllllllllllllllIlllllIIIlIll;
   }

   @Nullable
   public final String getText() {
      return lllllllllllllllllllIlllllIIlIllI.text;
   }

   public final void setText(@Nullable String lllllllllllllllllllIlllllIIlIIIl) {
      lllllllllllllllllllIlllllIIlIIlI.text = lllllllllllllllllllIlllllIIlIIIl;
   }
}
