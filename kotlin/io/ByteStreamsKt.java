package kotlin.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.ByteIterator;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0007\u001a\u00020\b*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u000b\u001a\u00020\f*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\r\u001a\u00020\u000e*\u00020\u000f2\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u001c\u0010\u0010\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\r\u0010\u0013\u001a\u00020\u000e*\u00020\u0014H\u0087\b\u001a\u001d\u0010\u0013\u001a\u00020\u000e*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0018*\u00020\u0001H\u0086\u0002\u001a\f\u0010\u0019\u001a\u00020\u0014*\u00020\u0002H\u0007\u001a\u0016\u0010\u0019\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u0004H\u0007\u001a\u0017\u0010\u001b\u001a\u00020\u001c*\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b\u001a\u0017\u0010\u001d\u001a\u00020\u001e*\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\nH\u0087\b¨\u0006\u001f"},
   d2 = {"buffered", "Ljava/io/BufferedInputStream;", "Ljava/io/InputStream;", "bufferSize", "", "Ljava/io/BufferedOutputStream;", "Ljava/io/OutputStream;", "bufferedReader", "Ljava/io/BufferedReader;", "charset", "Ljava/nio/charset/Charset;", "bufferedWriter", "Ljava/io/BufferedWriter;", "byteInputStream", "Ljava/io/ByteArrayInputStream;", "", "copyTo", "", "out", "inputStream", "", "offset", "length", "iterator", "Lkotlin/collections/ByteIterator;", "readBytes", "estimatedSize", "reader", "Ljava/io/InputStreamReader;", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}
)
@JvmName(
   name = "ByteStreamsKt"
)
public final class ByteStreamsKt {
   @NotNull
   public static final ByteIterator iterator(@NotNull final BufferedInputStream $this$iterator) {
      Intrinsics.checkParameterIsNotNull($this$iterator, "$this$iterator");
      return (ByteIterator)(new ByteIterator() {
         private int nextByte = -1;
         private boolean nextPrepared;
         private boolean finished;

         public final int getNextByte() {
            return this.nextByte;
         }

         public final void setNextByte(int var1) {
            this.nextByte = var1;
         }

         public final boolean getNextPrepared() {
            return this.nextPrepared;
         }

         public final void setNextPrepared(boolean var1) {
            this.nextPrepared = var1;
         }

         public final boolean getFinished() {
            return this.finished;
         }

         public final void setFinished(boolean var1) {
            this.finished = var1;
         }

         private final void prepareNext() {
            if (!this.nextPrepared && !this.finished) {
               this.nextByte = $this$iterator.read();
               this.nextPrepared = true;
               this.finished = this.nextByte == -1;
            }

         }

         public boolean hasNext() {
            this.prepareNext();
            return !this.finished;
         }

         public byte nextByte() {
            this.prepareNext();
            if (this.finished) {
               throw (Throwable)(new NoSuchElementException("Input stream is over."));
            } else {
               byte res = (byte)this.nextByte;
               this.nextPrepared = false;
               return res;
            }
         }
      });
   }

   @InlineOnly
   private static final ByteArrayInputStream byteInputStream(@NotNull String $this$byteInputStream, Charset charset) {
      byte[] var10000 = $this$byteInputStream.getBytes(charset);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
      byte[] var7 = var10000;
      return new ByteArrayInputStream(var7);
   }

   // $FF: synthetic method
   static ByteArrayInputStream byteInputStream$default(String $this$byteInputStream, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$byteInputStream = false;
      boolean var4 = false;
      if ($this$byteInputStream == null) {
         throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
      } else {
         byte[] var10000 = $this$byteInputStream.getBytes(charset);
         Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
         byte[] var7 = var10000;
         return new ByteArrayInputStream(var7);
      }
   }

   @InlineOnly
   private static final ByteArrayInputStream inputStream(@NotNull byte[] $this$inputStream) {
      int $i$f$inputStream = 0;
      return new ByteArrayInputStream($this$inputStream);
   }

   @InlineOnly
   private static final ByteArrayInputStream inputStream(@NotNull byte[] $this$inputStream, int offset, int length) {
      int $i$f$inputStream = 0;
      return new ByteArrayInputStream($this$inputStream, offset, length);
   }

