package com.mauriciotogneri.swagger.specs;

import com.google.gson.JsonObject;
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
        classes.put(name, clazz);
    }

    public SwaggerDefinitions swaggerDefinitions()
    {
        SwaggerDefinitions definitions = new SwaggerDefinitions();

        for (Entry<String, Class<?>> entry : classes.entrySet())
        {
            definitions.put(entry.getKey(), new JsonObject()); // TODO
        }

        return definitions;
    }
}