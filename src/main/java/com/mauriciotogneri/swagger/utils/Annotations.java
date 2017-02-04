package com.mauriciotogneri.swagger.utils;

import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.annotations.endpoint.Name;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Annotations
{
    private final Annotation[] annotations;

    public Annotations(Field field)
    {
        this.annotations = field.getAnnotations();
    }

    public Annotations(Class<?> clazz)
    {
        this.annotations = clazz.getAnnotations();
    }

    public String name()
    {
        Name name = annotation(Name.class);

        return (name != null) ? name.value() : null;
    }

    public String description()
    {
        Description description = annotation(Description.class);

        return (description != null) ? description.value() : null;
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation> A annotation(Class<A> type)
    {
        for (Annotation annotation : annotations)
        {
            if (annotation.annotationType().equals(type))
            {
                return (A) annotation;
            }
        }

        return null;
    }
}