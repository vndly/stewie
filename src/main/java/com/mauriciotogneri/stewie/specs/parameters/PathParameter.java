package com.mauriciotogneri.stewie.specs.parameters;

import com.mauriciotogneri.jsonschema.Annotations;

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