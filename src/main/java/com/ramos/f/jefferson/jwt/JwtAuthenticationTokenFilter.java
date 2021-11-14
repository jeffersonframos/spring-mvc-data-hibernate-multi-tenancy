package com.ramos.f.jefferson.jwt;

import com.ramos.f.jefferson.exception.InvalidTokenException;
import com.ramos.f.jefferson.security.CustomUserDetails;
import com.ramos.f.jefferson.service.UserService;
import com.ramos.f.jefferson.tenant.TenantContext;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.ramos.f.jefferson.util.Constants.HEADER_STRING;
import com.ramos.f.jefferson.util.Functions;
import java.util.logging.Level;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    
    public JwtAuthenticationTokenFilter(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String username = null;
        String authToken = request.getHeader(HEADER_STRING);
        if (isNotBlank(authToken)) {
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                String tenant = jwtTokenUtil.getTenantFromToken(authToken);
                TenantContext.setCurrentTenant(tenant);
            } catch (InvalidTokenException ex) {
                LOGGER.error("An error during getting user data from token", ex);
                throw new BadCredentialsException("Invalid tenant and user.");
            }
        } else {
            String url = request.getRequestURL().toString();
            String tenant = Functions.getSubdomain(url);
            TenantContext.setCurrentTenant(tenant);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                CustomUserDetails customUserDetails = (CustomUserDetails) userService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, customUserDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (InvalidTokenException ex) {
                LOGGER.error("An error during reading token", ex);
                throw new BadCredentialsException("Invalid token.");
            }
        }
        filterChain.doFilter(request, response);
    }
}
