package com.mauriciotogneri.swagger.model;

import com.mauriciotogneri.swagger.specs.Annotations;
import com.mauriciotogneri.swagger.specs.Definitions;

import java.lang.reflect.Field;

@SuppressWarnings("ALL")
public class SwaggerResponse
{
    private final transient Integer code;
    private final SwaggerSchema schema;
    private final SwaggerHeaderResponseList headers;
    private final String description;

    public SwaggerResponse(Integer code, Class<?> clazz, Class<?> headers, String description, Definitions definitions)
    {
        this.code = code;
        this.schema = clazz.equals(Object.class) ? null : SwaggerSchema.fromClass(clazz, new Annotations(clazz), definitions);
        this.headers = headers.equals(Object.class) ? null : headers(headers, definitions);
        this.description = description;
    }

    public String code()
    {
        return String.valueOf(code);
    }

    private SwaggerHeaderResponseList headers(Class<?> headers, Definitions definitions)
    {
        SwaggerHeaderResponseList result = new SwaggerHeaderResponseList();

        for (Field field : headers.getDeclaredFields())
        {
            Annotations annotations = new Annotations(field);

            String name = annotations.name();
            SwaggerSchema schema = SwaggerSchema.fromClass(field.getType(), new Annotations(field), definitions);
            String description = annotations.description();

            result.put(name, new SwaggerHeaderResponse(schema.type(), schema.format(), description));
        }

        return result;
    }
}