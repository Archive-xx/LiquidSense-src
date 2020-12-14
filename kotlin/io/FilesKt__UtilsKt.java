package kotlin.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 5,
   xi = 1,
   d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u001a(\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a(\u0010\r\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0002\u001a8\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\u001a\b\u0002\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00150\u0013\u001a&\u0010\u0016\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u001a\n\u0010\u0019\u001a\u00020\u000f*\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\n\u0010\u001c\u001a\u00020\u0002*\u00020\u0002\u001a\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00020\u001d*\b\u0012\u0004\u0012\u00020\u00020\u001dH\u0002¢\u0006\u0002\b\u001e\u001a\u0011\u0010\u001c\u001a\u00020\u001f*\u00020\u001fH\u0002¢\u0006\u0002\b\u001e\u001a\u0012\u0010 \u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0014\u0010\"\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010#\u001a\u00020\u0002*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0002\u001a\u0012\u0010&\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0001\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0002\u001a\u0012\u0010'\u001a\u00020\u000f*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0001\u001a\u0012\u0010(\u001a\u00020\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002\u001a\u001b\u0010)\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010!\u001a\u00020\u0002H\u0002¢\u0006\u0002\b*\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\b\u0010\u0004¨\u0006+"},
   d2 = {"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"},
   xs = "kotlin/io/FilesKt"
)
class FilesKt__UtilsKt extends FilesKt__FileTreeWalkKt {
   @NotNull
   public static final File createTempDir(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      File dir = File.createTempFile(prefix, suffix, directory);
      dir.delete();
      if (dir.mkdir()) {
         Intrinsics.checkExpressionValueIsNotNull(dir, "dir");
         return dir;
      } else {
         throw (Throwable)(new IOException("Unable to create temporary directory " + dir + '.'));
      }
   }

   // $FF: synthetic method
   public static File createTempDir$default(String var0, String var1, File var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var0 = "tmp";
      }

      if ((var3 & 2) != 0) {
         var1 = (String)null;
      }

      if ((var3 & 4) != 0) {
         var2 = (File)null;
      }

