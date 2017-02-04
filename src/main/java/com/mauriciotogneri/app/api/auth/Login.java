package com.mauriciotogneri.app.api.auth;

import com.mauriciotogneri.app.api.auth.Login.ParametersData;
import com.mauriciotogneri.app.api.auth.Login.ParametersPath;
import com.mauriciotogneri.app.api.auth.Login.ParametersUrl;
import com.mauriciotogneri.app.api.auth.Login.Person;
import com.mauriciotogneri.app.api.auth.Login.Profile;
import com.mauriciotogneri.app.api.auth.Login.RequestHeaders;
import com.mauriciotogneri.app.api.auth.Login.ResponseHeaders;
import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.annotations.endpoint.Error;
import com.mauriciotogneri.swagger.annotations.endpoint.Name;
import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.annotations.endpoint.Parameters;
import com.mauriciotogneri.swagger.annotations.endpoint.Path;
import com.mauriciotogneri.swagger.annotations.endpoint.Response;

import static com.mauriciotogneri.swagger.types.Header.CONTENT_TYPE;
import static com.mauriciotogneri.swagger.types.Header.COOKIE;
import static com.mauriciotogneri.swagger.types.HttpCode.BAD_REQUEST;
import static com.mauriciotogneri.swagger.types.HttpCode.METHOD_NOT_ALLOWED;
import static com.mauriciotogneri.swagger.types.HttpCode.NOT_FOUND;
import static com.mauriciotogneri.swagger.types.HttpCode.OK;
import static com.mauriciotogneri.swagger.types.HttpCode.UNAUTHORIZED;
import static com.mauriciotogneri.swagger.types.Method.POST;
import static com.mauriciotogneri.swagger.types.MimeType.JSON;

@Path(
        method = POST,
        path = "/v1/auth/login"
)
@Description("This is the login end point.")
@Parameters(
        header = RequestHeaders.class,
        path = ParametersPath.class,
        url = ParametersUrl.class,
        data = ParametersData.class
)
@Response(
        code = OK,
        produces = JSON,
        type = {Profile.class, Person.class},
        headers = ResponseHeaders.class,
        errors = {
                @Error(code = BAD_REQUEST, errors = {
                        "1001: invalid email",
                        "1002: invalid password"}
                ),
                @Error(code = UNAUTHORIZED),
                @Error(code = NOT_FOUND),
                @Error(code = METHOD_NOT_ALLOWED)
        }
)
public interface Login
{
    class RequestHeaders
    {
        @Name(CONTENT_TYPE)
        @Default(JSON)
        public String contentType;

        @Optional
        @Name(COOKIE)
        public String cookie;
    }

    class ResponseHeaders
    {
        @Name("Token")
        public String token;
    }

    class ParametersPath
    {
        public String id;
    }

    class ParametersUrl
    {
        public Integer limit;
    }

    class ParametersData
    {
        public String email;

        public String password;
    }

    class Profile
    {
        public Long id;

        public String email;
    }

    class Person
    {
        public Long id;

        public String fistName;

        public String lastName;
    }
}