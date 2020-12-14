package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000z\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a?\u0010'\u001a\u0002H(\"\u0004\b\u0000\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0011(+0\u0001¨\u00061"},
   d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"},
   xs = "kotlin/io/FilesKt"
)
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
   @InlineOnly
   private static final InputStreamReader reader(@NotNull File $this$reader, Charset charset) {
      int $i$f$reader = 0;
      boolean var4 = false;
      InputStream var3 = (InputStream)(new FileInputStream($this$reader));
      var4 = false;
      return new InputStreamReader(var3, charset);
   }

   // $FF: synthetic method
   static InputStreamReader reader$default(File $this$reader, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$reader = false;
      boolean var4 = false;
      InputStream var6 = (InputStream)(new FileInputStream($this$reader));
      var4 = false;
      return new InputStreamReader(var6, charset);
   }

   @InlineOnly
   private static final BufferedReader bufferedReader(@NotNull File $this$bufferedReader, Charset charset, int bufferSize) {
      int $i$f$bufferedReader = 0;
      boolean var5 = false;
      boolean var7 = false;
      InputStream var6 = (InputStream)(new FileInputStream($this$bufferedReader));
      var7 = false;
      Reader var4 = (Reader)(new InputStreamReader(var6, charset));
      var5 = false;
      return var4 instanceof BufferedReader ? (BufferedReader)var4 : new BufferedReader(var4, bufferSize);
   }

   // $FF: synthetic method
   static BufferedReader bufferedReader$default(File $this$bufferedReader, Charset charset, int bufferSize, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         bufferSize = 8192;
      }

      int $i$f$bufferedReader = false;
      boolean var5 = false;
      boolean var7 = false;
      InputStream var6 = (InputStream)(new FileInputStream($this$bufferedReader));
      var7 = false;
      Reader var9 = (Reader)(new InputStreamReader(var6, charset));
      var5 = false;
      return var9 instanceof BufferedReader ? (BufferedReader)var9 : new BufferedReader(var9, bufferSize);
   }

   @InlineOnly
   private static final OutputStreamWriter writer(@NotNull File $this$writer, Charset charset) {
      int $i$f$writer = 0;
      boolean var4 = false;
      OutputStream var3 = (OutputStream)(new FileOutputStream($this$writer));
      var4 = false;
      return new OutputStreamWriter(var3, charset);
   }

   // $FF: synthetic method
   static OutputStreamWriter writer$default(File $this$writer, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$writer = false;
      boolean var4 = false;
      OutputStream var6 = (OutputStream)(new FileOutputStream($this$writer));
      var4 = false;
      return new OutputStreamWriter(var6, charset);
   }

   @InlineOnly
   private static final BufferedWriter bufferedWriter(@NotNull File $this$bufferedWriter, Charset charset, int bufferSize) {
      int $i$f$bufferedWriter = 0;
      boolean var5 = false;
      boolean var7 = false;
      OutputStream var6 = (OutputStream)(new FileOutputStream($this$bufferedWriter));
      var7 = false;
      Writer var4 = (Writer)(new OutputStreamWriter(var6, charset));
      var5 = false;
      return var4 instanceof BufferedWriter ? (BufferedWriter)var4 : new BufferedWriter(var4, bufferSize);
   }

   // $FF: synthetic method
   static BufferedWriter bufferedWriter$default(File $this$bufferedWriter, Charset charset, int bufferSize, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      if ((var3 & 2) != 0) {
         bufferSize = 8192;
      }

      int $i$f$bufferedWriter = false;
      boolean var5 = false;
      boolean var7 = false;
      OutputStream var6 = (OutputStream)(new FileOutputStream($this$bufferedWriter));
      var7 = false;
      Writer var9 = (Writer)(new OutputStreamWriter(var6, charset));
      var5 = false;
      return var9 instanceof BufferedWriter ? (BufferedWriter)var9 : new BufferedWriter(var9, bufferSize);
   }

   @InlineOnly
   private static final PrintWriter printWriter(@NotNull File $this$printWriter, Charset charset) {
      int $i$f$printWriter = 0;
      short var4 = 8192;
      boolean var5 = false;
      boolean var7 = false;
      boolean var9 = false;
      OutputStream var8 = (OutputStream)(new FileOutputStream($this$printWriter));
      var9 = false;
      Writer var6 = (Writer)(new OutputStreamWriter(var8, charset));
      var7 = false;
      BufferedWriter var12 = var6 instanceof BufferedWriter ? (BufferedWriter)var6 : new BufferedWriter(var6, var4);
      Writer var13 = (Writer)var12;
      return new PrintWriter(var13);
   }

   // $FF: synthetic method
   static PrintWriter printWriter$default(File $this$printWriter, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$printWriter = false;
      short var4 = 8192;
      boolean var5 = false;
      boolean var7 = false;
      boolean var9 = false;
      OutputStream var8 = (OutputStream)(new FileOutputStream($this$printWriter));
      var9 = false;
      Writer var6 = (Writer)(new OutputStreamWriter(var8, charset));
      var7 = false;
      BufferedWriter var12 = var6 instanceof BufferedWriter ? (BufferedWriter)var6 : new BufferedWriter(var6, var4);
      Writer var13 = (Writer)var12;
      return new PrintWriter(var13);
   }

   @NotNull
   public static final byte[] readBytes(@NotNull File $this$readBytes) {
      Intrinsics.checkParameterIsNotNull($this$readBytes, "$this$readBytes");
      boolean var2 = false;
      Closeable var1 = (Closeable)(new FileInputStream($this$readBytes));
      var2 = false;
      Throwable var3 = (Throwable)null;

      try {
         FileInputStream input = (FileInputStream)var1;
         int var5 = false;
         int offset = 0;
         long var7 = $this$readBytes.length();
         boolean var9 = false;
         boolean var10 = false;
         int var13 = false;
         if (var7 > (long)Integer.MAX_VALUE) {
            throw (Throwable)(new OutOfMemoryError("File " + $this$readBytes + " is too big (" + var7 + " bytes) to fit in memory."));
         } else {
            int remaining = (int)var7;
            byte[] result = new byte[remaining];

            while(true) {
               int extraByte;
               if (remaining > 0) {
                  extraByte = input.read(result, offset, remaining);
                  if (extraByte >= 0) {
                     remaining -= extraByte;
                     offset += extraByte;
                     continue;
                  }
               }

               byte[] var10000;
               if (remaining > 0) {
                  var10 = false;
                  var10000 = Arrays.copyOf(result, offset);
                  Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.copyOf(this, newSize)");
               } else {
                  extraByte = input.read();
                  if (extraByte == -1) {
                     var10000 = result;
                  } else {
                     ExposingBufferByteArrayOutputStream extra = new ExposingBufferByteArrayOutputStream(8193);
                     extra.write(extraByte);
                     ByteStreamsKt.copyTo$default((InputStream)input, (OutputStream)extra, 0, 2, (Object)null);
                     int resultingSize = result.length + extra.size();
                     if (resultingSize < 0) {
                        throw (Throwable)(new OutOfMemoryError("File " + $this$readBytes + " is too big to fit in memory."));
                     }

                     byte[] var17 = extra.getBuffer();
                     var13 = false;
                     var10000 = Arrays.copyOf(result, resultingSize);
                     Intrinsics.checkExpressionValueIsNotNull(var10000, "java.util.Arrays.copyOf(this, newSize)");
                     byte[] var18 = var10000;
                     var10000 = ArraysKt.copyInto(var17, var18, result.length, 0, extra.size());
                  }
               }

               byte[] var23 = var10000;
               return var23;
            }
         }
      } catch (Throwable var21) {
         var3 = var21;
         throw var21;
      } finally {
         CloseableKt.closeFinally(var1, var3);
      }
   }

   public static final void writeBytes(@NotNull File $this$writeBytes, @NotNull byte[] array) {
      Intrinsics.checkParameterIsNotNull($this$writeBytes, "$this$writeBytes");
      Intrinsics.checkParameterIsNotNull(array, "array");
      Closeable var2 = (Closeable)(new FileOutputStream($this$writeBytes));
      boolean var3 = false;
      Throwable var4 = (Throwable)null;

      try {
         FileOutputStream it = (FileOutputStream)var2;
         int var6 = false;
         it.write(array);
         Unit var11 = Unit.INSTANCE;
      } catch (Throwable var9) {
         var4 = var9;
         throw var9;
      } finally {
         CloseableKt.closeFinally(var2, var4);
      }

   }

   public static final void appendBytes(@NotNull File $this$appendBytes, @NotNull byte[] array) {
      Intrinsics.checkParameterIsNotNull($this$appendBytes, "$this$appendBytes");
      Intrinsics.checkParameterIsNotNull(array, "array");
      Closeable var2 = (Closeable)(new FileOutputStream($this$appendBytes, true));
      boolean var3 = false;
      Throwable var4 = (Throwable)null;

      try {
         FileOutputStream it = (FileOutputStream)var2;
         int var6 = false;
         it.write(array);
         Unit var11 = Unit.INSTANCE;
      } catch (Throwable var9) {
         var4 = var9;
         throw var9;
      } finally {
         CloseableKt.closeFinally(var2, var4);
      }

   }

   @NotNull
   public static final String readText(@NotNull File $this$readText, @NotNull Charset charset) {
      Intrinsics.checkParameterIsNotNull($this$readText, "$this$readText");
      Intrinsics.checkParameterIsNotNull(charset, "charset");
      boolean var3 = false;
      boolean var5 = false;
      InputStream var4 = (InputStream)(new FileInputStream($this$readText));
      var5 = false;
      Closeable var2 = (Closeable)(new InputStreamReader(var4, charset));
      var3 = false;
      Throwable var11 = (Throwable)null;

      String var13;
      try {
         InputStreamReader it = (InputStreamReader)var2;
         int var6 = false;
         var13 = TextStreamsKt.readText((Reader)it);
      } catch (Throwable var9) {
         var11 = var9;
         throw var9;
      } finally {
         CloseableKt.closeFinally(var2, var11);
      }

      return var13;
   }

   // $FF: synthetic method
   public static String readText$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readText(var0, var1);
   }

   public static final void writeText(@NotNull File $this$writeText, @NotNull String text, @NotNull Charset charset) {
      Intrinsics.checkParameterIsNotNull($this$writeText, "$this$writeText");
      Intrinsics.checkParameterIsNotNull(text, "text");
      Intrinsics.checkParameterIsNotNull(charset, "charset");
      boolean var4 = false;
      byte[] var10000 = text.getBytes(charset);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
      byte[] var6 = var10000;
      FilesKt.writeBytes($this$writeText, var6);
   }

   // $FF: synthetic method
   public static void writeText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.writeText(var0, var1, var2);
   }

   public static final void appendText(@NotNull File $this$appendText, @NotNull String text, @NotNull Charset charset) {
      Intrinsics.checkParameterIsNotNull($this$appendText, "$this$appendText");
      Intrinsics.checkParameterIsNotNull(text, "text");
      Intrinsics.checkParameterIsNotNull(charset, "charset");
      boolean var4 = false;
      byte[] var10000 = text.getBytes(charset);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).getBytes(charset)");
      byte[] var6 = var10000;
      FilesKt.appendBytes($this$appendText, var6);
   }

   // $FF: synthetic method
   public static void appendText$default(File var0, String var1, Charset var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = Charsets.UTF_8;
      }

      FilesKt.appendText(var0, var1, var2);
   }

   public static final void forEachBlock(@NotNull File $this$forEachBlock, @NotNull Function2<? super byte[], ? super Integer, Unit> action) {
      Intrinsics.checkParameterIsNotNull($this$forEachBlock, "$this$forEachBlock");
      Intrinsics.checkParameterIsNotNull(action, "action");
      FilesKt.forEachBlock($this$forEachBlock, 4096, action);
   }

   public static final void forEachBlock(@NotNull File $this$forEachBlock, int blockSize, @NotNull Function2<? super byte[], ? super Integer, Unit> action) {
      Intrinsics.checkParameterIsNotNull($this$forEachBlock, "$this$forEachBlock");
      Intrinsics.checkParameterIsNotNull(action, "action");
      byte[] arr = new byte[RangesKt.coerceAtLeast(blockSize, 512)];
      boolean var5 = false;
      Closeable var4 = (Closeable)(new FileInputStream($this$forEachBlock));
      var5 = false;
      Throwable var6 = (Throwable)null;

      try {
         FileInputStream input = (FileInputStream)var4;
         boolean var8 = false;

         while(true) {
            int size = input.read(arr);
            if (size <= 0) {
               Unit var14 = Unit.INSTANCE;
               return;
            }

            action.invoke(arr, size);
         }
      } catch (Throwable var12) {
         var6 = var12;
         throw var12;
      } finally {
         CloseableKt.closeFinally(var4, var6);
      }
   }

   public static final void forEachLine(@NotNull File $this$forEachLine, @NotNull Charset charset, @NotNull Function1<? super String, Unit> action) {
      Intrinsics.checkParameterIsNotNull($this$forEachLine, "$this$forEachLine");
      Intrinsics.checkParameterIsNotNull(charset, "charset");
      Intrinsics.checkParameterIsNotNull(action, "action");
      TextStreamsKt.forEachLine((Reader)(new BufferedReader((Reader)(new InputStreamReader((InputStream)(new FileInputStream($this$forEachLine)), charset)))), action);
   }

   // $FF: synthetic method
   public static void forEachLine$default(File var0, Charset var1, Function1 var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      FilesKt.forEachLine(var0, var1, var2);
   }

   @InlineOnly
   private static final FileInputStream inputStream(@NotNull File $this$inputStream) {
      int $i$f$inputStream = 0;
      return new FileInputStream($this$inputStream);
   }

   @InlineOnly
   private static final FileOutputStream outputStream(@NotNull File $this$outputStream) {
      int $i$f$outputStream = 0;
      return new FileOutputStream($this$outputStream);
   }

   @NotNull
   public static final List<String> readLines(@NotNull File $this$readLines, @NotNull Charset charset) {
      Intrinsics.checkParameterIsNotNull($this$readLines, "$this$readLines");
      Intrinsics.checkParameterIsNotNull(charset, "charset");
      final ArrayList result = new ArrayList();
      FilesKt.forEachLine($this$readLines, charset, (Function1)(new Function1<String, Unit>() {
         public final void invoke(@NotNull String it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            result.add(it);
         }
      }));
      return (List)result;
   }

   // $FF: synthetic method
   public static List readLines$default(File var0, Charset var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = Charsets.UTF_8;
      }

      return FilesKt.readLines(var0, var1);
   }

   public static final <T> T useLines(@NotNull File $this$useLines, @NotNull Charset charset, @NotNull Function1<? super Sequence<String>, ? extends T> block) {
      int $i$f$useLines = 0;
      Intrinsics.checkParameterIsNotNull($this$useLines, "$this$useLines");
      Intrinsics.checkParameterIsNotNull(charset, "charset");
      Intrinsics.checkParameterIsNotNull(block, "block");
      short var5 = 8192;
      boolean var6 = false;
      boolean var8 = false;
      boolean var10 = false;
      InputStream var9 = (InputStream)(new FileInputStream($this$useLines));
      var10 = false;
      Reader var7 = (Reader)(new InputStreamReader(var9, charset));
      var8 = false;
      Closeable var4 = (Closeable)(var7 instanceof BufferedReader ? (BufferedReader)var7 : new BufferedReader(var7, var5));
      boolean var18 = false;
      Throwable var19 = (Throwable)null;
      boolean var14 = false;

      Object var21;
      try {
         var14 = true;
         BufferedReader it = (BufferedReader)var4;
         var8 = false;
         var21 = block.invoke(TextStreamsKt.lineSequence(it));
         var14 = false;
      } catch (Throwable var16) {
         var19 = var16;
         throw var16;
      } finally {
         if (var14) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               CloseableKt.closeFinally(var4, var19);
            } else if (var19 == null) {
               var4.close();
            } else {
               try {
                  var4.close();
               } catch (Throwable var15) {
               }
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var4, var19);
      } else {
         var4.close();
      }

      InlineMarker.finallyEnd(1);
      return var21;
   }

   // $FF: synthetic method
   public static Object useLines$default(File $this$useLines, Charset charset, Function1 block, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$useLines = false;
      Intrinsics.checkParameterIsNotNull($this$useLines, "$this$useLines");
      Intrinsics.checkParameterIsNotNull(charset, "charset");
      Intrinsics.checkParameterIsNotNull(block, "block");
      short var5 = 8192;
      boolean var6 = false;
      boolean var8 = false;
      boolean var10 = false;
      InputStream var9 = (InputStream)(new FileInputStream($this$useLines));
      var10 = false;
      Reader var7 = (Reader)(new InputStreamReader(var9, charset));
      var8 = false;
      Closeable var19 = (Closeable)(var7 instanceof BufferedReader ? (BufferedReader)var7 : new BufferedReader(var7, var5));
      boolean var20 = false;
      Throwable var21 = (Throwable)null;
      boolean var14 = false;

      Object var23;
      try {
         var14 = true;
         BufferedReader it = (BufferedReader)var19;
         var8 = false;
         var23 = block.invoke(TextStreamsKt.lineSequence(it));
         var14 = false;
      } catch (Throwable var16) {
         var21 = var16;
         throw var16;
      } finally {
         if (var14) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               CloseableKt.closeFinally(var19, var21);
            } else if (var21 == null) {
               var19.close();
            } else {
               try {
                  var19.close();
               } catch (Throwable var15) {
               }
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var19, var21);
      } else {
         var19.close();
      }

      InlineMarker.finallyEnd(1);
      return var23;
   }

   public FilesKt__FileReadWriteKt() {
   }
}
