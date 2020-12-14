package kotlin.jvm.internal;

import kotlin.SinceKotlin;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty1;

public abstract class PropertyReference1 extends PropertyReference implements KProperty1 {
   public PropertyReference1() {
   }

   @SinceKotlin(
      version = "1.1"
   )
   public PropertyReference1(Object receiver) {
      super(receiver);
   }

   protected KCallable computeReflected() {
      return Reflection.property1(this);
   }

   public Object invoke(Object receiver) {
      return this.get(receiver);
   }

   public KProperty1.Getter getGetter() {
      return ((KProperty1)this.getReflected()).getGetter();
   }

   @SinceKotlin(
      version = "1.1"
   )
   public Object getDelegate(Object receiver) {
      return ((KProperty1)this.getReflected()).getDelegate(receiver);
   }
}
