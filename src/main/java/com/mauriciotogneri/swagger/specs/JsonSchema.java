package com.mauriciotogneri.swagger.specs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.model.SwaggerSchema;

import java.io.File;
import java.lang.reflect.Field;

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
            TypeDefinition typeDef = new TypeDefinition(field.getType());

            JsonObject fieldObject = new JsonObject();

            if (typeDef.isString())
            {
                fieldObject.addProperty("type", SwaggerSchema.TYPE_STRING);
            }
            else if (typeDef.isBoolean())
            {
                fieldObject.addProperty("type", SwaggerSchema.TYPE_BOOLEAN);
            }
            else if (typeDef.isInteger())
            {
                fieldObject.addProperty("type", SwaggerSchema.TYPE_INTEGER);
            }
            else if (typeDef.isNumber())
            {
                fieldObject.addProperty("type", SwaggerSchema.TYPE_NUMBER);
            }
            else if (typeDef.isDate())
            {
                fieldObject.addProperty("type", SwaggerSchema.TYPE_STRING);
                fieldObject.addProperty("format", "date-time");
            }
            else if (typeDef.isEnum())
            {
                Object[] constants = typeDef.enums();

                JsonArray values = new JsonArray();

                for (Object constant : constants)
                {
                    values.add(constant.toString());
                }

                fieldObject.addProperty("type", SwaggerSchema.TYPE_STRING);
                fieldObject.add("enum", values);
            }
            else if (typeDef.isArray())
            {
                Class<?> componentType = typeDef.componentType();

                fieldObject.addProperty("type", SwaggerSchema.TYPE_ARRAY);

                JsonSchema componentSchema = new JsonSchema(componentType);
                fieldObject.add("items", componentSchema.schema());
            }
            else
            {
                String className = typeDef.canonicalName();

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