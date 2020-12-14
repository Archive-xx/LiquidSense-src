package net.ccbluex.liquidbounce.utils.render;

import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"},
   d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture;", "it", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation;", "apply"}
)
final class MiniMapRegister$updateChunks$1$4$1<T, R> implements Function<MiniMapRegister.ChunkLocation, MiniMapRegister.MiniMapTexture> {
   // $FF: synthetic field
   public static final MiniMapRegister$updateChunks$1$4$1 INSTANCE = new MiniMapRegister$updateChunks$1$4$1();

   @NotNull
   public final MiniMapRegister.MiniMapTexture apply(@NotNull MiniMapRegister.ChunkLocation llllIllIlIlIlll) {
      Intrinsics.checkParameterIsNotNull(llllIllIlIlIlll, "it");
      return new MiniMapRegister.MiniMapTexture();
   }
}
