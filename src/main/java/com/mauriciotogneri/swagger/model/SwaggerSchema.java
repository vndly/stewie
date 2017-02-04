package com.mauriciotogneri.swagger.model;

import java.util.Date;

public final class SwaggerSchema
{
    private final String type;
    private final String format;
    private final String[] enumValues;
    private final SwaggerSchema items;
    private final String $ref;

    // TODO
    // minimum
    // maximum
    // minLength
    // maxLength
    // pattern
    // minItems
    // maxItems

    private static final String TYPE_STRING = "string";
    private static final String TYPE_BOOLEAN = "boolean";
    private static final String TYPE_INTEGER = "integer";
    private static final String TYPE_NUMBER = "number";
    private static final String TYPE_ARRAY = "array";

    private SwaggerSchema(String type, String format, String[] enumValues, SwaggerSchema items, String ref)
    {
        this.type = type;
        this.format = format;
        this.enumValues = enumValues;
        this.items = items;
        this.$ref = (ref != null) ? String.format("#/definitions/%s", ref) : null;
    }

    public String type()
    {
        return type;
    }

    public String format()
    {
        return format;
    }

    public SwaggerSchema items()
    {
        return items;
    }

    public String[] enumValues()
    {
        return enumValues;
    }

    public static SwaggerSchema fromClass(Class<?> clazz)
    {
        if (clazz.equals(String.class))
        {
            return new SwaggerSchema(TYPE_STRING, null, null, null, null);
        }
        else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
        {
            return new SwaggerSchema(TYPE_BOOLEAN, null, null, null, null);
        }
        else if (clazz.equals(Integer.class) || clazz.equals(int.class) || clazz.equals(Long.class) || clazz.equals(long.class))
        {
            return new SwaggerSchema(TYPE_INTEGER, null, null, null, null);
        }
        else if (clazz.equals(Float.class) || clazz.equals(float.class) || clazz.equals(Double.class) || clazz.equals(double.class))
        {
            return new SwaggerSchema(TYPE_NUMBER, null, null, null, null);
        }
        else if (clazz.equals(Date.class))
        {
            // TODO: differentiate date from date-time

            return new SwaggerSchema(TYPE_STRING, "date-time", null, null, null);
        }
        else if (clazz.isEnum())
        {
            Object[] constants = clazz.getEnumConstants();

            String[] values = new String[constants.length];

            for (int i = 0; i < constants.length; i++)
            {
                values[i] = constants[i].toString();
            }

            return new SwaggerSchema(TYPE_STRING, null, values, null, null);
        }
        else if (clazz.isArray())
        {
            SwaggerSchema items = SwaggerSchema.fromClass(clazz.getComponentType());

            return new SwaggerSchema(TYPE_ARRAY, null, null, items, null);
        }
        else
        {
            return new SwaggerSchema(null, null, null, null, clazz.getCanonicalName());

            //throw new RuntimeException(String.format("Invalid type: %s", clazz.getName()));
        }
    }

    /*public static Boolean isBasicType(Class<?> clazz)
    {
        return (clazz.equals(String.class) ||
                (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) ||
                (clazz.equals(Integer.class) || clazz.equals(int.class)) ||
                (clazz.equals(Long.class) || clazz.equals(long.class)) ||
                (clazz.equals(Float.class) || clazz.equals(float.class)) ||
                (clazz.equals(Double.class) || clazz.equals(double.class)) ||
                (clazz.equals(Date.class)) ||
                (clazz.isEnum()) ||
                (clazz.isArray())
        );
    }*/
}