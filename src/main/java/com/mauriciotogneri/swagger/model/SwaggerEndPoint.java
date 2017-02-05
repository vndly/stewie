package com.mauriciotogneri.swagger.model;

import java.util.List;

@SuppressWarnings("ALL")
public class SwaggerEndPoint
{
    private final String[] tags;
    private final String summary;
    private final String description;
    private final String[] consumes;
    private final String[] produces;
    private final Boolean deprecated;
    private final List<SwaggerParameter> parameters;
    private final SwaggerResponseList responses;

    public SwaggerEndPoint(String summary, String description, Boolean deprecated, String tag, String[] consumes, String[] produces, List<SwaggerParameter> parameters, List<SwaggerResponse> responses)
    {
        this.summary = summary;
        this.description = description;
        this.deprecated = (deprecated) ? true : null;
        this.tags = new String[] {tag};
        this.consumes = (consumes.length != 0) ? consumes : null;
        this.produces = (produces.length != 0) ? produces : null;
        this.parameters = (!parameters.isEmpty()) ? parameters : null;
        this.responses = new SwaggerResponseList();

        for (SwaggerResponse response : responses)
        {
            this.responses.put(response.code(), response);
        }
    }
}