package com.mauriciotogneri.swagger.model;

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

    public static SwaggerType sequenceType(String description)
    {
        return new SwaggerType("string", null, null, description);
    }

   /* public static SwaggerType fromClass(ClassDef classDef, FieldDef fieldDef)
    {
        if (classDef.isString())
        {
            return SwaggerType.stringType();
        }
        else if (classDef.isBoolean())
        {
            return SwaggerType.booleanType();
        }
        else if (classDef.isInteger())
        {
            return SwaggerType.integerType();
        }
        else if (classDef.isLong())
        {
            return SwaggerType.longType();
        }
        else if (classDef.isFloat())
        {
            return SwaggerType.floatType();
        }
        else if (classDef.isDouble())
        {
            return SwaggerType.doubleType();
        }
        else if (classDef.isDate())
        {
            return SwaggerType.dateTimeType();
        }
        else if (classDef.isType(Sequence.class))
        {
            ClassDef fieldClassDef = fieldDef.genericType();

            if (!fieldClassDef.isPrimitive())
            {
                List<String> constants = new ArrayList<>();

                for (Object constant : fieldClassDef.enumConstants())
                {
                    constants.add(constant.toString());
                }

                return SwaggerType.sequenceType(String.format("[%s]", ListHelper.join(constants, ",")));
            }
            else
            {
                return SwaggerType.stringType();
            }
        }
        else if (classDef.isEnum())
        {
            Object[] constants = classDef.enumConstants();

            String[] values = new String[constants.length];

            for (int i = 0; i < constants.length; i++)
            {
                values[i] = constants[i].toString();
            }

            return SwaggerType.stringType(values);
        }
        else
        {
            throw new RuntimeException(String.format("Invalid type: %s", classDef.name()));
        }
    }*/
}