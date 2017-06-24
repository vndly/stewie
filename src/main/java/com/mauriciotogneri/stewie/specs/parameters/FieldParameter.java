package com.mauriciotogneri.stewie.specs.parameters;

import com.mauriciotogneri.jsonschema.Annotations;
import com.mauriciotogneri.jsonschema.Definitions;
import com.mauriciotogneri.jsonschema.TypeDefinition;
import com.mauriciotogneri.stewie.model.SwaggerParameter;
import com.mauriciotogneri.stewie.model.SwaggerSchema;

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

        if (!clazz.equals(Object.class))
        {
            for (Field field : clazz.getFields())
            {
                TypeDefinition typeDef = new TypeDefinition(field.getType());
                Annotations annotations = new Annotations(field);

                String name = field.getName();
                Boolean optional = optional(annotations);
                SwaggerSchema schema = SwaggerSchema.from(typeDef, new Annotations(field), definitions);
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
        }

        return parameters;
    }

    protected Boolean optional(Annotations annotations)
    {
        return annotations.optional();
    }
}