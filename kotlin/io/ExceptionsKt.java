package kotlin.io;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001H\u0002¨\u0006\u0006"},
   d2 = {"constructMessage", "", "file", "Ljava/io/File;", "other", "reason", "kotlin-stdlib"}
)
public final class ExceptionsKt {
   private static final String constructMessage(File file, File other, String reason) {
      StringBuilder sb = new StringBuilder(file.toString());
      if (other != null) {
         sb.append(" -> " + other);
      }

      if (reason != null) {
         sb.append(": " + reason);
      }

      String var10000 = sb.toString();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "sb.toString()");
      return var10000;
   }

   // $FF: synthetic method
   public static final String access$constructMessage(File file, File other, String reason) {
      return constructMessage(file, other, reason);
   }
}
