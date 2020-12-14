package net.ccbluex.liquidbounce.utils;

public class RollingArrayLongBuffer {
   // $FF: synthetic field
   private int currentIndex = 0;
   // $FF: synthetic field
   private final long[] contents;

   public int getTimestampsSince(long llIlIlIIIIllllI) {
      for(int llIlIlIIIlIIIlI = 0; llIlIlIIIlIIIlI < llIlIlIIIlIIIIl.contents.length; ++llIlIlIIIlIIIlI) {
         if (llIlIlIIIlIIIIl.contents[llIlIlIIIlIIIIl.currentIndex < llIlIlIIIlIIIlI ? llIlIlIIIlIIIIl.contents.length - llIlIlIIIlIIIlI + llIlIlIIIlIIIIl.currentIndex : llIlIlIIIlIIIIl.currentIndex - llIlIlIIIlIIIlI] < llIlIlIIIIllllI) {
            return llIlIlIIIlIIIlI;
         }
      }

      return llIlIlIIIlIIIIl.contents.length;
   }

   public RollingArrayLongBuffer(int llIlIlIIIlIllll) {
      llIlIlIIIllIIII.contents = new long[llIlIlIIIlIllll];
   }

   public long[] getContents() {
      return llIlIlIIIlIllIl.contents;
   }

   public void add(long llIlIlIIIlIIllI) {
      llIlIlIIIlIIlll.currentIndex = (llIlIlIIIlIIlll.currentIndex + 1) % llIlIlIIIlIIlll.contents.length;
      llIlIlIIIlIIlll.contents[llIlIlIIIlIIlll.currentIndex] = llIlIlIIIlIIllI;
   }
}
