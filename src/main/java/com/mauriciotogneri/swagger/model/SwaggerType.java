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

    private static SwaggerType integerType(String description)
    {
        return new SwaggerType("integer", null, null, description);
    }

    private static SwaggerType longType(String description)
    {
        return new SwaggerType("integer", null, null, description);
    }

    private static SwaggerType floatType(String description)
    {
        return new SwaggerType("number", null, null, description);
    }

    private static SwaggerType doubleType(String description)
    {
        return new SwaggerType("number", null, null, description);
    }

    private static SwaggerType stringType(String description)
    {
        return new SwaggerType("string", null, null, description);
    }

    private static SwaggerType enumType(String[] values, String description)
    {
        return new SwaggerType("string", null, values, description);
    }

    private static SwaggerType booleanType(String description)
    {
        return new SwaggerType("boolean", null, null, description);
    }

    private static SwaggerType dateTimeType(String description)
    {
        return new SwaggerType("string", "date-time", null, description);
    }

    public static SwaggerType fromClass(Class<?> clazz, String description)
    {
        if (clazz.equals(String.class))
        {
            return SwaggerType.stringType(description);
        }
        else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
        {
            return SwaggerType.booleanType(description);
        }
        else if (clazz.equals(Integer.class) || clazz.equals(int.class))
        {
            return SwaggerType.integerType(description);
        }
        else if (clazz.equals(Long.class) || clazz.equals(long.class))
        {
            return SwaggerType.longType(description);
        }
        else if (clazz.equals(Float.class) || clazz.equals(float.class))
        {
            return SwaggerType.floatType(description);
        }
        else if (clazz.equals(Double.class) || clazz.equals(double.class))
        {
            return SwaggerType.doubleType(description);
        }
        else if (clazz.equals(DateTime.class))
        {
            return SwaggerType.dateTimeType(description);
        }
        else if (clazz.equals(Enum.class))
        {
            Object[] constants = clazz.getEnumConstants();

            String[] values = new String[constants.length];

            for (int i = 0; i < constants.length; i++)
            {
                values[i] = constants[i].toString();
            }

            return SwaggerType.enumType(values, description);
        }
        else
        {
            throw new RuntimeException(String.format("Invalid type: %s", clazz.getName()));
        }
    }
}