package com.mauriciotogneri.swagger.specs;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Services implements Iterable<Service>
{
    private final File root;

    public Services(File root)
    {
        this.root = root;
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