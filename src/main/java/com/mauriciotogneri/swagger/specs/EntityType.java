package com.mauriciotogneri.swagger.specs;

import com.mauriciotogneri.swagger.model.SwaggerItems;

import java.util.Date;

public final class EntityType
{
    private final String type;
    private final String format;
    private final String[] enumValues;
    private final SwaggerItems items;

    private EntityType(String type, String format, String[] enumValues, SwaggerItems items)
    {
        this.type = type;
        this.format = format;
        this.enumValues = enumValues;
        this.items = items;
    }

    public String type()
    {
        return type;
    }

    public String format()
    {
        return format;
    }

    public SwaggerItems items()
    {
        return items;
    }

    public String[] enumValues()
    {
        return enumValues;
    }

    public static EntityType fromClass(Class<?> clazz)
    {
        if (clazz.equals(String.class))
        {
            return new EntityType("string", null, null, null);
        }
        else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
        {
            return new EntityType("boolean", null, null, null);
        }
        else if (clazz.equals(Integer.class) || clazz.equals(int.class))
        {
            return new EntityType("integer", null, null, null);
        }
        else if (clazz.equals(Long.class) || clazz.equals(long.class))
        {
            return new EntityType("integer", null, null, null);
        }
        else if (clazz.equals(Float.class) || clazz.equals(float.class))
        {
            return new EntityType("number", null, null, null);
        }
        else if (clazz.equals(Double.class) || clazz.equals(double.class))
        {
            return new EntityType("number", null, null, null);
        }
        else if (clazz.equals(Date.class))
        {
            return new EntityType("string", "date-time", null, null);
        }
        else if (clazz.isEnum())
        {
            Object[] constants = clazz.getEnumConstants();

            String[] values = new String[constants.length];

            for (int i = 0; i < constants.length; i++)
            {
                values[i] = constants[i].toString();
            }

            return new EntityType("string", null, values, null);
        }
        else if (clazz.isArray())
        {
            SwaggerItems items = new SwaggerItems(clazz.getComponentType());

            return new EntityType("array", null, null, items);
        }
        else
        {
            throw new RuntimeException(String.format("Invalid type: %s", clazz.getName()));
        }
    }

    public static Boolean isBasicType(Class<?> clazz)
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
    }
}