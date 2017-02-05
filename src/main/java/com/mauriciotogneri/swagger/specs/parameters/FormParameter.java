package com.mauriciotogneri.swagger.specs.parameters;

public class FormParameter extends FieldParameter
{
    public FormParameter(Class<?> clazz)
    {
        super("formData", clazz);
    }
}