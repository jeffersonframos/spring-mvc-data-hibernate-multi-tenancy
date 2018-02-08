/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.tenant;


/**
 *
 * @author jeffe
 */
public class TenantContext {
    
    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();
    
    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }
    
    public static String getCurrentTenant() {
        return currentTenant.get();
    }
    
    public static void clear() {
        currentTenant.set(null);
    }
    
}
