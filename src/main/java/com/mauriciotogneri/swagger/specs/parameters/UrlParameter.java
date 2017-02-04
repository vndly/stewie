package com.mauriciotogneri.swagger.specs.parameters;

import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.model.SwaggerParameter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class UrlParameter
{
    private final Class<?> clazz;

    public UrlParameter(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public List<SwaggerParameter> swaggerParameters()
    {
        List<SwaggerParameter> parameters = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields())
        {
            SwaggerParameter parameter = new SwaggerParameter(
                    field.getName(),
                    "query",
                    new JsonObject(),
                    !field.isAnnotationPresent(Optional.class),
                    "");

            parameters.add(parameter);
        }

        return parameters;
    }
}