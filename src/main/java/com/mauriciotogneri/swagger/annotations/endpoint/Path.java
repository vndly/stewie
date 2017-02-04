package com.mauriciotogneri.swagger.annotations.endpoint;

import com.mauriciotogneri.swagger.types.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(ElementType.TYPE)
public @interface Path
{
    Method method();

    String path();
}