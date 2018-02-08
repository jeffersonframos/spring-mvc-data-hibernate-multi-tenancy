/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.util;

import java.util.StringTokenizer;

/**
 *
 * @author jeffe
 */
public class Functions {
    
    public String getSubdomain(String url){
        url = url.replace("https://", "");
        url = url.replace("http://", "");
        StringTokenizer st = new StringTokenizer(url, ".");
        return st.nextToken();
    }
    
}
