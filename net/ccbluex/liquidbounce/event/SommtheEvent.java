package net.ccbluex.liquidbounce.event;

public class SommtheEvent extends Event {
   // $FF: synthetic field
   private double x;
   // $FF: synthetic field
   public static boolean SNEAKING;
   // $FF: synthetic field
   public static float YAW;
   // $FF: synthetic field
   public static float PREVPITCH;
   // $FF: synthetic field
   private boolean sneaking;
   // $FF: synthetic field
   public static float PREVYAW;
   // $FF: synthetic field
   public static float PITCH;
   // $FF: synthetic field
   private float yaw;
   // $FF: synthetic field
   private double z;
   // $FF: synthetic field
   private float pitch;
   // $FF: synthetic field
   private boolean onground;
   // $FF: synthetic field
   private double y;

   public boolean isSneaking() {
      return llIlIIlIIIlI.sneaking;
   }

   public float getPitch() {
      return llIlIIlIlllI.pitch;
   }

   public double getX() {
      return llIlIIlIlIll.x;
   }

   public double getY() {
      return llIlIIlIlIII.y;
   }

   public SommtheEvent(double llIlIlIIIlIl, double llIlIlIIIlII, double llIlIlIIIIll, float llIlIIlllIlI, float llIlIlIIIIIl, boolean llIlIIlllIII, boolean llIlIIllllll) {
      llIlIlIIIllI.yaw = llIlIIlllIlI;
      llIlIlIIIllI.pitch = llIlIlIIIIIl;
      llIlIlIIIllI.y = llIlIlIIIlII;
      llIlIlIIIllI.x = llIlIlIIIlIl;
      llIlIlIIIllI.z = llIlIlIIIIll;
      llIlIlIIIllI.onground = llIlIIllllll;
      llIlIlIIIllI.sneaking = llIlIIlllIII;
   }

   public float getYaw() {
      return llIlIIllIIlI.yaw;
   }

   public double getZ() {
      return llIlIIlIIllI.z;
   }

   public void fire() {
      PREVYAW = YAW;
      PREVPITCH = PITCH;
      YAW = llIlIIllIlIl.yaw;
      PITCH = llIlIIllIlIl.pitch;
      SNEAKING = llIlIIllIlIl.sneaking;
   }

   public boolean isOnground() {
      return llIlIIlIIIII.onground;
   }
}
