package com.mauriciotogneri.swagger.specs.parameters;

import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.model.SwaggerType;

import java.lang.reflect.Field;
import java.util.Date;

public class BaseParameter
{
    protected SwaggerParameter parameter(String name, String type, Boolean optional, Class<?> clazz, String defaultValue, String description)
    {
        if (isBasicType(clazz))
        {
            return new SwaggerParameter(
                    name,
                    type,
                    SwaggerType.fromClass(clazz),
                    !optional,
                    defaultValue,
                    description);
        }
        else
        {
            return new SwaggerParameter(
                    name,
                    type,
                    schema(clazz),
                    !optional,
                    defaultValue,
                    description);
        }
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

    protected Boolean isBasicType(Class<?> clazz)
    {
        return (clazz.equals(String.class) ||
                (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) ||
                (clazz.equals(Integer.class) || clazz.equals(int.class)) ||
                (clazz.equals(Long.class) || clazz.equals(long.class)) ||
                (clazz.equals(Float.class) || clazz.equals(float.class)) ||
                (clazz.equals(Double.class) || clazz.equals(double.class)) ||
                (clazz.equals(Date.class)) ||
                (clazz.isEnum())
        );
    }

    protected JsonObject schema(Class<?> clazz)
    {
        return new JsonObject(); // TODO
    }
}