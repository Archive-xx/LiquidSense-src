package jdk.nashorn.internal.runtime.logging;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {
   String name() default "";
}
