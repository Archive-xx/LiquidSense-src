//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "mcp_stable-22-1.8.9"!

package net.ccbluex.liquidbounce.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.chat.packet.PacketDeserializer;
import net.ccbluex.liquidbounce.chat.packet.PacketSerializer;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientErrorPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientMojangInfoPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientNewJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ClientSuccessPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.Packet;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerBanUserPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerLoginJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerLoginMojangPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerPrivateMessagePacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestJWTPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerRequestMojangInfoPacket;
import net.ccbluex.liquidbounce.chat.packet.packets.ServerUnbanUserPacket;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.login.UserUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018J\u0006\u0010 \u001a\u00020\u001eJ\u0006\u0010!\u001a\u00020\u001eJ\u0006\u0010\"\u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020\u0018J\u0006\u0010%\u001a\u00020\u001eJ\u0015\u0010&\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020\u0018H\u0000¢\u0006\u0002\b(J\u000e\u0010)\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020\u0018J\u000e\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020,J\u0016\u0010-\u001a\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010'\u001a\u00020\u0018J\u0010\u0010.\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0018H\u0002J\u000e\u0010/\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0018R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0011R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c¨\u00060"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/Client;", "Lnet/ccbluex/liquidbounce/chat/ClientListener;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "channel", "Lio/netty/channel/Channel;", "getChannel$LiquidSense", "()Lio/netty/channel/Channel;", "setChannel$LiquidSense", "(Lio/netty/channel/Channel;)V", "deserializer", "Lnet/ccbluex/liquidbounce/chat/packet/PacketDeserializer;", "jwt", "", "getJwt", "()Z", "setJwt", "(Z)V", "loggedIn", "getLoggedIn", "setLoggedIn", "serializer", "Lnet/ccbluex/liquidbounce/chat/packet/PacketSerializer;", "username", "", "getUsername", "()Ljava/lang/String;", "setUsername", "(Ljava/lang/String;)V", "banUser", "", "target", "connect", "disconnect", "isConnected", "loginJWT", "token", "loginMojang", "onMessage", "message", "onMessage$LiquidSense", "sendMessage", "sendPacket", "packet", "Lnet/ccbluex/liquidbounce/chat/packet/packets/Packet;", "sendPrivateMessage", "toUUID", "unbanUser", "LiquidSense"}
)
public abstract class Client extends MinecraftInstance implements ClientListener {
   // $FF: synthetic field
   private boolean loggedIn;
   // $FF: synthetic field
   private final PacketSerializer serializer = new PacketSerializer();
   // $FF: synthetic field
   @NotNull
   private String username = "";
   // $FF: synthetic field
   @Nullable
   private Channel channel;
   // $FF: synthetic field
   private boolean jwt;
   // $FF: synthetic field
   private final PacketDeserializer deserializer = new PacketDeserializer();

