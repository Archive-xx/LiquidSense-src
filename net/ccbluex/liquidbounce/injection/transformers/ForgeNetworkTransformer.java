//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.injection.transformers;

import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.ClassUtils;
import net.ccbluex.liquidbounce.script.remapper.injection.utils.NodeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;

public class ForgeNetworkTransformer implements IClassTransformer {
   public byte[] transform(String lIIIlIIlIlIlIII, String lIIIlIIlIlIlIlI, byte[] lIIIlIIlIlIIlll) {
      ClassNode lIIIlIIlIlIIllI;
      if (lIIIlIIlIlIlIII.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher")) {
         try {
            lIIIlIIlIlIIllI = ClassUtils.INSTANCE.toClassNode(lIIIlIIlIlIIlll);
            lIIIlIIlIlIIllI.methods.stream().filter((lIIIlIIlIIlIlIl) -> {
               return lIIIlIIlIIlIlIl.name.equals("handleVanilla");
            }).forEach((lIIIlIIlIIllIII) -> {
               LabelNode lIIIlIIlIIllIIl = new LabelNode();
               lIIIlIIlIIllIII.instructions.insertBefore(lIIIlIIlIIllIII.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, lIIIlIIlIIllIIl), new InsnNode(3), new InsnNode(172), lIIIlIIlIIllIIl));
            });
            return ClassUtils.INSTANCE.toBytes(lIIIlIIlIlIIllI);
         } catch (Throwable var6) {
            var6.printStackTrace();
         }
      }

      if (lIIIlIIlIlIlIII.equals("net.minecraftforge.fml.common.network.handshake.HandshakeMessageHandler")) {
         try {
            lIIIlIIlIlIIllI = ClassUtils.INSTANCE.toClassNode(lIIIlIIlIlIIlll);
            lIIIlIIlIlIIllI.methods.stream().filter((lIIIlIIlIIllllI) -> {
               return lIIIlIIlIIllllI.name.equals("channelRead0");
            }).forEach((lIIIlIIlIlIIIIl) -> {
               LabelNode lIIIlIIlIlIIIlI = new LabelNode();
               lIIIlIIlIlIIIIl.instructions.insertBefore(lIIIlIIlIlIIIIl.instructions.getFirst(), NodeUtils.INSTANCE.toNodes(new MethodInsnNode(184, "net/ccbluex/liquidbounce/injection/transformers/ForgeNetworkTransformer", "returnMethod", "()Z", false), new JumpInsnNode(153, lIIIlIIlIlIIIlI), new InsnNode(177), lIIIlIIlIlIIIlI));
            });
            return ClassUtils.INSTANCE.toBytes(lIIIlIIlIlIIllI);
         } catch (Throwable var5) {
            var5.printStackTrace();
         }
      }

      return lIIIlIIlIlIIlll;
   }

   public static boolean returnMethod() {
      return AntiForge.enabled && AntiForge.blockFML && !Minecraft.getMinecraft().isIntegratedServerRunning();
   }
}
