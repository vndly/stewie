package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.model.SwaggerSchema;
import com.mauriciotogneri.swagger.utils.Annotations;

public final class DataParameter
{
    private final Class<?> clazz;

    public DataParameter(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public Boolean isPresent()
    {
        return !clazz.equals(Object.class);
    }

    public Class<?> type()
    {
        return clazz;
    }

    public SwaggerParameter swaggerParameter()
    {
        Annotations annotations = new Annotations(clazz);

        String name = "data";
        String type = "body";
        SwaggerSchema schema = SwaggerSchema.fromClass(clazz);
        String defaultValue = annotations.defaultValue();
        String description = annotations.description();

        return new SwaggerParameter(
                name,
                type,
                schema,
                defaultValue,
                true,
                description);
    }
}