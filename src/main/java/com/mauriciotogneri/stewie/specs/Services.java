package com.mauriciotogneri.stewie.specs;

import com.mauriciotogneri.stewie.model.SwaggerPath;
import com.mauriciotogneri.stewie.model.SwaggerPathList;
import com.mauriciotogneri.stewie.model.SwaggerTag;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Services implements Iterable<Service>
{
    private final File root;

    public Services(File root)
    {
        this.root = root;
    }

    public List<SwaggerTag> tags()
    {
        List<SwaggerTag> tags = new ArrayList<>();

        for (Service service : this)
        {
            tags.add(new SwaggerTag(service.toString()));
        }

        return tags;
    }

    public SwaggerPathList paths(Definitions definitions)
    {
        SwaggerPathList paths = new SwaggerPathList();

        for (Service service : this)
        {
            for (EndPointInfo endPointInfo : service)
            {
                String endPointPath = endPointInfo.path();
                SwaggerPath swaggerPath;

                if (paths.containsKey(endPointPath))
                {
                    swaggerPath = paths.get(endPointPath);
                }
                else
                {
                    swaggerPath = new SwaggerPath();
                    paths.put(endPointInfo.path(), swaggerPath);
                }

                swaggerPath.put(endPointInfo.method().toLowerCase(), endPointInfo.swaggerEndPoint(definitions));
            }
        }

        return paths;
    }

    @Override
    public Iterator<Service> iterator()
    {
        final List<Service> services = new ArrayList<>();

        File[] roots = root.listFiles();

        if (roots != null)
        {
            for (File current : roots)
            {
                services.add(new Service(current));
            }
        }

        services.sort(Comparator.comparing(Service::toString));

        return new Iterator<Service>()
        {
            private int index = 0;

            @Override
            public boolean hasNext()
            {
                return index < services.size();
            }

            @Override
            public Service next()
            {
                if (index < services.size())
                {
                    return services.get(index++);
                }
                else
                {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}