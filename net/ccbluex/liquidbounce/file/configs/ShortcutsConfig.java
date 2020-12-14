package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.shortcuts.Shortcut;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0014J\b\u0010\u0007\u001a\u00020\u0006H\u0014¨\u0006\b"},
   d2 = {"Lnet/ccbluex/liquidbounce/file/configs/ShortcutsConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "saveConfig", "LiquidSense"}
)
public final class ShortcutsConfig extends FileConfig {
   protected void loadConfig() {
      JsonParser var10000 = new JsonParser();
      File var10001 = lllllllllllllllllIlllllIIIIlIIII.getFile();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "file");
      JsonElement lllllllllllllllllIlllllIIIIlIIIl = var10000.parse(FilesKt.readText$default(var10001, (Charset)null, 1, (Object)null));
      if (lllllllllllllllllIlllllIIIIlIIIl instanceof JsonArray) {
         Iterator lllllllllllllllllIlllllIIIIIllII = ((JsonArray)lllllllllllllllllIlllllIIIIlIIIl).iterator();

         while(true) {
            while(true) {
               label57:
               while(true) {
                  JsonElement lllllllllllllllllIlllllIIIIIllIl;
                  do {
                     if (!lllllllllllllllllIlllllIIIIIllII.hasNext()) {
                        return;
                     }

                     lllllllllllllllllIlllllIIIIIllIl = (JsonElement)lllllllllllllllllIlllllIIIIIllII.next();
                  } while(!(lllllllllllllllllIlllllIIIIIllIl instanceof JsonObject));

                  JsonElement var31 = ((JsonObject)lllllllllllllllllIlllllIIIIIllIl).get("name");
                  boolean var33;
                  if (var31 != null) {
                     String var32 = var31.getAsString();
                     if (var32 != null) {
                        short lllllllllllllllllIlllllIIIIIlIll = var32;
                        var31 = ((JsonObject)lllllllllllllllllIlllllIIIIIllIl).get("script");
                        if (var31 != null) {
                           JsonArray var34 = var31.getAsJsonArray();
                           if (var34 != null) {
                              byte lllllllllllllllllIlllllIIIIIlIlI = var34;
                              long lllllllllllllllllIlllllIIIIIlIII = false;
                              List lllllllllllllllllIlllllIIIIlIlIl = (List)(new ArrayList());
                              Iterator lllllllllllllllllIlllllIIIIIIlll = lllllllllllllllllIlllllIIIIIlIlI.iterator();

                              while(true) {
                                 while(true) {
                                    while(true) {
                                       while(true) {
                                          JsonElement lllllllllllllllllIlllllIIIIlIllI;
                                          do {
                                             if (!lllllllllllllllllIlllllIIIIIIlll.hasNext()) {
                                                LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command)(new Shortcut(lllllllllllllllllIlllllIIIIIlIll, lllllllllllllllllIlllllIIIIlIlIl)));
                                                var33 = false;
                                                continue label57;
                                             }

                                             lllllllllllllllllIlllllIIIIlIllI = (JsonElement)lllllllllllllllllIlllllIIIIIIlll.next();
                                          } while(!(lllllllllllllllllIlllllIIIIlIllI instanceof JsonObject));

                                          var31 = ((JsonObject)lllllllllllllllllIlllllIIIIlIllI).get("name");
                                          if (var31 != null) {
                                             var32 = var31.getAsString();
                                             if (var32 != null) {
                                                String lllllllllllllllllIlllllIIIIlIlll = var32;
                                                var31 = ((JsonObject)lllllllllllllllllIlllllIIIIlIllI).get("arguments");
                                                if (var31 != null) {
                                                   var34 = var31.getAsJsonArray();
                                                   if (var34 != null) {
                                                      short lllllllllllllllllIlllllIIIIIIlIl = var34;
                                                      Command var35 = LiquidBounce.INSTANCE.getCommandManager().getCommand(lllllllllllllllllIlllllIIIIlIlll);
                                                      if (var35 != null) {
                                                         Command lllllllllllllllllIlllllIIIIllIIl = var35;
                                                         char lllllllllllllllllIlllllIIIIIIIll = (Iterable)lllllllllllllllllIlllllIIIIIIlIl;
                                                         long lllllllllllllllllIlllllIIIIIIIlI = false;
                                                         double lllllllllllllllllIlllllIIIIIIIII = (Collection)(new ArrayList(CollectionsKt.collectionSizeOrDefault(lllllllllllllllllIlllllIIIIIIIll, 10)));
                                                         int lllllllllllllllllIlllllIIIIlllll = false;

                                                         for(Iterator lllllllllllllllllIllllIllllllllI = lllllllllllllllllIlllllIIIIIIIll.iterator(); lllllllllllllllllIllllIllllllllI.hasNext(); var33 = false) {
                                                            float lllllllllllllllllIllllIlllllllIl = lllllllllllllllllIllllIllllllllI.next();
                                                            JsonElement lllllllllllllllllIlllllIIIlIIlII = (JsonElement)lllllllllllllllllIllllIlllllllIl;
                                                            boolean lllllllllllllllllIllllIllllllIll = false;
                                                            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIlllllIIIlIIlII, "it");
                                                            int lllllllllllllllllIllllIlllllIlll = lllllllllllllllllIlllllIIIlIIlII.getAsString();
                                                            lllllllllllllllllIlllllIIIIIIIII.add(lllllllllllllllllIllllIlllllIlll);
                                                         }

                                                         byte lllllllllllllllllIllllIllllllIII = (List)lllllllllllllllllIlllllIIIIIIIII;
                                                         char lllllllllllllllllIlllllIIIIIIIll = (Collection)lllllllllllllllllIllllIllllllIII;
                                                         lllllllllllllllllIlllllIIIIIIIlI = false;
                                                         Object[] var36 = lllllllllllllllllIlllllIIIIIIIll.toArray(new String[0]);
                                                         if (var36 == null) {
                                                            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                                                         }

                                                         byte lllllllllllllllllIllllIllllllIII = var36;
                                                         lllllllllllllllllIlllllIIIIlIlIl.add(new Pair(lllllllllllllllllIlllllIIIIllIIl, lllllllllllllllllIllllIllllllIII));
                                                         var33 = false;
                                                         continue;
                                                      }

                                                      var33 = false;
                                                      continue;
                                                   }
                                                }

                                                var33 = false;
                                                continue;
                                             }
                                          }

                                          var33 = false;
                                       }
                                    }
                                 }
                              }
                           }
                        }

                        var33 = false;
                        continue;
                     }
                  }

                  var33 = false;
               }
            }
         }
      }
   }

   protected void saveConfig() {
      long lllllllllllllllllIllllIlllIlllII = new JsonArray();
      Iterator lllllllllllllllllIllllIlllIllIlI = LiquidBounce.INSTANCE.getCommandManager().getCommands().iterator();

      while(true) {
         Command lllllllllllllllllIllllIlllIllIll;
         do {
            if (!lllllllllllllllllIllllIlllIllIlI.hasNext()) {
               File var10000 = lllllllllllllllllIllllIlllIllllI.getFile();
               Intrinsics.checkExpressionValueIsNotNull(var10000, "file");
               String var10001 = FileManager.PRETTY_GSON.toJson((JsonElement)lllllllllllllllllIllllIlllIlllII);
               Intrinsics.checkExpressionValueIsNotNull(var10001, "FileManager.PRETTY_GSON.toJson(jsonArray)");
               FilesKt.writeText$default(var10000, var10001, (Charset)null, 2, (Object)null);
               return;
            }

            lllllllllllllllllIllllIlllIllIll = (Command)lllllllllllllllllIllllIlllIllIlI.next();
         } while(!(lllllllllllllllllIllllIlllIllIll instanceof Shortcut));

         JsonObject lllllllllllllllllIllllIllllIIIIl = new JsonObject();
         lllllllllllllllllIllllIllllIIIIl.addProperty("name", lllllllllllllllllIllllIlllIllIll.getCommand());
         JsonArray lllllllllllllllllIllllIllllIIIlI = new JsonArray();
         Iterator lllllllllllllllllIllllIlllIlIllI = ((Shortcut)lllllllllllllllllIllllIlllIllIll).getScript().iterator();

         while(lllllllllllllllllIllllIlllIlIllI.hasNext()) {
            Pair lllllllllllllllllIllllIllllIIIll = (Pair)lllllllllllllllllIllllIlllIlIllI.next();
            byte lllllllllllllllllIllllIlllIlIlIl = new JsonObject();
            lllllllllllllllllIllllIlllIlIlIl.addProperty("name", ((Command)lllllllllllllllllIllllIllllIIIll.getFirst()).getCommand());
            JsonArray lllllllllllllllllIllllIllllIIlIl = new JsonArray();
            float lllllllllllllllllIllllIlllIlIIIl = (String[])lllllllllllllllllIllllIllllIIIll.getSecond();
            float lllllllllllllllllIllllIlllIlIIII = lllllllllllllllllIllllIlllIlIIIl.length;

            for(int lllllllllllllllllIllllIlllIlIIlI = 0; lllllllllllllllllIllllIlllIlIIlI < lllllllllllllllllIllllIlllIlIIII; ++lllllllllllllllllIllllIlllIlIIlI) {
               int lllllllllllllllllIllllIlllIlIIll = lllllllllllllllllIllllIlllIlIIIl[lllllllllllllllllIllllIlllIlIIlI];
               lllllllllllllllllIllllIllllIIlIl.add(lllllllllllllllllIllllIlllIlIIll);
            }

            lllllllllllllllllIllllIlllIlIlIl.add("arguments", (JsonElement)lllllllllllllllllIllllIllllIIlIl);
            lllllllllllllllllIllllIllllIIIlI.add((JsonElement)lllllllllllllllllIllllIlllIlIlIl);
         }

         lllllllllllllllllIllllIllllIIIIl.add("script", (JsonElement)lllllllllllllllllIllllIllllIIIlI);
         lllllllllllllllllIllllIlllIlllII.add((JsonElement)lllllllllllllllllIllllIllllIIIIl);
      }
   }

   public ShortcutsConfig(@NotNull File lllllllllllllllllIllllIlllIIlIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllllIlllIIlIlI, "file");
      super(lllllllllllllllllIllllIlllIIlIlI);
   }
}
