package com.mauriciotogneri.swagger.specs.parameters;

import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.model.SwaggerParameter;

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
        return new SwaggerParameter(
                "data",
                "body",
                new JsonObject(),
                true,
                "");
    }
}