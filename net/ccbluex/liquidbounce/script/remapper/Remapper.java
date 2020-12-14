package net.ccbluex.liquidbounce.script.remapper;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\fH\u0002J\u001a\u0010\u000e\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u0005J\"\u0010\u0012\u001a\u00020\u00052\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005RR\u0010\u0003\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000RR\u0010\u0007\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/remapper/Remapper;", "", "()V", "fields", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "methods", "srgFile", "Ljava/io/File;", "srgName", "loadSrg", "", "parseSrg", "remapField", "clazz", "Ljava/lang/Class;", "name", "remapMethod", "desc", "LiquidSense"}
)
public final class Remapper {
   // $FF: synthetic field
   private static final HashMap<String, HashMap<String, String>> fields;
   // $FF: synthetic field
   private static final File srgFile;
   // $FF: synthetic field
   public static final Remapper INSTANCE;
   // $FF: synthetic field
   private static final HashMap<String, HashMap<String, String>> methods;
   // $FF: synthetic field
   private static final String srgName = "stable_22";

   @NotNull
   public final String remapMethod(@NotNull Class<?> lIlIlllIlIlIlll, @NotNull String lIlIlllIlIlIllI, @NotNull String lIlIlllIlIlIlIl) {
      Intrinsics.checkParameterIsNotNull(lIlIlllIlIlIlll, "clazz");
      Intrinsics.checkParameterIsNotNull(lIlIlllIlIlIllI, "name");
      Intrinsics.checkParameterIsNotNull(lIlIlllIlIlIlIl, "desc");
      if (!methods.containsKey(lIlIlllIlIlIlll.getName())) {
         return lIlIlllIlIlIllI;
      } else {
         Object var10000 = methods.get(lIlIlllIlIlIlll.getName());
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         var10000 = ((HashMap)var10000).getOrDefault(String.valueOf((new StringBuilder()).append(lIlIlllIlIlIllI).append(lIlIlllIlIlIlIl)), lIlIlllIlIlIllI);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "methods[clazz.name]!!.ge…efault(name + desc, name)");
         return (String)var10000;
      }
   }

   private final void parseSrg() {
      Iterable lIlIlllIlllllII = (Iterable)FilesKt.readLines$default(srgFile, (Charset)null, 1, (Object)null);
      String lIlIlllIllllIII = false;
      Iterator lIlIlllIlllIlll = lIlIlllIlllllII.iterator();

      while(lIlIlllIlllIlll.hasNext()) {
         String lIlIlllIlllIllI = lIlIlllIlllIlll.next();
         long lIlIlllIlllIlIl = (String)lIlIlllIlllIllI;
         short lIlIlllIlllIlII = false;
         boolean lIlIlllIlllIIll = StringsKt.split$default((CharSequence)lIlIlllIlllIlIl, new String[]{" "}, false, 0, 6, (Object)null);
         String lIlIllllIIIIIIl;
         String lIlIllllIIIIIlI;
         String lIlIllllIIIlIlI;
         boolean lIlIlllIllIllIl;
         String lIlIllllIIIlIIl;
         boolean lIlIlllIllIlIll;
         String var10000;
         boolean lIlIlllIllIlIlI;
         boolean var10001;
         Map lIlIlllIllIlIII;
         HashMap lIlIlllIllIIlll;
         String lIlIlllIllIllll;
         int lIlIlllIllIllIl;
         Object var29;
         if (StringsKt.startsWith$default(lIlIlllIlllIlIl, "FD:", false, 2, (Object)null)) {
            lIlIllllIIIIIIl = (String)lIlIlllIlllIIll.get(1);
            lIlIllllIIIIIlI = (String)lIlIlllIlllIIll.get(2);
            double lIlIlllIllIllll = 0;
            int lIlIlllIllIlllI = StringsKt.lastIndexOf$default((CharSequence)lIlIllllIIIIIIl, '/', 0, false, 6, (Object)null);
            lIlIlllIllIllIl = false;
            if (lIlIllllIIIIIIl == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = lIlIllllIIIIIIl.substring(lIlIlllIllIllll, lIlIlllIllIlllI);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            lIlIllllIIIlIIl = StringsKt.replace$default(var10000, '/', '.', false, 4, (Object)null);
            lIlIlllIllIlllI = StringsKt.lastIndexOf$default((CharSequence)lIlIllllIIIIIIl, '/', 0, false, 6, (Object)null) + 1;
            lIlIlllIllIllIl = false;
            if (lIlIllllIIIIIIl == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = lIlIllllIIIIIIl.substring(lIlIlllIllIlllI);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            lIlIllllIIIlIlI = var10000;
            lIlIlllIllIllIl = StringsKt.lastIndexOf$default((CharSequence)lIlIllllIIIIIlI, '/', 0, false, 6, (Object)null) + 1;
            lIlIlllIllIlIll = false;
            if (lIlIllllIIIIIlI == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = lIlIllllIIIIIlI.substring(lIlIlllIllIllIl);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            lIlIlllIllIllll = var10000;
            int lIlIlllIllIlllI = (Map)fields;
            lIlIlllIllIllIl = false;
            lIlIlllIllIlIlI = false;
            if (!lIlIlllIllIlllI.containsKey(lIlIllllIIIlIIl)) {
               lIlIlllIllIlIII = (Map)fields;
               int lIlIlllIllIlllI = false;
               lIlIlllIllIIlll = new HashMap();
               lIlIlllIllIlIII.put(lIlIllllIIIlIIl, lIlIlllIllIIlll);
               var10001 = false;
            }

            var29 = fields.get(lIlIllllIIIlIIl);
            if (var29 == null) {
               Intrinsics.throwNpe();
            }

            Intrinsics.checkExpressionValueIsNotNull(var29, "fields[className]!!");
            ((Map)var29).put(lIlIlllIllIllll, lIlIllllIIIlIlI);
            var10001 = false;
         } else if (StringsKt.startsWith$default(lIlIlllIlllIlIl, "MD:", false, 2, (Object)null)) {
            lIlIllllIIIIIIl = (String)lIlIlllIlllIIll.get(1);
            lIlIllllIIIIIlI = (String)lIlIlllIlllIIll.get(2);
            lIlIllllIIIlIIl = (String)lIlIlllIlllIIll.get(3);
            int lIlIlllIllIlllI = 0;
            lIlIlllIllIllIl = StringsKt.lastIndexOf$default((CharSequence)lIlIllllIIIIIIl, '/', 0, false, 6, (Object)null);
            lIlIlllIllIlIll = false;
            if (lIlIllllIIIIIIl == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = lIlIllllIIIIIIl.substring(lIlIlllIllIlllI, lIlIlllIllIllIl);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
            lIlIllllIIIlIlI = StringsKt.replace$default(var10000, '/', '.', false, 4, (Object)null);
            lIlIlllIllIllIl = StringsKt.lastIndexOf$default((CharSequence)lIlIllllIIIIIIl, '/', 0, false, 6, (Object)null) + 1;
            lIlIlllIllIlIll = false;
            if (lIlIllllIIIIIIl == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = lIlIllllIIIIIIl.substring(lIlIlllIllIllIl);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            lIlIlllIllIllll = var10000;
            int lIlIlllIllIlIll = StringsKt.lastIndexOf$default((CharSequence)lIlIllllIIIlIIl, '/', 0, false, 6, (Object)null) + 1;
            lIlIlllIllIlIlI = false;
            if (lIlIllllIIIlIIl == null) {
               throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }

            var10000 = lIlIllllIIIlIIl.substring(lIlIlllIllIlIll);
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
            String lIlIllllIIIIllI = var10000;
            String lIlIlllIllIllIl = (Map)methods;
            lIlIlllIllIlIll = false;
            short lIlIlllIllIIllI = false;
            if (!lIlIlllIllIllIl.containsKey(lIlIllllIIIlIlI)) {
               lIlIlllIllIlIII = (Map)methods;
               lIlIlllIllIllIl = false;
               lIlIlllIllIIlll = new HashMap();
               lIlIlllIllIlIII.put(lIlIllllIIIlIlI, lIlIlllIllIIlll);
               var10001 = false;
            }

            var29 = methods.get(lIlIllllIIIlIlI);
            if (var29 == null) {
               Intrinsics.throwNpe();
            }

            Intrinsics.checkExpressionValueIsNotNull(var29, "methods[className]!!");
            ((Map)var29).put(String.valueOf((new StringBuilder()).append(lIlIllllIIIIllI).append(lIlIllllIIIIIlI)), lIlIlllIllIllll);
            var10001 = false;
         }
      }

   }

   private Remapper() {
   }

   @NotNull
   public final String remapField(@NotNull Class<?> lIlIlllIllIIIII, @NotNull String lIlIlllIllIIIIl) {
      Intrinsics.checkParameterIsNotNull(lIlIlllIllIIIII, "clazz");
      Intrinsics.checkParameterIsNotNull(lIlIlllIllIIIIl, "name");
      if (!fields.containsKey(lIlIlllIllIIIII.getName())) {
         return lIlIlllIllIIIIl;
      } else {
         Object var10000 = fields.get(lIlIlllIllIIIII.getName());
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         var10000 = ((HashMap)var10000).getOrDefault(lIlIlllIllIIIIl, lIlIlllIllIIIIl);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "fields[clazz.name]!!.getOrDefault(name, name)");
         return (String)var10000;
      }
   }

   static {
      long lIlIlllIlIIllll = new Remapper();
      INSTANCE = lIlIlllIlIIllll;
      srgFile = new File(LiquidBounce.INSTANCE.getFileManager().dir, "mcp-stable_22.srg");
      byte lIlIlllIlIIlllI = false;
      fields = new HashMap();
      lIlIlllIlIIlllI = false;
      methods = new HashMap();
   }

   public final void loadSrg() {
      if (!srgFile.exists()) {
         srgFile.createNewFile();
         boolean var10001 = false;
         ClientUtils.getLogger().info("[Remapper] Downloading stable_22 srg...");
         HttpUtils.download("https://cloud.liquidbounce.net/LiquidBounce/srgs/mcp-stable_22.srg", srgFile);
         ClientUtils.getLogger().info("[Remapper] Downloaded stable_22.");
      }

      ClientUtils.getLogger().info("[Remapper] Loading srg...");
      lIlIllllIlIIIII.parseSrg();
      ClientUtils.getLogger().info("[Remapper] Loaded srg.");
   }
}
