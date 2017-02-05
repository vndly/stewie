package com.mauriciotogneri.swagger.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class SwaggerParameter
{
    private final String name;
    private final String in;
    private final SwaggerSchema schema;
    private final String description;
    private final Boolean required;

    @SerializedName("default")
    private final String defaultValue;

    public SwaggerParameter(String name, String in, SwaggerSchema schema, String defaultValue, Boolean required, String description)
    {
        this.name = name;
        this.in = in;
        this.schema = schema;
        this.description = description;
        this.required = required;
        this.defaultValue = defaultValue;
    }
}