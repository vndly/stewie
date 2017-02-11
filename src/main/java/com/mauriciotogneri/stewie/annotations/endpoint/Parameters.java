package com.mauriciotogneri.stewie.annotations.endpoint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Parameters
{
    Class<?> header() default Object.class;

    Class<?> path() default Object.class;

    Class<?> url() default Object.class;

    Class<?> form() default Object.class;

    Class<?> data() default Object.class;
}