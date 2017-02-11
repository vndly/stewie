package com.mauriciotogneri.stewie.annotations.endpoint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EndPoint
{
    String method();

    String path();

    String description() default "";

    boolean deprecated() default false;
}