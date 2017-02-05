package com.mauriciotogneri.swagger.specs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Date;

public class JsonSchema
{
    private final Class<?> clazz;

    public JsonSchema(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public JsonObject schema()
    {
        JsonObject schema = new JsonObject();

        if (clazz.equals(File.class))
        {
            schema.addProperty("type", "file");
        }
        else if (clazz.isArray())
        {
            schema.addProperty("type", "array");
        }
        else
        {
            schema.addProperty("type", "object");
            schema.add("properties", properties());

            JsonArray required = required();

            if (required.size() > 0)
            {
                schema.add("required", required);
            }
        }

        return schema;
    }

    private JsonObject properties()
    {
        JsonObject properties = new JsonObject();

        for (Field field : clazz.getDeclaredFields())
        {
            String name = field.getName();
            Class<?> type = field.getType();
            JsonObject fieldObject = new JsonObject();

            if (type.equals(String.class))
            {
                fieldObject.addProperty("type", "string");
            }
            else if (type.equals(Boolean.class) || type.equals(boolean.class))
            {
                fieldObject.addProperty("type", "boolean");
            }
            else if (type.equals(Integer.class) || type.equals(int.class) || type.equals(Long.class) || type.equals(long.class))
            {
                fieldObject.addProperty("type", "integer");
            }
            else if (type.equals(Float.class) || type.equals(float.class) || type.equals(Double.class) || type.equals(double.class))
            {
                fieldObject.addProperty("type", "number");
            }
            else if (type.equals(Date.class))
            {
                fieldObject.addProperty("type", "string");
                fieldObject.addProperty("format", "date-time");
            }
            else if (type.isEnum())
            {
                Object[] constants = type.getEnumConstants();

                String[] values = new String[constants.length];

                for (int i = 0; i < constants.length; i++)
                {
                    values[i] = constants[i].toString();
                }

                fieldObject.addProperty("type", "string");
                //TODO fieldObject.addProperty("emum", "");
            }
            else if (type.isArray())
            {
                Class<?> componentType = type.getComponentType();

                //TODO fieldObject.addProperty("type", "");
            }
            else
            {
                String className = type.getCanonicalName();

                //TODO fieldObject.addProperty("$ref", "");
            }

            properties.add(name, fieldObject);
        }

        return properties;
    }

    private JsonArray required()
    {
        JsonArray required = new JsonArray();

        for (Field field : clazz.getDeclaredFields())
        {
            Annotations annotations = new Annotations(field);

            if (!annotations.optional())
            {
                required.add(field.getName());
            }
        }

        return required;
    }
}