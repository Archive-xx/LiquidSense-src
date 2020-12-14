package kotlin.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin._Assertions;
import kotlin.collections.AbstractIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\u001a\u001b\u001cB\u0019\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0089\u0001\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u0014\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\b\u00128\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014¢\u0006\u0002\u0010\u0015J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u0017H\u0096\u0002J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0014J\u001a\u0010\u0007\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t0\bJ \u0010\f\u001a\u00020\u00002\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\rJ\u001a\u0010\n\u001a\u00020\u00002\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b0\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R@\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000b\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000b\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"},
   d2 = {"Lkotlin/io/FileTreeWalk;", "Lkotlin/sequences/Sequence;", "Ljava/io/File;", "start", "direction", "Lkotlin/io/FileWalkDirection;", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;)V", "onEnter", "Lkotlin/Function1;", "", "onLeave", "", "onFail", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "f", "Ljava/io/IOException;", "e", "maxDepth", "", "(Ljava/io/File;Lkotlin/io/FileWalkDirection;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;I)V", "iterator", "", "depth", "function", "DirectoryState", "FileTreeWalkIterator", "WalkState", "kotlin-stdlib"}
)
public final class FileTreeWalk implements Sequence<File> {
   private final File start;
   private final FileWalkDirection direction;
   private final Function1<File, Boolean> onEnter;
   private final Function1<File, Unit> onLeave;
   private final Function2<File, IOException, Unit> onFail;
   private final int maxDepth;

   @NotNull
   public Iterator<File> iterator() {
      return (Iterator)(new FileTreeWalk.FileTreeWalkIterator());
   }

   @NotNull
   public final FileTreeWalk onEnter(@NotNull Function1<? super File, Boolean> function) {
      Intrinsics.checkParameterIsNotNull(function, "function");
      return new FileTreeWalk(this.start, this.direction, function, this.onLeave, this.onFail, this.maxDepth);
   }

   @NotNull
   public final FileTreeWalk onLeave(@NotNull Function1<? super File, Unit> function) {
      Intrinsics.checkParameterIsNotNull(function, "function");
      return new FileTreeWalk(this.start, this.direction, this.onEnter, function, this.onFail, this.maxDepth);
   }

