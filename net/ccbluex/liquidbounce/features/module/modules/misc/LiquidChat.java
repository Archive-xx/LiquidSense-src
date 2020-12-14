//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.chat.Client;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientErrorPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientNewJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientSuccessPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestJWTPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.command.Command;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(
   name = "LiquidChat",
   description = "Allows you to chat with other LiquidBounce users.",
   category = ModuleCategory.MISC
)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000Y\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002*\u0001\n\b\u0007\u0018\u0000  2\u00020\u0001:\u0001 B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0010\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n \u0012*\u0004\u0018\u00010\u00110\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006!"},
   d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "client", "Lnet/ccbluex/liquidbounce/chat/Client;", "getClient", "()Lnet/ccbluex/liquidbounce/chat/Client;", "connectTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "jwtValue", "net/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$jwtValue$1", "Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$jwtValue$1;", "loggedIn", "", "loginThread", "Ljava/lang/Thread;", "urlPattern", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "connect", "", "onDisable", "onSession", "sessionEvent", "Lnet/ccbluex/liquidbounce/event/SessionEvent;", "onUpdate", "updateEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "toChatComponent", "Lnet/minecraft/util/IChatComponent;", "string", "", "Companion", "LiquidSense"}
)
public final class LiquidChat extends Module {
   // $FF: synthetic field
   private boolean loggedIn;
   // $FF: synthetic field
   private final MSTimer connectTimer;
   // $FF: synthetic field
   private Thread loginThread;
   // $FF: synthetic field
   public static final LiquidChat.Companion Companion = new LiquidChat.Companion((DefaultConstructorMarker)null);
   // $FF: synthetic field
   @NotNull
   private static String jwtToken = "";
   // $FF: synthetic field
   private final Pattern urlPattern;
   // $FF: synthetic field
   private final <undefinedtype> jwtValue;
   // $FF: synthetic field
   @NotNull
   private final Client client;

