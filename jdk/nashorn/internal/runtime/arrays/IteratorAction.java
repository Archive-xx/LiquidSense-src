package jdk.nashorn.internal.runtime.arrays;

import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

public abstract class IteratorAction<T> {
   protected final Object self;
   protected Object thisArg;
   protected final Object callbackfn;
   protected T result;
   protected long index;
   private final ArrayLikeIterator<Object> iter;

   public IteratorAction(Object self, Object callbackfn, Object thisArg, T initialResult) {
      this(self, callbackfn, thisArg, initialResult, ArrayLikeIterator.arrayLikeIterator(self));
   }

   public IteratorAction(Object self, Object callbackfn, Object thisArg, T initialResult, ArrayLikeIterator<Object> iter) {
      this.self = self;
      this.callbackfn = callbackfn;
      this.result = initialResult;
      this.iter = iter;
      this.thisArg = thisArg;
   }

   protected void applyLoopBegin(ArrayLikeIterator<Object> iterator) {
   }

   public final T apply() {
      boolean strict = Bootstrap.isStrictCallable(this.callbackfn);
      this.thisArg = this.thisArg == ScriptRuntime.UNDEFINED && !strict ? Context.getGlobal() : this.thisArg;
      this.applyLoopBegin(this.iter);
      boolean reverse = this.iter.isReverse();

      while(this.iter.hasNext()) {
         Object val = this.iter.next();
         this.index = this.iter.nextIndex() + (long)(reverse ? 1 : -1);

         try {
            if (!this.forEach(val, (double)this.index)) {
               return this.result;
            }
         } catch (Error | RuntimeException var5) {
            throw var5;
         } catch (Throwable var6) {
            throw new RuntimeException(var6);
         }
      }

      return this.result;
   }

   protected abstract boolean forEach(Object var1, double var2) throws Throwable;
}
