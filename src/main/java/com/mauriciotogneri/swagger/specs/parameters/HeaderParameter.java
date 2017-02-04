package com.mauriciotogneri.swagger.specs.parameters;

import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Name;
import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.model.SwaggerParameter;

import java.lang.reflect.Field;

public final class HeaderParameter
{
    private final String name;
    private final String[] value;
    private final Class<?> type;
    private final Boolean optional;

    public HeaderParameter(Field field)
    {
        this.name = field.isAnnotationPresent(Name.class) ? field.getAnnotation(Name.class).value() : "";
        this.value = field.isAnnotationPresent(Default.class) ? field.getAnnotation(Default.class).value() : new String[0];
        this.type = field.getType();
        this.optional = field.isAnnotationPresent(Optional.class);
    }

    public String name()
    {
        return name;
    }

    public String[] value()
    {
        return value;
    }

    public SwaggerParameter swaggerParameter()
    {
        return new SwaggerParameter(
                name,
                "header",
                new JsonObject(),
                !optional,
                "");
    }

    public static HeaderParameter[] from(Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();

        HeaderParameter[] result = new HeaderParameter[fields.length];

        for (int i = 0; i < fields.length; i++)
        {
            result[i] = new HeaderParameter(fields[i]);
        }

        return result;
    }
}