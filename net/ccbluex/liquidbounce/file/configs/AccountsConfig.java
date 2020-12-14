package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.Gson;
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
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.login.MinecraftAccount;

public class AccountsConfig extends FileConfig {
   // $FF: synthetic field
   public final List<MinecraftAccount> altManagerMinecraftAccounts = new ArrayList();

   protected void saveConfig() throws IOException {
      List<String> lIllIlIIllIlIIl = new ArrayList();

      boolean var10001;
      for(Iterator lIllIlIIllIIIll = lIllIlIIllIlIlI.altManagerMinecraftAccounts.iterator(); lIllIlIIllIIIll.hasNext(); var10001 = false) {
         byte lIllIlIIllIIIIl = (MinecraftAccount)lIllIlIIllIIIll.next();
         lIllIlIIllIlIIl.add(String.valueOf((new StringBuilder()).append(lIllIlIIllIIIIl.getName()).append(":").append(lIllIlIIllIIIIl.getPassword() == null ? "" : lIllIlIIllIIIIl.getPassword()).append(":").append(lIllIlIIllIIIIl.getAccountName() == null ? "" : lIllIlIIllIIIIl.getAccountName())));
      }

      byte lIllIlIIllIIIll = new PrintWriter(new FileWriter(lIllIlIIllIlIlI.getFile()));
      lIllIlIIllIIIll.println(FileManager.PRETTY_GSON.toJson(lIllIlIIllIlIIl));
      lIllIlIIllIIIll.close();
   }

   protected void loadConfig() throws IOException {
      List<String> lIllIlIIlllIllI = (List)(new Gson()).fromJson(new BufferedReader(new FileReader(lIllIlIIlllIlIl.getFile())), List.class);
      if (lIllIlIIlllIllI != null) {
         lIllIlIIlllIlIl.altManagerMinecraftAccounts.clear();
         Iterator lIllIlIIlllIIll = lIllIlIIlllIllI.iterator();

         while(lIllIlIIlllIIll.hasNext()) {
            String lIllIlIIllllIII = (String)lIllIlIIlllIIll.next();
            String[] lIllIlIIllllIIl = lIllIlIIllllIII.split(":");
            boolean var10001;
            if (lIllIlIIllllIIl.length >= 3) {
               lIllIlIIlllIlIl.altManagerMinecraftAccounts.add(new MinecraftAccount(lIllIlIIllllIIl[0], lIllIlIIllllIIl[1], lIllIlIIllllIIl[2]));
               var10001 = false;
            } else if (lIllIlIIllllIIl.length == 2) {
               lIllIlIIlllIlIl.altManagerMinecraftAccounts.add(new MinecraftAccount(lIllIlIIllllIIl[0], lIllIlIIllllIIl[1]));
               var10001 = false;
            } else {
               lIllIlIIlllIlIl.altManagerMinecraftAccounts.add(new MinecraftAccount(lIllIlIIllllIIl[0]));
               var10001 = false;
            }
         }

      }
   }

   public AccountsConfig(File lIllIlIlIIIllIl) {
      super(lIllIlIlIIIllIl);
   }
}
