package com.mauriciotogneri.stewie.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
public class SwaggerParameter
{
    private final String name;
    private final String in;
    private final SwaggerSchema schema;
    private final String description;
    private final Boolean required;
    private final String type;
    private final String format;
    private final SwaggerSchema items;
    private final String pattern;
    private final Integer minimum;
    private final Integer maximum;
    private final Integer minLength;
    private final Integer maxLength;
    private final Integer minItems;
    private final Integer maxItems;

    @SerializedName("enum")
    private final String[] enums;

    @SerializedName("default")
    private final String defaultValue;

    public SwaggerParameter(String name, String in, SwaggerSchema schema, String defaultValue, Boolean required, String description)
    {
        this.name = name;
        this.in = in;
        this.description = description;
        this.required = required;
        this.type = schema.type();
        this.format = schema.format();
        this.items = schema.items();
        this.pattern = schema.pattern();
        this.minimum = schema.minimum();
        this.maximum = schema.maximum();
        this.minLength = schema.minLength();
        this.maxLength = schema.maxLength();
        this.minItems = schema.minItems();
        this.maxItems = schema.maxItems();
        this.schema = schema.schema();
        this.enums = schema.enums();
        this.defaultValue = defaultValue;
    }
}