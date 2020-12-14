//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.tabs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.item.ItemUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"},
   d2 = {"Lnet/ccbluex/liquidbounce/tabs/HeadsTab;", "Lnet/minecraft/creativetab/CreativeTabs;", "()V", "heads", "Ljava/util/ArrayList;", "Lnet/minecraft/item/ItemStack;", "displayAllReleventItems", "", "itemList", "", "getTabIconItem", "Lnet/minecraft/item/Item;", "getTranslatedTabLabel", "", "hasSearchBar", "", "loadHeads", "LiquidSense"}
)
@SideOnly(Side.CLIENT)
public final class HeadsTab extends CreativeTabs {
   // $FF: synthetic field
   private final ArrayList<ItemStack> heads = new ArrayList();

   public HeadsTab() {
      super("Heads");
      lllllllllllllllllIllIlIllIIlIIIl.setBackgroundImageName("item_search.png");
      boolean var10001 = false;
      lllllllllllllllllIllIlIllIIlIIIl.loadHeads();
   }

   public void displayAllReleventItems(@NotNull List<ItemStack> lllllllllllllllllIllIlIllIlIIIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIlIllIlIIIlI, "itemList");
      lllllllllllllllllIllIlIllIlIIIlI.addAll((Collection)lllllllllllllllllIllIlIllIlIIIll.heads);
      boolean var10001 = false;
   }

   @NotNull
   public String getTranslatedTabLabel() {
      return "Heads";
   }

   private final void loadHeads() {
      try {
         ClientUtils.getLogger().info("Loading heads...");
         JsonElement lllllllllllllllllIllIlIllIllIlIl = (new JsonParser()).parse(HttpUtils.get("https://cloud.liquidbounce.net/LiquidBounce/heads.json"));
         Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIllIllIlIl, "headsConfiguration");
         if (!lllllllllllllllllIllIlIllIllIlIl.isJsonObject()) {
            return;
         }

         byte lllllllllllllllllIllIlIllIllIIII = lllllllllllllllllIllIlIllIllIlIl.getAsJsonObject();
         JsonElement var10000 = lllllllllllllllllIllIlIllIllIIII.get("enabled");
         Intrinsics.checkExpressionValueIsNotNull(var10000, "headsConf.get(\"enabled\")");
         if (var10000.getAsBoolean()) {
            var10000 = lllllllllllllllllIllIlIllIllIIII.get("url");
            Intrinsics.checkExpressionValueIsNotNull(var10000, "headsConf.get(\"url\")");
            String lllllllllllllllllIllIlIllIllIlll = var10000.getAsString();
            ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Loading heads from ").append(lllllllllllllllllIllIlIllIllIlll).append("...")));
            JsonParser var12 = new JsonParser();
            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIllIllIlll, "url");
            short lllllllllllllllllIllIlIllIlIlllI = var12.parse(HttpUtils.get(lllllllllllllllllIllIlIllIllIlll));
            Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIllIlIlllI, "headsElement");
            if (!lllllllllllllllllIllIlIllIlIlllI.isJsonObject()) {
               ClientUtils.getLogger().error("Something is wrong, the heads json is not a JsonObject!");
               return;
            }

            JsonObject lllllllllllllllllIllIlIllIlllIIl = lllllllllllllllllIllIlIllIlIlllI.getAsJsonObject();

            boolean var14;
            for(Iterator lllllllllllllllllIllIlIllIlIlIll = lllllllllllllllllIllIlIllIlllIIl.entrySet().iterator(); lllllllllllllllllIllIlIllIlIlIll.hasNext(); var14 = false) {
               short lllllllllllllllllIllIlIllIlIllII = (Entry)lllllllllllllllllIllIlIllIlIlIll.next();
               byte lllllllllllllllllIllIlIllIlIlIII = false;
               float lllllllllllllllllIllIlIllIlIlIlI = (JsonElement)lllllllllllllllllIllIlIllIlIllII.getValue();
               Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllIllIlIllIlIlIlI, "value");
               int lllllllllllllllllIllIlIllIlIlIIl = lllllllllllllllllIllIlIllIlIlIlI.getAsJsonObject();
               ArrayList var13 = lllllllllllllllllIllIlIllIllIIll.heads;
               StringBuilder var10001 = (new StringBuilder()).append("skull 1 3 {display:{Name:\"");
               JsonElement var10002 = lllllllllllllllllIllIlIllIlIlIIl.get("name");
               Intrinsics.checkExpressionValueIsNotNull(var10002, "headElement.get(\"name\")");
               var10001 = var10001.append(var10002.getAsString()).append("\"},SkullOwner:{Id:\"");
               var10002 = lllllllllllllllllIllIlIllIlIlIIl.get("uuid");
               Intrinsics.checkExpressionValueIsNotNull(var10002, "headElement.get(\"uuid\")");
               var10001 = var10001.append(var10002.getAsString()).append("\",Properties:{textures:[{Value:\"");
               var10002 = lllllllllllllllllIllIlIllIlIlIIl.get("value");
               Intrinsics.checkExpressionValueIsNotNull(var10002, "headElement.get(\"value\")");
               var13.add(ItemUtils.createItem(String.valueOf(var10001.append(var10002.getAsString()).append("\"}]}}}"))));
            }

            ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("Loaded ").append(lllllllllllllllllIllIlIllIllIIll.heads.size()).append(" heads from HeadDB.")));
         } else {
            ClientUtils.getLogger().info("Heads are disabled.");
         }
      } catch (Exception var11) {
         ClientUtils.getLogger().error("Error while reading heads.", (Throwable)var11);
      }

   }

   public boolean hasSearchBar() {
      return true;
   }

   @NotNull
   public Item getTabIconItem() {
      Item var10000 = Items.skull;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "Items.skull");
      return var10000;
   }
}
