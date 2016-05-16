package com.mysimplework.core.annotation;

import com.mysimplework.core.enums.OperatorEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dzhao on 22/09/2015.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MySearch {
    String name() default "";
    String type() default "String";
    String alias() default "";
    OperatorEnum opertor() default OperatorEnum.EQUALS;
    boolean pageable() default false;
    boolean sortable() default false;
}
