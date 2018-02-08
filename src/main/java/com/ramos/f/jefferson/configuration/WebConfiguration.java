/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.configuration;

import com.ramos.f.jefferson.tenant.TenantInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author jeffe
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.ramos.f.jefferson"})
public class WebConfiguration implements WebMvcConfigurer{
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantInterceptor());
    }
    
}
