package com.mauriciotogneri.swagger.model;

import com.google.gson.annotations.SerializedName;
import com.mauriciotogneri.swagger.specs.Schema;

@SuppressWarnings("ALL")
public final class SwaggerParameter
{
    private final String name;
    private final String in;
    private final Schema schema;
    private final String description;
    private final Boolean required;

    @SerializedName("default")
    private final String defaultValue;

    @SerializedName("enum")
    private final String[] enumValues;

    public SwaggerParameter(String name, String in, Schema schema, String defaultValue, Boolean required, String description)
    {
        this.name = name;
        this.in = in;
        this.schema = schema;
        this.description = description;
        this.required = required;
        this.enumValues = schema.enumValues();
        this.defaultValue = defaultValue;
    }
}