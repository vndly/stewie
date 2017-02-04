package com.mauriciotogneri.swagger.model;

import com.google.gson.JsonObject;

public final class SwaggerResponse
{
    private final JsonObject schema;

    public SwaggerResponse(Class<?>[] classDef)
    {
        this.schema = null; // TODO new JsonSchemaPrinter().referenceObject(classDef);
    }

    public SwaggerResponse()
    {
        this.schema = new JsonObject();
        this.schema.addProperty("type", "string");
    }
}