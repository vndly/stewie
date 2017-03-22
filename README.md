# Stewie
A Java library that generates a **Swagger** configuration file based on the declared API.

## Installation

Add the following code to your **pom.xml**:

```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
```

and the dependency:

```xml
    <dependency>
        <groupId>com.github.mauriciotogneri</groupId>
        <artifactId>stewie</artifactId>
        <version>1.1.1</version>
    </dependency>
```

## Configuration

The entry point requires as a parameter the path of the file with all the arguments, for example:

```ini
input    = src/main/java/com/package/api
output   = output/swagger.json
title    = Project Name
version  = 1.0.0
protocol = https
host     = example.com
base     = /
```

## Example

```java
@EndPoint(
        method = POST,
        path = "/v1/foo/bar/{id}",
        description = "This is a sample end point."
)
@Parameters(
        header = RequestHeaders.class,
        path = ParametersPath.class,
        url = ParametersUrl.class,
        data = ParametersData.class
)
@Responses({
        @Response(
                code = OK,
                produces = JSON,
                type = Profile.class,
                headers = ResponseHeaders.class,
                description = "Successful operation"
        ),
        @Response(
                code = BAD_REQUEST,
                produces = JSON,
                type = Error.class,
                description = "Bad Request"
        ),
        @Response(
                code = UNAUTHORIZED,
                produces = JSON,
                type = Error.class,
                description = "Unauthorized"
        ),
        @Response(
                code = NOT_FOUND,
                produces = JSON,
                type = Error.class,
                description = "Not Found"
        ),
        @Response(
                code = METHOD_NOT_ALLOWED,
                produces = JSON,
                type = Error.class,
                description = "Method Not Allowed"
        )
})
public interface SampleEndPoint
{
    class RequestHeaders
    {
        @Name(CONTENT_TYPE)
        @Default(JSON)
        String contentType;
        
        @Optional
        @Name(COOKIE)
        @Description("The session cookie")
        String cookie;
    }
    
    class ResponseHeaders
    {
        @Name("Token")
        @Description("The authentication token")
        String token;
    }
    
    class ParametersPath
    {
        @Description("The resource id")
        @Minimum(1)
        Long id;
    }
    
    class ParametersUrl
    {
        @Description("The filter switch")
        Boolean filter;
        
        @Description("The date to filter")
        Date date;
        
        @Description("The limit to filter")
        Double limit;
        
        @Description("The gender to filter")
        Gender gender;
    }
    
    @Description("The data parameter")
    class ParametersData
    {
        String email;
        
        String password;
    }
}
```

```java
public class Error
{
    Integer error;
}
```

```java
public enum Gender
{
    MALE,
    FEMALE
}
```

```java
public class Profile
{
    Long id;
    
    @Format("email")
    String email;
}
```