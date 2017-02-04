package com.mauriciotogneri.swagger.specs;

import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.annotations.endpoint.Parameters;
import com.mauriciotogneri.swagger.annotations.endpoint.Path;
import com.mauriciotogneri.swagger.annotations.endpoint.Response;
import com.mauriciotogneri.swagger.model.SwaggerEndPoint;
import com.mauriciotogneri.swagger.model.SwaggerParameter;
import com.mauriciotogneri.swagger.model.SwaggerResponse;
import com.mauriciotogneri.swagger.specs.parameters.DataParameter;
import com.mauriciotogneri.swagger.specs.parameters.HeaderParameter;
import com.mauriciotogneri.swagger.specs.parameters.PathParameter;
import com.mauriciotogneri.swagger.specs.parameters.UrlParameter;
import com.mauriciotogneri.swagger.types.Header;
import com.mauriciotogneri.swagger.types.Method;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class EndPoint
{
    private final String parent;
    private final String name;
    private final String path;
    private final Method method;
    private final String description;
    private final HeaderParameter[] headerParameters;
    private final PathParameter pathParameters;
    private final UrlParameter urlParameters;
    private final DataParameter dataParameter;
    private final Result result;

    public EndPoint(String parent, File file)
    {
        Class<?> clazz = getClass(file);

        Path path = clazz.getAnnotation(Path.class);
        Parameters parameters = clazz.getAnnotation(Parameters.class);
        Response response = clazz.getAnnotation(Response.class);
        Description description = clazz.getAnnotation(Description.class);

        this.parent = parent;
        this.name = file.getName().replace(".java", "");
        this.path = path.path();
        this.method = path.method();
        this.description = description.value();
        this.headerParameters = HeaderParameter.from(parameters.header());
        this.pathParameters = new PathParameter(parameters.path());
        this.urlParameters = new UrlParameter(parameters.url());
        this.dataParameter = new DataParameter(parameters.data());
        this.result = new Result(response);
    }

    public String name()
    {
        return name;
    }

    public String path()
    {
        return path;
    }

    public Method method()
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
        String[] produces = result.produces();
        List<SwaggerParameter> parameters = parameters(headerParameters, pathParameters, urlParameters, dataParameter);
        SwaggerResponse response;

        if (!result.isEmpty())
        {
            response = new SwaggerResponse(result.types());
        }
        else
        {
            response = new SwaggerResponse();
        }

        return new SwaggerEndPoint(name, description, parent, consumes, produces, parameters, String.valueOf(result.code()), response);
    }

    private String[] consumes()
    {
        for (HeaderParameter headerParameter : headerParameters)
        {
            if (Header.CONTENT_TYPE.equals(headerParameter.name()))
            {
                return headerParameter.value();
            }
        }

        return new String[0];
    }

    private List<SwaggerParameter> parameters(HeaderParameter[] headerParameters, PathParameter pathParameters, UrlParameter urlParameters, DataParameter dataParameter)
    {
        List<SwaggerParameter> parameters = new ArrayList<>();

        for (HeaderParameter parameter : headerParameters)
        {
            if (!Header.CONTENT_TYPE.equals(parameter.name()))
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