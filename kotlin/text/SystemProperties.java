package kotlin.text;

import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"},
   d2 = {"Lkotlin/text/SystemProperties;", "", "()V", "LINE_SEPARATOR", "", "kotlin-stdlib"}
)
final class SystemProperties {
   @JvmField
   @NotNull
   public static final String LINE_SEPARATOR;
   public static final SystemProperties INSTANCE;

   private SystemProperties() {
   }

   static {
      SystemProperties var0 = new SystemProperties();
      INSTANCE = var0;
      String var10000 = System.getProperty("line.separator");
      if (var10000 == null) {
         Intrinsics.throwNpe();
      }

      LINE_SEPARATOR = var10000;
   }
}
