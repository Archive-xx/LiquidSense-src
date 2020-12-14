package net.ccbluex.liquidbounce.utils.timer;

public final class TickTimer {
   // $FF: synthetic field
   private int tick;

   public boolean hasTimePassed(int llIlllIllIllIII) {
      return llIlllIllIllIll.tick >= llIlllIllIllIII;
   }

   public void update() {
      ++llIlllIlllIIIlI.tick;
   }

   public void reset() {
      llIlllIllIllllI.tick = 0;
   }
}
