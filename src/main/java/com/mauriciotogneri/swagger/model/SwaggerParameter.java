package com.mauriciotogneri.swagger.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public final class SwaggerParameter
{
    private final String name;
    private final String in;
    private final String type;
    private final String format;
    private final JsonObject schema;
    private final String description;
    private final boolean required;

    @SerializedName("default")
    private final String defaultValue;

    @SerializedName("enum")
    private final String[] enumValues;

    public SwaggerParameter(String name, String in, JsonObject schema, boolean required, String defaultValue, String description)
    {
        this.name = name;
        this.in = in;
        this.type = null;
        this.format = null;
        this.schema = schema;
        this.enumValues = null;
        this.description = description;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    public SwaggerParameter(String name, String in, SwaggerType type, boolean required, String defaultValue, String description)
    {
        this.name = name;
        this.in = in;
        this.type = type.type();
        this.format = type.format();
        this.schema = null;
        this.enumValues = type.enumValues();
        this.description = description;
        this.required = required;
        this.defaultValue = defaultValue;
    }
}