package com.ramos.f.jefferson.jwt;

import com.ramos.f.jefferson.exception.InvalidTokenException;
import com.ramos.f.jefferson.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.ramos.f.jefferson.util.Constants.CLAIM_KEY_CREATED;
import static com.ramos.f.jefferson.util.Constants.CLAIM_KEY_TENANT;
import static com.ramos.f.jefferson.util.Constants.CLAIM_KEY_USERNAME;
import static com.ramos.f.jefferson.util.Constants.TOKEN_PREFIX;
import org.springframework.context.annotation.PropertySource;

@Component
@PropertySource(value = "classpath:/jwt.properties")
public class JwtTokenUtil implements Serializable {
    @Value("${spring.jwt.expiration_time}")
    private long expirationTime;

    @Value("${spring.jwt.signing_key}")
    private String signingKey;

    public String generateToken(CustomUserDetails customUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, customUserDetails.getUsername());
        claims.put(CLAIM_KEY_TENANT, customUserDetails.getTenant());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String refreshToken(String token) throws InvalidTokenException {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        String refreshedToken = generateToken(claims);
        return refreshedToken;
    }

    public String getUsernameFromToken(String token) throws InvalidTokenException {
        final Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }
    
    public String getTenantFromToken(String token) throws InvalidTokenException {
        final Claims claims = getClaimsFromToken(token);
        return claims.get(CLAIM_KEY_TENANT, String.class);
    }

    public Date getExpirationDateFromToken(String token) throws InvalidTokenException {
        final Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    public boolean validateToken(String token, CustomUserDetails customUserDetails) throws InvalidTokenException {
        final String username = getUsernameFromToken(token);
        return (username.equals(customUserDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean canTokenBeRefreshed(String token) throws InvalidTokenException {
        return !isTokenExpired(token);
    }

    private Claims getClaimsFromToken(String token) throws InvalidTokenException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(getTokenFromBearer(token))
                    .getBody();
            return claims;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new InvalidTokenException();
        }
    }

    private boolean isTokenExpired(String token) throws InvalidTokenException {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) {
            throw new InvalidTokenException();
        }
        return expiration.before(new Date());
    }

    private String generateToken(Map<String, Object> claims) {
        final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
        final Date expiredDate = new Date(createdDate.getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();
    }
    
    private String getTokenFromBearer(String bearerToken) {
        if (bearerToken == null) {
            return null;
        }
        return bearerToken.replace(TOKEN_PREFIX, "");
    }
}
