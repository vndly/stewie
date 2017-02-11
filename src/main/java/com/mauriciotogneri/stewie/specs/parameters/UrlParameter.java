package com.mauriciotogneri.stewie.specs.parameters;

public class UrlParameter extends FieldParameter
{
    public UrlParameter(Class<?> clazz)
    {
        super("query", clazz);
    }
}