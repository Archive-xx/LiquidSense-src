package net.ccbluex.liquidbounce.cape;

import java.util.Arrays;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"},
   d2 = {"Lnet/ccbluex/liquidbounce/cape/ServiceAPI;", "Lnet/ccbluex/liquidbounce/cape/CapeService;", "baseURL", "", "(Ljava/lang/String;)V", "getCape", "uuid", "Ljava/util/UUID;", "LiquidSense"}
)
public final class ServiceAPI implements CapeService {
   // $FF: synthetic field
   private final String baseURL;

   @NotNull
   public String getCape(@NotNull UUID llllllIIllllI) {
      Intrinsics.checkParameterIsNotNull(llllllIIllllI, "uuid");
      boolean llllllIIlllIl = StringCompanionObject.INSTANCE;
      char llllllIIlllII = llllllIIlllll.baseURL;
      short llllllIIllIll = new Object[]{llllllIIllllI};
      int llllllIIllIlI = false;
      String var10000 = String.format(llllllIIlllII, Arrays.copyOf(llllllIIllIll, llllllIIllIll.length));
      Intrinsics.checkExpressionValueIsNotNull(var10000, "java.lang.String.format(format, *args)");
      return var10000;
   }

   public ServiceAPI(@NotNull String llllllIIlIllI) {
      Intrinsics.checkParameterIsNotNull(llllllIIlIllI, "baseURL");
      super();
      llllllIIlIlIl.baseURL = llllllIIlIllI;
   }
}
