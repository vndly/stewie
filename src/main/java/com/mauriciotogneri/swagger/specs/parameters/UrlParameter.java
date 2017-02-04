package com.mauriciotogneri.swagger.specs.parameters;

public final class UrlParameter extends FieldParameter
{
    public UrlParameter(Class<?> clazz)
    {
        super("query", clazz);
    }
}