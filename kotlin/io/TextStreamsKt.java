package kotlin.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000X\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\u0000\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\u0087\b\u001a5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\u0000\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u001f\u0082\u0002\b\n\u0006\b\u0011(\u001e0\u0001¨\u0006 "},
   d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"}
)
@JvmName(
   name = "TextStreamsKt"
)
public final class TextStreamsKt {
   @InlineOnly
   private static final BufferedReader buffered(@NotNull Reader $this$buffered, int bufferSize) {
      int $i$f$buffered = 0;
      return $this$buffered instanceof BufferedReader ? (BufferedReader)$this$buffered : new BufferedReader($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedReader buffered$default(Reader $this$buffered, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      int $i$f$buffered = false;
      return $this$buffered instanceof BufferedReader ? (BufferedReader)$this$buffered : new BufferedReader($this$buffered, bufferSize);
   }

   @InlineOnly
   private static final BufferedWriter buffered(@NotNull Writer $this$buffered, int bufferSize) {
      int $i$f$buffered = 0;
      return $this$buffered instanceof BufferedWriter ? (BufferedWriter)$this$buffered : new BufferedWriter($this$buffered, bufferSize);
   }

   // $FF: synthetic method
   static BufferedWriter buffered$default(Writer $this$buffered, int bufferSize, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         bufferSize = 8192;
      }

      int $i$f$buffered = false;
      return $this$buffered instanceof BufferedWriter ? (BufferedWriter)$this$buffered : new BufferedWriter($this$buffered, bufferSize);
   }

   public static final void forEachLine(@NotNull Reader $this$forEachLine, @NotNull Function1<? super String, Unit> action) {
      Intrinsics.checkParameterIsNotNull($this$forEachLine, "$this$forEachLine");
      Intrinsics.checkParameterIsNotNull(action, "action");
      int $i$f$useLines = false;
      short var5 = 8192;
      boolean var6 = false;
      Closeable var4 = (Closeable)($this$forEachLine instanceof BufferedReader ? (BufferedReader)$this$forEachLine : new BufferedReader($this$forEachLine, var5));
      boolean var20 = false;
      Throwable var21 = (Throwable)null;

      try {
         BufferedReader it$iv = (BufferedReader)var4;
         int var8 = false;
         Sequence it = lineSequence(it$iv);
         int var10 = false;
         Function1 action$iv = action;
         int $i$f$forEach = false;
         Iterator var14 = it.iterator();

         while(var14.hasNext()) {
            Object element$iv = var14.next();
            action$iv.invoke(element$iv);
         }

         Unit var22 = Unit.INSTANCE;
      } catch (Throwable var18) {
         var21 = var18;
         throw var18;
      } finally {
         CloseableKt.closeFinally(var4, var21);
      }
   }

   @NotNull
   public static final List<String> readLines(@NotNull Reader $this$readLines) {
      Intrinsics.checkParameterIsNotNull($this$readLines, "$this$readLines");
      boolean var2 = false;
      final ArrayList result = new ArrayList();
      forEachLine($this$readLines, (Function1)(new Function1<String, Unit>() {
         public final void invoke(@NotNull String it) {
            Intrinsics.checkParameterIsNotNull(it, "it");
            result.add(it);
         }
      }));
      return (List)result;
   }

   public static final <T> T useLines(@NotNull Reader $this$useLines, @NotNull Function1<? super Sequence<String>, ? extends T> block) {
      int $i$f$useLines = 0;
      Intrinsics.checkParameterIsNotNull($this$useLines, "$this$useLines");
      Intrinsics.checkParameterIsNotNull(block, "block");
      short var4 = 8192;
      boolean var5 = false;
      Closeable var3 = (Closeable)($this$useLines instanceof BufferedReader ? (BufferedReader)$this$useLines : new BufferedReader($this$useLines, var4));
      boolean var15 = false;
      Throwable var16 = (Throwable)null;
      boolean var11 = false;

      Object var17;
      try {
         var11 = true;
         BufferedReader it = (BufferedReader)var3;
         int var7 = false;
         var17 = block.invoke(lineSequence(it));
         var11 = false;
      } catch (Throwable var13) {
         var16 = var13;
         throw var13;
      } finally {
         if (var11) {
            InlineMarker.finallyStart(1);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
               CloseableKt.closeFinally(var3, var16);
            } else if (var16 == null) {
               var3.close();
            } else {
               try {
                  var3.close();
               } catch (Throwable var12) {
               }
            }

            InlineMarker.finallyEnd(1);
         }
      }

      InlineMarker.finallyStart(1);
      if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
         CloseableKt.closeFinally(var3, var16);
      } else {
         var3.close();
      }

