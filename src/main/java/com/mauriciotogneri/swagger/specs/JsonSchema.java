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
            Annotations annotations = new Annotations(field);
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
            else if (typeDef.isUri())
            {
                fieldObject.addProperty("type", SwaggerSchema.TYPE_STRING);
                fieldObject.addProperty("format", "uri");
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
                fieldObject.addProperty("$ref", String.format("#/definitions/%s", typeDef.name()));
            }

            applyAnnotations(fieldObject, annotations);

            properties.add(name, fieldObject);
        }

        return properties;
    }

    private void applyAnnotations(JsonObject json, Annotations annotations)
    {
        if (annotations.description() != null)
        {
            json.addProperty("description", annotations.description());
        }

        if (annotations.format() != null)
        {
            json.addProperty("format", annotations.format());
        }

        if (annotations.pattern() != null)
        {
            json.addProperty("pattern", annotations.pattern());
        }

        if (annotations.minimum() != null)
        {
            json.addProperty("minimum", annotations.maximum());
        }

        if (annotations.maximum() != null)
        {
            json.addProperty("maximum", annotations.maximum());
        }

        if (annotations.minLength() != null)
        {
            json.addProperty("minLength", annotations.minLength());
        }

        if (annotations.maxLength() != null)
        {
            json.addProperty("maxLength", annotations.maxLength());
        }

        if (annotations.minItems() != null)
        {
            json.addProperty("minItems", annotations.minItems());
        }

        if (annotations.maxItems() != null)
        {
            json.addProperty("maxItems", annotations.maxItems());
        }
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