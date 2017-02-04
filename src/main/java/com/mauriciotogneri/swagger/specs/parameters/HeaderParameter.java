package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.annotations.endpoint.Name;
import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.specs.Schema;

import java.lang.reflect.Field;

public final class HeaderParameter extends BaseParameter
{
    private final String name;
    private final String[] value;
    private final Class<?> clazz;
    private final Boolean optional;
    private final String description;

    public HeaderParameter(Field field)
    {
        this.name = field.isAnnotationPresent(Name.class) ? field.getAnnotation(Name.class).value() : "";
        this.value = field.isAnnotationPresent(Default.class) ? field.getAnnotation(Default.class).value() : new String[0];
        this.clazz = field.getType();
        this.optional = field.isAnnotationPresent(Optional.class);
        this.description = field.isAnnotationPresent(Description.class) ? field.getAnnotation(Description.class).value() : null;
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
        return parameter(name, "header", optional, Schema.fromClass(clazz), valueList(), description);
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