      InlineMarker.finallyEnd(1);
      return var17;
   }

   @InlineOnly
   private static final StringReader reader(@NotNull String $this$reader) {
      int $i$f$reader = 0;
      return new StringReader($this$reader);
   }

   @NotNull
   public static final Sequence<String> lineSequence(@NotNull BufferedReader $this$lineSequence) {
      Intrinsics.checkParameterIsNotNull($this$lineSequence, "$this$lineSequence");
      return SequencesKt.constrainOnce((Sequence)(new LinesSequence($this$lineSequence)));
   }

   @NotNull
   public static final String readText(@NotNull Reader $this$readText) {
      Intrinsics.checkParameterIsNotNull($this$readText, "$this$readText");
      StringWriter buffer = new StringWriter();
      copyTo$default($this$readText, (Writer)buffer, 0, 2, (Object)null);
      String var10000 = buffer.toString();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "buffer.toString()");
      return var10000;
   }

   public static final long copyTo(@NotNull Reader $this$copyTo, @NotNull Writer out, int bufferSize) {
      Intrinsics.checkParameterIsNotNull($this$copyTo, "$this$copyTo");
      Intrinsics.checkParameterIsNotNull(out, "out");
      long charsCopied = 0L;
      char[] buffer = new char[bufferSize];

      for(int chars = $this$copyTo.read(buffer); chars >= 0; chars = $this$copyTo.read(buffer)) {
         out.write(buffer, 0, chars);
         charsCopied += (long)chars;
      }

      return charsCopied;
   }

   // $FF: synthetic method
   public static long copyTo$default(Reader var0, Writer var1, int var2, int var3, Object var4) {
      if ((var3 & 2) != 0) {
         var2 = 8192;
      }

      return copyTo(var0, var1, var2);
   }

   @InlineOnly
   private static final String readText(@NotNull URL $this$readText, Charset charset) {
      int $i$f$readText = 0;
      byte[] var3 = readBytes($this$readText);
      boolean var4 = false;
      boolean var5 = false;
      return new String(var3, charset);
   }

   // $FF: synthetic method
   static String readText$default(URL $this$readText, Charset charset, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         charset = Charsets.UTF_8;
      }

      int $i$f$readText = false;
      byte[] var7 = readBytes($this$readText);
      boolean var4 = false;
      boolean var5 = false;
      return new String(var7, charset);
   }

   @NotNull
   public static final byte[] readBytes(@NotNull URL $this$readBytes) {
      Intrinsics.checkParameterIsNotNull($this$readBytes, "$this$readBytes");
      Closeable var1 = (Closeable)$this$readBytes.openStream();
      boolean var2 = false;
      Throwable var3 = (Throwable)null;

      byte[] var10;
      try {
         InputStream it = (InputStream)var1;
         int var5 = false;
         Intrinsics.checkExpressionValueIsNotNull(it, "it");
         var10 = ByteStreamsKt.readBytes(it);
      } catch (Throwable var8) {
         var3 = var8;
         throw var8;
      } finally {
         CloseableKt.closeFinally(var1, var3);
      }

      return var10;
   }
}
