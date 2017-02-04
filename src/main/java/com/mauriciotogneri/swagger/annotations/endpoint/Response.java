package com.mauriciotogneri.swagger.annotations.endpoint;

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

    String[] produces() default {};

    Class<?>[] type() default {};

    Class<?> headers() default Object.class;

    Error[] errors() default {};
}