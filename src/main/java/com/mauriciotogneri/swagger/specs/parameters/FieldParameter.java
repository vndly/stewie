package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.model.SwaggerSchema;
import com.mauriciotogneri.swagger.specs.Definitions;
import com.mauriciotogneri.swagger.specs.Annotations;

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

    public List<SwaggerParameter> swaggerParameters(Definitions definitions)
    {
        List<SwaggerParameter> parameters = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields())
        {
            Annotations annotations = new Annotations(field);

            String name = field.getName();
            Boolean optional = optional(annotations);
            SwaggerSchema schema = SwaggerSchema.fromClass(field.getType(), new Annotations(field), definitions);
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

    protected Boolean optional(Annotations annotations)
    {
        return annotations.optional();
    }
}