package net.ccbluex.liquidbounce.file.configs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ccbluex.liquidbounce.file.FileConfig;

public class FriendsConfig extends FileConfig {
   // $FF: synthetic field
   private final List<FriendsConfig.Friend> friends = new ArrayList();

   protected void saveConfig() throws IOException {
      PrintWriter lllIlIIIIlIIIll = new PrintWriter(new FileWriter(lllIlIIIIlIIlII.getFile()));

      boolean var10001;
      for(Iterator lllIlIIIIlIIIII = lllIlIIIIlIIlII.getFriends().iterator(); lllIlIIIIlIIIII.hasNext(); var10001 = false) {
         int lllIlIIIIIlllll = (FriendsConfig.Friend)lllIlIIIIlIIIII.next();
         lllIlIIIIlIIIll.append(lllIlIIIIIlllll.getPlayerName()).append(":").append(lllIlIIIIIlllll.getAlias()).append("\n");
      }

      lllIlIIIIlIIIll.close();
   }

   public boolean removeFriend(String lllIlIIIIIIllII) {
      if (!lllIlIIIIIIlIll.isFriend(lllIlIIIIIIllII)) {
         return false;
      } else {
         lllIlIIIIIIlIll.friends.removeIf((lllIIllllllIlIl) -> {
            return lllIIllllllIlIl.getPlayerName().equals(lllIlIIIIIIllII);
         });
         boolean var10001 = false;
         return true;
      }
   }

   public void clearFriends() {
      lllIIllllllllIl.friends.clear();
   }

   protected void loadConfig() throws IOException {
      lllIlIIIIllIIII.clearFriends();
      BufferedReader lllIlIIIIlIllII = new BufferedReader(new FileReader(lllIlIIIIllIIII.getFile()));

      String lllIlIIIIlIlIll;
      while((lllIlIIIIlIlIll = lllIlIIIIlIllII.readLine()) != null) {
         if (!lllIlIIIIlIlIll.contains("{") && !lllIlIIIIlIlIll.contains("}")) {
            lllIlIIIIlIlIll = lllIlIIIIlIlIll.replace(" ", "").replace("\"", "").replace(",", "");
            boolean var10001;
            if (lllIlIIIIlIlIll.contains(":")) {
               float lllIlIIIIlIlIlI = lllIlIIIIlIlIll.split(":");
               lllIlIIIIllIIII.addFriend(lllIlIIIIlIlIlI[0], lllIlIIIIlIlIlI[1]);
               var10001 = false;
            } else {
               lllIlIIIIllIIII.addFriend(lllIlIIIIlIlIll);
               var10001 = false;
            }
         }
      }

      lllIlIIIIlIllII.close();
   }

   public FriendsConfig(File lllIlIIIIllIllI) {
      super(lllIlIIIIllIllI);
   }

   public List<FriendsConfig.Friend> getFriends() {
      return lllIIlllllllIIl.friends;
   }

   public boolean addFriend(String lllIlIIIIIlIIIl, String lllIlIIIIIlIIII) {
      if (lllIlIIIIIlIIlI.isFriend(lllIlIIIIIlIIIl)) {
         return false;
      } else {
         lllIlIIIIIlIIlI.friends.add(new FriendsConfig.Friend(lllIlIIIIIlIIIl, lllIlIIIIIlIIII));
         boolean var10001 = false;
         return true;
      }
   }

   public boolean addFriend(String lllIlIIIIIllIll) {
      return lllIlIIIIIlllII.addFriend(lllIlIIIIIllIll, lllIlIIIIIllIll);
   }

   public boolean isFriend(String lllIlIIIIIIIIIl) {
      Iterator lllIlIIIIIIIIII = lllIlIIIIIIIlII.friends.iterator();

      FriendsConfig.Friend lllIIllllllllll;
      do {
         if (!lllIlIIIIIIIIII.hasNext()) {
            return false;
         }

         lllIIllllllllll = (FriendsConfig.Friend)lllIlIIIIIIIIII.next();
      } while(!lllIIllllllllll.getPlayerName().equals(lllIlIIIIIIIIIl));

      return true;
   }

   public class Friend {
      // $FF: synthetic field
      private final String alias;
      // $FF: synthetic field
      private final String playerName;

      public String getPlayerName() {
         return lllIIIlIlIIllIl.playerName;
      }

      public String getAlias() {
         return lllIIIlIlIIlIII.alias;
      }

      Friend(String lllIIIlIlIllIll, String lllIIIlIlIlIIll) {
         lllIIIlIlIllllI.playerName = lllIIIlIlIllIll;
         lllIIIlIlIllllI.alias = lllIIIlIlIlIIll;
      }
   }
}
