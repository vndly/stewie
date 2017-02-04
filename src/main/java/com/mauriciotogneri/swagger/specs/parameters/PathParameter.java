package com.mauriciotogneri.swagger.specs.parameters;

import java.lang.reflect.Field;

public final class PathParameter extends FieldParameter
{
    public PathParameter(Class<?> clazz)
    {
        super("path", clazz);
    }

    @Override
    protected Boolean optional(Field field)
    {
        return false;
    }
}