package com.mauriciotogneri.swagger.model;

import com.google.gson.JsonObject;

public final class SwaggerResponse
{
    private final transient Integer code;
    private final JsonObject schema;
    private final String description;

    public SwaggerResponse(Integer code, Class<?>[] classDef, Class<?> headers, String description)
    {
        this.code = code;
        this.schema = null; // TODO new JsonSchemaPrinter().referenceObject(classDef);
        this.description = description;
    }

    public String code()
    {
        return String.valueOf(code);
    }
}