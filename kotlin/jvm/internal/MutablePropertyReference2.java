package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KMutableProperty2;
import kotlin.reflect.KProperty2;

public abstract class MutablePropertyReference2 extends MutablePropertyReference implements KMutableProperty2 {
   protected KCallable computeReflected() {
      return Reflection.mutableProperty2(this);
   }

   public Object invoke(Object receiver1, Object receiver2) {
      return this.get(receiver1, receiver2);
   }

   public KProperty2.Getter getGetter() {
      return ((KMutableProperty2)this.getReflected()).getGetter();
   }

   public KMutableProperty2.Setter getSetter() {
      return ((KMutableProperty2)this.getReflected()).getSetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate(Object receiver1, Object receiver2) {
      return ((KMutableProperty2)this.getReflected()).getDelegate(receiver1, receiver2);
   }
}