   private final void connect() {
      label29: {
         if (!llllIlIlllIIIlI.client.isConnected()) {
            if (llllIlIlllIIIlI.loginThread == null) {
               break label29;
            }

            Thread var10000 = llllIlIlllIIIlI.loginThread;
            if (var10000 == null) {
               Intrinsics.throwNpe();
            }

            if (!var10000.isAlive()) {
               break label29;
            }
         }

         return;
      }

      if ((Boolean)llllIlIlllIIIlI.jwtValue.get()) {
         long llllIlIlllIIIII = (CharSequence)jwtToken;
         String llllIlIllIlllll = false;
         if (llllIlIlllIIIII.length() == 0) {
            ClientUtils.displayChatMessage("§7[§a§lChat§7] §cError: §7No token provided!");
            llllIlIlllIIIlI.setState(false);
            return;
         }
      }

      llllIlIlllIIIlI.loggedIn = false;
      llllIlIlllIIIlI.loginThread = ThreadsKt.thread$default(false, false, (ClassLoader)null, (String)null, 0, (Function0)(new Function0<Unit>() {
         public final void invoke() {
            try {
               llllIlIlllIIIlI.getClient().connect();
               if ((Boolean)llllIlIlllIIIlI.jwtValue.get()) {
                  llllIlIlllIIIlI.getClient().loginJWT(LiquidChat.Companion.getJwtToken());
               } else {
                  UserUtils var10000 = UserUtils.INSTANCE;
                  Session var10001 = LiquidChat.access$getMc$p$s1046033730().session;
                  Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.session");
                  String var3 = var10001.getToken();
                  Intrinsics.checkExpressionValueIsNotNull(var3, "mc.session.token");
                  if (var10000.isValidToken(var3)) {
                     llllIlIlllIIIlI.getClient().loginMojang();
                  }
               }
            } catch (Exception var2) {
               ClientUtils.getLogger().error("LiquidChat error", (Throwable)var2);
               ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§a§lChat§7] §cError: §7").append(var2.getClass().getName()).append(": ").append(var2.getMessage())));
            }

            llllIlIlllIIIlI.loginThread = (Thread)null;
         }
      }), 31, (Object)null);
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public LiquidChat() {
      llllIlIIlIIllII.setState(true);
      llllIlIIlIIllII.setArray(false);
      llllIlIIlIIllII.jwtValue = new BoolValue("JWT", false) {
         protected void onChanged(boolean llIlllIIlIlIIll, boolean llIlllIIlIlIIlI) {
            if (llllIlIIlIIllII.getState()) {
               llllIlIIlIIllII.setState(false);
               llllIlIIlIIllII.setState(true);
            }

         }
      };
      llllIlIIlIIllII.client = (Client)(new Client() {
         public void onLogon() {
            ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Logging in...");
         }

         public void onError(@NotNull Throwable llIIIIIIIlllIll) {
            Intrinsics.checkParameterIsNotNull(llIIIIIIIlllIll, "cause");
            ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§a§lChat§7] §c§lError: §7").append(llIIIIIIIlllIll.getClass().getName()).append(": ").append(llIIIIIIIlllIll.getMessage())));
         }

         public void onConnected() {
            ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Connected to chat server!");
         }

         public void onConnect() {
            ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Connecting to chat server...");
         }

         public void onHandshake(boolean llIIIlllIIIlIlI) {
         }

         public void onDisconnect() {
            ClientUtils.displayChatMessage("§7[§a§lChat§7] §cDisconnected from chat server!");
         }

         public void onPacket(@NotNull Packet llIIIIIIlIIIIll) {
            Intrinsics.checkParameterIsNotNull(llIIIIIIlIIIIll, "packet");
            if (llIIIIIIlIIIIll instanceof ClientMessagePacket) {
               if (access$getMc$p$s1046033730().thePlayer == null) {
                  ClientUtils.getLogger().info(String.valueOf((new StringBuilder()).append("[LiquidChat] ").append(((ClientMessagePacket)llIIIIIIlIIIIll).getUser().getName()).append(": ").append(((ClientMessagePacket)llIIIIIIlIIIIll).getContent())));
                  return;
               }

               byte llIIIIIIIllllllx = new ChatComponentText(String.valueOf((new StringBuilder()).append("§7[§a§lChat§7] §9").append(((ClientMessagePacket)llIIIIIIlIIIIll).getUser().getName()).append(": ")));
               IChatComponent llIIIIIIlIIIlll = llllIlIIlIIllII.toChatComponent(((ClientMessagePacket)llIIIIIIlIIIIll).getContent());
               llIIIIIIIllllllx.appendSibling(llIIIIIIlIIIlll);
               boolean var10001 = false;
               access$getMc$p$s1046033730().thePlayer.addChatMessage((IChatComponent)llIIIIIIIllllllx);
            } else if (llIIIIIIlIIIIll instanceof ClientPrivateMessagePacket) {
               ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§a§lChat§7] §c(P)§9 ").append(((ClientPrivateMessagePacket)llIIIIIIlIIIIll).getUser().getName()).append(": §7").append(((ClientPrivateMessagePacket)llIIIIIIlIIIIll).getContent())));
            } else {
               String llIIIIIIIllllll;
               if (llIIIIIIlIIIIll instanceof ClientErrorPacket) {
                  String var10000;
                  label103: {
                     double llIIIIIIIlllllI = ((ClientErrorPacket)llIIIIIIlIIIIll).getMessage();
                     switch(llIIIIIIIlllllI.hashCode()) {
                     case -1702222618:
                        if (llIIIIIIIlllllI.equals("LoginFailed")) {
                           var10000 = "Login Failed!";
                           break label103;
                        }
                        break;
                     case -1510372431:
                        if (llIIIIIIIlllllI.equals("NotBanned")) {
                           var10000 = "You are not banned!";
                           break label103;
                        }
                        break;
                     case -1285959671:
                        if (llIIIIIIIlllllI.equals("MessageTooLong")) {
                           var10000 = "Message is too long!";
                           break label103;
                        }
                        break;
                     case -1045325153:
                        if (llIIIIIIIlllllI.equals("AlreadyLoggedIn")) {
                           var10000 = "You are already logged in!";
                           break label103;
                        }
                        break;
                     case -643429254:
                        if (llIIIIIIIlllllI.equals("RateLimited")) {
                           var10000 = "You have been rate limited. Please try again later.";
                           break label103;
                        }
                        break;
                     case -187442662:
                        if (llIIIIIIIlllllI.equals("NotLoggedIn")) {
                           var10000 = "You must be logged in to use the chat! Enable LiquidChat.";
                           break label103;
                        }
                        break;
                     case -133334702:
                        if (llIIIIIIIlllllI.equals("InvalidId")) {
                           var10000 = "The given ID is invalid!";
                           break label103;
                        }
                        break;
                     case -52595950:
                        if (llIIIIIIIlllllI.equals("InvalidCharacter")) {
                           var10000 = "Message contains a non-ASCII character!";
                           break label103;
                        }
                        break;
                     case 227434454:
                        if (llIIIIIIIlllllI.equals("PrivateMessageNotAccepted")) {
                           var10000 = "Private message not accepted!";
                           break label103;
                        }
                        break;
                     case 248847163:
                        if (llIIIIIIIlllllI.equals("NotSupported")) {
                           var10000 = "This method is not supported!";
                           break label103;
                        }
                        break;
                     case 635054813:
                        if (llIIIIIIIlllllI.equals("Internal")) {
                           var10000 = "An internal server error occured!";
                           break label103;
                        }
                        break;
                     case 944720037:
                        if (llIIIIIIIlllllI.equals("NotPermitted")) {
                           var10000 = "You are missing the required permissions!";
                           break label103;
                        }
                        break;
                     case 1022838298:
                        if (llIIIIIIIlllllI.equals("EmptyMessage")) {
                           var10000 = "You are trying to send an empty message!";
                           break label103;
                        }
                        break;
                     case 1982491454:
                        if (llIIIIIIIlllllI.equals("Banned")) {
                           var10000 = "You are banned!";
                           break label103;
                        }
                        break;
                     case 1990882921:
                        if (llIIIIIIIlllllI.equals("MojangRequestMissing")) {
                           var10000 = "Mojang request missing!";
                           break label103;
                        }
                     }

                     var10000 = ((ClientErrorPacket)llIIIIIIlIIIIll).getMessage();
                  }

                  llIIIIIIIllllll = var10000;
                  ClientUtils.displayChatMessage(String.valueOf((new StringBuilder()).append("§7[§a§lChat§7] §cError: §7").append(llIIIIIIIllllll)));
               } else if (llIIIIIIlIIIIll instanceof ClientSuccessPacket) {
                  llIIIIIIIllllll = ((ClientSuccessPacket)llIIIIIIlIIIIll).getReason();
                  switch(llIIIIIIIllllll.hashCode()) {
                  case 66543:
                     if (llIIIIIIIllllll.equals("Ban")) {
                        ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Successfully banned user!");
                     }
                     break;
                  case 73596745:
                     if (llIIIIIIIllllll.equals("Login")) {
                        ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Logged in!");
                        ClientUtils.displayChatMessage("====================================");
                        ClientUtils.displayChatMessage("§c>> §lLiquidChat");
                        ClientUtils.displayChatMessage("§7Write message: §a.chat <message>");
                        ClientUtils.displayChatMessage("§7Write private message: §a.pchat <user> <message>");
                        ClientUtils.displayChatMessage("====================================");
                        llIIIIIIlIIIlII.setLoggedIn(true);
                     }
                     break;
                  case 81873590:
                     if (llIIIIIIIllllll.equals("Unban")) {
                        ClientUtils.displayChatMessage("§7[§a§lChat§7] §9Successfully unbanned user!");
                     }
                  }
               } else if (llIIIIIIlIIIIll instanceof ClientNewJWTPacket) {
                  LiquidChat.Companion.setJwtToken(((ClientNewJWTPacket)llIIIIIIlIIIIll).getToken());
                  llllIlIIlIIllII.jwtValue.set(true);
                  llllIlIIlIIllII.setState(false);
                  llllIlIIlIIllII.setState(true);
               }
            }

         }

         // $FF: synthetic method
         public static final Minecraft access$getMc$p$s1046033730() {
            return MinecraftInstance.mc;
         }
      });
      llllIlIIlIIllII.connectTimer = new MSTimer();
      LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command)(new Command("chat", new String[]{"lc", "irc"}) {
         public void execute(@NotNull String[] lllIlIlllIlII) {
            Intrinsics.checkParameterIsNotNull(lllIlIlllIlII, "args");
            if (lllIlIlllIlII.length > 1) {
               if (!llllIlIIlIIllII.getState()) {
                  lllIlIlllIlIl.chat("§cError: §7LiquidChat is disabled!");
                  return;
               }

               if (!llllIlIIlIIllII.getClient().isConnected()) {
                  lllIlIlllIlIl.chat("§cError: §LiquidChat is currently not connected to the server!");
                  return;
               }

               String lllIlIllllIII = StringUtils.toCompleteString(lllIlIlllIlII, 1);
               Client var10000 = llllIlIIlIIllII.getClient();
               Intrinsics.checkExpressionValueIsNotNull(lllIlIllllIII, "message");
               var10000.sendMessage(lllIlIllllIII);
            } else {
               lllIlIlllIlIl.chatSyntax("chat <message>");
            }

         }
      }));
      boolean var10001 = false;
      LiquidBounce.INSTANCE.getCommandManager().registerCommand((Command)(new Command("pchat", new String[]{"privatechat", "lcpm"}) {
         public void execute(@NotNull String[] lIlIIIIIlIIlll) {
            Intrinsics.checkParameterIsNotNull(lIlIIIIIlIIlll, "args");
            if (lIlIIIIIlIIlll.length > 2) {
               if (!llllIlIIlIIllII.getState()) {
                  lIlIIIIIlIIllI.chat("§cError: §7LiquidChat is disabled!");
                  return;
               }

               if (!llllIlIIlIIllII.getClient().isConnected()) {
                  lIlIIIIIlIIllI.chat("§cError: §LiquidChat is currently not connected to the server!");
                  return;
               }

               String lIlIIIIIlIlIIl = lIlIIIIIlIIlll[1];
               String lIlIIIIIlIlIlI = StringUtils.toCompleteString(lIlIIIIIlIIlll, 2);
               Client var10000 = llllIlIIlIIllII.getClient();
               Intrinsics.checkExpressionValueIsNotNull(lIlIIIIIlIlIlI, "message");
               var10000.sendPrivateMessage(lIlIIIIIlIlIIl, lIlIIIIIlIlIlI);
               lIlIIIIIlIIllI.chat("Message was successfully sent.");
            } else {
               lIlIIIIIlIIllI.chatSyntax("pchat <username> <message>");
            }

         }
      }));
      var10001 = false;
      CommandManager var10000 = LiquidBounce.INSTANCE.getCommandManager();
      float llllIlIIlIIIlII = "chattoken";
      float llllIlIIlIIlIlI = var10000;
      int llllIlIIlIIIIll = new String[0];
      llllIlIIlIIlIlI.registerCommand((Command)(new Command(llllIlIIlIIIlII, llllIlIIlIIIIll) {
         public void execute(@NotNull String[] lllIlllllIlIIll) {
            Intrinsics.checkParameterIsNotNull(lllIlllllIlIIll, "args");
            if (lllIlllllIlIIll.length > 1) {
               if (StringsKt.equals(lllIlllllIlIIll[1], "set", true)) {
                  if (lllIlllllIlIIll.length > 2) {
                     LiquidChat.Companion var10000 = LiquidChat.Companion;
                     String var10001 = StringUtils.toCompleteString(lllIlllllIlIIll, 2);
                     Intrinsics.checkExpressionValueIsNotNull(var10001, "StringUtils.toCompleteString(args, 2)");
                     var10000.setJwtToken(var10001);
                     llllIlIIlIIllII.jwtValue.set(true);
                     if (llllIlIIlIIllII.getState()) {
                        llllIlIIlIIllII.setState(false);
                        llllIlIIlIIllII.setState(true);
                     }
                  } else {
                     lllIlllllIlIlII.chatSyntax("chattoken set <token>");
                  }
               } else if (StringsKt.equals(lllIlllllIlIIll[1], "generate", true)) {
                  if (!llllIlIIlIIllII.getState()) {
                     lllIlllllIlIlII.chat("§cError: §7LiquidChat is disabled!");
                     return;
                  }

                  llllIlIIlIIllII.getClient().sendPacket((Packet)(new ServerRequestJWTPacket()));
               } else if (StringsKt.equals(lllIlllllIlIIll[1], "copy", true)) {
                  StringSelection lllIlllllIlIlll = new StringSelection(LiquidChat.Companion.getJwtToken());
                  Toolkit var3 = Toolkit.getDefaultToolkit();
                  Intrinsics.checkExpressionValueIsNotNull(var3, "Toolkit.getDefaultToolkit()");
                  var3.getSystemClipboard().setContents((Transferable)lllIlllllIlIlll, (ClipboardOwner)lllIlllllIlIlll);
                  lllIlllllIlIlII.chat("§aCopied to clipboard!");
               }
            } else {
               lllIlllllIlIlII.chatSyntax("chattoken <set/copy/generate>");
            }

         }
      }));
      var10001 = false;
      var10000 = LiquidBounce.INSTANCE.getCommandManager();
      llllIlIIlIIIlII = "chatadmin";
      llllIlIIlIIlIlI = var10000;
      llllIlIIlIIIIll = new String[0];
      llllIlIIlIIlIlI.registerCommand((Command)(new Command(llllIlIIlIIIlII, llllIlIIlIIIIll) {
         public void execute(@NotNull String[] lllllllllllllllllllIllIlIIlIIIlI) {
            Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIlIIlIIIlI, "args");
            if (!llllIlIIlIIllII.getState()) {
               lllllllllllllllllllIllIlIIlIIIll.chat("§cError: §7LiquidChat is disabled!");
            } else {
               if (lllllllllllllllllllIllIlIIlIIIlI.length > 1) {
                  if (StringsKt.equals(lllllllllllllllllllIllIlIIlIIIlI[1], "ban", true)) {
                     if (lllllllllllllllllllIllIlIIlIIIlI.length > 2) {
                        llllIlIIlIIllII.getClient().banUser(lllllllllllllllllllIllIlIIlIIIlI[2]);
                     } else {
                        lllllllllllllllllllIllIlIIlIIIll.chatSyntax("chatadmin ban <username>");
                     }
                  } else if (StringsKt.equals(lllllllllllllllllllIllIlIIlIIIlI[1], "unban", true)) {
                     if (lllllllllllllllllllIllIlIIlIIIlI.length > 2) {
                        llllIlIIlIIllII.getClient().unbanUser(lllllllllllllllllllIllIlIIlIIIlI[2]);
                     } else {
                        lllllllllllllllllllIllIlIIlIIIll.chatSyntax("chatadmin unban <username>");
                     }
                  }
               } else {
                  lllllllllllllllllllIllIlIIlIIIll.chatSyntax("chatadmin <ban/unban>");
               }

            }
         }
      }));
      var10001 = false;
      llllIlIIlIIllII.urlPattern = Pattern.compile("((?:[a-z0-9]{2,}:\\/\\/)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|(?:[-\\w_\\.]{1,}\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"§ \n]|$))", 2);
   }

   @EventTarget
   public final void onSession(@NotNull SessionEvent llllIlIlllllIIl) {
      Intrinsics.checkParameterIsNotNull(llllIlIlllllIIl, "sessionEvent");
      llllIlIlllllIlI.client.disconnect();
      llllIlIlllllIlI.connect();
   }

   public void onDisable() {
      llllIllIIIIIlIl.loggedIn = false;
      llllIllIIIIIlIl.client.disconnect();
   }

   private final IChatComponent toChatComponent(String llllIlIlIIIllIl) {
      long llllIlIlIIIlIII = (IChatComponent)null;
      Matcher llllIlIlIIlIIII = llllIlIlIIIlllI.urlPattern.matcher((CharSequence)llllIlIlIIIllIl);
      int llllIlIlIIlIIIl = 0;

      String var10000;
      boolean var10001;
      ChatStyle var19;
      while(llllIlIlIIlIIII.find()) {
         double llllIlIlIIIIIlI = llllIlIlIIlIIII.start();
         boolean llllIlIIlllllll = llllIlIlIIlIIII.end();
         char llllIlIIlllIlll = false;
         if (llllIlIlIIIllIl == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var10000 = llllIlIlIIIllIl.substring(llllIlIlIIlIIIl, llllIlIlIIIIIlI);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         String llllIlIlIIlIlIl = var10000;
         byte llllIlIIllllIlI = (CharSequence)llllIlIlIIlIlIl;
         llllIlIIlllIlll = false;
         if (llllIlIIllllIlI.length() > 0) {
            if (llllIlIlIIIlIII == null) {
               llllIlIlIIIlIII = (IChatComponent)(new ChatComponentText(llllIlIlIIlIlIl));
               var19 = ((ChatComponentText)llllIlIlIIIlIII).getChatStyle();
               Intrinsics.checkExpressionValueIsNotNull(var19, "component.chatStyle");
               var19.setColor(EnumChatFormatting.GRAY);
               var10001 = false;
            } else {
               llllIlIlIIIlIII.appendText(llllIlIlIIlIlIl);
               var10001 = false;
            }
         }

         llllIlIlIIlIIIl = llllIlIIlllllll;
         boolean llllIlIIlllIlII = false;
         if (llllIlIlIIIllIl == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
         }

         var10000 = llllIlIlIIIllIl.substring(llllIlIlIIIIIlI, llllIlIIlllllll);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.Strin…ing(startIndex, endIndex)");
         String llllIlIlIIlIllI = var10000;

         try {
            if ((new URI(llllIlIlIIlIllI)).getScheme() != null) {
               IChatComponent llllIlIlIIlIlll = (IChatComponent)(new ChatComponentText(llllIlIlIIlIllI));
               var19 = llllIlIlIIlIlll.getChatStyle();
               Intrinsics.checkExpressionValueIsNotNull(var19, "link.chatStyle");
               var19.setChatClickEvent(new ClickEvent(Action.OPEN_URL, llllIlIlIIlIllI));
               var10001 = false;
               var19 = llllIlIlIIlIlll.getChatStyle();
               Intrinsics.checkExpressionValueIsNotNull(var19, "link.chatStyle");
               var19.setUnderlined(true);
               var10001 = false;
               var19 = llllIlIlIIlIlll.getChatStyle();
               Intrinsics.checkExpressionValueIsNotNull(var19, "link.chatStyle");
               var19.setColor(EnumChatFormatting.GRAY);
               var10001 = false;
               if (llllIlIlIIIlIII == null) {
                  llllIlIlIIIlIII = llllIlIlIIlIlll;
               } else {
                  llllIlIlIIIlIII.appendSibling(llllIlIlIIlIlll);
                  var10001 = false;
               }
               continue;
            }
         } catch (URISyntaxException var13) {
         }

         if (llllIlIlIIIlIII == null) {
            llllIlIlIIIlIII = (IChatComponent)(new ChatComponentText(llllIlIlIIlIllI));
            var19 = ((ChatComponentText)llllIlIlIIIlIII).getChatStyle();
            Intrinsics.checkExpressionValueIsNotNull(var19, "component.chatStyle");
            var19.setColor(EnumChatFormatting.GRAY);
            var10001 = false;
         } else {
            llllIlIlIIIlIII.appendText(llllIlIlIIlIllI);
            var10001 = false;
         }
      }

      short llllIlIIlllllIl = false;
      if (llllIlIlIIIllIl == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         var10000 = llllIlIlIIIllIl.substring(llllIlIlIIlIIIl);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
         String llllIlIlIIlIIlI = var10000;
         if (llllIlIlIIIlIII == null) {
            llllIlIlIIIlIII = (IChatComponent)(new ChatComponentText(llllIlIlIIlIIlI));
            var19 = ((ChatComponentText)llllIlIlIIIlIII).getChatStyle();
            Intrinsics.checkExpressionValueIsNotNull(var19, "component.chatStyle");
            var19.setColor(EnumChatFormatting.GRAY);
            var10001 = false;
         } else {
            boolean llllIlIIlllllll = (CharSequence)llllIlIlIIlIIlI;
            llllIlIIlllllIl = false;
            if (llllIlIIlllllll.length() > 0) {
               llllIlIIlllllIl = false;
               if (llllIlIlIIIllIl == null) {
                  throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
               }

               var10000 = llllIlIlIIIllIl.substring(llllIlIlIIlIIIl);
               Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).substring(startIndex)");
               char llllIlIIlllIIII = var10000;
               llllIlIlIIIlIII.appendText(llllIlIIlllIIII);
               var10001 = false;
            }
         }

         return llllIlIlIIIlIII;
      }
   }

   // $FF: synthetic method
   public static final Thread access$getLoginThread$p(LiquidChat llllIlIIIlIlllI) {
      return llllIlIIIlIlllI.loginThread;
   }

   @NotNull
   public final Client getClient() {
      return llllIllIIIIlIIl.client;
   }

   @EventTarget
   public final void onUpdate(@NotNull UpdateEvent llllIlIlllIllll) {
      Intrinsics.checkParameterIsNotNull(llllIlIlllIllll, "updateEvent");
      if (!llllIlIllllIIII.client.isConnected()) {
         if (llllIlIllllIIII.loginThread != null) {
            Thread var10000 = llllIlIllllIIII.loginThread;
            if (var10000 == null) {
               Intrinsics.throwNpe();
            }

            if (var10000.isAlive()) {
               return;
            }
         }

         if (llllIlIllllIIII.connectTimer.hasTimePassed(5000L)) {
            llllIlIllllIIII.connect();
            llllIlIllllIIII.connectTimer.reset();
         }

      }
   }

   @Metadata(
      mv = {1, 1, 16},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
      d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/LiquidChat$Companion;", "", "()V", "jwtToken", "", "getJwtToken", "()Ljava/lang/String;", "setJwtToken", "(Ljava/lang/String;)V", "LiquidSense"}
   )
   public static final class Companion {
      public final void setJwtToken(@NotNull String lllllllllllllllllIllIIlllllllIII) {
         Intrinsics.checkParameterIsNotNull(lllllllllllllllllIllIIlllllllIII, "<set-?>");
         LiquidChat.jwtToken = lllllllllllllllllIllIIlllllllIII;
      }

      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker lllllllllllllllllIllIIllllllIIIl) {
         this();
      }

      @NotNull
      public final String getJwtToken() {
         return LiquidChat.jwtToken;
      }
   }
}
