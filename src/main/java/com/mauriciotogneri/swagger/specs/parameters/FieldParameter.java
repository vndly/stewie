package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.model.SwaggerParameter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldParameter extends BaseParameter
{
    private final String kind;
    private final Class<?> clazz;

    public FieldParameter(String kind, Class<?> clazz)
    {
        this.kind = kind;
        this.clazz = clazz;
    }

    public List<SwaggerParameter> swaggerParameters()
    {
        List<SwaggerParameter> parameters = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields())
        {
            String name = field.getName();
            String type = kind;
            Boolean optional = optional(field);
            Class<?> clazz = field.getType();
            String defaultValue = defaultValue(field);
            String description = description(field);

            parameters.add(parameter(name, type, optional, clazz, defaultValue, description));
        }

        return parameters;
    }

    protected Boolean optional(Field field)
    {
        return field.isAnnotationPresent(Optional.class);
    }
}