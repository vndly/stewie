package com.mauriciotogneri.swagger.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.mauriciotogneri.swagger.model.Swagger;
import com.mauriciotogneri.swagger.model.SwaggerDefinitions;
import com.mauriciotogneri.swagger.model.SwaggerInfo;
import com.mauriciotogneri.swagger.model.SwaggerPathList;
import com.mauriciotogneri.swagger.specs.Definitions;
import com.mauriciotogneri.swagger.specs.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

public class SwaggerGenerator
{
    public static void main(String[] args) throws Exception
    {
        if (args.length >= 1)
        {
            SwaggerGenerator swaggerGenerator = new SwaggerGenerator();
            swaggerGenerator.generate(new File(args[0]));
        }
        else
        {
            System.err.println("Usage: java -jar swagger-generator.jar PATH_TO_CONFIG_FILE");
        }
    }

    public void generate(File config) throws IOException
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream(config));

        File input = new File(properties.getProperty("input"));
        File output = new File(properties.getProperty("output"));

        String title = properties.getProperty("title");
        String version = properties.getProperty("version");
        String protocol = properties.getProperty("protocol");
        String host = properties.getProperty("host");
        String basePath = properties.getProperty("base");

        generate(input, output, title, version, protocol, host, basePath);
        checkSchema(output);
    }

    private void generate(File input, File output, String title, String version, String protocol, String host, String basePath) throws IOException
    {
        Services services = new Services(input);

        Definitions definitions = new Definitions();
        SwaggerPathList paths = services.paths(definitions);
        SwaggerDefinitions swaggerDefinitions = definitions.swaggerDefinitions();

        Swagger swagger = new Swagger(
                new SwaggerInfo(version, title),
                host,
                basePath,
                Arrays.asList(protocol),
                services.tags(),
                paths,
                swaggerDefinitions);

        swagger.save(output);
    }

    private void checkSchema(File output)
    {
        try
        {
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
            URL schemaPath = getClass().getResource("/swagger-schema.json");
            JsonSchema schema = factory.getJsonSchema(schemaPath.toString());
            JsonNode json = JsonLoader.fromFile(output);

            ProcessingReport report = schema.validate(json);
            System.out.println(report);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}