   @NotNull
   public final FileTreeWalk onFail(@NotNull Function2<? super File, ? super IOException, Unit> function) {
      Intrinsics.checkParameterIsNotNull(function, "function");
      return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, function, this.maxDepth);
   }

   @NotNull
   public final FileTreeWalk maxDepth(int depth) {
      if (depth <= 0) {
         throw (Throwable)(new IllegalArgumentException("depth must be positive, but was " + depth + '.'));
      } else {
         return new FileTreeWalk(this.start, this.direction, this.onEnter, this.onLeave, this.onFail, depth);
      }
   }

   private FileTreeWalk(File start, FileWalkDirection direction, Function1<? super File, Boolean> onEnter, Function1<? super File, Unit> onLeave, Function2<? super File, ? super IOException, Unit> onFail, int maxDepth) {
      this.start = start;
      this.direction = direction;
      this.onEnter = onEnter;
      this.onLeave = onLeave;
      this.onFail = onFail;
      this.maxDepth = maxDepth;
   }

   // $FF: synthetic method
   FileTreeWalk(File var1, FileWalkDirection var2, Function1 var3, Function1 var4, Function2 var5, int var6, int var7, DefaultConstructorMarker var8) {
      if ((var7 & 2) != 0) {
         var2 = FileWalkDirection.TOP_DOWN;
      }

      if ((var7 & 32) != 0) {
         var6 = Integer.MAX_VALUE;
      }

      this(var1, var2, var3, var4, var5, var6);
   }

   public FileTreeWalk(@NotNull File start, @NotNull FileWalkDirection direction) {
      Intrinsics.checkParameterIsNotNull(start, "start");
      Intrinsics.checkParameterIsNotNull(direction, "direction");
      this(start, direction, (Function1)null, (Function1)null, (Function2)null, 0, 32, (DefaultConstructorMarker)null);
   }

   // $FF: synthetic method
   public FileTreeWalk(File var1, FileWalkDirection var2, int var3, DefaultConstructorMarker var4) {
      if ((var3 & 2) != 0) {
         var2 = FileWalkDirection.TOP_DOWN;
      }

      this(var1, var2);
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"},
      d2 = {"Lkotlin/io/FileTreeWalk$WalkState;", "", "root", "Ljava/io/File;", "(Ljava/io/File;)V", "getRoot", "()Ljava/io/File;", "step", "kotlin-stdlib"}
   )
   private abstract static class WalkState {
      @NotNull
      private final File root;

      @Nullable
      public abstract File step();

      @NotNull
      public final File getRoot() {
         return this.root;
      }

      public WalkState(@NotNull File root) {
         Intrinsics.checkParameterIsNotNull(root, "root");
         super();
         this.root = root;
      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"},
      d2 = {"Lkotlin/io/FileTreeWalk$DirectoryState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootDir", "Ljava/io/File;", "(Ljava/io/File;)V", "kotlin-stdlib"}
   )
   private abstract static class DirectoryState extends FileTreeWalk.WalkState {
      public DirectoryState(@NotNull File rootDir) {
         Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
         super(rootDir);
         if (_Assertions.ENABLED) {
            boolean var2 = rootDir.isDirectory();
            boolean var3 = false;
            if (_Assertions.ENABLED && !var2) {
               int var4 = false;
               String var5 = "rootDir must be verified to be directory beforehand.";
               throw (Throwable)(new AssertionError(var5));
            }
         }

      }
   }

   @Metadata(
      mv = {1, 1, 15},
      bv = {1, 0, 3},
      k = 1,
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003\r\u000e\u000fB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0007\u001a\u00020\bH\u0014J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0002J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0082\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"},
      d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;", "Lkotlin/collections/AbstractIterator;", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk;)V", "state", "Ljava/util/ArrayDeque;", "Lkotlin/io/FileTreeWalk$WalkState;", "computeNext", "", "directoryState", "Lkotlin/io/FileTreeWalk$DirectoryState;", "root", "gotoNext", "BottomUpDirectoryState", "SingleFileState", "TopDownDirectoryState", "kotlin-stdlib"}
   )
   private final class FileTreeWalkIterator extends AbstractIterator<File> {
      private final ArrayDeque<FileTreeWalk.WalkState> state = new ArrayDeque();

      protected void computeNext() {
         File nextFile = this.gotoNext();
         if (nextFile != null) {
            this.setNext(nextFile);
         } else {
            this.done();
         }

      }

      private final FileTreeWalk.DirectoryState directoryState(File root) {
         // $FF: Couldn't be decompiled
      }

      private final File gotoNext() {
         while(true) {
            FileTreeWalk.WalkState var10000 = (FileTreeWalk.WalkState)this.state.peek();
            if (var10000 != null) {
               FileTreeWalk.WalkState topState = var10000;
               File file = topState.step();
               if (file == null) {
                  this.state.pop();
                  continue;
               }

               if (!Intrinsics.areEqual((Object)file, (Object)topState.getRoot()) && file.isDirectory() && this.state.size() < FileTreeWalk.this.maxDepth) {
                  this.state.push(this.directoryState(file));
                  continue;
               }

               return file;
            }

            return null;
         }
      }

      public FileTreeWalkIterator() {
         if (FileTreeWalk.this.start.isDirectory()) {
            this.state.push(this.directoryState(FileTreeWalk.this.start));
         } else if (FileTreeWalk.this.start.isFile()) {
            this.state.push(new FileTreeWalk.FileTreeWalkIterator.SingleFileState(FileTreeWalk.this.start));
         } else {
            this.done();
         }

      }

      @Metadata(
         mv = {1, 1, 15},
         bv = {1, 0, 3},
         k = 1,
         d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\nX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$BottomUpDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "failed", "", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "step", "kotlin-stdlib"}
      )
      private final class BottomUpDirectoryState extends FileTreeWalk.DirectoryState {
         private boolean rootVisited;
         private File[] fileList;
         private int fileIndex;
         private boolean failed;

         @Nullable
         public File step() {
            Function1 var10000;
            Unit var3;
            if (!this.failed && this.fileList == null) {
               var10000 = FileTreeWalk.this.onEnter;
               if (var10000 != null) {
                  if (!(Boolean)var10000.invoke(this.getRoot())) {
                     return null;
                  }
               }

               this.fileList = this.getRoot().listFiles();
               if (this.fileList == null) {
                  Function2 var2 = FileTreeWalk.this.onFail;
                  if (var2 != null) {
                     var3 = (Unit)var2.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), (File)null, "Cannot list files in a directory", 2, (DefaultConstructorMarker)null));
                  }

                  this.failed = true;
               }
            }

            if (this.fileList != null) {
               int var4 = this.fileIndex;
               File[] var10001 = this.fileList;
               if (var10001 == null) {
                  Intrinsics.throwNpe();
               }

               if (var4 < var10001.length) {
                  File[] var5 = this.fileList;
                  if (var5 == null) {
                     Intrinsics.throwNpe();
                  }

                  int var1;
                  this.fileIndex = (var1 = this.fileIndex) + 1;
                  return var5[var1];
               }
            }

            if (!this.rootVisited) {
               this.rootVisited = true;
               return this.getRoot();
            } else {
               var10000 = FileTreeWalk.this.onLeave;
               if (var10000 != null) {
                  var3 = (Unit)var10000.invoke(this.getRoot());
               }

               return null;
            }
         }

         public BottomUpDirectoryState(@NotNull File rootDir) {
            Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
            super(rootDir);
         }
      }

      @Metadata(
         mv = {1, 1, 15},
         bv = {1, 0, 3},
         k = 1,
         d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$TopDownDirectoryState;", "Lkotlin/io/FileTreeWalk$DirectoryState;", "rootDir", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "fileIndex", "", "fileList", "", "[Ljava/io/File;", "rootVisited", "", "step", "kotlin-stdlib"}
      )
      private final class TopDownDirectoryState extends FileTreeWalk.DirectoryState {
         private boolean rootVisited;
         private File[] fileList;
         private int fileIndex;

         @Nullable
         public File step() {
            Function1 var5;
            if (!this.rootVisited) {
               var5 = FileTreeWalk.this.onEnter;
               if (var5 != null) {
                  if (!(Boolean)var5.invoke(this.getRoot())) {
                     return null;
                  }
               }

               this.rootVisited = true;
               return this.getRoot();
            } else {
               Unit var3;
               if (this.fileList != null) {
                  int var10000 = this.fileIndex;
                  File[] var10001 = this.fileList;
                  if (var10001 == null) {
                     Intrinsics.throwNpe();
                  }

                  if (var10000 >= var10001.length) {
                     var5 = FileTreeWalk.this.onLeave;
                     if (var5 != null) {
                        var3 = (Unit)var5.invoke(this.getRoot());
                     }

                     return null;
                  }
               }

               label65: {
                  File[] var4;
                  if (this.fileList == null) {
                     this.fileList = this.getRoot().listFiles();
                     if (this.fileList == null) {
                        Function2 var2 = FileTreeWalk.this.onFail;
                        if (var2 != null) {
                           var3 = (Unit)var2.invoke(this.getRoot(), new AccessDeniedException(this.getRoot(), (File)null, "Cannot list files in a directory", 2, (DefaultConstructorMarker)null));
                        }
                     }

                     if (this.fileList == null) {
                        break label65;
                     }

                     var4 = this.fileList;
                     if (var4 == null) {
                        Intrinsics.throwNpe();
                     }

                     if (var4.length == 0) {
                        break label65;
                     }
                  }

                  var4 = this.fileList;
                  if (var4 == null) {
                     Intrinsics.throwNpe();
                  }

                  int var1;
                  this.fileIndex = (var1 = this.fileIndex) + 1;
                  return var4[var1];
               }

               var5 = FileTreeWalk.this.onLeave;
               if (var5 != null) {
                  var3 = (Unit)var5.invoke(this.getRoot());
               }

               return null;
            }
         }

         public TopDownDirectoryState(@NotNull File rootDir) {
            Intrinsics.checkParameterIsNotNull(rootDir, "rootDir");
            super(rootDir);
         }
      }

      @Metadata(
         mv = {1, 1, 15},
         bv = {1, 0, 3},
         k = 1,
         d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\b"},
         d2 = {"Lkotlin/io/FileTreeWalk$FileTreeWalkIterator$SingleFileState;", "Lkotlin/io/FileTreeWalk$WalkState;", "rootFile", "Ljava/io/File;", "(Lkotlin/io/FileTreeWalk$FileTreeWalkIterator;Ljava/io/File;)V", "visited", "", "step", "kotlin-stdlib"}
      )
      private final class SingleFileState extends FileTreeWalk.WalkState {
         private boolean visited;

         @Nullable
         public File step() {
            if (this.visited) {
               return null;
            } else {
               this.visited = true;
               return this.getRoot();
            }
         }

         public SingleFileState(@NotNull File rootFile) {
            Intrinsics.checkParameterIsNotNull(rootFile, "rootFile");
            super(rootFile);
            if (_Assertions.ENABLED) {
               boolean var3 = rootFile.isFile();
               boolean var4 = false;
               if (_Assertions.ENABLED && !var3) {
                  int var5 = false;
                  String var6 = "rootFile must be verified to be file beforehand.";
                  throw (Throwable)(new AssertionError(var6));
               }
            }

         }
      }
   }
}
