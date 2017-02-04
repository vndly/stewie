package com.mauriciotogneri.swagger.types;

public final class MimeType
{
    public static final String JSON = "application/json";
    public static final String PDF = "application/pdf";
    public static final String XLS = "application/vnd.ms-excel";
    public static final String JPG = "image/jpg";
    public static final String JPEG = "image/jpeg";
    public static final String PNG = "image/png";
    public static final String HTML = "text/html";
    public static final String FONT = "font/opentype";
    public static final String CSS = "text/css";
    public static final String CSV = "text/csv";
    public static final String DOC = "application/msword";
    public static final String PPT = "application/vnd.ms-powerpointtd";
    public static final String PLAIN = "text/plain";

    // TODO
    public static final String UTF_8 = "charset=utf-8";

    private MimeType()
    {
    }

    public static boolean isJson(String mimeType)
    {
        return mimeType.equals(MimeType.JSON);
    }

    public static boolean isImage(String mimeType)
    {
        return isJpg(mimeType) || isPng(mimeType);
    }

    public static boolean isJpg(String mimeType)
    {
        return mimeType.equals(MimeType.JPG) ||
                mimeType.equals(MimeType.JPEG);
    }

    public static boolean isPng(String mimeType)
    {
        return mimeType.equals(MimeType.PNG);
    }

    public static boolean isText(String mimeType)
    {
        return mimeType.equals(MimeType.PLAIN) ||
                mimeType.equals(MimeType.HTML) ||
                mimeType.equals(MimeType.CSS);
    }

    public static boolean isBinary(String mimeType)
    {
        return mimeType.equals(MimeType.PDF) ||
                mimeType.equals(MimeType.XLS) ||
                mimeType.equals(MimeType.FONT) ||
                mimeType.equals(MimeType.PNG) ||
                mimeType.equals(MimeType.JPEG) ||
                mimeType.equals(MimeType.JPG) ||
                mimeType.equals(MimeType.DOC) ||
                mimeType.equals(MimeType.PPT);
    }

    public static boolean isPdf(String mimeType)
    {
        return mimeType.equals(MimeType.PDF);
    }
}