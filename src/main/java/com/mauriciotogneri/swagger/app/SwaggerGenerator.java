package com.mauriciotogneri.swagger.app;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mauriciotogneri.swagger.model.Swagger;
import com.mauriciotogneri.swagger.model.SwaggerInfo;
import com.mauriciotogneri.swagger.model.SwaggerPath;
import com.mauriciotogneri.swagger.model.SwaggerTag;
import com.mauriciotogneri.swagger.specs.EndPoint;
import com.mauriciotogneri.swagger.specs.Service;
import com.mauriciotogneri.swagger.specs.Services;
import com.mauriciotogneri.swagger.utils.JsonHelper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class SwaggerGenerator
{
    private SwaggerGenerator()
    {
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length >= 1)
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream(args[0]));

            File input = new File(properties.getProperty("input"));
            File output = new File(properties.getProperty("output"));

            String title = properties.getProperty("title");
            String version = properties.getProperty("version");
            String protocol = properties.getProperty("protocol");
            String host = properties.getProperty("host");
            String basePath = properties.getProperty("base");

            SwaggerGenerator swaggerGenerator = new SwaggerGenerator();
            swaggerGenerator.generate(input, output, title, version, protocol, host, basePath);
        }
        else
        {
            System.err.println("Usage: java -jar swagger.jar CONFIG_FILE");
        }
    }

    private void generate(File input, File output, String title, String version, String protocol, String host, String basePath) throws IOException
    {
        Services services = new Services(input);

        List<SwaggerTag> tags = tags(services);
        Map<String, SwaggerPath> paths = paths(services);

        Swagger swagger = new Swagger(new SwaggerInfo(version, title), host, basePath, Arrays.asList(protocol), tags, paths, new JsonObject());

        Gson gson = JsonHelper.create(true);
        save(output, gson.toJson(swagger));
    }

    private List<SwaggerTag> tags(Services services)
    {
        List<SwaggerTag> tags = new ArrayList<>();

        for (Service service : services)
        {
            tags.add(new SwaggerTag(service.toString()));
        }

        return tags;
    }

    private Map<String, SwaggerPath> paths(Services services)
    {
        Map<String, SwaggerPath> paths = new HashMap<>();

        for (Service service : services)
        {
            for (EndPoint endPoint : service)
            {
                String endPointPath = endPoint.path();
                SwaggerPath swaggerPath;

                if (paths.containsKey(endPointPath))
                {
                    swaggerPath = paths.get(endPointPath);
                }
                else
                {
                    swaggerPath = new SwaggerPath();
                    paths.put(endPoint.path(), swaggerPath);
                }

                swaggerPath.put(endPoint.method().toString().toLowerCase(), endPoint.swaggerEndPoint());
            }
        }

        return paths;
    }

    public void save(File file, String content) throws IOException
    {
        File parent = file.getParentFile();

        if (((parent == null) || parent.exists() || parent.mkdirs()) && (file.exists() || file.createNewFile()))
        {
            try (FileWriter fileWriter = new FileWriter(file.getAbsoluteFile()))
            {
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(content);
                bw.close();
            }
        }
    }
}