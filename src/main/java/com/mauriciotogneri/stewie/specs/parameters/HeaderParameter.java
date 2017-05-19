package com.mauriciotogneri.stewie.specs.parameters;

import com.mauriciotogneri.jsonschema.Annotations;
import com.mauriciotogneri.jsonschema.Definitions;
import com.mauriciotogneri.jsonschema.TypeDefinition;
import com.mauriciotogneri.jsonschema.annotations.Default;
import com.mauriciotogneri.stewie.model.SwaggerParameter;
import com.mauriciotogneri.stewie.model.SwaggerSchema;

import java.lang.reflect.Field;

public class HeaderParameter
{
    private final String name;
    private final String[] defaultValue;
    private final Class<?> clazz;
    private final Boolean optional;
    private final String description;

    public HeaderParameter(Field field)
    {
        Annotations annotations = new Annotations(field);

        this.name = annotations.name();
        this.defaultValue = field.isAnnotationPresent(Default.class) ? new String[] {field.getAnnotation(Default.class).value()} : new String[0];
        this.clazz = field.getType();
        this.optional = annotations.optional();
        this.description = annotations.description();
    }

    public Boolean is(String type)
    {
        return type.equals(name);
    }

    public String[] defaultValue()
    {
        return defaultValue;
    }

    private String valueList()
    {
        return (defaultValue.length != 0) ? String.join("; ", defaultValue) : null;
    }

    public SwaggerParameter swaggerParameter(Definitions definitions)
    {
        return new SwaggerParameter(
                name,
                "header",
                SwaggerSchema.from(new TypeDefinition(clazz), new Annotations(clazz), definitions),
                valueList(),
                !optional,
                description);
    }

    public static HeaderParameter[] from(Class<?> clazz)
    {
        if (!clazz.equals(Object.class))
        {
            Field[] fields = clazz.getDeclaredFields();

            HeaderParameter[] result = new HeaderParameter[fields.length];

            for (int i = 0; i < fields.length; i++)
            {
                result[i] = new HeaderParameter(fields[i]);
            }

            return result;
        }
        else
        {
            return new HeaderParameter[0];
        }
    }
}