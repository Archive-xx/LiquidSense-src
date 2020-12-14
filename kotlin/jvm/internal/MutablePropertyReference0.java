package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty0;
import kotlin.reflect.KProperty0;

public abstract class MutablePropertyReference0 extends MutablePropertyReference implements KMutableProperty0 {
   public MutablePropertyReference0() {
   }

   @SinceKotlin(
      version = "1.1"
   )
   public MutablePropertyReference0(Object receiver) {
      super(receiver);
   }

   protected KCallable computeReflected() {
      return Reflection.mutableProperty0(this);
   }

   public Object invoke() {
      return this.get();
   }

   public KProperty0.Getter getGetter() {
      return ((KMutableProperty0)this.getReflected()).getGetter();
   }

   public KMutableProperty0.Setter getSetter() {
      return ((KMutableProperty0)this.getReflected()).getSetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate() {
      return ((KMutableProperty0)this.getReflected()).getDelegate();
   }
}
