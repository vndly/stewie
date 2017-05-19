package com.mauriciotogneri.stewie.specs;

import com.mauriciotogneri.stewie.annotations.Response;

public class Result
{
    private final Integer code;
    private final String[] produces;
    private final Class<?> type;
    private final Class<?> headers;
    private final String description;

    public Result(Response responses)
    {
        this.code = responses.code();
        this.produces = responses.produces();
        this.type = responses.type();
        this.headers = responses.headers();
        this.description = responses.description();
    }

    public Integer code()
    {
        return code;
    }

    public String[] produces()
    {
        return produces;
    }

    public Class<?> type()
    {
        return type;
    }

    public Class<?> headers()
    {
        return headers;
    }

    public String description()
    {
        return description;
    }

    public static Result[] from(Response[] responses)
    {
        Result[] results = new Result[responses.length];

        for (int i = 0; i < responses.length; i++)
        {
            results[i] = new Result(responses[i]);
        }

        return results;
    }
}