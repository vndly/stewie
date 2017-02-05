package com.mauriciotogneri.app.model;

import com.mauriciotogneri.swagger.annotations.fields.Format;

public class Profile
{
    public Long id;

    @Format("email")
    public String email;
}