   @InlineOnly
   private static final BufferedInputStream buffered(@NotNull InputStream $this$buffered, int bufferSize) {
      int $i$f$buffered = 0;
      return $this$buffered instanceof BufferedInputStream ? (BufferedInputStream)$this$buffered : new BufferedInputStream($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedInputStream buffered$default(InputStream $this$buffered, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      int $i$f$buffered = false;
      return $this$buffered instanceof BufferedInputStream ? (BufferedInputStream)$this$buffered : new BufferedInputStream($this$buffered, bufferSize);
   }

   @InlineOnly
   private static final InputStreamReader reader(@NotNull InputStream $this$reader, Charset charset) {
      int $i$f$reader = 0;
      return new InputStreamReader($this$reader, charset);
   }

   // $FF: synthetic method
   static InputStreamReader reader$default(InputStream $this$reader, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$reader = false;
      return new InputStreamReader($this$reader, charset);
   }

   @InlineOnly
   private static final BufferedReader bufferedReader(@NotNull InputStream $this$bufferedReader, Charset charset) {
      int $i$f$bufferedReader = 0;
      boolean var4 = false;
      Reader var3 = (Reader)(new InputStreamReader($this$bufferedReader, charset));
      short var6 = 8192;
      boolean var5 = false;
      return var3 instanceof BufferedReader ? (BufferedReader)var3 : new BufferedReader(var3, var6);
   }

   // $FF: synthetic method
   static BufferedReader bufferedReader$default(InputStream $this$bufferedReader, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$bufferedReader = false;
      boolean var4 = false;
      Reader var7 = (Reader)(new InputStreamReader($this$bufferedReader, charset));
      short var8 = 8192;
      boolean var5 = false;
      return var7 instanceof BufferedReader ? (BufferedReader)var7 : new BufferedReader(var7, var8);
   }

   @InlineOnly
   private static final BufferedOutputStream buffered(@NotNull OutputStream $this$buffered, int bufferSize) {
      int $i$f$buffered = 0;
      return $this$buffered instanceof BufferedOutputStream ? (BufferedOutputStream)$this$buffered : new BufferedOutputStream($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedOutputStream buffered$default(OutputStream $this$buffered, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      int $i$f$buffered = false;
      return $this$buffered instanceof BufferedOutputStream ? (BufferedOutputStream)$this$buffered : new BufferedOutputStream($this$buffered, bufferSize);
   }

   @InlineOnly
   private static final OutputStreamWriter writer(@NotNull OutputStream $this$writer, Charset charset) {
      int $i$f$writer = 0;
      return new OutputStreamWriter($this$writer, charset);
   }

   // $FF: synthetic method
   static OutputStreamWriter writer$default(OutputStream $this$writer, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$writer = false;
      return new OutputStreamWriter($this$writer, charset);
   }

   @InlineOnly
   private static final BufferedWriter bufferedWriter(@NotNull OutputStream $this$bufferedWriter, Charset charset) {
      int $i$f$bufferedWriter = 0;
      boolean var4 = false;
      Writer var3 = (Writer)(new OutputStreamWriter($this$bufferedWriter, charset));
      short var6 = 8192;
      boolean var5 = false;
      return var3 instanceof BufferedWriter ? (BufferedWriter)var3 : new BufferedWriter(var3, var6);
   }

   // $FF: synthetic method
   static BufferedWriter bufferedWriter$default(OutputStream $this$bufferedWriter, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$bufferedWriter = false;
      boolean var4 = false;
      Writer var7 = (Writer)(new OutputStreamWriter($this$bufferedWriter, charset));
      short var8 = 8192;
      boolean var5 = false;
      return var7 instanceof BufferedWriter ? (BufferedWriter)var7 : new BufferedWriter(var7, var8);
   }

   public static final long copyTo(@NotNull InputStream $this$copyTo, @NotNull OutputStream out, int bufferSize) {
      Intrinsics.checkParameterIsNotNull($this$copyTo, "$this$copyTo");
      Intrinsics.checkParameterIsNotNull(out, "out");
      long bytesCopied = 0L;
      byte[] buffer = new byte[bufferSize];

      for(int bytes = $this$copyTo.read(buffer); bytes >= 0; bytes = $this$copyTo.read(buffer)) {
         out.write(buffer, 0, bytes);
         bytesCopied += (long)bytes;
      }

      return bytesCopied;
   }

   // $FF: synthetic method
   public static long copyTo$default(InputStream var0, OutputStream var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      return copyTo(var0, var1, var2);
   }

   /** @deprecated */
   @Deprecated(
      message = "Use readBytes() overload without estimatedSize parameter",
      replaceWith = @ReplaceWith(
   imports = {},
   expression = "readBytes()"
)
   )
   @NotNull
   public static final byte[] readBytes(@NotNull InputStream $this$readBytes, int estimatedSize) {
      Intrinsics.checkParameterIsNotNull($this$readBytes, "$this$readBytes");
      int var3 = $this$readBytes.available();
      boolean var4 = false;
      int var7 = Math.max(estimatedSize, var3);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream(var7);
      copyTo$default($this$readBytes, (OutputStream)buffer, 0, 2, (Object)null);
      byte[] var10000 = buffer.toByteArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "buffer.toByteArray()");
      return var10000;
   }

   /** @deprecated */
   // $FF: synthetic method
   public static byte[] readBytes$default(InputStream var0, int var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = 8192;
      }

      return readBytes(var0, var1);
   }

   @SinceKotlin(
      version = "1.3"
   )
   @NotNull
   public static final byte[] readBytes(@NotNull InputStream $this$readBytes) {
      Intrinsics.checkParameterIsNotNull($this$readBytes, "$this$readBytes");
      short var2 = 8192;
      int var3 = $this$readBytes.available();
      boolean var4 = false;
      int var7 = Math.max(var2, var3);
      ByteArrayOutputStream buffer = new ByteArrayOutputStream(var7);
      copyTo$default($this$readBytes, (OutputStream)buffer, 0, 2, (Object)null);
      byte[] var10000 = buffer.toByteArray();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "buffer.toByteArray()");
      return var10000;
   }
}
