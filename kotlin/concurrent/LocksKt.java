package kotlin.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 2,
   d1 = {"\u0000\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a&\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b¢\u0006\u0002\u0010\u0005\u001a&\u0010\u0006\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00072\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b¢\u0006\u0002\u0010\b\u001a&\u0010\t\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b¢\u0006\u0002\u0010\u0005¨\u0006\n"},
   d2 = {"read", "T", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "write", "kotlin-stdlib"}
)
@JvmName(
   name = "LocksKt"
)
public final class LocksKt {
   @InlineOnly
   private static final <T> T withLock(@NotNull Lock $this$withLock, Function0<? extends T> action) {
      int $i$f$withLock = 0;
      $this$withLock.lock();

      Object var3;
      try {
         var3 = action.invoke();
      } finally {
         InlineMarker.finallyStart(1);
         $this$withLock.unlock();
         InlineMarker.finallyEnd(1);
      }

      return var3;
   }

   @InlineOnly
   private static final <T> T read(@NotNull ReentrantReadWriteLock $this$read, Function0<? extends T> action) {
      int $i$f$read = 0;
      ReadLock rl = $this$read.readLock();
      rl.lock();

      Object var4;
      try {
         var4 = action.invoke();
      } finally {
         InlineMarker.finallyStart(1);
         rl.unlock();
         InlineMarker.finallyEnd(1);
      }

      return var4;
   }

   @InlineOnly
   private static final <T> T write(@NotNull ReentrantReadWriteLock $this$write, Function0<? extends T> action) {
      int $i$f$write = 0;
      ReadLock rl = $this$write.readLock();
      int readCount = $this$write.getWriteHoldCount() == 0 ? $this$write.getReadHoldCount() : 0;
      boolean var5 = false;
      boolean var6 = false;
      Object var15 = 0;

      for(int var7 = readCount; var15 < var7; ++var15) {
         int var9 = false;
         rl.unlock();
      }

      WriteLock wl = $this$write.writeLock();
      wl.lock();

      try {
         var15 = action.invoke();
      } finally {
         InlineMarker.finallyStart(1);
         boolean var16 = false;
         boolean var8 = false;
         int var17 = 0;

         for(int var18 = readCount; var17 < var18; ++var17) {
            int var11 = false;
            rl.lock();
         }

         wl.unlock();
         InlineMarker.finallyEnd(1);
      }

      return var15;
   }
}
