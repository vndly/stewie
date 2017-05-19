package com.mauriciotogneri.stewie.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Response
{
    int code();

    String description();

    String[] produces() default {};

    Class<?> type() default Object.class;

    Class<?> headers() default Object.class;
}