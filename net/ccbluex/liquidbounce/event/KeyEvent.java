package net.ccbluex.liquidbounce.event;

import kotlin.Metadata;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/KeyEvent;", "Lnet/ccbluex/liquidbounce/event/Event;", "key", "", "(I)V", "getKey", "()I", "LiquidSense"}
)
public final class KeyEvent extends Event {
   // $FF: synthetic field
   private final int key;

   public KeyEvent(int lIIllIIlIlIIIl) {
      lIIllIIlIlIIlI.key = lIIllIIlIlIIIl;
   }

   public final int getKey() {
      return lIIllIIlIllIII.key;
   }
}
