package com.mauriciotogneri.swagger.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Swagger
{
    public final String swagger;
    public final SwaggerInfo info;
    public final String host;
    public final String basePath;
    public final List<SwaggerTag> tags;
    public final List<String> schemes;
    public final SwaggerPathList paths;
    public final SwaggerDefinitions definitions;

    public Swagger(SwaggerInfo info, String host, String basePath, List<String> schemes, List<SwaggerTag> tags, SwaggerPathList paths, SwaggerDefinitions definitions)
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

    private String json()
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.disableHtmlEscaping();
        Gson gson = builder.create();

        return gson.toJson(this);
    }

    public void save(File file) throws IOException
    {
        File parent = file.getParentFile();

        if (((parent == null) || parent.exists() || parent.mkdirs()) && (file.exists() || file.createNewFile()))
        {
            try (FileWriter fileWriter = new FileWriter(file.getAbsoluteFile()))
            {
                BufferedWriter bw = new BufferedWriter(fileWriter);
                bw.write(json());
                bw.close();
            }
        }
    }
}