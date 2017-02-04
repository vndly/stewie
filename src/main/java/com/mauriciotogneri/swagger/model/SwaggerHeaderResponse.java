package com.mauriciotogneri.swagger.model;

@SuppressWarnings("ALL")
public final class SwaggerHeaderResponse
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