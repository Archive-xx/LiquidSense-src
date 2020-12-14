package net.ccbluex.liquidbounce.utils.login;

public final class MinecraftAccount {
   // $FF: synthetic field
   private String inGameName;
   // $FF: synthetic field
   private final String username;
   // $FF: synthetic field
   private String password;

   public String getName() {
      return llIllIIIlIlIIlI.username;
   }

   public String getPassword() {
      return llIllIIIlIIllII.password;
   }

   public boolean isCracked() {
      return llIllIIIlIlIllI.password == null || llIllIIIlIlIllI.password.isEmpty();
   }

   public void setAccountName(String llIllIIIIllIlIl) {
      llIllIIIIlllIII.inGameName = llIllIIIIllIlIl;
   }

   public MinecraftAccount(String llIllIIlIIIIIll) {
      llIllIIlIIIIIlI.username = llIllIIlIIIIIll;
   }

   public String getAccountName() {
      return llIllIIIlIIIIlI.inGameName;
   }

   public MinecraftAccount(String llIllIIIllIIIlI, String llIllIIIlIlllIl, String llIllIIIlIllIll) {
      llIllIIIlIlllll.username = llIllIIIllIIIlI;
      llIllIIIlIlllll.password = llIllIIIlIlllIl;
      llIllIIIlIlllll.inGameName = llIllIIIlIllIll;
   }

   public MinecraftAccount(String llIllIIIllIllIl, String llIllIIIllIllll) {
      llIllIIIllIlllI.username = llIllIIIllIllIl;
      llIllIIIllIlllI.password = llIllIIIllIllll;
   }
}
