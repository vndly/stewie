package com.mauriciotogneri.swagger.model;

import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.specs.EntityType;

@SuppressWarnings("ALL")
public final class SwaggerItems
{
    private final String type;
    private final JsonObject schema;

    public SwaggerItems(Class<?> clazz)
    {
        if (EntityType.isBasicType(clazz))
        {
            EntityType entityType = EntityType.fromClass(clazz);

            this.type = entityType.type();
            this.schema = null;
        }
        else
        {
            this.type = null;
            this.schema = null; // TODO
        }
    }
}