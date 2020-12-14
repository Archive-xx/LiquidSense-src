package kotlin.io;

import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference0Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\t\u0010\u0015\u001a\u00020\nH\u0087\b\u001a\u0013\u0010\u0015\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000eH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0010H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0011H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0012H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0013H\u0087\b\u001a\u0011\u0010\u0015\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0014H\u0087\b\u001a\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017\u001a\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\f\u0010\u001a\u001a\u00020\r*\u00020\u001bH\u0002\u001a\f\u0010\u001c\u001a\u00020\n*\u00020\u001dH\u0002\u001a\u0018\u0010\u001e\u001a\u00020\n*\u00020\u001b2\n\u0010\u001f\u001a\u00060 j\u0002`!H\u0002\u001a$\u0010\"\u001a\u00020\r*\u00020\u00042\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001b2\u0006\u0010&\u001a\u00020\rH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006'"},
   d2 = {"BUFFER_SIZE", "", "LINE_SEPARATOR_MAX_LENGTH", "decoder", "Ljava/nio/charset/CharsetDecoder;", "getDecoder", "()Ljava/nio/charset/CharsetDecoder;", "decoder$delegate", "Lkotlin/Lazy;", "print", "", "message", "", "", "", "", "", "", "", "", "", "println", "readLine", "", "inputStream", "Ljava/io/InputStream;", "endsWithLineSeparator", "Ljava/nio/CharBuffer;", "flipBack", "Ljava/nio/Buffer;", "offloadPrefixTo", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "tryDecode", "byteBuffer", "Ljava/nio/ByteBuffer;", "charBuffer", "isEndOfStream", "kotlin-stdlib"}
)
@JvmName(
   name = "ConsoleKt"
)
public final class ConsoleKt {
   // $FF: synthetic field
   static final KProperty[] $$delegatedProperties = new KProperty[]{(KProperty)Reflection.property0(new PropertyReference0Impl(Reflection.getOrCreateKotlinPackage(ConsoleKt.class, "kotlin-stdlib"), "decoder", "getDecoder()Ljava/nio/charset/CharsetDecoder;"))};
   private static final int BUFFER_SIZE = 32;
   private static final int LINE_SEPARATOR_MAX_LENGTH = 2;
   private static final Lazy decoder$delegate;

   static {
      decoder$delegate = LazyKt.lazy((Function0)null.INSTANCE);
   }

   @InlineOnly
   private static final void print(Object message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(int message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(long message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(byte message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(short message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(char message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(boolean message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(float message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(double message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void print(char[] message) {
      int $i$f$print = 0;
      System.out.print(message);
   }

   @InlineOnly
   private static final void println(Object message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(int message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(long message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(byte message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(short message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(char message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(boolean message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(float message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(double message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println(char[] message) {
      int $i$f$println = 0;
      System.out.println(message);
   }

   @InlineOnly
   private static final void println() {
      int $i$f$println = 0;
      System.out.println();
   }

   private static final CharsetDecoder getDecoder() {
      Lazy var0 = decoder$delegate;
      Object var1 = null;
      KProperty var2 = $$delegatedProperties[0];
      boolean var3 = false;
      return (CharsetDecoder)var0.getValue();
   }

   @Nullable
   public static final String readLine() {
      InputStream var10000 = System.in;
      Intrinsics.checkExpressionValueIsNotNull(var10000, "System.`in`");
      return readLine(var10000, getDecoder());
   }

   @Nullable
   public static final String readLine(@NotNull InputStream inputStream, @NotNull CharsetDecoder decoder) {
      Intrinsics.checkParameterIsNotNull(inputStream, "inputStream");
      Intrinsics.checkParameterIsNotNull(decoder, "decoder");
      boolean var2 = decoder.maxCharsPerByte() <= (float)1;
      boolean var3 = false;
      boolean var4 = false;
      if (!var2) {
         int var20 = false;
         String var19 = "Encodings with multiple chars per byte are not supported";
         throw (Throwable)(new IllegalArgumentException(var19.toString()));
      } else {
         ByteBuffer byteBuffer = ByteBuffer.allocate(32);
         CharBuffer charBuffer = CharBuffer.allocate(4);
         StringBuilder stringBuilder = new StringBuilder();
         int read = inputStream.read();
         if (read == -1) {
            return null;
         } else {
            do {
               byteBuffer.put((byte)read);
               Intrinsics.checkExpressionValueIsNotNull(byteBuffer, "byteBuffer");
               Intrinsics.checkExpressionValueIsNotNull(charBuffer, "charBuffer");
               if (tryDecode(decoder, byteBuffer, charBuffer, false)) {
                  if (endsWithLineSeparator(charBuffer)) {
                     break;
                  }

                  if (charBuffer.remaining() < 2) {
                     offloadPrefixTo(charBuffer, stringBuilder);
                  }
               }

               read = inputStream.read();
            } while(read != -1);

            boolean var6 = false;
            boolean var7 = false;
            int var9 = false;
            tryDecode(decoder, byteBuffer, charBuffer, true);
            decoder.reset();
            var6 = false;
            var7 = false;
            CharBuffer $this$with = charBuffer;
            var9 = false;
            int length = charBuffer.position();
            if (length > 0 && charBuffer.get(length - 1) == '\n') {
               --length;
               if (length > 0 && charBuffer.get(length - 1) == '\r') {
                  --length;
               }
            }

            charBuffer.flip();
            boolean var11 = false;
            boolean var12 = false;
            int var21 = 0;

            for(int var13 = length; var21 < var13; ++var21) {
               int var15 = false;
               stringBuilder.append($this$with.get());
            }

            return stringBuilder.toString();
         }
      }
   }

   private static final boolean tryDecode(@NotNull CharsetDecoder $this$tryDecode, ByteBuffer byteBuffer, CharBuffer charBuffer, boolean isEndOfStream) {
      int positionBefore = charBuffer.position();
      byteBuffer.flip();
      CoderResult var5 = $this$tryDecode.decode(byteBuffer, charBuffer, isEndOfStream);
      boolean var6 = false;
      boolean var7 = false;
      int var9 = false;
      if (var5.isError()) {
         var5.throwException();
      }

      boolean var10 = charBuffer.position() > positionBefore;
      var6 = false;
      var7 = false;
      var9 = false;
      if (var10) {
         byteBuffer.clear();
      } else {
         flipBack((Buffer)byteBuffer);
      }

      return var10;
   }

   private static final boolean endsWithLineSeparator(@NotNull CharBuffer $this$endsWithLineSeparator) {
      int p = $this$endsWithLineSeparator.position();
      return p > 0 && $this$endsWithLineSeparator.get(p - 1) == '\n';
   }

   private static final void flipBack(@NotNull Buffer $this$flipBack) {
      $this$flipBack.position($this$flipBack.limit());
      $this$flipBack.limit($this$flipBack.capacity());
   }

   private static final void offloadPrefixTo(@NotNull CharBuffer $this$offloadPrefixTo, StringBuilder builder) {
      $this$offloadPrefixTo.flip();
      int var2 = $this$offloadPrefixTo.limit() - 1;
      boolean var3 = false;
      boolean var4 = false;
      int var8 = 0;

      for(int var5 = var2; var8 < var5; ++var8) {
         int var7 = false;
         builder.append($this$offloadPrefixTo.get());
      }

      $this$offloadPrefixTo.compact();
   }
}
