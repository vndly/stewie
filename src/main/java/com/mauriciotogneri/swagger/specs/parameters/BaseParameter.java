package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.specs.Schema;

import java.lang.reflect.Field;

public class BaseParameter
{
    protected SwaggerParameter parameter(String name, String type, Boolean optional, Schema schema, String defaultValue, String description)
    {
        return new SwaggerParameter(
                name,
                type,
                schema,
                defaultValue,
                !optional,
                description);
    }

    protected String defaultValue(Field field)
    {
        return defaultValue(field.getAnnotation(Default.class));
    }

    protected String defaultValue(Default annotation)
    {
        return (annotation != null) ? String.join("; ", annotation.value()) : null;
    }

    protected String description(Field field)
    {
        Description description = field.getAnnotation(Description.class);

        return (description != null) ? description.value() : null;
    }

    protected String description(Description annotation)
    {
        return (annotation != null) ? annotation.value() : null;
    }
}