//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.utils.render;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u001a\u001bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011J\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000eJ\u0006\u0010\u0019\u001a\u00020\u0015R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u000bj\b\u0012\u0004\u0012\u00020\u0005`\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\u000bj\b\u0012\u0004\u0012\u00020\u000e`\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"},
   d2 = {"Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister;", "", "()V", "chunkTextureMap", "Ljava/util/HashMap;", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation;", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture;", "Lkotlin/collections/HashMap;", "deleteAllChunks", "Ljava/util/concurrent/atomic/AtomicBoolean;", "queuedChunkDeletions", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "queuedChunkUpdates", "Lnet/minecraft/world/chunk/Chunk;", "getChunkTextureAt", "x", "", "z", "getLoadedChunkCount", "unloadAllChunks", "", "unloadChunk", "updateChunk", "chunk", "updateChunks", "ChunkLocation", "MiniMapTexture", "LiquidSense"}
)
public final class MiniMapRegister {
   // $FF: synthetic field
   private static final HashMap<MiniMapRegister.ChunkLocation, MiniMapRegister.MiniMapTexture> chunkTextureMap;
   // $FF: synthetic field
   private static final HashSet<MiniMapRegister.ChunkLocation> queuedChunkDeletions;
   // $FF: synthetic field
   private static final HashSet<Chunk> queuedChunkUpdates;
   // $FF: synthetic field
   public static final MiniMapRegister INSTANCE;
   // $FF: synthetic field
   private static final AtomicBoolean deleteAllChunks;

   public final int getLoadedChunkCount() {
      return chunkTextureMap.size();
   }

   private MiniMapRegister() {
   }

   static {
      long llllllllllllllllllIlIlIIlIIlIIIl = new MiniMapRegister();
      INSTANCE = llllllllllllllllllIlIlIIlIIlIIIl;
      chunkTextureMap = new HashMap();
      queuedChunkUpdates = new HashSet(256);
      queuedChunkDeletions = new HashSet(256);
      deleteAllChunks = new AtomicBoolean(false);
   }

   @Nullable
   public final MiniMapRegister.MiniMapTexture getChunkTextureAt(int llllllllllllllllllIlIlIlIllIIlIl, int llllllllllllllllllIlIlIlIllIIlII) {
      return (MiniMapRegister.MiniMapTexture)chunkTextureMap.get(new MiniMapRegister.ChunkLocation(llllllllllllllllllIlIlIlIllIIlIl, llllllllllllllllllIlIlIlIllIIlII));
   }

