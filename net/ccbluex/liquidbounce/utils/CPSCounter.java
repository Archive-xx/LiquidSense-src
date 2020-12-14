package net.ccbluex.liquidbounce.utils;

public class CPSCounter {
   // $FF: synthetic field
   private static final RollingArrayLongBuffer[] TIMESTAMP_BUFFERS = new RollingArrayLongBuffer[CPSCounter.MouseButton.values().length];
   // $FF: synthetic field
   private static final int MAX_CPS = 50;

   static {
      for(int lIIlIIIlllllII = 0; lIIlIIIlllllII < TIMESTAMP_BUFFERS.length; ++lIIlIIIlllllII) {
         TIMESTAMP_BUFFERS[lIIlIIIlllllII] = new RollingArrayLongBuffer(50);
      }

   }

   public static void registerClick(CPSCounter.MouseButton lIIlIIlIIIIIlI) {
      TIMESTAMP_BUFFERS[lIIlIIlIIIIIlI.getIndex()].add(System.currentTimeMillis());
   }

   public static int getCPS(CPSCounter.MouseButton lIIlIIIlllllll) {
      return TIMESTAMP_BUFFERS[lIIlIIIlllllll.getIndex()].getTimestampsSince(System.currentTimeMillis() - 1000L);
   }

   public static enum MouseButton {
      // $FF: synthetic field
      LEFT(0),
      // $FF: synthetic field
      RIGHT(2);

      // $FF: synthetic field
      private int index;
      // $FF: synthetic field
      MIDDLE(1);

      private MouseButton(int lllllllllllllllllIllllIIIIIlIIIl) {
         lllllllllllllllllIllllIIIIIlIllI.index = lllllllllllllllllIllllIIIIIlIIIl;
      }

      private int getIndex() {
         return lllllllllllllllllIllllIIIIIIlllI.index;
      }
   }
}
