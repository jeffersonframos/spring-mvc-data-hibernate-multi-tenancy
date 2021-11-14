package com.ramos.f.jefferson.util;

public class Constants {
    private Constants() {}
    
    public final static String TOKEN_PREFIX = "Bearer ";
    public final static String HEADER_STRING = "Authorization";
    public final static String CLAIM_KEY_USERNAME = "sub";
    public final static String CLAIM_KEY_TENANT = "tenant";
    public final static String CLAIM_KEY_CREATED = "created";
    public final static String CLAIM_KEY_EXPIRED = "exp";
    
    public final static String PAGE_REQUEST_PAGE_PARAMETER = "page";
    public final static String PAGE_REQUEST_QUANTITY_PARAMETER = "quantity";
    
    public final static String DEFAULT_TENANT = "default";
}
