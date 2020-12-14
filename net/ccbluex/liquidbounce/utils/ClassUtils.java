package net.ccbluex.liquidbounce.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0005H\u0007J\u0006\u0010\t\u001a\u00020\u0006R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/ClassUtils;", "", "()V", "cachedClasses", "", "", "", "hasClass", "className", "hasForge", "LiquidSense"}
)
public final class ClassUtils {
   // $FF: synthetic field
   private static final Map<String, Boolean> cachedClasses;
   // $FF: synthetic field
   public static final ClassUtils INSTANCE;

   public final boolean hasForge() {
      return hasClass("net.minecraftforge.common.MinecraftForge");
   }

   private ClassUtils() {
   }

   @JvmStatic
   public static final boolean hasClass(@NotNull String lllllllllllllllllIllIIllIIIIllII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIIllIIIIllII, "className");
      boolean var4;
      if (cachedClasses.containsKey(lllllllllllllllllIllIIllIIIIllII)) {
         Object var10000 = cachedClasses.get(lllllllllllllllllIllIIllIIIIllII);
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         var4 = (Boolean)var10000;
      } else {
         boolean lllllllllllllllllIllIIllIIIIlIll;
         try {
            Class.forName(lllllllllllllllllIllIIllIIIIllII);
            boolean var10001 = false;
            cachedClasses.put(lllllllllllllllllIllIIllIIIIllII, true);
            var10001 = false;
            lllllllllllllllllIllIIllIIIIlIll = true;
         } catch (ClassNotFoundException var3) {
            cachedClasses.put(lllllllllllllllllIllIIllIIIIllII, false);
            lllllllllllllllllIllIIllIIIIlIll = false;
         }

         var4 = lllllllllllllllllIllIIllIIIIlIll;
      }

      return var4;
   }

   static {
      long lllllllllllllllllIllIIllIIIIIIll = new ClassUtils();
      INSTANCE = lllllllllllllllllIllIIllIIIIIIll;
      boolean lllllllllllllllllIllIIllIIIIIIlI = false;
      cachedClasses = (Map)(new LinkedHashMap());
   }
}
