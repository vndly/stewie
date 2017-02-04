package com.mauriciotogneri.swagger.model;

import java.util.List;
import java.util.Map;

public final class Swagger
{
    public final String swagger;
    public final SwaggerInfo info;
    public final String host;
    public final String basePath;
    public final List<SwaggerTag> tags;
    public final List<String> schemes;
    public final Map<String, SwaggerPath> paths;
    public final SwaggerDefinitions definitions;

    public Swagger(SwaggerInfo info, String host, String basePath, List<String> schemes, List<SwaggerTag> tags, Map<String, SwaggerPath> paths, SwaggerDefinitions definitions)
    {
        this.swagger = "2.0";
        this.info = info;
        this.host = host;
        this.basePath = basePath;
        this.tags = tags;
        this.schemes = schemes;
        this.paths = paths;
        this.definitions = definitions;
    }
}