      return FilesKt.createTempDir(var0, var1, var2);
   }

   @NotNull
   public static final File createTempFile(@NotNull String prefix, @Nullable String suffix, @Nullable File directory) {
      Intrinsics.checkParameterIsNotNull(prefix, "prefix");
      File var10000 = File.createTempFile(prefix, suffix, directory);
      Intrinsics.checkExpressionValueIsNotNull(var10000, "File.createTempFile(prefix, suffix, directory)");
      return var10000;
   }

   // $FF: synthetic method
   public static File createTempFile$default(String var0, String var1, File var2, int var3, Object var4) {
      if ((var3 & 1) != 0) {
         var0 = "tmp";
      }

      if ((var3 & 2) != 0) {
         var1 = (String)null;
      }

      if ((var3 & 4) != 0) {
         var2 = (File)null;
      }

      return FilesKt.createTempFile(var0, var1, var2);
   }

   @NotNull
   public static final String getExtension(@NotNull File $this$extension) {
      Intrinsics.checkParameterIsNotNull($this$extension, "$this$extension");
      String var10000 = $this$extension.getName();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "name");
      return StringsKt.substringAfterLast(var10000, '.', "");
   }

   @NotNull
   public static final String getInvariantSeparatorsPath(@NotNull File $this$invariantSeparatorsPath) {
      Intrinsics.checkParameterIsNotNull($this$invariantSeparatorsPath, "$this$invariantSeparatorsPath");
      String var10000;
      if (File.separatorChar != '/') {
         var10000 = $this$invariantSeparatorsPath.getPath();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "path");
         var10000 = StringsKt.replace$default(var10000, File.separatorChar, '/', false, 4, (Object)null);
      } else {
         var10000 = $this$invariantSeparatorsPath.getPath();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "path");
      }

      return var10000;
   }

   @NotNull
   public static final String getNameWithoutExtension(@NotNull File $this$nameWithoutExtension) {
      Intrinsics.checkParameterIsNotNull($this$nameWithoutExtension, "$this$nameWithoutExtension");
      String var10000 = $this$nameWithoutExtension.getName();
      Intrinsics.checkExpressionValueIsNotNull(var10000, "name");
      return StringsKt.substringBeforeLast$default(var10000, ".", (String)null, 2, (Object)null);
   }

   @NotNull
   public static final String toRelativeString(@NotNull File $this$toRelativeString, @NotNull File base) {
      Intrinsics.checkParameterIsNotNull($this$toRelativeString, "$this$toRelativeString");
      Intrinsics.checkParameterIsNotNull(base, "base");
      String var10000 = toRelativeStringOrNull$FilesKt__UtilsKt($this$toRelativeString, base);
      if (var10000 != null) {
         return var10000;
      } else {
         throw (Throwable)(new IllegalArgumentException("this and base files have different roots: " + $this$toRelativeString + " and " + base + '.'));
      }
   }

   @NotNull
   public static final File relativeTo(@NotNull File $this$relativeTo, @NotNull File base) {
      Intrinsics.checkParameterIsNotNull($this$relativeTo, "$this$relativeTo");
      Intrinsics.checkParameterIsNotNull(base, "base");
      return new File(FilesKt.toRelativeString($this$relativeTo, base));
   }

   @NotNull
   public static final File relativeToOrSelf(@NotNull File $this$relativeToOrSelf, @NotNull File base) {
      Intrinsics.checkParameterIsNotNull($this$relativeToOrSelf, "$this$relativeToOrSelf");
      Intrinsics.checkParameterIsNotNull(base, "base");
      String var10000 = toRelativeStringOrNull$FilesKt__UtilsKt($this$relativeToOrSelf, base);
      File var7;
      if (var10000 != null) {
         String var2 = var10000;
         boolean var3 = false;
         boolean var4 = false;
         int var6 = false;
         var7 = new File(var2);
      } else {
         var7 = $this$relativeToOrSelf;
      }

      return var7;
   }

   @Nullable
   public static final File relativeToOrNull(@NotNull File $this$relativeToOrNull, @NotNull File base) {
      Intrinsics.checkParameterIsNotNull($this$relativeToOrNull, "$this$relativeToOrNull");
      Intrinsics.checkParameterIsNotNull(base, "base");
      String var10000 = toRelativeStringOrNull$FilesKt__UtilsKt($this$relativeToOrNull, base);
      File var7;
      if (var10000 != null) {
         String var2 = var10000;
         boolean var3 = false;
         boolean var4 = false;
         int var6 = false;
         var7 = new File(var2);
      } else {
         var7 = null;
      }

      return var7;
   }

   private static final String toRelativeStringOrNull$FilesKt__UtilsKt(@NotNull File $this$toRelativeStringOrNull, File base) {
      FilePathComponents thisComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents($this$toRelativeStringOrNull));
      FilePathComponents baseComponents = normalize$FilesKt__UtilsKt(FilesKt.toComponents(base));
      if (Intrinsics.areEqual((Object)thisComponents.getRoot(), (Object)baseComponents.getRoot()) ^ true) {
         return null;
      } else {
         int baseCount = baseComponents.getSize();
         int thisCount = thisComponents.getSize();
         boolean var8 = false;
         boolean var9 = false;
         int var11 = false;
         int i = 0;
         boolean var15 = false;

         for(int maxSameCount = Math.min(thisCount, baseCount); i < maxSameCount && Intrinsics.areEqual((Object)((File)thisComponents.getSegments().get(i)), (Object)((File)baseComponents.getSegments().get(i))); ++i) {
         }

         int sameCount = i;
         StringBuilder res = new StringBuilder();
         int i = baseCount - 1;
         int var18 = i;
         if (i >= i) {
            while(true) {
               if (Intrinsics.areEqual((Object)((File)baseComponents.getSegments().get(i)).getName(), (Object)"..")) {
                  return null;
               }

               res.append("..");
               if (i != sameCount) {
                  res.append(File.separatorChar);
               }

               if (i == var18) {
                  break;
               }

               --i;
            }
         }

         if (sameCount < thisCount) {
            if (sameCount < baseCount) {
               res.append(File.separatorChar);
            }

            Iterable var10000 = (Iterable)CollectionsKt.drop((Iterable)thisComponents.getSegments(), sameCount);
            Appendable var10001 = (Appendable)res;
            String var10002 = File.separator;
            Intrinsics.checkExpressionValueIsNotNull(var10002, "File.separator");
            CollectionsKt.joinTo$default(var10000, var10001, (CharSequence)var10002, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 124, (Object)null);
         }

         return res.toString();
      }
   }

   @NotNull
   public static final File copyTo(@NotNull File $this$copyTo, @NotNull File target, boolean overwrite, int bufferSize) {
      Intrinsics.checkParameterIsNotNull($this$copyTo, "$this$copyTo");
      Intrinsics.checkParameterIsNotNull(target, "target");
      if (!$this$copyTo.exists()) {
         throw (Throwable)(new NoSuchFileException($this$copyTo, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null));
      } else {
         if (target.exists()) {
            if (!overwrite) {
               throw (Throwable)(new FileAlreadyExistsException($this$copyTo, target, "The destination file already exists."));
            }

            if (!target.delete()) {
               throw (Throwable)(new FileAlreadyExistsException($this$copyTo, target, "Tried to overwrite the destination, but failed to delete it."));
            }
         }

         if ($this$copyTo.isDirectory()) {
            if (!target.mkdirs()) {
               throw (Throwable)(new FileSystemException($this$copyTo, target, "Failed to create target directory."));
            }
         } else {
            File var10000 = target.getParentFile();
            if (var10000 != null) {
               var10000.mkdirs();
            }

            boolean var5 = false;
            Closeable var4 = (Closeable)(new FileInputStream($this$copyTo));
            var5 = false;
            Throwable var6 = (Throwable)null;

            try {
               FileInputStream input = (FileInputStream)var4;
               int var9 = false;
               boolean var11 = false;
               Closeable var10 = (Closeable)(new FileOutputStream(target));
               var11 = false;
               Throwable var12 = (Throwable)null;

               try {
                  FileOutputStream output = (FileOutputStream)var10;
                  int var15 = false;
                  long var28 = ByteStreamsKt.copyTo((InputStream)input, (OutputStream)output, bufferSize);
               } catch (Throwable var24) {
                  var12 = var24;
                  throw var24;
               } finally {
                  CloseableKt.closeFinally(var10, var12);
               }
            } catch (Throwable var26) {
               var6 = var26;
               throw var26;
            } finally {
               CloseableKt.closeFinally(var4, var6);
            }
         }

         return target;
      }
   }

   // $FF: synthetic method
   public static File copyTo$default(File var0, File var1, boolean var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = 8192;
      }

      return FilesKt.copyTo(var0, var1, var2, var3);
   }

   public static final boolean copyRecursively(@NotNull File $this$copyRecursively, @NotNull File target, boolean overwrite, @NotNull final Function2<? super File, ? super IOException, ? extends OnErrorAction> onError) {
      Intrinsics.checkParameterIsNotNull($this$copyRecursively, "$this$copyRecursively");
      Intrinsics.checkParameterIsNotNull(target, "target");
      Intrinsics.checkParameterIsNotNull(onError, "onError");
      if (!$this$copyRecursively.exists()) {
         return (OnErrorAction)onError.invoke($this$copyRecursively, new NoSuchFileException($this$copyRecursively, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null)) != OnErrorAction.TERMINATE;
      } else {
         try {
            Iterator var5 = FilesKt.walkTopDown($this$copyRecursively).onFail((Function2)(new Function2<File, IOException, Unit>() {
               public final void invoke(@NotNull File f, @NotNull IOException e) {
                  Intrinsics.checkParameterIsNotNull(f, "f");
                  Intrinsics.checkParameterIsNotNull(e, "e");
                  if ((OnErrorAction)onError.invoke(f, e) == OnErrorAction.TERMINATE) {
                     throw (Throwable)(new TerminateException(f));
                  }
               }
            })).iterator();

            File src;
            label66:
            do {
               while(var5.hasNext()) {
                  src = (File)var5.next();
                  if (!src.exists()) {
                     continue label66;
                  }

                  String relPath = FilesKt.toRelativeString(src, $this$copyRecursively);
                  File dstFile = new File(target, relPath);
                  if (dstFile.exists() && (!src.isDirectory() || !dstFile.isDirectory())) {
                     boolean stillExists = !overwrite ? true : (dstFile.isDirectory() ? !FilesKt.deleteRecursively(dstFile) : !dstFile.delete());
                     if (stillExists) {
                        if ((OnErrorAction)onError.invoke(dstFile, new FileAlreadyExistsException(src, dstFile, "The destination file already exists.")) == OnErrorAction.TERMINATE) {
                           return false;
                        }
                        continue;
                     }
                  }

                  if (src.isDirectory()) {
                     dstFile.mkdirs();
                  } else if (FilesKt.copyTo$default(src, dstFile, overwrite, 0, 4, (Object)null).length() != src.length() && (OnErrorAction)onError.invoke(src, new IOException("Source file wasn't copied completely, length of destination file differs.")) == OnErrorAction.TERMINATE) {
                     return false;
                  }
               }

               return true;
            } while((OnErrorAction)onError.invoke(src, new NoSuchFileException(src, (File)null, "The source file doesn't exist.", 2, (DefaultConstructorMarker)null)) != OnErrorAction.TERMINATE);

            return false;
         } catch (TerminateException var9) {
            return false;
         }
      }
   }

   // $FF: synthetic method
   public static boolean copyRecursively$default(File var0, File var1, boolean var2, Function2 var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var2 = false;
      }

      if ((var4 & 4) != 0) {
         var3 = (Function2)null.INSTANCE;
      }

      return FilesKt.copyRecursively(var0, var1, var2, var3);
   }

   public static final boolean deleteRecursively(@NotNull File $this$deleteRecursively) {
      Intrinsics.checkParameterIsNotNull($this$deleteRecursively, "$this$deleteRecursively");
      Sequence $this$fold$iv = (Sequence)FilesKt.walkBottomUp($this$deleteRecursively);
      boolean initial$iv = true;
      int $i$f$fold = false;
      boolean accumulator$iv = initial$iv;

      File it;
      for(Iterator var5 = $this$fold$iv.iterator(); var5.hasNext(); accumulator$iv = (it.delete() || !it.exists()) && accumulator$iv) {
         Object element$iv = var5.next();
         it = (File)element$iv;
         int var9 = false;
      }

      return accumulator$iv;
   }

   public static final boolean startsWith(@NotNull File $this$startsWith, @NotNull File other) {
      Intrinsics.checkParameterIsNotNull($this$startsWith, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(other, "other");
      FilePathComponents components = FilesKt.toComponents($this$startsWith);
      FilePathComponents otherComponents = FilesKt.toComponents(other);
      if (Intrinsics.areEqual((Object)components.getRoot(), (Object)otherComponents.getRoot()) ^ true) {
         return false;
      } else {
         return components.getSize() < otherComponents.getSize() ? false : components.getSegments().subList(0, otherComponents.getSize()).equals(otherComponents.getSegments());
      }
   }

   public static final boolean startsWith(@NotNull File $this$startsWith, @NotNull String other) {
      Intrinsics.checkParameterIsNotNull($this$startsWith, "$this$startsWith");
      Intrinsics.checkParameterIsNotNull(other, "other");
      return FilesKt.startsWith($this$startsWith, new File(other));
   }

   public static final boolean endsWith(@NotNull File $this$endsWith, @NotNull File other) {
      Intrinsics.checkParameterIsNotNull($this$endsWith, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(other, "other");
      FilePathComponents components = FilesKt.toComponents($this$endsWith);
      FilePathComponents otherComponents = FilesKt.toComponents(other);
      if (otherComponents.isRooted()) {
         return Intrinsics.areEqual((Object)$this$endsWith, (Object)other);
      } else {
         int shift = components.getSize() - otherComponents.getSize();
         return shift < 0 ? false : components.getSegments().subList(shift, components.getSize()).equals(otherComponents.getSegments());
      }
   }

   public static final boolean endsWith(@NotNull File $this$endsWith, @NotNull String other) {
      Intrinsics.checkParameterIsNotNull($this$endsWith, "$this$endsWith");
      Intrinsics.checkParameterIsNotNull(other, "other");
      return FilesKt.endsWith($this$endsWith, new File(other));
   }

   @NotNull
   public static final File normalize(@NotNull File $this$normalize) {
      Intrinsics.checkParameterIsNotNull($this$normalize, "$this$normalize");
      FilePathComponents var1 = FilesKt.toComponents($this$normalize);
      boolean var2 = false;
      boolean var3 = false;
      int var5 = false;
      File var10000 = var1.getRoot();
      Iterable var10001 = (Iterable)normalize$FilesKt__UtilsKt(var1.getSegments());
      String var10002 = File.separator;
      Intrinsics.checkExpressionValueIsNotNull(var10002, "File.separator");
      return FilesKt.resolve(var10000, CollectionsKt.joinToString$default(var10001, (CharSequence)var10002, (CharSequence)null, (CharSequence)null, 0, (CharSequence)null, (Function1)null, 62, (Object)null));
   }

   private static final FilePathComponents normalize$FilesKt__UtilsKt(@NotNull FilePathComponents $this$normalize) {
      return new FilePathComponents($this$normalize.getRoot(), normalize$FilesKt__UtilsKt($this$normalize.getSegments()));
   }

   private static final List<File> normalize$FilesKt__UtilsKt(@NotNull List<? extends File> $this$normalize) {
      List list = (List)(new ArrayList($this$normalize.size()));
      Iterator var3 = $this$normalize.iterator();

      while(true) {
         while(true) {
            while(var3.hasNext()) {
               File file = (File)var3.next();
               String var10000 = file.getName();
               if (var10000 != null) {
                  String var4 = var10000;
                  switch(var4.hashCode()) {
                  case 46:
                     if (var4.equals(".")) {
                        continue;
                     }
                     break;
                  case 1472:
                     if (var4.equals("..")) {
                        if (!list.isEmpty() && Intrinsics.areEqual((Object)((File)CollectionsKt.last(list)).getName(), (Object)"..") ^ true) {
                           list.remove(list.size() - 1);
                           continue;
                        }

                        list.add(file);
                        continue;
                     }
                  }
               }

               list.add(file);
            }

            return list;
         }
      }
   }

   @NotNull
   public static final File resolve(@NotNull File $this$resolve, @NotNull File relative) {
      Intrinsics.checkParameterIsNotNull($this$resolve, "$this$resolve");
      Intrinsics.checkParameterIsNotNull(relative, "relative");
      if (FilesKt.isRooted(relative)) {
         return relative;
      } else {
         String var10000 = $this$resolve.toString();
         Intrinsics.checkExpressionValueIsNotNull(var10000, "this.toString()");
         String baseName = var10000;
         CharSequence var3 = (CharSequence)baseName;
         boolean var4 = false;
         return var3.length() != 0 && !StringsKt.endsWith$default((CharSequence)baseName, File.separatorChar, false, 2, (Object)null) ? new File(baseName + File.separatorChar + relative) : new File(baseName + relative);
      }
   }

   @NotNull
   public static final File resolve(@NotNull File $this$resolve, @NotNull String relative) {
      Intrinsics.checkParameterIsNotNull($this$resolve, "$this$resolve");
      Intrinsics.checkParameterIsNotNull(relative, "relative");
      return FilesKt.resolve($this$resolve, new File(relative));
   }

   @NotNull
   public static final File resolveSibling(@NotNull File $this$resolveSibling, @NotNull File relative) {
      Intrinsics.checkParameterIsNotNull($this$resolveSibling, "$this$resolveSibling");
      Intrinsics.checkParameterIsNotNull(relative, "relative");
      FilePathComponents components = FilesKt.toComponents($this$resolveSibling);
      File parentSubPath = components.getSize() == 0 ? new File("..") : components.subPath(0, components.getSize() - 1);
      return FilesKt.resolve(FilesKt.resolve(components.getRoot(), parentSubPath), relative);
   }

   @NotNull
   public static final File resolveSibling(@NotNull File $this$resolveSibling, @NotNull String relative) {
      Intrinsics.checkParameterIsNotNull($this$resolveSibling, "$this$resolveSibling");
      Intrinsics.checkParameterIsNotNull(relative, "relative");
      return FilesKt.resolveSibling($this$resolveSibling, new File(relative));
   }

   public FilesKt__UtilsKt() {
   }
}
