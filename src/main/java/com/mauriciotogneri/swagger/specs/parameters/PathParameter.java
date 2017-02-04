package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.utils.Annotations;

public final class PathParameter extends FieldParameter
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