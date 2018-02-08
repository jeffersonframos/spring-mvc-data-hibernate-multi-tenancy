/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.tenant;

import com.ramos.f.jefferson.util.Functions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author jeffe
 */
@Component
public class TenantInterceptor extends HandlerInterceptorAdapter{
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Functions functions = new Functions();
        String url = request.getRequestURL().toString();
        String tenantId = functions.getSubdomain(url);
//        String tenantId = request.getParameter("tenant");
        TenantContext.setCurrentTenant(tenantId);
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }
    
}