   public final void onMessage$LiquidSense(@NotNull String lllllllllllllllllllIlllIIIIIllll) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlllIIIIIllll, "message");
      Gson lllllllllllllllllllIlllIIIIlIIll = (new GsonBuilder()).registerTypeAdapter((Type)Packet.class, lllllllllllllllllllIlllIIIIIllIl.deserializer).create();
      byte lllllllllllllllllllIlllIIIIIlIII = (Packet)lllllllllllllllllllIlllIIIIlIIll.fromJson(lllllllllllllllllllIlllIIIIIllll, Packet.class);
      if (lllllllllllllllllllIlllIIIIIlIII instanceof ClientMojangInfoPacket) {
         lllllllllllllllllllIlllIIIIIllIl.onLogon();

         try {
            String lllllllllllllllllllIlllIIIIllIII = ((ClientMojangInfoPacket)lllllllllllllllllllIlllIIIIIlIII).getSessionHash();
            Minecraft var10000 = access$getMc$p$s1046033730();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "mc");
            MinecraftSessionService var7 = var10000.getSessionService();
            Session var10001 = access$getMc$p$s1046033730().session;
            Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.session");
            GameProfile var8 = var10001.getProfile();
            Session var10002 = access$getMc$p$s1046033730().session;
            Intrinsics.checkExpressionValueIsNotNull(var10002, "mc.session");
            var7.joinServer(var8, var10002.getToken(), lllllllllllllllllllIlllIIIIllIII);
            var10001 = access$getMc$p$s1046033730().session;
            Intrinsics.checkExpressionValueIsNotNull(var10001, "mc.session");
            String var9 = var10001.getUsername();
            Intrinsics.checkExpressionValueIsNotNull(var9, "mc.session.username");
            lllllllllllllllllllIlllIIIIIllIl.username = var9;
            lllllllllllllllllllIlllIIIIIllIl.jwt = false;
            Session var10003 = access$getMc$p$s1046033730().session;
            Intrinsics.checkExpressionValueIsNotNull(var10003, "mc.session");
            String var10 = var10003.getUsername();
            Intrinsics.checkExpressionValueIsNotNull(var10, "mc.session.username");
            Session var10004 = access$getMc$p$s1046033730().session;
            Intrinsics.checkExpressionValueIsNotNull(var10004, "mc.session");
            GameProfile var11 = var10004.getProfile();
            Intrinsics.checkExpressionValueIsNotNull(var11, "mc.session.profile");
            UUID var12 = var11.getId();
            Intrinsics.checkExpressionValueIsNotNull(var12, "mc.session.profile.id");
            lllllllllllllllllllIlllIIIIIllIl.sendPacket((Packet)(new ServerLoginMojangPacket(var10, var12, true)));
         } catch (Throwable var6) {
            lllllllllllllllllllIlllIIIIIllIl.onError(var6);
         }

      } else {
         Intrinsics.checkExpressionValueIsNotNull(lllllllllllllllllllIlllIIIIIlIII, "packet");
         lllllllllllllllllllIlllIIIIIllIl.onPacket(lllllllllllllllllllIlllIIIIIlIII);
      }
   }

   public final void sendMessage(@NotNull String lllllllllllllllllllIllIllllIlIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIllllIlIlI, "message");
      lllllllllllllllllllIllIllllIllIl.sendPacket((Packet)(new ServerMessagePacket(lllllllllllllllllllIllIllllIlIlI)));
   }

   public final void loginMojang() {
      lllllllllllllllllllIlllIIlIIIlII.sendPacket((Packet)(new ServerRequestMojangInfoPacket()));
   }

   // $FF: synthetic method
   public static final Minecraft access$getMc$p$s1046033730() {
      return MinecraftInstance.mc;
   }

   public final void banUser(@NotNull String lllllllllllllllllllIllIlllIlIIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIlllIlIIlI, "target");
      lllllllllllllllllllIllIlllIlIIll.sendPacket((Packet)(new ServerBanUserPacket(lllllllllllllllllllIllIlllIlIIll.toUUID(lllllllllllllllllllIllIlllIlIIlI))));
   }

   public final void setUsername(@NotNull String lllllllllllllllllllIlllIlIIllllI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlllIlIIllllI, "<set-?>");
      lllllllllllllllllllIlllIlIlIIIII.username = lllllllllllllllllllIlllIlIIllllI;
   }

   public final void unbanUser(@NotNull String lllllllllllllllllllIllIlllIIlIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIlllIIlIlI, "target");
      lllllllllllllllllllIllIlllIIllII.sendPacket((Packet)(new ServerUnbanUserPacket(lllllllllllllllllllIllIlllIIllII.toUUID(lllllllllllllllllllIllIlllIIlIlI))));
   }

   private final String toUUID(String lllllllllllllllllllIllIllIllllII) {
      String lllllllllllllllllllIllIllIlllIlI;
      try {
         UUID.fromString(lllllllllllllllllllIllIllIllllII);
         boolean var10001 = false;
         lllllllllllllllllllIllIllIlllIlI = lllllllllllllllllllIllIllIllllII;
      } catch (IllegalArgumentException var6) {
         String lllllllllllllllllllIllIllIllllll = UserUtils.INSTANCE.getUUID(lllllllllllllllllllIllIllIllllII);
         if (StringsKt.isBlank((CharSequence)lllllllllllllllllllIllIllIllllll)) {
            return "";
         }

         int lllllllllllllllllllIllIllIllIlll = (new StringBuffer(lllllllllllllllllllIllIllIllllll)).insert(20, '-').insert(16, '-').insert(12, '-').insert(8, '-');
         String var10000 = lllllllllllllllllllIllIllIllIlll.toString();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "uuid.toString()");
         lllllllllllllllllllIllIllIlllIlI = var10000;
      }

      return lllllllllllllllllllIllIllIlllIlI;
   }

   public final boolean getLoggedIn() {
      return lllllllllllllllllllIlllIlIIIIIII.loggedIn;
   }

   public final void sendPrivateMessage(@NotNull String lllllllllllllllllllIllIllllIIIIl, @NotNull String lllllllllllllllllllIllIlllIllIlI) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIllllIIIIl, "username");
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIlllIllIlI, "message");
      lllllllllllllllllllIllIllllIIIlI.sendPacket((Packet)(new ServerPrivateMessagePacket(lllllllllllllllllllIllIllllIIIIl, lllllllllllllllllllIllIlllIllIlI)));
   }

   public final void loginJWT(@NotNull String lllllllllllllllllllIlllIIIlllIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIlllIIIlllIIl, "token");
      lllllllllllllllllllIlllIIIlllIll.onLogon();
      lllllllllllllllllllIlllIIIlllIll.sendPacket((Packet)(new ServerLoginJWTPacket(lllllllllllllllllllIlllIIIlllIIl, true)));
      lllllllllllllllllllIlllIIIlllIll.jwt = true;
   }

   @NotNull
   public final String getUsername() {
      return lllllllllllllllllllIlllIlIlIllIl.username;
   }

   @Nullable
   public final Channel getChannel$LiquidSense() {
      return lllllllllllllllllllIlllIllIIIIII.channel;
   }

   public final boolean getJwt() {
      return lllllllllllllllllllIlllIlIIllIII.jwt;
   }

   public final void setLoggedIn(boolean lllllllllllllllllllIlllIIllllIII) {
      lllllllllllllllllllIlllIIllllIIl.loggedIn = lllllllllllllllllllIlllIIllllIII;
   }

   public final void setChannel$LiquidSense(@Nullable Channel lllllllllllllllllllIlllIlIllIlll) {
      lllllllllllllllllllIlllIlIlllIIl.channel = lllllllllllllllllllIlllIlIllIlll;
   }

   public Client() {
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("RequestMojangInfo", ServerRequestMojangInfoPacket.class);
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("LoginMojang", ServerLoginMojangPacket.class);
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("Message", ServerMessagePacket.class);
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("PrivateMessage", ServerPrivateMessagePacket.class);
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("BanUser", ServerBanUserPacket.class);
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("UnbanUser", ServerUnbanUserPacket.class);
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("RequestJWT", ServerRequestJWTPacket.class);
      lllllllllllllllllllIllIllIllIlII.serializer.registerPacket("LoginJWT", ServerLoginJWTPacket.class);
      lllllllllllllllllllIllIllIllIlII.deserializer.registerPacket("MojangInfo", ClientMojangInfoPacket.class);
      lllllllllllllllllllIllIllIllIlII.deserializer.registerPacket("NewJWT", ClientNewJWTPacket.class);
      lllllllllllllllllllIllIllIllIlII.deserializer.registerPacket("Message", ClientMessagePacket.class);
      lllllllllllllllllllIllIllIllIlII.deserializer.registerPacket("PrivateMessage", ClientPrivateMessagePacket.class);
      lllllllllllllllllllIllIllIllIlII.deserializer.registerPacket("Error", ClientErrorPacket.class);
      lllllllllllllllllllIllIllIllIlII.deserializer.registerPacket("Success", ClientSuccessPacket.class);
   }

   public final void sendPacket(@NotNull Packet lllllllllllllllllllIllIlllllIIIl) {
      Intrinsics.checkParameterIsNotNull(lllllllllllllllllllIllIlllllIIIl, "packet");
      String lllllllllllllllllllIllIlllllIIII = (new GsonBuilder()).registerTypeAdapter((Type)Packet.class, lllllllllllllllllllIllIlllllIIlI.serializer).create();
      Channel var10000 = lllllllllllllllllllIllIlllllIIlI.channel;
      boolean var10001;
      if (var10000 != null) {
         var10000.writeAndFlush(new TextWebSocketFrame(lllllllllllllllllllIllIlllllIIII.toJson(lllllllllllllllllllIllIlllllIIIl, (Type)Packet.class)));
         var10001 = false;
      } else {
         var10001 = false;
      }

   }

   public final void connect() {
      lllllllllllllllllllIlllIIlIlIlII.onConnect();
      byte lllllllllllllllllllIlllIIlIlIIII = new URI("wss://chat.liquidbounce.net:7886/ws");
      boolean lllllllllllllllllllIlllIIlIlIllI = StringsKt.equals(lllllllllllllllllllIlllIIlIlIIII.getScheme(), "wss", true);
      final SslContext lllllllllllllllllllIlllIIlIllIII = lllllllllllllllllllIlllIIlIlIllI ? SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE) : null;
      boolean lllllllllllllllllllIlllIIlIIllII = new NioEventLoopGroup();
      WebSocketClientHandshaker var10003 = WebSocketClientHandshakerFactory.newHandshaker(lllllllllllllllllllIlllIIlIlIIII, WebSocketVersion.V13, (String)null, true, (HttpHeaders)(new DefaultHttpHeaders()));
      Intrinsics.checkExpressionValueIsNotNull(var10003, "WebSocketClientHandshake…ue, DefaultHttpHeaders())");
      final char lllllllllllllllllllIlllIIlIIlIll = new ClientHandler(lllllllllllllllllllIlllIIlIlIlII, var10003);
      Bootstrap lllllllllllllllllllIlllIIlIlllII = new Bootstrap();
      ((Bootstrap)((Bootstrap)lllllllllllllllllllIlllIIlIlllII.group((EventLoopGroup)lllllllllllllllllllIlllIIlIIllII)).channel(NioSocketChannel.class)).handler((ChannelHandler)(new ChannelInitializer<SocketChannel>() {
         protected void initChannel(@NotNull SocketChannel llIIIlIIlllllll) {
            Intrinsics.checkParameterIsNotNull(llIIIlIIlllllll, "ch");
            boolean llIIIlIIlllllII = llIIIlIIlllllll.pipeline();
            boolean var10001;
            if (lllllllllllllllllllIlllIIlIllIII != null) {
               llIIIlIIlllllII.addLast(new ChannelHandler[]{(ChannelHandler)lllllllllllllllllllIlllIIlIllIII.newHandler(llIIIlIIlllllll.alloc())});
               var10001 = false;
            }

            llIIIlIIlllllII.addLast(new ChannelHandler[]{(ChannelHandler)(new HttpClientCodec()), (ChannelHandler)(new HttpObjectAggregator(8192)), (ChannelHandler)lllllllllllllllllllIlllIIlIIlIll});
            var10001 = false;
         }
      }));
      boolean var10001 = false;
      lllllllllllllllllllIlllIIlIlIlII.channel = lllllllllllllllllllIlllIIlIlllII.connect(lllllllllllllllllllIlllIIlIlIIII.getHost(), lllllllllllllllllllIlllIIlIlIIII.getPort()).sync().channel();
      lllllllllllllllllllIlllIIlIIlIll.getHandshakeFuture().sync();
      var10001 = false;
      if (lllllllllllllllllllIlllIIlIlIlII.isConnected()) {
         lllllllllllllllllllIlllIIlIlIlII.onConnected();
      }

   }

   public final void setJwt(boolean lllllllllllllllllllIlllIlIIIllII) {
      lllllllllllllllllllIlllIlIIIlllI.jwt = lllllllllllllllllllIlllIlIIIllII;
   }

   public final boolean isConnected() {
      boolean var1;
      if (lllllllllllllllllllIlllIIIlIllll.channel != null) {
         Channel var10000 = lllllllllllllllllllIlllIIIlIllll.channel;
         if (var10000 == null) {
            Intrinsics.throwNpe();
         }

         if (var10000.isOpen()) {
            var1 = true;
            return var1;
         }
      }

      var1 = false;
      return var1;
   }

   public final void disconnect() {
      Channel var10000 = lllllllllllllllllllIlllIIlIIlIII.channel;
      boolean var10001;
      if (var10000 != null) {
         var10000.close();
         var10001 = false;
      } else {
         var10001 = false;
      }

      lllllllllllllllllllIlllIIlIIlIII.channel = (Channel)null;
      lllllllllllllllllllIlllIIlIIlIII.username = "";
      lllllllllllllllllllIlllIIlIIlIII.jwt = false;
   }
}
