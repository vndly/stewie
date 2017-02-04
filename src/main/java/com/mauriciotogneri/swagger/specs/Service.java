package com.mauriciotogneri.swagger.specs;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public final class Service implements Iterable<EndPointInfo>
{
    private final File root;

    public Service(File root)
    {
        this.root = root;
    }

    @Override
    public Iterator<EndPointInfo> iterator()
    {
        final List<EndPointInfo> endPointInfos = new ArrayList<>();

        File[] roots = root.listFiles();

        if (roots != null)
        {
            for (File current : roots)
            {
                String name = current.getName().substring(0, current.getName().length() - 5);

                if (!name.endsWith("Module"))
                {
                    EndPointInfo endPointInfo = new EndPointInfo(toString(), current);
                    endPointInfos.add(endPointInfo);
                }
            }
        }

        endPointInfos.sort(Comparator.comparing(EndPointInfo::name));

        return new Iterator<EndPointInfo>()
        {
            private int index = 0;

            @Override
            public boolean hasNext()
            {
                return index < endPointInfos.size();
            }

            @Override
            public EndPointInfo next()
            {
                if (index < endPointInfos.size())
                {
                    return endPointInfos.get(index++);
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