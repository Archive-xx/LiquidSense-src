package net.ccbluex.liquidbounce.cape;

import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/cape/ServiceList;", "Lnet/ccbluex/liquidbounce/cape/CapeService;", "users", "", "", "(Ljava/util/Map;)V", "getCape", "uuid", "Ljava/util/UUID;", "LiquidSense"}
)
public final class ServiceList implements CapeService {
   // $FF: synthetic field
   private final Map<String, String> users;

   public ServiceList(@NotNull Map<String, String> lllIlIlIlIlIlIl) {
      Intrinsics.checkParameterIsNotNull(lllIlIlIlIlIlIl, "users");
      super();
      lllIlIlIlIllIII.users = lllIlIlIlIlIlIl;
   }

   @Nullable
   public String getCape(@NotNull UUID lllIlIlIlIllIll) {
      Intrinsics.checkParameterIsNotNull(lllIlIlIlIllIll, "uuid");
      Map var10000 = lllIlIlIlIllllI.users;
      String var10001 = lllIlIlIlIllIll.toString();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "uuid.toString()");
      return (String)var10000.get(StringsKt.replace$default(var10001, "-", "", false, 4, (Object)null));
   }
}
