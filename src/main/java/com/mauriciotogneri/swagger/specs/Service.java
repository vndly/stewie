package com.mauriciotogneri.swagger.specs;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Service implements Iterable<EndPoint>
{
    private final File root;

    public Service(File root)
    {
        this.root = root;
    }

    @Override
    public Iterator<EndPoint> iterator()
    {
        final List<EndPoint> endPoints = new ArrayList<>();

        File[] roots = root.listFiles();

        if (roots != null)
        {
            for (File current : roots)
            {
                String name = current.getName().substring(0, current.getName().length() - 5);

                if (!name.endsWith("Module"))
                {
                    EndPoint endPoint = new EndPoint(toString(), current);
                    endPoints.add(endPoint);
                }
            }
        }

        endPoints.sort(Comparator.comparing(EndPoint::name));

        return new Iterator<EndPoint>()
        {
            private int index = 0;

            @Override
            public boolean hasNext()
            {
                return index < endPoints.size();
            }

            @Override
            public EndPoint next()
            {
                if (index < endPoints.size())
                {
                    return endPoints.get(index++);
                }
                else
                {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    @Override
    public String toString()
    {
        return String.valueOf(root.getName().charAt(0)).toUpperCase() + root.getName().substring(1);
    }
}