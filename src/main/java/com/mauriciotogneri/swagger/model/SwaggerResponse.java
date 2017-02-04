package com.mauriciotogneri.swagger.model;

import com.mauriciotogneri.swagger.specs.Definitions;
import com.mauriciotogneri.swagger.specs.Annotations;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public final class SwaggerResponse
{
    private final transient Integer code;
    private final SwaggerSchema schema;
    private final Map<String, SwaggerHeaderResponse> headers;
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

    private Map<String, SwaggerHeaderResponse> headers(Class<?> headers, Definitions definitions)
    {
        Map<String, SwaggerHeaderResponse> result = new HashMap<>();

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