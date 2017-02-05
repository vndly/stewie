package com.mauriciotogneri.swagger.model;

import com.google.gson.annotations.SerializedName;
import com.mauriciotogneri.swagger.specs.Annotations;
import com.mauriciotogneri.swagger.specs.Definitions;
import com.mauriciotogneri.swagger.specs.TypeDefinition;

@SuppressWarnings("ALL")
public class SwaggerSchema
{
    private final String type;
    private final String format;
    private final SwaggerSchema items;
    private final String pattern;
    private final Integer minimum;
    private final Integer maximum;
    private final Integer minLength;
    private final Integer maxLength;
    private final Integer minItems;
    private final Integer maxItems;
    private final String $ref;

    @SerializedName("enum")
    private final String[] enumValues;

    public static final String TYPE_STRING = "string";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_INTEGER = "integer";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_FILE = "file";
    public static final String TYPE_ARRAY = "array";

    private SwaggerSchema(String type,
                          String format,
                          String[] enumValues,
                          SwaggerSchema items,
                          String pattern,
                          Integer minimum,
                          Integer maximum,
                          Integer minLength,
                          Integer maxLength,
                          Integer minItems,
                          Integer maxItems,
                          String ref)
    {
        this.type = type;
        this.format = format;
        this.enumValues = enumValues;
        this.items = items;
        this.$ref = (ref != null) ? String.format("#/definitions/%s", ref) : null;
        this.pattern = pattern;
        this.minimum = minimum;
        this.maximum = maximum;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.minItems = minItems;
        this.maxItems = maxItems;
    }

    public String type()
    {
        return type;
    }

    public String format()
    {
        return format;
    }

    public static SwaggerSchema fromClass(TypeDefinition typeDef, Annotations annotations, Definitions definitions)
    {
        if (typeDef.isString())
        {
            return new Builder(annotations).type(TYPE_STRING).build();
        }
        else if (typeDef.isBoolean())
        {
            return new Builder(annotations).type(TYPE_BOOLEAN).build();
        }
        else if (typeDef.isInteger())
        {
            return new Builder(annotations).type(TYPE_INTEGER).build();
        }
        else if (typeDef.isNumber())
        {
            return new Builder(annotations).type(TYPE_NUMBER).build();
        }
        else if (typeDef.isDate())
        {
            return new Builder(annotations).type(TYPE_STRING).format("date-time").build();
        }
        else if (typeDef.isFile())
        {
            return new Builder(annotations).type(TYPE_FILE).build();
        }
        else if (typeDef.isEnum())
        {
            Object[] constants = typeDef.enums();

            String[] values = new String[constants.length];

            for (int i = 0; i < constants.length; i++)
            {
                values[i] = constants[i].toString();
            }

            return new Builder(annotations).type(TYPE_STRING).enumValues(values).build();
        }
        else if (typeDef.isArray())
        {
            Class<?> componentType = typeDef.componentType();
            SwaggerSchema items = SwaggerSchema.fromClass(new TypeDefinition(componentType), new Annotations(componentType), definitions);

            return new Builder(annotations).type(TYPE_ARRAY).items(items).build();
        }
        else
        {
            String name = typeDef.canonicalName();

            definitions.add(name, typeDef.clazz());

            return new Builder().ref(name).build();
        }
    }

    static class Builder
    {
        private String type;
        private String format;
        private String[] enumValues;
        private SwaggerSchema items;
        private String pattern;
        private Integer minimum;
        private Integer maximum;
        private Integer minLength;
        private Integer maxLength;
        private Integer minItems;
        private Integer maxItems;
        private String ref;

        public Builder()
        {
        }

        public Builder(Annotations annotations)
        {
            this.pattern = annotations.pattern();
            this.minimum = annotations.minimum();
            this.maximum = annotations.maximum();
            this.minLength = annotations.minLength();
            this.maxLength = annotations.maxLength();
            this.minItems = annotations.minItems();
            this.maxItems = annotations.maxItems();
            this.format = annotations.format();
        }

        public Builder type(String type)
        {
            this.type = type;

            return this;
        }

        public Builder format(String format)
        {
            this.format = format;

            return this;
        }

        public Builder enumValues(String[] enumValues)
        {
            this.enumValues = enumValues;

            return this;
        }

        public Builder items(SwaggerSchema items)
        {
            this.items = items;

            return this;
        }

        public Builder pattern(String pattern)
        {
            this.pattern = pattern;

            return this;
        }

        public Builder minimum(Integer minimum)
        {
            this.minimum = minimum;

            return this;
        }

        public Builder maximum(Integer maximum)
        {
            this.maximum = maximum;

            return this;
        }

        public Builder minLength(Integer minLength)
        {
            this.minLength = minLength;

            return this;
        }

        public Builder maxLength(Integer maxLength)
        {
            this.maxLength = maxLength;

            return this;
        }

        public Builder minItems(Integer minItems)
        {
            this.minItems = minItems;

            return this;
        }

        public Builder maxItems(Integer maxItems)
        {
            this.maxItems = maxItems;

            return this;
        }

        public Builder ref(String ref)
        {
            this.ref = ref;

            return this;
        }

        public SwaggerSchema build()
        {
            return new SwaggerSchema(type,
                                     format,
                                     enumValues,
                                     items,
                                     pattern,
                                     minimum,
                                     maximum,
                                     minLength,
                                     maxLength,
                                     minItems,
                                     maxItems,
                                     ref);
        }
    }
}