/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.tenant;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

/**
 *
 * @author jeffe
 */
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver{

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        if(!StringUtils.isBlank(tenantId)){
            return tenantId;
        }
        return "default";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
    
}
