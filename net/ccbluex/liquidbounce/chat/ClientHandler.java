package net.ccbluex.liquidbounce.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0018\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0002H\u0014J\u0018\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"},
   d2 = {"Lnet/ccbluex/liquidbounce/chat/ClientHandler;", "Lio/netty/channel/SimpleChannelInboundHandler;", "", "client", "Lnet/ccbluex/liquidbounce/chat/Client;", "handshaker", "Lio/netty/handler/codec/http/websocketx/WebSocketClientHandshaker;", "(Lnet/ccbluex/liquidbounce/chat/Client;Lio/netty/handler/codec/http/websocketx/WebSocketClientHandshaker;)V", "getClient", "()Lnet/ccbluex/liquidbounce/chat/Client;", "handshakeFuture", "Lio/netty/channel/ChannelPromise;", "getHandshakeFuture", "()Lio/netty/channel/ChannelPromise;", "setHandshakeFuture", "(Lio/netty/channel/ChannelPromise;)V", "channelActive", "", "ctx", "Lio/netty/channel/ChannelHandlerContext;", "channelInactive", "channelRead0", "msg", "exceptionCaught", "cause", "", "handlerAdded", "LiquidSense"}
)
public final class ClientHandler extends SimpleChannelInboundHandler<Object> {
   // $FF: synthetic field
   private final WebSocketClientHandshaker handshaker;
   // $FF: synthetic field
   @NotNull
   private final Client client;
   // $FF: synthetic field
   @NotNull
   public ChannelPromise handshakeFuture;

   public void channelActive(@NotNull ChannelHandlerContext lllllIIIllIllIl) {
      Intrinsics.checkParameterIsNotNull(lllllIIIllIllIl, "ctx");
      lllllIIIllIllII.handshaker.handshake(lllllIIIllIllIl.channel());
      boolean var10001 = false;
   }

   protected void channelRead0(@NotNull ChannelHandlerContext lllllIIIlIlIIII, @NotNull Object lllllIIIlIIllll) {
      Intrinsics.checkParameterIsNotNull(lllllIIIlIlIIII, "ctx");
      Intrinsics.checkParameterIsNotNull(lllllIIIlIIllll, "msg");
      Channel lllllIIIlIlIlIl = lllllIIIlIlIIII.channel();
      Client var10000;
      boolean var7;
      if (!lllllIIIlIlIIIl.handshaker.isHandshakeComplete()) {
         ChannelPromise var6;
         try {
            lllllIIIlIlIIIl.handshaker.finishHandshake(lllllIIIlIlIlIl, (FullHttpResponse)lllllIIIlIIllll);
            var6 = lllllIIIlIlIIIl.handshakeFuture;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("handshakeFuture");
            }

            var6.setSuccess();
            var7 = false;
         } catch (WebSocketHandshakeException var5) {
            var6 = lllllIIIlIlIIIl.handshakeFuture;
            if (var6 == null) {
               Intrinsics.throwUninitializedPropertyAccessException("handshakeFuture");
            }

            var6.setFailure((Throwable)var5);
            var7 = false;
         }

         var10000 = lllllIIIlIlIIIl.client;
         ChannelPromise var8 = lllllIIIlIlIIIl.handshakeFuture;
         if (var8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("handshakeFuture");
         }

         var10000.onHandshake(var8.isSuccess());
      } else {
         if (lllllIIIlIIllll instanceof TextWebSocketFrame) {
            var10000 = lllllIIIlIlIIIl.client;
            String var10001 = ((TextWebSocketFrame)lllllIIIlIIllll).text();
            Intrinsics.checkExpressionValueIsNotNull(var10001, "msg.text()");
            var10000.onMessage$LiquidSense(var10001);
         } else if (lllllIIIlIIllll instanceof CloseWebSocketFrame) {
            lllllIIIlIlIlIl.close();
            var7 = false;
         }

      }
   }

   public void channelInactive(@NotNull ChannelHandlerContext lllllIIIllIIlIl) {
      Intrinsics.checkParameterIsNotNull(lllllIIIllIIlIl, "ctx");
      lllllIIIllIIllI.client.onDisconnect();
      lllllIIIllIIllI.client.setChannel$LiquidSense((Channel)null);
      lllllIIIllIIllI.client.setUsername("");
      lllllIIIllIIllI.client.setJwt(false);
   }

   public void exceptionCaught(@NotNull ChannelHandlerContext lllllIIIllIIIII, @NotNull Throwable lllllIIIlIlllll) {
      Intrinsics.checkParameterIsNotNull(lllllIIIllIIIII, "ctx");
      Intrinsics.checkParameterIsNotNull(lllllIIIlIlllll, "cause");
      ClientUtils.getLogger().error("LiquidChat error", lllllIIIlIlllll);
      lllllIIIllIIIIl.client.onError(lllllIIIlIlllll);
      ChannelPromise var10000 = lllllIIIllIIIIl.handshakeFuture;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("handshakeFuture");
      }

      boolean var10001;
      if (!var10000.isDone()) {
         var10000 = lllllIIIllIIIIl.handshakeFuture;
         if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("handshakeFuture");
         }

         var10000.setFailure(lllllIIIlIlllll);
         var10001 = false;
      }

      lllllIIIllIIIII.close();
      var10001 = false;
   }

   @NotNull
   public final ChannelPromise getHandshakeFuture() {
      ChannelPromise var10000 = lllllIIIlllllIl.handshakeFuture;
      if (var10000 == null) {
         Intrinsics.throwUninitializedPropertyAccessException("handshakeFuture");
      }

      return var10000;
   }

   @NotNull
   public final Client getClient() {
      return lllllIIIlIIlIlI.client;
   }

   public void handlerAdded(@NotNull ChannelHandlerContext lllllIIIlllIIIl) {
      Intrinsics.checkParameterIsNotNull(lllllIIIlllIIIl, "ctx");
      ChannelPromise var10001 = lllllIIIlllIIIl.newPromise();
      Intrinsics.checkExpressionValueIsNotNull(var10001, "ctx.newPromise()");
      lllllIIIlllIlII.handshakeFuture = var10001;
   }

   public ClientHandler(@NotNull Client lllllIIIlIIIlIl, @NotNull WebSocketClientHandshaker lllllIIIlIIIIIl) {
      Intrinsics.checkParameterIsNotNull(lllllIIIlIIIlIl, "client");
      Intrinsics.checkParameterIsNotNull(lllllIIIlIIIIIl, "handshaker");
      super();
      lllllIIIlIIIIll.client = lllllIIIlIIIlIl;
      lllllIIIlIIIIll.handshaker = lllllIIIlIIIIIl;
   }

   public final void setHandshakeFuture(@NotNull ChannelPromise lllllIIIllllIIl) {
      Intrinsics.checkParameterIsNotNull(lllllIIIllllIIl, "<set-?>");
      lllllIIIllllIlI.handshakeFuture = lllllIIIllllIIl;
   }
}
