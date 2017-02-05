package com.mauriciotogneri.swagger.specs;

import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.annotations.fields.Default;
import com.mauriciotogneri.swagger.annotations.fields.MaxItems;
import com.mauriciotogneri.swagger.annotations.fields.MaxLength;
import com.mauriciotogneri.swagger.annotations.fields.Maximum;
import com.mauriciotogneri.swagger.annotations.fields.MinItems;
import com.mauriciotogneri.swagger.annotations.fields.MinLength;
import com.mauriciotogneri.swagger.annotations.fields.Minimum;
import com.mauriciotogneri.swagger.annotations.fields.Name;
import com.mauriciotogneri.swagger.annotations.fields.Optional;
import com.mauriciotogneri.swagger.annotations.fields.Pattern;

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

    public Boolean optional()
    {
        Optional optional = annotation(Optional.class);

        return (optional != null);
    }

    public String description()
    {
        Description description = annotation(Description.class);

        return (description != null) ? description.value() : null;
    }

    public String pattern()
    {
        Pattern pattern = annotation(Pattern.class);

        return (pattern != null) ? pattern.value() : null;
    }

    public Integer minimum()
    {
        Minimum minimum = annotation(Minimum.class);

        return (minimum != null) ? minimum.value() : null;
    }

    public Integer maximum()
    {
        Maximum maximum = annotation(Maximum.class);

        return (maximum != null) ? maximum.value() : null;
    }

    public Integer minLength()
    {
        MinLength minLength = annotation(MinLength.class);

        return (minLength != null) ? minLength.value() : null;
    }

    public Integer maxLength()
    {
        MaxLength maxLength = annotation(MaxLength.class);

        return (maxLength != null) ? maxLength.value() : null;
    }

    public Integer minItems()
    {
        MinItems minItems = annotation(MinItems.class);

        return (minItems != null) ? minItems.value() : null;
    }

    public Integer maxItems()
    {
        MaxItems maxItems = annotation(MaxItems.class);

        return (maxItems != null) ? maxItems.value() : null;
    }

    public String defaultValue()
    {
        Default defaultValue = annotation(Default.class);

        return (defaultValue != null) ? String.join("; ", defaultValue.value()) : null;
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