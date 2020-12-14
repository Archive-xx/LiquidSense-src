//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.command;

import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010 \n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0003H\u0004J\u001b\u0010\u000f\u001a\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0004¢\u0006\u0002\u0010\u0011J\u0010\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0003H\u0004J\b\u0010\u0013\u001a\u00020\rH\u0004J\u001b\u0010\u0014\u001a\u00020\r2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H&¢\u0006\u0002\u0010\u0011J\b\u0010\u0016\u001a\u00020\rH\u0004J!\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00030\u00182\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u0016¢\u0006\u0002\u0010\u0019R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001a"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/Command;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "command", "", "alias", "", "(Ljava/lang/String;[Ljava/lang/String;)V", "getAlias", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getCommand", "()Ljava/lang/String;", "chat", "", "msg", "chatSyntax", "syntaxes", "([Ljava/lang/String;)V", "syntax", "chatSyntaxError", "execute", "args", "playEdit", "tabComplete", "", "([Ljava/lang/String;)Ljava/util/List;", "LiquidSense"}
)
@SideOnly(Side.CLIENT)
public abstract class Command extends MinecraftInstance {
   // $FF: synthetic field
   @NotNull
   private final String command;
   // $FF: synthetic field
   @NotNull
   private final String[] alias;

   protected final void chatSyntax(@NotNull String[] lllllllllllllllllllllIlllIlIllII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIlllIlIllII, "syntaxes");
      ClientUtils.displayChatMessage("§8[§9§lLiquidSense§8] §3Syntax:");
      char lllllllllllllllllllllIlllIlIlIIl = lllllllllllllllllllllIlllIlIllII;
      Exception lllllllllllllllllllllIlllIlIlIII = lllllllllllllllllllllIlllIlIllII.length;

      for(int lllllllllllllllllllllIlllIlIlIlI = 0; lllllllllllllllllllllIlllIlIlIlI < lllllllllllllllllllllIlllIlIlIII; ++lllllllllllllllllllllIlllIlIlIlI) {
         Exception lllllllllllllllllllllIlllIlIlIll = lllllllllllllllllllllIlllIlIlIIl[lllllllllllllllllllllIlllIlIlIlI];
         Exception lllllllllllllllllllllIlllIlIIlIl = (new StringBuilder()).append("§8> §7").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append(lllllllllllllllllllllIlllIlIllll.command).append(' ');
         float lllllllllllllllllllllIlllIlIIllI = false;
         if (lllllllllllllllllllllIlllIlIlIll == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         String var10000 = lllllllllllllllllllllIlllIlIlIll.toLowerCase();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
         int lllllllllllllllllllllIlllIlIIlII = var10000;
         ClientUtils.displayChatMessage(String.valueOf(lllllllllllllllllllllIlllIlIIlIl.append(lllllllllllllllllllllIlllIlIIlII)));
      }

   }

   public Command(@NotNull String lllllllllllllllllllllIlllIIlIlll, @NotNull String[] lllllllllllllllllllllIlllIIlIIll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIlllIIlIlll, "command");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIlllIIlIIll, "alias");
      super();
      lllllllllllllllllllllIlllIIllIII.command = lllllllllllllllllllllIlllIIlIlll;
      lllllllllllllllllllllIlllIIllIII.alias = lllllllllllllllllllllIlllIIlIIll;
   }

   @NotNull
   public final String getCommand() {
      return lllllllllllllllllllllIlllIIlllll.command;
   }

   protected final void chatSyntax(@NotNull String lllllllllllllllllllllIlllIllllII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIlllIllllII, "syntax");
      ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§8[§9§lLiquidSense§8] §3Syntax: §7").append(LiquidBounce.INSTANCE.getCommandManager().getPrefix()).append(lllllllllllllllllllllIlllIllllII)));
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public abstract void execute(@NotNull String[] var1);

   protected final void playEdit() {
      Minecraft var10000 = access$getMc$p$s1046033730();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
      var10000.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("random.anvil_use"), 1.0F));
   }

   @NotNull
   public final String[] getAlias() {
      return lllllllllllllllllllllIlllIIlllIl.alias;
   }

   protected final void chat(@NotNull String lllllllllllllllllllllIllllIIIIII) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIllllIIIIII, "msg");
      ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§8[§9§lLiquidSense§8] §3").append(lllllllllllllllllllllIllllIIIIII)));
   }

   @NotNull
   public List<String> tabComplete(@NotNull String[] lllllllllllllllllllllIllllIIIIll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllllIllllIIIIll, "args");
      return CollectionsKt.emptyList();
   }

   protected final void chatSyntaxError() {
      ClientUtils.displayChatMessage("§8[§9§lLiquidSense§8] §3Syntax error");
   }
}
