package net.ccbluex.liquidbounce.ui.font;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J'\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0004J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001e"},
   d2 = {"Lnet/ccbluex/liquidbounce/ui/font/CachedFont;", "", "displayList", "", "lastUsage", "", "deleted", "", "(IJZ)V", "getDeleted", "()Z", "setDeleted", "(Z)V", "getDisplayList", "()I", "getLastUsage", "()J", "setLastUsage", "(J)V", "component1", "component2", "component3", "copy", "equals", "other", "finalize", "", "hashCode", "toString", "", "LiquidSense"}
)
public final class CachedFont {
   // $FF: synthetic field
   private boolean deleted;
   // $FF: synthetic field
   private final int displayList;
   // $FF: synthetic field
   private long lastUsage;

   public final void setLastUsage(long lllllllllllllllllIlllIlllllIllll) {
      lllllllllllllllllIlllIllllllIIlI.lastUsage = lllllllllllllllllIlllIlllllIllll;
   }

   protected final void finalize() {
      if (!lllllllllllllllllIlllIllllllllII.deleted) {
         GL11.glDeleteLists(lllllllllllllllllIlllIllllllllII.displayList, 1);
      }

   }

   public boolean equals(@Nullable Object var1) {
      if (this != var1) {
         if (var1 instanceof CachedFont) {
            CachedFont var2 = (CachedFont)var1;
            if (this.displayList == var2.displayList && this.lastUsage == var2.lastUsage && this.deleted == var2.deleted) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   public final int getDisplayList() {
      return lllllllllllllllllIlllIlllllllIIl.displayList;
   }

   public final boolean component3() {
      return lllllllllllllllllIlllIllllIIIlll.deleted;
   }

   public final boolean getDeleted() {
      return lllllllllllllllllIlllIlllllIllII.deleted;
   }

   public final long component2() {
      return lllllllllllllllllIlllIllllIIlIlI.lastUsage;
   }

   @NotNull
   public final CachedFont copy(int lllllllllllllllllIlllIllllIIIIlI, long lllllllllllllllllIlllIlllIlllllI, boolean lllllllllllllllllIlllIlllIllllIl) {
      return new CachedFont(lllllllllllllllllIlllIllllIIIIlI, lllllllllllllllllIlllIlllIlllllI, lllllllllllllllllIlllIlllIllllIl);
   }

   public final long getLastUsage() {
      return lllllllllllllllllIlllIllllllIllI.lastUsage;
   }

   public int hashCode() {
      int var10000 = (Integer.hashCode(this.displayList) * 31 + Long.hashCode(this.lastUsage)) * 31;
      byte var10001 = this.deleted;
      if (var10001 != 0) {
         boolean var10002 = false;
         var10001 = 1;
      }

      return var10000 + var10001;
   }

   // $FF: synthetic method
   public static CachedFont copy$default(CachedFont lllllllllllllllllIlllIlllIllIlll, int var1, long var2, boolean var4, int lllllllllllllllllIlllIlllIllIIll, Object var6) {
      if ((lllllllllllllllllIlllIlllIllIIll & 1) != 0) {
         var1 = lllllllllllllllllIlllIlllIllIlll.displayList;
      }

      if ((lllllllllllllllllIlllIlllIllIIll & 2) != 0) {
         var2 = lllllllllllllllllIlllIlllIllIlll.lastUsage;
      }

      if ((lllllllllllllllllIlllIlllIllIIll & 4) != 0) {
         var4 = lllllllllllllllllIlllIlllIllIlll.deleted;
      }

      return lllllllllllllllllIlllIlllIllIlll.copy(var1, var2, var4);
   }

   @NotNull
   public String toString() {
      return String.valueOf((new StringBuilder()).append("CachedFont(displayList=").append(this.displayList).append(", lastUsage=").append(this.lastUsage).append(", deleted=").append(this.deleted).append(")"));
   }

   // $FF: synthetic method
   public CachedFont(int var1, long var2, boolean var4, int var5, DefaultConstructorMarker var6) {
      if ((var5 & 4) != 0) {
         var4 = false;
      }

      this(var1, var2, var4);
   }

   public final int component1() {
      return lllllllllllllllllIlllIllllIIllIl.displayList;
   }

   public CachedFont(int lllllllllllllllllIlllIlllllIIIII, long lllllllllllllllllIlllIllllIllIll, boolean lllllllllllllllllIlllIllllIllIlI) {
      super();
      lllllllllllllllllIlllIlllllIIIIl.displayList = lllllllllllllllllIlllIlllllIIIII;
      lllllllllllllllllIlllIlllllIIIIl.lastUsage = lllllllllllllllllIlllIllllIllIll;
      lllllllllllllllllIlllIlllllIIIIl.deleted = lllllllllllllllllIlllIllllIllIlI;
   }

   public final void setDeleted(boolean lllllllllllllllllIlllIlllllIIllI) {
      lllllllllllllllllIlllIlllllIIlll.deleted = lllllllllllllllllIlllIlllllIIllI;
   }
}
