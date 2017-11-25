package com.mauriciotogneri.stewie.specs;

import com.mauriciotogneri.jsonschema.Definitions;
import com.mauriciotogneri.stewie.annotations.EndPoint;
import com.mauriciotogneri.stewie.annotations.Parameters;
import com.mauriciotogneri.stewie.annotations.Responses;
import com.mauriciotogneri.stewie.model.SwaggerEndPoint;
import com.mauriciotogneri.stewie.model.SwaggerParameter;
import com.mauriciotogneri.stewie.model.SwaggerResponse;
import com.mauriciotogneri.stewie.specs.parameters.DataParameter;
import com.mauriciotogneri.stewie.specs.parameters.FormParameter;
import com.mauriciotogneri.stewie.specs.parameters.HeaderParameter;
import com.mauriciotogneri.stewie.specs.parameters.PathParameter;
import com.mauriciotogneri.stewie.specs.parameters.UrlParameter;
import com.mauriciotogneri.stewie.types.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EndPointInfo
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
    private final FormParameter formParameters;
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

        if (parameters != null)
        {
            this.headerParameters = HeaderParameter.from(parameters.header());
            this.pathParameters = new PathParameter(parameters.path());
            this.urlParameters = new UrlParameter(parameters.url());
            this.formParameters = new FormParameter(parameters.form());
            this.dataParameter = new DataParameter(parameters.data());
        }
        else
        {
            this.headerParameters = new HeaderParameter[0];
            this.pathParameters = new PathParameter(Object.class);
            this.urlParameters = new UrlParameter(Object.class);
            this.formParameters = new FormParameter(Object.class);
            this.dataParameter = new DataParameter(Object.class);
        }

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
        String filePath = file.getAbsolutePath();
        String classPath = filePath.substring(filePath.indexOf("/java/") + 6).replace("/", ".").replace(".java", "");

        try
        {
            return Class.forName(classPath);
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(String.format("Class not found: %s", classPath));
        }
    }

    public SwaggerEndPoint swaggerEndPoint(Definitions definitions)
    {
        String[] consumes = consumes();
        String[] produces = produces();
        List<SwaggerParameter> parameters = parameters(headerParameters, pathParameters, urlParameters, formParameters, dataParameter, definitions);
        List<SwaggerResponse> responses = new ArrayList<>();

        for (Result result : results)
        {
            responses.add(new SwaggerResponse(result.code(), result.type(), result.headers(), result.description(), definitions));
        }

        return new SwaggerEndPoint(name, description, deprecated, parent, consumes, produces, parameters, responses);
    }

    private String[] consumes()
    {
        for (HeaderParameter headerParameter : headerParameters)
        {
            if (headerParameter.is(Header.CONTENT_TYPE))
            {
                return headerParameter.defaultValue();
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

    private List<SwaggerParameter> parameters(HeaderParameter[] headerParameters, PathParameter pathParameters, UrlParameter urlParameters, FormParameter formParameters, DataParameter dataParameter, Definitions definitions)
    {
        List<SwaggerParameter> parameters = new ArrayList<>();

        for (HeaderParameter parameter : headerParameters)
        {
            if (!parameter.is(Header.CONTENT_TYPE))
            {
                parameters.add(parameter.swaggerParameter(definitions));
            }
        }

        parameters.addAll(pathParameters.swaggerParameters(definitions));
        parameters.addAll(urlParameters.swaggerParameters(definitions));
        parameters.addAll(formParameters.swaggerParameters(definitions));

        if (dataParameter.isPresent())
        {
            parameters.add(dataParameter.swaggerParameter(definitions));
        }

        return parameters;
    }
}