package com.mauriciotogneri.swagger.specs;

import com.mauriciotogneri.swagger.annotations.endpoint.Error;
import com.mauriciotogneri.swagger.annotations.endpoint.Response;

public class Result
{
    private final Integer code;
    private final String[] produces;
    private final Class<?>[] types;
    private final Class<?> headers;
    private final Error[] errors;

    public Result(Response response)
    {
        this.code = response.code();
        this.produces = response.produces();
        this.types = response.type();
        this.headers = response.headers();
        this.errors = response.errors();
    }

    public Integer code()
    {
        return code;
    }

    public String[] produces()
    {
        return produces;
    }

    public Boolean isEmpty()
    {
        return (types.length == 0);
    }

    public Class<?>[] types()
    {
        return types;
    }
}