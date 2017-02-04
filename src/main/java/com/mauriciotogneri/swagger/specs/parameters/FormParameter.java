package com.mauriciotogneri.swagger.specs.parameters;

public final class FormParameter extends FieldParameter
{
    public FormParameter(Class<?> clazz)
    {
        super("formData", clazz);
    }
}