//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.resources.ResourcePackRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ResourcePackRepository.class})
public class MixinResourcePackRepository {
   // $FF: synthetic field
   @Shadow
   @Final
   private static Logger logger;
   // $FF: synthetic field
   @Shadow
   @Final
   private File dirServerResourcepacks;

   @Overwrite
   private void deleteOldServerResourcesPacks() {
      try {
         List<File> lIIIlIlIlIII = Lists.newArrayList(FileUtils.listFiles(lIIIlIlIIIll.dirServerResourcepacks, TrueFileFilter.TRUE, (IOFileFilter)null));
         Collections.sort(lIIIlIlIlIII, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         int lIIIlIlIIlll = 0;
         Iterator lIIIlIlIIIII = lIIIlIlIlIII.iterator();

         while(lIIIlIlIIIII.hasNext()) {
            File lIIIlIlIlIIl = (File)lIIIlIlIIIII.next();
            if (lIIIlIlIIlll++ >= 10) {
               logger.info(String.valueOf((new StringBuilder()).append("Deleting old server resource pack ").append(lIIIlIlIlIIl.getName())));
               FileUtils.deleteQuietly(lIIIlIlIlIIl);
               boolean var10001 = false;
            }
         }
      } catch (Throwable var5) {
         var5.printStackTrace();
      }

   }
}
