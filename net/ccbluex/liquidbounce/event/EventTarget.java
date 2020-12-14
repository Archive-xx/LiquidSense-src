package net.ccbluex.liquidbounce.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Target;

@Target(
   allowedTargets = {AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER}
)
@Retention(RetentionPolicy.RUNTIME)
@Metadata(
   mv = {1, 1, 16},
   bv = {1, 0, 3},
   k = 1,
   d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0087\u0002\u0018\u00002\u00020\u0001B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004¨\u0006\u0005"},
   d2 = {"Lnet/ccbluex/liquidbounce/event/EventTarget;", "", "ignoreCondition", "", "()Z", "LiquidSense"}
)
@java.lang.annotation.Target({ElementType.METHOD})
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
public @interface EventTarget {
   boolean ignoreCondition() default false;
}
