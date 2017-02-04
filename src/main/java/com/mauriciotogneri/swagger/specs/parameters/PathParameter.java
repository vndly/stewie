package com.mauriciotogneri.swagger.specs.parameters;

import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.model.SwaggerParameter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class PathParameter
{
    private final Class<?> clazz;

    public PathParameter(Class<?> clazz)
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
                    "path",
                    new JsonObject(),
                    true,
                    "");

            parameters.add(parameter);
        }

        return parameters;
    }
}