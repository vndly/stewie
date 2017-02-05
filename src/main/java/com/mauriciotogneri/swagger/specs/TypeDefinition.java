package com.mauriciotogneri.swagger.specs;

import java.io.File;
import java.net.URI;
import java.util.Date;

public class TypeDefinition
{
    private final Class<?> clazz;

    public TypeDefinition(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public Class<?> clazz()
    {
        return clazz;
    }

    public String canonicalName()
    {
        return clazz.getCanonicalName();
    }

    public Object[] enums()
    {
        return clazz.getEnumConstants();
    }

    public Class<?> componentType()
    {
        return clazz.getComponentType();
    }

    public Boolean isString()
    {
        return (clazz.equals(String.class) || (clazz.equals(Character.class) || (clazz.equals(char.class))));
    }

    public Boolean isBoolean()
    {
        return (clazz.equals(Boolean.class) || clazz.equals(boolean.class));
    }

    public Boolean isInteger()
    {
        return (clazz.equals(Integer.class) || clazz.equals(int.class) || clazz.equals(Long.class) || clazz.equals(long.class));
    }

    public Boolean isNumber()
    {
        return (clazz.equals(Float.class) || clazz.equals(float.class) || clazz.equals(Double.class) || clazz.equals(double.class));
    }

    public Boolean isDate()
    {
        return (clazz.equals(Date.class));
    }

    public Boolean isUri()
    {
        return (clazz.equals(URI.class));
    }

    public Boolean isFile()
    {
        return (clazz.equals(File.class));
    }

    public Boolean isEnum()
    {
        return (clazz.isEnum());
    }

    public Boolean isArray()
    {
        return (clazz.isArray());
    }
}