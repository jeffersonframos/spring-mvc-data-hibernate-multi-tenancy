package com.ramos.f.jefferson.tenant;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver{

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        if(StringUtils.isNotBlank(tenantId)){
            return tenantId;
        }
        return "default";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
    
}
