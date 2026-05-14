package com.operation.common;

public class MinioConstants {
    public static final String BUCKET_NAME = "operation-files";
    public static final String BUCKET_DEVICE = "device-files";
    public static final String BUCKET_AVATAR = "avatar";
    public static final String BUCKET_DOCUMENT = "document";
    public static final String SEPARATOR = "/";

    public static final String PATH_AVATAR = "avatar/";
    public static final String PATH_DEVICE = "device/";
    public static final String PATH_DOCUMENT = "document/";
    public static final String PATH_ATTACHMENT = "attachment/";

    public static final long MAX_FILE_SIZE = 104857600L;
    public static final long PRESIGNED_EXPIRY = 3600L;
}
