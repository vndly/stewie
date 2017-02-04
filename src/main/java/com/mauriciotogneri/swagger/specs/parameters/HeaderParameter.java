package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.specs.Schema;
import com.mauriciotogneri.swagger.utils.Annotations;

import java.lang.reflect.Field;

public final class HeaderParameter
{
    private final String name;
    private final String[] value;
    private final Class<?> clazz;
    private final Boolean optional;
    private final String description;

    public HeaderParameter(Field field)
    {
        Annotations annotations = new Annotations(field);

        this.name = annotations.name();
        this.value = field.isAnnotationPresent(Default.class) ? field.getAnnotation(Default.class).value() : new String[0];
        this.clazz = field.getType();
        this.optional = annotations.optional();
        this.description = annotations.description();
    }

    public Boolean is(String type)
    {
        return type.equals(name);
    }

    public String[] value()
    {
        return value;
    }

    private String valueList()
    {
        return (value.length != 0) ? String.join("; ", value) : null;
    }

    public SwaggerParameter swaggerParameter()
    {
        return new SwaggerParameter(
                name,
                "header",
                Schema.fromClass(clazz),
                valueList(),
                !optional,
                description);
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