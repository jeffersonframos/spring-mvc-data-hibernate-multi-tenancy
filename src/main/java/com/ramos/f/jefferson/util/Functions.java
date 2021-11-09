package com.ramos.f.jefferson.util;

import java.util.StringTokenizer;

public class Functions {
    private Functions() {}
    
    public static String getSubdomain(String url){
        url = url.replace("https://", "");
        url = url.replace("http://", "");
        StringTokenizer st = new StringTokenizer(url, ".");
        return st.nextToken();
    }
    
}
