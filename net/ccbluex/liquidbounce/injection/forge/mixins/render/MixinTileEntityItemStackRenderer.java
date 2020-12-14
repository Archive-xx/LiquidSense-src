//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@SideOnly(Side.CLIENT)
@Mixin({TileEntityItemStackRenderer.class})
public class MixinTileEntityItemStackRenderer {
   // $FF: synthetic field
   @Shadow
   private TileEntityEnderChest enderChest;
   // $FF: synthetic field
   @Shadow
   private TileEntityChest field_147718_c;
   // $FF: synthetic field
   @Shadow
   private TileEntityChest field_147717_b;
   // $FF: synthetic field
   @Shadow
   private TileEntityBanner banner;

   @Overwrite
   public void renderByItem(ItemStack llIIIlIlIlllIl) {
      if (llIIIlIlIlllIl.getItem() == Items.banner) {
         llIIIlIlIllIll.banner.setItemValues(llIIIlIlIlllIl);
         TileEntityRendererDispatcher.instance.renderTileEntityAt(llIIIlIlIllIll.banner, 0.0D, 0.0D, 0.0D, 0.0F);
      } else if (llIIIlIlIlllIl.getItem() == Items.skull) {
         GameProfile llIIIlIllIIIll = null;
         if (llIIIlIlIlllIl.hasTagCompound()) {
            NBTTagCompound llIIIlIllIIlIl = llIIIlIlIlllIl.getTagCompound();

            try {
               if (llIIIlIllIIlIl.hasKey("SkullOwner", 10)) {
                  llIIIlIllIIIll = NBTUtil.readGameProfileFromNBT(llIIIlIllIIlIl.getCompoundTag("SkullOwner"));
               } else if (llIIIlIllIIlIl.hasKey("SkullOwner", 8) && llIIIlIllIIlIl.getString("SkullOwner").length() > 0) {
                  long llIIIlIlIlIlII = new GameProfile((UUID)null, llIIIlIllIIlIl.getString("SkullOwner"));
                  llIIIlIllIIIll = TileEntitySkull.updateGameprofile(llIIIlIlIlIlII);
                  llIIIlIllIIlIl.removeTag("SkullOwner");
                  llIIIlIllIIlIl.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), llIIIlIllIIIll));
               }
            } catch (Exception var6) {
            }
         }

         if (TileEntitySkullRenderer.instance != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5F, 0.0F, -0.5F);
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.disableCull();
            TileEntitySkullRenderer.instance.renderSkull(0.0F, 0.0F, 0.0F, EnumFacing.UP, 0.0F, llIIIlIlIlllIl.getMetadata(), llIIIlIllIIIll, -1);
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
         }
      } else {
         Block llIIIlIllIIIIl = Block.getBlockFromItem(llIIIlIlIlllIl.getItem());
         if (llIIIlIllIIIIl == Blocks.ender_chest) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(llIIIlIlIllIll.enderChest, 0.0D, 0.0D, 0.0D, 0.0F);
         } else if (llIIIlIllIIIIl == Blocks.trapped_chest) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(llIIIlIlIllIll.field_147718_c, 0.0D, 0.0D, 0.0D, 0.0F);
         } else if (llIIIlIllIIIIl != Blocks.chest) {
            ForgeHooksClient.renderTileItem(llIIIlIlIlllIl.getItem(), llIIIlIlIlllIl.getMetadata());
         } else {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(llIIIlIlIllIll.field_147717_b, 0.0D, 0.0D, 0.0D, 0.0F);
         }
      }

   }
}
