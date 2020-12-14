//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.script.api;

import java.util.List;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.runtime.JSType;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"},
   d2 = {"Lnet/ccbluex/liquidbounce/script/api/CreativeTab;", "", "()V", "registerTab", "", "scriptObjectMirror", "Ljdk/nashorn/api/scripting/ScriptObjectMirror;", "LiquidSense"}
)
public final class CreativeTab {
   // $FF: synthetic field
   public static final CreativeTab INSTANCE;

   static {
      CreativeTab lllllllllllllllllllIIlllIlIIIIlI = new CreativeTab();
      INSTANCE = lllllllllllllllllllIIlllIlIIIIlI;
   }

   @JvmStatic
   public static final void registerTab(@NotNull final ScriptObjectMirror lllllllllllllllllllIIlllIlIIlIII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIIlllIlIIlIII, "scriptObjectMirror");
      CreativeTabs var10001 = new CreativeTabs(JSType.toString(lllllllllllllllllllIIlllIlIIlIII.callMember("getLabel"))) {
         @NotNull
         public net.minecraft.item.Item getTabIconItem() {
            Object var10000 = JSType.toObject(lllllllllllllllllllIIlllIlIIlIII.callMember("getTabIconItem"));
            if (var10000 == null) {
               throw new TypeCastException("null cannot be cast to non-null type net.minecraft.item.Item");
            } else {
               return (net.minecraft.item.Item)var10000;
            }
         }

         @NotNull
         public String getTranslatedTabLabel() {
            String var10000 = JSType.toString(lllllllllllllllllllIIlllIlIIlIII.callMember("getLabel"));
            Intrinsics.checkExpressionValueIsNotNull(var10000, "JSType.toString(scriptOb…r.callMember(\"getLabel\"))");
            return var10000;
         }

         public void displayAllReleventItems(@Nullable List<ItemStack> llIIllllIlIIII) {
            lllllllllllllllllllIIlllIlIIlIII.callMember("displayAllReleventItems", llIIllllIlIIII);
            boolean var10001 = false;
         }
      };
      boolean var1 = false;
   }

   private CreativeTab() {
   }
}
