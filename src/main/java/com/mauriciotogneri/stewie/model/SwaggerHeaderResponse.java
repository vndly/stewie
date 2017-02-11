package com.mauriciotogneri.stewie.model;

@SuppressWarnings("ALL")
public class SwaggerHeaderResponse
{
    private final String type;
    private final String format;
    private final String description;

    public SwaggerHeaderResponse(String type, String format, String description)
    {
        this.type = type;
        this.format = format;
        this.description = description;
    }
}