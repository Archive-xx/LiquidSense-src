package net.ccbluex.liquidbounce.features.command.commands;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016¢\u0006\u0002\u0010\b¨\u0006\t"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/command/commands/ReloadCommand;", "Lnet/ccbluex/liquidbounce/features/command/Command;", "()V", "execute", "", "args", "", "", "([Ljava/lang/String;)V", "LiquidSense"}
)
public final class ReloadCommand extends Command {
   public ReloadCommand() {
      super("reload", new String[]{"configreload"});
   }

   public void execute(@NotNull String[] llIIIIlIIlll) {
      Intrinsics.checkParameterIsNotNull(llIIIIlIIlll, "args");
      llIIIIlIlIII.chat("Reloading...");
      llIIIIlIlIII.chat("§c§lReloading scripts...");
      LiquidBounce.INSTANCE.getScriptManager().reloadScripts();
      llIIIIlIlIII.chat("§c§lReloading fonts...");
      Fonts.loadFonts();
      llIIIIlIlIII.chat("§c§lReloading modules...");
      LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().modulesConfig);
      llIIIIlIlIII.chat("§c§lReloading values...");
      LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().valuesConfig);
      llIIIIlIlIII.chat("§c§lReloading accounts...");
      LiquidBounce.INSTANCE.getFileManager().loadConfig((FileConfig)LiquidBounce.INSTANCE.getFileManager().accountsConfig);
      llIIIIlIlIII.chat("§c§lReloading friends...");
      LiquidBounce.INSTANCE.getFileManager().loadConfig((FileConfig)LiquidBounce.INSTANCE.getFileManager().friendsConfig);
      llIIIIlIlIII.chat("§c§lReloading xray...");
      LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().xrayConfig);
      llIIIIlIlIII.chat("§c§lReloading HUD...");
      LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
      llIIIIlIlIII.chat("§c§lReloading ClickGUI...");
      LiquidBounce.INSTANCE.setClickGui(new ClickGui());
      LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
      llIIIIlIlIII.chat("Reloaded.");
   }
}
