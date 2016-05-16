package com.mysimplework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dzhao on 22/09/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MyRepository {
    String modelPackageName() default "";
    String modelSimpleName() default "";
    String packageName() default "";
    String simpleName() default "";
    String target() default "target/generated-sources/apt/";
}