   public final void updateChunks() {
      short llllllllllllllllllIlIlIIlllIIllI = queuedChunkUpdates;
      long llllllllllllllllllIlIlIIlllIIlIl = false;
      int llllllllllllllllllIlIlIIlllIIlII = false;
      synchronized(llllllllllllllllllIlIlIIlllIIllI) {
         int llllllllllllllllllIlIlIIlllIlIII = false;
         HashSet llllllllllllllllllIlIlIIlllIIIlI;
         boolean llllllllllllllllllIlIlIIlllIIIIl;
         boolean llllllllllllllllllIlIlIIlllIIIII;
         boolean llllllllllllllllllIlIlIIllIlllll;
         boolean llllllllllllllllllIlIlIIllIlllIl;
         Unit llllllllllllllllllIlIlIIlllIIIII;
         if (deleteAllChunks.get()) {
            llllllllllllllllllIlIlIIlllIIIlI = queuedChunkDeletions;
            llllllllllllllllllIlIlIIlllIIIIl = false;
            llllllllllllllllllIlIlIIlllIIIII = false;
            synchronized(llllllllllllllllllIlIlIIlllIIIlI) {
               llllllllllllllllllIlIlIIllIlllll = false;
               queuedChunkDeletions.clear();
               llllllllllllllllllIlIlIIlllIIIII = Unit.INSTANCE;
            }

            queuedChunkUpdates.clear();
            Map llllllllllllllllllIlIlIIllllllII = (Map)chunkTextureMap;
            llllllllllllllllllIlIlIIlllIIIIl = false;
            llllllllllllllllllIlIlIIllIlllll = false;
            Iterator llllllllllllllllllIlIlIIllIllllI = llllllllllllllllllIlIlIIllllllII.entrySet().iterator();

            while(llllllllllllllllllIlIlIIllIllllI.hasNext()) {
               char llllllllllllllllllIlIlIIllIlllIl = (Entry)llllllllllllllllllIlIlIIllIllllI.next();
               int llllllllllllllllllIlIlIlIIIIIIII = false;
               ((MiniMapRegister.MiniMapTexture)llllllllllllllllllIlIlIIllIlllIl.getValue()).delete$LiquidSense();
            }

            chunkTextureMap.clear();
            deleteAllChunks.set(false);
         } else {
            llllllllllllllllllIlIlIIlllIIIlI = queuedChunkDeletions;
            llllllllllllllllllIlIlIIlllIIIIl = false;
            llllllllllllllllllIlIlIIlllIIIII = false;
            synchronized(llllllllllllllllllIlIlIIlllIIIlI) {
               llllllllllllllllllIlIlIIllIlllll = false;
               Iterable llllllllllllllllllIlIlIIllllIIlI = (Iterable)queuedChunkDeletions;
               llllllllllllllllllIlIlIIllIlllIl = false;
               Iterator llllllllllllllllllIlIlIIllIlllII = llllllllllllllllllIlIlIIllllIIlI.iterator();

               while(llllllllllllllllllIlIlIIllIlllII.hasNext()) {
                  Object llllllllllllllllllIlIlIIllllIlII = llllllllllllllllllIlIlIIllIlllII.next();
                  MiniMapRegister.ChunkLocation llllllllllllllllllIlIlIIlllllIII = (MiniMapRegister.ChunkLocation)llllllllllllllllllIlIlIIllllIlII;
                  int llllllllllllllllllIlIlIIllllIllI = false;
                  MiniMapRegister.MiniMapTexture var10000 = (MiniMapRegister.MiniMapTexture)chunkTextureMap.remove(llllllllllllllllllIlIlIIlllllIII);
                  if (var10000 != null) {
                     var10000.delete$LiquidSense();
                  } else {
                     boolean var10001 = false;
                  }
               }

               queuedChunkDeletions.clear();
               llllllllllllllllllIlIlIIlllIIIII = Unit.INSTANCE;
            }
         }

         short llllllllllllllllllIlIlIIlllIIIlI = (Iterable)queuedChunkUpdates;
         llllllllllllllllllIlIlIIlllIIIIl = false;
         Iterator llllllllllllllllllIlIlIIlllIIIII = llllllllllllllllllIlIlIIlllIIIlI.iterator();

         while(llllllllllllllllllIlIlIIlllIIIII.hasNext()) {
            Object llllllllllllllllllIlIlIIlllIlIll = llllllllllllllllllIlIlIIlllIIIII.next();
            Chunk llllllllllllllllllIlIlIIlllIllIl = (Chunk)llllllllllllllllllIlIlIIlllIlIll;
            llllllllllllllllllIlIlIIllIlllIl = false;
            ((MiniMapRegister.MiniMapTexture)chunkTextureMap.computeIfAbsent(new MiniMapRegister.ChunkLocation(llllllllllllllllllIlIlIIlllIllIl.xPosition, llllllllllllllllllIlIlIIlllIllIl.zPosition), (Function)MiniMapRegister$updateChunks$1$4$1.INSTANCE)).updateChunkData(llllllllllllllllllIlIlIIlllIllIl);
         }

         queuedChunkUpdates.clear();
         Unit llllllllllllllllllIlIlIIlllIIlII1 = Unit.INSTANCE;
      }
   }

