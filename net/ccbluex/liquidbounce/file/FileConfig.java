package net.ccbluex.liquidbounce.file;

import java.io.File;
import java.io.IOException;

public abstract class FileConfig {
   // $FF: synthetic field
   private final File file;

   public FileConfig(File llllllllllllllllllIlIIlIIIIIlIII) {
      llllllllllllllllllIlIIlIIIIIlIIl.file = llllllllllllllllllIlIIlIIIIIlIII;
   }

   public File getFile() {
      return llllllllllllllllllIlIIIlllllllIl.file;
   }

   protected abstract void saveConfig() throws IOException;

   protected abstract void loadConfig() throws IOException;

   public void createConfig() throws IOException {
      llllllllllllllllllIlIIlIIIIIIlII.file.createNewFile();
      boolean var10001 = false;
   }

   public boolean hasConfig() {
      return llllllllllllllllllIlIIlIIIIIIIIl.file.exists();
   }
}
