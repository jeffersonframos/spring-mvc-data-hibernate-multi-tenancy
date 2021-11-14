package com.ramos.f.jefferson.tenant;

import com.ramos.f.jefferson.security.CustomUserDetails;
import static com.ramos.f.jefferson.util.Constants.DEFAULT_TENANT;
import com.ramos.f.jefferson.util.Functions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static java.util.Objects.isNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;

@Component
public class TenantInterceptor implements HandlerInterceptor {
    
    /**
     * Method used to set the tenant for the current request.
     * <p>
     * First it checks if the {@link Authentication} from the {@link SecurityContext} is 
     * null or an instance from {@link AnonymousAuthenticationToken}. This is needed in case of 
     * the user is trying to authenticate, so Spring sets the Authentication of the SecurityContext to 
     * AnonymousAuthenticationToken, with the principal with a String value of "anonymousUser".
     * In this case, the tenant is set using the subdomain from the request.
     * If the request does not contain a subdomain, this is resolved in the {@link TenantIdentifierResolver#resolveCurrentTenantIdentifier()}
     * </p>
     * <p>
     * If the Authentication is an instance of {@link CustomUserDetails}, then the tenant is set using 
     * the data from the authenticated user.
     * </p>
     * <p>
     * As a fail safe, if none of the requirements above is met, so the tenant is set to the default one.
     * </p>
     * 
     * 
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception 
     */
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNull(authentication) || authentication instanceof AnonymousAuthenticationToken) {
            String url = request.getRequestURL().toString();
            tenantId = Functions.getSubdomain(url);
        } else if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            tenantId = customUserDetails.getTenant();
        } else {
            tenantId = DEFAULT_TENANT;
        }
        TenantContext.setCurrentTenant(tenantId);
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
    
}
