package com.mauriciotogneri.swagger.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

@SuppressWarnings("ALL")
public final class SwaggerSchema
{
    private final String type;
    private final String format;
    private final SwaggerSchema items;
    private final String $ref;

    @SerializedName("enum")
    private final String[] enumValues;

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

    public static SwaggerSchema fromClass(Class<?> clazz)
    {
        if (clazz.equals(String.class))
        {
            return new Builder().type(TYPE_STRING).build();
        }
        else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class))
        {
            return new Builder().type(TYPE_BOOLEAN).build();
        }
        else if (clazz.equals(Integer.class) || clazz.equals(int.class) || clazz.equals(Long.class) || clazz.equals(long.class))
        {
            return new Builder().type(TYPE_INTEGER).build();
        }
        else if (clazz.equals(Float.class) || clazz.equals(float.class) || clazz.equals(Double.class) || clazz.equals(double.class))
        {
            return new Builder().type(TYPE_NUMBER).build();
        }
        else if (clazz.equals(Date.class))
        {
            return new Builder().type(TYPE_STRING).format("date-time").build();
        }
        else if (clazz.isEnum())
        {
            Object[] constants = clazz.getEnumConstants();

            String[] values = new String[constants.length];

            for (int i = 0; i < constants.length; i++)
            {
                values[i] = constants[i].toString();
            }

            return new Builder().type(TYPE_STRING).enumValues(values).build();
        }
        else if (clazz.isArray())
        {
            SwaggerSchema items = SwaggerSchema.fromClass(clazz.getComponentType());

            return new Builder().type(TYPE_ARRAY).items(items).build();
        }
        else
        {
            return new Builder().ref(clazz.getCanonicalName()).build();
        }
    }

    static class Builder
    {
        private String type;
        private String format;
        private String[] enumValues;
        private SwaggerSchema items;
        private String ref;

        public Builder type(String type)
        {
            this.type = type;

            return this;
        }

        public Builder format(String format)
        {
            this.format = format;

            return this;
        }

        public Builder enumValues(String[] enumValues)
        {
            this.enumValues = enumValues;

            return this;
        }

        public Builder items(SwaggerSchema items)
        {
            this.items = items;

            return this;
        }

        public Builder ref(String ref)
        {
            this.ref = ref;

            return this;
        }

        public SwaggerSchema build()
        {
            return new SwaggerSchema(type, format, enumValues, items, ref);
        }
    }
}