package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.SinceKotlin;

@Metadata(
   mv = {1, 1, 15},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002ø\u0001\u0000¢\u0006\u0004\b\t\u0010\nJ\u001b\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"},
   d2 = {"Lkotlin/time/TestClock;", "Lkotlin/time/AbstractLongClock;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(D)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}
)
@SinceKotlin(
   version = "1.3"
)
@ExperimentalTime
public final class TestClock extends AbstractLongClock {
   private long reading;

   protected long read() {
      return this.reading;
   }

   public final void plusAssign_LRDsOJo/* $FF was: plusAssign-LRDsOJo*/(double duration) {
      double delta = Duration.toDouble-impl(duration, this.getUnit());
      long longDelta = (long)delta;
      long var10001;
      if (longDelta != Long.MIN_VALUE && longDelta != Long.MAX_VALUE) {
         long newReading = this.reading + longDelta;
         if ((this.reading ^ longDelta) >= 0L && (this.reading ^ newReading) < 0L) {
            this.overflow-LRDsOJo(duration);
         }

         var10001 = newReading;
      } else {
         double newReading = (double)this.reading + delta;
         if (newReading > (double)Long.MAX_VALUE || newReading < (double)Long.MIN_VALUE) {
            this.overflow-LRDsOJo(duration);
         }

         var10001 = (long)newReading;
      }

      this.reading = var10001;
   }

   private final void overflow_LRDsOJo/* $FF was: overflow-LRDsOJo*/(double duration) {
      throw (Throwable)(new IllegalStateException("TestClock will overflow if its reading " + this.reading + "ns is advanced by " + Duration.toString-impl(duration) + '.'));
   }

   public TestClock() {
      super(TimeUnit.NANOSECONDS);
   }
}