   public final void updateChunk(@NotNull Chunk llllllllllllllllllIlIlIlIlllllII) {
      Intrinsics.checkParameterIsNotNull(llllllllllllllllllIlIlIlIlllllII, "chunk");
      Exception llllllllllllllllllIlIlIlIllllIll = queuedChunkUpdates;
      double llllllllllllllllllIlIlIlIllllIIl = false;
      boolean llllllllllllllllllIlIlIlIlllIllI = false;
      synchronized(llllllllllllllllllIlIlIlIllllIll) {
         byte llllllllllllllllllIlIlIlIlllIIll = false;
         llllllllllllllllllIlIlIlIlllIllI = queuedChunkUpdates.add(llllllllllllllllllIlIlIlIlllllII);
      }
   }

   public final void unloadChunk(int llllllllllllllllllIlIlIIlIlllIlI, int llllllllllllllllllIlIlIIlIlllIll) {
      Exception llllllllllllllllllIlIlIIlIllIllI = queuedChunkDeletions;
      short llllllllllllllllllIlIlIIlIllIlIl = false;
      int llllllllllllllllllIlIlIIlIllIlII = false;
      synchronized(llllllllllllllllllIlIlIIlIllIllI) {
         byte llllllllllllllllllIlIlIIlIllIIll = false;
         llllllllllllllllllIlIlIIlIllIlII = queuedChunkDeletions.add(new MiniMapRegister.ChunkLocation(llllllllllllllllllIlIlIIlIlllIlI, llllllllllllllllllIlIlIIlIlllIll));
      }
   }

