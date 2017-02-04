package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.specs.Schema;

public final class DataParameter extends BaseParameter
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
        String name = "data";
        String type = "body";
        Schema schema = Schema.fromClass(clazz);
        String defaultValue = defaultValue(clazz.getAnnotation(Default.class));
        String description = description(clazz.getAnnotation(Description.class));

        return parameter(name, type, false, schema, defaultValue, description);
    }
}