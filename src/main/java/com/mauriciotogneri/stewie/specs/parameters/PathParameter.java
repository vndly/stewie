package com.mauriciotogneri.stewie.specs.parameters;

import com.mauriciotogneri.stewie.specs.Annotations;

public class PathParameter extends FieldParameter
{
    public PathParameter(Class<?> clazz)
    {
        super("path", clazz);
    }

    @Override
    protected Boolean optional(Annotations annotations)
    {
        return false;
    }
}