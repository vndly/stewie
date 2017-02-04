package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.model.SwaggerParameter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class UrlParameter extends BaseParameter
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
            parameters.add(parameter(field.getName(), "query", !field.isAnnotationPresent(Optional.class), field.getType(), defaultValue(field)));
        }

        return parameters;
    }
}