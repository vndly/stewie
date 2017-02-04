package com.mauriciotogneri.swagger.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public final class SwaggerEndPoint
{
    private final String[] tags;
    private final String summary;
    private final String description;
    private final String[] consumes;
    private final String[] produces;
    private final List<SwaggerParameter> parameters;
    private final Map<String, SwaggerResponse> responses;

    public SwaggerEndPoint(String summary, String description, String tag, String[] consumes, String[] produces, List<SwaggerParameter> parameters, String code, SwaggerResponse response)
    {
        this.summary = summary;
        this.description = description;
        this.tags = new String[] {tag};
        this.consumes = (consumes.length != 0) ? consumes : null;
        this.produces = (produces.length != 0) ? produces : null;
        this.parameters = (!parameters.isEmpty()) ? parameters : null;
        this.responses = new HashMap<>();
        this.responses.put(code, response);
    }
}