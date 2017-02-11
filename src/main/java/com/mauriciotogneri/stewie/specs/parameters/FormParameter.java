package com.mauriciotogneri.stewie.specs.parameters;

public class FormParameter extends FieldParameter
{
    public FormParameter(Class<?> clazz)
    {
        super("formData", clazz);
    }
}