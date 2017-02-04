package com.mauriciotogneri.swagger.specs;

import com.mauriciotogneri.swagger.annotations.endpoint.EndPoint;
import com.mauriciotogneri.swagger.annotations.endpoint.Parameters;
import com.mauriciotogneri.swagger.annotations.endpoint.Responses;
import com.mauriciotogneri.swagger.model.SwaggerEndPoint;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.model.SwaggerResponse;
import com.mauriciotogneri.swagger.specs.parameters.DataParameter;
import com.mauriciotogneri.swagger.specs.parameters.HeaderParameter;
import com.mauriciotogneri.swagger.specs.parameters.PathParameter;
import com.mauriciotogneri.swagger.specs.parameters.UrlParameter;
import com.mauriciotogneri.swagger.types.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class EndPointInfo
{
    private final String parent;
    private final String name;
    private final String path;
    private final String method;
    private final String description;
    private final Boolean deprecated;
    private final HeaderParameter[] headerParameters;
    private final PathParameter pathParameters;
    private final UrlParameter urlParameters;
    private final DataParameter dataParameter;
    private final Result[] results;

    public EndPointInfo(String parent, File file)
    {
        Class<?> clazz = getClass(file);

        EndPoint endPoint = clazz.getAnnotation(EndPoint.class);
        Parameters parameters = clazz.getAnnotation(Parameters.class);
        Responses responses = clazz.getAnnotation(Responses.class);

        this.parent = parent;
        this.name = file.getName().replace(".java", "");
        this.path = endPoint.path();
        this.method = endPoint.method();
        this.description = endPoint.description().isEmpty() ? null : endPoint.description();
        this.deprecated = endPoint.deprecated();
        this.headerParameters = HeaderParameter.from(parameters.header());
        this.pathParameters = new PathParameter(parameters.path());
        this.urlParameters = new UrlParameter(parameters.url());
        this.dataParameter = new DataParameter(parameters.data());
        this.results = Result.from(responses.value());
    }

    public String name()
    {
        return name;
    }

    public String path()
    {
        return path;
    }

    public String method()
    {
        return method;
    }

    private Class<?> getClass(File file)
    {
        String folder = new File(".").getAbsolutePath().replace(".", "");
        String base = String.format("%ssrc/main/java/", folder);

        String classPath = file.getAbsolutePath().replace(base, "").replace("/", ".").replace(".java", "");

        try
        {
            return Class.forName(classPath);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(String.format("Class not found: %s", classPath));
        }
    }

    public SwaggerEndPoint swaggerEndPoint()
    {
        String[] consumes = consumes();
        String[] produces = produces();
        List<SwaggerParameter> parameters = parameters(headerParameters, pathParameters, urlParameters, dataParameter);
        List<SwaggerResponse> responses = new ArrayList<>();

        for (Result result : results)
        {
            responses.add(new SwaggerResponse(result.code(), result.types(), result.headers(), result.description()));
        }

        return new SwaggerEndPoint(name, description, deprecated, parent, consumes, produces, parameters, responses);
    }

    private String[] consumes()
    {
        for (HeaderParameter headerParameter : headerParameters)
        {
            if (headerParameter.is(Header.CONTENT_TYPE))
            {
                return headerParameter.value();
            }
        }

        return new String[0];
    }

    private String[] produces()
    {
        List<String> list = new ArrayList<>();

        for (Result result : results)
        {
            for (String produces : result.produces())
            {
                if (!list.contains(produces))
                {
                    list.add(produces);
                }
            }
        }

        String[] result = new String[list.size()];
        list.toArray(result);

        return result;
    }

    private List<SwaggerParameter> parameters(HeaderParameter[] headerParameters, PathParameter pathParameters, UrlParameter urlParameters, DataParameter dataParameter)
    {
        List<SwaggerParameter> parameters = new ArrayList<>();

        for (HeaderParameter parameter : headerParameters)
        {
            if (!parameter.is(Header.CONTENT_TYPE))
            {
                parameters.add(parameter.swaggerParameter());
            }
        }

        parameters.addAll(pathParameters.swaggerParameters());
        parameters.addAll(urlParameters.swaggerParameters());

        if (dataParameter.isPresent())
        {
            parameters.add(dataParameter.swaggerParameter());
        }

        return parameters;
    }
}