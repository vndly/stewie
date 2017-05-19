package com.mauriciotogneri.stewie.model;

import com.mauriciotogneri.jsonschema.Annotations;
import com.mauriciotogneri.jsonschema.Definitions;
import com.mauriciotogneri.jsonschema.TypeDefinition;

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
        this.schema = clazz.equals(Object.class) ? null : SwaggerSchema.from(new TypeDefinition(clazz), new Annotations(clazz), definitions);
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
            TypeDefinition typeDef = new TypeDefinition(field.getType());
            Annotations annotations = new Annotations(field);

            String name = annotations.name();
            SwaggerSchema schema = SwaggerSchema.from(typeDef, new Annotations(field), definitions);
            String description = annotations.description();

            result.put(name, new SwaggerHeaderResponse(schema.type(), schema.format(), description));
        }

        return result;
    }
}