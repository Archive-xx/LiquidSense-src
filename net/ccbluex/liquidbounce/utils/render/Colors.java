package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;

public enum Colors {
   // $FF: synthetic field
   BLACK(-16711423),
   // $FF: synthetic field
   AQUA(-7820064),
   // $FF: synthetic field
   DARKMAGENTA(-2252579),
   // $FF: synthetic field
   BLUE(-12028161),
   // $FF: synthetic field
   GREEN(-9830551),
   // $FF: synthetic field
   ORANGE(-29696),
   // $FF: synthetic field
   MAGENTA(-18751),
   // $FF: synthetic field
   DARKGREEN(-9320847),
   // $FF: synthetic field
   YELLOW(-256),
   // $FF: synthetic field
   DARKRED(-8388608),
   // $FF: synthetic field
   DARKORANGE(-2263808),
   // $FF: synthetic field
   DARKGREY(-14342875),
   // $FF: synthetic field
   GREY(-9868951),
   // $FF: synthetic field
   DARKBLUE(-12621684);

   // $FF: synthetic field
   public int c;
   // $FF: synthetic field
   DARKAQUA(-12621684),
   // $FF: synthetic field
   RED(-65536),
   // $FF: synthetic field
   DARKYELLOW(-2702025),
   // $FF: synthetic field
   WHITE(-65794);

   public static int getColor(int llllIlllIllIlIl, int llllIlllIlIlllI, int llllIlllIlIllIl, int llllIlllIllIIlI) {
      float llllIlllIlIlIll = 0;
      byte llllIlllIlIlIlI = llllIlllIlIlIll | llllIlllIllIIlI << 24;
      llllIlllIlIlIlI |= llllIlllIllIlIl << 16;
      llllIlllIlIlIlI |= llllIlllIlIlllI << 8;
      llllIlllIlIlIlI |= llllIlllIlIllIl;
      return llllIlllIlIlIlI;
   }

   private Colors(int llllIllllIlIlIl) {
      llllIllllIlIllI.c = llllIllllIlIlIl;
   }

   public static int getColor(int llllIllllIIIIIl, int llllIllllIIIIII, int llllIlllIllllll) {
      return getColor(llllIllllIIIIIl, llllIllllIIIIII, llllIlllIllllll, 255);
   }

   public static int getColor(Color llllIllllIIlllI) {
      return getColor(llllIllllIIlllI.getRed(), llllIllllIIlllI.getGreen(), llllIllllIIlllI.getBlue(), llllIllllIIlllI.getAlpha());
   }

   public static int getColor(int llllIlllIllllII) {
      return getColor(llllIlllIllllII, llllIlllIllllII, llllIlllIllllII, 255);
   }

   public static int getColor(int llllIllllIIlIIl, int llllIllllIIlIII) {
      return getColor(llllIllllIIlIIl, llllIllllIIlIIl, llllIllllIIlIIl, llllIllllIIlIII);
   }
}
