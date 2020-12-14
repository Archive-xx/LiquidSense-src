package net.ccbluex.liquidbounce.utils;

import net.minecraft.util.Vec3;

public class CustomVec3 {
   // $FF: synthetic field
   private double z;
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   private double y;

   public CustomVec3 addVector(double llllllllllllllllllIlllIIlIllIlll, double llllllllllllllllllIlllIIlIllIllI, double llllllllllllllllllIlllIIlIllIIIl) {
      return new CustomVec3(llllllllllllllllllIlllIIlIlllIII.x + llllllllllllllllllIlllIIlIllIlll, llllllllllllllllllIlllIIlIlllIII.y + llllllllllllllllllIlllIIlIllIllI, llllllllllllllllllIlllIIlIlllIII.z + llllllllllllllllllIlllIIlIllIIIl);
   }

   public CustomVec3 add(CustomVec3 llllllllllllllllllIlllIIlIlIIIlI) {
      return llllllllllllllllllIlllIIlIlIIlIl.addVector(llllllllllllllllllIlllIIlIlIIIlI.getX(), llllllllllllllllllIlllIIlIlIIIlI.getY(), llllllllllllllllllIlllIIlIlIIIlI.getZ());
   }

   public CustomVec3 floor() {
      return new CustomVec3(Math.floor(llllllllllllllllllIlllIIlIlIlllI.x), Math.floor(llllllllllllllllllIlllIIlIlIlllI.y), Math.floor(llllllllllllllllllIlllIIlIlIlllI.z));
   }

   public Vec3 mc() {
      return new Vec3(llllllllllllllllllIlllIIlIIlllll.x, llllllllllllllllllIlllIIlIIlllll.y, llllllllllllllllllIlllIIlIIlllll.z);
   }

   public double getY() {
      return llllllllllllllllllIlllIIllIIIIII.y;
   }

   public double squareDistanceTo(CustomVec3 llllllllllllllllllIlllIIlIlIlIII) {
      return Math.pow(llllllllllllllllllIlllIIlIlIlIII.x - llllllllllllllllllIlllIIlIlIlIIl.x, 2.0D) + Math.pow(llllllllllllllllllIlllIIlIlIlIII.y - llllllllllllllllllIlllIIlIlIlIIl.y, 2.0D) + Math.pow(llllllllllllllllllIlllIIlIlIlIII.z - llllllllllllllllllIlllIIlIlIlIIl.z, 2.0D);
   }

   public double getX() {
      return llllllllllllllllllIlllIIllIIIIll.x;
   }

   public CustomVec3(double llllllllllllllllllIlllIIllIIlIII, double llllllllllllllllllIlllIIllIIIlll, double llllllllllllllllllIlllIIllIIlIlI) {
      llllllllllllllllllIlllIIllIIllIl.x = llllllllllllllllllIlllIIllIIlIII;
      llllllllllllllllllIlllIIllIIllIl.y = llllllllllllllllllIlllIIllIIIlll;
      llllllllllllllllllIlllIIllIIllIl.z = llllllllllllllllllIlllIIllIIlIlI;
   }

   public double getZ() {
      return llllllllllllllllllIlllIIlIllllIl.z;
   }

   public String toString() {
      return String.valueOf((new StringBuilder()).append("[").append(llllllllllllllllllIlllIIlIIlllIl.x).append(";").append(llllllllllllllllllIlllIIlIIlllIl.y).append(";").append(llllllllllllllllllIlllIIlIIlllIl.z).append("]"));
   }
}
