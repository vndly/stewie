package com.mauriciotogneri.swagger.specs;

import com.mauriciotogneri.swagger.model.SwaggerDefinitions;

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
            // TODO: iterate recursively to find nested types
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