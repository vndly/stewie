package com.mauriciotogneri.swagger.model;

import org.joda.time.DateTime;

public final class SwaggerType
{
    private final String type;
    private final String format;
    private final String[] enumValues;
    private final String description;

    private SwaggerType(String type, String format, String[] enumValues, String description)
    {
        this.type = type;
        this.format = format;
        this.enumValues = enumValues;
        this.description = description;
    }

    public String type()
    {
        return type;
    }

    public String format()
    {
        return format;
    }

    public String[] enumValues()
    {
        return enumValues;
    }

    public String description()
    {
        return description;
    }

    public static SwaggerType integerType()
    {
        return new SwaggerType("integer", null, null, null);
    }

    public static SwaggerType longType()
    {
        return new SwaggerType("integer", null, null, null);
    }

    public static SwaggerType floatType()
    {
        return new SwaggerType("number", null, null, null);
    }

    public static SwaggerType doubleType()
    {
        return new SwaggerType("number", null, null, null);
    }

    public static SwaggerType stringType()
    {
        return new SwaggerType("string", null, null, null);
    }

    public static SwaggerType stringType(String[] values)
    {
        return new SwaggerType("string", null, values, null);
    }

    public static SwaggerType booleanType()
    {
        return new SwaggerType("boolean", null, null, null);
    }

    public static SwaggerType dateTimeType()
    {
        return new SwaggerType("string", "date-time", null, null);
    }

    public static SwaggerType fromClass(Class<?> clazz)
    {
        if (clazz.equals(String.class))
        {
            return SwaggerType.stringType();
        }
        else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
        {
            return SwaggerType.booleanType();
        }
        else if (clazz.equals(Integer.class) || clazz.equals(int.class))
        {
            return SwaggerType.integerType();
        }
        else if (clazz.equals(Long.class) || clazz.equals(long.class))
        {
            return SwaggerType.longType();
        }
        else if (clazz.equals(Float.class) || clazz.equals(float.class))
        {
            return SwaggerType.floatType();
        }
        else if (clazz.equals(Double.class) || clazz.equals(double.class))
        {
            return SwaggerType.doubleType();
        }
        else if (clazz.equals(DateTime.class))
        {
            return SwaggerType.dateTimeType();
        }
        else if (clazz.equals(Enum.class))
        {
            Object[] constants = clazz.getEnumConstants();

            String[] values = new String[constants.length];

            for (int i = 0; i < constants.length; i++)
            {
                values[i] = constants[i].toString();
            }

            return SwaggerType.stringType(values);
        }
        else
        {
            throw new RuntimeException(String.format("Invalid type: %s", clazz.getName()));
        }
    }
}