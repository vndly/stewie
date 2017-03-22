package com.mauriciotogneri.stewie.specs;

import com.mauriciotogneri.stewie.model.SwaggerDefinitions;

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

    public void add(TypeDefinition typeDef)
    {
        if (!typeDef.isPrimitive())
        {
            if (typeDef.isArray())
            {
                for (Field field : typeDef.fields())
                {
                    add(new TypeDefinition(typeDef.componentType()));
                }
            }
            else
            {
                String className = typeDef.name();

                if (!classes.containsKey(className))
                {
                    classes.put(className, typeDef.clazz());

                    for (Field field : typeDef.fields())
                    {
                        add(new TypeDefinition(field.getType()));
                    }
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

            definitions.put(entry.getKey(), jsonSchema.schema(false));
        }

        return definitions;
    }
}