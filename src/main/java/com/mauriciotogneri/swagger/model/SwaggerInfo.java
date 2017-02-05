package com.mauriciotogneri.swagger.model;

@SuppressWarnings("ALL")
public class SwaggerInfo
{
    public final String version;
    public final String title;

    public SwaggerInfo(String version, String title)
    {
        this.version = version;
        this.title = title;
    }
}