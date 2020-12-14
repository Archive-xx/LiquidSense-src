//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.ui.client.hud.element.elements;

import java.util.Comparator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.minecraft.client.gui.FontRenderer;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 3,
   d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0006\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u000e\u0010\u0003\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u00022\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u0001H\u0002H\u0002H\n¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"},
   d2 = {"<anonymous>", "", "T", "a", "kotlin.jvm.PlatformType", "b", "compare", "(Ljava/lang/Object;Ljava/lang/Object;)I", "kotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2"}
)
public final class Arraylist$updateElement$$inlined$sortedBy$1<T> implements Comparator<T> {
   // $FF: synthetic field
   final Arraylist this$0;

   public final int compare(T lIllIIlIlIIlI, T lIllIIlIlIIIl) {
      long lIllIIlIIllIl = false;
      Module lIllIIlIlIlIl = (Module)lIllIIlIlIIlI;
      int lIllIIlIIlIll = false;
      FontRenderer var10000 = (FontRenderer)Arraylist.access$getFontValue$p(lIllIIlIlIIII.this$0).get();
      String lIllIIlIIlIlI;
      FontRenderer lIllIIlIIlIIl;
      boolean lIllIIlIIlIII;
      String lIllIIlIIIlll;
      String var12;
      String var10001;
      if ((Boolean)Arraylist.access$getUpperCaseValue$p(lIllIIlIlIIII.this$0).get()) {
         lIllIIlIIlIlI = !(Boolean)Arraylist.access$getTags$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getName() : ((Boolean)Arraylist.access$getTagsArrayColor$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getColorlessTagName() : lIllIIlIlIlIl.getTagName());
         lIllIIlIIlIIl = var10000;
         lIllIIlIIlIII = false;
         if (lIllIIlIIlIlI == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var12 = lIllIIlIIlIlI.toUpperCase();
         Intrinsics.checkExpressionValueIsNotNull(var12, "(this as java.lang.String).toUpperCase()");
         lIllIIlIIIlll = var12;
         var10000 = lIllIIlIIlIIl;
         var10001 = lIllIIlIIIlll;
      } else {
         var10001 = !(Boolean)Arraylist.access$getTags$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getName() : ((Boolean)Arraylist.access$getTagsArrayColor$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getColorlessTagName() : lIllIIlIlIlIl.getTagName());
      }

      Comparable var13 = (Comparable)-var10000.getStringWidth(var10001);
      lIllIIlIlIlIl = (Module)lIllIIlIlIIIl;
      boolean lIllIIlIIIllI = var13;
      lIllIIlIIlIll = false;
      var10000 = (FontRenderer)Arraylist.access$getFontValue$p(lIllIIlIlIIII.this$0).get();
      if ((Boolean)Arraylist.access$getUpperCaseValue$p(lIllIIlIlIIII.this$0).get()) {
         lIllIIlIIlIlI = !(Boolean)Arraylist.access$getTags$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getName() : ((Boolean)Arraylist.access$getTagsArrayColor$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getColorlessTagName() : lIllIIlIlIlIl.getTagName());
         lIllIIlIIlIIl = var10000;
         lIllIIlIIlIII = false;
         if (lIllIIlIIlIlI == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var12 = lIllIIlIIlIlI.toUpperCase();
         Intrinsics.checkExpressionValueIsNotNull(var12, "(this as java.lang.String).toUpperCase()");
         lIllIIlIIIlll = var12;
         var10000 = lIllIIlIIlIIl;
         var10001 = lIllIIlIIIlll;
      } else {
         var10001 = !(Boolean)Arraylist.access$getTags$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getName() : ((Boolean)Arraylist.access$getTagsArrayColor$p(lIllIIlIlIIII.this$0).get() ? lIllIIlIlIlIl.getColorlessTagName() : lIllIIlIlIlIl.getTagName());
      }

      boolean lIllIIlIIIlIl = -var10000.getStringWidth(var10001);
      return ComparisonsKt.compareValues(lIllIIlIIIllI, (Comparable)lIllIIlIIIlIl);
   }

   public Arraylist$updateElement$$inlined$sortedBy$1(Arraylist var1) {
      this.this$0 = var1;
   }
}
