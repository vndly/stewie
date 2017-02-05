package com.mauriciotogneri.swagger.specs;

import com.mauriciotogneri.swagger.model.SwaggerDefinitions;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Definitions
{
    private final Map<String, Class<?>> classes;

    public Definitions()
    {
        this.classes = new HashMap<>();
    }

    public void add(String name, Class<?> clazz)
    {
        if (!classes.containsKey(name))
        {
            classes.put(name, clazz);
            iterateObject(clazz);
        }
    }

    private void iterateObject(Class<?> root)
    {
        for (Field field : root.getDeclaredFields())
        {
            TypeDefinition typeDef = new TypeDefinition(field.getType());

            if (typeDef.isArray())
            {
                iterateObject(typeDef.componentType());
            }
            else if (!typeDef.isPrimitive())
            {
                String name = typeDef.name();

                if (!classes.containsKey(name))
                {
                    classes.put(name, typeDef.clazz());
                    iterateObject(typeDef.clazz());
                }
            }
        }
    }

    public SwaggerDefinitions swaggerDefinitions()
    {
        SwaggerDefinitions definitions = new SwaggerDefinitions();

        for (Entry<String, Class<?>> entry : classes.entrySet())
        {
            JsonSchema jsonSchema = new JsonSchema(entry.getValue());

            definitions.put(entry.getKey(), jsonSchema.schema());
        }

        return definitions;
    }
}