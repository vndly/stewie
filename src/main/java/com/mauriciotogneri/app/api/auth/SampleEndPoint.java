package com.mauriciotogneri.app.api.auth;

import com.mauriciotogneri.app.api.auth.SampleEndPoint.ParametersData;
import com.mauriciotogneri.app.api.auth.SampleEndPoint.ParametersForm;
import com.mauriciotogneri.app.api.auth.SampleEndPoint.ParametersPath;
import com.mauriciotogneri.app.api.auth.SampleEndPoint.ParametersUrl;
import com.mauriciotogneri.app.api.auth.SampleEndPoint.RequestHeaders;
import com.mauriciotogneri.app.api.auth.SampleEndPoint.ResponseHeaders;
import com.mauriciotogneri.app.model.Error;
import com.mauriciotogneri.app.model.Gender;
import com.mauriciotogneri.app.model.Person;
import com.mauriciotogneri.app.model.Profile;
import com.mauriciotogneri.swagger.annotations.endpoint.Default;
import com.mauriciotogneri.swagger.annotations.endpoint.Description;
import com.mauriciotogneri.swagger.annotations.endpoint.Name;
import com.mauriciotogneri.swagger.annotations.endpoint.Optional;
import com.mauriciotogneri.swagger.annotations.endpoint.Parameters;
import com.mauriciotogneri.swagger.annotations.endpoint.EndPoint;
import com.mauriciotogneri.swagger.annotations.endpoint.Response;
import com.mauriciotogneri.swagger.annotations.endpoint.Responses;

import java.util.Date;

import static com.mauriciotogneri.swagger.types.Header.CONTENT_TYPE;
import static com.mauriciotogneri.swagger.types.Header.COOKIE;
import static com.mauriciotogneri.swagger.types.Method.POST;
import static com.mauriciotogneri.swagger.types.MimeType.JSON;
import static com.mauriciotogneri.swagger.types.StatusCode.BAD_REQUEST;
import static com.mauriciotogneri.swagger.types.StatusCode.METHOD_NOT_ALLOWED;
import static com.mauriciotogneri.swagger.types.StatusCode.NOT_FOUND;
import static com.mauriciotogneri.swagger.types.StatusCode.OK;
import static com.mauriciotogneri.swagger.types.StatusCode.UNAUTHORIZED;

@EndPoint(
        method = POST,
        path = "/v1/foo/bar/{id}",
        description ="This is a sample end point."
)
@Parameters(
        header = RequestHeaders.class,
        path = ParametersPath.class,
        url = ParametersUrl.class,
        form = ParametersForm.class,
        data = ParametersData.class
)
@Responses({
        @Response(
                code = OK,
                produces = JSON,
                type = {Profile.class, Person.class},
                headers = ResponseHeaders.class,
                description = "Successful operation"
        ),
        @Response(
                code = BAD_REQUEST,
                produces = JSON,
                type = Error.class
        ),
        @Response(
                code = UNAUTHORIZED,
                produces = JSON,
                type = Error.class
        ),
        @Response(
                code = NOT_FOUND,
                produces = JSON,
                type = Error.class
        ),
        @Response(
                code = METHOD_NOT_ALLOWED,
                produces = JSON,
                type = Error.class
        )
})
public interface SampleEndPoint
{
    class RequestHeaders
    {
        @Name(CONTENT_TYPE)
        @Default(JSON)
        public String contentType;

        @Optional
        @Name(COOKIE)
        @Description("The session cookie")
        public String cookie;
    }

    class ResponseHeaders
    {
        @Name("Token")
        public String token;
    }

    class ParametersPath
    {
        @Description("The resource id")
        public Long id;
    }

    class ParametersUrl
    {
        @Description("The filter switch")
        public Boolean filter;

        @Description("The date to filter")
        public Date date;
    }

    class ParametersForm
    {
        @Description("The limit to filter")
        public Double limit;

        @Description("The gender to filter")
        public Gender gender;
    }

    @Description("The data parameter")
    class ParametersData
    {
        public String email;

        public String password;
    }
}