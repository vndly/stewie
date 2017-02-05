package com.mauriciotogneri.swagger.types;

public final class Header
{
    // request
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_CHARSET = "Accept-Charset";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String ACCEPT_DATETIME = "Accept-Datetime";
    public static final String AUTHORIZATION = "Authorization";
    public static final String COOKIE = "Cookie";

    // response
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String LINK = "Link";
    public static final String PRAGMA = "Pragma";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_LANGUAGE = "Content-Language";
    public static final String CONTENT_LOCATION = "Content-Location";
    public static final String ETAG = "ETag";

    // both
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CACHE_CONTROL = "Cache-Control";

    private Header()
    {
    }
}