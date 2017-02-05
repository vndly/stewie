package com.mauriciotogneri.app.model;

import com.mauriciotogneri.swagger.annotations.fields.Format;

public class Profile
{
    Long id;

    @Format("email")
    String email;
}