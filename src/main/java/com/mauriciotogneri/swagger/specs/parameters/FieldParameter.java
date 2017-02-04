package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.specs.Schema;
import com.mauriciotogneri.swagger.utils.Annotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldParameter
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
            Annotations annotations = new Annotations(field);

            String name = field.getName();
            Boolean optional = optional(field);
            Schema schema = Schema.fromClass(field.getType());
            String defaultValue = annotations.defaultValue();
            String description = annotations.description();

            parameters.add(new SwaggerParameter(
                    name,
                    kind,
                    schema,
                    defaultValue,
                    !optional,
                    description));
        }

        return parameters;
    }

    protected Boolean optional(Field field)
    {
        return field.isAnnotationPresent(Optional.class);
    }
}