   public final void unloadAllChunks() {
      deleteAllChunks.set(true);
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation;", "", "x", "", "z", "(II)V", "getX", "()I", "getZ", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", "LiquidSense"}
   )
   public static final class ChunkLocation {
      // $FF: synthetic field
      private final int z;
      // $FF: synthetic field
      private final int x;

      public int hashCode() {
         return Integer.hashCode(this.x) * 31 + Integer.hashCode(this.z);
      }

      public final int component1() {
         return lIIllIlIllIIlI.x;
      }

      public final int getZ() {
         return lIIllIlIllllll.z;
      }

      @NotNull
      public String toString() {
         return String.valueOf((new StringBuilder()).append("ChunkLocation(x=").append(this.x).append(", z=").append(this.z).append(")"));
      }

      public final int component2() {
         return lIIllIlIllIIII.z;
      }

      @NotNull
      public final MiniMapRegister.ChunkLocation copy(int lIIllIlIlIlIIl, int lIIllIlIlIlIlI) {
         return new MiniMapRegister.ChunkLocation(lIIllIlIlIlIIl, lIIllIlIlIlIlI);
      }

      public ChunkLocation(int lIIllIlIllIllI, int lIIllIlIlllIII) {
         lIIllIlIlllIlI.x = lIIllIlIllIllI;
         lIIllIlIlllIlI.z = lIIllIlIlllIII;
      }

      public boolean equals(@Nullable Object var1) {
         if (this != var1) {
            if (var1 instanceof MiniMapRegister.ChunkLocation) {
               MiniMapRegister.ChunkLocation var2 = (MiniMapRegister.ChunkLocation)var1;
               if (this.x == var2.x && this.z == var2.z) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }

      public final int getX() {
         return lIIllIllIIIIlI.x;
      }

      // $FF: synthetic method
      public static MiniMapRegister.ChunkLocation copy$default(MiniMapRegister.ChunkLocation lIIllIlIlIIIll, int var1, int var2, int lIIllIlIlIIIII, Object var4) {
         if ((lIIllIlIlIIIII & 1) != 0) {
            var1 = lIIllIlIlIIIll.x;
         }

         if ((lIIllIlIlIIIII & 2) != 0) {
            var2 = lIIllIlIlIIIll.z;
         }

         return lIIllIlIlIIIll.copy(var1, var2);
      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\r\u0010\r\u001a\u00020\u000eH\u0000¢\u0006\u0002\b\u000fJ\b\u0010\u0010\u001a\u00020\u000eH\u0004J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"},
      d2 = {"Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture;", "", "()V", "deleted", "", "getDeleted", "()Z", "setDeleted", "(Z)V", "texture", "Lnet/minecraft/client/renderer/texture/DynamicTexture;", "getTexture", "()Lnet/minecraft/client/renderer/texture/DynamicTexture;", "delete", "", "delete$LiquidSense", "finalize", "updateChunkData", "chunk", "Lnet/minecraft/world/chunk/Chunk;", "LiquidSense"}
   )
   public static final class MiniMapTexture {
      // $FF: synthetic field
      @NotNull
      private final DynamicTexture texture = new DynamicTexture(16, 16);
      // $FF: synthetic field
      private boolean deleted;

      public final void delete$LiquidSense() {
         if (!lllllllllllllllllllIlIllIIllIlll.deleted) {
            lllllllllllllllllllIlIllIIllIlll.texture.deleteGlTexture();
            lllllllllllllllllllIlIllIIllIlll.deleted = true;
         }

      }

      public final boolean getDeleted() {
         return lllllllllllllllllllIlIllIlIlIllI.deleted;
      }

      public final void setDeleted(boolean lllllllllllllllllllIlIllIlIlIIII) {
         lllllllllllllllllllIlIllIlIlIIIl.deleted = lllllllllllllllllllIlIllIlIlIIII;
      }

      protected final void finalize() {
         if (!lllllllllllllllllllIlIllIIllIlIl.deleted) {
            lllllllllllllllllllIlIllIIllIlIl.texture.deleteGlTexture();
         }

      }

      public final void updateChunkData(@NotNull Chunk lllllllllllllllllllIlIllIlIIIIlI) {
         Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlIllIlIIIIlI, "chunk");
         int[] lllllllllllllllllllIlIllIlIIIlII = lllllllllllllllllllIlIllIlIIIIIl.texture.getTextureData();
         boolean lllllllllllllllllllIlIllIIlllllI = 0;

         for(byte lllllllllllllllllllIlIllIIllllIl = 15; lllllllllllllllllllIlIllIIlllllI <= lllllllllllllllllllIlIllIIllllIl; ++lllllllllllllllllllIlIllIIlllllI) {
            String lllllllllllllllllllIlIllIIllllII = 0;

            for(byte lllllllllllllllllllIlIllIIlllIll = 15; lllllllllllllllllllIlIllIIllllII <= lllllllllllllllllllIlIllIIlllIll; ++lllllllllllllllllllIlIllIIllllII) {
               IBlockState lllllllllllllllllllIlIllIlIIIlll = lllllllllllllllllllIlIllIlIIIIlI.getBlockState(new BlockPos(lllllllllllllllllllIlIllIIlllllI, lllllllllllllllllllIlIllIlIIIIlI.getHeightValue(lllllllllllllllllllIlIllIIlllllI, lllllllllllllllllllIlIllIIllllII) - 1, lllllllllllllllllllIlIllIIllllII));
               int var10001 = lllllllllllllllllllIlIllIlIIIlII.length - (lllllllllllllllllllIlIllIIllllII * 16 + lllllllllllllllllllIlIllIIlllllI + 1);
               Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIlIllIlIIIlll, "blockState");
               lllllllllllllllllllIlIllIlIIIlII[var10001] = lllllllllllllllllllIlIllIlIIIlll.getBlock().getMapColor(lllllllllllllllllllIlIllIlIIIlll).colorValue | -16777216;
            }
         }

         lllllllllllllllllllIlIllIlIIIIIl.texture.updateDynamicTexture();
      }

      @NotNull
      public final DynamicTexture getTexture() {
         return lllllllllllllllllllIlIllIlIllIIl.texture;
      }
   }
}
