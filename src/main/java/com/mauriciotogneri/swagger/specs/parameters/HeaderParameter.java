package com.mauriciotogneri.swagger.specs.parameters;

import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Name;
import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.model.SwaggerParameter;

import java.lang.reflect.Field;

public final class HeaderParameter extends BaseParameter
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
        return (value.length != 0) ? String.join(";", value) : null;
    }

    public SwaggerParameter swaggerParameter()
    {
        return parameter(name, "header", optional, type, valueList());
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